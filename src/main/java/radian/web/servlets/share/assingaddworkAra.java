package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 */

public class assingaddworkAra
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private boolean allowupdate;
  private boolean intcmIsApplication = false;

  public void setupdateStatus( boolean id )
  {
    this.allowupdate=id;
  }

  private boolean getupdateStatus()
     throws Exception
  {
    return this.allowupdate;
  }

  public assingaddworkAra( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
    PrintWriter out=response.getWriter();
    response.setContentType( "text/html" );
    HttpSession relabelsession=request.getSession();

    PersonnelBean personnelBean = (PersonnelBean) relabelsession.getAttribute("personnelBean");
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
    intcmIsApplication = false;
    if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
    {
     intcmIsApplication = true;
    }

    String personnelid= relabelsession.getAttribute( "PERSONNELID" ) == null ? "" : relabelsession.getAttribute( "PERSONNELID" ).toString();

    String itemid=request.getParameter( "itemid" );
    if ( itemid == null )
      itemid="";
    itemid = itemid.trim();

    String User_Action=request.getParameter( "Action" );
    if ( User_Action == null )
      User_Action="";
    User_Action = User_Action.trim();

	String hubid=request.getParameter( "selHub" );
	if ( hubid == null )
	  hubid="";
	hubid = hubid.trim();

    String facility=request.getParameter( "facilityid" );
    if ( facility == null )
      facility="";
    facility = facility.trim();

	String application=request.getParameter( "application" );
	if ( application == null )
	  application="";
	application=application.trim();

    String companyid=request.getParameter( "companyid" );
    if ( companyid == null )
      companyid="";
    companyid = companyid.trim();

    String invengrp=request.getParameter( "invengrp" );
    if ( invengrp == null )
      invengrp="";
    invengrp = invengrp.trim();

	String depID=request.getParameter( "depID" );
	if ( depID == null )
	  depID="";
	depID=depID.trim();

	String areaID=request.getParameter( "areaID" );
	if ( areaID == null )
	  areaID="";
	areaID=areaID.trim();

	String processID=request.getParameter( "processID" );
	if ( processID == null )
	  processID="";
	processID=processID.trim();

	String selplantid=request.getParameter( "facilityName" );
	if ( selplantid == null )
	  selplantid="";
	selplantid=selplantid.trim();

	String selbldgid=request.getParameter( "workAreaName" );
	if ( selbldgid == null )
	  selbldgid="";
	selbldgid=selbldgid.trim();

//System.out.println("User_Action    "+User_Action+"");

	if ( "chgwrkareaass".equalsIgnoreCase( User_Action ) )
	{
	  out.println( buildchangeworkarea( itemid,invengrp,facility,companyid,application,"" ) );
	}
	else if ( "Updworkarea".equalsIgnoreCase( User_Action ) )
	{
	  out.println( updateWorkArea( itemid,invengrp,facility,companyid,application,depID,areaID,processID ) );
	}
	else if ( "addworkarea".equalsIgnoreCase( User_Action ) )
	{
	  Hashtable initialData= ( Hashtable ) relabelsession.getAttribute( "INITIAL_ITEM_INVENTORY_DATA" );
	  out.println( builaddworkarea( initialData,hubid,"" ) );
	}
	else if ( "addnewworkarea".equalsIgnoreCase( User_Action ) )
	{
	  Hashtable initialData= ( Hashtable ) relabelsession.getAttribute( "INITIAL_ITEM_INVENTORY_DATA" );
	  out.println( addnewWorkArea( initialData,selplantid,selbldgid,depID,areaID,processID,hubid ) );
    }

   out.close();
 }

 public StringBuffer addnewWorkArea (  Hashtable initialData,String plantId, String buildingId,String depid, String areaID, String processid,String hubId  )
 {
   StringBuffer Msg=new StringBuffer();
   DBResultSet dbrs=null;
   ResultSet rs=null;
   CallableStatement cs=null;
   boolean closeresult=false;
   String errorcode ="";
	try
	{
	  Connection connect1=db.getConnection();
	  cs=connect1.prepareCall( "{call P_tap_close(?,?,?)}" );
	  cs.setInt( 1,Integer.parseInt( "" ) );
	  cs.registerOutParameter(2,java.sql.Types.NUMERIC);
	  cs.registerOutParameter(3,java.sql.Types.VARCHAR);
	  cs.execute();

	  errorcode=cs.getString( 3 );
	  if ( errorcode == null )
	  {
		closeresult=true;
	  }
	  else
	  {
		closeresult=false;
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  closeresult = false;
	  radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure P_tap_close in Close Tap Screen","Error occured while running P_tap_close\n" + e.getMessage() +"\nFor Input Parameters Receipt Id: " );
	}
	finally
	{
	  if ( cs != null )
	  {
		try
		{
		  cs.close();
		}
		catch ( Exception se )
		{
		  se.printStackTrace();
		}
	  }
	}

	if (closeresult)
	{
	  Msg.append( builaddworkarea( initialData,hubId,"The Work Area was succesfully created" ) );
	}
	else
	{
	  Msg.append( builaddworkarea( initialData,hubId,"An error occured while processing."+errorcode+"<BR>Please try again." ));
	}

   return Msg;
 }

 public StringBuffer builaddworkarea( Hashtable initialData,String selectedHub,String errmessage )
  {
	StringBuffer Msg=new StringBuffer();
	DBResultSet dbrs=null;
	ResultSet rs=null;
	String currdep = "";
	String currarea = "";
	String currprocess= "";
	String storagearea= "";

	Msg.append( radian.web.HTMLHelpObj.printHTMLHeader( "Add New Work Area/Process","iteminventory.js",intcmIsApplication ) );
	Msg.append( "<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n" );
	Msg.append( radian.web.dropdwnHelpObj.createComboBoxData( initialData ) );
	Msg.append( "// -->\n </SCRIPT>\n" );
	Msg.append( "</HEAD>  \n" );
	Msg.append( "<BODY>\n" );

	Msg.append( radian.web.HTMLHelpObj.PrintTitleBarNolink( "Add New Work Area/Process\n" ) );
	if (errmessage.length() > 0 )
	{
	  Msg.append( "<BR>"+errmessage+"</BR>\n" );
	}

	Msg.append( "<FORM METHOD=\"POST\" name=\"iteminventory\" action=\"" + radian.web.tcmisResourceLoader.getassigninvgrpwkareaurl() + "Session=Update\">\n" );
	Msg.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	Msg.append( "<TR VALIGN=\"MIDDLE\">\n" );

	 if (selectedHub.trim().length() == 0 )
	 {
       Vector hubID = (Vector)initialData.get("HUB_ID");
	   selectedHub= ( String ) hubID.firstElement(); //select the first facility if none were selected
	 }

	 Msg.append("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
	 Msg.append("<B>Plant:</B>\n");
	 Msg.append("</TD>\n");
	 Msg.append("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
	 Msg.append("<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" ID=\"facId\" size=\"1\" OnChange=\"facilityChanged(document.iteminventory.facilityName)\">\n");
	 Hashtable fh = (Hashtable)initialData.get(selectedHub);
	 //System.out.println(fh);
	 Vector facID = (Vector)fh.get("PLANT_ID");
	 Vector facDesc = (Vector)fh.get("PLANT_DESC");
	 String selplantid = "";
	 if ( selplantid.trim().length() == 0 )
	 {
	   selplantid= ( String ) facID.firstElement(); //select the first facility if none were selected
	 }
	 String preSelect="";
	 for (int i=0; i < facID.size(); i++ )
	 {
	   preSelect="";
	   if ( selplantid.equalsIgnoreCase( ( String ) facID.elementAt( i ) ) )
	   {
		 preSelect="selected";
		 selplantid= ( String ) facID.elementAt( i );
	   }
	   Msg.append( "<option " + preSelect + " value=\"" + ( String ) facID.elementAt( i ) + "\">" + ( String ) facDesc.elementAt( i ) + "</option>\n" );
	 }
	 Msg.append("</SELECT>\n");
	 Msg.append("</TD>\n");
	 Msg.append( "</TR>\n" );

	 Msg.append( "<TR VALIGN=\"MIDDLE\">\n" );
	 // Building
	 Msg.append( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	 Msg.append( "<B>Building:</B>&nbsp;\n" );
	 Msg.append( "</TD>\n" );

	 Msg.append( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	 Msg.append("<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\">\n");
	 Hashtable h = (Hashtable)fh.get(selplantid);
	 //System.out.println(h+"         "+selplantid);
	 Vector application = (Vector)h.get("BLDG");
	 Vector applicationDesc = (Vector)h.get("BLDG_DESC");
	 String selbuilding = "";
	 for (int i = 0; i < application.size(); i++) {
	   preSelect = "";
	   if (selbuilding.equalsIgnoreCase((String)application.elementAt(i))) {
		 preSelect = "selected";
	   }
	   Msg.append("<option "+preSelect+" value=\""+(String)application.elementAt(i)+"\">"+(String)application.elementAt(i)+"</option>\n");
	 }
	 Msg.append("</SELECT>\n");
	 Msg.append( "</TR>\n" );
	 Msg.append( "</TABLE>\n" );

	Msg.append( "<BR><BR><TABLE BORDER=\"0\" width=\"80%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	Hashtable initialdepData=null;
	try
	{
	  initialdepData=radian.web.dropdwnHelpObj.getdepareaData( db,"","" );
	}
	catch ( Exception ex )
	{
	}
	Vector depID= ( Vector ) initialdepData.get( "DEPT_ID" );
	Vector areaID= ( Vector ) initialdepData.get( "AREA_ID" );
	Vector processID= ( Vector ) initialdepData.get( "PROCESS_ID" );

	Msg.append( "<TR>\n" );
	Msg.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Dept:</B>\n" );
	Msg.append( "</TD>\n" );
	Msg.append( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	Msg.append( "<SELECT CLASS=\"HEADER\" NAME=\"dpdepID\" ID=\"dpdepID\" size=\"1\">\n" );
	for ( int i=0; i < depID.size(); i++ )
	{
	  Msg.append( "<option value=\"" + ( String ) depID.elementAt( i ) + "\">" + ( String ) depID.elementAt( i ) + "</option>\n" );
	}
	Msg.append( "</TD>\n" );

	Msg.append( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	Msg.append( "or New:&nbsp;\n" );
	Msg.append( "</TD>\n" );

	Msg.append( "<TD WIDTH=\"5%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	Msg.append( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"newdepID\" ID=\"newdepID\" value=\"\" size=\"8\">\n" );
	Msg.append( "</TD>\n" );
	Msg.append( "</TR>\n" );

	Msg.append( "<TR>\n" );
	Msg.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Area:</B>\n" );
	Msg.append( "</TD>\n" );
	Msg.append( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	Msg.append( "<SELECT CLASS=\"HEADER\" NAME=\"dpareaID\" ID=\"dpareaID\" size=\"1\">\n" );
	for ( int i=0; i < areaID.size(); i++ )
	{
	  Msg.append( "<option value=\"" + ( String ) areaID.elementAt( i ) + "\">" + ( String ) areaID.elementAt( i ) + "</option>\n" );
	}
	Msg.append( "</TD>\n" );
	Msg.append( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	Msg.append( "or New:&nbsp;\n" );
	Msg.append( "</TD>\n" );

	Msg.append( "<TD WIDTH=\"5%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	Msg.append( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"newareaID\" ID=\"newareaID\" value=\"\" size=\"8\">\n" );
	Msg.append( "</TD>\n" );

	Msg.append( "</TR>\n" );

	Msg.append( "<TR>\n" );
	Msg.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Process:</B>\n" );
	Msg.append( "</TD>\n" );
	Msg.append( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	Msg.append( "<SELECT CLASS=\"HEADER\" NAME=\"dpprocessID\" ID=\"dpprocessID\" size=\"1\">\n" );
	for ( int i=0; i < processID.size(); i++ )
	{
	  Msg.append( "<option value=\"" + ( String ) processID.elementAt( i ) + "\">" + ( String ) processID.elementAt( i ) + "</option>\n" );
	}
	Msg.append( "</TD>\n" );
	Msg.append( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	Msg.append( "or New:&nbsp;\n" );
	Msg.append( "</TD>\n" );

	Msg.append( "<TD WIDTH=\"5%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	Msg.append( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"newprocessID\" ID=\"newprocessID\" value=\"\" size=\"8\">\n" );
	Msg.append( "</TD>\n" );

	Msg.append( "</TR>\n" );
	Msg.append( "</TABLE>\n" );

	Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"addnewworkarea\">\n" );
	Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"depID\" VALUE=\"\">\n" );
	Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"areaID\" VALUE=\"\">\n" );
	Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"processID\" VALUE=\"\">\n" );
	Msg.append( "<SELECT CLASS=\"displaynone\" NAME=\"invengrp\" VALUE=\"\"></SELECT>\n" );
	Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"selHub\" VALUE=\""+selectedHub+"\">\n" );

	Msg.append( "<BR><BR><CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" onclick= \"updatedepids(this)\" name=\"okupdate\" value=\"Process Add\" type=\"submit\">\n" );
	Msg.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"Cancel\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	Msg.append( "</FORM></BODY></HTML>\n" );

	return Msg;
  }

 public StringBuffer updateWorkArea (  String item,String invengrp, String facilityid,String companyid,String application,String depid, String areaID, String processid  )
 {
   StringBuffer Msg=new StringBuffer();
   DBResultSet dbrs=null;
   ResultSet rs=null;
   CallableStatement cs=null;
   boolean closeresult=false;
   String errorcode="";
   try
   {
	 Connection connect1=db.getConnection();
	 cs=connect1.prepareCall( "{call PR_ITEM_COUNT_MR_CREATE_WA(?,?,?,?,?,?,?,?,?)}" );

	 cs.setInt( 1,Integer.parseInt( item ) );
	 cs.setString( 2,invengrp );
	 cs.setString( 3,companyid );
	 cs.setString( 4,facilityid );
	 cs.setString( 5,application );
	 cs.setString( 6,areaID );
	 cs.setString( 7,depid );
	 cs.setString( 8,processid );


	 cs.registerOutParameter( 9,java.sql.Types.VARCHAR );
	 cs.execute();

	 errorcode=cs.getString( 3 );
	 //System.out.println( "Result from PR_ITEM_COUNT_MR_CREATE_WA procedure Error Code:  " + errorcode + "  item  "+item+" invengrp   "+invengrp+"  facilityid  "+facilityid+"  companyid  "+companyid+" application   "+application +"  depid  "+depid+" areaID   "+areaID+" processid  "+processid+"" );

	 if ( errorcode == null )
	 {
	   closeresult=true;
	 }
	 else
	 {
	   closeresult=false;
	 }
   }
   catch ( Exception e )
	{
	  e.printStackTrace();
	  closeresult = false;
	  radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure PR_ITEM_COUNT_MR_CREATE_WA in Change Work Area assignment","Error occured while running PR_ITEM_COUNT_MR_CREATE_WA\n" + e.getMessage() +"\nFor Input Parameters item  "+item+" invengrp   "+invengrp+"  facilityid  "+facilityid+"  companyid  "+companyid+" application   "+application +"  depid  "+depid+" areaID   "+areaID+" processid  "+processid+"" );
	}
	finally
	{
	  if ( cs != null )
	  {
		try
		{
		  cs.close();
		}
		catch ( Exception se )
		{
		  se.printStackTrace();
		}
	  }
	}

	if (closeresult)
	{
	  Msg.append( radian.web.HTMLHelpObj.printHTMLHeader( "Change Work Area Association for Item:"+item+"","iteminventory.js",intcmIsApplication ) );
	  Msg.append( "</HEAD>  \n" );
	  Msg.append( "<BODY>  \n" );
	  Msg.append( "<FORM method=\"POST\" NAME=\"closetap\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"\">\n" );
	  Msg.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  Msg.append( "<TR VALIGN=\"MIDDLE\">\n" );
	  Msg.append( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"CENTER\" CLASS=\"announce\">\n" );
	  Msg.append( "<B>Item ID:</B>&nbsp;"+item+" was succesfully assigned to\n<BR>Dept: "+depid+"<BR>Area: "+areaID+"<BR>Process: "+processid+"<BR><BR>Please press OK to refresh your main page." );
	  Msg.append( "</TD>\n" );
	  Msg.append( "</TR>" );
	  Msg.append( "</TABLE>\n" );
	  Msg.append( "<BR>\n" );
	  Msg.append( "<CENTER><INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\"Ok\" OnClick=\"submitmainpage()\" NAME=\"sendterv\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
	  Msg.append( "</CENTER>\n" );
	  Msg.append( "</BODY>  \n" );
	  Msg.append( "</HTML>\n" );
	}
	else
	{
	  Msg.append(buildchangeworkarea( item,invengrp,facilityid,companyid,application,"An error occured while processing."+errorcode+"<BR>Please try again." ));
	}

   return Msg;
 }

 public StringBuffer buildchangeworkarea( String item,String invengrp, String facilityid,String companyid,String application,String errmessage )
 {
   StringBuffer Msgl=new StringBuffer();
   DBResultSet dbrs=null;
   ResultSet rs=null;
   String currdep = "";
   String currarea = "";
   String currprocess= "";
   String storagearea= "";

   Msgl.append( radian.web.HTMLHelpObj.printHTMLHeader( "Change Work Area Association for Item:"+item+"","iteminventory.js",intcmIsApplication ) );
   Msgl.append( "<BODY>\n" );
   Msgl.append( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + radian.web.tcmisResourceLoader.getassigninvgrpwkareaurl() + "Session=Update\">\n" );

   String query ="select distinct AREA_ID, DEPT_ID, PROCESS_ID, STORAGE_AREA from item_count_inventory_view where ITEM_ID = "+item+" and INVENTORY_GROUP = '"+invengrp+"' and FACILITY_ID = '"+facilityid+"' order by AREA_ID asc";
   try
   {
	 dbrs=db.doQuery( query );
	 rs=dbrs.getResultSet();

	 while ( rs.next() )
	 {
	   currdep=rs.getString( "DEPT_ID" ) == null ? "" : rs.getString( "DEPT_ID" ).trim();
	   currarea=rs.getString( "AREA_ID" ) == null ? "" : rs.getString( "AREA_ID" ).trim();
	   currprocess=rs.getString( "PROCESS_ID" ) == null ? "" : rs.getString( "PROCESS_ID" ).trim();
	   storagearea=rs.getString( "STORAGE_AREA" ) == null ? "" : rs.getString( "STORAGE_AREA" ).trim();
	 }
   }
   catch ( Exception e )
   {
	 e.printStackTrace();
	 Msgl.append( "An Error Occured While Querying the Database" );
	 Msgl.append( "</BODY>\n</HTML>\n" );
	 return Msgl;
   }
   finally
   {
	 if (dbrs!= null) {dbrs.close();}
   }

   Msgl.append( radian.web.HTMLHelpObj.PrintTitleBarNolink( "Change Work Area Association for Item:"+item+" Inventory Group: "+storagearea+"\n" ) );
   if (errmessage.length() > 0 )
   {
	 Msgl.append( "<BR>"+errmessage+"</BR>\n" );
   }

   Msgl.append( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
   Msgl.append( "<TR>\n" );
   Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"LEFT\">Current</TD>\n" );
   Msgl.append( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\">Dept:&bnsp;&bnsp;&bnsp;"+currdep+"&bnsp;&bnsp;&bnsp;Area:&bnsp;&bnsp;&bnsp;"+currarea+"&bnsp;&bnsp;&bnsp;Process:&bnsp;&bnsp;&bnsp;"+currprocess+"&bnsp;&bnsp;&bnsp;</TD>\n" );
   Msgl.append( "</TR>\n" );

  Hashtable initialData=null;
  try
  {
	initialData=radian.web.dropdwnHelpObj.getdepareaData( db,facilityid,companyid );
  }
  catch ( Exception ex )
  {
  }
   Vector depID = (Vector)initialData.get("DEPT_ID");
   Vector areaID = (Vector)initialData.get("AREA_ID");
   Vector processID = (Vector)initialData.get("PROCESS_ID");

   Msgl.append( "<TR>\n" );
   Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"LEFT\">Change To</TD>\n" );
   Msgl.append( "<TD WIDTH=\"40%\" CLASS=\"announce\" ALIGN=\"LEFT\">Dept:&nbsp;&nbsp;&nbsp;\n" );

   Msgl.append("<SELECT CLASS=\"HEADER\" NAME=\"depID\" ID=\"depID\" size=\"1\">\n");
   for (int i = 0; i < depID.size(); i++)
   {
	 Msgl.append("<option value=\""+(String)depID.elementAt(i)+"\">"+(String)depID.elementAt(i)+"</option>\n");
   }
   Msgl.append( "</TD></TR>\n" );

   Msgl.append( "<TR>\n" );
   Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"LEFT\">&nbsp;</TD>\n" );
   Msgl.append( "<TD WIDTH=\"40%\" CLASS=\"announce\" ALIGN=\"LEFT\">Area:&nbsp;&nbsp;&nbsp;\n" );

   Msgl.append( "<SELECT CLASS=\"HEADER\" NAME=\"areaID\" ID=\"areaID\" size=\"1\">\n" );
   for ( int i=0; i < areaID.size(); i++ )
   {
	 Msgl.append( "<option value=\"" + ( String ) areaID.elementAt( i ) + "\">" + ( String ) areaID.elementAt( i ) + "</option>\n" );
   }
   Msgl.append( "</TD></TR>\n" );


   Msgl.append( "<TR>\n" );
   Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"LEFT\">&nbsp;</TD>\n" );
   Msgl.append( "<TD WIDTH=\"40%\" CLASS=\"announce\" ALIGN=\"LEFT\">Process:&nbsp;&nbsp;&nbsp;\n" );

   Msgl.append( "<SELECT CLASS=\"HEADER\" NAME=\"processID\" ID=\"processID\" size=\"1\">\n" );
   for ( int i=0; i < processID.size(); i++ )
   {
	 Msgl.append( "<option value=\"" + ( String ) processID.elementAt( i ) + "\">" + ( String ) processID.elementAt( i ) + "</option>\n" );
   }
   Msgl.append( "</TD></TR>\n" );
   Msgl.append( "</TABLE>\n" );
   Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Updworkarea\">\n" );
   Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"facilityid\" VALUE=\""+facilityid+"\">\n" );
   Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"companyid\" VALUE=\""+companyid+"\">\n" );
   Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"invengrp\" VALUE=\""+invengrp+"\">\n" );
   Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"itemid\" VALUE=\""+item+"\">\n" );
   Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"application\" VALUE=\""+application+"\">\n" );
   Msgl.append( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" type=\"submit\">\n" );
   Msgl.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"Cancel\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
   Msgl.append( "</FORM></BODY></HTML>\n" );

   return Msgl;
 }

}
