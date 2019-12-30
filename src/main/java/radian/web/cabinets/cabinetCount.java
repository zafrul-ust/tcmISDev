package radian.web.cabinets;

//
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.NoBuyOrderPo;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import java.util.*;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 06-01-04 - Sorting the work area dropdown, and showing APPLICATION_DESC instead of APPLICATION
 * 06-04-04 - Sending an email with all the results after scanner upload is done
 * 09-20-04 - Creating the MRs in the order of item_id
 * 02-16-05 - Added Currency ID to the nobuyorderpo process
 */

public class cabinetCount
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private PrintWriter out=null;
  //private DBResultSet dbrs=null;
  //private ResultSet searchRs=null;
  private boolean allowupdate;
  //private Vector cabinetIdshown = null;
  private static org.apache.log4j.Logger cabcntlog = org.apache.log4j.Logger.getLogger(cabinetCount.class);
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

  public cabinetCount( ServerResourceBundle b,TcmISDBModel d )
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
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

    String searchtext ="";
    String User_Action="";
    String searchwhat="";
    String containsorlike ="";
    String facility="";
    String workarea="";
    String sortby = "";

    searchtext=request.getParameter( "searchtext" );
    if ( searchtext == null )
      searchtext="";
    searchtext = searchtext.trim();

    User_Action=request.getParameter( "UserAction" );
    if ( User_Action == null )
      User_Action="New";
    User_Action = User_Action.trim();

    searchwhat=request.getParameter( "searchwhat" );
    if ( searchwhat == null )
      searchwhat="CAT_PART_NO";
    searchwhat = searchwhat.trim();

    containsorlike=request.getParameter( "containsorlike" );
    if ( containsorlike == null )
      containsorlike="Like";
    containsorlike = containsorlike.trim();

    facility=request.getParameter( "facilityName" );
    if ( facility == null )
      facility="";
    facility=facility.trim();

    workarea=request.getParameter( "workAreaName" );
    if ( workarea == null )
      workarea="All Work Areas";
    workarea=workarea.trim();

    String selHub=request.getParameter( "hubC" );
    if ( selHub == null )
      selHub="";
    selHub = selHub.trim();


    sortby=request.getParameter( "sortby" );
    if ( sortby == null )
      sortby="";
    sortby = sortby.trim();

    String [] cabsarray = {"All"};
    cabsarray  = request.getParameterValues("binids");
    String cabsStringfromArray = "";
    Vector selectedcabs = new Vector();
    int cabadded = 0;
    if (cabsarray != null)
    {
      for (int loop  = 0 ; loop  < cabsarray.length  ; loop++)
      {
        if (cabadded > 0) {cabsStringfromArray += ",";}
        if ("All".equalsIgnoreCase(cabsarray[loop]))
        {

        }
        else
        {
        cabsStringfromArray += "'"+ cabsarray[loop] +"'";
        cabadded++;
        }
        selectedcabs.add(cabsarray[loop]);
      }
    }
    if (cabsStringfromArray.length() == 0)
    {
      cabsStringfromArray = "'All'";
      selectedcabs.add("New");
    }
	//cabcntlog.info("User_Action              "+User_Action+"");
    try
    {
      Hashtable initialData = null;
      Hashtable hub_list_set = new Hashtable();
      String initialDataLoaded = "";

      initialDataLoaded= relabelsession.getAttribute( "CABINET_LABEL_DATA_LOADED" ) == null ? "" : relabelsession.getAttribute( "CABINET_LABEL_DATA_LOADED" ).toString();

      if ( initialDataLoaded.equalsIgnoreCase( "Yes" ) )
      {
        initialData= ( Hashtable ) relabelsession.getAttribute( "INITIAL_CABINET_LABEL_DATA" );
        hub_list_set  = (Hashtable)relabelsession.getAttribute("HUB_PREF_LIST");
      }
      else
      {
        String CompanyID = relabelsession.getAttribute("COMPANYID").toString();
		Hashtable hub_list_set1=(Hashtable)relabelsession.getAttribute("HUB_PREF_LIST");
		Hashtable hub_list1 = (Hashtable)(hub_list_set1.get("HUB_LIST"));

		/*if (this.getupdateStatus())
		{
		   hub_list_set1  = radian.web.HTMLHelpObj.createHubList(db,"Cabinet Label",personnelid,CompanyID);
		}
		else
		{
		   hub_list_set1  = radian.web.HTMLHelpObj.createAllHubList(db);
		}*/
		//relabelsession.setAttribute("CABINET_LABEL_HUB_SET", hub_list_set1 );

		if (hub_list1.size() > 0)
		{
		initialData= radian.web.HTMLHelpObj.getcabinetInitialData(db,hub_list_set1);
		relabelsession.setAttribute( "INITIAL_CABINET_LABEL_DATA",initialData );
		}

        relabelsession.setAttribute( "CABINET_LABEL_DATA_LOADED","Yes" );

        hub_list_set  = hub_list_set1;
      }

	  //PersonnelBean personnelBean = relabelsession.getAttribute( "personnelBean" ) == null ? null : (PersonnelBean) relabelsession.getAttribute( "personnelBean" );
		if (personnelBean.getPermissionBean().hasFacilityPermission("Cabinet Label",selHub,null))
	  {
		this.setupdateStatus(true);
	  }
	  else
	  {
		this.setupdateStatus(false);
	  }

      if ( User_Action.equalsIgnoreCase( "getselbins" ) )
      {
        out.println(radian.web.HTMLHelpObj.getcablist(selHub,facility,workarea,db,relabelsession));
        //relabelsession.setAttribute("CABIDS_SHOWN", cabinetIdshown );
      }
      else if ( User_Action.equalsIgnoreCase( "Search" ) )
      {
        Vector data = new Vector();
        Vector showncabs = ( Vector ) relabelsession.getAttribute( "CABIDS_SHOWN" );

        Hashtable hubsetdetails = ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
        String branchplant = "";
       for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
       {
           String branch_plant= ( String ) ( e.nextElement() );
           String hub_name= ( String ) ( hubsetdetails.get( branch_plant ) );
           if (selHub.equalsIgnoreCase(hub_name))
           {
             branchplant = branch_plant;
             break;
           }
       }

        try
        {
          data= getResult( searchwhat,containsorlike,searchtext,facility,workarea,branchplant,cabsStringfromArray );
        }
        catch ( Exception e1 )
        {
          e1.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printHTMLError() );
        }

        relabelsession.setAttribute("CABCOUNTDATA", data );

        Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

        buildHeader( facility,workarea,initialData,selectedcabs,showncabs,selHub);

        if ( 0 == count )
        {
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
        }
        else
        {
          out.println( buildDetails( data ) );
        }

        //clean up
        data=null;
      }
      else if ( User_Action.equalsIgnoreCase( "generatexls" ) )
      {
        Vector retrndata= ( Vector ) relabelsession.getAttribute( "CABCOUNTDATA" );
        out.println( buildxlsDetails( retrndata,personnelid ) );
        retrndata=null;
      }
	  else if ( User_Action.equalsIgnoreCase( "Update" ) )
      {
		Vector retrn_data= ( Vector ) relabelsession.getAttribute( "CABCOUNTDATA" );
        Vector synch_data=SynchServerData( request,retrn_data );
		Vector showncabs = ( Vector ) relabelsession.getAttribute( "CABIDS_SHOWN" );

		Hashtable updateresults=updatecountdata( synch_data,personnelid );
		Boolean resultfromup = (Boolean)updateresults.get("RESULT");
        Vector errordata = (Vector)updateresults.get("ERRORDATA");
		boolean resulttotest =  resultfromup.booleanValue();

		buildHeader( facility,workarea,initialData,selectedcabs,showncabs,selHub);

		if ( false == resulttotest )
		{
		  out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occurred.<BR>Please Check Data and try Again." ) );
		}
		else
		{
		  out.println( radian.web.HTMLHelpObj.printMessageinTable( "All Your Selections Were Successfully Updated" ) );
		}
		out.println( buildDetails( errordata ) );
	  }
      else
      {
        Hashtable hub_sslist1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
        relabelsession.setAttribute("CABCOUNTDATA", new Vector() );

        Vector showncabs = new Vector();
        Hashtable result1=new Hashtable();
        result1.put( "All Cabinets","All" );
        showncabs.add(result1);
        relabelsession.setAttribute("CABIDS_SHOWN", showncabs );

        if ( hub_sslist1.size() < 1 )
        {
          buildHeader( facility,workarea,initialData,selectedcabs,showncabs,selHub);
          out.println( radian.web.HTMLHelpObj.printHTMLNoFacilities() );
          hub_list_set=null;
          out.close();
        }
        else
        {
          buildHeader( facility,workarea,initialData,selectedcabs,showncabs,selHub);
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

     private StringBuffer buildxlsDetails( Vector data,String personnelID ) throws Exception
     {
       //StringBuffer Msg=new StringBuffer();
	   String url = "";
	   String file="";

	   Random rand=new Random();
	   int tmpReq=rand.nextInt();
	   Integer tmpReqNum=new Integer( tmpReq );

	   String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	   String writefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();

	   file=writefilepath + personnelID + tmpReqNum.toString() + "cabmgmt.csv";
	   url=wwwHome + "reports/temp/" + personnelID + tmpReqNum.toString() + "cabmgmt.csv";

       try
       {
		 PrintWriter pw = new PrintWriter(new FileOutputStream(file));

         Hashtable summary=new Hashtable();
         summary= ( Hashtable ) data.elementAt( 0 );
         int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
         pw.print( "Cabinet,Bin,Item,Packaging,Description,Quantity \n" );

         int i=0;
         for ( int loop=0; loop < total; loop++ )
         {
           i++;
           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

		   String packaging=hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString();
		   String description=hD.get( "DESCRIPTION" ) == null ? "&nbsp;" : hD.get( "DESCRIPTION" ).toString();
		   //String bin_id=hD.get( "BIN_ID" ) == null ? "&nbsp;" : hD.get( "BIN_ID" ).toString();
		   //String cabinet_id=hD.get( "CABINET_ID" ) == null ? "&nbsp;" : hD.get( "CABINET_ID" ).toString();
		   String cabinet_name=hD.get( "CABINET_NAME" ) == null ? "&nbsp;" : hD.get( "CABINET_NAME" ).toString();
		   String bin_name=hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString();
		   String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
		   //String company_id=hD.get( "COMPANY_ID" ) == null ? "&nbsp;" : hD.get( "COMPANY_ID" ).toString();
		   //String updatedone=hD.get( "UPDATEDONE" ) == null ? "" : hD.get( "UPDATEDONE" ).toString();
		   String qtycunted=hD.get( "QTY_COUNTED" ) == null ? "" : hD.get( "QTY_COUNTED" ).toString();


           pw.print( "\"" + cabinet_name + "\"," );
           pw.print( "\"" + bin_name + "\"," );
           pw.print( "\"" + item_id + "\"," );
           pw.print( "\"" + packaging + "\"," );
           pw.print( "\"" + description + "\"," );
           pw.print( "\"" + qtycunted + "\"\n" );
         }
		 pw.close();
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Cabinet Management",intcmIsApplication ) );
       }

         StringBuffer MsgSB=new StringBuffer();
         if ( url.length() > 0 )
         {
           MsgSB.append( "<HTML><HEAD>\n" );
           MsgSB.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n" );
           MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
           MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
           MsgSB.append( "<TITLE>tcmIS Hub Cabinet Management</TITLE>\n" );
           MsgSB.append( "</HEAD>  \n" );
           MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
           MsgSB.append( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
           MsgSB.append( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Downloading Excel File</b></font><P></P><BR>\n" );
           MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n" );
           MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait ...</b></font><P></P><BR>\n" );
           MsgSB.append( "</CENTER>\n" );
           MsgSB.append( "</BODY></HTML>    \n" );
         }
         else
         {
           MsgSB.append( "<HTML><HEAD>\n" );
           MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
           MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
           MsgSB.append( "<TITLE>tcmIS Hub Cabinet Management</TITLE>\n" );
           MsgSB.append( "</HEAD>  \n" );
           MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
           MsgSB.append( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
           MsgSB.append( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>An Error Occured </b></font><P></P><BR>\n" );
           MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n" );
           MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please try Again or contact Tech Center.</b></font><P></P><BR>\n" );
           MsgSB.append( "</CENTER>\n" );
           MsgSB.append( "</BODY></HTML>    \n" );
         }

         return MsgSB;
    }

     //Build page - To build the gui page.
     private String buildDetails( Vector data )
     {
       StringBuffer Msg=new StringBuffer();
       StringBuffer MsgTmp1=new StringBuffer();
       String Color="CLASS=\"Inwhite";

       Hashtable hDNext=new Hashtable();
       try
       {
         Hashtable summary=new Hashtable();
         int ItemIdCount=0;
         int lastcab=1;
         boolean FirstRow=false;

         summary= ( Hashtable ) data.elementAt( 0 );
         int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

         //start table #3
         Msg.append( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

         int i=0; //used for detail lines
         for ( int loop=0; loop < total; loop++ )
         {
           i++;
           boolean createHdr=false;

           if ( loop % 10 == 0 && lastcab == 1 )
           {
             createHdr=true;
           }

           if ( createHdr )
           {
             Msg.append( "<tr align=\"center\">\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Cabinet</TH>\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Bin</TH>\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Item</TH>\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Packaging</TH>\n" );
             Msg.append( "<TH width=\"15%\"  height=\"38\">Description</TH>\n" );
			 Msg.append( "<TH width=\"5%\"  height=\"38\">Quantity</TH>\n" );
             Msg.append( "</tr>\n" );
           }

           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

           String Next_cab="";

           if ( ! ( i == total ) )
           {
             hDNext=new Hashtable();
             hDNext= ( Hashtable ) data.elementAt( i + 1 );
             Next_cab=hDNext.get( "CABINET_ID" ) == null ? "&nbsp;" : hDNext.get( "CABINET_ID" ).toString();
           }
           else
           {
             Next_cab=" ";
           }

		   String count_date=hD.get( "COUNT_DATE" ) == null ? "&nbsp;" : hD.get( "COUNT_DATE" ).toString();
		   String packaging=hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString();
		   String description=hD.get( "DESCRIPTION" ) == null ? "&nbsp;" : hD.get( "DESCRIPTION" ).toString();
		   String bin_id=hD.get( "BIN_ID" ) == null ? "&nbsp;" : hD.get( "BIN_ID" ).toString();
		   String cabinet_id=hD.get( "CABINET_ID" ) == null ? "&nbsp;" : hD.get( "CABINET_ID" ).toString();
		   String cabinet_name=hD.get( "CABINET_NAME" ) == null ? "&nbsp;" : hD.get( "CABINET_NAME" ).toString();
		   String bin_name=hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString();
		   String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
		   String company_id=hD.get( "COMPANY_ID" ) == null ? "&nbsp;" : hD.get( "COMPANY_ID" ).toString();
		   String updatedone=hD.get( "UPDATEDONE" ) == null ? "" : hD.get( "UPDATEDONE" ).toString();
   	       String qtycunted=hD.get( "QTY_COUNTED" ) == null ? "" : hD.get( "QTY_COUNTED" ).toString();

           boolean Samecab=false;

           if (cabinet_id.equalsIgnoreCase( Next_cab ) )
           {
             Samecab=true;
             lastcab++;
           }

           if ( Samecab )
           {
             if ( lastcab == 2 )
             {
               FirstRow=true;
             }
           }
		   else
		   {
             if ( ( !FirstRow ) && lastcab > 1 )
             {
               MsgTmp1.append( "<TR>\n" );
             }

             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + bin_name + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + item_id + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" >" + packaging + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"15%\" >" + description + "</td>\n" );
			 if ( !this.getupdateStatus() || "Yes".equalsIgnoreCase(updatedone))
			 {
			   MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"25\">"+qtycunted+"</td>\n" );
			 }
			 else
			 {
			   MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"25\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue('" + i + "')\" name=\"qty_picked" + i + "\"  value=\"\" maxlength=\"10\" size=\"5\"></td>\n" );
			 }
			 MsgTmp1.append( "</TR>\n" );

		     Msg.append( "<TR>\n" );
             Msg.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastcab + "\" >" + cabinet_name + "</td>\n" );

             Msg.append( MsgTmp1 );

             MsgTmp1=new StringBuffer();
             lastcab=1;
			 ItemIdCount++;
			 if ( ( ItemIdCount ) % 2 == 0 )
			 {
			   Color="CLASS=\"Inwhite";
			 }
			 else
			 {
			   Color="CLASS=\"Inblue";
			 }
			continue;
		  }

           if ( !FirstRow )
           {
             MsgTmp1.append( "<TR>\n" );
           }

			 MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + bin_name + "</td>\n" );
			 MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + item_id + "</td>\n" );
			 MsgTmp1.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" >" + packaging + "</td>\n" );
			 MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"15%\" >" + description + "</td>\n" );
			 if ( !this.getupdateStatus() || "Yes".equalsIgnoreCase(updatedone) )
			 {
			   MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"25\">"+qtycunted+"</td>\n" );
			 }
			 else
			 {
			   MsgTmp1.append( "<td " + Color + "\" width=\"5%\" height=\"25\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue('" + i + "')\" name=\"qty_picked" + i + "\"  value=\"\" maxlength=\"10\" size=\"5\"></td>\n" );
			 }
			 MsgTmp1.append( "</TR>\n" );
         }

         Msg.append( "</TABLE>\n" );
         Msg.append( "</FORM>\n" );
         Msg.append( "</BODY></HTML>\n" );
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         out.println( radian.web.HTMLHelpObj.printError( "Logistics",intcmIsApplication ) );
       }

       return Msg.toString();
     }


    //Build HTML Header
    private void buildHeader(String selectedFac,String tmpwaSelect, Hashtable initialData,Vector selectedcabs,Vector showncabs,String selectedHub)
    {
      //StringBuffer Msg=new StringBuffer();
      out.print(radian.web.HTMLHelpObj.printHTMLHeader("Cabinet Count","cabinetcount.js",intcmIsApplication));
      out.print("</HEAD>  \n");

      String Search_servlet = radian.web.tcmisResourceLoader.getcabinetcounturl();

      out.print("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
      out.print("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
      out.print("</DIV>\n");
      out.print("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

      out.print(radian.web.HTMLHelpObj.PrintTitleBar("Cabinet Count\n"));
	  if (initialData == null)
	  {
		out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
		return;
	  }

	  Vector hubID = (Vector)initialData.get("HUB_ID");
	  if (hubID.size() == 0)
	  {
		out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>There are no Cabinets in the hubs you have access to.<BR><BR>Contact tech support for more information."));
		return;
	  }

	  int i = 0;
	  if (selectedHub.trim().length() == 0 )
	  {
		selectedHub= ( String ) hubID.firstElement(); //select the first facility if none were selected
	  }

	  out.print("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
	  out.print(radian.web.HTMLHelpObj.createComboBoxData(initialData));
	  out.print("// -->\n </SCRIPT>\n");

      out.print( "<FORM METHOD=\"POST\" NAME=\"cabinetcounts\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n" );
      out.print( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
      out.print( "<TR VALIGN=\"MIDDLE\">\n" );
      //Hub
      out.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.print( "<B>Hub:</B>&nbsp;\n" );
      out.print( "</TD>\n" );
      out.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      out.print("<SELECT CLASS=\"HEADER\" NAME=\"hubC\" ID=\"hubC\" size=\"1\" OnChange=\"hubChanged(document.cabinetcounts.hubC)\">\n");

      /*Vector hubID = (Vector)initialData.get("HUB_ID");
      int i = 0;
      if (selectedHub.trim().length() == 0 )
      {
        selectedHub= ( String ) hubID.firstElement(); //select the first facility if none were selected
      }*/
      String preSelect = "";
      for (i = 0; i < hubID.size(); i++) {
        preSelect = "";
        if (selectedHub.equalsIgnoreCase((String)hubID.elementAt(i))) {
          preSelect = "selected";
          selectedHub = (String)hubID.elementAt(i);
        }
        out.print("<option "+preSelect+" value=\""+(String)hubID.elementAt(i)+"\">"+(String)hubID.elementAt(i)+"</option>\n");
      }
      out.print("</SELECT>\n");
      out.print("</TD>\n");

      // Cabinets
      out.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" ROWSPAN=\"3\">\n" );
      out.print( "<B>Cabinets:</B>&nbsp;<BR>\n" );
      out.print("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"getbinsdd\" value=\"Get Cabinets\" OnClick=\"getbins()\">\n");

      out.print( "</TD>\n" );
      out.print( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" ROWSPAN=\"3\">\n" );
      out.print( "<SELECT CLASS=\"HEADER\" NAME=\"binids\" ID=\"binids\" size=\"7\" multiple>\n" );
      //out.print( "<OPTION VALUE=\"All\">All Cabinets</OPTION>\n" );
      out.print( radian.web.HTMLHelpObj.getmultipleDropDown(showncabs,selectedcabs ) );
      out.print("</SELECT>\n");
      out.print("</TD>\n");

      //Search
      out.print("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      out.print( "<INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
      out.print("</TD>\n</TR>\n");

      out.print( "<TR VALIGN=\"MIDDLE\">\n" );
      //Facility
      out.print("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      out.print("<B>Facility:</B>\n");
      out.print("</TD>\n");
      out.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      out.print("<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" ID=\"facId\" size=\"1\" OnChange=\"facilityChanged(document.cabinetcounts.facilityName)\">\n");
      Hashtable fh = (Hashtable)initialData.get(selectedHub);
      Vector facID = (Vector)fh.get("FACILITY_ID");
      Vector facDesc = (Vector)fh.get("FACILITY_DESC");
      i = 0;
      if (selectedFac.trim().length() == 0 )
      {
        selectedFac= ( String ) facID.firstElement(); //select the first facility if none were selected
      }
      preSelect = "";
      for (i = 0; i < facID.size(); i++) {
        preSelect = "";
        if (selectedFac.equalsIgnoreCase((String)facID.elementAt(i))) {
          preSelect = "selected";
          selectedFac = (String)facID.elementAt(i);
        }
        out.print("<option "+preSelect+" value=\""+(String)facID.elementAt(i)+"\">"+(String)facDesc.elementAt(i)+"</option>\n");
      }
      out.print("</SELECT>\n");
      out.print("</TD>\n");

      //Create XLS
      out.print( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
      out.print("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Create XLS\" onclick= \"return createxls(this)\" NAME=\"XlsButton\">&nbsp;\n");
      out.print( "</TD>\n" );

      out.print( "<TR VALIGN=\"MIDDLE\">\n" );
      //Work Area
      out.print("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      out.print("<B>Work Area:</B>\n");
      out.print("</TD>\n");
      out.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      //out.print("<INPUT TYPE=\"hidden\" NAME=\"WorkAreaFullName\" ID=\"WorkAreaFullName\" value=\"\">\n");
      out.print("<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\" OnChange=\"workareaChanged()\">\n");
      Hashtable h = (Hashtable)fh.get(selectedFac);
      Vector application = (Vector)h.get("APPLICATION");
      Vector applicationDesc = (Vector)h.get("APPLICATION_DESC");
      for (i = 0; i < application.size(); i++) {
        preSelect = "";
        if (tmpwaSelect.equalsIgnoreCase((String)application.elementAt(i))) {
          preSelect = "selected";
        }
        out.print("<option "+preSelect+" value=\""+(String)application.elementAt(i)+"\">"+(String)applicationDesc.elementAt(i)+"</option>\n");
      }
      out.print("</SELECT>\n");
      out.print("</TD>\n");

      //Update
      out.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      out.print("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Update\" onclick= \"updatecount(this)\" NAME=\"newBin\">&nbsp;\n");
      out.print("</TD>\n");

      out.print("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      //out.print("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"New Cabinet\" onclick= \"newcab(this)\" NAME=\"newCab\">&nbsp;\n");
      out.print("</TD>\n");
      out.print("</TR>\n");

      out.print("</TABLE>\n");
      out.print("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");

      return;
    }

	 private Vector getResult( String searchLike,String containsorlke,String searchtext,String facSelected,String waSelected,String sourcehub,String cabselected )
       throws Exception
    {
      ResultSet searchRs = null;
      DBResultSet dbrs = null;
      Vector Data=new Vector();
      Hashtable DataH=new Hashtable();
      Hashtable summary=new Hashtable();

      boolean falgforand =false;

      int count=0;
      summary.put( "TOTAL_NUMBER",new Integer( count ) );
      Data.addElement( summary );

      try
      {
		String query="select CABINET_NAME,to_char(sysdate,'YYYY/MM/DD hh24:mi') COUNT_DATE,fx_kit_packaging(ITEM_ID) PACKAGING,fx_item_desc(ITEM_ID,'MA') DESCRIPTION,BIN_ID,CABINET_ID,BIN_NAME,ITEM_ID,COMPANY_ID from cabinet_bin_item_view where status = 'A' ";
        falgforand = true;
		if ( !( cabselected.trim().length() == 0 || "'All'".equalsIgnoreCase( cabselected ) ) )
		{
		  if ( falgforand )
			query+=" and CABINET_ID in (" + cabselected + ")  ";
		  else
			query+=" CABINET_ID in (" + cabselected + ")  ";
		}
		else
		{
		  query+=" where rownum = 0 ";
	    }

        query+=" ORDER BY CABINET_ID,BIN_ID,ITEM_ID";

          dbrs=db.doQuery( query );
          searchRs=dbrs.getResultSet();

          while ( searchRs.next() )
          {
            DataH=new Hashtable();
			//COUNT_DATE
			DataH.put("COUNT_DATE",searchRs.getString("COUNT_DATE")==null?"":searchRs.getString("COUNT_DATE"));
			//PACKAGING
			DataH.put("PACKAGING",searchRs.getString("PACKAGING")==null?"":searchRs.getString("PACKAGING"));
			//DESCRIPTION
			DataH.put("DESCRIPTION",searchRs.getString("DESCRIPTION")==null?"":searchRs.getString("DESCRIPTION"));
			//BIN_ID
			DataH.put("BIN_ID",searchRs.getString("BIN_ID")==null?"":searchRs.getString("BIN_ID"));
			//CABINET_ID
			DataH.put("CABINET_ID",searchRs.getString("CABINET_ID")==null?"":searchRs.getString("CABINET_ID"));
			//CABINET_NAME
			DataH.put("CABINET_NAME",searchRs.getString("CABINET_NAME")==null?"":searchRs.getString("CABINET_NAME"));
			//BIN_NAME
			DataH.put("BIN_NAME",searchRs.getString("BIN_NAME")==null?"":searchRs.getString("BIN_NAME"));
			//ITEM_ID
			DataH.put("ITEM_ID",searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID"));
			//COMPANY_ID
			DataH.put("COMPANY_ID",searchRs.getString("COMPANY_ID")==null?"":searchRs.getString("COMPANY_ID"));
			//QTY_COUNTED
		    DataH.put("QTY_COUNTED","");
			//UPDATEDONE
			DataH.put("UPDATEDONE","");

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

        return Data;
      }


  private Vector SynchServerData( HttpServletRequest request,Vector in_data )
  {
	Vector new_data=new Vector();
	Hashtable sum= ( Hashtable ) ( in_data.elementAt( 0 ) );
	int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
	new_data.addElement( sum );

	try
	{
	  for ( int i=1; i < count + 1; i++ )
	  {
		Hashtable hD=new Hashtable();
		hD= ( Hashtable ) ( in_data.elementAt( i ) );

		String qtypicked="";
		qtypicked=request.getParameter( "qty_picked" + i );
		if (qtypicked == null)
		{
		 qtypicked="";
		}

		hD.remove( "QTY_COUNTED" );
		hD.put( "QTY_COUNTED",qtypicked.trim() );

		new_data.addElement( hD );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  out.print( radian.web.HTMLHelpObj.printError( "Picking QC",intcmIsApplication ) );
	}

	return new_data;
  }

  public Hashtable updatecountdata( Vector inData, String personnelid ) throws Exception
{
  Hashtable result=new Hashtable();
  String curcompany_id = "";
  Vector errordata=new Vector();
  boolean upresult = true;
  Collection binIdsScanned = new Vector();
  String binIdsScannedList = "";
  //personnelid = "9422";

  Hashtable sum= ( Hashtable ) ( inData.elementAt( 0 ) );
  int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
  errordata.addElement( sum );
  try
  {
  for ( int i=1; i < count + 1; i++ )
	{
	  Hashtable hD=new Hashtable();
	  hD= ( Hashtable ) ( inData.elementAt( i ) );

	  String bin_id=hD.get( "BIN_ID" ).toString();
      String qty= hD.get( "QTY_COUNTED" ).toString();
      String updatedone=hD.get( "UPDATEDONE" ) == null ? "" : hD.get( "UPDATEDONE" ).toString();
      if ( !"Yes".equalsIgnoreCase(updatedone) && qty.trim().length() > 0)
	  {
        if (!binIdsScanned.contains(bin_id))
	    {
    		binIdsScanned.add(bin_id);
        }
      }
    }
  }
  catch ( Exception e234 )
  {
	e234.printStackTrace();
    upresult =false;
  }

  int scanBincount = 0;
  Iterator binIdsScannedIterator = binIdsScanned.iterator();
  while (binIdsScannedIterator.hasNext()) {
    String binId = (String) binIdsScannedIterator.next();
    if (scanBincount > 0) {
     binIdsScannedList += ",";
    }

    binIdsScannedList += "" + binId + "";
    scanBincount++;
  }

  //Update date processed for any unprocessed scans from a previous scan for the bins that were just uploaded.
  String updateDateProcessedForPrevCounts = "update cabinet_item_inventory_count set date_processed = sysdate where bin_id in ("+binIdsScannedList+") ";
  try
  {
    if (binIdsScannedList !=null && binIdsScannedList.length() > 0)
    {
      	db.doUpdate( updateDateProcessedForPrevCounts );
    }
  }
  catch ( Exception ex )
  {
	radian.web.emailHelpObj.sendnawazemail("Manual Cabinet Count Error Update date processed for any unprocessed scans from a previous scan.",
            "Manual Cabinet Count Error Update date processed for any unprocessed scans from a previous scan for the bins that were just uploaded.\n\n"
                    +ex.getMessage()+"\n\n"+binIdsScannedList+"");
   //upresult = false;
  }

  try
  {
	for ( int i=1; i < count + 1; i++ )
	{
	  Hashtable hD=new Hashtable();
	  hD= ( Hashtable ) ( inData.elementAt( i ) );

	  String bin_id=hD.get( "BIN_ID" ).toString();
	  String qty= hD.get( "QTY_COUNTED" ).toString();
	  String timestamp= hD.get( "COUNT_DATE" ).toString();
	  String compid= hD.get( "COMPANY_ID" ).toString();
	  String item_id=hD.get( "ITEM_ID" ).toString();
	  curcompany_id = compid;

   	  String updatedone=hD.get( "UPDATEDONE" ) == null ? "" : hD.get( "UPDATEDONE" ).toString();

	  //timestamp format yyyy/MM/DD hh:MM:ss
	  // the table to insert is cabinet_inventory_count
	  if ( !"Yes".equalsIgnoreCase(updatedone) && qty.trim().length() > 0)
	  {
		String insertdata="insert into cabinet_item_inventory_count (BIN_ID,COUNT_DATETIME,PERSONNEL_ID,ITEM_ID,COUNT_QUANTITY,COMPANY_ID) values \n";
		insertdata+="(" + bin_id + ",to_date('" + timestamp + "','YYYY/MM/DD hh24:mi:ss')," + personnelid + "," + item_id + "," + qty + ",'" + compid + "') \n";

        if (!binIdsScanned.contains(bin_id))
	    {
    		binIdsScanned.add(bin_id);
        }

        try
		{
		  db.doUpdate( insertdata );
		  hD.remove( "UPDATEDONE" );
		  hD.put( "UPDATEDONE","Yes" );
		}
		catch ( Exception ex )
		{
		  ex.printStackTrace();
		  radian.web.emailHelpObj.sendnawazemail( "Error from inserting into cabinet_item_inventory_count This count might already be entered",
												  "Error from inserting into cabinet_item_inventory_count This count might already be entered\n\n" +  ex.getMessage() + "" );
		  upresult=false;
		}
	  }

	  errordata.addElement( hD );
	}
  }
  catch ( Exception e234 )
  {
	e234.printStackTrace();
	upresult =false;
  }

  DBResultSet dbrs=null;
  ResultSet searchRs=null;
  Vector resultdata=new Vector();
  Hashtable DataH=new Hashtable();
  String applicationScannedList = "";

  try
  {
      /*if (binIdsScannedList !=null && binIdsScannedList.length() > 0)
      {
      //binIdsScannedList = "";
      String query="select distinct APPLICATION from CABINET_BIN where bin_id in (" + binIdsScannedList + ") ";
      String testbranchplant="";
      String opsEntityId = "";
      int applicationScannedListCount=0;

      DBResultSet applicationDbrs=null;
      ResultSet applicationSearchRs=null;

      applicationDbrs=db.doQuery( query );
      applicationSearchRs=applicationDbrs.getResultSet();
      while ( applicationSearchRs.next() )
      {
        String applicationScannedListTmp= ( applicationSearchRs.getString( "APPLICATION" ) == null ? "" : applicationSearchRs.getString( "APPLICATION" ) );
        if (applicationScannedListCount > 0) {
		 applicationScannedList += ",";
		}

		applicationScannedList += "'" + applicationScannedListTmp + "'";
		applicationScannedListCount++;
      }
      applicationDbrs.close();
      }*/
    //curcompany_id = "GE";
    String cabmrview="select * from cabinet_item_mr_create_view where COMPANY_ID = '"+curcompany_id+"'";
    /*if (applicationScannedList !=null && applicationScannedList.trim().length() > 0)
    {
      cabmrview += " and ORDERING_APPLICATION in ("+applicationScannedList+")";
    }*/
    if (binIdsScannedList !=null && binIdsScannedList.trim().length() > 0)
    {
      cabmrview += " and BIN_ID in ("+binIdsScannedList+")";
    }
    cabmrview += " order by ITEM_ID ";
	dbrs=db.doQuery( cabmrview );
	searchRs=dbrs.getResultSet();

	while ( searchRs.next() )
	{
	  DataH=new Hashtable();

	  //COMPANY_ID
	  DataH.put( "COMPANY_ID",searchRs.getString( "COMPANY_ID" ) == null ? "" : searchRs.getString( "COMPANY_ID" ) );
	  //CATALOG_ID
	  DataH.put( "CATALOG_ID",searchRs.getString( "CATALOG_ID" ) == null ? "" : searchRs.getString( "CATALOG_ID" ) );
	  //ITEM_ID
	  DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
	  //CAT_PART_NO
	  DataH.put( "CAT_PART_NO",searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ) );
	  //ORDERING_FACILITY
	  DataH.put( "ORDERING_FACILITY_ID",searchRs.getString( "ORDERING_FACILITY" ) == null ? "" : searchRs.getString( "ORDERING_FACILITY" ) );
	  //ORDERING_APPLICATION
	  DataH.put( "ORDERING_APPLICATION",searchRs.getString( "ORDERING_APPLICATION" ) == null ? "" : searchRs.getString( "ORDERING_APPLICATION" ) );
	  //PART_GROUP_NO
	  DataH.put( "PART_GROUP_NO",searchRs.getString( "PART_GROUP_NO" ) == null ? "" : searchRs.getString( "PART_GROUP_NO" ) );
	  //INVENTORY_GROUP
	  DataH.put( "INVENTORY_GROUP",searchRs.getString( "INVENTORY_GROUP" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP" ) );
	  //MR_QUANTITY
	  DataH.put( "QUANTITY_RECEIVED",searchRs.getString( "MR_QUANTITY" ) == null ? "" : searchRs.getString( "MR_QUANTITY" ) );
	  //STOCKED
	  DataH.put( "STOCKED",searchRs.getString( "STOCKED" ) == null ? "" : searchRs.getString( "STOCKED" ) );
	  //UNIT_PRICE
	  DataH.put( "UNIT_PRICE",searchRs.getString( "UNIT_PRICE" ) == null ? "" : searchRs.getString( "UNIT_PRICE" ) );
	  //CATALOG_PRICE
	  DataH.put( "CATALOG_PRICE",searchRs.getString( "CATALOG_PRICE" ) == null ? "" : searchRs.getString( "CATALOG_PRICE" ) );
	  //ACCOUNT_SYS_ID
	  DataH.put( "ACCOUNT_SYS_ID",searchRs.getString( "ACCOUNT_SYS_ID" ) == null ? "" : searchRs.getString( "ACCOUNT_SYS_ID" ) );
	  //PACKAGING
	  DataH.put("PACKAGING","Cabinet Count");
	  //REQUESTOR
	  DataH.put("REQUESTOR",""+personnelid+"");
      //CABINET_REPLENISHMENT
	  DataH.put("CABINET_REPLENISHMENT","Y");
      //CURRENCY_ID
	  DataH.put( "CURRENCY_ID",searchRs.getString( "currency_id" ) == null ? "" : searchRs.getString( "currency_id" ) );
	  resultdata.addElement( DataH );
	}
  }
  catch ( Exception ex )
  {
	ex.printStackTrace();
	upresult=false;
  }
  finally
  {
	if ( dbrs != null )
	{
	  dbrs.close();
	}
  }

  NoBuyOrderPo nbpObj=new NoBuyOrderPo();
  String[] resulthg=new String[3];
  String finalmrlist = "";
  for ( int loop=0; loop < resultdata.size(); loop++ )
  {
	Hashtable mrhD =new Hashtable();
	mrhD = ( Hashtable ) resultdata.elementAt( loop );

	try
	{
	  resulthg=nbpObj.processRequest( mrhD );
	}
	catch ( Exception ew )
	{
	  ew.printStackTrace();
	  upresult=false;
	}

	String resultok=resulthg[0].toString();
	String prnumber=resulthg[1].toString();
	String lineItem=resulthg[2].toString();

	if ( !"OK".equalsIgnoreCase( resultok ) )
	{
	  upresult=false;
	  radian.web.emailHelpObj.senddeveloperemail("Something Went wrong while Creating MR for Cabinet Item Count","\nSomething Went wrong while Creating MR for Cabinet Item Count\n Result is not OK. PR "+prnumber+"\n\nCheck Logs.");
	}
	else
	{
	  try
	  {
		finalmrlist += prnumber + ",";
		String[] args=new String[3];
    args[0]=curcompany_id;
    args[1]=prnumber;
		args[2]=lineItem;
		db.doProcedure( "p_line_item_allocate",args );
	  }
	  catch ( Exception dbe )
	  {
		radian.web.emailHelpObj.sendnawazemail( "p_line_item_allocate error (pr_number: " + prnumber + " line: " + lineItem + ")",
												"Error occured while trying to call procedure \n\nErr:\n" + dbe.getMessage() + "" );
	 }
	}
  }

  String updatecabmr = "update cabinet_item_inventory_count x  set date_processed = sysdate  where PERSONNEL_ID = "+personnelid+" and COMPANY_ID = '"+curcompany_id+"' and BIN_ID in ("+binIdsScannedList+") and date_processed is null and exists (select * from  cabinet_item_mr_create_view i,cabinet_part_item_bin p ";
  updatecabmr += " where  p.item_id = i.item_id (+) and  p.company_id = i.company_id (+) and  p.facility_id = i.ordering_facility (+) and  ";
  updatecabmr += " p.application = i.ordering_application (+) and  p.catalog_id = i.catalog_id (+) and  p.cat_part_no = i.cat_part_no (+) and ";
  updatecabmr += " p.part_group_no = i.part_group_no (+) and  nvl(i.count_datetime, x.count_datetime - 1) < x.count_datetime and  p.bin_id = x.bin_id) and date_processed is null ";


   /*String updatecabmr = "update cabinet_inventory_count c set date_processed = (select sysdate from cabinet_part_item_bin b, cabinet_mr_create_view e ";
   updatecabmr += " where b.bin_id = c.bin_id and b.company_id    = e.company_id (+) and b.facility_id       = e.ordering_facility (+) ";
   updatecabmr += " and b.application       = e.ordering_application (+) ";
   updatecabmr += " and b.catalog_id        = e.catalog_id (+) ";
   updatecabmr += " and b.cat_part_no       = e.cat_part_no (+) ";
   updatecabmr += " and b.part_group_no = e.part_group_no (+)  ";
   updatecabmr += " and nvl(e.count_datetime,c.count_datetime - 1) < c.count_datetime) where date_processed is null ";*/

  try
  {
    if (binIdsScannedList !=null && binIdsScannedList.length() > 0)
    {
      	db.doUpdate( updatecabmr );
    }
  }
  catch ( Exception ex )
  {
	radian.web.emailHelpObj.sendnawazemail("Cabinet Item Count Error from Updating after creating MR's","Error from Updating after creating MR's\n\n"+ex.getMessage()+"");
	upresult = false;
  }

  if ( finalmrlist.length() > 0 )
  {
	finalmrlist=finalmrlist.substring( 0,finalmrlist.length() - 1 );
  }
  String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
  wwwHome = wwwHome.substring(0, wwwHome.length() - 1);
  String resulturl = wwwHome + radian.web.tcmisResourceLoader.getcabupconfirmurl();
  /*radian.web.emailHelpObj.sendnawazemail("Cabinet Count Process Results","The count has been processed successfully, please go to the URL below to look at the results.\n\n\n"+resulturl+"UserAction=Search&mrslist="+finalmrlist+"\n\n\nPersonnel ID : "+personnelid+"");*/
  radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"Cabinet Count Process Results","The count has been processed successfully, please go to the URL below to look at the results.\n\n\n"+resulturl+"UserAction=Search&mrslist="+finalmrlist+"",Integer.parseInt(personnelid),true);

  result.put("RESULT",new Boolean(upresult));
  result.put( "ERRORDATA",errordata );
  return result;
 }

}