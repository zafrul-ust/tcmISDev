package com.tcmis.internal.supply.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.RemitToManagementInputBean;
import com.tcmis.internal.supply.beans.SupplierBillingLocationViewBean;
import com.tcmis.internal.supply.process.RemitToManagementProcess;

/******************************************************************************
 * Controller for remitToManagement
 * @version 1.0
 ******************************************************************************/

public class RemitToManagementAction extends TcmISBaseAction {	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws  BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "supplierremittoaddressmanagement");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return mapping.findForward("login");
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");		
		if (!personnelBean.getPermissionBean().hasUserPagePermission("newRemitTo"))
			return mapping.findForward("nopermissions");
		
		if (form != null) {
			RemitToManagementInputBean inputBean = new RemitToManagementInputBean(form, getTcmISLocale(request));
			RemitToManagementProcess process = new RemitToManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
			if (inputBean.getuAction() != null) {
				if ("changestatus".equalsIgnoreCase(inputBean.getuAction())) {
					Vector<SupplierBillingLocationViewBean> beanColl = (Vector<SupplierBillingLocationViewBean>) BeanHandler.getBeans((DynaBean) form, "SupplierBillingLocationViewBean", new SupplierBillingLocationViewBean(), getTcmISLocale(), "updated");
					process.updateStatus(beanColl.firstElement(), personnelBean.getPersonnelIdBigDecimal());
				}
				request.setAttribute("supplierBillingLocationViewBeanCollection", process.getRemitToAddresses(inputBean));
			}
			request.setAttribute("searchInput", inputBean);
			request.setAttribute("supplier", inputBean.getSupplier());
			request.setAttribute("supplierName", inputBean.getSupplierName());
			request.setAttribute("callerName", inputBean.getCallerName());
		}

		return mapping.findForward("success");
	}
}
