package radian.tcmis.common.exceptions;

/****************************************************************************
 * Exception thrown from a update method in <code>DbManager</code> or
 * <code>SqlManager</code>.
 ****************************************************************************/
public class DbUpdateException extends BaseException {
    public DbUpdateException(String message) {
        super(message);
        setMessageKey(message);
    }
}
