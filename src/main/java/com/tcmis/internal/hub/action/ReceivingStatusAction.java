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
import com.tcmis.internal.hub.beans.ReceivingStatusInputBean;
import com.tcmis.internal.hub.beans.ReceivingStatusViewBean;
import com.tcmis.internal.hub.process.ReceivingStatusProcess;

public class ReceivingStatusAction extends TcmISBaseAction {

	private void doSearch(ReceivingStatusProcess process, ReceivingStatusInputBean input, PersonnelBean user) throws BaseException {
		Collection<ReceivingStatusViewBean> results = process.getReceivingStatus(input, user);
		request.setAttribute("searchResults", results);
		input.setTotalLines(results.size());
		request.setAttribute("input", input);
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("success");

		// Login
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "ReceivingStatusmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			forward = mapping.findForward("login");
		}
		else {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			// Need to have the permission from the database
			if (!user.getPermissionBean().hasUserPagePermission("receivingStatus")) {
				forward = mapping.findForward("nopermissions");
			}
			else {
				ReceivingStatusProcess process = new ReceivingStatusProcess(getDbUser(request), getTcmISLocale());
				ReceivingStatusInputBean input = new ReceivingStatusInputBean(form, getTcmISLocale());

				// Search
				if (input.isSearch()) {
					doSearch(process, input, user);
				}
				else if (input.isCreateExcel()) {
					setExcel(response, "DisplayExcel");
					process.getExcelReport(process.getReceivingStatus(input, user), getTcmISLocale()).write(response.getOutputStream());
					forward = noForward;
				}
				else if (input.isUserSearch()) {
					request.setAttribute("assignees", process.getReceivingQcPersonnelForHub(input));
					forward = mapping.findForward("ajax");
				}
				else if (input.isUpdate()) {
					Collection beans = BeanHandler.getBeans((DynaBean)form, "receivingStatusDisplay", new ReceivingStatusViewBean(), "updated");
					process.updateAssignees(beans);
					doSearch(process, input, user);
				}
			}
		}

		return forward;

	}

}
