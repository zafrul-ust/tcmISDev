package com.tcmis.internal.catalog.action;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.CatalogAddQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestPendingAssignBean;
import com.tcmis.internal.catalog.beans.CatalogAddVendorQueueInputBean;
import com.tcmis.internal.catalog.process.CatalogAddQcProcess;
import com.tcmis.internal.catalog.process.CatalogAddVendorQueueProcess;

public class CatalogAddQcAction extends TcmISBaseAction {

	private final String PENDING_ASSIGNMENT_APPROVAL_ROLE = "Pending Assignment";
	
	private void doSearch(HttpServletRequest request, CatalogAddQcProcess process, CatalogAddQcInputBean input, PersonnelBean user) throws BaseException {
		Collection<? extends BaseDataBean> results = null;
		if (input.isPendingAssignment()) {
			results = process.getPendingAssnRequests(input);
		}
		else {
			results = process.getRequests(input,user);
		}
		request.setAttribute("resultsCollection", results);
		input.setTotalLines(results.size());
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!isLoggedIn(request)) { 
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "catalogaddqcmain");
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
		CatalogAddQcInputBean input = new CatalogAddQcInputBean(form, locale);
		CatalogAddQcProcess process = new CatalogAddQcProcess(getDbUser(), locale);
		process.initDataMapper(this.getProperty(mapping, "dataMapper"), this.getContextParam("tcmis.sim.datastore"));

