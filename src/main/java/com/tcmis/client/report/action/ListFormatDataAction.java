package com.tcmis.client.report.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.report.process.GenericReportProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;

public class ListFormatDataAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	    
		  if (!this.isLoggedIn(request)) {
		      request.setAttribute("requestedPage", "reportbasefields");
		      request.setAttribute("requestedURLWithParameters",
		                           this.getRequestedURLWithParameters(request));
		      return (mapping.findForward("login"));
		  }
		  request.setAttribute("startSearchTime", new Date().getTime());
		  
		  PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		  
		  GenericReportProcess genericReportProcess = new GenericReportProcess(this.getDbUser(request),getLocale(request));
		  
		  String strListId = (String) request.getParameter("listId");
		  request.setAttribute("listFormatDisplayResults", genericReportProcess.getListFormatDisplay(strListId, personnelBean.getCompanyId(), "Y"));
		  request.setAttribute("listId", strListId);
		    
		  request.setAttribute("endSearchTime", new Date().getTime());
	      return mapping.findForward("success");
	  }

}
