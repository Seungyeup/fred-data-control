# build from hadoop-base:3.2.1
# that we built before using docker build -t hadoop-base:3.2.1 .
FROM lsyeup1206/hadoop_cluster_base

HEALTHCHECK CMD curl -f http://localhost:9870/ || exit 1

# ENV HDFS_CONF_dfs_namenode_name_dir=file:///hadoop/dfs/name
RUN mkdir -p /hadoop/dfs/name
VOLUME /hadoop/dfs/name

ADD run.sh /run.sh
RUN chmod a+x /run.sh

# 9870 dfs.namenode.http-address
# 9867 dfs.datanode.ipc.address (namenode <-> datanode)
EXPOSE 9870 9867

CMD ["/run.sh"]