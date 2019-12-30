package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.barcode.BolgenericGenerator;
import java.text.DecimalFormat;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *09-11-03 - restored to the onld version of it.
 *10-29-03 - Added  NET_WEIGHT to the hastable
 */

public class reprintbols
{
	private ServerResourceBundle bundle = null;
	private TcmISDBModel db = null;
	private PrintWriter out = null;

	public reprintbols(ServerResourceBundle b, TcmISDBModel d)
	{
		bundle = b;
		db = d;
	}
	 public void doResult(HttpServletRequest request, HttpServletResponse response,HttpSession genbolsession)  throws ServletException,  IOException
	{
	  out=response.getWriter();
	  response.setContentType( "text/html" );
	  //StringBuffer Msgn=new StringBuffer();
	  String url = "";

	  Hashtable hub_list_set= new Hashtable();
	  String donevvstuff= genbolsession.getAttribute( "GENERICBOLSTUFF" ) == null ? "" : genbolsession.getAttribute( "GENERICBOLSTUFF" ).toString();
	  if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
	  {
		hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db );
		genbolsession.setAttribute("GENBOLHUB",hub_list_set);
		genbolsession.setAttribute("GENERICBOLSTUFF","Yes");
	  }
	  else
	  {
		hub_list_set = (Hashtable) genbolsession.getAttribute( "GENBOLHUB" );
	  }

	  String pullinven=request.getParameter( "pullinven" );
	  if ( pullinven == null )
		pullinven="";

	  String branch_plant=request.getParameter( "HubName" );
	  if ( branch_plant == null )
		branch_plant = "";

	  String User_Action=null;
	  User_Action=request.getParameter( "UserAction" );
	  if ( User_Action == null )
		User_Action="New";

	  String PaperSize=request.getParameter( "paperSize" );
	  if ( PaperSize == null )
		PaperSize="31";

	  String item_desc=request.getParameter( "item_desc" );
	  if ( item_desc == null )
		item_desc="";

	  String picklist_id=request.getParameter( "picklist_id" );
	  if ( picklist_id == null )
		picklist_id="";

	  String total_qty=request.getParameter( "total_qty" );
	  if ( total_qty == null )
		total_qty="";

	  String quantity_shipped=request.getParameter( "quantity_shipped" );
	  if ( quantity_shipped == null )
		quantity_shipped="";

	  /*String packaging=request.getParameter( "packaging" );
	  if ( packaging == null )
		packaging="";*/

	  /*String hazardnous=request.getParameter( "hazardous" );
	  if ( hazardous == null )
		hazardous="";*/

	  String dot=request.getParameter( "dot" );
	  if ( dot == null )
		dot="";

	  String erg=request.getParameter( "erg" );
	  if ( erg == null )
		erg="";

	  String item_idm=request.getParameter( "item_id" );
	  if ( item_idm == null )
		item_idm="";

	  String mfg_lot=request.getParameter( "mfg_lot" );
	  if ( mfg_lot == null )
		mfg_lot="";

	  String date_of_receipt=request.getParameter( "date_of_receipt" );
	  if ( date_of_receipt == null )
		date_of_receipt="";

	  String radian_po=request.getParameter( "radian_po" );
	  if ( radian_po == null )
		radian_po="";

	  String expire_date=request.getParameter( "expire_date" );
	  if ( expire_date == null )
		expire_date="";

	  String pr_number=request.getParameter( "pr_number" );
	  if ( pr_number == null )
		pr_number="";

	  String line_item=request.getParameter( "line_item" );
	  if ( line_item == null )
		line_item="";

	  String mr_line=request.getParameter( "mr_line" );
	  if ( mr_line == null )
		mr_line="";

	  String receipt_id=request.getParameter( "receipt_id" );
	  if ( receipt_id == null )
		receipt_id="";

	  String sales_order=request.getParameter( "sales_order" );
	  if ( sales_order == null )
		sales_order="";

	  String shipper_id=request.getParameter( "shipper_id" );
	  if ( shipper_id == null )
		shipper_id="";

	  String carrier=request.getParameter( "carrier" );
	  if ( carrier == null )
		carrier="";

	  String carrier_no=request.getParameter( "carrier_no" );
	  if ( carrier_no == null )
		carrier_no="";

	  String required_datetime=request.getParameter( "required_datetime" );
	  if ( required_datetime == null )
		required_datetime="";

	  String ship_date=request.getParameter( "ship_date" );
	  if ( ship_date == null )
		ship_date="";

	  String source_hub=request.getParameter( "source_hub" );
	  if ( source_hub == null )
		source_hub="";

	  String end_user=request.getParameter( "end_user" );
	  if ( end_user == null )
		end_user="";

	  String department=request.getParameter( "department" );
	  if ( department == null )
		department="";

	  String application=request.getParameter( "application" );
	  if ( application == null )
		application="";

	  String application_desc=request.getParameter( "application_desc" );
	  if ( application_desc == null )
		application_desc="";

