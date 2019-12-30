package com.tcmis.client.het.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.het.beans.CartManagementInputBean;
import com.tcmis.client.het.beans.FxItemSearchBean;
import com.tcmis.client.het.process.CartManagementProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;


public class CartItemSearchAction extends TcmISBaseAction  {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "cartitemsearchmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else if (form != null) {
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			CartManagementInputBean input = new CartManagementInputBean(form, getTcmISLocale());
			CartManagementProcess process = new CartManagementProcess(getDbUser(), getTcmISLocale());

			if (input.isSearch()) {
				Collection<FxItemSearchBean> results = process.getCartItemSearchData(input, user);
				input.setTotalLines(results.size());
				request.setAttribute("cartItemCollection", results);
				request.setAttribute("input", input);
			}
		}

		return next;
	}

}
