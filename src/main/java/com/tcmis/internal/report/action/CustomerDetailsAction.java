package com.tcmis.internal.report.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.common.framework.BaseJSONAction;
import com.tcmis.internal.report.beans.CustomerDetailsInput;
import com.tcmis.internal.report.process.CustomerDetailsProcess;
import com.tcmis.ws.tablet.process.SimpleTabletProcess;

public class CustomerDetailsAction extends BaseJSONAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject results = new JSONObject();
		// if (!isLoggedIn(request, true)) {
		// results.put("Status", RESPONSE_SESSION_EXPIRED);
		// results.put("Message", "Session Expired");
		// }
		// else {
		CustomerDetailsProcess process = new CustomerDetailsProcess(getDbUser());
		CustomerDetailsInput input = new CustomerDetailsInput(form, getTcmISLocale(request));

		try {
			if (input.isValid()) {
				if (input.isGetOperationalHeader()) {
					results.put("operationalHeader", process.getOperationalHeader(input));
				}
				else if (input.isGetBillingAddresses()) {
					results.put("addresses", process.getBillingAddressesForGrid(input));
				}
				else if (input.isGetShippingAddresses()) {
					results.put("addresses", process.getShippingAddressesForGrid(input));
				}
				else if (input.isGetcatalogs()) {
					results.put("catalogs", process.getCatalogsForGrid(input));
				}
				else if (input.isGetInventoryGroups()) {
					results.put("inventoryGroups", process.getInventoryGroupsForGrid(input));
				}
				else if (input.isGetInvoiceGroups()) {
					results.put("invoiceGroups", process.getInvoiceGroupsForGrid(input));
				}
				else if (input.isGetInvoiceHeader()) {
					results.put("invoiceHeader", process.getInvoicingHeader(input));
				}
				else if (input.isGetMarkupRules()) {
					results.put("markupRules", process.getServiceFeeMarkupRulesForGrid(input));
				}
				else if (input.isGetServiceFees()) {
					results.put("serviceFees", process.getServiceFeesForGrid(input));
				}
			}
			else {
				results.put("pulldownValues", process.getCompanies());
			}
			results.put("Status", RESPONSE_OK);
		}
		catch (Exception ex) {
			log.error("Error searching DB", ex);
			results.put("Status", RESPONSE_ERROR);
			results.put("Message", ex.toString());
		}
		// }

		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
