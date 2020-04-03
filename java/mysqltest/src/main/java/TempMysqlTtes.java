import java.sql.Connection;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;



import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;



public class TempMysqlTtes {



    private static String USERNAME = "root";

    private static String PASSWORD= "Guan&*(123";

    private static String HOST = "guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn";

    private static int PORT = 3366;

    private static String DATABASE = "test";

    private static String KEYSTORE_PATH = "file:./keystore";

    private static String  KEYSTORE_PASSWORD = "123456";

    private static String TRUSTORE_PATH = "file:./truststore";

    private static String  TRUSTORE_PASSWORD = "123456";

    public Connection connectMySqlDB() throws SQLException{

        MysqlDataSource mysqlDS=null;

        mysqlDS=new MysqlDataSource();

        mysqlDS.setUseSSL(true);

        mysqlDS.setRequireSSL(true);

        mysqlDS.setVerifyServerCertificate(false);

        mysqlDS.setLogger("com.mysql.jdbc.log.StandardLogger");

        mysqlDS.setClientCertificateKeyStoreType("JKS");

        mysqlDS.setClientCertificateKeyStoreUrl(KEYSTORE_PATH);

        mysqlDS.setClientCertificateKeyStorePassword(KEYSTORE_PASSWORD);

        mysqlDS.setTrustCertificateKeyStoreType("JKS");

        mysqlDS.setTrustCertificateKeyStoreUrl(TRUSTORE_PATH);

        mysqlDS.setTrustCertificateKeyStorePassword(TRUSTORE_PASSWORD);

        mysqlDS.setServerName(HOST);

        mysqlDS.setPort(PORT);

        mysqlDS.setUser(USERNAME);

        mysqlDS.setPassword(PASSWORD);

        mysqlDS.setDatabaseName(DATABASE);

        return mysqlDS.getConnection();

    }



    public static void main(String[] args) throws SQLException{

        TempMysqlTtes test=new TempMysqlTtes();

        Connection conn=null;

        Statement stm = null;

        ResultSet res = null;

        conn=test.connectMySqlDB();

        stm = conn.createStatement();

        res=stm.executeQuery("select * from t1");

        while(res.next()){

            System.out.println(res.getString(1));

            System.out.println(res.getString(2));

        }

        res.close();

        stm.close();

        conn.close();

    }

}