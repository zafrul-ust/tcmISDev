package com.tcmis.client.api.action;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.api.beans.InvoicePrintRollinsViewBean;
import com.tcmis.client.api.process.EcommerceInvoiceProcess;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;

public class EcommerceInvoiceAction extends BaseApiAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			
			// Write out the data
			PrintWriter out = response.getWriter();
			EcommerceInvoiceProcess process = new EcommerceInvoiceProcess("TCM_OPS");
			
			try {
				JSONArray responseBody = new JSONArray();
				Collection<InvoicePrintRollinsViewBean> invoices = process.getUnsentInvoices();
				
				if(!invoices.isEmpty()) 
					responseBody = process.getJSON(invoices);

				out.write(responseBody.toString(3));					
			}
			catch (Exception e) {
				String errMsg = "Error retrieving unsent invoices";
				
				log.error(errMsg, e);
				
				JSONObject results = new JSONObject();
				results.put("errorMsg", errMsg);
				results.put("success", false);
				
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
				out.write(results.toString(3));			
			}	
			
			out.close();
		}
		catch (Exception e) {
			log.error("Base Error in UnsentInvoicesAction", e);	
			throw new BaseException(e);
		}
		return noForward;
	}
}
