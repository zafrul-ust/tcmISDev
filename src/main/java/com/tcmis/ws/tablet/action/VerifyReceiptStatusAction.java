package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReceivingStatusViewBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.process.ReceiptProcess;

public class VerifyReceiptStatusAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TabletInputBean input = new TabletInputBean(form, getTcmISLocale(request));

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else if (!input.hasHub()) {
			results.put("Status", RESPONSE_ERROR);
			results.put("Message", "hub is required.");
		}
		else if (!input.hasReceiptId()) {
			results.put("Status", RESPONSE_ERROR);
			results.put("Message", "Receipt Id is required.");
		}
		else {
			ReceiptProcess process = new ReceiptProcess(getDbUser());
			PersonnelBean user = (PersonnelBean) request.getSession().getAttribute("personnelBean");
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getTcmISLocale());
			String dateFormat = library.getString("java.tabletdateformat");
			try {
				Collection<ReceivingStatusViewBean> beans = process.verifyReceiptId(input);
				if (beans != null && beans.size() > 0) {
					Iterator<ReceivingStatusViewBean> it = beans.iterator();
					ReceivingStatusViewBean bean = it.next();
					String receivingStatus = bean.getReceivingStatus(); 
					Date qcDate = bean.getQcDate();
					String binId = bean.getBin();
					if ((StringHandler.isBlankString(receivingStatus) || receivingStatus.equalsIgnoreCase("Binned")) && qcDate != null && binId != null) {
						Collection<ReceivingStatusViewBean> receiptDataCol = process.getReceiptDataByIdHub(input);				
						JSONArray receiptdata = new JSONArray();
						for (ReceivingStatusViewBean rcptData : receiptDataCol) {
							receiptdata.put(BeanHandler.getJsonObject(rcptData, dateFormat));
						}
						results.put("ReceiptData", receiptdata );	
						results.put("Exists", Boolean.TRUE);
					} else {
						results.put("Message", "NO_REBIN"); //Receipt Id - " + input.getReceiptId() + " cannot be ReBinned at this time.");	   
						results.put("Exists", Boolean.FALSE);
					}
				} else {
					results.put("Message", "NO_DATA"); // No data found for Receipt Id - " + input.getReceiptId());   
					results.put("Exists", Boolean.FALSE);					
				}
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
