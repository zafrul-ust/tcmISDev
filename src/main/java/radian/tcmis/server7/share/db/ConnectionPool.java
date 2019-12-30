package radian.tcmis.server7.share.db;

import java.sql.*;
import java.util.Stack;
/**
 * This class implements a simple connection pooling scheme.
 *
 * @author Frank Y. Kim
 */
public class ConnectionPool {
    /** Pool containing database connections. */
    private Stack pool = null;
    /** The current number of database connections that have been created. */
    private int totalConnections = 0;
    /** The maximum number of database connections that can be created. */
    private int maxConnections = 10;
    /** The resource file that contains database properties. */
    private ResourceLibrary resource = null;
    /** The amount of time in milliseconds that a connection will be pooled. */
    private long expiryTime = 3600000; // 1 hour
/**
 * Constructor that initializes attributes.
 */
protected ConnectionPool() {
    pool = new Stack();
    
    try
    {
        //minConnections = SystemResources.getInstance().getInt("database.minConnections");
        maxConnections = SystemResources.getInstance().getInt("database.maxConnections");
        expiryTime = SystemResources.getInstance().getLong("database.expiryTime");
    }
    catch (Exception e)
    {
        // just use the values specified in this class
    }
}
/**
 * Constructor that initializes attributes.
 */
protected ConnectionPool(int maxCons, long expiryTime) {
    pool = new Stack();

//  this.minConnections = minCons;
    this.maxConnections = maxCons;
    this.expiryTime = expiryTime;
}
    /**
     * Close any open connections when this object is garbage collected.
     */
    protected void finalize() throws Throwable
    {
        try
        {
            while (!pool.empty())
            {
                Connection connection = (Connection)pool.pop();
                connection.close();
            }
        }
        catch (SQLException e)
        {} // can't do anything at this point
    }
    /**
     * Returns a connection.
     *
     * @return A database connection.
     * @exception Exception.
     */
    protected synchronized DBConnection getConnection(String driver
                                                    , String url
                                                    , String username
                                                    , String password) throws Exception
    {
        DBConnection connection = null;
        
        if ( pool.empty() && totalConnections<maxConnections )
        {
            connection = getNewConnection(driver, url, username, password);
        }
        else
        {
            connection = getPooledConnection();
        }
        return connection;
    }
    /**
     * Returns a connection to the database depending on the type.
     *
     * @return A database connection.
     */
    private DBConnection getNewConnection(String driver
                                        , String url
                                        , String username
                                        , String password) throws Exception
    {
        DB db = DBFactory.create( driver );
        db.init( url, username, password );

        totalConnections++;
        return new DBConnection( db.getConnection(), url );
    }
    /**
     * Returns a pooled connection to the database.
     *
     * @return A database connection.
     */
    private synchronized DBConnection getPooledConnection() throws Exception
    {
        while (true)
        {
            if (!pool.empty())
            {
                return (DBConnection) pool.pop();
            }
            wait();
        }
        
/*      while (true)
        {
            if (!pool.empty())
            {
                DBConnection con = (DBConnection) pool.pop();
                if ( isValid(con) ) {
                    return con;
                } else {
                    totalConnections--;
                }
            }
            else if (totalConnections<maxConnections)
            {
                // check this to make sure that there are still
                // pooled connections that we should be waiting for
                // For ex: if all the connections in the pool have
                // expired then we will be waiting for a new connection
                // to be created
                throw new Exception("No pooled connections to get!");           
            }
            else
            {
                wait();
            }
        }
*/
    }
    /**
     * Helper method which determines if a connection has expired.
     *
     * @param connection The connection to test.
     * @return true if the connection is expired, false otherwise.
     */
    private boolean isExpired( DBConnection connection ) throws Exception
    {
        boolean expired = false;
        long createTime = connection.getTimestamp();
        long currentTime = System.currentTimeMillis();

        if ( (currentTime - createTime) > expiryTime )
        {
            expired = true;
        }

        return expired;
    }
    /**
     * Helper method which determines if a connection is still valid.
     *
     * @param connection The connection to test.
     * @return true if the connection is valid, false otherwise.
     */
    private boolean isValid( DBConnection connection ) throws Exception
    {
        boolean valid = false;

        if ( connection.getConnection() != null && 
            !connection.getConnection().isClosed() && 
            !isExpired(connection) )
        {
            valid = true;
        }

        return valid;
    }
    /**
     * Returns a connection to the pool.
     * This method must be called by the requestor to return
     * the connection to the pool.
     *
     * @param A connection to the database.
     */
    protected synchronized void releaseConnection(DBConnection connection) throws Exception
    {
        if ( isValid(connection) )
        {
            pool.push(connection);
            notify();
        }
        else
        {
            totalConnections--;
        }
/*
        pool.push(connection);
        notify();
*/
    }
}