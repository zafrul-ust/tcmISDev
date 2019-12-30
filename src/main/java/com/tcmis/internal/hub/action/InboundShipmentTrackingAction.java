package com.tcmis.internal.hub.action;

import java.util.Collection;

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
import com.tcmis.internal.hub.beans.InboundShipmentTrackingInputBean;
import com.tcmis.internal.hub.process.InboundShipmentTrackingProcess;

/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */

public class InboundShipmentTrackingAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("success");
		//Login
		if (!isLoggedIn(request)) {
			// Save this page for returning after logging in
			request.setAttribute("requestedPage", "inboundshipmenttrackingmain");
			//	Save any parameters passed in the URL, so that they can be accessed after the login
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			forward = mapping.findForward("login");
		}
		else {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			// Need to have the permission from the database, normally it's the pageName instead of "mrRelease"
			if (!user.getPermissionBean().hasUserPagePermission("inboundShipmentTracking")) 
			{
				forward = mapping.findForward("nopermissions");
			}
			else 
			{
				InboundShipmentTrackingProcess process = new InboundShipmentTrackingProcess(getDbUser(request), getTcmISLocale());
				InboundShipmentTrackingInputBean input = new InboundShipmentTrackingInputBean(form, getTcmISLocale());

				// Search
				if (input.isSearch()) {
					request.setAttribute("searchResults", process.getSearchResults(input, user));
					saveTcmISToken(request);
				}
				else if (input.isCreateExcel()) {
					setExcel(response, "InboundShipmentTrackingExcel");
					process.getExcelReport(process.getSearchResults(input, user), getTcmISLocale()).write(response.getOutputStream());
					forward = noForward;
				}
			}
		}
		return forward;
	}
}
