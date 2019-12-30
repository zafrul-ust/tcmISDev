package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.hub.beans.PickingUnitDocumentInputBean;
import com.tcmis.internal.hub.process.PickingUnitDocumentProcess;

public class PickingUnitDocumentAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request,true)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "pickingunitdocuments");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// set the standalone flag to true since MSDS Maintenance should work as a standalone app
			request.setAttribute("standalone", "true");
			// Now send the user to the login page
			next = mapping.findForward("login");
		}
		else {
			//PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			PickingUnitDocumentInputBean input = new PickingUnitDocumentInputBean(form);
			PickingUnitDocumentProcess process = new PickingUnitDocumentProcess(getDbUser());
			request.setAttribute("pickingUnitDocumentColl", process.search(input));
		}
		
		return next;
	}
}
