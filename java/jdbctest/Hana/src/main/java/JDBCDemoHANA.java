import sun.misc.BASE64Encoder;

import java.nio.charset.Charset;
import java.sql.*;

/**
 * JDBC Sample.
 *
 * @author <a href=”tigeriq123@163.com“> wangbing
 * @version CVS $Revision$ $Date$
 */
public class JDBCDemoHANA {


    private static final String DRIVER = "com.sap.db.jdbc.Driver";  //jdbc 4.0
    private static final String URL = "jdbc:sap://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:39044/?databaseName=TEST";

    public JDBCDemoHANA() {

    }

    public static void main(String[] args) throws Exception{
        JDBCDemoHANA demo = new JDBCDemoHANA();
        try {
//            demo.select();
//            demo.createHanaContext();
//            demo.createSleepSQL();
            demo.testing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testing() throws Exception{

        Connection con = this.getConnection(DRIVER, URL, "SYSTEM", "Guan&*(123");



        //PreparedStatement pstmt = con.prepareStatement("CREATE SCHEMA fff OWNED BY SYSTEM ");
        //PreparedStatement pstmt = con.prepareStatement("create table TEST2.BBBB (id INTEGER, name VARCHAR(50), time TIME, timestamp TIMESTAMP, money DOUBLE)");
        //PreparedStatement pstmt = con.prepareStatement("insert into TEST2.BBBB (ID, NAME, TIME, TIMESTAMP, MONEY) values (1, '张三', '2017-12-01 00:08:15', '2017-12-01 10:08:15', 980.86)");
        //PreparedStatement pstmt = con.prepareStatement("insert into TEST2.BBBB (ID, NAME, TIME, TIMESTAMP, MONEY) values (2, '李四', '12:38:45', '2017-11-30 13:08:15', 1080.06)");
        PreparedStatement pstmt = con.prepareStatement("create view TEST2.VCCCC as select * from TEST2.BBBB ");
        pstmt.execute();
        this.closeConnection(con, pstmt);

    /*
        Statement statement = con.createStatement();

        ResultSet rs = statement.executeQuery("select * from tables where table_name = 'aaaa'");
        while (rs.next()){
            System.out.println(rs.getString("SCHEMA_NAME"));
        }
        this.closeConnection(con, statement);
        */

/*
        Statement statement = con.createStatement();
        statement.execute("create table aaaa (id INTEGER, name VARCHAR(50), time TIME, timestamp TIMESTAMP, money DOUBLE)");
        */
    }



    public void select() throws Exception {
        Connection con = this.getConnection(DRIVER, URL, "SYSTEM", "Guan&*(123");
//        PreparedStatement pstmt = con.prepareStatement("select USER_ID,USER_NAME,USER_MODE from SYS.USERS");
//        PreparedStatement pstmt = con.prepareStatement("select * from tables");
        Statement statement = con.createStatement();
//        PreparedStatement pstmt = con.prepareStatement("select table_name from tables WHERE schema_name='SYSTEM'");
//        PreparedStatement pstmt = con.prepareStatement("insert into STUDENT values(?,?,?)");
        ResultSet rs = statement.executeQuery("select TABLE_NAME from tables");
        while (rs.next()){
            System.out.println(rs.getString("TABLE_NAME"));
        }
//        for(int i=0;i<10000;i++) {
//            pstmt.setInt(1,i);
//            pstmt.setString(2,"wanghongjie"+i);
//            pstmt.setInt(3,i+1);
//            pstmt.executeUpdate();
//            rs = pstmt.executeQuery();
//            this.processResult(rs);
//        }
        this.closeConnection(con, statement);

    }
    private void InsertSQL() throws Exception{
        Connection con = this.getConnection(DRIVER, URL, "SYSTEM", "Guan&*(123");
        Statement statement = con.createStatement();
        for(int i=0;i<200000;i++){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String sql = String.format("Insert into ORDERS VALUES ('20180518','%s',%s,'%s',%d,%d,%d,'杭州')",String.valueOf(i),String.valueOf(i+2000),timestamp.toString(),i+10,i+100,i+1000);
//            String sql = "Insert into ORDERS VALUES ('20180518','12','13',2018-05-14 12:00:00,12,56,22,'杭州')";
            System.out.println(sql);
            statement.executeUpdate(sql);
        }
    }
    private void createSleepSQL() throws Exception{
        Connection con = this.getConnection(DRIVER, URL, "SYSTEM", "Hana506sap");
        PreparedStatement pstmt = con.prepareStatement("CREATE OR REPLACE FUNCTION sleepSql3 (SLEEPING_TIME DECIMAL(16,2)) RETURNS result DECIMAL(16,2) AS V_TIME TIME; V_TIME_TO_WAKE TIME; BEGIN V_TIME := CURRENT_TIME; V_TIME_TO_WAKE := ADD_SECONDS (TO_TIME( V_TIME ), SLEEPING_TIME ); WHILE V_TIME != V_TIME_TO_WAKE DO V_TIME := CURRENT_TIME ; END WHILE; result := SLEEPING_TIME; END");
        pstmt.execute();
        this.closeConnection(con, pstmt);
    }

    private void createHanaContext() throws Exception{
        Connection con = this.getConnection(DRIVER, URL, "SYSTEM", "Hana506sap");
//        PreparedStatement pstmt = con.prepareStatement("drop table if EXISTS test");
//        pstmt.execute();
        PreparedStatement pstmt = con.prepareStatement("create table test (id INTEGER, name VARCHAR(50), time TIME, timestamp TIMESTAMP, money DOUBLE)");
//        pstmt.execute();
//        pstmt = con.prepareStatement("insert into test (ID, NAME, TIME, TIMESTAMP, MONEY) values (1, '张三', '2017-12-01 00:08:15', '2017-12-01 10:08:15', 980.86)");
//        pstmt.execute();
//        pstmt = con.prepareStatement("insert into test (ID, NAME, TIME, TIMESTAMP, MONEY) values (2, '李四', '12:38:45', '2017-11-30 13:08:15', 1080.06)");
//        pstmt.execute();
        this.closeConnection(con, pstmt);
    }
    private void processResult(ResultSet rs) throws Exception {
        if (rs.next()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int colNum = rsmd.getColumnCount();
            for (int i = 1; i <= colNum; i++) {
                if (i == 1) {
                    System.out.print(rsmd.getColumnName(i));
                } else {
                    System.out.print("\t" + rsmd.getColumnName(i));
                }

            }
            System.out.print("\n");
            System.out.println("———————–");
            do {
                for (int i = 1; i <= colNum; i++) {
                    if (i == 1) {
                        System.out.print(rs.getString(i));
                    } else {
                        System.out.print("\t"
                                + (rs.getString(i) == null ? "null" : rs
                                .getString(i).trim()));
                    }

                }
                System.out.print("\n");
            } while (rs.next());
        } else {
            System.out.println("query not result.");
        }

    }

    private Connection getConnection(String driver, String url, String user,
                                     String password) throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);

    }

    private void closeConnection(Connection con, Statement stmt)
            throws Exception {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

}
