package com.tcmis.client.report.action;

import java.math.BigDecimal;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.DynaBean;
import com.tcmis.client.report.process.BatchReportViewerProcess;
import com.tcmis.client.report.beans.BatchReportViewerInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for batch report viewer
 * @version 1.0
	******************************************************************************/
public class BatchReportViewerAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "batchreportviewermain");
	 //This is to save any parameters passed in the URL, so that they
	 //can be acccesed after the login
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

   //If you need access to who is logged in
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

	if (form != null) {
		this.saveTcmISToken(request);

		BatchReportViewerProcess batchReportViewerProcess = new BatchReportViewerProcess(this.getDbUser(request));
		if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			request.setAttribute("batchReportBeanCollection",batchReportViewerProcess.getSearchResult(personnelId));
		  	return mapping.findForward("showresults");
		} else if (((DynaBean) form).get("action") != null && "createExcel".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			this.setExcelHeader(response);
			batchReportViewerProcess.createExcelReport(personnelId, response.getWriter(), request.getLocale());
			return (mapping.findForward("viewfile"));
		} else if (((DynaBean) form).get("action") != null && "deleteReport".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
			if (!this.isTcmISTokenValid(request, true)) {
				BaseException be = new BaseException("Duplicate form submission");
				be.setMessageKey("error.submit.duplicate");
				this.saveTcmISToken(request);
				throw be;
			}
			DynaBean dynaBean = (DynaBean) form;
			Collection beans = BeanHandler.getBeans(dynaBean, "batchReportViewerInputBean", new BatchReportViewerInputBean());
			batchReportViewerProcess.deleteReport(beans);
			request.setAttribute("batchReportBeanCollection",batchReportViewerProcess.getSearchResult(personnelId));
		  	return mapping.findForward("showresults");
		}
	}
	return mapping.findForward("success");
 }
} //end of class
