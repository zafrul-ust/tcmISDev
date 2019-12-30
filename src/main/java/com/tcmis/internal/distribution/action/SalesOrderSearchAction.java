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
import com.tcmis.internal.distribution.beans.SalesOrderSearchBean;
import com.tcmis.internal.distribution.beans.SalesOrderViewBean;
import com.tcmis.internal.distribution.process.SalesOrderSearchProcess;

public class SalesOrderSearchAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//Login
	   	if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "salesordersearchmain");
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
		SalesOrderSearchProcess process = new SalesOrderSearchProcess(this.getDbUser(request),getTcmISLocaleString(request));		
		SalesOrderSearchBean inputBean = new SalesOrderSearchBean();
	    BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
	    
	    if ( uAction.equals("searchSalesOrder")) {
	    	Collection<SalesOrderViewBean> salesOrderCollection = process.getSalesOrder(inputBean, personnelBean);		
			request.setAttribute("salesOrderViewBeanColl", salesOrderCollection);
			this.saveTcmISToken(request);
	        return (mapping.findForward("success"));
	    }
	    else if (uAction.equals("createExcel") ) {
	    	Collection<SalesOrderViewBean> salesOrderCollection = process.getSalesOrder(inputBean, personnelBean);		
	        try {
	            this.setExcel(response,"Sales Orders");
	            process.getExcelReport(inputBean,salesOrderCollection,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	        }
	        catch (Exception ex) {
	         ex.printStackTrace();
	         return mapping.findForward("genericerrorpage");
	        }
			return noForward;	  
	    }
		 else if (uAction.equals("update")) 
		 {	 
			  if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders",null,null,null))
	          {
				  Collection<SalesOrderViewBean> salesOrderCollection = process.getSalesOrder(inputBean, personnelBean);		
				  request.setAttribute("salesOrderViewBeanColl", salesOrderCollection);
			      return (mapping.findForward("success"));
	          }

	          checkToken(request);
	          SalesOrderViewBean salesOrderBean = new SalesOrderViewBean();
	          Collection<SalesOrderViewBean>  beans = BeanHandler.getBeans((DynaBean) form, "salesOrderViewBean", salesOrderBean,getTcmISLocale(request));
	         
	       Collection errorMsgs =  process.update(beans);
	       request.setAttribute("salesOrderViewBeanColl", process.getSalesOrder(inputBean, personnelBean));               		  
           request.setAttribute("tcmISErrors", errorMsgs);    
           return mapping.findForward("success");
	     }
	    // Search
	    else {
	      return mapping.findForward("success");
	   }
	}

}
