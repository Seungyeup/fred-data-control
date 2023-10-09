#!/bin/bash

# Wait for the datanode containers to be up and running
while ! nc -z datanode1 9864 && ! nc -z datanode2 9864 && ! nc -z datanode3 9864; do
  sleep 1
done

# Run the start-dfs.sh script
./start-dfs.sh
./start-yarn.sh