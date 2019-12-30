package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.common.util.StringHandler;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.barcode.BoxLabelGenerator;
import radian.web.barcode.ZPLBarCodeGenerator;

/**
 * Title: TcmIS Description: Total Chemical Mgmt Copyright: Copyright (c) 2004
 * Company: URS Corporation
 * 
 * @author Nawaz Shaik
 * @version 06-05-04 - Moving to use ZPL to print labels 06-30-04 Changing
 *          Printer Path to be based on personnel ID 07-29-04 Changes to print
 *          based on inventory group and maintain less template files. 02-28-05
 *          Giving the option to pick a prnter if there are multiple printers at
 *          a hub
 */

public class reprintboxlabels {
	private ServerResourceBundle	bundle				= null;
	private TcmISDBModel			db					= null;
	private PrintWriter				out					= null;
	private boolean					intcmIsApplication	= false;

	public reprintboxlabels(ServerResourceBundle b, TcmISDBModel d) {
		bundle = b;
		db = d;
	}

	public void doResult(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		out = response.getWriter();
		response.setContentType("text/html");
		String personnelId = session.getAttribute("PERSONNELID") == null ? "" : session.getAttribute("PERSONNELID").toString();
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
			intcmIsApplication = true;
		}

		String User_Action = null;
		User_Action = request.getParameter("UserAction");
		if (User_Action == null)
			User_Action = "";

		String PaperSize = request.getParameter("paperSize");
		if (PaperSize == null)
			PaperSize = "35";

		String HubNoforlabel = request.getParameter("HubNoforlabel");
		if (HubNoforlabel == null)
			HubNoforlabel = "";
		HubNoforlabel.trim();

		String printKitLabels = request.getParameter("printKitLabels");
		if (printKitLabels == null)
			printKitLabels = "";

		String printerPath = request.getParameter("printerPath");
		if (printerPath == null)
			printerPath = "";

		String printerRes = request.getParameter("printerRes");
		if (printerRes == null)
			printerRes = "";

