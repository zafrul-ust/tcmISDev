package com.tcmis.client.het.action;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.het.beans.HetPermitBean;
import com.tcmis.client.het.beans.HetPermitIdForMgmtBean;
import com.tcmis.client.het.beans.HetPermitMgmtInputBean;
import com.tcmis.client.het.beans.PermitManagementInputBean;
import com.tcmis.client.het.process.PermitManagementProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

public class PermitManagementAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, PermitManagementProcess process, PermitManagementInputBean input, PersonnelBean user) throws BaseException {
		Collection<HetPermitBean> results = process.getSearchResult(input, user);
		input.setTotalLines(results.size());
		request.setAttribute("permits", results);
		request.setAttribute("input", input);
		request.setAttribute("areas", process.getAreas(user, input.getFacilityId()));
		request.setAttribute("buildings", process.getBuildings(user.getCompanyId(), input.getFacilityId(), null));	
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "permitmanagementmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else if (form != null) {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			PermitManagementProcess process = new PermitManagementProcess(getDbUser(), getTcmISLocale());
			PermitManagementInputBean input = new PermitManagementInputBean(form, getTcmISLocale());

			// Search
			if (input.isSearch()) {
				doSearch(request, process, input, user);
				saveTcmISToken(request);
			}
			// Update
			else if (input.isUpdate() || input.isDelete()) {
				checkToken(request);

				// Grab the input rows
				Collection<HetPermitBean> permits = BeanHandler.getBeans((DynaBean) form, "PermitManagement", new HetPermitBean(), getTcmISLocale(), "updated");
				
				String msg = input.isUpdate() ? process.updatePermits(permits, user) : process.deletePermits(permits, user) ;

				if (!StringHandler.isBlankString(msg)) {
					request.setAttribute("tcmISError", msg);
				}
				else {
					request.setAttribute("updateSuccess", "Y");
				}
				doSearch(request, process, input, user);
			}
			else if (input.isCreateExcel()) {
				setExcel(response, "PermitManagementExcel");
				process.getExcelReport(process.getSearchResult(input, user), getTcmISLocale()).write(response.getOutputStream());
				next = noForward;
			}
			else {
				request.setAttribute("facilities", process.getFacilities(user));
				request.setAttribute("areas", process.getAreas(user, null));
				request.setAttribute("buildings", process.getBuildings(user.getCompanyId(), null, null));
			}
		}

		return next;
	}

}
