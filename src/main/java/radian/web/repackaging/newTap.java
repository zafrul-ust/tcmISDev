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
 */

public class newTap
{
	private ServerResourceBundle bundle = null;
	private TcmISDBModel db = null;
	private dispensingHelpobj  hubObj = null;
	private boolean completeSuccess = true ;
	private boolean noneToUpd = true ;
	private boolean allowupdate;
	private int AddNewBinLineNum;
	private PrintWriter out = null;
	private static org.apache.log4j.Logger taplog = org.apache.log4j.Logger.getLogger(newTap.class);
	private boolean intcmIsApplication = false;
	private ResourceLibrary res = null; // res means resource.

	public newTap(ServerResourceBundle b, TcmISDBModel d)
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
		String User_Sort    = "";
		String generate_labels = "";

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

		String updatecount=request.getParameter( "updatecount" );
		if ( updatecount == null )
		  updatecount="";
		updatecount=updatecount.trim();

	    //taplog.debug("User_Action     "+User_Action+"");

		String addbin_bin_id = "";
		String addbin_item_id = "";
		String dupl_line = "";

		try
		{
			if ( User_Action.equalsIgnoreCase("New") )
			{
				buildHeader(User_Search,invengrp,0,branch_plant,updatecount);
				out.println(radian.web.HTMLHelpObj.printHTMLSelect());
				out.close();
			}
			else if ( User_Action.equalsIgnoreCase( "Search" ) )
			{
			  Vector openorder_data=new Vector();
			  openorder_data=hubObj.getavailtotaps( branch_plant,User_Search,invengrp );

			  Vector openbinSet=new Vector();
			  openbinSet=radian.web.HTMLHelpObj.createEmptyBinSet( db,branch_plant );
			  session.setAttribute( "EMPTYBIN",openbinSet );
			  session.setAttribute( "UPDATEDONE","NO" );

			  Hashtable sum= ( Hashtable ) ( openorder_data.elementAt( 0 ) );
			  int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

			  if ( 0 == count )
			  {
				buildHeader( User_Search,invengrp,0,branch_plant,updatecount);
				out.println( radian.web.HTMLHelpObj.printHTMLNoData( res.getString("msg.noitemfound" ) ) );
				out.close();
				//clean up
				openorder_data=null;
			  }
			  else
			  {
				Hashtable in_bin_set=new Hashtable();
				Hashtable out_bin_set2=createItemBinSet( openorder_data,branch_plant );

				Vector lot_status_set= ( Vector ) session.getAttribute( "STATUS_SET" );

				session.setAttribute( "NEW_TAP_DATA",openorder_data );
				session.setAttribute( "NEW_TAP_SET",out_bin_set2 );
				session.setAttribute( "SEARCHED_HUB",branch_plant );
				session.setAttribute( "SEARCHED_STRING",User_Search );

				buildHeader( User_Search,invengrp,count,branch_plant,updatecount);

				buildDetails( openorder_data,out_bin_set2,branch_plant,"","NONE");

				out.close();
				//clean up
				openorder_data=null;
				in_bin_set=null;
				out_bin_set2=null;
				lot_status_set=null;
			  } //when there are open orders for hub
			}
			else if ( User_Action.equalsIgnoreCase("Update") )
			{
				SubUser_Action= request.getParameter("SubUserAction");
				if (SubUser_Action == null)
				  SubUser_Action = "JSError2";

				Vector retrn_data = (Vector) session.getAttribute("NEW_TAP_DATA");
				Vector synch_data =  SynchServerData( request,retrn_data,SubUser_Action);
				retrn_data = null;

				if ( SubUser_Action.equalsIgnoreCase("AddBin"))
				{
					String AddBin_Line_No = "0";
					addbin_item_id     = request.getParameter("AddBin_Item_Id");
					if (addbin_item_id == null)
						  addbin_item_id = "";
					addbin_bin_id     = request.getParameter("AddBin_Bin_Id");
					if (addbin_bin_id == null)
						  addbin_bin_id = "";
					AddBin_Line_No = request.getParameter("AddBin_Line_No");
					if (AddBin_Line_No == null)
						  AddBin_Line_No = "0";

					setAddBinLineNum(Integer.parseInt(AddBin_Line_No));

					Hashtable all_bin_set_a    = (Hashtable)session.getAttribute("NEW_TAP_SET");
					Hashtable all_new_bin_set = addToItemBinSet(addbin_item_id, addbin_bin_id , all_bin_set_a);
					//
					session.setAttribute("DATA", synch_data );

					Hashtable sum= ( Hashtable ) ( synch_data.elementAt( 0 ) );
					int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

					buildHeader(User_Search,invengrp,count,branch_plant,updatecount);
					buildDetails(synch_data, all_new_bin_set,branch_plant,"",addbin_item_id);
					out.close();
					//clean up
					synch_data        = null;
					all_new_bin_set   = null;
					all_bin_set_a     = null;
					//
				}
				else if ( SubUser_Action.equalsIgnoreCase("UpdPage") )
				{
					Hashtable temp1        = (Hashtable)session.getAttribute("NEW_TAP_SET");

					session.setAttribute("NEW_TAP_DATA", synch_data );
					Hashtable updateresults = UpdateDetails(synch_data, personnelid,SubUser_Action);
					session.setAttribute("UPDATEDONE", "YES");

					Boolean resultfromup = (Boolean)updateresults.get("RESULT");
					Vector errordata = (Vector)updateresults.get("ERRORDATA");
					Vector errMsgs  = hubObj.getErrMsgs();
					session.setAttribute("NEW_TAP_DATA", errordata );

					Hashtable sum= ( Hashtable ) ( errordata.elementAt( 0 ) );
					int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

					boolean resulttotest =  resultfromup.booleanValue();

					Vector vList=hubObj.getLotSeqList();

					if ( vList.size() == 1 )
					{
					  String printreciplabel = vList.elementAt(0).toString();
					  session.setAttribute( "PRINT_TAP_ID_LABEL",printreciplabel );
					}

		            //StringBuffer Msg = new StringBuffer();
					out.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.startnewtap","newtap.js",intcmIsApplication,res ) );
					out.println( "</HEAD>  \n" );
					out.println( "<FORM method=\"POST\" NAME=\"closetap\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"\">\n" );
					out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
					out.println( "<TR VALIGN=\"MIDDLE\">\n" );
					out.println( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"CENTER\" CLASS=\"announce\">\n" );

					if ( true == resulttotest )
					{
					  if (completeSuccess)
					  {
						out.println( "<BODY onLoad=\"printtaplabelandclose()\">" );
						out.println( res.getString("msg.starttapsucc")+"\n<BR><BR>"+ res.getString("label.refreshmainpage") );
					  }
					}
					else
					{
					  out.println( "<BODY>" );
					  out.println( res.getString("msg.errorstarttap")+"<BR><BR>"+res.getString("msg.errcontacttech") );
					}
					out.println( "</TD>\n" );
					out.println( "</TR>" );
					out.println( "</TABLE>\n" );
					out.println( "<BR>\n" );
					out.println( "<CENTER><INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\""+res.getString("label.ok")+"\" OnClick=\"submitmainpage()\" NAME=\"sendterv\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
					out.println( "</CENTER>\n" );
					out.println( "</BODY></HTML>" );
					//out.println(Msg);
					//clean up
					synch_data        = null;
					temp1             = null;
					//
				}
				else
				{
					out.println(radian.web.HTMLHelpObj.printError("label.startnewtap",intcmIsApplication,res));
				}

			}//End of User_Session="Active" && User_Action="Update"
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("label.startnewtap",intcmIsApplication,res));
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

			String bin_name="";
			bin_name=request.getParameter( ( "selectElementBin" + i ) );
			if ( bin_name == null )
			  bin_name="";
			//if ( !bin_name.equalsIgnoreCase( hD.get( "BIN_NAME" ).toString() ) )
			if (bin_name.length() > 0)
			{
			  hD.remove( "BIN_NAME" );
			  hD.put( "BIN_NAME",bin_name );

			  //hD.remove( "DOSTATUSUPDATE" );
			  //hD.put( "DOSTATUSUPDATE","Yes" );
			}

			new_data.addElement( hD );
		  }
		}
		catch ( Exception e )
		{
		  e.printStackTrace();
		  out.println( radian.web.HTMLHelpObj.printError( "label.startnewtap",intcmIsApplication,res ) );
		}
		return new_data;
	}

	private Hashtable addToItemBinSet(String item_id, String bin_id, Hashtable in_set)
	{
		Hashtable bin_set = new Hashtable();
		try
		{
			bin_set = (Hashtable)in_set.get(item_id);
			int size = bin_set.size();
			if ( ( size == 1) && bin_set.containsValue("NONE"))
			{
			  bin_set.remove( new Integer(0));
			  bin_set.put( new Integer(0) , bin_id );
			}
			else
			{
			  bin_set.put( new Integer(size) , bin_id );
			}
			in_set.put(item_id, bin_set);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("label.startnewtap",intcmIsApplication,res));
		}
		return in_set;
	}

	private Hashtable createItemBinSet(Vector data, String branch_plant )
	{
		String item_id ;
		Hashtable bin_set = new Hashtable();
		Hashtable sum = ( Hashtable)( data.elementAt(0));
		int count = ((Integer)sum.get("TOTAL_NUMBER")).intValue();

		try
		{
			for ( int i = 1 ; i< count+1 ; i++)
			{
				Hashtable hD = new Hashtable();
				hD = ( Hashtable)( data.elementAt(i));
				item_id = (String)hD.get("ITEM_ID");
				if ( false == bin_set.containsKey(item_id))
				{
						Hashtable bin_for_item = radian.web.HTMLHelpObj.CreateBinForItem( db,item_id,branch_plant );
						bin_set.put( item_id, bin_for_item);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("label.startnewtap",intcmIsApplication,res));
		}

		return bin_set;
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
				String dostatusupdate = "";

				Line_Check      =  (hD.get("USERCHK")==null?" ":hD.get("USERCHK").toString());
				dostatusupdate     =  (hD.get("DOSTATUSUPDATE")==null?" ":hD.get("DOSTATUSUPDATE").toString());

				if ("yes".equalsIgnoreCase(Line_Check) && this.getupdateStatus() )
				{
					noneToUpd = false;
					linecheckcount++;
					boolean line_result = false;
					line_result = hubObj.startnewTap( hD, logonid ); // update database

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

			if (linecheckcount == 1){result = true;}
			if ( true == one_success )
			{
			result = true;
			}
		}
		catch (Exception e)
		{
			result = false;
			e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("label.startnewtap",intcmIsApplication,res));
		}

		updateresult.put("RESULT",new Boolean(result));
		updateresult.put("ERRORDATA",errordata);

		return updateresult;
	}

	private void buildHeader( String search_text,String selinvengrp,int total,String hubnumber,String updatecount )
	{
	  //StringBuffer Msg=new StringBuffer();
	  try
	  {
		out.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.startnewtap","newtap.js",intcmIsApplication,res ) );
		out.println( "<SCRIPT SRC=\"/clientscripts/invengrpdropdown.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
		out.println( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
		out.println( "</HEAD>  \n" );
		out.println( "<BODY>\n" );

		out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
		out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT><center>\n" );
		out.println( "</DIV>\n" );
		out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

		out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>Start New Tap</B>\n" ) );
		out.println( "<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + radian.web.tcmisResourceLoader.getnewtapurl() + "\">\n" );
		out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
		out.println( "<TR>\n" );

		//Hub
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<B>"+res.getString("label.inventorygroup")+":</B>&nbsp;\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"20%\">\n" );
		out.println( "<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invengrp\" value=\"" + selinvengrp + "\" size=\"10\">\n" );
		out.println( "</TD>\n" );

		out.println( "<TD WIDTH=\"15%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		//out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" value=\"" + search_text + "\" size=\"19\">\n" );
		out.println( "</TD>\n" );

		// Search
		out.println( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		//out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return newtapsearch(this)\" NAME=\"SearchButton\">&nbsp;\n" );
		out.println( "</TD>\n" );

		//Update
		out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\">\n" );
		if ( this.getupdateStatus() )
		{
		  String style = "";
		  if (!"1".equalsIgnoreCase(updatecount))
		  {
			style="STYLE=\"display: none;\"";
		  }

		  out.println( "<INPUT TYPE=\"submit\" VALUE=\""+res.getString("label.tap")+"\" onclick= \"return update(this)\" NAME=\"startnewtap\" "+style+" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
		}
		else
		{
		  out.println( "&nbsp;\n" );
		}
		out.println( "</TD>\n" );

		//Cancel
		out.println( "<TD WIDTH=\"35%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		out.println( "&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"Cancel\" value=\""+res.getString("label.cancel")+"\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
		out.println( "</TD>\n" );
		out.println( "</TR>\n" );
		out.println( "</TABLE>\n" );

		out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n" );
		out.println( "<tr><td>" );
		out.println( "<input type=\"hidden\" name=\"Total_number\" value=\"" + total + "\">\n" );
		out.println( "<input type=\"hidden\" name=\"hubnum\" value=\"" + hubnumber + "\">\n" );
		out.println( "<input type=\"hidden\" name=\"updatecount\" value=\"" + updatecount + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"DuplLineNumber\" VALUE=\" \">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Item_Id\" VALUE=\" \">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Bin_Id\" VALUE=\" \">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Line_No\" VALUE=\" \">\n" );
		out.println( "</TD></tr>" );
		out.println( "</table>\n" );
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		out.println( radian.web.HTMLHelpObj.printError( "label.startnewtap",intcmIsApplication,res ) );
	  }

	  return;
	}

private void buildDetails(Vector data,Hashtable all_bin_set,String HubNo,String SubuserAction,String Add_Item_id)
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
				out.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.item")+"Item</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.pkg")+"</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.receiptid")+"</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.mfglot")+"</TH>\n");
				out.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.bin")+"</TH>\n");
				out.println("<TH width=\"20%\"  height=\"38\">"+res.getString("label.parentdesc")+"</TH>\n");
				//out.println("<TH width=\"5%\"  CLASS=\"dgreen\" height=\"38\">Child Item</TH>\n");
				//out.println("<TH width=\"5%\"  CLASS=\"dgreen\" height=\"38\">Child Pkg</TH>\n");
				out.println("<TH width=\"4%\"  CLASS=\"dgreen\" height=\"38\" colspan=\"2\">"+res.getString("label.bin")+"</TH>\n" );
				//out.println("<TH width=\"20%\"  height=\"38\">Child Description</TH>\n");
				out.println("</tr>\n");
			}

			Hashtable hD = new Hashtable();
			hD = (Hashtable) data.elementAt(i);

			String hub=hD.get( "HUB" ) == null ? "&nbsp;" : hD.get( "HUB" ).toString();
			String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
			String inventory_group=hD.get( "INVENTORY_GROUP" ) == null ? "&nbsp;" : hD.get( "INVENTORY_GROUP" ).toString();
			String original_item_id=hD.get( "ORIGINAL_ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ORIGINAL_ITEM_ID" ).toString();
			String receipt_id=hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString();
			String Sel_bin=hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString();
			String mfg_lot=hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString();
			String expire_date=hD.get( "EXPIRE_DATE" ) == null ? "&nbsp;" : hD.get( "EXPIRE_DATE" ).toString();
			String lot_status=hD.get( "LOT_STATUS" ) == null ? "&nbsp;" : hD.get( "LOT_STATUS" ).toString();
			String quantity_available=hD.get( "QUANTITY_AVAILABLE" ) == null ? "&nbsp;" : hD.get( "QUANTITY_AVAILABLE" ).toString();
			String item_desc=hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString();
			String original_item_desc=hD.get( "ORIGINAL_ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ORIGINAL_ITEM_DESC" ).toString();
			String item_pkg=hD.get( "ITEM_PKG" ) == null ? "&nbsp;" : hD.get( "ITEM_PKG" ).toString();
			String original_item_pkg=hD.get( "ORIGINAL_ITEM_PKG" ) == null ? "&nbsp;" : hD.get( "ORIGINAL_ITEM_PKG" ).toString();
			String search_text=hD.get( "SEARCH_TEXT" ) == null ? "&nbsp;" : hD.get( "SEARCH_TEXT" ).toString();
		    String bin=hD.get( "BIN" ) == null ? "" : hD.get( "BIN" ).toString();

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
			String childColor = "CLASS=\"white";
			if (i%2==0)
			{
				Color ="CLASS=\"blue";
				childColor = "CLASS=\"green8";
			}
			else
			{
				Color ="CLASS=\"white";
			}

		out.println( "<tr align=\"center\">\n" );
		out.println( "<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" +chkbox_value+ "\"  onClick= \"checkValues(name,"+i+")\" " + checkednon + "  NAME=\"row_chk" + i + "\"></td>\n" );
		out.println( "<td " + Color + "l\" width=\"5%\">" + original_item_id + "</td>\n" );
		out.println( "<td " + Color + "l\" width=\"5%\">" + original_item_pkg + "</td>\n" );
		out.println( "<td " + Color + "l\" width=\"5%\">" + receipt_id + "</td>\n" );
		out.println( "<td " + Color + "l\" width=\"5%\">" + mfg_lot + "</td>\n" );
		out.println( "<td " + Color + "l\" width=\"5%\">" + bin + "</td>\n" );

		out.println( "<td " + Color + "l\" width=\"20%\">" + original_item_desc + "</td>\n" );

		//out.println( "<td " + childColor + "l\" width=\"5%\">" + item_id + "</td>\n" );
		//out.println( "<td " + childColor + "l\" width=\"5%\">" + item_pkg + "</td>\n" );
		out.println( "<td " + childColor + "r\" width=\"1%\">\n" );
		out.println("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"addBin" + i +"\" value=\"+\" OnClick=\"showEmtyBins(" + item_id + "," + i + "," + HubNo + ")\" ></TD>\n" );
		out.println( "<td " + childColor + "l\" width=\"3%\">\n" );
		out.println( "<select name=\"selectElementBin" + i + "\">\n" );

		Hashtable in_bin_set= ( Hashtable ) all_bin_set.get( item_id );
		int bin_size=in_bin_set.size();

		if ( Sel_bin.trim().length() == 0 && bin.trim().length() == 0 )
		{
		  out.println( "<option selected value=\"\">"+res.getString("label.none")+"</option>\n" );
		}
		else
		{
		  out.println( "<option selected value=\""+bin+"\">"+bin+"</option>\n" );
		}

		//Select the last bin that was added for an item
		String bin_selected="";
		if ( Add_Item_id.equalsIgnoreCase( "NONE" ) )
		{
		  for ( int j=0; j < bin_size; j++ )
		  {
			Integer key=new Integer( j );
			String bin_name= ( String ) in_bin_set.get( key );
			bin_selected="";
			if ( bin_name.equalsIgnoreCase( Sel_bin ) )
			{
			  bin_selected="selected";
			}
			out.println( "<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name + "</option>\n" );
		  }
		}
		else
		{
		  for ( int m=0; m < bin_size; m++ )
		  {
			Integer key=new Integer( m );
			String bin_name= ( String ) in_bin_set.get( key );
			bin_selected="";

			if ( i == AddBinLineNum1 )
			{
			  if ( m == ( bin_size - 1 ) )
			  {
				bin_selected="selected";
			  }
			}
			else
			{
			  if ( bin_name.equalsIgnoreCase( Sel_bin ) )
			  {
				bin_selected="selected";
			  }
			}

			out.println( "<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name + "</option>\n" );
		  }
		}

		out.println( "</select></td>\n" );

		//out.println( "<td " + Color + "l\" width=\"20%\">" + item_desc + "</td>\n" );

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
		out.println(radian.web.HTMLHelpObj.printError("label.startnewtap",intcmIsApplication,res));
	}

	return;
}
//End of method
}
//END OF CLASS

