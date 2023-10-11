#!/bin/bash

docker network create hadoop_cluster_network

docker build -t lsyeup1206/hadoop_cluster_base -f Dockerfile-Hadoop-Cluster .

docker-compose -f docker-compose-hadoop-cluster.yml up -d
