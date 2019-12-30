package com.tcmis.internal.hub.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

public class PrinterChoiceAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	/*if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "printcontainerlabels");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}*/

	String sourcePage = request.getParameter("sourcePage");
	if (sourcePage == null)
	 sourcePage = "";

	return (mapping.findForward(sourcePage));
 }
} //end of class