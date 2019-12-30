package com.tcmis.internal.supply.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.supply.process.PoUpdatePaymentTermsProcess;

public class PoUpdatePaymentTermsAction extends TcmISBaseAction {
		public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request)) {
			results.put("Message", getResourceBundleValue(request, "label.timeoutmessage1"));
		}
		else
		{
			PoUpdatePaymentTermsProcess poUpdatePaymentTermsProcess = new PoUpdatePaymentTermsProcess(getDbUser(), getTcmISLocale());
			String supplierId = request.getParameter("supplierId");
			String inventoryGroup = request.getParameter("inventoryGroup");
			
			String paymentTerms = poUpdatePaymentTermsProcess.getPaymentTerms(inventoryGroup, supplierId );
			if(paymentTerms == null || paymentTerms.equalsIgnoreCase("")) {
				results.put("Message", getResourceBundleValue(request, "error.db.select"));				
			} else {
				results.put("paymentTerms", paymentTerms);
			}
		}
					
		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;			
	}
}
