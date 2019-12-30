package com.tcmis.ws.scanner.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.ws.scanner.process.NexGenScanUploadProcess;

public class NexGenScanUploadAction extends BaseScannerAction {
	static String	urlhost	= "https://www.tcmis.com/";

	static {
		try {
			ResourceLibrary lconfig = new ResourceLibrary("tcmISWebResource");
			if (lconfig != null) {
				String hostname = lconfig.getString("hosturl");
				if (StringUtils.isNotBlank(hostname)) {
					urlhost = hostname;
				}
			}
		}
		catch (Exception ex) {
		}
	}

	@Override
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
					if (!input.has("personnelId")) {
						results.put("errorCode", RESPONSE_INVALID_INPUT);
						results.put("errorMessage", "personnelId is required");
						results.put("success", false);
					}
					else if (!input.has("scans") || input.getJSONArray("scans").length() == 0) {
						results.put("errorCode", RESPONSE_INVALID_INPUT);
						results.put("errorMessage", "At least one scan is required");
						results.put("success", false);
					}
					else {

						NexGenScanUploadProcess process = new NexGenScanUploadProcess(getDbUser());

						try {
							StringBuffer requestURL = request.getRequestURL();
							String emailUrl = requestURL.substring(0, requestURL.lastIndexOf("/")) + "/customermrtracking.do?";
							emailUrl = emailUrl.replaceFirst("^.*//[^/]+/", urlhost);
							process.uploadScans(input.getJSONArray("scans"), emailUrl);
							results.put("success", true);
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
			}

			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
		}
		catch (Exception e) {
			log.error("Base Error in NexGenScanUploadAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}
}
