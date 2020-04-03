package com.guandata;



import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.meta.derby.sys.Sys;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;

import static javafx.css.StyleOrigin.AUTHOR;


/**
 * 测试类
 * Created by jan on 2017/7/30.
 */
public class MysqlJooqTest {
    public static void main(String[] args) {
        // 用户名
        String userName = "root";
        // 密码
        String password = "Guan&*(123";
        // mysql连接url
        String url = "jdbc:mysql://guandata-dev-db1.chinanorth.cloudapp.chinacloudapi.cn:3316/test?useUnicode=true&characterEncoding=UTF-8";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            Result<Record> result = create.select().from("test1").fetch();

            result.get(0);
            result.get(1);
            for (Record r : result) {
                /*String name = (String) r.getValue("name");
                Object dec  = r.getValue("dec");
                if(dec instanceof BigDecimal){
                    System.out.println("bigdecimal");
                } else {
                    System.out.println("other");
                }
                System.out.println("ID:" + name);*/

                for(Field f : result.fields()){
                    System.out.println(MysqlJooqTest.checkTypeClass(f.getType()));
                }


                System.out.println("------------------------");


                for(Field f : result.fields()){
                    System.out.println(f.getName() + ":" + MysqlJooqTest.checkType(r.get(f.getName())));
                }

            }
        }
        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String checkType(Object t){
        if(t instanceof java.lang.Integer || t instanceof org.jooq.types.UShort)
            return "INTEGER";
        else if(t instanceof java.lang.Short)
            return "SHORT";
        else if(t instanceof java.lang.Byte || t instanceof org.jooq.types.UByte)
            return "SHORT";
        else if(t instanceof java.lang.Long || t instanceof org.jooq.types.UInteger)
            return "LONG";
        else if(t instanceof java.math.BigInteger || t instanceof org.jooq.types.ULong)
            return "LONG";
        else if(t instanceof java.lang.Float)
            return "FLOAT";
        else if(t instanceof java.lang.Double)
            return "DOUBLE";
        else if(t instanceof java.math.BigDecimal)
            return "DOUBLE";
        else if(t instanceof java.lang.Boolean)
            return "BOOLEAN";
        else if(t instanceof java.lang.String)
            return "STRING";
        else if(t instanceof java.sql.Date)
            return "DATE";
        else if(t instanceof java.sql.Timestamp)
            return "TIMESTAMP";
        else
            return "unknown type";
    }


    public static String checkTypeClass(Class c){
        if(c == java.lang.Integer.class || c == org.jooq.types.UShort.class)
            return "INTEGER";
        else if(c == java.lang.Short.class)
            return "SHORT";
        else if(c == java.lang.Byte.class || c == org.jooq.types.UByte.class)
            return "SHORT";
        else if(c == java.lang.Long.class || c == org.jooq.types.UInteger.class)
            return "LONG";
        else if(c == java.math.BigInteger.class || c == org.jooq.types.ULong.class)
            return "LONG";
        else if(c == java.lang.Float.class)
            return "FLOAT";
        else if(c == java.lang.Double.class)
            return "DOUBLE";
        else if(c == java.math.BigDecimal.class)
            return "DOUBLE";
        else if(c == java.lang.Boolean.class)
            return "BOOLEAN";
        else if(c == java.lang.String.class)
            return "STRING";
        else if(c == java.sql.Date.class)
            return "DATE";
        else if(c == java.sql.Timestamp.class)
            return "TIMESTAMP";
        else
            return "unknown type";
    }
}