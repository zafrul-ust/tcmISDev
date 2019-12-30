package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.ws.tablet.beans.InboundShipmentBean;
import com.tcmis.ws.tablet.process.InboundShipmentProcess;

public class InboundShipmentDetailsAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InboundShipmentBean input = new InboundShipmentBean(form, getTcmISLocale(request));

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getTcmISLocale());
			String dateFormat = library.getString("java.tabletdateformat");

			InboundShipmentProcess process = new InboundShipmentProcess(getDbUser());
			try {
				results.put("InboundShipmentDetail", process.getInboundShipmentDetails(input, dateFormat));
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
