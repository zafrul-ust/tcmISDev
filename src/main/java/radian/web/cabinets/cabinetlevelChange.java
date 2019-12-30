package radian.web.cabinets;

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
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.dbObjs.UserGroup;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 09-09-03 - Fixing a Bug. Not include All in cabinet ID
 * 06-01-04 - Code Refactoring and Sorting the work area dropdown, and showing APPLICATION_DESC instead of APPLICATION
 */


public class cabinetlevelChange
{
  private ServerResourceBundle bundle=null;
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

  public void setupdateStatus( boolean id )
  {
    this.allowupdate=id;
  }

  private boolean getupdateStatus()
     throws Exception
  {
    return this.allowupdate;
  }

  public cabinetlevelChange( ServerResourceBundle b,TcmISDBModel d )
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

    String personnelid=BothHelpObjs.makeBlankFromNull( ( String ) relabelsession.getAttribute( "PERSONNELID" ) );
		PersonnelBean personnelBean = (PersonnelBean) relabelsession.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

    String User_Action="";
    String facility="";
    String workarea="";

    User_Action=request.getParameter( "UserAction" );
    if ( User_Action == null )
      User_Action="New";
    User_Action = User_Action.trim();

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

    String itemid =request.getParameter( "item_id" );
    if ( itemid == null )
      itemid="";
    itemid = itemid.trim();

    String cabname =request.getParameter( "cabname" );
    if ( cabname == null )
      cabname="";
    cabname = cabname.trim();

    String cabid =request.getParameter( "cabid" );
    if ( cabid == null )
      cabid="";
    cabid = cabid.trim();

    //System.out.println( "User_Action   "+User_Action+"  User itemid is " + itemid );

