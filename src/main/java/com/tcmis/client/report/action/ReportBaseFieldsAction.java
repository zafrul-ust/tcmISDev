package com.tcmis.client.report.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.report.process.GenericReportProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for Report Base Fields
 * @version 1.0
     ******************************************************************************/
public class ReportBaseFieldsAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
	    
	  if (!this.isLoggedIn(request)) {
	      request.setAttribute("requestedPage", "reportbasefields");
	      request.setAttribute("requestedURLWithParameters",
	                           this.getRequestedURLWithParameters(request));
	      return (mapping.findForward("login"));
	  }
	  request.setAttribute("startSearchTime", new Date().getTime());
	 
	  String reportType = request.getParameter("reportType");
	  GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
	  request.setAttribute("baseFieldBeanCollection",genericReportProcess.getBaseFields(reportType));
	    
	  request.setAttribute("endSearchTime", new Date().getTime());
      return mapping.findForward("success");
  }
}