package com.tcmis.internal.catalog.action;

import java.util.Collection;
import java.util.Vector;
import java.util.stream.Collectors;

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
import com.tcmis.internal.catalog.beans.CatalogQueueItemBean;
import com.tcmis.internal.catalog.beans.CatalogVendorAssignmentBean;
import com.tcmis.internal.catalog.process.CatalogSupplierProcess;

/******************************************************************************
 * Controller for Purchase Order Supplier search page
 * @version 1.0
 ******************************************************************************/

public class NewTaskAction extends TcmISBaseAction
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
    
		//PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");		

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
        PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
        CatalogVendorAssignmentBean inputBean = new CatalogVendorAssignmentBean();
		BeanHandler.copyAttributes(form, inputBean);
		CatalogSupplierProcess process = new CatalogSupplierProcess(this);
      
	    String uAction = (String)( (DynaBean) form).get("uAction");

		if( uAction == null ) return mapping.findForward("success");

	    if ("search".equals(uAction)) 
		{    	  
			Collection supplierAddressViewBeanCollection = process.getQueueItem(inputBean);             
			request.setAttribute("beanCollection", supplierAddressViewBeanCollection);
		}
	    else if ("addLocalesToTask".equals(uAction)) {
	    	try {
		    	Collection<CatalogQueueItemBean> taskLocaleColl = BeanHandler.getBeans((DynaBean) form, "taskLocaleBean", new CatalogQueueItemBean(), getTcmISLocale(), "ok");
		    	Collection<CatalogVendorAssignmentBean> vendorItemColl = taskLocaleColl.stream()
		    			.map(task -> {
		    				CatalogVendorAssignmentBean vendorItem = new CatalogVendorAssignmentBean();
		    				vendorItem.setLocaleCode(task.getLocaleCode());
		    				vendorItem.setTask(task.getTask());
		    				vendorItem.setSupplier(inputBean.getSupplier());
		    				return vendorItem;
		    			}).collect(Collectors.toCollection(() -> new Vector<CatalogVendorAssignmentBean>()));
		    	process.addLocalesToTask(taskLocaleColl);
		    	process.addCatalogVendorAssignment(vendorItemColl, personnelBean);
	    	} catch(BaseException e) {
	    		String message = e.getMessage();
	    		if (message.contains("ORA-02291")) {
	    			message = "Error: Invalid Item ID. Please use an existing Item ID.";
	    		}
	    		request.setAttribute("tcmISError", message);
	    	}
	    	Collection supplierAddressViewBeanCollection = process.getQueueItem(inputBean);             
			request.setAttribute("beanCollection", supplierAddressViewBeanCollection);
	    }
		 else if ( "createExcel".equals(uAction)) 
		 {	 
//	          this.setExcel(response, "Supplier Search List");
//	          process.getExcelReport(inputBean,
//	                  (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	          return noForward;
	     }
		 else
		 {   // load search area data.
//			 request.setAttribute("vvCountryOnlyBeanCollection", process.getCountryList());
		 }

		return (mapping.findForward("success"));
    }
}
