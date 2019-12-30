package com.tcmis.client.het.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.het.beans.HetContainerEntryInputBean;
import com.tcmis.client.het.beans.HetContainerEntryViewBean;
import com.tcmis.client.het.process.ContainerEntryProcess;
import com.tcmis.client.het.process.HetContainerInventoryProcess;
import com.tcmis.client.het.process.PermitManagementProcess;
import com.tcmis.client.het.process.ShipmentReceivingProcess;
import com.tcmis.client.het.process.UsageLoggingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;

public class ContainerEntryAction extends TcmISBaseAction {
	private void doSearch(ContainerEntryProcess containerEntryProcess, PersonnelBean user, HetContainerEntryInputBean input) throws BaseException, Exception {
		request.setAttribute("hetContainerEntryColl", containerEntryProcess.getSearchResults(input, user));
		request.setAttribute("input", input);
		UsageLoggingProcess usageLoggingProcess = new UsageLoggingProcess(getDbUser(), getTcmISLocale());
		// Get VV values for pulldowns
		input.setPermits(new PermitManagementProcess(getDbUser()).getActivePermits(user.getCompanyId(), input.getFacilityId(), input.getWorkArea()));
		input.setSubstrates(usageLoggingProcess.getSubstrates(user.getCompanyId(), input.getFacilityId(), input.getWorkArea()));
		input.setApplicationMethods(usageLoggingProcess.getApplicationMethods(user.getCompanyId(), input.getFacilityId(), input.getWorkArea()));
		input.setCompanyId(user.getCompanyId());
		request.setAttribute("unitsOfMeasure", new HetContainerInventoryProcess(getDbUser(), getTcmISLocale()).getUOMs());
		request.setAttribute("containerType", new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request)).getVvUnidocsStorageContainerBeanColl());
		request.setAttribute("overrideUsageLogging", containerEntryProcess.isWorkAreaUsageLoggingOverride(input));
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "hetcontainerentrymain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		// If you need access to who is logged in
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		ContainerEntryProcess containerEntryProcess = new ContainerEntryProcess(getDbUser(request), getTcmISLocaleString(request));;

		/* Need to check if the user has permissions to view this page. if they do not have the permission
			  we need to not show them the page.*/
		/*if (!user.getPermissionBean().hasUserPagePermission("openPos")) {
			return (mapping.findForward("nopermissions"));
		}*/
		HetContainerEntryInputBean input = new HetContainerEntryInputBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));
		if (input.isSearch()) {
			doSearch(containerEntryProcess, user, input);
			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		}
		else if (input.isUpdate()) {

			// If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);

			// get the data from grid
			Collection beans = BeanHandler.getBeans((DynaBean) form, "hetContainerEntryViewBean", new HetContainerEntryViewBean(), getTcmISLocale(request));
			Vector retColl = containerEntryProcess.update(beans, user, input);

			if (!retColl.isEmpty() && retColl.firstElement().getClass().isInstance(new String()))
				request.setAttribute("tcmISErrors", retColl);
			else if (!retColl.isEmpty()) {
				request.setAttribute("updateSuccess", "Y");
				request.setAttribute("insertColl", retColl);
			}

			// After update the data, we do the re-search to refresh the window
			doSearch(containerEntryProcess, user, input);

			return (mapping.findForward("success"));
		}
		else {
			if (user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection() == null || user.getFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection().isEmpty()) {
				ShipmentReceivingProcess shipProcess = new ShipmentReceivingProcess(getDbUser(), getTcmISLocale());

				user.setFullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection(shipProcess.getUserFacilityWorkAreaGroupWorkAreaCollection(user.getPersonnelId()));
			}

			//get default facilityId
			if (StringHandler.isBlankString(user.getMyDefaultFacilityId())) {
				OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
				user.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(user.getPersonnelIdBigDecimal(), user.getCompanyId()));
			}
			return (mapping.findForward("success"));
		}
	}
}