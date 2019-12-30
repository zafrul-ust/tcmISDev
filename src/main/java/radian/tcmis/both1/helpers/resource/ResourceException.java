package radian.tcmis.both1.helpers.resource;

/**
 * Title:        tcmIS
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      URS/Radian Corp
 * @author       Rajendra Rajput
 * @version      1.0
 */


/**
 * Communicates failure information when getting requested resource
 */

public class ResourceException extends java.lang.Exception {
    Throwable rootCause = null;

    public ResourceException(String msg) {
        super(msg);
    }

    public ResourceException(String msg, Throwable e) {
        super(msg);
        rootCause = e;
    }

    public Throwable getRootCause() {
        return rootCause;
    }
}