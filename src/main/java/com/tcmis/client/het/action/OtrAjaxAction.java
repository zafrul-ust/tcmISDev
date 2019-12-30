package com.tcmis.client.het.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.het.beans.HetUsageBean;
import com.tcmis.client.het.process.ContainerEntryProcess;
import com.tcmis.client.het.process.UsageLoggingProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

public class OtrAjaxAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "containervalidationmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else if (form != null) {
			HetUsageBean input = new HetUsageBean(form, getTcmISLocale());
			ContainerEntryProcess process = new ContainerEntryProcess(getDbUser(), getTcmISLocaleString(request));
			request.setAttribute("OTR", process.isContainerOTR(input) ? "Y" : "N");

		}
		return next;
	}

}
