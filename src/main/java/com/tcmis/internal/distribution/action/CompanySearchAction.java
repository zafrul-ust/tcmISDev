package com.tcmis.internal.distribution.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.distribution.process.CustLookUpProcess;

/******************************************************************************
 * Controller for Purchase Order Item Search page
 * @version 1.0
 ******************************************************************************/

public class CompanySearchAction extends TcmISBaseAction
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
		
		CustLookUpProcess process = new CustLookUpProcess(this);
	    LogisticsInputBean bean = new LogisticsInputBean();
	    BeanHandler.copyAttributes(form, bean);
	    
		    request.setAttribute("beanCollection", process.getCompanyData(bean));
		return (mapping.findForward("success"));
    }
  
}
