package radian.web.servlets.miller.msds;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
import java.util.Collection;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *10-13-03 New Miller MSDS corpmsds
 *03-01-04 Adding department and status to the result set. Also can search by Department
 * 07-14-05 Sorting by Building. Redused use of StringBuffer
 */
public abstract class millerBuildSearchPage extends HttpServlet implements SingleThreadModel
{
     public void init(ServletConfig config) throws ServletException
    {
        super.init(config);

	    String facility_id = config.getInitParameter("facility");
		if ( facility_id == null )
        facility_id="";
		this.setmainfacility(facility_id);
		//System.out.println("facility_id   "+facility_id+"");
    }

    //Process the HTTP Post request
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        doPost(request,response);
    }

  private String mainfacility="";

  public void setmainfacility( String id )
  {
	this.mainfacility=id;
  }

  private String getmainfacility()
	 throws Exception
  {
	return this.mainfacility;
  }

    //Process the HTTP Get request
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
      String session_id="T";
      String client=getBundle().getString( "DB_CLIENT" );
      HttpSession millersession=request.getSession();
			PersonnelBean personnelBean = (PersonnelBean) millersession.getAttribute("personnelBean");
			boolean intcmIsApplication = false;
			if (personnelBean !=null)
			{
				 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
				 if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
					intcmIsApplication = true;
				 }
			}


      TcmISDBModel db=null;
      Hashtable ResultDdata=new Hashtable();

      session_id=request.getParameter( "Session_ID" );
      if ( session_id == null )
        session_id="New";

      String Second="";
      Second=request.getParameter( "Second" );
      if ( Second == null )
        Second="";

      String User_Action=request.getParameter( "UserAction" );
      if ( User_Action == null )
        User_Action="New";

	  String facility="";
	  facility=request.getParameter( "facility" );
	  if ( facility == null )
		facility="";
	  facility = facility.trim();

	  if (mainfacility.length() > 0){facility = mainfacility;}

	String client1=getBundle().get( "DB_CLIENT_NAME" ).toString();
	db=new RayTcmISDBModel( client1 );

	String dropdownjs="";
	Hashtable initialData=new Hashtable();
	Hashtable departmentData=new Hashtable();
	Hashtable statusData=new Hashtable();

	String doneinistuff=millersession.getAttribute( "MILLER_MSDS_STUFF" ) == null ? "" : millersession.getAttribute( "MILLER_MSDS_STUFF" ).toString();
	if ( doneinistuff.equalsIgnoreCase( "Yes" ) && !(facility.length() > 0) )
	{
	  initialData= ( Hashtable ) millersession.getAttribute( "MILLER_INITIAL_DATA" );
	  dropdownjs = ( String ) millersession.getAttribute( "MILLER_DROP_DOWN_JS" );
	  departmentData = ( Hashtable ) millersession.getAttribute( "MILLER_DEP_DATA" );
	  statusData = ( Hashtable ) millersession.getAttribute( "MILLER_STATUS_STUFF" );
	}
	else
	{
	  try
	  {
		initialData=this.getInitialData( db,facility );
		dropdownjs=this.createComboBoxData( initialData );
		departmentData = this.getDepartmentData(db);
		statusData = radian.web.vvHelpObj.getmsdsStatus(db);

		millersession.setAttribute( "MILLER_DROP_DOWN_JS",dropdownjs );
		millersession.setAttribute( "MILLER_INITIAL_DATA",initialData );
		millersession.setAttribute( "MILLER_DEP_DATA",departmentData );
		millersession.setAttribute( "MILLER_STATUS_STUFF",statusData );
		millersession.setAttribute( "MILLER_MSDS_STUFF","Yes" );

	  }
	  catch ( Exception ex )
	  {
	  }
	}

      String limit="";
      limit=request.getParameter( "limit" );
      if ( limit == null )
        limit="No";

      String recs="";
      recs=request.getParameter( "recs" );
      if ( recs == null )
        recs="";

      String interval="";
      interval=request.getParameter( "interval" );
      if ( interval == null )
        interval="";

      String item="";
      item=request.getParameter( "item" );
      if ( item == null )
        item="NAWAZ!";
	  item = item.trim();

      String from="";
      from=request.getParameter( "from" );
      if ( from == null )
        from="0";

      String sortbyIndex="";
      sortbyIndex=request.getParameter( "sortby" );
      if ( sortbyIndex == null )
        sortbyIndex="0";

	  String fac="";
	  fac=request.getParameter( "hubC" );
	  if ( fac == null )
		fac="";

	  String building="";
	  building=request.getParameter( "facilityName" );
	  if ( building == null )
		building="";

	  String waName="";
	  waName=request.getParameter( "workAreaName" );
	  if ( waName == null )
		waName="";


      String headers="";
      headers=request.getParameter( "headers" );
      if ( headers == null )
        headers="";

      String window="";
      window=request.getParameter( "window" );
      if ( window == null )
        window="";

      String msdsurl="";
      msdsurl=request.getParameter( "msdsurl" );
      if ( msdsurl == null )
        msdsurl="";

	String depId="";
	depId=request.getParameter( "depId" );
	if ( depId == null )
	  depId="";
	depId=depId.trim();

	String statusId="";
	statusId=request.getParameter( "statusId" );
	if ( statusId == null )
	  statusId="";
	statusId=statusId.trim();

	//System.out.println("Facility   : "+fac+"   Building  : "+building+"   Floor   : "+waName+"    sortby : "+sortbyIndex+"  depId :   "+depId+"   statusId:  "+statusId+"");

    response.setContentType( "text/html" );
    //StringBuffer Msg1=new StringBuffer();
    PrintWriter out=new PrintWriter( response.getOutputStream() );
    String MsgBN="";
    String MsgB="Help";

    //If the search string is NAWAZ! then no search will be performed
	if ("Search".equalsIgnoreCase( User_Action ))
    {
        Hashtable SearchDdata=new Hashtable();
        SearchDdata.put( "Session_ID",session_id );
        SearchDdata.put( "limit",limit );
        SearchDdata.put( "recs",recs );
        SearchDdata.put( "interval",interval );
        SearchDdata.put( "item",item );
        SearchDdata.put( "from",from );
        SearchDdata.put( "sortby",sortbyIndex );
        SearchDdata.put( "fac",fac );
		SearchDdata.put( "building",building );
        SearchDdata.put( "floor",waName );
        SearchDdata.put( "headers",headers );
        SearchDdata.put( "window",window );
        SearchDdata.put( "msdsurl",msdsurl );
        SearchDdata.put( "Second",Second );
		SearchDdata.put( "department",depId );
		SearchDdata.put( "StatusID",statusId );

        millerSearchMsds searchmiller=new millerSearchMsds( getBundle(),db );
        ResultDdata=searchmiller.GetResult( SearchDdata,millersession );

        try
        {
          MsgBN= ( String ) ResultDdata.get( "MSG_BACK_NEXT" );
          MsgB= ( String ) ResultDdata.get( "MSG_BODY" );
        }
        catch ( Exception e1d2 )
        {
          e1d2.printStackTrace();
          MsgBN="&nbsp;";
          MsgB="&nbsp;";
        }
    }
    else
    {
      if ( "Search".equalsIgnoreCase( User_Action ) )
      {
        MsgB="Please check atleast one category like catalog or non-catalog and try again.<BR><BR><BR><BR><BR><BR><BR>\n";
      }
    }

    HeaderFooter hf=new HeaderFooter( getBundle(),db );
    //HEAD
    //Msg1.append( hf.printHTMLHeader( " MSDS Viewer" ) );

	out.print( radian.web.HTMLHelpObj.printHTMLHeader( "MSDS Viewer","",intcmIsApplication ) );
	out.print("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/clientpages.css\"></LINK>\n");
    out.print("<SCRIPT SRC=\"/clientscripts/millermsds.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	out.print("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
	out.print(dropdownjs);
	out.print("// -->\n </SCRIPT>\n");
    //out.print( hf.printTopToolBar( "","tcmisMsds.gif" ) );
    out.print( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>MSDS Viewer</B>\n" ) );

    printHead( initialData,statusData,session_id,MsgBN,fac,recs,sortbyIndex,item,building,waName,facility,departmentData,depId,statusId,out );

    if ( !"Search".equalsIgnoreCase( User_Action ) )
    {
      out.print( "<TABLE BORDER=0 WIDTH=725><TR><TD>\n" );
      out.print( "<B><FONT SIZE=\"3\" FACE=ARIAL>Instructions:</FONT></B><P>\n" );
      out.print( "<UL>\n" );
      out.print( "<LI><FONT FACE=ARIAL SIZE=2 COLOR=990000><B>Search\n" );
      out.print( "-</B></FONT> Search for chemicals with a trade name,\n" );
      out.print( "manufacturer, or part number that contains the search word.  Use the key\n" );
      out.print( "words AND, OR, BUT NOT between search words to build more complex\n" );
      out.print( "queries.  (For example: \"Florida AND oranges BUT NOT navel oranges\"\n" );
      out.print( "would return all florida oranges that are not navel oranges.)<P>\n" );

      out.print( "<LI><FONT FACE=ARIAL SIZE=2 COLOR=009900><B>Facility -\n" );
      out.print( "</B></FONT>Search for chemicals used only within a specific\n" );
      out.print( "facility, or across All Facilities.<P>\n" );

	  out.print( "<LI><FONT FACE=ARIAL SIZE=2 COLOR=009900><B>Building -\n" );
	  out.print( "</B></FONT>Search for chemicals used only within a specific\n" );
	  out.print( "Building, or across All Buildings.<P>\n" );

	  out.print( "<LI><FONT FACE=ARIAL SIZE=2 COLOR=009900><B>Floor -\n" );
	  out.print( "</B></FONT>Search for chemicals used only within a specific\n" );
	  out.print( "Floor, or across All Floors.<P>\n" );

      out.print( "<LI><FONT FACE=ARIAL SIZE=2 COLOR=000000><B>Sort -\n" );
      out.print( "</B></FONT>Sort the search results by any of the columns\n" );
      out.print( "displayed.<P></UL>\n" );
      out.print( "</CENTER></TD></TR>\n" );
      out.print( "</TABLE><P>&nbsp;</P><P>&nbsp;</P>\n" );
    }
    else
    {
      out.print( MsgB );
    }

    out.print( hf.printFooter() );

    //out.println( Msg1 );
    db.close();
    out.close();
  }

  //Get Servlet information
  public String getServletInfo()
  {
    return "radian.web.servlets.share.BuildSearchPage Information";
  }

  private String createComboBoxData( Hashtable initialData )
	 {
	   //System.out.print(initialData);

	   String tmp=new String( "" );
	   String tmpHub = "var hubID = new Array(";
	   String altFacID="var altFacID = new Array();\n ";
	   String altFacDesc="var altFacDesc = new Array();\n ";
	   String altWAID="var altWAID = new Array();\n ";
	   String altWADesc="var altWADesc = new Array();\n ";
	   int i=0;
	   Vector hubIDV = (Vector)initialData.get("HUB_ID");
	   for (int ii = 0 ; ii < hubIDV.size(); ii++)
	   {
		 String hubID = (String)hubIDV.elementAt(ii);
		 tmpHub += "\""+hubID+"\""+",";

		 Hashtable fh = (Hashtable)initialData.get(hubID);

		 Vector facIDV= ( Vector ) fh.get( "FACILITY_ID" );
		 Vector facDescV= ( Vector ) fh.get( "FACILITY_DESC" );
		 if ( facIDV.size() > 0 && !facIDV.contains( "All Buildings" ) )
		 {
		   facIDV.insertElementAt( "All Buildings",0 );
		   facDescV.insertElementAt( "All Buildings",0 );
		   Vector wAreaID=new Vector( 1 );
		   wAreaID.addElement( "All Floors" );
		   Vector wAreaDesc=new Vector( 1 );
		   wAreaDesc.addElement( "All Floors" );
		   Hashtable tmpFacWA=new Hashtable( 3 );
		   tmpFacWA.put( "APPLICATION",wAreaID );
		   tmpFacWA.put( "APPLICATION_DESC",wAreaDesc );
		   fh.put( "All Buildings",tmpFacWA );
		 }
		 altFacID += "altFacID[\""+hubID+"\"] = new Array(";
		 altFacDesc += "altFacDesc[\""+hubID+"\"] = new Array(";
		 for ( i=0; i < facIDV.size(); i++ )
		 {
		   String facID= ( String ) facIDV.elementAt( i );
		   if ( !"All".equalsIgnoreCase( facID ) )
		   {
			 tmp+="\"" + facID + "\"" + ",";
			 altFacID+="\"" + facID + "\"" + ",";
			 altFacDesc+="\"" + ( String ) facDescV.elementAt( i ) + "\"" + ",";
			 //build work area
			 Hashtable h= ( Hashtable ) fh.get( facID );
			 Vector application= ( Vector ) h.get( "APPLICATION" );
			 Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );
			 if ( application.size() > 0 && !application.contains( "All Floors" ) )
			 {
			   application.insertElementAt( "All Floors",0 );
			   applicationDesc.insertElementAt( "All Floors",0 );
			 }
			 altWAID+="altWAID[\"" + facID + "\"] = new Array(";
			 altWADesc+="altWADesc[\"" + facID + "\"] = new Array(";
			 for ( int j=0; j < application.size(); j++ )
			 {
			   altWAID+="\"" + ( String ) application.elementAt( j ) + "\"" + ",";
			   altWADesc+="\"" + HelpObjs.findReplace( ( String ) applicationDesc.elementAt( j ),"\"","'" ) + "\"" + ",";
			 }
			 altWAID=altWAID.substring( 0,altWAID.length() - 1 ) + ");\n";
			 altWADesc=altWADesc.substring( 0,altWADesc.length() - 1 ) + ");\n";
		   }
		 }
		 //removing the last commas ','
		 altFacID=altFacID.substring( 0,altFacID.length() - 1 ) + ");\n";
		 altFacDesc=altFacDesc.substring( 0,altFacDesc.length() - 1 ) + ");\n";
	   }

	   if (tmpHub.indexOf(",") > 1) {
	   tmpHub = tmpHub.substring(0,tmpHub.length()-1) +");\n";
	   }

	   return ( tmpHub+" " + altFacID + " " + altFacDesc + " " + altWAID + " " + altWADesc + "" );
	  }


	  public Hashtable getDepartmentData(TcmISDBModel dbObj3)
	  {
		String query="select distinct DEPARTMENT, DEPARTMENT_DESC from FAC_BLDG_FLOOR_DEPT_VIEW where DEPARTMENT <> 'All' order by DEPARTMENT";
		DBResultSet dbrs=null;
		ResultSet rs=null;
		Hashtable result=new Hashtable();
		//result.put( "All","All" );
		try
		{
		  dbrs=dbObj3.doQuery( query );
		  rs=dbrs.getResultSet();
		  while ( rs.next() )
		  {
				String faci= ( rs.getString( "DEPARTMENT" ) == null ? "" : rs.getString( "DEPARTMENT" ) );
				String facn= ( rs.getString( "DEPARTMENT_DESC" ) == null ? "" : rs.getString( "DEPARTMENT_DESC" ) );
				result.put( faci,facn );
		  }
		}
		catch ( Exception e )
		{
		  e.printStackTrace();
		}
		finally {
		  if ( dbrs != null ) {dbrs.close();}

		}

		return result;
	  }

  public Hashtable getInitialData(TcmISDBModel db1,String facility) throws Exception
	 {
	   boolean onefacility = false;
	   if (facility.length() > 0)
	   {
		 onefacility = true;
	   }

	   String query="select FACILITY_ID, BUILDING, FLOOR, DEPARTMENT, BUILDING_DESC, FLOOR_DESC, DEPARTMENT_DESC from FAC_BLDG_FLOOR_DEPT_VIEW ";
	   if (onefacility)
	   {
		 query+= "where FACILITY_ID='"+facility+"'";
	   }

	   query+=" order by FACILITY_ID,BUILDING,FLOOR";

	   DBResultSet dbrs=null;
	   ResultSet rs=null;
	   Hashtable resultdata =new Hashtable();
	   Vector hubID = new Vector();
	   Vector hubDesc = new Vector();
	   int count = 0;

	   try
	   {
		 dbrs=db1.doQuery( query );
		 rs=dbrs.getResultSet();

		 String lastHub="";

		 while ( rs.next() )
		 {
		   String tmpFacID=rs.getString( "BUILDING" );
		   String tmpHub=rs.getString( "FACILITY_ID" );
		   count++;

		if (count == 1)
		{
		  if (!onefacility)
		  {
			hubID.addElement( "All Facilities" );
			hubDesc.addElement( "All Facilities" );
		  }
		  Hashtable fh=new Hashtable();
		  //facility data
		  Vector facID=new Vector();
		  Vector facDesc=new Vector();
		  facID.addElement( "All Buildings" );
		  facDesc.addElement( "All Buildings" );
		  Hashtable h=new Hashtable();
		  //application data
		  Vector application=new Vector();
		  Vector applicationDesc=new Vector();
		  application.addElement( "All Floors" );
		  applicationDesc.addElement( "All Floors" );
		  h.put( "APPLICATION",application );
		  h.put( "APPLICATION_DESC",applicationDesc );
		  fh.put( "All Buildings",h );
		  //putting them all together
		  fh.put( "FACILITY_ID",facID );
		  fh.put( "FACILITY_DESC",facDesc );
		  //resultdata.put( "DATA",fh );
		  if (!onefacility)
		  {
			resultdata.put( "All Facilities",fh );
		  }
		}
		   if ( !tmpHub.equalsIgnoreCase( lastHub ) )
		   {
			 //hub info
			 hubID.addElement(tmpHub);
			 hubDesc.addElement(tmpHub);

			 Hashtable fh=new Hashtable();
			 //facility data
			 Vector facID=new Vector();
			 Vector facDesc=new Vector();
			 facID.addElement( tmpFacID );
			 facDesc.addElement( rs.getString( "BUILDING_DESC" ) );
			 Hashtable h=new Hashtable();
			 //application data
			 Vector application=new Vector();
			 Vector applicationDesc=new Vector();
			 application.addElement( rs.getString( "FLOOR" ) );
			 applicationDesc.addElement( rs.getString( "FLOOR_DESC" ) );
			 h.put( "APPLICATION",application );
			 h.put( "APPLICATION_DESC",applicationDesc );
			 fh.put( tmpFacID,h );
			 //putting them all together
			 fh.put( "FACILITY_ID",facID );
			 fh.put( "FACILITY_DESC",facDesc );
			 //resultdata.put( "DATA",fh );
			 resultdata.put(tmpHub,fh);
		   }
		   else
		   { //hub already exist
			 Hashtable fh= ( Hashtable ) resultdata.get( tmpHub );
			 Vector facID= ( Vector ) fh.get( "FACILITY_ID" );
			 Vector facDesc= ( Vector ) fh.get( "FACILITY_DESC" );
			 if ( !facID.contains( tmpFacID ) )
			 {
			   facID.addElement( tmpFacID );
			   facDesc.addElement( rs.getString( "BUILDING_DESC" ) );
			   Hashtable h=new Hashtable( 3 );
			   Vector application=new Vector();
			   Vector applicationDesc=new Vector();
			   application.addElement( rs.getString( "FLOOR" ) );
			   applicationDesc.addElement( rs.getString( "FLOOR_DESC" ) );
			   h.put( "APPLICATION",application );
			   h.put( "APPLICATION_DESC",applicationDesc );
			   fh.put( tmpFacID,h );
			 }
			 else
			 {
			   Hashtable h= ( Hashtable ) fh.get( tmpFacID );
			   Vector application= ( Vector ) h.get( "APPLICATION" );
			   Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );
			   if ( !application.contains( rs.getString( "FLOOR" ) ) )
			   {
				 application.addElement( rs.getString( "FLOOR" ) );
				 applicationDesc.addElement( rs.getString( "FLOOR_DESC" ) );
			   }
			   h.put( "APPLICATION",application );
			   h.put( "APPLICATION_DESC",applicationDesc );
			   fh.put( tmpFacID,h );
			 }
			 fh.put( "FACILITY_ID",facID );
			 fh.put( "FACILITY_DESC",facDesc );
			 //resultdata.put( "DATA",fh );
			 resultdata.put(tmpHub,fh);
			 //System.out.println("tmpFacID    "+resultdata);
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

	   resultdata.put("HUB_ID",hubID);
	   resultdata.put("HUB_DESC",hubDesc);
	   return resultdata;
	 }


    protected void printHead( Hashtable initialData,Hashtable satatusData,String Session_ID,String BackNext,String selectedHub,String recs,
                                      String sortby,String item,String selectedbldg,String selectedfloor,String facility,Hashtable depData,String depId,String statusId,PrintWriter msdsout )
    {
      //StringBuffer Msg=new StringBuffer();
      String client = getBundle().getString( "DB_CLIENT" );

      int r=0;
      try
      {
        r=Integer.parseInt( recs );
      }
      catch ( Exception e2 )
      {
        r=0;
      }

	  msdsout.print( "<FORM METHOD=\"POST\" NAME=\"invoicereports\" ACTION=\"/tcmIS/miller/corpmsds?\"> \n" );
	  msdsout.print( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n" );
	  msdsout.print( "<TABLE BORDER=0 WIDTH=100% CELLPADDING=0 cellspacing=0 BGCOLOR=\"#fff0e1\">\n" );
	  msdsout.print( " <TR VALIGN=\"MIDDLE\">\n" );

	  //Facitiy Drop Box
	  msdsout.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<B>Facility:</B>\n" );
	  msdsout.print( "</TD>\n" );
      msdsout.print( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<SELECT CLASS=\"HEADER\" NAME=\"hubC\" ID=\"hubC\" size=\"1\" OnChange=\"hubChanged(document.invoicereports.hubC)\">\n" );

	  Vector hubID = (Vector)initialData.get("HUB_ID");
	  int i = 0;
	  if (selectedHub.trim().length() == 0 )
	  {
		selectedHub= ( String ) hubID.firstElement(); //select the first facility if none were selected
	  }
	  String preSelect = "";
	  for (i = 0; i < hubID.size(); i++) {
		preSelect = "";
		if (selectedHub.equalsIgnoreCase((String)hubID.elementAt(i))) {
		  preSelect = "selected";
		  selectedHub = (String)hubID.elementAt(i);
		}
		msdsout.print("<option "+preSelect+" value=\""+(String)hubID.elementAt(i)+"\">"+(String)hubID.elementAt(i)+"</option>\n");
	  }
	  msdsout.print("</SELECT>\n");
      msdsout.print( "</TD>\n" );

	  //Buildging
	  msdsout.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<B>Building:</B>\n" );
	  msdsout.print( "</TD>\n" );
	  msdsout.print( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" ID=\"facId\" size=\"1\" OnChange=\"facilityChanged(document.invoicereports.facilityName)\">\n" );
	  Hashtable fh= ( Hashtable ) initialData.get( selectedHub );
	  //System.out.println( "selectedHub    " + selectedHub );
	  Vector facID= ( Vector ) fh.get( "FACILITY_ID" );
	  Vector facDesc= ( Vector ) fh.get( "FACILITY_DESC" );
	  i=0;
	  if ( selectedbldg.trim().length() == 0 )
	  {
		selectedbldg= ( String ) facID.firstElement(); //select the first facility if none were selected
	  }
	  preSelect="";
	  for ( i=0; i < facID.size(); i++ )
	  {
		preSelect="";
		if ( selectedbldg.equalsIgnoreCase( ( String ) facID.elementAt( i ) ) )
		{
		  preSelect="selected";
		  selectedbldg= ( String ) facID.elementAt( i );
		}
		msdsout.print( "<option " + preSelect + " value=\"" + ( String ) facID.elementAt( i ) + "\">" + ( String ) facDesc.elementAt( i ) + "</option>\n" );
	  }
	  msdsout.print( "</SELECT>\n" );
	  msdsout.print( "</TD>\n" );

	  //Work Area
	  msdsout.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<B>Floor:</B>\n" );
	  msdsout.print( "</TD>\n" );
	  msdsout.print( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\" OnChange=\"workareaChanged()\">\n" );
	  Hashtable h= ( Hashtable ) fh.get( selectedbldg );
	  //System.out.println(h);
	  Vector application= ( Vector ) h.get( "APPLICATION" );
	  Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );
	  for ( i=0; i < application.size(); i++ )
	  {
		preSelect="";
		if ( selectedfloor.equalsIgnoreCase( ( String ) application.elementAt( i ) ) )
		{
		  preSelect="selected";
		}
		msdsout.print( "<option " + preSelect + " value=\"" + ( String ) application.elementAt( i ) + "\">" + ( String ) application.elementAt( i ) +	"</option>\n" );
	  }
	  msdsout.print( "</SELECT>\n" );
	  msdsout.print( "</TD>\n" );

	  msdsout.print( " </TR>\n" );

	  msdsout.print( " <TR VALIGN=\"MIDDLE\">\n" );
	  //Department Drop Box
	  msdsout.print( "<TD HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<B>Department:</B>\n" );
	  msdsout.print( "</TD>\n" );
	  msdsout.print( "<TD HEIGHT=45 WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<SELECT CLASS=\"HEADER\" NAME=\"depId\" ID=\"depId\" size=\"1\">\n" );
	  msdsout.print( "<OPTION VALUE=\"All\">All Departments</OPTION>\n" );
	  msdsout.print(radian.web.HTMLHelpObj.sorthashbyValue(depData.entrySet(),depId));
	  msdsout.print("</SELECT>\n");
	  msdsout.print( "</TD>\n" );
	  //Status
	  msdsout.print( "<TD HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<B>Status:</B>\n" );
	  msdsout.print( "</TD>\n" );
	  msdsout.print( "<TD HEIGHT=45 WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<SELECT CLASS=\"HEADER\" NAME=\"statusId\" size=\"1\"> \n" );
	  msdsout.print( "  <OPTION VALUE=\"All\">All Status</OPTION>\n" );
	  msdsout.print(radian.web.HTMLHelpObj.sorthashbyValue(satatusData.entrySet(),statusId));
	  /*msdsout.print( "  <OPTION " + ( statusId.equalsIgnoreCase( "Active" ) ? "selected" : "" ) + " VALUE=\"Active\">Active</OPTION>\n" );
	  msdsout.print( "  <OPTION " + ( statusId.equalsIgnoreCase( "Archive" ) ? "selected" : "" ) + " VALUE=\"Archive\">Archive</OPTION>\n" );
	  msdsout.print( "  <OPTION " + ( statusId.equalsIgnoreCase( "Test" ) ? "selected" : "" ) + " VALUE=\"Test\">Test</OPTION>\n" );*/
	  msdsout.print( "</SELECT>\n" );
	  msdsout.print( "</TD>\n" );
	  msdsout.print( "<TD HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "&nbsp;\n" );
	  msdsout.print( "</TD>\n" );
	  msdsout.print( "<TD HEIGHT=45 WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "&nbsp;\n" );
	  msdsout.print( "</TD>\n" );
	  msdsout.print( " </TR>\n" );

	  msdsout.print( " <TR VALIGN=\"MIDDLE\">\n" );
      //Sort By
	  msdsout.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  msdsout.print( "<B>Sort By:</B>\n" );
	  msdsout.print( "</TD>\n" );
	  msdsout.print( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
      msdsout.print( "<SELECT CLASS=\"HEADER\" NAME=\"sortby\" size=\"1\"> \n" );
      msdsout.print( "  <OPTION " + ( sortby.equalsIgnoreCase( "MATERIAL_ID" ) ? "selected" : "" ) + " VALUE=\"MATERIAL_ID\">Material</OPTION>\n" );
      msdsout.print( "  <OPTION " + ( sortby.equalsIgnoreCase( "CAT_PART_NO_STRING" ) ? "selected" : "" ) + " VALUE=\"CAT_PART_NO_STRING\">Part Number</OPTION>\n" );
      msdsout.print( "  <OPTION " + ( sortby.equalsIgnoreCase( "TRADE_NAME" ) ? "selected" : "" ) + " VALUE=\"TRADE_NAME\">Trade Name</OPTION>\n" );
      msdsout.print( "  <OPTION " + ( sortby.equalsIgnoreCase( "MANUFACTURER" ) ? "selected" : "" ) + " VALUE=\"MANUFACTURER\">Manufacturer</OPTION>\n" );
      msdsout.print( "</SELECT>\n" );
      msdsout.print( "</TD>\n" );

	  msdsout.print( "<TD HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\">&nbsp;\n</TD>\n" );
      msdsout.print( "<TD HEIGHT=45 WIDTH=\"15%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\">\n" );
      msdsout.print( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"item\" VALUE=\"" + ( item == "NAWAZ!" ? "" : item ) + "\" size=\"25\">\n" );
      msdsout.print( "</TD>\n" );

	  msdsout.print( " <TD HEIGHT=45 WIDTH=\"5%\"><INPUT TYPE=\"submit\" VALUE=\"Search\" NAME=\"B1\">&nbsp;&nbsp;\n" );
      msdsout.print(" </TD>\n");

      msdsout.print( "<TD HEIGHT=45 WIDTH=\"15%\" ALIGN=\"RIGHT\">\n" );
      msdsout.print( BackNext == null ? "&nbsp;" : BackNext );
      msdsout.print( "</TD>\n" );

      msdsout.print( " </TR>\n" );
      msdsout.print( "</TABLE> \n" );
      msdsout.print( "<INPUT TYPE=\"hidden\" NAME=\"from\" VALUE=\"1\">\n" );
      msdsout.print( "<INPUT TYPE=\"hidden\" NAME=\"SortByAct\" VALUE=\"0\">\n" );
      msdsout.print( "<INPUT TYPE=\"hidden\" NAME=\"Session_ID\" VALUE=\"" + Session_ID + "\">\n" );
      msdsout.print( "<INPUT TYPE=\"hidden\" NAME=\"interval\" VALUE=\"20\">\n" );
      msdsout.print( "<INPUT TYPE=\"hidden\" NAME=\"window\" VALUE=\"New\">\n" );
      msdsout.print( "<INPUT TYPE=\"hidden\" NAME=\"Second\" VALUE=\"No\">\n</FORM>\n" );
	  msdsout.print( "<INPUT TYPE=\"hidden\" NAME=\"facility\" VALUE=\"" + facility + "\">\n" );

      return;
    }

    protected abstract String  getClient();
    protected abstract ServerResourceBundle getBundle();

}
