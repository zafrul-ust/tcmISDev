package com.tcmis.client.api.action;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.api.beans.InvoicePrintRollinsViewBean;
import com.tcmis.client.api.process.EcommerceInvoiceProcess;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;

public class EcommerceInvoiceUpdateAction extends BaseApiAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();
			
			try {
				JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
				
				if (!input.has("invoices")) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "invoice(s) required");
					results.put("success", false);
				}
				else {
					EcommerceInvoiceProcess process = new EcommerceInvoiceProcess("TCM_OPS");

					try {
						process.submitInvoices(input.getJSONArray("invoices"));

						results.put("success", true);				
					}
					catch (Exception e) {
						String errMsg = "Error updating invoice(s) as sent";
						
						log.error(errMsg, e);
						
						results.put("errorMsg", errMsg);
						results.put("success", false);
						
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					}
				}
		
				// Write out the data
				PrintWriter out = response.getWriter();
				out.write(results.toString(3));
				out.close();
			}
			catch (JSONException jse) {
				results.put("errorCode", RESPONSE_INVALID_INPUT_TEXT);
				results.put("errorMessage", "Input is not valid JSON");
				results.put("success", false);
			}
		}
		catch (Exception e) {
			log.error("Base Error in EcommerceInvoiceUpdateAction", e);	
			throw new BaseException(e);
		}
		return noForward;
	}
}
