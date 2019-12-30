package com.tcmis.ws.tablet.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;

abstract class BaseTabletAction extends TcmISBaseAction {
	public static Log log = LogFactory.getLog(GenericProcess.class);
	protected static final String RESPONSE_OK = "OK";
	protected static final String RESPONSE_ERROR = "ERROR";
	protected static final String RESPONSE_INVALID_LOGIN = "INVALID LOGIN";
	protected static final String RESPONSE_PASSWORD_EXPIRED = "PASSWORD EXPIRED";
	protected static final String RESPONSE_SESSION_EXPIRED = "SESSION EXPIRED";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.request = request;
		if (log.isDebugEnabled()) {
			log.debug(getRequestedURLWithParameters(request));
		}
		executeAction(mapping, form, request, response);
		return mapping.findForward("empty");
	}


	public boolean responseIsOK(String response) {
		return StringHandler.isBlankString(response) || "OK".equals(response);
	}
}
