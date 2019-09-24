import java.sql.*;

public class SqlServerTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        /*String connectionString =
                "jdbc:sqlserver://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:1433;"
                        + "database=test;"
                        + "user=sa;"
                        + "password= Guan&*(123;";*/
        String connectionString =
                "jdbc:sqlserver://61.183.215.2:1333"
                        + "database=interface"
                        + "user=sa"
                        + "password= fxhy2014;";

        // Declare the JDBC objects.
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            String sql ="select * from yingbao";
            ResultSet rs = stmt.executeQuery(sql);
            while( rs.next()){

                //rs.getString("a");
                String a = rs.getString("a");
                System.out.println(a.toString());
                //rs.getInt("id");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) try { conn.close(); } catch(Exception e) {}
        }
    }
}
