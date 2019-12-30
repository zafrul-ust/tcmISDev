package com.tcmis.client.openCustomer.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for show login page
 * @version 1.0
 ******************************************************************************/
public class ProxyAction extends TcmISBaseAction {
	private static final String baseURL = "http://www.haasgroupintl.com/";
	private static final String proxyURL = "proxy.do?";

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		String path = request.getQueryString().replaceAll("proxy\\.do\\?", "");
		try {
			URL url = new URL(baseURL + path);
			URLConnection connection = url.openConnection();
			String type = connection.getContentType();
			response.setContentType(type);
			if (path.endsWith(".js") || path.endsWith(".css") ) {
				BufferedReader webpage = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = webpage.readLine();
				BufferedWriter proxiedPage = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
				while (line != null) {
					proxiedPage.write(fixLine(line) + "\n");
					line = webpage.readLine();
				}
				proxiedPage.flush();
				proxiedPage.close();
			}
			else {
				InputStream incomingData = connection.getInputStream();
				ServletOutputStream out = response.getOutputStream();
				IOUtils.copy(incomingData, out);
				out.flush();
				out.close();
			}
		}
		catch (Exception e) {
			log.error("Error accessing page using URL ->  " + baseURL + path, e);
		}
		return noForward;


	}

	private String fixLine(String line) {
		return line.replaceAll(baseURL,proxyURL).replaceAll("url\\(", "$0" + proxyURL);
	}

}