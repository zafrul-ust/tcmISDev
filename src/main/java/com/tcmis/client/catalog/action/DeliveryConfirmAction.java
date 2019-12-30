package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.DeliveryConfirmInputBean;
import com.tcmis.client.catalog.beans.DeliveryConfirmViewBean;
import com.tcmis.client.catalog.process.DeliveryConfirmProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class DeliveryConfirmAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "deliveryconfirmmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return mapping.findForward("login");
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		/*Need to check if the user has permissions to view this page. if they do not have the permission we need to not show them the page.*/
		if (!personnelBean.getPermissionBean().hasUserPagePermission("deliveryConfirmation"))
			return mapping.findForward("nopermissions");

		// copy date from dyna form to the data bean
		DeliveryConfirmInputBean inputBean = new DeliveryConfirmInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		BigDecimal pid = new BigDecimal(personnelBean.getPersonnelId());			
		
		if (inputBean.getuAction() != null) {
			DeliveryConfirmProcess process = new DeliveryConfirmProcess(this.getDbUser(request),getTcmISLocaleString(request));
			
			if (inputBean.isSearch()) {
				request.setAttribute("deliveryConfirmColl", process.getDeliveryConfirmations(inputBean,personnelBean));
				saveTcmISToken(request);
			} else if (inputBean.isUpdate()) {
				checkToken(request);
				if (!personnelBean.getPermissionBean().hasFacilityPermission("deliveryConfirmation", inputBean.getFacilityId(), null))
					request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				else {
					Collection<DeliveryConfirmViewBean> updateColl = BeanHandler.getBeans((DynaBean) form,"deliveryConfirmViewBean", new DeliveryConfirmViewBean(),getTcmISLocale(request));
					request.setAttribute("tcmISError", process.update(updateColl, pid));
				}
				
				request.setAttribute("deliveryConfirmColl", process.getDeliveryConfirmations(inputBean,personnelBean));
			} else if (inputBean.isCreateExcel()) {
				this.setExcel(response, "DeliveryConfirmExcel");
	            this.overrideMaxData(request);
	            process.getExcelReport(process.getDeliveryConfirmations(inputBean, personnelBean),(Locale) request.getSession().getAttribute("tcmISLocale"), personnelBean, inputBean.getShowConfirmed()).write(response.getOutputStream());
				return noForward;
			}

			// stick the input form back in to the request for use by the tcmis:setHiddenFields tag
			request.setAttribute("searchInput", inputBean);
		} else {
			OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request));
			request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(pid));
		}
		
		return mapping.findForward("success");
	}
}
