package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.process.InboundTransProcess;
import com.tcmis.ws.tablet.process.ReceiptProcess;
import com.tcmis.ws.tablet.process.SimpleTabletProcess;

public class ValidateBinAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			TabletInputBean input = new TabletInputBean(form, getTcmISLocale(request));

			if (input.hasBin() && input.hasHubId()) {
				SimpleTabletProcess tabletProcess = new SimpleTabletProcess(getDbUser());
				String status = tabletProcess.getBinStatus(input);
				if (StringHandler.isBlankString(status)) {
					results.put("Status", RESPONSE_OK);
					results.put("Results", "Invalid");					
				}
				else if ("I".equals(status)) {
					results.put("Status", RESPONSE_OK);
					results.put("Results", "Inactive");										
				}
				else {
					results.put("Status", RESPONSE_OK);
					results.put("Results", "Active");					
				}
			}
			else {
				results.put("Status", RESPONSE_ERROR);
				results.put("Message", "hubId and bin are required fields");
			}
		}

		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
