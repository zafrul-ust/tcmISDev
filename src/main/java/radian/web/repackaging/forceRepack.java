package radian.web.repackaging;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      Haas TCM
 * @author       Nawaz Shaik
 * @version
 * 07-08-04 -Code Cleanup
 */

public class forceRepack
{
	private ServerResourceBundle bundle = null;
	private TcmISDBModel db = null;
	private dispensingHelpobj  hubObj = null;
	private boolean completeSuccess = true ;
	private boolean noneToUpd = true ;
	private boolean allowupdate;
	private int AddNewBinLineNum;
	private PrintWriter out = null;
	private static org.apache.log4j.Logger forcelog = org.apache.log4j.Logger.getLogger(forceRepack.class);
	private boolean intcmIsApplication = false;
	private ResourceLibrary res = null; // res means resource.

	public forceRepack(ServerResourceBundle b, TcmISDBModel d)
	{
		bundle = b;
		db = d;
	}
	private void setAddBinLineNum( int id )
	{
	  this.AddNewBinLineNum=id;
	}

	private int getAddBinLineNum()
	   throws Exception
	{
	  return this.AddNewBinLineNum;
	}

	public void setupdateStatus(boolean id)
	{
	  this.allowupdate = id;
	}

	private boolean getupdateStatus()  throws Exception
	{
	 return this.allowupdate;
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request,response);
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException
	{
		hubObj = new dispensingHelpobj(db);

		out = response.getWriter();
		response.setContentType("text/html");
		HttpSession session = request.getSession();
        res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));

		String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

		DBResultSet receiptdbrs = null;
		ResultSet receiptrs = null;

		String User_Search = null;
		String User_Action = null;
		String SubUser_Action = null;
		String User_Session = "";
		String User_Sort    = "";
		String generate_labels = "";

		User_Session= request.getParameter("session");
		if (User_Session == null)
		  User_Session = "New";

		User_Action= request.getParameter("UserAction");
		if (User_Action == null)
		  User_Action = "New";

		User_Search = request.getParameter("SearchField");
		if (User_Search == null)
			  User_Search = "";
		User_Search=User_Search.trim();

		String invengrp=request.getParameter( "invengrp" );
		if ( invengrp == null )
		  invengrp="";
		invengrp=invengrp.trim();

		String branch_plant=request.getParameter( "hubnum" );
		if ( branch_plant == null )
		  branch_plant="";
		branch_plant=branch_plant.trim();

		String addbin_bin_id = "";
		String addbin_item_id = "";
		String dupl_line = "";

		try
		{
			if ( User_Action.equalsIgnoreCase("New") )
			{
				buildHeader(User_Search,invengrp,0,branch_plant);
				out.println(radian.web.HTMLHelpObj.printHTMLSelect(res));
				out.close();
			}
			else if ( User_Action.equalsIgnoreCase( "Search" ) )
			{
			  Vector openorder_data=new Vector();
			  openorder_data=hubObj.getforcerepacklist( branch_plant,User_Search,invengrp );

			  Hashtable sum= ( Hashtable ) ( openorder_data.elementAt( 0 ) );
			  int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
			  if ( 0 == count )
			  {
				buildHeader( User_Search,invengrp,0,branch_plant);
				out.println( radian.web.HTMLHelpObj.printHTMLNoData( res.getString("msg.noitemfound") ) );
				out.close();
				//clean up
				openorder_data=null;
			  }
			  else
			  {

				session.setAttribute( "FORCE_REPACK_DATA",openorder_data );

				buildHeader( User_Search,invengrp,count,branch_plant);

				buildDetails( openorder_data,branch_plant,"","NONE");

				out.close();
				//clean up
				openorder_data=null;
			  } //when there are open orders for hub
			}
			else if ( User_Action.equalsIgnoreCase("Update") )
			{
				SubUser_Action= request.getParameter("SubUserAction");
				if (SubUser_Action == null)
				  SubUser_Action = "JSError2";

				Vector retrn_data = (Vector) session.getAttribute("FORCE_REPACK_DATA");
				Vector synch_data =  SynchServerData( request,retrn_data,SubUser_Action);
				retrn_data = null;

				if ( SubUser_Action.equalsIgnoreCase("UpdPage") )
				{
					session.setAttribute("FORCE_REPACK_DATA", synch_data );
					Hashtable updateresults = UpdateDetails(synch_data, personnelid,SubUser_Action);

					Boolean resultfromup = (Boolean)updateresults.get("RESULT");
					Vector errordata = (Vector)updateresults.get("ERRORDATA");
					Vector errMsgs  = hubObj.getErrMsgs();
					session.setAttribute("FORCE_REPACK_DATA", errordata );

					Hashtable sum= ( Hashtable ) ( errordata.elementAt( 0 ) );
					int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

					boolean resulttotest =  resultfromup.booleanValue();

					StringBuffer Msg = new StringBuffer();
					StringBuffer Msghesd = new StringBuffer();
					Msghesd.append( radian.web.HTMLHelpObj.printHTMLHeader( "title.forcerepack","forcerepack.js",intcmIsApplication,res ) );
					Msghesd.append( "</HEAD>  \n" );

					Msg.append( "<FORM method=\"POST\" NAME=\"closetap\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"\">\n" );
					Msg.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
					Msg.append( "<TR VALIGN=\"MIDDLE\">\n" );
					Msg.append( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"CENTER\" CLASS=\"announce\">\n" );

					if ( true == resulttotest )
					{
					  if (completeSuccess)
					  {
						Msghesd.append("<BODY onLoad=\"submitmainpage()\">");
			            Msg.append( res.getString("label.repackageordersucc")+"\n<BR><BR>"+res.getString("label.refreshmainpage") );
					  }
					  else
					  {
						Msghesd.append("<BODY>");
						Msg.append( res.getString("msg.somerepackagefailed")+"\n<BR><BR>"+res.getString("msg.okortryagain") );
			          }
					}
					else
					{
					  Msghesd.append("<BODY>");
					  Msg.append( "An error occured while creating <B>Repackage Orders</B>&nbsp;<BR><BR>An error report has been sent to the tech center." );
					}
					Msg.append( "</TD>\n" );
					Msg.append( "</TR>" );
					Msg.append( "</TABLE>\n" );
					Msg.append( "<BR>\n" );
					Msg.append( "<CENTER><INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Ok\" OnClick=\"submitmainpage()\" NAME=\"sendterv\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
					Msg.append( "</CENTER>\n" );
					Msg.append( "</BODY>\n" );
					Msghesd.append(Msg);
					out.println(Msghesd);
					//clean up
					synch_data        = null;
				}
				else
				{
					out.println(radian.web.HTMLHelpObj.printError("title.forcerepack",intcmIsApplication,res));
				}

			}//End of User_Session="Active" && User_Action="Update"
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("title.forcerepack",intcmIsApplication,res));
		}

		//clean up
		hubObj = null;
		return;
	}

	private Vector SynchServerData( HttpServletRequest request,Vector in_data,String subuseraction)
	{
		Vector new_data = new Vector();
		Hashtable sum = ( Hashtable)( in_data.elementAt(0));
		int count = ((Integer)sum.get("TOTAL_NUMBER")).intValue();
		new_data.addElement(sum);

		try
		{
		  for ( int i=1; i < count + 1; i++ )
		  {
			Hashtable hD=new Hashtable();
			hD= ( Hashtable ) ( in_data.elementAt( i ) );

			String check="";
			check=request.getParameter( ( "row_chk" + i ) );
			if ( check == null )
			  check="";
			hD.remove( "USERCHK" );
			hD.put( "USERCHK",check );

		   String qtypicked="";
		   qtypicked=BothHelpObjs.makeBlankFromNull( request.getParameter( "qty_picked" + i ) );
		   hD.remove( "QUANTITY_PICKED" );
		   hD.put( "QUANTITY_PICKED",qtypicked.trim() );

			new_data.addElement( hD );
		  }
		}
		catch ( Exception e )
		{
		  e.printStackTrace();
		  out.println( radian.web.HTMLHelpObj.printError( "title.forcerepack",intcmIsApplication,res ) );
		}
		return new_data;
	}

	private Hashtable UpdateDetails(Vector data, String logonid, String subuseraction)
	{
		boolean result = false;
		Hashtable updateresult = new Hashtable();
		Vector errordata = new Vector();
		try
		{
			Hashtable summary = new Hashtable();
			summary = (Hashtable)data.elementAt(0);
			int total = ((Integer)(summary.get("TOTAL_NUMBER"))).intValue()  ;
			errordata.addElement(summary);

			int linecheckcount = 0;
			boolean one_success = false;
			for (int i = 1; i < total+1  ; i++)
			{
				Hashtable hD = new Hashtable();
				hD = (Hashtable) data.elementAt(i);
				String Line_Check = "";

				Line_Check      =  (hD.get("USERCHK")==null?" ":hD.get("USERCHK").toString());
				String qtypicked=hD.get( "QUANTITY_PICKED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_PICKED" ).toString();


				if ("yes".equalsIgnoreCase(Line_Check) && qtypicked.length() > 0 && this.getupdateStatus() )
				{
					noneToUpd = false;
					linecheckcount++;
					boolean line_result = false;
					line_result = hubObj.forcerepack( hD, logonid ); // update database

					if ( false == line_result)
					{
					  completeSuccess = false;

					  hD.remove("USERCHK");
					  hD.put("USERCHK", " ");

					  hD.remove("LINE_STATUS");
					  hD.put("LINE_STATUS", "NO");

					  errordata.addElement(hD);
					}
					if ( true == line_result)
					{
					one_success = true;
					hD.remove("LINE_STATUS");
					hD.put("LINE_STATUS", "YES");

					errordata.addElement(hD);
					}
			  }
			  else
			  {
			   errordata.addElement(hD);
			  }
			} //end of for

			if (linecheckcount == 1 && true == one_success){result = true;}
			if ( true == one_success )
			{
			result = true;
			}
		}
		catch (Exception e)
		{
			result = false;
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("repackaging",intcmIsApplication,res));
		}

		updateresult.put("RESULT",new Boolean(result));
		updateresult.put("ERRORDATA",errordata);

		return updateresult;
	}

	private void buildHeader( String search_text,String selinvengrp,int total,String hubnumber )
	{
		//StringBuffer Msg = new StringBuffer();
		try
		{
		  out.println(radian.web.HTMLHelpObj.printHTMLHeader("title.forcerepack","forcerepack.js",intcmIsApplication,res));
		  out.println("<SCRIPT SRC=\"/clientscripts/invengrpdropdown.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
		  out.println( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
		  out.println("</HEAD>  \n");
		  out.println("<BODY>\n");

		  out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
		  out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT><center>\n");
		  out.println("</DIV>\n");
		  out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

		  out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>"+res.getString("title.forcerepack")+"</B>\n"));
		  out.println("<FORM method=\"POST\" NAME=\"forcerepack\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+radian.web.tcmisResourceLoader.getforcerepackurl()+"\">\n");
		  out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
		  out.println("<TR>\n");

		  //Hub
		  out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
		  out.println("<B>"+res.getString("label.inventorygroup")+":</B>&nbsp;\n");
		  out.println("</TD>\n");
		  out.println("<TD WIDTH=\"20%\">\n");
		  out.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invengrp\" value=\""+selinvengrp+"\" size=\"10\">\n");
		  out.println("</TD>\n");

		  out.println("<TD WIDTH=\"15%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
		  out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" value=\""+search_text+"\" size=\"19\">\n");
		  out.println("</TD>\n");

		  // Search
		  out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
		  out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.search")+"\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n");

		  //Force Order
			out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\">\n" );
			if ( this.getupdateStatus() )
			{
			  out.println( "<INPUT TYPE=\"submit\" VALUE=\""+res.getString("label.forceorder")+"\" onclick= \"return update(this)\" NAME=\"forceorderbutton\" STYLE=\"display: none;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
			}
			else
			{
			  out.println( "&nbsp;\n" );
			}
			out.println( "</TD>\n" );

			//Cancel
			out.println( "<TD WIDTH=\"35%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
			out.println( "&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\""+res.getString("label.cancel")+"\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
			out.println( "</TD>\n" );


		  out.println("</TR>\n");

		 /* out.println( "<TR VALIGN=\"MIDDLE\">\n" );
		  // Inventory Group
		  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		  out.println( "&nbsp;\n" );
		  out.println( "</TD>\n" );
		  out.println( "<TD WIDTH=\"20%\">\n" );
		  out.println( "</TD>\n" );

		  out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
		  out.println("<B>Receipt ID:</B></TD>\n");

		  out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" value=\"\" size=\"19\">\n</TD>\n");

	//Tap
	out.println( "<TD WIDTH=\"10%\" ALIGN=\"CENTER\" CLASS=\"announce\">\n" );
	out.println( "&nbsp;\n" );
	out.println( "</TD>\n" );

		  //Tap
		  out.println("<TD WIDTH=\"35%\" ALIGN=\"CENTER\" CLASS=\"announce\">\n");
		   out.println("&nbsp;\n");
		  out.println("</TD>\n");

		  out.println("</TR>\n");*/
		  out.println("</TABLE>\n");

		  out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n");
		  out.println("<tr><td>");
		  out.println("<input type=\"hidden\" name=\"Total_number\" value=\""+total+"\">\n");
		  out.println("<input type=\"hidden\" name=\"hubnum\" value=\""+hubnumber+"\">\n");
		  out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n");
		  out.println( "<INPUT TYPE=\"hidden\" NAME=\"DuplLineNumber\" VALUE=\" \">\n" );
		  out.println("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n");
		  out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Item_Id\" VALUE=\" \">\n" );
		  out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Bin_Id\" VALUE=\" \">\n" );
		  out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Line_No\" VALUE=\" \">\n" );

		  out.println("</TD></tr>");
		  out.println("</table>\n");
	}
	catch (Exception e)
	{
		e.printStackTrace();
		out.println(radian.web.HTMLHelpObj.printError("repackaging",intcmIsApplication,res));
	}

	return;
}

