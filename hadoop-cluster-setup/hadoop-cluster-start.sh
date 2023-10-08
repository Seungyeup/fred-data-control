docker network create --driver bridge hadoop_cluster_net

#namenode
docker run -d --net hadoop_cluster_net --hostname hadoop-cluster-namenode -p 9870:9870 -p 9864:9864 -p 8088:8088 --name namenode lsyeup1206/hadoop_cluster_namenode:latest

#datanode 1
docker run -d --net hadoop_cluster_net --name datanode1 lsyeup1206/hadoop_cluster_datanode:latest

#datanode 2
docker run -d --net hadoop_cluster_net --name datanade2 lsyeup1206/hadoop_cluster_datanode:latest

#datanode 3
docker run -d --net hadoop_cluster_net --name datanode3 lsyeup1206/hadoop_cluster_datanode:latest
