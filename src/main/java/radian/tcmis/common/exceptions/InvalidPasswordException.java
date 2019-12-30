package radian.tcmis.common.exceptions;

/****************************************************************************
 * General exception.
 ****************************************************************************/
public class InvalidPasswordException extends BaseException {
    public InvalidPasswordException(String message) {
        super(message);
        setMessageKey(message);
    }
}
