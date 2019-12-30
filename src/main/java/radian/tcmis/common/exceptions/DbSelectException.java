package radian.tcmis.common.exceptions;

/****************************************************************************
 * Exception thrown from the select method in <code>DbManager</code> or
 * <code>SqlManager</code>.
 ****************************************************************************/
public class DbSelectException extends BaseException {
    public DbSelectException(String message) {
        super(message);
        setMessageKey(message);
    }
}
