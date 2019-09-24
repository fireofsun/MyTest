import java.sql.*;

public class TiDB {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String connectionString = "jdbc:mysql://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:4000/test?zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&useUnicode=true&characterEncoding=UTF8&useCursorFetch=true";
        Connection conn = null;
        String username="root";
        String password="Guan&*(123";

        try {
            conn = DriverManager.getConnection(connectionString,username,password);
            Statement stmt = conn.createStatement();

            String sql ="select 1 from dual";

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
