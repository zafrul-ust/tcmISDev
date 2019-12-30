package com.tcmis.client.dla.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.client.dla.beans.DlaInventoryAdjustmentBean;
import com.tcmis.client.dla.process.DlaInventoryAdjustmentProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;


public class DlaInventoryAdjusmentAction extends TcmISBaseAction {
	private void doSearch(DlaInventoryAdjustmentProcess process, DlaInventoryAdjustmentBean input, PersonnelBean user) throws BaseException {
		input.setCatPartNo(input.getCatPartNo().trim());
		Collection <DlaInventoryAdjustmentBean> results = process.getSearchResult(input);
		input.setTotalLines(results.size());
		// Stick the results into the session
		request.setAttribute("searchResults", results);
		// stick the input form back in to the request for use by the tcmis:setHiddenFields tag
		request.setAttribute("searchInput", input);
		request.setAttribute("unitOfSale",  process.getUnitOfSale(input.getCatPartNo()));
		// save the token if update actions can be performed later.
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException {
		String next = "success";

		if (!isLoggedIn(request)) {				   
			request.setAttribute("requestedPage", "dlainventoryadjustmentmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			next = "login";
		}
		else {

			// If you need access to who is logged in
			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

			/* Need to check if the user has permissions to view this page. */
			if (user.getPermissionBean().hasUserPagePermission("dlaInventoryAdjustments")) 
			{
				// copy date from dyna form to the input bean
				DlaInventoryAdjustmentBean input = new DlaInventoryAdjustmentBean(form, getTcmISLocale());
				DlaInventoryAdjustmentProcess process = new DlaInventoryAdjustmentProcess(getDbUser(), getTcmISLocale());

				// Search
				if (input.isSearch()) {
					// Pass the result collection in request
					doSearch(process, input, user);
				}
				//  Update
				else if (input.isUpdate()) {

					/*Need to check if the user has permissions to update this data. if they do not have the permission
					we show a error message.*/
					//if (!user.getPermissionBean().hasOpsEntityPermission("supplierPriceList", null, null)) 
					if(false)
					{
						request.setAttribute("tcmISError", getResourceBundleValue(request, "error.noaccesstopage"));
						// repopulate the search results
						doSearch(process, input, user);
					}
					else 
					{

						// If the page is being updated I check for a valid token.
						//checkToken will aslo save token for you to avoid duplicate form submissions.
						checkToken(request);

						// get the data from grid, selecting ONLY those beans whose okDoUpdate box has been checked
						Collection<DlaInventoryAdjustmentBean> beans = BeanHandler.getBeans((DynaBean) form, "DlaInventoryAdjustmentBean", new DlaInventoryAdjustmentBean(), getTcmISLocale());

						// Do the update and set any errors into the request
						request.setAttribute("tcmISErrors", process.update((Vector<DlaInventoryAdjustmentBean>)beans));

						// After update the data, we do the re-search to refresh the window
						doSearch(process, input, user);
					}

				}
			}
			else {
				next = "nopermissions";
			}
		}
		return (mapping.findForward(next));
	}
}
