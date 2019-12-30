package com.tcmis.client.common.action;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.CustomerMrTrackingInputBean;
import com.tcmis.client.common.process.CustomerMrTrackingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for cabinet labels
 * @version 1.0
 ******************************************************************************/
public class CustomerMrTrackingAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "customermrtrackingaction");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		CustomerMrTrackingInputBean input = new CustomerMrTrackingInputBean(form, getLocale(request));
		request.setAttribute("input", input);

		CustomerMrTrackingProcess process = new CustomerMrTrackingProcess(getDbUser(), getTcmISLocale());
		request.setAttribute("startSearchTime", new Date().getTime());
		Collection results = input.hasPrNumbers() ? process.getMrResults(input) : Collections.EMPTY_LIST;
		request.setAttribute("endSearchTime", new Date().getTime());
		input.setTotalLines(results.size());
		request.setAttribute("mrResultsCollection", results);
		request.setAttribute("allocationResultsCollection", input.hasPrNumbers() ? process.getAllocationResults(input) : Collections.EMPTY_LIST);

		return (mapping.findForward("success"));
	}
}
