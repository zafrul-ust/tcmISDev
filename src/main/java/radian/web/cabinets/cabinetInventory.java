package radian.web.cabinets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 06-01-04 - Code Refactoring and Sorting the work area dropdown, and showing APPLICATION_DESC instead of APPLICATION
 * 06-29-04 - Showing Unit Cost
 * 08-0-03 - Showing part number, Reorder Point and Stocking Level
 */

public class cabinetInventory
{
  private TcmISDBModel db=null;
  private PrintWriter out=null;
  //private DBResultSet dbrs=null;
  //private ResultSet searchRs=null;
  private Vector receiptIdstoLabel = null;
  private Vector numbersoflabels = null;
  private boolean completeSuccess = true ;
  private boolean noneToUpd = true ;
  protected boolean allowupdate;
  //private Vector cabinetIdshown = null;
	private boolean intcmIsApplication = false;
  /*public void setupdateStatus( boolean id )
  {
    this.allowupdate=id;
  }*/

  /*private boolean getupdateStatus()
     throws Exception
  {
    return this.allowupdate;
  }*/

  public cabinetInventory( TcmISDBModel d )
  {
    db=d;
  }

  public void doResult( HttpServletRequest request,
                        HttpServletResponse response )
     throws ServletException,IOException
  {
    out=response.getWriter();
    response.setContentType( "text/html" );
    HttpSession relabelsession=request.getSession();

    String personnelid=BothHelpObjs.makeBlankFromNull( ( String ) relabelsession.getAttribute( "PERSONNELID" ) );
		PersonnelBean personnelBean = (PersonnelBean) relabelsession.getAttribute("personnelBean");
    if (personnelBean !=null)
    {
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}
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
      searchwhat="ITEM_ID";
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

	String expfrom=request.getParameter( "expfrom" );
	if ( expfrom == null )
	  expfrom="";
	expfrom=expfrom.trim();

	String expto=request.getParameter( "expto" );
	if ( expto == null )
	  expto="";
	expto=expto.trim();

  String showonlyPositiveQty=request.getParameter( "showonlyPositiveQty" );
	if ( showonlyPositiveQty == null )
	  showonlyPositiveQty="";
	showonlyPositiveQty=showonlyPositiveQty.trim();

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

			initialDataLoaded = "";
			try {
			 initialDataLoaded = relabelsession.getAttribute("CABINET_LABEL_DATA_LOADED").toString();
			}
			catch (Exception getdoneFlag) {
			initialDataLoaded = "";
		  }

			if (initialDataLoaded.length() == 0) {
			try {
			 initialDataLoaded = relabelsession.getAttribute("CLIENT_CABINET_DATA_LOADED").toString();
			}
			catch (Exception ex) {
			 initialDataLoaded = "";
			}
		 }

      if ( initialDataLoaded.equalsIgnoreCase( "Yes" ) )
      {
        initialData= ( Hashtable ) relabelsession.getAttribute( "INITIAL_CABINET_LABEL_DATA" );
        hub_list_set  = (Hashtable)relabelsession.getAttribute("HUB_PREF_LIST");
      }
      else
      {
        //String CompanyID = relabelsession.getAttribute("COMPANYID").toString();
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
	  /*if (personnelBean.getPermissionBean().hasFacilityPermission("CabinetMgmt",selHub,null))
	  {
		this.setupdateStatus(true);
	  }
	  else
	  {
		this.setupdateStatus(false);
	  }*/

      if ( User_Action.equalsIgnoreCase( "getselbins" ) )
      {
        out.println(radian.web.HTMLHelpObj.getcablist(selHub,facility,workarea,db,relabelsession));
        //relabelsession.setAttribute("CABIDS_SHOWN", cabinetIdshown );
      }
      else if ( User_Action.equalsIgnoreCase( "Search" ) )
      {
        Vector data = new Vector();
        //Vector cablist = new Vector();
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
		  Hashtable faccompdetails = ( Hashtable ) initialData.get("FAC_COMP_DATA");
		  String faccompanyId = (String) faccompdetails.get(facility);
          data= getResult( searchwhat,containsorlike,searchtext,facility,workarea,sortby,branchplant,cabsStringfromArray,expfrom,expto,faccompanyId );
          //cablist = this.getcabinetlist();
        }
        catch ( Exception e1 )
        {
          e1.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printHTMLError() );
        }

        relabelsession.setAttribute("CABMGMTDATA", data );
        //relabelsession.setAttribute("CABLISTMGMT", cablist );

        Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

        buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData," ",sortby,hub_list_set,selectedcabs,showncabs,selHub,expfrom,expto,showonlyPositiveQty);

        if ( 0 == count )
        {
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
        }
        else
        {
          buildDetails( data,branchplant,showonlyPositiveQty);
        }

