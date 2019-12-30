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

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvReceiptDocumentTypeBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;

public class ListReceiptDocumentImageTypesAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			// get the user selected and default locale
			Locale usrLocale = (((PersonnelBean) request.getSession().getAttribute("personnelBean")).getLocale());
			if (usrLocale == null) {
				usrLocale = defaultLocale;
			}
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", usrLocale);
			
			JSONArray jsonBeans = new JSONArray();
			Collection<VvReceiptDocumentTypeBean> beans = (Collection<VvReceiptDocumentTypeBean>) request.getSession().getServletContext().getAttribute("vvReceiptDocumentTypeCollection");
			for (VvReceiptDocumentTypeBean bean : beans) {
				JSONObject jsonPicture = new JSONObject();
				if (bean.getDocumentType().startsWith("Picture-")) {
					jsonPicture = BeanHandler.getJsonObject(bean);
					jsonPicture.put("descFromLabel", library.getString(bean.getJspLabel()));
					jsonBeans.put(jsonPicture);
				}
			}

			results.put("Results", jsonBeans);
			results.put("Status", RESPONSE_OK);
		}

		// Write out the data
		OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
