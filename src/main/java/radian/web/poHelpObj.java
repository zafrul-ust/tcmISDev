/**
 * Title:        Resoource Loader
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * I am putting a bunch of get resource functions in this so that I dont have to be dependent on the
 * file path and the machine I am working on
 *
 * 06-27-03 - Code cleanup
 * 07-01-03 - Adding change listener to lookahead windows and showing all tcmbuys when asked for it.
 * 08-08-03 - Showing the Original Confirmed Date in tcm buy history
 * 09-15-03 - Showing the Address line 3 of the ship to address
 * 11-19-03 - Changed the function so that is does not show all invengroup
 * 12-16-03 - Made changes to the spec section to show the requirements of spec.
 * 01-13-04 - Saying Purchase TO only for specs that are active
 * 02-10-04 - Sorting INVENTORY_GROUP data by INVENTORY_GROUP
 * 02-23-04 - Sorting the TCM buys by radian PO
 * 04-26-04 - Getting INVENTORY_GROUP_NAME from INVENTORY_GROUP_DEFINITION to use for visible values in drop downs
 * 07-22-04 - Not setting the Hub from shipto. Getting ti from the Hub on Buy Order. Also lookahead can not be zero
 * 08-02-04 - Showing Flow Down Rev Date
 * 08-13-04 - Coloring Flow Downs
 * 10-13-04 - Showing the catalog commetns for each part in a inventory group
 * 02-23-05 - Showing Customer PO in the TCM buy History
 */
package radian.web;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.InventoryGroupObjBean;
import com.tcmis.common.util.DateHandler;
import oracle.jdbc.OracleTypes;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;

public abstract class poHelpObj
{
  private static org.apache.log4j.Logger polog2 = org.apache.log4j.Logger.getLogger(poHelpObj.class);

