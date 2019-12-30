package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ItemConsignmentInputBean;
import com.tcmis.internal.hub.process.ItemConsignmentProcess;

/******************************************************************************
 * Controller for receiving
 * 
 * @version 1.0
 ******************************************************************************/
public class ItemConsignmentAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	private void doSearch(HttpServletRequest request, ItemConsignmentProcess process, ItemConsignmentInputBean input, PersonnelBean user) throws BaseException {
		// Determine whether this Inventory Group is count by Item or Receipt, used in the search and in the results display
		if (user.getInventoryGroup(input.getHub(), input.getInventoryGroup()).isReceiptUsageInventoryGroup()) {
			input.setCountBy(ItemConsignmentInputBean.COUNT_BY_RECEIPT);
		}
		else {
			input.setCountBy(ItemConsignmentInputBean.COUNT_BY_ITEM);
		}

		Collection results = process.getSearchResult(input, user);
		request.setAttribute("resultsCollection", results);
		input.setTotalLines(results.size());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			// Tell the login process where to return to
			request.setAttribute("requestedPage", "itemconsignmentmain");
			// Save any parameters passed in the URL, for the after login return
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			// Now send the user to the login page
			return (mapping.findForward("login"));
		}

		// Get the user
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

		// Verify view permissions
		if (!user.getPermissionBean().hasUserPagePermission("itemConsignment")) {
			return (mapping.findForward("nopermissions"));
		}

		Locale locale = getTcmISLocale();

		// Get the user/page input in a usable form as an input bean
		ItemConsignmentInputBean input = new ItemConsignmentInputBean(form, locale);
		// Stick the bean back in the session for the hidden field tag
		request.setAttribute("inputBean", input);
		
		ItemConsignmentProcess process = new ItemConsignmentProcess(getDbUser(), locale);

		// Search ?
		if (input.isSearch()) {
			doSearch(request, process, input, user);
		}
		// Create Excel?
		else if (input.isCreateExcel()) {
			try {
				if (user.getInventoryGroup(input.getHub(), input.getInventoryGroup()).isReceiptUsageInventoryGroup()) {
					input.setCountBy(ItemConsignmentInputBean.COUNT_BY_RECEIPT);
				}
				else {
					input.setCountBy(ItemConsignmentInputBean.COUNT_BY_ITEM);
				}
				setExcel(response, "Item Consignment");
				process.getExcelReport(input, user).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}
		// Do Update?
		else if (input.isUpdate()) {
			// All of the other options require update permissions
			// Check if the user has permissions to update
			if (user.getPermissionBean().hasInventoryGroupPermission("itemConsignment", null, null, null)) 
			{
				checkToken(request);

				// Grab the input rows that have the OK box checked
				Collection<ItemConsignmentInputBean> rows = BeanHandler.getGridBeans((DynaBean) form, "ItemConsignmentInputBean", input, locale, "rowUpdated");

				// Do the update
				request.setAttribute("tcmisError", process.update(input, rows, user));

				// Refresh the search
				doSearch(request, process, input, user);
			}
			else 
			{
				request.setAttribute("tcmisError", getResourceBundleValue(request, "error.noaccesstopage"));
			}

		}
		saveTcmISToken(request);
		
		return mapping.findForward("success");
	}

}
