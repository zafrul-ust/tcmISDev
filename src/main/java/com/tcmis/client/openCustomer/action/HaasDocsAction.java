package com.tcmis.client.openCustomer.action;

import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for login
 * @version 1.0
 ******************************************************************************/
public class HaasDocsAction extends TcmISBaseAction {
	private String haasDocsBaseURL = "";

	public HaasDocsAction() {
		super();
		ResourceLibrary customerResource = new ResourceLibrary("openCustomer");
		haasDocsBaseURL = customerResource.getString("openCustomer.haasDocsBaseUrl");
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		StringBuilder haasDocsURL = new StringBuilder(haasDocsBaseURL).append("/report/displaymergedpdfs.do?");
		try {
			HttpSession session = request.getSession(true);
			String params = request.getQueryString();
			String type = request.getParameter("type");
			response.setHeader("Content-Disposition", "filename=Haas_" + type + ".pdf");
			response.setContentType("application/pdf");
			haasDocsURL.append(params);
			if (log.isDebugEnabled()) {
				log.error("Retrieveing URL: " + haasDocsURL.toString());
			}
			URL url = new URL(haasDocsURL.toString());
			InputStream incomingPDF = url.openStream();
			ServletOutputStream out = response.getOutputStream();
			IOUtils.copy(incomingPDF, out);
			out.flush();
			out.close();
		}
		catch (Exception e) {
			log.error("Error accessing PDF using URL ->  " + haasDocsURL.toString(), e);
		}
		return noForward;
	}
}
