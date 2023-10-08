####################################################################
# Namenode, Datanode 에 사용할 기본 이미지를 생성한다.
#
# 이미지명 : hadoop_cluster_base_image
# 허브주소 : lsyeup1206/hadoop_cluster_base_image
#################################################################### 

FROM ubuntu:22.04

### ENV VARIABLES ###
ENV JAVA_VERSION=jdk-11.0.2
ENV HADOOP_VERSION=hadoop-3.3.2
ENV JAVA_TAR_FILE=openjdk-11.0.2_linux-x64_bin.tar.gz
ENV HADOOP_TAR_FILE=hadoop-3.3.2.tar.gz

RUN apt update

### INSTALL JDK 11 ###
WORKDIR /opt
ADD ./resource/${JAVA_TAR_FILE} .

ENV JAVA_HOME=/opt/${JAVA_VERSION}
ENV PATH=$PATH:$JAVA_HOME:$JAVA_HOME/bin

### INSTALL HADOOP 3.3.2 ###
ADD ./resource/${HADOOP_TAR_FILE} .

ENV HADOOP_HOME=/opt/${HADOOP_VERSION}
ENV PATH=$PATH:$HADOOP_HOME:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

### CHANGE HADOOP CONFIG FILES FOR BASE IMAGE ###
ADD ./config/base-image/hadoop-env.sh $HADOOP_HOME/etc/hadoop/
ADD ./config/base-image/core-site.xml $HADOOP_HOME/etc/hadoop/
ADD ./config/base-image/mapred-site.xml $HADOOP_HOME/etc/hadoop/
ADD ./config/base-image/yarn-site.xml $HADOOP_HOME/etc/hadoop/

CMD /bin/bash
