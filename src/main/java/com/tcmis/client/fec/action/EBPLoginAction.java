/******************************************************
 * EBPLoginAction.java
 * 
 * Handle login from an EBP catalog (SAP)
 ******************************************************
 */
package com.tcmis.client.fec.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.fec.process.EBPShoppingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;


public class EBPLoginAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// servelet params
		String callerId = null;
		String loginId = null;
		String sapUserId = "";
		String hookUrl = "";

		String url;  // the URL wich points to the JNLP

		String forward = "fileview";
		String errMsg = null;

		callerId = request.getParameter("USERNAME");
		sapUserId = request.getParameter("LOGGEDONUSER");   // SAP user id
		hookUrl = request.getParameter("HOOK_URL");         // call back url, unique, used as payload id

		if (log.isDebugEnabled()) {
			log.debug("got callerId: " + callerId);
			log.debug("got sapUserId: " + sapUserId);
			log.debug("got hook url: " + hookUrl);
		}
		if (callerId==null || sapUserId==null || hookUrl==null) {
			return mapping.findForward("nouser");
		}

		HttpSession session = request.getSession();
		String client = this.getDbUser(request);;
		EBPShoppingProcess shoppingProcess = new EBPShoppingProcess(client);

		// get personnel bean, based on the SAP id send in
		PersonnelBean user = shoppingProcess.getHaasUserFromSAPId(sapUserId);
		if (user!=null) {
			loginId = user.getLogonId();
			url = shoppingProcess.createJnlpUrl(loginId, hookUrl);
			session.setAttribute("HookUrlBean",hookUrl);
			session.setAttribute("JNLPFileBean",url);
			if (log.isDebugEnabled()) {
				log.debug("created JNLP URL: " + url);
			}
		} else {
			forward="nouser";
			log.error("No user found!");
		}

		return mapping.findForward(forward);
	}

}
