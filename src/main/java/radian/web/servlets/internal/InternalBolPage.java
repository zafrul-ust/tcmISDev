package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.web.barcode.BolPage;
import radian.web.barcode.BoxLabelPage;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2007
 * Company:
 * @author       Nawaz Shaik
 * @version
 */

public class InternalBolPage
 extends HttpServlet
 implements SingleThreadModel {
 public void init(ServletConfig config) throws ServletException {
	super.init(config);
 }

 /**
	* HTTP POST handler.
*
	* @param request               An object implementing the request context
	* @param response              An object implementing the response context
	* @exception ServletException  A wrapper for all exceptions thrown by the
	*      servlet.
	*/

 public void doPost(HttpServletRequest request,
	HttpServletResponse response) throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	response.setContentType("text/html");

	RayTcmISDBModel db = null;
	BolPage obj1 = null;
	HttpSession session = request.getSession();

	try {
	 db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
	 if (db == null) {
		PrintWriter out2 = new PrintWriter(response.getOutputStream());
		out2.println("Starting DB");
		out2.println("DB is null");
		out2.close();
		return;
	 }

	 String User_Action = request.getParameter("UserAction");
	 if (User_Action == null) {
		User_Action = "New";
	 }

	 if ("generatebols".equalsIgnoreCase(User_Action)) {
		Vector Data = new Vector();
		Data = (Vector) session.getAttribute("pickedItems");
		obj1 = new BolPage();

		String url = "";
		url = obj1.GenerateBol(Data);

		if (url.length() > 1) {
		 out.println(url);
		}
		else {
		 out.println("<HTML><HEAD>\n");
		 out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
		 out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
		 out.println("<TITLE>Container Label Generator</TITLE>\n");
		 out.println("</HEAD>  \n");
		 out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
		 out.println(
			"<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
		 out.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n");
		 out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>No BOLs Were Built.</b></font><P></P><BR>\n");
		 out.println("</CENTER>\n");
		 out.println("</BODY></HTML>    \n");
		}

		out.close();
	 }
	 else if ("generateboxlabels".equalsIgnoreCase(User_Action)) {
		Vector printdata = (Vector) session.getAttribute("pickedItems");
		BoxLabelPage boxlabelobj = new BoxLabelPage();
		String url = "";
		url = boxlabelobj.GenerateBoxLabel(printdata);

		out.println(url);
		out.close();
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 return;
	}
	finally {
	 db.close();
	 try {
		out.close();
	 }
	 catch (Exception e) {
		//ignore
	 }
	 //Runtime.getRuntime().gc();
	 return;
	}
 }

 public void doGet(HttpServletRequest request,
	HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
 }
}