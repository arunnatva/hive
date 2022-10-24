#!/bin/bash
rm -f hive-jdbc-client.jar
javac -cp `hadoop classpath` com/cloudera/ps/HiveJdbcClient.java
if [  $? -eq 0  ]; then
  jar cvf hive-jdbc-client.jar com/cloudera/ps/HiveJdbcClient*.class
else
  echo "**** Compilation Failed ****"
fi
