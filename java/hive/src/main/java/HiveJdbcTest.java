import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Properties;

public class HiveJdbcTest {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }


        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "");
        // 这里传递了一个队列的hive_conf
        info.setProperty("hiveconf:hive.resultset.use.unique.column.names", "false");


        //Connection con = DriverManager.getConnection("jdbc:hive2://40.73.96.103:10000/test?zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&useUnicode=true&characterEncoding=UTF8&useCursorFetch=true&socketTimeout=7200000", info);
        Connection con = DriverManager.getConnection("jdbc:hive2://40.73.96.103:10000/test?hiveconf:hive.resultset.use.unique.column.names=false", "root", "");
        Statement stmt = con.createStatement();
        String tableName = "orders";
        // show tables
        String sql = "show tables '" + tableName + "'";
        System.out.println("Running: " + sql);
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            System.out.println(res.getString(1));
        }

        // describe table
        sql = "describe " + tableName;

        sql = "SELECT * FROM orderstypesfake";

        //sql = "SHOW TABLES";

        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1));
        }


        /*
        sql = "select * from " + tableName + " limit 20";
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(String.valueOf(res.getString(1)) + "\t" + res.getString(2));
        }*/


        /*
        //sql = "select MAKEDATE(YEAR(createtime), 1) + INTERVAL QUARTER(createtime) - 1 QUARTER from " + tableName + " limit 20";
        //sql = "select add_months(createtime,0) , from_unixtime(unix_timestamp((createtime)),'u') from " + tableName + " limit 20";
        sql = "select sum(`revenue`) as `revenue__sum__0` from (select order_id, a.member_id, number, creattime, cost, revenue, promotion, area, gender, birthday, reg_time, level_desc from orders a inner join users b on a.member_id=b.member_id) as `table_real` where `creattime` >= unix_timestamp('2017-04-02', 'yyyy-MM-dd') limit 20";
        //sql="set hive.resultset.use.unique.column.names=false;";
        System.out.println(sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            //System.out.println(String.valueOf(res.getString(1)) + "\t" + res.getString(2));
            System.out.println(String.valueOf(res.getString(1)));
        }*/
    }
}