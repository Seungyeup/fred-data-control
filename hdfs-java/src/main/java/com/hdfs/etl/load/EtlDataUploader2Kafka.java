package com.hdfs.etl.load;

import com.hdfs.etl.processor.Hdfs2Kafka;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class EtlDataUploader2Kafka {

    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
        log.info("service start");
        Hdfs2Kafka hdfs2kafka = new Hdfs2Kafka();
        log.info(hdfs2kafka.toString());

        hdfs2kafka.readHdfsFile("unemployee_annual.csv");
//               .forEach(str -> hdfs2kafka.sendLines2Kafka("topic_unempl_ann", str));
//        hdfs2kafka.getHdfsFilesInfo("unemployee_annual.csv");

//        hdfs2kafka.readHdfsFile("household_income.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_house_income_ann", str));
//        hdfs2kafka.getHdfsFilesInfo("household_income.csv");
//
//        hdfs2kafka.readHdfsFile("tax_exemption.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_tax_exemp_ann", str));
//        hdfs2kafka.getHdfsFilesInfo("tax_exemption.csv");
//
//        hdfs2kafka.readHdfsFile("civilian_force.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_civil_force_ann", str));
//        hdfs2kafka.getHdfsFilesInfo("civilian_force.csv");
//
//        hdfs2kafka.readHdfsFile("poverty.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_pov_ann", str));
//        hdfs2kafka.getHdfsFilesInfo("poverty.csv");
//
//        hdfs2kafka.readHdfsFile("real_gdp.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_gdp_ann", str));
//        hdfs2kafka.getHdfsFilesInfo("real_gdp.csv");
//
//        hdfs2kafka.readHdfsFile("unemployee_monthly.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_unempl_mon", str));
//        hdfs2kafka.getHdfsFilesInfo("unemployee_monthly.csv");
//
//        hdfs2kafka.readHdfsFile("earnings_Construction.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_earn_Construction_mon", str));
//        hdfs2kafka.getHdfsFilesInfo("earnings_Construction.csv");
//		/*
//		hdfs2kafka.readHdfsFile("earnings_Education_and_Health_Services.csv").forEach(str ->
//				hdfs2kafka.sendLines2Kafka("topic_earn_Education_and_Health_Services_mon", str));
//		hdfs2kafka.getHdfsFilesInfo("earnings_Education_and_Health_Services.csv");
//		*/
//        hdfs2kafka.readHdfsFile("earnings_Financial_Activities.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_earn_Financial_Activities_mon", str));
//        hdfs2kafka.getHdfsFilesInfo("earnings_Financial_Activities.csv");
//
//        hdfs2kafka.readHdfsFile("earnings_Goods_Producing.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_earn_Goods_Producing_mon", str));
//        hdfs2kafka.getHdfsFilesInfo("earnings_Goods_Producing.csv");
//
//        hdfs2kafka.readHdfsFile("earnings_Leisure_and_Hospitality.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_earn_Leisure_and_Hospitality_mon", str));
//        hdfs2kafka.getHdfsFilesInfo("earnings_Leisure_and_Hospitality.csv");
//
//        hdfs2kafka.readHdfsFile("earnings_Manufacturing.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_earn_Manufacturing_mon", str));
//        hdfs2kafka.getHdfsFilesInfo("earnings_Manufacturing.csv");
//
//        hdfs2kafka.readHdfsFile("earnings_Private_Service_Providing.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_earn_Private_Service_Providing_mon", str));
//        hdfs2kafka.getHdfsFilesInfo("earnings_Private_Service_Providing.csv");
//
//        hdfs2kafka.readHdfsFile("earnings_Professional_and_Business_Services.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_earn_Professional_and_Business_Services_mon", str));
//        hdfs2kafka.getHdfsFilesInfo("earnings_Professional_and_Business_Services.csv");
//
//        hdfs2kafka.readHdfsFile("earnings_Trade_Transportation_and_Utilities.csv").forEach(str ->
//                hdfs2kafka.sendLines2Kafka("topic_earn_Trade_Transportation_and_Utilities_mon", str));
//        hdfs2kafka.getHdfsFilesInfo("earnings_Trade_Transportation_and_Utilities.csv");

        hdfs2kafka.closeStream();
    }
}
