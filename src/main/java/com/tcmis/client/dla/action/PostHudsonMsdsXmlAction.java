package com.tcmis.client.dla.action;

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

import com.tcmis.client.dla.process.MsdsPostProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

import java.net.*;
import java.io.*;

public class PostHudsonMsdsXmlAction {

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


	public static void main(String[] args){

		try {

//			String outPath = "c:\\GeneratedJava\\dla\\test.xml";//HAASINV022711-1241412.xml";
//			String xmldata = readFileAsString(outPath);

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

/* test code for TLS 1.2
//https://vapps.dla.mil/DeliveryOrder/DeliveryOrderPort?WSDL
//            System.setProperty("javax.net.ssl.trustStore","c:\\Java\\jdk1.8.0_91\\jre\\lib\\security\\jssecacerts");//cacerts");
//
//            java.net.URL target = new URL("https://vapps.dla.mil/DeliveryOrder/DeliveryOrderPort?WSDL");
//            HttpsURLConnection uc  = (HttpsURLConnection)target.openConnection();
//            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
//                           sslContext.init(null, null, null);
//                           uc.setSSLSocketFactory(sslContext.getSocketFactory());
//            uc.setDoOutput(true);
//            uc.setDoInput(true);
//    		uc.setRequestProperty("Content-Type", "text/xml");
//            uc.setRequestMethod("POST");
//        	OutputStream os = uc.getOutputStream();
//    		PrintWriter writer = new PrintWriter(os);
//    		writer.write("");
//    		writer.close();
//    		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//    		String inputLine;
//    		StringBuilder sb = new StringBuilder();
//    		while ((inputLine = in.readLine()) != null) {
//    			sb.append(inputLine);
//    		}
//    		in.close();
//    		uc.getInputStream().close();
//    		uc.disconnect();
//    		System.out.println(sb.toString());
//    		if(1==1) return;
*/    		
			//Send header
//			xmldata = "";//\r\n";
//			System.setProperty("javax.net.ssl.trustStore", "c:\\workspace\\ws\\WEB-INF\\jssecacerts");
			System.setProperty("javax.net.ssl.trustStore", "jssecacerts");

//			System.out.println("test this");
			String slacURL = "https://vapps.dla.mil/DeliveryOrder/DeliveryOrderPort";
//			String slacURL = "https://eeousps.documentservices.dla.mil/DeliveryOrder/DeliveryOrderPort";
			System.out.println("url:"+slacURL);
			MsdsPostProcess proc = new MsdsPostProcess("TCM_OPS");
			String xmldata = proc.getPostXml();
			int nTry = 1;
			while( nTry-- > 0 ) {
				try {
					System.out.println(xmldata);
					System.out.println("Sending Requests");
					String response = getHttpPostResponse(slacURL, "EGW0410", "CNK22les!#sweetbro", xmldata);
					System.out.println(response);
					break;
				} catch(Exception ee){			
					ee.printStackTrace();
					Thread.sleep(5000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static int TIME_OUT = 0; //setting timeout to infinite
	public static String getHttpPostResponse(String url, String userName, String password, String postString) throws Exception {

		String authenticationString = userName + ":" + password;
		BASE64Encoder be = new BASE64Encoder();
		String encodedAuthenticationString = be.encode(authenticationString.getBytes());
		java.net.URL target = new URL(url);
//		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getLocalHost(),8888));//.getByName("java.sun.com")
//		System.setProperty("http.proxyHost", "127.0.0.1");
//		System.setProperty("http.proxyPort", "8888");
		HttpsURLConnection uc = (HttpsURLConnection) target.openConnection();//proxy);// use this for fiddler or Proxy.NO_PROXY);
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, null);
        uc.setSSLSocketFactory(sslContext.getSocketFactory());
		if (userName != null && password != null) {
			uc.setRequestProperty("Authorization", "Basic " + encodedAuthenticationString);
		}
//		uc.setReadTimeout(TIME_OUT);
//		uc.setFollowRedirects(true);
//		uc.setInstanceFollowRedirects(true);
//		uc.setAllowUserInteraction(true);
		uc.setDoOutput(true);
		uc.setDoInput(true);
//		uc.setUseCaches(false);
//		uc.setRequestMethod("GET");
		uc.setRequestMethod("POST");
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
		uc.getInputStream().close();
		uc.disconnect();
		return sb.toString();
	}
	
}

//<?xml version='1.0' encoding='UTF-8'?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
//<soapenv:Body><ns1:sendFileElement xmlns:ns1="http://delivery.msds.dscr.dla.mil/"><ns1:file_name>testfile</ns1:file_name>
//<ns1:arr><ns1:nsn>6830-01-296-2459</ns1:nsn><ns1:contractor_serial_number>15725405012007</ns1:contractor_serial_number><ns1:contract_and_do>SPM4AR-07-D-0100-08ED</ns1:contract_and_do><ns1:vendor_sos>Advanced Specialty Gases Inc</ns1:vendor_sos><ns1:rp_mfgr_cage>1SKZ6</ns1:rp_mfgr_cage><ns1:msds_responsible_party>Advanced Specialty Gases</ns1:msds_responsible_party><ns1:vendor_sos_cage>1SKZ6</ns1:vendor_sos_cage><ns1:msds_preparation_rev_date>5/1/2007</ns1:msds_preparation_rev_date><ns1:product_name>Freon 116 Refridgerant/Nitrous Oxide Blend 90/10</ns1:product_name></ns1:arr>
//<ns1:arr><ns1:nsn>6830-01-296-2459</ns1:nsn><ns1:contractor_serial_number>15725405012007</ns1:contractor_serial_number><ns1:contract_and_do>SPM4AR-07-D-0100-08ED</ns1:contract_and_do><ns1:vendor_sos>Advanced Specialty Gases Inc</ns1:vendor_sos><ns1:rp_mfgr_cage>1SKZ6</ns1:rp_mfgr_cage><ns1:msds_responsible_party>Advanced Specialty Gases</ns1:msds_responsible_party><ns1:vendor_sos_cage>1SKZ6</ns1:vendor_sos_cage><ns1:msds_preparation_rev_date>5/1/2007</ns1:msds_preparation_rev_date><ns1:product_name>Freon 116 Refridgerant/Nitrous Oxide Blend 90/10</ns1:product_name></ns1:arr>
//</ns1:sendFileElement></soapenv:Body></soapenv:Envelope>
//<?xml version='1.0' encoding='UTF-8'?><soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"><soap:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:env="http://schemas.xmlsoap.org/soap/envelope/" soap:mustUnderstand="1"><wsse:UsernameToken xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"><wsse:Username>ELT00029</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">TexasLonghorns512**</wsse:Password></wsse:UsernameToken></wsse:Security></soap:Header>
//<soap:Body><ns1:sendFileElement xmlns:ns1="http://delivery.msds.dscr.dla.mil/"><ns1:file_name>testfile</ns1:file_name>
//<ns1:arr><ns1:nsn>6830-01-296-2459</ns1:nsn><ns1:contractor_serial_number>15725405012007</ns1:contractor_serial_number><ns1:contract_and_do>SPM4AR-07-D-0100-08ED</ns1:contract_and_do><ns1:vendor_sos>Advanced Specialty Gases Inc</ns1:vendor_sos><ns1:rp_mfgr_cage>1SKZ6</ns1:rp_mfgr_cage><ns1:msds_responsible_party>Advanced Specialty Gases</ns1:msds_responsible_party><ns1:vendor_sos_cage>1SKZ6</ns1:vendor_sos_cage><ns1:msds_preparation_rev_date>2007-05-01 00:00:00.0</ns1:msds_preparation_rev_date><ns1:product_name>Freon 116 Refridgerant/Nitrous Oxide Blend 90/10</ns1:product_name></ns1:arr>
//<ns1:arr><ns1:nsn>6830-00-817-2342</ns1:nsn><ns1:contractor_serial_number>18715801012008</ns1:contractor_serial_number><ns1:contract_and_do>SPM4AR-07-D-0100-089J</ns1:contract_and_do><ns1:vendor_sos>Advanced Specialty Gases Inc</ns1:vendor_sos><ns1:rp_mfgr_cage>1SKZ6</ns1:rp_mfgr_cage><ns1:msds_responsible_party>Advanced Specialty Gases</ns1:msds_responsible_party><ns1:vendor_sos_cage>1SKZ6</ns1:vendor_sos_cage><ns1:msds_preparation_rev_date>2008-01-01 00:00:00.0</ns1:msds_preparation_rev_date><ns1:product_name>Sulfur Hexafluoride</ns1:product_name></ns1:arr>
//</ns1:sendFileElement></soap:Body></soap:Envelope>