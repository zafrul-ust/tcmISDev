package com.tcmis.ws.tablet.action;

import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.VvLocaleBean;
import com.tcmis.common.util.ResourceLibrary;

public class LocaleAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONArray locales = new JSONArray();
		JSONObject results = new JSONObject();

		try {

			Vector<VvLocaleBean> localeVector = (Vector<VvLocaleBean>) request.getSession().getServletContext().getAttribute("vvLocaleBeanCollection");
			for (VvLocaleBean localeBean : localeVector) {
				JSONObject localeJSON = new JSONObject();
				localeJSON.put("localeCode", localeBean.getLocaleCode());
				localeJSON.put("localeDesc", localeBean.getLocaleDescription());

				localeJSON.put("localeLabels", getLocalizedLabels(new Locale(localeBean.getLocaleCode().substring(0, 2), localeBean.getLocaleCode().substring(3, 5))));

				locales.put(localeJSON);
			}

			results.put("defaultLocale", defaultLocale.toString());
			results.put("locales", locales);
			results.put("Status", RESPONSE_OK);
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

	public JSONObject getLocalizedLabels(Locale libLocale) throws JSONException {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", libLocale);
		JSONObject labels = new JSONObject();
		labels.put("logonid", library.getString("label.logonid"));
		labels.put("password", library.getString("label.password"));
		labels.put("language", library.getString("label.language"));
		labels.put("login", library.getString("label.login"));
		labels.put("nopermission", library.getString("tablet.nopermissions.message"));
		labels.put("nounamepwd", library.getString("tablet.login.missingdata"));
		return labels;
	}

}
