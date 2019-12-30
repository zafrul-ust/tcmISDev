package com.tcmis.ws.scanner.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.ws.scanner.process.HubPrinterSearchProcess;

public class HubPrinterSearchAction extends BaseScannerAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();
			if (!isLoggedIn(request, true)) {
				results.put("errorMessage", RESPONSE_SESSION_EXPIRED);
				results.put("success", false);
			}
			else {
				HubPrinterSearchProcess process = new HubPrinterSearchProcess(getDbUser());
				results = getHubPrinters(jsonString, process);
			}

			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
		}
		catch(Exception e) {
			log.error("Base Error in HubPrinterSearchAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}
	
	public JSONObject getHubPrinters(String jsonString, HubPrinterSearchProcess process) throws JSONException {
		JSONObject results = new JSONObject();
		try {
			JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
			if (isFilterValid(input)) {
				try {
					JSONArray hubPrintersArray = process.getHubPrinters(input);
					results.put("printers",hubPrintersArray);
					results.put("pageData", process.getPageData());
					results.put("success", true);
				}
				catch(BaseException e) {
					log.error("Error searching DB", e);
					results.put("errorCode", RESPONSE_ERROR);
					results.put("errorMessage", e.getMessage());
					results.put("success", false);
				}
			}
			else {
				results.put("errorCode", RESPONSE_INVALID_INPUT);
				results.put("errorMessage", "filter is not in correct format: yyyy-MM-dd'T'HH:mm:ss");
				results.put("success", false);
			}
		}
		catch(JSONException jse) {
			results.put("errorCode", RESPONSE_INVALID_INPUT);
			results.put("errorMessage", "Input is not valid JSON");
			results.put("success", false);
		}
		return results;
	}
}
