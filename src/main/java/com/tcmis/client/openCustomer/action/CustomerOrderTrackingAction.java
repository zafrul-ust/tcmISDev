package com.tcmis.client.openCustomer.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.openCustomer.process.OpenUserCustomerProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.CustomerOrderTrackingInputBean;
import com.tcmis.internal.distribution.beans.SalesOrderViewBean;
import com.tcmis.internal.distribution.beans.UserCustomerViewBean;
import com.tcmis.client.openCustomer.process.CustomerOrderTrackingProcess;

public class CustomerOrderTrackingAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//Login
		if (!isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "customerordertrackingmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) this.getSessionObject(request, "personnelBean");
				if (!user.getPermissionBean().hasUserPagePermission("customerOrderTracking"))
				{
				   return (mapping.findForward("nopermissions"));
				}
		CustomerOrderTrackingProcess orderTrackingProcess = new CustomerOrderTrackingProcess(getDbUser(), getTcmISLocaleString(request));
		String uAction = (String) ((DynaBean) form).get("uAction");

		//Process search
		CustomerOrderTrackingInputBean inputBean = new CustomerOrderTrackingInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		if ("search".equals(uAction)) {
			Collection<SalesOrderViewBean> salesOrderCollection = orderTrackingProcess.getSalesOrder(inputBean, user);
			request.setAttribute("salesOrderViewBeanColl", salesOrderCollection);
			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		}
		else if ("createExcel".equals(uAction)) {
			Collection<SalesOrderViewBean> salesOrderCollection = orderTrackingProcess.getSalesOrder(inputBean, user);
			try {
				this.setExcel(response, "Order Tracking");
				orderTrackingProcess.getOpenCustomerExcelReport(inputBean, salesOrderCollection, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}
		// Search
		else {
			OpenUserCustomerProcess openCustomerProcess = new OpenUserCustomerProcess(getDbUser());
			request.setAttribute("userCustomerCollection", openCustomerProcess.getSearchResult(user));
		
			return mapping.findForward("success");
		}
	}

}
