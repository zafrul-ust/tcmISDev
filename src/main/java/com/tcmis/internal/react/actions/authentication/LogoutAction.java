package com.tcmis.internal.react.actions.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.internal.react.actions.TcmisReactAction;

public class LogoutAction extends TcmisReactAction {

    @Override
    public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response,
	    String jsonString) throws BaseException {
	int responseCode = HttpServletResponse.SC_OK;
	JSONObject responseBody = new JSONObject();
	try {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		session.removeAttribute("personnelBean");
		session.removeAttribute("passwd");
	    }
	    String token = request.getHeader("authorization");
	    deleteToken(token);
	    responseBody.put("ok", true);
	    PrintWriter out = response.getWriter();
	    out.write(responseBody.toString(2));
	    out.close();

	} catch (Exception e) {
	    log.error(e.getMessage());
	    responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	    try {
		response.sendError(responseCode, e.getMessage());
	    } catch (IOException ex) {
		log.error(ex.getMessage());
	    }
	}
	return noForward;
    }

}