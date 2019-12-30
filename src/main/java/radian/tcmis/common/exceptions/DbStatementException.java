package radian.tcmis.common.exceptions;

/****************************************************************************
 * Exception thrown from the select method in <code>DbManager</code> or
 * <code>SqlManager</code>.
 ****************************************************************************/
public class DbStatementException extends BaseException {
    public DbStatementException(String message) {
        super(message);
        setMessageKey(message);
    }
}
