package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.process.ReceiptProcess;

public class ReceiptUpdateAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			PersonnelBean user = (PersonnelBean)request.getSession().getAttribute("personnelBean");
			TabletInputBean input = new TabletInputBean(form, getTcmISLocale(request));
			ReceiptProcess process = new ReceiptProcess(getDbUser());
			try {
				String message = process.upsertReceipts(input, user);
				if (responseIsOK(message)) {
					results.put("Status", RESPONSE_OK);
				}
				else {
					results.put("Status", RESPONSE_ERROR);
					results.put("Message", message);
				}
			}
			catch (Exception ex) {
				log.error("Error searching DB", ex);
				results.put("Status", RESPONSE_ERROR);
				results.put("Message", ex.toString());
			}
		}

		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
