package com.tcmis.internal.supply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.BuyOrdersInputBean;
import com.tcmis.internal.supply.process.BuyOrdersProcess;


/**
 * ***************************************************************************
 * Controller for load po supplier
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class LoadPoSupplierAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "loadposupplier");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form == null) return (mapping.findForward("success"));

		BuyOrdersInputBean bean = new BuyOrdersInputBean();
		BeanHandler.copyAttributes(form, bean);
		BuyOrdersProcess process = new BuyOrdersProcess(this.getDbUser(request),this.getTcmISLocaleString(request));
		request.setAttribute("poSupplierColl", process.getPoSupplier(bean));

		return mapping.findForward("success");
	}
}
