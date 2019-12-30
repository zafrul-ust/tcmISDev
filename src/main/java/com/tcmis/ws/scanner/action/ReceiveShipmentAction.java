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
import com.tcmis.ws.scanner.beans.Shipment;
import com.tcmis.ws.scanner.process.ShipmentReceivingProcess;

public class ReceiveShipmentAction extends BaseScannerAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();

			try {
				JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
				if (!input.has("shipments")) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "At least one shipment is required");
					results.put("success", false);
				}
				else {
					JSONArray errors = new JSONArray();
					JSONArray shipments = input.getJSONArray("shipments");
					int i = 0;
					try {
						for (i = 0; i < shipments.length(); i++) {
							Shipment curShipment = new Shipment();
							BeanHandler.getJsonBeans(shipments.getJSONObject(i), curShipment);
							if (curShipment.isValid()) {
								ShipmentReceivingProcess process = new ShipmentReceivingProcess(getDbUser());
								process.receiveShipment(curShipment, errors);
							}
							else {
								JSONObject error = new JSONObject();
								error.put("id", curShipment.hasId() ? curShipment.getId() : "UNKNOWN");
								error.put("errorMessage",
										"Invalid SHIPMENT " + curShipment.getShipmentId() + " missing one of (hub, trackingNumber, personnelId and at least one receipt)");
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
							results.put("message", i + " shipments successfully received.");
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
			log.error("Base Error in ReceiveShipmentAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}

}
