package com.tcmis.client.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import radian.tcmis.common.util.BeanHandler;

import com.tcmis.client.common.beans.DeliveryScheduleInputBean;
import com.tcmis.client.common.process.DeliveryScheduleViewProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

public class DeliveryScheduleViewAction extends TcmISBaseAction {

	public DeliveryScheduleViewAction() {
		
	}
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "deliveryschedulechange");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// set the standalone flag to true since MSDS Maintenance should work as a standalone app
			request.setAttribute("standalone", "true");
			// Now send the user to the login page
			next = mapping.findForward("login");
		}else {
			PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			
			if (form != null) {
		    	DeliveryScheduleViewProcess process = new DeliveryScheduleViewProcess(this.getDbUser(request),getTcmISLocaleString(request));
		    	String uAction = (String) ((DynaBean) form).get("uAction");
		    	
		    	if ("showAll".equals(uAction)) {
		    		String company = (String) ((DynaBean) form).get("company");
		    		String facility = (String) ((DynaBean) form).get("facility");
		    		String reviewer = (String) ((DynaBean) form).get("reviewer");
		    		String searchTerms = (String) ((DynaBean) form).get("searchTerms");
		    		DeliveryScheduleInputBean dsib = new DeliveryScheduleInputBean();
		    		BeanHandler.copyAttributes((DynaBean) form, dsib);
		    		//request.setAttribute("deliveryScheduleViewBeanCollection", process.showAllScheduledDeliveries(company,facility,reviewer,personnelBean.getPersonnelIdBigDecimal(),searchTerms));
		    		request.setAttribute("deliveryScheduleViewBeanCollection", process.showAllScheduledDeliveries(dsib,personnelBean.getPersonnelIdBigDecimal()));
		    		request.setAttribute("reviewer", reviewer.toLowerCase());
		    	}else {
		    		request.setAttribute("reviewerCompFacBeanCollection", process.getCompanyFacility(personnelBean.getPersonnelIdBigDecimal()));
		    	}
			}
		}
		return next;
	}
}
