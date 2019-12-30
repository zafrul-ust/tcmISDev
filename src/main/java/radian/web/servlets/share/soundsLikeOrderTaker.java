package radian.web.servlets.share;

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

//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
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
 *
 * 11-01-02
 * Matched the input fields for New OrderTaker with the lengths in Database
 *
 * 11-12-02
 * making at least one of first name, last name or nickname not null
 * and also they can edit Main contact now
 *
 * 02-18-03
 * Buyers can not change the Main phone number or Fax
 * 08-27-03 Code cleanup and removed true from request.getsession()
 * 12-22-03 - Making the session Keys Unique to PO pages.
 * 03-17-04 - Showing email address of supplier contacts
 * 05-19-04 - not setting validcarrier as Yes when a order taker is selected
 */


public class soundsLikeOrderTaker
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
	private PrintWriter out = null;
    public soundsLikeOrderTaker(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

     public void doResult(HttpServletRequest request, HttpServletResponse response)
          throws ServletException,  IOException
    {
    out = response.getWriter();
    response.setContentType("text/html");

    HttpSession ordtakssion=request.getSession();

    String CompanyId=request.getParameter( "Company" );
    if ( CompanyId == null )
      CompanyId="";

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";

    String contactId=request.getParameter( "contactid" );
    if ( contactId == null )
      contactId="";
    contactId=contactId.trim();

    String Ordertaker=request.getParameter( "Ordertaker" );
    if ( Ordertaker == null )
      Ordertaker="";

    String suppID=request.getParameter( "suppID" );
    if ( suppID == null )
        suppID="";

    if ("okupdate".equalsIgnoreCase(Action))
    {
    buildsendSupplier("",suppID);
    }
    if ("Update".equalsIgnoreCase(Action))
    {
    createorderTaker(request);
    }

    if ("editthis".equalsIgnoreCase(Action))
    {
    Vector vvFob = (Vector)ordtakssion.getAttribute("PO_CONTACTTYPE");
    buildNewSupplier(suppID,vvFob,contactId);
    }
    else if ("showallcontacts".equalsIgnoreCase(Action))
    {
    buildshowallcontacts(suppID);
    }
    else if ("deletethis".equalsIgnoreCase(Action))
    {
    builddeletethis(suppID,contactId);
    }
    else if ("New".equalsIgnoreCase(Action))
    {
     Vector vvFob = (Vector)ordtakssion.getAttribute("PO_CONTACTTYPE");
     buildNewSupplier(suppID,vvFob,"");
    }
    else if ("Search".equalsIgnoreCase(Action))
    {
    buildLikeSupplier("Search",Ordertaker,suppID);
    }
    else
    {
    buildLikeSupplier("",Ordertaker,suppID);
    }
    out.close();
  }

  public void createorderTaker(HttpServletRequest request1)
  {
    boolean result = false;
    //StringBuffer Msgn = new StringBuffer();
    DBResultSet dbrs = null;
    ResultSet rs = null;

    String suppID = "";
    try{suppID = request1.getParameter("suppID").toString();}catch (Exception e1){suppID = "";}
	suppID = suppID.trim();

    String firstname = "";
    try{firstname = request1.getParameter("firstname").toString();}catch (Exception e1){firstname = "";}
	firstname = firstname.trim();

    String lastname = "";
    try{lastname = request1.getParameter("lastname").toString();}catch (Exception e1){lastname = "";}
		lastname = lastname.trim();
		lastname = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(lastname);

    String nickname = "";
		try{nickname = request1.getParameter("nickname").toString();}catch (Exception e1){nickname = "";}
		nickname = nickname.trim();
		nickname = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(nickname);

    String phone = "";
    try{phone = request1.getParameter("phone").toString();}catch (Exception e1){phone = "";}
		phone = phone.trim();

    String phoneext = "";
    try{phoneext = request1.getParameter("phoneext").toString();}catch (Exception e1){phoneext = "";}
		phoneext = phoneext.trim();

    String fax = "";
    try{fax = request1.getParameter("fax").toString();}catch (Exception e1){fax = "";}
	fax = fax.trim();

    String email = "";
    try{email = request1.getParameter("email").toString();}catch (Exception e1){email = "";}
	email = email.trim();
	email = radian.web.HTMLHelpObj.changeSingleQuotetoTwoSingleQuote(email);

    String contacttype = "";
    try{contacttype = request1.getParameter("contacttype").toString();}catch (Exception e1){contacttype = "";}
	contacttype = contacttype.trim();

    String contactId = "";
    try{contactId = request1.getParameter("contactid").toString();}catch (Exception e1){contactId = "";}
    contactId = contactId.trim();
    boolean undateorInsert = false;
    try
    {
      if ( contactId.length() > 0 )
      {
        undateorInsert=true;
      }
      else
      {
        dbrs=db.doQuery( "select nvl(max(contact_id),0)+1 contact_id from supplier_contact where SUPPLIER = '" + suppID + "' " );
        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          contactId=rs.getString( "CONTACT_ID" ) == null ? "" : rs.getString( "CONTACT_ID" ).trim();
        }
      }

	  if ( (suppID.length() > 0) || (contactId.length() > 0) )
	  {
	    String updItemquery = "";
	    if (undateorInsert)
	    {
	      updItemquery = "update supplier_contact set SUPPLIER='"+suppID+"',CONTACT_ID="+contactId+",CONTACT_TYPE='"+contacttype+"',FIRST_NAME='"+firstname+"',LAST_NAME='"+lastname+"',NICKNAME='"+nickname+"',PHONE='"+phone+"',PHONE_EXTENSION='"+phoneext+"',FAX='"+fax+"',EMAIL='"+email+"' where SUPPLIER = '"+suppID+"' and CONTACT_ID = '"+contactId+"'";
	    }
	    else
	    {
	      updItemquery = "insert into supplier_contact (SUPPLIER,CONTACT_ID,CONTACT_TYPE,FIRST_NAME,LAST_NAME,NICKNAME,PHONE,PHONE_EXTENSION,FAX,EMAIL) values ('"+suppID+"',"+contactId+",'"+contacttype+"','"+firstname+"','"+lastname+"','"+nickname+"','"+phone+"','"+phoneext+"','"+fax+"','"+email+"')";
	    }
	   db.doUpdate(updItemquery);
	  }

	  result = true;
      }
      catch (Exception e)
      {
	  e.printStackTrace();
	  result = false;
      }
      finally
      {
          if ( dbrs != null )
          {
            dbrs.close();
          }
      }

    if (!("In Active".equalsIgnoreCase(contacttype) || undateorInsert))
    {
     buildsendSupplier(nickname,suppID);
    }
    return;

  }

   public void builddeletethis(String supplierID,String contactId)
   {
     if ( contactId.length() > 0 )
     {
       try
       {
         db.doUpdate( "delete from supplier_contact where SUPPLIER = '" + supplierID + "' and CONTACT_ID = '" + contactId + "' and CONTACT_TYPE <> 'In Active'" );
       }
       catch ( Exception e )
       {
         e.printStackTrace();
       }

     }
      buildLikeSupplier("","",supplierID);
	  return;
   }

   public void buildNewSupplier(String supplierID,Vector vvcontacttype,String contactId)
   {
    //StringBuffer Msgn = new StringBuffer();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector contactType=new Vector();
    for ( int z=0; z < vvcontacttype.size(); z++ )
    {
      Hashtable data1= ( Hashtable ) vvcontacttype.elementAt( z );
      Enumeration E;
      for ( E=data1.keys(); E.hasMoreElements(); )
      {
        String key= ( String ) E.nextElement();
        String keyvalue=data1.get( key ).toString();
        if ( !"Main".equalsIgnoreCase( keyvalue ) )
        {
          contactType.addElement( data1 );
        }
      }
    }

    nematid_Servlet = bundle.getString("PURCHASE_ORDER_ORDERTAKER");
    out.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order New Order Taker","ordertaker.js",false));
    printJavaScripts();
    out.println("// -->\n </SCRIPT>\n");

    String supplier = "";
    String contact_id = "";
    String contact_type = "";
    String first_name = "";
    String last_name = "";
    String nickname = "";
    String phone = "";
    String phone_extension = "";
    String fax = "";
    String email = "";

     if (contactId.length() > 0)
     {
	  try
	 {
	    dbrs = db.doQuery("select * from supplier_contact where SUPPLIER = '"+supplierID+"' and CONTACT_ID = '"+contactId+"' and CONTACT_TYPE <> 'In Active'");
	    rs=dbrs.getResultSet();

	     while(rs.next())
	    {
	       supplier = rs.getString("SUPPLIER")==null?"":rs.getString("SUPPLIER").trim();
	       contact_id = rs.getString("CONTACT_ID")==null?"":rs.getString("CONTACT_ID").trim();
	       contact_type = rs.getString("CONTACT_TYPE")==null?"":rs.getString("CONTACT_TYPE").trim();
	       first_name = rs.getString("FIRST_NAME")==null?"":rs.getString("FIRST_NAME").trim();
	       last_name = rs.getString("LAST_NAME")==null?"":rs.getString("LAST_NAME").trim();
	       nickname = rs.getString("NICKNAME")==null?"":rs.getString("NICKNAME").trim();
	       phone = rs.getString("PHONE")==null?"":rs.getString("PHONE").trim();
	       phone_extension = rs.getString("PHONE_EXTENSION")==null?"":rs.getString("PHONE_EXTENSION").trim();
	       fax = rs.getString("FAX")==null?"":rs.getString("FAX").trim();
	       email = rs.getString("EMAIL")==null?"":rs.getString("EMAIL").trim();
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
     }

	if ("Main".equalsIgnoreCase(contact_type) && first_name.length() == 0){first_name = " ";}
	if ("Main".equalsIgnoreCase(contact_type) && last_name.length() == 0){last_name = " ";}

    out.println("<BODY>\n");
    out.println("<FORM METHOD=\"POST\" id=\"SupplierLike\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
    out.println("<INPUT TYPE=\"hidden\" ID=\"Action\" NAME=\"Action\" VALUE=\"Update\">\n");
    out.println("<INPUT TYPE=\"hidden\" ID=\"suppId\" NAME=\"suppID\" VALUE=\""+supplierID+"\">\n");
    out.println("<INPUT TYPE=\"hidden\" ID=\"contactid\" NAME=\"contactid\" VALUE=\""+contactId+"\">\n");
    out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\">\n");
    out.println("<TR><TD WIDTH=\"15%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Supplier:&nbsp;</B></TD><TD WIDTH=\"45%\">\n");
    out.println("&nbsp;"+supplierID+"\n");
    out.println("</TD></TR>\n");
    out.println("<TR><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>\n");
    out.println("<TR><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD></TR>\n");
    out.println("<TR>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>First Name:  </B></TD>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"firstname\" name=\"firstname\" value=\""+first_name+"\" SIZE=\"20\" MAXLENGTH=\"30\">\n</TD>\n");
    out.println("</TR>\n");
    out.println("<TR>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Last Name:  </B></TD>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"lastname\" name=\"lastname\" value=\""+last_name+"\" SIZE=\"20\" MAXLENGTH=\"30\">\n</TD>\n");
    out.println("</TR>\n");
    out.println("<TR>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Nick Name:  </B></TD>\n");
    /*if (contactId.length() > 0)
    {
      out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT CLASS=\"INVISIBLEHEADBLUE\" type=\"text\" name=\"nickname\" value=\""+nickname+"\" SIZE=\"10\" readonly>\n</TD>\n");
    }
    else*/
    {
      out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"nickname\" name=\"nickname\" value=\""+nickname+"\" SIZE=\"10\" MAXLENGTH=\"30\">\n</TD>\n");
    }
    out.println("</TR>\n");

    out.println("<TR>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Phone:  </B></TD>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"phone\" name=\"phone\" value=\""+phone+"\" SIZE=\"15\" MAXLENGTH=\"30\">&nbsp;&nbsp;&nbsp;<B>Ext:  </B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" type=\"text\" id=\"phoneext\" name=\"phoneext\" value=\""+phone_extension+"\" SIZE=\"5\" MAXLENGTH=\"30\">\n</TD>\n");
    out.println("</TR>\n");

    out.println("<TR>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Fax:  </B></TD>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"fax\" name=\"fax\" value=\""+fax+"\" SIZE=\"15\" MAXLENGTH=\"30\">\n</TD>\n");
    out.println("</TR>\n");

    out.println("<TR>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Email:  </B></TD>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"email\" name=\"email\" value=\""+email+"\" SIZE=\"20\" MAXLENGTH=\"40\">\n</TD>\n");
    out.println("</TR>\n");

    out.println("<TR>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Contact Type:  </B></TD>\n");
    //out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"contacttype\" value=\"\" SIZE=\"20\">\n</TD>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\">\n");
    if ("Main".equalsIgnoreCase(contact_type))
    {
	  out.println("<INPUT CLASS=\"INVISIBLEHEADBLUE\" type=\"text\" id=\"contacttype\" name=\"contacttype\" value=\""+contact_type+"\" SIZE=\"15\" MAXLENGTH=\"30\" readonly>\n");
      //out.println("<SELECT CLASS=\"HEADER\" NAME=\"contacttype\" size=\"1\" disabled>\n");
    }
    else
    {
	  out.println( "<SELECT CLASS=\"HEADER\" ID=\"contacttype\" NAME=\"contacttype\" size=\"1\">\n" );
	  if ( ! ( contact_type.length() > 0 ) )
	  {
		contact_type="Order";
	  }

	  out.println( radian.web.HTMLHelpObj.getDropDown( contactType,contact_type ) );
    }

    out.println("</SELECT>\n");
    out.println("</TD>\n");
    out.println("</TR>\n");
    out.println("</TABLE>\n");
    out.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" onclick= \"return checkContactvalues()\" value=\"OK\" id=\"CreateNew\" name=\"CreateNew\">\n");
    //out.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" name=\"CreateNew\" onclick= \"return actionValue(name,this)\">\n");
    out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    out.println("</FORM></BODY></HTML>\n");

    return;
   }

    public void buildsendSupplier(String ordertakernickname, String supplierID)
   {
    //StringBuffer Msgn = new StringBuffer();
    //StringBuffer Msgbody = new StringBuffer();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    nematid_Servlet = bundle.getString("PURCHASE_ORDER_ORDERTAKER");

    out.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",false));
    printJavaScripts();
    out.println("// -->\n </SCRIPT>\n");
    //StringBuffer Msgtemp = new StringBuffer();

    //Build the Java Script Here and Finish the thing
    out.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.println("<!--\n");
    out.println("function sendSupplierData()\n");
    out.println("{\n");

    try
    {
      dbrs=db.doQuery( "select * from supplier_contact where SUPPLIER = '" + supplierID + "' and lower(NICKNAME) = lower('" +  BothHelpObjs.changeSingleQuotetoTwoSingleQuote(ordertakernickname) + "') and CONTACT_TYPE <> 'In Active'" );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        //String supplier = rs.getString("SUPPLIER")==null?"":rs.getString("SUPPLIER").trim();
        String contact_id=rs.getString( "CONTACT_ID" ) == null ? "" : rs.getString( "CONTACT_ID" ).trim();
        //String contact_type = rs.getString("CONTACT_TYPE")==null?"":rs.getString("CONTACT_TYPE").trim();
        //String first_name = rs.getString("FIRST_NAME")==null?"":rs.getString("FIRST_NAME").trim();
        //String last_name = rs.getString("LAST_NAME")==null?"":rs.getString("LAST_NAME").trim();
        String nickname=rs.getString( "NICKNAME" ) == null ? "" : rs.getString( "NICKNAME" ).trim();
        String phone=rs.getString( "PHONE" ) == null ? "" : rs.getString( "PHONE" ).trim();
        String phone_extension=rs.getString( "PHONE_EXTENSION" ) == null ? "" : rs.getString( "PHONE_EXTENSION" ).trim();
        String fax=rs.getString( "FAX" ) == null ? "" : rs.getString( "FAX" ).trim();
        //String email = rs.getString("EMAIL")==null?"":rs.getString("EMAIL").trim();

        String totalphone="";
        if ( phone_extension.trim().length() > 0 )
        {
          totalphone=phone + " X " + phone_extension;
        }
        else
        {
          totalphone=phone;
        }

        out.println( "selectedRow = opener.document.getElementById(\"ordertaker\");\n" );
        out.println( "selectedRow.className = \"HEADER\";\n" );
        //out.println("selectedRow.value = \""+first_name+","+last_name+"\";\n");
        out.println( "selectedRow.value = \"" + HelpObjs.addescapesForJavascript(nickname) + "\";\n" );

        out.println( "selectedRow = opener.document.getElementById(\"ordertakerID\");\n" );
        out.println( "selectedRow.value = \"" + contact_id + "\";\n" );

        out.println( "selectedRow = opener.document.getElementById(\"validordertaker\");\n" );
        out.println( "selectedRow.value = \"Yes\";\n" );

        out.println( "contactphoneno = opener.document.getElementById(\"contactphoneno\");\n" );
        out.println( "contactphoneno.value = \"" + totalphone + "\";\n" );

        out.println( "contactfaxno11 = opener.document.getElementById(\"contactfaxno\");\n" );
        out.println( "contactfaxno11.value = \"" + fax + "\";\n" );

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

    out.println("window.close();\n");
    out.println(" }\n");
    out.println("// -->\n</SCRIPT>\n");
    //out.println(Msgtemp);
    //out.println("<BODY><BR><BR>\n");
    out.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
	out.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
	out.println("<INPUT TYPE=\"hidden\" ID=\"material\" NAME=\"material\" VALUE=\""+ordertakernickname+"\">\n");
	out.println("<INPUT TYPE=\"hidden\" ID=\"revisionDate\" NAME=\"revisionDate\" VALUE=\""+supplierID+"\">\n");
    out.println("<CENTER><BR><BR>\n");
    out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    out.println("</FORM></BODY></HTML>\n");
    //out.println(Msgbody);

    return;
   }

     public void buildLikeSupplier( String SearchFlag, String Ordertaker,String supplierID)
     {
      //StringBuffer Msgl = new StringBuffer();
      supplierID = supplierID.trim();
      DBResultSet dbrs = null;
      ResultSet rs = null;
      boolean foundsupplier = false;

      if ( ( supplierID.trim().length() > 0 ) && ( Ordertaker.length() > 0 ) && ( !"Search".equalsIgnoreCase( SearchFlag ) ) )
      {
        try
        {
          int test_number=DbHelpers.countQuery( db,"select count(*) from supplier_contact where SUPPLIER = '" + supplierID + "' and lower(NICKNAME) = lower('" +  BothHelpObjs.changeSingleQuotetoTwoSingleQuote(Ordertaker) + "') and CONTACT_TYPE <> 'In Active'" );
          //int test_number = DbHelpers.countQuery(db,"select count(*) from supplier_contact where CONTACT_TYPE <> 'Main' and SUPPLIER = '"+supplierID+"' and lower(NICKNAME) = lower('"+Ordertaker+"') and CONTACT_TYPE <> 'In Active'");

          if ( test_number == 1 )
          {
            foundsupplier=true;

          }
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    out.println("<BODY><BR><BR>\n");
	    out.println("<FONT FACE=\"Arial\" SIZE=\"3\">Error Occured.<BR>\n");
	    out.println("Please Try Again.\n");
	    out.println("<CENTER><BR><BR>\n");
	    out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
	    out.println("</FORM></BODY></HTML>\n");
	    return;
	}
  }

  if (foundsupplier)
  {
	buildsendSupplier(Ordertaker,supplierID);
	return;
  }
      nematid_Servlet = bundle.getString("PURCHASE_ORDER_ORDERTAKER");

      out.println(radian.web.HTMLHelpObj.printHTMLHeader("Purchase Order Order Taker","",false));
      printJavaScripts();
      out.println("function ShowSearch(name,entered)\n");
      out.println("{\n");
      out.println(" with (entered)\n");
      out.println(" {\n");
      out.println(" loc = \""+nematid_Servlet+"&Action=\" + name.toString() + \"&Ordertaker=\";\n");
      out.println(" loc = loc + window.document.SupplierLike.Ordertaker.value;\n");
      out.println("var supplierrep = window.document.SupplierLike.supplierid1.value;\n");
      out.println("supplierrep = supplierrep.replace(/&/gi, \"%26\");\n");
      out.println(" loc = loc + \"&suppID=\" + supplierrep; \n");
      out.println(" loc = loc + \"&contactid=\"; \n");
      out.println(" loc = loc + window.document.SupplierLike.contactid.value;\n");
      out.println(" }\n");
      out.println(" if (name.toString() == \"editthis\") \n");
      out.println(" {\n");
      out.println("  if (window.document.SupplierLike.contactid.value.length > 0) \n");
      out.println("  {\n");
      out.println("   window.location.replace(loc);\n");
      out.println("  }\n");
      out.println("  else\n");
      out.println("  {\n");
      out.println("   alert(\"Please Select a Contact to Edit.\");\n");
      out.println("  }\n");
      out.println(" }\n");
      out.println(" else\n");
      out.println(" {\n");
      out.println("  window.location.replace(loc);\n");
      out.println(" }\n");
      out.println(" }\n");
      out.println("// -->\n </SCRIPT>\n");

      out.println("<BODY onload=\"javascript:window.resizeTo(650,420)\">\n");
      out.println("<FORM METHOD=\"POST\" id=\"SupplierLike\" name=\"SupplierLike\" action=\""+nematid_Servlet+"Session=Update\">\n");
      out.println("<INPUT TYPE=\"hidden\" ID=\"supplierid1\" NAME=\"supplierid1\" VALUE=\""+supplierID+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" ID=\"contactname\" NAME=\"contactname\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" ID=\"contactid\" NAME=\"contactid\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" ID=\"contactphoneno\" NAME=\"contactphoneno\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" ID=\"contactfaxno\" NAME=\"contactfaxno\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"contacttype\" ID=\"contacttype\" VALUE=\"\">\n");
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"contactemail\" ID=\"contactemail\" VALUE=\"\">\n" );

      out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.println("<TR>\n");
      out.println("<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Search Text:</B></TD>\n");

      out.println("<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" id=\"Ordertaker\" name=\"Ordertaker\" value=\""+Ordertaker+"\" SIZE=\"25\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"Search\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n");
      out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"editthis\" name=\"editthis\" value=\"Edit\" OnClick=\"ShowSearch(name,this)\">&nbsp;&nbsp;&nbsp;&nbsp;\n");
      //out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"deletethis\" value=\"Delete\" OnClick=\"ShowSearch(name,this)\">&nbsp;&nbsp;&nbsp;&nbsp;\n");
      out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"New\" name=\"New\" value=\"New\" OnClick=\"ShowSearch(name,this)\"></TD></TR>\n");

      out.println("<TR><TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Contact Name:</B></FONT></TD><TD WIDTH=\"70%\" COLSPAN=\"2\"><INPUT CLASS=\"INVISIBLEHEADWHITE\" type=\"text\" id=\"supplierid\" name=\"supplierid\" value=\"\" SIZE=\"25\" readonly>\n");
      out.println("</TR>\n");

      out.println("</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.println("<TR>\n");
      out.println("<TH WIDTH=\"10%\"><B>Contact Name</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"10%\"><B>Nick Name</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"5%\"><B>Contact Type</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"10%\"><B>Contact Phone - Ext</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"10%\"><B>Fax</B></FONT></TH>\n");
	  out.println("<TH WIDTH=\"20%\"><B>Email</B></FONT></TH>\n");
      out.println("</TR></TABLE>\n");

      //open scrolling table
      out.println("<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
      out.println("<TBODY>\n");
      out.println("<TR>\n");
      out.println("<TD VALIGN=\"TOP\">\n");
      out.println("<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n");

      //Write code to insert rows here
      out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n");

      int totalrecords = 0;
      int count = 0;

      String query = "";

      if  ("Search".equalsIgnoreCase(SearchFlag))
      {
        query += "select * from supplier_contact where SUPPLIER = '"+supplierID+"' and ( lower(NICKNAME||LAST_NAME||FIRST_NAME||' ') like lower('%"+ BothHelpObjs.changeSingleQuotetoTwoSingleQuote(Ordertaker)+"%') and CONTACT_TYPE <> 'In Active')  order by NICKNAME asc";
      }
      else
      {
        query += "select * from supplier_contact where SUPPLIER = '"+supplierID+"' and ( lower(NICKNAME||LAST_NAME||FIRST_NAME||' ') like lower('%"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(Ordertaker)+"%') and CONTACT_TYPE <> 'In Active') order by NICKNAME asc";
      }

	  try
	  {
	      dbrs = db.doQuery(query);
	      rs=dbrs.getResultSet();

          while ( rs.next() )
          {
            totalrecords+=1;
            count+=1;

		//String supplier = rs.getString("SUPPLIER")==null?"":rs.getString("SUPPLIER").trim();
		String contact_id = rs.getString("CONTACT_ID")==null?"":rs.getString("CONTACT_ID").trim();
		String contact_type = rs.getString("CONTACT_TYPE")==null?"":rs.getString("CONTACT_TYPE").trim();
		String first_name = rs.getString("FIRST_NAME")==null?"":rs.getString("FIRST_NAME").trim();
		String last_name = rs.getString("LAST_NAME")==null?"":rs.getString("LAST_NAME").trim();
		String nickname = rs.getString("NICKNAME")==null?"":rs.getString("NICKNAME").trim();
		String phone = rs.getString("PHONE")==null?"":rs.getString("PHONE").trim();
		String phone_extension = rs.getString("PHONE_EXTENSION")==null?"":rs.getString("PHONE_EXTENSION").trim();
		String fax = rs.getString("FAX")==null?"":rs.getString("FAX").trim();
		String email = rs.getString("EMAIL")==null?"":rs.getString("EMAIL").trim();

		String totalphone = "";
		if (phone_extension.trim().length() > 0)
		{
		  totalphone = phone + " X " +phone_extension;
		}
		else
		{
		  totalphone = phone;
		}

		String Color = " ";
		if (count%2==0)
		{
		    Color = "BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		}
		else
		{
		   Color = " onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
		}

		out.println("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('"+HelpObjs.addescapesForJavascript(first_name)+","+HelpObjs.addescapesForJavascript(last_name)+"','"+contact_id+"','"+HelpObjs.addescapesForJavascript(nickname)+"','"+totalphone+"','"+fax+"','"+contact_type+"','"+HelpObjs.addescapesForJavascript(email)+"')\" BORDERCOLOR=\"black\">\n");

		out.println("<TD "+Color+" WIDTH=\"10%\" ALIGN=\"LEFT\">");
		out.println(""+first_name+","+last_name+"");out.println("</TD>\n");
		out.println("<TD "+Color+" WIDTH=\"12%\" ALIGN=\"LEFT\">");out.println(nickname);out.println("</TD>\n");
		out.println("<TD "+Color+" WIDTH=\"5%\" ALIGN=\"LEFT\">");out.println(contact_type);out.println("</TD>\n");
		out.println("<TD "+Color+" WIDTH=\"10%\" ALIGN=\"LEFT\">");out.println(""+totalphone+"");out.println("</TD>\n");
		out.println("<TD "+Color+" WIDTH=\"10%\" ALIGN=\"LEFT\">");out.println(""+fax+"");out.println("</TD>\n");
		out.println("<TD "+Color+" WIDTH=\"20%\" ALIGN=\"LEFT\">");out.println(""+email+"");out.println("</TD>\n");

		out.println("</TR>\n");
	      }
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    out.println("An Error Occured While Querying the Database");
	    out.println("</BODY>\n</HTML>\n");
	    return;
	}
	finally
	{
	    if (dbrs != null)  { dbrs.close(); }
	}

      if (totalrecords==0){out.println("<TR><TD>No Records Found</TD></TR>\n");}
      out.println("</TABLE>\n");

      //close scrolling table
      out.println("</DIV>\n");
      out.println("</TD>\n");
      out.println("</TR>\n");
      out.println("</TBODY>\n");
      out.println("</TABLE>\n");

      out.println("<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"okupdate\" name=\"okupdate\" value=\"Ok\" OnClick=\"sendCarrier(this.form)\" type=\"button\">\n");
      out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      out.println("</FORM></BODY></HTML>\n");

      return;
    }

    public void buildshowallcontacts( String supplierID )
    {
      //StringBuffer Msgl=new StringBuffer();
      supplierID=supplierID.trim();
      DBResultSet dbrs=null;
      ResultSet rs=null;
      nematid_Servlet=bundle.getString( "PURCHASE_ORDER_ORDERTAKER" );

      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Purchase Order Order Taker","",false ) );
      printJavaScripts();
      out.println( "// -->\n </SCRIPT>\n" );

      out.println( "<BODY onload=\"javascript:window.resizeTo(650,420)\">\n" );
      out.println( "<FORM METHOD=\"POST\" name=\"SupplierLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" ID=\"supplierid1\" NAME=\"supplierid1\" VALUE=\"" + supplierID + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" ID=\"contactname\" NAME=\"contactname\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" ID=\"contactid\" NAME=\"contactid\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" ID=\"contactphoneno\" NAME=\"contactphoneno\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" ID=\"contactfaxno\" NAME=\"contactfaxno\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"contacttype\" ID=\"contacttype\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"contactemail\" ID=\"contactemail\" VALUE=\"\">\n" );

      out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.println("<TR>\n");
      out.println("<TD WIDTH=\"20%\" CLASS=\"announce\" ALIGN=\"LEFT\"><B>Contacts for Supplier : "+supplierID+"</B></TD>\n");
      out.println("</TR>\n");
      out.println("</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.println("<TR>\n");
      out.println("<TH WIDTH=\"15%\"><B>Contact Name</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"15%\"><B>Nick Name</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"15%\"><B>Contact Type</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"15%\"><B>Contact Phone - Ext</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"15%\"><B>Fax</B></FONT></TH>\n");
	  out.println("<TH WIDTH=\"10%\"><B>Email</B></FONT></TH>\n");
		out.println("<TH WIDTH=\"5%\"><B>Contact ID</B></FONT></TH>\n");
      out.println("</TR></TABLE>\n");

      //open scrolling table
      out.println("<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
      out.println("<TBODY>\n");
      out.println("<TR>\n");
      out.println("<TD VALIGN=\"TOP\">\n");
      out.println("<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n");

      //Write code to insert rows here
      out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n");

      int totalrecords = 0;
      int count = 0;

      String query = "";
      query += "select * from supplier_contact where SUPPLIER = '"+supplierID+"' and CONTACT_TYPE <> 'In Active' order by NICKNAME asc";

      try
      {
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();

        while ( rs.next() )
        {
          totalrecords+=1;
          count+=1;

        //String supplier = rs.getString("SUPPLIER")==null?"":rs.getString("SUPPLIER").trim();
        String contact_id = rs.getString("CONTACT_ID")==null?"":rs.getString("CONTACT_ID").trim();
        String contact_type = rs.getString("CONTACT_TYPE")==null?"":rs.getString("CONTACT_TYPE").trim();
        String first_name = rs.getString("FIRST_NAME")==null?"":rs.getString("FIRST_NAME").trim();
        String last_name = rs.getString("LAST_NAME")==null?"":rs.getString("LAST_NAME").trim();
        String nickname = rs.getString("NICKNAME")==null?"":rs.getString("NICKNAME").trim();
        String phone = rs.getString("PHONE")==null?"":rs.getString("PHONE").trim();
        String phone_extension = rs.getString("PHONE_EXTENSION")==null?"":rs.getString("PHONE_EXTENSION").trim();
        String fax = rs.getString("FAX")==null?"":rs.getString("FAX").trim();
        String email = rs.getString("EMAIL")==null?"":rs.getString("EMAIL").trim();

        String totalphone = "";
        if (phone_extension.trim().length() > 0)
        {
          totalphone = phone + " X " +phone_extension;
        }
        else
        {
          totalphone = phone;
        }

        String Color = " ";
        if (count%2==0)
        {
            Color = "BGCOLOR=\"#E6E8FA\" ";
        }
        else
        {
           Color = " ";
        }

//        out.println("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addsupplierID('"+first_name+","+last_name+"','"+contact_id+"','"+nickname+"','"+totalphone+"','"+fax+"','"+contact_type+"')\" BORDERCOLOR=\"black\">\n");
        out.println("<TR ALIGN=\"MIDDLE\">\n");

        out.println("<TD "+Color+" WIDTH=\"15%\" ALIGN=\"LEFT\">");
        out.println(""+first_name+","+last_name+"");out.println("</TD>\n");
        out.println("<TD "+Color+" WIDTH=\"17%\" ALIGN=\"LEFT\">");out.println(nickname);out.println("</TD>\n");
        out.println("<TD "+Color+" WIDTH=\"17%\" ALIGN=\"LEFT\">");out.println(contact_type);out.println("</TD>\n");
        out.println("<TD "+Color+" WIDTH=\"15%\" ALIGN=\"LEFT\">");out.println(""+totalphone+"");out.println("</TD>\n");
        out.println("<TD "+Color+" WIDTH=\"15%\" ALIGN=\"LEFT\">");out.println(""+fax+"");out.println("</TD>\n");
		out.println("<TD "+Color+" WIDTH=\"15%\" ALIGN=\"LEFT\">");out.println(""+email+"");out.println("</TD>\n");
		out.println("<TD "+Color+" WIDTH=\"5%\" ALIGN=\"LEFT\">");out.println(""+contact_id+"");out.println("</TD>\n");

        out.println("</TR>\n");
          }
    }
    catch (Exception e)
    {
        e.printStackTrace();
        out.println("An Error Occured While Querying the Database");
        out.println("</BODY>\n</HTML>\n");
        return;
    }
    finally
    {
        if (dbrs != null)  { dbrs.close(); }
    }

      if (totalrecords==0){out.println("<TR><TD>No Records Found</TD></TR>\n");}
      out.println("</TABLE>\n");

      //close scrolling table
      out.println("</DIV>\n");
      out.println("</TD>\n");
      out.println("</TR>\n");
      out.println("</TBODY>\n");
      out.println("</TABLE>\n");

      out.println("<CENTER>&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Close\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      out.println("</FORM></BODY></HTML>\n");

      return;
    }

    private void printJavaScripts()
    {
    //StringBuffer Msglt = new StringBuffer();

    out.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.println("<!--\n");
    out.println("function cancel()\n");
    out.println("{\n");
    out.println("window.close();\n");
    out.println("}\n");
    out.println("function sendCarrier(entered)\n");
    out.println("{\n");
    out.println(" with (entered)\n");
    out.println(" {\n");
    out.println("contacttype11 = window.document.getElementById(\"contacttype\");\n");
    //out.println("contacttype.value = \"Yes\";\n");
    out.println(" eval( testmfgid = \"window.document.SupplierLike.supplierid\")\n");
    out.println(" if ( ( (eval(testmfgid.toString())).value.length > 0 )  && (contacttype11.value != \"Main\") )\n");
    out.println("{");
    out.println("selectedRow = opener.document.getElementById(\"ordertaker\");\n");
    out.println("selectedRow.className = \"HEADER\";\n");
    out.println("selectedRow.value = window.document.SupplierLike.supplierid.value;\n");

    out.println("selectedRow = opener.document.getElementById(\"ordertakerID\");\n");
    out.println("selectedRow.value = window.document.SupplierLike.contactname.value;\n");

    //out.println("alert(window.document.SupplierLike.contactphoneno.value);\n");
    out.println("contactphoneno11 = opener.document.getElementById(\"contactphoneno\");\n");
    out.println("contactphoneno11.value = window.document.SupplierLike.contactphoneno.value;\n");

    out.println("contactfaxno11 = opener.document.getElementById(\"contactfaxno\");\n");
    out.println("contactfaxno11.value = window.document.SupplierLike.contactfaxno.value;\n");

    out.println("selectedRow = opener.document.getElementById(\"validordertaker\");\n");
    out.println("selectedRow.value = \"Yes\";\n");

    /*out.println("selectedRow = opener.document.getElementById(\"validcarrier\");\n");
    out.println("selectedRow.value = \"Yes\";\n");*/

	out.println("suppliercontactemail = opener.document.getElementById(\"suppliercontactemail\");\n");
    out.println("suppliercontactemail.innerHTML = \"\" + window.document.SupplierLike.contactemail.value + \"\" ;\n");

    out.println("window.close();\n");
    out.println(" }\n");
    out.println(" }\n");
    out.println("}\n");
    out.println("function addsupplierID(matidvalue,addline1,nickname,contactphoneno,contactfaxno,contacttype,contactemail)\n");
    out.println("{\n");
    out.println("document.SupplierLike.supplierid.value = nickname;\n");
    out.println("document.SupplierLike.contactname.value = addline1;\n");
    out.println("document.SupplierLike.contactid.value = addline1;\n");
    out.println("document.SupplierLike.contactphoneno.value = contactphoneno;\n");
    out.println("document.SupplierLike.contactfaxno.value = contactfaxno;\n");
    out.println("document.SupplierLike.contacttype.value = contacttype;\n");
	out.println("document.SupplierLike.contactemail.value = contactemail;\n");
    out.println("}\n");
    return;
    }
}