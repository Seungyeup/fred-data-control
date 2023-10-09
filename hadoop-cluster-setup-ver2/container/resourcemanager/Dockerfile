# build from hadoop-base:3.2.1
# that we built before using docker build -t hadoop-base:3.2.1 .
FROM lsyeup1206/hadoop_cluster_base

HEALTHCHECK CMD curl -f http://localhost:8088/ || exit 1

ADD run.sh /run.sh
RUN chmod a+x /run.sh

EXPOSE 8088

CMD ["/run.sh"]