package com.tcmis.ws.scanner.action;

import java.text.ParseException;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.JsonRequestResponseAction;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;

abstract class BaseScannerAction extends JsonRequestResponseAction {
	public static Log log = LogFactory.getLog(BaseScannerAction.class);
	protected static final String RESPONSE_OK = "O";
	protected static final String RESPONSE_ERROR = "1";
	protected static final String RESPONSE_INVALID_LOGIN = "2";
	protected static final String RESPONSE_SESSION_EXPIRED = "3";
	protected static final String RESPONSE_INVALID_INPUT = "4";
	protected static final String RESPONSE_PARTIAL_SUCCESS = "5";


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
	
	protected boolean isFilterValid(JSONObject input) {
		if (input.has("filter")) {
			try {
				input.put("filterDate", DateHandler.getDateFromIso8601String(input.getString("filter")));
			}
			catch (Exception e) {
				return false;
			}
		}
		return true;
	}
}
