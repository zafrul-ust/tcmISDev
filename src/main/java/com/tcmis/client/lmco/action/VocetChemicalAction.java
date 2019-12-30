package com.tcmis.client.lmco.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.lmco.beans.VocetChemicalSearchViewBean;
import com.tcmis.client.lmco.beans.VocetMsdsSearchViewBean;
import com.tcmis.client.lmco.process.VocetChemicalProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/**
 * ***************************************************************************
 * Controller for Vocet Chemical
 *
 * @version 1.0
 *          ****************************************************************************
 */

public class VocetChemicalAction extends TcmISBaseAction {
	private void doSearch(VocetChemicalProcess process, VocetChemicalSearchViewBean input, PersonnelBean personnelBean) throws BaseException {
		Collection<VocetChemicalSearchViewBean> results = process.getSearchResult(input,personnelBean);
		// Stick the results into the session
		request.setAttribute("searchResults", results);
		// save the token if update actions can be performed later.
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "vocetchemicalmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		// If you need access to who is logged in
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");

//		 copy date from dyna form to the input bean
		VocetChemicalSearchViewBean input = new VocetChemicalSearchViewBean();
		BeanHandler.copyAttributes(form, input, getTcmISLocale(request));

		VocetChemicalProcess process = new VocetChemicalProcess(getDbUser(), getTcmISLocale());
		
		String uAction = request.getParameter("uAction");

		if ("search".equals(uAction)) {
				
			doSearch(process, input,personnelBean);
		}
		else if ("update".equals(uAction)) {

			/*Need to check if the user has permissions to update this data. if they do not have the permission
			we show a error message.*/
			if (!personnelBean.getPermissionBean().hasFacilityPermission("VocetProperties", input.getFacilityId(), null)) {
				request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
				// repopulate the search results
				doSearch(process, input,personnelBean);
			}
			else {
				// If the page is being updated I check for a valid token.
				//checkToken will aslo save token for you to avoid duplicate form submissions.
				checkToken(request);

				// get the data from grid, selecting ONLY those beans whose okDoUpdate box has been checked
				Collection<VocetChemicalSearchViewBean> beans = BeanHandler.getBeans((DynaBean) form, "vocetChemicalBean", new VocetChemicalSearchViewBean(), getTcmISLocale());

				// Do the update and set any errors into the request
				request.setAttribute("tcmISErrors", process.update(beans, input, personnelBean));

				// After update the data, we do the re-search to refresh the window
				doSearch(process, input,personnelBean);
			}
		}
		else if ("createExcel".equals(uAction)) {
			setExcel(response, "DisplayExcel");
			process.getExcelReport(process.getSearchResult(input,personnelBean), getTcmISLocale()).write(response.getOutputStream());
			return noForward;
		}
		else {
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

			OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request));
			//BigDecimal personnelId = new BigDecimal(bean.getPersonnelId());
			request.setAttribute("myWorkareaFacilityViewBeanCollection",
					orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
			
			request.setAttribute("vocetCategoryColl", process.getVocetCategoryColl());
			
			request.setAttribute("vocetSourceColl", process.getVocetSourceColl());
			
		}
			
		return (mapping.findForward("success"));
	}
}
