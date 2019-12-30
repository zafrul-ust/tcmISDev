package com.tcmis.internal.hub.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.HubCycleCountDisplayViewBean;
import com.tcmis.internal.hub.beans.HubCycleCountInputBean;
import com.tcmis.internal.hub.process.HubCycleCountProcess;

public class HubCycleCountAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "hubcyclecountmain");
			//This is to save any parameters passed in the URL, so that they may be accessed after the login
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//Get the user
		PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

		//Verify view permissions
		if (!user.getPermissionBean().hasUserPagePermission("hubCycleCount")) {
			return (mapping.findForward("nopermissions"));
		}

		String forward = "success";
		//Copy date from dyna form to the data bean
		HubCycleCountInputBean input = new HubCycleCountInputBean(request, form, getTcmISLocale());
		request.setAttribute("HubCycleCountInputBean", input);

		Locale locale = getTcmISLocale();
		HubCycleCountProcess process = new HubCycleCountProcess(getDbUser(), locale);

		//Search
		if (input.isSearch()) {
			doSearch(request, process, input);
		} else if (input.isAllRowsCounted()) { //verify All rows Counted
			int rowsNotCounted = process.verifyAllReceiptsCounted(input);				
			request.setAttribute("AllRowsCounted", rowsNotCounted );
			PrintWriter out = response.getWriter();
			out.write(rowsNotCounted+"");
			out.close();
			return noForward;	
		} else if (input.isValidCreateNewCountId()) { //verify can create new countid for the month
			JSONObject dataObj = new JSONObject();
			boolean valid = process.startNewCountForCurrMonthValid(input);
			dataObj.put("CreateCountId", valid);
			dataObj.put("CountMonth", input.getCountMonth());
			request.setAttribute("CreateCountId", valid );
			PrintWriter out = response.getWriter();
			out.write(dataObj.toString(3));
			out.close();
			return noForward;	
		} else if (input.isNextMonthCountStarted()) {
			boolean valid = process.isNextMonthCountStarted(input);			
			request.setAttribute("nextMonthCount", valid );
			PrintWriter out = response.getWriter();
			out.write(valid+"");
			out.close();
			return noForward;	
		}
		//All of the other options require update permissions. Check if the user has permissions to update
		else if (input.isStartNewCount() ||  input.isCloseCount() || input.isSave() ) {
			this.checkToken(request);
			//Grab the input rows
			Collection<HubCycleCountInputBean> rows = BeanHandler.getBeans((DynaBean) form, "HubCycleCountDisplayViewBean", input, locale);
			if (input.isStartNewCount()) { //Start New Count?
				HashMap<String, String> retMessages = process.startCount(input, user);
				String countId = "";
				//Start the count and stick any error messages in the session for display
				if (retMessages != null && (String)retMessages.get("COUNTID") != null) {
					countId = (String)retMessages.get("COUNTID");
					input.setCountId(new BigDecimal(countId));
				}				
				if (retMessages != null && (String)retMessages.get("ERROR") != null)
					request.setAttribute("tcmisError", (String)retMessages.get("ERROR"));
				doSearch(request, process, input);
			} else if (input.isCloseCount()) { //Close Count?
				request.setAttribute("tcmisError", process.closeCount(input, user));				
				doSearch(request, process, input);
			} else if (input.isSave()) { //Or save?
				request.setAttribute("tcmisError", process.save(input, rows, user));
				//Refresh the search
				doSearch(request, process, input);
			}
		}
		this.saveTcmISToken(request);
		return mapping.findForward(forward);
	}

	private void doSearch(HttpServletRequest request, HubCycleCountProcess process, HubCycleCountInputBean input) {
		try {
			BigDecimal currCountId = process.getCurrentCountId(input);
			input.setCountId(currCountId);
			Collection<HubCycleCountDisplayViewBean> results = process.getSearchResult(input);
			request.setAttribute("HubCycleCountViewBeanCollection", results);
			request.setAttribute("countId", input.getCountId());
			request.setAttribute("countMonth", input.getCountMonth());
			if (results != null && !results.isEmpty())
				input.setTotalLines(results.size());
			else 
				input.setTotalLines(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
