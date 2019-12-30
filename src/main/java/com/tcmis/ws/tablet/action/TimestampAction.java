package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.ws.tablet.beans.TimestampBean;

public class TimestampAction extends BaseTabletAction {
	private SimpleDateFormat	formatter;
	private SimpleDateFormat	formatterWithTime;

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject results = new JSONObject();

		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			try {
				TimestampBean input = new TimestampBean(form, getTcmISLocale(request));
				results.put("Status", RESPONSE_OK);
				results.put("Timestamp", input.getDate().getTime());
			}
			catch (Exception e) {
				results.put("Status", RESPONSE_ERROR);
				results.put("Message", e.getMessage());
			}
		}

		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
