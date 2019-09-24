import java.sql.*;
import java.util.Properties;

public class AdsTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Connection connection = null; Statement statement = null; ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://beastbi-14f41059-azB.cn-hangzhou-2.ads.aliyuncs.com:10052/beastbi?useUnicode=true&characterEncoding=UTF-8";
            Properties connectionProps = new Properties(); connectionProps.put("user", "LTAIJsqJX69w2Cdu"); connectionProps.put("password", "XafRziZliUsA4L7FgnFeIKxEFgHlUC");
            connection = DriverManager.getConnection(url, connectionProps); statement = connection.createStatement();
            //select `table_name` from `information_schema`.`tables` where `table_schema` in (select database() from dual) order by `table_name`
            //String query = "select count(*) from information_schema.tables";
            //String query = "select 1";
            //String query = "select `table_name`,table_schema from `information_schema`.`tables` order by `table_name`";
            String query = "select `schema_name` from `information_schema`.`schemata`";
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getObject(1));
            }
        } catch (ClassNotFoundException e) { e.printStackTrace();
        } catch (SQLException e) { e.printStackTrace();
        } catch (Exception e) { e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try { statement.close();
                } catch (SQLException e) { e.printStackTrace();
                } }
            if (connection != null) { try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); }
            } }

    }
}
