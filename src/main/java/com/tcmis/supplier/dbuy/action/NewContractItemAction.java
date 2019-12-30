/******************************************************
 * UpdatePricesAction.java
 * 
 * Search DbuyContractPrice table on the given id
 ******************************************************
 */
package com.tcmis.supplier.dbuy.action;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.supplier.dbuy.beans.DbuyContractBean;
import com.tcmis.supplier.dbuy.process.ContractProcess;


public class NewContractItemAction extends TcmISBaseAction {

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

		Collection items = null;

		String client = "TCM_OPS";   //this.getDbUser(request);
		ContractProcess contractProcess = new ContractProcess(client);

		DbuyContractBean contractBean = null;
		// get the materials bean from the session (so we can computer total number of rows)
		items = contractProcess.getContractItem(contractId,catPart);
		// set the session beans
		if (items != null && items.size()>0) {
			Iterator iter = items.iterator();
			if (iter.hasNext()) {
				contractBean = (DbuyContractBean) iter.next();
			} else {
				forward = "error";
			}
		} else {
			forward = "error";
		}
		session.setAttribute("contractBean",contractBean);

		return mapping.findForward(forward);
	}

}
