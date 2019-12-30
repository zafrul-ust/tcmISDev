package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.process.CreditReviewDetailsProcess;


/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */  

public class CreditReviewDetailsAction
extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "creditreviewdetails");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		/* if (!personnelBean.getPermissionBean().hasUserPagePermission("receiptSpec"))
       {
         return (mapping.findForward("nopermissions"));
       }*/

		String forward = "success";       
		request.setAttribute("startSearchTime", new Date().getTime());     

		String customerId = request.getParameter("customerId");
		String opsEntityId = request.getParameter("opsEntityId");
		
		if(StringHandler.isBlankString(customerId))
		{
			return mapping.findForward("genericerrorpage");
		}		
		
		String uAction = (String) ((DynaBean) form).get("uAction");
		
		CreditReviewDetailsProcess process = new CreditReviewDetailsProcess(this.getDbUser(request),getTcmISLocaleString(request));         
		
		if ("unappliedCash".equals(uAction)) {
			this.setExcel(response, "UnappliedCashExcel");
	        process.getUnappliedCashExcel(process.getCustomerUnappliedCash(customerId,opsEntityId), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	        return noForward;
		}
		else if ("openInvoices".equals(uAction)) {  
			this.setExcel(response, "OpenInvoicesExcel");
	        process.getOpenInvoicesExcel(process.getCustomerOpenInvoices(customerId,opsEntityId), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	        return noForward;
		}
		else if ("openOrders".equals(uAction)) {  
			this.setExcel(response, "OpenOrdersExcel");
	        process.getOpenOrdersExcel(process.getCustomerOpenOrders(customerId,opsEntityId), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	        return noForward;
		}
		
		request.setAttribute("CustomerUnappliedCashViewBeanCol", process.getCustomerUnappliedCash(customerId,opsEntityId));
		request.setAttribute("CustomerOpenInvoiceViewBeanCol", process.getCustomerOpenInvoices(customerId,opsEntityId));
		request.setAttribute("CustomerOpenOrdersViewBeanCol", process.getCustomerOpenOrders(customerId,opsEntityId));
		this.saveTcmISToken(request);

		
		request.setAttribute("endSearchTime", new Date().getTime() );      
		return (mapping.findForward(forward));
	}
}
