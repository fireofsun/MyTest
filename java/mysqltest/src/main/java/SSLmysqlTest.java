import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class SSLmysqlTest {
    public static void main(String args[]) {
        /*try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = null;
            String url = "jdbc:mysql://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:3366/test"+
                    "?verifyServerCertificate=true"+ "&useSSL=true";
            System.setProperty("javax.net.ssl.keyStore","./keystore");
            System.setProperty("javax.net.ssl.keyStorePassword","123456");
            //System.setProperty("javax.net.ssl.trustStore","./truststore");
            //System.setProperty("javax.net.ssl.trustStorePassword","123456");


            connection = DriverManager.getConnection(url,"root", "Guan&*(123");

            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery("select * from orders");
            while(rs.next())
                System.out.println(rs.getInt(1));
            Scanner in=new Scanner(System.in);
            in.next();
            connection.close();
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println(e);
        }*/
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:3366/test?" +
                "useSSL=true&verifyServerCertificate=false" +
                "&clientCertificateKeyStoreUrl=file:./keystore" +
                "&clientCertificateKeyStorePassword=123456";

        String USER = "root";
        String PASS = "Guan&*(123";

        Connection conn = null;
        Statement stmt = null;

        //System.setProperty("javax.net.ssl.keyStore","./keystore");
        //System.setProperty("javax.net.ssl.keyStorePassword","123456");

        try {
            // 注册JDBC驱动
            Class.forName(JDBC_DRIVER);
            // 建立连接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            // 设定需要执行的sql
            String sql = "select substring(`Shipping_Area`,-3) as `SUBSTR__0` from (select * from orders a inner join users b on a.Member_Id = b.UB_UserId) as `table_real` group by `SUBSTR__0` limit 20001";
            //String sql = "SELECT substring(`Shipping_Area`, -3) AS `SUBSTR__0` FROM orders LIMIT 30";
            // 执行操作
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // 通过字段检索
                String orderId = rs.getString("SUBSTR__0");
                // 输出数据
                System.out.print(" | " + orderId);
                System.out.print(" | \n");
            }

            // 断开连接
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se1) {
                se1.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }

        System.out.println("Goodbye!");
    }
}