	  String delivery_point=request.getParameter( "delivery_point" );
	  if ( delivery_point == null )
		delivery_point="";

	  String delivery_point_desc=request.getParameter( "delivery_point_desc" );
	  if ( delivery_point_desc == null )
		delivery_point_desc="";

	  String batch=request.getParameter( "batch" );
	  if ( batch == null )
		batch="";

	  String batch_id=request.getParameter( "batch_id" );
	  if ( batch_id == null )
		batch_id="";

	  String ship_to_location_id=request.getParameter( "ship_to_location_id" );
	  if ( ship_to_location_id == null )
		ship_to_location_id="";

	  String hub_location_id=request.getParameter( "hub_location_id" );
	  if ( hub_location_id == null )
		hub_location_id="";

	  String ship_to_country_abbrev=request.getParameter( "ship_to_country_abbrev" );
	  if ( ship_to_country_abbrev == null )
		ship_to_country_abbrev="";

	  String ship_to_state_abbrev=request.getParameter( "ship_to_state_abbrev" );
	  if ( ship_to_state_abbrev == null )
		ship_to_state_abbrev="";

	  String ship_to_address_line_1=request.getParameter( "ship_to_address_line_1" );
	  if ( ship_to_address_line_1 == null )
		ship_to_address_line_1="";

	  String ship_to_address_line_2=request.getParameter( "ship_to_address_line_2" );
	  if ( ship_to_address_line_2 == null )
		ship_to_address_line_2="";

	  String ship_to_address_line_3=request.getParameter( "ship_to_address_line_3" );
	  if ( ship_to_address_line_3 == null )
		ship_to_address_line_3="";

	  String ship_to_city=request.getParameter( "ship_to_city" );
	  if ( ship_to_city == null )
		ship_to_city="";

	  String ship_to_zip=request.getParameter( "ship_to_zip" );
	  if ( ship_to_zip == null )
		ship_to_zip="";

	  String ship_to_location_desc=request.getParameter( "ship_to_location_desc" );
	  if ( ship_to_location_desc == null )
		ship_to_location_desc="";

	  String hub_country_abbrev=request.getParameter( "hub_country_abbrev" );
	  if ( hub_country_abbrev == null )
		hub_country_abbrev="";

	  String hub_state_abbrev=request.getParameter( "hub_state_abbrev" );
	  if ( hub_state_abbrev == null )
		hub_state_abbrev="";

	  String hub_address_line_1=request.getParameter( "hub_address_line_1" );
	  if ( hub_address_line_1 == null )
		hub_address_line_1="";

	  String hub_address_line_2=request.getParameter( "hub_address_line_2" );
	  if ( hub_address_line_2 == null )
		hub_address_line_2="";

	  String hub_address_line_3=request.getParameter( "hub_address_line_3" );
	  if ( hub_address_line_3 == null )
		hub_address_line_3="";

	  String hub_city=request.getParameter( "hub_city" );
	  if ( hub_city == null )
		hub_city="";

	  String hub_zip=request.getParameter( "hub_zip" );
	  if ( hub_zip == null )
		hub_zip="";

	  String hub_location_desc=request.getParameter( "hub_location_desc" );
	  if ( hub_location_desc == null )
		hub_location_desc="";

	  String po_number=request.getParameter( "po_number" );
	  if ( po_number == null )
		po_number="";

	  String po_release_number=request.getParameter( "po_release_number" );
	  if ( po_release_number == null )
		po_release_number="";

	  String cat_part_no=request.getParameter( "cat_part_no" );
	  if ( cat_part_no == null )
		cat_part_no="";

	  String catalog_id=request.getParameter( "catalog_id" );
	  if ( catalog_id == null )
		catalog_id="";

	  Vector resultF=new Vector();
	  Hashtable boldata=new Hashtable();

