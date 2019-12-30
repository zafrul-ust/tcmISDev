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
import com.tcmis.ws.scanner.beans.Cylinder;
import com.tcmis.ws.scanner.process.CylinderTrackingProcess;

public class CylinderTrackingAction extends BaseScannerAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();

			try {
				JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
				if (!input.has("cylinders")) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "At least one cylinder is required");
					results.put("success", false);
				}
				else {
					try {
						JSONArray errors = new JSONArray();
						JSONArray cylinders = input.getJSONArray("cylinders");
						int i = 0;
						CylinderTrackingProcess process = new CylinderTrackingProcess(getDbUser());
						for (i = 0; i < cylinders.length(); i++) {
							Cylinder cylinder = (Cylinder) BeanHandler.getJsonBeans(cylinders.getJSONObject(i), new Cylinder());
							if (cylinder.isValid()) {
								process.trackCylinder(cylinder, errors);
							}
							else {
								JSONObject error = new JSONObject();
								error.put("id", cylinder.getId());
								error.put("errorMessage", cylinder.getInvalidMessage());
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
							results.put("message", i + " cylinders successfully tracked.");
						}
					}
					catch (Exception ex) {
						log.error("Error refurbing cylinder", ex);
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
				log.error("Error tracking cylinder", ex);
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
			log.error("Base Error in CylinderTrackingAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}

}
