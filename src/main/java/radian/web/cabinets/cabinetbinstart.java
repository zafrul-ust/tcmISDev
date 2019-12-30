package radian.web.cabinets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.web.servlets.internal.InternalServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 06-30-04 Changing Printer Path to be based on personnel ID
 */

public class cabinetbinstart extends HttpServlet implements SingleThreadModel {

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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		RayTcmISDBModel db = null;
		cabinetbins obj = null;
		cabilabelPage obj1 = null;
		String User_Action = "";
		HttpSession session = request.getSession();
		Hashtable loginresult = new Hashtable();

		String facilityName = "";
		Vector cabids = new Vector();
		Vector cab8ids = new Vector();

		try {
			db = new RayTcmISDBModel("Tcm_Ops", "2", radian.web.HTMLHelpObj.getTcmISLocaleString(request));
			//db = new RayTcmISDBModel("Tcm_Ops","1");
			if (db == null) {
				PrintWriter out2 = new PrintWriter(response.getOutputStream());
				out2.println("Starting DB");
				out2.println("DB is null");
				out2.close();
				return;
			}

			loginresult = radian.web.loginhelpObj.logintopage(session, request, db, out);
			String auth = (String) loginresult.get("AUTH");
			String errormsg = (String) loginresult.get("ERROSMSG");

			if (auth == null) {
				auth = "challenge";
			}
			if (errormsg == null) {
				errormsg = "";
			}

			if (!(auth.equalsIgnoreCase("authenticated"))) {
				out.println(radian.web.HTMLHelpObj.printLoginScreen(errormsg, "Cabinet Labels"));
				out.flush();
			}
			else {
				obj = new cabinetbins(new InternalServerResourceBundle(), db);

				if (radian.web.HTMLHelpObj.hasaccessto(session, "Cabinet Label")) {
					obj.setupdateStatus(true);
				}
				else {
					String CompanyID = session.getAttribute("COMPANYID") == null ? "" : session.getAttribute("COMPANYID").toString();
					if ("Radian".equalsIgnoreCase(CompanyID)) {
						obj.setupdateStatus(false);
					}
					//					else {
					//						out.println(radian.web.HTMLHelpObj.printLoginScreenOptions("You don't have access to this page.", "Cabinet Labels", session));
					//						out.close();
					//						return;
					//					}
				}

				User_Action = request.getParameter("UserAction");
				if (User_Action == null)
					User_Action = "New";
				String generate_labels = request.getParameter("generate_labels");
				if (generate_labels == null)
					generate_labels = "No";
				String genaction = request.getParameter("genaction");
				if (genaction == null)
					genaction = "generatecablabels";

				if ("yes".equalsIgnoreCase(generate_labels) && "GenerateLabels".equalsIgnoreCase(User_Action)) {
					cabids = (Vector) session.getAttribute("LABEL_BIN_IDS");
					session.removeAttribute("LABEL_BIN_IDS");
					facilityName = (String)session.getAttribute("LABEL_FACILITY_NAME");
					session.removeAttribute("LABEL_FACILITY_NAME");

					String PaperSize = request.getParameter("paperSize");
					if (PaperSize == null)
						PaperSize = "31";

					obj1 = new cabilabelPage(new InternalServerResourceBundle(), db);
					obj1.setpaperSize(PaperSize);

					//StringBuffer MsgSB=new StringBuffer();
					String url = "";

					String HubNoforlabel = request.getParameter("HubNoforlabel");
					if (HubNoforlabel == null)
						HubNoforlabel = "";
					HubNoforlabel.trim();
					String HubNum = "";
					if (HubNoforlabel.length() > 0) {
						HubNum = radian.web.HTMLHelpObj.gethubfromname(db, HubNoforlabel);
					}

					DBResultSet dbrs = null;
					ResultSet rs = null;
					String printerPath = "";
					String printerRes = "";
					try {
						String personnelid = session.getAttribute("PERSONNELID") == null ? "" : session.getAttribute("PERSONNELID").toString();
						String iszplprinter = "select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " + personnelid
						+ " and x.LABEL_STOCK = '" + PaperSize + "'";

						int printerCount = 0;

						try {
							dbrs = db.doQuery(iszplprinter);
							rs = dbrs.getResultSet();

							while (rs.next()) {
								printerPath = rs.getString("PRINTER_PATH") == null ? " " : rs.getString("PRINTER_PATH");
								printerRes = rs.getString("PRINTER_RESOLUTION_DPI") == null ? " " : rs.getString("PRINTER_RESOLUTION_DPI");
								printerCount++;
							}
						}
						catch (Exception e) {
							e.printStackTrace();
						}
						finally {
							if (dbrs != null) {
								dbrs.close();
							}
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						System.out.println("*** Error in calling barcode generator***");
					}

					String personnelid = session.getAttribute("PERSONNELID") == null ? "" : session.getAttribute("PERSONNELID").toString();
					url = obj1.generateLabels(cabids, cab8ids, genaction, HubNum, personnelid, printerPath, printerRes, facilityName);

					if ("yes".equals(request.getParameter("printTxtFile"))) {
						out.println(radian.web.HTMLHelpObj.hiddenPrintTxtFile(url.replace(".jnlp", ".txt")));
					}
					else {
						if (url.length() > 1) {
							out.println(radian.web.HTMLHelpObj.labelredirection(url));
						}
						else {
							out.println("<HTML><HEAD>\n");
							out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
							out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
							out.println("<TITLE>Container Label Generator</TITLE>\n");
							out.println("</HEAD>  \n");
							out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
							out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
							out.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n");
							out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>An Error Occured Producing Labels</b></font><P></P><BR>\n");
							out.println("</CENTER>\n");
							out.println("</BODY></HTML>    \n");
						}
						out.close();
					}
				}
				else {
					obj.doResult(request, response);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
		finally {
			db.close();
			obj = null;
			loginresult = null;
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
