package com.tcmis.client.aerojet.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogCartBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.aerojet.process.IProcuXmlProcess;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.framework.XmlRequestResponseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.ws.scanner.process.PersonnelProcess;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */

public class PunchOutSetupXmlAction 
	extends TcmISBaseAction {


	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		String xmlString = request.getParameter("loginRequest");
	try {
			String address = "deverror@tcmis.com";
			System.out.println("xmlString Before:"+xmlString);
			xmlString  = URLDecoder.decode(xmlString, "UTF-8");
			System.out.println("xmlString:"+xmlString);
			MailProcess.sendEmail(address, "", "deverror@haastcm.com", "Received Aerojet xml doc ", "DOC:\n" + xmlString);
		} catch (Exception ex) {
			log.error("error sending mail:" + ex.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("cxml-urlencoded param:" + request.getParameter("cxml-urlencoded"));
			log.debug("cxml-urlencoded attribute:" + request.getAttribute("cxml-urlencoded"));
		}
		IProcuXmlProcess process = new IProcuXmlProcess(this.getDbUser(request));
		process.setResourceLib("aerojet");//"aerojet"
// use sid , a ramdon generated id.
		process.setNewProc(true);
		String sid = process.getLoginToken(); // we can use token util later if we choose to
		String returnString = process.processSetUpDocument(xmlString, sid );
// All these are done in ordering process, not now.		
//		String personnelId = process.getPersonnelIdFromCxmlId(process.getSender());
//		String logonId = process.getLoginIdfromPersonnelId(personnelId);
////		String payLoadId = process.getPayloadId() + "|"  +process.getSender() +"|"+logonId;
//		String payLoadId = logonId + "|" + sid;
//		process.setLoginSessionInfo(payLoadId, process.getBuyerCockie(), process.getBrowserFormPost(), process.getResponsePayloadId());
//		PersonnelBean personnelBean = new PersonnelBean();
//		personnelBean.setLogonId(logonId);
////		personnelBean.setClearTextPassword(request.getParameter("passwd"));
//		LoginProcess loginProcess = new LoginProcess(this.getDbUser(request));
//		try {
//			personnelBean = loginProcess.loginWeb(personnelBean, false);
//		}catch(Exception ee){};
//		if( personnelBean == null || personnelBean.getPersonnelId() == 0 ) {
//			this.setSessionObject(request, null, "personnelBean");
//			return returnString;
//		}
//		personnelBean.setDbUser(this.getDbUser(request));
//		this.setSessionObject(request, personnelBean, "personnelBean");
//		Locale locale = null;
//		locale = new Locale("en", "US");
//		com.tcmis.common.admin.action.LoginAction.setLocaleAnyway(request, request.getSession(), locale.toString());
//		personnelBean.setLocale(locale);
//		request.getSession().setAttribute("payloadId", process.getPayloadId());
		System.out.println("return String:"+returnString);
		response.getOutputStream().write(returnString.getBytes());
		return this.noForward;
	}
	private static String readFileAsString(String filePath)
	throws java.io.IOException{
		StringBuffer fileData = new StringBuffer(1024);
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
		return fileData.toString()+"\n\n";
	}
	public static void main(String[] args) {
		try {
		// need to change to xml.
		String xmlString = readFileAsString("c:\\GeneratedJava\\CXML_166268.XML");
		IProcuXmlProcess process = new IProcuXmlProcess("TCM_OPS");
		String returnString = process.processSetUpDocument(xmlString, "166268");
		}catch(Exception ex){}
	}

}
