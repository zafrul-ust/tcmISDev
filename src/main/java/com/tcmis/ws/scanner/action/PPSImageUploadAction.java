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
import com.tcmis.ws.scanner.process.PPSImageUploadProcess;

public class PPSImageUploadAction extends BaseScannerAction {

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
					FormFile f = null;
					if (form.getMultipartRequestHandler() != null) {
						jsonString = ((String[]) form.getMultipartRequestHandler().getTextElements().get("json"))[0];
						f = (FormFile) form.getMultipartRequestHandler().getFileElements().get("file");
					}

					JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
					if (!input.has("personnelId") || !input.has("documentType") || f == null) {
						results.put("errorCode", RESPONSE_INVALID_INPUT);
						results.put("errorMessage", "personnelId, documentType and imageData are required for all image uploads");
						results.put("success", false);

					}
					else {
						String docType = input.getString("documentType");
						if (docType.endsWith("SIGNATURE")) {
							if (!input.has("tabletShipmentId")) {
								results.put("errorCode", RESPONSE_INVALID_INPUT);
								results.put("errorMessage", "tabletShipmentId is required for signatures");
								results.put("success", false);
							}
							else {
								PPSImageUploadProcess process = new PPSImageUploadProcess(getDbUser());
								try {
									process.uploadBOLSignature(input, f);
									results.put("success", true);
									results.put("message", "image successfully uploaded.");
								}
								catch (Exception e) {
									log.error("Error uploading signature", e);
									results.put("errorCode", RESPONSE_ERROR);
									results.put("errorMessage", e.toString());
									results.put("success", false);
								}
							}

						}
						else {
							if (!input.has("pickingUnitId")) {
								results.put("errorCode", RESPONSE_INVALID_INPUT);
								results.put("errorMessage", "pickingUnitId is required");
								results.put("success", false);
							}
							else {
// Larry TCMISDEV-4400 comments: Seems as long  as it has picking unit it and doc type is in the
// VV_PICKING_DOCUMENT_TYPE table. It become data driven. i.e. no java code change.								
								PPSImageUploadProcess process = new PPSImageUploadProcess(getDbUser());
								try {
									process.uploadPPSImage(input, f);
									results.put("success", true);
									results.put("message", "image successfully uploaded.");
								}
								catch (Exception e) {
									log.error("Error uploading image", e);
									results.put("errorCode", RESPONSE_ERROR);
									results.put("errorMessage", e.toString());
									results.put("success", false);
								}
							}
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
		catch (IOException | JSONException e) {
			log.error("Base Error in PPSImageUploadProcess ", e);
			throw new BaseException(e);
		}
		return noForward;
	}

}