	  for (int rem=0; rem < 25; rem++)
	  {
		boldata=new Hashtable();

		boldata.put( "PICKLIST_ID",picklist_id );
		boldata.put( "QUANTITY_SHIPPED",quantity_shipped );
		boldata.put( "PACKAGING","" );
		boldata.put( "ERG",erg );
		boldata.put( "MFG_LOT",mfg_lot );
		boldata.put( "DATE_OF_RECEIPT",date_of_receipt );
		boldata.put( "RADIAN_PO",radian_po );
		boldata.put( "EXPIRE_DATE",expire_date );
		boldata.put( "PR_NUMBER",pr_number );
		boldata.put( "LINE_ITEM",line_item );
		boldata.put( "MR_LINE",mr_line );
		boldata.put( "RECEIPT_ID",receipt_id );
		boldata.put( "SALES_ORDER",sales_order );
		boldata.put( "SHIPPER_ID",shipper_id );
		boldata.put( "CARRIER",carrier );
		boldata.put( "CARRIER_NO",carrier_no );
		boldata.put( "REQUIRED_DATETIME",required_datetime );
		boldata.put( "SHIP_DATE",ship_date );
		boldata.put( "SOURCE_HUB",source_hub );
		boldata.put( "END_USER",end_user );
		boldata.put( "DEPARTMENT",department );
		boldata.put( "APPLICATION",application );
		boldata.put( "APPLICATION_DESC",application_desc );
		boldata.put( "DELIVERY_POINT",delivery_point );
		boldata.put( "DELIVERY_POINT_DESC",delivery_point_desc );
		boldata.put( "BATCH",batch );
		boldata.put( "BATCH_ID",batch_id );
		boldata.put( "SHIP_TO_LOCATION_ID",ship_to_location_id );
		boldata.put( "HUB_LOCATION_ID",hub_location_id );
		boldata.put( "SHIP_TO_COUNTRY_ABBREV",ship_to_country_abbrev );
		boldata.put( "SHIP_TO_STATE_ABBREV",ship_to_state_abbrev );
		boldata.put( "SHIP_TO_ADDRESS_LINE_1",ship_to_address_line_1 );
		boldata.put( "SHIP_TO_ADDRESS_LINE_2",ship_to_address_line_2 );
		boldata.put( "SHIP_TO_ADDRESS_LINE_3",ship_to_address_line_3 );
		boldata.put( "SHIP_TO_CITY",ship_to_city );
		boldata.put( "SHIP_TO_ZIP",ship_to_zip );
		boldata.put( "SHIP_TO_LOCATION_DESC",ship_to_location_desc );
		boldata.put( "HUB_COUNTRY_ABBREV",hub_country_abbrev );
		boldata.put( "HUB_STATE_ABBREV",hub_state_abbrev );
		boldata.put( "HUB_ADDRESS_LINE_1",hub_address_line_1 );
		boldata.put( "HUB_ADDRESS_LINE_2",hub_address_line_2 );
		boldata.put( "HUB_ADDRESS_LINE_3",hub_address_line_3 );
		boldata.put( "HUB_CITY",hub_city );
		boldata.put( "HUB_ZIP",hub_zip );
		boldata.put( "HUB_LOCATION_DESC",hub_location_desc );
		boldata.put( "PO_NUMBER",po_number );
		boldata.put( "PO_RELEASE_NUMBER",po_release_number );
		boldata.put( "CAT_PART_NO",cat_part_no );
		boldata.put( "CATALOG_ID",catalog_id );
		boldata.put( "ITEM_DESC",item_desc );
		boldata.put( "NET_WEIGHT","" );
		boldata.put( "CHARGE_NUMBER","" );
    boldata.put( "OCONUS","" );
    boldata.put( "BILL_TO_ADDRESS_1","" );
    boldata.put( "BILL_TO_ADDRESS_2","" );
    boldata.put( "BILL_TO_ADDRESS_3","" );
    boldata.put( "BILL_TO_ADDRESS_4","" );
    boldata.put( "TRACKING_NUMBER","" );
    boldata.put( "FREIGHT_ORDER_NUMBER","" );
    boldata.put( "TRACKING_NUMBER_BARCODE","" );
    boldata.put( "SHIP_TO_ADDRESS","" );
    boldata.put( "INVENTORY_GROUP","" );
    boldata.put( "NUMBER_OF_BOXES", "" );

    item_idm=request.getParameter( "item_id" + rem + "" );
		if ( item_idm == null )
		  item_idm="";
		boldata.put( "ITEM_ID",item_idm );

		String rqvalue=request.getParameter( "rqvalue" + rem + "" );
		if ( rqvalue == null )
		  rqvalue="";
		boldata.put( "RQ_VALUE",rqvalue );

		String hazardous=request.getParameter( "hazardous" + rem + "" );
		if ( hazardous == null )
		  hazardous="";
		if ("Y".equalsIgnoreCase(hazardous)) { hazardous = "X";}

		boldata.put( "HAZARDOUS",hazardous );

		total_qty=request.getParameter( "total_qty" + rem + "" );
		if ( total_qty == null )
		  total_qty="";
		boldata.put( "TOTAL_QTY",total_qty );

		dot=request.getParameter( "dot" + rem + "" );
		if ( dot == null )
		 dot="";
		boldata.put( "DOT",dot );

		resultF.add(boldata);
	  }

	  out.println( "<HTML>\n" );
	  out.println( "<HEAD>\n" );
	  out.println( "<TITLE>Generic BOLs</TITLE>\n" );
	  out.println( "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n" );
	  out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
	  out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
	  out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
	  out.println( "<SCRIPT SRC=\"/clientscripts/picking.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n" );
	  out.println( "<SCRIPT SRC=\"/clientscripts/inventransaction.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n" );
	  out.println( "<SCRIPT SRC=\"/clientscripts/genericshipto.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n" );

	  out.println( "</HEAD>\n" );

