package radian.tcmis.server7.share.dbObjs;

import java.sql.*;
import java.util.Date;

import radian.tcmis.common.exceptions.DbConnectionException;
import radian.tcmis.common.exceptions.DbReturnConnectionException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

public class ConnectionPool {

  private static String DEFAULT_LOCALE = "en_US";

  public ConnectionPool() {
  }

  public static Connection getConnection(String client) throws
      DbConnectionException {
    return getConnection(client, DEFAULT_LOCALE);
  }

  public static Connection getConnection(String client, String locale) throws
      DbConnectionException {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Class.forName("org.apache.commons.dbcp.PoolingDriver");
    }
    catch (ClassNotFoundException e) {
    }
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/" + client);
		setLocale(conn,locale);
    }
    catch (SQLException ex) {
      //System.out.println("Error getting connection for " + client);
      ex.printStackTrace();
      DbConnectionException e = new DbConnectionException(
          "Error getting connection for " + client);
      e.setRootCause(ex);
      throw e;
    }
    return conn;
  }

  public static void returnConnection(Connection connection) throws
      DbReturnConnectionException {
    try {
      connection.close();
    }
    catch (SQLException ex) {
      //System.out.println("Error closing connection.");
      ex.printStackTrace();
      DbReturnConnectionException e = new DbReturnConnectionException(
          "Error closing connection");
      e.setRootCause(ex);
      throw e;
    }
  }

  private static void setLocale(Connection connection, String locale) throws SQLException {
      CallableStatement cs = null;
      try {
			 cs = connection.prepareCall("{call p_set_session_locale_code(?)}");
          cs.setString(1, locale);
          cs.execute();
      }
      finally {
        try {
          if (cs != null) {
            cs.close();
          }
        }
        catch (SQLException e) {
            //I'll eat this error but log it
            System.out.println(new Date()+"--Error closing CallableStatement when setting locale:"+locale);
        }
      }
  }

  public void printConnectionPoolData() {

		}

}
