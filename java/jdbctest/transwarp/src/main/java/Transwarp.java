import java.sql.*;
public class Transwarp {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        String connectionString = "jdbc:hive2://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:10000/test";
        Connection conn = null;
        //String username="root";
        //String password="mysql1qaz@WSX";

        try {
            //conn = DriverManager.getConnection(connectionString,username,password);
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();


            String sql = "select `createtime` as `createtime_0` from `orders` limit 2000";

            PreparedStatement ps = conn.prepareStatement(sql);
            //ResultSet rs = stmt.executeQuery(sql);
            ResultSet rs = ps.executeQuery(sql);
            while( rs.next()){
                System.out.println(rs.getString(1));
                //rs.getString();
                //rs.getInt();
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
