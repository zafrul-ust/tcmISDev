package com.tcmis.internal.hub.action;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

public class TerminalStatusRootCauseAction
	extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	  request.setAttribute("requestedPage", "terminalstatusrootcause");
		request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	  return (mapping.findForward("login"));
	}
	
	PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");

	String lotStatus = request.getParameter("lotStatus");
	if (lotStatus == null)
	  lotStatus = "";

	String rowNumber = request.getParameter("rowNumber");
	if (rowNumber == null)
	 rowNumber = "";

	VvDataProcess vvDataProcess = new VvDataProcess(this.
	 getDbUser(request));

	if (lotStatus.length() > 0) {
	 Collection rootCauseCollection = vvDataProcess.getVvLotStatusRootCause(
		lotStatus, personnelBean);
	 Collection companyIdsCollection = vvDataProcess.getCompanyIds(lotStatus);

	 request.setAttribute("rootCauseCollection", rootCauseCollection);
	 request.setAttribute("companyIdsCollection", companyIdsCollection);
	 request.setAttribute("lotStatus", lotStatus);
	 request.setAttribute("rowNumber", rowNumber);

	 return mapping.findForward("showinput");
	}
	else {
	 return mapping.findForward("systemerrorpage");
	}
 }
} //end of class