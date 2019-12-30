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
 * Title: TcmIS Description: Total Chemical Mgmt Copyright: Copyright (c) 2001
 * Company: URS Corporation
 * 
 * @author Nawaz Shaik
 * @version 04-18-04 - Giving an option to print all cabinet,bin and receipt
 *          labels for a cabinet 04-26-04 - Passing Hub Number to labelgenrator
 *          06-30-04 Changing Printer Path to be based on personnel ID
 */

public class cabinetlabelstart extends HttpServlet implements SingleThreadModel {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * HTTP POST handler.
	 * 
	 * @param request
	 *            An object implementing the request context
	 * @param response
	 *            An object implementing the response context
	 * @exception ServletException
	 *                A wrapper for all exceptions thrown by the servlet.
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		RayTcmISDBModel db = null;
		cabinetLabels obj = null;
		cabilabelPage obj1 = null;
		String User_Action = "";
		HttpSession session = request.getSession();
		Hashtable loginresult = new Hashtable();

		Vector cabids = new Vector();
		Vector cab8ids = new Vector();

		try {
			// db = new RayTcmISDBModel("Tcm_Ops","1");
			// TCMISPROD-3175 Add locale for database connection for
			// container_label_master_view to use expire_date_locale
			db = new RayTcmISDBModel("Tcm_Ops", "2", radian.web.HTMLHelpObj.getTcmISLocaleString(request));
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
				obj = new cabinetLabels(new InternalServerResourceBundle(), db);

				if (radian.web.HTMLHelpObj.hasaccessto(session, "Cabinet Label")) {
					obj.setupdateStatus(true);
				}
				else {
					String CompanyID = session.getAttribute("COMPANYID") == null ? "" : session.getAttribute("COMPANYID").toString();
					if ("Radian".equalsIgnoreCase(CompanyID)) {
						obj.setupdateStatus(false);
					}
					// else {
					// out.println(radian.web.HTMLHelpObj.printLoginScreenOptions("You don't have access to this page.",
					// "Cabinet Labels", session));
					// out.close();
					// return;
					// }
				}

				User_Action = request.getParameter("UserAction");
				if (User_Action == null) User_Action = "New";
				String generate_labels = request.getParameter("generate_labels");
				if (generate_labels == null) generate_labels = "No";
				String genaction = request.getParameter("genaction");
				if (genaction == null) genaction = "generatecablabels";
				String facilityName = request.getParameter("facilityName");

				String selectedPrinterPath = request.getParameter("printerPath");
				if (selectedPrinterPath == null) selectedPrinterPath = "";
				// System.out.println("selectedPrinterPath  "+
				// selectedPrinterPath);

				if ("yes".equalsIgnoreCase(generate_labels) && "GenerateLabels".equalsIgnoreCase(User_Action)) {
					cabids = (Vector) session.getAttribute("LABEL_CAB_IDS");
					cab8ids = (Vector) session.getAttribute("LABEL_CAB_LABEL_IDS");

					String PaperSize = request.getParameter("paperSize");
					if (PaperSize == null) PaperSize = "31";

					obj1 = new cabilabelPage(db);
					obj1.setpaperSize(PaperSize);

					String url = "";

					String HubNoforlabel = request.getParameter("HubNoforlabel");
					if (HubNoforlabel == null) HubNoforlabel = "";
					HubNoforlabel.trim();
					String HubNum = "";
					if (HubNoforlabel.length() > 0) {
						HubNum = radian.web.HTMLHelpObj.gethubfromname(db, HubNoforlabel);
					}

					String personnelid = session.getAttribute("PERSONNELID") == null ? "" : session.getAttribute("PERSONNELID").toString();

					DBResultSet dbrs = null;
					ResultSet rs = null;
					String printerPath = "";
					String printerRes = "";
					try {
						String iszplprinter = "select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " + personnelid + " and x.LABEL_STOCK = '"
								+ PaperSize + "'";

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

						if (printerCount > 1 && selectedPrinterPath.trim().length() == 0 && !"generatebaseline".equalsIgnoreCase(genaction) && !"yes".equals(request.getParameter("printTxtFile"))) {
							printPrinterChoice(db, out, printerPath, printerRes, PaperSize, personnelid, genaction, HubNoforlabel, User_Action, generate_labels);
							return;
						}
						else if (printerCount > 1 && selectedPrinterPath.trim().length() > 0) {
							printerPath = selectedPrinterPath;
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						System.out.println("*** Error in calling barcode generator***");
					}

					if ("allcabilabels".equalsIgnoreCase(genaction)) {
						cabialllabelPage allobj1 = new cabialllabelPage(db);
						url = allobj1.generateLabels(cabids, cab8ids, genaction, HubNum, personnelid, printerPath, printerRes);
					}
					else {
						url = obj1.generateLabels(cabids, cab8ids, genaction, HubNum, personnelid, printerPath, printerRes, facilityName);
					}

					if ("yes".equals(request.getParameter("printTxtFile"))) {
						out.println(radian.web.HTMLHelpObj.hiddenPrintTxtFile(url.replace(".jnlp", ".txt")));
					}
					else {
						out.println(radian.web.HTMLHelpObj.labelredirection(url));
					}
					out.close();

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
				// ignore
			}
			// Runtime.getRuntime().gc();
			return;
		}
	}

