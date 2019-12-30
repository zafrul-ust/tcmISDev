package com.tcmis.ws.authentication.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.TcmISBaseAction;

abstract class BaseAuthAction extends TcmISBaseAction {
	public static Log log = LogFactory.getLog(BaseAuthAction.class);
	protected static final String RESPONSE_REJECT = "REJECT";
	protected static final String RESPONSE_SAML = "SAML";
	protected static final String RESPONSE_EMAIL = "EMAIL";
	protected static final String RESPONSE_OK = "OK";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.request = request;
//		if (log.isDebugEnabled()) {
//			log.debug(getRequestedURLWithParameters(request));
//		}
		return executeAction(mapping, form, request, response);
		//return mapping.findForward("empty");
	}

}
