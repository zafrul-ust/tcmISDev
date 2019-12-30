package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

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
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.web.HTMLHelpObj;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 12-15-03 Code Cleanup
 * 02-20-04 Giving the option to choose if the new bin is At the hub or On the client site
 * 03-08-04 Giving an error message if add bin fails
 */

public class addnewBin   extends HttpServlet   implements SingleThreadModel
{
  private boolean intcmIsApplication = false;

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

  public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
	PrintWriter out=response.getWriter();
	response.setContentType( "text/html" );
	HttpSession session=request.getSession();
	TcmISDBModel db=null;
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
          PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
          Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
          intcmIsApplication = false;
          if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
          {
           intcmIsApplication = true;
          }


	  if ( auth == null )
	  {
		auth="challenge";
	  }
	  if ( errormsg == null )
	  {
		errormsg="";
	  }

	  if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
	  {
		out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Add New Bin" ) );
		out.flush();
	  }
	  else
	  {
		if ( radian.web.HTMLHelpObj.hasaccessto( session,"Inventory" ) )
		{

		}
		else
		{
		  out.println( radian.web.HTMLHelpObj.printMessageinTable( "You must be a member of the 'Inventory' group to access this page." ) );
		  out.close();
		  return;
		}

		String Hub=BothHelpObjs.makeSpaceFromNull( request.getParameter( "HubName" ) );
		if ( Hub == null )
		  Hub="";
		
		String room = request.getParameter("room");

		String newbinname=BothHelpObjs.makeSpaceFromNull( request.getParameter( "newbin" ) );
		if ( newbinname == null )
		  newbinname="";

		String User_Action=request.getParameter( "UserAction" );
		if ( User_Action == null )
		  User_Action="New";

		String onsite=request.getParameter( "onsite" );
		if ( onsite == null )
		  onsite="";
		onsite = onsite.trim();
		if (onsite.length() == 0) {onsite = "N";}

// receiving only		
		String binType=request.getParameter( "binType" );

		//StringBuffer Msgn=new StringBuffer();
		out.println( "<HTML>\n" );
		out.println( "<HEAD>\n" );
		out.println( "<TITLE>Add New Bin</TITLE>\n" );
		out.println( "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n" );
		out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
		out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
		out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
		out.println( "<SCRIPT SRC=\"/clientscripts/receivingjs.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n" );
		out.println( "</HEAD>\n" );
		out.println( "<BODY>\n" );

		boolean addsucess = false;
		String errmsg = "";
		Hashtable hub_list_set = new Hashtable();
		hub_list_set= (Hashtable)session.getAttribute("HUB_PREF_LIST");

		if ( "Update".equalsIgnoreCase( User_Action ) )
		{
		  String hubname=radian.web.HTMLHelpObj.gethubNamefromBP( hub_list_set,Hub );
		  //PersonnelBean personnelBean=session.getAttribute( "personnelBean" ) == null ? null :( PersonnelBean ) session.getAttribute( "personnelBean" );
		  if ( !personnelBean.getPermissionBean().hasInventoryGroupPermission( "addNewBin",null,hubname,null ) )
		  {
			errmsg = "You Don't have permissions to create a new BIN.";
		  }

		  try
		  {
			String query="insert into vv_hub_bins (BRANCH_PLANT,ROOM,BIN,ON_SITE,BIN_TYPE) VALUES ('" + Hub + "','" + room.replace("'", "''") + "','" + newbinname.replace("'", "''") + "','"+onsite+"','" + binType.replace("'", "''") +"')";
			db.doUpdate( query );
			addsucess = true;
		  }
		  catch ( Exception e )
		  {
			addsucess = false;
			errmsg = "Bin not added. Please try again or contact tech support.";
			e.printStackTrace();
		  }
		}

		if ( "Update".equalsIgnoreCase( User_Action ) && addsucess )
		{
		  out.println( "<CENTER><TD WIDTH=\"20%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		  out.println( "<font face=Arial SIZE=3><B>New Bin Added.</B>&nbsp;<BR><BR>Refresh search to use it.\n" );
		  out.println( "</TD>\n" );

		  out.println( "<BR><BR><BR>\n" );
		  out.println( "<td width=\"50%\" align=\"left\"><INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\"></FONT></td>\n" );
		}
		else
		{
		  out.println( "<form method=\"POST\" name=\"form1\">\n" );
		  out.println(errmsg);
		  out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"4\" WIDTH=\"250\" CLASS=\"moveup\">\n" );
		  out.println( "<TR VALIGN=\"MIDDLE\">\n" );

		  //Hub
		  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		  out.println( "<B>Hub:</B>&nbsp;\n" );
		  out.println( "</TD>\n" );
		  out.println( "<TD WIDTH=\"80%\">\n" );
		  //out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"HubName\" size=\"1\">\n" );
		  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		  String hub_selected;
		  for ( Enumeration e=hub_list.keys(); e.hasMoreElements(); )
		  {
			String branch_plant= ( String ) ( e.nextElement() );
			String hub_name= ( String ) ( hub_list.get( branch_plant ) );

			hub_selected="";
			if ( branch_plant.equalsIgnoreCase( Hub ) )
			{
			  hub_selected="selected";
			  out.println( hub_name );
			}
			//out.println( "<option  " + hub_selected + " value=\"" + branch_plant + "\">" + hub_name + "</option>\n" );
		  }
		  //out.println( "</SELECT>\n" );
		  out.println( "</TD>\n</TR>\n<TR>" );
		  
		  // Room
		  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		  out.println( "<B>Room:</B>&nbsp;\n" );
		  out.println( "</TD>\n" );
		  out.println( "<TD WIDTH=\"80%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		  out.println( "<SELECT CLASS=\"HEADER\" name=\"room\" id=\"room\">\n" );
		  out.println(radian.web.HTMLHelpObj.getHubRoomDropDown(db,Hub));
		  out.println( "</SELECT>\n" );
		  out.println( "</TD>\n</TR>\n<TR>\n" );

		  // Bin Name
		  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		  out.println( "<B>Bin:</B>&nbsp;\n" );
		  out.println( "</TD>\n" );
		  out.println( "<TD WIDTH=\"80%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" ID=\"newbin\" NAME=\"newbin\" value=\"\" maxsize=\"30\" size=\"18\">\n" );
		  out.println( "</TD>\n</TR>\n<TR>\n" );

          // On Site
		  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		  out.println( "<B>At the hub:</B>&nbsp;\n" );
		  out.println( "</TD>\n" );
		  out.println( "<TD WIDTH=\"80%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		  out.println( "<INPUT CLASS=\"HEADER\" type=\"checkbox\" name=\"onsite\" id=\"onsite\" value=\"Y\" checked>\n" );
		  out.println( "</TD>\n</TR>\n<TR>\n" );

          // Receiving Only
		  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		  out.println( "<B>Bin Type:</B>&nbsp;\n" );
		  out.println( "</TD>\n" );
		  out.println( "<TD WIDTH=\"80%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		  out.println( "<SELECT CLASS=\"HEADER\" name=\"binType\" id=\"binType\">\n" );
		  out.println( "<option value=\"\">Please Select</option>\n" );
		  Vector binTypes= ( Vector ) ( radian.web.HTMLHelpObj.createBinTypeList(db) );
		  for ( Object bin_type : binTypes )
		  {
			String bin_selected="";
			if ( bin_type.toString().equalsIgnoreCase( binType ) )
			{
			  bin_selected="selected";
			}
			out.println( "<option  " + bin_selected + " value=\"" + bin_type + "\">" + bin_type + "</option>\n" );
		  }
		  out.println( "</SELECT>\n" );
		  out.println( "</TD>\n</TR>\n<TR>\n" );
		  
		  out.println( "<td width=\"50%\" align=\"right\"><input CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Ok\" id=\"Login\" name=\"Login\" onclick=\"return validateForm();\"></td>\n" );
		  out.println( "<td width=\"50%\" align=\"left\"><INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"searchlike\" id=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></td>\n" );

		  out.println( "</TR></TABLE>\n" );
		}
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" ID=\"UserAction\" VALUE=\"Update\">\n" );
		out.println( "</FORM></BODY></HTML>\n" );
		//out.println( Msgn );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  out.println( radian.web.HTMLHelpObj.printError( "Add New Bin",intcmIsApplication ) );
	  return;
	}
	finally
	{
	  db.close();
	}

	out.close();
	//Runtime.getRuntime().gc();
  }
}