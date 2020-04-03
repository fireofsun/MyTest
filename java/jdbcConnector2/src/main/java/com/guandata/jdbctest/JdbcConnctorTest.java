package com.guandata.jdbctest;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class JdbcConnctorTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Connection connection = null; Statement statement = null;
        try {
            InputStream inStream = null;
            inStream = new FileInputStream(new File("./database.properties"));
            Properties prop = new Properties();
            prop.load(inStream);
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            String classname = prop.getProperty("classname");
            String connectionString = prop.getProperty("connectionString");
            String sql = prop.getProperty("sql");
            Class.forName(classname);
            connection = DriverManager.getConnection(connectionString, username, password);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<String> stores = new LinkedList<String>();
            while( rs.next()){
                String store = rs.getString(1);
                System.out.println(store);
                stores.add(store);

            }
            /*String allStores=String.join(",",stores);
            BufferedWriter out = new BufferedWriter(new FileWriter("./shops.config"));
            out.write(allStores);
            out.close();*/
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try { connection.close(); } catch(Exception e) {}
        }
    }
}