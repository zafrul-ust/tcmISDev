package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 11-21-03 - making a readonly version
 * 12-18-03 - Making it possible to have multiple emails stored for an PO. Makes it possible to store more.
 * 03-02-04 - Made the notes available for BPO
 */

public class poNotes
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private String nematid_Servlet="";
  private boolean allowupdate;
  private boolean intcmIsApplication = false;

  public poNotes( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  public void setupdateStatus( boolean id )
  {
	this.allowupdate=id;
  }

  private boolean getupdateStatus() throws Exception
  {
	return this.allowupdate;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response,PrintWriter out )
     throws ServletException,IOException
  {
   /* PrintWriter out = new PrintWriter ( new OutputStreamWriter(response.getOutputStream (), "UTF8" ) );
    //PrintWriter out=response.getWriter();
    response.setContentType( "text/html; charset=UTF-8" );
    request.setCharacterEncoding("UTF-8");
    */
    HttpSession notessession=request.getSession();

    PersonnelBean personnelBean = (PersonnelBean) notessession.getAttribute("personnelBean");
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
    if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
    {
     intcmIsApplication = true;
    }

    String personnelid=notessession.getAttribute( "PERSONNELID" ) == null ? "" : notessession.getAttribute( "PERSONNELID" ).toString();

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
    Action=Action.trim();

    String radianPO=request.getParameter( "radianPO" );
    if ( radianPO == null )
      radianPO="";
    radianPO=radianPO.trim();

	String radianbPO=request.getParameter( "radianbPO" );
	if ( radianbPO == null )
	  radianbPO="";
	radianbPO=radianbPO.trim();

	if (radianbPO.length() > 0)
	{
	  radianPO = radianbPO;
	}

	String subUserAction=request.getParameter( "subUserAction" );
	if ( subUserAction == null )
	  subUserAction="";
	subUserAction=subUserAction.trim();

    String Comments=request.getParameter( "Comments" );
    if ( Comments == null )
      Comments="";
    Comments=Comments.trim();

    //System.out.println("Here radianPO  " + radianPO + " Comments   "+Comments);

    if ( "view".equalsIgnoreCase( Action ))
    {
       buildviewpoNotes( radianPO,"",subUserAction ,out);
    }
    else if ( "Update".equalsIgnoreCase( Action ) )
    {
       buildaddNotes( personnelid,radianPO,Comments,false,subUserAction ,out);
    }
    else if ( "emailview".equalsIgnoreCase( Action ) )
    {
       buildviewemailNotes( radianPO,"",subUserAction ,out);
    }
    else if ( "Updateemail".equalsIgnoreCase( Action ) )
    {
       buildaddNotes( personnelid,radianPO,Comments,true,subUserAction ,out);
    }

    out.close();
  }

  public void buildaddNotes( String personnelID,String radianPO1,String Comments1,boolean emailnotes,String subusrAction,PrintWriter out )
  {
    boolean updatesuccess = false;
    //StringBuffer Msgn=new StringBuffer();
    String errmsg = "An error occuered please check the length and try again <BR>";

    try
    {
      Comments1=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( Comments1 );
      //Comments1=HelpObjs.findReplace( Comments1,"\n","<BR>" );
      //if ( Comments1.length() > 4000 ) {Comments1=Comments1.substring( 0,3999 );}

      if (emailnotes)
      {
        if ("bpo".equalsIgnoreCase(subusrAction))
		{
		  db.doQuery( "insert into BPO_EMAIL (BPO,USER_ID,EMAIL_DATE,EMAIL) values (" + radianPO1 + "," + personnelID + ",sysdate,'" + Comments1 + "')" );
		}
		else
		{
		  db.doQuery( "insert into PO_EMAIL (RADIAN_PO,USER_ID,EMAIL_DATE,EMAIL) values (" + radianPO1 + "," + personnelID + ",sysdate,'" + Comments1 + "')" );
		}
      }
      else
      {
		if ("bpo".equalsIgnoreCase(subusrAction))
		{
		  db.doQuery( "insert into BPO_NOTES (BPO,USER_ID,NOTE_DATE,NOTE) values (" + radianPO1 + "," + personnelID + ",sysdate,'" + Comments1 + "')" );
		}
		else
		{
		  db.doQuery( "insert into po_notes (RADIAN_PO,USER_ID,NOTE_DATE,NOTE) values (" + radianPO1 + "," + personnelID + ",sysdate,'" + Comments1 + "')" );
		}
      }
      updatesuccess = true;
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      updatesuccess = false;
      errmsg += e.getMessage();
    }

    if (updatesuccess)
    {
      //StringBuffer Msgbody=new StringBuffer();

      nematid_Servlet=bundle.getString( "PURCHASE_ORDER_PO" );
      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Notes","",intcmIsApplication ) );
      printJavaScripts(out);
      out.println( "// -->\n </SCRIPT>\n" );

      //StringBuffer Msgtemp=new StringBuffer();
      out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
      out.println( "<!--\n" );
      out.println( "function sendSupplierData()\n" );
      out.println( "{\n" );
      out.println( "window.close();\n" );
      out.println( " }\n" );
      out.println( "// -->\n</SCRIPT>\n" );
      //out.println( Msgtemp );
      out.println( "<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n" );
	  out.println( "<FORM METHOD=\"POST\" id=\"revDateLike\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
      out.println( "<CENTER><BR><BR>\n" );
      out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
      out.println( "</FORM></BODY></HTML>\n" );
      //out.println( Msgbody );

    }
    else
    {
      if (emailnotes)
      {
        buildviewemailNotes( radianPO1,errmsg,subusrAction,out );
      }
      else
      {
        buildviewpoNotes( radianPO1,errmsg,subusrAction,out );
      }
    }

    return;
   }

   public void buildviewpoNotes( String radianPO,String message,String subusrAction,PrintWriter out )
   {
     //StringBuffer Msgn=new StringBuffer();
     DBResultSet dbrs=null;
     ResultSet rs=null;

     nematid_Servlet=bundle.getString( "PURCHASE_ORDER_PONOTES" );
     out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Notes","",intcmIsApplication ) );
     printJavaScripts(out);
     out.println( "// -->\n </SCRIPT>\n" );
     out.println( "<BODY onload=\"javascript:window.resizeTo(700,500)\">\n" );
     out.println( "<FORM METHOD=\"POST\" id=\"SupplierLike\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"Action\" NAME=\"Action\" VALUE=\"Update\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"radianPO\" NAME=\"radianPO\" VALUE=\"" + radianPO + "\">\n" );
	 out.println( "<INPUT TYPE=\"hidden\" ID=\"subUserAction\" NAME=\"subUserAction\" VALUE=\"" + subusrAction + "\">\n" );
     out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\" CLASS=\"moveup\">\n" );
     out.println( "<TR><TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>PO:</B></TD><TD WIDTH=\"50%\" COLSPAN=\"2\">\n" );
     out.println( "&nbsp;" + radianPO + "\n" );
     out.println( "</TD></TR>\n" );
	 out.println( "</TABLE>\n" );
  	 out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	 try
	 {
	   if ( this.getupdateStatus() )
	   {
		 out.println( "<TR>\n" );
		 out.println( "<TD COLSPAN=\"3\" CLASS=\"announce\"><BR><B>" + message + "</B><BR></TD>\n" );
		 out.println( "</TR>\n" );
		 out.println( "<TR>\n" );
		 out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Add Notes:</B></TD>\n" );
		 out.println( "<TD WIDTH=\"50%\" COLSPAN=\"2\" CLASS=\"announce\">\n" );
		 out.println( "<TEXTAREA name=\"Comments\" rows=\"5\" cols=\"100\"></TEXTAREA></TD>\n" );
		 out.println( "</TD>\n" );
		 out.println( "</TR>\n" );
		 out.println( "</TABLE>\n" );
		 out.println( "<CENTER><BR><BR>\n" );
		 out.println( "<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Add Notes\" id=\"CreateNew\" name=\"CreateNew\">\n" );
		 out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	   }
	   else
	   {
		 out.println( "</TABLE><CENTER><BR><BR>\n" );
		 out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Close\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	   }
	 }
	 catch ( Exception ex )
	 {
	 }

	 out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\" CLASS=\"moveup\">\n" );
     out.println( "<TR>\n" );
     out.println( "<TH WIDTH=\"5%\" HEIGHT=\"30\"><B>User</B></TH>\n" );
     out.println( "<TH WIDTH=\"5%\" HEIGHT=\"30\"><B>Note Date</B></TH>\n" );
     out.println( "<TH WIDTH=\"45%\" HEIGHT=\"30\"><B>Note</B></TH>\n" );
     out.println( "</TR>\n" );

     String radian_po="";
     int count=0;

     try
     {
	   String poquery="";
	   if ("bpo".equalsIgnoreCase(subusrAction))
	   {
		 poquery="Select BPO RADIAN_PO,USER_ID,to_char(NOTE_DATE,'mm/dd/yyyy hh24:mi')NOTE_DATE1,NOTE_DATE,NOTE,NOTES_USER_NAME from bpo_notes_view where BPO = " + radianPO + " order by NOTE_DATE desc";
	   }
	   else
	   {
		 poquery="Select RADIAN_PO,USER_ID,to_char(NOTE_DATE,'mm/dd/yyyy hh24:mi')NOTE_DATE1,NOTE_DATE,NOTE,NOTES_USER_NAME from po_notes_view where radian_po = " + radianPO + " order by NOTE_DATE desc";
	   }

       dbrs=db.doQuery( poquery );
       rs=dbrs.getResultSet();

       while ( rs.next() )
       {
         count+=1;
         radian_po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ).trim();
         //String user_id=rs.getString( "USER_ID" ) == null ? "" : rs.getString( "USER_ID" ).trim();
         String note_date=rs.getString( "NOTE_DATE1" ) == null ? "" : rs.getString( "NOTE_DATE1" ).trim();
         String note=rs.getString( "NOTE" ) == null ? "" : rs.getString( "NOTE" ).trim();
         String noteusername=rs.getString( "NOTES_USER_NAME" ) == null ? "" : rs.getString( "NOTES_USER_NAME" ).trim();
		 note = HelpObjs.findReplace(note,"\n","<BR>");

         String Color=" ";
         if ( count % 2 == 0 )
         {
           Color="CLASS=\"Inblue";
         }
         else
         {
           Color="CLASS=\"Inwhite";
         }

         out.println( "<TR ALIGN=\"MIDDLE\" BORDERCOLOR=\"black\">\n" );
         out.println( "<TD " + Color + "\" WIDTH=\"5%\" HEIGHT=\"30\">" );
         out.println( noteusername );
         out.println( "</TD>\n" );
         out.println( "<TD " + Color + "l\" WIDTH=\"5%\" HEIGHT=\"30\">" );
         out.println( note_date );
         out.println( "</TD>\n" );
         out.println( "<TD " + Color + "l\" WIDTH=\"45%\" HEIGHT=\"30\">" );
         out.println( note );
         out.println( "</TD>\n" );
         out.println( "</TR>\n" );
       }
     }
     catch ( Exception e )
     {
       e.printStackTrace();
     }
     finally
     {
        if (dbrs != null)  { dbrs.close(); }
     }

	 out.println( "</TABLE>\n" );
     out.println( "</FORM></BODY></HTML>\n" );

     return;
   }

   public void buildviewemailNotes( String radianPO,String message,String subusrAction,PrintWriter out )
   {
     //StringBuffer Msgn=new StringBuffer();
     DBResultSet dbrs=null;
     ResultSet rs=null;

     nematid_Servlet=bundle.getString( "PURCHASE_ORDER_PONOTES" );
     out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Email Notes","",intcmIsApplication ) );
     printJavaScripts(out);
     out.println( "// -->\n </SCRIPT>\n" );

     out.println( "<BODY onload=\"javascript:window.resizeTo(700,500)\">\n" );
     out.println( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Updateemail\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"Action\" NAME=\"Action\" VALUE=\"Updateemail\">\n" );
     out.println( "<INPUT TYPE=\"hidden\" ID=\"radianPO\" NAME=\"radianPO\" VALUE=\"" + radianPO + "\">\n" );
	 out.println( "<INPUT TYPE=\"hidden\" ID=\"subUserAction\" NAME=\"subUserAction\" VALUE=\"" + subusrAction + "\">\n" );
     out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\" CLASS=\"moveup\">\n" );
     out.println( "<TR><TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>PO:</B></TD><TD WIDTH=\"50%\" COLSPAN=\"2\">\n" );
     out.println( "&nbsp;" + radianPO + "\n" );
     out.println( "</TD></TR>\n" );
     out.println( "</TABLE>\n" );
	 out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
	 try
	 {
	   if ( this.getupdateStatus() )
	   {
		 out.println( "<TR>\n" );
		 out.println( "<TD COLSPAN=\"3\" CLASS=\"announce\"><BR><B>" + message + "</B><BR></TD>\n" );
		 out.println( "</TR>\n" );
		 out.println( "<TR>\n" );
		 out.println( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Email Notes:</B></TD>\n" );
		 out.println( "<TD WIDTH=\"50%\" COLSPAN=\"2\" CLASS=\"announce\">\n" );
		 out.println( "<TEXTAREA name=\"Comments\" rows=\"12\" cols=\"100\"></TEXTAREA></TD>\n" );
		 out.println( "</TD>\n" );
		 out.println( "</TR>\n" );
		 out.println( "</TABLE>\n" );
		 out.println( "<CENTER><BR><BR>\n" );
		 out.println( "<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Add Email Notes\" id=\"CreateNew\" name=\"CreateNew\">\n" );
		 out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	   }
	   else
	   {
		 out.println( "</TABLE><CENTER><BR><BR>\n" );
		 out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Close\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
	   }
	 }
	 catch ( Exception ex )
	 {
	 }

	 out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\" CLASS=\"moveup\">\n" );
	 out.println( "<TR>\n" );
	 out.println( "<TH WIDTH=\"5%\" HEIGHT=\"30\"><B>User</B></TH>\n" );
	 out.println( "<TH WIDTH=\"5%\" HEIGHT=\"30\"><B>Note Date</B></TH>\n" );
	 out.println( "<TH WIDTH=\"45%\" HEIGHT=\"30\"><B>Note</B></TH>\n" );
	 out.println( "</TR>\n" );

	 String radian_po="";
	 int count=0;

	 try
	 {
	   String poquery="";
	   if ("bpo".equalsIgnoreCase(subusrAction))
	   {
		 poquery="Select BPO RADIAN_PO,USER_ID,to_char(EMAIL_DATE,'mm/dd/yyyy hh24:mi')EMAIL_DATE1,EMAIL_DATE,EMAIL,EMAIL_USER_NAME from BPO_EMAIL_VIEW where BPO = " + radianPO + " order by EMAIL_DATE desc";
	   }
	   else
	   {
		 poquery="Select RADIAN_PO,USER_ID,to_char(EMAIL_DATE,'mm/dd/yyyy hh24:mi')EMAIL_DATE1,EMAIL_DATE,EMAIL,EMAIL_USER_NAME from PO_EMAIL_VIEW where radian_po = " + radianPO + " order by EMAIL_DATE desc";
	   }

	   dbrs=db.doQuery( poquery );
	   rs=dbrs.getResultSet();

	   while ( rs.next() )
	   {
		 count+=1;
		 radian_po=rs.getString( "RADIAN_PO" ) == null ? "" : rs.getString( "RADIAN_PO" ).trim();
		 //String user_id=rs.getString( "USER_ID" ) == null ? "" : rs.getString( "USER_ID" ).trim();
		 String note_date=rs.getString( "EMAIL_DATE1" ) == null ? "" : rs.getString( "EMAIL_DATE1" ).trim();
		 String note=rs.getString( "EMAIL" ) == null ? "" : rs.getString( "EMAIL" ).trim();
		 String noteusername=rs.getString( "EMAIL_USER_NAME" ) == null ? "" : rs.getString( "EMAIL_USER_NAME" ).trim();
		 note = HelpObjs.findReplace(note,"\n","<BR>");

		 String Color=" ";
		 if ( count % 2 == 0 )
		 {
		   Color="CLASS=\"Inblue";
		 }
		 else
		 {
		   Color="CLASS=\"Inwhite";
		 }

		 out.println( "<TR ALIGN=\"MIDDLE\" BORDERCOLOR=\"black\">\n" );
		 out.println( "<TD " + Color + "\" WIDTH=\"5%\" HEIGHT=\"30\">" );
		 out.println( noteusername );
		 out.println( "</TD>\n" );
		 out.println( "<TD " + Color + "l\" WIDTH=\"5%\" HEIGHT=\"30\">" );
		 out.println( note_date );
		 out.println( "</TD>\n" );
		 out.println( "<TD " + Color + "l\" WIDTH=\"45%\" HEIGHT=\"30\">" );
		 out.println( note );
		 out.println( "</TD>\n" );
		 out.println( "</TR>\n" );
	   }
	 }
	 catch ( Exception e )
	 {
	   e.printStackTrace();
	 }
	 finally
	 {
		if (dbrs != null)  { dbrs.close(); }
	 }

	 out.println( "</TABLE>\n" );
     out.println( "</FORM></BODY></HTML>\n" );

     return;
   }

   private void printJavaScripts(PrintWriter out)
   {
     //StringBuffer Msglt=new StringBuffer();

     out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
     out.println( "<!--\n" );
     out.println( "function cancel()\n" );
     out.println( "{\n" );
     out.println( "window.close();\n" );
     out.println( "}\n" );

     return;
   }
}