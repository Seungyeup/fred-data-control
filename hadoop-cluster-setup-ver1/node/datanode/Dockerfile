####################################################################
# lsyeup1206/hadoop_cluster_base_image를 바탕으로 데이터노드 이미지를 생성한다.
#
# 이미지명 : hadoop_cluster_base_image
# 허브주소 : lsyeup1206/hadoop_cluster_datanode
####################################################################

FROM lsyeup1206/hadoop_cluster_base_image:latest


### MAKE NAMENODE DIR ###
RUN mkdir -p $HADOOP_HOME/hdfs/datanode

### CHANGE HADOOP CONFIG FILES FROM BASE IMAGE ###
ADD ./hdfs-site.xml $HADOOP_HOME/etc/hadoop/

### START DATANODE ###
CMD hdfs datanode