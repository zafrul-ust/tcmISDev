package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.FacLocDeliveryPointBean;
import com.tcmis.client.common.process.ClientDeliveryPointProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;


/******************************************************************************
 * Controller for Manufacturer Search page
 * @version 1.0
 ******************************************************************************/

public class ClientDeliveryPointAction extends TcmISBaseAction {

	private void doSearch(DynaBean dynaForm, PersonnelBean user) throws Exception {
		String facilityId = (String) dynaForm.get("facilityId");
		String locationId = (String) dynaForm.get("locationId");
		ClientDeliveryPointProcess process = new ClientDeliveryPointProcess(getDbUser());
		request.setAttribute("deliveryPoints", process.getDeliveryPointsForDock(user.getCompanyId(), facilityId, locationId));
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "managedeliverypoints");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else {

			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			String userAction = request.getParameter("uAction");

			if (form != null && userAction != null) {
				DynaBean dynaForm = (DynaBean) form;

				if (userAction.equals("search")) {
					doSearch(dynaForm, user);
				}
				else if (userAction.equals("update")) {
					Collection<FacLocDeliveryPointBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "deliveryPoint", new FacLocDeliveryPointBean(), getLocale(request), "updated");
					if (updatedRows != null && !updatedRows.isEmpty()) {
						ClientDeliveryPointProcess process = new ClientDeliveryPointProcess(getDbUser());
						for (FacLocDeliveryPointBean DeliveryPoint : updatedRows) {
							if (DeliveryPoint.isNewDeliveryPoint()) {
								DeliveryPoint.setCompanyId(user.getCompanyId());
								process.insertDeliveryPoint(DeliveryPoint);
							}
							else {
								process.updateDeliveryPoint(DeliveryPoint);
							}
						}
					}
					doSearch(dynaForm, user);
				}
			}
		}
		return next;
	}
}
