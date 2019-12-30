package com.tcmis.ws.tablet.action;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.tablet.beans.TabletInputBean;
import com.tcmis.ws.tablet.process.InboundTransProcess;
import com.tcmis.ws.tablet.process.ReceiptProcess;
import com.tcmis.ws.tablet.process.SimpleTabletProcess;

public class MenuAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			try {			
				SimpleTabletProcess tabletProcess = new SimpleTabletProcess(getDbUser());
				PersonnelBean user = (PersonnelBean) request.getSession().getAttribute("personnelBean");
				
				// get the user selected and default locale
				Locale usrLocale = (((PersonnelBean) request.getSession().getAttribute("personnelBean")).getLocale());
				if (usrLocale == null) {
					usrLocale = defaultLocale;
				}
				ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", usrLocale);
				
				JSONArray localizedPages = new JSONArray();
				JSONArray pages = tabletProcess.getTabletMenu(user);
				for ( int i = 0; i < pages.length(); i++) {
					JSONObject pagemenu = pages.getJSONObject(i);
					pagemenu.put("pageNameFromLocale", library.getString(pagemenu.getString("pageId")));
					localizedPages.put(pagemenu);
				}
				
				results.put("Pages", localizedPages);
				results.put("Status", RESPONSE_OK);
			}
			catch (Exception ex) {
				log.error("Error searching DB", ex);
				results.put("Status", RESPONSE_ERROR);
				results.put("Message", ex.toString());
			}
		}
		// Write out the data
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
		out.write(results.toString(3));
		out.close();
		return noForward;
	}
}
