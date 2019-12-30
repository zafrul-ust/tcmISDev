package com.tcmis.client.catalog.action;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.catalog.process.ApprovedWorkAreasProcess;
import com.tcmis.client.catalog.beans.UseApprovalDetailViewBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class ApprovedWorkAreasAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "approvedworkareas");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	if (request.getParameter("facPartNo") == null ||
	 request.getParameter("partGroupNo") == null) {
	 return mapping.findForward("systemerrorpage");
	}else {
	 UseApprovalDetailViewBean inputBean = new UseApprovalDetailViewBean();
	 BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

	 //call the process here
	 ApprovedWorkAreasProcess approvedWorkAreasProcess = new ApprovedWorkAreasProcess(this.getDbUser(request));

	 request.setAttribute("approvedWorkAreasBeanCollection",approvedWorkAreasProcess.getsearchResult(inputBean));
	 request.setAttribute("useCodeRequired",approvedWorkAreasProcess.isUseCodeRequired(inputBean));
     return mapping.findForward("success");
	}
 }
}
