package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.ws.tablet.beans.InboundShipmentDetailBean;
import com.tcmis.ws.tablet.process.InboundShipmentProcess;

public class InboundShipmentDetailsUpsertAction extends BaseTabletAction {

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InboundShipmentDetailBean input = new InboundShipmentDetailBean(form, getTcmISLocale(request));

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			InboundShipmentProcess process = new InboundShipmentProcess(getDbUser());
			PersonnelBean user = (PersonnelBean) request.getSession().getAttribute("personnelBean");
			try {
				Collection procResults = process.saveInboundShipmentDetail(input, user);
				Iterator procResultsIt = procResults.iterator();
				String shipmentId = "" + procResultsIt.next();
				String message = "" + procResultsIt.next();
				results.put("inboundShipmentDetailId", shipmentId);

				if (responseIsOK(message)) {
					results.put("Status", RESPONSE_OK);
				}
				else {
					results.put("Status", RESPONSE_ERROR);
					results.put("Message", message);
				}
			}
			catch (Exception ex) {
				log.error("Error updating DB", ex);
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
