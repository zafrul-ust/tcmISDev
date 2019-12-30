package radian.tcmis.server7.client.kemfast.dbObjs;

import radian.tcmis.server7.share.db.*;
import radian.tcmis.server7.share.dbObjs.ConnectionPool;

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

/**
 * This class is the single point of creation for database connections.
 * A simple connection pooling scheme is also implemented.
 * <p>
 * To retrieve a Connection object from this singleton do the following:<br>
 * DBConnection db = DBConnectionFactory.getInstance().getConnection();<br>
 * Connection con = db.getConnection();
 * <p>
 * Make sure to kemfastl releaseConnection() when you are done.
 *
 * @author Frank Y. Kim <a href="mailto:frank.kim@clearink.com">
 * frank.kim@clearink.com</a>
 * @author Jon S. Stevens <a href="mailto:jon@clearink.com">jon@clearink.com</a>
 * #############################################################################
 * ################ NOTE!!!!!!!!!!!!!!! ########################################
 * This class has been drastikemfastly rewritten and above documentation may not be
 * current.
 * Currently it's using the oracle jdbc connection pool implemented in
 * com.tcmis.sql.ConnectionPool. Remember to return connections to the
 * connection pool once you are done using them.
 */
public class KemfastDBConnectionFactory {

  private static ConnectionPool cp = null;
  private static ConnectionPool cpTest = null;

  /** The resource file that contains database properties. */
  //private static ResourceLibrary resource = null;
  //private static ResourceLibrary resourceTest = null;

  /******************************************************************+**********
   * This constructor is private to force clients to use getInstance()
   * to access this class.
   ****************************************************************************/
  public KemfastDBConnectionFactory() {
    //connection pool for production
    if (cp == null) {
      //resource = new ResourceLibrary("KemfastTcmISDatabase");
      try {
        cp = new ConnectionPool();
      }
      catch (Exception ioe) {
        System.out.println("ERROR configuring prod connection pool");
      }
    }
    //connection pool for development
    if (cpTest == null) {
      //resourceTest = new ResourceLibrary("KemfastTestTcmISDatabase");
      try {
        cpTest = new ConnectionPool();
      }
      catch (Exception ioe) {
        System.out.println("ERROR configuring dev connection pool");
      }
    }
  }

  /*****************************************************************************
   * This constructor is private to force clients to use getInstance()
   * to access this class.
   * @deprecated Use the empty constructor instead
   ****************************************************************************/
  public KemfastDBConnectionFactory(String logonVersion) {
    this();
  }

  public DBConnection getConnectionWithLocale(String logonVersion,String locale) throws Exception {
    //check if it's a prod connection requested
    if ("2".equalsIgnoreCase(logonVersion) ||
        "3".equalsIgnoreCase(logonVersion)) {
      return new DBConnection(this.getFreshConnectionWithLocale(locale));
    }else {
      return new DBConnection(this.getFreshTestConnectionWithLocale(locale));
    }
  }

  public Connection getFreshConnectionWithLocale(String logonVersion,String locale) throws Exception {
    //check if it's a prod connection requested
    if ("2".equalsIgnoreCase(logonVersion) ||
        "3".equalsIgnoreCase(logonVersion)) {
      return this.getFreshConnectionWithLocale(locale);
    }else {
      return this.getFreshTestConnectionWithLocale(locale);
    }
  }	

  public Connection getFreshConnectionWithLocale(String locale) throws Exception {
    return cp.getConnection("KEMFAST",locale);
  }

  public Connection getFreshTestConnectionWithLocale(String locale) throws Exception {
    return cpTest.getConnection("KEMFAST_TEST",locale);
  }

  /*****************************************************************************
   * The method through which this class is accessed.
   *
   * @return An instance of this class.
   ****************************************************************************/
  public KemfastDBConnectionFactory getInstance() {
    return new KemfastDBConnectionFactory();
  }

  /*****************************************************************************
   * The method through which this class is accessed.
   *
   * @return An instance of this class.
   * @deprecated Use getInstance() instead.
   ****************************************************************************/
  public KemfastDBConnectionFactory getInstance(String logonVersion) {
    return this.getInstance();
  }

  /*****************************************************************************
   * Retrieves a Connection to the production db.
   *
   * @return A database connection.
   * @exception Exception.
   ****************************************************************************/
  public Connection getFreshConnection() throws Exception {
    return cp.getConnection("KEMFAST");
  }

  /*****************************************************************************
   * display Connection data.
   *
   ****************************************************************************/
  public void printConnectionPoolData() {
    cp.printConnectionPoolData();
  }


