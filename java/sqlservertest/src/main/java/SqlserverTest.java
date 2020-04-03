import java.io.*;
import java.sql.*;
import java.util.Properties;

public class SqlserverTest {
    public static void main(String[] args){
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            InputStream inStream = new FileInputStream(new File("./database.properties"));
            Properties prop = new Properties();
            prop.load(inStream);
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            String classname = prop.getProperty("classname");
            String connectionString = prop.getProperty("connectionString");
            String sql = prop.getProperty("sql");
            //Class.forName(classname);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionString, username, password);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while( rs.next()){
                String store = rs.getString(1);
                System.out.println(store);

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch(Exception e) {}
        }

    }

}
