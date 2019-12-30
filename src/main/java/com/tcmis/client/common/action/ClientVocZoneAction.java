package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.VocZoneBean;
import com.tcmis.client.common.process.ClientVocZoneProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for Manufacturer Search page
 * @version 1.0
 ******************************************************************************/

public class ClientVocZoneAction extends TcmISBaseAction {

	private void doSearch(DynaBean dynaForm, PersonnelBean user) throws Exception {
		String companyId = (String) dynaForm.get("companyId");
		String facilityId = (String) dynaForm.get("facilityId");
		ClientVocZoneProcess process = new ClientVocZoneProcess(getDbUser());
		request.setAttribute("voczones", process.getVocZones(companyId,facilityId));
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "manageVocZones");
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
					Collection<VocZoneBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "voczone", new VocZoneBean(), getLocale(request), "updated");
					if (updatedRows != null && !updatedRows.isEmpty()) {
						ClientVocZoneProcess process = new ClientVocZoneProcess(getDbUser());
						for (VocZoneBean voczone : updatedRows) {
							if (voczone.isNewVocZone()) {
								if (voczone.hasVocZoneDescription()) {
									voczone.setCompanyId(user.getCompanyId());
									process.insertVocZone(voczone);
								}
							}
							else {
								process.updateVocZone(voczone);
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
