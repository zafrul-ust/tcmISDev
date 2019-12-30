package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Enumeration;
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
 * @version
 */

public class binstoScan
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private PrintWriter out=null;
  private boolean intcmIsApplication = false;
  //private Vector cabinetIdstoLabel = null;
  //private Vector cabinetlabelIds = null;
  //private Vector cabinetIdshown = null;

 /* protected boolean allowupdate;

  public void setupdateStatus( boolean id )
  {
    this.allowupdate=id;
  }

  private boolean getupdateStatus()
     throws Exception
  {
    return this.allowupdate;
  }
*/
  public binstoScan( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  public void doResult( HttpServletRequest request,
                        HttpServletResponse response )
     throws ServletException,IOException
  {
    out=response.getWriter();
    response.setContentType( "text/html" );
    HttpSession relabelsession=request.getSession();
    String personnelid= relabelsession.getAttribute( "PERSONNELID" ) == null ? "" : relabelsession.getAttribute( "PERSONNELID" ).toString();

    PersonnelBean personnelBean = (PersonnelBean) relabelsession.getAttribute("personnelBean");
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
    intcmIsApplication = false;
    if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
    {
     intcmIsApplication = true;
    }

    String User_Action="";
    String selHub = "";

    User_Action=request.getParameter( "UserAction" );
    if ( User_Action == null )
      User_Action="New";
    User_Action = User_Action.trim();

    selHub=request.getParameter( "HubName" );
    if ( selHub == null )
      selHub="";
    selHub = selHub.trim();

	String selRoom=request.getParameter( "hubroom" );
	if ( selRoom == null )
	  selRoom="All";
	selRoom=selRoom.trim();

	String searchitem=request.getParameter( "searchitem" );
	if ( searchitem == null )
	  searchitem="";
	searchitem=searchitem.trim();

	String showissuedin=request.getParameter( "showissuedin" );
	if ( showissuedin == null )
	  showissuedin="";
	showissuedin=showissuedin.trim();

	String isuuedindays=request.getParameter( "isuuedindays" );
	if ( isuuedindays == null )
	  isuuedindays="";
	isuuedindays=isuuedindays.trim();

	String showbincntdin=request.getParameter( "showbincntdin" );
	if ( showbincntdin == null )
	  showbincntdin="";
	showbincntdin=showbincntdin.trim();

	String bincntdays=request.getParameter( "bincntdays" );
	if ( bincntdays == null )
	  bincntdays="";
	bincntdays=bincntdays.trim();

	String showitemuntcost=request.getParameter( "showitemuntcost" );
	if ( showitemuntcost == null )
	  showitemuntcost="";
	showitemuntcost=showitemuntcost.trim();

	String unctcostgttn=request.getParameter( "unctcostgttn" );
	if ( unctcostgttn == null )
	  unctcostgttn="";
	unctcostgttn=unctcostgttn.trim();

	String showiteminvvalue=request.getParameter( "showiteminvvalue" );
	if ( showiteminvvalue == null )
	  showiteminvvalue="";
	showiteminvvalue=showiteminvvalue.trim();

	String invvaluegttn=request.getParameter( "invvaluegttn" );
	if ( invvaluegttn == null )
	  invvaluegttn="";
	invvaluegttn=invvaluegttn.trim();

	String unctcostlesstn=request.getParameter( "unctcostlesstn" );
	if ( unctcostlesstn == null )
	  unctcostlesstn="";
	unctcostlesstn=unctcostlesstn.trim();

	String invvaluelesstn=request.getParameter( "invvaluelesstn" );
	if ( invvaluelesstn == null )
	  invvaluelesstn="";
	invvaluelesstn=invvaluelesstn.trim();

	String User_Sort = request.getParameter("SortBy");
	if (User_Sort == null)
	    User_Sort = "BRANCH_PLANT";

    //System.out.println( "User_Action   "+User_Action+"  User Search is " + searchwhat + "  containsorlike" + containsorlike + "  facility " + facility+ "  workarea   "+workarea );

    try
    {
      Hashtable initialData = null;
	  Hashtable hub_list_set = new Hashtable();
	  hub_list_set= (Hashtable)relabelsession.getAttribute("HUB_PREF_LIST");
      String initialDataLoaded = "";

      initialDataLoaded="";
      try
      {
        initialDataLoaded=relabelsession.getAttribute( "ROOM_LABEL_DATA_LOADED" ).toString();
      }
      catch ( Exception getdoneFlag )
      {}

      if ( initialDataLoaded.equalsIgnoreCase( "Yes" ) )
      {
        initialData= ( Hashtable ) relabelsession.getAttribute( "INITIAL_ROOM_DATA" );
        //hub_list_set  = (Hashtable)relabelsession.getAttribute("ROOM_LABEL_HUB_SET");
      }
      else
      {
        String CompanyID = relabelsession.getAttribute("COMPANYID").toString();
        //Hashtable hub_list_set1 = new Hashtable();
        //hub_list_set1  = radian.web.HTMLHelpObj.createHubList( db,personnelid,CompanyID );

        //relabelsession.setAttribute("ROOM_LABEL_HUB_SET", hub_list_set1 );
		Hashtable hub_list1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
		if ( hub_list1.size() > 0 )
		{
		  initialData=gethubRoomData( hub_list_set );
		  relabelsession.setAttribute( "INITIAL_ROOM_DATA",initialData );
		}

        relabelsession.setAttribute( "ROOM_LABEL_DATA_LOADED","Yes" );
      }

      if ( User_Action.equalsIgnoreCase( "Search" ) )
      {
        Vector data = new Vector();

        try
        {
          data= getResult( selHub,selRoom,User_Sort,searchitem,showissuedin,isuuedindays,showbincntdin,bincntdays,showitemuntcost,unctcostgttn,showiteminvvalue,invvaluegttn,unctcostlesstn,invvaluelesstn );
        }
        catch ( Exception e1 )
        {
          e1.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printHTMLError() );
        }

        //relabelsession.setAttribute("HUB_LABEL_DATA", data );

        Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

        buildHeader( selHub,selRoom,initialData,hub_list_set,"",searchitem,showissuedin,isuuedindays,showbincntdin,bincntdays,showitemuntcost,unctcostgttn,showiteminvvalue,invvaluegttn,unctcostlesstn,invvaluelesstn);

        if ( 0 == count )
        {
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
        }
        else
        {
          buildDetails( data,selHub);
        }

        //clean up
        data=null;
      }
      else
      {
        Hashtable hub_sslist1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );

        if ( hub_sslist1.size() < 1 )
        {
          buildHeader( selHub,selRoom,initialData,hub_list_set,"",searchitem,showissuedin,isuuedindays,showbincntdin,bincntdays,showitemuntcost,unctcostgttn,showiteminvvalue,invvaluegttn,unctcostlesstn,invvaluelesstn);
          out.println( radian.web.HTMLHelpObj.printHTMLNoFacilities() );
          hub_list_set=null;
          out.close();
        }
        else
        {
          buildHeader( selHub,selRoom,initialData,hub_list_set,"",searchitem,showissuedin,isuuedindays,showbincntdin,bincntdays,showitemuntcost,unctcostgttn,showiteminvvalue,invvaluegttn,unctcostlesstn,invvaluelesstn);
          out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
          out.close();
          hub_list_set=null;
        }
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
    finally
    {
      try {
        out.close();
      }
      catch(Exception e) {
        //ignore
      }
    }
  }

  public Hashtable gethubRoomData( Hashtable hublist )
  {
	Hashtable hubsetdetails= ( Hashtable ) ( hublist.get( "HUB_LIST" ) );
	String allowedhubs="";
	for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
	{
	  String branch_plant= ( String ) ( e.nextElement() );
	  allowedhubs+="'" + branch_plant + "',";
	}
	allowedhubs=allowedhubs.substring( 0,allowedhubs.length() - 1 );

	DBResultSet dbrs=null;
	ResultSet rs=null;
	String query="select distinct BRANCH_PLANT,ROOM from vv_hub_bins where BRANCH_PLANT in (" + allowedhubs + ") and STATUS = 'A' order by BRANCH_PLANT";
	Hashtable resultdata=new Hashtable();
	Vector hubID=new Vector();

	try
	{
	  dbrs=db.doQuery( query );
	  rs=dbrs.getResultSet();
	  String lastHub="";

	  while ( rs.next() )
	  {
		String tmproom=rs.getString( "ROOM" ) == null ? "" : rs.getString( "ROOM" ) ;
		String tmpHub=rs.getString( "BRANCH_PLANT" );

		if ( !tmpHub.equalsIgnoreCase( lastHub ) )
		{
		  //hub info
		  hubID.addElement( tmpHub );

		  Hashtable fh=new Hashtable();
		  Vector facID=new Vector();
		  facID.addElement( "All" );
		  facID.addElement( tmproom );
		  fh.put( "ROOM",facID );

		  resultdata.put( tmpHub,fh );
		}
		else
		{ //hub already exist
		  Hashtable fh= ( Hashtable ) resultdata.get( tmpHub );
		  Vector facID= ( Vector ) fh.get( "ROOM" );

		  if ( !facID.contains( tmproom ) )
		  {
			facID.addElement( tmproom );
		  }

		  //fh.put( "FACILITY_ID",facID );
		  fh.put( "ROOM",facID );
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

  public static StringBuffer createhubroomjs( Hashtable finalresultdata )
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
	 Vector cabIDv= ( Vector ) fh.get( "ROOM" );
	 Vector invName= ( Vector ) fh.get( "ROOM" );

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

     //Build page - To build the gui page.
     private void buildDetails( Vector data,String hubname )
     {
       //StringBuffer Msg=new StringBuffer();
       String checkednon = "";

       try
       {
         Hashtable summary=new Hashtable();
         summary= ( Hashtable ) data.elementAt( 0 );
         int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
         //System.out.println( "Total Number : " + total );
         int vsize=data.size();
         //System.out.println( "Vector Size  : " + vsize );

         //start table #3
         out.println( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"50%\" CLASS=\"moveup\">\n" );

         String error_item;

         int i=0; //used for detail lines
         for ( int loop=0; loop < total; loop++ )
         {
           i++;
           boolean createHdr=false;

           if ( loop % 10 == 0 )
           {
             createHdr=true;
           }

           if ( createHdr )
           {
             out.println( "<tr>\n" );
             //if (this.getupdateStatus())
             {
               //out.println( "<TH width=\"2%\"  height=\"38\">Print");
               //if ( loop == 0 ) {out.println( "<BR><INPUT TYPE=\"checkbox\" value=\"\" onClick=\"checkall('row_chk','1')\" NAME=\"chkall\">" );}
               //out.println( "</TH>\n" );
             }
             out.println( "<TH width=\"5%\"  height=\"38\">Room</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Bin</TH>\n" );
             out.println( "</tr>\n" );
           }

           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

           String bin = hD.get("BIN")==null?"&nbsp;":hD.get("BIN").toString();
           String room = hD.get("ROOM")==null?"&nbsp;":hD.get("ROOM").toString();
           String Line_Check      =  (hD.get("USERCHK")==null?"&nbsp;":hD.get("USERCHK").toString());

           if (Line_Check.equalsIgnoreCase("yes")){checkednon = "checked";}else{checkednon = "";}

           String Color=" ";
           if ( i % 2 == 0 )
           {
             Color="CLASS=\"blue";
           }
           else
           {
             Color="CLASS=\"white";
           }

           /*String chkbox_value="no";
           if ( checkednon.equalsIgnoreCase( "checked" ) )
           {
             chkbox_value="yes";
           }*/

           out.println("<tr align=\"center\">\n");
           //if (this.getupdateStatus())
           {
           //out.println("<td height=\"25\" "+Color+"\" width=\"2%\" ALIGN=\"LEFT\"><INPUT TYPE=\"checkbox\" onClick= \"checkValues(name,this)\" value=\""+(chkbox_value)+"\" "+checkednon+" NAME=\"row_chk"+i+"\"></td>\n");
           }
           out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+room+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+bin+"</td>\n");
           out.println("</tr>\n");
         }

         out.println( "</table>\n" );
         out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
         out.println( "<tr>" );
         out.println( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
         out.println( "<INPUT TYPE=\"hidden\" NAME=\"totallines\" ID=\"totallines\" VALUE=\""+i+"\">\n" );
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

       return;
     }

    //Build HTML Header
    private void buildHeader(String hub_branch_id, String selectedroom,Hashtable initialData,Hashtable hub_list_set,String buildlabels, String searchitem,
							 String showissuedin,String isuuedindays,String showbincntdin,String bincntdays,String showitemuntcost,String unctcostgttn,String showiteminvvalue,String invvaluegttn,String unctcostlesstn,String invvaluelesstn)
    {
      //StringBuffer Msg=new StringBuffer();
      out.println(radian.web.HTMLHelpObj.printHTMLHeader("Bins to Scan","hublabels.js",intcmIsApplication));
	  out.println("</HEAD>  \n");

      if ("GENERATE_LABELS".equalsIgnoreCase(buildlabels))
      {
      out.println("<BODY onLoad=\"dobinPopup()\">\n");
      }
      else
      {
      out.println("<BODY>\n");
      }

      String Search_servlet = radian.web.tcmisResourceLoader.getbintoscanurl();

      out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
      out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
      out.println("</DIV>\n");
      out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

      out.println(radian.web.HTMLHelpObj.PrintTitleBar("Bins to Scan\n"));
	  Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
	  if (hub_list.size() < 1)
	  {
		out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
		return;
	  }
	  out.println( "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n" );
	  out.println( createhubroomjs( initialData ) );
	  out.println( "// -->\n </SCRIPT>\n" );

      out.println( "<FORM METHOD=\"POST\" NAME=\"hublabels\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n" );
      out.println( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
      out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  //Hub
	  out.println( "<TD WIDTH=\"2%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "<B>Hub:</B>&nbsp;\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"20%\" HEIGHT=\"35\">\n" );
	  out.println( "<SELECT CLASS=\"HEADER\" NAME=\"HubName\" OnChange=\"hubchanged(document.hublabels.HubName)\" size=\"1\">\n" );
	  //Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
	  Hashtable hub_prority_list= ( Hashtable ) ( hub_list_set.get( "HUB_PRORITY_LIST" ) );
	  if ( hub_branch_id.trim().length() == 0 )
	  {
		hub_branch_id=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
	  }
	  out.println( radian.web.HTMLHelpObj.sorthashbyValue( hub_prority_list.entrySet(),hub_branch_id,hub_list ) );
	  out.println( "</SELECT>\n" );
	  out.println( "</TD>\n" );

	  out.println( "<TD WIDTH=\"2%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "&nbsp;\n" );
	  out.println( "</TD>\n" );

	  out.println( "<TD WIDTH=\"20%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "&nbsp;\n" );
	  out.println( "</TD>\n" );

      //Search
      out.println("<TD WIDTH=\"2%\" CLASS=\"announce\" ALIGN=\"CENTER\">\n");
      out.println( "<INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
      out.println("</TD>\n");
      out.println("</TR>\n");

	  out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	  out.println( "<TD WIDTH=\"2%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "<B>Room:</B>&nbsp;</TD>\n" );
	  out.println( "<TD WIDTH=\"20%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
	  out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"hubroom\" size=\"1\">\n" );

	  Hashtable fh= ( Hashtable ) initialData.get( hub_branch_id );
	  Vector invidV= ( Vector ) fh.get( "ROOM" );
	  Vector invidName= ( Vector ) fh.get( "ROOM" );

	  for ( int i=0; i < invidV.size(); i++ )
	  {
		String preSelect="";
		String wacid= ( String ) invidV.elementAt( i );
		String invgName= ( String ) invidName.elementAt( i );

		if ( selectedroom.equalsIgnoreCase( wacid ) )
		{
		  preSelect="selected";
		}
		out.println( "<option " + preSelect + " value=\"" + wacid + "\"> " + invgName + "</option>\n" );
	  }
	  out.println( "</SELECT>\n" );
	  out.println( "</TD>\n" );

	  out.println("<TD WIDTH=\"2%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	  out.println( "<B>Item:</B>\n" );
	  out.println("</TD>\n");

	  out.println( "<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
	  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchitem\" value=\"" + searchitem + "\" size=\"20\">\n" );
	  out.println( "</TD>\n" );

	  out.println( "<TD WIDTH=\"2%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "&nbsp;\n" );
	  out.println( "</TD>\n" );
      out.println( "</TR>\n" );

	out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	//Items with Unit Cost
	out.println( "<TD WIDTH=\"2%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "<INPUT type=\"checkbox\" name=\"showitemuntcost\" value=\"Yes\" " + ( showitemuntcost.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
	out.println( "</TD>\n" );
	out.println( "<TD WIDTH=\"20%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
	out.println( "Items with Unit Cost > $\n" );
	out.println("<INPUT CLASS=\"HEADER\" type=\"text\" name=\"unctcostgttn\" value=\""+(unctcostgttn.length()<1?"":unctcostgttn)+"\" SIZE=\"3\" MAXLENGTH=\"5\">\n");
	out.println( " and < $\n" );
	out.println("<INPUT CLASS=\"HEADER\" type=\"text\" name=\"unctcostlesstn\" value=\""+(unctcostlesstn.length()<1?"":unctcostlesstn)+"\" SIZE=\"3\" MAXLENGTH=\"5\">\n");
	out.println( "</TD>\n" );

	//Items with Inventory Value
	out.println( "<TD WIDTH=\"2%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "<INPUT type=\"checkbox\" name=\"showiteminvvalue\" value=\"Yes\" " + ( showiteminvvalue.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
	out.println( "</TD>\n" );
	out.println( "<TD WIDTH=\"20%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\" COLSPAN=\"2\">\n" );
	out.println( "Items with Inventory Value > $\n" );
	out.println("<INPUT CLASS=\"HEADER\" type=\"text\" name=\"invvaluegttn\" value=\""+(invvaluegttn.length()<1?"":invvaluegttn)+"\" SIZE=\"3\" MAXLENGTH=\"5\">\n");
	out.println( "and < $\n" );
	out.println("<INPUT CLASS=\"HEADER\" type=\"text\" name=\"invvaluelesstn\" value=\""+(invvaluelesstn.length()<1?"":invvaluelesstn)+"\" SIZE=\"3\" MAXLENGTH=\"5\">\n");
	out.println( "</TD>\n" );

	out.println( "<TD WIDTH=\"2%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "&nbsp;\n" );
	out.println( "</TD>\n" );
	out.println( "</TR>\n" );

	out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	//Receipts issued in
	out.println( "<TD WIDTH=\"2%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "<INPUT type=\"checkbox\" name=\"showissuedin\" value=\"Yes\" " + ( showissuedin.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
	out.println( "</TD>\n" );
	out.println( "<TD WIDTH=\"20%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n" );
	out.println( "Receipts issued in the last \n" );
	out.println("<INPUT CLASS=\"HEADER\" type=\"text\" name=\"isuuedindays\" value=\""+(isuuedindays.length()<1?"":isuuedindays)+"\" SIZE=\"3\" MAXLENGTH=\"5\">\n");
	out.println("&nbsp;Days\n");
	out.println( "</TD>\n" );

	//Bins not counted in
	out.println( "<TD WIDTH=\"2%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "<INPUT type=\"checkbox\" name=\"showbincntdin\" value=\"Yes\" " + ( showbincntdin.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
	out.println( "</TD>\n" );
	out.println( "<TD WIDTH=\"20%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\" COLSPAN=\"2\">\n" );
	out.println( "Bins that have not been counted in the last \n" );
	out.println("<INPUT CLASS=\"HEADER\" type=\"text\" name=\"bincntdays\" value=\""+(bincntdays.length()<1?"":bincntdays)+"\" SIZE=\"3\" MAXLENGTH=\"5\">\n");
	out.println("&nbsp;Days\n");
	out.println( "</TD>\n" );

	out.println( "<TD WIDTH=\"2%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println( "&nbsp;\n" );
	out.println( "</TD>\n" );
	out.println( "</TR>\n" );

	out.println("</TABLE>\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"generate_labels\" VALUE=\"yes\">&nbsp;\n");
	//out.println("<INPUT TYPE=\"hidden\" NAME=\"Paper\" VALUE=\""+paper_size+"\">\n");

	return;
  }

  private Vector getResult( String hubSel,String roomSelected,String sortBy,String searchitem,String showissuedin,String isuuedindays,
							String showbincntdin,String binntdays,String showitemuntcost,String unctcostgttn,String showiteminvvalue,String invvaluegttn,String unctcostlesstn,String invvaluelesstn)
       throws Exception
    {

      Vector Data=new Vector();
      Hashtable DataH=new Hashtable();
      Hashtable summary=new Hashtable();
      String nodaysago="60";
      boolean falgforand =false;
	  DBResultSet dbrs=null;
	  ResultSet searchRs=null;

      int count=0;
      summary.put( "TOTAL_NUMBER",new Integer( count ) );
      Data.addElement( summary );

      try
      {
        String query="select distinct BRANCH_PLANT,ROOM,BIN from bins_to_scan_view ";
        query += "where BRANCH_PLANT = "+hubSel+" ";

        falgforand = true;

        if ( roomSelected.length() > 0 && (!"All".equalsIgnoreCase(roomSelected)) )
        {
          query+="and ROOM='" + roomSelected + "'";
          falgforand = true;
        }

//COUNT_DATETIME, INVENTORY_COST, UNIT_PRICE

		if ( searchitem.length() > 0 )
		{
		  {
			if ( falgforand )
			  query+=" and ITEM_ID = " + searchitem + "  ";
			else
			  query+=" ITEM_ID = " + searchitem + "  ";
		  }
		}

		if ( "Yes".equalsIgnoreCase( showissuedin ) && isuuedindays.length() > 0 )
		{
		  {
			if ( falgforand )
			  query+=" and DATE_PICKED > SYSDATE - " + isuuedindays + "  ";
			else
			  query+=" DATE_PICKED > SYSDATE - " + isuuedindays + "  ";
		  }
		}

		if ( "Yes".equalsIgnoreCase( showbincntdin ) && binntdays.length() > 0 )
		{
		  {
			if ( falgforand )
			  query+=" and COUNT_DATETIME > SYSDATE - " + binntdays + "  ";
			else
			  query+=" COUNT_DATETIME > SYSDATE - " + binntdays + "  ";
		  }
		}

		if ( "Yes".equalsIgnoreCase( showitemuntcost ) && unctcostgttn.length() > 0 )
		{
		  {
			if ( falgforand )
			  query+=" and UNIT_PRICE > " + unctcostgttn + "  ";
			else
			  query+=" UNIT_PRICE > " + unctcostgttn + "  ";
		  }
		}

		if ( "Yes".equalsIgnoreCase( showitemuntcost ) && unctcostlesstn.length() > 0 )
		{
		  {
			if ( falgforand )
			  query+=" and UNIT_PRICE < " + unctcostlesstn + " and INVENTORY_COST > 0 ";
			else
			  query+=" UNIT_PRICE < " + unctcostlesstn + " and INVENTORY_COST > 0 ";
		  }
		}

		if ( "Yes".equalsIgnoreCase( showiteminvvalue ) && invvaluegttn.length() > 0 )
		{
		  {
			if ( falgforand )
			  query+=" and INVENTORY_COST > " + invvaluegttn + "  ";
			else
			  query+=" INVENTORY_COST > " + invvaluegttn + "  ";
		  }
		}

		if ( "Yes".equalsIgnoreCase( showiteminvvalue ) && invvaluelesstn.length() > 0 )
		{
		  {
			if ( falgforand )
			  query+=" and INVENTORY_COST < " + invvaluelesstn + "  ";
			else
			  query+=" INVENTORY_COST < " + invvaluelesstn + "  ";
		  }
		}

		query+=" order by " + sortBy;

          dbrs=db.doQuery( query );
          searchRs=dbrs.getResultSet();

          /*ResultSetMetaData rsMeta1 = searchRs.getMetaData();
          System.out.println("Finished The Querry\n "+rsMeta1.getColumnCount());
          for(int i =1; i<=rsMeta1.getColumnCount(); i++)
          {
          System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");

          //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
          //System.out.println("//"+rsMeta1.getColumnName(i).toString()+"");
          //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
          }*/

          while ( searchRs.next() )
          {
            DataH=new Hashtable();
            //BIN
            DataH.put( "BIN",searchRs.getString( "BIN" ) == null ? "" : searchRs.getString( "BIN" ) );
            //ROOM
            DataH.put( "ROOM",searchRs.getString( "ROOM" ) == null ? "" : searchRs.getString( "ROOM" ) );
            DataH.put( "USERCHK","");
            DataH.put( "NO_OF_LABELS","");
            Data.addElement( DataH );
            count++;
          }

        }
        catch ( Exception e )
        {
          e.printStackTrace();
          throw e;
        }
        finally
        {
          if ( dbrs != null ){dbrs.close();}
        }

        Hashtable recsum=new Hashtable();
        recsum.put( "TOTAL_NUMBER",new Integer( count ) );
        Data.setElementAt( recsum,0 );
        Data.trimToSize();
        return Data;
      }
}
