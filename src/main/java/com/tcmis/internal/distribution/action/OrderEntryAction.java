package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
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
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.OrderEntryInputBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
import com.tcmis.internal.distribution.process.OrderEntryProcess;


/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */  

public class OrderEntryAction
extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "orderentry");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
	   if (!personnelBean.getPermissionBean().hasUserPagePermission("orderEntry"))
       {
         return (mapping.findForward("nopermissions"));
       }

		String forward = "success";       
		request.setAttribute("startSearchTime", new Date().getTime());     


		OrderEntryInputBean orderEntryInputBean = new OrderEntryInputBean();
		BeanHandler.copyAttributes(form, orderEntryInputBean,getTcmISLocale(request));

		OrderEntryProcess process = new OrderEntryProcess(this.getDbUser(request),getTcmISLocaleString(request));         

		// Search
		if ( (orderEntryInputBean.getAction() == null)  || ("".equals(orderEntryInputBean.getAction()) ) ) {
			request.setAttribute("salesQuoteViewBeanCollection", process.getSearchResult(orderEntryInputBean, personnelBean));


			this.saveTcmISToken(request);

		}		
		//  Delete 
		else if ("delete".equalsIgnoreCase(orderEntryInputBean.getAction())) {
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders",null,null,null))
			{
				request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
				request.setAttribute("salesQuoteViewBeanCollection", process.getSearchResult(orderEntryInputBean, personnelBean));
				return (mapping.findForward("success"));
			}

			checkToken(request);
			SalesQuoteViewBean bean = new SalesQuoteViewBean();
			Collection<SalesQuoteViewBean> beans;
			try {
				beans = BeanHandler.getBeans((DynaBean) form, "salesQuoteViewBean", bean,getTcmISLocale(request));
				
				Collection errorMsgs =  process.delete(beans, personnelBean);
				request.setAttribute("tcmISErrors", errorMsgs);   
			} catch (Exception e) {
				// TODO Auto-generated catch block				
				request.setAttribute("tcmISErrors", e.toString());
			}
			
			request.setAttribute("salesQuoteViewBeanCollection", process.getSearchResult(orderEntryInputBean, personnelBean));
			

		}

		request.setAttribute("endSearchTime", new Date().getTime() );      
		return (mapping.findForward(forward));
	}
}
