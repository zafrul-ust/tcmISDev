/******************************************************
 * SelectInvGroupAction.java
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


public class SelectInvGroupAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// servlet params
		String field = null;
		String path = null;

		String forward = "success";

		//field = (String) request.getParameter("sortfield");
		log.debug("in SelectInvGroupAction");

		HttpSession session = request.getSession();

		String client = "TCM_OPS";   //this.getDbUser(request);
		ContractProcess contractProcess = new ContractProcess(client);

		Collection inventoryGroupBeans = contractProcess.getAllInventoryGroups();

		// set the session beans
		session.setAttribute("InvGroupBeans",inventoryGroupBeans);

		if(log.isDebugEnabled()) {
			log.debug("got InvGroupBeans: "+ inventoryGroupBeans);
		}

		return mapping.findForward(forward);
	}

}
