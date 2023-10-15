# build from hadoop-base:3.2.1
# that we built before using docker build -t hadoop-base:3.2.1 .
FROM lsyeup1206/hadoop_cluster_base

HEALTHCHECK CMD curl -f http://localhost:8042/ || exit 1

ADD run.sh /run.sh
RUN chmod a+x /run.sh

# 8042 : yarn.nodemanager.webapp.address
EXPOSE 8042

CMD ["/run.sh"]