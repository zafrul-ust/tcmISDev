package radian.tcmis.server7.share.db;

import java.sql.*;
/**
 * This is the interface that must be implemented by all DB objects.
 */
public interface DB
{
    public static final String DB_NONE      = "None";
    public static final String DB_MM        = "MM";
    public static final String DB_ORACLE    = "Oracle";
    public static final String DB_WEBLOGIC  = "Weblogic";

    public static final String DRIVER_MM        = "org.gjt.mm.mysql.Driver";
    public static final String DRIVER_ORACLE    = "oracle.jdbc.driver.OracleDriver";
    public static final String DRIVER_WEBLOGIC  = "weblogic.jdbc.pool.Driver";
    public Connection getConnection() throws SQLException;
    public void init(String url, String username, String password)
        throws Exception;
}