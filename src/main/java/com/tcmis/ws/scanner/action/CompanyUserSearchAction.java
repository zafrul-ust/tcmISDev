package com.tcmis.ws.scanner.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.ws.scanner.process.CompanyUserSearchProcess;
import com.tcmis.ws.scanner.process.UserSearchProcess;

public class CompanyUserSearchAction extends BaseScannerAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();
			if (!isLoggedIn(request, true)) {
				results.put("errorMessage", RESPONSE_SESSION_EXPIRED);
				results.put("success", false);
			}
			else {
				CompanyUserSearchProcess process = new CompanyUserSearchProcess(getDbUser());
				JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();

				try {
					results.put("users", process.getCompanyUsers(input));
					results.put("pageData", process.getPageData());
					results.put("success", true);
				}
				catch (Exception ex) {
					log.error("Error searching DB", ex);
					results.put("errorCode", RESPONSE_ERROR);
					results.put("errorMessage", ex.toString());
					results.put("success", false);
				}
			}

			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
		}
		catch (Exception e) {
			log.error("Base Error in UserSearchAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}

}
