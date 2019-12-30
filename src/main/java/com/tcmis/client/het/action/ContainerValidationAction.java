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

public class ContainerValidationAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "containervalidationmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else {
			String action = "" + request.getParameter("uAction");
			if (action.equalsIgnoreCase("containerInentoriedDupCheck")) {
				ContainerEntryProcess containerEntryProcess = new ContainerEntryProcess(getDbUser(request), getTcmISLocaleString(request));
				String containerId = request.getParameter("containerId");
				String receiptId = containerId.substring(0, containerId.lastIndexOf('-'));
				String itemId = request.getParameter("itemId");
				String inventoryResult = containerEntryProcess.validateContainerInventoried(receiptId, containerId, itemId);
				if (inventoryResult != null) {
					request.setAttribute("isInventoriedContainer", true);
					if (inventoryResult.equalsIgnoreCase("container")) {
						request.setAttribute("receiptId", "");
						request.setAttribute("containerId", containerId);
					}
					else {
						request.setAttribute("receiptId", receiptId);
						request.setAttribute("containerId", "");
					}
					request.setAttribute("itemId", itemId);
				}
				else
					request.setAttribute("isInventoriedContainer", false);
			}
			else if (form != null) {
				HetUsageBean input = new HetUsageBean(form, getTcmISLocale());
				UsageLoggingProcess process = new UsageLoggingProcess(getDbUser(), getTcmISLocale());
				request.setAttribute("container", process.validateContainer(input));
			}
		}

		return next;
	}

}
