package com.tcmis.client.openCustomer.action;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.tcmis.client.openCustomer.process.LoginProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvLocaleBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Controller for login
 * @version 1.0
 ******************************************************************************/
public class LoginAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		//copy input into bean
		PersonnelBean personnelBean = new PersonnelBean();
		BeanHandler.copyAttributes(form, personnelBean);

		HttpSession session = request.getSession(true);
		boolean passwordExpired = false;
		if (!StringHandler.isBlankString(personnelBean.getLogonId())) {
			//login
			LoginProcess loginProcess = new LoginProcess(getDbUser(request));
			try {
				personnelBean = loginProcess.loginOpenCustomer(personnelBean);
				passwordExpired = loginProcess.isPasswordExpired(personnelBean);
			}
			catch (Exception ex) {
				String forward = (String) ((DynaValidatorForm) form).get("requestedPage");
				String requestedURLWithParameters = (String) ((DynaValidatorForm) form).get("requestedURLWithParameters");
				if (forward != null) {
					request.setAttribute("requestedPage", forward);
				}
				if (requestedURLWithParameters != null) {
					request.setAttribute("requestedURLWithParameters", requestedURLWithParameters);
				}
				request.setAttribute("home", personnelBean.getHome());
				throw ex;
			}

			if (personnelBean != null) {
				if (log.isInfoEnabled()) {
					log.info("bean:" + personnelBean.getLogonId() + " - " + personnelBean.getPersonnelId() + " - " + this.getDbUser(request));
				}

				personnelBean.setDbUser(getDbUser(request));
				log.debug("Set personnelbean.dbUser == " + personnelBean.getDbUser());

				VvDataProcess vvDataProcess = new VvDataProcess(getDbUser());
				session.setAttribute("vvCurrencyCollection", vvDataProcess.getVvCurrency());
				session.setAttribute("personnelBean", personnelBean);

				personnelBean.setLocale(setLocale(request, session, request.getParameter("langSetting")));
				
				//session.setAttribute("timeout", loginProcess.getTimeout(personnelBean));
			}
			else {
				if (log.isDebugEnabled()) {
					log.debug("PersonnelBean is null!");
				}
				String forward = (String) ((DynaValidatorForm) form).get("requestedPage");
				if (forward != null) {
					request.setAttribute("requestedPage", forward);
				}
				String requestedURLWithParameters = (String) ((DynaValidatorForm) form).get("requestedURLWithParameters");
				if (requestedURLWithParameters != null) {
					request.setAttribute("requestedURLWithParameters", requestedURLWithParameters);
				}
				request.setAttribute("home", personnelBean.getHome());

				BaseException be = new BaseException();
				be.setRootCause(new Exception("Invalid login"));
				be.setMessageKey("error.login.invalid");
				throw be;
			}
		}
		else {
			String forward = (String) ((DynaValidatorForm) form).get("requestedPage");
			if (forward != null) {
				request.setAttribute("requestedPage", forward);
			}
			String requestedURLWithParameters = (String) ((DynaValidatorForm) form).get("requestedURLWithParameters");
			if (requestedURLWithParameters != null) {
				request.setAttribute("requestedURLWithParameters", requestedURLWithParameters);
			}
			request.setAttribute("home", personnelBean.getHome());
			BaseException be = new BaseException();
			be.setRootCause(new Exception("Invalid login"));
			be.setMessageKey("error.login.invalid");
			throw be;
		}

		if (passwordExpired) {
			String forward = (String) ((DynaValidatorForm) form).get("requestedPage");
			if (forward != null) {
				request.setAttribute("requestedPage", forward);
			}

			String requestedURLWithParameters = (String) ((DynaValidatorForm) form).get("requestedURLWithParameters");
			if (requestedURLWithParameters != null && requestedURLWithParameters.length() > 0) {
				request.setAttribute("requestedURLWithParameters", requestedURLWithParameters);
			}
			else
				request.setAttribute("requestedURLWithParameters", "application.do");

			request.setAttribute("expiredPassword", "Y");
			return (mapping.findForward("changePassword"));
		}

		//check if I need to redirect to other page than index page
		String forward = "success";
		if (form != null && ((DynaValidatorForm) form).get("requestedURLWithParameters") != null && ((String) ((DynaValidatorForm) form).get("requestedURLWithParameters")).trim().length() > 0) {
			String requestedURLWithParameters = (String) ((DynaValidatorForm) form).get("requestedURLWithParameters");
			if (requestedURLWithParameters != null) {
				request.setAttribute("requestedURLWithParameters", requestedURLWithParameters);
			}
			forward = "redirectToUrl";
			if (requestedURLWithParameters.indexOf("application.do") != -1) {
				forward = "application";
			}
		}
		else if (form != null && ((DynaValidatorForm) form).get("requestedPage") != null && ((String) ((DynaValidatorForm) form).get("requestedPage")).trim().length() > 0) {
			forward = (String) ((DynaValidatorForm) form).get("requestedPage");
		}
		else if (personnelBean.getHome() != null && personnelBean.getHome().length() > 0) {
			forward = personnelBean.getHome();
		}

		if (log.isDebugEnabled()) {
			log.debug("forward:" + forward);
		}
		return (mapping.findForward(forward));
	}

	public static Locale setLocale(HttpServletRequest request, HttpSession session, String lang) {
		Locale locale = (Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY);
		if (locale == null)
			locale = request.getLocale(); // sent from browser.

		boolean found = false;
		Vector<VvLocaleBean> localeBeans = (Vector<VvLocaleBean>) session.getServletContext().getAttribute("vvLocaleBeanCollection");
		session.setAttribute("langSetting", lang); // for our own use...

		if (lang != null) {
			if (lang.length() == 5) {
				String[] langArr = lang.split("_");
				if (langArr.length == 2) {
					locale = new Locale(langArr[0].toLowerCase(), langArr[1].toUpperCase());
					String localeS = locale.toString();
					for (VvLocaleBean bean : localeBeans) {
						if (bean.getLocaleCode().equals(localeS))
							found = true;
					}
				}
			}
			else if (lang.length() == 2) {
				locale = new Locale(lang.toLowerCase());
				String localeS = locale.toString();
				for (VvLocaleBean bean : localeBeans) {
					if (bean.getLocaleCode().substring(0, 2).equals(localeS)) {
						locale = new Locale(bean.getLocaleCode().substring(0, 2), bean.getLocaleCode().substring(3, 5));
						found = true;
					}
				}
			}
		}

		if (!found)
			locale = defaultLocale;
		session.setAttribute("tcmISLocale", locale);
		/*This is for struts to know which locale to use*/
		session.setAttribute(org.apache.struts.Globals.LOCALE_KEY, locale);
		/*This is for the <fmt> tag.*/
		Config.set(session, Config.FMT_LOCALE, locale);

		session.setAttribute("tcmISDecimalSeparator", new DecimalFormatSymbols(locale).getDecimalSeparator());

		return locale;
	}

}
