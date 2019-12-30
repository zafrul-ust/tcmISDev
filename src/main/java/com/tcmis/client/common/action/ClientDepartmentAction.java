package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.DeptBean;
import com.tcmis.client.common.process.ClientDepartmentProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for Manufacturer Search page
 * @version 1.0
 ******************************************************************************/

public class ClientDepartmentAction extends TcmISBaseAction {

	private void doSearch(DynaBean dynaForm, PersonnelBean user) throws Exception {
		String companyId = (String) dynaForm.get("companyId");
		ClientDepartmentProcess process = new ClientDepartmentProcess(getDbUser());
		request.setAttribute("departments", process.getDepartments(companyId));
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "manageDepartments");
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
					Collection<DeptBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "department", new DeptBean(), getLocale(request), "updated");
					if (updatedRows != null && !updatedRows.isEmpty()) {
						ClientDepartmentProcess process = new ClientDepartmentProcess(getDbUser());
						for (DeptBean department : updatedRows) {
							if (department.isNewDepartment()) {
								if (department.hasDeptName()) {
									department.setCompanyId(user.getCompanyId());
									process.insertDepartment(department);
								}
							}
							else {
								process.updateDepartment(department);
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
