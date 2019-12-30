package radian.tcmis.server7.share.dbObjs;

import java.sql.*;
 /** This is the interface implemented by DatabaseModel. See method
  *  definitions on the DatabaseModel class doc
  */
 public interface  TcmISDBModelInt
{
  public void close();
  public ResultSet doQuery(String query) throws SQLException;
}


























