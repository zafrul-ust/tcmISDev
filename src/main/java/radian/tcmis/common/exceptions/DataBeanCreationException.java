package radian.tcmis.common.exceptions;

/****************************************************************************
 * Exception thrown from a factory trying to create data beans.
 ****************************************************************************/
public class DataBeanCreationException extends BaseException {
    public DataBeanCreationException(String message) {
        super(message);
        setMessageKey(message);
    }
}
