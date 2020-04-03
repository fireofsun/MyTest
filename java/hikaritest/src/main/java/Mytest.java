import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.sql.Driver;
import java.sql.DriverManager;

public class Mytest {

    public void test(){
        /*HikariConfig hconf = new HikariConfig();
        hconf.setDriverClassName("org.apache.kylin.jdbc.Driver");
        hconf.setJdbcUrl("jdbc:kylin://40.73.96.103:7070/Guandata");
        hconf.setUsername("ADMIN");
        hconf.setPassword("KYLIN");*/


        Driver driver = null;
        try {
            driver = (Driver)Class.forName("org.apache.kylin.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DataSourceOfExternalClassLoader dataSource = new DataSourceOfExternalClassLoader();
        dataSource.setDriver(driver);
        dataSource.setUser("ADMIN");
        dataSource.setPassword("KYLIN");
        dataSource.setUrl("jdbc:kylin://40.73.96.103:7070/Guandata");





    }


    public void testMysql(){
        HikariConfig hconf = new HikariConfig();
        hconf.setDriverClassName("com.mysql.jdbc.Driver");
        hconf.setJdbcUrl("jdbc:mysql://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:3305/test56");
        hconf.setUsername("root");
        hconf.setPassword("Guan&*(123");

        // 对于自定义的datasource
        //hconf.setDataSource(ClassLoaderDSUtil.getDataSource(hconf, connectorId, driverName))
        HikariDataSource datasource = new HikariDataSource(hconf);
        datasource.close();
        Settings settings = new Settings().withParamType(ParamType.INLINED);


        //通过hikari获得的datasource交给jooq来使用
        DSLContext context = DSL.using(datasource, SQLDialect.SQLITE, settings);
        Result xxx = context.select(DSL.field("AA")).from("AA").fetch();
        xxx.get(1);
    }

    public void testKylin(){

    }


    public static void main(String[] args){
        Mytest aa = new Mytest();
        aa.testMysql();

    }
}
