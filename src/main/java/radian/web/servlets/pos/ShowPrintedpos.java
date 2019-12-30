package radian.web.servlets.pos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class ShowPrintedpos extends HttpServlet implements SingleThreadModel
{
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    /**
    * HTTP POST handler.
    *
    * @param request               An object implementing the request context
    * @param response              An object implementing the response context
    * @exception ServletException  A wrapper for all exceptions thrown by the
    *      servlet.
    */
private boolean intcmIsApplication = false;
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        PrintWriter out = response.getWriter();
		response.setContentType( "text/html" );
        RayTcmISDBModel db = null;
        HttpSession session = request.getSession();
				PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
				boolean intcmIsApplication = false;
				if (personnelBean !=null)
				{
					 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
					 if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
						intcmIsApplication = true;
					 }
				}


        try
        {
          db = new RayTcmISDBModel("Tcm_Ops");
          if (db == null)
          {
              PrintWriter out2 = new PrintWriter (response.getOutputStream());
              out2.println("Starting DB");
              out2.println("DB is null");
              out2.close();
              return;
          }

        Hashtable loginresult = new Hashtable();
        loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
        String auth = (String)loginresult.get("AUTH");
        String errormsg = (String)loginresult.get("ERROSMSG");

        if (auth == null) {auth = "challenge";}
        if (errormsg == null) {errormsg = "";}

        if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
        {
          out.println( radian.web.HTMLHelpObj.printpoLoginScreen( errormsg,"Printed PO List" ) );
          out.flush();
        }
        else
        {
          String searchpo=request.getParameter( "searchpo" );
          if ( searchpo == null )
            searchpo="";
		  searchpo = searchpo.trim();

		 String searchbpo=request.getParameter( "searchbpo" );
		  if ( searchbpo == null )
			searchbpo="";
		  searchbpo = searchbpo.trim();

		  String action=request.getParameter( "UserAction" );
		   if ( action == null )
			 action="New";
		   action = action.trim();

		if ( action.equalsIgnoreCase("New") )
		{
			out.println(buildHeader(searchpo,searchbpo));
			out.println(radian.web.HTMLHelpObj.printHTMLSelect());
			out.close();
		}
		else if (action.equalsIgnoreCase("Search"))
		{
			Vector searchdata = new Vector();
			searchdata  = this.getSearchData(db,searchpo,searchbpo);

			Hashtable sum = ( Hashtable)( searchdata.elementAt(0));
			int count = ((Integer)sum.get("TOTAL_NUMBER")).intValue();
			if ( 0 == count )
			{
				out.println(buildHeader(searchpo,searchbpo));
				out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
				out.close();
				//clean up
				searchdata  = null;
			}
			else
			{
			  out.println(buildHeader(searchpo,searchbpo));
			  out.println(buildDetails(searchdata));
			  out.close();
			  //clean up
			  searchdata  = null;
			}
		}
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        return;
      }
      finally
      {
        db.close();
        return;
      }
    }

	private String buildHeader( String searchpostring,String searchbpostring )
	{
	  StringBuffer Msg=new StringBuffer();
	  try
	  {
		Msg.append( radian.web.HTMLHelpObj.printHTMLHeader( "Printed PO List","printpolist.js",intcmIsApplication ) );
		Msg.append( "</HEAD>  \n" );
		Msg.append( "<BODY>\n" );

		Msg.append( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
		Msg.append( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n" );
		Msg.append( "</DIV>\n" );
		Msg.append( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
		Msg.append( radian.web.HTMLHelpObj.printPurchaseOrderTitleBar( "<B>Printed PO List</B>\n" ) );

		//Eagle form for update
		Msg.append( "<FORM method=\"POST\" NAME=\"printpolist\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + radian.web.tcmisResourceLoader.getpinrtpolisturl() + "\">\n" );
		Msg.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

		Msg.append( "<TR>\n" );
		//PO Search String
		Msg.append( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		Msg.append( "<B>PO Number:</B>&nbsp;\n" );
		Msg.append( "</TD>\n" );
		Msg.append( "<TD  ALIGN=\"LEFT\" WIDTH=\"10%\">\n" );
		Msg.append( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchpo\" value=\"" + searchpostring + "\" maxlength=\"10\" size=\"10\">\n" );
		Msg.append( "</TD>\n" );

		//BPO Search String
		Msg.append( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		Msg.append( "<B>BO Number:</B>&nbsp;\n" );
		Msg.append( "</TD>\n" );
		Msg.append( "<TD  ALIGN=\"LEFT\" WIDTH=\"10%\">\n" );
		Msg.append( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchbpo\" value=\"" + searchbpostring + "\" maxlength=\"10\" size=\"10\">\n" );
		Msg.append( "</TD>\n" );

		//Search
		Msg.append( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
		Msg.append( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"UpdateButton\">&nbsp;\n" );
		Msg.append( "</TD>\n" );

		Msg.append( "</TR>\n" );
		Msg.append( "</TABLE>\n" );

		Msg.append( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n" );
		Msg.append( "<tr><td>" );
		Msg.append( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
		Msg.append( "</TD></tr>" );
		Msg.append( "</table>\n" );
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		Msg.append( radian.web.HTMLHelpObj.printError( "Printed PO List",intcmIsApplication ) );
	  }

	  return Msg.toString();
	}

	private String buildDetails(Vector data)
	{
	  StringBuffer Msg=new StringBuffer();
	  try
	  {
		Hashtable summary=new Hashtable();
		summary= ( Hashtable ) data.elementAt( 0 );
		int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
		int vsize=data.size();

		Msg.append( "<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"60%\" CLASS=\"moveup\">\n" );
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
			Msg.append( "<tr align=\"left\">\n" );
			Msg.append( "<TH width=\"2%\"  height=\"38\">PO/BO</TH>\n" );
			Msg.append( "<TH width=\"10%\"  height=\"38\">Supplier</TH>\n" );
			Msg.append( "<TH width=\"2%\"  height=\"38\">Date Printed</TH>\n" );
			Msg.append( "<TH width=\"2%\"  height=\"38\">Printed By</TH>\n" );
			Msg.append( "<TH width=\"2%\"  height=\"38\">Print Type</TH>\n" );
			Msg.append( "</tr>\n" );
		  }

		  Hashtable hD=new Hashtable();
		  hD= ( Hashtable ) data.elementAt( i );

		  //String image_id=hD.get( "IMAGE_ID" ) == null ? "&nbsp;" : hD.get( "IMAGE_ID" ).toString();
		  String radian_po=hD.get( "RADIAN_PO" ) == null ? "" : hD.get( "RADIAN_PO" ).toString();
		  String date_printed=hD.get( "DATE_PRINTED" ) == null ? "&nbsp;" : hD.get( "DATE_PRINTED" ).toString();
		  String file_url=hD.get( "FILE_URL" ) == null ? "&nbsp;" : hD.get( "FILE_URL" ).toString();
		  String supplier=hD.get( "SUPPLIER_NAME" ) == null ? "&nbsp;" : hD.get( "SUPPLIER_NAME" ).toString();
		  //String printed_by=hD.get( "PRINTED_BY" ) == null ? "&nbsp;" : hD.get( "PRINTED_BY" ).toString();
		  String printed_by_name=hD.get( "PRINTED_BY_NAME" ) == null ? "&nbsp;" : hD.get( "PRINTED_BY_NAME" ).toString();
		  String print_type=hD.get( "PRINT_TYPE" ) == null ? "&nbsp;" : hD.get( "PRINT_TYPE" ).toString();

		  String Color=" ";
		  if ( i % 2 == 0 )
		  {
			Color="CLASS=\"white";
		  }
		  else
		  {
			Color="CLASS=\"blue";
		  }

		  Msg.append( "<tr align=\"left\">\n" );
		  Msg.append( "<td " + Color + "\" width=\"5%\" height=\"38\"><A HREF=\"" + file_url + "\" TARGET=\"printpo" + i + "\">" + radian_po + "</A></td>\n" );
		  Msg.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + supplier + "</td>\n" );
		  Msg.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + date_printed + "</td>\n" );
		  Msg.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + printed_by_name + "</td>\n" );
		  Msg.append( "<td " + Color + "\" width=\"5%\" height=\"38\">" + print_type + "</td>\n" );
		  Msg.append( "</tr>\n" );
		}

		Msg.append( "</table>\n" );
		Msg.append( "</form>\n" );
		Msg.append( "</body></html>\n" );
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		Msg.append( radian.web.HTMLHelpObj.printError( "Printed PO List",intcmIsApplication ) );
	  }

	  return Msg.toString();
	}

	  private Vector getSearchData( TcmISDBModel dbObj,String searchpostring,String searchbpostring ) throws Exception
	  {
		Vector Data=new Vector();
		Hashtable DataH=new Hashtable();
		Hashtable summary=new Hashtable();

		DBResultSet dbrs=null;
		ResultSet polistrs=null;

		int count=0;
		summary.put( "TOTAL_NUMBER",new Integer( count ) );
		Data.addElement( summary );

		int num_rec=0;
		String polistquery="";

		if ( searchpostring.length() > 0 )
		{
		  polistquery= " select IMAGE_ID, RADIAN_PO, DATE_PRINTED, FILE_URL, SUPPLIER_NAME, PRINTED_BY, PRINTED_BY_NAME, PRINT_TYPE from po_image_view where RADIAN_PO like '%" + searchpostring + "%' order by DATE_PRINTED_ORDER desc";
		}
		else if ( searchpostring.length() > 0 )
		{
		  polistquery= " select IMAGE_ID, BPO RADIAN_PO, DATE_PRINTED, FILE_URL, SUPPLIER_NAME, PRINTED_BY, PRINTED_BY_NAME, PRINT_TYPE from bpo_image_view where BPO like '%" + searchbpostring + "%' order by DATE_PRINTED_ORDER desc";
		}

		try
		{
		  dbrs=dbObj.doQuery( polistquery );
		  polistrs=dbrs.getResultSet();

		  while ( polistrs.next() )
		  {
			DataH=new Hashtable();
			num_rec++;

			//DataH.put("IMAGE_ID",polistrs.getString("IMAGE_ID")==null?"":polistrs.getString("IMAGE_ID"));
			DataH.put( "RADIAN_PO",polistrs.getString( "RADIAN_PO" ) == null ? "" : polistrs.getString( "RADIAN_PO" ) );
			DataH.put( "DATE_PRINTED",polistrs.getString( "DATE_PRINTED" ) == null ? "" : polistrs.getString( "DATE_PRINTED" ) );
			DataH.put( "FILE_URL",polistrs.getString( "FILE_URL" ) == null ? "" : polistrs.getString( "FILE_URL" ) );
			DataH.put( "SUPPLIER_NAME",polistrs.getString( "SUPPLIER_NAME" ) == null ? "" : polistrs.getString( "SUPPLIER_NAME" ) );
			//DataH.put("PRINTED_BY",polistrs.getString("PRINTED_BY")==null?"":polistrs.getString("PRINTED_BY"));
			DataH.put( "PRINTED_BY_NAME",polistrs.getString( "PRINTED_BY_NAME" ) == null ? "" : polistrs.getString( "PRINTED_BY_NAME" ) );
			DataH.put( "PRINT_TYPE",polistrs.getString( "PRINT_TYPE" ) == null ? "" : polistrs.getString( "PRINT_TYPE" ) );

			Data.addElement( DataH );
		  }
		}
		catch ( Exception e )
		{
		  e.printStackTrace();
		  throw e;
		}
		finally
		{
		  if ( dbrs != null ) {dbrs.close();}
		}

		Hashtable recsum=new Hashtable();
		recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
		Data.setElementAt( recsum,0 );

		return Data;
	  }

    public void doGet( HttpServletRequest request,HttpServletResponse response )  throws ServletException,IOException
    {
      doPost( request,response );
    }
}

