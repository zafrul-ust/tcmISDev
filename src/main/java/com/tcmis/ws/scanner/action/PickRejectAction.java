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

import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.ws.scanner.beans.Pick;
import com.tcmis.ws.scanner.beans.Shipment;
import com.tcmis.ws.scanner.process.PickCompleteProcess;
import com.tcmis.ws.scanner.process.PickRejectProcess;
import com.tcmis.ws.scanner.process.ShipmentReceivingProcess;

public class PickRejectAction extends BaseScannerAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();

			try {
				JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
				if (!input.has("rejectedPicks")) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "At least one rejected pick is required");
					results.put("success", false);
				}
				else {
					try {
						JSONArray errors = new JSONArray();
						JSONArray rejectedPicks = input.getJSONArray("rejectedPicks");
						int i = 0;
						PickRejectProcess process = new PickRejectProcess(getDbUser());
						for (i = 0; i < rejectedPicks.length(); i++) {
							Pick reject = (Pick) BeanHandler.getJsonBeans(rejectedPicks.getJSONObject(i), new Pick());
							if (reject.hasPickingUnitId() && reject.hasPickPersonnelId()) {
								process.rejectPick(reject, errors);
							}
							else {
								JSONObject error = new JSONObject();
								error.put("pickingUnitId", reject.getPickingUnitId());
								error.put("errorMessage", "Must supply pickingUnitId && pickPersonnelId for rejected pick.");
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
							results.put("message", i + " picks successfully rejected.");
						}
					}
					catch (Exception ex) {
						log.error("Error rejecting pick", ex);
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
			catch (Exception ex) {
				log.error("Error rejecting pick", ex);
				results.put("errorCode", RESPONSE_ERROR);
				results.put("errorMessage", ex.toString());
				results.put("success", false);
			}

			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
		}
		catch (Exception e) {
			log.error("Base Error in PickRejectAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}

}
