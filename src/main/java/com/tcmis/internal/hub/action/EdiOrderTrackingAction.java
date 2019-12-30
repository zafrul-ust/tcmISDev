package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.common.util.ResourceLibrary;

import java.io.File;

import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.EdiOrderTrackingBean;
import com.tcmis.internal.hub.beans.ReceivingDocumentReportBean;
import com.tcmis.internal.hub.process.*;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;

/**
 * ***************************************************************************
 * Controller for EDI Order Tracking
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class EdiOrderTrackingAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "ediordertrackingmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("ediOrderErrorsTracking"))
			return (mapping.findForward("nopermissions"));

		//if form is not null we have to perform some action
		EdiOrderTrackingProcess process = new EdiOrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
		if (form != null) {
			//copy date from dyna form to the data form
			EdiOrderTrackingBean inputBean = new EdiOrderTrackingBean(form,getTcmISLocale(request));
			if (inputBean.isSearch()) {
				this.saveTcmISToken(request);
				request.setAttribute("ediOrderTrackingCollection",process.getSearchResult(inputBean));
			} else if (inputBean.isCreateExcel()) {
				this.setExcel(response, "EDI Order Tracking");
				process.createExcelReport(inputBean).write(response.getOutputStream());
				return noForward;
			}else if (inputBean.isUpdate()) {
				Collection beans = BeanHandler.getBeans((DynaBean) form, "ediOrderTracking", new EdiOrderTrackingBean(), this.getTcmISLocale(request));
				String msg = process.updateData(beans);
				if (!StringHandler.isBlankString(msg)) {
					request.setAttribute("tcmISError", msg);
				} else {
					request.setAttribute("updateSuccess", "Y");
				}
				request.setAttribute("ediOrderTrackingCollection", process.getSearchResult(inputBean));
			}else if (inputBean.isOpenEditEdiError()) {
				request.setAttribute("shiptoCityBeanColl", process.getShiptoCity(personnelBean,inputBean));
				return mapping.findForward("openEditEdiError");
			}else if (inputBean.isEditEdiError()) {
				String errorMsg = process.updateEdiData(personnelBean,inputBean);
				if (!StringHandler.isBlankString(errorMsg)) {
					request.setAttribute("tcmISError", errorMsg);
				}
				request.setAttribute("shiptoCityBeanColl", process.getShiptoCity(personnelBean,inputBean));
				return mapping.findForward("openEditEdiError");
			}else if (inputBean.isCancelCatalogAddRequest()) {
				String msg = process.cancelCatalogAddRequest(personnelBean,inputBean);
				if (!StringHandler.isBlankString(msg)) {
					request.setAttribute("tcmISError", msg);
				} else {
					request.setAttribute("updateSuccess", "Y");
				}
				request.setAttribute("ediOrderTrackingCollection", process.getSearchResult(inputBean));
			}else {
				//load initial drop down data
				request.setAttribute("companyFacilityCollection",process.buildCompanyFacilityObj(personnelBean));
			}
		}
		return mapping.findForward("success");
	}
}