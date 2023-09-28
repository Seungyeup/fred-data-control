package com.hdfs.etl.processor;


import com.hdfs.etl.util.PropertyFileReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class Hdfs2Kafka {

    private Properties systemProp = null;
    private FileSystem hadoopFs = null;
    private Properties kafkaProdProperty;
    private Producer<String, String> kafkaProducer;

    public Hdfs2Kafka() throws Exception {
        systemProp = PropertyFileReader.readPropertyFile("SystemConfig.properties");
        log.info(systemProp.toString());
        String HADOOP_CONF_DIR = systemProp.getProperty("hadoop.conf.dir");
        log.info(HADOOP_CONF_DIR);

        Configuration configuration = new Configuration();
        configuration.addResource(new Path("file:///" + HADOOP_CONF_DIR  + "/core-site.xml"));
        configuration.addResource(new Path("file:///" + HADOOP_CONF_DIR + "/hdfs-site.xml"));

        String namenode = systemProp.getProperty("hdfs.namenode.url");
        hadoopFs = FileSystem.get(new URI(namenode), configuration);

        kafkaProdProperty = new Properties();
        kafkaProdProperty.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, systemProp.get("kafka.brokerlist"));
        kafkaProdProperty.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaProdProperty.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaProdProperty.put(ProducerConfig.ACKS_CONFIG, "all");
        kafkaProdProperty.put(ProducerConfig.RETRIES_CONFIG, Integer.valueOf(1));
        kafkaProdProperty.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.valueOf(2000));
        kafkaProdProperty.put(ProducerConfig.LINGER_MS_CONFIG, Integer.valueOf(1));
        kafkaProdProperty.put(ProducerConfig.BUFFER_MEMORY_CONFIG, Integer.valueOf(133554432));

        kafkaProducer = new KafkaProducer<String, String>(kafkaProdProperty);

        log.info(hadoopFs.toString());
    }

    public List<String> readHdfsFile(String filename) throws Exception {
        Path path = new Path(filename);
        List<String> lines = new ArrayList<>();

        log.info(path.toString());

        if( !hadoopFs.exists(path) ){
            System.out.println("파일이 존재하지 않습니다.");
            return  null;
        }

        FSDataInputStream inputStream = hadoopFs.open(path);

        log.info(inputStream.toString());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // TODO : 값이 어떤 항목인지 읽기 불편함
        while (bufferedReader.ready()) {
            String[] line = bufferedReader.readLine().split(",");
            lines.add(line[2] + "," + line[3] + "," + line[4] + "," + line[5] + "," +
                    line[6] + "," + line[7] + "," + line[8] + "," + line[9] );
        }

        bufferedReader.close();
        inputStream.close();
        return lines;
    }

    public void getHdfsFilesInfo(String filename) throws Exception {
        Path path = new Path(filename);

        FileStatus fileStatus = hadoopFs.getFileStatus(path);

        if(fileStatus.isFile()){
            System.out.println("파일 블럭 사이즈 : " + fileStatus.getBlockSize());
            System.out.println("파일 Group : " + fileStatus.getGroup());
            System.out.println("파일 Owner : " + fileStatus.getOwner());
            System.out.println("파일 길이 : " + fileStatus.getLen());
        } else {
            System.out.println("파일이 아닙니다.");
        }
    }

    public void sendLines2Kafka(String topic, String line) {

        System.out.println(line);
        ProducerRecord<String, String> kafkaProducerRecord = new ProducerRecord<String, String>(topic, line);
        kafkaProducer.send(kafkaProducerRecord, new KafkaProducerCallBack());
        kafkaProducer.flush();
    }

    public void closeStream() throws Exception {
        if(hadoopFs != null){
            hadoopFs.close();
        }
        if(kafkaProducer != null){
            kafkaProducer.close();
        }
    }

    class KafkaProducerCallBack implements Callback {
        @Override
        public void onCompletion(RecordMetadata metadata, Exception exception) {
            if(exception != null){
                System.out.println(exception.getMessage());
            } else{
                System.out.println(metadata.topic() + "으로 " + metadata.serializedValueSize() + " 전송");
            }
        }
    }
}
