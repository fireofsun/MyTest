import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class mySqlTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String connectionString =
                "jdbc:mysql://10.18.14.150:3306/guandata?verifyServerCertificate=false&useSSL=true&zeroDateTimeBehavior=convertToNull";

        // Declare the JDBC objects.
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connectionString);
            Statement stmt = conn.createStatement();
            String sql ="\tSELECT TOP 100 PERCENT [userId] AS [userId__0], SUM([age]) AS [age__sum__0], SUM([level]) AS [level__sum__1]\n" +
                    "\t\t, 0 AS [__grouping_id]\n" +
                    "\tFROM (\n" +
                    "\t\tSELECT *\n" +
                    "\t\tFROM FAKEUSERS\n" +
                    "\t) [table_real]\n" +
                    "\tGROUP BY [userId]\n" +
                    "\tUNION ALL\n" +
                    "\t(SELECT NULL AS [userId__0], SUM([age]) AS [age__sum__0], SUM([level]) AS [level__sum__1]\n" +
                    "\t\t, 1 AS [__grouping_id]\n" +
                    "\tFROM (\n" +
                    "\t\tSELECT *\n" +
                    "\t\tFROM FAKEUSERS\n" +
                    "\t) [table_real])\n" +
                    "\tORDER BY [level__sum__1] DESC";
            ResultSet rs = stmt.executeQuery(sql);
            /*while( rs.next()){

                rs.getString();
                rs.getInt();
            }*/

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) try { conn.close(); } catch(Exception e) {}
        }
    }
}