package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.internal.hub.beans.DotShippingNameInputBean;
import com.tcmis.internal.hub.process.IataShippingNameProcess;


/******************************************************************************
 * Controller for DOT Shipping Names search page
 * @version 1.0
 ******************************************************************************/

public class IataShippingNameAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "iatashippingnamemain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		/*
     	if (! permissionBean.hasFacilityPermission("DotShippingName", null, null)) 
     	{
        	return mapping.findForward("nopermissions");
     	}
		*/
		if (form == null  && request.getParameter("uAction") == null)
		{    	 
			return mapping.findForward("success");
		}
		
		DotShippingNameInputBean inputBean = new DotShippingNameInputBean(form);
		Locale locale = getTcmISLocale(request);
        	    
		IataShippingNameProcess process = new IataShippingNameProcess(this.getDbUser(request));
		if (request.getParameter("uAction").equalsIgnoreCase("shippingNameDupCheck"))
		{
			Collection c = process.checkDupShippingName(request.getParameter("iataId"));

				request.setAttribute("shippingNames", c);
				return (mapping.findForward("transfer"));
		}
		if (inputBean.isSearch()) 
		{    	  
			Collection iataBeanCollection = process.getIataBeanCollection(inputBean);
			//request.setAttribute("tcmISError", "display error testing here");
			request.setAttribute("iataBeanCollection", iataBeanCollection);
			this.saveTcmISToken(request);
		} 
      
		return (mapping.findForward("success"));
    }
  
}
