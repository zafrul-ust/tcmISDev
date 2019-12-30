package com.tcmis.client.kilfrost.action;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.XmlReaderAction;
import com.tcmis.common.framework.XmlRequestResponseAction;
import com.tcmis.client.kilfrost.process.OrderProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.*;
import java.io.*;

public class TestOrderXmlAction {

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

			String outPath = "c:\\GeneratedJava\\order.xml";
			String xmldata = readFileAsString(outPath);

			//Create socket
			String hostname = "dev.tcmis.com";//"localhost";//
			int port = 80;
			InetAddress  addr = InetAddress.getByName(hostname);
			Socket sock = new Socket(addr, port);

			//Send header
			String path = "/tcmIS/kilfrost/orderxml.do";
			BufferedWriter  wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(),"UTF-8")); //UTF8
			wr.write("POST " + path + " HTTP/1.0\r\n");
			wr.write("Host: "+hostname+"\r\n");
			wr.write("Content-Length: " + xmldata.length() + "\r\n");
			wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
			wr.write("\r\n");

			//Send data
			wr.write(xmldata);
			wr.flush();

			// Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String line;
			while((line = rd.readLine()) != null)
				System.out.println(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

