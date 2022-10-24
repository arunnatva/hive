#!/bin/bash
kdestroy
sleep 5
#java -cp `hadoop classpath`:/opt/cloudera/parcels/CDH-6.3.4-1.cdh6.3.4.p0.6751098/lib/hive/lib/*:hive-jdbc-client.jar -Djava.security.auth.login.config=/root/hive-client/jaas.conf -Djava.security.krb5.conf=/etc/krb5.conf -Dsun.security.krb5.debug=false -Djavax.security.auth.useSubjectCredsOnly=false com.cloudera.ps.HiveJdbcClient
java -cp `hadoop classpath`:/opt/cloudera/parcels/CDH-6.3.4-1.cdh6.3.4.p0.6751098/lib/hive/lib/*:hive-jdbc-client.jar -Djava.security.auth.login.config=/root/hive-client/jaas.conf -Djava.security.krb5.conf=/etc/krb5.conf -Dsun.security.krb5.debug=false com.cloudera.ps.HiveJdbcClient
