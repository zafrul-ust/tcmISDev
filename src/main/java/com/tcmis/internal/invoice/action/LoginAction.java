package com.tcmis.internal.invoice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
//import com.tcmis.common.spring.HaasApplicationContext;
import com.tcmis.common.util.BeanHandler;
//import com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroupsMapping;

/******************************************************************************
 * Controller for login
 * 
 * @version 1.0
 ******************************************************************************/
// Larry Note: We should be using common login, but I don't want to change too
// much of current set up.
public class LoginAction extends TcmISBaseAction
{
	/*private static final String INV_GRP_CONFIG_BEAN_NAME = "invoiceGroupsConfigurationBean";
	private static final String CONFIGURED_INV_GRP_OPTIONS = "configuredInventoryGroupOptions";*/

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception
	{
		// copy input into bean
		PersonnelBean personnelBean = new PersonnelBean();
		BeanHandler.copyAttributes(form, personnelBean);
		// login
		if (personnelBean != null && personnelBean.getClient() != null)
		{
			LoginProcess loginProcess = new LoginProcess(
					this.getDbUser(request));
			personnelBean = loginProcess.loginWeb(personnelBean);
			if (personnelBean != null)
			{
				if (log.isInfoEnabled())
				{
					log.info("bean:" + personnelBean.getLogonId() + " - "
							+ personnelBean.getPersonnelId() + " - "
							+ personnelBean.getClient());
				}
				HttpSession session = request.getSession(true);
				session.setAttribute("personnelBean", personnelBean);
				/*if (this.getSessionObject(request, CONFIGURED_INV_GRP_OPTIONS) == null)
					configureInvoiceGroupOptions(request);*/
				CompanyBean company = loginProcess.getCompany(personnelBean);
				session.setAttribute("timeout", company.getAppTimeout());
				session.setAttribute("timeoutWait", company.getAppTimeoutWait());
			}
			else
			{
				if (log.isDebugEnabled())
				{
					log.debug("PersonnelBean is null!");
				}
				BaseException be = new BaseException();
				be.setRootCause(new Exception("Invalid login"));
				be.setMessageKey("error.login.invalid");
				throw be;
			}
		}
		else
		{
			BaseException be = new BaseException();
			be.setRootCause(new Exception("Invalid login"));
			be.setMessageKey("error.login.invalid");
			throw be;
		}
		// check if I need to redirect to other page than index page
		String page = request.getParameter("requestedPage");
		if ("haasinvoice".equals(page))
			return (mapping.findForward("haasinvoice"));
		return (mapping.findForward("success"));
	}

	/**
	 * Gets the configured invoice groups
	 * 
	 * @param request
	 * @throws BaseException
	 */
	/*private void configureInvoiceGroupOptions(HttpServletRequest request)
			throws BaseException
	{
		IConfiguredInvoiceGroupsMapping invoiceGroupsMapping = null;
		if (this.getSessionObject(request, INV_GRP_CONFIG_BEAN_NAME) == null)
		{
			// Load the bean definition
			invoiceGroupsMapping = (IConfiguredInvoiceGroupsMapping) HaasApplicationContext
					.getApplicationContext().getBean(INV_GRP_CONFIG_BEAN_NAME);
			this.setSessionObject(request, invoiceGroupsMapping,
					INV_GRP_CONFIG_BEAN_NAME);
		}
		else
		{
			invoiceGroupsMapping = (IConfiguredInvoiceGroupsMapping) this
					.getSessionObject(request, INV_GRP_CONFIG_BEAN_NAME);
		}

		if (invoiceGroupsMapping.getInvoiceGroupsSelection() != null)
		{
			this.setSessionObject(request,
					invoiceGroupsMapping.getInvoiceGroupsSelection(),
					CONFIGURED_INV_GRP_OPTIONS);
		}
	}*/
}
