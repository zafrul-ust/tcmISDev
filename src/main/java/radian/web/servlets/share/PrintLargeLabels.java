package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.barcode.ZPLBarCodeGenerator;

import com.tcmis.common.admin.beans.PersonnelBean;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2005
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class PrintLargeLabels {
	private ServerResourceBundle bundle = null;
	private TcmISDBModel db = null;
	private PrintWriter out = null;
	private static org.apache.log4j.Logger barclog = org.apache.log4j.Logger.getLogger(PrintLargeLabels.class);
	private boolean intcmIsApplication = false;

	public PrintLargeLabels(ServerResourceBundle b, TcmISDBModel d) {
		bundle = b;
		db = d;
	}

	public void doResult(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		out = response.getWriter();
		response.setContentType("text/html");

		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		if (personnelBean !=null) {
			Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
			if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
				intcmIsApplication = true;
			}
		}

		String personnelId = session.getAttribute("PERSONNELID") == null ? "" : session.getAttribute("PERSONNELID").toString();

		Vector receiptData = (Vector) session.getAttribute("LARGE_LABEL_DATA");

		String User_Action = null;
		User_Action = request.getParameter("UserAction");
		if (User_Action == null) {
			User_Action = "";

		}
		String PaperSize = request.getParameter("paperSize");
		if (PaperSize == null) {
			PaperSize = "64";

		}
		String printerPath = request.getParameter("printerPath");
		if (printerPath == null) {
			printerPath = "";

		}
		String printerRes = request.getParameter("printerRes");
		if (printerRes == null) {
			printerRes = "";

		}
		if (User_Action.equalsIgnoreCase("generatelabels") || User_Action.equalsIgnoreCase("pickedPrinterForLabels")) {
			Vector synch_data = new Vector();
			if (!User_Action.equalsIgnoreCase("pickedPrinterForLabels")) {
				synch_data = SynchprintData(request, receiptData);
				session.setAttribute("LARGE_LABEL_DATA", synch_data);
				receiptData = synch_data;
			}

			String url = "";
			try {
				DBResultSet dbrs = null;
				ResultSet rs = null;
				int printerCount = 0;
				String iszplprinter = "select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " + personnelId + " and x.LABEL_STOCK = '" + PaperSize + "'";
				if (printerPath.length() == 0) {
					try {
						dbrs = db.doQuery(iszplprinter);
						rs = dbrs.getResultSet();

						while (rs.next()) {
							printerPath = rs.getString("PRINTER_PATH") == null ? " " : rs.getString("PRINTER_PATH");
							printerRes = rs.getString("PRINTER_RESOLUTION_DPI") == null ? " " : rs.getString("PRINTER_RESOLUTION_DPI");
							printerCount++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (dbrs != null) {
							dbrs.close();
						}
					}

					if (printerCount > 1) {
						printPrinterChoice(out, printerPath, printerRes, PaperSize, personnelId);
						return;
					}
				}

				url = generateContainerLabel(receiptData, personnelId, printerPath, printerRes);
			} catch (Exception ffhf) {
				ffhf.printStackTrace();
				url = "";
			}
			receiptData = null;
			synch_data = null;
			out.println(radian.web.HTMLHelpObj.labelredirection(url));
		} else {
			boolean pageUnderConstruction = true;
			try {
				String query = "select count(*) from personnel where printer_location like 'Israel%' and personnel_id = " + personnelId;
				pageUnderConstruction = HelpObjs.countQuery(db, query) < 1;
			} catch (Exception ee) {
				ee.printStackTrace();
			}

			if (pageUnderConstruction) {
				buildUnderConstructionPage();
			} else {
				out.println("<HTML>\n");
				out.println("<HEAD>\n");
				out.println("<TITLE>Print Large Labels</TITLE>\n");
				out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
				out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
				out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
				out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
				out.println("<SCRIPT SRC=\"/clientscripts/largelabel.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
				out.println("</HEAD>\n");
				out.println("<BODY>\n");

				out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
				out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
				out.println("</DIV>\n");
				out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");
				out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>Print Large Labels</B>\n"));
				out.println("<FORM method=\"POST\" NAME=\"picking\" onSubmit=\"return SubmitOnlyOnce()\">\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"generatelabels\">\n");
				out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"300\" CLASS=\"moveup\">\n");
				out.println("<TR VALIGN=\"MIDDLE\">\n");
				//Generate Large Labels Button
				out.println("<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
				out.println("<INPUT TYPE=\"submit\" VALUE=\"Generate Labels\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
				out.println("</TD>\n");
				out.println("<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
				out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\">\n");
				out.println("</TD></TR></TABLE>\n");

				out.println(buildDetails(receiptData, User_Action, session));
			}
		}
		out.close();
	} //end of method

	void buildUnderConstructionPage() {
		out.println("<HTML>\n");
		out.println("<HEAD>\n");
		out.println("<TITLE>Under Construction</TITLE>\n");
		out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
		out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
		out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
		out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
		out.println("</HEAD>\n");
		out.println("<BODY>\n");
		out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");
		out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>This page is currently Under Construction</B>\n"));
		out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"1\" WIDTH=\"300\" CLASS=\"moveup\">\n");
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD>&nbsp;</TD>");
		out.println("</TR>");
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD>&nbsp;</TD>");
		out.println("</TR>");
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD>&nbsp;</TD>");
		out.println("</TR>");
		out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println("<TD>&nbsp;</TD>");
		out.println("</TR>");
		out.println("</TABLE>");
		out.println("<CENTER><IMG SRC=\"/images/ucbang.gif\" ></CENTER>");
		out.println("</BODY>\n");
		out.println("</HTML>\n");
	} //end of method

	public String generateContainerLabel(Vector receiptData, String personnelId, String printerPath, String printerRes) throws Exception {
		String Query_Statement = "";
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Hashtable data = new Hashtable();
		Vector dataV1 = new Vector();
		Vector invengrpvec = new Vector();
		int numberRec = 0;
		String updateprintdate = "";
		StringBuffer MsgSB = new StringBuffer();

		Random rand = new Random();
		int tmpReq = rand.nextInt();
		Integer tmpReqNum = new Integer(tmpReq);

		String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
		String url = wwwHome + "labels/Barcode" + tmpReqNum.toString() + ".pdf";
		String jnlpurl = wwwHome + "labels/Barcode" + tmpReqNum.toString() + ".jnlp";

		if (receiptData.size() == 0) {
			return "";
		}

		try {
			for (int i = 0; i < receiptData.size(); i++) {
				String Hub_Value = "";
				String Item_Id = "";
				String Quantity = "";
				String BarcodeRId = "";
				String inventorygrp = "";
				String certnuminvngrp = "";
				String qualitycntitem = "";
				String singleitemkit = "";
				String certified= "";

				Hashtable hDdata = new Hashtable();
				hDdata = (Hashtable) (receiptData.elementAt(i));
				String receiptidt = (hDdata.get("RECEIPT_ID") == null ? "" : hDdata.get("RECEIPT_ID").toString());
				String qtyonlabel = (hDdata.get("QUANTITY_ON_LABELS") == null ? "1" : hDdata.get("QUANTITY_ON_LABELS").toString());
				String nooflabels = (hDdata.get("NO_OF_LARGE_LABELS") == null ? "1" : hDdata.get("NO_OF_LARGE_LABELS").toString());

				Query_Statement = "Select COMPONENT_MSDS_LIST,RECERT_NUMBER,TOTAL_RECERTS_ALLOWED, LAST_ID_PRINTED, SPEC_LIST, COMPANY_MSDS_LIST,CATALOG_ITEM_DESCRIPTION,CUSTOMER_RECEIPT_ID,RECEIVER_DATE_OF_RECEIPT,SHELF_LIFE_STORAGE_TEMP,CERTIFIED,PICKABLE,DATE_OF_MANUFACTURE, SUPPLIER, SUPPLIER_NAME,TOTAL_COMPONENTS,INSEPARABLE_KIT,NUMBER_OF_KITS,MANAGE_KITS_AS_SINGLE_UNIT,QC_DOC,CERTIFICATION_NUMBER,QUALITY_CONTROL_ITEM,INVENTORY_GROUP,HUB,QUANTITY_RECEIVED,lpad(RECEIPT_ID,8,'0') RECEIPT_ID1,RECEIPT_ID,ITEM_ID,RADIAN_PO,MFG_LOT,to_char(DATE_OF_RECEIPT,'mm/dd/yyyy') DOR,";
				Query_Statement += "RECERTS,BIN,EXPIRE_DATE,LABEL_STORAGE_TEMP,mfg_desc,mfg_id,part_hazard_image,LOT_STATUS,OWNER_COMPANY_ID,PO_NUMBER,QUALITY_TRACKING_NUMBER, RECERTS_TO_RECERTS_ALLOWED,OWNER_SEGMENT_ID,ACCOUNT_NUMBER,ACCOUNT_NUMBER2,ACCOUNT_NUMBER3,ACCOUNT_NUMBER4,TRACE_ID,MATERIAL_ID,SUPPLIER_CAGE_CODE,EXPIRE_DATE_LOCALE, DATE_OF_REPACKAGING  from container_label_master_view where RECEIPT_ID = '" + receiptidt + "'";
				updateprintdate += receiptidt + ",";

				dbrs = db.doQuery(Query_Statement);
				rs = dbrs.getResultSet();

				data = new Hashtable();
				numberRec = 0;

				try {
					while (rs.next()) {
						BarcodeRId = com.tcmis.common.util.BarCodeHandler.Code128c(rs.getString("RECEIPT_ID1"));
						data.put("DETAIL_0", BarcodeRId);
						Item_Id = rs.getString("ITEM_ID") == null ? " " : rs.getString("ITEM_ID");
						inventorygrp = rs.getString("INVENTORY_GROUP") == null ? " " : rs.getString("INVENTORY_GROUP");
						certnuminvngrp = rs.getString("CERTIFICATION_NUMBER") == null ? " " : rs.getString("CERTIFICATION_NUMBER");
						qualitycntitem = rs.getString("QUALITY_CONTROL_ITEM") == null ? " " : rs.getString("QUALITY_CONTROL_ITEM");
						singleitemkit = rs.getString("MANAGE_KITS_AS_SINGLE_UNIT") == null ? " " : rs.getString("MANAGE_KITS_AS_SINGLE_UNIT");
						certified=rs.getString( "CERTIFIED" ) == null ? " " : rs.getString( "CERTIFIED" );

						data.put("OWNER_COMPANY_ID",rs.getString( "OWNER_COMPANY_ID") == null ? "" : rs.getString( "OWNER_COMPANY_ID"));
						data.put("PO_NUMBER",rs.getString( "PO_NUMBER") == null ? "" : rs.getString( "PO_NUMBER"));
						data.put("LOT_STATUS",rs.getString( "LOT_STATUS") == null ? "" : rs.getString( "LOT_STATUS"));

						data.put("ITEM_ID", Item_Id);
						data.put("RECEIPT_ID", rs.getString("RECEIPT_ID1") == null ? " " : rs.getString("RECEIPT_ID1"));
						data.put("MFG_LOT", rs.getString("MFG_LOT") == null ? " " : rs.getString("MFG_LOT"));
						data.put("DOR", rs.getString("DOR") == null ? " " : rs.getString("DOR"));
						data.put("RECERTS", rs.getString("RECERTS") == null ? " " : rs.getString("RECERTS"));
						data.put("BIN", rs.getString("BIN") == null ? " " : rs.getString("BIN"));
						data.put("EXPIRE_DATE", rs.getString("EXPIRE_DATE") == null ? " " : rs.getString("EXPIRE_DATE"));
						data.put("STORAGE_TEMP", rs.getString("LABEL_STORAGE_TEMP") == null ? " " : rs.getString("LABEL_STORAGE_TEMP"));
						data.put("INVENTORY_GROUP", inventorygrp);
						data.put("NUMBER_OF_KITS", rs.getString("NUMBER_OF_KITS") == null ? " " : rs.getString("NUMBER_OF_KITS"));
						data.put("INSEPARABLE_KIT", rs.getString("INSEPARABLE_KIT") == null ? " " : rs.getString("INSEPARABLE_KIT"));
						data.put("TOTAL_COMPONENTS", rs.getString("TOTAL_COMPONENTS") == null ? "" : rs.getString("TOTAL_COMPONENTS"));
						data.put("DATE_OF_MANUFACTURE", rs.getString("DATE_OF_MANUFACTURE") == null ? "" : rs.getString("DATE_OF_MANUFACTURE"));
						data.put("SUPPLIER", rs.getString("SUPPLIER") == null ? "" : rs.getString("SUPPLIER"));
						data.put("SUPPLIER_NAME", rs.getString("SUPPLIER_NAME") == null ? "" : rs.getString("SUPPLIER_NAME"));
						data.put("MFG_DESC", rs.getString("MFG_DESC") == null ? "" : rs.getString("MFG_DESC"));
						data.put("MFG_ID", rs.getString("MFG_ID") == null ? "" : rs.getString("MFG_ID"));
						data.put("QUANTITY_ON_LABELS", qtyonlabel);
						data.put("NO_OF_LARGE_LABELS", nooflabels);
						data.put("PART_HAZARD_IMAGE", rs.getString("PART_HAZARD_IMAGE") == null ? "" : rs.getString("PART_HAZARD_IMAGE"));
						data.put( "RECEIVER_DATE_OF_RECEIPT",rs.getString( "RECEIVER_DATE_OF_RECEIPT" ) == null ? "" : rs.getString( "RECEIVER_DATE_OF_RECEIPT" ) );
						data.put( "SHELF_LIFE_STORAGE_TEMP",rs.getString( "SHELF_LIFE_STORAGE_TEMP" ) == null ? "" : rs.getString( "SHELF_LIFE_STORAGE_TEMP" ) );
						data.put( "CATALOG_ITEM_DESCRIPTION",rs.getString( "CATALOG_ITEM_DESCRIPTION" ) == null ? "" : rs.getString( "CATALOG_ITEM_DESCRIPTION" ) );
						data.put( "CUSTOMER_RECEIPT_ID",rs.getString( "CUSTOMER_RECEIPT_ID" ) == null ? "" : rs.getString( "CUSTOMER_RECEIPT_ID" ) );
						data.put( "TOTAL_RECERTS_ALLOWED",rs.getString( "TOTAL_RECERTS_ALLOWED" ) == null ? "" : rs.getString( "TOTAL_RECERTS_ALLOWED" ) );
						data.put( "LAST_ID_PRINTED",rs.getString( "LAST_ID_PRINTED" ) == null ? "" : rs.getString( "LAST_ID_PRINTED" ) );
						data.put( "SPEC_LIST",rs.getString( "SPEC_LIST" ) == null ? "" : rs.getString( "SPEC_LIST" ) );
						data.put( "COMPANY_MSDS_LIST",rs.getString( "COMPANY_MSDS_LIST" ) == null ? "" : rs.getString( "COMPANY_MSDS_LIST" ) );
						data.put( "RECERT_NUMBER",rs.getString( "RECERT_NUMBER" ) == null ? "" : rs.getString( "RECERT_NUMBER" ) );
						data.put( "DIST_CUSTOMER_PART_LIST",rs.getString( "DIST_CUSTOMER_PART_LIST" ) == null ? "" : rs.getString( "DIST_CUSTOMER_PART_LIST" ) );
						data.put( "QUALITY_TRACKING_NUMBER",rs.getString( "QUALITY_TRACKING_NUMBER" ) == null ? "" : rs.getString( "QUALITY_TRACKING_NUMBER" ) );
						data.put( "RECERTS_TO_RECERTS_ALLOWED",rs.getString( "RECERTS_TO_RECERTS_ALLOWED" ) == null ? "" : rs.getString( "RECERTS_TO_RECERTS_ALLOWED" ) );
						data.put( "OWNER_SEGMENT_ID",rs.getString( "OWNER_SEGMENT_ID" ) == null ? "" : rs.getString( "OWNER_SEGMENT_ID" ) );
						data.put( "ACCOUNT_NUMBER",rs.getString( "ACCOUNT_NUMBER" ) == null ? "" : rs.getString( "ACCOUNT_NUMBER" ) );
						data.put( "ACCOUNT_NUMBER2",rs.getString( "ACCOUNT_NUMBER2" ) == null ? "" : rs.getString( "ACCOUNT_NUMBER2" ) );
						data.put( "ACCOUNT_NUMBER3",rs.getString( "ACCOUNT_NUMBER3" ) == null ? "" : rs.getString( "ACCOUNT_NUMBER3" ) );
						data.put( "ACCOUNT_NUMBER4",rs.getString( "ACCOUNT_NUMBER4" ) == null ? "" : rs.getString( "ACCOUNT_NUMBER4" ) );
						data.put( "TRACE_ID",rs.getString( "TRACE_ID" ) == null ? "" : rs.getString( "TRACE_ID" ));
						data.put( "MATERIAL_ID",rs.getString( "MATERIAL_ID" ) == null ? "" : rs.getString( "MATERIAL_ID" ));
						data.put( "COMPONENT_MSDS_LIST",rs.getString( "COMPONENT_MSDS_LIST" ) == null ? "" : rs.getString( "COMPONENT_MSDS_LIST" ));
				        data.put( "SUPPLIER_CAGE_CODE",rs.getString( "SUPPLIER_CAGE_CODE" ) == null ? "" : rs.getString( "SUPPLIER_CAGE_CODE" ));
				        data.put( "DATE_OF_REPACKAGING",rs.getString( "DATE_OF_REPACKAGING" ) == null ? "" : rs.getString( "DATE_OF_REPACKAGING" ));
				        data.put( "EXPIRE_DATE_LOCALE",rs.getString( "EXPIRE_DATE_LOCALE" ) == null ? "" : rs.getString( "EXPIRE_DATE_LOCALE" ));
						  
						String dpmnumber = rs.getString("QC_DOC") == null ? " " : rs.getString("QC_DOC");
						if (dpmnumber.trim().length() == 0) {
							data.put("DETAIL_14", "Not For Production");
						} else {
							data.put("DETAIL_14", "" + dpmnumber + "");
						}
						data.put("DETAIL_15", rs.getString("LABEL_STORAGE_TEMP") == null ? " " : rs.getString("LABEL_STORAGE_TEMP"));

						Hub_Value = rs.getString("HUB") == null ? "" : rs.getString("HUB");
						Quantity = rs.getString("QUANTITY_RECEIVED") == null ? "" : rs.getString("QUANTITY_RECEIVED"); ;
						if (!invengrpvec.contains(inventorygrp)) {
							invengrpvec.add(inventorygrp);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "";
				} finally {
					if (dbrs != null) {
						dbrs.close();
					}
				}

				Query_Statement = "select * from (Select distinct CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME," +
				"max(OPEN_MR_QTY) OPEN_MR_QTY,max(STOCKING_LEVEL) STOCKING_LEVEL,spec_id,SPEC_FIELDNAME," +
				"UNIT_OF_SALE,substr(PACKAGING,1,200) PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY from container_label_detail_view where ITEM_ID = " + Item_Id +
				" and HUB = " + Hub_Value + " and INVENTORY_GROUP = '" + inventorygrp + "' group by CUSTOMER_PART_REVISION,LABEL_SPEC," +
				"CAT_PART_NO,PART_SHORT_NAME,spec_id,SPEC_FIELDNAME,UNIT_OF_SALE,PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY " +
				"order by 4 desc,5 desc, 12 desc) a where rownum < 8";

				dbrs = db.doQuery(Query_Statement);
				rs = dbrs.getResultSet();

				data.put("PART_NO_1", "");
				data.put("PART_NO_2", "");
				data.put("PART_NO_3", "");
				data.put( "UNIT_OF_SALE","" );
				data.put( "PACKAGING","" );
				data.put( "KIT_MSDS_NUMBER","" );
				data.put( "MIN_RT_OUT_TIME","" );
				data.put( "MIN_STORAGE_TEMP_DISPLAY","" );
				data.put( "TIME_TEMP_SENSITIVE","" );
				data.put( "PART_DESCRIPTION","" );
				data.put( "SPEC_DISPLAY","" );
                data.put( "CUSTOMER_PART_REVISION","" );
                
                try {
					while (rs.next()) {
						if (numberRec == 0) {
							data.remove("PART_NO_1");
							data.put("PART_NO_1", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
							data.put("PART_SHORT_NAME", rs.getString("PART_SHORT_NAME") == null ? "" : rs.getString("PART_SHORT_NAME"));
							data.put("SPEC_ID", rs.getString("SPEC_ID") == null ? "" : rs.getString("SPEC_ID"));
							data.put("SPEC_FIELDNAME", rs.getString("SPEC_FIELDNAME") == null ? "" : rs.getString("SPEC_FIELDNAME"));
							data.put( "LABEL_SPEC",rs.getString("LABEL_SPEC") == null ? "" : rs.getString("LABEL_SPEC"));
							data.remove( "UNIT_OF_SALE" );
							data.put( "UNIT_OF_SALE",rs.getString( "UNIT_OF_SALE" ) == null ? " " : rs.getString( "UNIT_OF_SALE" ) );
							data.remove( "PACKAGING" );
							data.put( "PACKAGING",rs.getString( "PACKAGING" ) == null ? " " : rs.getString( "PACKAGING" ) );
							data.remove( "KIT_MSDS_NUMBER" );
							data.put( "KIT_MSDS_NUMBER",rs.getString( "KIT_MSDS_NUMBER" ) == null ? " " : rs.getString( "KIT_MSDS_NUMBER" ) );
							data.remove( "MIN_RT_OUT_TIME" );
							data.put( "MIN_RT_OUT_TIME",rs.getString( "MIN_RT_OUT_TIME" ) == null ? " " : rs.getString( "MIN_RT_OUT_TIME" ) );
							data.remove( "MIN_STORAGE_TEMP_DISPLAY" );
							data.put( "MIN_STORAGE_TEMP_DISPLAY",rs.getString( "MIN_STORAGE_TEMP_DISPLAY" ) == null ? " " : rs.getString( "MIN_STORAGE_TEMP_DISPLAY" ) );
							data.remove( "TIME_TEMP_SENSITIVE" );
							data.put( "TIME_TEMP_SENSITIVE",rs.getString( "TIME_TEMP_SENSITIVE" ) == null ? " " : rs.getString( "TIME_TEMP_SENSITIVE" ) );
							data.remove( "PART_DESCRIPTION" );
							String partDescription = rs.getString( "PART_DESCRIPTION" ) == null ? " " : rs.getString( "PART_DESCRIPTION" );
							if (partDescription.length() > 30)
							{
								partDescription = partDescription.substring(0,30);
							}
							data.put( "PART_DESCRIPTION",partDescription);
							data.remove( "SPEC_DISPLAY" );
							String specDisplay = rs.getString( "SPEC_DISPLAY" ) == null ? " " : rs.getString( "SPEC_DISPLAY" );
							if (specDisplay.length() > 76)
								specDisplay = specDisplay.substring(0,75);
							data.put( "SPEC_DISPLAY",specDisplay);
                            data.remove( "CUSTOMER_PART_REVISION" );
                            data.put( "CUSTOMER_PART_REVISION",rs.getString( "CUSTOMER_PART_REVISION" ) == null ? " " : rs.getString( "CUSTOMER_PART_REVISION" ) );
						} else if (numberRec == 1) {
							data.remove("PART_NO_2");
							data.put("PART_NO_2", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
						} else if (numberRec == 2) {
							data.remove("PART_NO_3");
							data.put("PART_NO_3", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
						}

						numberRec += 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "";
				} finally {
					if (dbrs != null) {
						dbrs.close();
					}
				}

				if ("Y".equalsIgnoreCase(qualitycntitem) && certnuminvngrp.trim().length() > 0) {
					data.remove("PART_NO_3");
					data.put("PART_NO_3", "Accepted By: " + certnuminvngrp + "");
				}

				if ( "Y".equalsIgnoreCase( qualitycntitem ) && "Y".equalsIgnoreCase( certified ) && certnuminvngrp.trim().length() == 0 )
				{
					barclog.info("No Certification Number for Receipt ID "+receiptidt+"");
					return "";
				}

				dataV1.addElement(data);

			} //End of For
			updateprintdate = updateprintdate.substring(0, (updateprintdate.length() - 1));
			String printupquery = "update receipt set PICKABLE='Y',LAST_LABEL_PRINT_DATE = SYSDATE where receipt_id in (" + updateprintdate + ") ";
			//db.doUpdate( printupquery );

		} catch (Exception e) { //End Try
			e.printStackTrace();
			return "";
		}

		try {
			ZPLBarCodeGenerator zplobj = new ZPLBarCodeGenerator(db);
			zplobj.buildLargeLabelZpl(dataV1, tmpReqNum.toString(), "64", personnelId, printerPath, printerRes, invengrpvec);
			url = jnlpurl;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		data = null;
		dataV1 = null;
		invengrpvec = null;

		return url;
	}

	private void printPrinterChoice(PrintWriter out, String printerPath, String printerRes, String paperSize, String personnelId) {
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
		//out.println("<INPUT TYPE=\"hidden\" NAME=\"printKitLabels\" VALUE=\""+printKitLabels+"\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"paperSize\" VALUE=\"" + paperSize + "\">\n");
		//out.println("<INPUT TYPE=\"hidden\" NAME=\"HubNoforlabel\" VALUE=\""+HubNoforlabel+"\">\n");

		out.println("<BR><B>Please Pick a Printer:</B>");
		out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"500\" CLASS=\"moveup\">\n");
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String tmpPrinterPath = "";
		String tmpPrinterRes = "";

		try {
			String iszplprinter = "select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " +
			personnelId + " and x.LABEL_STOCK = '" + paperSize + "' ORDER BY x.PRINTER_PATH";
			dbrs = db.doQuery(iszplprinter);
			rs = dbrs.getResultSet();

			while (rs.next()) {
				tmpPrinterPath = rs.getString("PRINTER_PATH") == null ? " " : rs.getString("PRINTER_PATH");
				tmpPrinterRes = rs.getString("PRINTER_RESOLUTION_DPI") == null ? " " : rs.getString("PRINTER_RESOLUTION_DPI");

				out.println("<TR VALIGN=\"MIDDLE\">\n");
				out.println("<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
				out.println("<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"printerPath\" onChange=\"setprinterRes('" + tmpPrinterRes + "')\" value=\"" + tmpPrinterPath + "\" " + (tmpPrinterPath.equalsIgnoreCase(printerPath) ? "checked" : "") + ">\n");
				out.println("</TD>\n<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" ID=\"option3\">" + tmpPrinterPath + "&nbsp;</TD>\n");
				/*out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
             out.println("<INPUT ID=\"option2\" TYPE=\"radio\" NAME=\"printerRes\" value=\""+printerRes+"\">\n");
             out.println("</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option4\">"+printerRes+"</TD>\n\n");*/
				out.println("</TR>\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbrs != null) {
				dbrs.close();
			}
		}

		out.println("<TR VALIGN=\"MIDDLE\">\n");
		//Generate Labels Button
		out.println("<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"submit\" VALUE=\"Generate Labels\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
		out.println("</TD>\n");

		out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\">\n");
		out.println("</TD></TR>\n");

		/*out.println("<TR VALIGN=\"MIDDLE\">\n");
       out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
       out.println( "<INPUT type=\"checkbox\" name=\"printKitLabels\" value=\"Yes\" " + ( printKitLabels.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
       out.println( "</TD>\n" );
       out.println( "<TD WIDTH=\"5%\" COLSPAN=\"7\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
       out.println( "&nbsp;Skip Kit/Case Qty Labels\n" );
       out.println( "</TD>\n" );*/
		out.println("</TABLE>\n");
	}

	private Vector SynchprintData(HttpServletRequest request, Vector in_data) {
		Vector new_data = new Vector();
		Hashtable sum = (Hashtable) (in_data.elementAt(0));
		int count = in_data.size() - 1;
		//new_data.addElement( sum );

		try {
			for (int i = 1; i < count + 1; i++) {
				Hashtable hDdata = new Hashtable();
				hDdata = (Hashtable) (in_data.elementAt(i));
				String Line_Check = (hDdata.get("USERCHK") == null ? "" : hDdata.get("USERCHK").toString());

				String qtyonlabel = "";
				qtyonlabel = BothHelpObjs.makeBlankFromNull(request.getParameter("qtyonlabel" + i));
				qtyonlabel = qtyonlabel.trim();

				String nooflabels = "";
				nooflabels = BothHelpObjs.makeBlankFromNull(request.getParameter("nooflabels" + i));
				nooflabels = nooflabels.trim();

				if (nooflabels.length() > 0 && "Yes".equalsIgnoreCase(Line_Check)) {
					int testnum = 0;
					try {
						testnum = Integer.parseInt(nooflabels);
					} catch (NumberFormatException ex) {
						testnum = 0;
					}

					if (testnum > 0) {
						hDdata.put("QUANTITY_ON_LABELS", qtyonlabel);
						hDdata.put("NO_OF_LARGE_LABELS", nooflabels);
					}
					new_data.addElement(hDdata);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("Print Large Labels",intcmIsApplication));
		}

		return new_data;
	}

	private String buildDetails(Vector receiptData, String SubuserAction, HttpSession sessiondetails) {
		StringBuffer Msg = new StringBuffer();
		DBResultSet dbrs = null;
		ResultSet rs = null;

		try {
			Hashtable summary = new Hashtable();
			summary = (Hashtable) receiptData.elementAt(0);
			int total = receiptData.size() - 1;
			//start table #3
			out.println("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveupmore\">\n");
			String checkednon = "";
			int i = 0; //used for detail lines
			int lineadded = 0;
			String Color = "CLASS=\"white";

			for (int loop = 0; loop < total; loop++) {
				i++;
				Hashtable hD = new Hashtable();
				hD = (Hashtable) receiptData.elementAt(i);
				boolean createHdr = false;

				if (lineadded % 10 == 0) {
					createHdr = true;
				}

				if (createHdr) {
					Msg.append("<tr align=\"center\">\n");
					Msg.append("<TH width=\"5%\"  height=\"38\">Receipt ID</TH>\n");
					Msg.append("<TH width=\"5%\"  height=\"38\">Item Id</TH>\n");
					Msg.append("<TH width=\"20%\"  height=\"38\">Item Desc</TH>\n");
					Msg.append("<TH width=\"5%\"  height=\"38\">Expire Date</TH>\n");
					Msg.append("<TH width=\"5%\"  height=\"38\">Qty on Label</TH>\n");
					Msg.append("<TH width=\"5%\"  height=\"38\">No of Labels</TH>\n");
					Msg.append("</tr>\n");
					createHdr = false;
				}

				String Item_id = (hD.get("ITEM_ID") == null ? "" : hD.get("ITEM_ID").toString());
				String line_desc = (hD.get("LINE_DESC") == null ? "" : hD.get("LINE_DESC").toString());
				String Item_desc = (hD.get("ITEM_DESC") == null ? "" : hD.get("ITEM_DESC").toString());
				String part_desc = (hD.get("PART_DESCRIPTION") == null ? "" : hD.get("PART_DESCRIPTION").toString());

				//String Mfg_lot= ( hD.get( "MFG_LOT" ) == null ? "" : hD.get( "MFG_LOT" ).toString() );
				//String Date_recieved= ( hD.get( "DATE_RECIEVED" ) == null ? "&nbsp;" : hD.get( "DATE_RECIEVED" ).toString() );
				//String bin= ( hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString() );
				String receipt_id = (hD.get("RECEIPT_ID") == null ? "&nbsp;" : hD.get("RECEIPT_ID").toString());
				//String quantity= ( hD.get( "QUANTITY_RECEIVED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_RECEIVED" ).toString() );
				//String lotstatus= ( hD.get( "LOT_STATUS" ) == null ? "&nbsp;" : hD.get( "LOT_STATUS" ).toString() );
				//String dom= ( hD.get( "DOM" ) == null ? "" : hD.get( "DOM" ).toString() );
				//String dos= ( hD.get( "DOS" ) == null ? "" : hD.get( "DOS" ).toString() );
				String expdate = (hD.get("EXPIRE_DATE") == null ? "" : hD.get("EXPIRE_DATE").toString());
				//String indefshelflie= ( hD.get( "INDEFINITE_SHELF_LIFE" ) == null ? " " : hD.get( "INDEFINITE_SHELF_LIFE" ).toString() );
				//String actshipDate= ( hD.get( "ACTUAL_SHIP_DATE" ) == null ? " " : hD.get( "ACTUAL_SHIP_DATE" ).toString() );
				String Line_Check = (hD.get("USERCHK") == null ? "" : hD.get("USERCHK").toString());

				if (line_desc.length() == 0) {
					line_desc = Item_desc;
				}
				if (part_desc.length() > 0) {
					line_desc = part_desc;
				}

				if (lineadded % 2 == 0) {
					Color = "CLASS=\"white";
				} else {
					Color = "CLASS=\"blue";
				}

				if (Line_Check.equalsIgnoreCase("yes")) {
					lineadded++;
					Msg.append("<TR>\n");
					Msg.append("<input type=\"hidden\" name=\"receiptid" + i + "\" value=\"" + receipt_id + "\">\n");
					Msg.append("<td " + Color + "\" width=\"5%\" height=\"38\">" + receipt_id + "</td>\n");
					Msg.append("<td " + Color + "\" width=\"5%\" height=\"38\">" + Item_id + "</td>\n");
					Msg.append("<td " + Color + "\" width=\"20%\" height=\"38\">" + line_desc + "</td>\n");
					Msg.append("<td " + Color + "\" width=\"5%\" height=\"38\">" + expdate + "</td>\n");
					Msg.append("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"qtyonlabel" + i + "\"  value=\"\" maxlength=\"5\" size=\"3\"></td>\n");
					Msg.append("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"nooflabels" + i + "\"  value=\"\" maxlength=\"5\" size=\"3\"></td>\n");
					Msg.append("</TR>\n");
				}
			}

			Msg.append("</TABLE>\n");
			Msg.append("</form>\n");
			Msg.append("</body></html>\n");
		} catch (Exception e) {
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("Print Large Labels",intcmIsApplication));
		}

		return Msg.toString();
	}
}
