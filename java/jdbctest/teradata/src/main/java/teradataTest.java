import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class teradataTest {
    public static void main(String []args){
        Connection c=null;
        Statement stmt=null;
        try {
            Class.forName("com.teradata.jdbc.TeraDriver");
            c= DriverManager.getConnection("jdbc:teradata://192.168.199.203/DATABASE=testbase,COLUMN_NAME=ON", "testuser", "testpassword");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "select * from MRT_STORE_KPI_DAILY";
            //String sql = "select * from DIM_STORE_BASE_INFO_CUR";

            ResultSet result=stmt.executeQuery(sql);
            result.toString();
            result.next();
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
