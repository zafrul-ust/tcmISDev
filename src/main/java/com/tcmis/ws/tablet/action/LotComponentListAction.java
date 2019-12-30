package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.process.SimpleTabletProcess;

public class LotComponentListAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			SimpleTabletProcess tabletProcess = new SimpleTabletProcess(getDbUser());
			TabletInputBean input = new TabletInputBean(form, getTcmISLocale(request));

			if (input.hasItemId()) {
				try {
					results.put("componentLots", tabletProcess.getLotNumbersForItemComponents(input));
				}
				catch (Exception ex) {
					log.error("Error searching DB", ex);
					results.put("Status", RESPONSE_ERROR);
					results.put("Message", ex.toString());
				}
				results.put("Status", RESPONSE_OK);
			}
			else {
				results.put("Status", RESPONSE_ERROR);
				results.put("Message", "ItemId is required.");
			}
		}

		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
