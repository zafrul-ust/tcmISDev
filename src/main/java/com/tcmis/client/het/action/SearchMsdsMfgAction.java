package com.tcmis.client.het.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.het.beans.CartManagementInputBean;
import com.tcmis.client.het.beans.FxItemSearchBean;
import com.tcmis.client.het.beans.MixtureManagementInputBean;
import com.tcmis.client.het.process.CartManagementProcess;
import com.tcmis.client.het.process.MixtureManagementProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;


public class SearchMsdsMfgAction extends TcmISBaseAction  {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "searchmsdsmfgmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else if (form != null) {
			
			MixtureManagementProcess process = new MixtureManagementProcess(getDbUser(), getTcmISLocale());
			MixtureManagementInputBean input = new MixtureManagementInputBean(form, getTcmISLocale());
			if (input.isSearch()) {
				request.setAttribute("msdsMfgColl", process.getMSDSMfgColl(input));
			}
		}

		return next;
	}

}