private void buildDetails(Vector data,String HubNo,String SubuserAction,String Add_Item_id)
{
	//StringBuffer Msg = new StringBuffer();
	String checkednon = "";

	try
	{
		Hashtable summary = new Hashtable();
		summary = (Hashtable)data.elementAt(0);
		int total = ((Integer)(summary.get("TOTAL_NUMBER"))).intValue()  ;
		out.println("<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");
		int AddBinLineNum1 = getAddBinLineNum();

		int i = 0;  //used for detail lines
		for (int loop  = 0 ; loop  < total  ; loop++)
		{
		   i++;
		   boolean createHdr = false;

		   if ( loop%10 == 0 )
		   { createHdr = true;
		   }

			if ( createHdr )
			{
				out.println("<tr align=\"center\">\n");
				out.println("<TH width=\"2%\"  height=\"38\">"+res.getString("label.ok")+"</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.item")+"</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.pkg")+"</TH>\n");
				out.println("<TH width=\"20%\"  height=\"38\">"+res.getString("label.description")+"</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.qty")+"</TH>\n");
				out.println("</tr>\n");
			}

			Hashtable hD = new Hashtable();
			hD = (Hashtable) data.elementAt(i);

			String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
			//String original_item_id=hD.get( "ORIGINAL_ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ORIGINAL_ITEM_ID" ).toString();
			//String hub=hD.get( "HUB" ) == null ? "&nbsp;" : hD.get( "HUB" ).toString();
			//String inventory_group=hD.get( "INVENTORY_GROUP" ) == null ? "&nbsp;" : hD.get( "INVENTORY_GROUP" ).toString();
			//String receipt_id=hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString();
			//String bin=hD.get( "BIN" ) == null ? "&nbsp;" : hD.get( "BIN" ).toString();
			//String mfg_lot=hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString();
			//String expire_date=hD.get( "EXPIRE_DATE" ) == null ? "&nbsp;" : hD.get( "EXPIRE_DATE" ).toString();
			//String quantity_available=hD.get( "QUANTITY_AVAILABLE" ) == null ? "&nbsp;" : hD.get( "QUANTITY_AVAILABLE" ).toString();
			String item_desc=hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString();
			String item_pkg=hD.get( "ITEM_PKG" ) == null ? "&nbsp;" : hD.get( "ITEM_PKG" ).toString();
			//String search_text=hD.get( "SEARCH_TEXT" ) == null ? "&nbsp;" : hD.get( "SEARCH_TEXT" ).toString();
			String qtypicked=hD.get( "QUANTITY_PICKED" ) == null ? "&nbsp;" : hD.get( "QUANTITY_PICKED" ).toString();

			String Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
			if ( Line_Check.equalsIgnoreCase( "yes" ) )
			{
			  checkednon="checked";
			}
			else
			{
			  checkednon="";
			}

			String chkbox_value="no";
			if ( checkednon.equalsIgnoreCase( "checked" ) )
			{
			  chkbox_value="yes";
			}

			String Color = " ";
			if (i%2==0)
			{
				Color ="CLASS=\"blue";
			}
			else
			{
				Color ="CLASS=\"white";
			}

		out.println( "<tr align=\"center\">\n" );
		out.println( "<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" +chkbox_value+ "\"  onClick= \"checkValues(name,'"+i+"')\" " + checkednon + "  NAME=\"row_chk" + i + "\"></td>\n" );
		out.println( "<td " + Color + "l\" width=\"5%\">" + item_id + "</td>\n" );
		out.println( "<td " + Color + "l\" width=\"5%\">" + item_pkg + "</td>\n" );
		out.println( "<td " + Color + "l\" width=\"20%\">" + item_desc + "</td>\n" );
		out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue(name,this,'" + i + "')\" name=\"qty_picked" + i + "\"  value=\"" + qtypicked + "\" maxlength=\"10\" size=\"5\"></td>\n" );

		out.println( "<input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + item_id + "\">\n" );
		out.println( "<input type=\"hidden\" name=\"item_id\" value=\"" + item_id + "\">\n" );
		out.println("</tr>\n");
		}

		out.println("</table>\n");
		out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
		out.println("<tr>");
		out.println("<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
		out.println("</TD></tr>");
		out.println("</table>\n");

		out.println("</form>\n");
		out.println("</body></html>\n");
	}
	catch (Exception e)
	{
		e.printStackTrace();
		out.println(radian.web.HTMLHelpObj.printError("repackaging",intcmIsApplication,res));
	}

	return;
}
//End of method
}
//END OF CLASS

