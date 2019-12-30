package com.tcmis.internal.catalog.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.ChemicalBean;
import com.tcmis.internal.catalog.process.CaseNumberSearchProcess;

/******************************************************************************
 * Controller for CasNumber Search page
 * @version 1.0
 ******************************************************************************/

public class CaseNumberSearchAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		String loginNeeded = request.getParameter("loginNeeded");
		
		if (!"N".equals(loginNeeded) && !this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "casnumbersearchmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

	//	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		String userAction = request.getParameter("userAction");
		String fromListManagement = request.getParameter("fromListManagement");
		boolean tradeSecret = !StringHandler.isBlankString(request.getParameter("tradeSecret"));
		String searchField = request.getParameter("searchField");
		String searchMode = request.getParameter("searchMode");
		String searchArgument = request.getParameter("searchArgument");
		CaseNumberSearchProcess process = new CaseNumberSearchProcess(this.getDbUser(request));

		if (userAction == null) {
			return (mapping.findForward("success"));
		}

		if (userAction.equals("search")) {
			Collection chemSynonymViewBeanCollection = process.getChemSynonymViewBeanCollection(searchField, searchMode, searchArgument, tradeSecret);
			request.setAttribute("fromListManagement", fromListManagement);
			request.setAttribute("chemSynonymViewBeanCollection", chemSynonymViewBeanCollection);
			this.saveTcmISToken(request);
		}
		else if (userAction.equals("new")) {
			return (mapping.findForward("newcasnumber"));
		}
		else if (userAction.equals("refresh")) {
			ChemicalBean inputBean = new ChemicalBean();
			BeanHandler.copyAttributes(form, inputBean);
		
			process.insertNewChemical(inputBean);
			request.setAttribute("chemicalname", inputBean.getPreferredName());
			request.setAttribute("casnumber", inputBean.getCasNumber());
			return (mapping.findForward("newcasnumber"));
		}
		else if ("createExcel".equals(userAction))
        {
            this.setExcel(response, "CasNumberExcel");
            process.getExcelReport(process.getChemSynonymViewBeanCollection(searchField, searchMode, searchArgument, tradeSecret),(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
            return noForward;
       }
		
		return (mapping.findForward("success"));
	}

}
