package radian.tcmis.common.exceptions;

/****************************************************************************
 * Exception thrown copying a bean throws an Exception while converting
 * data types
 ****************************************************************************/
public class BeanCopyException extends BaseException {
    public BeanCopyException(String message) {
        super(message);
        setMessageKey(message);
    }
}