  public static void buildsendItemDetails( TcmISDBModel dbObj3,String lineID,String item_id,boolean fromline,PrintWriter poout )
  {
    //StringBuffer Msgtemp=new StringBuffer();
    DBResultSet dbrs=null;
    ResultSet rs=null;
    poout.println( "{\n" );

    int compn=Integer.parseInt( lineID.trim() );
    String itemColor=" ";
    if ( compn % 2 == 0 )
    {
      itemColor="INVISIBLEHEADBLUE";
    }
    else
    {
      itemColor="INVISIBLEHEADWHITE";
    }

    try
    {
      dbrs=dbObj3.doQuery( "Select ITEM_ID,ITEM_DESC,CATEGORY_ID,REVISION_COMMENTS,REPLACEMENT_ITEM,STOCKING_TYPE,MSRP,PRICE_UNIT,STORAGE_TEMP,SHELF_LIFE_DAYS,SHELF_LIFE_BASIS,SAMPLE_ONLY,ITEM_TYPE,fx_kit_packaging (item_id) packaging from item  where item_id = " + item_id + " " );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        String item_desc=HelpObjs.addescapesForJavascript( rs.getString( "ITEM_DESC" ) == null ? "" : rs.getString( "ITEM_DESC" ).trim() );
        String item_type=rs.getString( "ITEM_TYPE" ) == null ? "" : rs.getString( "ITEM_TYPE" ).trim().toUpperCase();
        String packaging=HelpObjs.addescapesForJavascript( rs.getString( "packaging" ) == null ? "" : rs.getString( "packaging" ).trim() );

        poout.println( "selectedRow = opener.document.getElementById(\"lineitemid" + lineID + "\");\n" );
        poout.println( "selectedRow.className = \"" + itemColor + "\";\n" );
        poout.println( "selectedRow.value = \"" + item_id + "\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"itemtype" + lineID + "\");\n" );
        poout.println( "selectedRow.value = \"" + item_type + "\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"validitem" + lineID + "\");\n" );
        poout.println( "selectedRow.value = \"Yes\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"itemtypecell" + lineID + "\");\n" );
        poout.println( "selectedRow.innerHTML = \"" + item_type + "\";\n" );

        poout.println( "selectedRow = opener.document.getElementById(\"pakgingcell" + lineID + "\");\n" );
        poout.println( "selectedRow.innerHTML = \"" + packaging + "\";\n" );

        if ( "MA".equalsIgnoreCase( item_type ) )
        {
          poout.println( "selectedRow = opener.document.getElementById(\"itemdescdivrow" + lineID + "" + lineID + "\");\n" );
          poout.println( "selectedRow.innerHTML = \"" + item_desc + "\";\n" );

          if ( fromline )
          {
            poout.println( "opener.invalidatespecsandflow(" + lineID + "); \n" );

            poout.println( "validflowdown = opener.document.getElementById(\"validflowdown" + lineID + "\");\n" );
            poout.println( "validflowdown.value = \"No\";\n" );

            poout.println( "validspec = opener.document.getElementById(\"validspec" + lineID + "\");\n" );
            poout.println( "validspec.value = \"No\";\n" );

          }
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

    poout.println( " }\n" );
    return ;
  }

  public static void buildsendShipto( TcmISDBModel dbObj9,String shipTo,String shipToCompanyId, PrintWriter poout )
  {
    //StringBuffer Msgtemp=new StringBuffer();
    DBResultSet dbrs=null;
    ResultSet rs=null;
    poout.println( "{\n" );

    try
    {
      String shipToLocQuery = "";
      shipToLocQuery = "Select * from ship_to_location_view  where lower(LOCATION_ID) = lower('" + shipTo + "') ";
      if (shipToCompanyId.length() >0)
      {
        shipToLocQuery +=" and COMPANY_ID = '"+shipToCompanyId+"'";
      }

      dbrs=dbObj9.doQuery(shipToLocQuery);
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        String location_id=rs.getString( "LOCATION_ID" ) == null ? "" : rs.getString( "LOCATION_ID" ).trim();
        String country_abbrev=rs.getString( "COUNTRY_ABBREV" ) == null ? "" : rs.getString( "COUNTRY_ABBREV" ).trim();
        String state_abbrev=rs.getString( "STATE_ABBREV" ) == null ? "" : rs.getString( "STATE_ABBREV" ).trim();
        String address_line_1=rs.getString( "ADDRESS_LINE_1" ) == null ? "" : rs.getString( "ADDRESS_LINE_1" ).trim();
        String address_line_2=rs.getString( "ADDRESS_LINE_2" ) == null ? "" : rs.getString( "ADDRESS_LINE_2" ).trim();
  	    String address_line_3=rs.getString( "ADDRESS_LINE_3" ) == null ? "" : rs.getString( "ADDRESS_LINE_3" ).trim();
        String city=rs.getString( "CITY" ) == null ? "" : rs.getString( "CITY" ).trim();
        String zip=rs.getString( "ZIP" ) == null ? "" : rs.getString( "ZIP" ).trim();
        String location_desc=rs.getString( "LOCATION_DESC" ) == null ? "" : rs.getString( "LOCATION_DESC" ).trim();
        String company_id=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();

        poout.println( "shiptoid = opener.document.getElementById(\"shiptoid\");\n" );
        poout.println( "shiptoid.className = \"HEADER\";\n" );
        poout.println( "shiptoid.value = \"" + location_id + "\";\n" );

        poout.println( "shiptocompanyid = opener.document.getElementById(\"shiptocompanyid\");\n" );
        poout.println( "shiptocompanyid.value = \"" + company_id + "\";\n" );

        poout.println( "validshipto = opener.document.getElementById(\"validshipto\");\n" );
        poout.println( "validshipto.value = \"Yes\";\n" );

        poout.println( "shiptoline1 = opener.document.getElementById(\"shiptoline1\");\n" );
        poout.println( "shiptoline1.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + location_desc + "</FONT>\";\n" );

        poout.println( "shiptoline2 = opener.document.getElementById(\"shiptoline2\");\n" );
        poout.println( "shiptoline2.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + address_line_1 + "</FONT>\";\n" );

		poout.println( "shiptoline3 = opener.document.getElementById(\"shiptoline3\");\n" );
        poout.println( "shiptoline3.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + address_line_2 + "</FONT>\";\n" );

        if ( address_line_3.length() > 0 )
        {
		  poout.println( "shiptoline3 = opener.document.getElementById(\"shiptoline4\");\n" );
          poout.println( "shiptoline3.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + address_line_3 + "</FONT>\";\n" );

          poout.println( "shiptoline4 = opener.document.getElementById(\"shiptoline5\");\n" );
          poout.println( "shiptoline4.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + city + ", " + state_abbrev + " " + zip + " " + country_abbrev + "</FONT>\";\n" );
        }
        else
        {
          poout.println( "shiptoline3 = opener.document.getElementById(\"shiptoline4\");\n" );
          poout.println( "shiptoline3.innerHTML = \"<FONT COLOR=\\\"Maroon\\\">" + city + ", " + state_abbrev + " " + zip + " " + country_abbrev + "</FONT>\";\n" );

          poout.println( "shiptoline4 = opener.document.getElementById(\"shiptoline5\");\n" );
          poout.println( "shiptoline4.innerHTML = \"<FONT COLOR=\\\"Maroon\\\"></FONT>\";\n" );
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

    /*int numofHubs=0;
    try
    {
      dbrs=dbObj9.doQuery( "Select * from location_to_hub_view where LOCATION_ID = '" + shipTo + "' " );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        String hubName=rs.getString( "HUB_NAME" ) == null ? "" : rs.getString( "HUB_NAME" ).trim();
        String branchPlant=rs.getString( "BRANCH_PLANT" ) == null ? "" : rs.getString( "BRANCH_PLANT" ).trim();

        poout.println( "hubSlectName = opener.document.getElementById(\"HubName\");\n" );
        poout.println( "hubSlectName.value = \"" + branchPlant + "\";\n" );

        poout.println( "HubFullName = opener.document.getElementById(\"HubFullName\");\n" );
        poout.println( "HubFullName.value = \"" + hubName + "\";\n" );

        numofHubs++;
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

    poout.println( "numofHubs = opener.document.getElementById(\"numofHubs\");\n" );
    poout.println( "numofHubs.value = \"" + numofHubs + "\";\n" );*/
    poout.println( " }\n" );

    return;
  }

  public static void buildsendLookahead( TcmISDBModel dbObj3,String setlinestatus,String lineID,String itemID,String radianpo,boolean canupdate,PrintWriter poout,ResourceLibrary res )
  {
    //StringBuffer Msgtemp=new StringBuffer();
    DBResultSet dbrs=null;
    ResultSet rs=null;

    poout.println( "{\n" );
    int count=0;

    //Won't show anything if the po or bpo is not a valid number
    try
    {
      String itemnotesquery="";
      if ( radianpo.length() > 0 && itemID.length() > 0 )
      {
        itemnotesquery="select distinct x.INVENTORY_GROUP,x.PART_GROUP_NO,x.CAT_PART_NO,x.LOOK_AHEAD_DAYS,x.COMPANY_ID,x.CATALOG_ID from customer.catalog_part_inventory x, buy_order y where y.PART_ID= x.CAT_PART_NO and x.COMPANY_ID = y.COMPANY_ID and y.RADIAN_PO=" + radianpo + " and y.ITEM_ID=" + itemID + " ";

        dbrs=dbObj3.doQuery( itemnotesquery );
        rs=dbrs.getResultSet();

        poout.println( "var lookaheaddivrow = opener.document.getElementById(\"lookaheaddetaildivrow" + lineID + "" + lineID + "\");\n" );
        poout.println( "var insidehtml =\"<TABLE ID=\\\"lookaheadtable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
        poout.println( "insidehtml +=\"<TR>\";\n" );
        poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.companyid")+"</B></TH>\";\n" );
        poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.catalogid")+"</B></TH>\";\n" );
        poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.inventorygroup")+"</B></TH>\";\n" );
        poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.partno")+"</B></TH>\";\n" );
        poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.lookaheaddays")+"</B></TH>\";\n" );
        poout.println( "insidehtml +=\"</TR>\";\n" );

        int i=0;
        while ( rs.next() )
        {
          String company_id=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();
          String catalog_id=rs.getString( "CATALOG_ID" ) == null ? "" : rs.getString( "CATALOG_ID" ).trim();
          String cat_part_no=HelpObjs.addescapesForJavascript( rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ).trim() );
          String lookaheaddays=rs.getString( "LOOK_AHEAD_DAYS" ) == null ? "" : rs.getString( "LOOK_AHEAD_DAYS" ).trim();
          String inventorygrp=rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ).trim();
          String partgrpno=rs.getString( "PART_GROUP_NO" ) == null ? "" : rs.getString( "PART_GROUP_NO" ).trim();

          count+=1;
          String Color=" ";
          if ( count % 2 == 0 )
          {
            Color="CLASS=\\\"Inbluel";
          }
          else
          {
            Color="CLASS=\\\"Inwhitel";
          }

          poout.println( "insidehtml +=\"<TR>\";\n" );
          poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + company_id + "</TD>\";\n" );
          poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + catalog_id + "</TD>\";\n" );
					poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"15%\\\">" + inventorygrp + "</TD>\";\n" );
          poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"15%\\\">" + cat_part_no + "</TD>\";\n" );
          if ( canupdate )
          {
            poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"15%\\\"><input type=\\\"text\\\" CLASS=\\\"HEADER\\\" size=\\\"5\\\" name=\\\"lookaheaddays" + lineID + "_" + i + "\\\" value=\\\"" + lookaheaddays + "\\\" onChange=\\\"lookaheadchanged('"+lineID+"','"+i+"')\\\"></TD>\";\n" );
          }
          else
          {
            poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + lookaheaddays + "</TD>\";\n" );
          }
          poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"lookaheadcompanyid" + lineID + "_" + i + "\\\" value=\\\"" + company_id +  "\\\">\";\n" );
          poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"lookaheadcatalogid" + lineID + "_" + i + "\\\" value=\\\"" + catalog_id +  "\\\">\";\n" );
          poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"lookaheadcatpartno" + lineID + "_" + i + "\\\" value=\\\"" + cat_part_no + "\\\">\";\n" );
          poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"lookaheadinventorygrp" + lineID + "_" + i + "\\\" value=\\\"" + inventorygrp + "\\\">\";\n" );
          poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"lookaheadpartgrpno" + lineID + "_" + i + "\\\" value=\\\"" + partgrpno + "\\\">\";\n" );

          poout.println( "insidehtml +=\"</TR>\";\n" );

          i++;
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

    if ( count == 0 )
    {
      poout.println( "insidehtml +=\"<TR>\";\n" );
      poout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"5\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
      poout.println( "insidehtml +=\"</TR>\";\n" );
    }

    poout.println( "insidehtml +=\"</TABLE>\";\n" );
    poout.println( "lookaheaddivrow.innerHTML =insidehtml;\n" );
    poout.println( "nooflookaheads = opener.document.getElementById(\"nooflookaheads" + lineID + "\");\n" );
    poout.println( "nooflookaheads.value = \"" + count + "\";\n" );
    poout.println( "validlookahead = opener.document.getElementById(\"validlookahead" + lineID + "\");\n" );
    poout.println( "validlookahead.value = \"Yes\";\n" );

    //if the use click on add before clicking on the plus this part sets the image to minus and also the underlying hidden elements
    if ( "yes".equalsIgnoreCase( setlinestatus ) )
    {
      poout.println( "var lookaheadimg = opener.document.getElementById(\"lookaheadimg" + lineID + "\");\n" );
      poout.println( "lookaheadimg.setAttribute(\"src\",'/images/minus.jpg');\n" );

      poout.println( "var lookaheadstatus  =  opener.document.getElementById(\"lookaheadstatus" + lineID + "\");\n" );
      poout.println( "lookaheadstatus.value = \"Yes\";\n" );
    }

    poout.println( " }\n" );

    //return Msgtemp;
  }

  public static void buildsendpartNotes( TcmISDBModel dbObj3,String lineID,String itemID,String invengrp,String setlinestatus,PrintWriter poout,ResourceLibrary res )
  {
	poout.println( "{\n" );
	DBResultSet dbrs=null;
	ResultSet rs=null;
	int count=0;

	try
	{
	  String itemnotesquery="select COMPANY_ID, CATALOG_ID, CAT_PART_NO, CAT_PART_COMMENT  from ig_cat_part_comment_view where item_id=" + itemID + " and inventory_group='"+invengrp+"' and CAT_PART_COMMENT is not null";

	  dbrs=dbObj3.doQuery( itemnotesquery );
	  rs=dbrs.getResultSet();

	  poout.println( "var itemnotescell = opener.document.getElementById(\"partnotesdivrow" + lineID + "" + lineID + "\");\n" );
	  poout.println( "var insidehtml =\"<TABLE ID=\\\"partdetailtable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
	  poout.println( "insidehtml +=\"<TR>\";\n" );
	  poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.company")+"</B></TH>\";\n" );
	  poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.catalog")+"</B></TH>\";\n" );
	  poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.partnum")+"</B></TH>\";\n" );
	  poout.println( "insidehtml +=\"<TH WIDTH=\\\"50%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.comments")+"</B></TH>\";\n" );
	  poout.println( "insidehtml +=\"</TR>\";\n" );

	  while ( rs.next() )
	  {
		String company=rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ).trim();
		String catalog=rs.getString( "CATALOG_ID" ) == null ? "" : rs.getString( "CATALOG_ID" ).trim();
		String partnum=rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ).trim();
		String comments=HelpObjs.addescapesForJavascript( rs.getString( "CAT_PART_COMMENT" ) == null ? "" : rs.getString( "CAT_PART_COMMENT" ).trim() );

		count+=1;

		String Color=" ";
		if ( count % 2 == 0 )
		{
		  Color="CLASS=\\\"Inblue";
		}
		else
		{
		  Color="CLASS=\\\"Inwhite";
		}

		poout.println( "insidehtml +=\"<TR>\";\n" );
		poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + company + "</TD>\";\n" );
		poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + catalog + "</TD>\";\n" );
		poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + partnum + "</TD>\";\n" );
		poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"50%\\\">" + comments + "</TD>\";\n" );
		poout.println( "insidehtml +=\"</TR>\";\n" );

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

	if ( count == 0 )
	{
	  poout.println( "insidehtml +=\"<TR>\";\n" );
	  poout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"4\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
	  poout.println( "insidehtml +=\"</TR>\";\n" );
	}

	poout.println( "insidehtml +=\"</TABLE>\";\n" );
	poout.println( "itemnotescell.innerHTML =insidehtml;\n" );

	//if the use click on add before clicking on the plus this part sets the image to minus and also the underlying hidden elements
	if ( "yes".equalsIgnoreCase( setlinestatus ) )
	{
	  poout.println( "var partnotesimage = opener.document.getElementById(\"partnotesimage" + lineID + "\");\n" );
	  poout.println( "partnotesimage.setAttribute(\"src\",'/images/minus.jpg');\n" );

	  poout.println( "var partnotestatus  =  opener.document.getElementById(\"partnotestatus" + lineID + "\");\n" );
	  poout.println( "partnotestatus.value = \"Yes\";\n" );
	}

	poout.println( " }\n" );
	return;
  }

  public static void buildsenditemNotes( TcmISDBModel dbObj3,String lineID,String itemID,String setlinestatus,PrintWriter poout,ResourceLibrary res )
  {
    //StringBuffer Msgtemp=new StringBuffer();
    poout.println( "{\n" );
    DBResultSet dbrs=null;
    ResultSet rs=null;
    int count=0;

    try
    {

      String itemnotesquery="select ibc.ITEM_ID ,ibc.COMMENTS,ibc.TRANSACTION_DATE, to_char(ibc.TRANSACTION_DATE,'mm/dd/yyyy') TRANS_DATE, p.first_name ||' '|| p.last_name Full_name from item_buyer_comments ibc,personnel p ";
      itemnotesquery+=" where item_id = " + itemID + " and ibc.buyer_id = p.personnel_id and company_id = 'Radian' order by TRANSACTION_DATE desc";

      dbrs=dbObj3.doQuery( itemnotesquery );
      rs=dbrs.getResultSet();

      poout.println( "var itemnotescell = opener.document.getElementById(\"row4detaildivrow" + lineID + "" + lineID + "\");\n" );
      poout.println( "var insidehtml =\"<TABLE ID=\\\"linedetailtable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
      poout.println( "insidehtml +=\"<TR>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.date")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"10%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.buyer")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"50%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.comments")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"</TR>\";\n" );

      while ( rs.next() )
      {
        String buyer_id=rs.getString( "Full_name" ) == null ? "" : rs.getString( "Full_name" ).trim();
        String comments=HelpObjs.addescapesForJavascript( rs.getString( "COMMENTS" ) == null ? "" : rs.getString( "COMMENTS" ).trim() );
        String transaction_date=rs.getString( "TRANS_DATE" ) == null ? "" : rs.getString( "TRANS_DATE" ).trim();

        count+=1;

        String Color=" ";
        if ( count % 2 == 0 )
        {
          Color="CLASS=\\\"Inblue";
        }
        else
        {
          Color="CLASS=\\\"Inwhite";
        }

        poout.println( "insidehtml +=\"<TR>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + transaction_date + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"10%\\\">" + buyer_id + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"50%\\\">" + comments + "</TD>\";\n" );
        poout.println( "insidehtml +=\"</TR>\";\n" );

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

    if ( count == 0 )
    {
      poout.println( "insidehtml +=\"<TR>\";\n" );
      poout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"3\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
      poout.println( "insidehtml +=\"</TR>\";\n" );
    }

    poout.println( "insidehtml +=\"</TABLE>\";\n" );
    poout.println( "itemnotescell.innerHTML =insidehtml;\n" );

    //if the use click on add before clicking on the plus this part sets the image to minus and also the underlying hidden elements
    if ( "yes".equalsIgnoreCase( setlinestatus ) )
    {
      poout.println( "var itemnotesimgae = opener.document.getElementById(\"itemnotesimage" + lineID + "\");\n" );
      poout.println( "itemnotesimgae.setAttribute(\"src\",'/images/minus.jpg');\n" );

      poout.println( "var itemnotestatus  =  opener.document.getElementById(\"itemnotestatus" + lineID + "\");\n" );
      poout.println( "itemnotestatus.value = \"Yes\";\n" );
    }

    poout.println( " }\n" );
    return;
  }

  public static void buildshowTcmBuys( TcmISDBModel dbObj1,String lineID,String itemID,String setlinestatus,String ShipToLocid,String opsEntityId,PrintWriter poout,ResourceLibrary res )
  {
    //return buildshowTcmBuys( dbObj1,lineID,itemID,setlinestatus,ShipToLocid,false,poout);
	buildshowTcmBuys( dbObj1,lineID,itemID,setlinestatus,ShipToLocid,false,opsEntityId,poout,res);
	return;
  }

  public static void buildshowTcmBuys( TcmISDBModel dbObj1,String lineID,String itemID,String setlinestatus,String ShipToLocid,boolean showall,String opsEntityId,PrintWriter poout,ResourceLibrary res )
  {
    //StringBuffer Msgtemp=new StringBuffer();
    poout.println( "{\n" );
    DBResultSet dbrs=null;
    ResultSet rs=null;
    int count=0;

    try
    {
      String itemnotesquery="select CUSTOMER_PO,CURRENCY_ID,to_char(ORIGINAL_DATE_CONFIRMED,'mm/dd/yyyy hh:mi AM') ORIGINAL_DATE_CONFIRMED,RADIAN_PO,CARRIER,SHIP_TO_COMPANY_ID,SHIP_TO_LOCATION_ID,SUPPLIER_CONTACT_NAME,PHONE,to_char(FIRST_TIME_RECEIVED,'mm/dd/yyyy') FIRST_TIME_RECEIVED,BUYER,BUYER_NAME,SUPPLIER,SUPPLIER_NAME,PO_LINE,BRANCH_PLANT,ITEM_ID,QUANTITY,UNIT_PRICE,to_char(DATE_SENT,'mm/dd/yyyy') DATE_SENT,DATE_CONFIRMED,to_char(DATE_CONFIRMED,'mm/dd/yyyy hh:mi AM') DATE_CONFIRMED1,QUANTITY_RECEIVED from po_view where item_id = " + itemID + " ";
      if (opsEntityId.trim().length() > 0)
      {itemnotesquery += " and ops_entity_id = '"+opsEntityId+"'";}
      if (!showall){itemnotesquery += " and rownum < 11";}
	  itemnotesquery += "order by radian_po desc";

      dbrs=dbObj1.doQuery( itemnotesquery );
      rs=dbrs.getResultSet();

      poout.println( "var itemnotescell = opener.document.getElementById(\"row8detaildivrow" + lineID + "" + lineID + "\");\n" );
      poout.println( "var insidehtml =\"<TABLE ID=\\\"tcmbuytable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
      poout.println( "insidehtml +=\"<TR>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.buyer")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.pon")+"</B></TH>\";\n" );
	  poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.cuspon")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.shipto")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.carrier")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.supplier")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.ordertakenby")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.phoneno")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.qty")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.orderdate")+"<BR>("+res.getString("label.lastconfirmed")+")</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.originalconfirmdate")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.unitprice")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.receivedate")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.qtyrecvd")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"</TR>\";\n" );

      while ( rs.next() )
      {
        String radian_po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ).trim();
        String quantity=rs.getString( "QUANTITY" ) == null ? "" : rs.getString( "QUANTITY" ).trim();
        String unit_price=rs.getString( "UNIT_PRICE" ) == null ? "" : rs.getString( "UNIT_PRICE" ).trim();

        /*BigDecimal unitPrice=new BigDecimal("0");
        if ( unit_price.length() > 0 )
        {
          unitPrice=new BigDecimal( unit_price );
        }*/

        String date_confirmed=rs.getString( "DATE_CONFIRMED1" ) == null ? "" : rs.getString( "DATE_CONFIRMED1" ).trim();
        String orig_date_confirmed=rs.getString( "ORIGINAL_DATE_CONFIRMED" ) == null ? "" : rs.getString( "ORIGINAL_DATE_CONFIRMED" ).trim();
        String quantity_received=rs.getString( "QUANTITY_RECEIVED" ) == null ? "" : rs.getString( "QUANTITY_RECEIVED" ).trim();
        String buyername=rs.getString( "BUYER_NAME" ) == null ? "" : rs.getString( "BUYER_NAME" ).trim();
        String suppliername=HelpObjs.addescapesForJavascript( rs.getString( "SUPPLIER_NAME" ) == null ? "" : rs.getString( "SUPPLIER_NAME" ).trim() );
        String ship_to_location_id=rs.getString( "SHIP_TO_LOCATION_ID" ) == null ? "" : rs.getString( "SHIP_TO_LOCATION_ID" ).trim();
        String supplier_contact_name=rs.getString( "SUPPLIER_CONTACT_NAME" ) == null ? "" : rs.getString( "SUPPLIER_CONTACT_NAME" ).trim();
        String first_time_received=rs.getString( "FIRST_TIME_RECEIVED" ) == null ? "" : rs.getString( "FIRST_TIME_RECEIVED" ).trim();
        String phone=rs.getString( "PHONE" ) == null ? "" : rs.getString( "PHONE" ).trim();
        String carrier=rs.getString( "CARRIER" ) == null ? "" : rs.getString( "CARRIER" ).trim();
				String currencyid=rs.getString( "CURRENCY_ID" ) == null ? "" : rs.getString( "CURRENCY_ID" ).trim();
				String customerPo=rs.getString( "CUSTOMER_PO" ) == null ? "" : rs.getString( "CUSTOMER_PO" ).trim();

        count+=1;

        String Color=" ";
        if ( count % 2 == 0 )
        {
          Color="CLASS=\\\"Inblue";
        }
        else
        {
          Color="CLASS=\\\"Inwhite";
        }

        if ( ship_to_location_id.equalsIgnoreCase( ShipToLocid ) )
        {
          Color="CLASS=\\\"green";
        }

        //Buyer,Supplier,Order Date not clear,
        poout.println( "insidehtml +=\"<TR>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + buyername + "</TD>\";\n" );

        String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
        String radianpourl=wwwHome + "tcmIS/supply/purchorder?po=" + radian_po + "&Action=searchlike&subUserAction=po";
        
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\"><A onclick=\\\"javascript:window.open('" + radianpourl + "')\\\" STYLE=\\\"color:#0000ff;cursor:hand;text-decoration:underline\\\">" + radian_po + "</A></TD>\";\n" );
        poout.println( "var customerPo = htmlDencode('" + HelpObjs.addescapesForJavascript(customerPo) + "');\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">\"+customerPo+\"</TD>\";\n" );
		poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + ship_to_location_id + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + carrier + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"15%\\\">" + suppliername + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + supplier_contact_name + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + phone + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + quantity + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + date_confirmed + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + orig_date_confirmed + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + unit_price + " "+currencyid+"</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + first_time_received + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + quantity_received + "</TD>\";\n" );

        poout.println( "insidehtml +=\"</TR>\";\n" );
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

    if ( count == 0 )
    {
      poout.println( "insidehtml +=\"<TR>\";\n" );
      poout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"14\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
      poout.println( "insidehtml +=\"</TR>\";\n" );
    }

    poout.println( "insidehtml +=\"</TABLE>\";\n" );
    if ( count == 10 )
    {
      poout.println( "insidehtml +=\"<CENTER><A HREF=\\\"javascript:gettcmBuysmore('" + lineID + "')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\">"+res.getString("label.showall")+"</A></CENTER>\";\n" );
    }

    poout.println( "itemnotescell.innerHTML =insidehtml;\n" );

    //if the use click on add before clicking on the plus this part sets the image to minus and also the underlying hidden elements
    if ( "yes".equalsIgnoreCase( setlinestatus ) )
    {
      poout.println( "var itemnotesimgae = opener.document.getElementById(\"tcmbuyimg" + lineID + "\");\n" );
      poout.println( "itemnotesimgae.setAttribute(\"src\",'/images/minus.jpg');\n" );

      poout.println( "var itemnotestatus  =  opener.document.getElementById(\"tcmbuystatus" + lineID + "\");\n" );
      poout.println( "itemnotestatus.value = \"Yes\";\n" );
    }

    poout.println( " }\n" );
    //return Msgtemp;
  }
  
  public static void buildsendFlowdowns( TcmISDBModel dbObj2,String setlinestatus,String lineID,String itemID,String radianpo,
          String radianbpo,String shipToLoc,String ammenNum,String shiptocompanyid,
          PrintWriter poout,ResourceLibrary res)
  {
	  buildsendFlowdowns( dbObj2,setlinestatus, lineID, itemID, radianpo, radianbpo, shipToLoc, ammenNum, shiptocompanyid, poout, res, false);
  }

  public static void buildsendFlowdowns( TcmISDBModel dbObj2,String setlinestatus,String lineID,String itemID,String radianpo,
                                                 String radianbpo,String shipToLoc,String ammenNum,String shiptocompanyid,
                                                 PrintWriter poout,ResourceLibrary res, boolean dataTransferStatusClosed )
  {
    Connection connect1=null;
    DBResultSet dbrs=null;
    CallableStatement cs=null;
    ResultSet flowRs=null;
    //StringBuffer Msgtemp=new StringBuffer();
    int count=0;

    poout.println( "{\n" );

    //Won't show anything if the po or bpo is not a valid number
    try
    {
      connect1=dbObj2.getConnection();
      //int rowNumber=Integer.parseInt( lineID ) * 1000;
      BigDecimal rowNumber= new BigDecimal(lineID);
      rowNumber = rowNumber.multiply(new BigDecimal("1000"));
      polog2.info("Calling from pkg_po.Po_flowdown.....");
      cs=connect1.prepareCall( "{call pkg_po.Po_flowdown(?,?,?,?,?,?,?)}" );
      cs.setString(1,shipToLoc);
      if (shiptocompanyid.length() > 0){cs.setString(2, shiptocompanyid);}else{cs.setNull(2, OracleTypes.VARCHAR);}
      if (itemID.length() > 0){cs.setInt(3, Integer.parseInt(itemID));}else{cs.setNull(1, OracleTypes.INTEGER);}
	  if (radianpo.length() > 0){BigDecimal bigponumber = new BigDecimal(radianpo);cs.setBigDecimal( 4,bigponumber );}
      else if (radianbpo.length() > 0){BigDecimal bigbponumber = new BigDecimal(radianbpo);cs.setBigDecimal( 4,bigbponumber );}
      else{cs.setNull(4, OracleTypes.INTEGER);}
      if (lineID.length() > 0){cs.setBigDecimal(5, rowNumber);}else{cs.setNull(1, OracleTypes.INTEGER);}
      if (ammenNum.length() > 0){cs.setInt(6, Integer.parseInt(ammenNum));}else{cs.setNull(6, OracleTypes.INTEGER);}
      cs.registerOutParameter( 7,OracleTypes.VARCHAR );
      cs.execute();
      polog2.info("Getting query from pkg_po.Po_flowdown.....");
      String resultQuery=cs.getObject( 7 ).toString();
      dbrs=dbObj2.doQuery( resultQuery );
      flowRs=dbrs.getResultSet();
      polog2.info("Done query from pkg_po.Po_flowdown.....");        

      poout.println( "var itemnotescell = opener.document.getElementById(\"row7detaildivrow" + lineID + "" + lineID + "\");\n" );
      poout.println( "var insidehtml =\"<TABLE ID=\\\"flowdowntable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
      poout.println( "insidehtml +=\"<TR>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"2%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.ok")+"</B></TH>\";\n" );
      //poout.println( "insidehtml +=\"<TH WIDTH=\\\"2%\\\" HEIGHT=\\\"25\\\"><B>Attach</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.flowdown")+"</B></TH>\";\n" );
	  poout.println( "insidehtml +=\"<TH WIDTH=\\\"2%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.revdate")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.description")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.catalog")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.company")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.type")+"</B></TH>\";\n" );
      poout.println( "insidehtml +=\"</TR>\";\n" );

      int i=0;
      while ( flowRs.next() )
      {
    	String flow_down_type = flowRs.getString( "FLOW_DOWN_TYPE" );
    	if(flow_down_type != null && flow_down_type.equalsIgnoreCase("receiving"))
    		  continue;
        String cat_part_no="";
        String company_id=flowRs.getString( "COMPANY_ID" ) == null ? "" : flowRs.getString( "COMPANY_ID" ).trim();
        String catalog_id=flowRs.getString( "CATALOG_ID" ) == null ? "" : flowRs.getString( "CATALOG_ID" ).trim();
        String flow_down=flowRs.getString( "FLOW_DOWN" ) == null ? "" : flowRs.getString( "FLOW_DOWN" ).trim();
        String flow_down_desc=HelpObjs.addescapesForJavascript( flowRs.getString( "FLOW_DOWN_DESC" ) == null ? "" :flowRs.getString( "FLOW_DOWN_DESC" ).trim() );
        String content=flowRs.getString( "CONTENT" ) == null ? "" : flowRs.getString( "CONTENT" ).trim();
        String on_line=flowRs.getString( "ON_LINE" ) == null ? "" : flowRs.getString( "ON_LINE" ).trim();
        String attach=flowRs.getString( "ATTACH" ) == null ? "" : flowRs.getString( "ATTACH" ).trim();
        String ok=flowRs.getString( "OK" ) == null ? "" : flowRs.getString( "OK" ).trim();
        String color=flowRs.getString( "COLOR" ) == null ? "" : flowRs.getString( "COLOR" ).trim();
  	    String revdate =flowRs.getString( "REVISION_DATE" ) == null ? "" : flowRs.getString( "REVISION_DATE" ).trim();

		count+=1;
		String Color=" ";
		if ( "1".equalsIgnoreCase( color ) )
		{
		  Color="CLASS=\\\"green";
		}
		else if ( "2".equalsIgnoreCase( color ) )
		{
		  Color="CLASS=\\\"yellow";
		}
		else if ( "3".equalsIgnoreCase( color ) )
		{
		  Color="CLASS=\\\"orange";
		}
		else if ( "4".equalsIgnoreCase( color ) )
		{
		  Color="CLASS=\\\"red";
		}
		else if ( "5".equalsIgnoreCase( color ) )
		{
		  Color="CLASS=\\\"purple";
		}
		else
		{
		  Color="CLASS=\\\"Inwhite";
		}

        poout.println( "insidehtml +=\"<TR>\";\n" );
        
        if (dataTransferStatusClosed)
	  	{
	  		  /*poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"2%\\\">\";\n" );
	  		  
	  		  if(ok.equalsIgnoreCase( "Y" ))
	  			  poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
	  		  
	  		  poout.println(" insidehtml +=\"</TD>\";\n");
	  		  */
        	poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"2%\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"flowokchk" + lineID + "_" + i + "\\\" onClick=\\\"setlineUnconfirmed('" + lineID + "')\\\" " + ( ok.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + " disabled></TD>\";\n" );
            
	  	}
        else
        	poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"2%\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"flowokchk" + lineID + "_" + i + "\\\" onClick=\\\"setlineUnconfirmed('" + lineID + "')\\\" " + ( ok.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "></TD>\";\n" );
        
        if ( "Y".equalsIgnoreCase( on_line ) && content.length() > 0 )
        {
          poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\"><A onclick=\\\"javascript:window.open('" + content + "')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\"><U>" + flow_down + "</U></A></TD>\";\n" );
        }
        else
        {
          poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + flow_down + "</TD>\";\n" );
        }

        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"2%\\\">" + revdate + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"15%\\\">" + flow_down_desc + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + catalog_id + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + company_id + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + flow_down_type + "</TD>\";\n" );
        poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"flowdownid" + lineID + "_" + i + "\\\" value=\\\"" + flow_down + "\\\">\";\n" );
        poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"flowdowncompanyid" + lineID + "_" + i + "\\\" value=\\\"" + company_id + "\\\">\";\n" );
        poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"flowdowncatalogid" + lineID + "_" + i + "\\\" value=\\\"" + catalog_id + "\\\">\";\n" );
        poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"flowdowncatpartno" + lineID + "_" + i + "\\\" value=\\\"" + cat_part_no + "\\\">\";\n" );
        poout.println( "insidehtml +=\"</TR>\";\n" );

        i++;
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
    finally
    {
      if ( cs != null )
      {
        try
        {
          cs.close();
        }
        catch ( SQLException se )
        {}
      }
      if ( dbrs != null )
      {
        dbrs.close();
      }
    }

    if ( count == 0 )
    {
      poout.println( "insidehtml +=\"<TR>\";\n" );
      poout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"7\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
      poout.println( "insidehtml +=\"</TR>\";\n" );
    }

    poout.println( "insidehtml +=\"</TABLE>\";\n" );
    poout.println( "itemnotescell.innerHTML =insidehtml;\n" );
    poout.println( "noofflowdowns = opener.document.getElementById(\"noofflowdownsdivrow" + lineID + "" + lineID + "\");\n" );
    poout.println( "noofflowdowns.value = \"" + count + "\";\n" );

    //if the use click on add before clicking on the plus this part sets the image to minus and also the underlying hidden elements
    if ( "yes".equalsIgnoreCase( setlinestatus ) )
    {
      poout.println( "var itemnotesimgae = opener.document.getElementById(\"flowdownimg" + lineID + "\");\n" );
      poout.println( "itemnotesimgae.setAttribute(\"src\",'/images/minus.jpg');\n" );

      poout.println( "var itemnotestatus  =  opener.document.getElementById(\"flowdownstatus" + lineID + "\");\n" );
      poout.println( "itemnotestatus.value = \"Yes\";\n" );
    }

    flowRs=null;
    poout.println( "}\n" );
    return;
  }
  
  public static void buildsendSpecs( TcmISDBModel dbObj6,String setlinestatus,String lineID,String itemID,String radianpo,String radianbpo,
          							String shipToLoc,String ammenNum,String shiptocompanyid,String invengrp, String opsEntityId, 
          							PrintWriter poout,boolean specOverRide,ResourceLibrary res)
  {
	  buildsendSpecs(dbObj6, setlinestatus, lineID, itemID, radianpo, radianbpo, shipToLoc, ammenNum, shiptocompanyid, invengrp, 
			  		 opsEntityId, poout, specOverRide, res, false);	  
  }

  public static void buildsendSpecs( TcmISDBModel dbObj6,String setlinestatus,String lineID,String itemID,String radianpo,String radianbpo,
                                     String shipToLoc,String ammenNum,String shiptocompanyid,String invengrp, String opsEntityId, 
                                     PrintWriter poout,boolean specOverRide,ResourceLibrary res, boolean dataTransferStatusClosed)
  {
    DBResultSet dbrs=null;
    //StringBuffer Msgtemp=new StringBuffer();
    Connection connect1=null;
    CallableStatement cs=null;
    ResultSet specrs=null;
    poout.println( "{\n" );
    int count=0;
    String genericCoc="";
    String genericCoa="";
    ResultSet rsGenCocData=null;
    //int rowNumber=Integer.parseInt( lineID ) * 1000;
    BigDecimal rowNumber= new BigDecimal(lineID);
    rowNumber = rowNumber.multiply(new BigDecimal("1000"));

    /*try
    {
      if ( radianpo.length() > 0 )
      {
        dbrs=dbObj6.doQuery( "Select GENERIC_COC,GENERIC_COA from po_line_detail_view where RADIAN_PO = " + radianpo + " and PO_LINE = " + rowNumber + " and AMENDMENT=" + ammenNum + " " );
      }
      else
      {
        dbrs=dbObj6.doQuery( "Select GENERIC_COC,GENERIC_COA from bpo_line_detail_view where BPO = " + radianbpo + " and BPO_LINE = " + rowNumber + " and AMENDMENT=" + ammenNum + " " );
      }

      rsGenCocData=dbrs.getResultSet();

      while ( rsGenCocData.next() )
      {
        genericCoc=rsGenCocData.getString( "GENERIC_COC" ) == null ? "" : rsGenCocData.getString( "GENERIC_COC" ).trim();
        genericCoa=rsGenCocData.getString( "GENERIC_COA" ) == null ? "" : rsGenCocData.getString( "GENERIC_COA" ).trim();
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
    }*/

    try
    {
      connect1=dbObj6.getConnection();
    polog2.info("Calling pkg_po.Po_spec.....");
    cs=connect1.prepareCall( "{call pkg_po.Po_spec(?,?,?,?,?,?,?,?)}" );

    cs.setString(1,shipToLoc);
    if (shiptocompanyid.length() > 0){cs.setString(2, shiptocompanyid);}else{cs.setNull(2, OracleTypes.VARCHAR);}

    if (itemID.length() > 0){cs.setInt(3, Integer.parseInt(itemID));}else{cs.setNull(1, OracleTypes.INTEGER);}

    if (radianpo.length() > 0){BigDecimal bigponumber = new BigDecimal(radianpo);cs.setBigDecimal( 4,bigponumber );}
    else if (radianbpo.length() > 0){BigDecimal bigbponumber = new BigDecimal(radianbpo);cs.setBigDecimal( 4,bigbponumber );}
    else{cs.setNull(4, OracleTypes.INTEGER);}

    if (lineID.length() > 0){cs.setBigDecimal(5, rowNumber);}else{cs.setNull(1, OracleTypes.INTEGER);}

    if (ammenNum.length() > 0){cs.setInt(6, Integer.parseInt(ammenNum));}else{cs.setNull(6, OracleTypes.INTEGER);}
    if (invengrp.length() > 0){cs.setString(7, invengrp);}else{cs.setNull(7, OracleTypes.VARCHAR);}

    cs.registerOutParameter( 8,OracleTypes.VARCHAR );
    cs.execute();

    String resultQuery=cs.getObject( 8 ).toString();
    polog2.info("Run query from pkg_po.Po_spec.....");
    dbrs=dbObj6.doQuery( resultQuery );
    specrs=dbrs.getResultSet();
    polog2.info("Done query from pkg_po.Po_spec.....");

    poout.println( "var itemnotescell = opener.document.getElementById(\"row6detaildivrow" + lineID + "" + lineID + "\");\n" );
    poout.println( "var insidehtml =\"<TABLE ID=\\\"spectable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );

    /*poout.println( "insidehtml +=\"<TR>\";\n" );
    poout.println( "insidehtml +=\"<TD WIDTH=\\\"2%\\\" HEIGHT=\\\"25\\\">&nbsp;</TD>\";\n" );
    poout.println( "insidehtml +=\"<TD WIDTH=\\\"2%\\\" HEIGHT=\\\"25\\\">&nbsp;</TD>\";\n" );
    poout.println( "insidehtml +=\"<TD WIDTH=\\\"2%\\\" HEIGHT=\\\"25\\\">&nbsp;</TD>\";\n" );
    poout.println( "insidehtml +=\"<TD WIDTH=\\\"2%\\\" HEIGHT=\\\"25\\\">&nbsp;</TD>\";\n" );
    poout.println("insidehtml +=\"<TD WIDTH=\\\"25%\\\" ALIGN=\\\"CENTER\\\" HEIGHT=\\\"25\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"cocforflow" + lineID + "\\\" onClick=\\\"checkflowChecks('" + lineID + "')\\\" " + ( genericCoc.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + ">&nbsp;&nbsp;<B>Generic COC</B></TD>\";\n" );
    poout.println("insidehtml +=\"<TD WIDTH=\\\"25%\\\" ALIGN=\\\"CENTER\\\" HEIGHT=\\\"25\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"coaforflow" + lineID + "\\\" onClick=\\\"checkflowflowChecks('" + lineID + "')\\\" " + ( genericCoa.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + ">&nbsp;&nbsp;<B>Generic COA</B></TD>\";\n" );
    poout.println( "insidehtml +=\"</TR>\";\n" );*/
    poout.println( "insidehtml +=\"<TR>\";\n" );
    //poout.println( "insidehtml +=\"<TH WIDTH=\\\"2%\\\" ROWSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>OK</B></TH>\";\n" );
    //poout.println( "insidehtml +=\"<TH WIDTH=\\\"2%\\\" ROWSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>Attach</B></TH>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"8%\\\" COLSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.saved")+"</B></TH>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"20%\\\" ROWSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("catalogspec.label.spec")+"</B></TH>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"10%\\\" ROWSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.speclibrary")+"</B></TH>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"20%\\\" COLSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.curreq")+"</B></TH>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"20%\\\" COLSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.reqlastsav")+"</B></TH>\";\n" );

		//poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" ROWSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>Reqt @ Conf</B></TH>\";\n" );
		//poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" ROWSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>Status</B></TH>\";\n" );
    //poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" ROWSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>Company</B></TH>\";\n" );
		poout.println( "insidehtml +=\"</TR>\";\n" );

	 		poout.println( "insidehtml +=\"<TR>\";\n" );
	 poout.println( "insidehtml +=\"<TH WIDTH=\\\"4%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coc")+"</B></TH>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"4%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coa")+"</B></TH>\";\n" );
		//poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" COLSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>Current Requirements</B></TH>\";\n" );
		//poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" COLSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>Requirements @ Last Confirmation</B></TH>\";\n" );
//poout.println( "insidehtml +=\"</TR>\";\n" );


		//poout.println( "insidehtml +=\"<TR>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"7%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coc")+"</B></TH>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"7%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coa")+"</B></TH>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"7%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coc")+"</B></TH>\";\n" );
		poout.println( "insidehtml +=\"<TH WIDTH=\\\"7%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coa")+"</B></TH>\";\n" );
		poout.println( "insidehtml +=\"</TR>\";\n" );

    int i=0;
    while ( specrs.next() )
    {
      String cat_part_no="";
      String catalog_id="";
      String spec_id=specrs.getString( "SPEC_ID" ) == null ? "" : specrs.getString( "SPEC_ID" ).trim();
      //String company_id=specrs.getString( "COMPANY_ID" ) == null ? "" : specrs.getString( "COMPANY_ID" ).trim();
      String spec_library_desc=specrs.getString( "SPEC_LIBRARY_DESC" ) == null ? "" : specrs.getString( "SPEC_LIBRARY_DESC" ).trim();
      String content=specrs.getString( "CONTENT" ) == null ? "" : specrs.getString( "CONTENT" ).trim();
      String on_line=specrs.getString( "ON_LINE" ) == null ? "" : specrs.getString( "ON_LINE" ).trim();
      String currentCocRequirement=specrs.getString( "CURRENT_COC_REQUIREMENT" ) == null ? "" : specrs.getString( "CURRENT_COC_REQUIREMENT" ).trim();
      String currentCoaRequirement=specrs.getString( "CURRENT_COA_REQUIREMENT" ) == null ? "" : specrs.getString( "CURRENT_COA_REQUIREMENT" ).trim();
			String savedCoc=specrs.getString( "SAVED_COC" ) == null ? "" : specrs.getString( "SAVED_COC" ).trim();
      String savedCoa=specrs.getString( "SAVED_COA" ) == null ? "" : specrs.getString( "SAVED_COA" ).trim();
			String cocReqAtSave=specrs.getString( "COC_REQ_AT_SAVE" ) == null ? "" : specrs.getString( "COC_REQ_AT_SAVE" ).trim();
      String coaReqAtSave=specrs.getString( "COA_REQ_AT_SAVE" ) == null ? "" : specrs.getString( "COA_REQ_AT_SAVE" ).trim();
      //String attach=specrs.getString( "ATTACH" ) == null ? "" : specrs.getString( "ATTACH" ).trim();
      //String ok=specrs.getString( "OK" ) == null ? "" : specrs.getString( "OK" ).trim();
      String spec_library=specrs.getString( "SPEC_LIBRARY" ) == null ? "" : specrs.getString( "SPEC_LIBRARY" ).trim();
      String color=specrs.getString( "COLOR" ) == null ? "" : specrs.getString( "COLOR" ).trim();
			String colorAtsave=specrs.getString( "COLOR_AT_SAVE" ) == null ? "" : specrs.getString( "COLOR_AT_SAVE" ).trim();

      //String certreq =specrs.getString( "CERT_REQUIRED_CURRENT" ) == null ? "" : specrs.getString( "CERT_REQUIRED_CURRENT" ).trim();
			//String certreqatsave =specrs.getString( "CERT_REQUIRED_AT_SAVE" ) == null ? "" : specrs.getString( "CERT_REQUIRED_AT_SAVE" ).trim();
			//String ceractive =specrs.getString( "STATUS" ) == null ? "" : specrs.getString( "STATUS" ).trim();
			//String certLastModified=specrs.getString( "DATE_LAST_CHANGED" ) == null ? "" : specrs.getString( "DATE_LAST_CHANGED" ).trim();
			/*Date certLastModified =specrs.getDate( "DATE_LAST_CHANGED" );
			String formattedCertLastModified = "";
			 if (certLastModified != null)
			 {
				SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
				formattedCertLastModified = dateFormatter.format(certLastModified);
			 }*/
			String spec_id_display =specrs.getString( "SPEC_ID_DISPLAY" ) == null ? "" : specrs.getString( "SPEC_ID_DISPLAY" ).trim();
			String spec_detail =specrs.getString( "DETAIL" ) == null ? "" : specrs.getString( "DETAIL" ).trim();

			/*if (!("Active".equalsIgnoreCase(ceractive)))
			{
			 certreq = "N";
			}*/

      count+=1;
      String tdColor=" ";
      if ( "1".equalsIgnoreCase( color ) )
      {
        tdColor="CLASS=\\\"green";
      }
      else if ( "2".equalsIgnoreCase( color ) )
      {
        tdColor="CLASS=\\\"yellow";
      }
      else if ( "3".equalsIgnoreCase( color ) )
      {
        tdColor="CLASS=\\\"orange";
      }
      else if ( "4".equalsIgnoreCase( color ) )
      {
        tdColor="CLASS=\\\"red";
      }
      else if ( "5".equalsIgnoreCase( color ) )
      {
        tdColor="CLASS=\\\"purple";
      }
      else
      {
        tdColor="CLASS=\\\"Inwhite";
      }

			String tdColorAtsave=" ";
			if ( "1".equalsIgnoreCase( colorAtsave ) )
			{
			 tdColorAtsave="CLASS=\\\"green";
			}
			else if ( "2".equalsIgnoreCase( colorAtsave ) )
			{
			 tdColorAtsave="CLASS=\\\"yellow";
			}
			else if ( "3".equalsIgnoreCase( colorAtsave ) )
			{
			 tdColorAtsave="CLASS=\\\"orange";
			}
			else if ( "4".equalsIgnoreCase( colorAtsave ) )
			{
			 tdColorAtsave="CLASS=\\\"red";
			}
			else if ( "5".equalsIgnoreCase( colorAtsave ) )
			{
			 tdColorAtsave="CLASS=\\\"purple";
			}
			else
			{
			 tdColorAtsave="CLASS=\\\"Inwhite";
			}

      poout.println( "insidehtml +=\"<TR>\";\n" );
      
      if (dataTransferStatusClosed)
	  {
		  /*poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"7%\\\">\";\n" );
		  
		  if(savedCoc.equalsIgnoreCase( "Y" ))
			  poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
		  
		  poout.println(" insidehtml +=\"</TD>\";\n");
		  */
		  poout.println( "insidehtml +=\"<TD " + tdColor + "l\\\" WIDTH=\\\"4%\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"speccocchk" + lineID + "_" + i + "\\\" onClick=\\\"checkSpecChecks('" + lineID + "','" + i + "')\\\" " + ( savedCoc.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + " disabled></TD>\";\n" );
	  }
	  else
		  poout.println( "insidehtml +=\"<TD " + tdColor + "l\\\" WIDTH=\\\"4%\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"speccocchk" + lineID + "_" + i + "\\\" onClick=\\\"checkSpecChecks('" + lineID + "','" + i + "')\\\" " + ( savedCoc.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "></TD>\";\n" );
		 
	  if (dataTransferStatusClosed)
	  {
		  /*
		  poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"7%\\\">\";\n" );
		  
		  if(savedCoa.equalsIgnoreCase( "Y" ))
			  poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
		  
		  poout.println(" insidehtml +=\"</TD>\";\n");
		  */
		  poout.println( "insidehtml +=\"<TD " + tdColor + "l\\\" WIDTH=\\\"4%\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"speccoachk" + lineID + "_" + i + "\\\" onClick=\\\"checkFlowChecks('" + lineID + "','" + i + "')\\\" " + ( savedCoa.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + " disabled></TD>\";\n" );			 
	  }
	  else
		  poout.println( "insidehtml +=\"<TD " + tdColor + "l\\\" WIDTH=\\\"4%\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"speccoachk" + lineID + "_" + i + "\\\" onClick=\\\"checkFlowChecks('" + lineID + "','" + i + "')\\\" " + ( savedCoa.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "></TD>\";\n" );
	 

		if ("Y".equalsIgnoreCase(on_line) && content.length() > 0) {
			if (content.startsWith("http")) {
				String[] words = content.split("/");
				content = words[words.length - 1];
			}
			poout.println("insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"20%\\\"><A onclick=\\\"javascript:window.open('../haas/docViewer.do?uAction=viewSpec&fileName=" + content
					+ "&opsEntityId="+opsEntityId+"&inventoryGroup="+invengrp+"&spec="+spec_id
					+ "')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\"><U>" + spec_id_display + "</U></A>\";\n");
		}
      else
      {
        poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"20%\\\">" + spec_id_display + "\";\n" );
      }

		  /*if (formattedCertLastModified.trim().length() > 0)
			{
			 poout.println(" insidehtml +=\" &#40;"+formattedCertLastModified+"&#41;\";");
			}*/
			poout.println(" insidehtml +=\"</TD>\";\n");

     poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"10%\\\">" + spec_library_desc + "</TD>\";\n" );

		 poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"7%\\\">\";\n" );
		 if ("Y".equalsIgnoreCase(currentCocRequirement))
		 {
			poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
		 }
		 else
		 {
			//poout.println(" insidehtml +=\"<img src=/images/item_chk0.gif border=0 alt=No align=>\";\n");
		 }
		 poout.println(" insidehtml +=\"</TD>\";\n");


		 poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"7%\\\">\";\n" );
		 if ("Y".equalsIgnoreCase(currentCoaRequirement))
		 {
			poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
		 }
		 else
		 {
			//poout.println(" insidehtml +=\"<img src=/images/item_chk0.gif border=0 alt=No align=>\";\n");
		 }
		 poout.println(" insidehtml +=\"</TD>\";\n");


		 poout.println( "insidehtml +=\"<TD " + tdColorAtsave + "\\\" WIDTH=\\\"7%\\\">\";\n" );
		 if ("Y".equalsIgnoreCase(cocReqAtSave))
		 {
			poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
		 }
		 else
		 {
			//poout.println(" insidehtml +=\"<img src=/images/item_chk0.gif border=0 alt=No align=>\";\n");
		 }
		 poout.println(" insidehtml +=\"</TD>\";\n");


		 poout.println( "insidehtml +=\"<TD " + tdColorAtsave + "\\\" WIDTH=\\\"7%\\\">\";\n" );
		 if ("Y".equalsIgnoreCase(coaReqAtSave))
		 {
			poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
		 }
		 else
		 {
			//poout.println(" insidehtml +=\"<img src=/images/item_chk0.gif border=0 alt=No align=>\";\n");
		 }
		 poout.println(" insidehtml +=\"</TD>\";\n");


      //poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + company_id + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdownid" + lineID + "_" + i + "\\\" value=\\\"" + spec_id + "\\\">\";\n" );
			poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdowndetail" + lineID + "_" + i + "\\\" value=\\\"" + spec_detail + "\\\">\";\n" );
      //poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdowncompanyid" + lineID + "_" + i + "\\\" value=\\\"" + company_id +  "\\\">\";\n" );
      poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdowncatalogid" + lineID + "_" + i + "\\\" value=\\\"" + catalog_id +  "\\\">\";\n" );
      poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdowncatpartno" + lineID + "_" + i + "\\\" value=\\\"" + cat_part_no + "\\\">\";\n" );
      poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdownspeclibrary" + lineID + "_" + i + "\\\" value=\\\"" + spec_library + "\\\">\";\n" );
			poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specCurrentCocRequirement" + lineID + "_" + i + "\\\" value=\\\"" + currentCocRequirement + "\\\">\";\n" );
			poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specCurrentCoaRequirement" + lineID + "_" + i + "\\\" value=\\\"" + currentCoaRequirement + "\\\">\";\n" );
			poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specColor" + lineID + "_" + i + "\\\" value=\\\"" + color + "\\\">\";\n" );
      poout.println( "insidehtml +=\"</TR>\";\n" );

      i++;
    }
  }
  catch ( Exception e )
  {
    e.printStackTrace();
  }
  finally
  {
    if ( cs != null )
    {
      try
      {
        cs.close();
      }
      catch ( SQLException se )
      {}
    }
    if ( dbrs != null )
    {
      dbrs.close();
    }
  }

  if ( count == 0 )
  {
    poout.println( "insidehtml +=\"<TR>\";\n" );
    poout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"11\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
    poout.println( "insidehtml +=\"</TR>\";\n" );
  }

  poout.println( "insidehtml +=\"</TABLE>\";\n" );
  poout.println( "itemnotescell.innerHTML =insidehtml;\n" );
  poout.println( "noofspecs = opener.document.getElementById(\"noofspecsdivrow" + lineID + "" + lineID + "\");\n" );
  poout.println( "noofspecs.value = \"" + count + "\";\n" );

  //if the use click on add before clicking on the plus this part sets the image to minus and also the underlying hidden elements
  if ( "yes".equalsIgnoreCase( setlinestatus ) )
  {
    poout.println( "var itemnotesimgae = opener.document.getElementById(\"specimg" + lineID + "\");\n" );
    poout.println( "itemnotesimgae.setAttribute(\"src\",'/images/minus.jpg');\n" );
    poout.println( "var itemnotestatus  =  opener.document.getElementById(\"specstatus" + lineID + "\");\n" );
    poout.println( "itemnotestatus.value = \"Yes\";\n" );
  }

  poout.println( "}\n" );
  specrs=null;
  return;
  }

