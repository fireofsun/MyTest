import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
public class SSLmysqlTest {
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = null;
            String url = "jdbc:mysql://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn/test"+
                    "?verifyServerCertificate=true"+ "&useSSL=true"+ "&requireSSL=true";

            System.setProperty("javax.net.ssl.keyStore","./CERTIFICATES/mysql4/ca.pem");
            System.setProperty("javax.net.ssl.keyStorePassword","./CERTIFICATES/mysql4/client-cert.pem");
            System.setProperty("javax.net.ssl.trustStore","./CERTIFICATES/mysql4/client-key.pem");
//System.setProperty("javax.net.ssl.trustStorePassword","/CERTIFICATES/mysql4/client-key.pem");

            connection = DriverManager.getConnection(url,"root", "Guan&*(123");

            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery("select * from x_user");
            while(rs.next())
                System.out.println(rs.getInt(1));
            Scanner in=new Scanner(System.in);
            in.next();
            connection.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}