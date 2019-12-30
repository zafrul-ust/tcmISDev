package com.tcmis.internal.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.internal.catalog.beans.CatalogAddVendorQueueInputBean;
import com.tcmis.internal.catalog.process.CatalogAddQcProcess;
import com.tcmis.internal.catalog.process.CatalogAddVendorQueueProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

import java.util.Collection;
import java.util.Locale;

public class CatalogAddVendorQueueDetailsAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "catalogaddvendorqueuedetails");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			return (mapping.findForward("login"));
		}

		// Get the user
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		// Verify view permissions from customer.user_page
		if (!user.getPermissionBean().hasUserPagePermission("catalogAddProcess")) {
			return (mapping.findForward("nopermissions"));
		}

		Locale locale = getTcmISLocale();

		// Get the user/page input in a usable form as an input bean
		CatalogAddVendorQueueInputBean input = new CatalogAddVendorQueueInputBean(form, locale);
		if (input.isSearch()) {
			CatalogAddQcProcess process = new CatalogAddQcProcess(getDbUser(), locale);
			request.setAttribute("catalogVendors", process.getCatalogVendor());
			CatalogAddVendorQueueProcess vendorQueueProcess = new CatalogAddVendorQueueProcess(user.getPersonnelIdBigDecimal(), getDbUser());
			request.setAttribute("catalogUsers", vendorQueueProcess.getCatalogUsers(input));
			if (input.hasCatalogAddRequestId()) {
				input.setSearchField("requestId");
				input.setSearchMode("is");
				input.setSearchArgument(input.getCatalogAddRequestId().toString());
			}
			Collection results = vendorQueueProcess.getRequestDetails(input);
			request.setAttribute("resultsCollection", results);
			request.setAttribute("calledFrom", "workQueueDetails");
			input.setCalledFrom("workQueueDetails");
			input.setTotalLines(results.size());
			request.setAttribute("inputBean", input);
			saveTcmISToken(request);
			return mapping.findForward("workQueueResults");
		}else {
			return mapping.findForward("success");
		}
	}
}