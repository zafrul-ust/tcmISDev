package com.tcmis.client.openCustomer.action;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.openCustomer.beans.OrderMsdsBean;
import com.tcmis.client.openCustomer.beans.OrderMsdsInputBean;
import com.tcmis.client.openCustomer.process.OpenUserCustomerProcess;
import com.tcmis.client.openCustomer.process.OrderMsdsProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

public class OrderMsdsAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "ordermsdsmain");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			return (mapping.findForward("login"));
		}

		// Get the user
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		Locale locale = getTcmISLocale();

		// Get the user/page input in a usable form as an input bean
		OrderMsdsInputBean input = new OrderMsdsInputBean(form, locale);
		// Stick the bean back in the session for the hidden field tag
		request.setAttribute("inputBean", input);


		// Search ?
		if (input.isSearch()) {
			OrderMsdsProcess process = new OrderMsdsProcess(getDbUser(), locale);
			Collection<OrderMsdsBean> results = process.getSearchResult(user, input);
			request.setAttribute("resultsCollection", results);
			input.setTotalLines(results.size());
		}
		else {
			// Retrieve the data for the search menus
			OpenUserCustomerProcess openCustomerProcess = new OpenUserCustomerProcess(getDbUser());
			request.setAttribute("userCustomerCollection", openCustomerProcess.getSearchResult(user));
		}

		return mapping.findForward("success");
	}
}
