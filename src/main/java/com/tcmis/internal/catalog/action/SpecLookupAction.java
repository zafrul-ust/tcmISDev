package com.tcmis.internal.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.catalog.process.SpecLookupProcess;

public class SpecLookupAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("success");

		//Login
		if (!isLoggedIn(request)) {
			// Save this page for returning after logging in
			request.setAttribute("requestedPage", "speclookupmain");
			//	Save any parameters passed in the URL, so that they can be accessed after the login
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			forward = mapping.findForward("login");
		}
		else {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			if (!user.getPermissionBean().hasUserPagePermission("specLookup")) {
				forward = mapping.findForward("nopermissions");
			}
			else {
				SpecLookupProcess process = new SpecLookupProcess(getDbUser(request), getTcmISLocale()); 
				
				String action = request.getParameter("uAction");
				String searchArgument = request.getParameter("searchArgument");

				// Search
				if ("search".equals(action)) {
					request.setAttribute("searchResults", process.getSpecBeanCollection(user,searchArgument));
					saveTcmISToken(request);
				}
				else if ("createExcel".equals(action)) {
					setExcel(response, "DisplayExcel");
					process.getExcelReport(process.getSpecBeanCollection(user,searchArgument), getTcmISLocale()).write(response.getOutputStream());
					forward = noForward;
				}
			}
		}

		return forward;

	}

}
