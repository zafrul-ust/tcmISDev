package com.tcmis.client.api.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
			CreateMrProcess process = new CreateMrProcess(this.getDbUser(request), this.getTcmISLocale(request));
			JSONObject json = process.transformJson(new JSONObject(jsonString), this.getPathCompany(request));
			
			CompletableFuture<JSONObject> future = CompletableFuture.supplyAsync(
					() -> process.createMr(json), Executors.newSingleThreadExecutor());
			future.whenComplete((requestBody,ex) -> process.confirmOrder(requestBody,ex));
			responseBody = constructResponse(200, "OK", json.getJSONObject("mrHeader").getString("payloadId"));
		} catch (JSONException e) {
			log.error(String.format("Error: Invalid JSON Object; Cause - %s; Message - %s", e.getCause(), e.getMessage()));
			responseBody = constructResponse(400, "Invalid JSON in request body", "");
		}
		
		try {
			PrintWriter out = response.getWriter();
			out.write(responseBody.toString());
			out.close();
		} catch(IOException e) {
			log.error(e);
		}
		return noForward;
	}
	
	private JSONObject constructResponse(final int responseCode, final String message, final String payloadId) {
		try {
			return new JSONObject() {{
				this.put("Response", new JSONObject() {{
					this.put("Status", new JSONObject() {{
						this.put("code", responseCode);
						this.put("text", message);
					}});
				}});
				if ( ! payloadId.isEmpty()) {
					this.put("payloadID", payloadId);
				}
			}};
		} catch(JSONException e2) {
			log.error(String.format("Error: Unable to format response body; Cause - %s; Message - %s", e2.getCause(), e2.getMessage()));
		}
		return new JSONObject();
	}
}
