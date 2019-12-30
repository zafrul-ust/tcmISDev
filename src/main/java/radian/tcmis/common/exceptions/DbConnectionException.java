package radian.tcmis.common.exceptions;

/****************************************************************************
 * Exception thrown when there is a problem communicating with the db.
 ****************************************************************************/
public class DbConnectionException extends BaseException {
    public DbConnectionException(String message) {
        super(message);
        setMessageKey(message);
    }
}
