package radian.tcmis.common.exceptions;

/****************************************************************************
 * Exception thrown from the check method in <code>DbManager</code> and
 * <code>SqlManager</code>.
 ****************************************************************************/
public class DbCheckException extends BaseException {
    public DbCheckException(String message) {
        super(message);
        setMessageKey(message);
    }
}
