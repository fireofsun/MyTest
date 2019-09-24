import java.sql.*;

public class JustTesting {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        //Class.forName("com.mysql.jdbc.Driver");
        //String connectionString = "jdbc:mysql://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:3307/test?verifyServerCertificate=false&useSSL=true&zeroDateTimeBehavior=CONVERT_TO_NULL";
        //String connectionString = "jdbc:mysql://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:3306/test?verifyServerCertificate=false&useSSL=true&zeroDateTimeBehavior=CONVERT_TO_NULL";
        //String connectionString = "jdbc:mysql://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:3305/test?verifyServerCertificate=false&useSSL=true&zeroDateTimeBehavior=CONVERT_TO_NULL";

        //?zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&useUnicode=true&characterEncoding=UTF8&useCursorFetch=true
        //String connectionString = "jdbc:mysql://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:3307/test?zeroDateTimeBehavior=CONVERT_TO_NULL&tinyInt1isBit=false&useUnicode=true&characterEncoding=UTF8&useCursorFetch=true";
        //String connectionString = "jdbc:mysql://43.227.195.84:3306/flke?zeroDateTimeBehavior=CONVERT_TO_NULL&verifyServerCertificate=false&useSSL=true";
        //String connectionString = "jdbc:mysql://43.227.195.84:3306/flke?zeroDateTimeBehavior=CONVERT_TO_NULL&verifyServerCertificate=false&useSSL=true&tinyInt1isBit=false&useUnicode=true&characterEncoding=UTF8&useCursorFetch=true";
        //String connectionString = "jdbc:mysql://43.227.195.84:3306/flke?verifyServerCertificate=false&useSSL=true&zeroDateTimeBehavior=convertToNull";

        // Declare the JDBC objects.

        String connectionString = "jdbc:mysql://127.0.0.1:3376/test?zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&useUnicode=true&characterEncoding=UTF8&useCursorFetch=true";
        Connection conn = null;
        String username="root";
        String password="mysql1qaz@WSX";

        try {
            conn = DriverManager.getConnection(connectionString,username,password);
            Statement stmt = conn.createStatement();

            String sql ="select * from (select * from `aa_test`) as realTable limit 30;";

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