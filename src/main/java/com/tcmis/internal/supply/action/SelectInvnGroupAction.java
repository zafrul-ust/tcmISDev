package com.tcmis.internal.supply.action;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.InventoryGroupSelectionBean;
import com.tcmis.internal.supply.process.SelectInvnGroupProcess;

/******************************************************************************
 * Controller for customer requests
 * @version 1.0
     ******************************************************************************/
public class SelectInvnGroupAction
    extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) 
	throws BaseException, Exception 
	{
	  
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "selectinvngroup");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}  
		
		String action = request.getParameter("Action");
		String locationId = request.getParameter("shipToLocationId");		
		request.setAttribute("startSearchTime", new Date().getTime());
		
		InventoryGroupSelectionBean inputBean = new InventoryGroupSelectionBean();
		if (form == null) {
			return (mapping.findForward("success"));
		}
		BeanHandler.copyAttributes(form, inputBean);
		
		SelectInvnGroupProcess process = new SelectInvnGroupProcess(this.getDbUser(request));
		
		if (action != null && action.equalsIgnoreCase("selectInventoryGrp")) {
			Collection<InventoryGroupSelectionBean> inventoryGrpBeansCol = process.createInventoryGroupList(locationId);		
			request.setAttribute("inventoryGroupCollection", inventoryGrpBeansCol);
			HashMap<String, String> additionalInfoCol = process.getAddDetails(locationId);
			if (additionalInfoCol != null && additionalInfoCol.size() > 0) {
				request.setAttribute("currencyId", (String)additionalInfoCol.get("currencyId"));
				request.setAttribute("opsEntityId",(String)additionalInfoCol.get("opsEntityId"));	
			}
			
			this.saveTcmISToken(request);
		}
		
		request.setAttribute("endSearchTime", new Date().getTime());
		return mapping.findForward("success");
	}
}