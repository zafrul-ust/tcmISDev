package com.tcmis.ws.scanner.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.scanner.process.UploadImageProcess;

public class UploadImageAction extends BaseScannerAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();
			ActionForm form = (ActionForm)request.getAttribute("genericForm");
			FormFile f = null;
			if (form.getMultipartRequestHandler() != null) {
				jsonString = ((String[])form.getMultipartRequestHandler().getTextElements().get("json"))[0];
				f = (FormFile)form.getMultipartRequestHandler().getFileElements().get("file");
			}
			
			try {
				JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
				if (!input.has("personnelId") || !(input.has("receiptId") || input.has("rids")) || !input.has("imageType") || f == null) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "personnelId, receiptId, imageType and imageData are ALL required");
					results.put("success", false);
				}
				else {
					UploadImageProcess process = new UploadImageProcess(this.getDbUser(request));
					String errorMsg = process.saveImage(input,f);
					if ( ! StringHandler.isBlankString(errorMsg)) {
						results.put("errorCode", RESPONSE_INVALID_INPUT);
						results.put("errorMessage", errorMsg);
						results.put("success", false);
					}
					else {
						results.put("success", true);
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
