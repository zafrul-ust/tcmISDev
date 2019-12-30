package com.tcmis.internal.catalog.action;

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
import com.tcmis.internal.catalog.beans.CustomerIndexingMsdsQViewBean;
import com.tcmis.internal.catalog.beans.CustomerMsdsQueueInputBean;
import com.tcmis.internal.catalog.process.CustomerMsdsQueueProcess;

/******************************************************************************
 * Controller for Customer MSDS Queue
 * @version 1.0
 ******************************************************************************/
public class CustomerMsdsQueueAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "customermsdsqueuemain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("customerMsdsQueue")) {
			return (mapping.findForward("nopermissions"));
		}

		// if form is not null we have to perform some action
		if (form != null) {
			// copy date from dyna form to the data form
			CustomerMsdsQueueInputBean bean = new CustomerMsdsQueueInputBean();
			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

			CustomerMsdsQueueProcess process = new CustomerMsdsQueueProcess(this.getDbUser(request), getTcmISLocaleString(request));
			
			String action = (String) ((DynaBean) form).get("uAction");
			request.setAttribute("catalogUsers", process.getCatalogUsers(bean));
			if ("search".equalsIgnoreCase(action)) {
				request.setAttribute("customerIndexingMsdsQViewBeanColl", process.getsearchResult(bean));
			} else if ("assignUser".equalsIgnoreCase(action)) {
				Collection<CustomerIndexingMsdsQViewBean> records = BeanHandler.getBeans((DynaBean) form, "customerMsdsBean", "datetimeformat", new CustomerIndexingMsdsQViewBean(), getTcmISLocale(request));
				process.assignUser(bean, records);
				request.setAttribute("customerIndexingMsdsQViewBeanColl", process.getsearchResult(bean));
			} else if ("createExcel".equalsIgnoreCase(action)) {
				this.setExcel(response,"CustomerMSDSQueue");
				process.getExcelReport(bean,(java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
				return noForward;
			} else {
				request.setAttribute("myCompanyCollection", process.getMyCompanies(personnelBean.getPersonnelId()));
			}
		}
		return mapping.findForward("success");
	}
}