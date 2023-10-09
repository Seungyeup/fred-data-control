####################################################################
# Base-Image Dockerfile
#
# 이미지명 : hadoop_cluster_base_image
# 허브주소 : lsyeup1206/hadoop_cluster_base_image
#################################################################### 
FROM ubuntu:22.04


### ENV VARIABLES ###
ENV USER=root
ENV JAVA_VERSION=jdk-11.0.2
ENV HADOOP_VERSION=hadoop-3.3.2
ENV JAVA_TAR_FILE=openjdk-11.0.2_linux-x64_bin.tar.gz
ENV HADOOP_TAR_FILE=hadoop-3.3.2.tar.gz
ENV HADOOP_CONF_DIR=/etc/hadoop

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


### INSTALL PYTHON 11 ###
RUN apt update && apt upgrade -y
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get install software-properties-common -y
RUN add-apt-repository ppa:deadsnakes/ppa -y
RUN apt update
RUN apt install python3.11 -y


### INSTALL JDK 11 ###
WORKDIR /opt
ADD ./resource/${JAVA_TAR_FILE} .

ENV JAVA_HOME=/opt/${JAVA_VERSION}
ENV PATH=$PATH:$JAVA_HOME:$JAVA_HOME/bin


### INSTALL HADOOP 3.3.2 ###
ADD ./resource/${HADOOP_TAR_FILE} .

ENV HADOOP_HOME=/opt/${HADOOP_VERSION}
ENV PATH=$PATH:$HADOOP_HOME:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

RUN ln -s /opt/$HADOOP_VERSION/etc/hadoop /etc/hadoop
RUN mkdir /opt/$HADOOP_VERSION/logs
RUN mkdir /hadoop-data


### CHANGE HADOOP CONFIG FILES FOR BASE IMAGE ###
ADD ./config/hdfs-site.xml $HADOOP_HOME/etc/hadoop/
ADD ./config/core-site.xml $HADOOP_HOME/etc/hadoop/
ADD ./config/mapred-site.xml $HADOOP_HOME/etc/hadoop/
ADD ./config/yarn-site.xml $HADOOP_HOME/etc/hadoop/