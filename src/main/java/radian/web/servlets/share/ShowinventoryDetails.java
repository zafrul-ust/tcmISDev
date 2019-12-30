package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Collection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.HubInvenLabelsTables;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */


public class ShowinventoryDetails   extends HttpServlet   implements SingleThreadModel
{
  public void init( ServletConfig config )	 throws ServletException
  {
	super.init( config );
  }

  /**
   * HTTP POST handler.
   *
   * @param request               An object implementing the request context
   * @param response              An object implementing the response context
   * @exception ServletException  A wrapper for all exceptions thrown by the
   *      servlet.
   */
  public void doGet( HttpServletRequest request,HttpServletResponse response )	 throws ServletException,IOException
  {
	doPost( request,response );
  }
private boolean intcmIsApplication = false;
  public void doPost( HttpServletRequest request,HttpServletResponse response )
	 throws ServletException,IOException
  {
	PrintWriter out=response.getWriter();
	response.setContentType( "text/html" );
	TcmISDBModel db=null;
	HttpSession session=request.getSession();
	PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
  if (personnelBean !=null)
  {
Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
{
 intcmIsApplication = true;
}
  }

	//StringBuffer Msgn=new StringBuffer();
	try
	{
	  db=new RayTcmISDBModel( "Tcm_Ops" );
	  if ( db == null )
	  {
		PrintWriter out2=new PrintWriter( response.getOutputStream() );
		out2.println( "Starting DB" );
		out2.println( "DB is null" );
		out2.close();
		return;
	  }

	  Hashtable loginresult=new Hashtable();
	  loginresult=radian.web.loginhelpObj.logintopage( session,request,db,out );
	  String auth= ( String ) loginresult.get( "AUTH" );
	  String errormsg= ( String ) loginresult.get( "ERROSMSG" );

	  if ( auth == null )
	  {
		auth="challenge";
	  }
	  if ( errormsg == null )
	  {
		errormsg="";
	  }
	  String cuscabinvpage=session.getAttribute( "CUSTOMER_CABINET_INV_PAGE" ) == null ? "" : session.getAttribute( "CUSTOMER_CABINET_INV_PAGE" ).toString();
		Collection hubInventoryGroupOvBeanCollection = null;
		try {
		 hubInventoryGroupOvBeanCollection = new Vector( (Collection) session.getAttribute(
			"hubInventoryGroupOvBeanCollection"));
		}
		catch (Exception ex) {
		}

	  if ( !auth.equalsIgnoreCase( "authenticated" ) && !"Yes".equalsIgnoreCase(cuscabinvpage) )
	  {
		out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Inventory Details" ) );
		out.flush();
	  }
	  else
	  {
		String hubNum=request.getParameter( "hubNum" );
		if ( hubNum == null )
		  hubNum="";
		hubNum=hubNum.trim();

		String itemid=request.getParameter( "itemid" );
		if ( itemid == null )
		  itemid="";
		itemid=itemid.trim();

		out.println( "<HTML>\n" );
		out.println( "<HEAD>\n" );
		out.println( "<TITLE>Inventory Details</TITLE>\n" );
		out.println( "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n" );
		out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
		out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
		out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
		out.println( "</HEAD>\n" );
		out.println( "<BODY>\n" );

		out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
		out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
		out.println( "</DIV>\n" );
		out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

		out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "Inventory Details for Item: " + itemid + "\n" ) );
		HubInvenLabelsTables hubObj=new HubInvenLabelsTables( db );
		Vector data=new Vector();
		//BROKEN data=hubObj.getAllopenOrder( hubNum, itemid, "","","","","ITEM_ID","Equals","",hubInventoryGroupOvBeanCollection,"" );


		try
		{
		  Hashtable summary=new Hashtable();
		  summary= ( Hashtable ) data.elementAt( 0 );
		  int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
		  out.println( "<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n" );

      if (total == 0)
      {
      out.println( "No Inventory Found" );
      }

      int lineadded=0;
		  int lastItemNum=1;
		  int ItemIdCount=0;
		  String Color="CLASS=\"white";
		  String invcolor="CLASS=\"INVISIBLEHEADWHITE";

		  int i=0; //used for detail lines
		  for ( int loop=0; loop < total; loop++ )
		  {
			i++;
			boolean createHdr=false;

			if ( lineadded % 10 == 0 && lastItemNum == 1 )
			{
			  createHdr=true;
			}

			if ( createHdr )
			{
			  out.println( "<tr align=\"center\">\n" );
			  out.println( "<TH width=\"5%\"  height=\"38\">Inven Grp</TH>\n" );
			  out.println( "<TH width=\"5%\"  height=\"38\">Rec ID<BR>Orig-ID<BR>(Owner)</TH>\n" );
			  out.println( "<TH width=\"5%\"  height=\"38\">Quantity</TH>\n" );
        out.println( "<TH width=\"5%\"  height=\"38\">Received Qty</TH>\n" );
        out.println( "<TH width=\"5%\"  height=\"38\">Status</TH>\n" );
			  out.println( "<TH width=\"5%\"  height=\"38\">Lot</TH>\n" );
			  out.println( "<TH width=\"5%\"  height=\"38\">Exp Date mm/dd/yyyy</TH>\n" );
			  out.println( "<TH width=\"4%\"  height=\"38\">Bin</TH>\n" );

			  //out.println( "<TH width=\"20%\"  height=\"38\">Description</TH>\n" );
			  //out.println( "<TH width=\"5%\"  height=\"38\">Root Cause</TH>\n" );
			  //out.println( "<TH width=\"5%\"  height=\"38\">Age (Days)</TH>\n" );
			  //out.println( "<TH width=\"2%\"  height=\"38\">Recert#</TH>\n" );
			  //out.println( "<TH width=\"2%\"  height=\"38\"># Labels</TH>\n" );
			  //out.println( "<TH width=\"5%\"  height=\"38\">Last Print Date</TH>\n" );
			  //out.println( "<TH width=\"5%\"  height=\"38\">Storage Temp</TH>\n" );
			  //out.println( "<TH width=\"10%\" COLSPAN =\"2\" height=\"38\">Notes</TH>\n" );
			  out.println( "</tr>\n" );
			}

			Hashtable hD=new Hashtable();
			hD= ( Hashtable ) data.elementAt( i );

			String Next_rec="";
			if ( ! ( i == total ) )
			{
			  Hashtable hDNext=new Hashtable();
			  hDNext= ( Hashtable ) data.elementAt( i + 1 );
			  Next_rec=hDNext.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hDNext.get( "RECEIPT_ID" ).toString();
			}
			else
			{
			  Next_rec=" ";
			}

			// get main information
			String Item_id= ( hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString() );
			//String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString() );
			String Qty= ( hD.get( "QUANTITY" ) == null ? "" : hD.get( "QUANTITY" ).toString() );
			String Mfg_lot= ( hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString() );
			//String bin         = (hD.get("BIN")==null?"&nbsp;":hD.get("BIN").toString());
			String status= ( hD.get( "LOT_STATUS" ) == null ? "&nbsp;" : hD.get( "LOT_STATUS" ).toString() );
			String receipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
			//String Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
			//String nooflabels= ( hD.get( "NO_OF_LABELS" ) == null ? "&nbsp;" : hD.get( "NO_OF_LABELS" ).toString() );
			String Sel_bin= ( hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString() );
			//String recert= ( hD.get( "RECERT_NUMBER" ) == null ? "" : hD.get( "RECERT_NUMBER" ).toString() );
			//String lastprintdate= ( hD.get( "LAST_PRINT_DATE" ) == null ? "&nbsp;" : hD.get( "LAST_PRINT_DATE" ).toString() );
			String Expire_date=hD.get( "EXPIRE_DATE" ).toString();
			//String LINE_STATUS=BothHelpObjs.makeBlankFromNull( hD.get( "LINE_STATUS" ).toString() );
			//String reciptnots= ( hD.get( "NOTES" ) == null ? "&nbsp;" : hD.get( "NOTES" ).toString() );
			//String lotstatusage= ( hD.get( "LOT_STATUS_AGE" ) == null ? "&nbsp;" : hD.get( "LOT_STATUS_AGE" ).toString() );
			//String rootcause=BothHelpObjs.makeBlankFromNull( hD.get( "TERMINAL_ROOT_CAUSE" ).toString() );
			//String rootcausecompany=BothHelpObjs.makeBlankFromNull( hD.get( "TERMINAL_COMPANY" ).toString() );
			//String rootcausenotes=BothHelpObjs.makeBlankFromNull( hD.get( "TERMINAL_NOTES" ).toString() );
			String invengrp=BothHelpObjs.makeBlankFromNull( hD.get( "INVENTORY_GROUP" ).toString() );
			String qtyreceived= ( hD.get( "QUANTITY_RECEIVED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_RECEIVED" ).toString() );
			//String qualitycntitem= ( hD.get( "QUALITY_CONTROL_ITEM" ) == null ? " " : hD.get( "QUALITY_CONTROL_ITEM" ).toString() );
			//String pickable= ( hD.get( "PICKABLE" ) == null ? " " : hD.get( "PICKABLE" ).toString() );
			String ownercomp= ( hD.get( "OWNER_COMPANY_ID" ) == null ? " " : hD.get( "OWNER_COMPANY_ID" ).toString() );
			if ( ownercomp.length() == 0 || "Radian".equalsIgnoreCase( ownercomp ) )
			{
			  ownercomp="Haas";
			}

			String origrecid= ( hD.get( "ORIGINAL_RECEIPT_ID" ) == null ? " " : hD.get( "ORIGINAL_RECEIPT_ID" ).toString() );
			if ( origrecid.trim().length() == 0 )
			{
			  origrecid="-";
			}
			//String unitcost= ( hD.get( "UNIT_COST" ) == null ? " " : hD.get( "UNIT_COST" ).toString() );

			String mngkitassingl=hD.get( "MANAGE_KITS_AS_SINGLE_UNIT" ).toString();
			String componentid=hD.get( "COMPONENT_ID" ).toString();
			String compmatdesc=hD.get( "MATERIAL_DESC" ).toString();
			String invgrpname=hD.get( "INVENTORY_GROUP_NAME" ).toString();
			String numofkits=hD.get( "NUMBER_OF_KITS" ).toString();
			//String tmpstortmp=hD.get( "STORAGE_TEMP" ).toString();
			//String storageTemp=hD.get( "STORAGE_TEMP1" ).toString();

			/*if ( "2118".equalsIgnoreCase( hubNum ) )
			{
			  storageTemp=tmpstortmp;
			}*/

			boolean Samepoline=false;
			boolean FirstRow=false;
			if ( "N".equalsIgnoreCase( mngkitassingl ) && Next_rec.equalsIgnoreCase( receipt_id ) )
			{
			  Samepoline=true;
			  lastItemNum++;
			}
			else
			{
			  ItemIdCount++;
			  lastItemNum=1;
			}

			if ( Samepoline )
			{
			  if ( lastItemNum == 2 )
			  {
				FirstRow=true;
			  }
			}
			else if ( "Y".equalsIgnoreCase( mngkitassingl ) )
			{
			  FirstRow=true;
			  numofkits="1";
			}

			out.println( "<tr align=\"center\">\n" );
			boolean recqcallowed=false;

			lineadded++;

			  if ( FirstRow )
			  {
			   //out.println( "<td " + Color + "\" width=\"2%\" rowspan=\"" + numofkits + "\">&nbsp;</td>\n" );
			   out.println( "<td " + Color + "\" width=\"5%\" rowspan=\"" + numofkits + "\">" + invgrpname + "</td>\n\n" );
			   out.println( "<td " + Color + "\" width=\"5%\" rowspan=\"" + numofkits + "\">" + receipt_id + "<BR>" + origrecid + "<BR>(" + ownercomp + ")</td>\n\n" );
				//out.println( "<td " + Color + "\" width=\"2%\" rowspan=\"" + numofkits + "\">" + ( recert ) + "</td>\n" );
			   out.println( "<td " + Color + "\" width=\"5%\" rowspan=\"" + numofkits + "\">" + Qty + "</td>\n" );
         out.println( "<td " + Color + "\" width=\"5%\" rowspan=\"" + numofkits + "\">"+ qtyreceived + "</td>\n" );
        }

			  /*if ( "N".equalsIgnoreCase( mngkitassingl ) )
			  {
				out.println( "<td " + Color + "l\" width=\"20%\">" + compmatdesc + "</td>\n" );
			  }
			  else
			  {
				out.println( "<td " + Color + "l\" width=\"20%\">" + Item_desc + "</td>\n" );
			  }*/

			  if ( FirstRow )
			  {
				out.println( "<td " + Color + "\" width=\"5%\" rowspan=\"" + numofkits + "\">" + status + "\n</td>\n" );
				//out.println( "<td " + Color + "\" width=\"5%\" rowspan=\"" + numofkits + "\">" + rootcause + "\n</td>\n" );
				//out.println( "<td " + Color + "\" width=\"4%\" rowspan=\"" + numofkits + "\">" + lotstatusage + "</td>\n" );
			  }

			  out.println( "<td " + Color + "\" width=\"5%\">" + Mfg_lot +"</td>\n\n" );
			  if ( "INDEFINITE".equalsIgnoreCase( Expire_date ) || "01/01/3000".equalsIgnoreCase( Expire_date ) )
			  {
				out.println( "<td " + Color + "\" width=\"5%\">Indefinite</td>\n" );
			  }
			  else
			  {
				out.println( "<td " + Color + "\" width=\"5%\">" + Expire_date + "</td>\n" );
			  }
//			  out.println( "<td " + Color + "r\" width=\"1%\">&nbsp;</td>\n" );
			  out.println( "<td " + Color + "l\" width=\"3%\">" + Sel_bin + "\n</td>\n" );


			  if ( FirstRow )
			  {
				/*if ( this.getupdateStatus() && ! ( recqcallowed ) )
				{
				  out.println( "<td " + Color + "\" width=\"5%\" rowspan=\"" + numofkits + "\"><input type=\"text\" CLASS=\"DETAILS\" name=\"qty_recd" + i + "\"  value=\"" + nooflabels + "\" maxlength=\"30\" size=\"3\"></td>\n" );
				}
				else
				{
				  out.println( "<td " + Color + "\" width=\"5%\" rowspan=\"" + numofkits + "\">&nbsp;</td>\n" );
				}
				out.println( "<td " + Color + "\" width=\"5%\" rowspan=\"" + numofkits + "\">" + lastprintdate + "</td>\n" );
				out.println( "<td " + Color + "\" width=\"5%\" rowspan=\"" + numofkits + "\">" + storageTemp + "</td>\n" );
				out.println( "<td " + Color + "\" width=\"10%\" rowspan=\"" + numofkits + "\">" + reciptnots + "</td>\n" );
				out.println( "<td " + Color + "\" width=\"1%\" rowspan=\"" + numofkits + "\">&nbsp;</td>\n" );*/
			  }
			  //out.println( "<input type=\"hidden\" name=\"receipt_id" + i + "\" value=\"" + receipt_id + "\">\n" );


			if ( ( ItemIdCount ) % 2 == 0 )
			{
			  Color="CLASS=\"white";
			  invcolor="CLASS=\"INVISIBLEHEADWHITE";
			}
			else
			{
			  Color="CLASS=\"blue";
			  invcolor="CLASS=\"INVISIBLEHEADBLUE";
			}
		  }

		  out.println( "</table>\n" );
		  out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
		  out.println( "<tr>" );
		  out.println( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
		  out.println( "</TD></tr>" );
		  out.println( "</table>\n" );

		  out.println( "</form>\n" );
		  out.println( "</body></html>\n" );
		}
		catch ( Exception e )
		{
		  e.printStackTrace();
		  out.println( radian.web.HTMLHelpObj.printError( "Logistics",intcmIsApplication ) );
		}

	  }
	}
catch ( Exception e )
{
e.printStackTrace();
out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
return;
}
finally
{
db.close();
}

out.println( "</FORM></BODY></HTML>\n" );
//out.println( Msgn );
out.close();
}
	}
