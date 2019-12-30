package com.tcmis.client.openCustomer.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.StartPagesProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for ShowApplicationAction
 * @version 1.0
 ******************************************************************************/
public class ShowApplicationAction extends TcmISBaseAction {
	private static final String baseURL = "http://www.haasgroupintl.com/";
	private static final String proxyURL = "proxy.do?";

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		/*If somebody is trying to logout I don't want to do any other checks.*/
		if (((DynaBean) form).get("action") != null && ((String) ((DynaBean) form).get("action")).equalsIgnoreCase("logout")) {

			HttpSession session = request.getSession(false);
			if (session != null) {
				try {
					session.removeAttribute("personnelBean");
					session.setAttribute("loginState", "challenge");
					session.setAttribute("clientloginState", "challenge");
					session.invalidate();
				}
				catch (Exception e) {
				}
			}

			if (((DynaBean) form).get("lostSession") != null && ((String) ((DynaBean) form).get("lostSession")).trim().length() > 0) {
				request.setAttribute("lostSession", "lostSession");
			}

			request.setAttribute("requestedPage", "application");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request).replaceAll("logout", ""));
			putHomepageIntoRequest(request);
			return (mapping.findForward("login"));
		}

		// Make sure the user is logged in
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "application");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			putHomepageIntoRequest(request);
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");

		StartPagesProcess startPagesProcess = new StartPagesProcess(getDbUser(request));
		request.setAttribute("startPageViewBeanCollection", startPagesProcess.getSearchResult(personnelBean));
		return (mapping.findForward("success"));
	}

	private String fixHeaderLineURLS(String line) {
		String fixedLine = line.replaceAll("(src|SRC|href|HREF)\\s*=\\s*(\\\"|\\')(?!http)", "$0" + proxyURL);
		fixedLine = fixedLine.replaceAll("(\\'|\\\")(?=image)", "$0" + proxyURL);
		fixedLine = fixedLine.replaceAll(baseURL, proxyURL);
		fixedLine = fixedLine.replaceAll("(http:|HTTP:)", "https:");
		//log.debug("Before: " + line + "\nAfter: " + fixedLine);
		return fixedLine;
	}

	private String fixBodyLineURLS(String line) {
		String fixedLine = line.replaceAll("(href|HREF)\\s*=\\s*(\\\"|\\')(?!http)", "$0" + baseURL);
		fixedLine = fixedLine.replaceAll("(src|SRC)\\s*=\\s*(\\\"|\\')", "$0" + proxyURL);
		fixedLine = fixedLine.replaceAll("(\\'|\\\")(?=image)", "$0" + proxyURL);
		//log.debug("B-Before: " + line + "\nB-After: " + fixedLine);
		return fixedLine;
	}

	@SuppressWarnings("unchecked")
	private void putHomepageIntoRequest(HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("Fetching " + baseURL);
		}
		List<String> linesFromWebpage;
		try {
			BufferedReader webpage = new BufferedReader(new InputStreamReader(new URL(baseURL).openStream()));

			boolean inHeader = false;
			boolean inBody = false;
			boolean loginDivFound = false;
			StringBuilder headerData = new StringBuilder();
			StringBuilder bodyData = new StringBuilder();
			String line = webpage.readLine();
			while (line != null) {
				String lowerCase = line.toLowerCase();
				if (lowerCase.indexOf("<head") >= 0) {
					inHeader = true;
				}
				else if (lowerCase.indexOf("</head") >= 0) {
					inHeader = false;
				}
				else if (lowerCase.indexOf("<body") >= 0) {
					inBody = true;
				}
				else if (lowerCase.indexOf("</body") >= 0) {
					inBody = false;
					break;
				}
				else if (inHeader) {
					if (lowerCase.indexOf("<meta") < 0) {
						headerData.append(fixHeaderLineURLS(line)).append("\n");
					}
				}
				else if (inBody) {
					bodyData.append(fixBodyLineURLS(line)).append("\n");
					if (!loginDivFound && line.indexOf("loginDiv") > 0) {
						loginDivFound = true;
					}
				}
				line = webpage.readLine();
			}
			if (loginDivFound) {
				request.setAttribute("headerData", headerData.toString());
				request.setAttribute("bodyData", bodyData.toString());
			}
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (log.isDebugEnabled()) {
			log.debug("Done fetching " + baseURL);
		}
	}
}