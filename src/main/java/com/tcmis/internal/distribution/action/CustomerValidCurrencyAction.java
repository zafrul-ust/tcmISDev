package com.tcmis.internal.distribution.action;

import java.util.Collection;

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
import com.tcmis.internal.distribution.beans.CustomerValidCurrencyInputBean;
import com.tcmis.internal.distribution.beans.OpsEntCustomerCurrencyViewBean;
import com.tcmis.internal.distribution.beans.OpsEntityRemittanceViewBean;
import com.tcmis.internal.distribution.process.CustomerValidCurrencyProcess;

/*
 *
 *    
 */

public class CustomerValidCurrencyAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "customervalidcurrencymain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		// If you need access to who is logged in
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

		/* Need to check if the user has permissions to view this page. if they do not have the permission
			  we need to not show them the page.
		if (!user.getPermissionBean().hasUserPagePermission("openPos")) {
			return (mapping.findForward("nopermissions"));
		}  */

		String forward = "success";

		// copy date from dyna form to the input bean
		CustomerValidCurrencyInputBean inputBean = new CustomerValidCurrencyInputBean();
		String uAction = (String) ( (DynaBean)form).get("uAction");
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		CustomerValidCurrencyProcess process = new CustomerValidCurrencyProcess(getDbUser(request), getLocale(request));

		Collection errorMsgs = null;
		// Search
		if ("search".equals(uAction)) {
			// Pass the result collection in request
			request.setAttribute("customerOpsEntityCurrencyColl", process.getSearchResult(inputBean, user));
			String query = "select * from ops_entity_remittance_view where ops_entity_id = '"+inputBean.getOpsEntityId()+"' order by currency_Id";
			request.setAttribute("beanColl", process.getSearchResult(query, new OpsEntityRemittanceViewBean()));

			// save the token if update actions can be performed later.
			this.saveTcmISToken(request);
			return (mapping.findForward("success"));
		}
		//  Update
		else if ("update".equals(uAction)) {

			if (!user.getPermissionBean().hasOpsEntityPermission("CustomerDetailUpdate", null, null) && 
					!user.getPermissionBean().hasOpsEntityPermission("CustomerDetailAdmin", null, null)	) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				// repopulate the search results
				request.setAttribute("customerOpsEntityCurrencyColl", process.getSearchResult(inputBean, user));
				return (mapping.findForward("success"));
			}

			checkToken(request);

			Collection<OpsEntCustomerCurrencyViewBean> beans = BeanHandler.getBeans((DynaBean) form, "currencyBean", new OpsEntCustomerCurrencyViewBean(), getTcmISLocale(request));

			errorMsgs = process.update(inputBean, beans, user);

			// After update the data, we do the re-search to refresh the window
			request.setAttribute("customerOpsEntityCurrencyColl", process.getSearchResult(inputBean, user));
			String query = "select * from ops_entity_remittance_view where ops_entity_id = '"+inputBean.getOpsEntityId()+"' order by currency_Id";
			request.setAttribute("beanColl", process.getSearchResult(query, new OpsEntityRemittanceViewBean()));
			request.setAttribute("tcmISErrors", errorMsgs);
			return (mapping.findForward("success"));

		}
		else {

		}

		return (mapping.findForward(forward));
	}
}
