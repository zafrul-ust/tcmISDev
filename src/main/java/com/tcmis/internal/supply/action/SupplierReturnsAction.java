package com.tcmis.internal.supply.action;

import java.util.Collection;

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
import com.tcmis.internal.supply.beans.SupplierReturnsInputBean;
//import com.tcmis.internal.supply.beans.SupplierReturnsUpdateBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.supply.process.SupplierReturnsProcess;

/******************************************************************************
 * Controller for Purchase Order Supplier search page
 * @version 1.0
 ******************************************************************************/

public class SupplierReturnsAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "supplierreturnsmain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
  
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");		
  		
		 if (!personnelBean.getPermissionBean().hasUserPagePermission("rmaProcess"))
	       {
	         return (mapping.findForward("nopermissions"));
	       }
		    	    
		SupplierReturnsInputBean inputBean = new SupplierReturnsInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		SupplierReturnsProcess process = new SupplierReturnsProcess(this.getDbUser(request),this.getTcmISLocaleString(request));
      
	    String uAction = (String)( (DynaBean) form).get("uAction");

	    if ("search".equals(uAction)) 
		{    	  
			Collection logisticsViewBeanCollection = process.getLogisticsViewBeanCollection(personnelBean, inputBean);
             
			request.setAttribute("logisticsViewBeanCollection", logisticsViewBeanCollection);
			this.saveTcmISToken(request);
		}
		
		 else if ( "createExcel".equals(uAction)) 
		 {	 
	          this.setExcel(response, "Supplier Returns");
	          process.getExcelReport(personnelBean,inputBean,
	                  (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	          return noForward;
	     }
		 else if ( "update".equals(uAction)) 
		 {	 
			  if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("supplierReturns",null,null,null))
	          {
	              request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
	              request.setAttribute("logisticsViewBeanCollection",  process.getLogisticsViewBeanCollection(personnelBean, inputBean));
	          }

	          checkToken(request);
	          LogisticsViewBean logisticsViewBean = new LogisticsViewBean();
	          Collection<LogisticsViewBean> beans = BeanHandler.getBeans((DynaBean) form, "logisticsViewBean", logisticsViewBean, getTcmISLocale(request));
	         
	          Collection errorMsgs =  process.update(personnelBean, beans);
              request.setAttribute("logisticsViewBeanCollection", process.getLogisticsViewBeanCollection(personnelBean, inputBean));  
              if (errorMsgs != null)
              request.setAttribute("tcmISErrors", errorMsgs);    
	     }
		 else
		 {   // load search area data.
			 
		 }
		
		return (mapping.findForward("success"));
    }
  
}
