import os
from pyarrow import fs

# hdfs = fs.HadoopFileSystem('localhost', port=9000, user='lsy')

# pyarrow 원천
# https://arrow.apache.org/docs/python/filesystems.html
# CLASSPATH: must contain the Hadoop jars. You can set these using:
os.environ['ARROW_LIBHDFS_DIR'] = '/Users/lsy/hadoop/lib/native'
hdfs = fs.HadoopFileSystem.from_uri('hdfs://localhost:9000/?user=lsy')