		String forwardTo = "success";
		if (input.isSearch()) {
			if (input.displayByWorkQueue()) {
				CatalogAddVendorQueueProcess vendorQueueProcess = new CatalogAddVendorQueueProcess(user.getPersonnelIdBigDecimal(), getDbUser());
				vendorQueueProcess.initDataMapper(this.getProperty(mapping, "dataMapper"), this.getContextParam("tcmis.sim.datastore"));
				CatalogAddVendorQueueInputBean vendorQueueInputBean = new CatalogAddVendorQueueInputBean(form , locale);
				if (vendorQueueInputBean.isWescoApproval())
					this.overrideMaxData(request);	//don't apply max data if search for work queue Pending approval
				Collection results = vendorQueueProcess.getFullRequest(vendorQueueInputBean);
				this.overrideMaxData(request);	//don't apply max data for rest of codes below
				request.setAttribute("catalogVendors", process.getCatalogVendor());
				if (vendorQueueInputBean.isWescoTask()) {
					request.setAttribute("catalogUsers", process.getCatalogUsers(input));
				}
				else {
					request.setAttribute("catalogUsers", vendorQueueProcess.getCatalogUsers(vendorQueueInputBean));
				}
				request.setAttribute("userSupplier", vendorQueueProcess.getUserSupplier(user));
				request.setAttribute("resultsCollection", results);
				request.setAttribute("calledFrom", "wescoApproval");
				vendorQueueInputBean.setCalledFrom("wescoApproval");
				vendorQueueInputBean.setTotalLines(results.size());
				// Stick the bean back in the session for the hidden field tag
				request.setAttribute("inputBean", vendorQueueInputBean);
				saveTcmISToken(request);
				forwardTo = "workQueueResults";
			}else if(input.isPendingAssignment()) {
				input.setApprovalRole(PENDING_ASSIGNMENT_APPROVAL_ROLE);
				request.setAttribute("inputBean", input);
				doSearch(request, process, input, user);
				forwardTo = "pendingAssignResults";
			}else {
				doSearch(request, process, input, user);
				this.overrideMaxData(request);	//don't apply max data for rest of codes below
				// Stick the bean back in the session for the hidden field tag
				request.setAttribute("inputBean", input);
				request.setAttribute("catalogUsers", process.getCatalogUsers(input));
			}
		}else if (input.isApproveAction() || input.isApproveWithholdAction()) {
			Collection<CatalogAddRequestPendingAssignBean> rows = BeanHandler.getGridBeans((DynaBean) form, "catAddPendingAssnViewBean", new CatalogAddRequestPendingAssignBean(), locale, "ok");
			String errorMsg = process.approvePendingAssignRequest(input, user, input.isApproveWithholdAction(), rows);
			if ( ! "OK".equals(errorMsg)) {
				request.setAttribute("tcmisError", errorMsg);
			}
			input.setApprovalRole(PENDING_ASSIGNMENT_APPROVAL_ROLE);
			request.setAttribute("inputBean", input);
			doSearch(request, process, input, user);
			forwardTo = "pendingAssignResults";
		}else if (input.isRejectAction()) {
			Collection<CatalogAddRequestPendingAssignBean> rows = BeanHandler.getGridBeans((DynaBean) form, "catAddPendingAssnViewBean", new CatalogAddRequestPendingAssignBean(), locale, "ok");
			process.rejectPendingAssignRequest(input, user, rows);
			input.setApprovalRole(PENDING_ASSIGNMENT_APPROVAL_ROLE);
			request.setAttribute("inputBean", input);
			doSearch(request, process, input, user);
			forwardTo = "pendingAssignResults";
		}else if (input.isCreateExcel()) {
			try {
				setExcel(response, "Catalog Add QC");
				this.overrideMaxData(request);
				if (input.displayByWorkQueue()) {
					CatalogAddVendorQueueProcess vendorQueueProcess = new CatalogAddVendorQueueProcess(user.getPersonnelIdBigDecimal(), getDbUser());
					CatalogAddVendorQueueInputBean vendorQueueInputBean = new CatalogAddVendorQueueInputBean(form , locale);
					vendorQueueProcess.getExcelReport(vendorQueueInputBean, locale).write(response.getOutputStream());
				}else {
					process.getExcelReport(input, locale).write(response.getOutputStream());
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}else if (input.isUpdate()) {
			checkToken(request);
			if (input.isPendingAssignment()) {
				// Grab the input rows that have the OK box checked
				Collection<CatalogAddRequestPendingAssignBean> rows = BeanHandler.getGridBeans((DynaBean) form, "catAddPendingAssnViewBean", new CatalogAddRequestPendingAssignBean(), locale, "ok");
				// Do the update
				try {
					process.updatePendingAssignRequest(rows);
				} catch(Exception e) {
					request.setAttribute("tcmisError", "Error updating request");
				}
				input.setApprovalRole(PENDING_ASSIGNMENT_APPROVAL_ROLE);
				request.setAttribute("inputBean", input);
				doSearch(request, process, input, user);
				forwardTo = "pendingAssignResults";
			}
			else {
				this.overrideMaxData(request);	//don't apply max data for rest of codes below
				// Grab the input rows that have the OK box checked
				Collection<CatalogAddQcInputBean> rows = BeanHandler.getGridBeans((DynaBean) form, "CatalogAddQcInputBean", input, locale, "updated");
				// Do the update
				request.setAttribute("tcmisError", process.updateData(input, rows, user));
				// Refresh the search
				request.setAttribute("inputBean", input);
				request.setAttribute("catalogUsers", process.getCatalogUsers(input));
				request.setAttribute("catalogVendors", process.getCatalogVendor());
				if (input.getMaxData() != null)
					TcmISBaseAction.setSessionAttribute("__MaxDataCount",input.getMaxData().toString());
				doSearch(request, process, input, user);
			}
		}else if (input.isAssignToSelfAction()){
			Collection<String> errorMessages = process.assignRequestToSelf(input, user);
			
			if (errorMessages.isEmpty()) {
				response.getWriter().println(user.getFullName());
			}
			else {
				response.getWriter().println("Error: " + errorMessages.stream().collect(Collectors.joining("; ")));
			}
			return noForward;
		} else if (input.isRejectionCommentsAction()) {
			forwardTo = "commentPopup";
		} else {
            //load initial data
			CatalogAddVendorQueueProcess vendorQueueProcess = new CatalogAddVendorQueueProcess(user.getPersonnelIdBigDecimal(), getDbUser());
            request.setAttribute("catalogCol", process.getCompanyCatalogs(user.getPersonnelId()));
		    request.setAttribute("catalogUsers", process.getCatalogUsers(input));
			request.setAttribute("catalogVendors", process.getCatalogVendor());
			request.setAttribute("taskStatuses", vendorQueueProcess.getCatalogQueueStatuses());
        }
		return mapping.findForward(forwardTo);
	}
}