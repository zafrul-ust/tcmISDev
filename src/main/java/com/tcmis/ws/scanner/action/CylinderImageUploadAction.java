package com.tcmis.ws.scanner.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestWrapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.ws.scanner.process.CylinderImageUploadProcess;
import com.tcmis.ws.scanner.process.PPSImageUploadProcess;

public class CylinderImageUploadAction extends BaseScannerAction {

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
					ActionForm form = (ActionForm) request.getAttribute("genericForm");
					FormFile uploadedFile = null;
					if (form.getMultipartRequestHandler() != null) {
						jsonString = ((String[]) form.getMultipartRequestHandler().getTextElements().get("json"))[0];
						uploadedFile = (FormFile) form.getMultipartRequestHandler().getFileElements().get("file");
						JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
						if (!input.has("personnelId") || !input.has("serialNumber") || !input.has("manufacturerIdNo") || uploadedFile == null) {
							results.put("errorCode", RESPONSE_INVALID_INPUT);
							results.put("errorMessage", "personnelId, serialNumber, manufacturerIdNo and image data are ALL required");
							results.put("success", false);
						}
						else {
							CylinderImageUploadProcess process = new CylinderImageUploadProcess(getDbUser());
							try {
								JSONObject error = process.uploadImage(input, uploadedFile);
								if (error == null) {
									results.put("success", true);
									results.put("message", "image successfully uploaded.");
								}
								else {
									results = error;
								}
							}
							catch (Exception e) {
								log.error("Error searching DB", e);
								results.put("errorCode", RESPONSE_ERROR);
								results.put("errorMessage", e.toString());
								results.put("success", false);
							}
						}
					}
					else {
						results.put("errorCode", RESPONSE_INVALID_INPUT);
						results.put("errorMessage", "Upload is not a valid multipart request");
						results.put("success", false);
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
		catch (IOException | JSONException e) {
			log.error("Base Error in PPSImageUploadProcess ", e);
			throw new BaseException(e);
		}
		return noForward;
	}

}
