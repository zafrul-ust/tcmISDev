package com.tcmis.ws.scanner.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 12, 2008
 * Time: 10:51:05 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.PrintWriter;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.XmlReaderAction;
import com.tcmis.common.framework.XmlRequestResponseAction;
import com.tcmis.ws.scanner.process.EchoProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/******************************************************************************
 *
 * @version 1.0
 ******************************************************************************/
public class EchoAction
    extends XmlRequestResponseAction {

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
		return fileData.toString();
	}
	
  public String processCall(HttpServletRequest request,
                          HttpServletResponse response,
                          String xmlString) throws BaseException {
    String message = "OK";
    try {
      String address = "deverror@tcmis.com";
//      MailProcess.sendEmail(address, "", "kilfrost@haastcm.com",
//                            "Received order ", "ORDER:\n" + xmlString);
    }
    catch (Exception ex) {
      log.error("error sending mail:" + ex.getMessage());
    }
    if(xmlString != null && xmlString.length() > 0) {
        try {
        	EchoProcess process = new EchoProcess(this.getDbUser(request));
//        	System.out.println("personnelId:"+request.getParameter("personnelId"));
//        	String[] facilities = request.getParameterValues("facilityId");
//        	for(String s:facilities) 
//        		System.out.println("facilityId:"+s);

//        	getCabinetReceiptItem
            String result = process.processEcho(xmlString);
            log.debug(result);
//        	System.out.println(xmlString);
//			String outPath = "c:\\GeneratedJava\\testgetcabresp.xml";
//			String result = readFileAsString(outPath);
//            PrintWriter writer = response.getWriter();
//            writer.write(result);
//            writer.close();
            return result;
        }
        catch(Exception e) {
            e.printStackTrace();
            log.fatal("Error processing Echo " + e.getMessage(), e);
            message = "Error:" + e.getMessage();
        }
    }
    return "";
  }
}