/******************************************************
 * UpdatePricesAction.java
 * 
 * Search DbuyContractPrice table on the given id
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


public class ViewPricesAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// servlet params
		String contractId = null;
		String catPart = null;

		String forward = "success";

		contractId = request.getParameter("contract_id");
		catPart = request.getParameter("cat_part_no");
		if(log.isDebugEnabled()) {
			log.debug("got contract id: " + contractId);
			log.debug("got cat part no:" + catPart);
		}

		HttpSession session = request.getSession();

		Collection itemPrices = null;

		String client = "TCM_OPS";   //this.getDbUser(request);
		ContractProcess contractProcess = new ContractProcess(client);

		// get the materials bean from the session (so we can computer total number of rows)
		itemPrices = contractProcess.getItemPrices(contractId);
		// set the session beans
		session.setAttribute("ContractPriceBeans",itemPrices);
		session.setAttribute("CatPartNoBean",catPart);
		session.removeAttribute("UpdatePriceMessage");

		log.debug("set attributes");

		return mapping.findForward(forward);
	}

}