	  //System.out.println("User_Action      " +User_Action+"    pullinven   "+pullinven);
	  DecimalFormat netwtTotal =new DecimalFormat( "####.000" );
	  if ( User_Action.equalsIgnoreCase( "generatelabels" ) )
	  {
		Vector new_data=new Vector();
		Vector new_dataF=new Vector();

		for ( int i=0; i < 25; i++ )
		{
		  Hashtable temp= ( Hashtable ) ( resultF.elementAt( i ) );
		  String item_id1= ( temp.get( "ITEM_ID" ) == null ? "" : temp.get( "ITEM_ID" ).toString() );
		  int itemIdint = 0;
		  try
		  {
			itemIdint=Integer.parseInt( item_id1 );
		  }
		  catch ( NumberFormatException ex )
		  {
			item_id1="";
		  }

		  String rqfrompage= ( temp.get( "RQ_VALUE" ) == null ? "" : temp.get( "RQ_VALUE" ).toString() );
		  String hazfrompage= ( temp.get( "HAZARDOUS" ) == null ? "" : temp.get( "HAZARDOUS" ).toString() );
		  String finalhazard = hazfrompage;

		String item_id= ( temp.get( "ITEM_ID" ) == null ? "" : temp.get( "ITEM_ID" ).toString() );
		String dotname= ( temp.get( "DOT" ) == null ? "" : temp.get( "DOT" ).toString() );
    String origDotName= ( temp.get( "DOT" ) == null ? "" : temp.get( "DOT" ).toString() );
    //String hazardous= ( temp.get( "HAZARDOUS" ) == null ? "" : temp.get( "HAZARDOUS" ).toString() );
		String packaging= ( temp.get( "PACKAGING" ) == null ? "" : temp.get( "PACKAGING" ).toString() );
		String totalqty= ( temp.get( "TOTAL_QTY" ) == null ? "" : temp.get( "TOTAL_QTY" ).toString() );
		String netweight= ( temp.get( "NET_WEIGHT" ) == null ? "" : temp.get( "NET_WEIGHT" ).toString() );

		float toatQty=0;
		if ( totalqty.trim().length() > 0 )
		{
		  try
		  {
			toatQty=Float.parseFloat( totalqty );
		  }
		  catch ( NumberFormatException ex )
		  {
			toatQty=0;
		  }
		}

		  int count=0;
		  dot = "";

		  if ( item_id1.trim().length() > 0 )
		  {
			//System.out.println("item_id " + i + "      "+item_id1);
			String query=" select fx_item_desc(" + item_id1 + ",'MA') ITEM_DESC, fx_kit_packaging(" + item_id1 + ") PACKAGING from dual ";

			DBResultSet dbrs=null;
			ResultSet searchRs=null;
			try
			{
			  dbrs=db.doQuery( query );
			  searchRs=dbrs.getResultSet();
			  while ( searchRs.next() )
			  {
				item_desc=searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" );
				packaging=searchRs.getString( "PACKAGING" ) == null ? "" : searchRs.getString( "PACKAGING" );
			  }

			  temp.remove( "ITEM_DESC" );
			  temp.put( "ITEM_DESC","" + item_desc + "" );

			  temp.remove( "PACKAGING" );
			  temp.put( "PACKAGING","" + packaging + "" );

			  //query=" select decode(replace(substr(lower(m.ground_shipping_name),1,2),' ','') 	,'no','Non Regulated','','',m.GROUND_SHIPPING_NAME||','||m.HAZARD_CLASS||','||m.UN_NUMBER||','||	m.PACKING_GROUP) DOT,decode(m.hazard_class, '0', ' ', null, ' ', 'X') hazardous from global.material m ";
			  //query+=" where material_Id in (select MATERIAL_ID from global.part where item_id = " + item_id1 + ")";

			  String dotquery=" select * from part_dot_view where item_id = " + item_id1 + " order by HAZARDOUS";
			  //String dotquery=" select * from global.material m where material_Id in (select MATERIAL_ID from global.part where item_id = " + item_id1 + ") order by HAZARD_CLASS";

			  dbrs=db.doQuery( dotquery );
			  searchRs=dbrs.getResultSet();

			  while ( searchRs.next() )
			  {
				//String ITEM_ID = searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID"));
				String tmpPACKAGING=searchRs.getString( "PACKAGING" ) == null ? "" : searchRs.getString( "PACKAGING" );
				String tmpHAZARDOUS=searchRs.getString( "HAZARDOUS" ) == null ? "" : searchRs.getString( "HAZARDOUS" );
				String DOT=searchRs.getString( "DOT" ) == null ? "" : searchRs.getString( "DOT" );
				//String ERG=searchRs.getString( "ERG" ) == null ? "" : searchRs.getString( "ERG" ) );
				//String MATERIAL_DESC=searchRs.getString( "MATERIAL_DESC" ) == null ? "" : searchRs.getString( "MATERIAL_DESC" ) );
				//String ITEM_DESC=searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" ) );
				String tmpnetweightlb=searchRs.getString( "NET_WEIGHT_LB" ) == null ? "" : searchRs.getString( "NET_WEIGHT_LB" );
				//String ORM_D=searchRs.getString( "ORM_D" ) == null ? "" : searchRs.getString( "ORM_D" ) );
				//String BULK_PKG_MARINE_POLLUTANT=searchRs.getString( "BULK_PKG_MARINE_POLLUTANT" ) == null ? "" : searchRs.getString( "BULK_PKG_MARINE_POLLUTANT" ) );

				float netwtLb=0;
				if ( tmpnetweightlb.trim().length() > 0 )
				{
				  try
				  {
					netwtLb=Float.parseFloat( tmpnetweightlb );
				  }
				  catch ( NumberFormatException ex )
				  {
					netwtLb=0;
				  }
				}
				float totalnetwt=0;
				totalnetwt=netwtLb * toatQty;

				String tempdotname=DOT;

				if ( count > 0 )
				{
				  dotname+="\n\n" + tempdotname;
				}
				else
				{
				  dotname=tempdotname;
				}

				if ( count > 0 )
				{
				  hazfrompage+="\n\n" + tmpHAZARDOUS;
				}
				else
				{
				  hazfrompage=tmpHAZARDOUS;
				}
				if ( !"X".equalsIgnoreCase( finalhazard ) )
				{
				  finalhazard=hazfrompage;
				}

				if ( count > 0 )
				{
				  packaging+="\n\n" + tmpPACKAGING;
				}
				else
				{
				  packaging=tmpPACKAGING;
				}

				if ( count > 0 )
				{
				  netweight+="\n\n" + "" + netwtTotal.format( totalnetwt ) + "";
				}
				else
				{
				  netweight="" + netwtTotal.format( totalnetwt ) + "";
				}

				count++;
			  }

        if (origDotName.length() == 0)
        {
        temp.remove( "DOT" );
			  temp.put( "DOT",dotname );
        }
			  temp.remove( "HAZARDOUS" );
			  temp.put( "HAZARDOUS",finalhazard );

			  temp.remove( "PACKAGING" );
			  temp.put( "PACKAGING",packaging );

			  temp.remove( "NET_WEIGHT" );
			  temp.put( "NET_WEIGHT",netweight );

			 /* while ( rs.next() )
			  {
				String GROUND_SHIPPING_NAME=rs.getString( "GROUND_SHIPPING_NAME" ) == null ? "" : rs.getString( "GROUND_SHIPPING_NAME" );
				String HAZARD_CLASS=rs.getString( "HAZARD_CLASS" ) == null ? "" : rs.getString( "HAZARD_CLASS" );
				String UN_NUMBER=rs.getString( "UN_NUMBER" ) == null ? "" : rs.getString( "UN_NUMBER" );
				String NA_NUMBER=rs.getString( "NA_NUMBER" ) == null ? "" : rs.getString( "NA_NUMBER" );
				String PACKING_GROUP=rs.getString( "PACKING_GROUP" ) == null ? "" : rs.getString( "PACKING_GROUP" );

				String tempdotname=GROUND_SHIPPING_NAME;

				if (GROUND_SHIPPING_NAME.length() > 0)
				{
				  tempdotname+="," + HAZARD_CLASS;
				}

			    if ( UN_NUMBER.length() > 0 )
				{
				  tempdotname+="," + UN_NUMBER;
				}
				else if ( NA_NUMBER.length() > 0 )
				{
				  tempdotname+="," + NA_NUMBER;
				}

				if ( PACKING_GROUP.length() > 0 )
				{
				  tempdotname+="," + PACKING_GROUP;
				}

				if ( count > 0 )
				{
				  dot+="\n" + tempdotname;
				}
				else
				{
				  if ( "Y".equalsIgnoreCase( rqfrompage ) )
				  {
					dot="RQ ";
				  }

				  dot+=tempdotname;
				}

				/*if ( count > 0 )
				{
				  dot+=" " + rs.getString( "DOT" ) == null ? "" : rs.getString( "DOT" );
				}
				else
				{
				  if ( "Y".equalsIgnoreCase( rqfrompage ) )
				  {
					dot="RQ ";
				  }
				  dot+=rs.getString( "DOT" ) == null ? "" : rs.getString( "DOT" );
				}*/

				/*hazardous=rs.getString( "HAZARD_CLASS" ) == null ? "" : rs.getString( "HAZARD_CLASS" );
				if (!"X".equalsIgnoreCase(finalhazard))
				{
				  if (!"0".equalsIgnoreCase(hazardous) && (hazardous.trim().length() > 0))
				  {
					finalhazard= "X";
				  }
				}
				count++;
			  }

			  temp.remove( "DOT" );
			  temp.put( "DOT","" + dot + "" );

			  temp.remove( "HAZARDOUS" );
			  temp.put( "HAZARDOUS","" + finalhazard + "" );*/
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
			new_data.addElement( temp );
		  }
		  new_dataF.addElement( temp );
	}

	resultF = new_dataF;

	if ( pullinven.equalsIgnoreCase( "yes" ) )
	{
	  Vector newdotvec=radian.web.HTMLHelpObj.getBolinvenData( db,new_data,branch_plant );
	  new_data = newdotvec;
	}

	Random rand = new Random();
	int tmpReq = rand.nextInt();
	Integer tmpReqNum = new Integer(tmpReq);
	BolgenericGenerator obj = new BolgenericGenerator();

	try
	{
	  if ( pullinven.equalsIgnoreCase( "yes" ) )
	  {
      if (new_data.size() >0)
      url=obj.buildTest( new_data,tmpReqNum.toString(),"genericinv" );
	  }
	  else
	  {
      if (new_data.size() >0)
      url=obj.buildTest( new_data,tmpReqNum.toString(),"generic" );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}

	//resultF=new_data;

	//out.println(radian.web.HTMLHelpObj.labelredirection(url));
	//out.close();
	if (url.trim().length() > 0)
	{
	  out.println( "<BODY onLoad=\"window.open('" + url + "')\">\n" );
	}
	else
	{
	  out.println( "<BODY>\n" );
	}

	}
	else
	{
	  out.println( "<BODY>\n" );
	}

	  out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
	  out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
	  out.println( "</DIV>\n" );
	  out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
	  out.println( radian.web.HTMLHelpObj.PrintTitleBar( "<B>Generic BOL</B>\n" ) );
	  out.println( "<FORM method=\"POST\" NAME=\"picking\" onSubmit=\"return SubmitOnlyOnce()\">\n" );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"generatelabels\">\n" );
	  out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "<B>Hub:</B>&nbsp;\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"20%\" ALIGN=\"LEFT\">\n" );
	  out.println( "<SELECT CLASS=\"HEADER\" NAME=\"HubName\" size=\"1\">\n" );
	  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
	  String hub_selected;
	  for ( Enumeration e=hub_list.keys(); e.hasMoreElements(); )
	  {
		String branch_plant1= ( String ) ( e.nextElement() );
		String hub_name= ( String ) ( hub_list.get( branch_plant1 ) );
		//
		hub_selected="";
		if ( branch_plant1.equalsIgnoreCase( branch_plant ) )
		{
		  hub_selected="selected";
		}
		out.println( "<option  " + hub_selected + " value=\"" + branch_plant1 + "\">" + hub_name + "</option>\n" );
	  }
	  out.println( "</SELECT>\n" );
	  out.println( "</TD>\n" );

