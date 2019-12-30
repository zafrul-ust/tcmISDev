package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.UserPageAdminViewBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;

import com.tcmis.common.admin.beans.UserPageSelectViewBean;
import com.tcmis.common.admin.process.PersonnelProcess;
import com.tcmis.internal.currency.beans.CurrencyExchangeRateBean;
import com.tcmis.internal.distribution.process.CurrencyExchangeRateProcess;
import com.tcmis.common.admin.process.UserPageSelectViewProcess;

/******************************************************************************
 * Controller for PagePermissions
 * @version 1.0
 ******************************************************************************/
public class CurrencyExchangeRateAction
extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		//  login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "currencyexchangerate".toLowerCase());
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("currencyExchangeRate"))
		{
		   return (mapping.findForward("nopermissions"));
		}  

		String action =  request.getParameter("uAction");			

		if ( "update".equals(action)) {
			CurrencyExchangeRateProcess process = new CurrencyExchangeRateProcess(this.getDbUser(request),getTcmISLocaleString(request));

//  		java.util.Enumeration e = request.getParameterNames();
//  		while(e.hasMoreElements()) 
//  		{
//  			String name = (String) e.nextElement();
//  			System.out.println(name +":"+request.getParameter(name));
//  		}

  		DynaBean dynaBean = (DynaBean) form;
			Collection<CurrencyExchangeRateBean> beans = BeanHandler.getBeans(dynaBean, "beanCollection",new CurrencyExchangeRateBean(), getTcmISLocale(request));
			String errorMsg = "";
			for(CurrencyExchangeRateBean bean:beans) {
				errorMsg +=	process.updateExchangeRate(bean);
			}
			request.setAttribute("tcmISError", errorMsg );		
		}
		CurrencyExchangeRateProcess process = new CurrencyExchangeRateProcess(this.getDbUser(request),getTcmISLocaleString(request));
		DynaBean dynaBean = (DynaBean) form;
		CurrencyExchangeRateBean bean = new CurrencyExchangeRateBean();
		BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
		request.setAttribute("beanCollection",
				process.getCurrencyExchangeRate(bean));
		return mapping.findForward("success");
	}
}