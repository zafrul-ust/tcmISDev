package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.tcmis.common.util.ResourceLibrary;

public class TimeAction extends BaseTabletAction {
	private  SimpleDateFormat	formatter;
	private  SimpleDateFormat	formatterWithTime;
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject results = new JSONObject();

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getTcmISLocale());
		formatter			= new SimpleDateFormat(library.getString("java.tabletdateformat"));
		formatterWithTime	= new SimpleDateFormat(library.getString("java.tabletdatetimeformat"));

		Date now = new Date();
		Calendar indefinite = Calendar.getInstance();
		indefinite.set(3000, Calendar.JANUARY, 1);
		
		results.put("Status", RESPONSE_OK);
		results.put("Time", formatterWithTime.format(now));
		results.put("Date", formatter.format(now));
		results.put("Indefinite", formatter.format(indefinite.getTime()));

		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
