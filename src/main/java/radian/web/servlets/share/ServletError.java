package radian.web.servlets.share;

/**
 * Title:        tcmIS
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corp
 * @author Rajendra Rajput
 * @version 1.0
 */

public class ServletError extends Exception {

    Throwable rootCause = null;

    public ServletError(String msg) {
        super(msg);
    }

    public ServletError(String msg, Throwable e) {
        super(msg);
        rootCause = e;
    }

    public Throwable getRootCause() {
        return rootCause;
    }

}