public static void buildsendSpecs( TcmISDBModel dbObj6,String setlinestatus,String lineID,String itemID,String radianpo,String radianbpo,
                                             String shipToLoc,String ammenNum,String shiptocompanyid,String invengrp,PrintWriter poout,boolean specOverRide,Vector data,ResourceLibrary res)
  {
    poout.println( "{\n" );
    int count=0;
    BigDecimal rowNumber= new BigDecimal(lineID);
    rowNumber = rowNumber.multiply(new BigDecimal("1000"));

    Hashtable summary = new Hashtable();
    summary = (Hashtable) data.elementAt(0);
    int total = ((Integer) (summary.get("TOTAL_NUMBER"))).intValue();

    try
    {
    poout.println( "var itemnotescell = opener.document.getElementById(\"row6detaildivrow" + lineID + "" + lineID + "\");\n" );
    poout.println( "var insidehtml =\"<TABLE ID=\\\"spectable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
    poout.println( "insidehtml +=\"<TR>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"8%\\\" COLSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.saved")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"<TH WIDTH=\\\"20%\\\" ROWSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("catalogspec.label.spec")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"<TH WIDTH=\\\"10%\\\" ROWSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.speclibrary")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"<TH WIDTH=\\\"20%\\\" COLSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.curreq")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"<TH WIDTH=\\\"20%\\\" COLSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.reqlastsav")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"</TR>\";\n" );

	poout.println( "insidehtml +=\"<TR>\";\n" );
    poout.println( "insidehtml +=\"<TH WIDTH=\\\"4%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coc")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"<TH WIDTH=\\\"4%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coa")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"<TH WIDTH=\\\"7%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coc")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"<TH WIDTH=\\\"7%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coa")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"<TH WIDTH=\\\"7%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coc")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"<TH WIDTH=\\\"7%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.coa")+"</B></TH>\";\n" );
	poout.println( "insidehtml +=\"</TR>\";\n" );

    System.out.println("total  "+total);
    int i = 0; //used for detail lines
    for (int loop = 0; loop < total; loop++)
    {
	  i++;
      Hashtable hD = new Hashtable();
      String resultRadianPO ="";
      String resulrPoLine ="";
      String resultAmendment ="";
      hD = (Hashtable) data.elementAt(i);
      resultRadianPO = hD.get( "RADIAN_PO")==null?"":hD.get( "RADIAN_PO").toString();
      resulrPoLine = hD.get( "PO_LINE")==null?"":hD.get( "PO_LINE").toString();
      resultAmendment = hD.get( "AMENDMENT")==null?"":hD.get( "AMENDMENT").toString();
      //System.out.println("resulrPoLine  "+resulrPoLine+" rowNumber"+rowNumber);
      //System.out.println("resultAmendment  "+resultAmendment+" ammenNum"+ammenNum);
      //polog.info("i "+i+" resultItemId "+resultItemId);
      if (resultRadianPO.equalsIgnoreCase(radianpo) && resulrPoLine.equalsIgnoreCase(""+rowNumber+"") && resultAmendment.equalsIgnoreCase(ammenNum))
      {
      String cat_part_no="";
      String catalog_id="";
      String spec_id=hD.get( "SPEC_ID" ) == null ? "" : hD.get( "SPEC_ID" ).toString();
      //String company_id=hD.get( "COMPANY_ID" ) == null ? "" : hD.get( "COMPANY_ID" ).toString();
      String spec_library_desc=hD.get( "SPEC_LIBRARY_DESC" ) == null ? "" : hD.get( "SPEC_LIBRARY_DESC" ).toString();
      String content=hD.get( "CONTENT" ) == null ? "" : hD.get( "CONTENT" ).toString();
      String on_line=hD.get( "ON_LINE" ) == null ? "" : hD.get( "ON_LINE" ).toString();
      String currentCocRequirement=hD.get( "CURRENT_COC_REQUIREMENT" ) == null ? "" : hD.get( "CURRENT_COC_REQUIREMENT" ).toString();
      String currentCoaRequirement=hD.get( "CURRENT_COA_REQUIREMENT" ) == null ? "" : hD.get( "CURRENT_COA_REQUIREMENT" ).toString();
	  String savedCoc=hD.get( "SAVED_COC" ) == null ? "" : hD.get( "SAVED_COC" ).toString();
      String savedCoa=hD.get( "SAVED_COA" ) == null ? "" : hD.get( "SAVED_COA" ).toString();
	  String cocReqAtSave=hD.get( "COC_REQ_AT_SAVE" ) == null ? "" : hD.get( "COC_REQ_AT_SAVE" ).toString();
      String coaReqAtSave=hD.get( "COA_REQ_AT_SAVE" ) == null ? "" : hD.get( "COA_REQ_AT_SAVE" ).toString();
      String spec_library=hD.get( "SPEC_LIBRARY" ) == null ? "" : hD.get( "SPEC_LIBRARY" ).toString();
      String color=hD.get( "COLOR" ) == null ? "" : hD.get( "COLOR" ).toString();
	  String colorAtsave=hD.get( "COLOR_AT_SAVE" ) == null ? "" : hD.get( "COLOR_AT_SAVE" ).toString();
	  String spec_id_display =hD.get( "SPEC_ID_DISPLAY" ) == null ? "" : hD.get( "SPEC_ID_DISPLAY" ).toString();
	  String spec_detail =hD.get( "DETAIL" ) == null ? "" : hD.get( "DETAIL" ).toString();

      count+=1;
      String tdColor=" ";
      if ( "1".equalsIgnoreCase( color ) )
      {
        tdColor="CLASS=\\\"green";
      }
      else if ( "2".equalsIgnoreCase( color ) )
      {
        tdColor="CLASS=\\\"yellow";
      }
      else if ( "3".equalsIgnoreCase( color ) )
      {
        tdColor="CLASS=\\\"orange";
      }
      else if ( "4".equalsIgnoreCase( color ) )
      {
        tdColor="CLASS=\\\"red";
      }
      else if ( "5".equalsIgnoreCase( color ) )
      {
        tdColor="CLASS=\\\"purple";
      }
      else
      {
        tdColor="CLASS=\\\"Inwhite";
      }

			String tdColorAtsave=" ";
			if ( "1".equalsIgnoreCase( colorAtsave ) )
			{
			 tdColorAtsave="CLASS=\\\"green";
			}
			else if ( "2".equalsIgnoreCase( colorAtsave ) )
			{
			 tdColorAtsave="CLASS=\\\"yellow";
			}
			else if ( "3".equalsIgnoreCase( colorAtsave ) )
			{
			 tdColorAtsave="CLASS=\\\"orange";
			}
			else if ( "4".equalsIgnoreCase( colorAtsave ) )
			{
			 tdColorAtsave="CLASS=\\\"red";
			}
			else if ( "5".equalsIgnoreCase( colorAtsave ) )
			{
			 tdColorAtsave="CLASS=\\\"purple";
			}
			else
			{
			 tdColorAtsave="CLASS=\\\"Inwhite";
			}

      poout.println( "insidehtml +=\"<TR>\";\n" );
			/*if ( !specOverRide && ("1".equalsIgnoreCase( color ) || "2".equalsIgnoreCase( color )) )
			{
			 poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"2%\\\"><INPUT TYPE=\\\"hidden\\\" value=\\\"" + ( ok.equalsIgnoreCase( "Y" ) ? "Yes" : "" ) + "\\\" NAME=\\\"specokchk" + lineID + "" + i + "\\\"> " + ( ok.equalsIgnoreCase( "Y" ) ? "<img src=\\\"/images/item_chk1_dis.gif\\\" border=0 align=\\\"left\\\">" : "<img src=\\\"/images/item_chk0_dis.gif\\\" border=0 align=\\\"left\\\">" ) + "</TD>\";\n" );
			 poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"2%\\\"><INPUT TYPE=\\\"hidden\\\" value=\\\"" + ( ok.equalsIgnoreCase( "Y" ) ? "Yes" : "" ) + "\\\" NAME=\\\"specattachchk" + lineID + "" + i + "\\\"> " + ( attach.equalsIgnoreCase( "Y" ) ? "<img src=\\\"/images/item_chk1_dis.gif\\\" border=0 align=\\\"left\\\">" : "<img src=\\\"/images/item_chk0_dis.gif\\\" border=0 align=\\\"left\\\">" ) + "</TD>\";\n" );
			 poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"2%\\\"><INPUT TYPE=\\\"hidden\\\" value=\\\"" + ( ok.equalsIgnoreCase( "Y" ) ? "Yes" : "" ) + "\\\" NAME=\\\"speccocchk" + lineID + "" + i + "\\\"> " + ( coc.equalsIgnoreCase( "Y" ) ? "<img src=\\\"/images/item_chk1_dis.gif\\\" border=0 align=\\\"left\\\">" : "<img src=\\\"/images/item_chk0_dis.gif\\\" border=0 align=\\\"left\\\">" ) + "</TD>\";\n" );
			 poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"2%\\\"><INPUT TYPE=\\\"hidden\\\" value=\\\"" + ( ok.equalsIgnoreCase( "Y" ) ? "Yes" : "" ) + "\\\" NAME=\\\"speccoachk" + lineID + "" + i + "\\\"> " + ( coa.equalsIgnoreCase( "Y" ) ? "<img src=\\\"/images/item_chk1_dis.gif\\\" border=0 align=\\\"left\\\">" : "<img src=\\\"/images/item_chk0_dis.gif\\\" border=0 align=\\\"left\\\">" ) + "</TD>\";\n" );
			}
			else*/
			{
			 //poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"2%\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"specokchk" + lineID + "_" + i + "\\\" onClick=\\\"setlineUnconfirmed('" + lineID + "')\\\" " + ( ok.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "></TD>\";\n" );
			 //poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"2%\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"specattachchk" + lineID + "_" + i + "\\\" onClick=\\\"setlineUnconfirmed('" + lineID + "')\\\" " + ( attach.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "></TD>\";\n" );
			 poout.println( "insidehtml +=\"<TD " + tdColor + "l\\\" WIDTH=\\\"4%\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"speccocchk" + lineID + "_" + i + "\\\" onClick=\\\"checkSpecChecks('" + lineID + "','" + i + "')\\\" " + ( savedCoc.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "></TD>\";\n" );
			 poout.println( "insidehtml +=\"<TD " + tdColor + "l\\\" WIDTH=\\\"4%\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"speccoachk" + lineID + "_" + i + "\\\" onClick=\\\"checkFlowChecks('" + lineID + "','" + i + "')\\\" " + ( savedCoa.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "></TD>\";\n" );
			}

      if ( "Y".equalsIgnoreCase( on_line ) && content.length() > 0 )
      {
        if ( !content.startsWith( "http" ) )
        {
          String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
          content=wwwHome + "OpSupport/Raytheon/spec_drawings/" + content;
        }
        poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"20%\\\"><A onclick=\\\"javascript:window.open('" + content + "')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\"><U>" + spec_id_display + "</U></A>\";\n" );
      }
      else
      {
        poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"20%\\\">" + spec_id_display + "\";\n" );
      }

		  /*if (formattedCertLastModified.trim().length() > 0)
			{
			 poout.println(" insidehtml +=\" &#40;"+formattedCertLastModified+"&#41;\";");
			}*/
			poout.println(" insidehtml +=\"</TD>\";\n");

     poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"10%\\\">" + spec_library_desc + "</TD>\";\n" );

		 poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"7%\\\">\";\n" );
		 if ("Y".equalsIgnoreCase(currentCocRequirement))
		 {
			poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
		 }
		 else
		 {
			//poout.println(" insidehtml +=\"<img src=/images/item_chk0.gif border=0 alt=No align=>\";\n");
		 }
		 poout.println(" insidehtml +=\"</TD>\";\n");


		 poout.println( "insidehtml +=\"<TD " + tdColor + "\\\" WIDTH=\\\"7%\\\">\";\n" );
		 if ("Y".equalsIgnoreCase(currentCoaRequirement))
		 {
			poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
		 }
		 else
		 {
			//poout.println(" insidehtml +=\"<img src=/images/item_chk0.gif border=0 alt=No align=>\";\n");
		 }
		 poout.println(" insidehtml +=\"</TD>\";\n");


		 poout.println( "insidehtml +=\"<TD " + tdColorAtsave + "\\\" WIDTH=\\\"7%\\\">\";\n" );
		 if ("Y".equalsIgnoreCase(cocReqAtSave))
		 {
			poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
		 }
		 else
		 {
			//poout.println(" insidehtml +=\"<img src=/images/item_chk0.gif border=0 alt=No align=>\";\n");
		 }
		 poout.println(" insidehtml +=\"</TD>\";\n");


		 poout.println( "insidehtml +=\"<TD " + tdColorAtsave + "\\\" WIDTH=\\\"7%\\\">\";\n" );
		 if ("Y".equalsIgnoreCase(coaReqAtSave))
		 {
			poout.println(" insidehtml +=\"<img src=/images/item_chk1.gif border=0 alt=Yes align=>\";\n");
		 }
		 else
		 {
			//poout.println(" insidehtml +=\"<img src=/images/item_chk0.gif border=0 alt=No align=>\";\n");
		 }
		 poout.println(" insidehtml +=\"</TD>\";\n");


      //poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + company_id + "</TD>\";\n" );
      poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdownid" + lineID + "_" + i + "\\\" value=\\\"" + spec_id + "\\\">\";\n" );
			poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdowndetail" + lineID + "_" + i + "\\\" value=\\\"" + spec_detail + "\\\">\";\n" );
      //poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdowncompanyid" + lineID + "_" + i + "\\\" value=\\\"" + company_id +  "\\\">\";\n" );
      poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdowncatalogid" + lineID + "_" + i + "\\\" value=\\\"" + catalog_id +  "\\\">\";\n" );
      poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdowncatpartno" + lineID + "_" + i + "\\\" value=\\\"" + cat_part_no + "\\\">\";\n" );
      poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specdownspeclibrary" + lineID + "_" + i + "\\\" value=\\\"" + spec_library + "\\\">\";\n" );
			poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specCurrentCocRequirement" + lineID + "_" + i + "\\\" value=\\\"" + currentCocRequirement + "\\\">\";\n" );
			poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specCurrentCoaRequirement" + lineID + "_" + i + "\\\" value=\\\"" + currentCoaRequirement + "\\\">\";\n" );
			poout.println( "insidehtml +=\"<input type=\\\"hidden\\\" name=\\\"specColor" + lineID + "_" + i + "\\\" value=\\\"" + color + "\\\">\";\n" );
      poout.println( "insidehtml +=\"</TR>\";\n" );
      }
    }
  }
  catch ( Exception e )
  {
    e.printStackTrace();
  }
  finally
  {    
  }

  if ( count == 0 )
  {
    poout.println( "insidehtml +=\"<TR>\";\n" );
    poout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"11\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
    poout.println( "insidehtml +=\"</TR>\";\n" );
  }

  poout.println( "insidehtml +=\"</TABLE>\";\n" );
  poout.println( "itemnotescell.innerHTML =insidehtml;\n" );
  poout.println( "noofspecs = opener.document.getElementById(\"noofspecsdivrow" + lineID + "" + lineID + "\");\n" );
  poout.println( "noofspecs.value = \"" + count + "\";\n" );

  //if the use click on add before clicking on the plus this part sets the image to minus and also the underlying hidden elements
  if ( "yes".equalsIgnoreCase( setlinestatus ) )
  {
    poout.println( "var itemnotesimgae = opener.document.getElementById(\"specimg" + lineID + "\");\n" );
    poout.println( "itemnotesimgae.setAttribute(\"src\",'/images/minus.jpg');\n" );
    poout.println( "var itemnotestatus  =  opener.document.getElementById(\"specstatus" + lineID + "\");\n" );
    poout.println( "itemnotestatus.value = \"Yes\";\n" );
  }

  poout.println( "}\n" );
  }

  public static Hashtable getinvgrpInitialData( TcmISDBModel dbObj3,Hashtable hublist ) throws Exception
  {
	 return getinvgrpInitialData(dbObj3,hublist,true,false);
  }

  public static Hashtable getinvgrpInitialData( TcmISDBModel dbObj3,Hashtable hublist,boolean showall ) throws Exception
  {
	 return getinvgrpInitialData(dbObj3,hublist,showall,false);
  }

   public static Hashtable getinvgrpInitialData( TcmISDBModel dbObj3,Hashtable hublist,boolean showall,boolean showonlycountable ) throws Exception
  {
	 return getinvgrpInitialData(dbObj3,hublist,showall,showonlycountable,"");
  }

   public static Hashtable getinvgrpInitialData( TcmISDBModel dbObj3,Hashtable hublist,boolean showall,boolean showonlycountable,String opsEntityId ) throws Exception
   {
     Hashtable hubsetdetails= ( Hashtable ) ( hublist.get( "HUB_LIST" ) );
     String allowedhubs="";
     for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
     {
       String branch_plant= ( String ) ( e.nextElement() );
       allowedhubs+="'" + branch_plant + "',";
     }
     allowedhubs=allowedhubs.substring( 0,allowedhubs.length() - 1 );

     String query="select INVENTORY_GROUP, HUB, INVENTORY_GROUP_NAME from inventory_group_definition where HUB in (" + allowedhubs + ") ";
	 if (showonlycountable)
	 {
	   query +=" and ISSUE_GENERATION='Item Counting' ";
	 }

     if (opsEntityId !=null && opsEntityId.trim().length() > 0)
     {
         query +=" and OPS_ENTITY_ID='"+opsEntityId+"' ";
     }

     query +="order by HUB,INVENTORY_GROUP";
     DBResultSet dbrs=null;
     ResultSet rs=null;
     Hashtable resultdata=new Hashtable();
     Vector hubID=new Vector();

     try
     {
       dbrs=dbObj3.doQuery( query );
       rs=dbrs.getResultSet();
       String lastHub="";

       while ( rs.next() )
       {
         String tmpFacID=rs.getString( "INVENTORY_GROUP" );
		 String tmpinvgrpname=rs.getString( "INVENTORY_GROUP_NAME" );
         String tmpHub=rs.getString( "HUB" );

         if ( !tmpHub.equalsIgnoreCase( lastHub ) )
         {
           //hub info
           hubID.addElement( tmpHub );

           Hashtable fh=new Hashtable();
           Vector facID=new Vector();
		   Vector invname=new Vector();
		   if (showall)
		   {
			 facID.addElement( "All" );
			 invname.addElement( "All" );
		   }
           facID.addElement( tmpFacID );
		   invname.addElement( tmpinvgrpname );
           fh.put( "INVENTORY_GROUP",facID );
		   fh.put( "INVENTORY_GROUP_NAME",invname );
           resultdata.put( tmpHub,fh );
         }
         else
         { //hub already exist
           Hashtable fh= ( Hashtable ) resultdata.get( tmpHub );
           Vector facID= ( Vector ) fh.get( "INVENTORY_GROUP" );
		   Vector invName= ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );
           if ( !facID.contains( tmpFacID ) )
           {
             facID.addElement( tmpFacID );
			 invName.addElement( tmpinvgrpname );
           }

           //fh.put( "FACILITY_ID",facID );
		   fh.put( "INVENTORY_GROUP",facID );
		   fh.put( "INVENTORY_GROUP_NAME",invName );
           resultdata.put( tmpHub,fh );
         }
         lastHub=tmpHub;
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
     resultdata.put( "HUB_ID",hubID );
     return resultdata;
   }

   public static StringBuffer createhubinvgrjs( Hashtable finalresultdata, Hashtable hubPrefList )
	{
	  StringBuffer Msgjs=new StringBuffer();
	  String tmp="";
	  String althubid="var althubid = new Array(";
	  String altinvid="var altinvid = new Array();\n ";
	  String altinvName="var altinvName = new Array();\n ";

	  int i=0;

	  Hashtable hub_list= ( Hashtable ) ( hubPrefList.get( "HUB_LIST" ) );
      for ( Enumeration e=hub_list.keys(); e.hasMoreElements(); )
      {
		String branchPlant= ( String ) ( e.nextElement() );

		althubid+="\"" + branchPlant + "\"" + ",";

		Hashtable fh= ( Hashtable ) finalresultdata.get( branchPlant );
		if (fh == null)
		{
		  altinvid+="altinvid[\"" + branchPlant + "\"] = new Array(\"All\");";
		  altinvName+="altinvName[\"" + branchPlant + "\"] = new Array(\"All\");";
	    }
		else
		{
		  Vector cabIDv= ( Vector ) fh.get( "INVENTORY_GROUP" );
		  Vector invName= ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );

		  altinvid+="altinvid[\"" + branchPlant + "\"] = new Array(";
		  altinvName+="altinvName[\"" + branchPlant + "\"] = new Array(";
		  for ( i=0; i < cabIDv.size(); i++ )
		  {
			String facID= ( String ) cabIDv.elementAt( i );
			String invgrpname= ( String ) invName.elementAt( i );
			tmp+="\"" + facID + "\"" + ",";
			altinvid+="\"" + facID + "\"" + ",";
			altinvName+="\"" + invgrpname + "\"" + ",";
		  }
		  //removing the last commas ','
		  altinvid=altinvid.substring( 0,altinvid.length() - 1 ) + ");\n";
		  altinvName=altinvName.substring( 0,altinvName.length() - 1 ) + ");\n";
		}

	  }

	  if ( althubid.indexOf( "," ) > 1 )
	  {
		althubid=althubid.substring( 0,althubid.length() - 1 ) + ");\n";
	  }

	  Msgjs.append( althubid + " " + altinvid + " " + altinvName );

	  return Msgjs;
	}

