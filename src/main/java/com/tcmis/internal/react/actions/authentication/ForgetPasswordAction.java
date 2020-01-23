package com.tcmis.internal.react.actions.authentication;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.admin.process.ForgetPasswordProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.internal.react.actions.TcmisReactAction;

public class ForgetPasswordAction extends TcmisReactAction {

    @Override
    public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response,
	    String jsonString) throws BaseException {

	int responseCode = HttpServletResponse.SC_OK;
	String requestMethod = request.getMethod();
	String responseMsg = "Success";
	JSONObject responseBody = new JSONObject();
	String errMsg = "";
	try {
	    if (requestMethod.equalsIgnoreCase("PUT")) {
		String locale = request.getParameter("langSetting");
		String logonId = request.getParameter("logonId");
		if (logonId != null && locale != null) {
		    ForgetPasswordProcess process = new ForgetPasswordProcess(this.getDbUser(request), locale);
		    errMsg = process.resetNewPassword(logonId, process.getPassword());
		    if ("ok".equalsIgnoreCase(errMsg)) {
			responseBody.put("updateStatus", "success");
		    } else {
			responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			responseBody.put("tcmISError", errMsg);
		    }
		} else {
		    responseCode = HttpServletResponse.SC_BAD_REQUEST;
		    responseBody.put("tcmISError", "LogonId is empty");
		}
	    } else {
		responseCode = HttpServletResponse.SC_BAD_REQUEST;
		responseBody.put("tcmISError", "Bad request");
	    }
	} catch (JSONException e) {
	    responseCode = HttpServletResponse.SC_BAD_REQUEST;
	    responseMsg = "Request body is not valid JSON";
	} catch (Exception e) {
	    responseCode = HttpServletResponse.SC_BAD_REQUEST;
	    responseMsg = e.getMessage();
	}
	try {
	    if (responseCode == HttpServletResponse.SC_OK) {
		PrintWriter out = response.getWriter();
		out.write(responseBody.toString(2));
		out.close();
	    } else {
		response.sendError(responseCode, responseMsg);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return noForward;
    }
}
