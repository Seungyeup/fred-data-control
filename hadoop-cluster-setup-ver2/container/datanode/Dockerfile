# build from hadoop-base:3.2.1
# that we built before using docker build -t hadoop-base:3.3.1 .
FROM lsyeup1206/hadoop_cluster_base

HEALTHCHECK CMD curl -f http://localhost:9864/ || exit 1

# ENV HDFS_CONF_dfs_datanode_data_dir=file:///hadoop/dfs/data
RUN mkdir -p /hadoop/dfs/data
VOLUME /hadoop/dfs/data

ADD run.sh /run.sh
RUN chmod a+x /run.sh

# 9864 : dfs.datanode.http.address
# 8042 : yarn nodemanager webapp address
# 9866 : dfs.datanode.address
#        the address at which the DataNode in the Hadoop cluster will listen for incoming data transfer requests.
EXPOSE 9864 8042 9866

CMD ["/run.sh"]