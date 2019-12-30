package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;
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

import com.tcmis.client.common.beans.*;
import com.tcmis.client.catalog.process.PointOfSaleProcess;


/**
 * ***************************************************************************
 * Controller for tap source
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class TapSourceAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "tapsource");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form == null) return (mapping.findForward("success"));

		String action = (String) ((DynaBean) form).get("uAction");
		if (action == null) return mapping.findForward("success");
		PointOfSaleProcess process = new PointOfSaleProcess(this.getDbUser(request), getTcmISLocaleString(request));

		//copy date from dyna form to the data form
	 	PointOfSaleInventoryViewBean bean = new PointOfSaleInventoryViewBean();
	 	BeanHandler.copyAttributes(form, bean);
		if ("getTapData".equalsIgnoreCase(action)) {
			log.debug("here in getTapData");
			//reload data
			request.setAttribute("tappableColl",process.getTappableData(bean));
			request.setAttribute("binColl", process.getValidBinData(bean));
		}else {
			//do nothing for now
		}
		return mapping.findForward("success");
	}
}
