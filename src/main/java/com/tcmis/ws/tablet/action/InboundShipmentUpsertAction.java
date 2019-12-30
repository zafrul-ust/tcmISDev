package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.ws.tablet.beans.InboundShipmentBean;
import com.tcmis.ws.tablet.beans.InboundShipmentDetailBean;
import com.tcmis.ws.tablet.beans.InboundShipmentDocumentBean;
import com.tcmis.ws.tablet.beans.InboundShipmentDocumentInputBean;
import com.tcmis.ws.tablet.process.InboundShipmentProcess;

public class InboundShipmentUpsertAction extends BaseTabletAction {

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InboundShipmentBean input = new InboundShipmentBean(form, getTcmISLocale(request));

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			InboundShipmentProcess process = new InboundShipmentProcess(getDbUser());
			PersonnelBean user = (PersonnelBean) request.getSession().getAttribute("personnelBean");
			String shipmentId;
			String message = "";
			try {
				if (!input.hasInboundShipmentId()) {
					Collection procResults = process.saveInboundShipment(input, user);
					Iterator procResultsIt = procResults.iterator();
					shipmentId = "" + procResultsIt.next();
					message = "" + procResultsIt.next();
					results.put("inboundShipmentId", shipmentId);
				}
				else {
					shipmentId = "" + input.getInboundShipmentId();
					results.put("inboundShipmentId", shipmentId);
				}
				if (responseIsOK(message)) {
					results.put("Status", RESPONSE_OK);
					Collection documents = BeanHandler.getBeans((DynaBean) form, "docId", "tabletdateformat", new InboundShipmentDocumentInputBean(), getTcmISLocale(request));
					process.saveInboundShipmentDocuments(documents, shipmentId, user);
					Collection details = BeanHandler.getBeans((DynaBean) form, "details", "tabletdateformat", new InboundShipmentDetailBean(), getTcmISLocale(request));
					process.saveInboundShipmentDetails(details, shipmentId, user);
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
