#!/bin/bash

rm -rf ~/local-docker-volume/hadoop_cluster/*
mkdir -p ~/local-docker-volume/hadoop_cluster/hadoop_namenode
mkdir -p ~/local-docker-volume/hadoop_cluster/hadoop_datanode1
mkdir -p ~/local-docker-volume/hadoop_cluster/hadoop_datanode2
mkdir -p ~/local-docker-volume/hadoop_cluster/hadoop_datanode3

docker network create hadoop_cluster_network

docker build -t lsyeup1206/hadoop_cluster_base -f Dockerfile-Hadoop-Cluster .

docker-compose -f docker-compose-hadoop-cluster.yml up -d
