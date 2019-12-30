package com.tcmis.client.het.action;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.het.beans.FxMixtureIdMgmtSearchBean;
import com.tcmis.client.het.beans.PermitManagementInputBean;
import com.tcmis.client.het.process.MixturePermissionProcess;
import com.tcmis.client.het.process.SubstrateManagementProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

public class MixturePermissionAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, MixturePermissionProcess process, PermitManagementInputBean input, PersonnelBean user) throws BaseException {
		Collection<FxMixtureIdMgmtSearchBean> results = process.getSearchResult(input, user);
		input.setTotalLines(results.size());
		request.setAttribute("substrates", results);
		request.setAttribute("input", input);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "mixturepermissionmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else if (form != null) {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			SubstrateManagementProcess sProcess = new SubstrateManagementProcess(getDbUser(), getTcmISLocale());
			MixturePermissionProcess process = new MixturePermissionProcess(getDbUser(), getTcmISLocale());
			PermitManagementInputBean input = new PermitManagementInputBean(form, getTcmISLocale());

			if (input.isSearchForAreas()) {
				request.setAttribute("startSearchTime", new Date().getTime());
				request.setAttribute("areas", sProcess.getAreas(user, input.getFacilityId()));
				request.setAttribute("endSearchTime", new Date().getTime());
			}
			else if (input.isSearchForBuildings()) {
				request.setAttribute("startSearchTime", new Date().getTime());
				request.setAttribute("buildings", sProcess.getBuildingsForArea(user.getCompanyId(), input.getAreaId()));
				request.setAttribute("endSearchTime", new Date().getTime());
			}
			else if (input.isSearchForWorkAreas()) {
				request.setAttribute("startSearchTime", new Date().getTime());
				request.setAttribute("workAreas", sProcess.getWorkAreasForBuilding(user.getCompanyId(), input.getFacilityId(), input.getBuildingId()));
				request.setAttribute("endSearchTime", new Date().getTime());
			}
			else {
				// Search
				if (input.isSearch()) {
					doSearch(request, process, input, user);
					saveTcmISToken(request);
				}

				//  Update
				else if (input.isUpdate()) {
					checkToken(request);

					// Grab the input rows
					Collection<FxMixtureIdMgmtSearchBean> substrate = BeanHandler.getBeans((DynaBean) form, "mixturePermission", new FxMixtureIdMgmtSearchBean(), getTcmISLocale());

					String msg = process.update(substrate, user);

					if (!StringHandler.isBlankString(msg)) {
						request.setAttribute("tcmISError", msg);
					}
					else {
						request.setAttribute("updateSuccess", "Y");
					}
					doSearch(request, process, input, user);
				}
				else if (input.isCreateExcel()) {
					setExcel(response, "MixturePermissionExcel");
					process.getExcelReport(process.getSearchResult(input, user), getTcmISLocale()).write(response.getOutputStream());
					next = noForward;
				} else {
					// Set initial value
					request.setAttribute("facilities", sProcess.getFacilities(user));
					request.setAttribute("areas", sProcess.getAreas(user, null));
					request.setAttribute("buildings", sProcess.getBuildingsForArea(user.getCompanyId(), null));
					request.setAttribute("workAreas", sProcess.getWorkAreasForBuilding(user.getCompanyId(), null, null));
				}
			}

		}
		return next;
	}

}
