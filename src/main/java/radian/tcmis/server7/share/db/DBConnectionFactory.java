package radian.tcmis.server7.share.db;

/**
Dash, a Servlet Framework for building Dynamic Websites
Copyright (C) 1999  Jon S. Stevens

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Library General Public
License as published by the Free Software Foundation; either
version 2 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Library General Public License for more details.

You should have received a copy of the GNU Library General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
*/
import java.sql.*;
import java.util.Stack;
/**
 * This class is the single point of creation for database connections.
 * A simple connection pooling scheme is also implemented.
 * <p>
 * To retrieve a Connection object from this singleton do the following:<br>
 * DBConnection db = DBConnectionFactory.getInstance().getConnection();<br>
 * Connection con = db.getConnection();
 * <p>
 * Make sure to call releaseConnection() when you are done.
 *
 * @author Frank Y. Kim <a href="mailto:frank.kim@clearink.com">frank.kim@clearink.com</a>
 * @author Jon S. Stevens <a href="mailto:jon@clearink.com">jon@clearink.com</a>
 */
public class DBConnectionFactory {

    /** The single instance of this class. */
    private static DBConnectionFactory instance = null;

    /* Trong 7-25 determine which client to use */
    private static String currentClient = null;
    private static DBConnectionFactory rayInstance = null;
    private static DBConnectionFactory drsInstance = null;

    /** Pool containing database connections. */
    private Stack pool = null;

    /** The current number of database connections that have been created. */
    private int totalConnections = 0;

    /** The maximum number of database connections that can be created. */
    private int maxConnections = 10;

    /** The resource file that contains database properties. */
    private static ResourceLibrary resource = null;

    /** The amount of time in milliseconds that a connection will be pooled. */
    private static long expiryTime = 3600000; // 1 hour

