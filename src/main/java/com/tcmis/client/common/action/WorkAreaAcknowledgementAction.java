package com.tcmis.client.common.action;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.beans.WorkAreaAcknowledgementBean;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.common.process.WorkAreaAcknowledgementProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;
import com.tcmis.internal.hub.beans.ManualCabinetCountInputBean;

public class WorkAreaAcknowledgementAction extends TcmISBaseAction {


	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, WorkAreaAcknowledgementProcess process, ManualCabinetCountInputBean input) throws BaseException {
		try
		{
			Collection results = process.getSearchData(input);
			request.setAttribute("cabinetsCollection", results);
		    input.setTotalLines(results.size());
		}
		catch(Exception e)
		{
			request.setAttribute("tcmISError",new ResourceLibrary("com.tcmis.common.resources.CommonResources",  getTcmISLocale()).getString("error.db.select"));	
		}

	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "main");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else {

			PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			if (false && !user.getPermissionBean().hasUserPagePermission("cabinetCount")) {
				next = mapping.findForward("nopermissions");
			}
			else {

				ManualCabinetCountInputBean input = new ManualCabinetCountInputBean(form, getTcmISLocale());
				request.setAttribute("input", input);

				WorkAreaAcknowledgementProcess process = new WorkAreaAcknowledgementProcess(this.getDbUser(request));
				if (input.isUpdate()) {
					checkToken(request);
					Collection beans = BeanHandler.getBeans((DynaBean) form, "cabinetBinItemBean", new WorkAreaAcknowledgementBean(),this.getTcmISLocale(request), "okDoUpdate");
					
					Collection msg = process.update(beans,user.getPersonnelIdBigDecimal(),getTcmISLocale());
					
					if (msg != null) {
						request.setAttribute("tcmISErrors", msg);
					}
					else {
						request.setAttribute("updateSuccess", "Y");
					}
					doSearch(request, process, input);
				}
				else if (input.isCreateExcel()) {
					try {
						this.setExcel(response, "WorkAreaAck");
						process.getExcelReport(input, user.getLocale()).write(response.getOutputStream());
						next = noForward;
					}
					catch (Exception ex) {
						ex.printStackTrace();
						next = mapping.findForward("genericerrorpage");
					}
				}
				else if (input.isSearch()) {
					doSearch(request, process, input);
					this.saveTcmISToken(request);
				}
				else {
					//populate drop downs
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