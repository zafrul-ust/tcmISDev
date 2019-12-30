package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.RequestUtils;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.common.beans.CabinetPutAwayBean;
import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.common.process.CabinetPutAwayProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.web.IHaasConstants;

public class CabinetPutAwayAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, CabinetPutAwayProcess process, WorkAreaSearchTemplateInputBean input) throws BaseException, Exception {
		Collection results = process.getSearchData(input);
		input.setTotalLines(results.size());
		request.setAttribute("searchResultBeanCollection", results);
	}
			
			
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientcabinetputawaymain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("cabinetPutAway"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }
		
	    WorkAreaSearchTemplateInputBean input = new WorkAreaSearchTemplateInputBean(form, getLocale(request));
	    CabinetPutAwayProcess process = new CabinetPutAwayProcess(getDbUser());

		if (input.isSearch()) {
			doSearch(request, process, input);
			this.saveTcmISToken(request);		
		}
		else if (input.isUpdate()) {
			checkToken(request);
			Collection beans = BeanHandler.getBeans((DynaBean) form, "cabinetPutAwayBean", new CabinetPutAwayBean(), getTcmISLocale(),"okDoUpdate");
			StringBuffer requestURL = request.getRequestURL();
			Collection msg = process.putAwayInventory(beans, personnelBean);

			if (msg != null && msg.size() > 0) {
				request.setAttribute("tcmISError", msg);
			}
			else {
				request.setAttribute("updateSuccess", "Y");
			}
			doSearch(request, process, input);
		}
		else if (input.isPrint()){			
			StringBuffer reportURL = RequestUtils.createServerStringBuffer(
					request.getScheme(), request.getServerName(),
					request.getServerPort());
			StringBuilder reportRequest = new StringBuilder(
					reportURL.toString());
			reportRequest.append(IHaasConstants.RPT_HAAS_REPORT_URI);
			reportRequest.append("?pr_shipment_id=");
			reportRequest.append(request.getParameter("shipmentIds"));
			reportRequest.append("&pr_filter_cond=");
			reportRequest.append(process.getPackingListFilterQuery(request.getParameter("shipmentApplicationIds"), request.getParameter("putAwayMethod")));
			reportRequest.append(IHaasConstants.RPT_PARAM_BEAN_NAME);
			reportRequest.append("CabinetPutAwayPLReportDef");
			reportRequest.append("&locale=");
			reportRequest.append(request.getLocale());
			//
			log.info("--> Redirect URL : " + reportRequest
					.toString());
			//
			response.sendRedirect(response.encodeRedirectURL(reportRequest
					.toString()));
			return null;
		}
		else if ("createExcel".equalsIgnoreCase(input.getuAction())) {
			try {
				this.setExcel(response, "CabinetPutAway");
                this.overrideMaxData(request);
                process.getCabinetPutAwayExcelReport(input, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
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
