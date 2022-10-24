package com.cloudera.ps;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;

import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.conf.Configuration;

public class HiveJdbcClient {

  private static String driverName = "org.apache.hive.jdbc.HiveDriver";


  /**
   * @param args
   * @throws SQLException
   */
  public static void main(String[] args) throws SQLException {

   String principal = "thomas@TEST.COM";
   String keytabLocation = "/opt/cloudera/security/keytabs/thomas.keytab";


   try {
     UserGroupInformation ugi = UserGroupInformation.getLoginUser();
     Configuration conf = new Configuration();
     conf.addResource("/etc/hadoop/core-site.xml");

     UserGroupInformation.setConfiguration(conf);
     ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(principal, keytabLocation);


     if(!UserGroupInformation.isSecurityEnabled()) {
        System.out.println("kerberos is not enabled");
     } else {
        System.out.println(" **** KERBEROS IS ENABLED **** ");
        System.out.println(UserGroupInformation.getLoginUser());
        System.out.println(ugi.getCurrentUser());
    }

   Class.forName("org.apache.hive.jdbc.HiveDriver");

   ugi.checkTGTAndReloginFromKeytab();
   ugi.doAs(new PrivilegedExceptionAction<Void>() {

   @Override
   public Void run() throws Exception {


    Connection con = DriverManager.getConnection("jdbc:hive2://ccycloud-2.tkreutzer.root.hwx.site:10000/default;AuthMech=1;KrbRealm=TEST.COM;principal=hive/_HOST@TEST.COM;ssl=true;sslTrustStore=/opt/cloudera/security/pki/cluster_truststore.jks;trustStorePassword=Cloudera123");
    Statement stmt = con.createStatement();
    String tableName = "testHiveDriverTable";
    String sql = "select * from hive_migr.can_migr_tbl limit 10";
    System.out.println("Running: " + sql);
    ResultSet res = stmt.executeQuery(sql);
    while(res.next()) {
      System.out.println(res.getString(1) + "\t" + res.getString(2));
    }
     return null;
   }
  });
  }
  catch (ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
      System.exit(1);
    }

  catch (Exception e) {
     e.printStackTrace();
     System.exit(1);
   }

  }
}