	  String checkednon = "";
	  if ( pullinven.equalsIgnoreCase( "yes" ) )
	  {
		checkednon="checked";
	  }

	  out.println("<TD ALIGN=\"LEFT\" WIDTH=\"30%\" HEIGHT=\"5\"><INPUT CLASS=\"WHITEHEADER\"  TYPE=\"checkbox\" NAME=\"pullinven\" ID=\"pullinven\" value=\"Yes\" "+checkednon+" >&nbsp;&nbsp;<B>Show Inventory</B></TD>\n");

	  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
	  out.println( "<INPUT TYPE=\"submit\" VALUE=\"Generate BOL\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
	  //out.println( "</TD>\n" );
	  //out.println( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
	  //out.println( "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\">\n" );
	  out.println( "</TD></TR></TABLE>\n" );
	  out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n");

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\"><B>From:</B></td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"hub_location_desc\"  value=\""+hub_location_desc+"\" size=\"25\">\n");
	  out.println( "<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchshiptolike\" value=\"...\" OnClick=\"getshipTo('hub_location_desc','from')\"><IMG src=\"/images/search_small.gif\" alt=\"Ship To\"></BUTTON>\n" );
  	  out.println( "</TD>\n" );

	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\">Shipper Id</td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"shipper_id\"  value=\""+shipper_id+"\" size=\"15\"></td>\n");
	  out.println( "</TR>\n" );

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td CLASS=\"Inbluer\" width=\"5%\"></td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"hub_address_line_1\"  value=\""+hub_address_line_1+"\" size=\"25\"></td>\n");
	  out.println("<td CLASS=\"Inbluer\" width=\"5%\">Carrier</td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"carrier\"  value=\""+carrier+"\" size=\"15\"></td>\n");
	  out.println( "</TR>\n" );

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\"></td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"hub_address_line_2\"  value=\""+hub_address_line_2+"\" size=\"25\"></td>\n");
	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\">Carrier_no</td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"carrier_no\"  value=\""+carrier_no+"\" size=\"15\"></td>\n");
	  out.println( "</TR>\n" );

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td CLASS=\"Inbluer\" width=\"5%\">City</td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"hub_city\"  value=\""+hub_city+"\" size=\"15\"></td>\n");

	  out.println("<td CLASS=\"Inbluer\" width=\"5%\">State</td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"hub_state_abbrev\"  value=\""+hub_state_abbrev+"\" size=\"3\"></td>\n");
	  out.println( "</TR>\n" );
	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\">Zip</td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"hub_zip\"  value=\""+hub_zip+"\" size=\"7\"></td>\n");

	  //out.println("<td CLASS=\"Inwhiter\" width=\"5%\">Country</td>\n");
	  //out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"hub_country_abbrev\"  value=\""+hub_country_abbrev+"\" size=\"5\"></td>\n");
	  out.println( "</TR>\n" );

