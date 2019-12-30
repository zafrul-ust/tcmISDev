package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.client.common.beans.DeliveryScheduleChangeDataBean;
import com.tcmis.client.common.beans.DeliveryScheduleChangeHeaderViewBean;
import com.tcmis.client.common.beans.DeliveryScheduleFacilityBean;
import com.tcmis.client.common.process.DeliveryScheduleChangeProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class DeliveryScheduleChangeAction extends TcmISBaseAction {

	public DeliveryScheduleChangeAction() {
		
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
		    	DeliveryScheduleChangeProcess process = new DeliveryScheduleChangeProcess(this.getDbUser(request),getTcmISLocaleString(request));
		    	String uAction = (String) ((DynaBean) form).get("uAction");
		    	
		    	if ("finalSchedule".equals(uAction)) {
		    		DeliveryScheduleChangeHeaderViewBean headerBean = new DeliveryScheduleChangeHeaderViewBean();
		    		BeanHandler.copyAttributes(form, headerBean, getTcmISLocale(request));
		    		
		    		request.setAttribute("deliveryScheduleFinalBeanCollection", process.getDeliveryScheduleFinal(headerBean));
		    		request.setAttribute("headerBean", headerBean);
		    		
		    		return (mapping.findForward("finalSchedule"));
		    	}else if ("viewDelivery".equals(uAction)) {
		    		String prNumber = request.getParameter("prNumber");
		    		String lineItem = request.getParameter("lineItem");
		    		
	    			DeliveryScheduleChangeHeaderViewBean headerBean = new DeliveryScheduleChangeHeaderViewBean();
	    			headerBean.setPrNumber(new BigDecimal(prNumber));
	    			headerBean.setLineItem(lineItem);
	    			String isBuyer = (String) ((DynaBean) form).get("isBuyer");
	    			String isCsr = (String) ((DynaBean) form).get("isCsr");
		    		
	    			request.setAttribute("buyer", isBuyer);
		    		request.setAttribute("csr", isCsr);
		    		request.setAttribute("headerBean", headerBean);
		    		request.setAttribute("callback", "callback");
		    	}else if ("viewDeliveryCallback".equals(uAction)) {
		    		DeliveryScheduleChangeHeaderViewBean inputBean = new DeliveryScheduleChangeHeaderViewBean();
		    		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		    		
		    		String prNumber = request.getParameter("prNumber");
		    		String lineItem = request.getParameter("lineItem");
		    		Collection<DeliveryScheduleChangeHeaderViewBean> headerColl = process.getHeaderInfo(prNumber,lineItem);
		    		if (headerColl.isEmpty()) {
		    			DeliveryScheduleChangeHeaderViewBean bean = new DeliveryScheduleChangeHeaderViewBean();
		    			bean.setPrNumber(new BigDecimal(prNumber));
		    			bean.setLineItem(lineItem);
		    			headerColl.add(bean);
		    		}
		    		String reviewerMsg = process.getReviewMessage(prNumber, lineItem);
		    		String isBuyer = (String) ((DynaBean) form).get("isBuyer");
	    			String isCsr = (String) ((DynaBean) form).get("isCsr");
		    		
		    		request.setAttribute("buyer", isBuyer);
		    		request.setAttribute("csr", isCsr);
		    		request.setAttribute("headerBean", headerColl.iterator().next());
		    		request.setAttribute("reviewerMsg", reviewerMsg);
                    request.setAttribute("deliveryBeanCollection", process.getDeliveryView(prNumber,lineItem));
                }else if ("approve".equals(uAction)) {
		    		DeliveryScheduleChangeHeaderViewBean inputBean = new DeliveryScheduleChangeHeaderViewBean();
		    		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		    		boolean isBuyer = Boolean.parseBoolean((String) ((DynaBean) form).get("isBuyer"));
		    		boolean isCSR = Boolean.parseBoolean((String) ((DynaBean) form).get("isCsr"));
		    		
		    		if (isBuyer) {
		    			process.expediterApprove(inputBean,personnelBean);
		    		}
		    		if (isCSR) {
		    			process.csrApprove(inputBean,personnelBean);
		    		}
		    		
		    		request.setAttribute("headerBean", inputBean);
		    		request.setAttribute("callback", "callback");
		    	}
		    }
		}
		return next;
	}
}
