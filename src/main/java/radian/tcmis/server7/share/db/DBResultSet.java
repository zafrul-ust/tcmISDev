package radian.tcmis.server7.share.db;


import java.sql.*;

public class DBResultSet
{

    Statement st = null;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    
    public DBResultSet(){

    }

    public void setResultSet(ResultSet rs){
       this.rs = rs;
    }

    public ResultSet getResultSet(){
       return rs;
    }

    public void setStatement(Statement st){
       this.st = st;
    }

    public void close() {
      try {
        st.close();
      }catch (Exception e){};
      try {
        rs.close();
      } catch (Exception e) {};

    }

}