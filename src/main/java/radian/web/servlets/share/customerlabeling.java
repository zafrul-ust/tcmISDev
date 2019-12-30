package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
import radian.tcmis.server7.share.helpers.HelpObjs;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */


public class customerlabeling
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private PrintWriter out=null;

  private Vector receiptIdstoLabel = null;
  private Vector numbersoflabels = null;
  private Vector searchlikeV = null;
  private Vector sortbythis = null;

  protected boolean allowupdate;
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

  public customerlabeling( ServerResourceBundle b,TcmISDBModel d )
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
    facility = facility.trim();

    workarea=request.getParameter( "workAreaName" );
    if ( workarea == null )
      workarea="";
    workarea = workarea.trim();

    sortby=request.getParameter( "sortby" );
    if ( sortby == null )
      sortby="";
    sortby = sortby.trim();

    //System.out.println( "User Search is " + searchwhat + "  containsorlike" + containsorlike + "  facility " + facility+ "  workarea   "+workarea );

    try
    {
      Hashtable initialData = null;
      String initialDataLoaded = "";

      initialDataLoaded="";
      try
      {
        initialDataLoaded=relabelsession.getAttribute( "INITIAL_LABEL_DATA_LOADED" ).toString();
      }
      catch ( Exception getdoneFlag )
      {}
      if ( initialDataLoaded.equalsIgnoreCase( "Yes" ) )
      {
        initialData= ( Hashtable ) relabelsession.getAttribute( "INITIAL_LABEL_DATA" );
        searchlikeV= ( Vector ) relabelsession.getAttribute( "SEARCH_LIKE_CUSLABLE" );
        sortbythis= ( Vector ) relabelsession.getAttribute( "SORT_BY_CUSLABLE" );
      }
      else
      {
        initialData= this.getInvoiceInitialData( );
        relabelsession.setAttribute( "INITIAL_LABEL_DATA",initialData );
        relabelsession.setAttribute( "INITIAL_LABEL_DATA_LOADED","Yes" );

        searchlikeV = this.getsearchlike();
        relabelsession.setAttribute( "SEARCH_LIKE_CUSLABLE",searchlikeV );

        sortbythis = this.getsortbythese();
        relabelsession.setAttribute( "SORT_BY_CUSLABLE",sortbythis );
      }

      if ( User_Action.equalsIgnoreCase( "Search" ) )
      {
        Vector data = new Vector();

        try
        {
          data= getResult( searchwhat,containsorlike,searchtext,facility,workarea,sortby );
        }
        catch ( Exception e1 )
        {
          e1.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printHTMLError() );
        }

        relabelsession.setAttribute("DATA", data );

        Hashtable sum= ( Hashtable ) ( data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

        buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData," ",sortby );

        if ( 0 == count )
        {
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
        }
        else
        {
          buildDetails( data );
        }

        //clean up
        data=null;
      }
      else if ( User_Action.equalsIgnoreCase( "getlabelqtys" ) )
      {
        Vector retrn_data= ( Vector ) relabelsession.getAttribute( "DATA" );
        Vector synch_data=SynchServerData( request,retrn_data );

        relabelsession.setAttribute("DATA", synch_data );

        relabelsession.setAttribute("RELABEL_SEQUENCE_NUMBERS", receiptIdstoLabel);
        relabelsession.setAttribute("RELABEL_QUANTITYS", numbersoflabels);

        Hashtable sum= ( Hashtable ) ( synch_data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

        if ( 0 == count )
        {
          buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData," ",sortby );
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
        }
        else
        {
          buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData,"GENERATE_LABELS",sortby );
          buildDetails( synch_data );
        }
      }
      else
      {
        buildHeader( searchwhat,containsorlike,searchtext,facility,workarea,initialData," ",sortby );
        out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
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

  public Vector getsearchlike()
  {
    Hashtable result=null;
    Vector ResultV=new Vector();

    result=new Hashtable();
    result.put( "Receipt Id","RECEIPT_ID" );
    ResultV.addElement( result );

    result=new Hashtable();
    result.put( "Item Id","ITEM_ID" );
    ResultV.addElement( result );

    result=new Hashtable();
    result.put( "Part Number","CAT_PART_NO" );
    ResultV.addElement( result );

    result=new Hashtable();
    result.put( "MR","MR_NUMBER" );
    ResultV.addElement( result );

    result=new Hashtable();
    result.put( "Requestor","REQUESTOR_NAME" );
    ResultV.addElement( result );

    return ResultV;
  }

  public Vector getsortbythese()
  {
    Hashtable result=null;
    Vector ResultV=new Vector();

    result=new Hashtable();
    result.put( "Receipt Id","RECEIPT_ID" );
    ResultV.addElement( result );

    result=new Hashtable();
    result.put( "Item Id","ITEM_ID" );
    ResultV.addElement( result );

    result=new Hashtable();
    result.put( "Part Number","CAT_PART_NO" );
    ResultV.addElement( result );

    result=new Hashtable();
    result.put( "MR","MR_NUMBER" );
    ResultV.addElement( result );

    result=new Hashtable();
    result.put( "Requestor","REQUESTOR_NAME" );
    ResultV.addElement( result );

    result=new Hashtable();
    result.put( "Date Shipped","DATE_DELIVERED" );
    ResultV.addElement( result );

    return ResultV;
  }


  public Hashtable getInvoiceInitialData() throws Exception
  {
    String query="select * from hub_fac_loc_app_view ";
    DBResultSet dbrs=null;
    ResultSet rs=null;
    Hashtable resultdata =new Hashtable();
    try
    {
      dbrs=db.doQuery( query );
      rs=dbrs.getResultSet();

      String lastHub="";

      while ( rs.next() )
      {
        String tmpFacID=rs.getString( "facility_id" );
        String tmpHub="2103";
        if ( !tmpHub.equalsIgnoreCase( lastHub ) )
        {
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
          resultdata.put( "DATA",fh );
        }
        else
        { //hub already exist
          Hashtable fh= ( Hashtable ) resultdata.get( "DATA" );
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
          resultdata.put( "DATA",fh );
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
    return resultdata;
  }

  private String createComboBoxData( Hashtable initialData )
    {
      //System.out.print(initialData);

      String tmp=new String( "" );
      //String tmpFac = "var facID = new Array(";
      String altFacID="var altFacID = new Array();\n ";
      String altFacDesc="var altFacDesc = new Array();\n ";
      String altWAID="var altWAID = new Array();\n ";
      String altWADesc="var altWADesc = new Array();\n ";
      int i=0;
      {
        //second build facility array
        Hashtable fh= ( Hashtable ) initialData.get( "DATA" );
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
        altFacID+="altFacID[\"DATA\"] = new Array(";
        altFacDesc+="altFacDesc[\"DATA\"] = new Array(";
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
            altWADesc+="\"" + HelpObjs.findReplace( ( String ) applicationDesc.elementAt( j ),"\"","'" ) + "\"" + ",";
          }
          altWAID=altWAID.substring( 0,altWAID.length() - 1 ) + ");\n";
          altWADesc=altWADesc.substring( 0,altWADesc.length() - 1 ) + ");\n";
        }
        //removing the last commas ','
        altFacID=altFacID.substring( 0,altFacID.length() - 1 ) + ");\n";
        altFacDesc=altFacDesc.substring( 0,altFacDesc.length() - 1 ) + ");\n";
      }
      return ( " " + altFacID + " " + altFacDesc + " " + altWAID + " " + altWADesc + "" );
     }

     //Build page - To build the gui page.
     private void buildDetails( Vector data )
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
            out.println( "<TH width=\"5%\"  height=\"38\">Receipt Id</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Date Shipped</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Facility</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Work Area</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">MR-Line</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Requestor</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Part Number</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Lot</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Part Description</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Item</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\">Item Desc</TH>\n" );
            out.println( "<TH width=\"5%\"  height=\"38\"># Labels</TH>\n" );
            out.println( "</tr>\n" );
           }

           Hashtable hD=new Hashtable();
           hD= ( Hashtable ) data.elementAt( i );

           String receipt_id = hD.get("RECEIPT_ID")==null?"&nbsp;":hD.get("RECEIPT_ID").toString();
           String pr_number=hD.get( "PR_NUMBER" ) == null ? "&nbsp;" : hD.get( "PR_NUMBER" ).toString();
           String line_item=hD.get( "LINE_ITEM" ) == null ? "&nbsp;" : hD.get( "LINE_ITEM" ).toString();
           String cat_part_no=hD.get( "CAT_PART_NO" ) == null ? "&nbsp;" : hD.get( "CAT_PART_NO" ).toString();
           String item_id=hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString();
           String requestor=hD.get( "REQUESTOR" ) == null ? "&nbsp;" : hD.get( "REQUESTOR" ).toString();
           String requestor_name=hD.get( "REQUESTOR_NAME" ) == null ? "&nbsp;" : hD.get( "REQUESTOR_NAME" ).toString();
           String item_desc=hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString();
           String mfg_lot = hD.get("MFG_LOT")==null?"&nbsp;":hD.get("MFG_LOT").toString();
           String labelqty = hD.get("NO_OF_LABELS")==null?"":hD.get("NO_OF_LABELS").toString();
           String workarea = hD.get("APPLICATION")==null?"&nbsp;":hD.get("APPLICATION").toString();
           String facility=hD.get( "FACILITY_ID" ) == null ? "&nbsp;" : hD.get( "FACILITY_ID" ).toString();
           String dateshipped = hD.get("DATE_DELIVERED")==null?"&nbsp;":hD.get("DATE_DELIVERED").toString();

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
          out.println("<td "+Color+"\" width=\"5%\">"+(receipt_id)+"</td>\n");
          out.println("<td "+Color+"\" width=\"5%\">"+(dateshipped)+"</td>\n");
          out.println("<td "+Color+"\" width=\"5%\">"+(facility)+"</td>\n");
          out.println("<td "+Color+"\" width=\"5%\">"+(workarea)+"</td>\n");
          out.println("<td "+Color+"\" width=\"5%\">"+(pr_number+"-"+line_item)+"</td>\n");
          out.println("<td "+Color+"\" width=\"5%\">"+(requestor_name)+"</td>\n");
          out.println("<td "+Color+"\" width=\"5%\">"+(cat_part_no)+"</td>\n");
          out.println("<td "+Color+"\" width=\"5%\">"+(mfg_lot)+"</td>\n");
          out.println("<td "+Color+"\" width=\"5%\">"+(partdesc)+"</td>\n");
          out.println("<td "+Color+"\" width=\"5%\">"+(item_id)+"</td>\n");
          out.println("<td "+Color+"l\" width=\"5%\">"+(item_desc)+"</td>\n");
          out.println("<td "+Color+"\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"labels_qty"+i+"\"  value=\""+labelqty+"\" maxlength=\"4\" size=\"6\"></td>\n");
          out.println("</tr>\n");
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
    private void buildHeader(String searchLike,String containsorlke,String searchtext,String tmpSelect,String tmpwaSelect, Hashtable initialData,String buildlabels, String sortby)
    {
      //StringBuffer Msg=new StringBuffer();
      HeaderFooter hf=new HeaderFooter( bundle,db );

     out.println(hf.printHTMLHeader("tcmIS Drop Ship Receiving",intcmIsApplication));
     out.println("<SCRIPT SRC=\"/clientscripts/facworkareadrops.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
     out.println("<SCRIPT SRC=\"/clientscripts/clientrelableing.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");

     out.println("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
     out.println(createComboBoxData(initialData));
     out.println("// -->\n </SCRIPT>\n");

      if ( "GENERATE_LABELS".equalsIgnoreCase( buildlabels ) )
      {
       out.println( hf.printTopToolBar( "doPopup()","tcmisrelabel.gif" ) );
      }
      else
      {
       out.println( hf.printTopToolBar( "","tcmisrelabel.gif" ) );
      }

      String Search_servlet=bundle.getString( "RELABELING_SERVLET" );

     out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
     out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
     out.println("</DIV>\n");
     out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

     out.println( "<FORM METHOD=\"POST\" NAME=\"coustomerrelabel\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n" );
     out.println( "<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
     out.println( "<TR VALIGN=\"MIDDLE\">\n" );
      //Facility
      String preSelect="";
      String selectedHub="DATA";
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
     out.println( "<B>Facility:</B>\n" );
     out.println( "</TD>\n" );
     out.println( "<TD WIDTH=\"25%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
     out.println( "<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" size=\"1\" OnChange=\"facilityChanged(document.coustomerrelabel.facilityName)\">\n" );
      Hashtable fh= ( Hashtable ) initialData.get( selectedHub );
      Vector facID= ( Vector ) fh.get( "FACILITY_ID" );
      Vector facDesc= ( Vector ) fh.get( "FACILITY_DESC" );
      String selectedFac="";
      int i=0;
      selectedFac= ( String ) facID.firstElement(); //select the first facility if none were selected
      preSelect="";
      for ( i=0; i < facID.size(); i++ )
      {
        preSelect="";
        if ( tmpSelect.equalsIgnoreCase( ( String ) facID.elementAt( i ) ) )
        {
          preSelect="selected";
          selectedFac= ( String ) facID.elementAt( i );
        }
       out.println( "<option " + preSelect + " value=\"" + ( String ) facID.elementAt( i ) + "\">" + ( String ) facDesc.elementAt( i ) + "</option>\n" );
      }
     out.println( "</SELECT>\n" );
     out.println( "</TD>\n" );

      //Options
     out.println( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
     out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"searchwhat\" size=\"1\">\n" );
     out.println(radian.web.HTMLHelpObj.getDropDown(searchlikeV,searchLike));

     out.println( "</SELECT>\n" );
     out.println("</TD>\n\n");

      //Contains Is
     out.println( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
     out.println( "<SELECT CLASS=\"HEADER\" NAME=\"containsorlike\" size=\"1\">\n" );

      if ( searchLike.equalsIgnoreCase( "Contains" ) )
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
      //Work Area
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
     out.println( "<B>Work Area:</B>\n" );
     out.println( "</TD>\n" );
     out.println( "<TD WIDTH=\"25%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
      //Msg.append("<INPUT TYPE=\"hidden\" NAME=\"WorkAreaFullName\" ID=\"WorkAreaFullName\" value=\"\">\n");
     out.println( "<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\" >\n" );
      Hashtable h= ( Hashtable ) fh.get( selectedFac );
      Vector application= ( Vector ) h.get( "APPLICATION" );
      Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );
      for ( i=0; i < application.size(); i++ )
      {
        preSelect="";
        if ( tmpwaSelect.equalsIgnoreCase( ( String ) application.elementAt( i ) ) )
        {
          preSelect="selected";
        }
       out.println( "<option " + preSelect + " value=\"" + ( String ) application.elementAt( i ) + "\">" + ( String ) applicationDesc.elementAt( i ) + "</option>\n" );
      }
     out.println( "</SELECT>\n" );
     out.println( "</TD>\n" );

      /*//Blank
     out.println( "<TD WIDTH=\"10%\" CLASS=\"announce\">\n" );
     out.println( "&nbsp;\n" );
     out.println( "</TD>\n" );
*/
      //Blank
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
     out.println( "<B>Sort By :</B>\n" );
     out.println( "</TD>\n" );

      //Options
     out.println( "<TD HEIGHT=\"38\"WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
     out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"sortby\" size=\"1\">\n" );
     out.println(radian.web.HTMLHelpObj.getDropDown(sortbythis,sortby));
     out.println( "</SELECT>\n" );
     out.println("</TD>\n\n");

      //Label Options
     out.println( "<TD WIDTH=\"15%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
     out.println( "<INPUT TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('31')\" value=\"31\" checked>3''/1''&nbsp;\n" );
     out.println( "<INPUT TYPE=\"radio\" NAME=\"paperSize\" ONCLICK=\"assignPaper('811')\" value=\"811\">8.5''/11''\n" );
     out.println("</TD>\n");

      //Generate Labels
     out.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
     out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Generate Labels\" onclick= \"generatelabels(this)\" NAME=\"UpdateButton\">&nbsp;\n");
     out.println("<INPUT TYPE=\"hidden\" NAME=\"generate_labels\" VALUE=\"yes\">&nbsp;\n");
     out.println("</TD>\n");

      /*//Blank
     out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
     out.println( "&nbsp;\n" );
     out.println( "</TD>\n" );*/

     out.println("</TR></TABLE>\n");
     out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");

      return;
    }

    private Vector getResult( String searchLike,String containsorlke,String searchtext,String facSelected,String waSelected,String sortby )
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
        String query="select * from relabelling_search_view where ";

        if ( searchtext.length() > 0 )
        {
          query+="" + searchLike + "";
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
        else
        {
          query+=" rownum = 0";
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
          //System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");

          //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
          System.out.println("//"+rsMeta1.getColumnName(i).toString()+"");
          System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
          }*/

          while ( searchRs.next() )
          {
            DataH=new Hashtable();
            //RECEIPT_ID
            DataH.put( "RECEIPT_ID",searchRs.getString( "RECEIPT_ID" ) == null ? "" : searchRs.getString( "RECEIPT_ID" ) );
            //PR_NUMBER
            DataH.put( "PR_NUMBER",searchRs.getString( "PR_NUMBER" ) == null ? "" : searchRs.getString( "PR_NUMBER" ) );
            //LINE_ITEM
            DataH.put( "LINE_ITEM",searchRs.getString( "LINE_ITEM" ) == null ? "" : searchRs.getString( "LINE_ITEM" ) );
            //CAT_PART_NO
            DataH.put( "CAT_PART_NO",searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ) );
            //ITEM_ID
            DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
            //REQUESTOR
            DataH.put( "REQUESTOR",searchRs.getString( "REQUESTOR" ) == null ? "" : searchRs.getString( "REQUESTOR" ) );
            //REQUESTOR_NAME
            DataH.put( "REQUESTOR_NAME",searchRs.getString( "REQUESTOR_NAME" ) == null ? "" : searchRs.getString( "REQUESTOR_NAME" ) );
            //ITEM_DESC
            DataH.put( "ITEM_DESC",searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" ) );
            //MFG_LOT
            DataH.put( "MFG_LOT",searchRs.getString( "MFG_LOT" ) == null ? "" : searchRs.getString( "MFG_LOT" ) );
            //APPLICATION
            DataH.put( "APPLICATION",searchRs.getString( "APPLICATION" ) == null ? "" : searchRs.getString( "APPLICATION" ) );
            //FACILITY_ID
            DataH.put( "FACILITY_ID",searchRs.getString( "FACILITY_ID" ) == null ? "" : searchRs.getString( "FACILITY_ID" ) );
            //DATE_DELIVERED
            DataH.put( "DATE_DELIVERED",searchRs.getString( "DATE_DELIVERED" ) == null ? "" : searchRs.getString( "DATE_DELIVERED" ) );
            //LABEL_QTY
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

        receiptIdstoLabel = new Vector();
        numbersoflabels = new Vector();

        try
        {
          for ( int i=1; i < count + 1; i++ )
          {
            Hashtable hD=new Hashtable();
            hD= ( Hashtable ) ( in_data.elementAt( i ) );

            String labels_qty="";
            labels_qty=request.getParameter( ( "labels_qty" + i ) );
            if ( labels_qty == null )
              labels_qty="";
            hD.remove( "NO_OF_LABELS" );
            hD.put( "NO_OF_LABELS",labels_qty );

            if (labels_qty.trim().length() > 0)
            {
                numbersoflabels.addElement(labels_qty);
                receiptIdstoLabel.addElement(hD.get("RECEIPT_ID").toString());
            }

            new_data.addElement( hD );
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
          out.println( radian.web.HTMLHelpObj.printError( "Inventory",intcmIsApplication ) );
        }

        return new_data;
      }

}
