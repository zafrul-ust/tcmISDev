package com.tcmis.client.seagate.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.tcmis.client.seagate.process.CxmlProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.XmlRequestResponseAction;
import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class CxmlAction extends XmlRequestResponseAction {

	@Override
	public String processCall(HttpServletRequest request, HttpServletResponse response, String xmlString) throws BaseException {
		/*
		try {
			String address = "deverror@tcmis.com";
			MailProcess.sendEmail(address, "", "seagate_cxml@haastcm.com", "Received xcbl doc ", "DOC:\n" + xmlString);
		} catch (Exception ex) {
			log.error("error sending mail:" + ex.getMessage());
		}
      */ 
		if (log.isDebugEnabled()) {
			log.debug("cxml-urlencoded param:" + request.getParameter("cxml-urlencoded"));
			log.debug("cxml-urlencoded attribute:" + request.getAttribute("cxml-urlencoded"));
		}
		//TODO: to be removed after testing
		//xmlString = readFileAsString((String)request.getParameter("fileinput"));
		//TODO: to be removed after testing
		log.debug(xmlString); //added new
		String returnString = "";
		CxmlProcess process = new CxmlProcess(this.getDbUser(request));
		try {
			returnString = process.processDocument(xmlString, request.getSession().getId());
		}
		catch (BaseException be) {
			//return mapping.findForward("nopermissions");
			return process.processRequestError("500", "Internal Server Error: " + be.getRootCause().getMessage());
		}
		catch (Exception ex) {
			//return mapping.findForward("nopermissions");
			return process.processRequestError("500", "Internal Server Error: " + ex.getMessage());
		}

		request.getSession().setAttribute("payloadId", process.getPayloadId());
		return returnString;
	}
	
	private static String readFileAsString(String filePath) {
		
		StringBuffer fileData = new StringBuffer(1024);
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(filePath));
			char[] buf = new char[1024];
			int numRead=0;
			while((numRead=reader.read(buf)) != -1){
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileData.toString()+"\n\n";
	}
}