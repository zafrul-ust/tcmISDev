/******************************************************
 * SortItemsAction.java
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


public class SortItemsAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// servlet params
		String field = null;
		String path = null;

		String forward = "success";

		field = request.getParameter("sortfield");
		if(log.isDebugEnabled()) {
			log.debug("got field: " + field);
		}

		HttpSession session = request.getSession();

		String client = "TCM_OPS";   //this.getDbUser(request);
		ContractProcess contractProcess = new ContractProcess(client);

		// get the materials bean from the session (so we can computer total number of rows)
		Collection itemBeans = (Collection) session.getAttribute("DbuyItemBeans");
		if (itemBeans!=null) {
			itemBeans = contractProcess.sortItems(itemBeans,field);
			// set the session beans
			session.setAttribute("DbuyItemBeans",itemBeans);
		}

		return mapping.findForward(forward);
	}

}
