package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.common.process.ClientWorkAreaGroupProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.ReportingEntityBean;

/******************************************************************************
 * Controller for work area group page
 * @version 1.0
 ******************************************************************************/

public class ClientWorkAreaGroupAction extends TcmISBaseAction {

	private void doSearch(DynaBean dynaForm, PersonnelBean user) throws Exception {
		String facilityId = (String) dynaForm.get("facilityId");
		ClientWorkAreaGroupProcess process = new ClientWorkAreaGroupProcess(getDbUser());
		request.setAttribute("workAreaGroups", process.getWorkAreaGroupsForFacility(user.getCompanyId(), facilityId));
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "manageworkareagroups");
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
				else if (userAction.equals("new")) {
					next = mapping.findForward("newgroup");
				}
				else if (userAction.equals("update")) {
					Collection<ReportingEntityBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "workAreaGroup", new ReportingEntityBean(), getLocale(request), "updated");
					if (updatedRows != null && !updatedRows.isEmpty()) {
						ClientWorkAreaGroupProcess process = new ClientWorkAreaGroupProcess(getDbUser());
						for (ReportingEntityBean workAreaGroup : updatedRows) {
							if (workAreaGroup.isNewGroup()) {
								workAreaGroup.setCompanyId(user.getCompanyId());
								process.insertWorkAreaGroup(workAreaGroup);
							}
							else {
								process.updateWorkAreaGroup(workAreaGroup);
							}
						}
					}
					//AdHocInventoryReportProcess adHocInventoryReportProcess = new AdHocInventoryReportProcess(this.getDbUser(request),getLocale(request));
					CabinetDefinitionManagementProcess cabinetDefinitionManagementProcess = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
			        //Collection facilityCollection = adHocInventoryReportProcess.getFacilityAreaBldgRm(user.getPersonnelIdBigDecimal(),user.getCompanyId());
					Collection facilityCollection = cabinetDefinitionManagementProcess.getFacilityAreaBldgRm(user);
			        user.setFacilityAreaBuildingFloorRoomColl(facilityCollection);
			        request.setAttribute("facAppReportViewBeanCollection", facilityCollection);
					doSearch(dynaForm, user);
				}
			}
		}
		return next;
	}
}
