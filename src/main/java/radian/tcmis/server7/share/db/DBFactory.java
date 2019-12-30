package  radian.tcmis.server7.share.db;

/**
 * This class creates different DB objects based on the
 * database driver that is provided.
 *
 * @author Frank Y. Kim
 * @author Jon S. Stevens
 */
public class DBFactory {
    protected static DB create(String driver) throws InstantiationException
    {
        // NOTE: This setup only allows one type of driver to be used
        // to access a specific database
        /*
        if ( driver.equals( DB.DRIVER_MM ))
            return new DBMM();
        else if ( driver.equals( DB.DRIVER_WEBLOGIC ))
            return new DBWeblogic();
        else if ( driver.equals( DB.DRIVER_ORACLE ))
            return new DBOracle();
        else
            throw new InstantiationException("Database Type "+ driver +" not implemented!");
        */
        return new DBOracle();
    }
}