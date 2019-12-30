package com.tcmis.ws.scanner.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.ws.scanner.process.BinReceiptsProcess;

import radian.tcmis.common.util.StringHandler;

public class BinReceiptsAction extends BaseScannerAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();
			try {
				BinReceiptsProcess process = new BinReceiptsProcess(this.getDbUser(request));
				JSONObject input = new JSONObject(jsonString).getJSONArray("binnings").getJSONObject(0);
				String errorMsg = process.binReceipts(input);
				if ( ! StringHandler.isBlankString(errorMsg)) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", errorMsg);
					results.put("success", false);
				}
				else {
					results.put("success", true);
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
			log.error("Base Error in BinReceiptsAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}
}
