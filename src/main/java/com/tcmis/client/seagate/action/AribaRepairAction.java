package com.tcmis.client.seagate.action;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.client.seagate.beans.MrNeedingApprovalViewBean;
import com.tcmis.client.seagate.process.AribaRepairProcess;

/**
 * ***************************************************************************
 * Controller for
 *
 * @version 1.0
 *          ****************************************************************************
 */

public class AribaRepairAction extends TcmISBaseAction {
	private void doSearch(AribaRepairProcess process, MrNeedingApprovalViewBean input, PersonnelBean user) throws BaseException {
		Collection<MrNeedingApprovalViewBean> results = process.getSearchResult(input);
		input.setTotalLines(results.size());
		// Stick the results into the session
		request.setAttribute("searchResults", results);
		// stick the input form back in to the request for use by the tcmis:setHiddenFields tag
		request.setAttribute("searchInput", input);
		// save the token if update actions can be performed later.
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException {
		String next = "success";

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "aribarepairmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			next = "login";
		}
		else {

			// If you need access to who is logged in
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
				
				// copy date from dyna form to the input bean
				MrNeedingApprovalViewBean input = new MrNeedingApprovalViewBean(form, getTcmISLocale());

				AribaRepairProcess process = new AribaRepairProcess(getDbUser(), getTcmISLocale());

				if (input.isSearch()) {
					// Pass the result collection in request
					doSearch(process, input, user);
					request.setAttribute("actionDone", "search");
				}
			}
		return (mapping.findForward(next));
	}
}
