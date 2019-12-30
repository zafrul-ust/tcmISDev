package radian.tcmis.common.exceptions;

/****************************************************************************
 * Exception thrown from the connection pool.
 ****************************************************************************/
public class ConnectionPoolException extends BaseException {
    public ConnectionPoolException(String message) {
        super(message);
        setMessageKey(message);
    }
}
