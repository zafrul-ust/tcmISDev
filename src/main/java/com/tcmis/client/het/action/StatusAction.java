package com.tcmis.client.het.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.het.beans.HetStatus;
import com.tcmis.client.het.process.StatusProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

public class StatusAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "hetstatus");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		HetStatus input = new HetStatus(form, getTcmISLocale());
		StatusProcess process = new StatusProcess(getDbUser(), getTcmISLocale());

		if (input.isUpdate()) {
			process.initiateSynch();
		}

		Collection results = process.getStatusResults();
		request.setAttribute("results", results);
		input.setTotalLines(results.size());
		request.setAttribute("input", input);

		return (mapping.findForward("success"));
	}
}
