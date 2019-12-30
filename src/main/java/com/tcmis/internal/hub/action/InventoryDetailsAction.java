package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.internal.hub.beans.LogisticsViewBean;

import com.tcmis.internal.hub.process.CabinetInventoryProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for InventoryDetails
 * @version 1.0
	******************************************************************************/

public class InventoryDetailsAction extends TcmISBaseAction
{
  
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception 
	{
        /*Cannot check login as this is also accessed from the client cabinet inventory page*/
        /*if (!this.isLoggedIn(request))
		{
			request.setAttribute("requestedPage", "inventorydetails");
		
			request.setAttribute("requestedURLWithParameters",
				 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}*/
		request.setAttribute("startSearchTime", new Date().getTime());    
		//PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		//BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		String itemId = request.getParameter("itemId");
		String hub = request.getParameter("hub");
		//log.debug("selected itemId = [" + itemId + "]; selected hub = [" + hub + "]; " ); 

		CabinetInventoryProcess cabinetInventoryProcess = new CabinetInventoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
		Collection logisticsViewBeanCollection = cabinetInventoryProcess.getLogisticsViewBeanCollection(itemId, hub);
		//log.debug("logisticsViewBeanCollection.size() = " + logisticsViewBeanCollection.size() + ";"); 
    
		request.setAttribute("logisticsViewBeanCollection", logisticsViewBeanCollection);  
		request.setAttribute("endSearchTime", new Date().getTime() ); 
		return mapping.findForward("success");
	}
} //end of class

