package com.hdfs.etl.processor;

import com.hdfs.etl.pojo.StoredColumnPojo;
import com.hdfs.etl.util.PropertyFileReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import scala.Predef;
import scala.collection.JavaConverters;
import scala.jdk.javaapi.CollectionConverters;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.spark.sql.functions.*;

public class Kafka2MySQL {
    private Properties systemProp;
    private SparkSession spark;

    public Kafka2MySQL() throws Exception {
        systemProp = PropertyFileReader.readPropertyFile("SystemConfig.properties");
        String appName = (String)systemProp.get("spark.batch.name");
        spark = SparkSession.builder().master("local[*]").appName(appName).getOrCreate();
    }

    private static <A, B> scala.collection.immutable.Map<A, B> toScalaMap(Map<A, B> m) {
        return JavaConverters.mapAsScalaMapConverter(m).asScala().toMap(Predef.$conforms());
    }

    public Dataset<Row> getDataframe(String kafkaTopic) {
        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("kafka.bootstrap.servers", systemProp.getProperty("kafka.brokerlist"));
        kafkaParams.put("subscribe", kafkaTopic);
        kafkaParams.put("startingOffsets", systemProp.getProperty("kafka.resetType"));

        Dataset<Row> df = spark.read().format("kafka")
                .options(kafkaParams)
                .load()
                .selectExpr("CAST(value AS STRING) as column")
                .filter(not(col("column").startsWith("date")));
        return df;
    }

    public void saveDataframe2MySQL(Dataset<Row> df, String targetSrc){
        Map<String, String> options = new HashMap<String, String>();

        Dataset<Row> dfs = df.select(from_csv(col("column"), StoredColumnPojo.getStructType(), toScalaMap(options)))
                .as("entityStoredPojo")
                .selectExpr("entityStoredPojo.date",
                        "entityStoredPojo.value",
                        "entityStoredPojo.id",
                        "entityStoredPojo.title",
                        "entityStoredPojo.state",
                        "entityStoredPojo.frequency_short",
                        "entityStoredPojo.units_short",
                        "entityStoredPojo.seasonal_adjustment_short"
                ).toDF();

        dfs.show();
        dfs.printSchema();

        Properties jdbcProps = new Properties();
        jdbcProps.put("user", systemProp.get("mysql.username"));
        jdbcProps.put("password", systemProp.get("mysql.password"));

        dfs.write().mode(SaveMode.Overwrite).jdbc((String)systemProp.get("mysql.output.uri"), targetSrc, jdbcProps);

        spark.close();
    }

}
