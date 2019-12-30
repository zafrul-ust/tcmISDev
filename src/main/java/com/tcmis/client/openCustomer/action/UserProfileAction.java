package com.tcmis.client.openCustomer.action;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.openCustomer.process.ForgetPasswordProcess;
import com.tcmis.client.openCustomer.process.UserProfileProcess;
//import com.tcmis.common.admin.process.UserCompanyAdminViewProcess;
//import com.tcmis.client.openCustomer.process.UserCustomerAdminViewProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/**
 * ***************************************************************************
 * Controller for userProfile
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class UserProfileAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		//		login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "userprofilemain");
			request.setAttribute("requestedURLWithParameters", request.getRequestURI());
			return (mapping.findForward("login"));
		}

		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
		request.setAttribute("userId", user.getPersonnelId());
		request.setAttribute("personnelId", request.getParameter("personnelId")); //modifiee
		//		main
		if (form == null)
			return (mapping.findForward("success"));

		//		result
		UserProfileProcess process = new UserProfileProcess(getDbUser(request), getTcmISLocaleString(request));
		
		PersonnelBean userBeingUpdated = new PersonnelBean();
		request.setAttribute("createNewUserCount", request.getParameter("createNewUserCount"));
		
		BeanHandler.copyAttributes(form, userBeingUpdated, getTcmISLocale(request));
		String action;
		// result
		if ((action = (String) ((DynaBean) form).get("action")) == null)
			return (mapping.findForward("success"));

		if (action.equalsIgnoreCase("update")) {
			String result = process.updateValue(user.getPersonnelId(), userBeingUpdated);
			
			if(result.length() > 0)
				request.setAttribute("tcmISError", result);
			
			userBeingUpdated = process.refreshPersonalBean(userBeingUpdated);
			request.setAttribute("personnelBean", userBeingUpdated);
			if (user.getPersonnelId() == userBeingUpdated.getPersonnelId()) {
				// Update of own data so update the data in the session
				user.setEmail(userBeingUpdated.getEmail());
				user.setFirstName(userBeingUpdated.getFirstName());
				user.setLastName(userBeingUpdated.getLastName());
				user.setMidInitial(userBeingUpdated.getMidInitial());
				user.setPhone(userBeingUpdated.getPhone());
				user.setAltPhone(userBeingUpdated.getAltPhone());
				user.setPager(userBeingUpdated.getPager());
				user.setFax(userBeingUpdated.getFax());
				user.setFontSizePreference(userBeingUpdated.getFontSizePreference());
			}
		}
		else if (action.equalsIgnoreCase("change")) {
			java.util.Vector v = (java.util.Vector) process.getSearchResult(userBeingUpdated);
			userBeingUpdated = (PersonnelBean) v.get(0);
			userBeingUpdated = process.refreshPersonalBean(userBeingUpdated);
			request.setAttribute("personnelBean", userBeingUpdated);
		}
		else if (action.equalsIgnoreCase("changeLang")) {
			com.tcmis.common.admin.action.LoginAction.setLocale(request, request.getSession(), request.getParameter("langSetting"));
			request.setAttribute("personnelBean", userBeingUpdated);
		}
		else if (action.equalsIgnoreCase("newUser")) {
			BigDecimal defaultCustomer = new BigDecimal(request.getParameter("defaultCustomer"));
			java.util.Vector results = (java.util.Vector) process.createNewUser(userBeingUpdated,user.getPersonnelId(),defaultCustomer);
			BigDecimal personnelId = (BigDecimal) results.get(0);
			String message = (String) results.get(1);
			if (personnelId != null && "OK".equalsIgnoreCase(message)) {
				userBeingUpdated.setPersonnelId(personnelId.intValue());
				userBeingUpdated.setLogonId(userBeingUpdated.getNewLogonId());
				userBeingUpdated.setPassword("");
				
				process.updateValue(user.getPersonnelId(), userBeingUpdated);
				ForgetPasswordProcess fpProcess = new ForgetPasswordProcess(this.getDbUser(request));
				String newPassword = fpProcess.getPassword();
				String errMsg = fpProcess.createNewPassword(userBeingUpdated.getNewLogonId(), newPassword,"NEW");
			}
			else
				request.setAttribute("tcmISError", message);

			userBeingUpdated = process.refreshPersonalBean(userBeingUpdated);
			request.setAttribute("personnelBean", userBeingUpdated);
		}
		//		search
		else if (action.equalsIgnoreCase("login")) {
			userBeingUpdated = user;
			request.setAttribute("createNewUserCount", process.createNewUserPermission(user));
		}
		else if (action.equalsIgnoreCase("showResult"))
			return mapping.findForward("showResult");
		
		return mapping.findForward("success");
	}
}