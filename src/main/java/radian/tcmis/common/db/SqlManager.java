package radian.tcmis.common.db;

import java.sql.*;
import java.util.Date;
import radian.tcmis.common.exceptions.*;
import radian.tcmis.common.util.*;

/******************************************************************************
 * Does basic database functions
 *****************************************************************************/
public class SqlManager
    implements Globals {

//Log log = LogFactory.getLog(this.getClass());

  public SqlManager() {
    super();
  } //end constructor

      /******************************************************************************
   * Checks whether submitted query returns any rows from the database.<BR>
   *
   * @param connection  the <code>Connection</code> to use to communicate with
   *         the database
   * @param checkQuery  Sql query to be executed.
   * @return a boolean which is true if the query returns any rows and false
   *         if it doesn't
   * @throws BaseException If an <code>SQLException</code> is thrown
       ****************************************************************************/
  public boolean check(Connection connection, String checkQuery) throws
      BaseException {
    Statement statement = null;
    ResultSet resultset = null;
    boolean test = false;
    try {
      statement = connection.createStatement();
      synchronized (statement) {
        resultset = statement.executeQuery(checkQuery);
      }
      if (resultset.next()) {
        test = true;
      }
    }
    catch (SQLException e) {
      System.out.println("SQL ERROR:" + e.getMessage());
      throw new DbCheckException(Globals.DB_CHECK_EXCEPTION);
    }
    finally {
      closeStatement(statement);
    }
    return test;
  } //end of check

  /****************************************************************************
   * Constructs a <code>DataSet</code> holding the results of the submitted
   * query.  This version creates a new empty <code>DataSet</code> to hold the
   * results in, then calls <code>selectToLookup( connection, selectQuery,
   * lookup )</code>
   *
   * @param Connection the <code>Connection</code> to use to connect to the
   *          database
   * @param selectQuery the SQL query to run
   * @throws BaseException if there are problems communicating with the
   *          database
       ****************************************************************************/
  public DataSet select(Connection connection, String selectQuery) throws
      BaseException {
    return this.select(connection, selectQuery, new DataSet());
  }

  /****************************************************************************
   * Constructs a <code>DataSet</code> holding the results of the submitted
   * query.  This version creates a new empty <code>DataSet</code> with a
   * specified default sort column to hold the results in, then calls
   * <code>selectToLookup( connection, selectQuery,
   * lookup )</code>
   *
   * @param Connection the <code>Connection</code> to use to connect to the
   *          database
   * @param selectQuery the SQL query to run
   * @param defaultSortColumn the column that the <code>DataSet.sortedIterator
   *          </code> method should sort on if a sort column isn't sepecified
   * @throws BaseException if there are problems communicating with the
   *          database
       ****************************************************************************/
  public DataSet select(Connection connection,
                        String selectQuery,
                        String defaultSortColumn) throws BaseException {
    return this.select(connection,
                       selectQuery,
                       new DataSet(defaultSortColumn));
  }

  /****************************************************************************
   * Constructs a <code>DataSet</code> holding the results of the submitted
   * query.
   *
   * @param Connection the <code>Connection</code> to use to connect to the
   *          database
   * @param selectQuery the SQL query to run
   * @param dataSet the <code>DataSet</code> to put the query results in
   * @throws BaseException if there are problems communicating with the
   *          database
       ****************************************************************************/
  public DataSet select(Connection connection,
                        String selectQuery,
                        DataSet dataSet) throws BaseException {
    Statement statement = null;
    ResultSet resultSet = null;
    try {
//      if (log.isDebugEnabled()) {
//        log.debug(selectQuery);
//      }

      statement = connection.createStatement();
      synchronized (statement) {
        resultSet = statement.executeQuery(selectQuery);
      }
      this.populateDataSet(dataSet, resultSet);
    }
    catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR:" + e.getMessage());
      DbSelectException ex = new DbSelectException(Globals.DB_SELECT_EXCEPTION);
      ex.setRootCause(e);
      throw ex;
    }
    finally {
      closeStatement(statement);
    }
    return dataSet;
  } // end select()

  /****************************************************************************
   * Takes the data out of a <code>ResultSet</code>, places each row in a
   * <code>DataSetRow</code> and loads them into a <code>DataSet</code>.
   * Helper method for the <code>select</code> method.
   *
   * @param lookup the <code>DataSet</code> to place the data in
   * @param resultSet the <code>ResultSet</code> to get the data from
       ****************************************************************************/
  private void populateDataSet(DataSet dataSet, ResultSet resultSet) throws
      SQLException {
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    int columnCount = resultSetMetaData.getColumnCount();
    for (int i = 1; i <= columnCount; i++) {
      dataSet.addColumnName(resultSetMetaData.getColumnName(i));
    }
    while (resultSet.next()) {
      DataSetRow dataSetRow = new DataSetRow();
      for (int i = 1; i <= columnCount; i++) {
        dataSetRow.add(dataSet.getColumnName(i),
                       this.getResultSetValue(resultSet, i,
                                              resultSetMetaData));
      }
      dataSet.addDataSetRow(dataSetRow);
    }
  }

      /******************************************************************************
   * Gets value asked for from <code>ResultSet</code>, gets it as a particular
   * class based on its type in the datbase table, and returns it cast upwards
   * as an Object so that it can be stored in either a Vector, a LookupData, or
   * whatever structure we want to hold it<BR>
   *
   * @param resultSet the <code>ResultSet</code> to pull values from
   * @parm columnNumber the number of the column to pull from the <code>
   *         ResultSet
   * @param resultSetMetaData  the <code>ResultSetMetaData</code> object
   *         associated with resultSet
   * @return an object of a particular class (String, long, etc.) cast as an
   *         Object
   * @throws SQLException If the getXXX method of the <code>ResultSet</code>
   *         throws a SQLException
       *****************************************************************************/
  private Object getResultSetValue(ResultSet resultSet,
                                   int columnNumber,
                                   ResultSetMetaData resultSetMetaData) throws
      SQLException {
    Object returnObject;
    switch (resultSetMetaData.getColumnType(columnNumber)) {
      case Types.CHAR:
        returnObject = resultSet.getString(columnNumber);
        break;
      case Types.VARCHAR:
        returnObject = resultSet.getString(columnNumber);
        break;
      case Types.LONGVARCHAR:
        returnObject = resultSet.getAsciiStream(columnNumber);
        break;
      case Types.INTEGER:
        returnObject = new Long(resultSet.getLong(columnNumber));
        break;
      case Types.NUMERIC:
        returnObject = (java.math.BigDecimal)
            resultSet.getBigDecimal(columnNumber);
        if (returnObject != null) {
          //ran into an issue querying views, added this as fix
          //add decimal point if default doesn't have one
          if (resultSetMetaData.getScale(columnNumber) < 1) {
            returnObject = resultSet.getBigDecimal(columnNumber).
                setScale(
                this.getScale(returnObject.toString()));
          }
          else {
            returnObject = resultSet.getBigDecimal(columnNumber).
                setScale(
                resultSetMetaData.getScale(columnNumber));
          }
        }
        break;
      case Types.FLOAT:
        returnObject =
            new Float(resultSet.getFloat(columnNumber));
        break;
      case Types.DOUBLE:
        returnObject =
            new Double(resultSet.getDouble(columnNumber));
        break;
      case Types.DATE:
        returnObject = resultSet.getTimestamp(columnNumber);
        break;
      case Types.TIMESTAMP:
        returnObject = resultSet.getTimestamp(columnNumber);
        break;
      case Types.CLOB:
        returnObject = resultSet.getClob(columnNumber);
        break;
      default:
        returnObject = resultSet.getString(columnNumber);
        ;
    }
    return returnObject;
  } // end of getResultSetValue()

      /******************************************************************************
   * Substitute method for <code>resultSetMetaData.getScale</code> which for
   * some reason doesn't return the right value on some views.
   *
   * @param s the string to look for a . character
   * @return an int representing the scale of the string
       *****************************************************************************/
  public int getScale(String s) {
    int scale = 0;
    if (s.indexOf(".") != -1) {
      scale = s.substring(s.indexOf(".")).length();
    }
    return scale;
  }

      /******************************************************************************
   * Executes update, insert, or delete query submitted.<BR>
   * Returns integer resulting from statement.executeUpdate(query).
   * <BR>
   * @param connection  the <code>Connection</code> to use to communicate with
   *         the database
   * @param updateQuery  SQL query to be executed.
   * @return the number of records affected by the SQL query
   * @throws BaseException if there are problems communicating with the
   *          database
       *****************************************************************************/
  public int update(Connection connection, String updateQuery) throws
      BaseException {
    Statement statement = null;
    int count = 0;
    try {
//      if (log.isDebugEnabled()) {
//        log.info(updateQuery);
//      }

      statement = connection.createStatement();
      synchronized (statement) {
        count = statement.executeUpdate(updateQuery);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR:" + e.getMessage());
      throw new DbUpdateException(Globals.DB_UPDATE_EXCEPTION);
    }
    finally {
      closeStatement(statement);
    }
    return count;
  } //end of update

  /****************************************************************************
   * Executes the update, delete or insert query submitted, if the date
   * submitted is the same as the MODIFY_DATE in the table row specified
   *
   * @param connection  the <code>Connection</code> to use to communicate with
   *         the database
   * @param updateQuery  SQL query to be executed.
   * @param tableName the name of the database table whose MODIFY_DATE should
   *          be checked
   * @param primaryKeyName the name of the key to use to identify the row
   *          in the table to check
   * @param primaryKeyValue the value of the key to use to identify the row
   *          in the table to check
   * @param dateToCheck the date to compare the table's MODIFY_DATE to
   * @return the number of rows affected by the SQL query
   * @throws BaseException if there are problems communicating with the
   *          database or if the MODIFY_DATE on the record to be updated has
   *          been changed
       ****************************************************************************/
  public int update(Connection connection,
                    String updateQuery,
                    String tableName,
                    String primaryKeyName,
                    int primaryKeyValue,
                    Date dateToCheck) throws BaseException {
    return this.update(connection,
                       updateQuery,
                       tableName,
                       primaryKeyName,
                       "" + primaryKeyValue,
                       dateToCheck);
  }

  /****************************************************************************
   * Executes the update, delete or insert query submitted, if the date
   * submitted is the same as the MODIFY_DATE in the table row specified
   *
   * @param connection  the <code>Connection</code> to use to communicate with
   *         the database
   * @param updateQuery  SQL query to be executed.
   * @param tableName the name of the database table whose MODIFY_DATE should
   *          be checked
   * @param primaryKeyName the name of the key to use to identify the row
   *          in the table to check
   * @param primaryKeyValue the value of the key to use to identify the row
   *          in the table to check
   * @param dateToCheck the date to compare the table's MODIFY_DATE to
   * @return the number of rows affected by the SQL query
   * @throws BaseException if there are problems communicating with the
   *          database or if the MODIFY_DATE on the record to be updated has
   *          been changed
       ****************************************************************************/
  public int update(Connection connection,
                    String updateQuery,
                    String tableName,
                    String primaryKeyName,
                    String primaryKeyValue,
                    Date dateToCheck) throws BaseException {
    boolean state = true;
    Statement statement = null;
    int count = 0;
    try {
      //store commit state to restore after we're done
      state = connection.getAutoCommit();
      synchronized (connection) {
        // set the connection's autocommit to false, so that the SQL
        // queries/updates can be part of the same transaction
        connection.setAutoCommit(false);
        // check the MODIFY_DATE on the record to be modified.  If it
        // has changed, rollback the connection (to take back the
        // SELECT FOR UPDATE run by isRowModified), and throw an
        // exception
        if (isRowModified(connection,
                          tableName,
                          primaryKeyName,
                          primaryKeyValue,
                          dateToCheck)) {
          connection.rollback();
          // change the auto commit setting back to the same state it
          // was when we got it
          connection.setAutoCommit(state);
          throw new DbUpdateException(Globals.DB_MODIFY_EXCEPTION);
        }
        // if row hasn't been modified, go ahead and execute the
        // update statement and commit the transaction
        else {
//          if (log.isDebugEnabled()) {
//            log.info(updateQuery);
//          }

          statement = connection.createStatement();
          count = statement.executeUpdate(updateQuery);
          connection.commit();
          connection.setAutoCommit(state);
        }
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR:" + e.getMessage());
      try {
        connection.rollback();
        connection.setAutoCommit(state);
      }
      catch (SQLException sqe) { /* do nothing */}
      DbUpdateException ex = new DbUpdateException(Globals.DB_MODIFY_EXCEPTION);
      ex.setRootCause(e);
      throw ex;
    }
    finally {
      closeStatement(statement);
    }
    return count;
  } //end of update

  /****************************************************************************
   * Executes the update, or delete query submitted, if the date
   * submitted is the same as the MODIFY_DATE in the table row/s specified.<p>
   *
   * This version is intended to be used with SQL queries that affect more than
   * one row.
   *
   * @param connection  the <code>Connection</code> to use to communicate with
   *         the database
   * @param updateQuery  SQL query to be executed.
   * @param tableName the name of the database table whose MODIFY_DATE should
   *          be checked
   * @param whereClause  the where clause from <code>updateQuery</code>
   * @param dateToCheck the date to compare the table's MODIFY_DATE to
   * @return the number of rows affected by the SQL query
   * @throws BaseException if there are problems communicating with the
   *          database or if the MODIFY_DATE on the record to be updated has
   *          been changed
       ****************************************************************************/
  public int bulkUpdate(Connection connection,
                        String updateQuery,
                        String tableName,
                        String whereClause,
                        Date dateToCheck) throws BaseException {
    Statement statement = null;
    ResultSet resultSet = null;
    SqlManager sqlManager = new SqlManager();
    int count = 0;
    boolean state = true;
    try {
      state = connection.getAutoCommit();
      synchronized (connection) {
        // set the connection's autocommit to false, so that the SQL
        // queries/updates can be part of the same transaction
        connection.setAutoCommit(false);
        // check the MODIFY_DATE on the records to be modified.  If it
        // has changed, rollback the connection (to take back the
        // SELECT FOR UPDATE used by isRowModified), and throw an
        // exception
        if (isRowModified(connection,
                          tableName,
                          whereClause,
                          dateToCheck)) {
          connection.rollback();
          // change the autocommit setting back to what it was when we
          // got it
          connection.setAutoCommit(state);
          throw new DbUpdateException("Row has been modified");
        }
        // if row hasn't been modified, go ahead and execute the
        // SQL and commit the transaction
        else {
          statement = connection.createStatement();
          count = statement.executeUpdate(updateQuery);
          connection.commit();
          connection.setAutoCommit(state);
        }
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR: " + e.getMessage());
      try {
        connection.rollback();
        connection.setAutoCommit(state);
      }
      catch (SQLException sqe) { /* do nothing */}
      DbUpdateException ex = new DbUpdateException(
          "SQLException thrown while updating");
      ex.setRootCause(e);
      throw ex;
    }
    finally {
      sqlManager.closeStatement(statement);
    }
    return count;
  }

  /****************************************************************************
   * Checks to see if a row in a database table has been modified since a
   * certain date/time (usually the MODIFY_DATE of that table gathered from
   * a previous select statement).<p>
   *
   * Note: Be aware that this method uses "FOR UPDATE NOWAIT" in its select
   * statement.  This means several things:
   * <ol>
   * <li>If you call this method yourself, it must be within a transaction
   * (you must set auto commit to false using <code>Connection.setAutoCommit(
   * false ) -- don't forget to set it back to <code>true</code> when you are
   * done with the transaction).  If you do not do this, it will throw a
   * <code>dbSelectException</code>.
   * <li>The record will be locked until you either commit or rollback the
   * transaction ( using <code>Connection.commit()</code> or <code>
   * Connection.rollback()</code>
   * </ol>
   *
   * @param connection the <code>Connection</code> to use to communicate with
   *          the database
   * @param tableName the name of the database table to query against
   * @param primaryKeyName the name of the key to use to identify the row
   *          in the table to check
   * @param primaryKeyValue the value of the key to use to identify the row
   *          in the table to check
   * @param dateToCheck the date to compare the table's MODIFY_DATE to
   * @return a <code>boolean</code> indicating whether the MODIFY_DATE has
   *          changed (true - the date has changed, suggesting that the
   *          row has been modified)
   * @throws BaseException if there are problems communicating with the
   *          database
       ****************************************************************************/
  public boolean isRowModified(Connection connection,
                               String tableName,
                               String primaryKeyName,
                               String primaryKeyValue,
                               Date dateToCheck) throws BaseException {
    Statement statement = null;
    ResultSet resultSet = null;
    boolean isModified = false;
    try {
      String sqlToExecute = "select MODIFY_DATE from " +
          tableName + " where " + primaryKeyName +
          " = " +
          primaryKeyValue + " for update nowait";
      statement = connection.createStatement();
      synchronized (statement) {
        resultSet = statement.executeQuery(sqlToExecute);
      }
      if (resultSet.next()) {
        Date tableDate = resultSet.getTimestamp("MODIFY_DATE");
        // if both dates are null, let's regard them as equal, so
        // the record hasn't been modified
        if (tableDate == null && dateToCheck == null) {
          isModified = false;
        }
        // if only one is null (while the "or" below would allow
        // them to both be null, we already would have caught that
        // above), then they aren't equal so the record has been
        // modified
        else if (tableDate == null || dateToCheck == null) {
          isModified = true;
        }
        // if the dates are equal, the record hasn't been modified
        else if (tableDate.equals(dateToCheck)) {
          isModified = false;
        }
        // if the dates aren't equal, the record has been modified
        else {
          isModified = true;
        }
      }
      // if we didn't find the record, it has been somehow modified
      // (either deleted or the primary key changed or we specified
      // a different field to search on than the primary key and that
      // field has been changed).  Any one of these is a BIG problem,
      // but it's not up to this routine to raise the alarm by
      // throwing an Exception
      else {
        isModified = true;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR:" + e.getMessage());
      throw new DbSelectException(Globals.DB_SELECT_EXCEPTION);
    }
    finally {
      closeStatement(statement);
    }
    return isModified;
  }

  /****************************************************************************
   * Checks to see if any row in a database table matching certain criteria
   * has been modified since a certain date/time (usually an Oracle sysdate
   * obtained when the original query was run).<p>
   *
   * Meant for use with multi-row updates.<p>
   *
   * Note: Be aware that this method uses "FOR UPDATE NOWAIT" in its select
   * statement.  This means several things:
   * <ol>
   * <li>If you call this method yourself, it must be within a transaction
   * (you must set auto commit to false using <code>Connection.setAutoCommit(
   * false ) -- don't forget to set it back to <code>true</code> when you are
   * done with the transaction).  If you do not do this, it will throw a
   * <code>dbSelectException</code>.
   * <li>The record will be locked until you either commit or rollback the
   * transaction ( using <code>Connection.commit()</code> or <code>
   * Connection.rollback()</code>
   * </ol>
   *
   * @param connection the <code>Connection</code> to use to communicate with
   *          the database
   * @param tableName the name of the database table to query against
   * @param an SQL where clause defining which rows to check
   * @param dateToCheck the date to compare the table's MODIFY_DATE to
   * @return a <code>boolean</code> indicating whether the MODIFY_DATE has
   *          changed (true - the date has changed, suggesting that the
   *          row has been modified)
   * @throws BaseException if there are problems communicating with the
   *          database
       ****************************************************************************/
  public boolean isRowModified(Connection connection,
                               String tableName,
                               String whereClause,
                               Date dateToCheck) throws BaseException {
    Statement statement = null;
    ResultSet resultSet = null;
    SqlManager sqlManager = new SqlManager();
    int rowCount = 0;
    boolean isModified = false;
    try {
      String sqlToExecute = "select modify_date from " + tableName + " " +
          whereClause +
          " order by modify_date desc for update nowait";
      statement = connection.createStatement();
      synchronized (statement) {
        resultSet = statement.executeQuery(sqlToExecute);
      }
      if (resultSet.next()) {
        Date tableDate = resultSet.getTimestamp("MODIFY_DATE");
        // if the table date is null, none of the records have been
        // modified
        if (tableDate == null) {
          isModified = false;
        }
        // if the date to check is null and the table date isn't,
        // at least one record has been modified
        else if (dateToCheck == null) {
          isModified = true;
        }
        // if the table date is after the date to check, at least one
        // record has been modified
        else if (tableDate.after(dateToCheck)) {
          isModified = true;
        }
        // if the two dates are equal or the table date is before
        // the date to check, none of the records have been modified
        else {
          isModified = false;
        }
      }
      // if we didn't find any records, the have been somehow modified
      // (either deleted or the columns we based our criteria on have
      // been changed).  Any one of these is a BIG problem,
      // but it's not up to this routine to raise the alarm by
      // throwing an Exception
      else {
        isModified = true;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR:" + e.getMessage());
      throw new DbSelectException(Globals.DB_SELECT_EXCEPTION);
    }
    finally {
      closeStatement(statement);
    }
    return isModified;
  }

      /*****************************************************************************
   * Tries to close statment and throws an exception if it can't
   *
   * @param statement  the <code>Statement</code> to be closed
   * @throws DbStatementException  If trying to close the statement causes a
   *          <code>SQLException</code>
       ****************************************************************************/
  public void closeStatement(Statement statement) throws DbStatementException {
    try {
      if (statement != null) {
        statement.close();
      }
    }
    catch (SQLException e) {
      throw new DbStatementException(Globals.DB_STATEMENT_EXCEPTION);
    }
  }

      /*****************************************************************************
   * Returns the next number from the specified sequence.
   *
   * @param connection the <code>Connection</code> to use to communicate with
   *          the database
   * @param sequenceName the name of the sequence to query against
   * @throws DbStatementException  If trying to close the statement causes a
   *          <code>SQLException</code>
       ****************************************************************************/
  public int getOracleSequence(Connection connection, String sequenceName) throws
      BaseException {
    Statement statement = null;
    ResultSet resultset = null;
    int sequenceNumber = 0;
    String query = "select " + sequenceName + ".nextval from DUAL";
    try {
      statement = connection.createStatement();
      synchronized (statement) {
        resultset = statement.executeQuery(query);
      }
      while (resultset.next()) {
        sequenceNumber = resultset.getInt("NEXTVAL");
      }
      resultset.close();
    }
    catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR:" + e.getMessage());
      throw new DbSelectException(Globals.DB_SELECT_EXCEPTION);
    }
    finally {
      closeStatement(statement);
    }
    return sequenceNumber;
  }

      /*****************************************************************************
   * Returns the current Oracle sysdate
   *
   * @param connection the <code>Connection</code> to use to communicate with
   *          the database
   * @return a <code>Date</code> containing the current Oracle sysdate
   * @throws BaseException If there are problems connecting to the database
       ****************************************************************************/
  public Date getOracleSysdate(Connection connection) throws BaseException {
    Date oracleSysdate = null;
    try {
      String sqlQuery = "select sysdate from dual";
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery(sqlQuery);
      if (rs.next()) {
        oracleSysdate = rs.getTimestamp("SYSDATE");
      }
      else {
        throw new DbSelectException("No sysdate returned");
      }
    }
    catch (SQLException e) {
      DbSelectException dbe = new DbSelectException("Error while " +
          "trying to retrieve Oracle sysdate");
      dbe.setRootCause(e);
      throw dbe;
    }
    return oracleSysdate;
  }
} // end of class
