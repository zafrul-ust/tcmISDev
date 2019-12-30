package com.tcmis.internal.catalog.action;

import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.internal.catalog.beans.CatalogAddRequestVendorBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.MsdsIndexingBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.CatalogAddQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddVendorQueueInputBean;
import com.tcmis.internal.catalog.process.CatalogAddQcProcess;
import com.tcmis.internal.catalog.process.CatalogAddVendorQueueProcess;

public class CatalogAddVendorQueueAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, CatalogAddVendorQueueProcess process, CatalogAddVendorQueueInputBean input) throws BaseException {
		Collection results = process.getRequests(input);
		request.setAttribute("resultsCollection", results);
		input.setTotalLines(results.size());
		saveTcmISToken(request);
	}


	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		/*if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "catalogaddvendorqueuemain");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			return (mapping.findForward("login"));
		}*/

		// Get the user
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		// Verify view permissions from customer.user_page
		if ( ! (user.getPermissionBean().hasUserPagePermission("catAddVendorQueue") ||
				user.getPermissionBean().hasUserPagePermission("catalogAddProcess"))) {
			return (mapping.findForward("nopermissions"));
		}
		Locale locale = getTcmISLocale();

        // Get the user/page input in a usable form as an input bean
		CatalogAddVendorQueueInputBean input = new CatalogAddVendorQueueInputBean(form, locale);
		CatalogAddVendorQueueProcess process = new CatalogAddVendorQueueProcess(user.getPersonnelIdBigDecimal(), getDbUser());
		process.initDataMapper(this.getProperty(mapping, "dataMapper"), this.getContextParam("tcmis.sim.datastore"));

		request.setAttribute("calledFrom", input.getCalledFrom());
		if (input.isSearch()) { 
            // Stick the bean back in the session for the hidden field tag
		    request.setAttribute("inputBean", input);
            request.setAttribute("catalogUsers", process.getCatalogUsers(input));
			request.setAttribute("catalogVendors", new Vector(0));
            doSearch(request, process, input);
		}else if (input.isCreateExcel()) {
			try {
				setExcel(response, "Catalog Add Vendor Queue");
				process.getExcelReport(input, locale).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}else if (input.isUpdate()) {
			checkToken(request);
			this.overrideMaxData(request);	//don't apply max data for rest of codes below
			// Grab the input rows that have the OK box checked
			Collection<CatalogAddRequestVendorBean> rows = BeanHandler.getGridBeans((DynaBean) form, "CatalogAddVendorQueueInputBean", new CatalogAddRequestVendorBean(), locale, "grabbed");
			request.setAttribute("catalogVendors", new Vector(0));
			CatalogAddQcProcess catalogAddQcProcess = new CatalogAddQcProcess(getDbUser(), locale);
			if (input.isWescoApproval()) {
				process.releaseWorkQueueItems(user, input, rows);
			}else {
				process.updateAssignedTo(input, rows);
				process.updateVendorWorkQueueItems(user,rows);
				request.setAttribute("catalogVendors", catalogAddQcProcess.getCatalogVendor());
				request.setAttribute("userSupplier", process.getUserSupplier(user));
			}
			//reload data
			request.setAttribute("inputBean", input);
			if (input.isWescoTask()) {
				CatalogAddQcInputBean catAddQcInput = new CatalogAddQcInputBean(form, locale);
				request.setAttribute("catalogUsers", catalogAddQcProcess.getCatalogUsers(catAddQcInput));
			}
			else {
				request.setAttribute("catalogUsers", process.getCatalogUsers(input));
			}
			doSearch(request, process, input);
		}else if (input.isApproveAction()) {
			Collection<CatalogAddRequestVendorBean> rows = BeanHandler.getGridBeans((DynaBean) form, "CatalogAddVendorQueueInputBean", new CatalogAddRequestVendorBean(), locale, "grabbed");
			
			process.approveRows(rows, user);
			request.setAttribute("inputBean", input);
			request.setAttribute("catalogUsers", process.getCatalogUsers(input));
			doSearch(request, process, input);
		}else {
            //load initial data
		    request.setAttribute("catalogUsers", process.getCatalogUsers(input));
			request.setAttribute("taskList", process.getCatalogQueueTasks());
			request.setAttribute("statusList", process.getCatalogQueueStatuses());
        }
		return mapping.findForward("success");
	}
}