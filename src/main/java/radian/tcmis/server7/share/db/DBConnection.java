package radian.tcmis.server7.share.db;

import java.sql.*;
/**
 * This class wraps the Connection class and adds a timestamp
 * that indicates how old a connection is.  This class is used
 * by the DBConnectionFactory to expire connections when they
 * get too old.
 *
 * NOTE: There's got to be a better way to do this.
 *
 * @author Frank Y. Kim
 */
public class DBConnection {
    /** The database connection. */
    private Connection connection = null;
    /** The url of this connection. */
    private String url = "";
    /** The time in milliseconds at which the connection was created. */
    private long timestamp;

    /** 12-12-02 keep track whether it is a time out connection. */
    private boolean sqlTimeOut = false;

    /**
     * This constructor is used in DBConnectionFactory only.
     * It will be removed once DBConnectionFactory is removed.
     */
    //original trong 9-25 protected DBConnection(Connection connection) {
    public DBConnection(Connection connection) {
        this.connection = connection;
        this.timestamp = System.currentTimeMillis();
    }
    /**
     * Constructor that initializes attributes.
     */
    //original trong 9-25 protected DBConnection(Connection connection, String url) {
    public DBConnection(Connection connection, String url) {
        this.connection = connection;
        this.url = url;
        this.timestamp = System.currentTimeMillis();
    }
    /**
     * Returns a connection.
     *
     * @return The database connection.
     */
    public Connection getConnection()
    {
        return connection;
    }
    /**
     * Returns a long representing the time this connection was created.
     *
     * @return The time in milliseconds that this connection was created.
     */
    public long getTimestamp()
    {
        return timestamp;
    }
    public String getUrl()
    {
        return url;
    }
    public void clearConnection() {
      this.connection = null;
    }
    //12-12-02
    public void setSqlTimeOut(boolean b) {
      this.sqlTimeOut = b;
    }
    public boolean getSqlTimeOut() {
      return this.sqlTimeOut;
    }
}