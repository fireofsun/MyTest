import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgreTest {
    public static void main(String []args){
        Connection c=null;
        Statement stmt=null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://postgres-nnfx7yng.sh.cdb.myqcloud.com:62/postgres", "root", "newman1!");
            c.setAutoCommit(false); // 把自动提交
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "show tables";
            ResultSet result=stmt.executeQuery(sql);
            //stmt.executeUpdate(sql);
            result.toString();
            System.out.println("Table created successfully");

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }
}
