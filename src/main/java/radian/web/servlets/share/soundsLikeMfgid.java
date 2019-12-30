package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;

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
 * 11-21-02
 * Chaning the soundx Query to like
 * 11-22-02
 * Don't do Query if search text is null
 * 01-29-03
 * Do search my mfg id
 *  03-20-03 - Can do Logical Searches now
 * 06-16-03 Code cleanup
 * 08-04-03 Adding javascript escape characters for manufacturer contact
 * 11-10-04 - Added mfg email and mfg notes
 */

public class soundsLikeMfgid
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String newmfgid_servlet = "";
	private PrintWriter out = null;
    public soundsLikeMfgid(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

     public void doResult(HttpServletRequest request, HttpServletResponse response)  throws ServletException,  IOException
    {
    out = response.getWriter();
    response.setContentType("text/html");

    String manufacturer=request.getParameter( "manufacturer" );
    if ( manufacturer == null )
      manufacturer="";
	manufacturer = manufacturer.trim();

    String CompanyId=request.getParameter( "Company" );
    if ( CompanyId == null )
      CompanyId="";
	CompanyId = CompanyId.trim();

    String CompNum=request.getParameter( "CompNum" );
    if ( CompNum == null )
      CompNum="0";
	CompNum = CompNum.trim();

    String Action=request.getParameter( "Action" );
    if ( Action == null )
      Action="";
	Action =Action.trim();

    String searchString=request.getParameter( "SearchString" );
    if ( searchString == null )
      searchString="";
	searchString =searchString.trim();

    if ("Update".equalsIgnoreCase(Action))
    {
    String mfg_id=request.getParameter( "mfgid" );
    if ( mfg_id == null )
      mfg_id="";
	mfg_id =mfg_id.trim();

    String mfg_desc=request.getParameter( "Manufacturer_desc" );
    if ( mfg_desc == null )
      mfg_desc="";
	mfg_desc =mfg_desc.trim();

    String mfg_url=request.getParameter( "Manufacturer_url" );
    if ( mfg_url == null )
      mfg_url="";
	mfg_url =mfg_url.trim();

    String contact=request.getParameter( "contact" );
    if ( contact == null )
      contact="";
	contact =contact.trim();

    String phoneno=request.getParameter( "phone_no" );
    if ( phoneno == null )
      phoneno="";
	phoneno =phoneno.trim();

	String mfgemail=request.getParameter( "mfgemail" );
	if ( mfgemail == null )
	  mfgemail="";
	mfgemail =mfgemail.trim();

	String mfgnotes=request.getParameter( "mfgnotes" );
	if ( mfgnotes == null )
	  mfgnotes="";
	mfgnotes =mfgnotes.trim();

 	String country_abbrev=request.getParameter( "country_abbrev" );
	if ( country_abbrev == null )
	  country_abbrev="";
	country_abbrev =country_abbrev.trim();

    //Insert into Manufacturer Table These values and Print a page which will on ok put the mfg id in main page
    buildNewMfgDone(CompNum,mfg_id,mfg_desc,mfg_url,contact,phoneno,mfgemail,mfgnotes,country_abbrev);
    }
    else if ("New".equalsIgnoreCase(Action))
    {
     Hashtable initialstData = new Hashtable ();
		 try {
			initialstData = radian.web.poHelpObj.getNewSupplierInitialData(db);
		 }
		 catch (Exception ex) {
		 }
     HttpSession invoicesession = request.getSession();
     invoicesession.setAttribute( "STATE_COUNTRY",initialstData);
		 Hashtable vvcountrystate= ( Hashtable ) invoicesession.getAttribute( "STATE_COUNTRY" );
     buildNewMfg(CompNum,CompanyId,vvcountrystate);
    }
    else if ("Search".equalsIgnoreCase(Action))
    {
    buildLikemfgid(manufacturer,CompNum,"Search",searchString,CompanyId);
    }
    else
    {
    buildLikemfgid(manufacturer,CompNum,"SoundX","",CompanyId);
    }

    out.close();

    }

  public void buildNewMfgDone( String control_name,String MfgID, String Mfg_desc, String mfgurl, String contact,
                               String phonenumber,String mfgemail,String mfgnotes, String country_abbrev)
  {
  //StringBuffer Msgn = new StringBuffer();

  out.println("<HTML>\n");
  out.println("<HEAD>\n");
  out.println("<TITLE>New Material Id</TITLE>\n");
  out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
  out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
  out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
  printJavaScripts(control_name);
  out.println("// -->\n </SCRIPT>\n");

  if ( ! ( mfgurl.startsWith( "http://" ) || mfgurl.startsWith( "https://" ) ) && ( mfgurl.length() > 0 ) )
  {
    mfgurl="http://" + mfgurl;
  }

  String insertmfg = "insert into global.manufacturer (MFG_ID,MFG_DESC,MFG_URL,PHONE,CONTACT,EMAIL,NOTES,COUNTRY_ABBREV) values ";
  insertmfg += " ("+MfgID+", '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(Mfg_desc)+"', '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(mfgurl)+"',";
  insertmfg += " '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(phonenumber)+"','"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(contact)+"','"+
      BothHelpObjs.changeSingleQuotetoTwoSingleQuote(mfgemail)+"','"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(mfgnotes)+"','"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(country_abbrev)+"')";

  String message = "New Manufacturer: <B>"+MfgID+"</B> was sucessfully Created";

  try
  {
      db.doUpdate(insertmfg);
  }
  catch (Exception e)
  {
      e.printStackTrace();
      MfgID = "";
      message = "An Error Occured while New Manufacturer was being Created. Please try again.";
  }

  out.println("<BODY><BR><BR><BR><BR>\n");
  out.println("<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\""+newmfgid_servlet+"Session=Update\">\n");
  out.println("<INPUT TYPE=\"hidden\" NAME=\"mfgid\" VALUE=\""+MfgID+"\">\n");
  out.println("<INPUT TYPE=\"hidden\" NAME=\"mfg_description\" VALUE=\""+Mfg_desc+"\">\n");
  out.println("<INPUT TYPE=\"hidden\" NAME=\"mfg_phone\" VALUE=\""+phonenumber+"\">\n");
  out.println("<INPUT TYPE=\"hidden\" NAME=\"mfgurl\" ID=\"mfgurl\" VALUE=\""+mfgurl+"\">\n");
  out.println("<INPUT TYPE=\"hidden\" NAME=\"mfgcontact\" ID=\"mfgcontact\" VALUE=\""+contact+"\">\n");
  out.println("<INPUT TYPE=\"hidden\" NAME=\"mfgemail\" ID=\"mfgemail\" VALUE=\""+mfgemail+"\">\n");
  out.println("<INPUT TYPE=\"hidden\" NAME=\"mfgnotes\" ID=\"mfgnotes\" VALUE=\""+mfgnotes+"\">\n");

  out.println("<FONT FACE=\"Arial\" SIZE=\"3\">"+message+"</FONT>");
  out.println("<BR><BR><BR><BR><CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"sendmfgID(this.form)\" type=\"button\">\n");
  out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
  out.println("</BODY></HTML>\n");

  return;
  }

  public void buildNewMfg( String control_name,String CompanyId1,Hashtable vvcountrystate)
   {
    //StringBuffer Msgn = new StringBuffer();
    newmfgid_servlet = bundle.getString("QUALITY_CONTROL_MFG");
    out.println(radian.web.HTMLHelpObj.printHTMLHeader("Catalog Add QC Mew MFG","",false));
    printJavaScripts(control_name);
    out.println("// -->\n </SCRIPT>\n");

    boolean foundmfgId = false;
    int mfgid1 = 0;

    while(!foundmfgId)
    {
      try
      {
	  mfgid1 = DbHelpers.getNextVal(db,"global.mfg_seq");

	  int test_number = DbHelpers.countQuery(db,"select count(*) from global.manufacturer where MFG_ID = "+mfgid1+"");

	  if (test_number == 0)
	  {
	    foundmfgId = true;
	  }
      }
	  catch (Exception e)
      {
	  e.printStackTrace();
      }
    }

    out.println("<BODY>\n");
    out.println("<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\""+newmfgid_servlet+"Session=Update\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"CompNum\" VALUE=\""+control_name+"\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"Company\" VALUE=\""+CompanyId1+"\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Update\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"mfgid\" VALUE=\""+mfgid1+"\">\n");
    out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\">\n");
    out.println("<TR><TD WIDTH=\"15%\" ALIGN=\"RIGHT\" CLASS=\"announce\"><B>Manufacturer ID:&nbsp;</B></TD><TD WIDTH=\"45%\">\n");

    //Get new Mfg Sequence Number here and put it in the Value attribute
    out.println("&nbsp;"+mfgid1+"\n");
    out.println("</TD></TR>\n");
    out.println("<TR><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>\n");
    out.println("<TR><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD></TR>\n");
    out.println("<TR>\n");
    out.println("<TD WIDTH=\"15%\" CLASS=\"Inwhiter\"><B>Mfg Description:  </B></TD>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"Manufacturer_desc\" value=\"\" SIZE=\"65\">\n</TD>\n");
    out.println("</TR>\n");

    out.println("<TR>\n");
    out.println("<TD WIDTH=\"15%\" CLASS=\"Inbluer\"><B>Mfg URL:  </B></TD>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"Manufacturer_url\" value=\"\" SIZE=\"50\">\n</TD>\n");
    out.println("</TR>\n");

    out.println("<TR>\n");
    out.println("<TD WIDTH=\"15%\" CLASS=\"Inwhiter\"><B>Contact:  </B></TD>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"contact\" value=\"\" SIZE=\"25\">\n</TD>\n");
    out.println("</TR>\n");

    out.println("<TR>\n");
    out.println("<TD WIDTH=\"15%\" CLASS=\"Inbluer\"><B>Phone Number:  </B></TD>\n");
    out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"phone_no\" value=\"\" SIZE=\"25\">\n</TD>\n");
    out.println("</TR>\n");

	out.println("<TR>\n");
	out.println("<TD WIDTH=\"15%\" CLASS=\"Inwhiter\"><B>Email:  </B></TD>\n");
	out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"mfgemail\" value=\"\" SIZE=\"25\">\n</TD>\n");
	out.println("</TR>\n");

	out.println("<TR>\n");
	out.println("<TD WIDTH=\"15%\" CLASS=\"Inbluer\"><B>Notes:  </B></TD>\n");
	out.println("</TD>\n");
	out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><TEXTAREA name=\"mfgnotes\" rows=\"3\" cols=\"50\"></TEXTAREA>\n</TD>\n");
	out.println("</TR>\n");

  out.println("<TR>\n");
  out.println( "<TD WIDTH=\"15%\"  CLASS=\"Inwhiter\" ALIGN=\"left\"><B>Country:  </B></TD>\n");
  out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><SELECT CLASS=\"HEADER\"  NAME=\"country_abbrev\" size=\"1\">\n");
  Hashtable countrylist = (Hashtable) vvcountrystate.get("COUNTRY");
  out.println(radian.web.HTMLHelpObj.sorthashbyValue(countrylist.entrySet(),"USA"));
  out.println("</SELECT>\n");
  out.println( "</TD>\n" );
  out.println("</TR>\n");


    out.println("</TABLE>\n");
    out.println("<BR><BR><BR><BR><CENTER> <INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" name=\"CreateNew\" onclick= \"return actionValue(name,this)\">\n");
    out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    out.println("</FORM></BODY></HTML>\n");

    return;
   }

     public void buildLikemfgid(String manufacturer,String control_name,String SearchFlag, String SearchString, String CompanyId1)
     {
      //StringBuffer Msgl = new StringBuffer();

      newmfgid_servlet = bundle.getString("QUALITY_CONTROL_MFG");
      out.println(radian.web.HTMLHelpObj.printHTMLHeader("Catalog Add QC Select MFG","",false));
      printJavaScripts(control_name);
      out.println("function ShowSearch(name,entered)\n");
      out.println("{\n");
      out.println(" with (entered)\n");
      out.println(" {\n");
      out.println(" loc = \""+newmfgid_servlet+"Action=\" + name.toString() + \"&SearchString=\";\n");
      out.println(" loc = loc + window.document.MfgidLike.SearchString.value;\n");
      out.println(" loc = loc + \"&CompNum="+control_name+"\"; \n");
      out.println(" loc = loc + \"&Company="+CompanyId1+"\"; \n");
      out.println(" loc = loc + \"&manufacturer="+manufacturer+"\"; \n");
      out.println(" }\n");
      out.println("  window.location.replace(loc);\n");
      out.println(" }\n");
      out.println("// -->\n </SCRIPT>\n");
      out.println("<BODY>\n");
      out.println("<FORM METHOD=\"POST\" name=\"MfgidLike\" action=\""+newmfgid_servlet+"Session=Update\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"manufacturer\" VALUE=\""+manufacturer+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"mfg_description\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"mfg_phone\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"mfgurl\" ID=\"mfgurl\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"mfgcontact\" ID=\"mfgcontact\" VALUE=\"\">\n");
	  out.println("<INPUT TYPE=\"hidden\" NAME=\"mfgemail\" ID=\"mfgemail\" VALUE=\"\">\n");
	  out.println("<INPUT TYPE=\"hidden\" NAME=\"mfgnotes\" ID=\"mfgnotes\" VALUE=\"\">\n");

      if (!"Search".equalsIgnoreCase(SearchFlag))
      {
        SearchString = manufacturer;
      }

      out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\" bgcolor=\"white\">\n");
      out.println("<TR><TD WIDTH=\"30%\" ALIGN=\"LEFT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" TYPE=\"submit\" VALUE=\"SoundsLike\" NAME=\"SoundX\"></TD>\n");
      out.println("<TD WIDTH=\"20%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n");
      out.println("<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\""+HelpObjs.addescapesForJavascript(SearchString)+"\" SIZE=\"15\"></TD>\n");
      out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"New\" value=\"New\" OnClick=\"ShowSearch(name,this)\"></TD></TR>\n");
      out.println("<TR><TD WIDTH=\"30%\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Mfg ID:</B></FONT></TD><TD WIDTH=\"70%\" COLSPAN=\"3\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"mfgid\" value=\"\" SIZE=\"10\" readonly>\n");
      out.println("</TR>\n");
      out.println("</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.println("<TR>\n");
      out.println("<TH WIDTH=\"15%\"><B>Mfg Id</B></TH>\n");
      out.println("<TH WIDTH=\"40%\"><B>Description</B></TH>\n");
      out.println("</TR></TABLE>\n");

      //open scrolling table
      out.println("<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
      out.println("<TBODY>\n");
      out.println("<TR>\n");
      out.println("<TD VALIGN=\"TOP\">\n");
      out.println("<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n");

      //Write code to insert rows here
      out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"1\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n");

      int totalrecords = 0;
      int count = 0;
	  DBResultSet dbrs = null;
	  ResultSet rs = null;
      String query = "";
      SearchString=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
      String wherese=radian.web.logicalSearchhelp.doLogicM( SearchString,"MFG_DESC||MFG_ID" );

      if ("Search".equalsIgnoreCase(SearchFlag))
      {
      query += "select MFG_ID,MFG_DESC,PHONE,MFG_URL,CONTACT,EMAIL,NOTES from global.manufacturer where "+wherese+" order by MFG_ID,MFG_DESC";
      }
      else if (manufacturer.length() > 0)
      {
      query += "select MFG_ID,MFG_DESC,PHONE,MFG_URL,CONTACT,EMAIL,NOTES from global.manufacturer where "+wherese+" order by MFG_ID,MFG_DESC";
      }

      if (query.length() > 0)
      {
          try
          {
              dbrs = db.doQuery(query);
              rs=dbrs.getResultSet();

              while(rs.next())
              {
                totalrecords += 1;
                count += 1;

                String Color = " ";
                if (count%2==0)
                {
                    Color = "BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
                }
                else
                {
                   Color = " onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
                }

                String mfgid = rs.getString( "MFG_ID" ) == null ? "" : rs.getString( "MFG_ID" ).trim();
                String mfgdesc1 = rs.getString( "MFG_DESC" ) == null ? "" : rs.getString( "MFG_DESC" ).trim();
                String mfgphone = rs.getString( "PHONE" ) == null ? "" : rs.getString( "PHONE" ).trim();
                String mfgurl=rs.getString( "MFG_URL" ) == null ? "" : rs.getString( "MFG_URL" ).trim();
                String mfgcontact=rs.getString( "CONTACT" ) == null ? "" : rs.getString( "CONTACT" ).trim();
				String mfgemail=rs.getString( "EMAIL" ) == null ? "" : rs.getString( "EMAIL" ).trim();
				String mfgnotes=rs.getString( "NOTES" ) == null ? "" : rs.getString( "NOTES" ).trim();

                if (mfgphone.length() > 0)
                {
                  mfgphone = HelpObjs.addescapesForJavascript(mfgphone);
                }

                out.println("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addmfgID('"+mfgid+"','"+HelpObjs.addescapesForJavascript(mfgdesc1)+"','"+mfgphone+"','"+HelpObjs.addescapesForJavascript(mfgurl)+"','"+HelpObjs.addescapesForJavascript(mfgcontact)+"','"+HelpObjs.addescapesForJavascript(mfgemail)+"','"+HelpObjs.addescapesForJavascript(mfgnotes)+"')\">\n");
                out.println("<TD ");
                out.println(Color);
                out.println(" WIDTH=\"15%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
                out.println(BothHelpObjs.makeBlankFromNull(rs.getString("MFG_ID")));
                out.println("</FONT></TD>\n");
                out.println("<TD ");
                out.println(Color);
                out.println(" WIDTH=\"40%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">");
                out.println(mfgdesc1);
                out.println("</FONT></TD>\n");

                out.println("</TR>\n");
              }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            out.println("An Error Occured While Querying the Databse");
            out.println("</BODY>\n</HTML>\n");
            return;
        }
        finally
        {
            if ( dbrs != null ){ dbrs.close(); }
        }
      }

      if ( totalrecords==0 && "Search".equalsIgnoreCase(SearchFlag) )
            {out.println("<TR><TD>No Records Found</TD></TR>\n");}

      out.println("</TABLE>\n");

      //close scrolling table
      out.println("</DIV>\n");
      out.println("</TD>\n");
      out.println("</TR>\n");
      out.println("</TBODY>\n");
      out.println("</TABLE>\n");

      out.println("<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\"  name=\"searchlike\" value=\"Ok\" OnClick=\"sendmfgID(this.form)\">\n");
      out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\"  name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></CENTER>\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"control_name\" VALUE=\""+control_name+"\">\n");
      out.println("</FORM></BODY></HTML>\n");

      return;
    }

    protected void printJavaScripts(String controlname)
    {
    //StringBuffer Msglt = new StringBuffer();

    out.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.println("<!--\n");

    out.println("function search(entered)\n{\n");
    out.println("  window.document.MfgidLike.Action.value = \"NEW\";\n");
    out.println("  return true;\n");
    out.println("}\n");

    out.println("function actionValue(name,entered)\n");
    out.println("{\n");
    out.println("  var finalMsgt;\n finalMsgt = \"Please enter valid values for: \\n\\n\";");
    out.println("    var result ;\nvar allClear = 0;\n");
    out.println("       curval  =  window.document.MfgidLike.mfgid.value;\n");
    out.println("       if (curval.length < 1)\n");
    out.println("       {\n");
    out.println("        finalMsgt = finalMsgt +   \" Manufacturer Id \" + \"\\n\";\n");
    out.println("        allClear = 1;\n");
    out.println("       }\n");
    out.println("       curval2  =  window.document.MfgidLike.Manufacturer_desc.value;\n");
    out.println("       if (curval2.length < 1)\n");
    out.println("       {\n");
    out.println("        finalMsgt = finalMsgt +   \" Manufacturer Desc \" + \"\\n\";\n");
    out.println("        allClear = 1;\n");
    out.println("       }\n");
    out.println(" if (allClear < 1)\n");
    out.println("  {\n");
    out.println("     result = true;\n");
    out.println("  }\n");
    out.println("  else\n");
    out.println("  {\n");
    out.println("      alert(finalMsgt);\n");
    out.println("      result = false;\n");
    out.println("  }\n");
    out.println("   return result;\n");
    out.println("   }\n");

    out.println("function sendmfgID(entered)\n");
    out.println("{\n");
    out.println(" with (entered)\n");
    out.println(" {\n");
    out.println(" eval( testmfgid = \"window.document.MfgidLike.mfgid\")\n");
    out.println(" if ( (eval(testmfgid.toString())).value.length > 0 )\n");
    out.println("{");
    int compn = Integer.parseInt(controlname);

    out.println("opener.document.ComponentForm"+(compn+1)+".mfgid"+compn+"");
    out.println(".value = window.document.MfgidLike.mfgid.value;\n");
    out.println("opener.document.ComponentForm"+(compn+1)+".manufacturer"+compn+"");
    out.println(".value = window.document.MfgidLike.mfg_description.value;\n");

    out.println("opener.document.ComponentForm"+(compn+1)+".mfgphone"+compn+"");
    out.println(".value = window.document.MfgidLike.mfg_phone.value;\n");

    out.println("var mfgphonediv = opener.document.getElementById(\"mfgphonediv"+compn+"\"); \n");
    out.println("mfgphonediv.innerHTML = window.document.MfgidLike.mfg_phone.value;\n");

    out.println("opener.document.ComponentForm"+(compn+1)+".materialid"+compn+"");
    out.println(".value = '';\n");
    //out.println("opener.document.ComponentForm"+(compn+1)+".materialdesc"+compn+"");
    //out.println(".value = '';\n");
    out.println("opener.document.ComponentForm"+(compn+1)+".tradename"+compn+"");
    out.println(".value = '';\n");
    /*out.println("opener.document.ComponentForm"+(compn+1)+".landdot"+compn+"");
    out.println(".value = '';\n");
    out.println("opener.document.ComponentForm"+(compn+1)+".airdot"+compn+"");
    out.println(".value = '';\n");
    out.println("opener.document.ComponentForm"+(compn+1)+".waterdot"+compn+"");
    out.println(".value = '';\n");*/
    out.println("opener.document.ComponentForm"+(compn+1)+".revdate"+compn+"");
    out.println(".value = '';\n");
    out.println("opener.document.ComponentForm"+(compn+1)+".content"+compn+"");
    out.println(".value = '';\n");

    out.println("opener.document.ComponentForm"+(compn+1)+".mfgurl"+compn+"");
    out.println(".value = window.document.MfgidLike.mfgurl.value;\n");

    out.println("opener.document.ComponentForm"+(compn+1)+".mfgcontact"+compn+"");
    out.println(".value = window.document.MfgidLike.mfgcontact.value;\n");

    out.println("var mfgurl1 = document.getElementById(\"mfgurl\").value; \n");
    out.println("var mfgcontact1 = document.getElementById(\"mfgcontact\").value; \n");
    out.println("var mfghtml = \"\";    \n");
/*    out.println("if ( !( mfgurl.startsWith( \"http://\" ) || mfgurl.startsWith( \"https://\" ) ) && ( mfgurl.length() > 0 ) )\n");
    out.println(" {\n");
    out.println("   mfgurl=\"http://\" + mfgurl;\n");
    out.println(" }\n");*/
    out.println(" if ( ( mfgcontact1.length > 0 ) && ( mfgurl1.length > 0 ) )\n");
    out.println("{\n");
    out.println("  mfghtml = \"<A onclick=\\\"javascript:window.open('\" + mfgurl1 + \"')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\">\" + mfgcontact1 + \"</A>\";\n");
    out.println("}\n");
    out.println("else if ( mfgurl1.length > 0 )\n");
    out.println("{\n");
    out.println("  mfghtml =  \"<A onclick=\\\"javascript:window.open('\" + mfgurl1 + \"')\\\" STYLE=\\\"color:#0000ff;cursor:hand\\\">\" + mfgurl1 + \"</A>\";\n");
    out.println("}\n");
    out.println("else if ( mfgcontact1.length > 0 )\n");
    out.println("{\n");
    out.println("  mfghtml = mfgcontact1;\n");
    out.println("}\n");
    out.println( "selectedRow = opener.document.getElementById(\"mfgurlinfo"+compn+"\");\n" );
    out.println( "selectedRow.innerHTML = mfghtml;\n" );

	out.println("opener.document.ComponentForm"+(compn+1)+".mfgemail"+compn+"");
    out.println(".value = window.document.MfgidLike.mfgemail.value;\n");

	out.println( "ccmfgnotes = opener.document.getElementById(\"mfgnotes"+compn+"\");\n" );
	out.println( "ccmfgnotes.innerHTML = window.document.MfgidLike.mfgnotes.value;\n" );

    out.println(" }\n");
    out.println(" }\n");
    out.println("window.close();\n");
    out.println("}\n");

    out.println("function cancel()\n");
    out.println("{\n");
    out.println("window.close();\n");
    out.println("}\n");

    out.println("function addmfgID(matidvalue,mfgdesc,mfgphone,mfgurl,mfgcontact,mfgemail,mfgnotes)\n");
    out.println("{\n");
    out.println("document.MfgidLike.mfgid.value = matidvalue;\n");
    out.println("document.MfgidLike.mfg_description.value = mfgdesc;\n");
    out.println("document.MfgidLike.mfg_phone.value = mfgphone;\n");
    out.println("document.MfgidLike.mfgurl.value = mfgurl;\n");
    out.println("document.MfgidLike.mfgcontact.value = mfgcontact;\n");
	out.println("document.MfgidLike.mfgemail.value = mfgemail;\n");
	out.println("document.MfgidLike.mfgnotes.value = mfgnotes;\n");

    out.println("}\n");
    return;
    }
}