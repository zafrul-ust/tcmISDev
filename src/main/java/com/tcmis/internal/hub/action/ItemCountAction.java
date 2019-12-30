package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.HashSet;
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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ItemCountDisplayViewBean;
import com.tcmis.internal.hub.beans.ItemCountInputBean;
import com.tcmis.internal.hub.process.ItemCountProcess;

/******************************************************************************
 * Controller for receiving
 * 
 * @version 1.0
 ******************************************************************************/
public class ItemCountAction extends TcmISBaseAction {

	private void doSearch(HttpServletRequest request, ItemCountProcess process, ItemCountInputBean input) throws BaseException {
		Collection<ItemCountDisplayViewBean> results = process.getSearchResult(input);
		request.setAttribute("ItemCountViewBeanCollection", results);
		input.setTotalLines(results.size());
		if (results.size() > 0) {
			HashSet<String> activeCounts = new HashSet<String>();
			for (ItemCountDisplayViewBean row : results) {
				activeCounts.add(row.getInventoryGroup());
			}
			input.setInventoryGroup(activeCounts.toArray(new String[activeCounts.size()]));
		}
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "itemcountmain");
			// This is to save any parameters passed in the URL, so
			// that they may be accessed after the login
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		// Get the user
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

		// Verify view permissions
		if (!user.getPermissionBean().hasUserPagePermission("itemCount")) {
			return (mapping.findForward("nopermissions"));
		}

		String forward = "success";

		// copy date from dyna form to the data bean
		ItemCountInputBean input = new ItemCountInputBean(request, form, getTcmISLocale());
		request.setAttribute("itemCountInputBean", input);

		// Find out if we are dealing with a Hub that has Inventory
		// Group Collections
		// if (input.hasHub() && !input.isAddItem()) {
		// input.setCollection(user.getHub(input.getHub()).hasInventoryGroupThatIsCollection());
		// }

		if (input.hasInventoryGroup() && !input.isAddItem() && !input.isSelectCatPartNo()) {
			input.setCollection(user.getOpsEntityInventoryGroup(input.getOpsEntityId(), input.getHub(), input.getInventoryGroup()[0]).isInventoryGroupCollection());
		}

		Locale locale = getTcmISLocale();
		ItemCountProcess process = new ItemCountProcess(getDbUser(), locale);

		if(!(StringHandler.isBlankString(input.getuAction()) || input.isAddItem() || input.isAddItemSearch()))
		{
			String isProcessDisbursementVal = process.isProcessDisbursement(input.getInventoryGroup()[0]);
		
			if("Y".equalsIgnoreCase(isProcessDisbursementVal))
			{
				forward ="successdisburse";
				input.setIsProcessDisbursement(isProcessDisbursementVal);				
			}
			else if(isProcessDisbursementVal.indexOf("error") != -1)
				request.setAttribute("tcmisError", isProcessDisbursementVal);
		}
			
		// Search ?
		
		if (input.isSearch()) {
			doSearch(request, process, input);
		}
		else if (input.isSelectCatPartNo()) {
			request.setAttribute("ItemCountViewBeanCollection", process.getSearchResult(input));
			forward = "selectcatpartno";
		}
		else if (input.isAddItemSearch()) {
			Collection results = process.getAddItemSearchResult(input);
			request.setAttribute("AddItemSearchResults", results);
			input.setTotalLines(results.size());
		}
		// Create Excel?
		else if (input.isCreateExcel()) {
			try {
				setExcel(response, "Item Count");
				process.getExcelReport(input).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				forward = "genericerrorpage";
			}
			return noForward;
		}
		// All of the other options require update permissions
		// Check if the user has permissions to update
		else if (input.isStartNewCount() || input.isCloseCount() || input.isUpdate() || input.isAddItemUpdate()) {
			if (user.getPermissionBean().hasInventoryGroupPermission("Hub ItemCount", null, null, null)) {
				checkToken(request);
				// Grab the input rows
				Collection<ItemCountInputBean> rows = BeanHandler.getBeans((DynaBean) form, "ItemCountDisplayViewBean", input, locale);

				// Start New Count?
				if (input.isStartNewCount()) {
					try {
						// Start the count and stick any error
						// messages
						// in the session for display
						request.setAttribute("tcmisError", process.startCount(input, user));
						doSearch(request, process, input);
					}
					catch (Exception ex) {
						ex.printStackTrace();
						forward = "genericerrorpage";
					}
				}
				// Close Count?
				else if (input.isCloseCount()) {
					try {
						// End the count after filtering out any
						// Inventory groups that aren't Open
						// and stick any error messages in the
						// session for display
						request.setAttribute("tcmisError", process.closeCount(input, user));
						doSearch(request, process, input);
					}
					catch (Exception ex) {
						ex.printStackTrace();
						forward = "genericerrorpage";
					}
				}
				// Or Update (Process Count)?
				else if (input.isUpdate()) {
					// Do the update after filtering out any
					// Inventory groups that aren't Open
					// and stick any error messages in the session
					// for display
					request.setAttribute("tcmisError", process.update(input, rows, user));
					// Refresh the search
					doSearch(request, process, input);
				}
				else if (input.isAddItemUpdate()) {
					request.setAttribute("tcmisError", process.addItemToCount(input));
					// Refresh the search
					doSearch(request, process, input);
				}
			}
			else {
				request.setAttribute("tcmisError", getResourceBundleValue(request, "error.noaccesstopage"));
			}

		}
		saveTcmISToken(request);
		return mapping.findForward(forward);
	}
}
