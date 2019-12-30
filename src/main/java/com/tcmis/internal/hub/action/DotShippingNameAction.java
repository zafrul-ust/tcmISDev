package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.tcmis.internal.hub.beans.DotShippingNameInputBean;
//import com.tcmis.internal.hub.beans.Cfr49HazardousMaterialViewBean;

import com.tcmis.internal.hub.process.DotShippingNameProcess;

/******************************************************************************
 * Controller for DOT Shipping Names search page
 * @version 1.0
 ******************************************************************************/

public class DotShippingNameAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "dotshippingnamemain");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		PermissionBean permissionBean = personnelBean.getPermissionBean();
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
        	    
		DotShippingNameProcess process = new DotShippingNameProcess(this.getDbUser(request));
		if (request.getParameter("uAction").equalsIgnoreCase("shippingNameDupCheck"))
		{
			Collection c = process.checkDupShippingName(request.getParameter("hazmatId"));

				request.setAttribute("shippingNames", c);
				return (mapping.findForward("transfer"));
		}
		if (inputBean.isSearch()) 
		{    	  
			Collection hazMatBeanCollection = process.getHazMatBeanCollection(inputBean);
             
			request.setAttribute("hazMatBeanCollection", hazMatBeanCollection);
			this.saveTcmISToken(request);
		} 
		/*else if (inputBean.getuAction().equalsIgnoreCase("shippingNameDupCheck"))
		{
			return null;
		}*/
      
		return (mapping.findForward("success"));
    }
  
}
