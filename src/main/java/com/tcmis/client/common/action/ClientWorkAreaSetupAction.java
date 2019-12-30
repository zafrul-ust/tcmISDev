package com.tcmis.client.common.action;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.FacLocAppBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.beans.FacLocAppViewBean;
import com.tcmis.client.common.beans.FacilityDockBean;
import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;
import com.tcmis.client.common.beans.WorkAreaSetupInputBean;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.common.process.ClientWorkAreaSetupProcess;
import com.tcmis.client.report.process.AdHocInventoryReportProcess;
import com.tcmis.common.admin.beans.InventoryGroupDefinitionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

public class ClientWorkAreaSetupAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, ClientWorkAreaSetupProcess process, WorkAreaSearchTemplateInputBean input, PersonnelBean personnelBean) throws BaseException {
		Collection<FacLocAppBean> results = process.getWorkAreaSearchResults(input);
		request.setAttribute("workAreaCollection", results);

		// Get AccountSys info for facility
		CatalogProcess catalogProcess = new CatalogProcess(getDbUser(), getTcmISLocaleString(request));

		setDisplayFlags(catalogProcess, process, results, input,personnelBean);

		// Get Docks for Facility
		Collection<FacilityDockBean> docks = process.getDocksForFacility(input);
		input.setFacilityDocks(docks);

		// Get Inventory Groups for Facility
		Collection<InventoryGroupDefinitionBean> groups = process.getInventoryGroupsForFacility(input);
		input.setFacilityInventoryGroups(groups);

		// Get the default values used for new work area groups
		FacLocAppBean defaultValues;
		if (results.isEmpty()) {
			defaultValues = new FacLocAppBean();
			defaultValues.setFacilityId(input.getFacilityId());
			defaultValues.setLocationId(docks.isEmpty() ? "" : docks.iterator().next().getDockLocationId());
			defaultValues.setInventoryGroup(groups.isEmpty() ? "" : groups.iterator().next().getInventoryGroup());
			defaultValues.setReportingEntityId(input.getReportingEntityId());
		}
		else {
			defaultValues = results.iterator().next();
		}
		input.setDefaultValues(defaultValues);
	}

	private void setDisplayFlags(CatalogProcess catalogProcess, ClientWorkAreaSetupProcess process, Collection results, WorkAreaSearchTemplateInputBean input, PersonnelBean personnelBean) throws BaseException {
		input.setTotalLines(results.size());
		request.setAttribute("input", input);

		CatalogInputBean tmpBean = new CatalogInputBean();
		tmpBean.setFacilityId(input.getFacilityId());
		request.setAttribute("prRulesViewCollection", catalogProcess.getPrRulesForFacility(tmpBean));

		// If UseCodeRequired for Facility
		if (!results.isEmpty() && ((Vector) results).firstElement() != null) {
			Object bean = ((Vector) results).firstElement();
			if (bean.getClass() == FacLocAppBean.class)
				request.setAttribute("isUseCodeRequired", ((FacLocAppBean) bean).isUseCodeRequired());
			else
				request.setAttribute("isUseCodeRequired", ((FacLocAppViewBean) bean).isUseCodeRequired());
		}

		if (personnelBean.isFeatureReleased("HmrbTab",input.getFacilityId(),input.getCompanyId())) {
			request.setAttribute("showHmrbFeatures", Boolean.TRUE);
			//If show AllowSplitKits Column
			request.setAttribute("showAllowSplitKits", catalogProcess.isAllowSplitKits(input.getFacilityId()));
		}
		else {
			request.setAttribute("showHmrbFeatures", Boolean.FALSE);
			request.setAttribute("showAllowSplitKits", Boolean.FALSE);
		}

        request.setAttribute("showChargeTypeDefault",personnelBean.isFeatureReleased("ShowChargeTypeDefault",input.getFacilityId(),input.getCompanyId())?"Y":"N");
		request.setAttribute("compassPoints", process.getCompassPoints());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientcabinetsetupmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
			return next;
		}
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
	     if (!user.getPermissionBean().hasUserPagePermission("clientCabinetDefinition"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }


		if (form != null) {

			WorkAreaSearchTemplateInputBean input = new WorkAreaSearchTemplateInputBean(form, getTcmISLocale());
			request.setAttribute("input", input);

			ClientWorkAreaSetupProcess process = new ClientWorkAreaSetupProcess(getDbUser(), getTcmISLocale());
			if (input.isPopDept()) {
				request.setAttribute("deptCollection", process.getDept(request.getParameter("facilityId")));
				return mapping.findForward("setDept");
			}
			else if (input.isHistorySearch()) {
				request.setAttribute("startSearchTime", new Date().getTime());
				Collection results = process.getSearchHistory(input, user);
				request.setAttribute("workAreaCollection", results);
				setDisplayFlags(new CatalogProcess(getDbUser(), getTcmISLocaleString(request)), process, results, input,user);
				request.setAttribute("endSearchTime", new Date().getTime());
				return (mapping.findForward("history"));
			}
			else if (input.isUpdate()) {
				checkToken(request);

				Collection updatedCabinets = BeanHandler.getBeans((DynaBean) form, "resultGridDiv", new WorkAreaSetupInputBean(), "touched");
				String msg = process.updateWorkAreaSetup(input, updatedCabinets, user);

				if (!StringHandler.isBlankString(msg)) {
					Vector<String> err = new Vector<String>();
					err.add(msg);
					request.setAttribute("tcmISErrors", err);
				}
				else {//comment
					request.setAttribute("updateSuccess", "Y");
				}
				doSearch(request, process, input, user);
			}
			else if (input.isCreateExcel()) {
				try {
					setExcel(response, "ClientCabinetSetup");
					process.getExcelReport(input, (java.util.Locale) request.getSession().getAttribute("tcmISLocale"),user).write(response.getOutputStream());
					next = noForward;
				}
				catch (Exception ex) {
					ex.printStackTrace();
					next = mapping.findForward("genericerrorpage");
				}
			}
			else if (input.isSearch()) {
				doSearch(request, process, input, user);
				saveTcmISToken(request);
			}
			else {
				//populate drop downs
				if(user != null)
				{
			        if (user.getFacilityAreaBuildingFloorRoomColl() == null || user.getFacilityAreaBuildingFloorRoomColl().size() == 0) {
                        CabinetDefinitionManagementProcess cabinetDefinitionManagementProcess = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
                        Collection facilityCollection = cabinetDefinitionManagementProcess.getFacilityAreaBldgRm(user);
			            user.setFacilityAreaBuildingFloorRoomColl(facilityCollection);
			            request.setAttribute("facAppReportViewBeanCollection", facilityCollection);
			        }else {
			            request.setAttribute("facAppReportViewBeanCollection",user.getFacilityAreaBuildingFloorRoomColl());
			        }
	
					//get default facilityId
					if(user.getMyCompanyDefaultFacilityIdCollection() == null || user.getMyCompanyDefaultFacilityIdCollection().size() == 0) {
						OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
						user.setMyCompanyDefaultFacilityIdCollection(orderTrackingProcess.getMyCompanpyDefaultFacility(user.getPersonnelId()));
					}
				}
			}
		}

		return next;
	}

}