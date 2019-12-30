package com.tcmis.client.samsung.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.tcmis.client.samsung.beans.TmsOrderManagementBean;
import com.tcmis.client.samsung.process.TmsOrderManagementProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

import java.util.Collection;

/**
 * ***************************************************************************
 * Controller for TMS Order Management
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class TmsOrderManagementAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "tmsordermanagementmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("tmsOrderManagement"))
			return (mapping.findForward("nopermissions"));
		//if form is not null we have to perform some action
		TmsOrderManagementProcess process = new TmsOrderManagementProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
		if (form != null) {
			//copy date from dyna form to the data form
			TmsOrderManagementBean inputBean = new TmsOrderManagementBean(form,getTcmISLocale(request));
			if (inputBean.isSearch()) {
				this.saveTcmISToken(request);
				getSearchData(request,process,inputBean);
			} else if (inputBean.isCreateExcel()) {
				this.setExcel(response, "TMS Order Management");
				process.createExcelReport(inputBean).write(response.getOutputStream());
				return noForward;
			}else if (inputBean.isUpdate() || inputBean.isUpdateAndReprocess()) {
				this.saveTcmISToken(request);
				Collection beans = BeanHandler.getBeans((DynaBean) form, "orderManagement", new TmsOrderManagementBean(), this.getTcmISLocale(request));
				String errorMsg = process.updateData(inputBean,beans,personnelBean);
				if (!StringHandler.isBlankString(errorMsg)) {
					request.setAttribute("tcmISError", errorMsg);
				}
				getSearchData(request,process,inputBean);
			}else if (inputBean.isCancelReservation() || inputBean.isCancelLineReservation()) {
				this.saveTcmISToken(request);
				String errorMsg = process.cancelReservation(inputBean,personnelBean);
				if (!StringHandler.isBlankString(errorMsg)) {
					request.setAttribute("tcmISError", errorMsg);
				}
				getSearchData(request,process,inputBean);
			}else {
				//load initial drop down data
				inputBean.setCompanyId("SAMSUNG");
				request.setAttribute("facilityCollection",process.getFacilityDropDown(inputBean));
			}
		}
		return mapping.findForward("success");
	} //end of method

	private void getSearchData(HttpServletRequest request, TmsOrderManagementProcess process, TmsOrderManagementBean inputBean) {
		try {
			request.setAttribute("orderManagementCollection", process.getSearchResult(inputBean));
			request.setAttribute("applicationCollection", process.getFacilityApplicationDropDown(inputBean));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}  //end of class

/*
CommonResource:

label.reservationnumber=Reservation No
label.orderedbetween=Ordered Between
label.dateordered=Date Ordered
label.updateandreprocess=Update and Reprocess

 */