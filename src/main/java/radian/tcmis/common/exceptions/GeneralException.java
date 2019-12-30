package radian.tcmis.common.exceptions;

/****************************************************************************
 * General exception.
 ****************************************************************************/
public class GeneralException extends BaseException {
    public GeneralException(String message) {
        super(message);
        setMessageKey(message);
    }
}

