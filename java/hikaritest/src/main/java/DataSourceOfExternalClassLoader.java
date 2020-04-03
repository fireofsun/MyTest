import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class DataSourceOfExternalClassLoader implements DataSource{
    private transient PrintWriter logWriter = null;
    private Driver driver = null;
    private String user = null;
    private String password = null;
    private String url = null;
    private String database = null;
    private String host = null;
    private String port = null;

    public DataSourceOfExternalClassLoader() {
    }

    public Connection getConnection() throws SQLException {
        return this.getConnection(null, null);
    }

    public Connection getConnection(String user, String password) throws SQLException {
        try {
            Properties info = new Properties();
            if (user != null) {
                info.setProperty("user", user);
            } else {
                info.setProperty("user", this.user);
            }

            if (password != null) {
                info.setProperty("password", password);
            } else {
                info.setProperty("password", this.password);
            }

            if (this.url != null) {
                return driver.connect(this.url, info);
            } else if (this.host != null && this.port != null && this.database != null) {
                return driver.connect("jdbc:hive2://" + this.host + ":" + this.port + "/" + this.database, info);
            } else {
                throw new SQLException("Need to set valid host, port, database");
            }
        } catch (Exception e) {
            throw new SQLException("Error in getting HiveConnection", e);
        }
    }

    public void setDriver(Driver driver){
        this.driver = driver;
    }

    public void setURL(String url) {
        this.setUrl(url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public PrintWriter getLogWriter() {
        return this.logWriter;
    }

    public int getLoginTimeout() {
        return 0;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("Method not supported");
    }

    public void setLogWriter(PrintWriter p) {
        this.logWriter = p;
    }

    public void setLoginTimeout(int timeout) {
    }

    public boolean isWrapperFor(Class<?> c) {
        return c.isInstance(this);
    }

    public <T> T unwrap(Class<T> arg0) throws SQLException {
        throw new SQLException("HiveDataSource ClassCastException");
    }
}