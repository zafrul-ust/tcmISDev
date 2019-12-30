package com.tcmis.client.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.ChemicalListDetailTblBean;
import com.tcmis.client.catalog.process.ChemicalApprovalReviewProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;

import com.tcmis.common.admin.beans.FinancialApproverTreeViewBean;

import java.util.Date;

/******************************************************************************
 * Controller for New Chem List Data Action
 * @version 1.0
 ******************************************************************************/

public class NewChemListDataAction extends TcmISBaseAction{

	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws  BaseException, Exception 
			{
		if (!this.isLoggedIn(request)) 
		{
			request.setAttribute("requestedPage", "shownewchemlistdetail");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}


		ChemicalApprovalReviewProcess process = new ChemicalApprovalReviewProcess(this.getDbUser(request),this.getTcmISLocaleString(request));
        Object results[] = process.getReviewResult(request.getParameter("companyId"),request.getParameter("requestId"),request.getParameter("showPassAndFailReviewRules"));
        request.setAttribute("archivedDate",results[0]);
        request.setAttribute("beanCollection",results[1]);
		return (mapping.findForward("success"));
	}
}
