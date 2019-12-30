package com.tcmis.common.admin.action;

import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleUtils;
import org.apache.struts.validator.DynaValidatorForm;

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.HubPrefViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PersonnelUserGroupViewBean;
import com.tcmis.common.admin.beans.VvCurrencyBean;
import com.tcmis.common.admin.beans.VvLocaleBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Controller for login
 * @version 1.0
 ******************************************************************************/
public class DocViewerLoginAction extends TcmISBaseAction {
	private static boolean needpass = true;
	static { 
		try {
			if( "NoPassRequired".equals( new ResourceLibrary("localconfig").getString("NoPassRequired") ) ) 
				needpass = false;
		}catch(Exception ex){
			//ex.printStackTrace();
		}
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		//copy input into bean
		PersonnelBean personnelBean = new PersonnelBean();
		BeanHandler.copyAttributes(form, personnelBean);

		HttpSession session = request.getSession(true);
		session.invalidate();
		session = request.getSession(true);
//		String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix().replace("/", "");
//		Cookie tcmIScookie = new Cookie("tcmIS_Session", module + "_" + session.getId());
//		tcmIScookie.setMaxAge(12 * 60 * 60);  // 12 hours.
//		tcmIScookie.setPath("/");
//		response.addCookie(tcmIScookie);
		boolean passwordExpired = false;
		if (!StringHandler.isBlankString(personnelBean.getLogonId())) {

			//login
			LoginProcess loginProcess = new LoginProcess(getDbUser(request));
			try {
				personnelBean = loginProcess.loginWeb(personnelBean,needpass);
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

				personnelBean.setDbUser(getDbUser());
				session.setAttribute("personnelBean", personnelBean);
				session.setAttribute("schema", loginProcess.getSchema());

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
			if (requestedURLWithParameters != null) {
				request.setAttribute("requestedURLWithParameters", requestedURLWithParameters);
			}
			return (mapping.findForward("changepassword"));
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
}
