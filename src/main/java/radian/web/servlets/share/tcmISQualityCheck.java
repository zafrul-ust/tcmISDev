package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.CatAddQc;
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
 *
 * 11-04-02
 * replace dbrs.close with if (dbrs != null)  { dbrs.close(); } to avoid some nullpointerExceptions
 * 06-16-03 - Code Clanup and made changes to the catalog drop down to show catalid id instead of catalog desc
 * 12-22-03 - Code Refactoring, moved vv_stuff to different class
 * 02-18-04 - Adding VAPOR_PRESSURE_DETECT, VAPOR_PRESSURE, VAPOR_PRESSURE_UNIT, VAPOR_PRESSURE_TEMP, VAPOR_PRESSURE_TEMP_UNIT to page
 */

public class tcmISQualityCheck
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String tcmIS_details_Servlet = "";
    private String tcmIS_quality_Servlet = "";
    private String MSDS_quality_Servlet = "";
		private boolean msdsUpdateAllowed = false;
		private boolean itemQcAllowed = false;
private boolean intcmIsApplication = false;
		public void setMsdsUpdateAllowed(boolean id)
		{
			this.msdsUpdateAllowed = id;
		}

		private boolean getMsdsUpdateAllowed()
		{
		 return this.msdsUpdateAllowed;
		}

		public void setItemQcAllowed(boolean id)
		{
			this.itemQcAllowed = id;
		}

		private boolean getItemQcAllowed()
		{
		 return this.itemQcAllowed;
		}

    public tcmISQualityCheck(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResult( HttpServletRequest request,HttpServletResponse response,HttpSession session1 )
       throws ServletException,IOException
    {
      PrintWriter out=response.getWriter();
      String User_Action="";
      String company_selected="0";
      String catalog_desc="";
      String User_Sort="";
      String User_status="";
			PersonnelBean personnelBean = (PersonnelBean) session1.getAttribute("personnelBean");
			Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
			if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
			{
			 intcmIsApplication = true;
			}

      CatAddQc cataddqcObj=new CatAddQc( db );
      tcmIS_quality_Servlet=bundle.getString( "QUALITY_CONTROL" );
      tcmIS_details_Servlet=bundle.getString( "QUALITY_CONTROL_DETAILS" );
      MSDS_quality_Servlet=bundle.getString( "QUALITY_CONTROL_MSDS" );

      response.setContentType( "text/html" );

      User_Action=request.getParameter( "Action" );
      if ( User_Action == null )
        User_Action="New";

      company_selected=request.getParameter( "company" );
      if ( company_selected == null )
        company_selected="0";
      if ( company_selected.length() < 1 )
      {
        company_selected="0";
      }

      catalog_desc=request.getParameter( "CatDescription" );
      if ( catalog_desc == null )
        catalog_desc="ALL";

      User_Sort=request.getParameter( "SortBy" );
      if ( User_Sort == null )
        User_Sort="3";

      User_status=request.getParameter( "Category" );
      if ( User_status == null )
        User_status="Pending MSDS";

   String searchlike = request.getParameter("searchlike");
    if (searchlike == null) {
        searchlike = "";
    }
    searchlike = searchlike.trim();

    String searchfor = request.getParameter("searchfor");
    if (searchfor == null) {
        searchfor = "";
    }
    searchfor = searchfor.trim();
    if (searchlike.length() == 0) {
        searchlike = "car.request_id";
    }

   String searchField = request.getParameter("searchField");
    if (searchField == null) {
        searchField = "";
    }
    searchField = searchField.trim();

      //System.out.println( "User status is " + User_status + " Company Index" + company_selected + "Catalog Desc " + catalog_desc );
      try
      {
        if ( "Search".equalsIgnoreCase( User_Action ) )
        {
          Vector compnayids= ( Vector ) session1.getAttribute( "COMPANY_IDS" );
          String companyArrayJs= ( String ) session1.getAttribute( "CATALOGS_JS" );
          int index=Integer.parseInt( company_selected );
          String CompanyName1=compnayids.elementAt( index ).toString();
          buildHeader( "",compnayids,companyArrayJs,CompanyName1,catalog_desc,User_Sort,User_status,out,searchlike,searchfor,searchField);
          Vector OpenOrders=new Vector();

          session1.setAttribute( "COMPANY_BACK",CompanyName1 );
          session1.setAttribute( "CATALOG_BACK",catalog_desc );
          session1.setAttribute( "SORT_BACK",User_Sort );
          session1.setAttribute( "STATUS_BACK",User_status );

          try
          {
            OpenOrders=cataddqcObj.getAllopenOrder( CompanyName1,catalog_desc,User_Sort,User_status,searchlike,searchfor,searchField );

            if ( "Pending QC".equalsIgnoreCase( User_status ) )
            {
              builditemqcRequests( OpenOrders,User_status,out,searchlike,searchfor,searchField );
            }
            else if ( "Pending MSDS".equalsIgnoreCase( User_status ) )
            {
              builditemqcRequests( OpenOrders,User_status,out,searchlike,searchfor,searchField);
            }
            else
            {
              builditemqcRequests( OpenOrders,User_status,out,searchlike,searchfor,searchField);
            }
          }
          catch ( Exception e )
          {
            e.printStackTrace();
            out.println( "An Error Occured While Querying the Databse" );
          }
          out.println( "</BODY></HTML>\n" );
        }
        else if ( "BackSearch".equalsIgnoreCase( User_Action ) )
        {
          Vector compnayids= ( Vector ) session1.getAttribute( "COMPANY_IDS" );
          String companyArrayJs= ( String ) session1.getAttribute( "CATALOGS_JS" );
          //int index=Integer.parseInt( company_selected );

          String companyback=session1.getAttribute( "COMPANY_BACK" ) == null ? "" : session1.getAttribute( "COMPANY_BACK" ).toString();
          String catalogback=session1.getAttribute( "CATALOG_BACK" ) == null ? "" : session1.getAttribute( "CATALOG_BACK" ).toString();
          String sortback=session1.getAttribute( "SORT_BACK" ) == null ? "" : session1.getAttribute( "SORT_BACK" ).toString();
          String whichStatus=session1.getAttribute( "STATUS_BACK" ) == null ? "" : session1.getAttribute( "STATUS_BACK" ).toString();

          buildHeader( "",compnayids,companyArrayJs,companyback,catalogback,sortback,whichStatus,out,searchlike,searchfor,searchField);
          Vector OpenOrders=new Vector();

          try
          {
            OpenOrders=cataddqcObj.getAllopenOrder( companyback,catalogback,sortback,whichStatus,searchlike,searchfor,searchField);

            if ( "Pending QC".equalsIgnoreCase( whichStatus ) )
            {
              builditemqcRequests( OpenOrders,whichStatus,out,searchlike,searchfor,searchField);
            }
            else if ( "Pending MSDS".equalsIgnoreCase( whichStatus ) )
            {
              builditemqcRequests( OpenOrders,whichStatus,out,searchlike,searchfor,searchField);
            }
            else
            {
              builditemqcRequests( OpenOrders,whichStatus,out,searchlike,searchfor,searchField);
            }
          }
          catch ( Exception e )
          {
            e.printStackTrace();
            out.println( "An Error Occured While Querying the Databse" );
          }
          out.println( "</BODY></HTML>\n" );
        }
        else if ( "active".equalsIgnoreCase( User_Action ) )
        {
          Vector compnayids=radian.web.vvHelpObj.getCompanyIds(db);
          String companyArrayJs=radian.web.vvHelpObj.getCatalogJs( db,compnayids ).toString();
          int index=Integer.parseInt( company_selected );
          String CompanyName1=compnayids.elementAt( index ).toString();
          session1.setAttribute( "COMPANY_IDS",compnayids );
          session1.setAttribute( "CATALOGS_JS",companyArrayJs );

          buildHeader( "",compnayids,companyArrayJs,CompanyName1,catalog_desc,User_Sort,User_status,out,searchlike,searchfor,searchField);
          out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
        }
        else
        {
          Vector compnayids=new Vector();
          String companyArrayJs="";
          String CompanyName1="";

          String donevvstuff=session1.getAttribute( "VVCATALOGSTUFF" ) == null ? "" : session1.getAttribute( "VVCATALOGSTUFF" ).toString();
          if ( donevvstuff.equalsIgnoreCase( "Yes" ) )
          {
            int index=Integer.parseInt( company_selected );
            compnayids= ( Vector ) session1.getAttribute( "COMPANY_IDS" );
            companyArrayJs= ( String ) session1.getAttribute( "CATALOGS_JS" );
            CompanyName1=compnayids.elementAt( index ).toString();
          }
          else
          {
            session1.setAttribute( "CATEGORY",radian.web.vvHelpObj.getcategory(db) );
            session1.setAttribute( "PKG_STYLE",radian.web.vvHelpObj.getpkgstyle(db) );
            session1.setAttribute( "SIZE_UNIT",radian.web.vvHelpObj.getsizeunit(db) );
            session1.setAttribute( "STORAGE_TEMP",radian.web.vvHelpObj.getstoragetemp(db) );
            session1.setAttribute( "WEIGHT_UNIT",radian.web.vvHelpObj.getweightunit(db) );
            session1.setAttribute( "ITEM_TYPE",radian.web.vvHelpObj.getitemtype(db) );
            session1.setAttribute( "PHYSICAL_STATE",radian.web.vvHelpObj.getphysicalstate(db) );
            session1.setAttribute( "VOC_UNIT",radian.web.vvHelpObj.getvocunit(db) );
            session1.setAttribute( "DENSITY_UNIT",radian.web.vvHelpObj.getdensityunit(db) );
            session1.setAttribute( "SP_HAZARD",radian.web.vvHelpObj.getsphazard(db) );
            session1.setAttribute( "PPE",radian.web.vvHelpObj.getppe(db) );
			session1.setAttribute( "VAPOR_PRESSURE_UNIT",radian.web.vvHelpObj.getvapourpressunit(db) );
			session1.setAttribute( "VAPOR_PRESSURE_DETECT",radian.web.vvHelpObj.getvapourpressdetect(db) );
			session1.setAttribute( "VOC_SOURCE",radian.web.vvHelpObj.getvocsource(db) );

            compnayids=radian.web.vvHelpObj.getcatCompanyIds(db);
            companyArrayJs=radian.web.vvHelpObj.getCatalogJs( db,compnayids ).toString();
            int index=Integer.parseInt( company_selected );
            CompanyName1=compnayids.elementAt( index ).toString();
            session1.setAttribute( "COMPANY_IDS",compnayids );
            session1.setAttribute( "CATALOGS_JS",companyArrayJs );
            session1.setAttribute( "VVCATALOGSTUFF","Yes" );
          }

          buildHeader( "",compnayids,companyArrayJs,CompanyName1,catalog_desc,User_Sort,User_status,out,searchlike,searchfor,searchField);
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

  private void builditemqcRequests( Vector toBeQced,String Status,PrintWriter out,String searchlike,String searchfor,String searchField )
  {
    //StringBuffer Msg=new StringBuffer();
    out.println( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

    if ( toBeQced.size() == 0 )
    {
      out.println( "<TR><TD HEIGHT=\"30\" CLASS=\"Inwhite\">No Records Found</TD></TR>\n" );
    }
    else
    {
      for ( int count=0; count < toBeQced.size(); count++ )
      {
        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) toBeQced.elementAt( count );

        if ( count % 10 == 0 )
        {
          out.println( "<TR>\n" );
          out.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\"><B>Request Id</B></TH>\n" );
		  out.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\"><B>Request Type</B></TH>\n" );
          out.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\"><B>Status</B></TH>\n" );
          out.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\"><B>Company</B></TH>\n" );
          out.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\"><B>Catalog Id</B></TH>\n" );
          out.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\"><B>Requestor</B></TH>\n" );
          out.println( "<TH WIDTH=\"15%\" HEIGHT=\"38\"><B>Material Description</B></TH>\n" );
          out.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\"><B>Manufacturer</B></TH>\n" );
          out.println( "<TH WIDTH=\"15%\" HEIGHT=\"38\"><B>Comments</B></TH>\n" );
          out.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\"><B>Submitted</B></TH>\n" );
          out.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\"><B>MSDS QC-Date</B></TH>\n" );
          out.println( "</TR>\n" );
        }

        String Color=" ";
        if ( count % 2 == 0 )
        {
          Color="CLASS=\"Inwhite";
        }
        else
        {
          Color="CLASS=\"Inblue";
        }

          String CompanyId=hD.get( "COMPANY" ).toString();
					String requestId=BothHelpObjs.makeBlankFromNull(hD.get("REQUEST_ID").toString());

          out.println("<TR>\n");

          out.println("<TD WIDTH=\"5%\" HEIGHT=\"38\" "+Color+"l\">");

          if ("Pending QC".equalsIgnoreCase(Status) && this.getItemQcAllowed())
          {
					 /*if (intcmIsApplication)
					 {
						out.println("<A href=# onclick=\"parent.openIFrame('newchemitemqc"+requestId+"','"+tcmIS_details_Servlet+"Company="+CompanyId+"&isTab=Item&Action=details&request_id="+requestId+"','Request Id "+requestId+"','')\">");
						out.println(""+requestId+"</A>");
			//openIFrame('msds','/tcmIS/ray/Msds?limit=yes&noncatalog=yes&istcmIsApplication=Y','MSDS','');
					 }
					 else*/
					 {
						out.println("<A HREF=\""+tcmIS_details_Servlet+"Company="+CompanyId+"&isTab=Item&Action=details&request_id="+requestId+"&searchlike="+searchlike+"&searchfor="+searchfor+"&searchField="+searchField+"\">");						out.println(""+requestId+"</A>");
					 }
          }
          else if ("Pending MSDS".equalsIgnoreCase(Status) && this.getMsdsUpdateAllowed())
          {
					 /*if (intcmIsApplication)
					 {
						out.println("<A href=# onclick=\"parent.openIFrame('newchemmsds"+requestId+"','"+MSDS_quality_Servlet+"Company="+CompanyId+"&isTab=Item&Action=details&request_id="+requestId+"','Request Id "+requestId+"','')\">");
						out.println(""+requestId+"</A>");
					 }
					 else*/
					 {
						out.println("<A HREF=\""+MSDS_quality_Servlet+"Company="+CompanyId+"&isTab=Item&Action=details&request_id="+requestId+"&searchlike="+searchlike+"&searchfor="+searchfor+"&searchField="+searchField+"\">");
						out.println(""+requestId+"</A>");
      		 }
          }
					else
					{
					 out.println(requestId);
		      }
          out.println("\n");

		  out.println("<TD WIDTH=\"5%\" HEIGHT=\"38\" "+Color+"l\">");out.println((hD.get("STARTING_VIEW").toString()));out.println("\n");
          out.println("<TD WIDTH=\"5%\" HEIGHT=\"38\" "+Color+"l\">");out.println((hD.get("STATUS").toString()));out.println("\n");
          out.println("<TD WIDTH=\"5%\" HEIGHT=\"38\" "+Color+"l\">");out.println(CompanyId);out.println("\n");
          out.println("<TD WIDTH=\"5%\" HEIGHT=\"38\" "+Color+"l\">");out.println((hD.get("FACILITY_NAME").toString()));out.println("\n");
          out.println("<TD WIDTH=\"5%\" HEIGHT=\"38\" "+Color+"l\">");out.println((hD.get("FULL_NAME").toString()));out.println("\n");
          out.println("<TD WIDTH=\"15%\" HEIGHT=\"38\" "+Color+"l\">");out.println((hD.get("MATERIAL_DESC").toString()));out.println("\n");
          out.println("<TD WIDTH=\"5%\" HEIGHT=\"38\" "+Color+"l\">");out.println((hD.get("MANUFACTURER").toString()));out.println("\n");
	      out.println("<TD WIDTH=\"15%\" HEIGHT=\"38\" "+Color+"l\">");out.println(hD.get("COMMENTS").toString());out.println("\n");
          out.println("<TD WIDTH=\"5%\" HEIGHT=\"38\" "+Color+"l\">");out.println((hD.get("REQUEST_DATE").toString()));out.println("\n");
          out.println("<TD WIDTH=\"5%\" HEIGHT=\"38\" "+Color+"l\">");out.println((hD.get("QCDATE").toString()));out.println("\n");
          out.println("</TR>\n");
        }
      }
      out.println("</TABLE>\n");
      return;
    }

    public void buildArrayofCom( int totalNum,PrintWriter out )
    {
      //StringBuffer Msgd2=new StringBuffer();

      out.println( "<SCRIPT>\n" );
      out.println( "var cmds = new Array();\n" );
      for ( int count=0; count < totalNum; count++ )
      {
        out.println( "cmds[" + count + "] = \"Component" + ( count + 1 ) + "\";\n" );
      }
      out.println( "</SCRIPT>\n" );

      return;
    }

    //Build HTML Header
    private void buildHeader( String Message,Vector CompanyIds,String compArrayJs,String CompanyName,String CatDesc,String sortby,String Status,PrintWriter out,
                              String searchlike,String searchfor,String searchField)
    {
      //StringBuffer Msg=new StringBuffer();
      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC","cataddqc.js",intcmIsApplication ) );
			//out.println( "<script type=\"text/javascript\" src=\"/js/common/tabs.js\"></script>\n" );
			out.println( "<script type=\"text/javascript\" src=\"/js/common/commonutil.js\"></script>\n" );
      out.println( "<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n" );
      out.println( compArrayJs );
      out.println( "// -->\n </SCRIPT>\n" );
      out.println( "</HEAD>\n" );
      out.println( "<BODY onload=\"resetApplicationTimer()\">\n" );

      out.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
      out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
      out.println( "</DIV>\n" );
      out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
      out.println( radian.web.HTMLHelpObj.PrintCatalogTitleBar( "Catalog Add QC\n" ) );
      out.println( "<FORM method=\"POST\" NAME=\"companyform\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + tcmIS_quality_Servlet + "\">\n" );
      out.println( "<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
      out.println( "<TR>\n" );

      //Company
      out.println( "<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"RIGHT\">\n" );
      out.println( "<B>Company:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println( "<TD CLASS=\"announce\" WIDTH=\"20%\" ALIGN=\"LEFT\">\n" );
      out.println( "<SELECT CLASS=\"HEADER\" NAME=\"company\" ONCHANGE=\"reshow(document.companyform.company)\">\n" );
      for ( int i=0; i < CompanyIds.size(); i++ )
      {
        String comnameinloop=CompanyIds.elementAt( i ).toString();
        out.println( "<OPTION " + ( CompanyName.equalsIgnoreCase( comnameinloop ) ? "selected" : " " ) + " VALUE=\"" + i + "\">" + BothHelpObjs.addSpaceForUrl( comnameinloop ) + "</OPTION>\n" );
      }
      out.println( "</SELECT>\n" );
      out.println( "</TD>\n" );

      // Sort
      out.println( "<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"RIGHT\">\n" );
      out.println( "<B>Sort:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println( "<TD CLASS=\"announce\" WIDTH=\"25%\" ALIGN=\"LEFT\">\n" );
      out.println( "<SELECT CLASS=\"HEADER\" NAME=\"SortBy\" size=\"1\">\n" );
      if ( sortby.equalsIgnoreCase( "1" ) )
      {
        out.println( "<option selected value=\"1\">Company/Catalog Id/Submitted/QC_date</option>\n" );
        out.println( "<option value=\"2\">Submitted/QC-Date/Company/Catalog Id</option>\n" );
        out.println( "<option value=\"3\">Mfg/Request ID/Desc</option>\n" );
      }
      else if ( sortby.equalsIgnoreCase( "2" ) )
      {
        out.println( "<option value=\"1\">Company/Catalog Id/Submitted/QC_date</option>\n" );
        out.println( "<option selected value=\"2\">Submitted/QC-Date/Company/Catalog Id</option>\n" );
        out.println( "<option value=\"3\">Mfg/Request ID/Desc</option>\n" );
      }
      else
      {
        out.println( "<option value=\"1\">Company/Catalog Id/Submitted/QC_date</option>\n" );
        out.println( "<option value=\"2\">Submitted/QC-Date/Company/Catalog Id</option>\n" );
        out.println( "<option selected value=\"3\">Mfg/Request ID/Desc</option>\n" );
      }


      out.println( "</SELECT>\n" );
      out.println( "</TD>\n" );

      //List Orders
      out.println( "<TD CLASS=\"announce\" WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n" );
      out.println( "<INPUT TYPE=\"submit\" ID=\"genbutton1\" VALUE=\"List Requests\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println( "</TR><TR>\n" );

      //Catalog Drop Box
      //String fac1="";
      out.println( "<TD CLASS=\"announce\" HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\">\n" );
      out.println( "<B>Catalog:</B>&nbsp; \n" );
      out.println( "</TD>\n" );
      out.println( "<TD CLASS=\"announce\" WIDTH=\"20%\" ALIGN=\"LEFT\">\n" );

      out.println( "<SELECT CLASS=\"HEADER\" NAME=\"catalogs\" ONCHANGE=\"showvalue(document.companyform.catalogs)\">\n" );

      if ( "ALL".equalsIgnoreCase( CompanyName ) )
      {
        out.println( "<OPTION VALUE=\"None\">First Choose a Company</OPTION>\n" );
      }
      else
      {
        Vector catDescs=radian.web.vvHelpObj.getCatalogDescs( db,CompanyName );

        for ( int i=0; i < catDescs.size(); i++ )
        {
          String catdecinloop=catDescs.elementAt( i ).toString();
          out.println( "<OPTION " + ( CatDesc.equalsIgnoreCase( catdecinloop ) ? "selected" : "" ) + " VALUE=\"" + BothHelpObjs.addSpaceForUrl( catdecinloop ) + "\">" + catdecinloop + "</OPTION>\n" );
        }
      }
      out.println( "</SELECT>\n" );

      //Status
      out.println( "<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"RIGHT\">\n" );
      out.println( "<B>Status:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println( "<TD CLASS=\"announce\" WIDTH=\"25%\" ALIGN=\"LEFT\">\n" );
      out.println( "<SELECT CLASS=\"HEADER\" NAME=\"Category\" size=\"1\">\n" );

      if ( Status.equalsIgnoreCase( "Pending MSDS" ) )
      {
        out.println( "<option selected value=\"Pending MSDS\">Pending MSDS</option>\n" );
        out.println( "<option value=\"Pending QC\">Pending QC</option>\n" );
      }
      else
      {
        out.println( "<option value=\"Pending MSDS\">Pending MSDS</option>\n" );
        out.println( "<option selected value=\"Pending QC\">Pending QC</option>\n" );
      }

      out.println( "</SELECT>\n" );
      out.println( "</TD><TD>&nbsp;</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      out.println( "<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"RIGHT\">\n" );
      out.println( "<B>Search:</B>&nbsp;\n" );
      out.println( "</TD>\n" );
      out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"left\" CLASS=\"announce\">\n" );
	  out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"searchlike\" id=\"searchlike\" size=\"1\">\n" );
	  Hashtable searchthese=new Hashtable();
	  searchthese.put( "Item Id","cai.item_id" );
	  searchthese.put( "Description","cai.MATERIAL_DESC" );
      searchthese.put( "Manufacturer","cai.MANUFACTURER" );
      searchthese.put( "Request Id","car.request_id" );
      out.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( searchthese.entrySet(),searchlike ) );
	  out.println( "</SELECT>\n" );
	  out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"searchfor\" size=\"1\">\n" );
	  Hashtable sortresult=new Hashtable();
	  sortresult.put( "Contains","Like");
	  sortresult.put( "Equals", "Equals");
	  out.println( radian.web.HTMLHelpObj.sorthashbyValueshowkey( sortresult.entrySet(),searchfor ) );
	  out.println( "</SELECT>\n" );
	  out.println( "</TD>\n" );

	  out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" colspan=\"3\" CLASS=\"announce\">\n" );
	  out.println( "<B>for</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchField\" value=\"" + searchField + "\" size=\"20\">\n" );
	  out.println( "</TD>\n" );
      out.println( "</TR>\n" );
      out.println( "<TR><TD CLASS=\"announce\" ALIGN=\"CENTER\"><B>" + Message + "</B></TD></TR>\n" );
      out.println( "</TABLE>\n" );
      out.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n" );
      out.println( "<tr><td>" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Search\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"CatDescription\" VALUE=\"" + ( CatDesc == null ? "ALL" : "" + CatDesc + "" ) + "\">\n" );
      out.println( "</TD></tr>" );
      out.println( "</table></FORM>\n\n" );

      return ;
    }
}
