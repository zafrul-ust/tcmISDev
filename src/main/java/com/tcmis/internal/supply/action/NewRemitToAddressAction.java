package com.tcmis.internal.supply.action;

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
import com.tcmis.internal.supply.beans.*;
import com.tcmis.internal.supply.process.NewRemitToAddressProcess;

public class NewRemitToAddressAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "newremittoaddress");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return mapping.findForward("login");
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("supplierSearch"))
			return (mapping.findForward("nopermissions"));

		NewRemitToAddressProcess process = new NewRemitToAddressProcess(this.getDbUser(request), this.getLocale(request));
		NewRemitToAddressInputBean inputBean = new NewRemitToAddressInputBean();
		BeanHandler.copyAttributes(form, inputBean);
		
		//depends on whether this is the first time or not, callerName will either be a paramater or an attribute
		request.setAttribute("callerName", request.getParameter("callerName") != null ? request.getParameter("callerName") : (String) request.getAttribute("callerName"));
		
		String uAction = (String) ((DynaBean) form).get("uAction");
		if ("new".equals(uAction)) {
			String err = process.callCreateRemitTo(inputBean, personnelBean);
			if (err.trim().length() > 0)
				request.setAttribute("tcmISError", err);
			else
				request.setAttribute("done", "Y");
		}

		return mapping.findForward("success");
	}
}