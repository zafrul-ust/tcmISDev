package com.tcmis.client.het.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.het.beans.HetPermitBean;
import com.tcmis.client.het.beans.HetUsageBean;
import com.tcmis.client.het.beans.MonitorUsageInputBean;
import com.tcmis.client.het.process.MonitorUsageProcess;
import com.tcmis.client.het.process.PermitManagementProcess;
import com.tcmis.client.het.process.ShipmentReceivingProcess;
import com.tcmis.client.het.process.UsageLoggingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class MonitorUsageAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, MonitorUsageProcess process, MonitorUsageInputBean input, PersonnelBean user) throws BaseException {
		Collection<HetUsageBean> results = process.getSearchResult(input, user);
		request.setAttribute("hetUsageBeanColl", results);
		request.setAttribute("input", input);
		input.setTotalLines(results.size());
		// Get VV values for pulldowns
		UsageLoggingProcess usageLoggingProcess = new UsageLoggingProcess(getDbUser(), getTcmISLocale());
		BigDecimal workArea = input.hasApplicationId() ? new BigDecimal(input.getApplicationId()) : null;
		// Get VV values for pulldowns
		if (input.isContainerSearch() && !results.isEmpty()) {
			workArea = results.iterator().next().getApplicationId();
		}
		PermitManagementProcess permitProcess = new PermitManagementProcess(getDbUser());
		request.setAttribute("allPermits", permitProcess.getActivePermits(user.getCompanyId(), input.getFacilityId(), null));
		Collection<HetPermitBean> permits = permitProcess.getActivePermits(user.getCompanyId(), input.getFacilityId(), workArea);
		input.setPermits(permits);
		if (!permits.isEmpty()) {
			request.setAttribute("searchBuildingId", permits.iterator().next().getBuildingId());
		}
		input.setSubstrates(usageLoggingProcess.getSubstrates(user.getCompanyId(), input.getFacilityId(), workArea));
		input.setApplicationMethods(usageLoggingProcess.getApplicationMethods(user.getCompanyId(), input.getFacilityId(), workArea));
		input.setCompanyId(user.getCompanyId());

	}

	/**
	 * *************************************************************************
	 * ** Controller for
	 * 
	 * @version 1.0
	 *          *************************************************************
	 *          ***************
	 */
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "monitorusagemain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		// If you need access to who is logged in
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

		// copy date from dyna form to the input bean
		MonitorUsageInputBean input = new MonitorUsageInputBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));

		MonitorUsageProcess process = new MonitorUsageProcess(getDbUser(request), getTcmISLocaleString(request));

		Collection errorMsgs = null;
		// Search
		if (input.isSearch()) {
			// Pass the result collection in request
			doSearch(request, process, input, user);

			// save the token if update actions can be performed later.
			saveTcmISToken(request);
			return (mapping.findForward("success"));
		}
		if (input.isHistorySearch()) {
			request.setAttribute("startSearchTime", new Date().getTime());
			Collection results = process.getSearchHistory(input, user);
			request.setAttribute("endSearchTime", new Date().getTime());
			request.setAttribute("hetUsageBeanColl", results);
			request.setAttribute("input", input);
			return (mapping.findForward("history"));
		}
		// Update
		else if (input.isUpdate()) {

			/*
			 * Need to check if the user has permissions to update this data. if
			 * they do not have the permission we show a error message.
			 */
			/*
			 * if
			 * (!user.getPermissionBean().hasInventoryGroupPermission("BuyOrder"
			 * , null, null, null)) { request.setAttribute("tcmISError",
			 * getResourceBundleValue(request, "error.noaccesstopage")); //
			 * repopulate the search results
			 * request.setAttribute("hetUsageBeanColl",
			 * process.getSearchResult(input, user)); return
			 * (mapping.findForward("success")); }
			 */
			// If the page is being updated I check for a valid token.
			// checkToken will aslo save token for you to avoid duplicate form
			// submissions.
			checkToken(request);
			// get the data from grid
			Collection<HetUsageBean> beans = BeanHandler.getGridBeans((DynaBean) form, "HetUsageBean", new HetUsageBean(), getTcmISLocale(), "ok");
			errorMsgs = process.update(beans, user);
			if (errorMsgs != null) request.setAttribute("tcmISErrors", errorMsgs);
			// After update the data, we do the re-search to refresh the window
			doSearch(request, process, input, user);
			return (mapping.findForward("success"));
		}
		else if (input.isDelete()) {

			/*
			 * Need to check if the user has permissions to update this data. if
			 * they do not have the permission we show a error message.
			 */
			/*
			 * if
			 * (!user.getPermissionBean().hasInventoryGroupPermission("BuyOrder"
			 * , null, null, null)) { request.setAttribute("tcmISError",
			 * getResourceBundleValue(request, "error.noaccesstopage")); //
			 * repopulate the search results
			 * request.setAttribute("hetUsageBeanColl",
			 * process.getSearchResult(input, user)); return
			 * (mapping.findForward("success")); }
			 */

			// If the page is being updated I check for a valid token.
			// checkToken will aslo save token for you to avoid duplicate form
			// submissions.
			checkToken(request);

			// get the data from grid
			Collection<HetUsageBean> beans = BeanHandler.getBeans((DynaBean) form, "HetUsageBean", new HetUsageBean(), getTcmISLocale(request), "ok");
			errorMsgs = process.delete(beans, user);
			if (errorMsgs != null) request.setAttribute("tcmISErrors", errorMsgs);

			// After update the data, we do the re-search to refresh the window
			doSearch(request, process, input, user);
			return (mapping.findForward("success"));
		}
		else if (input.isCreateExcel()) {
			this.setExcel(response, "HETUsageReport");
			process.getExcelReport(process.getSearchResult(input, user), getTcmISLocale(request)).write(response.getOutputStream());
			return noForward;
		}
		else {
			if (user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection() == null || user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection().isEmpty()) {
				ShipmentReceivingProcess shipProcess = new ShipmentReceivingProcess(getDbUser(), getTcmISLocale());
				user.setFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection(shipProcess.getUserFacilityWorkAreaGroupWorkAreaCollection(user.getPersonnelId()));
			}
			return (mapping.findForward("success"));
		}
	}
}
