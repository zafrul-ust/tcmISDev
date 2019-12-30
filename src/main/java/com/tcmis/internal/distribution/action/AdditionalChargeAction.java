package com.tcmis.internal.distribution.action;

import java.util.Collection;
import java.util.Locale;

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
import com.tcmis.internal.distribution.beans.AdditionalChargeInputBean;
import com.tcmis.internal.distribution.beans.CatalogItemAddChargeBean;
import com.tcmis.internal.distribution.process.AdditionalChargeProcess;

public class AdditionalChargeAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "additionalchargemain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		// Get the user
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

		// Check view permissions
		if (!user.getPermissionBean().hasUserPagePermission("additionalChargeMain")) {
			return (mapping.findForward("nopermissions"));
		}

		if (form == null) {
			return (mapping.findForward("success"));
		}

		AdditionalChargeInputBean input = new AdditionalChargeInputBean(form);
		// Stick the bean back in the session so that the JSP can
		// retrieve hidden fields
		request.setAttribute("additionalChargeInputBean", input);

		if (input.isSearch()) {
			doSearch(request, input);
			saveTcmISToken(request);
		}
		else if (input.isCreateExcel()) {
			return doExcel(mapping, response, input);
		}
		// Or Update?
		else if (input.isUpdate()) {
			// If the page is being updated check for a valid token.
			// checkToken will also save token for you to avoid
			// duplicate form submissions.
			checkToken(request);

			// Check if the user has permissions to update
			if (user.getPermissionBean().hasOpsEntityPermission("additionalChargeMain", input.getOpsEntityId(), null)) {
				doUpdate((DynaBean) form, input, user);
			}
			else {
				request.setAttribute("tcmisError", getResourceBundleValue(request, "error.noaccesstopage"));
			}
			// Refresh the search
			doSearch(request, input);

		}

		return (mapping.findForward("success"));
	}

	private void doSearch(HttpServletRequest request, AdditionalChargeInputBean input) throws BaseException {
		AdditionalChargeProcess process = new AdditionalChargeProcess(getDbUser(), getTcmISLocale());
		Collection results = process.getSearchResults(input);
		request.setAttribute("CatalogItemAddChargeBeanCollection", results);
		input.setTotalLines(results.size());
	}

	private ActionForward doExcel(ActionMapping mapping, HttpServletResponse response, AdditionalChargeInputBean input) throws BaseException {
		AdditionalChargeProcess process = new AdditionalChargeProcess(getDbUser(), getTcmISLocale());
		try {
			setExcel(response, "Search Global Item");
			process.getItemExcelReport(input).write(response.getOutputStream());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return mapping.findForward("genericerrorpage");
		}
		return noForward;
	}

	private void doUpdate(DynaBean form, AdditionalChargeInputBean input, PersonnelBean user) throws BaseException, Exception {
		Locale locale = getTcmISLocale();
		AdditionalChargeProcess process = new AdditionalChargeProcess(getDbUser(), locale);
		// Grab the input rows
		Collection<CatalogItemAddChargeBean> rows = BeanHandler.getGridBeans(form, "additionalChargeResultsGrid", new CatalogItemAddChargeBean(), locale, "updated");

		// Do the update
		Collection errorMsgs = process.update(input, rows, user);
		// Stick any returned errors in the session for display
		request.setAttribute("tcmisError", errorMsgs);
	}
}
