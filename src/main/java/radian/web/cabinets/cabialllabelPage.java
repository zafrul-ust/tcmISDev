package radian.web.cabinets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.web.BarCodeHelpObj;
import radian.web.barcode.ZPLBarCodeGenerator;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 06-30-04 Changing Printer Path to be based on personnel ID
 * 07-30-04 Changing the templates to be based on inventory group instead of hub
 * 09-13-04 Printing Main Receipt Label and the kit labels consecutively
 */

public class cabialllabelPage {
	TcmISDBModel db = null;
	private String paperSize = "";
	private Vector invengrpvec = new Vector();
	private static org.apache.log4j.Logger barclog = org.apache.log4j.Logger.getLogger(cabinetlabelstart.class);
	private static Pattern barCode2dReplace = Pattern.compile("\\||,",Pattern.MULTILINE);

	public cabialllabelPage(TcmISDBModel d) {
		db = d;
	}

	public cabialllabelPage() {

	}

	public void setpaperSize(String id) {
		this.paperSize = id;
	}

	private String getpaperSize() throws Exception {
		return this.paperSize;
	}

	public String generateLabels(Vector cabidnums, Vector cab8numbs, String gencase, String HubNoforlabel, String personnelID, String printerPath, String printerRes) throws ServletException, IOException {
		//Vector cabdata = new Vector();
		//barclog.info("Here in cabialllabelPage starting to get data");
		Hashtable bindata = new Hashtable();
		Hashtable receiptdata = new Hashtable();
		StringBuffer MsgSB = new StringBuffer();

		Random rand = new Random();
		int tmpReq = rand.nextInt();
		Integer tmpReqNum = new Integer(tmpReq);

		String updateprintdate = "";
		String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
		String url = wwwHome + "labels/alllablforcabs" + tmpReqNum.toString() + ".pdf";
		String jnlpurl = wwwHome + "labels/alllablforcabs" + tmpReqNum.toString() + ".jnlp";

		if (cabidnums.size() == 0) {
			return "";
		}

		String allowedcabs = "";
		try {
			for (int jk = 0; jk < cabidnums.size(); jk++) {
				String cabsstmp = (String) (cabidnums.elementAt(jk));
				allowedcabs += "" + cabsstmp + ",";
			}
			allowedcabs = allowedcabs.substring(0, allowedcabs.length() - 1);

			bindata = getbindata(allowedcabs);
			//cabdata = getcabinetdata(allowedcabs);
			//receiptdata = getreceiptdata(allowedcabs);
		} //End Trygetreceiptdata
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		//cabdata.trimToSize();
		//bindata.trimToSize();

		try {
			buildzplurl(bindata, "alllablforcabs" + tmpReqNum.toString() + "", HubNoforlabel, personnelID, printerPath, printerRes);
			url = jnlpurl;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("*** Error in calling barcode generator***");
			return "";
		}
		finally {
		}

		String retunrstring = MsgSB.toString();
		return url;
	}

