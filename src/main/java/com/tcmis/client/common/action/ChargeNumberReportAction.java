package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.ChargeNumberInputBean;
import com.tcmis.client.common.process.ChargeNumberReportProcess;
import com.tcmis.client.report.process.AdHocInventoryReportProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;

public class ChargeNumberReportAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("success");

		//Login
		if (!isLoggedIn(request)) {
			// Save this page for returning after logging in
			request.setAttribute("requestedPage", "chargenumberreportmain");
			//	Save any parameters passed in the URL, so that they can be accessed after the login
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			forward = mapping.findForward("login");
		}
		else {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			// Need to have the permission from the database, normally it's the pageName instead of "mrRelease"
			if (!user.getPermissionBean().hasUserPagePermission("chargeNumberReport")) {
				forward = mapping.findForward("nopermissions");
			}
			else {
				ChargeNumberReportProcess process = new ChargeNumberReportProcess(getDbUser(request), getTcmISLocale());
				ChargeNumberInputBean input = new ChargeNumberInputBean(form, getTcmISLocale());

				// Search
				if (input.isSearch()) {
					request.setAttribute("searchResults", process.getSearch(input, user));
					request.setAttribute("chargeColLabels", process.getChargeNumberLabels(input,user));
		
					saveTcmISToken(request);
				}
				else if (input.isCreateExcel()) {
					setExcel(response, "DisplayExcel");
					process.getExcelReport(input,user,process.getSearch(input, user), getTcmISLocale()).write(response.getOutputStream());
					forward = noForward;
				}
				else
				{
			        if (user.getFacilityAreaBuildingFloorRoomColl() == null || user.getFacilityAreaBuildingFloorRoomColl().size() == 0) {
						AdHocInventoryReportProcess adHocInventoryReportProcess = new AdHocInventoryReportProcess(this.getDbUser(request),getLocale(request));
			            Collection tmpColl = adHocInventoryReportProcess.getFacilityAreaBldgRm(user.getPersonnelIdBigDecimal(),user.getCompanyId());
			            user.setFacilityAreaBuildingFloorRoomColl(tmpColl);
			            user.getMyDefaultFacilityId();
			            request.setAttribute("facAppReportViewBeanCollection", tmpColl);
			        }else {
			            request.setAttribute("facAppReportViewBeanCollection",user.getFacilityAreaBuildingFloorRoomColl());
			        }
					
				}
			}
		}

		return forward;

	}

}
