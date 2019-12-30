package com.tcmis.client.het.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.het.beans.HetUsageBean;
import com.tcmis.client.het.beans.HetUsageLoggingViewBean;
import com.tcmis.client.het.beans.UsageLoggingInputBean;
import com.tcmis.client.het.process.HetContainerInventoryProcess;
import com.tcmis.client.het.process.PermitManagementProcess;
import com.tcmis.client.het.process.ShipmentReceivingProcess;
import com.tcmis.client.het.process.UsageLoggingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

public class UsageLoggingAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, UsageLoggingProcess process, UsageLoggingInputBean input, PersonnelBean user) throws BaseException {
		Collection<HetUsageLoggingViewBean> results = process.getSearchData(input, user);
		input.setTotalLines(results.size());
		request.setAttribute("loggingCollection", results);
		request.setAttribute("cartSearch", input.isCartSearch() ? Boolean.TRUE : Boolean.FALSE);

		// Get VV values for pulldowns
		PermitManagementProcess permitProcess = new PermitManagementProcess(getDbUser());
		if (!results.isEmpty() && results.iterator().next().isHetMultipleBuildingUsage()) {
			request.setAttribute("multiBuilding", Boolean.TRUE);
			request.setAttribute("allPermits", permitProcess.getActivePermits(user.getCompanyId(), input.getFacilityId(), null));
			request.setAttribute("areas", permitProcess.getAreas(user, input.getFacilityId()));
			request.setAttribute("buildings", permitProcess.getBuildings(user.getCompanyId(), input.getFacilityId(), null));
		}
		else {
			request.setAttribute("multiBuilding", Boolean.FALSE);
		}
		input.setPermits(permitProcess.getActivePermits(user.getCompanyId(), input.getFacilityId(), input.getWorkArea()));
		input.setSubstrates(process.getSubstrates(user.getCompanyId(), input.getFacilityId(), input.getWorkArea()));
		input.setApplicationMethods(process.getApplicationMethods(user.getCompanyId(), input.getFacilityId(), input.getWorkArea()));
		input.setCompanyId(user.getCompanyId());
		input.setWorkAreaAllowedSplitKits(process.isWorkAreaAllowedSplitKits(user.getCompanyId(), input.getFacilityId(), input.getWorkArea().toPlainString()));
		String buildingId = permitProcess.getBuildingIdForWorkArea(user.getCompanyId(), input.getFacilityId(), input.getWorkArea());
		if (StringHandler.isBlankString(buildingId)) {
			buildingId = "-999";
		}
		input.setBuildingId(buildingId);
		input.setAreaId(permitProcess.getAreaIdForBuilding(user.getCompanyId(), input.getFacilityId(), buildingId));
		request.setAttribute("unitsOfMeasure", new HetContainerInventoryProcess(getDbUser(), getTcmISLocale()).getUOMs());
		request.setAttribute("input", input);

	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "usageloggingmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else if (form != null) {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			UsageLoggingInputBean input = new UsageLoggingInputBean(form, getTcmISLocale());

			UsageLoggingProcess process = new UsageLoggingProcess(getDbUser(), getTcmISLocale());
			if (input.isUpdate()) {
				checkToken(request);

				// Grab the input rows
				Collection<HetUsageBean> rows = BeanHandler.getBeans((DynaBean) form, "usageLogging", new HetUsageBean(), getLocale(request), "updated");

				String msg = process.logUsage(input, rows, user);

				if (!StringHandler.isBlankString(msg)) {
					request.setAttribute("tcmISError", msg);
				}
				else {
					request.setAttribute("updateSuccess", "Y");
				}
				doSearch(request, process, input, user);
			}
			// else if (input.isCreateExcel()) {
			// try {
			// setExcel(response, "ClientCabinetSetup");
			// //process.getExcelReport(input, (java.util.Locale)
			// request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			// next = noForward;
			// }
			// catch (Exception ex) {
			// ex.printStackTrace();
			// next = mapping.findForward("genericerrorpage");
			// }
			// }
			else if (input.isSearch()) {
				doSearch(request, process, input, user);
				saveTcmISToken(request);
			}
			else if (input.isDoOtherContainerSearch()) {
				request.setAttribute("containers", process.getAlternateContainers(input, user));
				request.setAttribute("mixtureCount", "0");
			}
			else if (input.isDoSolventSearch()) {
				request.setAttribute("containers", process.getSolvents(input, user));
				request.setAttribute("mixtureCount", "0");
			}
			else if (input.isDoAdHocMixtureSearch()) {
				request.setAttribute("containers", process.getAdHocMixture(input, user));
				request.setAttribute("mixtureCount", "0");
			}
			else if (input.isDoAddContainerMixtureSearch()) {
				request.setAttribute("containers", process.getAddContainerMixture(input, user));
				request.setAttribute("mixtureCount", process.getMixtureMSDSCount(input));
			}
			// populate drop downs
			if (user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection() == null || user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection().isEmpty()) {
				ShipmentReceivingProcess shipProcess = new ShipmentReceivingProcess(getDbUser(), getTcmISLocale());
				user.setFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection(shipProcess.getUserFacilityWorkAreaGroupWorkAreaCollection(user.getPersonnelId()));
			}

			// get default facilityId
			if (StringHandler.isBlankString(user.getMyDefaultFacilityId())) {
				OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
				user.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(user.getPersonnelIdBigDecimal(), user.getCompanyId()));
			}
		}

		return next;
	}

}