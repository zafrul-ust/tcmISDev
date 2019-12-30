package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.TestRequestInputBean;
import com.tcmis.internal.hub.process.TestRequestSearchProcess;

public class TestRequestSearchAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward next = mapping.findForward("success");

		// Check that the user is logged in before continuing.
		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "testrequestsearchmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		// Need to have the permission from the database, normally it's the
		// pageName instead of "mrRelease"
		if (!user.getPermissionBean().hasUserPagePermission("marsRequestSearch")) {
			return (mapping.findForward("nopermissions"));
		}

		TestRequestInputBean input = new TestRequestInputBean();
		TestRequestSearchProcess process = new TestRequestSearchProcess(getDbUser(request));
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));

		if (input.isCreateExcel()) {
			try {
				this.setExcel(response, "TestRequestSearchResult");
				process.getExcelReport(input, user, user.getLocale()).write(response.getOutputStream());
				next = noForward;
			}
			catch (Exception ex) {
				ex.printStackTrace();
				next = mapping.findForward("genericerrorpage");
			}
		}
		else if (input.isSearch()) {
			Collection results = process.getSearchData(input, user);
			request.setAttribute("testRequestSearchResults", results);
			input.setTotalLines(results.size());
			this.saveTcmISToken(request);
		}
		else {
			// may need to populate drop downs appropriately for the logged in
			// user.
			if (!"Radian".equals(user.getCompanyId())) {
				request.setAttribute("UserFacilityCollection", process.getFacilitySelect(user.getPersonnelId()));
			} else {
				request.setAttribute("UserFacilityCollection", "");
			}
		}
		return next;
	}

}