    try
    {
      Hashtable hub_list_set  = (Hashtable)relabelsession.getAttribute("HUB_PREF_LIST");
      Hashtable hubsetdetails = ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
      String branchplant = "";
      for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
      {
        String branch_plant= ( String ) ( e.nextElement() );
        String hub_name= ( String ) ( hubsetdetails.get( branch_plant ) );
        if ( selHub.equalsIgnoreCase( hub_name ) )
        {
          branchplant=branch_plant;
          break;
        }
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

      if ( User_Action.equalsIgnoreCase( "showlevels" ) )
      {
        Vector data = new Vector();

        try
        {
          data= getResult( facility,workarea,branchplant,itemid,cabid );

        }
        catch ( Exception e1 )
        {
          e1.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printHTMLError() );
        }

        relabelsession.setAttribute("CABLVLCHGMGMTDATA", data );

        Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

        buildHeader( facility,workarea,selHub,itemid,data,cabname,"",cabid);

        if ( 0 == count )
        {
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
        }
        else
        {
          buildDetails( data);
        }

        //clean up
        data=null;
      }
      else if ( User_Action.equalsIgnoreCase( "Update" ) )
      {
        Vector retrn_data= ( Vector ) relabelsession.getAttribute( "CABLVLCHGMGMTDATA" );
        Vector synch_data=SynchServerData( request,retrn_data );

        if (UpdateDetails(synch_data,personnelid,selHub))
        {
          buildHeader( facility,workarea,selHub,itemid,synch_data,cabname,"Successfull",cabid);
        }
        else
        {
          buildHeader( facility,workarea,selHub,itemid,synch_data,cabname,"Error",cabid);
        }

        Vector data = new Vector();
        try
        {
          data= getResult( facility,workarea,branchplant,itemid,cabid );
        }
        catch ( Exception e1 )
        {
          e1.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printHTMLError() );
        }
        relabelsession.setAttribute("CABLVLCHGMGMTDATA", data );

        Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

        if ( 0 == count )
        {
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
        }
        else
        {
          buildDetails( data);
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

 /* public Hashtable getcabinetInitialData(Hashtable hublist) throws Exception
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
		  falgforand = true;
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

     //Build page - To build the gui page.
     private void buildDetails( Vector data )
     {
       //StringBuffer Msg=new StringBuffer();

       try
       {
         Hashtable summary=new Hashtable();

         String facility_id = "";
         String application = "";
         String partgroupno = "";
         String cat_part_no = "";

         summary= ( Hashtable ) data.elementAt( 0 );
         int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
         //System.out.println( "Total Number : " + total );
         int vsize=data.size();
         //System.out.println( "Vector Size  : " + vsize );

         //start table #3
         out.println( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n" );

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
             out.println( "<tr align=\"center\">\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Description</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Container Size</TH>\n" );
             //out.println( "<TH width=\"5%\"  height=\"38\">Work Area</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Part Number</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Reorder Point</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Stocking Level</TH>\n" );
						 out.println( "<TH width=\"5%\"  height=\"38\">Lead Time in Days</TH>\n" );
             out.println( "<TH width=\"5%\"  height=\"38\">Remarks</TH>\n" );
             out.println( "</tr>\n" );
           }

           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

           //String source_hub = hD.get("SOURCE_HUB")==null?"&nbsp;":hD.get("SOURCE_HUB").toString();
           //String company_id=hD.get( "COMPANY_ID" ) == null ? "&nbsp;" : hD.get( "COMPANY_ID" ).toString();
           cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "" : hD.get( "CAT_PART_NO" ).toString();
           //String materialidno=hD.get( "MATERIAL_ID_STRING" ) == null ? "&nbsp;" : hD.get( "MATERIAL_ID_STRING" ).toString();
           String reorder_point=hD.get( "REORDER_POINT" ) == null ? "&nbsp;" : hD.get( "REORDER_POINT" ).toString();
           String stocking_level = hD.get("STOCKING_LEVEL")==null?"&nbsp;":hD.get("STOCKING_LEVEL").toString();

           String item_id = hD.get("ITEM_ID")==null?"&nbsp;":hD.get("ITEM_ID").toString();
           String packaging = hD.get("PACKAGING")==null?"&nbsp;":hD.get("PACKAGING").toString();
           String material_desc = hD.get("MATERIAL_DESC")==null?"&nbsp;":hD.get("MATERIAL_DESC").toString();
           String mfg_desc = hD.get("MFG_DESC")==null?"&nbsp;":hD.get("MFG_DESC").toString();

           facility_id=hD.get( "FACILITY_ID" ) == null ? "" : hD.get( "FACILITY_ID" ).toString();
           application=hD.get( "APPLICATION" ) == null ? "" : hD.get( "APPLICATION" ).toString();
           partgroupno=hD.get( "PART_GROUP_NO" ) == null ? "" : hD.get( "PART_GROUP_NO" ).toString();
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

           out.println("<tr align=\"center\">\n");
           out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+material_desc+".."+mfg_desc+"</td>\n");
           out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+(packaging)+"</td>\n");
           //out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+(application)+"</td>\n");
           out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+(cat_part_no)+"</td>\n");
           if (this.getupdateStatus())
           {
             out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"reorderpt"+i+"\" value=\""+reorder_point+"\" maxlength=\"10\" size=\"4\"></td>\n" );
           }
           else
           {
             out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + ( reorder_point ) + "</td>\n" );
           }

           if (this.getupdateStatus())
           {
             out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"stocklevel"+i+"\" value=\""+stocking_level+"\" maxlength=\"10\" size=\"4\"></td>\n" );
           }
           else
           {
             out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + ( stocking_level ) + "</td>\n" );
           }

		       if (this.getupdateStatus())
					 {
						 out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"leadtimedays"+i+"\" value=\""+leadtimedays+"\" maxlength=\"10\" size=\"4\"></td>\n" );
					 }
					 else
					 {
						 out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + leadtimedays + "</td>\n" );
					 }


           if (this.getupdateStatus())
           {
             out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"remarks"+i+"\" value=\"\" maxlength=\"200\" size=\"25\"></td>\n" );
           }
           else
           {
             out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"></td>\n" );
           }

           out.println("</tr>\n");
         }

         out.println( "</table>\n" );
         out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
         out.println( "<tr>" );
         out.println( "<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n" );
         out.println( "</TD></tr>" );
         out.println( "</table>\n" );

         out.println( "</form>\n" );

         out.println(buildHistdetails(facility_id,application,cat_part_no,partgroupno));

         out.println( "</body></html>\n" );
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         out.println( radian.web.HTMLHelpObj.printError( "Logistics",intcmIsApplication ) );
       }

       return;
     }

     private StringBuffer buildHistdetails(String selectedFac,String tmpwaSelect, String partnum, String partgrpno)
     {
        StringBuffer MsgH=new StringBuffer();
        String query = "select x.*,to_char(DATE_MODIFIED,'dd Mon yyyy hh24:mi') DATE_MODIFIED1, fx_personnel_id_to_name(MODIFIED_BY) MODIFIED_BY_NAME from cabinet_part_level_log x where facility_id='"+selectedFac+"' and APPLICATION = '"+tmpwaSelect+"' and CAT_PART_NO='"+partnum+"'";
        DBResultSet dbrs = null;
        ResultSet hisrs = null;

        MsgH.append( "<BR><TABLE WIDTH=\"100%\" BORDER=\"0\" CLASS=\"moveup\">\n" );
        MsgH.append( "<TR><TD WIDTH=\"100%\" ALIGN=\"LEFT\" HEIGHT=\"22\" CLASS=\"heading\">Change History</TD></TR>\n" );
        MsgH.append("</TABLE>\n");

        MsgH.append( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
        int linecount=0;
        boolean createHdr=false;

        try
        {
             dbrs = db.doQuery(query);
             hisrs=dbrs.getResultSet();

             while (hisrs.next())
             {
               createHdr=false;
               if ( linecount % 10 == 0 )
               {
                 createHdr=true;
               }

               if ( createHdr )
               {
                 MsgH.append( "<tr align=\"center\">\n" );
                 MsgH.append( "<TH width=\"5%\"  height=\"38\">Part Number</TH>\n" );
                 MsgH.append( "<TH width=\"5%\"  height=\"38\">Changed On</TH>\n" );
                 MsgH.append( "<TH width=\"5%\"  height=\"38\">Changed By</TH>\n" );
                 MsgH.append( "<TH width=\"5%\"  height=\"38\">Old Reorder Point</TH>\n" );
                 MsgH.append( "<TH width=\"5%\"  height=\"38\">New Reorder Point</TH>\n" );
                 MsgH.append( "<TH width=\"5%\"  height=\"38\">Old Stocking Level</TH>\n" );
                 MsgH.append( "<TH width=\"5%\"  height=\"38\">New Stocking Level</TH>\n" );
								 MsgH.append( "<TH width=\"5%\"  height=\"38\">Old Lead Time in Days</TH>\n" );
								 MsgH.append( "<TH width=\"5%\"  height=\"38\">New Lead Time in Days</TH>\n" );
                 MsgH.append( "<TH width=\"5%\"  height=\"38\">Remarks</TH>\n" );
                 MsgH.append( "</tr>\n" );
               }

               String company_id = hisrs.getString("COMPANY_ID")==null?"":hisrs.getString("COMPANY_ID");
               String catalog_id=hisrs.getString( "CATALOG_ID" ) == null ? "" : hisrs.getString( "CATALOG_ID" );
               String cat_part_no=hisrs.getString( "CAT_PART_NO" ) == null ? "" : hisrs.getString( "CAT_PART_NO" );
               String part_group_no=hisrs.getString( "PART_GROUP_NO" ) == null ? "" : hisrs.getString( "PART_GROUP_NO" );
               String facility_id=hisrs.getString( "FACILITY_ID" ) == null ? "" : hisrs.getString( "FACILITY_ID" );
               String application=hisrs.getString( "APPLICATION" ) == null ? "" : hisrs.getString( "APPLICATION" );
               String old_reorder_point=hisrs.getString( "OLD_REORDER_POINT" ) == null ? "" : hisrs.getString( "OLD_REORDER_POINT" );
               String old_stocking_level=hisrs.getString( "OLD_STOCKING_LEVEL" ) == null ? "" : hisrs.getString( "OLD_STOCKING_LEVEL" );
               String new_reorder_point=hisrs.getString( "NEW_REORDER_POINT" ) == null ? "" : hisrs.getString( "NEW_REORDER_POINT" );
               String new_stocking_level=hisrs.getString( "NEW_STOCKING_LEVEL" ) == null ? "" : hisrs.getString( "NEW_STOCKING_LEVEL" );
               String date_modified=hisrs.getString( "DATE_MODIFIED1" ) == null ? "" : hisrs.getString( "DATE_MODIFIED1" );
               String modified_by=hisrs.getString( "MODIFIED_BY_NAME" ) == null ? "" : hisrs.getString( "MODIFIED_BY_NAME" );
               String remarks=hisrs.getString( "REMARKS" ) == null ? "" : hisrs.getString( "REMARKS" );
							 String oldleadtimedays = hisrs.getString("OLD_LEAD_TIME_DAYS")==null?"":hisrs.getString("OLD_LEAD_TIME_DAYS");
								String newleadtimedays = hisrs.getString("NEW_LEAD_TIME_DAYS")==null?"":hisrs.getString("NEW_LEAD_TIME_DAYS");

               String Color=" ";
               if ( linecount % 2 == 0 )
               {
                 Color="CLASS=\"white";
               }
               else
               {
                 Color="CLASS=\"blue";
               }

               MsgH.append("<tr align=\"center\">\n");
               MsgH.append("<td "+Color+"\" width=\"5%\" height=\"38\">"+cat_part_no+"</td>\n");
               MsgH.append("<td "+Color+"\" width=\"5%\" height=\"38\">"+date_modified+"</td>\n");
               MsgH.append("<td "+Color+"\" width=\"5%\" height=\"38\">"+modified_by+"</td>\n");
               MsgH.append("<td "+Color+"\" width=\"5%\" height=\"38\">"+old_reorder_point+"</td>\n");
               MsgH.append("<td "+Color+"\" width=\"5%\" height=\"38\">"+new_reorder_point+"</td>\n");
               MsgH.append("<td "+Color+"\" width=\"5%\" height=\"38\">"+old_stocking_level+"</td>\n");
               MsgH.append("<td "+Color+"\" width=\"5%\" height=\"38\">"+new_stocking_level+"</td>\n");
							 MsgH.append("<td "+Color+"\" width=\"5%\" height=\"38\">"+oldleadtimedays+"</td>\n");
							 MsgH.append("<td "+Color+"\" width=\"5%\" height=\"38\">"+newleadtimedays+"</td>\n");
               MsgH.append("<td "+Color+"\" width=\"5%\" height=\"38\">"+remarks+"</td>\n");
               MsgH.append("</tr>\n");
               linecount++;
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

       MsgH.append( "</TABLE>\n" );
       return MsgH;
     }

    //Build HTML Header
    private void buildHeader(String selectedFac,String tmpwaSelect, String selectedHub, String itemid1,Vector data, String cabname1,String message, String cabid)
    {
      //StringBuffer Msg=new StringBuffer();
      out.println(radian.web.HTMLHelpObj.printHTMLHeader("Cabinet Management","cabinetlevels.js",intcmIsApplication));

      out.println("</HEAD>  \n");

      String Search_servlet = radian.web.tcmisResourceLoader.getcabinetchglvlurl();

      out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
      out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
      out.println("</DIV>\n");
      out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

      out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("Cabinet Management\n"));
      out.println("<BR><B>"+message+"</B>");
      out.println( "<FORM METHOD=\"POST\" NAME=\"invoicereports\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n" );
      out.println( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
      out.println( "<TR VALIGN=\"MIDDLE\">\n" );
      //Item ID
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>Item ID:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">"+itemid1+"</TD>\n");

      //Change Levels
      out.println("<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      try
      {
        if ( this.getupdateStatus() )
        {
          out.println( "<INPUT TYPE=\"submit\" VALUE=\"Update Values\" onclick= \"return update(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
        }
      }
      catch ( Exception ex )
      {
      }
      out.println("</TD>\n</TR>\n");

      out.println( "<TR VALIGN=\"MIDDLE\">\n" );
      //Hub
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>Hub:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">"+selectedHub+"</TD>\n");

      out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
      out.println( "&nbsp;\n" );
      out.println("</TD>\n");
      out.println("</TR>\n");

      out.println( "<TR VALIGN=\"MIDDLE\">\n" );
      //Facility
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>Facility:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">"+selectedFac+"</TD>\n");

      out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
      out.println( "&nbsp;\n" );
      out.println("</TD>\n");
      out.println("</TR>\n");

      out.println( "<TR VALIGN=\"MIDDLE\">\n" );
      //Work Area
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>Work Area:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">"+tmpwaSelect+"</TD>\n");

      out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
      out.println( "&nbsp;\n" );
      out.println("</TD>\n");
      out.println("</TR>\n");

      out.println( "<TR VALIGN=\"MIDDLE\">\n" );
      //Cabinet
      out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.println( "<B>Cabinet:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println("<TD WIDTH=\"15%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">"+cabname1+"</TD>\n");

      out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
      out.println( "&nbsp;\n" );
      out.println("</TD>\n");
      out.println("</TR>\n");

      out.println("</TABLE>\n");

      out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"workAreaName\" VALUE=\""+tmpwaSelect+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"hubC\" VALUE=\""+selectedHub+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"item_id\" VALUE=\""+itemid1+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"cabname\" VALUE=\""+cabname1+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"facilityName\" VALUE=\""+selectedFac+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"cabid\" VALUE=\""+cabid+"\">\n");

      return;
    }

    private Vector getResult( String facSelected,String waSelected,String sourcehub,String itemid,String cabinetid ) throws Exception
    {

      Vector Data=new Vector();
      Hashtable DataH=new Hashtable();
      Hashtable summary=new Hashtable();
      String nodaysago="60";
      boolean falgforand =false;
      DBResultSet dbrs = null;
      ResultSet searchRs = null;

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
		  query+=" and x.FACILITY_ID='" + facSelected + "'";
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

        if ( (itemid.trim().length() == 0) )
        {

        }
        else
        {
          if ( falgforand )
            query+=" and ITEM_ID = " + itemid + "  ";
          else
            query+=" ITEM_ID = " + itemid + "  ";
        }

        if ( !( cabinetid.trim().length() == 0 || "'All'".equalsIgnoreCase( cabinetid ) ) )
        {
          if ( falgforand )
            query+=" and CABINET_ID = " + cabinetid + "  ";
          else
            query+=" CABINET_ID = " + cabinetid + "  ";
        }

          dbrs=db.doQuery( query );
          searchRs=dbrs.getResultSet();

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

            //OLD_REORDER_POINT
            DataH.put( "OLD_REORDER_POINT",searchRs.getString( "REORDER_POINT" ) == null ? "" : searchRs.getString( "REORDER_POINT" ) );
            //OLD_STOCKING_LEVEL
            DataH.put("OLD_STOCKING_LEVEL",searchRs.getString("STOCKING_LEVEL")==null?"":searchRs.getString("STOCKING_LEVEL"));
            //USE_APPLICATION
            DataH.put("USE_APPLICATION",searchRs.getString("USE_APPLICATION")==null?"":searchRs.getString("USE_APPLICATION"));
						//LEAD_TIME_DAYS
						DataH.put( "LEAD_TIME_DAYS",searchRs.getString( "LEAD_TIME_DAYS" ) == null ? "" : searchRs.getString( "LEAD_TIME_DAYS" ) );
						//OLD_LEAD_TIME_DAYS
						DataH.put( "OLD_LEAD_TIME_DAYS",searchRs.getString( "LEAD_TIME_DAYS" ) == null ? "" : searchRs.getString( "LEAD_TIME_DAYS" ) );

            DataH.put("REMARKS","");
            DataH.put("UPDATE_LEVELS","");

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

            String reorderpt="";
            reorderpt=request.getParameter( ( "reorderpt" + i ) );
            if ( reorderpt == null )
              reorderpt="";

            String stocklevel="";
            stocklevel=request.getParameter( ( "stocklevel" + i ) );
            if ( stocklevel == null )
              stocklevel="";

						 String leadtimedays="";
						 leadtimedays=request.getParameter( ( "leadtimedays" + i ) );
						 if ( leadtimedays == null )
							leadtimedays="";

            String remarks="";
            remarks=request.getParameter( ( "remarks" + i ) );
            if ( remarks == null )
              remarks="";

            hD.remove("REORDER_POINT");
            hD.put("REORDER_POINT",reorderpt.trim());

            hD.remove("STOCKING_LEVEL");
            hD.put("STOCKING_LEVEL",stocklevel.trim());

						hD.remove("LEAD_TIME_DAYS");
            hD.put("LEAD_TIME_DAYS",leadtimedays.trim());

            String oldreorpt = ( hD.get( "OLD_REORDER_POINT" ) == null ? " " : hD.get( "OLD_REORDER_POINT" ).toString() );
            String oldstklvl = ( hD.get( "OLD_STOCKING_LEVEL" ) == null ? " " : hD.get( "OLD_STOCKING_LEVEL" ).toString() );
						String oldleadtimedays = ( hD.get( "OLD_LEAD_TIME_DAYS" ) == null ? " " : hD.get( "OLD_LEAD_TIME_DAYS" ).toString() );

            //System.out.println("  oldreorpt   "+oldreorpt+"  reorderpt  "+reorderpt+"    oldstklvl   "+oldstklvl+"  stocklevel   "+stocklevel+"");

            if ( reorderpt.equalsIgnoreCase(oldreorpt) && stocklevel.equalsIgnoreCase(oldstklvl) && leadtimedays.equalsIgnoreCase(oldleadtimedays) )
            {

            }
            else
            {
              hD.remove("UPDATE_LEVELS");
              hD.put("UPDATE_LEVELS","Yes");
            }

            hD.remove("REMARKS");
            hD.put("REMARKS",remarks.trim());

            new_data.addElement( hD );
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printError( "Cabinet Level Change",intcmIsApplication ) );
        }

        return new_data;
      }

      private boolean UpdateDetails( Vector data,String logonid,String selectedHub )
      {
       boolean result=false;
       int count=0;

        try
        {
          Hashtable summary=new Hashtable();
          summary= ( Hashtable ) data.elementAt( 0 );
          int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

          int vsize=data.size();

          int linecheckcount=0;
          boolean one_success=false;
          for ( int i=1; i < total + 1; i++ )
          {
            Hashtable hD=new Hashtable();
            hD= ( Hashtable ) data.elementAt( i );
            String returnedReceiptId="";
            String Line_Check="";
            Line_Check= ( hD.get( "UPDATE_LEVELS" ) == null ? "&nbsp;" : hD.get( "UPDATE_LEVELS" ).toString() );

            if ( Line_Check.equalsIgnoreCase( "yes" ) )
            {
              noneToUpd=false;
              //System.out.println("Doing Update");
              linecheckcount++;
              String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "" : hD.get( "CAT_PART_NO" ).toString();
              String reorder_point=hD.get( "REORDER_POINT" ) == null ? "" : hD.get( "REORDER_POINT" ).toString();
              String stocking_level=hD.get( "STOCKING_LEVEL" ) == null ? "" : hD.get( "STOCKING_LEVEL" ).toString();
              String oldreorder_point=hD.get( "OLD_REORDER_POINT" ) == null ? "" : hD.get( "OLD_REORDER_POINT" ).toString();
              String oldstocking_level=hD.get( "OLD_STOCKING_LEVEL" ) == null ? "" : hD.get( "OLD_STOCKING_LEVEL" ).toString();

              //System.out.println("  oldreorder_point   "+oldreorder_point+"  reorder_point  "+reorder_point+"    oldstocking_level   "+oldstocking_level+"  stocking_level   "+stocking_level+"");

              String item_id=hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString();
              String packaging=hD.get( "PACKAGING" ) == null ? "" : hD.get( "PACKAGING" ).toString();
              String material_desc=hD.get( "MATERIAL_DESC" ) == null ? "" : hD.get( "MATERIAL_DESC" ).toString();
              String mfg_desc=hD.get( "MFG_DESC" ) == null ? "" : hD.get( "MFG_DESC" ).toString();

              String facility_id=hD.get( "FACILITY_ID" ) == null ? "" : hD.get( "FACILITY_ID" ).toString();
              String application=hD.get( "APPLICATION" ) == null ? "" : hD.get( "APPLICATION" ).toString();
              String partgroupno=hD.get( "PART_GROUP_NO" ) == null ? "" : hD.get( "PART_GROUP_NO" ).toString();
              String remarks=hD.get( "REMARKS" ) == null ? "" : hD.get( "REMARKS" ).toString();
              //if (remarks.length() > 200) {remarks = remarks.substring(0,199);}
              remarks = HelpObjs.singleQuoteToDouble(remarks);
              String company_id=hD.get( "COMPANY_ID" ) == null ? "" : hD.get( "COMPANY_ID" ).toString();
              String catalog_id=hD.get( "CATALOG_ID" ) == null ? "" : hD.get( "CATALOG_ID" ).toString();
							String leadtimedays = hD.get("LEAD_TIME_DAYS")==null?"":hD.get("LEAD_TIME_DAYS").toString();
							String oldleadtimedays = ( hD.get( "OLD_LEAD_TIME_DAYS" ) == null ? " " : hD.get( "OLD_LEAD_TIME_DAYS" ).toString() );

              String insertdata = "insert into cabinet_part_level_log (COMPANY_ID,CATALOG_ID,CAT_PART_NO,PART_GROUP_NO,FACILITY_ID,APPLICATION,OLD_REORDER_POINT,";
              insertdata += "OLD_STOCKING_LEVEL,NEW_REORDER_POINT,NEW_STOCKING_LEVEL,OLD_LEAD_TIME_DAYS,NEW_LEAD_TIME_DAYS,DATE_MODIFIED,MODIFIED_BY,REMARKS) ";
              insertdata += "values ('"+company_id+"','"+catalog_id+"','"+cat_part_no+"',"+partgroupno+",'"+facility_id+"','"+application+"',"+oldreorder_point+",";
              insertdata += ""+oldstocking_level+","+reorder_point+","+stocking_level+","+oldleadtimedays+","+leadtimedays+",SYSDATE,"+logonid+",'"+remarks+"')";

              String updatelvls = "";
              updatelvls  = "update cabinet_part_inventory set REORDER_POINT="+reorder_point+",STOCKING_LEVEL="+stocking_level+",LEAD_TIME_DAYS="+leadtimedays+" where ";
              updatelvls += "COMPANY_ID = '"+company_id+"' and CATALOG_ID='"+catalog_id+"' and FACILITY_ID = '"+facility_id+"' and APPLICATION ='"+application+"' ";
              updatelvls += "and CAT_PART_NO = '"+cat_part_no+"' and PART_GROUP_NO = "+partgroupno+"";

              try
              {
                db.doUpdate( updatelvls );
                db.doUpdate( insertdata );
              }
              catch ( Exception ex )
              {
               result=false;
              }

		          if (!"Decatur Hub".equalsIgnoreCase(selectedHub))
							{
								float stockingLevel = Float.parseFloat (stocking_level);
								float oldstockingLevel = Float.parseFloat (oldstocking_level);
								float percentChange = 0;

								percentChange = ((stockingLevel - oldstockingLevel) / oldstockingLevel) * 100;
								//System.out.println("percentChange  "+percentChange+"");

								if ( ("0".equalsIgnoreCase(stocking_level) && !"0".equalsIgnoreCase(oldstocking_level)) ||
								     ("0".equalsIgnoreCase(oldstocking_level) && !"0".equalsIgnoreCase(stocking_level)) ||
  								   (java.lang.Math.abs(percentChange) > 30) )
							 {
								UserGroup ug = new UserGroup(db);
								ug.setGroupId("HubManager");
								ug.setGroupFacId(selectedHub);
								ug.setCompanyId("Radian");

			          StringBuffer emailMessage=new StringBuffer();
								emailMessage.append("Cabinet Level Changes:\n\n");
								emailMessage.append("Facility: "+facility_id+"\n");
								emailMessage.append("Cabinet: "+application+"\n");
								emailMessage.append("Part Number: "+cat_part_no+"\n");
								emailMessage.append("Item Id: "+item_id+"\n");
								emailMessage.append("Stocking Level: Old: "+oldstocking_level+" New: "+stocking_level+"\n");
								emailMessage.append("Reorder Point: Old: "+oldreorder_point+" New: "+reorder_point+"\n");
								emailMessage.append("Lead Time in Days: Old: "+oldleadtimedays+" New: "+leadtimedays+"\n");
								emailMessage.append("Comments: "+remarks+"\n");
								HelpObjs.sendMail(ug, "Cabinet Level Changes", emailMessage.toString());
							 }
							}
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
          out.println( radian.web.HTMLHelpObj.printError( "Cabinet Level Change",intcmIsApplication ) );
        }

        return result;
      }
}
