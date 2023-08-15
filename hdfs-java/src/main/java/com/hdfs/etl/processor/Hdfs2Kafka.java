package com.hdfs.etl.processor;


import com.hdfs.etl.util.PropertyFileReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Hdfs2Kafka {

    private Properties systemProp = null;
    private FileSystem hadoopFs = null;

    public Hdfs2Kafka() throws Exception {
        systemProp = PropertyFileReader.readPropertyFile("SystemConfig.properties");
        String HADOOP_CONF_DIR = systemProp.getProperty("hadoop.conf.dir");

        Configuration configuration = new Configuration();
        configuration.addResource(new Path("file:///" + HADOOP_CONF_DIR  + "/core-site.xml"));
        configuration.addResource(new Path("file:///" + HADOOP_CONF_DIR + "/hdfs-site.xml"));

        String namenode = systemProp.getProperty("hdfs.namenode.url");
        hadoopFs = FileSystem.get(new URI(namenode), configuration);
    }

    public List<String> readHdfsFile(String filename) throws Exception {
        Path path = new Path(filename);
        List<String> lines = new ArrayList<>();

        if( !hadoopFs.exists(path) ){
            System.out.println("파일이 존재하지 않습니다.");
            return  null;
        }

        FSDataInputStream inputStream = hadoopFs.open(path);
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
    }

    public void closeStream() throws Exception {
        if(hadoopFs != null){
            hadoopFs.close();
        }
    }

}
