package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.fedex.process.CloseServiceProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbUpdateException;
import com.tcmis.common.framework.BaseStatus;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.PackShipConfirmInputBean;
import com.tcmis.internal.hub.process.PackShipConfirmProcess;
import com.tcmis.internal.report.beans.ErrorBean;

/**
 * ***************************************************************************
 * Controller for ship confirm
 * 
 * @version 1.0
 *          *****************************************************************
 *          ***********
 */
public class PackShipConfirmAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "packshipconfirmmain");
			// This is to save any parameters passed in the URL, so
			// that they
			// can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("packshipConfirm")) {
			return (mapping.findForward("nopermissions"));
		}

		if (form != null) {

			// copy data from dyna form to the data form
			PackShipConfirmInputBean bean = new PackShipConfirmInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

			BigDecimal userId = new BigDecimal(personnelBean.getPersonnelId());
			bean.setUserId(userId);
			PackShipConfirmProcess process = new PackShipConfirmProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			// check what button was pressed and determine where to
			// go
			if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				if (log.isDebugEnabled())
					log.debug("HERE IN SEARCH");
				this.saveTcmISToken(request);
				if ("ltltl".equals(bean.getTransportationMode())) {
					request.setAttribute("ltltlViewBeanCollection", process.getSearchResult(bean));
					return (mapping.findForward("showltl"));
				}
				else {
					request.setAttribute("parcelViewBeanCollection", process.getSearchResult(bean));
					return (mapping.findForward("showparcel"));
				}
			}
			else if (((DynaBean) form).get("action") != null && "createExcel".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				if (log.isDebugEnabled()) 
					log.debug("HERE IN CREATE EXCEL");
				this.setExcelHeader(response);
				// CREATE EXCEL HERE
				if ("ltltl".equals(bean.getTransportationMode())) {
					return (mapping.findForward("showltl"));
				}
				else {
					return (mapping.findForward("showparcel"));
				}
			}
			else if (((DynaBean) form).get("action") != null && "update".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				if (log.isDebugEnabled()) 
					log.debug("HERE IN UPDATE");
				/*
				 * if(!personnelBean.getPermissionBean().
				 * hasFacilityPermission("shipConfirm",
				 * bean.getSourceHub(), null)) { return
				 * (mapping.findForward("nopermissions")); }
				 */
				checkToken(request);
				DynaBean dynaBean = (DynaBean) form;
				Collection beans = BeanHandler.getBeans(dynaBean, "packShipConfirmInputBean", new PackShipConfirmInputBean(), getTcmISLocale(request));
				if ("ltltl".equals(bean.getTransportationMode())) {
					Collection errorMsgs = process.updateLtlTl(beans);

					request.setAttribute("ltltlViewBeanCollection", process.getSearchResult(bean));
					request.setAttribute("tcmISErrors", errorMsgs);
					return (mapping.findForward("showltl"));
				}
				else {
					try {
						process.updateParcel(beans);
					}
					catch (DbUpdateException uex) {
						// set database errors so we can
						// show them to users
						ErrorBean errorBean = new ErrorBean();
						errorBean.setCause(uex.getMessage());
						request.setAttribute("errorBean", errorBean);
					}
					request.setAttribute("parcelViewBeanCollection", process.getSearchResult(bean));
					return (mapping.findForward("showparcel"));
				}
			}
			else if (((DynaBean) form).get("action") != null && "packConfirmShipment".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				if (log.isDebugEnabled()) 
					log.debug("HERE IN packconfirmshipment");
				this.saveTcmISToken(request);
				request.setAttribute("packShipmentBeanCollection", process.getPackConfirmShipmentResult(bean));
				return (mapping.findForward("packconfirmshipment"));
			}
			else if (((DynaBean) form).get("action") != null && "confirmShipment".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				if (log.isDebugEnabled()) {
					log.debug("HERE IN confirmshipment: " + bean.getDeliveredDate());
				}
				/*
				 * if(!personnelBean.getPermissionBean().
				 * hasFacilityPermission("shipConfirm",
				 * bean.getSourceHub(), null)) { return
				 * (mapping.findForward("nopermissions")); }
				 */
				checkToken(request);
				DynaBean dynaBean = (DynaBean) form;
				Collection beans = BeanHandler.getBeans(dynaBean, "packShipConfirmInputBean", new PackShipConfirmInputBean(), getTcmISLocale(request));
				String errorMessage = process.confirmShipment(bean, beans);
				if (errorMessage.length() > 0) {
					request.setAttribute("updateErrorFlag", "Error");
					request.setAttribute("messageToUser", errorMessage);
					request.setAttribute("packShipmentBeanCollection", process.getPackConfirmShipmentResult(bean));
				}
				else {
					request.setAttribute("updateErrorFlag", "Updated");
				}
				return (mapping.findForward("packconfirmshipment"));
			}
			else if (((DynaBean) form).get("action") != null && "palletDetail".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				if (log.isDebugEnabled()) 
					log.debug("HERE IN palletdetail");
				this.saveTcmISToken(request);
				request.setAttribute("palletDetailOvBeanCollection", process.getPalletDetailResult(bean));
				return (mapping.findForward("palletdetail"));
			}
			else if (((DynaBean) form).get("action") != null && "updatePalletDetail".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				if (log.isDebugEnabled()) 
					log.debug("HERE IN update updatePalletDetail");
				/*
				 * if(!personnelBean.getPermissionBean().
				 * hasFacilityPermission("shipConfirm",
				 * bean.getSourceHub(), null)) { return
				 * (mapping.findForward("nopermissions")); }
				 */
				checkToken(request);
				DynaBean dynaBean = (DynaBean) form;
				Collection beans = BeanHandler.getBeans(dynaBean, "packShipConfirmInputBean", new PackShipConfirmInputBean(), getTcmISLocale(request));
				String errorMessage = process.updatePalletDetail(bean, beans);
				if (errorMessage.length() > 0) {
					request.setAttribute("updateErrorFlag", "Error");
					request.setAttribute("messageToUser", errorMessage);
					request.setAttribute("palletDetailOvBeanCollection", process.getPalletDetailResult(bean));
				}
				else {
					request.setAttribute("updateErrorFlag", "Updated");
				}
				return (mapping.findForward("palletdetail"));
			}
			else if (((DynaBean) form).get("action") != null && "putShipmentOnPallet".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				if (log.isDebugEnabled()) 
					log.debug("HERE IN putshipmentonpallet");
				// request.setAttribute("sourceHub",bean.getSourceHub());
				// request.setAttribute("palletId",bean.getPalletId());
				this.saveTcmISToken(request);
				request.setAttribute("mrShipmentDetailBean", process.getMrShipmentDetailResult(bean));
				return (mapping.findForward("putshipmentonpallet"));
			}
			else if (((DynaBean) form).get("action") != null && "updatePutShipmentOnPallet".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				if (log.isDebugEnabled()) 
					log.debug("HERE IN updatePutShipmentOnPallet");
				/*
				 * if(!personnelBean.getPermissionBean().
				 * hasFacilityPermission("shipConfirm",
				 * bean.getSourceHub(), null)) { return
				 * (mapping.findForward("nopermissions")); }
				 */
				checkToken(request);
				String errorMessage = process.updatePutShipmentOnPallet(bean);
				if (errorMessage.length() > 0) {
					request.setAttribute("updateErrorFlag", "Error");
					request.setAttribute("messageToUser", errorMessage);
					request.setAttribute("mrShipmentDetailBean", process.getMrShipmentDetailResult(bean));
				}
				else {
					request.setAttribute("updateErrorFlag", "Updated");
				}
				return (mapping.findForward("putshipmentonpallet"));
			}
			if (((DynaBean) form).get("action") != null && "requestWAWFInsRequest".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				/*
				 * if(!personnelBean.getPermissionBean().
				 * hasFacilityPermission("shipConfirm",
				 * bean.getSourceHub(), null)) { return
				 * (mapping.findForward("nopermissions")); }
				 */
				checkToken(request);
				DynaBean dynaBean = (DynaBean) form;
				Collection<PackShipConfirmInputBean> beans = BeanHandler.getBeans(dynaBean, "packShipConfirmInputBean", new PackShipConfirmInputBean(), getTcmISLocale(request));

				process.requestWAWFInsRequest(beans);
				
				if ("ltltl".equals(bean.getTransportationMode())) {
					request.setAttribute("ltltlViewBeanCollection", process.getSearchResult(bean));
					return (mapping.findForward("showltl"));
				}
				else {
					request.setAttribute("parcelViewBeanCollection", process.getSearchResult(bean));
					return (mapping.findForward("showparcel"));
				}
			}
			else if (((DynaBean) form).get("action") != null && "printHazardousGoodsManifest".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				/*
				 * if(!personnelBean.getPermissionBean().
				 * hasFacilityPermission("shipConfirm",
				 * bean.getSourceHub(), null)) { return
				 * (mapping.findForward("nopermissions")); }
				 */
				checkToken(request);
				DynaBean dynaBean = (DynaBean) form;
				Collection<PackShipConfirmInputBean> beans = BeanHandler.getBeans(dynaBean, "packShipConfirmInputBean", new PackShipConfirmInputBean(), getTcmISLocale(request));
				BigDecimal hazDocId = null;
				try {
					hazDocId = process.printHazardousGoodsManifest(beans);
				}
				catch (DbUpdateException uex) {
					// set database errors so we can show
					// them to users
					ErrorBean errorBean = new ErrorBean();
					errorBean.setCause(uex.getMessage());
					request.setAttribute("errorBean", errorBean);
				}
				if (hazDocId != null) {
					request.setAttribute("FedexParcelHazDocId", hazDocId);
					request.setAttribute("popUp", "Y");
				}
				request.setAttribute("parcelViewBeanCollection", process.getSearchResult(bean));
				return (mapping.findForward("showparcel"));
			}
			else if ("endOfDayFedexClose".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
				//
				//				if (!personnelBean.getPermissionBean().hasFacilityPermission("shipConfirm", bean.getSourceHub(), null)) {
				//					ResourceLibrary msgLibrary = new ResourceLibrary("com.tcmis.common.resources.CommonResources");
				//					request.setAttribute("tcmISStatus", new BaseStatus(StatusType.ERROR, msgLibrary.getString("error.generic.permission")));
				//				}
				//				else {
				CloseServiceProcess closeProcess = new CloseServiceProcess(getDbUser());
				BaseStatus status = closeProcess.submitCloseRequest(bean.getOpsEntityId(), bean.getSourceHub());
				request.setAttribute("tcmISStatus", status);
				//				}
				return mapping.findForward("fedexclosestatus");
			}
			else if (actionEquals("mrLineSearch", form)) {
				request.setAttribute("mrlineCollection", process.listMrLines(bean));
				return mapping.findForward("mrlines");
			}
			else {
				if (log.isDebugEnabled()) 
					log.debug("HERE IN LOAD INITIAL DATA");
				// load initial data for dropdown
				return (mapping.findForward("success"));
			}
		}
		return (mapping.findForward("success"));
	}
	
	private boolean actionEquals(String action, ActionForm form) {
		return action!=null&&action.equalsIgnoreCase(StringHandler.emptyIfNull(((DynaBean)form).get("action")));
	}
}
