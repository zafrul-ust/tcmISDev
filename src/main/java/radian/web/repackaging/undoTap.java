package radian.web.repackaging;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import javax.servlet.http.HttpSession;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      Haas TCM
 * @author       Nawaz Shaik
 */

public class undoTap
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  PrintWriter out = null;
  private boolean intcmIsApplication = false;
  private ResourceLibrary res = null; // res means resource.

  public undoTap( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  private boolean allowupdate;
  public void setupdateStatus(boolean id)
  {
	this.allowupdate=id;
  }

  private boolean getupdateStatus()
	 throws Exception
  {
	return this.allowupdate;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response )
     throws ServletException,IOException
  {
    out=response.getWriter();
    response.setContentType( "text/html" );

    HttpSession session = request.getSession();
    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
    Action=Action.trim();

    String searchString=request.getParameter( "SearchString" );
    if ( searchString == null )
      searchString="";
    searchString=searchString.trim();

    String invengrp=request.getParameter( "invengrp" );
    if ( invengrp == null )
      invengrp="";
    invengrp=invengrp.trim();

	String branch_plant=request.getParameter( "hubnum" );
	  if ( branch_plant == null )
		branch_plant="";
	  branch_plant=branch_plant.trim();

	  String undotapid=request.getParameter( "undotapid" );
	  if ( undotapid == null )
		undotapid="";
	  undotapid=undotapid.trim();

    if ( "UPDATE".equalsIgnoreCase( Action ) )
    {
        buildundotap(undotapid);
    }
    else if ( "Search".equalsIgnoreCase( Action ) )
    {
      searchopentaps( "Search",searchString,invengrp,branch_plant);
    }
    else
    {
      searchopentaps( "",searchString,invengrp,branch_plant);
    }
    out.close();
  }

  private void buildundotap(String receipt_id)
{
  //StringBuffer Msg=new StringBuffer();
  CallableStatement cs=null;
  boolean closeresult = false;

  try
  {
	Connection connect1=db.getConnection();
	cs=connect1.prepareCall( "{call P_untap(?,?)}" );
	cs.setInt( 1,Integer.parseInt( receipt_id ) );
	cs.registerOutParameter(2,java.sql.Types.VARCHAR);
	cs.execute();

	String errorcode=BothHelpObjs.makeBlankFromNull( cs.getString( 2 ) );
	if ( errorcode.trim().length() > 0 )
	{
	  closeresult=false;
	}
	else
	{
	  closeresult=true;
	}
  }
  catch ( Exception e )
  {
	e.printStackTrace();
	closeresult = false;
	radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure P_untap in Close Tap Screen","Error occured while running P_untap\n" + e.getMessage() +"\nFor Input Parameters Receipt Id: " + receipt_id + "" );
  }
  finally
  {
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

	out.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.reversetap","closetap.js",intcmIsApplication,res ) );
	out.println( "</HEAD>  \n" );
	out.println( "<BODY>\n" );
	out.println( "<FORM method=\"POST\" NAME=\"closetap\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"\">\n" );
	out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
	out.println( "<TR VALIGN=\"MIDDLE\">\n" );
	out.println( "<TD HEIGHT=45 WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"CENTER\" CLASS=\"announce\">\n" );
	if (closeresult)
	{
	  out.println( res.getString("label.tapreversed")+" "+res.getString("label.tapid")+":"+receipt_id+"\n<BR><BR>"+res.getString("label.refreshmainpage") );
	}
	else
	{
	  out.println( res.getString("msg.errorreverse")+receipt_id+"\n"+res.getString("msg.errcontacttech")+"<BR><BR>" );
	}
	out.println( "</TD>\n" );
	out.println( "</TR>" );
	out.println( "</TABLE>\n" );
	out.println( "<BR>\n" );
	out.println( "<CENTER><INPUT TYPE=\"button\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" VALUE=\""+res.getString("label.ok")+"\" OnClick=\"submitmainpage()\" NAME=\"sendterv\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" );
	out.println( "</CENTER>\n" );
	out.println( "</BODY>\n" );
	out.println( "</HTML>\n" );
  return;
}

  public void searchopentaps( String SearchFlag,String SearchString,String invengrp,String branchplant )
  {
    //StringBuffer Msgl=new StringBuffer();
    DBResultSet dbrs=null;
    ResultSet searchRs=null;
    String branchPlant="";

    String nematid_Servlet= radian.web.tcmisResourceLoader.getnewtapurl();
    out.println( radian.web.HTMLHelpObj.printHTMLHeader( "label.reversetap","undotap.js",intcmIsApplication,res ) );

    out.println( "<BODY onload=\"javascript:window.resizeTo(670,450)\">\n" );
	out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
	out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT></center>\n");
	out.println("</DIV>\n");
	out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

    out.println( "<FORM method=\"POST\" NAME=\"undotap\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+radian.web.tcmisResourceLoader.getundotapurl()+"\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" NAME=\"invengrp\" ID=\"invengrp\" VALUE=\"" + invengrp + "\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" NAME=\"hubnum\" ID=\"hubnum\" VALUE=\"" + branchplant + "\">\n" );
	out.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" ID=\"Action\" VALUE=\"\">\n" );
    out.println( "<TABLE BORDER=\"0\" width=\"603\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
    out.println( "<TR>\n" );
    out.println( "<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>"+res.getString("label.searchtext")+":</B></TD>\n" );
    if ( "Search".equalsIgnoreCase( SearchFlag ) )
    {
      out.println( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"" + SearchString + "\" SIZE=\"40\">\n" );
    }
    else
    {
      out.println( "<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"\" SIZE=\"40\">\n" );
    }
	out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.search")+"\" onclick= \"return undotapsearch(this)\" NAME=\"SearchButton\">&nbsp;</TD>\n");

    out.println( "<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>"+res.getString("label.tapid")+":</B></FONT></TD><TD WIDTH=\"70%\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" name=\"undotapid\" value=\"\" SIZE=\"25\" readonly>\n" );
    out.println( "</TR>\n" );
    out.println( "</TABLE><TABLE BORDER=\"0\" width=\"603\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
    out.println( "<TR>\n" );
    out.println( "<TH WIDTH=\"68\"><B>"+res.getString("label.item")+"</B></FONT></TH>\n" );
	out.println( "<TH WIDTH=\"75\"><B>"+res.getString("label.packaging")+"</B></FONT></TH>\n" );
    out.println( "<TH WIDTH=\"397\"><B>"+res.getString("label.description")+"</B></FONT></TH>\n" );
    out.println( "<TH WIDTH=\"63\"><B>"+res.getString("label.tapid")+"</B></FONT></TH>\n" );
    out.println( "</TR></TABLE>\n" );

    //open scrolling table
    out.println( "<TABLE CLASS=\"newmoveup\" WIDTH=\"619\">\n" );
    out.println( "<TBODY>\n" );
    out.println( "<TR>\n" );
    out.println( "<TD VALIGN=\"TOP\">\n" );
    out.println( "<DIV ID=\"orderdetail\" CLASS=\"newscroll_column350\">\n" );
    //Write code to insert rows here
    out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"newmoveup\">\n" );

    int totalrecords=0;
    int count=0;

    String query="";

    if ( "Search".equalsIgnoreCase( SearchFlag ) )
    {
	  SearchString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
	  String wherese=radian.web.logicalSearchhelp.doLogicM( SearchString,"SEARCH_TEXT" );
	  query+="select * from tapped_inventory_view x where "+wherese+" and HUB='"+branchplant+"' and INVENTORY_GROUP='"+invengrp+"' and QUANTITY_ISSUED=0 order by ITEM_CONVERSION_ISSUE_ID asc";
    }
    else
    {

    }

    if (  "Search".equalsIgnoreCase( SearchFlag ) )
    {
      try
      {
        dbrs=db.doQuery( query );
        searchRs=dbrs.getResultSet();

        while ( searchRs.next() )
        {
          totalrecords+=1;
          count+=1;

		  String hub = searchRs.getString("HUB")==null?"":searchRs.getString("HUB");
		  String inventory_group = searchRs.getString("INVENTORY_GROUP")==null?"":searchRs.getString("INVENTORY_GROUP");
		  String item_id = searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID");
		  String item_conversion_issue_id = searchRs.getString("ITEM_CONVERSION_ISSUE_ID")==null?"":searchRs.getString("ITEM_CONVERSION_ISSUE_ID");
		  String receipt_id = searchRs.getString("RECEIPT_ID")==null?"":searchRs.getString("RECEIPT_ID");
		  String quantity_received = searchRs.getString("QUANTITY_RECEIVED")==null?"":searchRs.getString("QUANTITY_RECEIVED");
		  String quantity_issued = searchRs.getString("QUANTITY_ISSUED")==null?"":searchRs.getString("QUANTITY_ISSUED");
		  String search_text = searchRs.getString("SEARCH_TEXT")==null?"":searchRs.getString("SEARCH_TEXT");
		  String item_pkg = searchRs.getString("ITEM_PKG")==null?"":searchRs.getString("ITEM_PKG");
		  String item_desc = searchRs.getString("ITEM_DESC")==null?"":searchRs.getString("ITEM_DESC");

          String Color=" ";
          if ( count % 2 == 0 )
          {
            Color="BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }
          else
          {
            Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
          }

          out.println( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"addtapid('" + receipt_id + "')\" BORDERCOLOR=\"black\">\n" );
          out.println( "<TD " + Color + " WIDTH=\"66\" ALIGN=\"LEFT\">" );
          out.println( item_id );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + " WIDTH=\"72\" ALIGN=\"LEFT\">" );
          out.println( item_pkg );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + " WIDTH=\"399\" ALIGN=\"LEFT\">" );
          out.println( item_desc );
          out.println( "</TD>\n" );
          out.println( "<TD " + Color + " WIDTH=\"62\" ALIGN=\"LEFT\">" );
          out.println( receipt_id );
          out.println( "</TD>\n" );

          out.println( "</TR>\n" );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.println( res.getString("err.querydatabase") );
        out.println( "</BODY>\n</HTML>\n" );
        //return Msgl;
      }
      finally
      {
        if (dbrs!= null) {dbrs.close();}
      }

    if ( totalrecords == 0 )
    {
      out.println( "<TR><TD>"+res.getString("label.norecordfound")+"</TD></TR>\n" );
    }
    }
    out.println( "</TABLE>\n" );

    //close scrolling table
    out.println( "</DIV>\n" );
    out.println( "</TD>\n" );
    out.println( "</TR>\n" );
    out.println( "</TBODY>\n" );
    out.println( "</TABLE>\n" );
	out.println( "<CENTER>\n<BR><BR>" );
	try
	{
	  if ( this.getupdateStatus() )
	  {
		out.println( "<INPUT TYPE=\"submit\" VALUE=\""+res.getString("label.reverseselectedtap")+"\" onclick= \"return undoupdate(this)\" NAME=\"UpdateButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
	  }
	  else
	  {
		out.println( "&nbsp;\n" );
	  }
	}
	catch ( Exception ex )
	{
	}
    out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\""+res.getString("label.cancel")+"\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
    out.println( "</FORM></BODY></HTML>\n" );

    return;
  }
}