package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;

public class SessionCheckAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			PersonnelBean user = (PersonnelBean)request.getSession().getAttribute("personnelBean");
			results.put("Status", RESPONSE_OK);
			results.put("PersonnelId", user.getPersonnelId());
			results.put("LogonId", user.getLogonId());
			results.put("Name", user.getFullName());
			results.put("Email", user.getEmail());
		}
		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}
}
