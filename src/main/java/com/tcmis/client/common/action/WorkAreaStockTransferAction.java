package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.beans.WorkAreaStockTransferBean;
import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.common.process.WorkAreaStockTransferProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class WorkAreaStockTransferAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, WorkAreaStockTransferProcess process, WorkAreaSearchTemplateInputBean input, Collection workAreas) throws BaseException, Exception {
		Collection results = process.getSearchData(input, workAreas);
		input.setTotalLines(results.size());
		
		request.setAttribute("searchInputBeanCollection", workAreas);
		request.setAttribute("searchResultBeanCollection", results);
	}
			
			
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "workareastocktransfer");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
	     /*if (!personnelBean.getPermissionBean().hasUserPagePermission("workAreaStockTransfer"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }
		*/
	    WorkAreaSearchTemplateInputBean input = new WorkAreaSearchTemplateInputBean(form, getLocale(request));
	    Collection<WorkAreaSearchTemplateInputBean> workAreas = BeanHandler.getBeans((DynaBean) form, "input", new WorkAreaSearchTemplateInputBean(), getTcmISLocale());
		
	    WorkAreaStockTransferProcess process = new WorkAreaStockTransferProcess(getDbUser());

		if (input.isSearch()) {
			doSearch(request, process, input, workAreas);
			this.saveTcmISToken(request);		
		}
		else if (input.isUpdate()) {
			//checkToken(request);
			Collection beans = BeanHandler.getBeans((DynaBean) form, "workAreaStockTransferBean", new WorkAreaStockTransferBean(), getTcmISLocale(),"okDoUpdate");

			Collection msg = process.transferInventory(input,beans, personnelBean);

			if (msg != null && msg.size() > 0) {
				request.setAttribute("tcmISError", msg);
			}
			else {
				request.setAttribute("updateSuccess", "Y");
			}
			
			doSearch(request, process, input, workAreas);
		}
		else if ("createExcel".equalsIgnoreCase(input.getuAction())) {
			try {
				this.setExcel(response, "WorkAreaStockTransfer");
                this.overrideMaxData(request);
                process.getWorkAreaStockTransferExcelReport(input, workAreas, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			} catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}
		else{
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

		return (mapping.findForward("success"));
	}
}
