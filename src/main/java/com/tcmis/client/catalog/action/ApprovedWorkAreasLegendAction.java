package com.tcmis.client.catalog.action;

import java.util.Collection;
import java.util.Date;

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
public class ApprovedWorkAreasLegendAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {
	request.setAttribute("startSearchTime", new Date().getTime());
	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "approvedworkareaslegend");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}
	 UseApprovalDetailViewBean inputBean = new UseApprovalDetailViewBean();
	 BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

	 //call the process here
	 ApprovedWorkAreasProcess approvedWorkAreasProcess = new ApprovedWorkAreasProcess(this.getDbUser(request));

	 request.setAttribute("approvedWorkAreasBeanCollection",approvedWorkAreasProcess.getApprovalCodeDefinition(inputBean));
	 request.setAttribute("endSearchTime", new Date().getTime());
     return mapping.findForward("success");

 }
}
