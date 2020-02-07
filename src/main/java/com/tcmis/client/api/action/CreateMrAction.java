package com.tcmis.client.api.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.client.api.process.CreateMrProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.JsonRequestResponseAction;

public class CreateMrAction extends JsonRequestResponseAction {
	
	@Override
	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response,
			String jsonString) throws BaseException {
		JSONObject responseBody = null;
		try {
			StringBuilder requestDetails = new StringBuilder("Request Details:\n")
					.append("Content-Type: ").append(request.getHeader("Content-Type")).append("\n")
					.append("URI: ").append(request.getRequestURI()).append("\n")
					.append("Request Body: ").append(jsonString);
			log.debug(requestDetails.toString());
			
			CreateMrProcess process = new CreateMrProcess(this.getDbUser(request), this.getTcmISLocale(request));
			JSONObject json = process.transformJson(new JSONObject(jsonString), this.getPathCompany(request));
			
			String operation = json.getJSONObject("mrHeader").getString("operation");
			switch(operation) {
			case CreateMrProcess.NEW_ACTION: 
				CompletableFuture<JSONObject> future = CompletableFuture.supplyAsync(
						() -> process.createMr(json), Executors.newSingleThreadExecutor());
				future.whenComplete((requestBody,ex) -> process.confirmOrder(requestBody,ex));
				break;
			case CreateMrProcess.UPDATE_ACTION: 
			case CreateMrProcess.CANCEL_ACTION: 
			case CreateMrProcess.DELETE_ACTION:
				process.updateMr(json);
				break;
			default:
				throw new BaseException(String.format("Invalid type %s for request", operation));
			}
			
			responseBody = constructResponse(200, "OK", process.getPayloadTs(null, json));
		} catch (JSONException e) {
			log.error(String.format("Error: Invalid JSON Object; Cause - %s; Message - %s", e.getCause(), e.getMessage()));
			responseBody = constructResponse(400, "Invalid JSON in request body", new String[0]);
		} catch (Exception e) {
			log.error(e);
			responseBody = constructResponse(500, String.format("Unexpected error on host; Cause - %s; Message - %s", e.getCause(), e.getMessage()), new String[0]);
		}
		
		try {
			try {
				log.debug(String.format("Response Body: %s", responseBody.toString(4)));
			} catch(JSONException e) {
				log.debug(String.format("Response Body: %s", responseBody.toString()));
			}
			PrintWriter out = response.getWriter();
			out.write(responseBody.toString());
			out.close();
		} catch(IOException e) {
			log.error(e);
		}
		return noForward;
	}
	
	private JSONObject constructResponse(final int responseCode, final String message, final String[] payloadTs) {
		try {
			return new JSONObject() {{
				this.put("Response", new JSONObject() {{
					this.put("Status", new JSONObject() {{
						this.put("code", responseCode);
						this.put("text", message);
					}});
				}});
				if (payloadTs.length > 0) {
					this.put("payloadID", payloadTs[CreateMrProcess.PAYLOAD_ID]);
					this.put("timestamp", payloadTs[CreateMrProcess.TIMESTAMP]);
				}
			}};
		} catch(JSONException e2) {
			log.error(String.format("Error: Unable to format response body; Cause - %s; Message - %s", e2.getCause(), e2.getMessage()));
		}
		return new JSONObject();
	}
}
