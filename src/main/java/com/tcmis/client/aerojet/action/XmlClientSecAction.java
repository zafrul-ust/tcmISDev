package com.tcmis.client.aerojet.action;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Date;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericWebServiceProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;


import java.net.*;
import java.io.*;

public class XmlClientSecAction {

	static {
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){
 
	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.endsWith("tcmis.com")) {
	                return true;
	            }
	            return false;
	        }
	    });
	}
 
	public static void main(String[] args){

		try {

			String outPath = "c:\\GeneratedJava\\aerojet\\LoginRequestJsul.xml";//LoginRequestJsul.xml";//PunchOutSetupJsul.xml";//Jsul.xml";//c:\\GeneratedJava\\dla\\test.xml";//HAASINV022711-1241412.xml";
			String xmldata = GenericWebServiceProcess.readFileAsString(outPath);

			//Create socket
//			String hostname = "qa.tcmis.com";//"dev.tcmis.com";//"localhost";//https://psoft-fsysib.slac.stanford.edu/PSIGW/HttpListeningConnector?From=SL_HAAS_INVOICE&To=PSFT_FSYS&Operation=SL_HAAS_INVOICE.VERSION_1
//
//			int port = 80;
//			InetAddress  addr = InetAddress.getByName(hostname);
//			Socket sock = new Socket(addr, port);

//			POST /msds_delivery/deliveryorder HTTP/1.1
//			Content-Type: text/xml; charset=UTF-8
//			SOAPAction: "http://delivery.msds.dscr.dla.mil//sendFile"
//			User-Agent: Axis2
//			Host: apps.aviation.dla.mil
//			Transfer-Encoding: chunked
			System.out.println(new Date());
//			String hddata="<?xml version=\"1.0\" encoding=\"utf-8\"?><PersonnelRequest xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\">"+
//						"<EchoRequest><Value>test this.</Value></EchoRequest>";
//			String hddata="<?xml version=\"1.0\" encoding=\"utf-8\"?><packingGroupRequest xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\">"+
//					"<packingGroupId>525031</packingGroupId></packingGroupRequest>";
			String hddata="<?xml version=\"1.0\" encoding=\"utf-8\"?><PersonnelRequest xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\">"+
					"<LoginId>sskidmore</LoginId><Password>haastcm</Password></PersonnelRequest>";

//				xmldata = hddata;//readFileAsString(outPath);


//			xmldata=
//					"<?xml version=\"1.0\" encoding=\"utf-8\"?><EchoRequest xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\">"+
//			"<Value>test this.</Value></EchoRequest>\n\n";
			
			
			//Send header
//			xmldata = "";//\r\n";
//			System.setProperty("javax.net.ssl.trustStore", "c:\\workspace\\ws\\WEB-INF\\jssecacerts");
//			System.setProperty("javax.net.ssl.trustStore", "jssecacerts");
//			System.setProperty("javax.net.ssl.trustStore", "jssecacerts");
//			System.setProperty("javax.net.ssl.keyStore", "c:\\Java\\jdk1.7.0_02\\jre\\lib\\security\\cacerts");
//			the trustAnchors parameter must be non-empty
//			System.out.println("test this");
//			System.setProperty("sun.security.ssl.allowUnsafeRenegotiation","true");// ignore errors.
			String hostname = "demo.tcmis.com";//"test3.tcmis.com";//qa.tcmis.com";//"localhost";//"qa.tcmis.com";//localhost";//"dev.tcmis.com";//"www.tcmis.com";//
			String path = "/tcmIS/aerojet/punchoutsetupxml.do";//punchoutsetup.do";//PackingGroup.do";//";//Personnel.do?NoFacilities=Y";//"/tcmIS/lmco/CabinetReceiptItem.do";//"/tcmIS/ws/ItemScanSubmit.do";//avcorp/Personnel.do";//?personnelId=24031&facilityId=Whippany&facilityId=WhippanyNew";ValidateToken
			String URL = "https://"+hostname+path;
			String response = getHttpPostResponse(URL, null, null, xmldata);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static int TIME_OUT = 0; //setting timeout to infinite
	public static String getHttpPostResponse(String url, String userName, String password, String postString) throws IOException, MalformedURLException {

		String authenticationString = userName + ":" + password;
		Base64 be = new Base64();
		String encodedAuthenticationString = be.encodeToString(authenticationString.getBytes());
		java.net.URL target = new URL(url);

		HttpURLConnection uc = (HttpURLConnection) target.openConnection(Proxy.NO_PROXY);
		if (userName != null && password != null) {
			uc.setRequestProperty("Authorization", "Basic " + encodedAuthenticationString);
		}
		uc.setReadTimeout(TIME_OUT);
		uc.setFollowRedirects(true);
		uc.setInstanceFollowRedirects(true);
		uc.setAllowUserInteraction(true);
		uc.setDoOutput(true);
		uc.setDoInput(true);
		uc.setUseCaches(false);
//		wr.write("Host: "+hostname+"\r\n");
//		wr.write("SOAPAction: http://qa.tcmis.com/ws/PolScannerService\r\n");
//					
//		wr.write("Content-Length: " + xmldata.length() + "\r\n");
//		wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
//		
		uc.setRequestMethod("GET");
		uc.setRequestProperty("SOAPAction","http://qa.tcmis.com/ws/PolScannerService");
//		uc.setRequestProperty("Host", hostanme);
		uc.setRequestProperty("Content-Type", "text/xml");
		// serialize and write the data
		OutputStream os = uc.getOutputStream();
		PrintWriter writer = new PrintWriter(os);
		writer.write(postString);
		writer.close();
		os.close();
		//    BufferedReader in = new BufferedReader(new InputStreamReader(target.openStream()));
		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		String inputLine;
		StringBuilder sb = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			sb.append(inputLine);
		}
		in.close();
		uc.disconnect();
		return sb.toString();
	}
	public static void buildjsp(String hostname) {
//		https://test3/tcmIS/hub/levelmanagementmain.jsp?jsp_precompile
	}
}

