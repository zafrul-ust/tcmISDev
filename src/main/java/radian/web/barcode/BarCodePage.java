package radian.web.barcode;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 *
 * 11-11-02
 * Making Changes to make seagate Labels
 * 05-06-03 - Rolloed back changes made for Coustomer labeling page
 * 05-09-03 - Cleaned up the code.
 * 05-12-03 - Added the hub part. to access a different tempplate of each hub
 * 11-20-03 - Made special changes to HSd and Sikorsky Inventory Group labels. Updating the pickable field in receipt to 'Y' whenever a label is printed
 * 01-16-04 - Changed the wording from Acceptance Stamp to Accepted By
 * 04-26-04 - Changes to accomodate the printing of different labels for parts in a kit
 * 06-05-04 - Moving to use ZPL to print labels
 * 06-30-04 Changing Printer Path to be based on personnel ID
 * 07-30-04 Changing the templates to be based on inventory group instead of hub
 * 09-13-04 Printing Main Receipt Label and the kit labels consecutively
 * 02-28-05 Giving the option to not print Kit/Case Qty Labels.
 * 07-18-06 Skiping the check for certification number if the labels are being printed from supplier shipping page.
 */
public class BarCodePage
{
	// Initializing global Variables
	private ServerResourceBundle bundle=null;
	private TcmISDBModel db = null;
	private Vector QuantityNos = null;
	boolean UserDefinedQty = false;
	private String PaperSize = "31";
	private String hubnum = "2101";
	protected boolean seagateLabels = false;
	protected String ConBin = "";
	private static org.apache.log4j.Logger barclog = org.apache.log4j.Logger.getLogger(BarCodePage.class);
	private static Pattern barCode2dReplace = Pattern.compile("\\||,",Pattern.MULTILINE);

	public BarCodePage(ServerResourceBundle b, TcmISDBModel d)
	{
		bundle = b;
		db = d;
	}

	public BarCodePage(ServerResourceBundle b, TcmISDBModel d,String papersize1)
	{
		bundle = b;
		db = d;
		PaperSize = papersize1.trim();
	}

	public BarCodePage(ServerResourceBundle b, TcmISDBModel d,Vector Quantity1s)
	{
		bundle = b;
		db = d;
		QuantityNos = Quantity1s;
		UserDefinedQty = true;
	}

	public BarCodePage(ServerResourceBundle b, TcmISDBModel d,Vector Quantity1s,String papersize2)
	{
		bundle = b;
		db = d;
		QuantityNos = Quantity1s;
		UserDefinedQty = true;
		PaperSize = papersize2.trim();
	}

	public BarCodePage()
	{

	}

	//Nawaz 05-02-02
	public void setconBin(String id)
	{
		this.ConBin = id;
	}

	private String getconBin()  throws Exception
	{
		return this.ConBin;
	}

	public void sethubNum(String id21)
	{
		this.hubnum = id21;
	}

	private String gethubNum()  throws Exception
	{
		return this.hubnum;
	}

	public String generateContainerLabel( Vector Seqnumbers,String personnelId,boolean printKitLabels ) throws ServletException,IOException
	{
		return generateContainerLabel( Seqnumbers,personnelId,printKitLabels,"","" );
	}

