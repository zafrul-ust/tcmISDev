package com.tcmis.internal.distribution.action;

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
import com.tcmis.internal.distribution.process.CustomerOrderTrackingProcess;

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
		/*		if (!personnelBean.getPermissionBean().hasUserPagePermission("salesOrders"))
				{
				   return (mapping.findForward("nopermissions"));
				}  */
		CustomerOrderTrackingProcess orderTrackingProcess = new CustomerOrderTrackingProcess(getDbUser(), getTcmISLocaleString(request));
		String uAction = (String) ((DynaBean) form).get("uAction");
		if (user.isOpenCustomer() && (uAction == null)) {
			OpenUserCustomerProcess openCustomerProcess = new OpenUserCustomerProcess(getDbUser());
			request.setAttribute("userCustomerCollection", openCustomerProcess.getSearchResult(user));
		}
		else {
			String query = "select distinct customer_id, customer_name from user_customer_view where personnel_id = {0} order by customer_name";
			request.setAttribute("userCustomerCollection", orderTrackingProcess.getSearchResult(query, new UserCustomerViewBean(), user.getPersonnelId()));
		}
		//Main
		
		if (uAction == null) {
			// Just populate the userCustomerCollection and return
			return mapping.findForward("success");
		}

		//Process search
		CustomerOrderTrackingInputBean inputBean = new CustomerOrderTrackingInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		if (uAction.equals("search")) {
			Collection<SalesOrderViewBean> salesOrderCollection = orderTrackingProcess.getSalesOrder(inputBean, user);
			request.setAttribute("salesOrderViewBeanColl", salesOrderCollection);
			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		}
		else if (uAction.equals("createExcel")) {
			Collection<SalesOrderViewBean> salesOrderCollection = orderTrackingProcess.getSalesOrder(inputBean, user);
			try {
				this.setExcel(response, "Order Tracking");
				orderTrackingProcess.getExcelReport(inputBean, salesOrderCollection, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}
		// Search
		else {
			return mapping.findForward("success");
		}
	}

}
