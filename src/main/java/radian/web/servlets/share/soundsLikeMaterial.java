package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
 *
 * 01-29-03
 * Do search my material id
 * 03-17-03
 * Removed Javascript from the code
 * 03-20-03 - Can do Logical Searches now
 * 07-14-03 - Removing the DOT and Solids info from the page and Code Cleanup
 * 08-13-03 - Also searching the Trade_name for material ID
 * 08-18-03 - Trying to solve the misterious exception messages
 * 10-13-03 - Escaping # when creating a new Material
 */

public class soundsLikeMaterial
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
	private PrintWriter out = null;
    public soundsLikeMaterial(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResult(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException
    {
    out=null;
    try
    {
      out=response.getWriter();
      response.setContentType( "text/html" );

      String tradeName=request.getParameter( "tradename" );
      if ( tradeName == null )
      {
        tradeName="";
      }

      String CompanyId=request.getParameter( "Company" );
      if ( CompanyId == null )
      {
        CompanyId="";
      }

      String CompNum=request.getParameter( "CompNum" );
      if ( CompNum == null )
      {
        CompNum="0";
      }

      String Action=request.getParameter( "Action" );
      if ( Action == null )
      {
        Action="";
      }

      String searchString=request.getParameter( "SearchString" );
      if ( searchString == null )
      {
        searchString="";
      }

      String MfgId=request.getParameter( "MfgID" );
      if ( MfgId == null )
      {
        MfgId="";
      }

      String materialdesc=request.getParameter( "material_desc" );
      if ( materialdesc == null )
      {
        materialdesc="";
      }

      String material_id=request.getParameter( "material_id" );
      if ( material_id == null )
      {
        material_id="";
      }

      if ( "Update".equalsIgnoreCase( Action ) )
      {
        //Insert into Material Table These values and Print a page which will on ok put the Material id in main page
        buildNewMaterialDone( CompNum,material_id,materialdesc,MfgId,tradeName ) ;
      }
      else if ( "New".equalsIgnoreCase( Action ) )
      {
        buildNewMaterial( CompNum,CompanyId,MfgId );
      }
      else if ( "Search".equalsIgnoreCase( Action ) )
      {
        buildLikeMaterial( tradeName,CompNum,"Search",searchString,CompanyId,MfgId );
      }
      else
      {
        buildLikeMaterial( tradeName,CompNum,"SoundX","",CompanyId,MfgId );
      }
    }
    catch ( IOException ex )
    {
      ex.printStackTrace();

      HttpSession session=request.getSession();
      String FullName=session.getAttribute( "FULLNAME" ) == null ? "" : session.getAttribute( "FULLNAME" ).toString();
      radian.web.emailHelpObj.sendnawazemail("Error in soundsLikeMaterial in Catalog QC page","Error in soundsLikeMaterial in Catalog QC page\nError MSG:\n "+ex.getMessage()+"\n User Logged in "+FullName+"");
      out.println(radian.web.HTMLHelpObj.printMessageinTable("An Error Occured.<BR>A message has been sent to the Tech Group.<BR>Please try again."));
    }
    finally
    {
      out.close();
    }
    }

    public void buildNewMaterialDone( String control_name,String MatID,String Mat_desc,String MfgID,String tradeName )
    {
      //StringBuffer Msgn=new StringBuffer();
      nematid_Servlet=bundle.getString( "QUALITY_CONTROL_MATERIAL" );

      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC New Material","ctalogmaterial.js",false ) );

      printJavaScripts( control_name );
      out.println( "// -->\n </SCRIPT>\n" );
      out.println( "<BODY><BR><BR><BR><BR>\n" );

      String insertmaterial = "insert into global.material (MATERIAL_ID,MATERIAL_DESC,TRADE_NAME,MFG_ID) values ";
      insertmaterial += " ("+MatID+",'"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(Mat_desc)+"','"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tradeName)+"',"+MfgID+")";

      String message="New Material: <B>" + MatID + "</B> was sucessfully Created";
      try
      {
        db.doUpdate( insertmaterial );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        MatID="";
        message="An Error Occured while New Material was being Created. Please try again.";
      }

      out.println( "<FORM METHOD=\"POST\" name=\"MaterialLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"material\" VALUE=\"" + MatID + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"mat_description\" VALUE=\"" + Mat_desc + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"tradename\" VALUE=\"" + tradeName + "\">\n" );
      /*out.println( "<INPUT TYPE=\"hidden\" NAME=\"landdot\" VALUE=\"" + landdot + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"airdot\" VALUE=\"" + airdot + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"waterdot\" VALUE=\"" + waterdot + "\">\n" );*/

      out.println( "<FONT FACE=\"Arial\" SIZE=\"3\">" + message + "</FONT>" );
      out.println("\n<BR><BR><BR><BR><CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"sendmaterialID(this.form)\" type=\"button\">\n");
      out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      out.println("</BODY></HTML>\n");

      return;
    }

    public void buildNewMaterial( String control_name,String CompanyId1,String MfgID1 )
    {
      //StringBuffer Msgn=new StringBuffer();
      nematid_Servlet=bundle.getString( "QUALITY_CONTROL_MATERIAL" );

      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC New Material","ctalogmaterial.js",false ) );

      printJavaScripts( control_name );
      out.println( "// -->\n </SCRIPT>\n" );

      boolean foundmfgId=false;
      int materialid1=0;

      while ( !foundmfgId )
      {
        try
        {
          materialid1=DbHelpers.getNextVal( db,"global.material_seq" );

          int test_number=DbHelpers.countQuery( db,"select count(*) from global.material where MATERIAL_ID = " + materialid1 + "" );

          if ( test_number == 0 )
          {
            foundmfgId=true;
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
        }
        finally
        {

        }
      }

      out.println( "<BODY>\n" );
      out.println( "<FORM METHOD=\"POST\" name=\"MaterialLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );

      out.println( "<INPUT TYPE=\"hidden\" NAME=\"CompNum\" VALUE=\"" + control_name + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"Company\" VALUE=\"" + CompanyId1 + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Update\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"MfgID\" VALUE=\"" + MfgID1 + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"material_id\" VALUE=\"" + materialid1 + "\">\n" );


      out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\">\n" );
      out.println( "<TR><TD WIDTH=\"15%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Material ID:&nbsp;</B></TD><TD WIDTH=\"45%\">\n" );
      out.println( "&nbsp;" + materialid1 + "\n" );
      out.println( "</TD></TR>\n" );
      out.println( "<TR><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>\n" );
      out.println( "<TR><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD></TR>\n" );
      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Material Description:  </B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"material_desc\" value=\"\" SIZE=\"65\">\n</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Trade Name:  </B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"tradename\" value=\"\" SIZE=\"65\">\n</TD>\n" );
      out.println( "</TR>\n" );

      /*out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Land Dot:  </B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"landdot\" value=\"\" SIZE=\"40\">\n</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Air Dot:  </B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"airdot\" value=\"\" SIZE=\"40\">\n</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Water Dot:  </B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"waterdot\" value=\"\" SIZE=\"40\">\n</TD>\n" );
      out.println( "</TR>\n" );*/

      out.println("</TABLE>\n");
      out.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" name=\"CreateNew\" onclick= \"return actionValue(name,this)\">\n");
      out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      out.println("</FORM></BODY></HTML>\n");

      return;
    }

     public void buildLikeMaterial( String trade_name,String control_name,String SearchFlag, String SearchString,
                                            String CompanyId1, String MfgID1)
     {
      //StringBuffer Msgl = new StringBuffer();
      DBResultSet dbrs=null;
      ResultSet rs=null;

      nematid_Servlet = bundle.getString("QUALITY_CONTROL_MATERIAL");
      out.println(radian.web.HTMLHelpObj.printHTMLHeader("Catalog Add QC Material","ctalogmaterial.js",false));

      printJavaScripts(control_name);

      out.println("function ShowSearch(name,entered)\n");
      out.println("{\n");
      out.println(" with (entered)\n");
      out.println(" {\n");
      out.println(" loc = \""+nematid_Servlet+"Action=\" + name.toString();\n");
      out.println(" loc = loc + \"&CompNum="+control_name+"\"; \n");
      out.println(" loc = loc + \"&MfgID="+MfgID1+"\"; \n");
      out.println(" loc = loc + \"&Company="+CompanyId1+"\"; \n");
	  out.println(" loc = loc + \"&SearchString=\";\n");
      out.println(" loc = loc + window.document.MaterialLike.SearchString.value;\n");
	  out.println(" loc = loc + \"&trade_name="+HelpObjs.addescapesForJavascript(trade_name)+"\"; \n");
      out.println(" }\n");
      out.println("  window.location.replace(loc);\n");
      out.println(" }\n");
      out.println("// -->\n </SCRIPT>\n");

      out.println("<BODY>\n");
      out.println("<FORM METHOD=\"POST\" name=\"MaterialLike\" action=\""+nematid_Servlet+"Session=Update\">\n");

      //out.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"SoundX\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"trade_name\" VALUE=\""+trade_name+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"mat_description\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"Company\" VALUE=\""+CompanyId1+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"tradename\" VALUE=\"\">\n");
      /*out.println("<INPUT TYPE=\"hidden\" NAME=\"landdot\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"airdot\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"waterdot\" VALUE=\"\">\n");*/

      if (!"Search".equalsIgnoreCase(SearchFlag))
      {
        SearchString = trade_name;
      }

      out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.println("<TR><TD WIDTH=\"30%\" ALIGN=\"LEFT\" ><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  TYPE=\"submit\" VALUE=\"SoundsLike\" NAME=\"SoundX\"></TD>\n");
      out.println("<TD WIDTH=\"20%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n");
      out.println("<TD WIDTH=\"40%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\""+HelpObjs.addescapesForJavascript(SearchString)+"\" SIZE=\"15\"></TD>\n");
      out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"New\" value=\"New\" OnClick=\"ShowSearch(name,this)\"></TD></TR>\n");
      out.println("<TR><TD WIDTH=\"30%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Material ID:</B></FONT></TD><TD WIDTH=\"70%\" COLSPAN=\"3\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"material\" value=\"\" SIZE=\"10\" readonly>\n");
      out.println("</TR>\n");
      out.println("</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n");
      out.println("<TR>\n");
      out.println("<TH WIDTH=\"15%\"><B>Material Id</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"35%\"><B>Material Description</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"40%\"><B>Trade Name</B></FONT></TH>\n");
      out.println("<TH WIDTH=\"10%\"><B>Online</B></FONT></TH>\n");
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

      //This is the query to get all request ids which need catalog part number
      String query="";
      SearchString=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
      trade_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( trade_name );

      String wherese=radian.web.logicalSearchhelp.doLogicM( SearchString,"MATERIAL_DESC||MATERIAL_ID||TRADE_NAME" );
      String wheretn=radian.web.logicalSearchhelp.doLogicM( trade_name,"MATERIAL_DESC||MATERIAL_ID||TRADE_NAME" );

      if (MfgID1.length() > 0)
      {
        if  ("Search".equalsIgnoreCase(SearchFlag))
        {
          query += "select * from global.material where "+wherese+" and MFG_ID = "+MfgID1+" order by MATERIAL_DESC ";
        }
        else if (trade_name.length() > 0)
        {
          query += "select * from global.material where "+wheretn+" and MFG_ID = "+MfgID1+" order by MATERIAL_DESC ";
        }
      }
      else
      {
        if  ("Search".equalsIgnoreCase(SearchFlag))
        {
          query += "select * from global.material where "+wherese+" order by TRADE_NAME ";
        }
        else if (trade_name.length() > 0)
        {
          query += "select * from global.material where "+wheretn+" order by TRADE_NAME ";
        }
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

              String materialid1 = BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_ID")).trim();
              String materialdesc1 = BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_DESC")).trim();
              String tradename1 = BothHelpObjs.makeBlankFromNull(rs.getString("TRADE_NAME")).trim();
              /*String landdot1 = BothHelpObjs.makeBlankFromNull(rs.getString("GROUND_DOT")).trim();
              String waterdot1 = BothHelpObjs.makeBlankFromNull(rs.getString("WATER_DOT")).trim();
              String airdot1 = BothHelpObjs.makeBlankFromNull(rs.getString("AIR_DOT")).trim();*/

              String Color = " ";
              if (count%2==0)
              {
                  Color = "BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
              }
              else
              {
                 Color = " onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ";
              }
              //out.println("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addmaterialID("+BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_ID"))+")\" BORDERCOLOR=\"black\">\n");
              out.println("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addmaterialID("+materialid1+",'"+HelpObjs.addescapesForJavascript(materialdesc1)+"','"+HelpObjs.addescapesForJavascript(tradename1)+"')\" BORDERCOLOR=\"black\">\n");
              //out.println("<TR ALIGN=\"MIDDLE\" ONCLICK=\"addmaterialID("+materialid1+",'"+HelpObjs.addescapesForJavascript(materialdesc1)+"','"+HelpObjs.addescapesForJavascript(tradename1)+"','"+HelpObjs.addescapesForJavascript(landdot1)+"','"+HelpObjs.addescapesForJavascript(airdot1)+"','"+HelpObjs.addescapesForJavascript(waterdot1)+"')\" BORDERCOLOR=\"black\">\n");
              out.println("<TD "+Color+" WIDTH=\"15%\" ALIGN=\"LEFT\">");
              //out.println("<BUTTON name=\"matID"+count+"\" value=\""+BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_ID"))+"\" OnClick=\"addmaterialID(name,this)\" type=\"button\">"+BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_ID"))+"</BUTTON>\n");
              out.println(materialid1);
              out.println("</TD>\n");
              out.println("<TD "+Color+" WIDTH=\"35%\" ALIGN=\"LEFT\">");out.println(materialdesc1);out.println("</TD>\n");
              out.println("<TD "+Color+" WIDTH=\"40%\" ALIGN=\"LEFT\">");out.println(tradename1);out.println("</TD>\n");

              String online = BothHelpObjs.makeBlankFromNull(rs.getString("MSDS_ON_LINE"));

              out.println("<TD "+Color+" WIDTH=\"10%\" ALIGN=\"LEFT\">");

              if ("Y".equalsIgnoreCase(online))
              {
                out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"View\" OnClick=\"ShowMSDS("+BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_ID"))+")\">\n");
              }
              else
              {
                out.println("&nbsp;");
              }
              out.println("</TD>\n");

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
      out.println("<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"sendmaterialID(this.form)\" type=\"button\">\n");
      out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"findlike\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"MfgID\" VALUE=\""+MfgID1+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"control_name\" VALUE=\""+control_name+"\">\n");
      out.println("</FORM></BODY></HTML>\n");

      return;
    }

    protected void printJavaScripts(String controlname)
    {
    //StringBuffer Msglt = new StringBuffer();

    out.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.println("<!--\n");
    out.println("function sendmaterialID(entered)\n");
    out.println("{\n");
    out.println(" with (entered)\n");
    out.println(" {\n");
    out.println(" eval( testmfgid = \"window.document.MaterialLike.material\")\n");
    out.println(" if ( (eval(testmfgid.toString())).value.length > 0 )\n");
    out.println("{");

    int compn = Integer.parseInt(controlname);

    out.println("opener.document.ComponentForm"+(compn+1)+".materialid"+compn+"");
    out.println(".value = window.document.MaterialLike.material.value;\n");
    out.println("opener.document.ComponentForm"+(compn+1)+".materialdesc"+compn+"");
    out.println(".value = window.document.MaterialLike.mat_description.value;\n");
    out.println("opener.document.ComponentForm"+(compn+1)+".tradename"+compn+"");
    out.println(".value = window.document.MaterialLike.tradename.value;\n");

    /*out.println("opener.document.ComponentForm"+(compn+1)+".landdot"+compn+"");
    out.println(".value = window.document.MaterialLike.landdot.value;\n");
    out.println("opener.document.ComponentForm"+(compn+1)+".airdot"+compn+"");
    out.println(".value = window.document.MaterialLike.airdot.value;\n");
    out.println("opener.document.ComponentForm"+(compn+1)+".waterdot"+compn+"");
    out.println(".value = window.document.MaterialLike.waterdot.value;\n");*/

    out.println("opener.document.ComponentForm"+(compn+1)+".revdate"+compn+"");
    out.println(".value = '';\n");
    out.println("opener.document.ComponentForm"+(compn+1)+".content"+compn+"");
    out.println(".value = '';\n");

    out.println(" }\n");
    out.println(" }\n");
    out.println("window.close();\n");
    out.println("}\n");

    out.println("function cancel()\n");
    out.println("{\n");
    out.println("window.close();\n");
    out.println("}\n");

    return;
    }
}