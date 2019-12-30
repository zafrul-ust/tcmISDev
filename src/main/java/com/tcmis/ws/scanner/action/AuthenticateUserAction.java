package com.tcmis.ws.scanner.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.ResourceLibrary;

public class AuthenticateUserAction extends BaseScannerAction {
	private static boolean needpass = true;
//	static { 
//		try {
//			if( "NoPassRequired".equals( new ResourceLibrary("localconfig").getString("NoPassRequired") ) ) 
//				needpass = false;
//		}catch(Exception ex){
//			//ex.printStackTrace();
//		}
//	}
	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();

			try {
				JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
				if (!input.has("logonId") || !input.has("password")) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "logonId && password are required");
					results.put("success", false);
				}
				else {
					String client = getDbUser(request);
					LoginProcess loginProcess = new LoginProcess(getDbUser(request));

					try {
						String personnelId = loginProcess.authenticateScannerUser(input.getString("logonId"), input.getString("password"), needpass);
						if (StringUtils.isNotBlank(personnelId)) {
							results.put("personnelId", personnelId);
							results.put("success", true);
						}
						else {
							results.put("success", false);
							results.put("errorCode", RESPONSE_INVALID_LOGIN);
							results.put("errorMessage", "Invalid login");
						}
					}
					catch (Exception ex) {
						log.error("Error searching DB", ex);
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
			log.error("Base Error in CompanySearchAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}

}
