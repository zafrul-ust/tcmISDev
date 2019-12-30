package radian.tcmis.server7.share.dbObjs;

import java.util.*;
import java.sql.*;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.db.*;

public class Authenticator {
  //public static final int TOKENTIMEOUT = 1920000; // 32 min
  public static final int TOKENTIMEOUT = 86400000; // 24 h 
  TcmISDBModel db  = null;
  String ip = null;
  String token = null;
  String connection = null;

  public Authenticator(){

  }

  public Authenticator(TcmISDBModel db){
    this.db = db;
  }

  // public class methods

  public void setIp(String ip){
    this.ip = ip;
  }

  public String getIp(){
    return ip;
  }

  public void setToken(String t){
    this.token = t;
  }

  public String getToken(){
    return token;
  }

  public void setConnection (String c){
    this.connection  = c;
  }

  public String getConnection(){
    return connection;
  }

  public void load()  throws Exception {
    if (ip == null || ip.length() == 0) {
      return;
    }
    String query = "select token,connection from tcmis_token where ip_address = '" + ip + "'";
    DBResultSet dbrs = null;
    ResultSet rs = null;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        token = rs.getString("token");
        connection = rs.getString("connection");
        break;
      }

     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
       if (dbrs!=null) dbrs.close();
     }
    return;
  }
  
  public String addToken(String ip)  throws Exception {
     String token;
     cleanUpTable(ip);
     while(true) {
       token = generateToken();
       if (tokenIsUnique(token)) {
         break;
       }
     }

     String query = "insert into tcmis_token (ip_address, token, connection) values ( '" + ip + "','" + token + "' , 'ENCRYPTED')";
     try {
       db.doUpdate(query);
      } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
      }
     updateLastDate(token);
     return token;
  }

  public boolean checkAuth(String token, String ip)  throws Exception{
    boolean auth = false;
    if (token == null || ip == null || token.length() == 0 || ip.length() == 0) {
      return false;
    }

    String query = "select ip_address, last_update_time from tcmis_token where token = '" + token + "'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String fileIP = "";
    java.sql.Timestamp sqlDate = new java.sql.Timestamp(0);
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        fileIP = rs.getString("ip_address");
        sqlDate = rs.getTimestamp("last_update_time");
        break;
      }

     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
       if (dbrs!=null)  dbrs.close();
     }

    //HelpObjs.monitor(1,"Hit by IP: "+fileIP,getBundle());
    if (( new java.util.Date().getTime() - sqlDate.getTime() ) > TOKENTIMEOUT ) {
      return false;
    }
    if (ip.equals(fileIP)) {
      auth = true;
      updateLastDate(token);
    }
    return auth;
  }

  // private class methods
  private String generateToken() {
    Random ran = new Random();
    String rString = "";
    String token = "";
    long number = 0;
    while(true) {
      rString = rString + String.valueOf(Math.abs(ran.nextLong()));
      if (rString.length() > 32) {
        token = rString.substring(0,31);
        break;
      }
    }
    return token;
  }

  private void updateLastDate(String token)  throws Exception{
    String query = "update tcmis_token set last_update_time = sysdate where token = '" + token + "'";
    try {
      db.doUpdate(query);
     } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    }
  }

  private boolean tokenIsUnique(String token)  throws Exception{
      String query = "select token from tcmis_token where token = '" + token + "'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      int flag =0;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
            flag = 1;
            break;
         }
       } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
      } finally{
         if (dbrs!=null) dbrs.close();
       }
      return (flag == 0);
  }

  public void cleanUpTable(String ip)  throws Exception {
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String query = "delete from tcmis_token where ip_address = '" + ip + "'";
     try {
       db.doUpdate(query);

      } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     }

  }

  public String getTokenForIp(String ip)  throws Exception{
    if (ip == null || ip.length() == 0) {
      return null;
    }
    String token = null;
    String query = "select token from tcmis_token where ip_address = '" + ip + "'";
    DBResultSet dbrs = null;
      ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        token = rs.getString("token");
        break;
      }

     } catch (Exception e) {
     e.printStackTrace();
     HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
     throw e;
     } finally{
         dbrs.close();
       }

    if (checkAuth(token,ip)){
       return token;
    }
    return null;
  }

  public void addConnectionType(String token, String connection) throws Exception {
    String query = "update tcmis_token set connection = '"+connection+"' where token = '" + token + "'";
    try {
      db.doUpdate(query);
     } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    }
  }

  public void addUserInfo(String token, String name, String client, String version) throws Exception {
       if (version==null) version = "BETA";
    
    String query = "update tcmis_token set user_name = '"+name+"', client_name = '"+client+"', tcmis_version= '"+version+"' where token = '" + token + "'";
    try {
      db.doUpdate(query);

     } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    }
  }
}




























