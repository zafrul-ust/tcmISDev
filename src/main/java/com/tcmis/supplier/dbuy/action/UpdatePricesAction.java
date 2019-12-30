/******************************************************
 * UpdatePricesAction.java
 * 
 * Search DbuyContractPrice table on the given id
 ******************************************************
 */
package com.tcmis.supplier.dbuy.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.dbuy.process.ContractProcess;


public class UpdatePricesAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String contractId = null;
		String newPrice = null;
		String endDate = null;
		String uptoQty = null;
		String currencyId = null;
		String comments = null;

		String forward = "success";

		contractId = request.getParameter("contract_id");
		newPrice = request.getParameter("price");
		endDate = request.getParameter("end_date");
		uptoQty = request.getParameter("up_to_quantity");
		currencyId = request.getParameter("currency_id");
		comments = request.getParameter("comments");

		if(log.isDebugEnabled()) {
			log.debug("got contract id: " + contractId);
			log.debug("got price: " + newPrice);
			log.debug("got end date: " + endDate);
			log.debug("got up to qty: " + uptoQty);
			log.debug("got currency id: " + currencyId);
			log.debug("got comments: " + comments);
		}

		HttpSession session = request.getSession();

		String client = "TCM_OPS";   //this.getDbUser(request);
		ContractProcess contractProcess = new ContractProcess(client);

		try {
			contractProcess.updateItemPrices(contractId,newPrice,endDate,uptoQty,currencyId,comments);
			session.setAttribute("UpdatePriceMessage","Price updated succesfully.");
		} catch (BaseException be) {
			forward = "error";
			session.setAttribute("ErrorMessage",be.getMessage());
		}
		//         session.setAttribute("ContractPriceBeans",itemPrices);

		return mapping.findForward(forward);
	}
}
