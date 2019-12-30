package com.tcmis.client.aerojet.action;


import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Date;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.XmlReaderAction;
import com.tcmis.common.framework.XmlRequestResponseAction;
import com.tcmis.client.kilfrost.process.OrderProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.net.*;
import java.io.*;

public class AerojetXmlClientSimAction {

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


	public static void main(String[] args){

		try {
// CabinetReceiptItemRequest.xmlDX InvGrpItem.xml Personnel_73125.xml ResultSubmit_72822.xml ResultSubmit_73128.xml === AvcorpPersonnel73043.xml
//  lockheed	A254A1C6FFEF1A0BE04400144F80B9CE		PersonnelLockheed28024.xml 28035 28033 CabinetCountwebdatawebappstcmISWEB-INFxcbldocumentsScannerUploadResultSubmit_72710.xml _73180.xml 74568.
			// InvGrpItem.xml
			String outPath = "c:\\GeneratedJava\\aerojet\\LoginRequestJsul.xml";//PunchOutSetupJsul.xml";//ResultSubmit_95040.xml";//_85944.xml";//
			//\\lmco\\CabinetItemReceiptTest.xml";//kaban\\ResultSubmit_12594.xml";//ResultSubmit_62317.xml";//ResultSubmit_62317.xml";//Personnel_62321.xml";//test.xml" ;


//				"C:\\Documents and Settings\\larry.liu\\Desktop\\Schemas\\new\\Schemas\\EchoRequest.xml";//CabinetReceiptItemRequest.xml";//"ResultSubmit_63514.xml";//CabinetReceiptItemRequest.xml";//testgetcab.xml"; ValidateTokenRequest.xml
//			PersonnelRequest.xml
			System.out.println(new Date());
			String hddata="<?xml version=\"1.0\" encoding=\"utf-8\"?><PersonnelRequest xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\">"+
						"<LoginId>sskidmore</LoginId><Password>haastcm</Password></PersonnelRequest>";

			String xmldata = readFileAsString(outPath);
//			xmldata = hddata;
//			http://qa.tcmis.com/tcmIS/ws/ItemScanSubmit.do

			//Create socket XmlClientSimAction.javaz
			String hostname = "localhost";//qa.tcmis.com";//"localhost";//"qa.tcmis.com";//localhost";//"dev.tcmis.com";//"www.tcmis.com";//
			int port = 80;
			InetAddress  addr = InetAddress.getByName(hostname);
			Socket sock = new Socket(addr, port);

			//Send header
			//"/tcmIS/ws/CabinetReceiptItem.do?personnelId=24031&facilityId=Whippany&facilityId=WhippanyNew";//"/ws/services/ScannerService/";
			//"/ws/services/ScannerService";
			//CabinetReceiptItem Echo ResultSubmit Personnel ValidateToken
			//Personnel.do?NoFacilities=Y ItemScanSubmit.do Echo.do
			
//			String path = "/tcmIS/aerojet/punchoutsetup.do";//ResultSubmit.do";//";//Personnel.do?NoFacilities=Y";//"/tcmIS/lmco/CabinetReceiptItem.do";//"/tcmIS/ws/ItemScanSubmit.do";//avcorp/Personnel.do";//?personnelId=24031&facilityId=Whippany&facilityId=WhippanyNew";ValidateToken
			String path = "/tcmIS/aerojet/punchoutsetupxml.do";//ResultSubmit.do";//";//Personnel.do?NoFacilities=Y";//"/tcmIS/lmco/CabinetReceiptItem.do";//"/tcmIS/ws/ItemScanSubmit.do";//avcorp/Personnel.do";//?personnelId=24031&facilityId=Whippany&facilityId=WhippanyNew";ValidateToken
			BufferedWriter  wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(),"UTF-8")); //UTF8
			wr.write("POST " + path + " HTTP/1.0\r\n");
			wr.write("Host: "+hostname+"\r\n");
			wr.write("SOAPAction: http://qa.tcmis.com/ws/ScannerService\r\n");
						
			wr.write("Content-Length: " + xmldata.length() + "\r\n");
			wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
			wr.write("\r\n");

			
			//Send data
			wr.write(xmldata);
			wr.flush();

			// Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String line;
			boolean printed_date = false;
			while((line = rd.readLine()) != null) {
				if( !printed_date ) {
					System.out.println(new Date());
					printed_date = true;
				}
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

