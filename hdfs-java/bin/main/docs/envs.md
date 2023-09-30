
hdfs
- [windows port] netstat -ano | findstr 9864
- [windows task kill] taskkill /f /pid 16364
- [hdfs dir] C:\hadoop-3.3.2\sbin
  - start-dfs.cmd, start-yarn.cmd

kafka
- [kafka dir]  C:\kafka\bin\windows
  => The input line is too long. => powershell 에서 실행 
  - C:\kafka\bin\windows\zookeeper-server-start.bat %ZOOKEEPER_HOME%\config\zookeeper.properties
  - C:\kafka\bin\windows\kafka-server-start.bat %KAFKA_HOME%\config\server.properties