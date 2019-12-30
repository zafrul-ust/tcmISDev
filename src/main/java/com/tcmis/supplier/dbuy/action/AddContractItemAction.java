/******************************************************
 * AddContractItemAction.java
 * 
 * Search DbuyContractPrice table on the given id
 ******************************************************
 */
package com.tcmis.supplier.dbuy.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.supplier.dbuy.beans.DbuyContractInputBean;
import com.tcmis.supplier.dbuy.process.ContractProcess;


public class AddContractItemAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String forward = "success";

		String client = "TCM_OPS";  //"TEST"; // "TCM_OPS";   //this.getDbUser(request);
		ContractProcess contractProcess = new ContractProcess(client);
		String result = null;

		DbuyContractInputBean inputBean = new DbuyContractInputBean();
		BeanHandler.copyAttributes(form, inputBean);
		if(log.isDebugEnabled()) {
			log.debug("inputBean.itemId=" + inputBean.getItemId().toString());
			log.debug("inputBean.carrier=" + inputBean.getCarrier());
			log.debug("inputBean.criticalCarrier=" + inputBean.getCriticalOrderCarrier());
		}
		try {
			result = contractProcess.addContractItem(inputBean);    //  .testAddItem(inputBean); // .addContractItem(inputBean);    //
			if (result!=null && result.length()>0) {
				request.setAttribute("ResultMessage", result);
			} else {
				request.setAttribute("ResultMesssage","Item Added");
			}
		} catch (BaseException be) {
			forward = "error";
			request.setAttribute("ErrorMessage",be.getMessage());
		}
		return mapping.findForward(forward);
	}
}
