/******************************************************
 * OrderSearchAction.java
 *
 * Handle results from the Attribute Query form
 ******************************************************
 */
package com.tcmis.internal.hub.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.hub.process.EdiOrderStatusProcess;


public class EdiOrderSearchAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// session beans (in)
		PersonnelBean user = null;
		// parameters
		String searchField = null;
		String operator = null;
		String value = null;
		String companyId = null;
		// session beans (out)
		Collection statusBeanCollection = null;

		String searchValueBean = "";
		String searchFieldBean = "";
		String searchOperatorBean = "";
		String forward = "Success";
		HttpSession session = request.getSession();

		searchField = request.getParameter("searchfield");
		operator = request.getParameter("operator");
		value = request.getParameter("searchvalue");
		companyId = request.getParameter("companyid");
		searchValueBean = value;
		searchFieldBean = searchField;
		searchOperatorBean = operator;

		user = (PersonnelBean) session.getAttribute("personnelBean");
		if (user==null) {
			forward = "NoLogin";
		} else {
			String client = this.getDbUser(request);
			EdiOrderStatusProcess orderstatusProcess = new EdiOrderStatusProcess(client);
			try {
				if (log.isDebugEnabled()) {
					log.debug("searching, for: "+searchField+ " " + operator + " " + value);
				}
				statusBeanCollection = orderstatusProcess.getSearchCollection(companyId, searchField, operator, value.trim());
			} catch (BaseException be) {
				forward = "Error";
				orderstatusProcess = null;
			}
		}

		session.setAttribute("errorviewBeanCollection",statusBeanCollection);
		session.setAttribute("SearchValueBean",searchValueBean);
		session.setAttribute("SearchFieldBean",searchFieldBean);
		session.setAttribute("SearchOperatorBean",searchOperatorBean);

		return mapping.findForward(forward);
	}
}
