####################################################################
# lsyeup1206/hadoop_cluster_base_image를 바탕으로 네임노드 이미지를 생성한다.
#
# 이미지명 : hadoop_cluster_namenode
# 허브주소 : lsyeup1206/hadoop_cluster_namenode
####################################################################

FROM lsyeup1206/hadoop_cluster_base_image:latest


### MAKE NAMENODE DIR ###
RUN apt install netcat -y
RUN mkdir -p $HADOOP_HOME/hdfs/namenode

### CHANGE HADOOP CONFIG FILES FROM BASE IMAGE ###
ADD ./hdfs-site.xml $HADOOP_HOME/etc/hadoop/
ADD ./entrypoint.sh .

RUN chmod +x ./entrypoint.sh

### FORMAT NAMENODE ###
RUN hdfs namenode -format

### START NAMENODE
CMD hdfs namenode