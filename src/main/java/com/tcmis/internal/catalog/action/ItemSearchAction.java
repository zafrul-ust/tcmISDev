package com.tcmis.internal.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

import com.tcmis.internal.catalog.process.ItemSearchProcess;

/******************************************************************************
 * Controller for Item Search page
 * 
 * @version 2.0
 ******************************************************************************/

public class ItemSearchAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "itemsearchmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		ItemSearchProcess process = new ItemSearchProcess(this.getDbUser(request));
		String userAction = request.getParameter("uAction");

		if (form != null && userAction != null)
			if (userAction.equalsIgnoreCase("search")) {
				String searchArgument = request.getParameter("searchArgument");
				String mfgId = request.getParameter("mfgId");
				String excludeItemIds = request.getParameter("excludeItemIds");
				
				request.setAttribute("itemBeanCollection", process.getItemBeanCollection(mfgId, searchArgument, excludeItemIds));
				this.saveTcmISToken(request);
			}
		
		return mapping.findForward("success");
	}

}