//out.println("<td "+Color+"r\" width=\"5%\">hub_address_line_3</td>\n");
//out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"hub_address_line_3\"  value=\""+hub_address_line_3+"\" size=\"10\"></td>\n");

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );

	  out.println("<td CLASS=\"Inbluer\" width=\"5%\"><B>Ship To</B></td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_to_location_desc\"  value=\""+ship_to_location_desc+"\" size=\"25\">\n");
	  out.println( "<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchshiptolike\" value=\"...\" OnClick=\"getshipTo('ship_to_location_desc','to')\"><IMG src=\"/images/search_small.gif\" alt=\"Ship To\"></BUTTON>\n" );
  	  out.println( "</TD>\n" );

	  out.println("<td CLASS=\"Inbluer\" width=\"5%\">PO Number</td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"po_number\"  value=\""+po_number+"\" size=\"15\">\n");
	  out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PO Ref&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
	  out.println("<input type=\"text\" CLASS=\"DETAILS\" name=\"po_release_number\"  value=\""+po_release_number+"\" size=\"15\"></td>\n");

	  /*out.println("<td "+Color+"r\" width=\"5%\">PO Number</td>\n");
	  out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"po_number\"  value=\""+po_number+"\" size=\"15\"></td>\n");
	  out.println("<td "+Color+"r\" width=\"5%\">PO Ref</td>\n");
	  out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"po_release_number\"  value=\""+po_release_number+"\" size=\"15\"></td>\n");*/

	  out.println( "</TR>\n" );

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\"></td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_to_address_line_1\"  value=\""+ship_to_address_line_1+"\" size=\"25\"></td>\n");

	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\">Req. Del. Date</td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"required_datetime\"  value=\""+required_datetime+"\" size=\"15\"></td>\n");

	  out.println( "</TR>\n" );

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td CLASS=\"Inbluer\" width=\"5%\"></td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_to_address_line_2\"  value=\""+ship_to_address_line_2+"\" size=\"25\"></td>\n");

	  out.println("<td CLASS=\"Inbluer\" width=\"5%\">Ship Date</td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_date\"  value=\""+ship_date+"\" size=\"15\"></td>\n");

	  out.println( "</TR>\n" );

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\">City</td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_to_city\"  value=\""+ship_to_city+"\" size=\"15\"></td>\n");

	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\">State</td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_to_state_abbrev\"  value=\""+ship_to_state_abbrev+"\" size=\"3\"></td>\n");
	  out.println( "</TR>\n" );
	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td CLASS=\"Inbluer\" width=\"5%\">Zip</td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_to_zip\"  value=\""+ship_to_zip+"\" size=\"7\"></td>\n");

	  out.println("<td CLASS=\"Inbluer\" width=\"5%\">&nbsp;</td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\">&nbsp;</td>\n");

