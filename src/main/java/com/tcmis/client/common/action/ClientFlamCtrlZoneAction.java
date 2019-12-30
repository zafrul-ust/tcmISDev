package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.FlammabilityControlZoneBean;
import com.tcmis.client.common.process.ClientFlamCtrlZoneProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for Manufacturer Search page
 * @version 1.0
 ******************************************************************************/

public class ClientFlamCtrlZoneAction extends TcmISBaseAction {

	private void doSearch(DynaBean dynaForm, PersonnelBean user) throws Exception {
		String companyId = (String) dynaForm.get("companyId");
		String facilityId = (String) dynaForm.get("facilityId");
		ClientFlamCtrlZoneProcess process = new ClientFlamCtrlZoneProcess(getDbUser());
		request.setAttribute("flammabilitycontrolzones", process.getFlammabilityControlZones(companyId, facilityId));
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "manageFlamCtrlZns");
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
					Collection<FlammabilityControlZoneBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "flammabilitycontrolzone", new FlammabilityControlZoneBean(), getLocale(request), "updated");
					if (updatedRows != null && !updatedRows.isEmpty()) {
						ClientFlamCtrlZoneProcess process = new ClientFlamCtrlZoneProcess(getDbUser());
						for (FlammabilityControlZoneBean flammabilitycontrolzone : updatedRows) {
							if (flammabilitycontrolzone.isNewFlammabilityControlZoneId()) {
								if (flammabilitycontrolzone.hasFlammabilityControlZoneDesc()) {
									flammabilitycontrolzone.setCompanyId(user.getCompanyId());
									process.insertFlammabilityControlZone(flammabilitycontrolzone);
								}
							}
							else {
								process.updateFlammabilityControlZone(flammabilitycontrolzone);
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
