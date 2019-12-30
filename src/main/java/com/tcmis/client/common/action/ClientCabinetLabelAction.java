package com.tcmis.client.common.action;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.report.process.AdHocInventoryReportProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetLabelInputBean;
import com.tcmis.internal.hub.process.CabinetBinLabelProcess;
import com.tcmis.internal.hub.process.CabinetLabelProcess;

/******************************************************************************
 * Controller for cabinet labels
 * @version 1.0
 ******************************************************************************/
public class ClientCabinetLabelAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientcabinetlabelmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("clientCabinetLabel"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }
		
		CabinetLabelInputBean input = new CabinetLabelInputBean(form, getLocale(request));
		request.setAttribute("cabinetLabelInputBean", input);

		if (input.isNoAction()) {
	        if (personnelBean.getFacilityAreaBuildingFloorRoomColl() == null || personnelBean.getFacilityAreaBuildingFloorRoomColl().size() == 0) {
	            CabinetDefinitionManagementProcess cabinetDefinitionManagementProcess = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
	            Collection facilityCollection = cabinetDefinitionManagementProcess.getFacilityAreaBldgRm(personnelBean);
	            personnelBean.setFacilityAreaBuildingFloorRoomColl(facilityCollection);
	            request.setAttribute("facAppReportViewBeanCollection", facilityCollection);
	        }else {
	            request.setAttribute("facAppReportViewBeanCollection",personnelBean.getFacilityAreaBuildingFloorRoomColl());
	        }
	        
			//get default facilityId
			if(personnelBean.getMyCompanyDefaultFacilityIdCollection() == null || personnelBean.getMyCompanyDefaultFacilityIdCollection().size() == 0) {
				OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
				personnelBean.setMyCompanyDefaultFacilityIdCollection(orderTrackingProcess.getMyCompanpyDefaultFacility(personnelBean.getPersonnelId()));
			}
			return (mapping.findForward("success"));
		}
		else if (input.isSearch()) {
			CabinetLabelProcess process = new CabinetLabelProcess(getDbUser());
			Collection results = process.getSearchData(input);
			input.setTotalLines(results.size());
			request.setAttribute("searchResultBeanCollection", results);
		}
		else if (input.isSearchBin()) {
			CabinetBinLabelProcess process = new CabinetBinLabelProcess(getDbUser());
			request.setAttribute("startSearchTime", new Date().getTime() );
			Collection results = process.getBinData(input);
			request.setAttribute("endSearchTime", new Date().getTime() );
			input.setTotalLines(results.size());
			request.setAttribute("searchResultBeanCollection", results);
		}else if (input.isCreateExcel()) {
			try {
				CabinetLabelProcess process = new CabinetLabelProcess(getDbUser());
				process.setCalledFromClientCabinet(true);
				this.setExcel(response, "ClientCabinetLabel");
				process.getExcelReport(input, personnelBean.getLocale()).write(response.getOutputStream());
				return noForward;
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
		}

		return (mapping.findForward("success"));
	}
}
