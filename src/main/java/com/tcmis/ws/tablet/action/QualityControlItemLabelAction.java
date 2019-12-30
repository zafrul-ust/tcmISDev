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
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.process.InboundTransProcess;

public class QualityControlItemLabelAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject results = new JSONObject();

		try {
			if (!isLoggedIn(request, true)) {
				results.put("Status", RESPONSE_SESSION_EXPIRED);
				results.put("Message", "Session Expired");
			}
			else {
				Locale locale = getTcmISLocale(request);
				TabletInputBean input = new TabletInputBean(form, locale);

				if (input.hasItemId() && input.hasInventoryGroup()) {
					InboundTransProcess process = new InboundTransProcess(getDbUser());
					ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

					try {
						String label = process.getQualityControli18nLabel(input);
						if (!StringHandler.isBlankString(label)) {
							String message = library.getString(label);
							results.put("QualityControlItemLabel", message);
						}
						else {
							results.put("Message", "No Label found.");
						}
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
					results.put("Message", "Both inventoryGroup & itemId are required");
				}
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

}
