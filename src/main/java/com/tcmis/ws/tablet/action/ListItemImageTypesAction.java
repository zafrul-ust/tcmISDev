package com.tcmis.ws.tablet.action;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.util.BeanHandler;
import com.tcmis.ws.tablet.beans.VvItemImageTagBean;

public class ListItemImageTypesAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			JSONArray jsonCarriers = new JSONArray();
			Collection<VvItemImageTagBean> tags = (Collection<VvItemImageTagBean>) request.getSession().getServletContext().getAttribute("vvItemImageTypeCollection");
			for (VvItemImageTagBean tag : tags) {
				jsonCarriers.put(BeanHandler.getJsonObject(tag));
			}

			results.put("Results", jsonCarriers);
			results.put("Status", RESPONSE_OK);
		}

		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
