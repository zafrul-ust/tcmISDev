package radian.web.cabinets;

//
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 09-09-03 - Fixing a Bug. Not include All in cabinet ID
 * 11-12-03 - Changed Management to Deifnition in the title
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 06-01-04 - Code Refactoring and Sorting the work area dropdown, and showing APPLICATION_DESC instead of APPLICATION
 */


public class cabinetDefinition
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private PrintWriter out=null;
  //private DBResultSet dbrs=null;
  //private ResultSet searchRs=null;
  private boolean allowupdate;
  //private Vector cabinetIdshown = null;
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

  public cabinetDefinition( ServerResourceBundle b,TcmISDBModel d )
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
        //System.out.println(statusStringfromArray);
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

    //System.out.println( "User_Action   "+User_Action+"  User Search is " + searchwhat + "  containsorlike" + containsorlike + "  facility " + facility+ "  workarea   "+workarea );

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

      if ( User_Action.equalsIgnoreCase( "AddandQuit" ) ||   User_Action.equalsIgnoreCase( "AddandContinue" ) )
      {
        boolean addresult = true;
        String locid = "";
        String invngrp = "";
        String cmpid = "";
        String errmsg = "";
        DBResultSet dbrs = null;
        ResultSet searchRs = null;

        String newcabname=request.getParameter( "cabname" );
        if ( newcabname == null )
          newcabname="";
        newcabname=newcabname.trim();

        if (newcabname.length() > 0)
        {
          String addquery="select LOCATION_ID,INVENTORY_GROUP,COMPANY_ID from fac_loc_app where application = '"+workarea+"' ";
          ResultSet rs=null;

          try
          {
            dbrs=db.doQuery( addquery );
            rs=dbrs.getResultSet();
            while ( rs.next() )
            {
              locid= ( rs.getString( "LOCATION_ID" ) == null ? "" : rs.getString( "LOCATION_ID" ) );
              invngrp= ( rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ) );
              cmpid= ( rs.getString( "COMPANY_ID" ) == null ? "" : rs.getString( "COMPANY_ID" ) );
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

          String addupdateqry="insert into fac_loc_app (facility_id,application,location_id,application_desc,delivery_point,status,company_id,inventory_group) ";
          addupdateqry+="values ('"+facility+"','"+newcabname+"','"+locid+"','Cabinet','"+newcabname+"','A','"+cmpid+"','"+invngrp+"') ";


          String instocab = "insert into cabinet(company_id,facility_id,ordering_application,cabinet_id,use_application,cabinet_name) ";
          instocab += "select company_id,facility_id, application,cabinet_seq.nextval, '"+workarea+"', '"+newcabname+"' from fac_loc_app ";
          instocab += "Where company_id = '"+cmpid+"' and facility_id = '"+facility+"' and application =  '"+newcabname+"' ";

          try
          {
            db.doUpdate( addupdateqry );
            db.doUpdate( instocab );
          }
          catch ( Exception ex )
          {
            addresult = false;
            errmsg = "<BR><B>An Error Occured <BR>" + ex.getMessage();
          }
       }
       else
       {
         errmsg = "<BR><B>No Cabinet Name Specified</B>";
         addresult = false;
       }

       if (addresult){errmsg = "<BR><B>New Cabinet Created Successfully.</B>";}
       if (User_Action.equalsIgnoreCase( "AddandQuit" ) && !addresult) {User_Action = "";}
       buildnewcab( selHub,facility,workarea,initialData,newcabname,User_Action,errmsg);
      }
      else if ( User_Action.equalsIgnoreCase( "newcab" ) )
      {
        buildnewcab( selHub,facility,workarea,initialData,"",User_Action,"");
      }
      else if ( User_Action.equalsIgnoreCase( "getselbins" ) )
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

        relabelsession.setAttribute("CABMGMTDATA", data );

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
        Vector retrndata= ( Vector ) relabelsession.getAttribute( "CABMGMTDATA" );
        out.println( buildxlsDetails( retrndata,personnelid ) );
        retrndata=null;
      }
      else
      {
        Hashtable hub_sslist1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
        relabelsession.setAttribute("CABMGMTDATA", new Vector() );

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
         pw.print( "Work Area,Cabinet,Bin,Catalog,Part Number,Item,Description,Packaging,MSDS Number,Reorder Point,Stocking Level \n" );

         int i=0;
         for ( int loop=0; loop < total; loop++ )
         {
           i++;
           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

           //String source_hub=hD.get( "SOURCE_HUB" ) == null ? "&nbsp;" : hD.get( "SOURCE_HUB" ).toString();
           //String company_id=hD.get( "COMPANY_ID" ) == null ? "&nbsp;" : hD.get( "COMPANY_ID" ).toString();
           //String facility_id=hD.get( "FACILITY_ID" ) == null ? "&nbsp;" : hD.get( "FACILITY_ID" ).toString();
           String application=hD.get( "CABINET_ID" ) == null ? "&nbsp;" : hD.get( "CABINET_ID" ).toString();
           String catalog_id=hD.get( "CATALOG_ID" ) == null ? "&nbsp;" : hD.get( "CATALOG_ID" ).toString();
           String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "&nbsp;" : hD.get( "CAT_PART_NO" ).toString();
           String materialidno=hD.get( "MATERIAL_ID_STRING" ) == null ? "&nbsp;" : hD.get( "MATERIAL_ID_STRING" ).toString();
           String reorder_point=hD.get( "REORDER_POINT" ) == null ? "&nbsp;" : hD.get( "REORDER_POINT" ).toString();
           String stocking_level=hD.get( "STOCKING_LEVEL" ) == null ? "&nbsp;" : hD.get( "STOCKING_LEVEL" ).toString();

           String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
           String packaging=hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString();
           String material_desc=hD.get( "MATERIAL_DESC" ) == null ? "&nbsp;" : hD.get( "MATERIAL_DESC" ).toString();
           String mfg_desc=hD.get( "MFG_DESC" ) == null ? "&nbsp;" : hD.get( "MFG_DESC" ).toString();
           //String cabinet_id=hD.get( "CABINET_ID" ) == null ? "&nbsp;" : hD.get( "CABINET_ID" ).toString();
           String bin_name=hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString();
           String cabinet_name=hD.get( "CABINET_NAME" ) == null ? "&nbsp;" : hD.get( "CABINET_NAME" ).toString();

           pw.print( "\"" + application + "\"," );
           pw.print( "\"" + cabinet_name + "\"," );
           pw.print( "\"" + bin_name + "\"," );
           pw.print( "\"" + catalog_id + "\"," );
           pw.print( "\"" + cat_part_no + "\"," );
           pw.print( "\"" + item_id + "\"," );
           pw.print( "\"" + material_desc + ".." + mfg_desc + "\"," );
           pw.print( "\"" + packaging + "\"," );
           pw.print( "\"" + materialidno + "\"," );
           pw.print( "\"" + reorder_point + "\"," );
           pw.print( "\"" + stocking_level + "\"\n" );
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
       StringBuffer MsgTmp=new StringBuffer();
       StringBuffer MsgTmp1=new StringBuffer();
       StringBuffer MsgTmpDate=new StringBuffer();
       String Color="CLASS=\"Inwhite";

       Hashtable hDNext=new Hashtable();
       try
       {
         Hashtable summary=new Hashtable();
         int ItemIdCount=0;
         int lastwaNum=1;
         int lastcab=1;

         boolean FirstRow=false;
         boolean FirstdateRow=false;

         summary= ( Hashtable ) data.elementAt( 0 );
         int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

         //start table #3
         Msg.append( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

         int i=0; //used for detail lines
         for ( int loop=0; loop < total; loop++ )
         {
           i++;
           boolean createHdr=false;

           if ( loop % 10 == 0 && lastwaNum == 1 )
           {
             createHdr=true;
           }

           if ( createHdr )
           {
             Msg.append( "<tr align=\"center\">\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Work Area</TH>\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Cabinet</TH>\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Bin</TH>\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Catalog</TH>\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Part Number</TH>\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Item</TH>\n" );
             Msg.append( "<TH width=\"5%\"  height=\"38\">Description</TH>\n" );
             Msg.append( "</tr>\n" );
           }

           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

           String Next_wa="";
           String Next_cab="";
           String Next_bin="";
           String Next_catalog="";

           if ( ! ( i == total ) )
           {
             hDNext=new Hashtable();
             hDNext= ( Hashtable ) data.elementAt( i + 1 );
             Next_wa=hDNext.get( "APPLICATION" ) == null ? "&nbsp;" : hDNext.get( "APPLICATION" ).toString();
             Next_cab=hDNext.get( "CABINET_ID" ) == null ? "&nbsp;" : hDNext.get( "CABINET_ID" ).toString();
             Next_bin=hDNext.get( "BIN_ID" ) == null ? "&nbsp;" : hDNext.get( "BIN_ID" ).toString();
             Next_catalog=hDNext.get( "CATALOG_ID" ) == null ? "&nbsp;" : hDNext.get( "CATALOG_ID" ).toString();
           }
           else
           {
             Next_wa=" ";
             Next_cab=" ";
             Next_bin=" ";
             Next_catalog="";
           }

           //String source_hub = hD.get("SOURCE_HUB")==null?"&nbsp;":hD.get("SOURCE_HUB").toString();
           String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
           //String packaging=hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString();
           //String material_id_string=hD.get( "MATERIAL_ID_STRING" ) == null ? "&nbsp;" : hD.get( "MATERIAL_ID_STRING" ).toString();
           String material_desc=hD.get( "MATERIAL_DESC" ) == null ? "&nbsp;" : hD.get( "MATERIAL_DESC" ).toString();
           String mfg_desc=hD.get( "MFG_DESC" ) == null ? "&nbsp;" : hD.get( "MFG_DESC" ).toString();
           //String company_id=hD.get( "COMPANY_ID" ) == null ? "&nbsp;" : hD.get( "COMPANY_ID" ).toString();
           //String facility_id=hD.get( "FACILITY_ID" ) == null ? "&nbsp;" : hD.get( "FACILITY_ID" ).toString();
           String application=hD.get( "APPLICATION" ) == null ? "&nbsp;" : hD.get( "APPLICATION" ).toString();
           String catalog_id=hD.get( "CATALOG_ID" ) == null ? "&nbsp;" : hD.get( "CATALOG_ID" ).toString();
           String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "&nbsp;" : hD.get( "CAT_PART_NO" ).toString();
           //String part_group_no=hD.get( "PART_GROUP_NO" ) == null ? "&nbsp;" : hD.get( "PART_GROUP_NO" ).toString();
           //String reorder_point=hD.get( "REORDER_POINT" ) == null ? "&nbsp;" : hD.get( "REORDER_POINT" ).toString();
           //String stocking_level=hD.get( "STOCKING_LEVEL" ) == null ? "&nbsp;" : hD.get( "STOCKING_LEVEL" ).toString();
           //String bin_id=hD.get( "BIN_ID" ) == null ? "&nbsp;" : hD.get( "BIN_ID" ).toString();
           String cabinet_id=hD.get( "CABINET_ID" ) == null ? "&nbsp;" : hD.get( "CABINET_ID" ).toString();
           String bin_name=hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString();
           String cabinet_name=hD.get( "CABINET_NAME" ) == null ? "&nbsp;" : hD.get( "CABINET_NAME" ).toString();

           boolean Samewa=false;
           boolean Samecab=false;

           if ( application.equalsIgnoreCase( Next_wa ) )
           {
             Samewa=true;
             lastwaNum++;
             if (cabinet_id.equalsIgnoreCase( Next_cab ) )
             {
               Samecab=true;
               lastcab++;
             }
           }

           if ( Samecab )
           {
             if ( lastcab == 2 )
             {
               FirstdateRow=true;
             }
             if ( !FirstdateRow )
             {
               MsgTmp1.append( "<TR>\n" );
             }
             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + bin_name + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"10%\" >" + catalog_id + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" >" + cat_part_no + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + item_id + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >"+material_desc+".."+mfg_desc+"</td>\n" );
             MsgTmp1.append( "</TR>\n" );
           }
           else if ( !Samecab )
           {
             if ( ( !FirstdateRow ) && lastcab > 1 )
             {
               MsgTmp1.append( "<TR>\n" );
             }

             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + bin_name + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"10%\" >" + catalog_id + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "l\" width=\"5%\" >" + cat_part_no + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >" + item_id + "</td>\n" );
             MsgTmp1.append( "<td height=\"25\" " + Color + "\" width=\"5%\" >"+material_desc+".."+mfg_desc+"</td>\n" );
             MsgTmp1.append( "</TR>\n" );

             MsgTmpDate.append( "<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastcab + "\" >" + cabinet_name + "</td>\n" );

             MsgTmpDate.append( MsgTmp1 );

             MsgTmp1=new StringBuffer();
             lastcab=1;
           }

           if ( Samewa )
           {
             if ( lastwaNum == 2 )
             {
               FirstRow=true;
             }
           }
           else
           {
             if ( ( !FirstRow ) && lastwaNum > 1 )
             {
               MsgTmp.append( "<TR>\n" );
             }

             if ( !Samecab )
             {
               MsgTmp.append( MsgTmpDate );
             }
             else
             {

             }

             MsgTmp.append( "</TR>\n" );

             Msg.append( "<TR>\n" );

             Msg.append( "<td height=\"25\" " + Color + "\" width=\"10%\" ALIGN=\"LEFT\" ROWSPAN=\"" + lastwaNum + "\">" + application + "</td>\n" );
             Msg.append( MsgTmp );

             MsgTmp=new StringBuffer();
             MsgTmpDate=new StringBuffer();
             lastwaNum=1;

             if ( !application.equalsIgnoreCase( Next_wa )  )
             {
               ItemIdCount++;
             }


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
             MsgTmp.append( "<TR>\n" );
           }

           if ( !Samecab )
           {
             MsgTmp.append( MsgTmpDate );
             MsgTmpDate=new StringBuffer();
           }
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
      out.print(radian.web.HTMLHelpObj.printHTMLHeader("Cabinet Definition","cabinetdef.js",intcmIsApplication));
      out.print("</HEAD>  \n");

      String Search_servlet = radian.web.tcmisResourceLoader.getcabinetdefurl();

      out.print("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
      out.print("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
      out.print("</DIV>\n");
      out.print("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

      out.print(radian.web.HTMLHelpObj.PrintTitleBar("Cabinet Definition\n"));
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

      out.print( "<FORM METHOD=\"POST\" NAME=\"cabinetdefs\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n" );
      out.print( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
      out.print( "<TR VALIGN=\"MIDDLE\">\n" );
      //Hub
      out.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.print( "<B>Hub:</B>&nbsp;\n" );
      out.print( "</TD>\n" );
      out.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      out.print("<SELECT CLASS=\"HEADER\" NAME=\"hubC\" ID=\"hubC\" size=\"1\" OnChange=\"hubChanged(document.cabinetdefs.hubC)\">\n");

      /*int i = 0;
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
      out.print("<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" ID=\"facId\" size=\"1\" OnChange=\"facilityChanged(document.cabinetdefs.facilityName)\">\n");
      Hashtable fh = (Hashtable)initialData.get(selectedHub);
      //System.out.print(fh);
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
      //System.out.print(h);
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

      //New Bin
      out.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      out.print("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"New Bin\" onclick= \"newbin(this)\" NAME=\"newBin\">&nbsp;\n");
      out.print("</TD>\n");

      //New Cabinet
      out.print("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      //out.print("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"New Cabinet\" onclick= \"newcab(this)\" NAME=\"newCab\">&nbsp;\n");
      out.print("</TD>\n");
      out.print("</TR>\n");

      out.print("</TABLE>\n");
      out.print("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");

      return;
    }
    private void buildnewcab(String selectedHub,String selectedFac,String tmpwaSelect, Hashtable initialData,String newcabname,
                                     String useraction,String message)
    {
          //StringBuffer Msg=new StringBuffer();
          out.print(radian.web.HTMLHelpObj.printHTMLHeader("New Cabinet","cabinetdef.js",intcmIsApplication));

          out.print("</HEAD>  \n");

          String Search_servlet = radian.web.tcmisResourceLoader.getcabinetdefurl();

          out.print("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
          out.print("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
          out.print("</DIV>\n");
          out.print("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

          out.print(radian.web.HTMLHelpObj.PrintTitleBarNolink("New Cabinet\n"));
          out.print(message);
          out.print( "<FORM METHOD=\"POST\" NAME=\"cabinetdefs\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n" );
          out.print( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
          int i=0;
          out.print( "<TR VALIGN=\"MIDDLE\">\n" );
          //Facility
          out.print("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
          out.print("<B>Facility:</B>\n");
          out.print("</TD>\n");
          out.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
          Hashtable fh = (Hashtable)initialData.get(selectedHub);
          //System.out.print(fh);
          //Vector facID = (Vector)fh.get("FACILITY_ID");
          //Vector facDesc = (Vector)fh.get("FACILITY_DESC");
          out.print("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"facilityName\" ID=\"facId\" value=\""+selectedFac+"\" size=\"12\" readonly>\n");
          out.print("</TD>\n");

          out.print("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
          out.print( "&nbsp;\n" );
          out.print("</TD>\n");

          out.print( "<TR VALIGN=\"MIDDLE\">\n" );
          //Work Area
          out.print("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
          out.print("<B>Work Area:</B>\n");
          out.print("</TD>\n");
          out.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
          if ( useraction.equalsIgnoreCase( "AddandQuit" ) )
          {
           out.print("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"workAreaName\" ID=\"waName\" value=\""+tmpwaSelect+"\" size=\"12\" readonly>\n");
          }
          else
          {
            //out.print("<INPUT TYPE=\"hidden\" NAME=\"WorkAreaFullName\" ID=\"WorkAreaFullName\" value=\"\">\n");
            out.print( "<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\">\n" );
            Hashtable h= ( Hashtable ) fh.get( selectedFac );
            //System.out.print(h);
            Vector application= ( Vector ) h.get( "APPLICATION" );
            //Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );
            for ( i=0; i < application.size(); i++ )
            {
              String preSelect="";
              String tmpapp= ( String ) application.elementAt( i );
              if ( ! ( "All Work Areas".equalsIgnoreCase( tmpapp ) ) )
              {
                if ( tmpwaSelect.equalsIgnoreCase( tmpapp ) )
                {
                  preSelect="selected";
                }
                out.print( "<option " + preSelect + " value=\"" + tmpapp + "\">" + tmpapp + "</option>\n" );
              }
            }
          }
          out.print("</SELECT>\n");
          out.print("</TD>\n");

          out.print("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
          out.print( "&nbsp;\n" );
          out.print("</TD>\n");

          out.print("</TR>\n");

          out.print( "<TR VALIGN=\"MIDDLE\">\n" );
          //New Cabinet
          out.print("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
          out.print("<B>New Cabinet:</B>\n");
          out.print("</TD>\n");

          //New Cabinet Name
          out.print("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
          if ( useraction.equalsIgnoreCase( "AddandQuit" ) )
          {
            out.print("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"cabname\" ID=\"cabname\" value=\""+newcabname+"\" size=\"12\" readonly>\n");
          }
          else
          {
            out.print( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"cabname\" ID=\"cabname\" value=\""+newcabname+"\" size=\"12\" MAXLENGTH=\"30\">\n" );
          }
          out.print("</TD>\n");
          out.print("</TR>\n");

          out.print( "<TR VALIGN=\"MIDDLE\">\n" );
          if ( useraction.equalsIgnoreCase( "AddandQuit" ) )
          {
            //Cancel
            out.print("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
            out.print( "&nbsp;\n" );
            out.print("</TD>\n");

            //Blank
            out.print( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"CENTER\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
            out.print("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" onclick= \"closeUserWin()\" NAME=\"closewin\">&nbsp;\n");
            out.print("</TD>\n");

            //Blank
            out.print("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
            out.print( "&nbsp;\n" );
            out.print("</TD>\n");
          }
          else
          {
          //Add & Quit
          out.print( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
          out.print( "<INPUT TYPE=\"submit\" VALUE=\"Add & Quit\" onclick= \"return addquit(this)\" NAME=\"Addquitbut\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
          out.print("</TD>\n");

          //Add & Continue
         out.print("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"CENTER\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
         out.print( "<INPUT TYPE=\"submit\" VALUE=\"Add & Continue\" onclick= \"return addcont(this)\" NAME=\"AddConbutton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
         out.print("</TD>\n");

         //Cancel
         out.print("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
         out.print("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Cancel\" onclick= \"closeUserWin()\" NAME=\"closewin\">&nbsp;\n");
         out.print("</TD>\n");
         }
        out.print("</TR>\n");

          out.print("</TABLE>\n");
          out.print("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");
          out.print("<INPUT TYPE=\"hidden\" NAME=\"hubC\" VALUE=\""+selectedHub+"\">\n");

          return;
        }

    private Vector getResult( String searchLike,String containsorlke,String searchtext,String facSelected,String waSelected,String sourcehub,String cabselected )
       throws Exception
    {
      Vector Data=new Vector();
      Hashtable DataH=new Hashtable();
      Hashtable summary=new Hashtable();
      ResultSet searchRs = null;
      DBResultSet dbrs = null;
      boolean falgforand =false;

      int count=0;
      summary.put( "TOTAL_NUMBER",new Integer( count ) );
      Data.addElement( summary );

      try
      {
        String query="select distinct x.* from cabinet_part_level_view x ";
        query += "where x.STATUS = 'A' and x.source_hub = "+sourcehub+"  ";
        //query += "and x.facility_id = '"+facSelected+"'  ";
        falgforand = true;

		if ( facSelected.length() > 0 && (!"All Facilities".equalsIgnoreCase(facSelected)) )
		{
		  query+="and x.FACILITY_ID='" + facSelected + "'";
		  falgforand = true;
		}

        if ( searchtext.length() > 0 )
        {
          query+="and y." + searchLike + "";
          if ( "Contains".equalsIgnoreCase( containsorlke ) )
          {
            query+=" like '%" + searchtext + "%' ";
          }
          else
          {
            query+=" = '" + searchtext + "' ";
          }
          falgforand = true;
        }

        if ( !"All Work Areas".equalsIgnoreCase( waSelected ) )
        {
          if ( falgforand )
            query+="and x.USE_APPLICATION = '" + waSelected + "'  ";
          else
            query+=" x.USE_APPLICATION = '" + waSelected + "'  ";
        }

        if ( !( cabselected.trim().length() == 0 || "'All'".equalsIgnoreCase( cabselected ) ) )
        {
          if ( falgforand )
            query+=" and CABINET_ID in (" + cabselected + ")  ";
          else
            query+=" CABINET_ID in (" + cabselected + ")  ";
        }

        query+=" ORDER BY USE_APPLICATION,CABINET_ID,BIN_ID,CATALOG_ID";

          dbrs=db.doQuery( query );
          searchRs=dbrs.getResultSet();

          /*ResultSetMetaData rsMeta1 = searchRs.getMetaData();
          System.out.print("Finished The Querry\n "+rsMeta1.getColumnCount());
          for(int i =1; i<=rsMeta1.getColumnCount(); i++)
          {
          System.out.print("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");

          //System.out.print("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
          //System.out.print("//"+rsMeta1.getColumnName(i).toString()+"");
          //System.out.print("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
          }*/

          while ( searchRs.next() )
          {
            DataH=new Hashtable();
            //SOURCE_HUB
            DataH.put( "SOURCE_HUB",searchRs.getString( "SOURCE_HUB" ) == null ? "" : searchRs.getString( "SOURCE_HUB" ) );
            //ITEM_ID
            DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
            //PACKAGING
            DataH.put( "PACKAGING",searchRs.getString( "PACKAGING" ) == null ? "" : searchRs.getString( "PACKAGING" ) );
            //MATERIAL_ID_STRING
            DataH.put( "MATERIAL_ID_STRING",searchRs.getString( "MATERIAL_ID_STRING" ) == null ? "" : searchRs.getString( "MATERIAL_ID_STRING" ) );
            //MATERIAL_DESC
            DataH.put( "MATERIAL_DESC",searchRs.getString( "MATERIAL_DESC" ) == null ? "" : searchRs.getString( "MATERIAL_DESC" ) );
            //MFG_DESC
            DataH.put( "MFG_DESC",searchRs.getString( "MFG_DESC" ) == null ? "" : searchRs.getString( "MFG_DESC" ) );
            //COMPANY_ID
            DataH.put( "COMPANY_ID",searchRs.getString( "COMPANY_ID" ) == null ? "" : searchRs.getString( "COMPANY_ID" ) );
            //FACILITY_ID
            DataH.put( "FACILITY_ID",searchRs.getString( "FACILITY_ID" ) == null ? "" : searchRs.getString( "FACILITY_ID" ) );
            //APPLICATION
            DataH.put( "APPLICATION",searchRs.getString( "APPLICATION" ) == null ? "" : searchRs.getString( "APPLICATION" ) );
            //CATALOG_ID
            DataH.put( "CATALOG_ID",searchRs.getString( "CATALOG_ID" ) == null ? "" : searchRs.getString( "CATALOG_ID" ) );
            //CAT_PART_NO
            DataH.put( "CAT_PART_NO",searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ) );
            //PART_GROUP_NO
            DataH.put( "PART_GROUP_NO",searchRs.getString( "PART_GROUP_NO" ) == null ? "" : searchRs.getString( "PART_GROUP_NO" ) );
            //REORDER_POINT
            DataH.put( "REORDER_POINT",searchRs.getString( "REORDER_POINT" ) == null ? "" : searchRs.getString( "REORDER_POINT" ) );
            //STOCKING_LEVEL
            DataH.put( "STOCKING_LEVEL",searchRs.getString( "STOCKING_LEVEL" ) == null ? "" : searchRs.getString( "STOCKING_LEVEL" ) );
            //BIN_ID
            DataH.put( "BIN_ID",searchRs.getString( "BIN_ID" ) == null ? "" : searchRs.getString( "BIN_ID" ) );
            //CABINET_ID
            DataH.put( "CABINET_ID",searchRs.getString( "CABINET_ID" ) == null ? "" : searchRs.getString( "CABINET_ID" ) );
            //BIN_NAME
            DataH.put( "BIN_NAME",searchRs.getString( "BIN_NAME" ) == null ? "" : searchRs.getString( "BIN_NAME" ) );
            //CABINET_NAME
            DataH.put( "CABINET_NAME",searchRs.getString( "CABINET_NAME" ) == null ? "" : searchRs.getString( "CABINET_NAME" ) );
            //USE_APPLICATION
            DataH.put( "USE_APPLICATION",searchRs.getString( "USE_APPLICATION" ) == null ? "" : searchRs.getString( "USE_APPLICATION" ) );

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
          if ( dbrs != null )
          {
            dbrs.close();
          }

        }

        Hashtable recsum=new Hashtable();
        recsum.put( "TOTAL_NUMBER",new Integer( count ) );
        Data.setElementAt( recsum,0 );

        return Data;
      }
}
