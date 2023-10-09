FROM python:3.7

USER root

# --------------------------------------------------------
# JAVA
# --------------------------------------------------------
RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-11-jdk

ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk/
#ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-arm64/

# --------------------------------------------------------
# HADOOP
# --------------------------------------------------------
ENV HADOOP_VERSION=3.3.1
ENV HADOOP_URL=https://downloads.apache.org/hadoop/common/hadoop-$HADOOP_VERSION/hadoop-$HADOOP_VERSION.tar.gz
ENV HADOOP_PREFIX=/opt/hadoop-$HADOOP_VERSION
ENV HADOOP_CONF_DIR=/etc/hadoop
ENV MULTIHOMED_NETWORK=1
ENV USER=root
ENV HADOOP_HOME=/opt/hadoop-$HADOOP_VERSION
ENV PATH $HADOOP_PREFIX/bin/:$PATH
ENV PATH $HADOOP_HOME/bin/:$PATH

RUN set -x \
    && curl -fSL "$HADOOP_URL" -o /tmp/hadoop.tar.gz \
    && tar -xvf /tmp/hadoop.tar.gz -C /opt/ \
    && rm /tmp/hadoop.tar.gz*

RUN ln -s /opt/hadoop-$HADOOP_VERSION/etc/hadoop /etc/hadoop
RUN mkdir /opt/hadoop-$HADOOP_VERSION/logs
RUN mkdir /hadoop-data

USER root

COPY conf/core-site.xml $HADOOP_CONF_DIR/core-site.xml
COPY conf/hdfs-site.xml $HADOOP_CONF_DIR/hdfs-site.xml
COPY conf/mapred-site.xml $HADOOP_CONF_DIR/mapred-site.xml
COPY conf/yarn-site.xml $HADOOP_CONF_DIR/yarn-site.xml


####################################################################
# Base-Image Dockerfile
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
ADD ./config/hadoop-env.sh $HADOOP_HOME/etc/hadoop/
ADD ./config/core-site.xml $HADOOP_HOME/etc/hadoop/
ADD ./config/mapred-site.xml $HADOOP_HOME/etc/hadoop/
ADD ./config/yarn-site.xml $HADOOP_HOME/etc/hadoop/

CMD /bin/bash
