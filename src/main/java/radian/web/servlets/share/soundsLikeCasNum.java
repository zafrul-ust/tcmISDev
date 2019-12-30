package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 03-20-03 - Can do Logical Searches now
 * 08-27-03 Code cleanup and removed true from request.getsession()
 */


public class soundsLikeCasNum
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
	private PrintWriter out = null;
    public soundsLikeCasNum( ServerResourceBundle b,TcmISDBModel d )
    {
      bundle=b;
      db=d;
    }

    public void doResult( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
    {
      out=response.getWriter();
      response.setContentType( "text/html" );

      String tradeName=request.getParameter( "trade_name" );
      if ( tradeName == null )
        tradeName="";

      String CompanyId=request.getParameter( "Company" );
      if ( CompanyId == null )
        CompanyId="";

      String CompNum=request.getParameter( "CompNum" );
      if ( CompNum == null )
        CompNum="0";

      String Action=request.getParameter( "Action" );
      if ( Action == null )
        Action="";

      String searchString=request.getParameter( "SearchString" );
      if ( searchString == null )
        searchString="";

      String MfgId=request.getParameter( "MfgID" );
      if ( MfgId == null )
        MfgId="";

      String materialdesc=request.getParameter( "material_desc" );
      if ( materialdesc == null )
        materialdesc="";

      String material_id=request.getParameter( "material_id" );
      if ( material_id == null )
        material_id="";

      String CasName=request.getParameter( "casnum" );
      if ( CasName == null )
         CasName="";

      String prefename=request.getParameter( "prefename" );
      if ( prefename == null )
         prefename="";

      String chemformula=request.getParameter( "chemformula" );
      if ( chemformula == null )
         chemformula="";

      String moleculeweight=request.getParameter( "moleculeweight" );
      if ( moleculeweight == null )
         moleculeweight="";

      String commonname=request.getParameter( "commonname" );
      if ( commonname == null )
         commonname="";


      if ( "Update".equalsIgnoreCase( Action ) )
      {
        buildLikenewCasnumDone( CompNum,material_id,materialdesc,CasName,prefename,chemformula,moleculeweight,commonname );
      }
      else if ( "New".equalsIgnoreCase( Action ) )
      {
        buildLikenewCasnum( CompNum,CompanyId,CasName );
      }
      else if ( "Search".equalsIgnoreCase( Action ) )
      {
        buildLikeCasnum( tradeName,CompNum,"Search",searchString,CompanyId,MfgId,CasName );
      }
      else if ( "Nothing".equalsIgnoreCase( Action ) )
      {
        buildLikeCasnum( tradeName,CompNum,"Nothing","",CompanyId,MfgId,CasName );
      }
      out.close();
    }

    public void buildLikenewCasnumDone( String control_name,String MatID,String Mat_desc,String casNum,String prefename,
                                                String chemformula,String moleculeweight,String commonname )
    {
      //StringBuffer Msgn=new StringBuffer();

      nematid_Servlet=bundle.getString( "QUALITY_CONTROL_CASNUM" );
      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC New CAS Num","",false ) );
      printJavaScripts();
      out.println( "// -->\n </SCRIPT>\n" );
      out.println( "<BODY>\n<BR><BR><BR><BR>\n" );

      String insertmaterial="insert into global.chemical (CAS_NUMBER,PREFERRED_NAME,MOLECULAR_WEIGHT,CHEMICAL_FORMULA,COMMON_NAME) values ";
      insertmaterial+=" ('" + BothHelpObjs.changeSingleQuotetoTwoSingleQuote( casNum ) + "', '" + BothHelpObjs.changeSingleQuotetoTwoSingleQuote( prefename ) + "'," + ( moleculeweight.length() == 0 ? "null" : moleculeweight ) + ",'" + ( chemformula.length() == 0 ? "null" : BothHelpObjs.changeSingleQuotetoTwoSingleQuote( chemformula ) ) + "','" + ( commonname.length() == 0 ? "null" : BothHelpObjs.changeSingleQuotetoTwoSingleQuote( commonname ) ) + "') ";

      String message="New CAS Num: <B>" + casNum + "</B> was sucessfully Created";
      try
      {
        int doesCasnumExist=DbHelpers.countQuery( db,"select count(*) from global.chemical where CAS_NUMBER = '" + BothHelpObjs.changeSingleQuotetoTwoSingleQuote( casNum ) + "'" );

        if ( doesCasnumExist < 1 )
        {
          db.doUpdate( insertmaterial );
        }
        else
        {
          message="This <B>CAS Number : " + casNum + " </B>already Exists in the Database";
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        MatID="";
        message="An Error Occured while New CAS Num was being Created. Please try again.";
      }

      out.println( "<FORM METHOD=\"POST\" name=\"MaterialLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"material\" VALUE=\"" + MatID + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"mat_description\" VALUE=\"" + Mat_desc + "\">\n" );
      out.println( "<FONT FACE=\"Arial\" SIZE=\"3\">" + message + "</FONT>" );
      out.println( "<BR><BR><BR><BR><CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\">\n" );
      out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
      out.println( "</BODY></HTML>\n" );

      return;
    }

    public void buildLikenewCasnum( String control_name,String CompanyId1,String actualControlname )
    {
      //StringBuffer Msgn=new StringBuffer();
      nematid_Servlet=bundle.getString( "QUALITY_CONTROL_CASNUM" );
      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC New CAS Num","",false ) );
      printJavaScripts();
      out.println( "// -->\n </SCRIPT>\n" );
      out.println( "<BODY>\n" );
      out.println( "<FORM METHOD=\"POST\" name=\"MaterialLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"CompNum\" VALUE=\"" + control_name + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"Company\" VALUE=\"" + CompanyId1 + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Update\">\n" );
      out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\">\n" );
      out.println( "<TR><TD WIDTH=\"5%\" ALIGN=\"LEFT\"><FONT FACE=\"Arial\" SIZE=\"2\">Enter CAS Number Information:</B></TD><TD WIDTH=\"10%\">\n" );
      out.println( "</TD></TR>\n" );
      out.println( "<TR><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>\n" );
      //out.println("<TR><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD></TR>\n");
      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"5%\" CLASS=\"Inbluer\"><B>CAS Number: </B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><FONT FACE=\"Arial\" SIZE = \"2\"><INPUT type=\"text\" name=\"casnum\" value=\"\" maxlength=\"12\" SIZE=\"20\">\n</TD>\n" );
      out.println( "</TR>\n" );
      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"5%\" CLASS=\"Inwhiter\"><B>Preferred Name: </B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><FONT FACE=\"Arial\" SIZE = \"2\"><INPUT type=\"text\" name=\"prefename\" value=\"\" SIZE=\"60\">\n</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"5%\" CLASS=\"Inbluer\"><B>Chemical Formula: </B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><FONT FACE=\"Arial\" SIZE = \"2\"><INPUT type=\"text\" name=\"chemformula\" value=\"\" SIZE=\"60\">\n</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"5%\" CLASS=\"Inwhiter\"><B>Common Name: </B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><FONT FACE=\"Arial\" SIZE = \"2\"><INPUT type=\"text\" name=\"commonname\" value=\"\" SIZE=\"60\">\n</TD>\n" );
      out.println( "</TR>\n" );

      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Molecular Weight: </B></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><FONT FACE=\"Arial\" SIZE = \"2\"><INPUT type=\"text\" name=\"moleculeweight\" value=\"\" SIZE=\"15\">\n</TD>\n" );
      out.println( "</TR>\n" );
      out.println( "</TABLE>\n" );
      out.println( "<BR><BR><CENTER> <INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"OK\" name=\"CreateNew\" onclick= \"return actionValue(name,this)\">\n" );
      out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
      out.println( "</FORM></BODY></HTML>\n" );

      return;
    }

    public void buildLikeCasnum( String trade_name,String control_name,String SearchFlag,String SearchString,
                                         String CompanyId1,String MfgID1,String actualControlname )
    {
      //StringBuffer Msgl=new StringBuffer();
      DBResultSet dbrs=null;
      ResultSet rs=null;

      nematid_Servlet=bundle.getString( "QUALITY_CONTROL_CASNUM" );
      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC CAS Number Look Up","",false ) );
      printJavaScripts();
      out.println( "function ShowSearch(name,entered)\n" );
      out.println( "{\n" );
      out.println( " with (entered)\n" );
      out.println( " {\n" );
      out.println( " loc = \"" + nematid_Servlet + "Action=\" + name.toString() + \"&SearchString=\";\n" );
      out.println( " loc = loc + window.document.MaterialLike.SearchString.value;\n" );
      out.println( " loc = loc + \"&CompNum=" + control_name + "\"; \n" );
      out.println( " loc = loc + \"&MfgID=" + MfgID1 + "\"; \n" );
      out.println( " loc = loc + \"&Company=" + CompanyId1 + "\"; \n" );
      out.println( " loc = loc + \"&trade_name=" + trade_name + "\"; \n" );
      out.println( " loc = loc + \"&CasName=" + actualControlname + "\"; \n" );
      out.println( " }\n" );
      out.println( "  window.location.replace(loc);\n" );
      out.println( " }\n" );
      out.println( "// -->\n </SCRIPT>\n" );
      out.println( "<BODY>\n" );
      out.println( "<FORM METHOD=\"POST\" name=\"MaterialLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"SoundX\">\n");
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"trade_name\" VALUE=\"" + trade_name + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"mat_description\" VALUE=\"\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"Company\" VALUE=\"" + CompanyId1 + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"CasName\" VALUE=\"" + actualControlname + "\">\n" );
      out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"3\" CELLSPACING=\"0\" bgcolor=\"white\">\n" );
      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"40%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"SearchString\" value=\"" + HelpObjs.addescapesForJavascript( SearchString ) + "\" SIZE=\"30\"></TD>\n" );
      out.println( "<TD WIDTH=\"20%\" ALIGN=\"LEFT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"Search\" value=\"Search\" OnClick=\"ShowSearch(name,this)\"></TD>\n" );
      out.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"New\" value=\"New\" OnClick=\"ShowSearch(name,this)\"></TD></TR>\n" );
      out.println( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\">\n" );
      out.println( "<TR>\n" );
      out.println( "<TD WIDTH=\"15%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Cas Number</B></FONT></TD>\n" );
      out.println( "<TD WIDTH=\"40%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Preffered Name</B></FONT></TD>\n" );
      out.println( "</TR></TABLE>\n" );

      //open scrolling table
      out.println( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.println( "<TBODY>\n" );
      out.println( "<TR>\n" );
      out.println( "<TD VALIGN=\"TOP\">\n" );
      out.println( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column350\">\n" );

      //Write code to insert rows here
      out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

      int totalrecords=0;
      int count=0;

      if ( !"Nothing".equalsIgnoreCase( SearchFlag ) )
      {
        String query="";
        SearchString=BothHelpObjs.changeSingleQuotetoTwoSingleQuote( SearchString );
        String whereclause=radian.web.logicalSearchhelp.doLogicM( SearchString,"CAS_NUMBER||PREFERRED_NAME" );

        if ( "Search".equalsIgnoreCase( SearchFlag ) )
        {
          //query += "select * from chemical where lower(CAS_NUMBER||PREFERRED_NAME) like lower('%"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(SearchString)+"%') order by lower(CAS_NUMBER) ";
          query+="select distinct cas_number, preferred_name from chem_synonym_view where " + whereclause + " order by lower(CAS_NUMBER) ";
        }

        try
        {
          dbrs=db.doQuery( query );
          rs=dbrs.getResultSet();

          while ( rs.next() )
          {
            totalrecords+=1;
            count+=1;

            String Color=" ";
            if ( count % 2 == 0 )
            {
              Color="BGCOLOR=\"#E6E8FA\" ";
            }
            else
            {
              Color=" ";
            }

            out.println( "<TR ALIGN=\"MIDDLE\">\n" );
            out.println( "<TD " );
            out.println( Color );
            out.println( " WIDTH=\"15%\" ALIGN=\"LEFT\">" );
            out.println( BothHelpObjs.makeSpaceFromNull( rs.getString( "CAS_NUMBER" ) ) );
            out.println( "</FONT></TD>\n" );
            out.println( "<TD " );
            out.println( Color );
            out.println( " WIDTH=\"40%\" ALIGN=\"LEFT\">" );
            out.println( BothHelpObjs.makeSpaceFromNull( rs.getString( "PREFERRED_NAME" ) ) );
            out.println( "</FONT></TD>\n" );

            out.println( "</TR>\n" );
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
          out.println( "An Error Occured While Querying the Databse" );
          out.println( "</BODY>\n</HTML>\n" );
          return;
        }
        finally
        {
          if ( dbrs != null )
          {
            dbrs.close();
          }
          if ( totalrecords == 0 )
          {
            out.println( "<TR><TD>No Records Found</TD></TR>\n" );
          }
        }

      }
      out.println( "</TABLE>\n" );

      //close scrolling table
      out.println( "</DIV>\n" );
      out.println( "</TD>\n" );
      out.println( "</TR>\n" );
      out.println( "</TBODY>\n" );
      out.println( "</TABLE>\n" );
      out.println( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\">\n" );
      out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"findlike\">\n");
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"MfgId\" VALUE=\"" + MfgID1 + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"control_name\" VALUE=\"" + control_name + "\">\n" );
      out.println( "</FORM></BODY></HTML>\n" );

      return;
    }

    private void printJavaScripts()
    {
      //StringBuffer Msglt=new StringBuffer();

      out.println( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
      out.println( "<!--\n" );
      out.println( "function actionValue(name,entered)\n" );
      out.println( "{\n" );
      out.println( "  var finalMsgt;\n finalMsgt = \"Please enter valid values for: \\n\\n\";" );
      out.println( "    var result ;\nvar allClear = 0;\n" );
      out.println( "       curval  =  window.document.MaterialLike.casnum.value;\n" );
      out.println( "       if (curval.length < 1)\n" );
      out.println( "       {\n" );
      out.println( "        finalMsgt = finalMsgt +   \" Cas Num \" + \"\\n\";\n" );
      out.println( "        allClear = 1;\n" );
      out.println( "       }\n" );
      out.println( "       curval2  =  window.document.MaterialLike.prefename.value;\n" );
      out.println( "       if (curval2.length < 1)\n" );
      out.println( "       {\n" );
      out.println( "        finalMsgt = finalMsgt +   \" Reffered Name \" + \"\\n\";\n" );
      out.println( "        allClear = 1;\n" );
      out.println( "       }\n" );
      out.println( " if (allClear < 1)\n" );
      out.println( "  {\n" );
      out.println( "     result = true;\n" );
      out.println( "  }\n" );
      out.println( "  else\n" );
      out.println( "  {\n" );
      out.println( "      alert(finalMsgt);\n" );
      out.println( "      result = false;\n" );
      out.println( "  }\n" );
      out.println( "   return result;\n" );
      out.println( "   }\n" );
      out.println( "function cancel()\n" );
      out.println( "{\n" );
      out.println( "window.close();\n" );
      out.println( "}\n" );
      out.println( "function addmaterialID(matidvalue)\n" );
      out.println( "{\n" );
      out.println( "document.MaterialLike.material.value = matidvalue;\n" );
      out.println( "}\n" );

      return;
    }
}