//      out.println("<td CLASS=\"Inbluer\" width=\"5%\">Country</td>\n");
//      out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_to_country_abbrev\"  value=\""+ship_to_country_abbrev+"\" size=\"5\"></td>\n");

	  out.println( "</TR>\n" );
	  //out.println("<td "+Color+"r\" width=\"5%\">ship_to_address_line_3</td>\n");
	  //out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_to_address_line_3\"  value=\""+ship_to_address_line_3+"\" size=\"10\"></td>\n");
/*
	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td "+Color+"r\" width=\"5%\">End User</td>\n");
	  out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"end_user\"  value=\""+end_user+"\" size=\"20\"></td>\n");
	  out.println("<td "+Color+"r\" width=\"5%\">Department</td>\n");
	  out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"department\"  value=\""+department+"\" size=\"25\"></td>\n");
	  out.println( "</TR>\n" );

	   out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	   //out.println("<td "+Color+"r\" width=\"5%\">application</td>\n");
	   //out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"application\"  value=\""+application+"\" size=\"10\"></td>\n");
	   out.println("<td "+Color+"r\" width=\"5%\">Work Area</td>\n");
	   out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"application_desc\"  value=\""+application_desc+"\" size=\"25\"></td>\n");
	   out.println("<td "+Color+"r\" width=\"5%\">Delivery to</td>\n");
	   out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"delivery_point\"  value=\""+delivery_point+"\" size=\"25\"></td>\n");
	   //out.println("<td "+Color+"r\" width=\"5%\">delivery_point_desc</td>\n");
	   //out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"delivery_point_desc\"  value=\""+delivery_point_desc+"\" size=\"10\"></td>\n");
	  out.println( "</TR>\n" );
*/
/*
	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );

	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\">No of Units</td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"total_qty\"  value=\""+total_qty+"\" size=\"4\"></td>\n");

	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\">Packaging</td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"packaging\"  value=\""+packaging+"\" size=\"25\"></td>\n");

	  out.println( "</TR>\n" );

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );

	  out.println("<td CLASS=\"Inbluer\" width=\"5%\">Hazardous</td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"hazardous\"  value=\""+hazardous+"\" size=\"3\"></td>\n");

	  out.println("<td CLASS=\"Inbluer\" width=\"5%\">DOT</td>\n");
	  out.println("<td CLASS=\"Inbluel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"dot\"  value=\""+dot+"\" size=\"30\"></td>\n");

	  out.println( "</TR>\n" );

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\">Item</td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"item_id\"  value=\""+item_id+"\" size=\"10\"></td>\n");

	  out.println("<td CLASS=\"Inwhiter\" width=\"5%\">Item Desc</td>\n");
	  out.println("<td CLASS=\"Inwhitel\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"item_desc\"  value=\""+item_desc+"\" size=\"30\"></td>\n");

	  out.println( "</TR>\n" );

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println( "</TR>\n" );
*/

