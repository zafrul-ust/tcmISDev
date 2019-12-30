package com.tcmis.ws.scanner.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.ws.scanner.beans.PrintInput;
import com.tcmis.ws.scanner.process.PrintProcess;

public class ScannerPrintAction extends BaseScannerAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
		try {
			JSONObject results = new JSONObject();

			try {
				JSONObject input = StringUtils.isNotBlank(jsonString) ? new JSONObject(jsonString) : new JSONObject();
				if (!input.has("prints")) {
					results.put("errorCode", RESPONSE_INVALID_INPUT);
					results.put("errorMessage", "At least one print is required");
					results.put("success", false);
				}
				else {
					JSONArray errors = new JSONArray();
					JSONArray prints = input.getJSONArray("prints");
					int i = 0;
					try {
						PrintProcess process = new PrintProcess(getDbUser());
						JSONArray pdfs = new JSONArray();
						for (i = 0; i < prints.length(); i++) {
							PrintInput curPrint = new PrintInput();
							BeanHandler.getJsonBeans(prints.getJSONObject(i), curPrint);
							if (curPrint.isValid()) {
								if (curPrint.isPrintCerts()) {
									JSONArray urls = process.printCert(curPrint, errors);
									if (urls.length() > 0) {
										JSONObject print = new JSONObject();
										print.put("id", curPrint.getId());
										print.put("urls", urls);
										pdfs.put(print);
									}
								}
								else if (curPrint.isPrintQVR()) {
									JSONArray urls = process.printQVR(curPrint, errors);
									if (urls.length() > 0) {
										JSONObject print = new JSONObject();
										print.put("id", curPrint.getId());
										print.put("urls", urls);
										pdfs.put(print);
									}
								}
								else if (curPrint.isPrintDeliveryLabels()) {
									process.printDeliveryLabel(curPrint, errors);
								}
								else if (curPrint.isPrintContainerLabels()) {
									process.printContainerLabel(curPrint, errors);
								}
								else if (curPrint.isPrintIATA()) {
									process.printIataDotLabel(curPrint, "iata", errors);
								}
								else if (curPrint.isPrintDOT()) {
									process.printIataDotLabel(curPrint, "dot", errors);
								}
								else if (curPrint.isPrintShippingLabels()) {
									process.printShippingLabels(curPrint, errors);
								}
								else if (curPrint.isPrintLongBOL()) {
									String url = process.printBOL(curPrint, true, errors);
									if (StringUtils.isNotBlank(url)) {
										JSONObject print = new JSONObject();
										print.put("id", curPrint.getId());
										print.put("url", url);
										pdfs.put(print);
									}
								}
								else if (curPrint.isPrintShortBOL()) {
									String url = process.printBOL(curPrint, false, errors);
									if (StringUtils.isNotBlank(url)) {
										JSONObject print = new JSONObject();
										print.put("id", curPrint.getId());
										print.put("url", url);
										pdfs.put(print);
									}
								}
								else if (curPrint.isPrintPVR()) {
									String url = process.printPVR(curPrint, errors);
									if (StringUtils.isNotBlank(url)) {
										JSONObject print = new JSONObject();
										print.put("id", curPrint.getId());
										print.put("url", url);
										pdfs.put(print);
									}
								}
								else if (curPrint.isPrintDeliveryTicket()) {
									String url = process.printDeliveryTicket(curPrint, errors);
									if (StringUtils.isNotBlank(url)) {
										JSONObject print = new JSONObject();
										print.put("id", curPrint.getId());
										print.put("url", url);
										pdfs.put(print);
									}
								}
								else if (curPrint.isPrintHoldSheet()) {
									String url = process.printHoldSheet(curPrint, errors);
									if (StringUtils.isNotBlank(url)) {
										JSONObject print = new JSONObject();
										print.put("id", curPrint.getId());
										print.put("url", url);
										pdfs.put(print);
									}
								}
								else if (curPrint.isPrintStraightBOL()) {
									String url = process.printStraightBOL(curPrint, errors);
									if (StringUtils.isNotBlank(url)) {
										JSONObject print = new JSONObject();
										print.put("id", curPrint.getId());
										print.put("url", url);
										pdfs.put(print);
									}
								}
								else if (curPrint.isPrintPackingList()) {
									String url = process.printPackingList(curPrint, errors);
									if (StringUtils.isNotBlank(url)) {
										JSONObject print = new JSONObject();
										print.put("id", curPrint.getId());
										print.put("url", url);
										pdfs.put(print);
									}
								}
								else {
									JSONObject error = new JSONObject();
									error.put("id", curPrint.getId());
									error.put("errorMessage", "Invalid printType: " + curPrint.getPrintType());
									errors.put(error);
								}
							}
							else {
								JSONObject error = new JSONObject();
								error.put("id", curPrint.getId());
								error.put("errorMessage", "Print must contain printerId, printType & either pickingUnitId, receiptId or tabletShipmentId");
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
							results.put("message", i + " prints successfully processed.");
						}
						if (pdfs.length() > 0) {
							results.put("pdfUrls", pdfs);
						}
					}
					catch (Exception ex) {
						log.error("Error accessing DB", ex);
						results.put("errorCode", RESPONSE_ERROR);
						if (ex instanceof BaseException) {
							results.put("errorMessage", ((BaseException)ex).getRootCause().getMessage());
						}
						else {
							results.put("errorMessage", ex.getMessage());
						}

						results.put("errorMessage", ex.toString());
						results.put("success", false);
					}
				}
			}
			catch (JSONException jse) {
				results.put("errorCode", RESPONSE_INVALID_INPUT);
				results.put("errorMessage", "Input is not valid JSON. " + jse.getMessage());
				results.put("success", false);
			}

			// Write out the data
			PrintWriter out = response.getWriter();
			out.write(results.toString(3));
			out.close();
		}
		catch (Exception e) {
			log.error("Base Error in PickCompleteAction", e);
			throw new BaseException(e);
		}
		return noForward;
	}

}
