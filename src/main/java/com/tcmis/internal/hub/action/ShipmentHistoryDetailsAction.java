package com.tcmis.internal.hub.action;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;

import com.tcmis.internal.hub.beans.ShipmentBean;
import com.tcmis.internal.hub.beans.ShipmentHistoryInputBean;
import com.tcmis.internal.hub.beans.ShipmentHistoryDetailsViewBean;
import com.tcmis.internal.hub.process.ShipmentHistoryDetailsProcess;


public class ShipmentHistoryDetailsAction extends TcmISBaseAction
{
  
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception 
	{
        /*Cannot check login as this is also accessed from the client cabinet inventory page*/
       if (!this.isLoggedIn(request))
		{
			request.setAttribute("requestedPage", "shipmenthistorydetails");
		
			request.setAttribute("requestedURLWithParameters",
				 this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
       
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	   	if (!personnelBean.getPermissionBean().hasUserPagePermission("shipmentHistory"))
		{
		   return (mapping.findForward("nopermissions"));
		}
		request.setAttribute("startSearchTime", new Date().getTime());    
		ShipmentHistoryInputBean inputBean = new ShipmentHistoryInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		ShipmentHistoryDetailsProcess shipmentHistoryDetailsProcess = new ShipmentHistoryDetailsProcess(this.getDbUser(request),getTcmISLocaleString(request));
	    String uAction = (String) ( (DynaBean)form).get("uAction");

		if ( "createExcel".equals(uAction))
        {
            this.setExcel(response, "shipmentHistorydetailsExcel");
            shipmentHistoryDetailsProcess.getExcelReport(shipmentHistoryDetailsProcess.getShipmentHistoryDetails(inputBean.getShipmentId(),personnelId),
                                                         (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
            return noForward;
        }
		if( "update".equals(uAction) ) {
	    	checkToken(request);
	    	shipmentHistoryDetailsProcess.updateShippingReference(BeanHandler.getBeans((DynaBean)form, "shipmentHistoryDetailsViewBean", new ShipmentHistoryDetailsViewBean(), getTcmISLocale(request)));
	    }
		saveTcmISToken(request);
		Collection shipmentHistoryDetailsViewBeanColl = shipmentHistoryDetailsProcess.getShipmentHistoryDetails(inputBean.getShipmentId(),personnelId);
		request.setAttribute("shipmentHistoryDetailsViewBeanColl", shipmentHistoryDetailsViewBeanColl);  
		request.setAttribute("endSearchTime", new Date().getTime() ); 
		return mapping.findForward("success");
	}
	
} //end of class
