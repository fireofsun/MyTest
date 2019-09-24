import java.sql.*;

public class pg {

    public static void main(String []args) throws Exception{
        Connection c=null;
        Statement stmt=null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://postgres-nnfx7yng.sh.cdb.myqcloud.com:62/postgres", "root", "newman1!");
            c.setAutoCommit(false); // 把自动提交
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "select pay as '编码' from orders";
            ResultSet result=stmt.executeQuery(sql);
            ResultSetMetaData metaData = result.getMetaData();
            //stmt.executeUpdate(sql);

            int colum = metaData.getColumnCount();
            for (int i = 1; i <= colum; i++)
            {
                String typeStr = ""; //类型
                String columName = metaData.getColumnName(i);
                System.out.println(columName);
            }
            /*while(result.next()){
                System.out.println(result.getCursorName());
            }*/

            System.out.println("Table created successfully");

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        /*
        try {

            Class.forName("org.postgresql.Driver");
            //Connection db = DriverManager.getConnection("jdbc:postgresql://192.168.199.7:15432/test", "gpadmin", "");
            Connection db = DriverManager.getConnection("jdbc:postgresql://postgres-nnfx7yng.sh.cdb.myqcloud.com:62/postgres", "root", "newman1!");
            Statement st = db.createStatement();
//        ResultSet rs = st.executeQuery("SELECT * FROM films");
            ResultSet rs = st.executeQuery("SELECT * FROM field");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            rs.close();
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }*/

    }
}
