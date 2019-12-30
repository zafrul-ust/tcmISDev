package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
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

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 */

public class assignaddinvengroup
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

  public assignaddinvengroup( ServerResourceBundle b,TcmISDBModel d )
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

	String zeroateom=request.getParameter( "zeroateom" );
	if ( zeroateom == null )
	  zeroateom="";
	zeroateom=zeroateom.trim();

	String contractpricing=request.getParameter( "contractpricing" );
	if ( contractpricing == null )
	  contractpricing="";
	contractpricing=contractpricing.trim();

	if ( "chginvgrp".equalsIgnoreCase( User_Action ) )
	{
	  Hashtable initialData= ( Hashtable ) relabelsession.getAttribute( "INITIAL_ITEM_INVENTORY_DATA" );
	  out.println( buildchangeinvgroup( initialData,itemid,hubid,"" ) );
	}
	else if ( "Updinvengrp".equalsIgnoreCase( User_Action ) )
	{
	  Hashtable initialData= ( Hashtable ) relabelsession.getAttribute( "INITIAL_ITEM_INVENTORY_DATA" );
	  out.println( updateWorkArea( initialData,itemid,hubid,selplantid,selbldgid,invengrp,depID,areaID,processID,zeroateom,contractpricing ) );
	}
	else if ( "addeditinvgrp".equalsIgnoreCase( User_Action ) )
	{
	  Hashtable initialData= ( Hashtable ) relabelsession.getAttribute( "INITIAL_ITEM_INVENTORY_DATA" );
	  out.println( builaddinvgrp( initialData,hubid,"" ) );
	}
	else if ( "addnewinvgrp".equalsIgnoreCase( User_Action ) )
	{
	  Hashtable initialData= ( Hashtable ) relabelsession.getAttribute( "INITIAL_ITEM_INVENTORY_DATA" );
	  out.println( addnewinvgrp( initialData,selplantid,selbldgid,invengrp,hubid ) );
    }

   out.close();
 }

 public StringBuffer addnewinvgrp(  Hashtable initialData,String plantId, String buildingId,String storageid,String hubId  )
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
	  Msg.append( builaddinvgrp( initialData,hubId,"The Work Area was succesfully created" ) );
	}
	else
	{
	  Msg.append( builaddinvgrp( initialData,hubId,"An error occured while processing."+errorcode+"<BR>Please try again." ));
	}

   return Msg;
 }

 public StringBuffer builaddinvgrp( Hashtable initialData,String selectedHub,String errmessage )
  {
	StringBuffer Msg=new StringBuffer();
	DBResultSet dbrs=null;
	ResultSet rs=null;
	String currdep = "";
	String currarea = "";
	String currprocess= "";
	String storagearea= "";

	Msg.append( radian.web.HTMLHelpObj.printHTMLHeader( "Add New Storage Area","iteminventory.js",intcmIsApplication ) );
	Msg.append( "<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n" );
	Msg.append( radian.web.dropdwnHelpObj.createComboBoxData( initialData ) );
	Msg.append( "// -->\n </SCRIPT>\n" );
	Msg.append( "</HEAD>  \n" );
	Msg.append( "<BODY>\n" );

	Msg.append( radian.web.HTMLHelpObj.PrintTitleBarNolink( "Add New Storage Area\n" ) );
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

	Msg.append( "<TR>\n" );
	Msg.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Storage:</B>\n" );
	Msg.append( "</TD>\n" );
	Msg.append( "<TD WIDTH=\"5%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	Msg.append( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"newdepID\" ID=\"newdepID\" value=\"\" size=\"8\">\n" );
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

 public StringBuffer updateWorkArea ( Hashtable initialData,String item,String selHub,String plantid,String bldgid,String storagId,String depid, String areaID, String processid, String zeroateom, String contractpri )
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
	  Msg.append( radian.web.HTMLHelpObj.printHTMLHeader( "New Inventory Group Assignment for Item:"+item+"","iteminventory.js",intcmIsApplication) );
	  Msg.append( "</HEAD>  \n" );
	  Msg.append( "<BODY>  \n" );
	  Msg.append( "<FORM method=\"POST\" NAME=\"closetap\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"\">\n" );
	  Msg.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	  Msg.append( "<TR VALIGN=\"MIDDLE\">\n" );
	  Msg.append( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"CENTER\" CLASS=\"announce\">\n" );
	  Msg.append( "<B>Item ID:</B>&nbsp;"+item+" was succesfully assigned to\n<BR>Storage: "+storagId+"<BR><BR>Please press OK to refresh your main page." );
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
	  Msg.append(buildchangeinvgroup( initialData,item,selHub,"An error occured while processing."+errorcode+"<BR>Please try again." ));
	}

   return Msg;
 }

 public StringBuffer buildchangeinvgroup( Hashtable initialData,String item,String selhub,String errmessage )
 {
   StringBuffer Msgl=new StringBuffer();
   DBResultSet dbrs=null;
   ResultSet rs=null;

   Msgl.append( radian.web.HTMLHelpObj.printHTMLHeader( "New Inventory Group Assignment for Item:"+item+"","iteminventory.js",intcmIsApplication ) );
   Msgl.append( "<BODY>\n" );
   Msgl.append( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + radian.web.tcmisResourceLoader.getassigninvgrpwkareaurl() + "Session=Update\">\n" );

   Vector hubID = (Vector)initialData.get("HUB_ID");
   Vector hubDesc = (Vector)initialData.get("HUB_DESC");
   String selHubname = "";

   if ( selhub.trim().length() == 0 )
   {
	 selhub= ( String ) hubID.firstElement(); //select the first facility if none were selected
   }
   for ( int i=0; i < hubID.size(); i++ )
   {
	 if ( selhub.equalsIgnoreCase( ( String ) hubID.elementAt( i ) ) )
	 {
	   selHubname= ( String ) hubDesc.elementAt( i );
	 }
   }

   Msgl.append( radian.web.HTMLHelpObj.PrintTitleBarNolink( "New Inventory Group Assignment for Item: "+item+" and Program: "+selHubname+"\n" ) );
   if ( errmessage.length() > 0 )
   {
	 Msgl.append( "<BR>" + errmessage + "</BR>\n" );
   }

   Msgl.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
   Msgl.append( "<TR VALIGN=\"MIDDLE\">\n" );

    Msgl.append("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
    Msgl.append("<B>Assign To:</B>\n");
	Msgl.append("</TD>\n");

	Msgl.append("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
	Msgl.append("<B>Plant:</B>\n");
	Msgl.append("</TD>\n");
	Msgl.append("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
	Msgl.append("<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" ID=\"facId\" size=\"1\" OnChange=\"facilityChanged(document.iteminventory.facilityName)\">\n");
	Hashtable fh = (Hashtable)initialData.get(selhub);
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
	  Msgl.append( "<option " + preSelect + " value=\"" + ( String ) facID.elementAt( i ) + "\">" + ( String ) facDesc.elementAt( i ) + "</option>\n" );
	}
	Msgl.append("</SELECT>\n");
	Msgl.append("</TD>\n");
	// Building
	Msgl.append( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	Msgl.append( "<B>Building:</B>&nbsp;\n" );
	Msgl.append( "</TD>\n" );

	Msgl.append( "<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	Msgl.append("<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" OnChange=\"workareaChanged()\" size=\"1\">\n");
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
	  Msgl.append("<option "+preSelect+" value=\""+(String)application.elementAt(i)+"\">"+(String)application.elementAt(i)+"</option>\n");
	}
	Msgl.append("</SELECT>\n");
	Msgl.append( "</TD>\n" );

	// Inventory Group
	Msgl.append( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	Msgl.append( "<B>Storage:</B>&nbsp;\n" );
	Msgl.append( "</TD>\n" );
	Msgl.append( "<TD WIDTH=\"10%\">\n" );

	Msgl.append( "<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\">\n" );
	Msgl.append( "<OPTION VALUE=\"All\">All Storage Areas</OPTION>\n" );
	Msgl.append( "</SELECT>\n" );
	Msgl.append( "</TD>\n" );

	Msgl.append( "</TR>\n" );

	Msgl.append( "<TR>\n" );
	Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"LEFT\">And</TD>\n" );
	Msgl.append( "</TR>\n" );

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

	Msgl.append( "<TR>\n" );
	Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"LEFT\">Sell To:</TD>\n" );

	Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\">Dept:\n" );
	Msgl.append("</TD>\n");
	Msgl.append("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
	Msgl.append( "<SELECT CLASS=\"HEADER\" NAME=\"depID\" ID=\"depID\" size=\"1\">\n" );
	for ( int i=0; i < depID.size(); i++ )
	{
	  Msgl.append( "<option value=\"" + ( String ) depID.elementAt( i ) + "\">" + ( String ) depID.elementAt( i ) + "</option>\n" );
	}
	Msgl.append( "</TD>\n" );

	Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\">Area:&nbsp;&nbsp;&nbsp;\n" );
	Msgl.append( "</TD>\n" );
	Msgl.append( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
	Msgl.append( "<SELECT CLASS=\"HEADER\" NAME=\"areaID\" ID=\"areaID\" size=\"1\">\n" );
	for ( int i=0; i < areaID.size(); i++ )
	{
	  Msgl.append( "<option value=\"" + ( String ) areaID.elementAt( i ) + "\">" + ( String ) areaID.elementAt( i ) + "</option>\n" );
	}
	Msgl.append( "</TD>\n" );

	Msgl.append( "<TD WIDTH=\"40%\" CLASS=\"announce\" ALIGN=\"RIGHT\">Process:&nbsp;&nbsp;&nbsp;\n" );
	Msgl.append( "</TD>\n" );
	Msgl.append( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
	Msgl.append( "<SELECT CLASS=\"HEADER\" NAME=\"processID\" ID=\"processID\" size=\"1\">\n" );
	for ( int i=0; i < processID.size(); i++ )
	{
	  Msgl.append( "<option value=\"" + ( String ) processID.elementAt( i ) + "\">" + ( String ) processID.elementAt( i ) + "</option>\n" );
	}
	Msgl.append( "</TD>\n" );
	Msgl.append( "</TR>\n" );

	Msgl.append( "<TR>\n" );
	Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"LEFT\"></TD>\n" );

	Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\">Zero @ End of Month:\n" );
	Msgl.append("</TD>\n");

	Msgl.append( "<td width=\"5%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"Y\" NAME=\"zeroateom\"></td>\n" );
	Msgl.append( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\">Zero @ End of Month:\n" );
	Msgl.append("</TD>\n");
	Msgl.append( "<td width=\"5%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"Y\" NAME=\"contractpricing\"></td>\n" );
	Msgl.append( "</TR>\n" );

	Msgl.append( "</TABLE>\n" );
	Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Updinvengrp\">\n" );
	Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"selHub\" VALUE=\""+selhub+"\">\n" );
	Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"itemid\" VALUE=\""+item+"\">\n" );
	Msgl.append( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"okupdate\" value=\"Ok\" type=\"submit\">\n" );
	Msgl.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"Cancel\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	Msgl.append( "</FORM></BODY></HTML>\n" );

	return Msgl;
 }
}
