package radian.tcmis.server7.share.db;

import java.sql.*;

/**
    This code should be used for a weblogic database pool.
*/

public class DBOracle implements DB
{
//  private static final String DATABASE_DRIVER = DBConstants.DRIVER_ORACLE;
    private String url = null;
    private String username= null;
    private String password= null;
   
    //before trong 8-1 protected DBOracle()
    public DBOracle()
    {
    }
    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, username, password);
    }
    public void init(String url, String username, String password) throws Exception
    {
        this.url = url;
        this.username = username;
        this.password = password;
        
        Class.forName( DRIVER_ORACLE ).newInstance();
    }
}