		if (User_Action.equalsIgnoreCase("generatelabels") || User_Action.equalsIgnoreCase("pickedPrinterForLabels")) {
			Vector finaldata = (Vector) session.getAttribute("PRINTBOXLBLDATA");
			Vector invgrpsprint = (Vector) session.getAttribute("BOXLBLINV_GRPS");
			Vector synch_data = new Vector();

			if (!User_Action.equalsIgnoreCase("pickedPrinterForLabels")) {
				synch_data = SynchprintData(request, finaldata);
				session.setAttribute("PRINTBOXLBLDATA", synch_data);
				finaldata = synch_data;
			}
			BoxLabelGenerator obj = new BoxLabelGenerator();

			Random rand = new Random();
			int tmpReq = rand.nextInt();
			Integer tmpReqNum = new Integer(tmpReq);

			String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
			String url = wwwHome + "labels/boxlabel" + tmpReqNum.toString() + ".pdf";
			String jnlpurl = wwwHome + "labels/boxlabel" + tmpReqNum.toString() + ".jnlp";

			try {
				if (!"811".equalsIgnoreCase(PaperSize)) {
					DBResultSet dbrs = null;
					ResultSet rs = null;
					int printerCount = 0;
					String iszplprinter = "select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = "
							+ personnelId + " and x.LABEL_STOCK = '" + PaperSize + "'";
					// String printerPath = "";
					// String printerRes = "";
					if (printerPath.length() == 0) {
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

						if (printerCount > 1) {
							printPrinterChoice(out, printKitLabels, printerPath, printerRes, PaperSize, personnelId, HubNoforlabel);
							return;
						}
					}

					ZPLBarCodeGenerator zplobj = new ZPLBarCodeGenerator(db);
					zplobj.buildboxZpl(finaldata, tmpReqNum.toString(), PaperSize, HubNoforlabel, personnelId, printerPath, printerRes, invgrpsprint);
					url = jnlpurl;
				}
				else {
					if (synch_data.size() > 0)
						obj.buildTest(synch_data, tmpReqNum.toString(), PaperSize);
				}
			}
			catch (Exception ffhf) {
				ffhf.printStackTrace();
				url = "";
			}

			finaldata = null;
			synch_data = null;

			out.println(radian.web.HTMLHelpObj.labelredirection(url));

		}
		else {
			// StringBuffer Msgn = new StringBuffer();

			out.println("<HTML>\n");
			out.println("<HEAD>\n");
			out.println("<TITLE>Print BOX Labels</TITLE>\n");
			out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
			out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
			out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
			out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
			out.println("<SCRIPT SRC=\"/clientscripts/picking.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
			out.println("<SCRIPT SRC=\"/js/hub/boxlabels.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
			out.println("</HEAD>\n");
			out.println("<BODY>\n");

			out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
			out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
			out.println("</DIV>\n");
			out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

			out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>Print BOX Labels</B>\n"));

			out.println("<FORM method=\"POST\" NAME=\"picking\" onSubmit=\"return SubmitOnlyOnce()\">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"generatelabels\">\n");

			out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"300\" CLASS=\"moveup\">\n");
			out.println("<TR VALIGN=\"MIDDLE\">\n");
			// Options
			out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
			out.println("<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"paperSize\" value=\"35\" checked>\n");
			out.println("</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option3\">3''/5''&nbsp;</TD>\n");
			out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
			out.println("<INPUT ID=\"option2\" TYPE=\"radio\" NAME=\"paperSize\" value=\"811\">\n");
			out.println("</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option4\">8.5''/11''</TD>\n\n");
			// Generate Labels Button
			out.println("<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
			out.println(
					"<INPUT TYPE=\"submit\" VALUE=\"Generate Labels\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
			out.println("</TD>\n");

			out.println("<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
			out.println(
					"<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\">\n");
			out.println("</TD></TR></TABLE>\n");

			Vector printdata = (Vector) session.getAttribute("PRINTDATA");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"HubNoforlabel\" VALUE=\"" + HubNoforlabel + "\">\n");
			// out.println(Msgn);
			out.println(buildDetails(printdata, User_Action, session));
		}
		out.close();
	}

	private void printPrinterChoice(PrintWriter out, String printKitLabels, String printerPath, String printerRes, String paperSize, String personnelId, String HubNoforlabel) {
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
		out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"pickedPrinterForLabels\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"printerRes\" VALUE=\"" + printerRes + "\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"printKitLabels\" VALUE=\"" + printKitLabels + "\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"paperSize\" VALUE=\"" + paperSize + "\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"HubNoforlabel\" VALUE=\"" + HubNoforlabel + "\">\n");

		out.println("<BR><B>Please Pick a Printer:</B>");
		out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"500\" CLASS=\"moveup\">\n");
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String tmpPrinterPath = "";
		String tmpPrinterRes = "";

		try {
			String iszplprinter = "select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = "
					+ personnelId + " and x.LABEL_STOCK = '" + paperSize + "' ORDER BY x.PRINTER_PATH";
			dbrs = db.doQuery(iszplprinter);
			rs = dbrs.getResultSet();

			while (rs.next()) {
				tmpPrinterPath = rs.getString("PRINTER_PATH") == null ? " " : rs.getString("PRINTER_PATH");
				tmpPrinterRes = rs.getString("PRINTER_RESOLUTION_DPI") == null ? " " : rs.getString("PRINTER_RESOLUTION_DPI");

				out.println("<TR VALIGN=\"MIDDLE\">\n");
				out.println("<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
				out.println("<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"printerPath\" onChange=\"setprinterRes('" + tmpPrinterRes + "')\" value=\"" + tmpPrinterPath + "\" "
						+ (tmpPrinterPath.equalsIgnoreCase(printerPath) ? "checked" : "") + ">\n");
				out.println("</TD>\n<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" ID=\"option3\">" + tmpPrinterPath + "&nbsp;</TD>\n");
				/*
				 * out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n"); out.
				 * println("<INPUT ID=\"option2\" TYPE=\"radio\" NAME=\"printerRes\" value=\""
				 * +printerRes+"\">\n"); out.
				 * println("</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option4\">"
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
		out.println(
				"<INPUT TYPE=\"submit\" VALUE=\"Generate Labels\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
		out.println("</TD>\n");

		out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println(
				"<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\">\n");
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

	private Vector SynchprintData(HttpServletRequest request, Vector in_data) {
		Vector new_data = new Vector();
		String total = BothHelpObjs.makeBlankFromNull(request.getParameter("Total"));

		try {
			for (int i = 1; i < (Integer.parseInt(total) + 1); i++) {
				Hashtable hDdata = new Hashtable();

				hDdata = (Hashtable) (in_data.elementAt(i - 1));

				String nooflabels = "";
				nooflabels = BothHelpObjs.makeBlankFromNull(request.getParameter("nooflabels" + i));
				nooflabels = nooflabels.trim();

				if (nooflabels.length() > 0) {
					int testnum = 0;
					try {
						testnum = Integer.parseInt(nooflabels);
					}
					catch (NumberFormatException ex) {
						testnum = 0;
					}

					if (testnum > 0) {

						String hdmrline = (hDdata.get("DETAIL_4") == null ? " " : hDdata.get("DETAIL_4").toString());
						String mrline = BothHelpObjs.makeBlankFromNull(request.getParameter("mrline" + i));

						if (hdmrline.equalsIgnoreCase(mrline)) {

							String delivery_point_desc = BothHelpObjs.makeBlankFromNull(request.getParameter("delivery_point_desc" + i));
							String cat_part_no = BothHelpObjs.makeBlankFromNull(request.getParameter("cat_part_no" + i));

							hDdata.remove("DETAIL_3");
							hDdata.put("DETAIL_3", delivery_point_desc);
							hDdata.remove("DETAIL_7");
							hDdata.put("DETAIL_7", cat_part_no);
							hDdata.remove("DETAIL_0");
							hDdata.put("DETAIL_0", "" + testnum + "");
						}
						
						String pattern = "yyyy/MM/dd";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						String today = simpleDateFormat.format(new Date());
						
						hDdata.remove("DATE_PRINTED");
						hDdata.put("DATE_PRINTED", today);

						Hashtable boxdata = new Hashtable();
						for (int j = 0; j < testnum; j++) {
							boxdata = (Hashtable) hDdata.clone();

							boxdata.remove("CUR_LABEL_COUNT");
							boxdata.put("CUR_LABEL_COUNT", j + 1);

							boxdata.remove("PPS_BARCODE");
							boxdata.put("PPS_BARCODE", getPPSBarcode(hDdata.get("PICKING_UNIT_ID").toString(), j + 1, testnum));
							new_data.addElement(boxdata);
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("Re-Print Labels", intcmIsApplication));
		}

		return new_data;
	}

	private String buildDetails(Vector VFromSession, String SubuserAction, HttpSession sessiondetails) {
		StringBuffer Msg = new StringBuffer();
		Vector dataV1 = new Vector();
		Hashtable dataFromSession = new Hashtable();
		Vector VMr_Number = new Vector();
		Vector VLine_Number = new Vector();
		Vector Vlabl_qty = new Vector();
		Vector VPicking_Unit = new Vector();
		Vector invengrpvec = new Vector();
		int lineadded = 0;
		DBResultSet dbrs = null;
		ResultSet rs = null;

		String supplierpage = sessiondetails.getAttribute("SUPPLIERPAGE") == null ? "" : sessiondetails.getAttribute("SUPPLIERPAGE").toString();

		try {
			for (int i = 0; i < VFromSession.size(); i++) {
				dataFromSession = new Hashtable();
				VMr_Number = new Vector();
				VLine_Number = new Vector();
				Vlabl_qty = new Vector();
				VPicking_Unit = new Vector();
				Vector dropShipVector = new Vector();
				Vector radianPoVector = new Vector();
				Vector poLineVector = new Vector();

				dataFromSession = (Hashtable) VFromSession.elementAt(i);
				VMr_Number = (Vector) dataFromSession.get("mr_number");
				VLine_Number = (Vector) dataFromSession.get("line_number");
				Vlabl_qty = (Vector) dataFromSession.get("lbl_qty");
				VPicking_Unit = (Vector) dataFromSession.get("picking_unit");

				try {
					dropShipVector = (Vector) dataFromSession.get("dropShip");
					radianPoVector = (Vector) dataFromSession.get("radianPo");
					poLineVector = (Vector) dataFromSession.get("poLine");
				}
				catch (Exception ex) {
					dropShipVector = new Vector();
					radianPoVector = new Vector();
					poLineVector = new Vector();
				}

				if (VPicking_Unit == null) {
					VPicking_Unit = new Vector();
				}

				if (Vlabl_qty == null) {
					Vlabl_qty = new Vector();
				}

				if (VMr_Number == null) {
					return radian.web.HTMLHelpObj.printMessageinTable("No Records Found").toString();
				}

				Msg.append("<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n");

				for (int j = 0; j < VMr_Number.size(); j++) {
					boolean createHdr = false;

					if (j % 10 == 0) {
						createHdr = true;
					}

					if (createHdr) {
						Msg.append("<tr align=\"center\">\n");
						Msg.append("<TH width=\"5%\"  height=\"38\">Requestor</TH>\n");
						Msg.append("<TH width=\"5%\"  height=\"38\">End User</TH>\n");
						Msg.append("<TH width=\"5%\"  height=\"38\">Facility</TH>\n");
						Msg.append("<TH width=\"5%\"  height=\"38\">Work Area</TH>\n");
						Msg.append("<TH width=\"5%\"  height=\"38\">MR-Line</TH>\n");
						Msg.append("<TH width=\"5%\"  height=\"38\">Deliver To</TH>\n");
						Msg.append("<TH width=\"5%\"  height=\"38\">Part No.</TH>\n");
						Msg.append(
								"<TH width=\"5%\"  height=\"38\">No of Labels<br><input type=\"text\" CLASS=\"DETAILS\" name=\"allLabelQuantity\" id=\"allLabelQuantity\" value=\"10\" maxlength=\"3\" size=\"2\" onChange=\"changeAllLabelQuantity()\"></TH>\n");
						Msg.append("</tr>\n");
						createHdr = false;
					}

					String pickingUnitId = "";
					String prnumber = "";
					String lineitem = "";
					String labelqty = "10";
					String dropShip = "";
					String radianPo = "";
					String poLine = "";
					String Query_Statement = "";

					prnumber = (String) VMr_Number.elementAt(j);
					lineitem = (String) VLine_Number.elementAt(j);

					pickingUnitId = (VPicking_Unit.size() - 1 < j || VPicking_Unit.elementAt(j) == null) ? "" : (String) VPicking_Unit.elementAt(j);
					labelqty = (Vlabl_qty.size() - 1 < j || Vlabl_qty.elementAt(j) == null) ? "10" : (String) Vlabl_qty.elementAt(j);

					Query_Statement = "SELECT blv.LAST_NAME, blv.FIRST_NAME, blv.END_USER, blv.FACILITY_NAME, blv.APPLICATION, blv.DELIVERY_POINT, "
							+ "blv.PR_NUMBER, blv.LINE_ITEM, blv.COMPANY_ID, blv.CAT_PART_NO, blv.APPLICATION_DESC, blv.DELIVERY_POINT_DESC, "
							+ "blv.INVENTORY_GROUP, blv.REQUESTOR_NAME, blv.REQUESTOR_PHONE, blv.REQUESTOR_FAX, blv.REQUESTOR_EMAIL, "
							+ "blv.PAYMENT_TERMS, blv.PO_NUMBER, blv.SHIP_TO_ADDRESS_LINE_1, blv.SHIP_TO_ADDRESS_LINE_2, "
							+ "blv.SHIP_TO_ADDRESS_LINE_3, blv.SHIP_TO_ADDRESS_LINE_4, blv.SHIP_TO_ADDRESS_LINE_5, blv.SHIP_TO_PHONE, "
							+ "blv.SHIP_TO_FAX, blv.SHIP_FROM_ADDRESS_LINE_1, blv.SHIP_FROM_ADDRESS_LINE_2, blv.SHIP_FROM_ADDRESS_LINE_3, "
							+ "blv.SHIP_FROM_ADDRESS_LINE_4, blv.SHIP_FROM_ADDRESS_LINE_5, blv.SHIP_FROM_PHONE, blv.SHIP_FROM_FAX, "
							+ "REPLACE( string_agg( blv.receipt_list ), ',', '; ' ) receipt_list, pui.PICKING_UNIT_ID " 
							+ "FROM   box_label_view blv, picking_unit_issue pui "
							+ "where blv.PR_NUMBER = '" + prnumber + "' and blv.LINE_ITEM = '" + lineitem + "' and pui.issue_id(+) = blv.issue_id";

					if (StringUtils.isNotBlank(pickingUnitId)) {
						Query_Statement += " and pui.picking_unit_id = " + pickingUnitId;
					}
					
					Query_Statement += " Group BY blv.LAST_NAME, blv.FIRST_NAME, blv.END_USER, blv.FACILITY_NAME, blv.APPLICATION, blv.DELIVERY_POINT, blv.PR_NUMBER, "
							+ "blv.LINE_ITEM, blv.COMPANY_ID, blv.CAT_PART_NO, blv.APPLICATION_DESC, blv.DELIVERY_POINT_DESC, blv.INVENTORY_GROUP, "
							+ "blv.REQUESTOR_NAME, blv.REQUESTOR_PHONE, blv.REQUESTOR_FAX, blv.REQUESTOR_EMAIL, blv.PAYMENT_TERMS, blv.PO_NUMBER, "
							+ "blv.SHIP_TO_ADDRESS_LINE_1, blv.SHIP_TO_ADDRESS_LINE_2, blv.SHIP_TO_ADDRESS_LINE_3, blv.SHIP_TO_ADDRESS_LINE_4, "
							+ "blv.SHIP_TO_ADDRESS_LINE_5, blv.SHIP_TO_PHONE, blv.SHIP_TO_FAX, blv.SHIP_FROM_ADDRESS_LINE_1, "
							+ "blv.SHIP_FROM_ADDRESS_LINE_2, blv.SHIP_FROM_ADDRESS_LINE_3, blv.SHIP_FROM_ADDRESS_LINE_4, "
							+ "blv.SHIP_FROM_ADDRESS_LINE_5, blv.SHIP_FROM_PHONE, blv.SHIP_FROM_FAX, pui.PICKING_UNIT_ID";

					if ("Yes".equalsIgnoreCase(supplierpage)) {
						dropShip = (String) dropShipVector.elementAt(j);
						if ("N".equalsIgnoreCase(dropShip) || (prnumber.length() == 0 && lineitem.length() == 0)) {
							radianPo = (String) radianPoVector.elementAt(j);
							poLine = (String) poLineVector.elementAt(j);
							Query_Statement = "Select LAST_NAME, FIRST_NAME, '' END_USER, FACILITY_ID, APPLICATION, DELIVERY_POINT, '' PR_NUMBER, ''LINE_ITEM, '' COMPANY_ID, CAT_PART_NO, APPLICATION_DESC, DELIVERY_POINT_BUILDING DELIVERY_POINT_DESC,INVENTORY_GROUP from supplier_label_view where RADIAN_PO = '"
									+ radianPo + "' and PO_LINE = '" + poLine + "'";
						}
					}

					dbrs = db.doQuery(Query_Statement);
					rs = dbrs.getResultSet();
					Hashtable data = new Hashtable();
					StringBuffer Msgtmp = new StringBuffer();

					while (rs.next()) {
						lineadded++;
						String firstname = rs.getString("FIRST_NAME") == null ? " " : rs.getString("FIRST_NAME");
						String lastname = rs.getString("LAST_NAME") == null ? " " : rs.getString("LAST_NAME");
						String fullname = firstname + "  " + lastname;

						String end_user = rs.getString("END_USER") == null ? "" : rs.getString("END_USER").toString();
						String facility_name = rs.getString("FACILITY_NAME") == null ? "" : rs.getString("FACILITY_NAME").toString();
						// String application=rs.getString( "APPLICATION" ) ==
						// null ? "" : rs.getString( "APPLICATION" ).toString();
						// String delivery_point=rs.getString( "DELIVERY_POINT"
						// ) == null ? "" : rs.getString( "DELIVERY_POINT"
						// ).toString();
						String application_desc = rs.getString("APPLICATION_DESC") == null ? "" : rs.getString("APPLICATION_DESC").toString();
						String delivery_point_desc = rs.getString("DELIVERY_POINT_DESC") == null ? "" : rs.getString("DELIVERY_POINT_DESC").toString();

						String pr_number = rs.getString("PR_NUMBER") == null ? "" : rs.getString("PR_NUMBER").toString();
						String line_item = rs.getString("LINE_ITEM") == null ? "" : rs.getString("LINE_ITEM").toString();
						String cat_part_no = rs.getString("CAT_PART_NO") == null ? "" : rs.getString("CAT_PART_NO").toString();
						String inventorygrp = rs.getString("INVENTORY_GROUP") == null ? " " : rs.getString("INVENTORY_GROUP");
						if (!invengrpvec.contains(inventorygrp)) {
							invengrpvec.add(inventorygrp);
						}

						String requestorName = rs.getString("REQUESTOR_NAME") == null ? "" : rs.getString("REQUESTOR_NAME").toString();
						String requestorPhone = rs.getString("REQUESTOR_PHONE") == null ? "" : rs.getString("REQUESTOR_PHONE").toString();
						String requestorFax = rs.getString("REQUESTOR_FAX") == null ? "" : rs.getString("REQUESTOR_FAX").toString();
						String requestorEmail = rs.getString("REQUESTOR_EMAIL") == null ? "" : rs.getString("REQUESTOR_EMAIL").toString();
						String paymentTerms = rs.getString("PAYMENT_TERMS") == null ? "" : rs.getString("PAYMENT_TERMS").toString();
						String poNumber = rs.getString("PO_NUMBER") == null ? "" : rs.getString("PO_NUMBER").toString();
						String shipToAddressLine1 = rs.getString("SHIP_TO_ADDRESS_LINE_1") == null ? "" : rs.getString("SHIP_TO_ADDRESS_LINE_1").toString();
						String shipToAddressLine2 = rs.getString("SHIP_TO_ADDRESS_LINE_2") == null ? "" : rs.getString("SHIP_TO_ADDRESS_LINE_2").toString();
						String shipToAddressLine3 = rs.getString("SHIP_TO_ADDRESS_LINE_3") == null ? "" : rs.getString("SHIP_TO_ADDRESS_LINE_3").toString();
						String shipToAddressLine4 = rs.getString("SHIP_TO_ADDRESS_LINE_4") == null ? "" : rs.getString("SHIP_TO_ADDRESS_LINE_4").toString();
						String shipToAddressLine5 = rs.getString("SHIP_TO_ADDRESS_LINE_5") == null ? "" : rs.getString("SHIP_TO_ADDRESS_LINE_5").toString();
						String shipToPhone = rs.getString("SHIP_TO_PHONE") == null ? "" : rs.getString("SHIP_TO_PHONE").toString();
						String shipToFax = rs.getString("SHIP_TO_FAX") == null ? "" : rs.getString("SHIP_TO_FAX").toString();
						String shipFromAddressLine1 = rs.getString("SHIP_FROM_ADDRESS_LINE_1") == null ? "" : rs.getString("SHIP_FROM_ADDRESS_LINE_1").toString();
						String shipFromAddressLine2 = rs.getString("SHIP_FROM_ADDRESS_LINE_2") == null ? "" : rs.getString("SHIP_FROM_ADDRESS_LINE_2").toString();
						String shipFromAddressLine3 = rs.getString("SHIP_FROM_ADDRESS_LINE_3") == null ? "" : rs.getString("SHIP_FROM_ADDRESS_LINE_3").toString();
						String shipFromAddressLine4 = rs.getString("SHIP_FROM_ADDRESS_LINE_4") == null ? "" : rs.getString("SHIP_FROM_ADDRESS_LINE_4").toString();
						String shipFromAddressLine5 = rs.getString("SHIP_FROM_ADDRESS_LINE_5") == null ? "" : rs.getString("SHIP_FROM_ADDRESS_LINE_5").toString();
						String shipFromPhone = rs.getString("SHIP_FROM_PHONE") == null ? "" : rs.getString("SHIP_FROM_PHONE").toString();
						String shipFromFax = rs.getString("SHIP_FROM_FAX") == null ? "" : rs.getString("SHIP_FROM_FAX").toString();
						String receiptList = rs.getString("RECEIPT_LIST") == null ? "" : rs.getString("RECEIPT_LIST").toString();

						data.put("DETAIL_1", fullname);
						data.put("DETAIL_0", "1");
						data.put("DETAIL_2", end_user);
						data.put("DETAIL_3", "" + delivery_point_desc + "");
						data.put("DETAIL_4", "" + pr_number + "-" + line_item + "");
						data.put("DETAIL_5", facility_name);
						data.put("DETAIL_6", "" + application_desc + "");
						data.put("DETAIL_7", cat_part_no);
						data.put("PART_DESCRIPTION", "Placeholder Part Description");
						data.put("QTY", "Placeholder Quantity");
						data.put("LOT", "Placeholder Lot");
						data.put("TO_NO", "Placeholder To Number");
						data.put("DELIVERY", "Placeholder Delivery");
						data.put("TO_LINE_NO", "Placeholder To Line Number");
						data.put("SHIP_TO_NO", "Placeholder Ship To Number");
						data.put("INVENTORY_GROUP", inventorygrp);
						data.put("REQUESTOR_NAME", requestorName);
						data.put("REQUESTOR_PHONE", requestorPhone);
						data.put("REQUESTOR_FAX", requestorFax);
						data.put("REQUESTOR_EMAIL", requestorEmail);
						data.put("PAYMENT_TERMS", paymentTerms);
						data.put("PO_NUMBER", poNumber);
						data.put("SHIP_TO_ADDRESS_LINE_1", shipToAddressLine1);
						data.put("SHIP_TO_ADDRESS_LINE_2", shipToAddressLine2);
						data.put("SHIP_TO_ADDRESS_LINE_3", shipToAddressLine3);
						data.put("SHIP_TO_ADDRESS_LINE_4", shipToAddressLine4);
						data.put("SHIP_TO_ADDRESS_LINE_5", shipToAddressLine5);
						data.put("SHIP_TO_PHONE", shipToPhone);
						data.put("SHIP_TO_FAX", shipToFax);
						String shipToAddress = "" + shipToAddressLine1 + "\\&" + shipToAddressLine2 + "\\&" + shipToAddressLine3 + "\\&" + shipToAddressLine4 + "\\&"
								+ shipToAddressLine5 + "";
						if (shipToPhone.length() > 0) {
							shipToAddress += "\\&Tel:" + shipToPhone + "";
						}
						if (shipToFax.length() > 0) {
							shipToAddress += "\\&Fax:" + shipToFax + "";
						}
						data.put("SHIP_TO_ADDRESS", shipToAddress);
						String shipFromAddress = "" + shipFromAddressLine1 + "\\&" + shipFromAddressLine2 + "\\&" + shipFromAddressLine3 + "\\&" + shipFromAddressLine4 + "\\&"
								+ shipFromAddressLine5 + "";
						if (shipFromPhone.length() > 0) {
							shipFromAddress += "\\&Tel:" + shipFromPhone + "";
						}
						if (shipFromFax.length() > 0) {
							shipFromAddress += "\\&Fax:" + shipFromFax + "";
						}
						data.put("SHIP_FROM_ADDRESS", shipFromAddress);
						data.put("SHIP_FROM_ADDRESS_LINE_1", shipFromAddressLine1);
						data.put("SHIP_FROM_ADDRESS_LINE_2", shipFromAddressLine2);
						data.put("SHIP_FROM_ADDRESS_LINE_3", shipFromAddressLine3);
						data.put("SHIP_FROM_ADDRESS_LINE_4", shipFromAddressLine4);
						data.put("SHIP_FROM_ADDRESS_LINE_5", shipFromAddressLine5);
						data.put("SHIP_FROM_PHONE", shipFromPhone);
						data.put("SHIP_FROM_FAX", shipFromFax);
						if (!StringHandler.isBlankString(pickingUnitId))
							data.put("PICKING_UNIT_ID", "PUI" + pickingUnitId);
						else
							data.put("PICKING_UNIT_ID", "");
						data.put("RECEIPT_LIST", getReceipts(receiptList));

						dataV1.addElement(data);

						String Color = " ";
						String invcolor = "CLASS=\"INVISIBLEHEADWHITE";

						if (j % 2 == 0) {
							Color = "CLASS=\"white";
							invcolor = "CLASS=\"INVISIBLEHEADWHITE";
						}
						else {
							Color = "CLASS=\"blue";
							invcolor = "CLASS=\"INVISIBLEHEADBLUE";
						}

						Msgtmp.append("<TR>\n");
						Msg.append("<input type=\"hidden\" name=\"mrline" + lineadded + "\" value=\"" + prnumber + "-" + lineitem + "\">\n");
						Msgtmp.append("<td " + Color + "\" width=\"5%\" height=\"38\">" + fullname + "</td>\n");
						Msgtmp.append("<td " + Color + "\" width=\"5%\" height=\"38\">" + end_user + "</td>\n");
						Msgtmp.append("<td " + Color + "\" width=\"5%\" height=\"38\">" + facility_name + "</td>\n");
						Msgtmp.append("<td " + Color + "\" width=\"5%\" height=\"38\">" + application_desc + "</td>\n");
						Msgtmp.append("<td " + Color + "\" width=\"5%\" height=\"38\">" + pr_number + "-" + line_item + "</td>\n");
						if ("Yes".equalsIgnoreCase(supplierpage)) {
							Msgtmp.append("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"hidden\" name=\"delivery_point_desc" + lineadded + "\"  value=\""
									+ delivery_point_desc + "\">" + delivery_point_desc + "</td>\n");
							Msgtmp.append("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"hidden\" name=\"cat_part_no" + lineadded + "\"  value=\"" + cat_part_no
									+ "\">" + cat_part_no + "</td>\n");
						}
						else {
							Msgtmp.append("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"delivery_point_desc" + lineadded
									+ "\"  value=\"" + delivery_point_desc + "\" maxlength=\"30\" size=\"10\"></td>\n");
							Msgtmp.append("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"cat_part_no" + lineadded + "\"  value=\""
									+ cat_part_no + "\" maxlength=\"10\" size=\"20\"></td>\n");
						}
						Msgtmp.append("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"nooflabels" + lineadded + "\"  value=\""
								+ labelqty + "\" maxlength=\"5\" size=\"3\"></td>\n");
						Msgtmp.append("</TR>\n");

					}
					Msg.append(Msgtmp);
				}
			}

			Msg.append("</table>\n");
			Msg.append("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
			Msg.append("<tr>");
			Msg.append("<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
			Msg.append("</TD></tr>");
			Msg.append("</table>\n");
			Msg.append("<input type=\"hidden\" name=\"Total\" value=\"" + lineadded + "\">\n");

			sessiondetails.setAttribute("PRINTBOXLBLDATA", dataV1);
			sessiondetails.setAttribute("BOXLBLINV_GRPS", invengrpvec);
			dataV1 = null;

			Msg.append("</form>\n");
			Msg.append("</body></html>\n");
		}
		catch (Exception e) {
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("Print BOX Labels", intcmIsApplication));
		}

		dataV1 = null;
		dataFromSession = null;
		VMr_Number = null;
		VLine_Number = null;
		invengrpvec = null;

		return Msg.toString();
	}

	private String getPPSBarcode(String pickingUnitId, int curLabelCount, int totalLabels) {
		StringBuilder barcodeStr = new StringBuilder("");

		if (!StringHandler.isBlankString(pickingUnitId)) {
			barcodeStr.append("PU|");
			barcodeStr.append(pickingUnitId + "|");
			barcodeStr.append(curLabelCount + "|");
			barcodeStr.append(totalLabels);
		}

		return barcodeStr.toString();
	}

	/**
	 * Parse a list of receipt and quantity pairs and format the list for
	 * display on the Delivery label. The formatted list is limited to 10
	 * receipts.
	 * 
	 * @param receiptList
	 *            list of receipt and quantity pairs from the database
	 * @return the formatted list of receipt and quantity pairs
	 */
	private String getReceipts(String receiptList) {
		StringBuilder receipts = new StringBuilder("");

		if (!StringHandler.isBlankString(receiptList)) {
			// Param receiptList is expected to be in the format of
			// receipt_id|"Qty" qty ie. 21027551|Qty 8
			// Corresonding database update TCMISDEV-3988
			String[] receiptListArray = receiptList.split(";");
			int maxReceipts = 10;

			if (receiptListArray.length < 10)
				maxReceipts = receiptListArray.length;

			for (int i = 0; i < maxReceipts; i++) {
				receipts.append(receiptListArray[i]);
				receipts.append("   ");
			}
		}
		return receipts.toString();
	}
}
