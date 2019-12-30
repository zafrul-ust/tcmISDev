package com.tcmis.client.samsung.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.samsung.process.OrderFulfillmentProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

public class OrderFulfillmentAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException {
		try {
			OrderFulfillmentProcess process = new OrderFulfillmentProcess("SAMSUNG",true);
	      	process.processData();
	    } catch (Exception e) {
	    	log.error("Samsung order fulfillment processing", e);
	        MailProcess.sendEmail("deverror@haastcm.com", null,
	                              "deverror@haastcm.com",
	                              "Samsung order fulfillment processing",
	                              "Samsung order fulfillment processing.\n" + e.getMessage());
	    }
		
		return noForward;
	}
}
