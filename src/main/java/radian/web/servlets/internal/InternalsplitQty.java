package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.splitQty;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 11-26-03 Can not split into a pickable status if you don't have permissions
 * 02-28-05 Giving the option to not print Kit/Case Qty Labels.
 */

public class InternalsplitQty  extends HttpServlet implements SingleThreadModel
{

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }


    /**
    * HTTP POST handler.
    *
    * @param request               An object implementing the request context
    * @param response              An object implementing the response context
    * @exception ServletException  A wrapper for all exceptions thrown by the
    *      servlet.
    */

	 public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	 {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		RayTcmISDBModel db = null;
		String branch_plant;
		splitQty obj = null;
		String User_Action = "";
		HttpSession session = request.getSession();

	try
	{
	 db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
	 if (db == null)
	 {
		PrintWriter out2 = new PrintWriter (response.getOutputStream());
		out2.println("Starting DB");
		out2.println("DB is null");
		out2.close();
		return;
	 }

	 Hashtable loginresult = new Hashtable();
	 loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
	 String auth = (String)loginresult.get("AUTH");
	 String errormsg = (String)loginresult.get("ERROSMSG");

	 if (auth == null) {auth = "challenge";}
	 if (errormsg == null) {errormsg = "";}

	 if (! ( auth.equalsIgnoreCase( "authenticated" ) ) )
	 {
		out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Split Quantity" ) );
		out.flush();
	 }
	 else
	 {
		Hashtable hub_list_set=new Hashtable();
		hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );
		Hashtable initialinvData = new Hashtable ();

        String opsEntityId=request.getParameter( "opsEntityId" );
		if ( opsEntityId == null )
		 opsEntityId="";
		opsEntityId=opsEntityId.trim();
       
        Hashtable hub_list1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		if (hub_list1.size() > 0)
		{
		 initialinvData=radian.web.poHelpObj.getinvgrpInitialData( db,hub_list_set,true,false,opsEntityId );
		 session.setAttribute( "INVENGRP_DATA",initialinvData );
		}

		try{ branch_plant = request.getParameter("HubNo").toString();}catch (Exception e){ branch_plant = "";}
		String personnelId= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		Hashtable iniinvendata= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );

		obj = new splitQty((ServerResourceBundle) new InternalServerResourceBundle(),db);
		//StringBuffer Msg = new StringBuffer();

		User_Action= request.getParameter("UserAction");
		if (User_Action == null)
		 User_Action = "New";

		String SubUser_Action= request.getParameter("SubUserAction");
		if (SubUser_Action == null)
		 SubUser_Action = "JSError2";

		String invengrp=request.getParameter( "invengrp" );
		if ( invengrp == null )
		 invengrp="";
		invengrp=invengrp.trim();

		String printKitLabels = request.getParameter("printKitLabels");
		if (printKitLabels == null)
		 printKitLabels = "";

		String netPendingAdj = request.getParameter("netPendingAdj");
		if (netPendingAdj == null)
		 netPendingAdj = "";

		String movePendingAdjustment = request.getParameter("movePendingAdjustment");
		if (movePendingAdjustment == null)
		 movePendingAdjustment = "";

        String receiptid  = BothHelpObjs.makeBlankFromNull(request.getParameter("receiptid"));
		String addbin  = BothHelpObjs.makeBlankFromNull(request.getParameter("AddBin_Bin_Id"));
		//String maxsplitqty  = BothHelpObjs.makeBlankFromNull(request.getParameter("maxsplitqty"));
		//String splitqty  = BothHelpObjs.makeBlankFromNull(request.getParameter("splitqty"));
		String maxsplitqty = request.getParameter("maxsplitqty");
		if ( maxsplitqty == null )
		 maxsplitqty="";
		if (maxsplitqty.trim().length() ==0)
		 maxsplitqty = "0";

		String splitqty = request.getParameter("splitqty");
		if ( splitqty == null )
		 splitqty="";
		if (splitqty.trim().length() ==0)
		 splitqty = "0";

		boolean redraw = false;
		if ( splitqty =="0")
		{
		 redraw = true;
		}

		BigDecimal maxSplitQuantity = new BigDecimal(maxsplitqty);
		BigDecimal splitQuantity = new BigDecimal(splitqty);

		String lotstatus  = BothHelpObjs.makeBlankFromNull(request.getParameter("selectElementStatus"));
		String bin  = BothHelpObjs.makeBlankFromNull(request.getParameter("selectElementBin"));
		String qtyavailable  = BothHelpObjs.makeBlankFromNull(request.getParameter("QtyAvailable"));
		String origlotstatus  = BothHelpObjs.makeBlankFromNull(request.getParameter("origlotstatus"));
		String qualitycntitem  = BothHelpObjs.makeBlankFromNull(request.getParameter("qualitycntitem"));


		boolean certupdate = false;
		//int maxsplit = 0;
		//int split = 0;
		Hashtable all_bin_set_d    = (Hashtable)session.getAttribute("BIN_SET");
		Vector all_status_set_d1 = (Vector)session.getAttribute("STATUS_SET");
		Vector lotstatus_invengrps= ( Vector ) session.getAttribute( "STATUS_SET_ALLOWED_INVENGRP" );

		//System.out.println(" qtyavailable "+qtyavailable+" receiptid "+receiptid+" addbin "+addbin+" maxsplitqty "+maxsplitqty+" splitqty "+splitqty+" User_Action  "+User_Action+"  SubUser_Action"+SubUser_Action+"  lotstatus"+lotstatus+" "+branch_plant);
		if ( "Updatesplit".equalsIgnoreCase(SubUser_Action) )
		{
		 try {
			DBResultSet dbrs = null;
			ResultSet rs = null;
			dbrs = db.doQuery("select fx_quality_control_item_id (x.item_id,'"+invengrp+"') QUALITY_CONTROL_ITEM from receipt x where receipt_id = " +
			 receiptid + "");
			rs = dbrs.getResultSet();
			while (rs.next()) {
			 qualitycntitem = rs.getString("QUALITY_CONTROL_ITEM") == null ? "" :	rs.getString("QUALITY_CONTROL_ITEM");
			}
		 }
		 catch (Exception e) {
			e.printStackTrace();
			return;
	   }

		 //try{maxsplit = Integer.parseInt(maxsplitqty);}catch(Exception maxp){maxsplit =0;}
		 //try{split = Integer.parseInt(splitqty);}catch(Exception spli){split = 0;}

		 if ( "Y".equalsIgnoreCase( qualitycntitem ) )
		 {
			//if ( !origlotstatus.equalsIgnoreCase( lotstatus ) )
			{
			 for ( int h=0; h < all_status_set_d1.size(); h++ )
			 {
				Hashtable data1= ( Hashtable ) all_status_set_d1.elementAt( h );
				Enumeration E;
				for ( E=data1.keys(); E.hasMoreElements(); )
				{
				 String key= ( String ) E.nextElement();
				 String keyvalue=data1.get( key ).toString();

				 if ( lotstatus.equalsIgnoreCase( key ) && "Y".equalsIgnoreCase( keyvalue ) )
				 {
					certupdate=true;
				 }
				}
			 }
			}
		 }
		}

		//		out.println("<BR>");

		Vector statusNoterminal = new Vector();

		for (int z=0; z < all_status_set_d1.size(); z++)
		{
		 Hashtable data1= ( Hashtable ) all_status_set_d1.elementAt( z );
		 Enumeration E;
		 for ( E=data1.keys(); E.hasMoreElements(); )
		 {
			String key= ( String ) E.nextElement();
			String keyvalue=data1.get( key ).toString();
			if ( "NonConforming".equalsIgnoreCase(key) || "Customer Purchase".equalsIgnoreCase(key) || "Write Off Requested".equalsIgnoreCase(key) || "3PL Purchase".equalsIgnoreCase(key) )
			{

			}
			else
			{
			 statusNoterminal.addElement( data1 );
			}
		 }
		}

		int receiptId = 0;
		String errmsg = "";

		if ("AddBin".equalsIgnoreCase(SubUser_Action) || redraw)
		{
		 obj.buildsplitQty(all_bin_set_d,statusNoterminal,receiptid,branch_plant,addbin.trim(),splitqty,iniinvendata,lotstatus_invengrps,"",netPendingAdj,out);
		}
		else if ("Updatesplit".equalsIgnoreCase(SubUser_Action) )
		{
		 //System.out.println("compare "+splitQuantity.compareTo(maxSplitQuantity)+"");
		 if ((splitQuantity.compareTo(maxSplitQuantity) == -1 || splitQuantity.compareTo(maxSplitQuantity) ==0))
		 {
			try
			{
			 Connection connect1 = db.getConnection();
			 CallableStatement cstmt = connect1.prepareCall("{call p_receipt_split(?,?,?,?,?,?,?,?,?,?,?,?)}");
			 GregorianCalendar gcDate_mfgd = new GregorianCalendar();
			 Timestamp CountDateTimeStamp = new Timestamp(gcDate_mfgd.getTime().getTime());

			 cstmt.setString(1, branch_plant);
			 cstmt.setString(2, bin);
			 cstmt.setTimestamp(3, CountDateTimeStamp);
			 cstmt.setBigDecimal(4, splitQuantity);
			 cstmt.setString(5, lotstatus);
			 cstmt.setString(6, personnelId);
			 cstmt.setInt(7, Integer.parseInt(receiptid));
			 cstmt.setString(8, invengrp);
			 if (certupdate){cstmt.setString(9,personnelId);}else{cstmt.setNull(9,java.sql.Types.VARCHAR);}

			 cstmt.registerOutParameter(10, Types.INTEGER);
			 cstmt.registerOutParameter(11, Types.VARCHAR);

             if (movePendingAdjustment.equalsIgnoreCase("Y"))
             {
               cstmt.setString(12, "Y");
             }
             else
             {
               cstmt.setString(12, "N");
             }
             cstmt.executeQuery();

			 receiptId = cstmt.getInt(10);
			 errmsg = cstmt.getString(11);

			 //System.out.println("New Split Receipt Id: "+receiptId);
			 //System.out.println("Error Message: "+errmsg);

			 connect1.commit();
			 cstmt.close();

			}
			catch (Exception e)
			{
			 e.printStackTrace();
       errmsg = e.getMessage();
			}
		 }

		 if (receiptId > 0)
		 {
			Vector vList = new Vector();
			vList.addElement(""+receiptId+"");
			session.setAttribute("REC_LABEL_SEQUENCE_NUMBERS", vList);

			out.println("<HTML>\n");
			out.println("<HEAD>\n");
			out.println("<TITLE>Split Quantity</TITLE>\n");
			out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
			out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
			out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
			out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
			out.println("<SCRIPT SRC=\"/clientscripts/receivingjs.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
			out.println("</HEAD>\n");
			out.println("<BODY>\n");
			out.println("<font face=Arial SIZE=2>The quantity was split and the new Receipt Id is :  <B>"+receiptId+"</B><BR><BR>\n");
			out.println("Please refresh search to view results.</FONT><BR><BR>\n");

			out.println("<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"/tcmIS/Hub/Receiving?session=Active&generate_labels=yes&UserAction=GenerateLabels\">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"contBin\" VALUE=\" \">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"Paper\" VALUE=\"31\">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"HubNoforlabel\" VALUE=\""+splitqty+"\">\n");

			//start table #1
			out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
			out.println("<TR VALIGN=\"MIDDLE\">\n");

			//Options
			out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
			out.println("<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('31')\" value=\"31\" checked>\n");
			out.println("</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option3\">3''/1''&nbsp;</TD>\n");
			out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
			out.println("<INPUT ID=\"option2\" TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('811')\" value=\"811\">\n");
			out.println("</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option4\">8.5''/11''</TD>\n\n");
			//Button
			out.println("<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
			out.println("   <INPUT TYPE=\"BUTTON\" ID=\"genbutton1\" VALUE=\"Container Labels\" NAME=\"SearchButton\" onclick= \"javascript:doRecPopup(this,'CONTAINER')\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\">&nbsp;\n");
			out.println("</TD>\n");
			out.println("</TD></TR>\n");

			out.println("<TR VALIGN=\"MIDDLE\">\n");
			out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
			out.println( "<INPUT type=\"checkbox\" name=\"printKitLabels\" value=\"Yes\" " + ( printKitLabels.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
			out.println( "</TD>\n" );
			out.println( "<TD WIDTH=\"5%\" COLSPAN=\"4\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
			out.println( "&nbsp;Skip Kit/Case Qty Labels\n" );
			out.println( "</TD>\n" );
			out.println("</TR></TABLE>\n");

			out.println("<BR>&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"searchlike\" value=\"Close\" OnClick=\"cancel()\"></CENTER></P>\n");
			out.println("</BODY></HTML>\n");
		 }
		 else
		 {
			obj.buildsplitQty(all_bin_set_d,statusNoterminal,receiptid,branch_plant,addbin.trim(),splitqty,iniinvendata,lotstatus_invengrps,errmsg,netPendingAdj,out);
		 }
		}
		else if ("splitqty".equalsIgnoreCase(User_Action) )
		{
		 obj.buildsplitQty(all_bin_set_d,statusNoterminal,receiptid,branch_plant,addbin.trim(),splitqty,iniinvendata,lotstatus_invengrps,errmsg,netPendingAdj,out);
		}

		out.println("<BR>");
		//out.println(Msg);
	 }
	}
	catch (Exception e)
	{
	 e.printStackTrace();
	 return;
	}
	finally
	{
	 db.close();
	 obj = null;
	 try {
		out.close();
	 }
	 catch(Exception e) {
		//ignore
	 }
	 //Runtime.getRuntime().gc();
	 return;
	}
 }

 public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
 {
	doPost(request,response);
 }
}