  /*****************************************************************************
   * Retrieves a Connection to the development db.
   *
   * @return A database connection.
   * @exception Exception.
   ****************************************************************************/
  public Connection getFreshTestConnection() throws Exception {
    return cpTest.getConnection("KEMFAST_TEST");
  }

  /*****************************************************************************
   * Retrieves a Connection based on requested logon version.
   *
   * @param  A String holding the logon version
   * @return A database connection.
   * @exception Exception.
   *****************************************************************************/
  public Connection getFreshConnection(String logonVersion) throws Exception {
    //check if it's a prod connection requested
    if ("2".equalsIgnoreCase(logonVersion) ||
        "3".equalsIgnoreCase(logonVersion)) {
      return this.getFreshConnection();
    }
    //if it's not we'll return a dev connection
    else {
      return this.getFreshTestConnection();
    }
  }


  /*****************************************************************************
   * Retrieves a DBConnection to the production db.
   *
   * @return A database connection wrapped in a DBConnection.
   * @exception Exception.
   * @deprecated Use fetchConnection() instead
   *****************************************************************************/
  public DBConnection getConnection() throws Exception {
    return new DBConnection(this.getFreshConnection());
  }

  /*****************************************************************************
   * Retrieves a DBConnection to the development db.
   *
   * @return A database connection wrapped in a DBConnection.
   * @exception Exception.
   * @deprecated Use fetchConnection() instead
   *****************************************************************************/
  public DBConnection getTestConnection() throws Exception {
    return new DBConnection(this.getFreshTestConnection());
  }

  /*****************************************************************************
   * Retrieves a DBConnection based on requested logon version.
   *
   * @param  A String holding the logon version
   * @return A database connection wrapped in a DBConnection.
   * @exception Exception.
   * @deprecated Use fetchConnection() method instead
   *****************************************************************************/
  public DBConnection getConnection(String logonVersion) throws Exception {
    //check if it's a prod connection requested
    if ("2".equalsIgnoreCase(logonVersion) ||
        "3".equalsIgnoreCase(logonVersion)) {
      return this.getConnection();
    }
    //if it's not we'll return a dev connection
    else {
      return this.getTestConnection();
    }
  }

  /***************************************************************************
   * Returns a production connection to the pool.
   * This method must be kemfastled by the requestor to return
   * the connection to the pool.
   *
   * @param A Connection object
   ****************************************************************************/
  public void returnConnection(Connection connection) throws Exception {
    cp.returnConnection(connection);
  }

  /***************************************************************************
   * Returns a development connection to the pool.
   * This method must be called by the requestor to return
   * the connection to the pool.
   *
   * @param A Connection object
   ****************************************************************************/
  public void returnTestConnection(Connection connection) throws Exception {
    cpTest.returnConnection(connection);
  }

  /***************************************************************************
   * Returns a connection to the pool based on requested login version.
   * This method must be called by the requestor to return
   * the connection to the pool.
   *
   * @param A Connection object
   * @param A String holding the logon version
   ****************************************************************************/

  public void returnConnection(Connection connection,
                                String logonVersion) throws Exception {

    if ("2".equalsIgnoreCase(logonVersion) ||
        "3".equalsIgnoreCase(logonVersion)) {
      returnConnection(connection);
    }
    else {
      returnTestConnection(connection);
    }
  }


  /***************************************************************************
   * Returns a production connection to the pool.
   * This method must be called by the requestor to return
   * the connection to the pool.
   *
   * @param A DBConnection object
   * @deprecated Use fetchConnection and returnConnection instead
   ****************************************************************************/
  public void releaseConnection(DBConnection dbConnection) throws Exception {
    this.returnConnection(dbConnection.getConnection());
  }

  /***************************************************************************
   * Returns a development connection to the pool.
   * This method must be called by the requestor to return
   * the connection to the pool.
   *
   * @param A DBConnection object
   * @deprecated Use fetchConnection and returnConnection instead
   ****************************************************************************/
  public void releaseTestConnection(DBConnection dbConnection) throws Exception {
    this.returnTestConnection(dbConnection.getConnection());
  }

  /***************************************************************************
   * Returns a connection to the pool based on requested login version.
   * This method must be called by the requestor to return
   * the connection to the pool.
   *
   * @param A DBConnection object
   * @param A String holding the logon version
   * @deprecated Use fetchConnection and returnConnection instead
   ****************************************************************************/

  public void releaseConnection(DBConnection dbConnection,
                                String logonVersion) throws Exception {

    if ("2".equalsIgnoreCase(logonVersion) ||
        "3".equalsIgnoreCase(logonVersion)) {
      releaseConnection(dbConnection);
    }
    else {
      releaseTestConnection(dbConnection);
    }
  }
}
