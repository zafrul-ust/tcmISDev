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
import com.tcmis.common.util.BeanHandler;
import com.tcmis.ws.scanner.beans.Pick;
import com.tcmis.ws.scanner.process.PickCompleteProcess;

public class PickCompleteAction extends BaseScannerAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();

			try {
				JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
				if (!input.has("picks")) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "At least one pick is required");
					results.put("success", false);
				}
				else {
					JSONArray errors = new JSONArray();
					JSONArray picks = input.getJSONArray("picks");
					int i = 0;
					try {
						PickCompleteProcess process = new PickCompleteProcess(getDbUser());
						for (i = 0; i < picks.length(); i++) {
							Pick curPick = new Pick();
							BeanHandler.getJsonBeans(picks.getJSONObject(i), curPick);
							if (curPick.isValid()) {
								process.completePick(curPick, errors);
							}
							else {
								JSONObject error = new JSONObject();
								error.put("id", curPick.getId());
								error.put("pickingUNitId", curPick.getPickingUnitId());
								error.put("errorMessage", curPick.getInvalidMessage());
								errors.put(error);
							}
						}
						if (errors.length() > 0) {
							results.put("errorCode", RESPONSE_PARTIAL_SUCCESS);
							results.put("errors", errors);
							results.put("success", false);
						}
						else {
							results.put("success", true);
							results.put("message", i + " picks successfully received.");
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

			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
		}
		catch (Exception e) {
			log.error("Base Error in PickCompleteAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}

}
