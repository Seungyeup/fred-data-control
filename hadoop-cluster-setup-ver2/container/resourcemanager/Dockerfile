# build from hadoop-base:3.2.1
# that we built before using docker build -t hadoop-base:3.2.1 .
FROM lsyeup1206/hadoop_cluster_base

HEALTHCHECK CMD curl -f http://localhost:8088/ || exit 1

ADD run.sh /run.sh
RUN chmod a+x /run.sh

# 8088 : yarn.resourcemanager.webapp.address
# 8032 : yarn.resourcemanager.address
# 8030 : yarn.resourcemanager.scheduler.address
# 8031 : yarn.resourcemanager.resource-tracker.address
EXPOSE 8088 8032 8030 8031

CMD ["/run.sh"]