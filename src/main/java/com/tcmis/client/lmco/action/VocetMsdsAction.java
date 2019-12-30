package com.tcmis.client.lmco.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import java.math.BigDecimal;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.lmco.beans.VocetMsdsSearchViewBean;
import com.tcmis.client.lmco.process.VocetMsdsProcess;

/**
 * ***************************************************************************
 * Controller for Vocet Msds
 *
 * @version 1.0
 *          ****************************************************************************
 */

public class VocetMsdsAction extends TcmISBaseAction {
	private void doSearch(VocetMsdsProcess process, VocetMsdsSearchViewBean input) throws BaseException {
		Collection<VocetMsdsSearchViewBean> results = process.getSearchResult(input);
		// Stick the results into the session
		request.setAttribute("searchResults", results);
		// save the token if update actions can be performed later.
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "vocetmsdsmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		// If you need access to who is logged in
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");

		// copy date from dyna form to the input bean
		VocetMsdsSearchViewBean input = new VocetMsdsSearchViewBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));

		VocetMsdsProcess process = new VocetMsdsProcess(getDbUser(), getTcmISLocale());
		
		String uAction = request.getParameter("uAction");

		if ("search".equals(uAction)) {
				
			doSearch(process, input);
		}
		else if ("update".equals(uAction)) {

			/*Need to check if the user has permissions to update this data. if they do not have the permission
			we show a error message.*/
			if (!personnelBean.getPermissionBean().hasFacilityPermission("VocetProperties", input.getFacilityId(), null)) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				// repopulate the search results
				doSearch(process, input);
			}
			else {
				// If the page is being updated I check for a valid token.
				//checkToken will aslo save token for you to avoid duplicate form submissions.
				checkToken(request);

				// get the data from grid, selecting ONLY those beans whose okDoUpdate box has been checked
				Collection<VocetMsdsSearchViewBean> beans = BeanHandler.getBeans((DynaBean) form, "vocetMsdsBean", new VocetMsdsSearchViewBean(), getTcmISLocale());

				// Do the update and set any errors into the request
				request.setAttribute("tcmISErrors", process.update(beans, input, personnelBean));

				// After update the data, we do the re-search to refresh the window
				doSearch(process, input);
			}
		}
		else if ("createExcel".equals(uAction)) {
			setExcel(response, "DisplayExcel");
			process.getExcelReport(process.getSearchResult(input), getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else {
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

			OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.
					getDbUser(request));
			//BigDecimal personnelId = new BigDecimal(bean.getPersonnelId());
			request.setAttribute("myWorkareaFacilityViewBeanCollection",
					orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
			
			request.setAttribute("vocetStatusColl", process.getVocetStatusColl());
			request.setAttribute("vocetCoatCategoryColl", process.getVocetCoatCategoryColl());
			
		}
			
		return (mapping.findForward("success"));
	}
}
