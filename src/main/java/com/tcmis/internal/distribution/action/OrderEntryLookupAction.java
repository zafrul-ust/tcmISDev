package com.tcmis.internal.distribution.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.OrderEntryLookupSearchBean;
import com.tcmis.internal.distribution.beans.SalesOrderViewBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
import com.tcmis.internal.distribution.process.OrderEntryLookupProcess;

public class OrderEntryLookupAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//Login
	   	if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "orderentitylookupmain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
	   	}	
	   	
	   	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
/*		if (!personnelBean.getPermissionBean().hasUserPagePermission("salesOrders"))
		{
		   return (mapping.findForward("nopermissions"));
		}  */
		
	   	//Main
	  	String uAction = (String) ( (DynaBean)form).get("uAction");
		if( uAction == null ) return mapping.findForward("success");
		
		//Process search
		OrderEntryLookupProcess process = new OrderEntryLookupProcess(this.getDbUser(request),getTcmISLocaleString(request));		
		
		OrderEntryLookupSearchBean inputBean = new OrderEntryLookupSearchBean();
	    
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
	    
		// Search
	    if ( uAction.equals("searchOrders")) {
	    	Collection<SalesQuoteViewBean> salesOrderCollection = process.getSalesOrder(inputBean, personnelBean);		
			request.setAttribute("orderEntityViewBeanColl", salesOrderCollection);
			this.saveTcmISToken(request);
	        return (mapping.findForward("success"));
	    }
	    if ( uAction.equals("createExcel")) {
	    	 this.setExcel(response, "OrderEntryExcel");
	         process.getExcelReport(process.getSalesOrder(inputBean, personnelBean), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	         return noForward;
	    }

	    
	    else {
	      return mapping.findForward("success");
	   }
	}

}
