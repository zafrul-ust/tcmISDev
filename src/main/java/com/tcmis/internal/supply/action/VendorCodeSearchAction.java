package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.supply.beans.POItemSearchInputBean;

import com.tcmis.internal.supply.process.POItemSearchProcess;
import com.tcmis.internal.supply.process.SupplierRequestProcess;

/******************************************************************************
 * Controller for Purchase Order Item Search page
 * @version 1.0
 ******************************************************************************/

public class VendorCodeSearchAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "vendorcodesearchmain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		if (form == null) 
		{  
			return (mapping.findForward("success"));
		}
		
	    SupplierRequestProcess process = new SupplierRequestProcess(this.getDbUser(request),this.getLocale(request));
	    LogisticsInputBean bean = new LogisticsInputBean();
	    BeanHandler.copyAttributes(form, bean);
	    
	    String searchType= request.getParameter("searchType");
	    if( "sapCustomerCode".equals(searchType) )
		    request.setAttribute("beanCollection", process.getCustomerCodeData(bean));
	    else
	    	request.setAttribute("beanCollection", process.getVendorCodeData(bean));
		return (mapping.findForward("success"));
    }
  
}
