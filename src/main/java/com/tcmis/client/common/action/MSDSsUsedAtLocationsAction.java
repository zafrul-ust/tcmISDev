package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;
import com.tcmis.client.common.process.MSDSsUsedAtLocationsProcess;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.report.process.AdHocInventoryReportProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.DropDownListBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;


public class MSDSsUsedAtLocationsAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("success");

		//Login
		if (!isLoggedIn(request)) {
			// Save this page for returning after logging in
			request.setAttribute("requestedPage", "msdssusedatlocationsmain");
			//	Save any parameters passed in the URL, so that they can be accessed after the login
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			forward = mapping.findForward("login");
		}
		else {
			PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");

			// Need to have the permission from the database, normally it's the pageName instead of "mrRelease"
			/*if (!user.getPermissionBean().hasUserPagePermission("mrRelease")) 
			{
				forward = mapping.findForward("nopermissions");
			}
			else */
			{
				 MSDSsUsedAtLocationsProcess process = new  MSDSsUsedAtLocationsProcess(getDbUser(request), getTcmISLocale());
				WorkAreaSearchTemplateInputBean input = new WorkAreaSearchTemplateInputBean(form, getTcmISLocale());

				// Search
				if (input.isSearch()) {
					request.setAttribute("searchResults", process.getSearchResults(input));
					saveTcmISToken(request);
				}
				else if (input.isCreateExcel()) {
					setExcel(response, "MSDSsUsedAtLocationsExcel");
                    this.overrideMaxData(request);
                    process.getExcelReport(process.getSearchResults(input), getTcmISLocale()).write(response.getOutputStream());
					forward = noForward;
				}
				else {
			        if (personnelBean.getFacilityAreaBuildingFloorRoomColl() == null || personnelBean.getFacilityAreaBuildingFloorRoomColl().size() == 0) {
			            CabinetDefinitionManagementProcess cabinetDefinitionManagementProcess = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
	                    Collection facilityCollection = cabinetDefinitionManagementProcess.getFacilityAreaBldgRm(personnelBean);
	                    personnelBean.setFacilityAreaBuildingFloorRoomColl(facilityCollection);
			            request.setAttribute("facAppReportViewBeanCollection", facilityCollection);
			        }else {
			            request.setAttribute("facAppReportViewBeanCollection",personnelBean.getFacilityAreaBuildingFloorRoomColl());
			        }

                    //get default facilityId
					if (StringHandler.isBlankString(personnelBean.getMyDefaultFacilityId())) {
						OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(getDbUser(request), getTcmISLocaleString(request));
						personnelBean.setMyDefaultFacilityId(orderTrackingProcess.getMyDefaultFacility(personnelBean.getPersonnelIdBigDecimal(), personnelBean.getCompanyId()));
					}
			        
					return mapping.findForward("success");
				}
			}
		}

		return forward;

	}

}
