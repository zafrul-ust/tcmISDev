package com.tcmis.client.openCustomer.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for show login page
 * @version 1.0
 ******************************************************************************/
public class ShowLoginAction extends TcmISBaseAction {
	private static final String baseURL = "http://www.haasgroupintl.com/";
	private static final String proxyURL = "proxy.do?";

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request, true)) {
			putHomepageIntoRequest(request);
			return (mapping.findForward("showlogin"));
		}
		else {
			return (mapping.findForward("showindex"));
		}

	}

	private String fixHeaderLineURLS(String line) {
		String fixedLine = line.replaceAll("(src|SRC|href|HREF)\\s*=\\s*(\\\"|\\')(?!http)", "$0" + proxyURL);
		fixedLine = fixedLine.replaceAll("(\\'|\\\")(?=image)", "$0" + proxyURL);
		fixedLine = fixedLine.replaceAll(baseURL, proxyURL);
		log.debug("Before: " + line + "\nAfter: " + fixedLine);
		return fixedLine;
	}

	private String fixBodyLineURLS(String line) {
		String fixedLine = line.replaceAll("(href|HREF)\\s*=\\s*(\\\"|\\')(?!http)", "$0" + baseURL);
		fixedLine = fixedLine.replaceAll("(src|SRC)\\s*=\\s*(\\\"|\\')", "$0" + proxyURL);
		fixedLine = fixedLine.replaceAll("(\\'|\\\")(?=image)", "$0" + proxyURL);
		log.debug("B-Before: " + line + "\nB-After: " + fixedLine);
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