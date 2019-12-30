package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.framework.DropDownListBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetManagementInputBean;
import com.tcmis.internal.hub.beans.CabinetPartLevelViewBean;
import com.tcmis.internal.hub.process.CabinetManagementProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PointOfSaleAccountSysInfoBean;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.common.beans.CabinetManagementBean;
/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class CabinetManagementAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "cabinetmanagementmain");
			// This is to save any parameters passed in the URL, so that they
			// can be acccesed after the login
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("cabinetManagement"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }

		CabinetDefinitionManagementProcess process = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
		if (form != null) {
			String uAction = (String) ((DynaBean) form).get("uAction");
			CabinetManagementInputBean bean = new CabinetManagementInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

			if ("addBin".equalsIgnoreCase(uAction)) {
				/*
				BigDecimal tmpCabinetId = bean.getCabinetId();
				if (tmpCabinetId == null) {
					tmpCabinetId = new BigDecimal(bean.getCabinetIdArray()[0]);
				}
				String tmpApplication = bean.getApplication();
				if (StringHandler.isBlankString(tmpApplication)) {
					tmpApplication = bean.getUseApplication();
				}
				request.setAttribute("application", tmpApplication);
				request.setAttribute("cabinetId", tmpCabinetId);
				*/
				return (mapping.findForward("newbin"));
			}else if ("search".equalsIgnoreCase(uAction)) {
				this.saveTcmISToken(request);
				Collection dataColl = process.getSearchData(bean);
				request.setAttribute("cabinetPartLevelViewBeanCollection",dataColl);
				Object[] results = process.getCabinetManagementRowSpan(dataColl);
				request.setAttribute("rowCountFirstLevel", results[0]);
				request.setAttribute("rowCountSecondLevel", results[1]);
				//CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				//CatalogInputBean tmpBean = new CatalogInputBean();
				//tmpBean.setFacilityId(bean.getFacilityId());
				//request.setAttribute("prRulesViewCollection",catalogProcess.getPrRulesForFacility(tmpBean));
				return mapping.findForward("results");
			} else if ("update".equalsIgnoreCase(uAction)) {
				checkToken(request);
				Collection<CabinetManagementBean> c = BeanHandler.getBeans((DynaBean) form, "cabinetPartLevelViewBean", new CabinetManagementBean(), getTcmISLocale(request));
				String errMsg = process.updateCabinetBinPartInventory(c, personnelBean);
				request.setAttribute("tcmISError", errMsg);
				Collection dataColl = process.getSearchData(bean);
				request.setAttribute("cabinetPartLevelViewBeanCollection",dataColl);
				Object[] results = process.getCabinetManagementRowSpan(dataColl);
				request.setAttribute("rowCountFirstLevel", results[0]);
				request.setAttribute("rowCountSecondLevel", results[1]);
				//CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				//CatalogInputBean tmpBean = new CatalogInputBean();
				//tmpBean.setFacilityId(bean.getFacilityId());
				//request.setAttribute("prRulesViewCollection",catalogProcess.getPrRulesForFacility(tmpBean));
				return mapping.findForward("results");
			} else if ("createExcel".equalsIgnoreCase(uAction)) {
				try {
					this.setExcel(response, "CabinetManagement");
					process.getExcelReport(bean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				} catch (Exception ex) {
					ex.printStackTrace();
					return mapping.findForward("genericerrorpage");
				}
				return noForward;
			}else if ("editCabinetDirectedCharge".equalsIgnoreCase(uAction)) {
				CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				CatalogInputBean tmpBean = new CatalogInputBean();
				BeanHandler.copyAttributes(form, tmpBean, this.getTcmISLocale(request));
				PointOfSaleAccountSysInfoBean posBean = catalogProcess.getPosAccountSysChargeNumberPoData(tmpBean);
				process.getDirectedChargeInfo(posBean,tmpBean);
				request.setAttribute("accountSysChargeNumberPoData",posBean);
				return mapping.findForward("loadChargeInfo");
			} else if ("setWorkAreaCabinetPartDirectedCharge".equalsIgnoreCase(uAction)) {
				CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
				CatalogInputBean tmpBean = new CatalogInputBean();
				BeanHandler.copyAttributes(form, tmpBean, this.getTcmISLocale(request));
				String errorVal = "OK";
				if ("true".equalsIgnoreCase(tmpBean.getUserEnteredChargeNumber()) && !StringHandler.isBlankString(tmpBean.getChargeNumbers())) {
					errorVal = catalogProcess.validateChargeNumbers(tmpBean);
				}

				if ("OK".equalsIgnoreCase(errorVal)) {
					request.setAttribute("chargeNumbersValid",process.setWorkAreaCabinetPartDirectedCharge(tmpBean));
				}else {
					request.setAttribute("chargeNumbersValid",errorVal);
				}

				return mapping.findForward("loadChargeNumberValidator");
			} else {
				CabinetManagementProcess cmProcess = new CabinetManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
				request.setAttribute("hubCabinetViewBeanCollection",cmProcess.getAllLabelData((PersonnelBean) this.getSessionObject(request,"personnelBean")));
				return mapping.findForward("success");
			}
		}
		
		return mapping.findForward("success");
	}
}