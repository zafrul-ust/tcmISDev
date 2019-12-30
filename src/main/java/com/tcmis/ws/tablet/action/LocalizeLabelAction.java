package com.tcmis.ws.tablet.action;

import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.base.Library;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvLocaleBean;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

public class LocalizeLabelAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject results = new JSONObject();

		try {
			// If the user is logged in use the logged in locale, otherwise use the provided locale
			PersonnelBean user = (PersonnelBean) request.getSession().getAttribute("personnelBean");
			Locale usrLocale = user != null ? user.getLocale() : null;
			if (usrLocale == null) {
				String requestedLocale = (String) request.getParameter("locale");
				if (!StringHandler.isBlankString(requestedLocale)) {
					if (requestedLocale.equals("en_US")) usrLocale = defaultLocale;
					else {
						String codes[] = requestedLocale.split("_");
						if (codes.length < 2) {
							usrLocale = defaultLocale;
						}
						else
							usrLocale = new Locale(codes[0], codes[1]);
					}
				}
				else {
					usrLocale = defaultLocale;
				}
			}
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", usrLocale);

			String input = (String) request.getParameter("input");

			if (StringHandler.isBlankString(input)) {
				results.put("Status", RESPONSE_ERROR);
				results.put("Message", "Enter input for localizing.");
			}
			else {
				// loop through the list of labels and translate them as per the
				// locale
				JSONObject localeJSON = new JSONObject();
				for (String label : (input).split(",\\s*")) {
					localeJSON.put(label, library.getString(replaceUnderScores(label)));
				}

				results.put("locale", usrLocale.toString());
				results.put("localizedList", localeJSON);
				results.put("Status", RESPONSE_OK);
			}
		}
		catch (Exception ex) {
			log.error("Error searching DB", ex);
			results.put("Status", RESPONSE_ERROR);
			results.put("Message", ex.toString());
		}

		// Write out the data
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
		out.write(results.toString(3));
		out.close();
		return noForward;

	}

	private String replaceUnderScores(String label) {
		return label.replaceAll("_", ".");
	}

}
