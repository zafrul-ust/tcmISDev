package radian.tcmis.common.exceptions;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NoDataException extends BaseException {
    public NoDataException(String message) {
        super(message);
        setMessageKey(message);
    }
}
