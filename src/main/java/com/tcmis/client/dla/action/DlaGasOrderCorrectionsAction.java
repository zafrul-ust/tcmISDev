package com.tcmis.client.dla.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.dla.beans.DlaGasOrderCorrectionsBean;
import com.tcmis.client.dla.process.DlaGasOrderCorrectionsProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.SqlHandler;


public class DlaGasOrderCorrectionsAction extends TcmISBaseAction {
	private void doSearch(DlaGasOrderCorrectionsProcess process, DlaGasOrderCorrectionsBean input, PersonnelBean user) throws BaseException, Exception {
		Collection <DlaGasOrderCorrectionsBean> results = process.getSearchResult(input, user);
		if(results == null)
			request.setAttribute("tcmISError",getResourceBundleValue(request, "error.db.select"));
		else
		{
			input.setTotalLines(results.size());
			// Stick the results into the session
			request.setAttribute("searchResults", results);
			// stick the input form back in to the request for use by the tcmis:setHiddenFields tag
			request.setAttribute("searchInput", input);
			// save the token if update actions can be performed later.
			saveTcmISToken(request);
			
			//prepare data for right-click menus
			if (results.size() > 0) {
				Map<String,String> m = new HashMap<String, String>();
				for (DlaGasOrderCorrectionsBean bean : results) {
					String prNumber = SqlHandler.validBigDecimal(bean.getPrNumber());
					String shipmentId = SqlHandler.validBigDecimal(bean.getShipmentId());
					m.put(prNumber + "," + shipmentId, process.getInvoices(prNumber, shipmentId));
				}
				request.setAttribute("invoicesData", m);
			}
		}
		
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		String next = "success";

		if (!isLoggedIn(request)) {				   
			request.setAttribute("requestedPage", "dlagasordercorrectionsmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			next = "login";
		}
		else {

			// If you need access to who is logged in
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			/* Need to check if the user has permissions to view this page. */
			if (user.getPermissionBean().hasUserPagePermission("dlaGasOrderCorrections")) 
			{
				// copy date from dyna form to the input bean
				DlaGasOrderCorrectionsBean input = new DlaGasOrderCorrectionsBean(form, getTcmISLocale());
				DlaGasOrderCorrectionsProcess process = new DlaGasOrderCorrectionsProcess(getDbUser(), getTcmISLocale());

				// Search
				if (input.isSearch()) {
					// Pass the result collection in request
					doSearch(process, input, user);
				}
				//  Update
				else if (input.isUpdate()) {

						// If the page is being updated I check for a valid token.
						//checkToken will aslo save token for you to avoid duplicate form submissions.
						checkToken(request);

						// get the data from grid, selecting ONLY those beans whose okDoUpdate box has been checked
						Collection<DlaGasOrderCorrectionsBean> beans = BeanHandler.getBeans((DynaBean) form, "Edi856CorrectionsBean", new DlaGasOrderCorrectionsBean(), getTcmISLocale());

						// Do the update and set any errors into the request
						request.setAttribute("tcmISErrors", process.update((Vector<DlaGasOrderCorrectionsBean>)beans));

						// After update the data, we do the re-search to refresh the window
						doSearch(process, input, user);
				}
				
				else if (input.isResubmit()) {
					//If the page is being updated I check for a valid token.
					//checkToken will also save token for you to avoid duplicate form submissions.
					checkToken(request);
					
					Collection errorMsgs = process.resubmitInvoice(input.getInvoiceToResubmitId());
					if (errorMsgs != null && errorMsgs.size() > 0)
						request.setAttribute("tcmISError",getResourceBundleValue(request, "error.db.update"));
					else
						request.setAttribute("tcmISError",getResourceBundleValue(request, "msg.updatesuccess"));
					// After update the data, we do the re-search to refresh the window
					doSearch(process, input, user);
				}
			}
			else {
				next = "nopermissions";
			}
		}
		return (mapping.findForward(next));
	}
}
