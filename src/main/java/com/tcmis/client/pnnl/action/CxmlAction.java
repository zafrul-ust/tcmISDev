package com.tcmis.client.pnnl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.pnnl.process.CxmlProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.XmlRequestResponseAction;

/******************************************************************************
 *
 * @version 1.0
 ******************************************************************************/
public class CxmlAction
extends XmlRequestResponseAction {

	@Override
	public String processCall(HttpServletRequest request,
			HttpServletResponse response,
			String xmlString) throws BaseException {
		try {
			String address = "deverror@tcmis.com";
			MailProcess.sendEmail(address, "", "mnajera@haastcm.com", // "pnnl_cxml@haastcm.com",
					"Received xcbl doc ", "DOC:\n" + xmlString);
		}
		catch (Exception ex) {
			log.error("error sending mail:" + ex.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("cxml-urlencoded param:" + request.getParameter("cxml-urlencoded"));
			log.debug("cxml-urlencoded attribute:" + request.getAttribute("cxml-urlencoded"));
		}
		CxmlProcess process = new CxmlProcess(this.getDbUser(request));
		String returnString = process.processDocument(xmlString, request.getSession().getId());
		request.getSession().setAttribute("payloadId", process.getPayloadId());
		return returnString;
	}
}