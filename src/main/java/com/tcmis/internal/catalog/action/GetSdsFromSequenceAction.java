package com.tcmis.internal.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.common.framework.GenericProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.framework.TcmISBaseAction;

public class GetSdsFromSequenceAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "getsdsfromsequence");
			request.setAttribute("requestedURLWithParameters",
			this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		ActionForward next = mapping.findForward("success");
		GenericProcess genericProcess = new GenericProcess(getDbUser());
		request.setAttribute("newSdsNumber",genericProcess.getSingleValue("select customer_sds_seq.nextval from dual"));
		return next;
	}
}
