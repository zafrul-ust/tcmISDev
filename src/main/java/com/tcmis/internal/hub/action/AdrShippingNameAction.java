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
import com.tcmis.internal.hub.process.AdrShippingNameProcess;


/******************************************************************************
 * Controller for DOT Shipping Names search page
 * @version 1.0
 ******************************************************************************/

public class AdrShippingNameAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "adrshippingnamemain");
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
		if (form == null && request.getParameter("uAction") == null)
		{    	 
			return mapping.findForward("success");
		}
		
		DotShippingNameInputBean inputBean = new DotShippingNameInputBean(form);
		Locale locale = getTcmISLocale(request);
        	    
		AdrShippingNameProcess process = new AdrShippingNameProcess(this.getDbUser(request));
		if (request.getParameter("uAction").equalsIgnoreCase("shippingNameDupCheck"))
		{
			Collection c = process.checkDupShippingName(request.getParameter("adrId"));

				request.setAttribute("shippingNames", c);
				return (mapping.findForward("transfer"));
		}
		if (inputBean.isSearch()) 
		{    	  
			Collection adrBeanCollection = process.getAdrBeanCollection(inputBean);
			//request.setAttribute("tcmISError", "display error testing here");
			request.setAttribute("adrBeanCollection", adrBeanCollection);
			this.saveTcmISToken(request);
		} 
      
		return (mapping.findForward("success"));
    }
  
}
