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

import com.tcmis.common.admin.beans.VvReceiptDocumentTypeBean;
import com.tcmis.common.util.BeanHandler;

public class ListReceiptDocumentTypesAction extends BaseTabletAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject results = new JSONObject();
		if (!isLoggedIn(request, true)) {
			results.put("Status", RESPONSE_SESSION_EXPIRED);
			results.put("Message", "Session Expired");
		}
		else {
			JSONArray jsonBeans = new JSONArray();
			Collection<VvReceiptDocumentTypeBean> beans = (Collection<VvReceiptDocumentTypeBean>) request.getSession().getServletContext().getAttribute("vvReceiptDocumentTypeCollection");
			for (VvReceiptDocumentTypeBean bean : beans) {
				if (!bean.getDocumentType().startsWith("Picture")) {
					jsonBeans.put(BeanHandler.getJsonObject(bean));
				}
			}

			results.put("Results", jsonBeans);
			results.put("Status", RESPONSE_OK);
		}

		// Write out the data
		PrintWriter out = response.getWriter();
		out.write(results.toString(3));
		out.close();
		return noForward;
	}

}