	private void printPrinterChoice(RayTcmISDBModel db, PrintWriter out, String printerPath, String printerRes, String papersize, String personnelId, String genaction, String HubNoforlabel, String UserAction, String generate_labels) {
		out.println("<HTML>\n");
		out.println("<HEAD>\n");
		out.println("<TITLE>Re-Print Labels</TITLE>\n");
		out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
		out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
		out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
		out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
		out.println("<SCRIPT SRC=\"/clientscripts/picking.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
		out.println("</HEAD>\n");
		out.println("<BODY>\n");

		out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
		out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
		out.println("</DIV>\n");
		out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

		out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>Re-Print Labels</B>\n"));

		out.println("<FORM method=\"POST\" NAME=\"picking\" onSubmit=\"return SubmitOnlyOnce()\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"" + UserAction + "\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"printerRes\" VALUE=\"" + printerRes + "\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"generate_labels\" VALUE=\"" + generate_labels + "\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"paperSize\" VALUE=\"" + papersize + "\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"genaction\" VALUE=\"" + genaction + "\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"HubNoforlabel\" VALUE=\"" + HubNoforlabel + "\">\n");

		out.println("<BR><B>Please Pick a Printer:</B>");
		out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"500\" CLASS=\"moveup\">\n");
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String tmpPrinterPath = "";
		String tmpPrinterRes = "";

		try {
			String iszplprinter = "select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " + personnelId + " and x.LABEL_STOCK = '" + papersize
					+ "' ORDER BY x.PRINTER_PATH";
			dbrs = db.doQuery(iszplprinter);
			rs = dbrs.getResultSet();

			while (rs.next()) {
				tmpPrinterPath = rs.getString("PRINTER_PATH") == null ? " " : rs.getString("PRINTER_PATH");
				tmpPrinterRes = rs.getString("PRINTER_RESOLUTION_DPI") == null ? " " : rs.getString("PRINTER_RESOLUTION_DPI");

				out.println("<TR VALIGN=\"MIDDLE\">\n");
				out.println("<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
				out.println("<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"printerPath\" onChange=\"setprinterRes('" + tmpPrinterRes + "')\" value=\"" + tmpPrinterPath + "\" " + (tmpPrinterPath.equalsIgnoreCase(printerPath) ? "checked" : "") + ">\n");
				out.println("</TD>\n<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" ID=\"option3\">" + tmpPrinterPath + "&nbsp;</TD>\n");
				/*
				 * out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
				 * out.println(
				 * "<INPUT ID=\"option2\" TYPE=\"radio\" NAME=\"printerRes\" value=\""
				 * +printerRes+"\">\n"); out.println(
				 * "</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option4\">"
				 * +printerRes+"</TD>\n\n");
				 */
				out.println("</TR>\n");
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

		out.println("<TR VALIGN=\"MIDDLE\">\n");
		// Generate Labels Button
		out.println("<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"submit\" VALUE=\"Generate Labels\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
		out.println("</TD>\n");

		out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\">\n");
		out.println("</TD></TR>\n");

		/*
		 * out.println("<TR VALIGN=\"MIDDLE\">\n"); out.println(
		 * "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n"
		 * ); out.println(
		 * "<INPUT type=\"checkbox\" name=\"printKitLabels\" value=\"Yes\" " + (
		 * printKitLabels.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n"
		 * ); out.println( "</TD>\n" ); out.println(
		 * "<TD WIDTH=\"5%\" COLSPAN=\"7\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n"
		 * ); out.println( "&nbsp;Skip Kit/Case Qty Labels\n" ); out.println(
		 * "</TD>\n" );
		 */
		out.println("</TABLE>\n");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
