package com.tcmis.internal.supply.action;

import java.util.Collection;
import java.util.Vector;

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
import com.tcmis.internal.supply.beans.POSupplierInputBean;
import com.tcmis.internal.distribution.beans.SupplierEntitySearchViewBean;
import com.tcmis.internal.supply.process.POSupplierProcess;

/******************************************************************************
 * Controller for Purchase Order Supplier search page
 * @version 1.0
 ******************************************************************************/

public class POSupplierAction extends TcmISBaseAction
{
	private void doSearch(POSupplierInputBean inputBean, String popUp) throws Exception {
		
		POSupplierProcess process = new POSupplierProcess(this.getDbUser(request));
		
		Collection supplierCollection = null;
	    if("Y".equals(popUp))
	    	supplierCollection = process.getSupplierEntitySearchViewBeanCollection(inputBean);
	    else
	    	supplierCollection = process.getSupplierAddressViewBeanCollection(inputBean);
           
        request.setAttribute("supplierAddressViewBeanCollection",  supplierCollection);
		request.setAttribute("vvPaymentTermsBeanCollection", process.getPaymentTermsDropDown());
		
	}

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "posuppliermain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");		
		
		request.setAttribute("valueElementId",request.getParameter("valueElementId"));
		request.setAttribute("displayElementId",request.getParameter("displayElementId"));	
		request.setAttribute("statusFlag",request.getParameter("statusFlag"));
		String popUp = request.getParameter("popUp");
		
		 if (!personnelBean.getPermissionBean().hasUserPagePermission("supplierSearch"))
	       {
	         return (mapping.findForward("nopermissions"));
	       }
		    	    
		POSupplierInputBean inputBean = new POSupplierInputBean();
		BeanHandler.copyAttributes(form, inputBean);
		POSupplierProcess process = new POSupplierProcess(this.getDbUser(request));
      
	    String uAction = (String)( (DynaBean) form).get("uAction");

	    if ("new".equals(uAction) ) 
	    	return mapping.findForward("showrequestdetail");    

	    if ("search".equals(uAction)) 
		{    	
	    	doSearch(inputBean, popUp);
			this.saveTcmISToken(request);
		}
		
		 else if ( "createExcel".equals(uAction)) 
		 {	 
			 this.setExcel(response, "Supplier Search List");
			 
			Collection supplierColl = null;
		    if("Y".equals(request.getParameter("popUp"))) {
		    	supplierColl = process.getSupplierEntitySearchViewBeanCollection(inputBean);
		    	process.getExcelReport(supplierColl,
		                  (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
		    }
		    else {
		    	supplierColl = process.getSupplierAddressViewBeanCollection(inputBean);
		    	process.getSupplierAddressExcelReport(supplierColl,
		                  (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
		    }
		    	
	        return noForward;
	     }
		 else if ( "update".equals(uAction)) 
		 {	 
			  if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("BuyOrder",null,null,null))
	          {
	              request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
	              doSearch(inputBean, popUp);
	          }

	          checkToken(request);
	          SupplierEntitySearchViewBean poSupplierBean = new SupplierEntitySearchViewBean();
	          Collection<SupplierEntitySearchViewBean> beans = BeanHandler.getBeans((DynaBean) form, "supplierAddressViewBean", poSupplierBean,getTcmISLocale(request));
	         
	          Collection errorMsgs =  process.update(beans);
	          doSearch(inputBean, popUp);  
	     }
		 else if ( "showcurrentpaymentterms".equals(uAction)) 
		 {	 
			  
              request.setAttribute("currentPaymentTermsCollection", process.getCurrentPaymentTerms(inputBean));
              request.setAttribute("currentPaymentTermsExceptionsCollection", process.getPaymentTermsExceptions(inputBean)); 
                 
	     }
		 else
		 {   // load search area data.
			 request.setAttribute("vvCountryBeanCollection", process.getCountryList());
		 }
		
		return (mapping.findForward("success"));
    }
  
}
