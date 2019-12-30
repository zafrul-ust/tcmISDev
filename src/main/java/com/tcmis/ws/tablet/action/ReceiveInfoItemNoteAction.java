package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.process.InboundTransProcess;

public class ReceiveInfoItemNoteAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		JSONObject results = new JSONObject();

		try {
			if (!isLoggedIn(request, true)) {
				results.put("Status", RESPONSE_SESSION_EXPIRED);
				results.put("Message", "Session Expired");
			}
			else {
				Locale locale = getTcmISLocale(request);
				TabletInputBean input = new TabletInputBean(form, locale);

				if (input.hasItemId() && input.hasHubId()) {
					InboundTransProcess process = new InboundTransProcess(getDbUser());
					try {
						String itemNote = process.getReceiveInfoItemNote(input);						
						results.put("ReceiveInfoItemNote", itemNote);						
						results.put("Status", RESPONSE_OK);
					}
					catch (Exception ex) {
						log.error("Error searching DB", ex);
						results.put("Status", RESPONSE_ERROR);
						results.put("Message", ex.toString());
					}
				}
				else {
					results.put("Status", RESPONSE_ERROR);
					results.put("Message", "Both hubId & itemId are required");
				}
			}			
		}
		catch (Exception ex) {
			log.error("Error searching DB", ex);
			results.put("Status", RESPONSE_ERROR);
			results.put("Message", ex.toString());
		}

		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		
		return noForward;

	}

}