/*
out.println("<td "+Color+"r\" width=\"5%\">quantity_shipped</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"quantity_shipped\"  value=\""+quantity_shipped+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">erg</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"erg\"  value=\""+erg+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">mfg_lot</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"mfg_lot\"  value=\""+mfg_lot+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">date_of_receipt</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"date_of_receipt\"  value=\""+date_of_receipt+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">radian_po</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"radian_po\"  value=\""+radian_po+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">expire_date</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"expire_date\"  value=\""+expire_date+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">pr_number</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"pr_number\"  value=\""+pr_number+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">line_item</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"line_item\"  value=\""+line_item+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">mr_line</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"mr_line\"  value=\""+mr_line+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">receipt_id</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"receipt_id\"  value=\""+receipt_id+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">sales_order</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"sales_order\"  value=\""+sales_order+"\" size=\"10\"></td>\n");

out.println("<td "+Color+"r\" width=\"5%\">source_hub</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"source_hub\"  value=\""+source_hub+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">batch</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"batch\"  value=\""+batch+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">batch_id</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"batch_id\"  value=\""+batch_id+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">ship_to_location_id</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"ship_to_location_id\"  value=\""+ship_to_location_id+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">hub_location_id</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"hub_location_id\"  value=\""+hub_location_id+"\" size=\"10\"></td>\n");

out.println("<td "+Color+"r\" width=\"5%\">cat_part_no</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"cat_part_no\"  value=\""+cat_part_no+"\" size=\"10\"></td>\n");
out.println("<td "+Color+"r\" width=\"5%\">catalog_id</td>\n");
out.println("<td "+Color+"l\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"catalog_id\"  value=\""+catalog_id+"\" size=\"10\"></td>\n");
*/
	  out.println("</TABLE>\n");
	  out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
	  out.println("<TR VALIGN=\"MIDDLE\">\n<TH HEIGHT=\"35\" WIDTH=\"5%\">Item</TH>\n");
	  out.println("<TH HEIGHT=\"35\" WIDTH=\"2%\">No of Units</TH>\n");
	  out.println("<TH HEIGHT=\"35\" WIDTH=\"2%\">RQ</TH>\n");
	  out.println("<TH HEIGHT=\"35\" WIDTH=\"2%\">Hazardous</TH>\n");
	  out.println("<TH HEIGHT=\"35\" WIDTH=\"15%\">DOT</TH>\n");
	  out.println("</TR>\n");


	  for ( int rem=0; rem < 25; rem++ )
	  {
		Hashtable temp= ( Hashtable ) ( resultF.elementAt( rem ) );
		String Color=" ";
		if ( rem % 2 == 0 )
		{
		  Color="CLASS=\"Inwhite";
		}
		else
		{
		  Color="CLASS=\"Inblue";
		}
		out.println( "<TR VALIGN=\"MIDDLE\">\n" );
		out.println( "<TD WIDTH=\"5%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"item_id" + rem + "\" value=\""+temp.get( "ITEM_ID" ).toString()+"\" SIZE=\"10\"></TD>\n" );
		out.println( "<TD WIDTH=\"2%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"total_qty" + rem + "\" value=\""+temp.get( "TOTAL_QTY" ).toString()+"\" SIZE=\"5\"></TD>\n" );
		out.println( "<TD WIDTH=\"2%\" " + Color + "\"><INPUT type=\"checkbox\" name=\"rqvalue" + rem + "\" value=\"Y\"></TD>\n" );
		out.println( "<TD WIDTH=\"2%\" " + Color + "\"><INPUT type=\"checkbox\" name=\"hazardous" + rem + "\" value=\"Y\"></TD>\n" );
		out.println( "<TD WIDTH=\"15%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"dot" + rem + "\" value=\""+temp.get( "DOT" ).toString()+"\" SIZE=\"40\"></TD>\n" );
		out.println( "</TR>\n" );
	  }

		out.println("</TABLE>\n");
	  out.println( "</form>\n" );
	  out.println( "</body></html>\n" );

	//out.println( Msgn );
	out.close();

	resultF=null;
	boldata=null;
}
}
