package com.tcmis.ws.scanner.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.ws.scanner.beans.PrintInput;
import com.tcmis.ws.scanner.process.PickingUnitStateUpdateProcess;
import com.tcmis.ws.scanner.process.PrintProcess;

public class PickingUnitStateUpdateAction extends BaseScannerAction {

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
					if (!input.has("personnelId") || !input.has("pickingUnitId")) {
						results.put("errorCode", RESPONSE_INVALID_INPUT);
						results.put("errorMessage", "personnelId and pickingUnitId are required");
						results.put("success", false);
					}
					else {
						PickingUnitStateUpdateProcess process = new PickingUnitStateUpdateProcess(getDbUser());
						try {
							String successMsg = "Picking unit state successfully updated.";
							process.updatePickingUnitState(input);
							
							// Automatically pring Hold sheet
							JSONArray errors = new JSONArray();
							String pickingState = input.optString("pickingState");
							if (pickingState.equals("PICK_HOLD") || pickingState.equals("QC_HOLD")) {
								successMsg += " Hold Sheet printed.";
								PrintInput printInput = new PrintInput();
								printInput.setId("AUTO_PRINT_ON_UPDATE");
								printInput.setPersonnelId(new BigDecimal(input.optInt("personnelId")));
								printInput.setPickingUnitId(input.optInt("pickingUnitId"));
								printInput.setPrintType("HoldSheet");
								if (input.has("printerId")) {
									printInput.setPrinterId(input.getInt("printerId"));
								}
								if (printInput.isValid()) {
									PrintProcess printProcess = new PrintProcess(getDbUser());
									printProcess.printHoldSheet(printInput, errors);
								}
								else {
									JSONObject error = new JSONObject();
									error.put("id", printInput.getId());
									error.put("errorMessage", "Print must contain printerId, pickUnitId & printType");
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
								results.put("message", successMsg);
							}
						}
						catch(Exception e) {
							log.error("Error searching DB", e);
							results.put("errorCode", RESPONSE_ERROR);
							results.put("errorMessage", e.toString());
							results.put("success", false);
						}
					}
					
				}
				catch (JSONException jse) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "Input is not valid JSON");
					results.put("success", false);
				}
				
				PrintWriter out = response.getWriter();
				out.write(results.toString(3));
				out.close();
			}
		}
		catch(IOException | JSONException e) {
			log.error("Base Error in PickingUnitStateUpdateAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}
}
