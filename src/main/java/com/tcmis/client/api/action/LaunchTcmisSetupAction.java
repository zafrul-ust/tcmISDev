package com.tcmis.client.api.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.api.process.LaunchTcmisProcess;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;

import com.tcmis.common.util.StringHandler;

public class LaunchTcmisSetupAction extends BaseApiAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();
			JSONObject input = new JSONObject(jsonString);
			JSONObject responseJSON = new JSONObject();
			JSONObject responseStatusJSON = new JSONObject();
			JSONObject punchOutSetupResponseJSON = new JSONObject();
			JSONObject punchOutSetupResponseStartPageJSON = new JSONObject();
			try {
				LaunchTcmisProcess process = new LaunchTcmisProcess(this.getDbUser(request));
				StringBuffer requestURL = request.getRequestURL();
				String launchUrl = process.setupLaunchData(input,requestURL.substring(0,requestURL.lastIndexOf("/")));
				if ( ! StringHandler.isBlankString(launchUrl)) {
					responseStatusJSON.put("code", RESPONSE_OK);
					responseStatusJSON.put("text", RESPONSE_OK_TEXT);
					punchOutSetupResponseStartPageJSON.put("URL",launchUrl);
				} else {
					responseStatusJSON.put("code", RESPONSE_INVALID_LOGIN);
					responseStatusJSON.put("text", RESPONSE_INVALID_LOGIN_TEXT);
				}

			} catch (JSONException jse) {
				jse.printStackTrace();
				responseStatusJSON.put("code", RESPONSE_INVALID_INPUT);
				responseStatusJSON.put("text", RESPONSE_INVALID_INPUT_TEXT);
			}
			responseStatusJSON.put("value", "");
			responseJSON.put("Status",responseStatusJSON);
			punchOutSetupResponseJSON.put("StartPage",punchOutSetupResponseStartPageJSON);
			responseJSON.put("PunchOutSetupResponse",punchOutSetupResponseJSON);
			results.put("Response",responseJSON);

			results.put("lang",input.getString("lang"));
			results.put("payloadID",input.getString("payloadID"));
			results.put("timestamp",input.getString("timestamp"));
			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
		}
		catch (Exception e) {
			log.error("Base Error in LaunchTcmisSetupAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}
}
