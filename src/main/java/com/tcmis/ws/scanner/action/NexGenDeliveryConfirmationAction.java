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
import com.tcmis.ws.scanner.process.BinItemSearchProcess;
import com.tcmis.ws.scanner.process.NexGenDeliveryConfirmationProcess;

public class NexGenDeliveryConfirmationAction extends BaseScannerAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();
			if (!isLoggedIn(request, true)) {
				results.put("errorMessage", RESPONSE_SESSION_EXPIRED);
				results.put("success", false);
			}
			else {
				try {
					JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
					if (!input.has("personnelId")) {
						results.put("errorCode", RESPONSE_INVALID_INPUT);
						results.put("errorMessage", "personnelId is required");
						results.put("success", false);
					}
					else if (!input.has("confirmations") || input.getJSONArray("confirmations").length() == 0) {
						results.put("errorCode", RESPONSE_INVALID_INPUT);
						results.put("errorMessage", "At least one confirmations is required");
						results.put("success", false);
					}
					else {

						NexGenDeliveryConfirmationProcess process = new NexGenDeliveryConfirmationProcess(getDbUser());

						try {
							JSONArray errors = process.confirmDelivery(input.getJSONArray("confirmations"));
							if (errors != null && errors.length() > 0) {
								results.put("success", false);
								results.put("errorCode", RESPONSE_PARTIAL_SUCCESS);
								results.put("errorMessage", "Partial Success");
								results.put("errors", errors);
							}
							else {
								results.put("success", true);
							}
						}
						catch (Exception ex) {
							log.error("Error updating DB", ex);
							results.put("errorCode", RESPONSE_ERROR);
							results.put("errorMessage", ex.toString());
							results.put("success", false);
						}
					}
				}
				catch (JSONException jse) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "Input is not valid JSON");
					results.put("success", false);
				}
			}

			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
		}
		catch (Exception e) {
			log.error("Base Error in NexGenDeliveryConfirmationAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}
}
