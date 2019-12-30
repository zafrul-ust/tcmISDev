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
import com.tcmis.ws.scanner.beans.DepartureShipment;
import com.tcmis.ws.scanner.beans.Pick;
import com.tcmis.ws.scanner.beans.PickingUnit;
import com.tcmis.ws.scanner.beans.Shipment;
import com.tcmis.ws.scanner.process.DepartureScanProcess;
import com.tcmis.ws.scanner.process.PickCompleteProcess;
import com.tcmis.ws.scanner.process.PickRejectProcess;
import com.tcmis.ws.scanner.process.ShipmentReceivingProcess;

public class UpdateShipmentAction extends BaseScannerAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();

			try {
				JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
				if (!input.has("shipments")) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "At least one shipment required");
					results.put("success", false);
				}
				else {
					try {
						JSONArray errors = new JSONArray();
						JSONArray shipments = input.getJSONArray("shipments");
						int i = 0;
						for (i = 0; i < shipments.length(); i++) {
							DepartureScanProcess process = new DepartureScanProcess(getDbUser());
							DepartureShipment shipment = (DepartureShipment) BeanHandler.getJsonBeans(shipments.getJSONObject(i), new DepartureShipment());
							if (shipment.hasTabletShipmentId()) {
								process.updateShipment(shipment, errors);
							}
							else {
								JSONObject error = new JSONObject();
								error.put("id", shipment.getId());
								error.put("errorMessage", "Requires tabletShipmentId");
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
							results.put("message", "Shipment updated.");
						}
					}
					catch (Exception ex) {
						log.error("Error updating shipment", ex);
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
				log.error("Error updating shipment", ex);
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
			log.error("Base Error in UpdateShipmentAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}

}
