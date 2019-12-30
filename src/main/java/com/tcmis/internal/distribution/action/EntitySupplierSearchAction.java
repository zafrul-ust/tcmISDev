package com.tcmis.internal.distribution.action;

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
import com.tcmis.internal.distribution.beans.EntitySupplierSearchInputBean;
import com.tcmis.internal.distribution.process.EntitySupplierSearchProcess;
import com.tcmis.internal.supply.beans.SupplierAddressViewBean;

/******************************************************************************
 * Controller for Purchase Order Supplier search page
 * @version 1.0
 ******************************************************************************/

public class EntitySupplierSearchAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "entitysuppliersearchmain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");		

		request.setAttribute("valueElementId",request.getParameter("valueElementId"));
		request.setAttribute("displayElementId",request.getParameter("displayElementId"));
        request.setAttribute("secondarySupplier",request.getParameter("secondarySupplier"));
        request.setAttribute("rowNumber",request.getParameter("rowNumber"));
        request.setAttribute("inventoryGroup",request.getParameter("inventoryGroup"));
        request.setAttribute("statusFlag",request.getParameter("statusFlag"));

		 /*if (!personnelBean.getPermissionBean().hasUserPagePermission("entitySupplierSearch"))
	       {
	         return (mapping.findForward("nopermissions"));
	       }*/
		    	    
		EntitySupplierSearchInputBean inputBean = new EntitySupplierSearchInputBean();
		BeanHandler.copyAttributes(form, inputBean);
		EntitySupplierSearchProcess process = new EntitySupplierSearchProcess(this.getDbUser(request));
      
	    String uAction = (String)( (DynaBean) form).get("uAction");

	    if ("new".equals(uAction) ) 
	    	return mapping.findForward("showrequestdetail");    

	    if ("search".equals(uAction)) 
		{    	  
			Collection supplierAddressViewBeanCollection = process.getSupplierAddressViewBeanCollection(inputBean);             
			request.setAttribute("supplierAddressViewBeanCollection", supplierAddressViewBeanCollection);
			this.saveTcmISToken(request);
		}
		 else if ( "createExcel".equals(uAction)) 
		 {	 
	          this.setExcel(response, "Supplier Search List");
	          process.getExcelReport(inputBean,
	                  (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	          return noForward;
	     }
		 else
		 {   // load search area data.
			 request.setAttribute("vvCountryOnlyBeanCollection", process.getCountryList());
		 }

		return (mapping.findForward("success"));
    }
}