	private Hashtable getbindata(String allowedcabs) {
		Vector dataV = new Vector();
		DBResultSet dbrs = null;
		ResultSet searchRs = null;
		Hashtable data = new Hashtable();
		Hashtable finaldata = new Hashtable();

		String query = "";
		query = "select distinct MATERIAL_ID,LOCATION_DETAIL,INVENTORY_GROUP,KANBAN_REORDER_QUANTITY,REORDER_POINT,STOCKING_LEVEL,QC_DOC,CABINET_NAME,PACKAGING, MATERIAL_ID_STRING, MFG_DESC,lpad(BIN_ID,8,'0') BIN_ID1,BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,ITEM_ID,BIN_NAME,NUM_CAT_PARTS,CAT_PART_NO,PART_DESCRIPTION,PART_SHORT_NAME from Cabinet_bin_label_view where CABINET_ID in ("
			+ allowedcabs + ") order by CABINET_ID";

		try {
			dbrs = db.doQuery(query);
			searchRs = dbrs.getResultSet();
			data = new Hashtable();
			String binBarcodeRId = "";
			String cabBarcodeRId = "";

			while (searchRs.next()) {
				String tmpcabid = searchRs.getString("CABINET_ID1") == null ? "" : searchRs.getString("CABINET_ID1");
				String tmpbinId = searchRs.getString("BIN_ID1") == null ? "" : searchRs.getString("BIN_ID1");
				int tmpnoofprts = searchRs.getInt("NUM_CAT_PARTS");
				String tmppartId = searchRs.getString("CAT_PART_NO") == null ? "" : searchRs.getString("CAT_PART_NO");
				String tmppartdesc = searchRs.getString("PART_DESCRIPTION") == null ? "" : searchRs.getString("PART_DESCRIPTION");
				String tmpitemid = searchRs.getString("ITEM_ID") == null ? "" : searchRs.getString("ITEM_ID");
				String mfgname = searchRs.getString("MFG_DESC") == null ? "" : searchRs.getString("MFG_DESC");
				String msdsnum = searchRs.getString("MATERIAL_ID_STRING") == null ? "" : searchRs.getString("MATERIAL_ID_STRING");
				String packging = searchRs.getString("PACKAGING") == null ? "" : searchRs.getString("PACKAGING");
				String partShortName = searchRs.getString("PART_SHORT_NAME") == null ? "" : searchRs.getString("PART_SHORT_NAME");

				data = new Hashtable();
				binBarcodeRId = com.tcmis.common.util.BarCodeHandler.Code128b(tmpcabid + "-" + tmpbinId);
				//System.out.println("binBarcodeRId   "+tmpcabid + "-" + tmpbinId+"  Converts to  "+binBarcodeRId);
				data.put("DETAIL_0", binBarcodeRId);
				data.put("DETAIL_1", tmpcabid + "-" + tmpbinId);
				data.put("ITEM_ID", tmpitemid);
				data.put("CAT_PART_NO", tmppartId);
				data.put("DETAIL_2", searchRs.getString("BIN_NAME") == null ? "" : searchRs.getString("BIN_NAME"));
				if (tmpnoofprts > 1) {
					data.put("DETAIL_3", "Multiple / Multiple");
					data.put("DETAIL_4", "DESC: Multiple");
				}
				else {
					data.put("DETAIL_3", tmpitemid + " / " + tmppartId);
					data.put("DETAIL_4", "DESC: " + tmppartdesc + "");
				}

				data.put("DETAIL_5", searchRs.getString("CABINET_ID") == null ? "" : searchRs.getString("CABINET_ID"));
				data.put("DETAIL_6", searchRs.getString("BIN_ID") == null ? "" : searchRs.getString("BIN_ID"));
				data.put("DETAIL_7", mfgname);
				//data.put( "DETAIL_8","MSDS: "+msdsnum+"    PKG: "+packging+"" );
				data.put("DETAIL_8", "" + msdsnum + "");
				data.put("DETAIL_9", "" + packging + "");
				data.put("DETAIL_10", searchRs.getString("QC_DOC") == null ? "" : searchRs.getString("QC_DOC"));
				data.put("DETAIL_11", searchRs.getString("CABINET_NAME") == null ? "" : searchRs.getString("CABINET_NAME"));
				data.put("DETAIL_12", ("CAB" + tmpcabid));
				data.put("DETAIL_13", searchRs.getString("REORDER_POINT") == null ? "" : searchRs.getString("REORDER_POINT"));
				data.put("DETAIL_14", searchRs.getString("STOCKING_LEVEL") == null ? "" : searchRs.getString("STOCKING_LEVEL"));
				data.put( "KANBAN_REORDER_QUANTITY",searchRs.getString( "KANBAN_REORDER_QUANTITY" ) == null ? "" : searchRs.getString( "KANBAN_REORDER_QUANTITY" ) );
				String inventorygrp = searchRs.getString("INVENTORY_GROUP") == null ? " " : searchRs.getString("INVENTORY_GROUP");
				if (!invengrpvec.contains(inventorygrp)) {
					invengrpvec.add(inventorygrp);
				}
				data.put("INVENTORY_GROUP", inventorygrp);
				data.put("PART_SHORT_NAME", partShortName);
                data.put("LOCATION_DETAIL", searchRs.getString("LOCATION_DETAIL") == null ? "" : searchRs.getString("LOCATION_DETAIL"));
                data.put("MATERIAL_ID", searchRs.getString("MATERIAL_ID") == null ? "" : searchRs.getString("MATERIAL_ID"));
                dataV.addElement(data);
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

		dataV.trimToSize();
		Hashtable receiptdata = new Hashtable();
		Hashtable finalreceiptdata = new Hashtable();
		try {
			for (int j = 0; j < dataV.size(); j++) {
				Hashtable bindata = (Hashtable) dataV.elementAt(j);
				String binid = bindata.get("DETAIL_6").toString();

				receiptdata = getreceiptdata(binid);
				finalreceiptdata.put(binid, receiptdata);
			}
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}

		if (dataV.size() == 0) {
			data = new Hashtable();
			data.put("DETAIL_0", "");
			data.put("DETAIL_1", "");
			data.put("DETAIL_2", "");
			data.put("DETAIL_3", "");
			data.put("DETAIL_4", "");
			data.put("DETAIL_5", "");
			data.put("DETAIL_6", "");
			data.put("DETAIL_7", "");
			data.put("DETAIL_8", "");
			dataV.addElement(data);
		}

		finaldata.put("BIN_DATA", dataV);
		finaldata.put("BIN_RECEIPT_DATA", finalreceiptdata);
		return finaldata;
	}

	/* private Vector getcabinetdata( String allowedcabs )
	{
	Vector dataV=new Vector();
	DBResultSet dbrs=null;
	ResultSet searchRs=null;
	Hashtable data=new Hashtable();
	BarCodeHelpObj BarcodeConverter=new BarCodeHelpObj();
	String query="select lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,CABINET_NAME from cabinet where CABINET_ID in (" + allowedcabs + ") order by CABINET_ID";

	try
	{
	  dbrs=db.doQuery( query );
	  searchRs=dbrs.getResultSet();
	  data=new Hashtable();
	  String binBarcodeRId="";
	  String cabBarcodeRId="";

	  while ( searchRs.next() )
	  {
		String tmpcabid1=searchRs.getString( "CABINET_ID1" ) == null ? "" : searchRs.getString( "CABINET_ID1" );
		//String tmpcadddbid=searchRs.getString( "CABINET_ID" ) == null ? "" : searchRs.getString( "CABINET_ID" );
		data=new Hashtable();

		cabBarcodeRId=BarcodeConverter.Bar128A( "CAB" + tmpcabid1 );
		data.put( "DETAIL_0",cabBarcodeRId );
		data.put( "DETAIL_1", ( "CAB" + tmpcabid1 ) );
		data.put( "DETAIL_2",searchRs.getString( "CABINET_NAME" ) == null ? "" : searchRs.getString( "CABINET_NAME" ) );
		data.put( "DETAIL_3",searchRs.getString( "CABINET_ID" ) == null ? "" : searchRs.getString( "CABINET_ID" ) );

		dataV.addElement( data );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null )
	  {
		dbrs.close();
	  }
	}

	dataV.trimToSize();
	if (dataV.size() == 0)
	{
	  data=new Hashtable();
	  data.put( "DETAIL_0","" );
	  data.put( "DETAIL_1","" );
	  data.put( "DETAIL_2","" );
	  data.put( "DETAIL_3","" );
	  dataV.addElement(data);
	}

	return dataV;
	}*/

	private Hashtable getreceiptdata(String allowedcabs) {
		Vector dataV = new Vector();
		Hashtable resultdataV = new Hashtable();
		Vector receiptids = new Vector();
		Vector qtys = new Vector();
		DBResultSet dbrs = null;
		ResultSet searchRs = null;
		Hashtable data = new Hashtable();
		String query = "select CABINET_ID, BIN_ID, RECEIPT_ID, ITEM_ID, COUNT_QUANTITY from CABINET_RECEIPT_ITEM_BIN_VIEW where BIN_ID in (" + allowedcabs + ") order by CABINET_ID";

		try {
			dbrs = db.doQuery(query);
			searchRs = dbrs.getResultSet();
			data = new Hashtable();
			String binBarcodeRId = "";
			String cabBarcodeRId = "";

			while (searchRs.next()) {
				data = new Hashtable();

				data.put("CABINET_ID", searchRs.getString("CABINET_ID") == null ? "" : searchRs.getString("CABINET_ID"));
				data.put("BIN_ID", searchRs.getString("BIN_ID") == null ? "" : searchRs.getString("BIN_ID"));
				data.put("RECEIPT_ID", searchRs.getString("RECEIPT_ID") == null ? "" : searchRs.getString("RECEIPT_ID"));
				data.put("COUNT_QUANTITY", searchRs.getString("COUNT_QUANTITY") == null ? "" : searchRs.getString("COUNT_QUANTITY"));
				data.put("ITEM_ID", searchRs.getString("ITEM_ID") == null ? "" : searchRs.getString("ITEM_ID"));

				dataV.addElement(data);
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

		resultdataV = getReciptLabelData(dataV);
		return resultdataV;
	}

	public Hashtable getReciptLabelData(Vector Seqnumbers) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		Vector receptdataV1 = new Vector();
		Vector comptdataV1 = new Vector();
		Hashtable finaldata = new Hashtable();
		Hashtable data = new Hashtable();
		Hashtable dataV = new Hashtable();
		Hashtable compdata = new Hashtable();
		Hashtable finalcompdata = new Hashtable();
		Vector invengrpvec = new Vector();

		int numberRec = 0;

		finaldata.put("RECEIPT_DATA", receptdataV1);
		finaldata.put("RECEIPT_COMP_DATA", finalcompdata);

		if (Seqnumbers.size() == 0) {
			return finaldata;
		}

		try {
			for (int i = 0; i < Seqnumbers.size(); i++) {
				Hashtable hD = (Hashtable) Seqnumbers.elementAt(i);

				String cab_id = hD.get("CABINET_ID") == null ? "" : hD.get("CABINET_ID").toString();
				String bin_id = hD.get("BIN_ID") == null ? "" : hD.get("BIN_ID").toString();
				String receipt_id = hD.get("RECEIPT_ID") == null ? "" : hD.get("RECEIPT_ID").toString();
				String lblqty = hD.get("COUNT_QUANTITY") == null ? "" : hD.get("COUNT_QUANTITY").toString();
				String item_id = hD.get("ITEM_ID") == null ? "" : hD.get("ITEM_ID").toString();

				String Hub_Value = "";
				String Item_Id = "";
				String BarcodeRId = "";
				String inventorygrp = "";
				String certnuminvngrp = "";
				String qualitycntitem = "";
				String singleitemkit = "";
				String certified = "";
				String Query_Statement = "Select COMPONENT_MSDS_LIST,DIST_CUSTOMER_PART_LIST,DIST_CUSTOMER_PART_LIST,RECERT_NUMBER,TOTAL_RECERTS_ALLOWED, LAST_ID_PRINTED, SPEC_LIST, COMPANY_MSDS_LIST,CATALOG_ITEM_DESCRIPTION,CUSTOMER_RECEIPT_ID,RECEIVER_DATE_OF_RECEIPT,SHELF_LIFE_STORAGE_TEMP,to_char(EXPIRE_DATE_ORIG,'dd-MON-yyyy') BOEING_EXPIRE_DATE,to_char(EXPIRE_DATE_ORIG,'mmddyyyy') ITALY_EXPIRE_DATE,DATE_OF_MANUFACTURE,MAX_USE_IN_MONTHS,EXPIRE_DATE_LOCALE,DATE_OF_MANUFACTURE_LOCALE,CERTIFIED,PICKABLE,TOTAL_COMPONENTS,INSEPARABLE_KIT,NUMBER_OF_KITS,MANAGE_KITS_AS_SINGLE_UNIT,QC_DOC,CERTIFICATION_NUMBER,QUALITY_CONTROL_ITEM,INVENTORY_GROUP,HUB,QUANTITY_RECEIVED,lpad(RECEIPT_ID,8,'0') RECEIPT_ID1,RECEIPT_ID,ITEM_ID,RADIAN_PO,MFG_LOT,to_char(DATE_OF_RECEIPT,'mm/dd/yyyy') DOR,";
				Query_Statement += "RECERTS,BIN,EXPIRE_DATE,LABEL_STORAGE_TEMP,LABEL_COLOR, SHELF_LIFE_BASIS, SHELF_LIFE_BASIS_DATE, SPEC_DETAIL,LOT_STATUS,OWNER_COMPANY_ID,PO_NUMBER,QUALITY_TRACKING_NUMBER, RECERTS_TO_RECERTS_ALLOWED,OWNER_SEGMENT_ID,ACCOUNT_NUMBER,ACCOUNT_NUMBER2,ACCOUNT_NUMBER3,ACCOUNT_NUMBER4,TRACE_ID,MATERIAL_ID,SUPPLIER_CAGE_CODE,DATE_OF_REPACKAGING  from container_label_master_view where RECEIPT_ID = '"
					+ receipt_id + "'";

				dbrs = db.doQuery(Query_Statement);
				rs = dbrs.getResultSet();

				data = new Hashtable();
				dataV = new Hashtable();
				compdata = new Hashtable();
				numberRec = 0;

				try {
					while (rs.next()) {
						BarcodeRId = com.tcmis.common.util.BarCodeHandler.Code128c(rs.getString("RECEIPT_ID1"));
						//barclog.info("Barcode 128 C  "+BarcodeRId+   "   RECEIPT_ID  Code "+BarcodeConverter.Bar128C(rs.getString("RECEIPT_ID"))+"");
						data.put("DETAIL_0", BarcodeRId);
						Item_Id = rs.getString("ITEM_ID") == null ? " " : rs.getString("ITEM_ID");
						inventorygrp = rs.getString("INVENTORY_GROUP") == null ? " " : rs.getString("INVENTORY_GROUP");
						certnuminvngrp = rs.getString("CERTIFICATION_NUMBER") == null ? " " : rs.getString("CERTIFICATION_NUMBER");
						qualitycntitem = rs.getString("QUALITY_CONTROL_ITEM") == null ? " " : rs.getString("QUALITY_CONTROL_ITEM");
						singleitemkit = rs.getString("MANAGE_KITS_AS_SINGLE_UNIT") == null ? " " : rs.getString("MANAGE_KITS_AS_SINGLE_UNIT");
						certified = rs.getString("CERTIFIED") == null ? " " : rs.getString("CERTIFIED");
						String receiptId = rs.getString("RECEIPT_ID1") == null ? " " : rs.getString("RECEIPT_ID1");
						String mfgLot = rs.getString("MFG_LOT") == null ? " " : rs.getString("MFG_LOT");
						String italyExpireDate = rs.getString("ITALY_EXPIRE_DATE") == null ? " " : rs.getString("ITALY_EXPIRE_DATE");
						String labelStorageTemp = rs.getString("LABEL_STORAGE_TEMP") == null ? " " : rs.getString("LABEL_STORAGE_TEMP");
						String expireDate = rs.getString("EXPIRE_DATE") == null ? " " : rs.getString("EXPIRE_DATE");
						String maxUseInMonths = rs.getString("MAX_USE_IN_MONTHS") == null ? " " : rs.getString("MAX_USE_IN_MONTHS");
						String expireDateLocale = rs.getString("EXPIRE_DATE_LOCALE") == null ? " " : rs.getString("EXPIRE_DATE_LOCALE");
						if (("-1").equals(maxUseInMonths)) {
							maxUseInMonths = expireDateLocale;
						}

						data.put("OWNER_COMPANY_ID", rs.getString("OWNER_COMPANY_ID") == null ? "" : rs.getString("OWNER_COMPANY_ID"));
						data.put("PO_NUMBER", rs.getString("PO_NUMBER") == null ? "" : rs.getString("PO_NUMBER"));
						data.put("LOT_STATUS", rs.getString("LOT_STATUS") == null ? "" : rs.getString("LOT_STATUS"));

						data.put("DETAIL_1", Item_Id);
						data.put("DETAIL_2", receiptId);
						data.put("DETAIL_3", mfgLot);
						data.put("DETAIL_4", rs.getString("DOR") == null ? " " : rs.getString("DOR"));
						data.put("DETAIL_5", rs.getString("RECERTS") == null ? " " : rs.getString("RECERTS"));
						data.put("DETAIL_6", rs.getString("BIN") == null ? " " : rs.getString("BIN"));
						data.put("DETAIL_7", expireDate);
						data.put("DETAIL_8", labelStorageTemp);
						data.put("DETAIL_16", singleitemkit);

						data.put("INVENTORY_GROUP", inventorygrp);
						data.put("NUMBER_OF_KITS", rs.getString("NUMBER_OF_KITS") == null ? " " : rs.getString("NUMBER_OF_KITS"));
						data.put("INSEPARABLE_KIT", rs.getString("INSEPARABLE_KIT") == null ? " " : rs.getString("INSEPARABLE_KIT"));
						data.put("TOTAL_COMPONENTS", rs.getString("TOTAL_COMPONENTS") == null ? "" : rs.getString("TOTAL_COMPONENTS"));
						data.put("LABEL_COLOR", rs.getString("LABEL_COLOR") == null ? "" : rs.getString("LABEL_COLOR"));
						data.put("SHELF_LIFE_BASIS", rs.getString("SHELF_LIFE_BASIS") == null ? "" : rs.getString("SHELF_LIFE_BASIS"));
						data.put("SHELF_LIFE_BASIS_DATE", rs.getString("SHELF_LIFE_BASIS_DATE") == null ? "" : rs.getString("SHELF_LIFE_BASIS_DATE"));
						data.put("SPEC_DETAIL", rs.getString("SPEC_DETAIL") == null ? "" : rs.getString("SPEC_DETAIL"));
						data.put("ITEM_ID", rs.getString("ITEM_ID") == null ? "" : rs.getString("ITEM_ID"));
						data.put("FREE_TEXT", "");
						data.put("MAX_USE_IN_MONTHS", maxUseInMonths);
						data.put("EXPIRE_DATE_LOCALE", expireDateLocale);
						data.put("DATE_OF_MANUFACTURE_LOCALE", rs.getString("DATE_OF_MANUFACTURE_LOCALE") == null ? "" : rs.getString("DATE_OF_MANUFACTURE_LOCALE"));
						data.put("DATE_OF_MANUFACTURE", rs.getString("DATE_OF_MANUFACTURE") == null ? "" : rs.getString("DATE_OF_MANUFACTURE"));
						String italyBarcode = Item_Id + "," + receiptId + "," + mfgLot + "," + italyExpireDate;
						
						String iai2DBarcode = Item_Id + "|" + (mfgLot.indexOf("|") != -1 || mfgLot.indexOf(",") != -1 ? barCode2dReplace.matcher(mfgLot).replaceAll(" "):mfgLot) + "|" + italyExpireDate;
						data.put("IAI_2D_BARCODE", iai2DBarcode);

						data.put("DATE_OF_MANUFACTURE", rs.getString("DATE_OF_MANUFACTURE") == null ? "" : rs.getString("DATE_OF_MANUFACTURE"));
						data.put("ITALY_BARCODE", italyBarcode);
						data.put("RECEIPT_ID", receiptId);
						data.put("MFG_LOT", mfgLot);
						data.put("BIN", rs.getString("BIN") == null ? " " : rs.getString("BIN"));
						data.put("USE_WITH", "");
						data.put("EXPIRE_DATE", expireDate);
						data.put("ITEM_COMPONENT", "");
						data.put("RECEIVER_DATE_OF_RECEIPT", rs.getString("RECEIVER_DATE_OF_RECEIPT") == null ? "" : rs.getString("RECEIVER_DATE_OF_RECEIPT"));
						data.put("SHELF_LIFE_STORAGE_TEMP", rs.getString("SHELF_LIFE_STORAGE_TEMP") == null ? "" : rs.getString("SHELF_LIFE_STORAGE_TEMP"));
						data.put("CATALOG_ITEM_DESCRIPTION", rs.getString("CATALOG_ITEM_DESCRIPTION") == null ? "" : rs.getString("CATALOG_ITEM_DESCRIPTION"));
						data.put("CUSTOMER_RECEIPT_ID", rs.getString("CUSTOMER_RECEIPT_ID") == null ? "" : rs.getString("CUSTOMER_RECEIPT_ID"));
						data.put("TOTAL_RECERTS_ALLOWED", rs.getString("TOTAL_RECERTS_ALLOWED") == null ? "" : rs.getString("TOTAL_RECERTS_ALLOWED"));
						data.put("LAST_ID_PRINTED", rs.getString("LAST_ID_PRINTED") == null ? "" : rs.getString("LAST_ID_PRINTED"));
						data.put("SPEC_LIST", rs.getString("SPEC_LIST") == null ? "" : rs.getString("SPEC_LIST"));
						data.put("COMPANY_MSDS_LIST", rs.getString("COMPANY_MSDS_LIST") == null ? "" : rs.getString("COMPANY_MSDS_LIST"));
						data.put("RECERT_NUMBER", rs.getString("RECERT_NUMBER") == null ? "" : rs.getString("RECERT_NUMBER"));
						data.put("DIST_CUSTOMER_PART_LIST", rs.getString("DIST_CUSTOMER_PART_LIST") == null ? "" : rs.getString("DIST_CUSTOMER_PART_LIST"));
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

						if (labelStorageTemp != null && labelStorageTemp.trim().length() > 0) {
							labelStorageTemp = labelStorageTemp.substring(2, labelStorageTemp.length());
						}
						data.put("LABEL_STORAGE_TEMP", labelStorageTemp);

						String dpmnumber = rs.getString("QC_DOC") == null ? " " : rs.getString("QC_DOC");
						if (dpmnumber.trim().length() == 0) {
							data.put("DETAIL_14", "Not For Production");
						}
						else {
							data.put("DETAIL_14", "" + dpmnumber + "");
						}
						data.put("DETAIL_15", rs.getString("LABEL_STORAGE_TEMP") == null ? " " : rs.getString("LABEL_STORAGE_TEMP"));

						Hub_Value = rs.getString("HUB") == null ? "" : rs.getString("HUB");
						if (!invengrpvec.contains(inventorygrp)) {
							invengrpvec.add(inventorygrp);
						}

						//Rcpt ID + | + Part # + | + Lot # + | + Exp Date (DD-MMM-YY) + | + Pad with zeros to total 81 chars
						String boeing2dBarcode = "";
						boeing2dBarcode += com.tcmis.common.util.StringHandler.padCharacter(receiptId, new Integer("8"), "0", "left");
						if (dpmnumber.trim().length() == 0) {
							boeing2dBarcode += "|N/A";
						}
						else {
							boeing2dBarcode += "|" + (dpmnumber.indexOf("|") != -1 || dpmnumber.indexOf(",") != -1 ? barCode2dReplace.matcher(dpmnumber).replaceAll(" "):dpmnumber);
						}
						boeing2dBarcode += "|" + (mfgLot.indexOf("|") != -1 || mfgLot.indexOf(",") != -1 ? barCode2dReplace.matcher(mfgLot).replaceAll(" "):mfgLot);
						String boeingExpireDate = rs.getString("BOEING_EXPIRE_DATE") == null ? " " : rs.getString("BOEING_EXPIRE_DATE");
						data.put( "BOEING_EXPIRE_DATE",rs.getString( "BOEING_EXPIRE_DATE" ) == null ? " " : rs.getString( "BOEING_EXPIRE_DATE" ) );

						if (expireDate.equalsIgnoreCase("N/A")) {
							boeing2dBarcode += "|N/A|";
						}
						else {
							boeing2dBarcode += "|" + boeingExpireDate + "|";
						}
						boeing2dBarcode = com.tcmis.common.util.StringHandler.padCharacter(boeing2dBarcode, new Integer("81"), "0", "");
						//System.out.println("boeing2dBarcode:  " +boeing2dBarcode);
						data.put("BOEING_2D_BARCODE", boeing2dBarcode);
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

				if (inventorygrp.length() > 0) {
					Query_Statement = "select * from (Select distinct CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME,"
						+ "max(OPEN_MR_QTY) OPEN_MR_QTY,max(STOCKING_LEVEL) STOCKING_LEVEL,spec_id,SPEC_FIELDNAME,"
						+ "UNIT_OF_SALE,substr(PACKAGING,1,200) PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY from container_label_detail_view where ITEM_ID = "
						+ Item_Id
						+ " and HUB = "
						+ Hub_Value
						+ " and INVENTORY_GROUP = '"
						+ inventorygrp
						+ "' group by CUSTOMER_PART_REVISION,LABEL_SPEC,"
						+ "CAT_PART_NO,PART_SHORT_NAME,spec_id,SPEC_FIELDNAME,UNIT_OF_SALE,PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY order by 4 desc,5 desc, 12 desc) a where rownum < 8";
				}
				else {
					Query_Statement = "select * from (Select distinct CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME,"
						+ "max(OPEN_MR_QTY) OPEN_MR_QTY,max(STOCKING_LEVEL) STOCKING_LEVEL,spec_id,"
						+ "SPEC_FIELDNAME,UNIT_OF_SALE,substr(PACKAGING,1,200) PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY from container_label_detail_view where ITEM_ID = "
						+ Item_Id + " and HUB = " + Hub_Value
						+ " group by CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME,spec_id,SPEC_FIELDNAME,UNIT_OF_SALE,PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY "
						+ "order by 4 desc,5 desc, 12 desc) a where rownum < 8";
				}

				dbrs = db.doQuery(Query_Statement);
				rs = dbrs.getResultSet();

				data.put("DETAIL_9", "");
				data.put("DETAIL_10", "");
				data.put("DETAIL_11", "");
				data.put("DETAIL_12", "");
				data.put("DETAIL_13", "");
				data.put("DETAIL_17", "");
				data.put("DETAIL_18", "");
				data.put("PART_SHORT_NAME", "");
				data.put("LABEL_SPEC", "");
				data.put("SPEC_ID", "");
				data.put("SPEC_FIELDNAME", "");
				data.put("UNIT_OF_SALE", "");
				data.put("PACKAGING", "");
				data.put("KIT_MSDS_NUMBER", "");
				data.put("MIN_RT_OUT_TIME", "");
				data.put("MIN_STORAGE_TEMP_DISPLAY", "");
				data.put("TIME_TEMP_SENSITIVE", "");
				data.put("PART_DESCRIPTION", "");
				data.put( "SPEC_DISPLAY","" );
                data.put( "CUSTOMER_PART_REVISION","" );
                
                String partList = "";
				int partCount = 0;
				try {
					while (rs.next()) {
						String currentPartNumber = rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO");
						if (currentPartNumber.trim().length() > 0) {
							partList += currentPartNumber;
							if (partCount > 0) {
								partList += ",";
							}

							partCount++;
						}

						if (numberRec == 0) {
							data.remove("DETAIL_9");
							data.put("DETAIL_9", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
							data.remove("PART_SHORT_NAME");
							data.put("PART_SHORT_NAME", rs.getString("PART_SHORT_NAME") == null ? " " : rs.getString("PART_SHORT_NAME"));
							data.remove("LABEL_SPEC");
							data.put("LABEL_SPEC", rs.getString("LABEL_SPEC") == null ? " " : rs.getString("LABEL_SPEC"));
							data.remove("SPEC_ID");
							data.put("SPEC_ID", rs.getString("SPEC_ID") == null ? " " : rs.getString("SPEC_ID"));
							data.remove("SPEC_FIELDNAME");
							data.put("SPEC_FIELDNAME", rs.getString("SPEC_FIELDNAME") == null ? " " : rs.getString("SPEC_FIELDNAME"));
							data.remove("UNIT_OF_SALE");
							data.put("UNIT_OF_SALE", rs.getString("UNIT_OF_SALE") == null ? " " : rs.getString("UNIT_OF_SALE"));
							data.remove("PACKAGING");
							data.put("PACKAGING", rs.getString("PACKAGING") == null ? " " : rs.getString("PACKAGING"));
							data.remove("KIT_MSDS_NUMBER");
							data.put("KIT_MSDS_NUMBER", rs.getString("KIT_MSDS_NUMBER") == null ? " " : rs.getString("KIT_MSDS_NUMBER"));
							data.remove("MIN_RT_OUT_TIME");
							data.put("MIN_RT_OUT_TIME", rs.getString("MIN_RT_OUT_TIME") == null ? " " : rs.getString("MIN_RT_OUT_TIME"));
							data.remove("MIN_STORAGE_TEMP_DISPLAY");
							data.put("MIN_STORAGE_TEMP_DISPLAY", rs.getString("MIN_STORAGE_TEMP_DISPLAY") == null ? " " : rs.getString("MIN_STORAGE_TEMP_DISPLAY"));
							data.remove("TIME_TEMP_SENSITIVE");
							data.put("TIME_TEMP_SENSITIVE", rs.getString("TIME_TEMP_SENSITIVE") == null ? " " : rs.getString("TIME_TEMP_SENSITIVE"));
							data.remove("PART_DESCRIPTION");
							String partDescription = rs.getString("PART_DESCRIPTION") == null ? " " : rs.getString("PART_DESCRIPTION");
							if (partDescription.length() > 30) {
								partDescription = partDescription.substring(0, 30);
							}
							data.put("PART_DESCRIPTION", partDescription);
							data.remove( "SPEC_DISPLAY" );
							String specDisplay = rs.getString( "SPEC_DISPLAY" ) == null ? " " : rs.getString( "SPEC_DISPLAY" );
							if (specDisplay.length() > 76)
								specDisplay = specDisplay.substring(0,75);
							data.put( "SPEC_DISPLAY",specDisplay);

							String iai2DBarcode = data.get("IAI_2D_BARCODE") == null ? "" : data.get("IAI_2D_BARCODE").toString();
							iai2DBarcode += "|" + currentPartNumber;
							data.remove("IAI_2D_BARCODE");
							data.put("IAI_2D_BARCODE", iai2DBarcode);

                            data.remove( "CUSTOMER_PART_REVISION" );
                            data.put( "CUSTOMER_PART_REVISION",rs.getString( "CUSTOMER_PART_REVISION" ) == null ? " " : rs.getString( "CUSTOMER_PART_REVISION" ) );
                        }
						else if (numberRec == 1) {
							data.remove("DETAIL_10");
							data.put("DETAIL_10", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
							data.remove("LABEL_SPEC2");
							data.put("LABEL_SPEC2", rs.getString("LABEL_SPEC") == null ? " " : rs.getString("LABEL_SPEC"));
						}
						else if (numberRec == 2) {
							data.remove("DETAIL_11");
							data.put("DETAIL_11", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
						}
						else if (numberRec == 3) {
							data.remove("DETAIL_12");
							data.put("DETAIL_12", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
						}
						else if (numberRec == 4) {
							data.remove("DETAIL_13");
							data.put("DETAIL_13", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
						}
						else if (numberRec == 5) {
							data.remove("DETAIL_17");
							data.put("DETAIL_17", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
						}
						else if (numberRec == 6) {
							data.remove("DETAIL_18");
							data.put("DETAIL_18", rs.getString("CAT_PART_NO") == null ? " " : rs.getString("CAT_PART_NO"));
						}

						numberRec += 1;
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
				data.put("PART_LIST", partList);

				if ("Y".equalsIgnoreCase(qualitycntitem) && certnuminvngrp.trim().length() > 0) {
					data.remove("DETAIL_13");
					data.put("DETAIL_13", "Accepted By: " + certnuminvngrp + "");
				}

				if ("Y".equalsIgnoreCase(qualitycntitem) && "Y".equalsIgnoreCase(certified) && certnuminvngrp.trim().length() == 0) {
					barclog.info("No Certification Number for Receipt ID " + receipt_id + "");
					return finaldata;
				}

				Query_Statement = "Select distinct HMIS_HEALTH, HMIS_FLAMMABILITY, HMIS_REACTIVITY, SPECIFIC_HAZARD, HEALTH, FLAMMABILITY, REACTIVITY, PERSONAL_PROTECTION, MFG_DESC, TRADE_NAME,RECERT_NUMBER,LABEL_STORAGE_TEMP, lpad(RECEIPT_ID,8,'0') RECEIPT_ID1,RECEIPT_ID, USE_WITH,ITEM_COMPONENT_QUANTITY,ITEM_ID,ITEM_COMPONENT,MFG_LOT, BIN, EXPIRE_DATE, QC_DOC,LABEL_COLOR, SHELF_LIFE_BASIS, SHELF_LIFE_BASIS_DATE, SPEC_DETAIL,DATE_OF_REPACKAGING  from CONTAINER_LABEL_COMPONENT_VIEW where RECEIPT_ID = "
					+ receipt_id + "";
				dbrs = db.doQuery(Query_Statement);
				rs = dbrs.getResultSet();
				comptdataV1 = new Vector();
				String keyreceiptid = "";
				try
				{
					int recCntr = 0;
					while ( rs.next() )
					{
						if (recCntr == 0) {
							recCntr = 1;
							data.put("HEALTH", rs.getString("HEALTH") == null ? " " : rs.getString("HEALTH") );
							data.put("FLAMMABILITY", rs.getString("FLAMMABILITY") == null ? " " : rs.getString("FLAMMABILITY"));
							data.put("REACTIVITY", rs.getString("REACTIVITY") == null ? " " : rs.getString("REACTIVITY"));
							data.put("PERSONAL_PROTECTION", rs.getString("PERSONAL_PROTECTION") == null ? " " : rs.getString("PERSONAL_PROTECTION") );
							data.put("MFG_DESC", rs.getString("MFG_DESC") == null ? " " : rs.getString("MFG_DESC") );
							data.put("TRADE_NAME", rs.getString("TRADE_NAME") == null ? " " :  rs.getString("TRADE_NAME"));
							data.put("HMIS_HEALTH", rs.getString("HMIS_HEALTH") == null ? " " : rs.getString("HMIS_HEALTH"));
							data.put("HMIS_FLAMMABILITY", rs.getString("HMIS_FLAMMABILITY") == null ? " " : rs.getString("HMIS_FLAMMABILITY"));
                            data.put("HMIS_REACTIVITY", rs.getString("HMIS_REACTIVITY") == null ? " " : rs.getString("HMIS_REACTIVITY"));
                            data.put("SPECIFIC_HAZARD", rs.getString("SPECIFIC_HAZARD") == null ? " " : rs.getString("SPECIFIC_HAZARD"));
						}
						if ("N".equalsIgnoreCase(singleitemkit)) {
							compdata = new Hashtable();
							compdata.put("DETAIL_0", rs.getString("RECEIPT_ID1") == null ? " " : rs.getString("RECEIPT_ID1"));
							compdata.put("RECEIPT_ID", rs.getString("RECEIPT_ID1") == null ? " " : rs.getString("RECEIPT_ID1"));
							String dpmnumber = rs.getString("QC_DOC") == null ? " " : rs.getString("QC_DOC");
							if (dpmnumber.trim().length() == 0) {
								compdata.put("DETAIL_1", "Not For Production");
								compdata.put("QC_DOC", "Not For Production");
							}
							else {
								compdata.put("DETAIL_1", "" + dpmnumber + "");
								compdata.put("QC_DOC", "" + dpmnumber + "");
							}

							keyreceiptid = rs.getString("RECEIPT_ID1") == null ? " " : rs.getString("RECEIPT_ID1");

							compdata.put("DETAIL_2", rs.getString("MFG_LOT") == null ? " " : rs.getString("MFG_LOT"));
							compdata.put("DETAIL_3", rs.getString("BIN") == null ? " " : rs.getString("BIN"));
							compdata.put("DETAIL_4", rs.getString("LABEL_STORAGE_TEMP") == null ? " " : rs.getString("LABEL_STORAGE_TEMP"));
							compdata.put("DETAIL_5", rs.getString("EXPIRE_DATE") == null ? " " : rs.getString("EXPIRE_DATE"));
							compdata.put("DETAIL_6", rs.getString("ITEM_COMPONENT") == null ? " " : rs.getString("ITEM_COMPONENT"));
							String usewith = "Use ";
							usewith += rs.getString("ITEM_COMPONENT_QUANTITY") == null ? " " : rs.getString("ITEM_COMPONENT_QUANTITY");
							usewith += " With: ";
							usewith += rs.getString("USE_WITH") == null ? " " : rs.getString("USE_WITH");
							compdata.put("DETAIL_7", usewith);
							compdata.put("DETAIL_8", rs.getString("LABEL_STORAGE_TEMP") == null ? " " : rs.getString("LABEL_STORAGE_TEMP"));
							compdata.put("ITEM_COMPONENT_QUANTITY", rs.getString("ITEM_COMPONENT_QUANTITY") == null ? " " : rs.getString("ITEM_COMPONENT_QUANTITY"));
							compdata.put("LABEL_COLOR", rs.getString("LABEL_COLOR") == null ? "" : rs.getString("LABEL_COLOR"));
							compdata.put("SHELF_LIFE_BASIS", rs.getString("SHELF_LIFE_BASIS") == null ? "" : rs.getString("SHELF_LIFE_BASIS"));
							compdata.put("SHELF_LIFE_BASIS_DATE", rs.getString("SHELF_LIFE_BASIS_DATE") == null ? "" : rs.getString("SHELF_LIFE_BASIS_DATE"));
							compdata.put("SPEC_DETAIL", rs.getString("SPEC_DETAIL") == null ? "" : rs.getString("SPEC_DETAIL"));
							compdata.put("ITEM_ID", rs.getString("ITEM_ID") == null ? "" : rs.getString("ITEM_ID"));
							compdata.put("RECERT_NUMBER", rs.getString("RECERT_NUMBER") == null ? "" : rs.getString("RECERT_NUMBER"));
							compdata.put("FREE_TEXT", "");
							compdata.put("BIN", rs.getString("BIN") == null ? " " : rs.getString("BIN"));
							compdata.put("MFG_LOT", rs.getString("MFG_LOT") == null ? " " : rs.getString("MFG_LOT"));
							compdata.put("USE_WITH", usewith);
							compdata.put("EXPIRE_DATE", rs.getString("EXPIRE_DATE") == null ? " " : rs.getString("EXPIRE_DATE"));
							compdata.put("ITEM_COMPONENT", rs.getString("ITEM_COMPONENT") == null ? " " : rs.getString("ITEM_COMPONENT"));

							compdata.put("HMIS_HEALTH", rs.getString("HMIS_HEALTH") == null ? " " : rs.getString("HMIS_HEALTH"));
							compdata.put("HMIS_FLAMMABILITY", rs.getString("HMIS_FLAMMABILITY") == null ? " " : rs.getString("HMIS_FLAMMABILITY"));
							compdata.put("HMIS_REACTIVITY", rs.getString("HMIS_REACTIVITY") == null ? " " : rs.getString("HMIS_REACTIVITY"));
							compdata.put("SPECIFIC_HAZARD", rs.getString("SPECIFIC_HAZARD") == null ? " " : rs.getString("SPECIFIC_HAZARD"));
							compdata.put("HEALTH", rs.getString("HEALTH") == null ? " " : rs.getString("HEALTH"));
							compdata.put("FLAMMABILITY", rs.getString("FLAMMABILITY") == null ? " " : rs.getString("FLAMMABILITY"));
							compdata.put("REACTIVITY", rs.getString("REACTIVITY") == null ? " " : rs.getString("REACTIVITY"));
							compdata.put("PERSONAL_PROTECTION", rs.getString("PERSONAL_PROTECTION") == null ? " " : rs.getString("PERSONAL_PROTECTION"));
							compdata.put("MFG_DESC", rs.getString("MFG_DESC") == null ? " " : rs.getString("MFG_DESC"));
							compdata.put("TRADE_NAME", rs.getString("TRADE_NAME") == null ? " " : rs.getString("TRADE_NAME"));
							compdata.put("DATE_OF_REPACKAGING", rs.getString("DATE_OF_REPACKAGING") == null ? " " : rs.getString("DATE_OF_REPACKAGING"));
							
							compdata.put("PART_1", "" + data.get("DETAIL_9").toString() + "");
							compdata.put("PART_2", "" + data.get("DETAIL_10").toString() + "");
							compdata.put("PART_3", "" + data.get("DETAIL_11").toString() + "");
							compdata.put("PART_4", "" + data.get("DETAIL_12").toString() + "");
							compdata.put("PART_5", "" + data.get("DETAIL_17").toString() + "");
							compdata.put("PART_6", "" + data.get("DETAIL_18").toString() + "");
							compdata.put("PART_7", "" + data.get("DETAIL_13").toString() + "");
							compdata.put("CATALOG_ITEM_DESCRIPTION", "" + data.get("CATALOG_ITEM_DESCRIPTION").toString() + "");
							compdata.put( "COMPANY_MSDS_LIST",""+data.get("COMPANY_MSDS_LIST").toString()+"" );
							compdata.put("CUSTOMER_RECEIPT_ID", "" + data.get("CUSTOMER_RECEIPT_ID").toString() + "");
							compdata.put("KIT_MSDS_NUMBER", "" + data.get("KIT_MSDS_NUMBER").toString() + "");
							compdata.put("MIN_RT_OUT_TIME", "" + data.get("MIN_RT_OUT_TIME").toString() + "");
							compdata.put("MIN_STORAGE_TEMP_DISPLAY", "" + data.get("MIN_STORAGE_TEMP_DISPLAY").toString() + "");
							compdata.put( "PART_DESCRIPTION",""+data.get("PART_DESCRIPTION").toString()+"" );
							compdata.put("TIME_TEMP_SENSITIVE", "" + data.get("TIME_TEMP_SENSITIVE").toString() + "");
							compdata.put("EXPIRE_DATE_LOCALE", "" + data.get("EXPIRE_DATE_LOCALE").toString() + "");

							if ("Y".equalsIgnoreCase(qualitycntitem) && certnuminvngrp.trim().length() > 0) {
								compdata.put("DETAIL_9", "Accepted By: " + certnuminvngrp + "");
							}
							else {
								compdata.put("DETAIL_9", "");
							}

							compdata.put("DETAIL_10", "");
							compdata.put("DETAIL_11", "");
							compdata.put("DETAIL_12", "");
							compdata.put("DETAIL_13", "");

							comptdataV1.addElement(compdata);
						}
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
				if(!comptdataV1.isEmpty()) {
					finalcompdata.put(keyreceiptid, comptdataV1);
				}

				dataV.put("QUANTITY_RECEIVED", lblqty);
				dataV.put("DATA", data);
				receptdataV1.addElement(dataV);

			} //End of For

		} //End Try
		catch (Exception e) {
			e.printStackTrace();
			return finaldata;
		}

		comptdataV1.trimToSize();
		if (comptdataV1.size() == 0) {
			compdata = new Hashtable();
			compdata.put("DETAIL_0", "");
			compdata.put("DETAIL_1", "");
			compdata.put("DETAIL_2", "");
			compdata.put("DETAIL_3", "");
			compdata.put("DETAIL_4", "");

			comptdataV1.addElement(compdata);
		}

		receptdataV1.trimToSize();
		if (receptdataV1.size() == 0) {
			data = new Hashtable();
			data.put("DETAIL_0", "");
			data.put("DETAIL_1", "");
			data.put("DETAIL_2", "");
			data.put("DETAIL_3", "");
			data.put("DETAIL_4", "");
			data.put("DETAIL_5", "");
			data.put("DETAIL_6", "");
			data.put("DETAIL_7", "");
			data.put("DETAIL_8", "");
			data.put("DETAIL_9", "");
			data.put("DETAIL_10", "");
			data.put("DETAIL_11", "");
			data.put("DETAIL_12", "");
			data.put("DETAIL_13", "");
			data.put("DETAIL_14", "");
			data.put("DETAIL_15", "");
			data.put("DETAIL_16", "");

			receptdataV1.addElement(data);
		}

		finaldata.remove("RECEIPT_DATA");
		finaldata.put("RECEIPT_DATA", receptdataV1);
		finaldata.remove("RECEIPT_COMP_DATA");
		finaldata.put("RECEIPT_COMP_DATA", finalcompdata);

		return finaldata;
	}

	public void buildzplurl(Hashtable allTheData, String NameofFile, String HubNo, String personnelID, String printerPath, String printerRes) throws Exception {
		ZPLBarCodeGenerator zplobj = new ZPLBarCodeGenerator(db);

		Vector binData = new Vector();
		Hashtable allReceiptData = new Hashtable();
		binData = (Vector) allTheData.get("BIN_DATA");
		allReceiptData = (Hashtable) allTheData.get("BIN_RECEIPT_DATA");

		//StringBuffer cabbinlabel=new StringBuffer();
		BarCodeHelpObj BarcodeConverter = new BarCodeHelpObj();
		DBResultSet dbrs = null;
		ResultSet rs = null;
		/*String iszplprinter="select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " + personnelID + " and x.LABEL_STOCK = '31'";
		String printerPath = "";
		String printerRes = "";
		try
		{
		dbrs=db.doQuery( iszplprinter );
		rs=dbrs.getResultSet();

		while ( rs.next() )
		{
		printerPath=rs.getString( "PRINTER_PATH" ) == null ? " " : rs.getString( "PRINTER_PATH" );
		printerRes=rs.getString( "PRINTER_RESOLUTION_DPI" ) == null ? " " : rs.getString( "PRINTER_RESOLUTION_DPI" );
		}
		}
		catch ( Exception e )
		{
		e.printStackTrace();
		}
		finally
		{
		if ( dbrs != null ) { dbrs.close();}
		}*/

		String writefilepath = radian.web.tcmisResourceLoader.getsavelabelpath();
		String file = "";
		file = writefilepath + NameofFile + ".txt";
		PrintWriter pw = new PrintWriter(new FileOutputStream(file));

		StringBuilder zpl = new StringBuilder();
		String cabinetTemplate = zplobj.gettemplates(invengrpvec, "cabinet", printerRes);
		String cabinetbinTemplate = zplobj.gettemplates(invengrpvec, "cabinetbin", printerRes);
		String containerTemplate = zplobj.gettemplates(invengrpvec, "container", printerRes);
		 
		zpl.append(cabinetTemplate);
		zpl.append(cabinetbinTemplate);
		zpl.append(containerTemplate);

		//cabbinlabel.append( BarcodeConverter.cabinetlabeltmplate(printerPath) );
		//cabbinlabel.append( BarcodeConverter.itemlabeltmplate(printerPath) );

		Vector labeledCabs = new Vector();

		try {
			Hashtable cablablformatlist = new Hashtable();
			cablablformatlist = zplobj.getlabelformatlist(invengrpvec, "cabinet");

			Hashtable binlablformatlist = new Hashtable();
			binlablformatlist = zplobj.getlabelformatlist(invengrpvec, "cabinetbin");

			Hashtable cablablfieldlist = new Hashtable();
			cablablfieldlist = zplobj.getlbldeflist(cablablformatlist, printerRes);

			Hashtable binlablfieldlist = new Hashtable();
			binlablfieldlist = zplobj.getlbldeflist(binlablformatlist, printerRes);

			Hashtable lablformatlist = new Hashtable();
			lablformatlist = zplobj.getlabelformatlist(invengrpvec, "container");

			Hashtable lablfieldlist = new Hashtable();
			lablfieldlist = zplobj.getlbldeflist(lablformatlist, printerRes);

			Hashtable lablformatLocalelist = new Hashtable();
			lablformatLocalelist = zplobj.getlabelformatlist(invengrpvec, "containerLocale");

			Hashtable lablfieldLocalelist = new Hashtable();
			lablfieldLocalelist = zplobj.getlbldeflist(lablformatLocalelist, printerRes);

			Hashtable kitlablformatlist = new Hashtable();
			kitlablformatlist = zplobj.getlabelformatlist(invengrpvec, "kit");

			Hashtable kitlablfieldlist = new Hashtable();
			kitlablfieldlist = zplobj.getlbldeflist(kitlablformatlist, printerRes);

			Hashtable kitlablformatLocalelist = new Hashtable();
			kitlablformatLocalelist = zplobj.getlabelformatlist(invengrpvec, "kitLocale");

			Hashtable kitlablfieldLocalelist = new Hashtable();
			kitlablfieldLocalelist = zplobj.getlbldeflist(kitlablformatLocalelist, printerRes);

			for (int j = 0; j < binData.size(); j++) {
				Hashtable binsh = (Hashtable) binData.elementAt(j);
				String bincabinetid = binsh.get("DETAIL_12").toString();
				String binid = binsh.get("DETAIL_6").toString();
				String invengrp = (binsh.get("INVENTORY_GROUP") == null ? "" : binsh.get("INVENTORY_GROUP").toString());

				if (!labeledCabs.contains(bincabinetid)) {
					Hashtable cabh = new Hashtable();
					cabh.put("DETAIL_1", bincabinetid);
					cabh.put("DETAIL_2", binsh.get("DETAIL_11").toString());
					cabh.put("INVENTORY_GROUP", invengrp);

					StringBuffer cabinetlabel = zplobj.cabinetlabel(cabh, cablablformatlist, cablablfieldlist);
					zpl.append(cabinetlabel);
					labeledCabs.add(bincabinetid);
				}
				StringBuffer cabbinlabel = zplobj.cabbinlabel(binsh, HubNo, binlablformatlist, binlablfieldlist);
				zpl.append(cabbinlabel);

				Hashtable allReceiptDatatemp = new Hashtable();
				allReceiptDatatemp = (Hashtable) allReceiptData.get(binid);
				Vector receptdataV1 = new Vector();
				Hashtable comptdataV1 = new Hashtable();
				receptdataV1 = (Vector) allReceiptDatatemp.get("RECEIPT_DATA");
				comptdataV1 = (Hashtable) allReceiptDatatemp.get("RECEIPT_COMP_DATA");
				if (receptdataV1.size() > 0) {
					//cabbinlabel.append( zplobj.buildReceiptlabels( HubNo,receptdataV1,comptdataV1,"31",false,printerPath,printerRes,invengrpvec,lablformatlist,lablfieldlist,kitlablformatlist,kitlablfieldlist ) );

					boolean printtemplate = false;
					String papersiZe = "31";

					if (printtemplate) {
						String templates = "";
						if ("038".equalsIgnoreCase(papersiZe)) {
							templates = zplobj.gettemplates(invengrpvec, "tape", printerRes);
						}
						else if ("35".equalsIgnoreCase(papersiZe)) {
							templates = zplobj.gettemplates(invengrpvec, "pmc", printerRes);
						}
						else {
							templates = zplobj.gettemplates(invengrpvec, "container", printerRes);
						}
						zpl.append(templates);
					}
					//barclog.debug("Bin Id:  "+binid+"      No of Receipts:  "+receptdataV1.size()+"  ");
					try {
						for (int i = 0; i < receptdataV1.size(); i++) {
							Hashtable h = (Hashtable) receptdataV1.elementAt(i);
							String qtyreceived = h.get("QUANTITY_RECEIVED").toString();
							int labelqty = 0;
							try {
								labelqty = Integer.parseInt(qtyreceived);
							}
							catch (Exception eee) {
								labelqty = 1;
							}

							Hashtable recptData = (Hashtable) h.get("DATA");
							String kitlabel = (recptData.get("DETAIL_16") == null ? "" : recptData.get("DETAIL_16").toString());
							String keyReceipt = (recptData.get("DETAIL_2") == null ? "" : recptData.get("DETAIL_2").toString());
							//String invengrp= ( recptData.get( "INVENTORY_GROUP" ) == null ? "" : recptData.get( "INVENTORY_GROUP" ).toString() );
							String nofofcominkit = (recptData.get("NUMBER_OF_KITS") == null ? "" : recptData.get("NUMBER_OF_KITS").toString());
							int numfkits = Integer.parseInt(nofofcominkit);
							String inseperablekit = (recptData.get("INSEPARABLE_KIT") == null ? "" : recptData.get("INSEPARABLE_KIT").toString());
							String numberofcomp = (recptData.get("TOTAL_COMPONENTS") == null ? "" : recptData.get("TOTAL_COMPONENTS").toString());
							int noofcomp = 0;
							try {
								noofcomp = Integer.parseInt(numberofcomp);
							}
							catch (Exception eee) {
								noofcomp = 1;
							}

							StringBuffer recLabel = new StringBuffer();
							StringBuffer comprecLabel = new StringBuffer();
							boolean usepqvalue = true;
							if ("N".equalsIgnoreCase(kitlabel) || (numfkits > 1 && !"Y".equalsIgnoreCase(inseperablekit))) {
								usepqvalue = false;
							}

							recLabel.append(zplobj.stditemlabel(recptData, "" + labelqty + "", HubNo, lablformatlist, lablfieldlist, lablformatLocalelist, lablfieldLocalelist, usepqvalue, ""));

							if ("N".equalsIgnoreCase(kitlabel)) {
								Vector compV = new Vector();
								compV = (Vector) comptdataV1.get(keyReceipt);

								if (compV == null) {
									compV = new Vector();
								}

								comprecLabel.append(zplobj.componentlabel(compV, HubNo, kitlablformatlist, kitlablfieldlist, invengrp, usepqvalue));
							}
							else if (numfkits > 1 && !"Y".equalsIgnoreCase(inseperablekit)) {
								comprecLabel.append(zplobj.stdkitlabel(recptData, "" + (noofcomp - 1) + "", HubNo, kitlablformatlist, kitlablfieldlist, kitlablformatLocalelist, kitlablfieldLocalelist, invengrp, true, ""));
							}

							if (usepqvalue) {
								zpl.append(recLabel);
								zpl.append(comprecLabel);
							}
							else {
								for (int nooflabels = 0; nooflabels < labelqty; nooflabels++) {
									zpl.append(recLabel);
									zpl.append(comprecLabel);
								}
							}
						}
					}
					catch (Exception e11) {
						e11.printStackTrace();
					}
				}
			}
			pw.print(zpl.toString());
		}
		catch (Exception e11) {
			e11.printStackTrace();
		}
		finally {
			pw.close();
		}
		StringBuffer blankbuffer = new StringBuffer();
		zplobj.writefiletodisk(blankbuffer, zplobj.buildprintjnlpfile(HubNo, "31", "labels/" + NameofFile + ".txt", personnelID, printerPath, zpl.toString()), "" + NameofFile + "", false);
	}

	/*public void buildiTest( Vector cabinetdata,Vector bindata,Hashtable receiptdata, String NameofFile,String HubNo )  throws Exception
	{
	ACJEngine en=new ACJEngine();
	en.setDebugMode( true );
	en.setX11GfxAvailibility( false );
	en.setTargetOutputDevice( ACJConstants.PDF );
	OutputStream os=null;
	TemplateManager tm=null;

	ACJOutputProcessor eClient=new ACJOutputProcessor();

	String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
	String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
	String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
	String papersize=this.getpaperSize().trim();

	eClient.setPathForFontMapFile( fontmappath );

	  barclog.info("Here in cabialllabelPage generating PDF file");
	  try
	  {
		if ( "2118".equalsIgnoreCase( HubNo ) )
		{
		  en.readTemplate( "" + templatpath + "alllblsforcab2118.erw" );
		}
		else
		{
		  en.readTemplate( "" + templatpath + "alllblsforcab.erw" );
		}
	}
	catch ( Exception e )
	{
	System.out.println( "ERROR loading template" );
	e.printStackTrace();
	throw e;
	}

	  tm=en.getTemplateManager();

	try
	  {
		AppDataHandler ds=new AppDataHandler();
		//register table...
		RegisterTable[] cabrt=getcabData( cabinetdata );
		for ( int i=0; i < cabrt.length; i++ )
		{
		  Vector v1=cabrt[i].getData();
		  Vector v2=cabrt[i].getMethods();
		  ds.RegisterTable( cabrt[i].getData(),cabrt[i].getName(),cabrt[i].getMethods(),cabrt[i].getWhere() );
		}

		RegisterTable[] binrt=getbinData( bindata );
		for ( int i=0; i < binrt.length; i++ )
		{
		  Vector v1=binrt[i].getData();
		  Vector v2=binrt[i].getMethods();
		  ds.RegisterTable( binrt[i].getData(),binrt[i].getName(),binrt[i].getMethods(),binrt[i].getWhere() );
		}

		Vector receptdataV1=new Vector();
		Vector comptdataV1=new Vector();
		receptdataV1= ( Vector ) receiptdata.get( "RECEIPT_DATA" );
		comptdataV1= ( Vector ) receiptdata.get( "RECEIPT_COMP_DATA" );
		RegisterTable[] recrt=getrecData( receptdataV1 );
		for ( int i=0; i < recrt.length; i++ )
		{
		  Vector v1=recrt[i].getData();
		  Vector v2=recrt[i].getMethods();
		  ds.RegisterTable( recrt[i].getData(),recrt[i].getName(),recrt[i].getMethods(),recrt[i].getWhere() );
		}

		RegisterTable[] reccomrt=getreccompData( comptdataV1 );
		for ( int i=0; i < reccomrt.length; i++ )
		{
		  Vector v1=reccomrt[i].getData();
		  Vector v2=reccomrt[i].getMethods();
		  ds.RegisterTable( reccomrt[i].getData(),reccomrt[i].getName(),reccomrt[i].getMethods(),reccomrt[i].getWhere() );
		}
		en.setDataSource( ds );

		en.setDataSource( ds );
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		throw e;
	  }

	try
	{
	  eClient.setReportData( en.generateReport() );
	}
	catch ( Exception ex )
	{
	  System.out.println( "\n\n---------ERROR: While generating report" );
	  ex.printStackTrace();
	  throw ex;
	}


	try
	{
	  eClient.setPDFProperty( "FileName","" + writefilepath + "" + NameofFile + "" );
	  eClient.generatePDF();
	}
	catch ( Exception e )
	{
	  System.out.println( "\n\n---------ERROR generating report" );
	  e.printStackTrace();
	  throw e;
	}
	}

	public RegisterTable[] getcabData( Vector reportData1 )  throws Exception
	{
	RegisterTable[] rt=new RegisterTable[1];

	try
	{
	 rt[0]=new RegisterTable( allcabinetData.getVector( reportData1,"CABINET" ),"CABINET",allcabinetData.getFieldVector( ),null );
	}
	catch ( Exception e1 )
	{
	 e1.printStackTrace();
	 throw e1;
	}
	return rt;
	}

	public RegisterTable[] getbinData( Vector reportData1 ) throws Exception
	{
	RegisterTable[] rt=new RegisterTable[1];

	try
	{
	  rt[0]=new RegisterTable( allcabbinData.getVector( reportData1,"BIN" ),"BIN",allcabbinData.getFieldVector( ),null );
	}
	catch ( Exception e1 )
	{
	  e1.printStackTrace();
	  throw e1;
	}
	return rt;
	}

	public RegisterTable[] getrecData( Vector reportData1 ) throws Exception
	{
	RegisterTable[] rt=new RegisterTable[1];

	try
	{
	  rt[0]=new RegisterTable( allcabreceiptData.getVector( reportData1,"RECEIPT" ),"BARCODE",allcabreceiptData.getFieldVector( ),null );
	}
	catch ( Exception e1 )
	{
	  e1.printStackTrace();
	  throw e1;
	}
	return rt;
	}

	public RegisterTable[] getreccompData( Vector reportData1 ) throws Exception
	{
	RegisterTable[] rt=new RegisterTable[1];

	try
	{
	  rt[0]=new RegisterTable( allcabreccompData.getVector( reportData1,"RECCOMP" ),"RECCOMP",allcabreccompData.getFieldVector( ),null );
	}
	catch ( Exception e1 )
	{
	  e1.printStackTrace();
	  throw e1;
	}
	return rt;
	}
	 */
}