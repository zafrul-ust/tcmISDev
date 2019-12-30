package com.tcmis.internal.supply.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.PoViewBean;
import com.tcmis.internal.supply.process.TcmBuyHistoryProcess;

/******************************************************************************
 * Controller for Tcm Buy History page
 * @version 1.0
 ******************************************************************************/

public class TcmBuyHistoryAction extends TcmISBaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws  BaseException, Exception 
    {
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "tcmbuyhistory");
			request.setAttribute("requestedURLWithParameters",
    			 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
    
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		PermissionBean permissionBean = personnelBean.getPermissionBean();
		/*
     	if (! permissionBean.hasFacilityPermission("POSupplierContact", null, null)) 
     	{
        	return mapping.findForward("nopermissions");
     	}
		*/
	    String displayElementId = request.getParameter("displayElementId");
	    String valueElementId = request.getParameter("valueElementId");
    	request.setAttribute("displayElementId", displayElementId);
    	request.setAttribute("valueElementId", valueElementId);
    	request.setAttribute("startSearchTime", new Date().getTime());   
		    	
    	PoViewBean inputBean = new PoViewBean();
    	BeanHandler.copyAttributes(form, inputBean);
		TcmBuyHistoryProcess process = new TcmBuyHistoryProcess(this.getDbUser(request));
      
		if (null!=inputBean ) 
		{		
			Collection poViewBeanCollection = process.getPoViewBeanCollection(inputBean);
             
			request.setAttribute("poViewBeanCollection", poViewBeanCollection);
			this.saveTcmISToken(request);
		}
		
		request.setAttribute("endSearchTime", new Date().getTime() ); 
		return (mapping.findForward("success"));
    }
  
}
