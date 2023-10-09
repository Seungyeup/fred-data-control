#!/bin/bash

docker network create hadoop_cluster_network

# docker build -t lsyeup1206/hadoop_cluster_base .

docker-compose up -d