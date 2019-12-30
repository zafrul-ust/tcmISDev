package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.process.ReceiptProcess;

public class ReceiptByReceiptIdAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TabletInputBean input = new TabletInputBean(form, getTcmISLocale(request));

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else if (!input.hasReceiptId()) {
			results.put("Status", RESPONSE_ERROR);
			results.put("Message", "receiptId is required.");
		}
		else {
			ReceiptProcess process = new ReceiptProcess(getDbUser());
			try {
				results.put("Receipts", process.getReceiptsById(input));
				results.put("Status", RESPONSE_OK);
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
