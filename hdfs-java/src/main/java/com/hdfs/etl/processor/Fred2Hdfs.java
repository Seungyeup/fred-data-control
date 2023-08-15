package com.hdfs.etl.processor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.hdfs.etl.pojo.EtlColumnPojo;
import com.hdfs.etl.pojo.FredColumnPojo;
import com.hdfs.etl.util.PropertyFileReader;
import com.hdfs.etl.util.US_STATES;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.checkerframework.checker.units.qual.C;


import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Fred2Hdfs {

    public enum APITYPES {
        SEARCH("series/search"),
        OBSERVATION("series/observations");

        private String apiType;

        APITYPES(String apiType){
            this.apiType = apiType;
        }
    }

    public enum FREQUENCY {
        MONTH('M'),
        YEAR('A');

        private char freq;

        FREQUENCY(char freq) {
            this.freq = freq;
        }
    }

    // 시스템 속성을 저장하는 객체
    private Properties fredProp = null;

    // FRED 의 Json 타입의 데이터를 EtlColumnPojo 로 매핑하여 변환
    private ObjectMapper mapper = null;

    // 하둡 파일시스템 관리 객체
    private FileSystem hadoopFs = null;

    public Fred2Hdfs() throws Exception {
        fredProp = PropertyFileReader.readPropertyFile("SystemConfig.properties");
        String HADOOP_CONF_DIR = fredProp.getProperty("hadoop.conf.dir");

        mapper = new ObjectMapper();

        Configuration configuration = new Configuration();
        configuration.addResource(new Path("file:///" + HADOOP_CONF_DIR + "/core-site.xml"));
        configuration.addResource(new Path("file:///" + HADOOP_CONF_DIR + "/hdfs-site.xml"));

        String namenode = fredProp.getProperty("hdfs.namenode.url");
        hadoopFs = FileSystem.get(new URI(namenode), configuration);
    }

    /*
    *  FRED 에서 값을 읽어와서 EtlColumnPojo 클래스에 매핑
    */
    public List<EtlColumnPojo> getEtlListData(FREQUENCY frequency, US_STATES state, String searchText) throws Exception {
        String fredUrl = fredProp.getProperty("fred.url");
        String apiKey = fredProp.getProperty("fred.apiKey");
        String fileType = "json";
        String searchUrl = fredUrl
                + APITYPES.valueOf("SEARCH").apiType
                + "?search_text=" + searchText.replace(' ', '+')
                + state.getValueFullName().replaceAll(" ", "+")
                + "&api_key=" + apiKey
                + "&file_type" + fileType;

        System.out.println(searchUrl);

        JsonNode rootNode = mapper.readTree(new URL(searchUrl));
        // TODO : sleep 아닌 좀 더 효율적으로 기다리는 방법 없나? (sync 라던지...)
        Thread.sleep(500);
        ArrayNode nodeSeries = (ArrayNode) rootNode.get("series");
        List<FredColumnPojo> listFredData = mapper.readValue(nodeSeries.traverse(), new TypeReference<List<FredColumnPojo>>(){});

        Predicate<FredColumnPojo> predicate =
                fred -> (fred.getTitle().equals(searchText + state.getValueFullName()))
                            && (fred.getFrequency_short().charAt(0) == frequency.freq)
                            && fred.getSeasonal_adjustment_short().equals("MSA");

        List<EtlColumnPojo> listData = listFredData.stream().filter(predicate).flatMap(pojo -> {
                String observUrl = fredUrl
                        + APITYPES.valueOf("OBSERVATION").apiType
                        + "?series_id" + pojo.getId()
                        + "&api_key" + apiKey
                        + "&file_type=" + fileType;

                System.out.println(observUrl);

                try{
                    JsonNode nodeValue = mapper.readTree(new URL(observUrl));
                    Thread.sleep(500);
                    ArrayNode nodeValueObserv = (ArrayNode) nodeValue.get("observations");
                    List<EtlColumnPojo> listEtlData = mapper.readValue(nodeValueObserv.traverse(),
                            new TypeReference<List<EtlColumnPojo>>() {});

                    for (EtlColumnPojo valuePojo : listEtlData) {
                        valuePojo.setState(state.getValueFullName());
                        valuePojo.setId(pojo.getId());
                        valuePojo.setTitle(pojo.getTitle().replace(',', '_'));
                        valuePojo.setFrequency_short(pojo.getFrequency_short());
                        valuePojo.setUnits_short(pojo.getUnits_short());
                        valuePojo.setSeasonal_adjustment_short(pojo.getSeasonal_adjustment_short());
                    }

                    return listEtlData.stream();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
        }).collect(Collectors.toList());

        return  listData;
    }

    /*
     *  FRED 에서 추출한 데이터를 파일로 출력
     */
    public void writeCsv2Hdfs(String filename, List<EtlColumnPojo> nodeList) throws Exception {
        Path hadoopPath = new Path(filename);

        FSDataOutputStream hadoopOutputStream = null;
        BufferedWriter bufferedWriter = null;

        if(nodeList.size() != 0){
            if(hadoopFs.exists(hadoopPath)){
                hadoopOutputStream = hadoopFs.append(hadoopPath);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(hadoopOutputStream,
                        StandardCharsets.UTF_8));
            } else {
                hadoopOutputStream = hadoopFs.create(hadoopPath, true);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(hadoopOutputStream,
                        StandardCharsets.UTF_8));
                bufferedWriter.write(nodeList.get(0).getColumns());
                bufferedWriter.newLine();
            }

            for ( EtlColumnPojo pojo : nodeList) {
                bufferedWriter.write(pojo.getValues());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            hadoopOutputStream.close();
        }
    }

    public void writeCsv2Local(boolean first, String path, String filename, List<EtlColumnPojo> nodeList) throws Exception {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        csvMapper.findAndRegisterModules();
        csvMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        CsvSchema schema = csvMapper.schemaFor(EtlColumnPojo.class).withColumnSeparator(',');
        if (first) {
            schema = schema.withHeader();
        } else {
            schema = schema.withoutHeader();
        }

        File outputFile = new File(path + filename);
        OutputStream outputStream = new FileOutputStream(outputFile, true);

        ObjectWriter ow = csvMapper.writer(schema);
        ow.writeValue(outputStream,  nodeList);
        outputStream.close();
    }

    public void clearInputFiles(String path, String filename) throws Exception {
        Path hadoopPath = new Path(filename);

        if(hadoopFs.exists(hadoopPath)){
            hadoopFs.delete(hadoopPath, true);
            System.out.println("Hadoop File System 에서 `" + hadoopPath.getName() + "` 파일이 삭제되었습니다.");
        }

        File localPath = new File(path + filename);
        if( localPath.exists() ){
            localPath.delete();
            System.out.println("Local 에서 `" + localPath.getName() + "` 파일이 삭제되었습니다.");
        }

    }

    public void closeStream() throws Exception {
        if(hadoopFs != null){
            hadoopFs.close();
        }
    }

}