        //clean up
        data=null;
      }
      else if ( User_Action.equalsIgnoreCase( "generatexls" ) )
      {
        Vector retrndata= ( Vector ) relabelsession.getAttribute( "CABMGMTDATA" );
        out.println( buildxlsDetails( retrndata,"createxls",personnelid,showonlyPositiveQty ) );
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
          buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData,"",sortby,hub_list_set,selectedcabs,showncabs,selHub,expfrom,expto,showonlyPositiveQty);
          out.println( radian.web.HTMLHelpObj.printHTMLNoFacilities() );
          hub_list_set=null;
          out.close();
        }
        else
        {
          buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData,"",sortby,hub_list_set,selectedcabs,showncabs,selHub,expfrom,expto,showonlyPositiveQty);
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

  /*public Hashtable getcabinetInitialData(Hashtable hublist) throws Exception
   {

     Hashtable hubsetdetails = ( Hashtable ) ( hublist.get( "HUB_LIST" ) );
     String allowedhubs = "";
     for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
     {
         String branch_plant= ( String ) ( e.nextElement() );
         String hub_name= ( String ) ( hubsetdetails.get( branch_plant ) );
         allowedhubs += "'"+hub_name+ "',";
     }
     allowedhubs = allowedhubs.substring(0,allowedhubs.length()-1);

     String query="select * from hub_cabinet_view where PREFERRED_WAREHOUSE in ("+allowedhubs+") order by PREFERRED_WAREHOUSE,FACILITY_ID,APPLICATION";
     DBResultSet dbrs=null;
     ResultSet rs=null;
     Hashtable resultdata =new Hashtable();
     Vector hubID = new Vector();
     Vector hubDesc = new Vector();
	 Hashtable facCompanyData =new Hashtable();

     try
     {
       dbrs=db.doQuery( query );
       rs=dbrs.getResultSet();

       String lastHub="";

       while ( rs.next() )
       {
         String tmpFacID=rs.getString( "facility_id" );
         String tmpHub=rs.getString( "PREFERRED_WAREHOUSE" );
		 String tmpCompanyID=rs.getString( "COMPANY_ID" );

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
           facDesc.addElement( rs.getString( "facility_name" ) );
		   facCompanyData.put(tmpFacID,tmpCompanyID);
           Hashtable h=new Hashtable();
           //application data
           Vector application=new Vector();
           Vector applicationDesc=new Vector();
           application.addElement( rs.getString( "application" ) );
           applicationDesc.addElement( rs.getString( "application_desc" ) );
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
             facDesc.addElement( rs.getString( "facility_name" ) );
			 facCompanyData.put(tmpFacID,tmpCompanyID);
             Hashtable h=new Hashtable( 3 );
             Vector application=new Vector();
             Vector applicationDesc=new Vector();
             application.addElement( rs.getString( "application" ) );
             applicationDesc.addElement( rs.getString( "application_desc" ) );
             h.put( "APPLICATION",application );
             h.put( "APPLICATION_DESC",applicationDesc );
             fh.put( tmpFacID,h );
           }
           else
           {
             Hashtable h= ( Hashtable ) fh.get( tmpFacID );
             Vector application= ( Vector ) h.get( "APPLICATION" );
             Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );
             if ( !application.contains( rs.getString( "application" ) ) )
             {
               application.addElement( rs.getString( "application" ) );
               applicationDesc.addElement( rs.getString( "application_desc" ) );
             }
             h.put( "APPLICATION",application );
             h.put( "APPLICATION_DESC",applicationDesc );
             fh.put( tmpFacID,h );
           }
           fh.put( "FACILITY_ID",facID );
           fh.put( "FACILITY_DESC",facDesc );
           //resultdata.put( "DATA",fh );
           resultdata.put(tmpHub,fh);
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
	 resultdata.put("FAC_COMP_DATA",facCompanyData);

     return resultdata;
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
         if ( facIDV.size() > 1 && !facIDV.contains( "All Facilities" ) )
         {
           facIDV.insertElementAt( "All Facilities",0 );
           facDescV.insertElementAt( "All Facilities",0 );
           Vector wAreaID=new Vector( 1 );
           wAreaID.addElement( "All Work Areas" );
           Vector wAreaDesc=new Vector( 1 );
           wAreaDesc.addElement( "All Work Areas" );
           Vector tmpAcS=new Vector( 1 );
           tmpAcS.addElement( "All Accounting Systems" );
           Hashtable tmpFacWA=new Hashtable( 3 );
           tmpFacWA.put( "APPLICATION",wAreaID );
           tmpFacWA.put( "APPLICATION_DESC",wAreaDesc );
           tmpFacWA.put( "ACCOUNTING_SYSTEM",tmpAcS );
           fh.put( "All Facilities",tmpFacWA );
         }
         altFacID += "altFacID[\""+hubID+"\"] = new Array(";
         altFacDesc += "altFacDesc[\""+hubID+"\"] = new Array(";
         for ( i=0; i < facIDV.size(); i++ )
         {
           String facID= ( String ) facIDV.elementAt( i );
           tmp+="\"" + facID + "\"" + ",";
           altFacID+="\"" + facID + "\"" + ",";
           altFacDesc+="\"" + ( String ) facDescV.elementAt( i ) + "\"" + ",";
           //build work area
           Hashtable h= ( Hashtable ) fh.get( facID );
           Vector application= ( Vector ) h.get( "APPLICATION" );
           Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );
           if ( application.size() > 1 && !application.contains( "All Work Areas" ) )
           {
             application.insertElementAt( "All Work Areas",0 );
             applicationDesc.insertElementAt( "All Work Areas",0 );
           }
           altWAID+="altWAID[\"" + facID + "\"] = new Array(";
           altWADesc+="altWADesc[\"" + facID + "\"] = new Array(";
           for ( int j=0; j < application.size(); j++ )
           {
             altWAID+="\"" + ( String ) application.elementAt( j ) + "\"" + ",";
             altWADesc+="\"" + HelpObjs.findReplace( ( String ) application.elementAt( j ),"\"","'" ) + "\"" + ",";
           }
           altWAID=altWAID.substring( 0,altWAID.length() - 1 ) + ");\n";
           altWADesc=altWADesc.substring( 0,altWADesc.length() - 1 ) + ");\n";
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

     public StringBuffer getcablist( String hubSel,String facSelected,String waSelected,String cabselected )
     {
       StringBuffer Msgn=new StringBuffer();
       StringBuffer Msgbody=new StringBuffer();
       Hashtable result=null;

       Msgn.append( radian.web.HTMLHelpObj.printHTMLHeader( "Get Bins","cabinetlabels.js" ) );
       Msgn.append( "</HEAD>\n" );

       Msgbody.append( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"\">\n" );

       StringBuffer Msgtemp=new StringBuffer();

       //Build the Java Script Here and Finish the thing
       Msgtemp.append( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
       Msgtemp.append( "<!--\n" );

       Msgtemp.append( "function sendSupplierData()\n" );
       Msgtemp.append( "{\n" );

       int count=0;

       String revisionfromquery="";

       Msgtemp.append( "opener.removeAllOptionItem(opener.document.getElementById(\"binids\"));\n" );
       Msgtemp.append( "opener.addOptionItem(opener.document.getElementById(\"binids\"),'All','All Cabinets');\n" );
       cabinetIdshown=new Vector();
       result=new Hashtable();
       result.put( "All Cabinets","All" );
       cabinetIdshown.add( result );

       try
       {
         boolean falgforand=false;
         String binquery="select * from hub_cabinet_view where ";

         if ( facSelected.length() > 0 && ( !"All Facilities".equalsIgnoreCase( facSelected ) ) )
         {
           binquery+="FACILITY_ID='" + facSelected + "'";
           falgforand=true;
         }
		 else if ( "All Facilities".equalsIgnoreCase(facSelected) )
		 {
		  binquery+="PREFERRED_WAREHOUSE='" + hubSel + "'";
		  falgforand=true;
		 }

         if ( "All Work Areas".equalsIgnoreCase( waSelected ) )
         {

         }
         else
         {
           if ( falgforand )
             binquery+=" and APPLICATION = '" + waSelected + "'  ";
           else
             binquery+=" APPLICATION = '" + waSelected + "'  ";
         }

         binquery+=" order by APPLICATION";

         dbrs=db.doQuery( binquery );
         searchRs=dbrs.getResultSet();

         while ( searchRs.next() )
         {
           result=new Hashtable();

           String hazcode=searchRs.getString( "CABINET_ID" ) == null ? "" : searchRs.getString( "CABINET_ID" );
           String hazcodedesc=searchRs.getString( "CABINET_NAME" ) == null ? "" : searchRs.getString( "CABINET_NAME" );
           result.put( hazcodedesc,hazcode );
           cabinetIdshown.add( result );

           Msgtemp.append( "opener.addOptionItem(opener.document.getElementById(\"binids\"),'" + hazcode + "','" + hazcodedesc + "');\n" );
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

       DBResultSet compdbrs=null;
       ResultSet comprs=null;

       Msgtemp.append( "window.close();\n" );
       Msgtemp.append( " }\n" );

       Msgtemp.append( "// -->\n</SCRIPT>\n" );
       Msgn.append( Msgtemp );
       //Msgn.append("<BODY><BR><BR>\n");
       Msgn.append( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );

       Msgbody.append( "<CENTER><BR><BR>\n" );
       Msgbody.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
       Msgbody.append( "</FORM></BODY></HTML>\n" );
       Msgn.append( Msgbody );

       return Msgn;
    }*/

     private StringBuffer buildxlsDetails( Vector data,String SubuserAction,String personnelID,String showonlyPositiveQty ) throws Exception
     {
       StringBuffer Msg=new StringBuffer();

       try
       {
         Hashtable summary=new Hashtable();
         summary= ( Hashtable ) data.elementAt( 0 );
         int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
         int vsize=data.size();

         Hashtable hDNext=new Hashtable();
         Msg.append( "Item,Description,DPM,Part Num,Work Area,Cabinet,Receipt ID,Mfg Lot, Expire Date,Last Count Date,Last Count Qty,Delivered Since Last Count,Total Qty,Hub OnHand Qty,Unit Cost,Reorder Point,Stocking Level,Lead Time in Days\n" );

         int i=0;
         for ( int loop=0; loop < total; loop++ )
         {
           i++;
           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

		   //String hub=hD.get( "HUB" ) == null ? "&nbsp;" : hD.get( "HUB" ).toString();
		   //String bin_id=hD.get( "BIN_ID" ) == null ? "&nbsp;" : hD.get( "BIN_ID" ).toString();
		   //String cabinet_id=hD.get( "CABINET_ID" ) == null ? "&nbsp;" : hD.get( "CABINET_ID" ).toString();
		   String cabinet_name=hD.get( "CABINET_NAME" ) == null ? "&nbsp;" : hD.get( "CABINET_NAME" ).toString();
		   //String facility_id=hD.get( "FACILITY_ID" ) == null ? "&nbsp;" : hD.get( "FACILITY_ID" ).toString();
		   //String ordering_application=hD.get( "ORDERING_APPLICATION" ) == null ? "&nbsp;" : hD.get( "ORDERING_APPLICATION" ).toString();
		   String use_application=hD.get( "USE_APPLICATION" ) == null ? "&nbsp;" : hD.get( "USE_APPLICATION" ).toString();
		   //String company_id=hD.get( "COMPANY_ID" ) == null ? "&nbsp;" : hD.get( "COMPANY_ID" ).toString();
		   String receipt_id=hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString();
		   String mfg_lot=hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString();
		   String expire_date=hD.get( "EXPIRE_DATE" ) == null ? "&nbsp;" : hD.get( "EXPIRE_DATE" ).toString();
		   String count_datetime=hD.get( "COUNT_DATETIME" ) == null ? "&nbsp;" : hD.get( "COUNT_DATETIME" ).toString();
		   //String counted_by_personnel_id=hD.get( "COUNTED_BY_PERSONNEL_ID" ) == null ? "&nbsp;" : hD.get( "COUNTED_BY_PERSONNEL_ID" ).toString();
		   //String counted_by_name=hD.get( "COUNTED_BY_NAME" ) == null ? "&nbsp;" : hD.get( "COUNTED_BY_NAME" ).toString();
		   String count_quantity=hD.get( "COUNT_QUANTITY" ) == null ? "&nbsp;" : hD.get( "COUNT_QUANTITY" ).toString();
		   String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
		   String item_desc=hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString();
		   String qty_issued_after_count=hD.get( "QTY_ISSUED_AFTER_COUNT" ) == null ? "&nbsp;" : hD.get( "QTY_ISSUED_AFTER_COUNT" ).toString();
		   String total_quantity=hD.get( "TOTAL_QUANTITY" ) == null ? "&nbsp;" : hD.get( "TOTAL_QUANTITY" ).toString();
		   String qc_doc=hD.get( "QC_DOC" ) == null ? "&nbsp;" : hD.get( "QC_DOC" ).toString();
		   String unit_cost=hD.get( "UNIT_COST" ) == null ? "&nbsp;" : hD.get( "UNIT_COST" ).toString();
		   String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "&nbsp;" : hD.get( "CAT_PART_NO" ).toString();
		   String reorder_point=hD.get( "REORDER_POINT" ) == null ? "&nbsp;" : hD.get( "REORDER_POINT" ).toString();
		   String stocking_level = hD.get("STOCKING_LEVEL")==null?"&nbsp;":hD.get("STOCKING_LEVEL").toString();
		   String qty_available_after_alloc = hD.get("qty_available_after_alloc")==null?"&nbsp;":hD.get("qty_available_after_alloc").toString();
			 String leadtimedays = hD.get("LEAD_TIME_DAYS")==null?"":hD.get("LEAD_TIME_DAYS").toString();

       String totalQuantity=hD.get( "TOTAL_QUANTITY" ) == null ? "0" : hD.get( "TOTAL_QUANTITY" ).toString();
       BigDecimal totQty = new BigDecimal(totalQuantity);
       if ((showonlyPositiveQty.equalsIgnoreCase( "Yes" ) && totQty.intValue() > 0) || !showonlyPositiveQty.equalsIgnoreCase( "Yes" ) )           
       {
           Msg.append( "\"" + item_id + "\"," );
           Msg.append( "\"" + item_desc + "\"," );
		   Msg.append( "\"" + qc_doc + "\"," );
		   Msg.append( "\"" + cat_part_no + "\"," );
           //Msg.append( "\"" + ordering_application + "\"," );
           Msg.append( "\"" + use_application + "\"," );
           Msg.append( "\"" + cabinet_name + "\"," );
           Msg.append( "\"" + receipt_id + "\"," );
           Msg.append( "\"" + mfg_lot + "\"," );
           Msg.append( "\"" + expire_date + "\"," );
           Msg.append( "\"" + count_datetime + "\"," );
		   Msg.append( "\"" + count_quantity + "\"," );
		   Msg.append( "\"" + qty_issued_after_count + "\"," );
           Msg.append( "\"" + total_quantity + "\"," );
		   Msg.append( "\"" + qty_available_after_alloc + "\"," );
		   Msg.append( "\"" + unit_cost + "\"," );
		   Msg.append( "\"" + reorder_point + "\"," );
		   Msg.append( "\"" + stocking_level + "\"," );
			 Msg.append( "\"" + leadtimedays + "\"\n" );
       }
         }
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Cabinet Inventory",intcmIsApplication ) );
       }

         String url = "";
         String file = "";
         PrintStream ps = null;

         Random rand=new Random();
         int tmpReq=rand.nextInt();
         Integer tmpReqNum=new Integer( tmpReq );

         String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
         String writefilepath=radian.web.tcmisResourceLoader.getsavelreportpath();

         file=writefilepath + "/" + personnelID + tmpReqNum.toString() + "cabmgmt.csv";
         url=wwwHome + "reports/" + personnelID + tmpReqNum.toString() + "cabmgmt.csv";

         try
         {
           ps=new PrintStream( new FileOutputStream( file ),true );

           String contents="";
           contents+=Msg;
           byte outbuf[];
           outbuf=contents.getBytes();
           ps.write( outbuf );
           ps.close();
         }
         catch ( IOException writee )
         {
           writee.printStackTrace();
           url="";
         }

         StringBuffer MsgSB=new StringBuffer();
         if ( url.length() > 0 )
         {
           MsgSB.append( "<HTML><HEAD>\n" );
           MsgSB.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n" );
           MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
           MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
           MsgSB.append( "<TITLE>tcmIS Hub Cabinet Inventory</TITLE>\n" );
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
           MsgSB.append( "<TITLE>tcmIS Hub Cabinet Inventory</TITLE>\n" );
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
     private void buildDetails( Vector data,String hubnum,String showonlyPositiveQty)
     {
       //StringBuffer Msg=new StringBuffer();

       try
       {
         Hashtable summary=new Hashtable();
         summary= ( Hashtable ) data.elementAt( 0 );
         int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
         int vsize=data.size();
         out.println( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
         String error_item;

         int i=0; //used for detail lines
         for ( int loop=0; loop < total; loop++ )
         {
           i++;
           boolean createHdr=false;

           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );
           String totalQuantity=hD.get( "TOTAL_QUANTITY" ) == null ? "0" : hD.get( "TOTAL_QUANTITY" ).toString();
           BigDecimal totQty = new BigDecimal(totalQuantity);
           //System.out.println("totalQuantity "+totalQuantity+" showonlyPositiveQty "+showonlyPositiveQty+"");
           if ((showonlyPositiveQty.equalsIgnoreCase( "Yes" ) && totQty.intValue() > 0) || !showonlyPositiveQty.equalsIgnoreCase( "Yes" ) )
           {
           if ( loop % 10 == 0 )
           {
             createHdr=true;
           }

           if ( createHdr )
           {
             out.println( "<tr align=\"center\">\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Item</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Description</TH>\n" );
			 if ("2118".equalsIgnoreCase(hubnum))
			 {
			   out.println( "<TH width=\"5%\"  height=\"38\">DPM</TH>\n" );
			 }
			 else
			 {
			   out.println( "<TH width=\"5%\"  height=\"38\">Part Num</TH>\n" );
		     }
 		     out.println( "<TH width=\"5%\"  height=\"38\">Hub OnHand Qty</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Work Area</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Cabinet</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Receipt Id</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Mfg Lot</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Expire Date</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Last Count Date</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Last Count Qty</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Delivered Since Last Count</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Total Qty</TH>\n" );

						 out.println( "<TH width=\"5%\"  height=\"38\">Unit Cost</TH>\n" );
						 out.println( "<TH width=\"5%\"  height=\"38\">Reorder Point</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Stocking Level</TH>\n" );
						 out.println( "<TH width=\"5%\"  height=\"38\">Lead Time in Days</TH>\n" );
             out.println( "</tr>\n" );
           }

	//	HUB, BIN_ID, CABINET_ID, CABINET_NAME, FACILITY_ID, ORDERING_APPLICATION, USE_APPLICATION, COMPANY_ID, RECEIPT_ID, MFG_LOT, EXPIRE_DATE, COUNT_DATETIME, COUNTED_BY_PERSONNEL_ID, COUNTED_BY_NAME, COUNT_QUANTITY, ITEM_ID, ITEM_DESC, QTY_ISSUED_AFTER_COUNT, TOTAL_QUANTITY


		   String hub=hD.get( "HUB" ) == null ? "&nbsp;" : hD.get( "HUB" ).toString();
		   //String bin_id=hD.get( "BIN_ID" ) == null ? "&nbsp;" : hD.get( "BIN_ID" ).toString();
		   //String cabinet_id=hD.get( "CABINET_ID" ) == null ? "&nbsp;" : hD.get( "CABINET_ID" ).toString();
		   String cabinet_name=hD.get( "CABINET_NAME" ) == null ? "&nbsp;" : hD.get( "CABINET_NAME" ).toString();
		   //String facility_id=hD.get( "FACILITY_ID" ) == null ? "&nbsp;" : hD.get( "FACILITY_ID" ).toString();
		   //String ordering_application=hD.get( "ORDERING_APPLICATION" ) == null ? "&nbsp;" : hD.get( "ORDERING_APPLICATION" ).toString();
		   String use_application=hD.get( "USE_APPLICATION" ) == null ? "&nbsp;" : hD.get( "USE_APPLICATION" ).toString();
		   //String company_id=hD.get( "COMPANY_ID" ) == null ? "&nbsp;" : hD.get( "COMPANY_ID" ).toString();
		   String receipt_id=hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString();
		   String mfg_lot=hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString();
		   String expire_date=hD.get( "EXPIRE_DATE" ) == null ? "&nbsp;" : hD.get( "EXPIRE_DATE" ).toString();
		   String count_datetime=hD.get( "COUNT_DATETIME" ) == null ? "&nbsp;" : hD.get( "COUNT_DATETIME" ).toString();
		   //String counted_by_personnel_id=hD.get( "COUNTED_BY_PERSONNEL_ID" ) == null ? "&nbsp;" : hD.get( "COUNTED_BY_PERSONNEL_ID" ).toString();
		   //String counted_by_name=hD.get( "COUNTED_BY_NAME" ) == null ? "&nbsp;" : hD.get( "COUNTED_BY_NAME" ).toString();
		   String count_quantity=hD.get( "COUNT_QUANTITY" ) == null ? "&nbsp;" : hD.get( "COUNT_QUANTITY" ).toString();
		   String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
		   String item_desc=hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString();
		   String qty_issued_after_count=hD.get( "QTY_ISSUED_AFTER_COUNT" ) == null ? "&nbsp;" : hD.get( "QTY_ISSUED_AFTER_COUNT" ).toString();
		   String total_quantity=hD.get( "TOTAL_QUANTITY" ) == null ? "&nbsp;" : hD.get( "TOTAL_QUANTITY" ).toString();
		   String qc_doc=hD.get( "QC_DOC" ) == null ? "&nbsp;" : hD.get( "QC_DOC" ).toString();
 		   String unit_cost=hD.get( "UNIT_COST" ) == null ? "&nbsp;" : hD.get( "UNIT_COST" ).toString();
		   String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "&nbsp;" : hD.get( "CAT_PART_NO" ).toString();
		   String reorder_point=hD.get( "REORDER_POINT" ) == null ? "&nbsp;" : hD.get( "REORDER_POINT" ).toString();
		   String stocking_level = hD.get("STOCKING_LEVEL")==null?"&nbsp;":hD.get("STOCKING_LEVEL").toString();
		   String qty_available_after_alloc = hD.get("qty_available_after_alloc")==null?"&nbsp;":hD.get("qty_available_after_alloc").toString();
			 String leadtimedays = hD.get("LEAD_TIME_DAYS")==null?"":hD.get("LEAD_TIME_DAYS").toString();

           String Color=" ";
           if ( i % 2 == 0 )
           {
             Color="CLASS=\"blue";
           }
           else
           {
             Color="CLASS=\"white";
           }

           out.println("<tr align=\"center\">\n");
           out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+item_id+"</td>\n");
           out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+item_desc+"</td>\n");
		   if ("2118".equalsIgnoreCase(hubnum))
		   {
			 out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + qc_doc + "</td>\n" );
		   }
		   else
		   {
			 out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + cat_part_no + "</td>\n" );
		   }

		   if (!"0".equalsIgnoreCase(qty_available_after_alloc))
		   {
			 out.println("<td "+Color+"\" width=\"5%\" height=\"38\"><A HREF=\"javascript:showinventorydetail('"+item_id+"','"+hub+"')\">"+qty_available_after_alloc+"</A></td>\n");
		   }
		   else
		   {
			 out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+qty_available_after_alloc+"</td>\n");
		   }

           //out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+ordering_application+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+use_application+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+cabinet_name+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+receipt_id+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+mfg_lot+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+expire_date+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+count_datetime+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+count_quantity+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+qty_issued_after_count+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+total_quantity+"</td>\n");
		   out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+unit_cost+"</td>\n");
		   out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + reorder_point + "</td>\n" );
			 out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + stocking_level + "</td>\n" );
			 out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + leadtimedays + "</td>\n" );
			 out.println("</tr>\n");

       }
      }

         out.println( "</table>\n" );
         out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
         out.println( "<tr>" );
         out.println( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
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
    private void buildHeader(String searchLike,String containsorlke,String searchtext,String selectedFac,String tmpwaSelect, Hashtable initialData,String buildlabels,
                                     String sortby,Hashtable hubsetdata,Vector selectedcabs,Vector showncabs,String selectedHub,String expiresfrom,String expiresto,String showonlyPositiveQty)
    {
      //StringBuffer Msg=new StringBuffer();
      out.println(radian.web.HTMLHelpObj.printHTMLHeader("Cabinet Inventory","cabinetinv.js",intcmIsApplication));
      out.println("</HEAD>  \n");

      String Search_servlet = radian.web.tcmisResourceLoader.getcanbinvurl();

      out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
      out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
      out.println("</DIV>\n");
      out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

      out.println(radian.web.HTMLHelpObj.PrintTitleBar("Cabinet Inventory\n"));
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

      out.println( "<FORM METHOD=\"POST\" NAME=\"invoicereports\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n" );
      out.println( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
      out.println( "<TR VALIGN=\"MIDDLE\">\n" );
      //Hub
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>Hub:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      out.println("<SELECT CLASS=\"HEADER\" NAME=\"hubC\" ID=\"hubC\" size=\"1\" OnChange=\"hubChanged(document.invoicereports.hubC)\">\n");

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
        out.println("<option "+preSelect+" value=\""+(String)hubID.elementAt(i)+"\">"+(String)hubID.elementAt(i)+"</option>\n");
      }
      out.println("</SELECT>\n");
      out.println("</TD>\n");

      // Cabinets
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" ROWSPAN=\"3\">\n" );
      //out.println( "<B>Cabinets:</B>&nbsp;<BR>\n" );
      out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"getbinsdd\" value=\"Get Cabinets:\" OnClick=\"getbins()\">\n");

      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" ROWSPAN=\"3\">\n" );
      out.println( "<SELECT CLASS=\"HEADER\" NAME=\"binids\" ID=\"binids\" size=\"7\" multiple>\n" );
      //out.println( "<OPTION VALUE=\"All\">All Cabinets</OPTION>\n" );
      out.println( radian.web.HTMLHelpObj.getmultipleDropDown(showncabs,selectedcabs ) );
      out.println("</SELECT>\n");
      out.println("</TD>\n");

      //Options
      out.println( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
      out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"searchwhat\" size=\"1\">\n" );
	  Hashtable searchthese=new Hashtable();
	  searchthese.put( "Item Id","ITEM_ID" );
	  searchthese.put( "Item Desc","ITEM_DESC" );
	  searchthese.put( "Part Num","CAT_PART_NO" );
	  out.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( searchthese.entrySet(),searchLike ) );
	  out.println( "</SELECT>\n" );

      /*if ( searchLike.equalsIgnoreCase( "ITEM_ID" ) )
      {
        out.println( "<option selected value=\"ITEM_ID\">Item Id</option>\n" );
        //out.println( "<option value=\"CAT_PART_NO\">Part Number</option>\n" );
      }
      else if ( searchLike.equalsIgnoreCase( "CAT_PART_NO" ) )
      {
        out.println( "<option value=\"ITEM_ID\">Item Id</option>\n" );
        //out.println( "<option selected value=\"CAT_PART_NO\">Part Number</option>\n" );
      }
      else
      {
        out.println( "<option value=\"ITEM_ID\">Item Id</option>\n" );
        //out.println( "<option selected value=\"CAT_PART_NO\">Part Number</option>\n" );
      }

      out.println( "</SELECT>\n" );*/
      out.println("</TD>\n\n");

      //Contains Is
      out.println( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
      out.println( "<SELECT CLASS=\"HEADER\" NAME=\"containsorlike\" size=\"1\">\n" );

      if ( containsorlke.equalsIgnoreCase( "Contains" ) )
      {
        out.println( "<option  selected value=\"Contains\">Contains</option>\n" );
        out.println( "<option  value=\"Is\">Is</option>\n" );
      }
      else
      {
        out.println( "<option  value=\"Contains\">Contains</option>\n" );
        out.println( "<option  selected value=\"Is\">Is</option>\n" );
      }
      out.println( "</SELECT>\n" );
      out.println( "</TD>\n\n" );

      //Search text
      out.println( "<TD HEIGHT=\"38\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\" WIDTH=\"15%\">\n" );
      out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"searchtext\" NAME=\"searchtext\" value=\"" + searchtext + "\" size=\"20\">\n" );
      out.println( "</TD>\n" );

      //Search
      out.println("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      out.println( "<INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
      out.println("</TD>\n</TR>\n");

      out.println( "<TR VALIGN=\"MIDDLE\">\n" );
      //Facility
      out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      out.println("<B>Facility:</B>\n");
      out.println("</TD>\n");
      out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      out.println("<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" ID=\"facId\" size=\"1\" OnChange=\"facilityChanged(document.invoicereports.facilityName)\">\n");
      Hashtable fh = (Hashtable)initialData.get(selectedHub);
      //System.out.println(fh);
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
        out.println("<option "+preSelect+" value=\""+(String)facID.elementAt(i)+"\">"+(String)facDesc.elementAt(i)+"</option>\n");
      }
      out.println("</SELECT>\n");
      out.println("</TD>\n");

      /*out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
      out.println( "&nbsp;\n" );
      out.println("</TD>\n");*/

      //Blank
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  out.println( "<B>Expires in</B>\n" );
      out.println( "</TD>\n" );

      //Expires In
      out.println( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"searchtext\" NAME=\"expfrom\" value=\"" + expiresfrom + "\" size=\"2\" MAXLENGTH=\"4\">\n" );
      out.println("</TD>\n\n");

      //Blank
      out.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  out.println( "&nbsp;to&nbsp;&nbsp;\n" );
	  out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"searchtext\" NAME=\"expto\" value=\"" + expiresto + "\" size=\"2\" MAXLENGTH=\"4\">\n" );
      out.println( "&nbsp;days</TD>\n" );


      //Blank
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
//    out.println( "&nbsp;\n" );
      out.println("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Create XLS\" onclick= \"return createxls(this)\" NAME=\"XlsButton\">&nbsp;\n");
      out.println( "</TD>\n" );

      out.println( "<TR VALIGN=\"MIDDLE\">\n" );
      //Work Area
      out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      out.println("<B>Work Area:</B>\n");
      out.println("</TD>\n");
      out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"WorkAreaFullName\" ID=\"WorkAreaFullName\" value=\"\">\n");
      out.println("<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\" OnChange=\"workareaChanged()\">\n");
      Hashtable h = (Hashtable)fh.get(selectedFac);
      //System.out.println(h);
      Vector application = (Vector)h.get("APPLICATION");
      Vector applicationDesc = (Vector)h.get("APPLICATION_DESC");
      for (i = 0; i < application.size(); i++) {
        preSelect = "";
        if (tmpwaSelect.equalsIgnoreCase((String)application.elementAt(i))) {
          preSelect = "selected";
        }
        out.println("<option "+preSelect+" value=\""+(String)application.elementAt(i)+"\">"+(String)applicationDesc.elementAt(i)+"</option>\n");
      }
      out.println("</SELECT>\n");
      out.println("</TD>\n");

	  //Sort By
	  out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  out.println( "<B>Sort By :</B>\n" );
	  out.println( "</TD>\n" );

	  //Options
	  out.println( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"sortby\" size=\"1\">\n" );

	  if ( sortby.equalsIgnoreCase( "CAT_PART_NO,CABINET_ID" ) )
	  {
		//out.println( "<option selected value=\"x.CAT_PART_NO,x.CABINET_ID\">Part Number</option>\n" );
		out.println( "<option value=\"CABINET_ID,ITEM_ID\">Cabinet</option>\n" );
		out.println( "<option value=\"ITEM_ID,CABINET_ID\">Item ID</option>\n" );
	  }
	  else if ( sortby.equalsIgnoreCase( "CABINET_ID,ITEM_ID" ) )
	  {
		//out.println( "<option value=\"x.CAT_PART_NO,x.CABINET_ID\">Part Number</option>\n" );
		out.println( "<option selected value=\"CABINET_ID,ITEM_ID\">Cabinet</option>\n" );
		out.println( "<option value=\"ITEM_ID,CABINET_ID\">Item ID</option>\n" );
	  }
	  else if ( sortby.equalsIgnoreCase( "ITEM_ID,CABINET_ID" ) )
	  {
		//out.println( "<option value=\"x.CAT_PART_NO,x.CABINET_ID\">Part Number</option>\n" );
		out.println( "<option value=\"CABINET_ID,ITEM_ID\">Cabinet</option>\n" );
		out.println( "<option selected value=\"ITEM_ID,CABINET_ID\">Item ID</option>\n" );
	  }
	  else
	  {
		//out.println( "<option selected value=\"x.CAT_PART_NO,x.CABINET_ID\">Part Number</option>\n" );
		out.println( "<option value=\"CABINET_ID,ITEM_ID\">Cabinet</option>\n" );
		out.println( "<option value=\"ITEM_ID,CABINET_ID\">Item ID</option>\n" );
	  }
	  out.println( "</SELECT>\n" );
	  out.println("</TD>\n\n");


    //Show Only Qunaity > 0
	  out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "<INPUT type=\"checkbox\" name=\"showonlyPositiveQty\" id=\"showonlyPositiveQty\" value=\"Yes\" " + ( showonlyPositiveQty.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\" COLSPAN=\"2\">\n" );
	  out.println( "Show Only Quantity > 0\n" );
	  out.println( "</TD>\n" );

      //New Bin
      out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      //out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"New Bin\" onclick= \"newbin(this)\" NAME=\"UpdateButton\">&nbsp;\n");
      out.println("</TD>\n");

      //Generate Cabinet Labels
      out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
      //out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Update\" onclick= \"return update(this)\" NAME=\"UpdateButton\">&nbsp;\n");
      out.println("</TD>\n");
      out.println("</TR>\n");

      out.println("</TABLE>\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");

      return;
    }

    public Vector getcabinetlist()
    {
        String query = "select CABINET_ID,CABINET_NAME from hub_cabinet_view order by CABINET_NAME";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Hashtable result = null;
        Vector ResultV = new Vector();
        try
        {
             dbrs = db.doQuery(query);
              rs=dbrs.getResultSet();
             while (rs.next())
         {
               result = new Hashtable();
               String faci  = (rs.getString("CABINET_ID")==null?"":rs.getString("CABINET_ID"));
               String facn  = (rs.getString("CABINET_NAME")==null?"":rs.getString("CABINET_NAME"));
               result.put(facn,faci);
               ResultV.addElement(result);
             }
        }
        catch (Exception e)
        {
        e.printStackTrace();
        }
        finally
        {
           if (dbrs != null)  { dbrs.close(); }
        }
        return ResultV;
     }

    private Vector getResult( String searchLike,String containsorlke,String searchtext,String facSelected,String waSelected,String sortby,String sourcehub,String cabselected,
							  String expirefrom,String expireto,String companyID)
       throws Exception
    {
      DBResultSet dbrs = null;
      ResultSet searchRs = null;
      Vector Data=new Vector();
      Hashtable DataH=new Hashtable();
      Hashtable summary=new Hashtable();
      String nodaysago="60";
      boolean falgforand =false;
	  Connection connect1;
      CallableStatement cs = null;
      int count=0;
      summary.put( "TOTAL_NUMBER",new Integer( count ) );
      Data.addElement( summary );
	  String query = "";
	  try
	  {
		connect1 = db.getConnection();
		try
		{
		  cs=connect1.prepareCall( "{call PR_CABINET_INV_COUNT_REPORT(?,?,?,?,?,?,?,?,?,?,?,?)}" );
		  cs.setString( 1,sourcehub );
		  cs.setString( 2,companyID );
		  if ( facSelected.length() > 0 && ( !"All Facilities".equalsIgnoreCase( facSelected ) ) )
		  {
			cs.setString( 3,facSelected );

		  }
		  else
		  {
			cs.setNull( 3,java.sql.Types.VARCHAR );
		  }
		  if ( !"All Work Areas".equalsIgnoreCase( waSelected ) )
		  {
			cs.setString( 4,waSelected );
		  }
		  else
		  {
			cs.setNull( 4,java.sql.Types.VARCHAR );
		  }

		  if ( ! ( cabselected.trim().length() == 0 || "'All'".equalsIgnoreCase( cabselected ) ) )
		  {
			cs.setString( 5,cabselected );
		  }
		  else
		  {
			cs.setNull( 5,java.sql.Types.VARCHAR );
		  }

		  if ( searchtext.length() > 0 )
		  {
			if ( "Contains".equalsIgnoreCase( containsorlke ) )
			{
			  cs.setString( 6,"Y" );
			}
			else
			{
			  cs.setNull( 6,java.sql.Types.VARCHAR );
			}
			cs.setString( 7,searchLike );
			cs.setString( 8,searchtext );
		  }
		  else
		  {
			cs.setNull( 6,java.sql.Types.VARCHAR );
			cs.setNull( 7,java.sql.Types.VARCHAR );
			cs.setNull( 8,java.sql.Types.VARCHAR );
		  }

		  if (expirefrom.trim().length() > 0){cs.setInt(9,Integer.parseInt(expirefrom));}else{cs.setNull(9,java.sql.Types.VARCHAR);}
		  if (expireto.trim().length() > 0){cs.setInt(10,Integer.parseInt(expireto));}else{cs.setNull(10,java.sql.Types.VARCHAR);}

		cs.setString( 11,sortby );
		cs.registerOutParameter( 12,java.sql.Types.VARCHAR );
		cs.execute();

		query=BothHelpObjs.makeBlankFromNull( cs.getString( 12 ) );
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		radian.web.emailHelpObj.senddatabaseHelpemails( "Error Calling Procedure call PR_CABINET_INV_COUNT_REPORT in Cabinet Inventory",
														"Error occured while running call PR_CABINET_INV_COUNT_REPORT \nError Msg:\n" + e.getMessage() +"" );
		throw e;
	  }
	  finally
	  {
		connect1.commit();
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

          dbrs=db.doQuery( query );
          searchRs=dbrs.getResultSet();

		  while ( searchRs.next() )
		  {
			DataH=new Hashtable();
			//HUB
			DataH.put( "HUB",searchRs.getString( "HUB" ) == null ? "" : searchRs.getString( "HUB" ) );
			//BIN_ID
			DataH.put( "BIN_ID",searchRs.getString( "BIN_ID" ) == null ? "" : searchRs.getString( "BIN_ID" ) );
			//CABINET_ID
			DataH.put( "CABINET_ID",searchRs.getString( "CABINET_ID" ) == null ? "" : searchRs.getString( "CABINET_ID" ) );
			//CABINET_NAME
			DataH.put( "CABINET_NAME",searchRs.getString( "CABINET_NAME" ) == null ? "" : searchRs.getString( "CABINET_NAME" ) );
			//FACILITY_ID
			DataH.put( "FACILITY_ID",searchRs.getString( "FACILITY_ID" ) == null ? "" : searchRs.getString( "FACILITY_ID" ) );
			//ORDERING_APPLICATION
			DataH.put( "ORDERING_APPLICATION",searchRs.getString( "ORDERING_APPLICATION" ) == null ? "" : searchRs.getString( "ORDERING_APPLICATION" ) );
			//USE_APPLICATION
			DataH.put( "USE_APPLICATION",searchRs.getString( "USE_APPLICATION" ) == null ? "" : searchRs.getString( "USE_APPLICATION" ) );
			//COMPANY_ID
			DataH.put( "COMPANY_ID",searchRs.getString( "COMPANY_ID" ) == null ? "" : searchRs.getString( "COMPANY_ID" ) );
			//RECEIPT_ID
			DataH.put( "RECEIPT_ID",searchRs.getString( "RECEIPT_ID" ) == null ? "" : searchRs.getString( "RECEIPT_ID" ) );
			//MFG_LOT
			DataH.put( "MFG_LOT",searchRs.getString( "MFG_LOT" ) == null ? "" : searchRs.getString( "MFG_LOT" ) );
			//EXPIRE_DATE
			DataH.put( "EXPIRE_DATE",searchRs.getString( "EXPIRE_DATE" ) == null ? "" : searchRs.getString( "EXPIRE_DATE" ) );
			//COUNT_DATETIME
			DataH.put( "COUNT_DATETIME",searchRs.getString( "COUNT_DATETIME" ) == null ? "" : searchRs.getString( "COUNT_DATETIME" ) );
			//COUNTED_BY_PERSONNEL_ID
			DataH.put( "COUNTED_BY_PERSONNEL_ID",searchRs.getString( "COUNTED_BY_PERSONNEL_ID" ) == null ? "" : searchRs.getString( "COUNTED_BY_PERSONNEL_ID" ) );
			//COUNTED_BY_NAME
			DataH.put( "COUNTED_BY_NAME",searchRs.getString( "COUNTED_BY_NAME" ) == null ? "" : searchRs.getString( "COUNTED_BY_NAME" ) );
			//COUNT_QUANTITY
			DataH.put( "COUNT_QUANTITY",searchRs.getString( "COUNT_QUANTITY" ) == null ? "" : searchRs.getString( "COUNT_QUANTITY" ) );
			//ITEM_ID
			DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
			//ITEM_DESC
			DataH.put( "ITEM_DESC",searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" ) );
			//QTY_ISSUED_AFTER_COUNT
			DataH.put( "QTY_ISSUED_AFTER_COUNT",searchRs.getString( "QTY_ISSUED_AFTER_COUNT" ) == null ? "" : searchRs.getString( "QTY_ISSUED_AFTER_COUNT" ) );
			//TOTAL_QUANTITY
			DataH.put( "TOTAL_QUANTITY",searchRs.getString( "TOTAL_QUANTITY" ) == null ? "" : searchRs.getString( "TOTAL_QUANTITY" ) );
			//QC_DOC
			DataH.put( "QC_DOC",searchRs.getString( "QC_DOC" ) == null ? "" : searchRs.getString( "QC_DOC" ) );
			//UNIT_COST
			DataH.put( "UNIT_COST",searchRs.getString( "UNIT_COST" ) == null ? "" : searchRs.getString( "UNIT_COST" ) );
			//CAT_PART_NO
            DataH.put( "CAT_PART_NO",searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ) );
			//REORDER_POINT
			DataH.put( "REORDER_POINT",searchRs.getString( "REORDER_POINT" ) == null ? "" : searchRs.getString( "REORDER_POINT" ) );
			//STOCKING_LEVEL
			DataH.put("STOCKING_LEVEL",searchRs.getString("STOCKING_LEVEL")==null?"":searchRs.getString("STOCKING_LEVEL"));
			//qty_available_after_alloc
			DataH.put("qty_available_after_alloc",searchRs.getString("qty_available_after_alloc")==null?"":searchRs.getString("qty_available_after_alloc"));
			//LEAD_TIME_DAYS
			DataH.put( "LEAD_TIME_DAYS",searchRs.getString( "LEAD_TIME_DAYS" ) == null ? "" : searchRs.getString( "LEAD_TIME_DAYS" ) );

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
        Data.trimToSize();
        return Data;
      }
}