	public String generateContainerLabel( Vector Seqnumbers,String personnelId,boolean printKitLabels,String printerPath,String printerRes ) throws ServletException,IOException
	{
		String Query_Statement="";
		DBResultSet dbrs=null;
		ResultSet rs=null;
		Hashtable data=new Hashtable();
		Hashtable dataV=new Hashtable();
		Vector dataV1=new Vector();
		Vector comptdataV1=new Vector();
		Hashtable compdata=new Hashtable();
		Hashtable finalcompdata=new Hashtable();
		Vector invengrpvec=new Vector();
		int numberRec=0;

		ACJBarCodeGenerator obj=null;
		obj=new ACJBarCodeGenerator();

		StringBuffer MsgSB=new StringBuffer();

		Random rand=new Random();
		int tmpReq=rand.nextInt();
		Integer tmpReqNum=new Integer( tmpReq );

		String updateprintdate="";

		String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
		String url=wwwHome + "labels/Barcode" + tmpReqNum.toString() + ".pdf";
		String jnlpurl=wwwHome + "labels/Barcode" + tmpReqNum.toString() + ".jnlp";

		if ( Seqnumbers.size() == 0 )
		{
			return "";
		}


		try
		{
			String ConOrBin=this.getconBin();

			for ( int i=0; i < Seqnumbers.size(); i++ )
			{
				String Hub_Value="";
				String Item_Id="";
				String Quantity="";
				String BarcodeRId="";
				String inventorygrp="";
				String certnuminvngrp="";
				String qualitycntitem="";
				String singleitemkit="";
				String certified= "";
				String bin = "";
				String receiptidt=Seqnumbers.elementAt( i ).toString();

				Query_Statement = "select x.*, to_char(x.EXPIRE_DATE_ORIG,'dd-MON-yyyy') BOEING_EXPIRE_DATE, to_char(x.EXPIRE_DATE_ORIG,'mmddyyyy') ITALY_EXPIRE_DATE, lpad(x.RECEIPT_ID,8,'0') RECEIPT_ID1, to_char(x.DATE_OF_RECEIPT,'mm/dd/yyyy') DOR, y.CAT_PART_NO, x.item_desc";
				if ("2106".equals(hubnum))
					Query_Statement += ", DECODE (r.expire_date, TO_DATE ('01/01/3000', 'mm/dd/yyyy'), 'INDEFINITE', TO_CHAR (r.expire_date, 'MM/DD/YYYY')) EXPIRE_DATE1";
				Query_Statement += " from container_label_master_view x, container_label_detail_view y, receipt r where x.ITEM_ID = y.ITEM_ID and x.HUB = y.HUB and x.INVENTORY_GROUP = y.INVENTORY_GROUP and r.RECEIPT_ID = x.RECEIPT_ID and r.INVENTORY_GROUP = x.INVENTORY_GROUP and x.RECEIPT_ID = '" + receiptidt + "'";

				updateprintdate+=receiptidt + ",";
				dbrs=db.doQuery( Query_Statement );
				rs=dbrs.getResultSet();

				data=new Hashtable();
				dataV=new Hashtable();
				numberRec=0;

				try
				{
					while ( rs.next() )
					{
						BarcodeRId=com.tcmis.common.util.BarCodeHandler.Code128c( rs.getString( "RECEIPT_ID1" ) );
						//barclog.info("Barcode 128 C  "+BarcodeRId+   "   RECEIPT_ID  Code "+BarcodeConverter.Bar128C(rs.getString("RECEIPT_ID"))+"");
						data.put( "DETAIL_0",BarcodeRId );
						Item_Id=rs.getString( "ITEM_ID" ) == null ? " " : rs.getString( "ITEM_ID" );
						inventorygrp=rs.getString( "INVENTORY_GROUP" ) == null ? " " : rs.getString( "INVENTORY_GROUP" );
						certnuminvngrp=rs.getString( "CERTIFICATION_NUMBER" ) == null ? " " : rs.getString( "CERTIFICATION_NUMBER" );
						qualitycntitem=rs.getString( "QUALITY_CONTROL_ITEM" ) == null ? " " : rs.getString( "QUALITY_CONTROL_ITEM" );
						singleitemkit=rs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" ) == null ? " " : rs.getString( "MANAGE_KITS_AS_SINGLE_UNIT" );
						certified=rs.getString( "CERTIFIED" ) == null ? " " : rs.getString( "CERTIFIED" );
						bin=rs.getString( "BIN" ) == null ? "" : rs.getString( "BIN" );
						String receiptId = rs.getString( "RECEIPT_ID1" ) == null ? " " : rs.getString( "RECEIPT_ID1" );
						String mfgLot = rs.getString( "MFG_LOT" ) == null ? " " : rs.getString( "MFG_LOT" );
						String italyExpireDate = rs.getString( "ITALY_EXPIRE_DATE" ) == null ? " " : rs.getString( "ITALY_EXPIRE_DATE" );
						String labelStorageTemp = rs.getString( "LABEL_STORAGE_TEMP" ) == null ? " " : rs.getString( "LABEL_STORAGE_TEMP" );
						String expireDate = rs.getString( "EXPIRE_DATE" ) == null ? " " : rs.getString( "EXPIRE_DATE" );
						String maxUseInMonths = rs.getString( "MAX_USE_IN_MONTHS" ) == null ? " " : rs.getString( "MAX_USE_IN_MONTHS" );
						String expireDateLocale = rs.getString( "EXPIRE_DATE_LOCALE" ) == null ? " " : rs.getString( "EXPIRE_DATE_LOCALE" );
						if (("-1").equals(maxUseInMonths)) {
							maxUseInMonths = expireDateLocale;
						}

						data.put("OWNER_COMPANY_ID",rs.getString( "OWNER_COMPANY_ID") == null ? "" : rs.getString( "OWNER_COMPANY_ID"));
						data.put("PO_NUMBER",rs.getString( "PO_NUMBER") == null ? "" : rs.getString( "PO_NUMBER"));
						data.put("LOT_STATUS",rs.getString( "LOT_STATUS") == null ? "" : rs.getString( "LOT_STATUS"));

						data.put( "DETAIL_1",Item_Id );
						data.put( "DETAIL_2",receiptId);
						data.put( "DETAIL_3",mfgLot );
						data.put( "DETAIL_4",rs.getString( "DOR" ) == null ? " " : rs.getString( "DOR" ) );
						data.put( "DETAIL_5",rs.getString( "RECERTS" ) == null ? " " : rs.getString( "RECERTS" ) );
						data.put( "DETAIL_6",rs.getString( "BIN" ) == null ? " " : rs.getString( "BIN" ) );
						data.put( "DETAIL_7",expireDate );
						data.put( "DETAIL_8",labelStorageTemp );
						data.put( "DETAIL_16",singleitemkit );
						data.put( "INVENTORY_GROUP",inventorygrp );
						data.put( "NUMBER_OF_KITS",rs.getString( "NUMBER_OF_KITS" ) == null ? " " : rs.getString( "NUMBER_OF_KITS" ) );
						data.put( "INSEPARABLE_KIT",rs.getString( "INSEPARABLE_KIT" ) == null ? " " : rs.getString( "INSEPARABLE_KIT" ) );
						data.put( "TOTAL_COMPONENTS",rs.getString( "TOTAL_COMPONENTS" ) == null ? "" : rs.getString( "TOTAL_COMPONENTS" ) );
						data.put( "LABEL_COLOR",rs.getString( "LABEL_COLOR" ) == null ? "" : rs.getString( "LABEL_COLOR" ) );
						data.put( "SHELF_LIFE_BASIS",rs.getString( "SHELF_LIFE_BASIS" ) == null ? "" : rs.getString( "SHELF_LIFE_BASIS" ) );
						data.put( "SHELF_LIFE_BASIS_DATE",rs.getString( "SHELF_LIFE_BASIS_DATE" ) == null ? "" : rs.getString( "SHELF_LIFE_BASIS_DATE" ) );
						data.put( "SPEC_DETAIL",rs.getString( "SPEC_DETAIL" ) == null ? "" : rs.getString( "SPEC_DETAIL" ) );
						data.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
						data.put( "FREE_TEXT","" );
						data.put( "MAX_USE_IN_MONTHS",maxUseInMonths);
						data.put( "EXPIRE_DATE_LOCALE",expireDateLocale);
						data.put( "DATE_OF_MANUFACTURE_LOCALE",rs.getString( "DATE_OF_MANUFACTURE_LOCALE" ) == null ? "" : rs.getString( "DATE_OF_MANUFACTURE_LOCALE" ) );
						data.put( "DATE_OF_MANUFACTURE",rs.getString( "DATE_OF_MANUFACTURE" ) == null ? "" : rs.getString( "DATE_OF_MANUFACTURE" ) );
						String italyBarcode = Item_Id+","+receiptId+","+mfgLot+","+italyExpireDate;
						String iai2DBarcode = Item_Id +"|"+(mfgLot.indexOf("|") != -1 || mfgLot.indexOf(",") != -1 ? barCode2dReplace.matcher(mfgLot).replaceAll(" "):mfgLot)+"|"+italyExpireDate;
						data.put( "IAI_2D_BARCODE",iai2DBarcode);

						data.put( "DATE_OF_MANUFACTURE",rs.getString( "DATE_OF_MANUFACTURE" ) == null ? "" : rs.getString( "DATE_OF_MANUFACTURE" ) );
						data.put( "ITALY_BARCODE",italyBarcode);
						data.put( "RECEIPT_ID",receiptId);
						data.put( "MFG_LOT",mfgLot );
						data.put( "BIN",rs.getString( "BIN" ) == null ? " " : rs.getString( "BIN" ) );
						data.put( "USE_WITH","" );
						if ("2106".equals(hubnum))
							data.put("EXPIRE_DATE", rs.getString( "EXPIRE_DATE1" ) == null ? " " : rs.getString( "EXPIRE_DATE1" ));
						else
							data.put( "EXPIRE_DATE",expireDate );
						data.put( "ITEM_COMPONENT","");
						data.put( "RECEIVER_DATE_OF_RECEIPT",rs.getString( "RECEIVER_DATE_OF_RECEIPT" ) == null ? "" : rs.getString( "RECEIVER_DATE_OF_RECEIPT" ) );
						data.put( "SHELF_LIFE_STORAGE_TEMP",rs.getString( "SHELF_LIFE_STORAGE_TEMP" ) == null ? "" : rs.getString( "SHELF_LIFE_STORAGE_TEMP" ) );
						data.put( "CATALOG_ITEM_DESCRIPTION",rs.getString( "CATALOG_ITEM_DESCRIPTION" ) == null ? "" : rs.getString( "CATALOG_ITEM_DESCRIPTION" ) );
						data.put( "CUSTOMER_RECEIPT_ID",rs.getString( "CUSTOMER_RECEIPT_ID" ) == null ? "" : rs.getString( "CUSTOMER_RECEIPT_ID" ) );
						data.put( "TOTAL_RECERTS_ALLOWED",rs.getString( "TOTAL_RECERTS_ALLOWED" ) == null ? "" : rs.getString( "TOTAL_RECERTS_ALLOWED" ) );
						data.put( "LAST_ID_PRINTED",rs.getString( "LAST_ID_PRINTED" ) == null ? "" : rs.getString( "LAST_ID_PRINTED" ) );
						data.put( "SPEC_LIST",rs.getString( "SPEC_LIST" ) == null ? "" : rs.getString( "SPEC_LIST" ) );
						data.put( "COMPANY_MSDS_LIST",rs.getString( "COMPANY_MSDS_LIST" ) == null ? "" : rs.getString( "COMPANY_MSDS_LIST" ) );
						data.put( "RECERT_NUMBER",rs.getString( "RECERT_NUMBER" ) == null ? "" : rs.getString( "RECERT_NUMBER" ) );
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
                        data.put( "DATE_OF_RECEIPT",rs.getString( "DOR" ) == null ? " " : rs.getString( "DOR" ));
                        data.put( "CAT_PART_NO",rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ));
                        data.put( "ITEM_DESC",rs.getString( "ITEM_DESC" ) == null ? "" : rs.getString( "ITEM_DESC" ));
                        
						if (labelStorageTemp !=null && labelStorageTemp.trim().length() >0)
						{
							labelStorageTemp = labelStorageTemp.substring(2,labelStorageTemp.length());
						}
						data.put( "LABEL_STORAGE_TEMP",labelStorageTemp);

						String dpmnumber=rs.getString( "QC_DOC" ) == null ? " " : rs.getString( "QC_DOC" );
						if ( dpmnumber.trim().length() == 0 )
						{
							data.put( "DETAIL_14","Not For Production" );
							data.put( "QC_DOC","Not For Production" );
						}
						else
						{
							data.put( "DETAIL_14","" + dpmnumber + "" );
							data.put( "QC_DOC","" + dpmnumber + "" );
						}
						data.put( "DETAIL_15",rs.getString( "LABEL_STORAGE_TEMP" ) == null ? " " : rs.getString( "LABEL_STORAGE_TEMP" ) );

						Hub_Value=rs.getString( "HUB" ) == null ? "" : rs.getString( "HUB" );
						Quantity=rs.getString( "QUANTITY_RECEIVED" ) == null ? "" : rs.getString( "QUANTITY_RECEIVED" ); ;
						if ( !invengrpvec.contains( inventorygrp ) )
						{
							invengrpvec.add( inventorygrp );
						}

						//Rcpt ID + | + Part # + | + Lot # + | + Exp Date (DD-MMM-YY) + | + Pad with zeros to total 81 chars
						String boeing2dBarcode="";
						boeing2dBarcode += com.tcmis.common.util.StringHandler.padCharacter(receiptId,new Integer("8"),"0","left");
						if (dpmnumber.trim().length() == 0)
						{
							boeing2dBarcode += "|N/A";
						}
						else
						{
							boeing2dBarcode += "|" +(dpmnumber.indexOf("|") != -1 || dpmnumber.indexOf(",") != -1 ? barCode2dReplace.matcher(dpmnumber).replaceAll(" "):dpmnumber);
						}
						boeing2dBarcode += "|" +(mfgLot.indexOf("|") != -1 || mfgLot.indexOf(",") != -1 ? barCode2dReplace.matcher(mfgLot).replaceAll(" "):mfgLot);
						String boeingExpireDate = rs.getString( "BOEING_EXPIRE_DATE" ) == null ? " " : rs.getString( "BOEING_EXPIRE_DATE" );
						data.put( "BOEING_EXPIRE_DATE",rs.getString( "BOEING_EXPIRE_DATE" ) == null ? " " : rs.getString( "BOEING_EXPIRE_DATE" ) );

						if (expireDate.equalsIgnoreCase("N/A"))
						{
							boeing2dBarcode += "|N/A|";
						}
						else
						{
							boeing2dBarcode += "|" +boeingExpireDate + "|";
						}
						boeing2dBarcode = com.tcmis.common.util.StringHandler.padCharacter(boeing2dBarcode,new Integer("81"),"0","");
						//System.out.println("boeing2dBarcode:  " +boeing2dBarcode);
						data.put( "BOEING_2D_BARCODE",boeing2dBarcode);

					}
				}
				catch ( Exception e )
				{
					e.printStackTrace();
					return "";
				}
				finally
				{
					if ( dbrs != null )
					{
						dbrs.close();
					}
				}

				if ( inventorygrp.length() > 0 )
				{
					Query_Statement="select * from (Select distinct CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME," +
					"max(OPEN_MR_QTY) OPEN_MR_QTY,max(STOCKING_LEVEL) STOCKING_LEVEL,spec_id,SPEC_FIELDNAME," +
					"UNIT_OF_SALE,substr(PACKAGING,1,200) PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY from container_label_detail_view where ITEM_ID = " + Item_Id +
					" and HUB = " + Hub_Value +	" and INVENTORY_GROUP = '" + inventorygrp + "' group by CUSTOMER_PART_REVISION,LABEL_SPEC," +
					"CAT_PART_NO,PART_SHORT_NAME,spec_id,SPEC_FIELDNAME,UNIT_OF_SALE,PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY order by 4 desc,5 desc, 12 desc) a where rownum < 8";
				}
				else
				{
					Query_Statement="select * from (Select distinct CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME," +
					"max(OPEN_MR_QTY) OPEN_MR_QTY,max(STOCKING_LEVEL) STOCKING_LEVEL,spec_id," +
					"SPEC_FIELDNAME,UNIT_OF_SALE,substr(PACKAGING,1,200) PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY from container_label_detail_view where ITEM_ID = " + Item_Id +
					" and HUB = " + Hub_Value + " group by CUSTOMER_PART_REVISION,LABEL_SPEC,CAT_PART_NO,PART_SHORT_NAME,spec_id,SPEC_FIELDNAME,UNIT_OF_SALE,PACKAGING,KIT_MSDS_NUMBER,MIN_RT_OUT_TIME,MIN_STORAGE_TEMP_DISPLAY,TIME_TEMP_SENSITIVE,PART_DESCRIPTION,SPEC_DISPLAY " +
					"order by 4 desc,5 desc, 12 desc) a where rownum < 8";
				}

				dbrs=db.doQuery( Query_Statement );
				rs=dbrs.getResultSet();

				data.put( "DETAIL_9","" );
				data.put( "DETAIL_10","" );
				data.put( "DETAIL_11","" );
				data.put( "DETAIL_12","" );
				data.put( "DETAIL_13","" );
				data.put( "DETAIL_17","" );
				data.put( "DETAIL_18","" );
				data.put( "PART_SHORT_NAME","" );
				data.put( "LABEL_SPEC","" );
				data.put( "SPEC_ID","" );
				data.put( "SPEC_FIELDNAME","" );
				data.put( "UNIT_OF_SALE","" );
				data.put( "PACKAGING","" );
				data.put( "KIT_MSDS_NUMBER","" );
				data.put( "MIN_RT_OUT_TIME","" );
				data.put( "MIN_STORAGE_TEMP_DISPLAY","" );
				data.put( "TIME_TEMP_SENSITIVE","" );
				data.put( "PART_DESCRIPTION","" );
				data.put( "SPEC_DISPLAY","" );
                data.put( "CUSTOMER_PART_REVISION","" );

                String partList = "";
				int partCount = 0;

				try
				{
					while ( rs.next() )
					{
						String currentPartNumber = rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" );
						if (currentPartNumber.trim().length() > 0)
						{
							partList += currentPartNumber;
							if (partCount > 0) {
								partList += ",";
							}

							partCount++;
						}

						if ( numberRec == 0 )
						{
							data.remove( "DETAIL_9" );
							data.put( "DETAIL_9",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
							data.remove( "PART_SHORT_NAME" );
							data.put( "PART_SHORT_NAME",rs.getString( "PART_SHORT_NAME" ) == null ? " " : rs.getString( "PART_SHORT_NAME" ) );
							data.remove( "LABEL_SPEC" );
							data.put( "LABEL_SPEC",rs.getString( "LABEL_SPEC" ) == null ? " " : rs.getString( "LABEL_SPEC" ) );
							data.remove( "SPEC_ID" );
							data.put( "SPEC_ID",rs.getString( "SPEC_ID" ) == null ? " " : rs.getString( "SPEC_ID" ) );
							data.remove( "SPEC_FIELDNAME" );
							data.put( "SPEC_FIELDNAME",rs.getString( "SPEC_FIELDNAME" ) == null ? " " : rs.getString( "SPEC_FIELDNAME" ) );
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

							String iai2DBarcode=data.get( "IAI_2D_BARCODE" ) == null ? "" : data.get( "IAI_2D_BARCODE" ).toString();
							iai2DBarcode +=  "|" +currentPartNumber;
							data.remove( "IAI_2D_BARCODE" );
							data.put( "IAI_2D_BARCODE",iai2DBarcode);
                            data.remove( "CUSTOMER_PART_REVISION" );
                            data.put( "CUSTOMER_PART_REVISION",rs.getString( "CUSTOMER_PART_REVISION" ) == null ? " " : rs.getString( "CUSTOMER_PART_REVISION" ) );
                        }
						else if ( numberRec == 1 )
						{
							data.remove( "DETAIL_10" );
							data.put( "DETAIL_10",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
							data.remove( "LABEL_SPEC2" );
							data.put( "LABEL_SPEC2",rs.getString( "LABEL_SPEC" ) == null ? " " : rs.getString( "LABEL_SPEC" ) );
						}
						else if ( numberRec == 2 )
						{
							data.remove( "DETAIL_11" );
							data.put( "DETAIL_11",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
						}
						else if ( numberRec == 3 )
						{
							data.remove( "DETAIL_12" );
							data.put( "DETAIL_12",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
						}
						else if ( numberRec == 4 )
						{
							data.remove( "DETAIL_13" );
							data.put( "DETAIL_13",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
						}
						else if ( numberRec == 5 )
						{
							data.remove( "DETAIL_17" );
							data.put( "DETAIL_17",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
						}
						else if ( numberRec == 6 )
						{
							data.remove( "DETAIL_18" );
							data.put( "DETAIL_18",rs.getString( "CAT_PART_NO" ) == null ? " " : rs.getString( "CAT_PART_NO" ) );
						}

						numberRec+=1;
					}
				}
				catch ( Exception e )
				{
					e.printStackTrace();
					return "";
				}
				finally
				{
					if ( dbrs != null )
					{
						dbrs.close();
					}
				}

				data.put( "PART_LIST",partList);

				if ( "Y".equalsIgnoreCase( qualitycntitem ) && certnuminvngrp.trim().length() > 0 )
				{
					data.remove( "DETAIL_13" );
					data.put( "DETAIL_13","Accepted By: " + certnuminvngrp + "" );
				}

				if ( !"Virtual Hub Supplier".equalsIgnoreCase(bin) && "Y".equalsIgnoreCase( qualitycntitem ) && "Y".equalsIgnoreCase( certified ) && certnuminvngrp.trim().length() == 0 )
				{
					barclog.info("No Certification Number for Receipt ID "+receiptidt+"");
					return "";
				}

				Query_Statement="Select distinct HMIS_HEALTH, HMIS_FLAMMABILITY, HMIS_REACTIVITY, SPECIFIC_HAZARD, HEALTH, FLAMMABILITY, REACTIVITY, PERSONAL_PROTECTION, MFG_DESC, TRADE_NAME, RECERT_NUMBER,LABEL_STORAGE_TEMP,lpad(RECEIPT_ID,8,'0') RECEIPT_ID1,RECEIPT_ID, USE_WITH,ITEM_COMPONENT_QUANTITY,ITEM_ID,ITEM_COMPONENT,MFG_LOT, BIN, EXPIRE_DATE, QC_DOC,LABEL_COLOR, SHELF_LIFE_BASIS, SHELF_LIFE_BASIS_DATE, SPEC_DETAIL,DATE_OF_REPACKAGING  from CONTAINER_LABEL_COMPONENT_VIEW where RECEIPT_ID = " + receiptidt + "";
				dbrs=db.doQuery( Query_Statement );
				rs=dbrs.getResultSet();
				comptdataV1=new Vector();
				String keyreceiptid="";
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
						if ( "N".equalsIgnoreCase( singleitemkit ) )
						{
							compdata=new Hashtable();
							compdata.put( "DETAIL_0",rs.getString( "RECEIPT_ID1" ) == null ? " " : rs.getString( "RECEIPT_ID1" ) );
							compdata.put( "RECEIPT_ID",rs.getString( "RECEIPT_ID1" ) == null ? " " : rs.getString( "RECEIPT_ID1" ) );
							String dpmnumber=rs.getString( "QC_DOC" ) == null ? " " : rs.getString( "QC_DOC" );
							if ( dpmnumber.trim().length() == 0 )
							{
								compdata.put( "DETAIL_1","Not For Production" );
								compdata.put( "QC_DOC","Not For Production" );
							}
							else
							{
								compdata.put( "DETAIL_1","" + dpmnumber + "" );
								compdata.put( "QC_DOC","" + dpmnumber + "" );
							}

							keyreceiptid=rs.getString( "RECEIPT_ID1" ) == null ? " " : rs.getString( "RECEIPT_ID1" );

							compdata.put( "DETAIL_2",rs.getString( "MFG_LOT" ) == null ? " " : rs.getString( "MFG_LOT" ) );
							compdata.put( "DETAIL_3",rs.getString( "BIN" ) == null ? " " : rs.getString( "BIN" ) );
							compdata.put( "DETAIL_4",rs.getString( "LABEL_STORAGE_TEMP" ) == null ? " " : rs.getString( "LABEL_STORAGE_TEMP" ) );
							compdata.put( "DETAIL_5",rs.getString( "EXPIRE_DATE" ) == null ? " " : rs.getString( "EXPIRE_DATE" ) );
							compdata.put( "DETAIL_6",rs.getString( "ITEM_COMPONENT" ) == null ? " " : rs.getString( "ITEM_COMPONENT" ) );
							String usewith="Use ";
							usewith+=rs.getString( "ITEM_COMPONENT_QUANTITY" ) == null ? " " : rs.getString( "ITEM_COMPONENT_QUANTITY" );
							usewith+=" With: ";
							usewith+=rs.getString( "USE_WITH" ) == null ? " " : rs.getString( "USE_WITH" );
							compdata.put( "DETAIL_7",usewith );
							compdata.put( "DETAIL_8",rs.getString( "LABEL_STORAGE_TEMP" ) == null ? " " : rs.getString( "LABEL_STORAGE_TEMP" ) );
							compdata.put( "ITEM_COMPONENT_QUANTITY",rs.getString( "ITEM_COMPONENT_QUANTITY" ) == null ? " " : rs.getString( "ITEM_COMPONENT_QUANTITY" ) );
							compdata.put( "LABEL_COLOR",rs.getString( "LABEL_COLOR" ) == null ? "" : rs.getString( "LABEL_COLOR" ) );
							compdata.put( "SHELF_LIFE_BASIS",rs.getString( "SHELF_LIFE_BASIS" ) == null ? "" : rs.getString( "SHELF_LIFE_BASIS" ) );
							compdata.put( "SHELF_LIFE_BASIS_DATE",rs.getString( "SHELF_LIFE_BASIS_DATE" ) == null ? "" : rs.getString( "SHELF_LIFE_BASIS_DATE" ) );
							compdata.put( "SPEC_DETAIL",rs.getString( "SPEC_DETAIL" ) == null ? "" : rs.getString( "SPEC_DETAIL" ) );
							compdata.put( "ITEM_ID",rs.getString( "ITEM_ID" ) == null ? "" : rs.getString( "ITEM_ID" ) );
							compdata.put( "RECERT_NUMBER",rs.getString( "RECERT_NUMBER" ) == null ? "" : rs.getString( "RECERT_NUMBER" ) );
							compdata.put( "FREE_TEXT","" );
							compdata.put( "BIN",rs.getString( "BIN" ) == null ? " " : rs.getString( "BIN" ) );
							compdata.put( "MFG_LOT",rs.getString( "MFG_LOT" ) == null ? " " : rs.getString( "MFG_LOT" ) );
							compdata.put( "USE_WITH",usewith );
							compdata.put( "EXPIRE_DATE",rs.getString( "EXPIRE_DATE" ) == null ? " " : rs.getString( "EXPIRE_DATE" ) );
							compdata.put( "ITEM_COMPONENT",rs.getString( "ITEM_COMPONENT" ) == null ? " " : rs.getString( "ITEM_COMPONENT" ) );

							compdata.put( "HMIS_HEALTH",rs.getString( "HMIS_HEALTH" ) == null ? " " : rs.getString( "HMIS_HEALTH" ) );
							compdata.put( "HMIS_FLAMMABILITY",rs.getString( "HMIS_FLAMMABILITY" ) == null ? " " : rs.getString( "HMIS_FLAMMABILITY" ) );
							compdata.put( "HMIS_REACTIVITY",rs.getString( "HMIS_REACTIVITY" ) == null ? " " : rs.getString( "HMIS_REACTIVITY" ) );
							compdata.put( "SPECIFIC_HAZARD",rs.getString( "SPECIFIC_HAZARD" ) == null ? " " : rs.getString( "SPECIFIC_HAZARD" ) );
							compdata.put( "HEALTH",rs.getString( "HEALTH" ) == null ? " " : rs.getString( "HEALTH" ) );
							compdata.put( "FLAMMABILITY",rs.getString( "FLAMMABILITY" ) == null ? " " : rs.getString( "FLAMMABILITY" ) );
							compdata.put( "REACTIVITY",rs.getString( "REACTIVITY" ) == null ? " " : rs.getString( "REACTIVITY" ) );
							compdata.put( "PERSONAL_PROTECTION",rs.getString( "PERSONAL_PROTECTION" ) == null ? " " : rs.getString( "PERSONAL_PROTECTION" ) );
							compdata.put( "MFG_DESC",rs.getString( "MFG_DESC" ) == null ? " " : rs.getString( "MFG_DESC" ) );
							compdata.put( "TRADE_NAME",rs.getString( "TRADE_NAME" ) == null ? " " : rs.getString( "TRADE_NAME" ) );
							compdata.put( "DATE_OF_REPACKAGING",rs.getString( "DATE_OF_REPACKAGING" ) == null ? " " : rs.getString( "DATE_OF_REPACKAGING" ) );


							compdata.put( "PART_1",""+data.get("DETAIL_9").toString()+"" );
							compdata.put( "PART_2",""+data.get("DETAIL_10").toString()+"" );
							compdata.put( "PART_3",""+data.get("DETAIL_11").toString()+"" );
							compdata.put( "PART_4",""+data.get("DETAIL_12").toString()+"" );
							compdata.put( "PART_5",""+data.get("DETAIL_17").toString()+"" );
							compdata.put( "PART_6",""+data.get("DETAIL_18").toString()+"" );
							compdata.put( "PART_7",""+data.get("DETAIL_13").toString()+"" );
							compdata.put( "CATALOG_ITEM_DESCRIPTION",""+data.get("CATALOG_ITEM_DESCRIPTION").toString()+"" );
							compdata.put( "COMPANY_MSDS_LIST",""+data.get("COMPANY_MSDS_LIST").toString()+"" );
							compdata.put( "CUSTOMER_RECEIPT_ID",""+data.get("CUSTOMER_RECEIPT_ID").toString()+"" );
							compdata.put( "KIT_MSDS_NUMBER",""+data.get("KIT_MSDS_NUMBER").toString()+"" );
							compdata.put( "MIN_RT_OUT_TIME",""+data.get("MIN_RT_OUT_TIME").toString()+"" );
							compdata.put( "MIN_STORAGE_TEMP_DISPLAY",""+data.get("MIN_STORAGE_TEMP_DISPLAY").toString()+"" );
							compdata.put( "PART_DESCRIPTION",""+data.get("PART_DESCRIPTION").toString()+"" );
							compdata.put( "TIME_TEMP_SENSITIVE",""+data.get("TIME_TEMP_SENSITIVE").toString()+"" );
							compdata.put( "EXPIRE_DATE_LOCALE",""+data.get("EXPIRE_DATE_LOCALE").toString()+"" );
							


							if ( "Y".equalsIgnoreCase( qualitycntitem ) && certnuminvngrp.trim().length() > 0 )
							{
								compdata.put( "DETAIL_9","Accepted By: " + certnuminvngrp + "" );
							}
							else
							{
								compdata.put( "DETAIL_9","" );
							}

							compdata.put( "DETAIL_10","" );
							compdata.put( "DETAIL_11","" );
							compdata.put( "DETAIL_12","" );
							compdata.put( "DETAIL_13","" );

							if ( !"Virtual Hub Supplier".equalsIgnoreCase(bin) && "Y".equalsIgnoreCase( qualitycntitem ) && certnuminvngrp.trim().length() == 0 )
							{
								barclog.info("No Certification Number for Receipt ID "+receiptidt+"");
								return "";
							}

							comptdataV1.addElement( compdata );
						}
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
				if (!comptdataV1.isEmpty()) {
					finalcompdata.put( keyreceiptid,comptdataV1 );
				}

				if ( ConOrBin.equalsIgnoreCase( "BIN" ) )
				{
					dataV.put( "QUANTITY_RECEIVED","1" );
				}
				else if ( UserDefinedQty )
				{
					dataV.put( "QUANTITY_RECEIVED",QuantityNos.elementAt( i ).toString() );
				}
				else
				{
					dataV.put( "QUANTITY_RECEIVED",Quantity );
				}
				dataV.put( "DATA",data );
				dataV1.addElement( dataV );

			} //End of For
			updateprintdate=updateprintdate.substring( 0, ( updateprintdate.length() - 1 ) );
			String printupquery="update receipt set PICKABLE='Y',LAST_LABEL_PRINT_DATE = SYSDATE where receipt_id in (" + updateprintdate + ") ";
			db.doUpdate( printupquery );

		} //End Try
		catch ( Exception e )
		{
			e.printStackTrace();
			return "";
		}

		try
		{
			String iszplprinter="select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " + personnelId + " and x.LABEL_STOCK = '" + PaperSize + "'";
			//String printerPath="";
			//String printerRes="";

			if ( printerPath.length() == 0 )
			{
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
					if ( dbrs != null ){dbrs.close();}
				}
			}

			if ( printerPath.trim().length() > 0 )
				//if ( !"811".equalsIgnoreCase( PaperSize ) && ("2118".equalsIgnoreCase(this.hubnum.trim()) || "35".equalsIgnoreCase(PaperSize))  )
			{
				ZPLBarCodeGenerator zplobj=new ZPLBarCodeGenerator( db );
				zplobj.buildReceiptZpl( dataV1,tmpReqNum.toString(),PaperSize,this.hubnum.trim(),finalcompdata,personnelId,printerPath,printerRes,invengrpvec,printKitLabels);
				url=jnlpurl;
			}
			else
			{
				Vector dumpcompdata=new Vector();
				obj.buildTest( dataV1,tmpReqNum.toString(),PaperSize,this.hubnum.trim(),dumpcompdata );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return "";
		}

		data=null;
		dataV=null;
		dataV1=null;
		comptdataV1=null;
		compdata=null;
		finalcompdata=null;
		invengrpvec=null;

		return url;
	}

	public String getServletInfo()
	{
		return "radian.web.servlets.SearchMsds Information";
	}
}

