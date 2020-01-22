package com.tcmis.client.api.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.JsonRequestResponseAction;

abstract class BaseApiAction extends JsonRequestResponseAction {
	public static Log log = LogFactory.getLog(BaseApiAction.class);
	protected static final String RESPONSE_OK = "200";
	protected static final String RESPONSE_OK_TEXT = "OK";
	protected static final String RESPONSE_INVALID_LOGIN = "401";
	protected static final String RESPONSE_INVALID_LOGIN_TEXT = "You don't have permission to access tcmIS";
	protected static final String RESPONSE_INVALID_INPUT = "500";
	protected static final String RESPONSE_INVALID_INPUT_TEXT = "Input is not valid JSON";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.request = request;
		if (log.isDebugEnabled()) {
			log.debug(getRequestedURLWithParameters(request));
		}
		executeAction(mapping, form, request, response);
		return mapping.findForward("empty");
	}

	protected boolean isLoggedIn(HttpServletRequest request, boolean ignoreCompany) {
		return true;
	}

}
