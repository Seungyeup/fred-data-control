import pyarrow.fs as pa_fs
import json

# pyarrow 원천
# https://arrow.apache.org/docs/python/filesystems.html
# CLASSPATH: must contain the Hadoop jars. You can set these using:
# os.environ['ARROW_LIBHDFS_DIR'] = '/Users/lsy/hadoop/lib/native'

with open('config.json') as f:
    config = json.load(f)

# print(config)

hdfs = pa_fs.HadoopFileSystem(config.get('remote_hadoop_master_node_url'),
                              port=config.get('remote_hadoop_master_node_port'))

# get files
files = hdfs.get_file_info(pa_fs.FileSelector('/'))

# print files
for file_info in files:
    print(f"Name: {file_info.path}, Type: {file_info.type}, Size: {file_info.size}")