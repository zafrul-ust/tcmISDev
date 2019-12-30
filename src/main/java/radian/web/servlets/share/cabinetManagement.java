package radian.web.servlets.share;

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
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 09-09-03 - Fixing a Bug. Not include All in cabinet ID
 * 11-12-03 - Added item ID to the sort by fields
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 06-01-04 - Code Refactoring and Sorting the work area dropdown, and showing APPLICATION_DESC instead of APPLICATION
 * 08-23-04 - Giving the option to active and deactivate Bins
 */


public class cabinetManagement
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  //private PrintWriter out=null;
  private DBResultSet dbrs=null;
  private ResultSet searchRs=null;

  private Vector receiptIdstoLabel = null;
  private Vector numbersoflabels = null;
  private boolean completeSuccess = true ;
  private boolean noneToUpd = true ;
  protected boolean allowupdate;
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

  public cabinetManagement( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  public void doResult( HttpServletRequest request,
                        HttpServletResponse response )
     throws ServletException,IOException
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

    String personnelid=BothHelpObjs.makeBlankFromNull( ( String ) relabelsession.getAttribute( "PERSONNELID" ) );

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

	String showinactive=request.getParameter( "showinactive" );
	if ( showinactive == null )
	  showinactive="";
	showinactive=showinactive.trim();

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

      initialDataLoaded="";
      try
      {
        initialDataLoaded=relabelsession.getAttribute( "CABINET_LABEL_DATA_LOADED" ).toString();
      }
      catch ( Exception getdoneFlag )
      {}

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
	  if (personnelBean.getPermissionBean().hasFacilityPermission("CabinetMgmt",selHub,null))
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
        Vector cablist = new Vector();
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
          data= getResult( searchwhat,containsorlike,searchtext,facility,workarea,sortby,branchplant,cabsStringfromArray,showinactive );
          cablist = this.getcabinetlist();
        }
        catch ( Exception e1 )
        {
          e1.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printHTMLError() );
        }

        relabelsession.setAttribute("CABMGMTDATA", data );
        relabelsession.setAttribute("CABLISTMGMT", cablist );

        Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

        buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData," ",sortby,hub_list_set,selectedcabs,showncabs,selHub,out,showinactive );

        if ( 0 == count )
        {
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
        }
        else
        {
          buildDetails( data,cablist,out );
        }

        //clean up
        data=null;
      }
      else if ( User_Action.equalsIgnoreCase( "Update" ) )
      {
        Vector retrn_data= ( Vector ) relabelsession.getAttribute( "CABMGMTDATA" );
        Vector synch_data=SynchServerData( request,retrn_data );
        Vector showncabs = ( Vector ) relabelsession.getAttribute( "CABIDS_SHOWN" );
        Vector cabmgmgtlist= ( Vector ) relabelsession.getAttribute( "CABLISTMGMT" );

        relabelsession.setAttribute("CABMGMTDATA", synch_data );

		Hashtable updateresults = UpdateDetails(synch_data, personnelid);
		Boolean resultfromup = (Boolean)updateresults.get("RESULT");
		Vector errordata = (Vector)updateresults.get("ERRORDATA");
		relabelsession.setAttribute("CABMGMTDATA", errordata );
		boolean resulttotest =  resultfromup.booleanValue();

        Hashtable sum= ( Hashtable ) ( synch_data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

        buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData," ",sortby,hub_list_set,selectedcabs,showncabs,selHub,out,showinactive );

		if ( false == resulttotest )
		{
		  if ( true == noneToUpd )
		  {
			out.println( radian.web.HTMLHelpObj.printMessageinTable( "No Changes were made to Update." ) );
		  }
		  else
		  {
			out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occurred.<BR>Please Check Data and try Again." ) );
		  }
		}
		else
		{
		  if ( true == completeSuccess )
		  {
			out.println( radian.web.HTMLHelpObj.printMessageinTable( "All Your Selections Were Successfully Updated" ) );

		  }
		  else
		  {
			out.println( radian.web.HTMLHelpObj.printMessageinTable( "Some of Your Selections Were Not Successfully Updated" ) );
		  }
		}

        if ( 0 == count )
        {
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
        }
        else
        {
          buildDetails( synch_data,cabmgmgtlist,out );
        }

      }
      else if ( User_Action.equalsIgnoreCase( "generatexls" ) )
      {
        Vector retrndata= ( Vector ) relabelsession.getAttribute( "CABMGMTDATA" );
        out.println( buildxlsDetails( retrndata,"createxls",personnelid ) );
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
          buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData,"",sortby,hub_list_set,selectedcabs,showncabs,selHub,out,showinactive );
          out.println( radian.web.HTMLHelpObj.printHTMLNoFacilities() );
          hub_list_set=null;
          out.close();
        }
        else
        {
          buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData,"",sortby,hub_list_set,selectedcabs,showncabs,selHub,out,showinactive );
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
      out.close();
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

     try
     {
       dbrs=db.doQuery( query );
       rs=dbrs.getResultSet();

       String lastHub="";

       while ( rs.next() )
       {
         String tmpFacID=rs.getString( "facility_id" );
         String tmpHub=rs.getString( "PREFERRED_WAREHOUSE" );

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
    }
*/
     private StringBuffer buildxlsDetails( Vector data,String SubuserAction,String personnelID ) throws Exception
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
         int vsize=data.size();

         Hashtable hDNext=new Hashtable();
         pw.print( "Item,Description,Container Size,Catalog Id,Cabinet,Bin,Part Number,MSDS Number,Reorder Point,Stocking Level,Lead Time in Days\n" );

         int i=0;
         for ( int loop=0; loop < total; loop++ )
         {
           i++;
           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

           //String source_hub=hD.get( "SOURCE_HUB" ) == null ? "&nbsp;" : hD.get( "SOURCE_HUB" ).toString();
           //String company_id=hD.get( "COMPANY_ID" ) == null ? "&nbsp;" : hD.get( "COMPANY_ID" ).toString();
           //String facility_id=hD.get( "FACILITY_ID" ) == null ? "&nbsp;" : hD.get( "FACILITY_ID" ).toString();
           String catalog_id=hD.get( "CATALOG_ID" ) == null ? "&nbsp;" : hD.get( "CATALOG_ID" ).toString();
           String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "&nbsp;" : hD.get( "CAT_PART_NO" ).toString();
           String materialidno=hD.get( "MATERIAL_ID_STRING" ) == null ? "&nbsp;" : hD.get( "MATERIAL_ID_STRING" ).toString();
           String reorder_point=hD.get( "REORDER_POINT" ) == null ? "&nbsp;" : hD.get( "REORDER_POINT" ).toString();
           String stocking_level=hD.get( "STOCKING_LEVEL" ) == null ? "&nbsp;" : hD.get( "STOCKING_LEVEL" ).toString();

           String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
           String packaging=hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString();
           String material_desc=hD.get( "MATERIAL_DESC" ) == null ? "&nbsp;" : hD.get( "MATERIAL_DESC" ).toString();
           String mfg_desc=hD.get( "MFG_DESC" ) == null ? "&nbsp;" : hD.get( "MFG_DESC" ).toString();
           String application = hD.get("APPLICATION")==null?"&nbsp;":hD.get("APPLICATION").toString();
           //String cabinetid =hD.get( "CABINET_ID" ) == null ? "&nbsp;" : hD.get( "CABINET_ID" ).toString();
           //String bin_id = hD.get("BIN_ID")==null?"&nbsp;":hD.get("BIN_ID").toString();
           String bin_name=hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString();
           String cabinet_name=hD.get( "CABINET_NAME" ) == null ? "&nbsp;" : hD.get( "CABINET_NAME" ).toString();
					 String leadtimedays = hD.get("LEAD_TIME_DAYS")==null?"":hD.get("LEAD_TIME_DAYS").toString();

           pw.print( "\"" + item_id + "\"," );
           pw.print( "\"" + material_desc + ".." + mfg_desc + "\"," );
           pw.print( "\"" + packaging + "\"," );
           pw.print( "\"" + catalog_id + "\"," );
           pw.print( "\"" + cabinet_name + "\"," );
           pw.print( "\"" + bin_name + "\"," );
           pw.print( "\"" + cat_part_no + "\"," );
           pw.print( "\"" + materialidno + "\"," );
           pw.print( "\"" + reorder_point + "\"," );
           pw.print( "\"" + stocking_level + "\"," );
					 pw.print( "\"" + leadtimedays + "\"\n" );

         }
		 pw.close();
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         //out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Cabinet Management" ) );
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
     private void buildDetails( Vector data,Vector cabslist,PrintWriter cabout )
     {
       //StringBuffer Msg=new StringBuffer();

       try
       {
         Hashtable summary=new Hashtable();
         summary= ( Hashtable ) data.elementAt( 0 );
         int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
         //System.out.println( "Total Number : " + total );
         int vsize=data.size();
         //System.out.println( "Vector Size  : " + vsize );

         //start table #3
         cabout.println( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n" );

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
             cabout.println( "<tr align=\"center\">\n" );
             cabout.println( "<TH width=\"5%\"  height=\"38\">Item</TH>\n" );
             cabout.println( "<TH width=\"5%\"  height=\"38\">Description</TH>\n" );
             cabout.println( "<TH width=\"5%\"  height=\"38\">Container Size</TH>\n" );
             cabout.println( "<TH width=\"5%\"  height=\"38\">Catalog Id</TH>\n" );
             //cabout.println( "<TH width=\"5%\"  height=\"38\">Facility</TH>\n" );
             //cabout.println( "<TH width=\"5%\"  height=\"38\">Work Area</TH>\n" );
             cabout.println( "<TH width=\"5%\"  height=\"38\">Cabinet</TH>\n" );
             cabout.println( "<TH width=\"5%\"  height=\"38\">Bin</TH>\n" );
             cabout.println( "<TH width=\"5%\"  height=\"38\">Part Number</TH>\n" );
             cabout.println( "<TH width=\"5%\"  height=\"38\">MSDS Number</TH>\n" );
             cabout.println( "<TH width=\"5%\"  height=\"38\">Reorder Point</TH>\n" );
             cabout.println( "<TH width=\"5%\"  height=\"38\">Stocking Level</TH>\n" );
						 cabout.println( "<TH width=\"5%\"  height=\"38\">Lead Time in Days</TH>\n" );
						 cabout.println( "<TH width=\"2%\"  height=\"38\">Status</TH>\n" );
             cabout.println( "</tr>\n" );
           }

           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

           String source_hub = hD.get("SOURCE_HUB")==null?"&nbsp;":hD.get("SOURCE_HUB").toString();
           String company_id=hD.get( "COMPANY_ID" ) == null ? "&nbsp;" : hD.get( "COMPANY_ID" ).toString();
           String facility_id=hD.get( "FACILITY_ID" ) == null ? "&nbsp;" : hD.get( "FACILITY_ID" ).toString();
           String catalog_id=hD.get( "CATALOG_ID" ) == null ? "&nbsp;" : hD.get( "CATALOG_ID" ).toString();
           String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "&nbsp;" : hD.get( "CAT_PART_NO" ).toString();
           String materialidno=hD.get( "MATERIAL_ID_STRING" ) == null ? "&nbsp;" : hD.get( "MATERIAL_ID_STRING" ).toString();
           String reorder_point=hD.get( "REORDER_POINT" ) == null ? "&nbsp;" : hD.get( "REORDER_POINT" ).toString();
           String stocking_level = hD.get("STOCKING_LEVEL")==null?"&nbsp;":hD.get("STOCKING_LEVEL").toString();

           String item_id = hD.get("ITEM_ID")==null?"&nbsp;":hD.get("ITEM_ID").toString();
           String packaging = hD.get("PACKAGING")==null?"&nbsp;":hD.get("PACKAGING").toString();
           String material_desc = hD.get("MATERIAL_DESC")==null?"&nbsp;":hD.get("MATERIAL_DESC").toString();
           String mfg_desc = hD.get("MFG_DESC")==null?"&nbsp;":hD.get("MFG_DESC").toString();

           String application = hD.get("APPLICATION")==null?"&nbsp;":hD.get("APPLICATION").toString();
           String cabinetid =hD.get( "CABINET_ID" ) == null ? "&nbsp;" : hD.get( "CABINET_ID" ).toString();
           String bin_id = hD.get("BIN_ID")==null?"&nbsp;":hD.get("BIN_ID").toString();
           String bin_name=hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString();
           String cabinet_name=hD.get( "CABINET_NAME" ) == null ? "&nbsp;" : hD.get( "CABINET_NAME" ).toString();
           String useapplication = hD.get("USE_APPLICATION")==null?"&nbsp;":hD.get("USE_APPLICATION").toString();
					 String status = hD.get("STATUS")==null?"":hD.get("STATUS").toString();
					 String updatestatus = hD.get("UPDATE_CABINET")==null?"":hD.get("UPDATE_CABINET").toString();
					 String leadtimedays = hD.get("LEAD_TIME_DAYS")==null?"":hD.get("LEAD_TIME_DAYS").toString();

           String facilitydesc = "";
           String partdesc = "";

           String Color=" ";
           if ( i % 2 == 0 )
           {
             Color="CLASS=\"blue";
           }
           else
           {
             Color="CLASS=\"white";
           }

		  String chkbox_value="no";
		  String checkednon="";
		  if ("I".equalsIgnoreCase(status))
		  {
			Color="CLASS=\"black";
		  }
		  else if ("A".equalsIgnoreCase(status))
		  {
			checkednon = "checked";
			chkbox_value="yes";
		  }

		  if ( "FAILED".equalsIgnoreCase( updatestatus ) )
		  {
			Color="CLASS=\"error";
		  }

           cabout.println("<tr "+Color+"\" ID=\"displayrow"+i+"\" align=\"center\">\n");
           //if (this.getupdateStatus())
           {
             cabout.println("<td width=\"5%\" height=\"38\"><A HREF=\"javascript:changeminmaxcab('"+item_id+"','"+cabinetid+"','"+bin_id+"','"+cabinet_name+"','"+useapplication+"')\">"+item_id+"</A></td>\n");
           }
           /*else
           {
              cabout.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + ( item_id ) + "</td>\n" );
           }*/

           cabout.println("<td width=\"5%\" height=\"38\">"+material_desc+".."+mfg_desc+"</td>\n");
           cabout.println("<td width=\"5%\" height=\"38\">"+packaging+"</td>\n");
           cabout.println("<td width=\"5%\" height=\"38\">"+catalog_id+"</td>\n");
           //cabout.println("<td width=\"5%\" height=\"38\">"+(facility_id)+"</td>\n");
           //cabout.println("<td width=\"5%\" height=\"38\">"+(application)+"</td>\n");
           cabout.println("<td width=\"5%\" height=\"38\">"+cabinet_name+"</td>\n");
           cabout.println("<td width=\"5%\" height=\"38\">"+bin_name+"</td>\n");
           cabout.println("<td width=\"5%\" height=\"38\">"+cat_part_no+"</td>\n");
           cabout.println("<td width=\"5%\" height=\"38\">"+materialidno+"</td>\n");
           cabout.println( "<td width=\"5%\" height=\"38\">" + reorder_point + "</td>\n" );
           cabout.println( "<td width=\"5%\" height=\"38\">" + stocking_level + "</td>\n" );
					 cabout.println( "<td width=\"5%\" height=\"38\">" + leadtimedays + "</td>\n" );

		   if ( this.getupdateStatus() )
		   {
			 cabout.println("<td width=\"2%\"><INPUT TYPE=\"checkbox\" value=\""+(chkbox_value)+"\"  onClick= \"checkactivestatus('"+i+"')\" " + checkednon+ "  NAME=\"row_chk"+i+"\"></td>\n");
		   }
		   else
		   {
			 cabout.println("<td width=\"2%\">&nbsp;</td>\n" );
		   }
		   cabout.println("<input type=\"hidden\" name=\"bin_id"+i+"\" value=\""+bin_id+"\">\n");
		   cabout.println("<input type=\"hidden\" name=\"status"+i+"\" value=\""+status+"\">\n");

           cabout.println("</tr>\n");
         }

         cabout.println( "</table>\n" );
         cabout.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
         cabout.println( "<tr>" );
         cabout.println( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
         cabout.println( "</TD></tr>" );
         cabout.println( "</table>\n" );

         cabout.println( "</form>\n" );
         cabout.println( "</body></html>\n" );
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         cabout.println( radian.web.HTMLHelpObj.printError( "Logistics",intcmIsApplication ) );
       }

       return;
     }


    //Build HTML Header
    private void buildHeader(String searchLike,String containsorlke,String searchtext,String selectedFac,String tmpwaSelect, Hashtable initialData,String buildlabels,
                                     String sortby,Hashtable hubsetdata,Vector selectedcabs,Vector showncabs,String selectedHub,PrintWriter cabout,String showinactive)
    {
      //StringBuffer Msg=new StringBuffer();
      cabout.print(radian.web.HTMLHelpObj.printHTMLHeader("Cabinet Management","cabinetmgmt.js",intcmIsApplication));
      cabout.print("</HEAD>  \n");

      String Search_servlet = radian.web.tcmisResourceLoader.getcabinetmgmturl();

      cabout.print("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
      cabout.print("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
      cabout.print("</DIV>\n");
      cabout.print("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

      cabout.print(radian.web.HTMLHelpObj.PrintTitleBar("Cabinet Management\n"));
	  if (initialData == null)
	  {
		cabout.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
		return;
	  }

	  Vector hubID = (Vector)initialData.get("HUB_ID");
	  if (hubID.size() == 0)
	  {
		cabout.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>There are no Cabinets in the hubs you have access to.<BR><BR>Contact tech support for more information."));
		return;
	  }

	  int i = 0;
	  if (selectedHub.trim().length() == 0 )
	  {
		selectedHub= ( String ) hubID.firstElement(); //select the first facility if none were selected
	  }

	  cabout.print("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
	  cabout.print(radian.web.HTMLHelpObj.createComboBoxData(initialData));
	  cabout.print("// -->\n </SCRIPT>\n");

      cabout.print( "<FORM METHOD=\"POST\" NAME=\"invoicereports\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n" );
      cabout.print( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
      cabout.print( "<TR VALIGN=\"MIDDLE\">\n" );
      //Hub
      cabout.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      cabout.print( "<B>Hub:</B>&nbsp;\n" );
      cabout.print( "</TD>\n" );
      cabout.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      cabout.print("<SELECT CLASS=\"HEADER\" NAME=\"hubC\" ID=\"hubC\" size=\"1\" OnChange=\"hubChanged(document.invoicereports.hubC)\">\n");

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
        cabout.print("<option "+preSelect+" value=\""+(String)hubID.elementAt(i)+"\">"+(String)hubID.elementAt(i)+"</option>\n");
      }
      cabout.print("</SELECT>\n");
      cabout.print("</TD>\n");

      // Cabinets
      cabout.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" ROWSPAN=\"3\">\n" );
      //cabout.print( "<B>Cabinets:</B>&nbsp;<BR>\n" );
      cabout.print("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"getbinsdd\" value=\"Get Cabinets:\" OnClick=\"getbins()\">\n");

      cabout.print( "</TD>\n" );
      cabout.print( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" ROWSPAN=\"3\">\n" );
      cabout.print( "<SELECT CLASS=\"HEADER\" NAME=\"binids\" ID=\"binids\" size=\"7\" multiple>\n" );
      //cabout.print( "<OPTION VALUE=\"All\">All Cabinets</OPTION>\n" );
      cabout.print( radian.web.HTMLHelpObj.getmultipleDropDown(showncabs,selectedcabs ) );
      cabout.print("</SELECT>\n");
      cabout.print("</TD>\n");

      //Options
      cabout.print( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
      cabout.print( "<SELECT CLASS=\"HEADER\"  NAME=\"searchwhat\" size=\"1\">\n" );
      if ( searchLike.equalsIgnoreCase( "ITEM_ID" ) )
      {
        cabout.print( "<option selected value=\"ITEM_ID\">Item Id</option>\n" );
        cabout.print( "<option value=\"CAT_PART_NO\">Part Number</option>\n" );
      }
      else if ( searchLike.equalsIgnoreCase( "CAT_PART_NO" ) )
      {
        cabout.print( "<option value=\"ITEM_ID\">Item Id</option>\n" );
        cabout.print( "<option selected value=\"CAT_PART_NO\">Part Number</option>\n" );
      }
      else
      {
        cabout.print( "<option value=\"ITEM_ID\">Item Id</option>\n" );
        cabout.print( "<option selected value=\"CAT_PART_NO\">Part Number</option>\n" );
      }

      cabout.print( "</SELECT>\n" );
      cabout.print("</TD>\n\n");

      //Contains Is
      cabout.print( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
      cabout.print( "<SELECT CLASS=\"HEADER\" NAME=\"containsorlike\" size=\"1\">\n" );

      if ( searchLike.equalsIgnoreCase( "Is" ) )
      {
        cabout.print( "<option  value=\"Contains\">Contains</option>\n" );
        cabout.print( "<option  selected value=\"Is\">Is</option>\n" );
      }
      else
      {
        cabout.print( "<option  selected value=\"Contains\">Contains</option>\n" );
        cabout.print( "<option  value=\"Is\">Is</option>\n" );
      }
      cabout.print( "</SELECT>\n" );
      cabout.print( "</TD>\n\n" );

      //Search text
      cabout.print( "<TD HEIGHT=\"38\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\" WIDTH=\"15%\">\n" );
      cabout.print( "<INPUT CLASS=\"HEADER\" TYPE=\"searchtext\" NAME=\"searchtext\" value=\"" + searchtext + "\" size=\"20\">\n" );
      cabout.print( "</TD>\n" );

      //Search
      cabout.print("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      cabout.print( "<INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
      cabout.print("</TD>\n</TR>\n");

      cabout.print( "<TR VALIGN=\"MIDDLE\">\n" );
      //Facility
      cabout.print("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      cabout.print("<B>Facility:</B>\n");
      cabout.print("</TD>\n");
      cabout.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      cabout.print("<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" ID=\"facId\" size=\"1\" OnChange=\"facilityChanged(document.invoicereports.facilityName)\">\n");
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
        cabout.print("<option "+preSelect+" value=\""+(String)facID.elementAt(i)+"\">"+(String)facDesc.elementAt(i)+"</option>\n");
      }
      cabout.print("</SELECT>\n");
      cabout.print("</TD>\n");

	  //Blank
	  cabout.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  cabout.print( "&nbsp;\n" );
	  cabout.print( "</TD>\n" );

      //Blank
      cabout.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
      cabout.print( "<B>Sort By :</B>\n" );
      cabout.print( "</TD>\n" );

      //Options
      cabout.print( "<TD HEIGHT=\"38\"WIDTH=\"15%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
      cabout.print( "<SELECT CLASS=\"HEADER\"  NAME=\"sortby\" size=\"1\">\n" );

      if ( sortby.equalsIgnoreCase( "x.CAT_PART_NO,x.CABINET_ID" ) )
      {
        cabout.print( "<option selected value=\"x.CAT_PART_NO,x.CABINET_ID\">Part Number</option>\n" );
        cabout.print( "<option value=\"x.CABINET_ID,x.CAT_PART_NO\">Cabinet</option>\n" );
		cabout.print( "<option value=\"x.ITEM_ID,x.CABINET_ID\">Item ID</option>\n" );
      }
      else if ( sortby.equalsIgnoreCase( "x.CABINET_ID,x.CAT_PART_NO" ) )
      {
        cabout.print( "<option value=\"x.CAT_PART_NO,x.CABINET_ID\">Part Number</option>\n" );
        cabout.print( "<option selected value=\"x.CABINET_ID,x.CAT_PART_NO\">Cabinet</option>\n" );
		cabout.print( "<option value=\"x.ITEM_ID,x.CABINET_ID\">Item ID</option>\n" );
      }
	  else if ( sortby.equalsIgnoreCase( "x.ITEM_ID,x.CABINET_ID" ) )
	  {
		cabout.print( "<option value=\"x.CAT_PART_NO,x.CABINET_ID\">Part Number</option>\n" );
		cabout.print( "<option value=\"x.CABINET_ID,x.CAT_PART_NO\">Cabinet</option>\n" );
		cabout.print( "<option selected value=\"x.ITEM_ID,x.CABINET_ID\">Item ID</option>\n" );
	  }
      else
      {
        cabout.print( "<option selected value=\"x.CAT_PART_NO,x.CABINET_ID\">Part Number</option>\n" );
        cabout.print( "<option value=\"x.CABINET_ID,x.CAT_PART_NO\">Cabinet</option>\n" );
		cabout.print( "<option value=\"x.ITEM_ID,x.CABINET_ID\">Item ID</option>\n" );
      }
      cabout.print( "</SELECT>\n" );
      cabout.print("</TD>\n\n");

	  cabout.print("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
	  cabout.print( "<INPUT TYPE=\"submit\" VALUE=\"Update\" onclick= \"return update(this)\" NAME=\"UpdateButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
      cabout.print("</TD>\n");

	  cabout.print( "</TR>\n" );

      cabout.print( "<TR VALIGN=\"MIDDLE\">\n" );
      //Work Area
      cabout.print("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      cabout.print("<B>Work Area:</B>\n");
      cabout.print("</TD>\n");
      cabout.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      //cabout.print("<INPUT TYPE=\"hidden\" NAME=\"WorkAreaFullName\" ID=\"WorkAreaFullName\" value=\"\">\n");
      cabout.print("<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\" OnChange=\"workareaChanged()\">\n");
      Hashtable h = (Hashtable)fh.get(selectedFac);
      //System.out.println(h);
      Vector application = (Vector)h.get("APPLICATION");
      Vector applicationDesc = (Vector)h.get("APPLICATION_DESC");
      for (i = 0; i < application.size(); i++) {
        preSelect = "";
        if (tmpwaSelect.equalsIgnoreCase((String)application.elementAt(i))) {
          preSelect = "selected";
        }
        cabout.print("<option "+preSelect+" value=\""+(String)application.elementAt(i)+"\">"+(String)applicationDesc.elementAt(i)+"</option>\n");
      }
      cabout.print("</SELECT>\n");
      cabout.print("</TD>\n");


      cabout.print("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	  cabout.println( "<INPUT type=\"checkbox\" name=\"showinactive\" value=\"Yes\" " + ( showinactive.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
      cabout.print("</TD>\n");

      cabout.print("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      cabout.print("Show Inactive Bins</TD>\n");

      //New Bin
      cabout.print("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
	  cabout.print("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"New Bin\" onclick= \"newbin(this)\" NAME=\"newBin\">&nbsp;\n");
      cabout.print("</TD>\n");


	  cabout.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
	  cabout.print("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Create XLS\" onclick= \"return createxls(this)\" NAME=\"XlsButton\">&nbsp;\n");
	  cabout.print( "</TD>\n" );

      cabout.print("</TR>\n");
      cabout.print("</TABLE>\n");
      cabout.print("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");

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

    private Vector getResult( String searchLike,String containsorlke,String searchtext,String facSelected,String waSelected,String sortby,String sourcehub,
							  String cabselected,String showinactive ) throws Exception
    {
      Vector Data=new Vector();
      Hashtable DataH=new Hashtable();
      Hashtable summary=new Hashtable();
      String nodaysago="60";
      boolean falgforand =false;

      int count=0;
      summary.put( "TOTAL_NUMBER",new Integer( count ) );
      Data.addElement( summary );

      try
      {
        String query="select distinct x.* from cabinet_part_level_view x ";
        query += "where x.source_hub = "+sourcehub+"  ";
        //query += "and x.facility_id = '"+facSelected+"'  ";
        falgforand = true;

	    if ("Yes".equalsIgnoreCase( showinactive ))
		{
		  //query+="and x.STATUS <> 'A' ";
	    }
		else
		{
		  query+="and x.STATUS = 'A' ";
	    }

		if ( facSelected.length() > 0 && (!"All Facilities".equalsIgnoreCase(facSelected)) )
		{
		  query+="and x.FACILITY_ID='" + facSelected + "'";
		  falgforand = true;
		}

        if ( searchtext.length() > 0 )
        {
          query+="and lower(x." + searchLike + ")";
          if ( "Contains".equalsIgnoreCase( containsorlke ) )
          {
            query+=" like lower('%" + searchtext + "%') ";
          }
          else
          {
            query+=" = lower('" + searchtext + "') ";
          }
          falgforand = true;
        }

        if ( "All Work Areas".equalsIgnoreCase( waSelected ) )
        {

        }
        else
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

        if (sortby.length() > 0)
        {
          query+=" order by " + sortby + "";
        }

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
            //SOURCE_HUB
            DataH.put( "SOURCE_HUB",searchRs.getString( "SOURCE_HUB" ) == null ? "" : searchRs.getString( "SOURCE_HUB" ) );
            //COMPANY_ID
            DataH.put( "COMPANY_ID",searchRs.getString( "COMPANY_ID" ) == null ? "" : searchRs.getString( "COMPANY_ID" ) );
            //FACILITY_ID
            DataH.put( "FACILITY_ID",searchRs.getString( "FACILITY_ID" ) == null ? "" : searchRs.getString( "FACILITY_ID" ) );
            //APPLICATION
            DataH.put( "APPLICATION",searchRs.getString( "APPLICATION" ) == null ? "" : searchRs.getString( "APPLICATION" ) );
            //CABINET_ID
            DataH.put( "CABINET_ID",searchRs.getString( "CABINET_ID" ) == null ? "" : searchRs.getString( "CABINET_ID" ) );
            //CATALOG_ID
            DataH.put( "CATALOG_ID",searchRs.getString( "CATALOG_ID" ) == null ? "" : searchRs.getString( "CATALOG_ID" ) );
            //CAT_PART_NO
            DataH.put( "CAT_PART_NO",searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ) );
            //PART_GROUP_NO
            DataH.put( "PART_GROUP_NO",searchRs.getString( "PART_GROUP_NO" ) == null ? "" : searchRs.getString( "PART_GROUP_NO" ) );
            //REORDER_POINT
            DataH.put( "REORDER_POINT",searchRs.getString( "REORDER_POINT" ) == null ? "" : searchRs.getString( "REORDER_POINT" ) );
            //STOCKING_LEVEL
            DataH.put("STOCKING_LEVEL",searchRs.getString("STOCKING_LEVEL")==null?"":searchRs.getString("STOCKING_LEVEL"));
            //ITEM_ID
            DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
            //PACKAGING
            DataH.put( "PACKAGING",searchRs.getString( "PACKAGING" ) == null ? "" : searchRs.getString( "PACKAGING" ) );
            //MATERIAL_DESC
            DataH.put( "MATERIAL_DESC",searchRs.getString( "MATERIAL_DESC" ) == null ? "" : searchRs.getString( "MATERIAL_DESC" ) );
            //MFG_DESC
            DataH.put("MFG_DESC",searchRs.getString("MFG_DESC")==null?"":searchRs.getString("MFG_DESC"));
            //MATERIAL_ID_STRING
            DataH.put("MATERIAL_ID_STRING",searchRs.getString("MATERIAL_ID_STRING")==null?"":searchRs.getString("MATERIAL_ID_STRING"));
            //BIN_ID
            DataH.put( "BIN_ID",searchRs.getString( "BIN_ID" ) == null ? "" : searchRs.getString( "BIN_ID" ) );
            //BIN_NAME
            DataH.put( "BIN_NAME",searchRs.getString( "BIN_NAME" ) == null ? "" : searchRs.getString( "BIN_NAME" ) );
            //CABINET_NAME
            DataH.put( "CABINET_NAME",searchRs.getString( "CABINET_NAME" ) == null ? "" : searchRs.getString( "CABINET_NAME" ) );
            //USE_APPLICATION
            DataH.put( "USE_APPLICATION",searchRs.getString( "USE_APPLICATION" ) == null ? "" : searchRs.getString( "USE_APPLICATION" ) );
						//STATUS
						DataH.put( "STATUS",searchRs.getString( "STATUS" ) == null ? "" : searchRs.getString( "STATUS" ) );
						//LEAD_TIME_DAYS
						DataH.put( "LEAD_TIME_DAYS",searchRs.getString( "LEAD_TIME_DAYS" ) == null ? "" : searchRs.getString( "LEAD_TIME_DAYS" ) );

            DataH.put("UPDATE_CABINET","");
            //DataH.put("UPDATE_LEVELS","");

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

		    String bin_id="";
            bin_id=request.getParameter( ( "bin_id" + i ) );
            if ( bin_id == null )
              bin_id="";

            String storedbin_id= ( hD.get( "BIN_ID" ) == null ? " " : hD.get( "BIN_ID" ).toString() );
            if ( storedbin_id.equalsIgnoreCase( bin_id ) )
            {
			  String status="";
			  status=request.getParameter( ( "status" + i ) );
			  if ( status == null )
				status="";

			  String storedstatus= ( hD.get( "STATUS" ) == null ? " " : hD.get( "STATUS" ).toString() );
			  if ( !storedstatus.equalsIgnoreCase( status ) )
			  {
				hD.remove( "UPDATE_CABINET" );
				hD.put( "UPDATE_CABINET","Yes" );

				hD.remove( "STATUS" );
				hD.put( "STATUS",status );
			  }
            }

            /*String reorderpt="";
            reorderpt=request.getParameter( ( "reorderpt" + i ) );
            if ( reorderpt == null )
              reorderpt="";

            String reopnth= ( hD.get( "REORDER_POINT" ) == null ? " " : hD.get( "REORDER_POINT" ).toString() );
            if ( !cabinetIdh.equalsIgnoreCase( cabid ) )
            {
              hD.remove("UPDATE_LEVELS");
              hD.put("UPDATE_LEVELS","Yes");

              hD.remove("REORDER_POINT");
              hD.put("REORDER_POINT",reorderpt);
            }

            String stocklevel="";
            stocklevel=request.getParameter( ( "stocklevel" + i ) );
            if ( stocklevel == null )
              stocklevel="";

            String stoclevlh= ( hD.get( "STOCKING_LEVEL" ) == null ? " " : hD.get( "STOCKING_LEVEL" ).toString() );
            if ( !cabinetIdh.equalsIgnoreCase( cabid ) )
            {
             hD.remove("UPDATE_LEVELS");
             hD.put("UPDATE_LEVELS","Yes");

             hD.remove("STOCKING_LEVEL");
             hD.put("STOCKING_LEVEL",stoclevlh);
            }*/

            new_data.addElement( hD );
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
          //out.println( radian.web.HTMLHelpObj.printError( "Inventory" ) );
        }

        return new_data;
      }

	  private boolean updatestatus( String bin_id,String status,String personnel_id )
	  {
		String updatecabpartitembin="update cabinet_part_item_bin set STATUS = '" + status + "' where bin_id = " + bin_id + " ";
		boolean result=false;
		try
		{
		  db.doUpdate( updatecabpartitembin );
		  result = true;
		}
		catch ( Exception ex )
		{
		  ex.printStackTrace();
		}
		return result;
	  }

	  private Hashtable UpdateDetails( Vector data,String logonid )
	  {
		boolean result=false;
		Hashtable updateresult=new Hashtable();
		Vector errordata=new Vector();
		int count=0;
		try
		{
		  Hashtable summary=new Hashtable();
		  summary= ( Hashtable ) data.elementAt( 0 );
		  errordata.addElement( summary );
		  int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
		  int vsize=data.size();
		  int linecheckcount=0;
		  boolean one_success=false;
		  for ( int i=1; i < total + 1; i++ )
		  {
			Hashtable hD=new Hashtable();
			hD= ( Hashtable ) data.elementAt( i );
			String Line_Check="";
			Line_Check= ( hD.get( "UPDATE_CABINET" ) == null ? "&nbsp;" : hD.get( "UPDATE_CABINET" ).toString() );
			String bin_id = hD.get("BIN_ID")==null?"&nbsp;":hD.get("BIN_ID").toString();
			String status = hD.get("STATUS")==null?"":hD.get("STATUS").toString();

			if ( Line_Check.equalsIgnoreCase( "yes" ) )
			{
			  noneToUpd=false;
			  linecheckcount++;

			  Hashtable ResultH=new Hashtable();
			  boolean line_result=false;
			  line_result = updatestatus( bin_id,status, logonid );

			  if ( false == line_result )
			  {
				completeSuccess=false;

				hD.remove( "UPDATE_CABINET" );
				hD.put( "UPDATE_CABINET","FAILED" );
				errordata.addElement( hD );
			  }
			  else
			  {
				errordata.addElement( hD );
		      }
			  if ( true == line_result )
			  {
				one_success=true;
			  }
			  ResultH=null;
			}
			else
			{
			  errordata.addElement( hD );
		    }
		  } //end of for

		  if ( linecheckcount == 1 )
		  {
			result=true;
		  }
		  if ( true == one_success )
		  {
			result=true;
		  }
		}
		catch ( Exception e )
		{
		  result=false;
		  e.printStackTrace();
		}
		updateresult.put( "RESULT",new Boolean( result ) );
		updateresult.put( "ERRORDATA",errordata );
		return updateresult;
	  }
}