    private static String dbUrl = "owldev";
    /**
     * This ctor is private to force clients to use getInstance()
     * to access this class.
     */
    /*private DBConnectionFactory()
    {
        pool = new Stack();
        //Trong 7-25
        if (client.equalsIgnoreCase("Ray")) {
          resource = new ResourceLibrary("RayTcmISDatabase");
        } else if (client.equalsIgnoreCase("DRS")) {
          resource = new ResourceLibrary("DRSTcmISDatabase");
        } else {
          resource = new ResourceLibrary("RayTcmISDatabase");
        }
        System.out.println("\n\n------------ which database am i hitting: "+resource.getString("database.userid")+"\n\n");
        try {
            maxConnections = resource.getInt("database.maxConnections");
            expiryTime = resource.getLong("database.expiryTime");
        } catch (Exception e) {
            // just use the default values for maxConnections and expiryTime
        }
    } */
    private DBConnectionFactory(String client)
    {
        pool = new Stack();
        //Trong 7-25
        if (client.equalsIgnoreCase("Ray")) {
          resource = new ResourceLibrary("RayTcmISDatabase");
        } else if (client.equalsIgnoreCase("DRS")) {
          resource = new ResourceLibrary("DRSTcmISDatabase");
        } else {
          resource = new ResourceLibrary("RayTcmISDatabase");
        }
        //System.out.println("\n\n------------ which database am i hitting: "+resource.getString("database.userid")+"\n\n");
        try {
            maxConnections = resource.getInt("database.maxConnections");
            expiryTime = resource.getLong("database.expiryTime");
        } catch (Exception e) {
            // just use the default values for maxConnections and expiryTime
        }
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
     * Retrieves a connection.
     *
     * @return A database connection.
     * @exception Exception.
     */
    public synchronized DBConnection getConnection() throws Exception
    {
        DBConnection connection = null;
        //System.out.println("\n\n&&&&&&&&&&&&&& in getconnection: "+totalConnections+"  ---> "+pool.size()+"\n\n");
        if ( pool.empty() && totalConnections<maxConnections )
        {
            connection = getNewConnection(true);
        }
        else
        {
            connection = getPooledConnection();
        }
        return connection;
    }
    /**
     * The method through which this class is accessed.
     *
     * @return The single instance of this class.
     */
    /*public static DBConnectionFactory getInstance()             //Orignial method 7-26
    {
        if (instance == null)
        {
            synchronized (DBConnectionFactory.class)
            {
                if (instance == null)
                {
                    client = cl;
                    instance = new DBConnectionFactory();
                }
            }
        }
        return instance;
    } */
    public static DBConnectionFactory getInstance(String cl)
    {
      currentClient = cl;
      synchronized (DBConnectionFactory.class)
      {
        //System.out.println("\n\n!!!!!!!!!!! in getinstance current client: " +currentClient);
        if (cl.equalsIgnoreCase("Ray")) {
          if (rayInstance == null) {
            //System.out.println("\n\n\n~~~~~~~~~~~~ in rayinstance = null\n\n");
            rayInstance = new DBConnectionFactory(cl);
            instance = rayInstance;
          }else {
            //System.out.println("\n\n\n~~~~~~~~~~~~ in rayinstance != null\n\n");
            instance = rayInstance;
          }
        }else if (cl.equalsIgnoreCase("DRS")) {
          if (drsInstance == null) {
            //System.out.println("\n\n\n~~~~~~~~~~~~ in drsinstance = null\n\n");
            drsInstance = new DBConnectionFactory(cl);
            instance = drsInstance;
          }else {
            //System.out.println("\n\n\n~~~~~~~~~~~~ in drsinstance != null\n\n");
            instance = drsInstance;
          }
        }else {
          //System.out.println("\n\n\n~~~~~~~~~~~~ should not be here\n\n"); //There will other clients
          rayInstance = new DBConnectionFactory(cl);;  //so this is just a space holder/reminder
          instance = rayInstance;
        }                                              //that I need to add new client here
      }
      return instance;
    } /*
    public static DBConnectionFactory getInstance(String cl)   //trong 7-25
    {
      synchronized (DBConnectionFactory.class)
      {
        client = cl;
        instance = new DBConnectionFactory();
      }
      return instance;
    }  */



    /**
     * Returns a connection to the database depending on the type.
     *
     * @return A database connection.
     */
    private  DBConnection getNewConnection(boolean addToPool) throws Exception
    {
        DB db;
        String type = resource.getString("database.type");
        /*
        if ( type.equalsIgnoreCase( DB.DB_NONE ))
            db = new DBNone();
        else if ( type.equalsIgnoreCase( DB.DB_MM ))
            db = new DBMM();
        else if ( type.equalsIgnoreCase( DB.DB_WEBLOGIC ))
            db = new DBWeblogic();
        else if ( type.equalsIgnoreCase( DB.DB_ORACLE ))
            db = new DBOracle();
        else
            throw new InstantiationException("Database Type "+ type +" not implemented");
        */

        db = new DBOracle();

        db.init( resource.getString("database.url"),
                  resource.getString("database.userid"),
                  resource.getString("database.password") );

        if (addToPool)  totalConnections++;
        return new DBConnection( db.getConnection() );
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
    }
    /**
     * Helper method which determines if a connection is still valid.
     *
     * @param connection The connection to test.
     * @return true if the connection is valid, false otherwise.
     */
    private boolean isConnectionValid( DBConnection connection ) throws Exception
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
     * Returns a connection to the pool.
     * This method must be called by the requestor to return
     * the connection to the pool.
     *
     * @param A connection to the database.
     */
    /*public synchronized void releaseConnection(DBConnection connection) throws Exception
    {
        if ( isConnectionValid(connection) )
        {
            System.out.println("Valid Connection");
            pool.push(connection);
            notify();
        }
        else
        {
            System.out.println("NOT Valid Connection");
            totalConnections--;
            try {
              if (connection.getConnection() !=null && !connection.getConnection().isClosed() ) connection.getConnection().close();
            } catch (Exception e){
              e.printStackTrace();
            }; // nothing to do here.
        }
    }*/  //trong 8-1
    public synchronized void releaseConnection(DBConnection connection) throws Exception
    {
        if ( isConnectionValid(connection) )
        {
            //System.out.println("Valid Connection");
            pool.push(connection);
            notify();
        }
        else
        {
            //System.out.println("NOT Valid Connection");
            //System.out.println("\n\n!!!!!!!!!!! in release connection current client: " +currentClient);
            totalConnections--;
            try {
              if (connection.getConnection() !=null && !connection.getConnection().isClosed() ) connection.getConnection().close();
              if (currentClient.equalsIgnoreCase("Ray")) {
                rayInstance = null;
              }else if (currentClient.equalsIgnoreCase("DRS")){
                drsInstance = null;
              }else {                                 //space holder/reminder that there will be more clients and is where
                rayInstance = null;                   //i need to add them
              }
            } catch (Exception e){
              e.printStackTrace();
            }; // nothing to do here.
        }
    }


    public String getURl(){
        return dbUrl;
    }

    public synchronized int getTotalConnections(){
        return totalConnections;
    }

    public synchronized Stack getPool(){
        return pool;
    }

    public Connection getFreshConnection() throws Exception {
        DBConnection DB = this.getNewConnection(false);
        return DB.getConnection();
    }


}