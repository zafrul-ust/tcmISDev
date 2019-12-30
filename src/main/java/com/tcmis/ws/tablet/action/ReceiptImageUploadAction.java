package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.process.InboundTransProcess;
import com.tcmis.ws.tablet.process.ReceiptProcess;

public class ReceiptImageUploadAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			String message = "";
			TabletInputBean input = new TabletInputBean(form, getTcmISLocale(request));
			if (input.hasReceiptId() && input.hasImageType()  && input.hasImageData()) {
				PersonnelBean user = (PersonnelBean) request.getSession().getAttribute("personnelBean");
				ReceiptProcess process = new ReceiptProcess(getDbUser());

				try {
					message = process.saveReceiptImage(input, user);
				}
				catch (Exception ex) {
					log.error("Error saving Image", ex);
					results.put("Status", RESPONSE_ERROR);
					results.put("Message", ex.toString());
				}

				if (StringHandler.isBlankString(message)) {
					results.put("Status", RESPONSE_OK);
					results.put("ImageUrl", input.getReturnValue());
				}
				else {
					results.put("Status", RESPONSE_ERROR);
					results.put("Message", message);
				}
			}
			else {
				if (!input.hasReceiptId()) {
					message += "receiptId is required. ";
				}
				if (!input.hasImageType()) {
					message += "imageType is required. ";
				}
				if (!input.hasImageData()) {
					message += "imageData is not attached. ";
				}
				results.put("Status", RESPONSE_ERROR);
				results.put("Message", message);
			}
		}

		log.debug(results.toString(3));
		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