public static StringBuffer getHubInventoryGroupJavaScript( Collection hubInventoryGroupOvBeanColl,boolean showAllOption,boolean showOnlyItemCountable ) throws
   Exception
{
  StringBuffer hubInventoryGroupJavaScript=new StringBuffer();

  hubInventoryGroupJavaScript.append( "var althubid = new Array(" );

  int hubCount=0;
  Iterator hubIterator=hubInventoryGroupOvBeanColl.iterator();
  while ( hubIterator.hasNext() )
  {
	HubInventoryGroupOvBean hubInventoryGroupOvBean= ( HubInventoryGroupOvBean )
													hubIterator.next();
	if ( hubCount > 0 )
	{
	  hubInventoryGroupJavaScript.append( ",\"" + hubInventoryGroupOvBean.getBranchPlant() + "\"" );
	}
	else
	{
	  hubInventoryGroupJavaScript.append( "\"" + hubInventoryGroupOvBean.getBranchPlant() + "\"" );
	}
	hubCount++;
  }
  hubInventoryGroupJavaScript.append( ");\n" );

  hubInventoryGroupJavaScript.append( "var altinvid = new Array();\n" );
  hubInventoryGroupJavaScript.append( "var altinvName = new Array();\n" );

  hubIterator=hubInventoryGroupOvBeanColl.iterator();
  while ( hubIterator.hasNext() )
  {
	HubInventoryGroupOvBean hubInventoryGroupOvBean= ( HubInventoryGroupOvBean )
													hubIterator.next();
	String currentBranchPlant=hubInventoryGroupOvBean.getBranchPlant();
	Collection inventoryGroupCollection= ( Collection ) hubInventoryGroupOvBean.getInventoryGroupCollection();
	int inventoryGroupCount=0;
	hubInventoryGroupJavaScript.append( "altinvid[\"" + currentBranchPlant + "\"] = new Array(" );
	if ( showAllOption )
	{
	  hubInventoryGroupJavaScript.append( "\"All\"" );
	  inventoryGroupCount++;
	}

	Iterator inventoryGroupIterator=inventoryGroupCollection.iterator();
	while ( inventoryGroupIterator.hasNext() )
	{
	  InventoryGroupObjBean inventoryGroupObjBean= ( InventoryGroupObjBean )
												   inventoryGroupIterator.next();
	  if ((showOnlyItemCountable && "Item Counting".equalsIgnoreCase(inventoryGroupObjBean.getIssueGeneration())) || !showOnlyItemCountable)
	  {
		if ( inventoryGroupCount > 0 )
		{
		  hubInventoryGroupJavaScript.append( ",\"" + inventoryGroupObjBean.getInventoryGroup() + "\"" );
		}
		else
		{
		  hubInventoryGroupJavaScript.append( "\"" + inventoryGroupObjBean.getInventoryGroup() + "\"" );
		}
	  }
	}

	  hubInventoryGroupJavaScript.append( ");\n" );
	  hubInventoryGroupJavaScript.append( "altinvName[\"" + currentBranchPlant + "\"] = new Array(" );
	  inventoryGroupCount=0;
	  if ( showAllOption )
	  {
		hubInventoryGroupJavaScript.append( "\"All\"" );
		inventoryGroupCount++;
	  }

	  inventoryGroupIterator=inventoryGroupCollection.iterator();
	  while ( inventoryGroupIterator.hasNext() )
	  {
		InventoryGroupObjBean inventoryGroupObjBean= ( InventoryGroupObjBean )
													 inventoryGroupIterator.next();
		if ((showOnlyItemCountable && "Item Counting".equalsIgnoreCase(inventoryGroupObjBean.getIssueGeneration())) || !showOnlyItemCountable)
		{
		  if ( inventoryGroupCount > 0 )
		  {
			hubInventoryGroupJavaScript.append( ",\"" + inventoryGroupObjBean.getInventoryGroupName() + "\"" );
		  }
		  else
		  {
			hubInventoryGroupJavaScript.append( "\"" + inventoryGroupObjBean.getInventoryGroupName() + "\"" );
		  }
		}
	  }
	  hubInventoryGroupJavaScript.append( ");\n" );
	}

	return hubInventoryGroupJavaScript;
  }

  public static boolean noItemCountingHubs( Collection hubInventoryGroupOvBeanColl ) throws
	 Exception
  {
	Iterator hubIterator=hubInventoryGroupOvBeanColl.iterator();
	int itemCountingHubCount=0;

	while ( hubIterator.hasNext() )
	{
	  HubInventoryGroupOvBean hubInventoryGroupOvBean= ( HubInventoryGroupOvBean )
													  hubIterator.next();
	  String currentBranchPlant=hubInventoryGroupOvBean.getBranchPlant();
	  Collection inventoryGroupCollection= ( Collection ) hubInventoryGroupOvBean.getInventoryGroupCollection();

	  Iterator inventoryGroupIterator=inventoryGroupCollection.iterator();
	  while ( inventoryGroupIterator.hasNext() )
	  {
		InventoryGroupObjBean inventoryGroupObjBean= ( InventoryGroupObjBean )
													inventoryGroupIterator.next();
		if ( "Item Counting".equalsIgnoreCase( inventoryGroupObjBean.getIssueGeneration() ) )
		{
		  itemCountingHubCount++;
		}
	  }
	}

	return ( itemCountingHubCount == 0 );
  }

  public static StringBuffer createhubinvgrjs( Hashtable finalresultdata )
   {
     StringBuffer Msgjs=new StringBuffer();
     Vector hubid= ( Vector ) finalresultdata.get( "HUB_ID" );

     String tmp="";
     String althubid="var althubid = new Array(";
     String altinvid="var altinvid = new Array();\n ";
	 String altinvName="var altinvName = new Array();\n ";

     int i=0;

     for ( int ii=0; ii < hubid.size(); ii++ )
     {
       String wacid= ( String ) hubid.elementAt( ii );
       althubid+="\"" + wacid + "\"" + ",";

       Hashtable fh= ( Hashtable ) finalresultdata.get( wacid );
       Vector cabIDv= ( Vector ) fh.get( "INVENTORY_GROUP" );
	   Vector invName= ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );

       altinvid+="altinvid[\"" + wacid + "\"] = new Array(";
	   altinvName+="altinvName[\"" + wacid + "\"] = new Array(";
       for ( i=0; i < cabIDv.size(); i++ )
       {
         String facID= ( String ) cabIDv.elementAt( i );
		 String invgrpname= ( String ) invName.elementAt( i );
         tmp+="\"" + facID + "\"" + ",";
         altinvid+="\"" + facID + "\"" + ",";
		 altinvName+="\"" + invgrpname + "\"" + ",";
       }
       //removing the last commas ','
       altinvid=altinvid.substring( 0,altinvid.length() - 1 ) + ");\n";
	   altinvName=altinvName.substring( 0,altinvName.length() - 1 ) + ");\n";
     }

     if ( althubid.indexOf( "," ) > 1 )
     {
       althubid=althubid.substring( 0,althubid.length() - 1 ) + ");\n";
     }

     Msgjs.append( althubid + " " + altinvid + " " + altinvName );

     return Msgjs;
   }

   public static void buildshowClientBuys( TcmISDBModel dbObj9,String lineID,String itemID,String setlinestatus,PrintWriter poout,ResourceLibrary res )
   {
     //StringBuffer Msgtemp=new StringBuffer();
     poout.println( "{\n" );
     DBResultSet dbrs=null;
     ResultSet clientbuyrs=null;
     int count=0;

     try
     {
       String itemnotesquery="select CURRENCY_ID,ITEM_ID,COMPANY_ID,CATALOG_ID,BUYER,BUYER_NAME,PO,FAC_PART_NO,SUPPLIER,MANUFACTURER,MFG_PART_NO,QUANTITY,UOM,to_char(PO_DATE,'mm/dd/yyyy') PO_DATE1,PO_DATE,UNIT_PRICE from client_buy_history_view where item_id = " + itemID + " order by PO_DATE desc";
       dbrs=dbObj9.doQuery( itemnotesquery );
       clientbuyrs=dbrs.getResultSet();

       poout.println( "var itemnotescell = opener.document.getElementById(\"row9detaildivrow" + lineID + "" + lineID + "\");\n" );
       poout.println( "var insidehtml =\"<TABLE ID=\\\"clientbuytable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
       poout.println( "insidehtml +=\"<TR>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.client")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.catalog")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.buyer")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.pon")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.facilitypn")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.supplier")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.manufacturer")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.mfgpn")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.qty")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.uom")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.orderdate")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.unitprice")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"</TR>\";\n" );

       while ( clientbuyrs.next() )
       {
         //String item_id=clientbuyrs.getString( "ITEM_ID" ) == null ? "" : clientbuyrs.getString( "ITEM_ID" ).trim();
         String company_id=clientbuyrs.getString( "COMPANY_ID" ) == null ? "" : clientbuyrs.getString( "COMPANY_ID" ).trim();
         String catalog_id=clientbuyrs.getString( "CATALOG_ID" ) == null ? "" : clientbuyrs.getString( "CATALOG_ID" ).trim();
         //String buyer=clientbuyrs.getString( "BUYER" ) == null ? "" : clientbuyrs.getString( "BUYER" ).trim();
         String buyername=clientbuyrs.getString( "BUYER_NAME" ) == null ? "" : clientbuyrs.getString( "BUYER_NAME" ).trim();
         String po=clientbuyrs.getString( "PO" ) == null ? "" : clientbuyrs.getString( "PO" ).trim();
         String fac_part_no=clientbuyrs.getString( "FAC_PART_NO" ) == null ? "" : clientbuyrs.getString( "FAC_PART_NO" ).trim();
         String supplier=clientbuyrs.getString( "SUPPLIER" ) == null ? "" : clientbuyrs.getString( "SUPPLIER" ).trim();
         String manufacturer=clientbuyrs.getString( "MANUFACTURER" ) == null ? "" : clientbuyrs.getString( "MANUFACTURER" ).trim();
         String mfg_part_no=clientbuyrs.getString( "MFG_PART_NO" ) == null ? "" : clientbuyrs.getString( "MFG_PART_NO" ).trim();
         String quantity=clientbuyrs.getString( "QUANTITY" ) == null ? "" : clientbuyrs.getString( "QUANTITY" ).trim();
         String uom=clientbuyrs.getString( "UOM" ) == null ? "" : clientbuyrs.getString( "UOM" ).trim();
         String po_date=clientbuyrs.getString( "PO_DATE1" ) == null ? "" : clientbuyrs.getString( "PO_DATE1" ).trim();
         String unit_price=clientbuyrs.getString( "UNIT_PRICE" ) == null ? "" : clientbuyrs.getString( "UNIT_PRICE" ).trim();
				 String currencyid=clientbuyrs.getString( "CURRENCY_ID" ) == null ? "" : clientbuyrs.getString( "CURRENCY_ID" ).trim();

        BigDecimal unitPrice=new BigDecimal("0");
				if ( unit_price.length() > 0 )
				{
				 unitPrice=new BigDecimal( unit_price );
				}

         count+=1;
         String Color=" ";
         if ( count % 2 == 0 )
         {
           Color="CLASS=\\\"Inblue";
         }
         else
         {
           Color="CLASS=\\\"Inwhite";
         }

         //Buyer,Supplier,Order Date not clear,
         poout.println( "insidehtml +=\"<TR>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + company_id + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + catalog_id + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + buyername + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + po + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + fac_part_no + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"15%\\\">" + supplier + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"15%\\\">" + manufacturer + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + mfg_part_no + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + quantity + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + uom + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"5%\\\">" + po_date + "</TD>\";\n" );
				 poout.println( "insidehtml +=\"<TD " + Color + "r\\\" WIDTH=\\\"5%\\\">" + unitPrice + " "+currencyid+"</TD>\";\n" );
         poout.println( "insidehtml +=\"</TR>\";\n" );
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

     if ( count == 0 )
     {
       poout.println( "insidehtml +=\"<TR>\";\n" );
       poout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"12\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
       poout.println( "insidehtml +=\"</TR>\";\n" );
     }

     poout.println( "insidehtml +=\"</TABLE>\";\n" );
     poout.println( "itemnotescell.innerHTML =insidehtml;\n" );

     //if the use click on add before clicking on the plus this part sets the image to minus and also the underlying hidden elements
     if ( "yes".equalsIgnoreCase( setlinestatus ) )
     {
       poout.println( "var itemnotesimgae = opener.document.getElementById(\"clientbuys" + lineID + "\");\n" );
       poout.println( "itemnotesimgae.setAttribute(\"src\",'/images/minus.jpg');\n" );

       poout.println( "var itemnotestatus  =  opener.document.getElementById(\"clientbuystatus" + lineID + "\");\n" );
       poout.println( "itemnotestatus.value = \"Yes\";\n" );
     }

     poout.println( "}\n" );
     return;
   }

   public static void buildshowMsdsRevDate( TcmISDBModel dbObj11,String radianpo,String radianbpo,String lineID,String itemID,String ammenNum,PrintWriter poout,ResourceLibrary res )
   {
     //StringBuffer Msgtemp=new StringBuffer();
     poout.println( "{\n" );
     DBResultSet dbrs=null;
     ResultSet msdsrevrs=null;
     int count=0;
     String msdscheck="";
     DBResultSet cocdbrs=null;
     ResultSet rsGenCocData=null;

     try
     {

       //int rowNumber=Integer.parseInt( lineID ) * 1000;
       BigDecimal rowNumber= new BigDecimal(lineID);
       rowNumber = rowNumber.multiply(new BigDecimal("1000"));

       if ( radianpo.length() > 0 )
       {
         cocdbrs=dbObj11.doQuery( "Select MSDS_REQUESTED_DATE from po_line_detail_view where RADIAN_PO = " + radianpo + " and PO_LINE = " + rowNumber +  " and AMENDMENT=" + ammenNum + " " );
       }
       else
       {
         cocdbrs=dbObj11.doQuery( "Select MSDS_REQUESTED_DATE from bpo_line_detail_view where BPO = " + radianbpo + " and BPO_LINE = " + rowNumber + " and AMENDMENT=" + ammenNum + " " );
       }

       rsGenCocData=cocdbrs.getResultSet();

       while ( rsGenCocData.next() )
       {
         msdscheck=rsGenCocData.getString( "MSDS_REQUESTED_DATE" ) == null ? "" : rsGenCocData.getString( "MSDS_REQUESTED_DATE" ).trim();
       }

       poout.println( "var itemnotescell = opener.document.getElementById(\"rowmsdsdetaildivrow" + lineID + "" + lineID + "\");\n" );
       poout.println( "var insidehtml =\"<TABLE ID=\\\"msdstable" + lineID + "\\\"BORDER=\\\"0\\\" BGCOLOR=\\\"#999999\\\" width=\\\"100%\\\" CELLPADDING=\\\"1\\\" CELLSPACING=\\\"1\\\">\";\n" );
       poout.println( "insidehtml +=\"<TR>\";\n" );
       poout.println( "insidehtml +=\"<TD WIDTH=\\\"25%\\\" ALIGN=\\\"RIGHT\\\" COLSPAN=\\\"2\\\" HEIGHT=\\\"25\\\">"+res.getString("label.askedsuppsendmsds")+": </TD>\";\n" );
       poout.println( "insidehtml +=\"<TD WIDTH=\\\"25%\\\" ALIGN=\\\"LEFT\\\" COLSPAN=\\\"2\\\" HEIGHT=\\\"25\\\"><INPUT TYPE=\\\"checkbox\\\" value=\\\"Yes\\\" NAME=\\\"checkformsdsasked" + lineID + "\\\" " + ( msdscheck.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "></TD>\";\n" );
       poout.println( "insidehtml +=\"</TR>\";\n" );
       poout.println( "insidehtml +=\"<TR>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.component")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"10%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.packaging")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"15%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.mfg")+"</B></TH>\";\n" );
       poout.println( "insidehtml +=\"<TH WIDTH=\\\"5%\\\" HEIGHT=\\\"25\\\"><B>"+res.getString("label.msdsdate")+"</B></TH>\";\n" );

       String msdsquesry="select ITEM_ID,DESCRIPTION,CONTENT,MANUFACTURER,PACKAGING,REVISION_DATE,ON_LINE from item_component_view where item_id = " + itemID + "";
       dbrs=dbObj11.doQuery( msdsquesry );
       msdsrevrs=dbrs.getResultSet();

       while ( msdsrevrs.next() )
       {
         //String item_id=msdsrevrs.getString( "ITEM_ID" ) == null ? "" : msdsrevrs.getString( "ITEM_ID" ).trim();
         String description=msdsrevrs.getString( "DESCRIPTION" ) == null ? "" : msdsrevrs.getString( "DESCRIPTION" ).trim();
         String manufacturer=msdsrevrs.getString( "MANUFACTURER" ) == null ? "" : msdsrevrs.getString( "MANUFACTURER" ).trim();
         String packaging=msdsrevrs.getString( "PACKAGING" ) == null ? "" : msdsrevrs.getString( "PACKAGING" ).trim();
         String revision_date=msdsrevrs.getString( "REVISION_DATE" ) == null ? "" : msdsrevrs.getString( "REVISION_DATE" ).trim();
         String on_line=msdsrevrs.getString( "ON_LINE" ) == null ? "" : msdsrevrs.getString( "ON_LINE" ).trim();
         String content=msdsrevrs.getString( "CONTENT" ) == null ? "" : msdsrevrs.getString( "CONTENT" ).trim();

         count+=1;
         String Color=" ";
         if ( count % 2 == 0 )
         {
           Color="CLASS=\\\"Inblue";
         }
         else
         {
           Color="CLASS=\\\"Inwhite";
         }
         poout.println( "insidehtml +=\"<TR>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"15%\\\">" + HelpObjs.addescapesForJavascript( description ) + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"10%\\\">" + HelpObjs.addescapesForJavascript( packaging ) + "</TD>\";\n" );
         poout.println( "insidehtml +=\"<TD " + Color + "l\\\" WIDTH=\\\"15%\\\">" + HelpObjs.addescapesForJavascript( manufacturer ) + "</TD>\";\n" );

         if ( "Y".equalsIgnoreCase( on_line ) && content.length() > 0 )
         {
           poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\"><A onclick=\\\"javascript:window.open('" + content + "')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\"><U>" + revision_date + "</U></A></TD>\";\n" );
         }
         else
         {
           poout.println( "insidehtml +=\"<TD " + Color + "\\\" WIDTH=\\\"5%\\\">" + revision_date + "</TD>\";\n" );
         }

         poout.println( "insidehtml +=\"</TR>\";\n" );
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

     if ( count == 0 )
     {
       poout.println( "insidehtml +=\"<TR>\";\n" );
       poout.println( "insidehtml +=\"<TD CLASS=\\\"Inwhite\\\" WIDTH=\\\"5%\\\" COLSPAN=\\\"4\\\">"+res.getString("label.norecordfound")+"</TD>\";\n" );
       poout.println( "insidehtml +=\"</TR>\";\n" );
     }

     poout.println( "insidehtml +=\"</TABLE>\";\n" );
     poout.println( "itemnotescell.innerHTML =insidehtml;\n" );
     poout.println( "}\n" );
     return;
   }

 public static Hashtable getNewSupplierInitialData(TcmISDBModel dbObj3) throws Exception {
	String query = "select nvl(x.STATE_ABBREV,'None') STATE_ABBREV, nvl( x.STATE,'None') STATE, " +
            "y.COUNTRY_ABBREV, y.COUNTRY from vv_state x, vv_country y " +
            "where x.COUNTRY_ABBREV(+) = y.COUNTRY_ABBREV order by COUNTRY_ABBREV,STATE";

	DBResultSet dbrs = null;
	ResultSet rs = null;
	Hashtable resultdata = new Hashtable();
	Hashtable hubdata = new Hashtable();
	Vector hubID = new Vector();

	try {
	 dbrs = dbObj3.doQuery(query);
	 rs = dbrs.getResultSet();
	 String lastHub = "";

	 while (rs.next()) {
		String tmpFacID = rs.getString("STATE_ABBREV");
		String tmpinvgrpname = rs.getString("STATE");
		String tmpHub = rs.getString("COUNTRY_ABBREV");
		String tmpcountrydec = rs.getString("COUNTRY");

		if (!tmpHub.equalsIgnoreCase(lastHub)) {
		 //hub info
		 hubID.addElement(tmpHub);
		 hubdata.put(tmpHub, tmpcountrydec);

		 Hashtable fh = new Hashtable();
		 Vector facID = new Vector();
		 Vector invname = new Vector();

		 facID.addElement(tmpFacID);
		 invname.addElement(tmpinvgrpname);
		 fh.put("STATE_ABBREV", facID);
		 fh.put("STATE", invname);
		 resultdata.put(tmpHub, fh);
		}
		else { //hub already exist
		 Hashtable fh = (Hashtable) resultdata.get(tmpHub);
		 Vector facID = (Vector) fh.get("STATE_ABBREV");
		 Vector invName = (Vector) fh.get("STATE");
		 if (!facID.contains(tmpFacID)) {
			facID.addElement(tmpFacID);
			invName.addElement(tmpinvgrpname);
		 }

		 //fh.put( "FACILITY_ID",facID );
		 fh.put("STATE_ABBREV", facID);
		 fh.put("STATE", invName);
		 resultdata.put(tmpHub, fh);
		}
		lastHub = tmpHub;
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

	resultdata.put("COUNTRYV", hubID);
	resultdata.put("COUNTRY", hubdata);
	return resultdata;
 }
 }