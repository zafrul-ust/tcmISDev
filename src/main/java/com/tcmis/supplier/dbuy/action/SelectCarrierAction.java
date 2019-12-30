/******************************************************
 * SelectCarrierAction.java
 * 
 * Sort the collection of items based in the given field.
 ******************************************************
 */
package com.tcmis.supplier.dbuy.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.dbuy.process.ContractProcess;


public class SelectCarrierAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// servlet params
		String invgrp = null;
		String path = null;

		String forward = "success";

		invgrp = request.getParameter("invgrp");
		if(log.isDebugEnabled()) {
			log.debug("in getCarriersAction, got invgrp:"+invgrp);
		}

		HttpSession session = request.getSession();

		String client = "TCM_OPS";   //this.getDbUser(request);
		ContractProcess contractProcess = new ContractProcess(client);

		Collection carrierBeans = contractProcess.getCarriers(invgrp);

		// set the session beans
		session.setAttribute("CarrierBeans",carrierBeans);

		if(log.isDebugEnabled()) {
			log.debug("got carrierBeans: "+ carrierBeans);
		}

		return mapping.findForward(forward);
	}

}
