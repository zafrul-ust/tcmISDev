package com.tcmis.internal.hub.action;

import java.io.File;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.process.CycleCountProcess;

public class CycleCountAction
	extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	/*if (!this.isLoggedIn(request)) {
	  request.setAttribute("requestedPage", "showcyclecountresults");
	  return (mapping.findForward("login"));
	}*/

	if (request.getParameter("uploadId") == null) {
	  return mapping.findForward("systemerrorpage");
	}
	else {
	  String uploadId = request.getParameter("uploadId");

	  //call the process here
	  CycleCountProcess cycleCountProcess = new CycleCountProcess(this.
		  getDbUser(request));

	  boolean excelReport = false;
	  if ( (form != null) && ( (DynaBean) form).get("buttonCreateExcel") != null &&
		  ( (String) ( (DynaBean) form).get("buttonCreateExcel")).length() > 0) {
	   excelReport = true;
	  }

	  Collection c = cycleCountProcess.getsearchResult(uploadId,excelReport);
	  request.setAttribute("scannedReceiptReportViewBeanCollection",cycleCountProcess.createRelationalObject(c));

	  if (excelReport) {

		ResourceLibrary resource = new ResourceLibrary("report");

		File dir = new File(resource.getString("excel.report.serverpath"));
		File file = File.createTempFile("excel", ".xls", dir);
		cycleCountProcess.writeExcelFile(cycleCountProcess.createRelationalObject(c),
							   file.getAbsolutePath(),request.getLocale());
		this.setSessionObject(request,
							  resource.getString("report.hosturl") +
							  resource.getString("excel.report.urlpath") +
							  file.getName(), "filePath");

		request.setAttribute("doexcelpopup", "Yes");
	  }

	  Collection missingRecipts = cycleCountProcess.getMissingReceipts(uploadId);
	  request.setAttribute("scannedBinMissingReceiptBeanCollection",missingRecipts);

	  //log.info("Upload Id : "+uploadId+"");
	  request.setAttribute("uploadId", uploadId);
	  return mapping.findForward("showresults");
	}
  } //end of method
} //end of class
