package com.tcmis.internal.catalog.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.catalog.process.NsnItemSearchProcess;

/******************************************************************************
 * Controller for Material Search page
 * @version 1.0
 ******************************************************************************/

public class NsnItemSearchAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "nsnitemsearchmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		String userAction = request.getParameter("uAction");

		try {
			if (userAction != null && userAction.equals("search")) {
				String searchArgument = request.getParameter("nsn");
				NsnItemSearchProcess process = new NsnItemSearchProcess(this.getDbUser(request));
				Collection nsnItemCollection = process.searchNsnItems(searchArgument);
	
				request.setAttribute("nsnItemCollection", nsnItemCollection);
			}
		} catch (BaseException e) {
			request.setAttribute("tcmisError", e.getMessage());
            log.error(e);
		}

		return (mapping.findForward("success"));
	}

}
