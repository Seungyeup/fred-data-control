import os
import numpy as np
import pyarrow as pa
from pyarrow import fs # pyarrow 사용을 위해서는 하둡 라이브러리 파일이 있어야 함

# hdfs = fs.HadoopFileSystem('localhost', port=9000, user='lsy')

# pyarrow 원천
# https://arrow.apache.org/docs/python/filesystems.html
# CLASSPATH: must contain the Hadoop jars. You can set these using:
# os.environ['ARROW_LIBHDFS_DIR'] = '/Users/lsy/hadoop/lib/native'
hdfs = fs.HadoopFileSystem.from_uri('hdfs://localhost:9000/?user=lsy')
# hdfs = fs.HadoopFileSystem(host, port, user=user, kerb_ticket=ticket_cache_path)
print(hdfs)