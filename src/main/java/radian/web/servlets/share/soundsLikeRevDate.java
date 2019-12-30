package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 11-15-02
 * adding PERSONAL_PROTECTION to hmis data
 *
 * 01-03-03
 * Adding a text area to enter ALT_DATA_SOURCE information
 * 07-23-03 Code cleanup
 * 08-13-03 - Sending Flash Point Units
 * 09-17-03 - Trim material ID to avoid error
 * 02-18-04 - Adding VAPOR_PRESSURE_DETECT, VAPOR_PRESSURE, VAPOR_PRESSURE_UNIT, VAPOR_PRESSURE_TEMP, VAPOR_PRESSURE_TEMP_UNIT to page
 * 11-10-04 - Added new VOC fields
 */

public class soundsLikeRevDate
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
	private PrintWriter out = null;
    public soundsLikeRevDate(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResult( HttpServletRequest request,HttpServletResponse response )
       throws ServletException,IOException
    {
      out=response.getWriter();
      response.setContentType( "text/html" );

      String tradeName=request.getParameter( "tradename" );
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
	  MfgId = MfgId.trim();

      String materialdesc=request.getParameter( "material_desc" );
      if ( materialdesc == null )
        materialdesc="";

      String materialid=request.getParameter( "materialid" );
      if ( materialid == null )
        materialid="";
	  materialid = materialid.trim();

      String newrevdate=request.getParameter( "newrevdate" );
      if ( newrevdate == null )
        newrevdate="";
	  newrevdate=newrevdate.trim();

      if ( "newdate".equalsIgnoreCase( Action ) )
      {
        buildsendRevDate( CompNum,materialid,newrevdate );
      }
      else if ( "olddate".equalsIgnoreCase( Action ) )
      {
        buildsendRevDate( CompNum,materialid,searchString );
      }
      else
      {
        buildLikeRevDate( materialid,CompNum,CompanyId,MfgId,"" );
      }
      out.close();
  }

  public void buildsendRevDate( String control_name,String materialid1,String date )
  {
    //StringBuffer Msgn=new StringBuffer();
    //StringBuffer Msgbody=new StringBuffer();
    nematid_Servlet=bundle.getString( "QUALITY_CONTROL_REVDATE" );

    out.print( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC Rev Date","",false ) );
    printJavaScripts();
    out.print( "// -->\n </SCRIPT>\n" );

    boolean foundstuff=false;
    if ( date.length() == 10 )
    {
      try
      {
        int test_number=DbHelpers.countQuery( db,"select count(*) from global.msds where MATERIAL_ID = " + materialid1 + " and REVISION_DATE=to_date('" + date + "','MM/DD/YYYY')" );

        if ( test_number > 0 )
        {
          foundstuff=true;
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        out.print( "<BODY><BR><BR>\n" );
		out.print( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
		out.print( "<INPUT TYPE=\"hidden\" NAME=\"material\" VALUE=\"" + materialid1 + "\">\n" );
		out.print( "<INPUT TYPE=\"hidden\" NAME=\"revisionDate\" VALUE=\"" + date + "\">\n" );
        out.print( "<FONT FACE=\"Arial\" SIZE=\"3\">Please Enter the Date in shown Format.<BR>\n" );
        out.print( "Try Again.\n" );
        out.print( "<CENTER><BR><BR>\n" );
        out.print( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
        out.print( "</FORM></BODY></HTML>\n" );
        //out.print( Msgbody );
        return;
      }
    }
    else
    {
	  out.print( "<BODY><BR><BR>\n" );
	  out.print( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
	  out.print( "<INPUT TYPE=\"hidden\" NAME=\"material\" VALUE=\"" + materialid1 + "\">\n" );
	  out.print( "<INPUT TYPE=\"hidden\" NAME=\"revisionDate\" VALUE=\"" + date + "\">\n" );
	  out.print( "<FONT FACE=\"Arial\" SIZE=\"3\">Please Enter the Date in shown Format.<BR>\n" );
      out.print( "Try Again.\n" );
      out.print( "<CENTER><BR><BR>\n" );
      out.print( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
      out.print( "</FORM></BODY></HTML>\n" );
      //out.print( Msgbody );
      return;
    }

    if ( !foundstuff && date.length() == 10 )
    {
      String insertmaterial="insert into global.msds (MATERIAL_ID,REVISION_DATE) values (" + materialid1 + ",to_date('" + date + "','MM/DD/YYYY')) ";

      try
      {
        db.doUpdate( insertmaterial );
      }
      catch ( Exception e )
      {
        e.printStackTrace();

        out.print( "<BODY><BR><BR>\n" );
		out.print( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
		out.print( "<INPUT TYPE=\"hidden\" NAME=\"material\" VALUE=\"" + materialid1 + "\">\n" );
		out.print( "<INPUT TYPE=\"hidden\" NAME=\"revisionDate\" VALUE=\"" + date + "\">\n" );
        out.print( "<FONT FACE=\"Arial\" SIZE=\"3\">An Error Occured while New Revision Date was being Created.<BR>\n" );
        out.print( "Please Try Again.\n" );
        out.print( "<CENTER><BR><BR>\n" );
        out.print( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
        out.print( "</FORM></BODY></HTML>\n" );
        //out.print( Msgbody );
        return;
      }
   }

    //StringBuffer Msgtemp = new StringBuffer();
	DBResultSet dbrs = null;
    ResultSet rs = null;
    //Build the Java Script Here and Finish the thing
    out.print("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.print("<!--\n");
    out.print("function sendRevDateData()\n");
    out.print("{\n");
    int compn = Integer.parseInt(control_name);
    int count = 0;

    try
    {
      dbrs=db.doQuery( "Select to_char(x.REVISION_DATE,'mm/dd/yyyy') REVISION_DATE,to_char(x.REVISION_DATE,'mmddyy') REVISION_DATEFILE, x.* from global.msds  x where x.MATERIAL_ID = " + materialid1 + " and x.REVISION_DATE=to_date('" + date + "','MM/DD/YYYY') " );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".revdate" + compn + ".value = '" );
        out.print( rs.getString( "REVISION_DATE" ) == null ? "" : rs.getString( "REVISION_DATE" ) + "';\n" );

        String altdatasource=HelpObjs.addescapesForJavascript( rs.getString( "ALT_DATA_SOURCE" ) == null ? "" : rs.getString( "ALT_DATA_SOURCE" ).trim() );
        String remarks=HelpObjs.addescapesForJavascript( rs.getString( "REMARK" ) == null ? "" : rs.getString( "REMARK" ).trim() );
        out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".content" + compn + ".value = '" );
        String contenturl=rs.getString( "CONTENT" ) == null ? "" : rs.getString( "CONTENT" );
        String revdatefield=rs.getString( "REVISION_DATEFILE" ) == null ? "" : rs.getString( "REVISION_DATEFILE" );

        if ( contenturl.length() == 0 )
        {
          String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
          contenturl= wwwHome + "MSDS/" + materialid1 + "_" + revdatefield + ".pdf";
        }

	  out.print(contenturl);
	  out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".spegravity"+compn+".value = '");out.print(rs.getString("SPECIFIC_GRAVITY")==null?"":rs.getString("SPECIFIC_GRAVITY")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".nfpah"+compn+".value = '");out.print(rs.getString("HEALTH")==null?"":rs.getString("HEALTH")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".nfpaf"+compn+".value = '");out.print(rs.getString("FLAMMABILITY")==null?"":rs.getString("FLAMMABILITY")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".nfpar"+compn+".value = '");out.print(rs.getString("REACTIVITY")==null?"":rs.getString("REACTIVITY")); out.print("';\n");
      out.print("opener.document.ComponentForm"+(compn+1)+".nfpas"+compn+".value = '");out.print(rs.getString("SPECIFIC_HAZARD")==null?"":rs.getString("SPECIFIC_HAZARD")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".hmish"+compn+".value = '");out.print(rs.getString("HMIS_HEALTH")==null?"":rs.getString("HMIS_HEALTH")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".hmisf"+compn+".value = '");out.print(rs.getString("HMIS_FLAMMABILITY")==null?"":rs.getString("HMIS_FLAMMABILITY")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".hmisr"+compn+".value = '");out.print(rs.getString("HMIS_REACTIVITY")==null?"":rs.getString("HMIS_REACTIVITY")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".hmiss"+compn+".value = '");out.print(rs.getString("PERSONAL_PROTECTION")==null?"":rs.getString("PERSONAL_PROTECTION")); out.print("';\n");
      out.print("opener.document.ComponentForm"+(compn+1)+".flashpoint"+compn+".value = '");out.print(rs.getString("FLASH_POINT")==null?"":rs.getString("FLASH_POINT")); out.print("';\n");
      out.print("opener.document.ComponentForm"+(compn+1)+".flashpointunits"+compn+".value = '");out.print(rs.getString("FLASH_POINT_UNIT")==null?"":rs.getString("FLASH_POINT_UNIT")); out.print("';\n");
 	  out.print("opener.document.ComponentForm"+(compn+1)+".density"+compn+".value = '");out.print(rs.getString("DENSITY")==null?"":rs.getString("DENSITY")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".denunits"+compn+".value = '");out.print(rs.getString("DENSITY_UNIT")==null?"":rs.getString("DENSITY_UNIT")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".physicalstate"+compn+".value = '");out.print(rs.getString("PHYSICAL_STATE")==null?"":rs.getString("PHYSICAL_STATE")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".vocunits"+compn+".value = '");out.print(rs.getString("VOC_UNIT")==null?"":rs.getString("VOC_UNIT")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".voc"+compn+".value = '");out.print(rs.getString("VOC")==null?"":rs.getString("VOC")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".voclower"+compn+".value = '");out.print(rs.getString("VOC_LOWER")==null?"":rs.getString("VOC_LOWER")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".vocupper"+compn+".value = '");out.print(rs.getString("VOC_UPPER")==null?"":rs.getString("VOC_UPPER")); out.print("';\n");

	  out.print("opener.document.ComponentForm"+(compn+1)+".percsolids"+compn+".value = '");out.print(rs.getString("SOLIDS")==null?"":rs.getString("SOLIDS")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".percsolidslower"+compn+".value = '");out.print(rs.getString("SOLIDS_LOWER")==null?"":rs.getString("SOLIDS_LOWER")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".percsolidsupper"+compn+".value = '");out.print(rs.getString("SOLIDS_UPPER")==null?"":rs.getString("SOLIDS_UPPER")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".percsolidsunit"+compn+".value = '");out.print(rs.getString("SOLIDS_UNIT")==null?"":rs.getString("SOLIDS_UNIT")); out.print("';\n");

	  out.print("opener.document.ComponentForm"+(compn+1)+".msdsremark"+compn+".value = '");out.print(remarks); out.print("';\n");
      out.print("opener.document.ComponentForm"+(compn+1)+".altdatasource"+compn+".value = '");out.print(altdatasource); out.print("';\n");

  	  out.print("opener.document.ComponentForm"+(compn+1)+".vappressdet"+compn+".value = '");out.print(rs.getString("VAPOR_PRESSURE_DETECT")==null?"":rs.getString("VAPOR_PRESSURE_DETECT")); out.print("';\n");
   	  out.print("opener.document.ComponentForm"+(compn+1)+".vappress"+compn+".value = '");out.print(rs.getString("VAPOR_PRESSURE")==null?"":rs.getString("VAPOR_PRESSURE")); out.print("';\n");
      out.print("opener.document.ComponentForm"+(compn+1)+".vappressunit"+compn+".value = '");out.print(rs.getString("VAPOR_PRESSURE_UNIT")==null?"":rs.getString("VAPOR_PRESSURE_UNIT")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".vappresstemp"+compn+".value = '");out.print(rs.getString("VAPOR_PRESSURE_TEMP")==null?"":rs.getString("VAPOR_PRESSURE_TEMP")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".vappresstempunt"+compn+".value = '");out.print(rs.getString("VAPOR_PRESSURE_TEMP_UNIT")==null?"":rs.getString("VAPOR_PRESSURE_TEMP_UNIT")); out.print("';\n");

	  out.print("opener.document.ComponentForm"+(compn+1)+".voc_source"+compn+".value = '");out.print(rs.getString("VOC_SOURCE")==null?"":rs.getString("VOC_SOURCE")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_h2o_exempt"+compn+".value = '");out.print(rs.getString("VOC_LESS_H2O_EXEMPT")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_h2o_exempt_unit"+compn+".value = '");out.print(rs.getString("VOC_LESS_H2O_EXEMPT_UNIT")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_UNIT")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_h2o_exempt_lower"+compn+".value = '");out.print(rs.getString("VOC_LESS_H2O_EXEMPT_LOWER")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_LOWER")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_h2o_exempt_upper"+compn+".value = '");out.print(rs.getString("VOC_LESS_H2O_EXEMPT_UPPER")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_UPPER")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_h2o_exempt_source"+compn+".value = '");out.print(rs.getString("VOC_LESS_H2O_EXEMPT_SOURCE")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_SOURCE")); out.print("';\n");

	  out.print("opener.document.ComponentForm"+(compn+1)+".voc_lb_per_solid_lb"+compn+".value = '");out.print(rs.getString("VOC_LB_PER_SOLID_LB")==null?"":rs.getString("VOC_LB_PER_SOLID_LB")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".voc_lb_per_solid_lb_source"+compn+".value = '");out.print(rs.getString("VOC_LB_PER_SOLID_LB_SOURCE")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_SOURCE")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".voc_lb_per_solid_lb_lower"+compn+".value = '");out.print(rs.getString("VOC_LB_PER_SOLID_LB_LOWER")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_LOWER")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".voc_lb_per_solid_lb_upper"+compn+".value = '");out.print(rs.getString("VOC_LB_PER_SOLID_LB_UPPER")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_UPPER")); out.print("';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".solids_source"+compn+".value = '");out.print(rs.getString("SOLIDS_SOURCE")==null?"":rs.getString("SOLIDS_SOURCE")); out.print("';\n");
		out.print("opener.document.ComponentForm"+(compn+1)+".vapor_pressure_lower"+compn+".value = '");out.print(rs.getString("VAPOR_PRESSURE_LOWER")==null?"":rs.getString("VAPOR_PRESSURE_LOWER")); out.print("';\n");
		out.print("opener.document.ComponentForm"+(compn+1)+".vapor_pressure_upper"+compn+".value = '");out.print(rs.getString("VAPOR_PRESSURE_UPPER")==null?"":rs.getString("VAPOR_PRESSURE_UPPER")); out.print("';\n");
		out.print("opener.document.ComponentForm"+(compn+1)+".vapor_pressure_source"+compn+".value = '");out.print(rs.getString("VAPOR_PRESSURE_SOURCE")==null?"":rs.getString("VAPOR_PRESSURE_SOURCE")); out.print("';\n");
/*out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_h20_exempt_source"+compn+".value = '");out.print(rs.getString("VOC_LESS_H20_EXEMPT_SOURCE")==null?"":rs.getString("VOC_LESS_H20_EXEMPT_SOURCE")); out.print("';
");
out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_exempt_lower"+compn+".value = '");out.print(rs.getString("VOC_LESS_EXEMPT_LOWER")==null?"":rs.getString("VOC_LESS_EXEMPT_LOWER")); out.print("';
");
out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_exempt_upper"+compn+".value = '");out.print(rs.getString("VOC_LESS_EXEMPT_UPPER")==null?"":rs.getString("VOC_LESS_EXEMPT_UPPER")); out.print("';
");

out.print("opener.document.ComponentForm"+(compn+1)+".voc_lb_per_solid_lb_low_calc"+compn+".value = '");out.print(rs.getString("VOC_LB_PER_SOLID_LB_LOW_CALC")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_LOW_CALC")); out.print("';
");
out.print("opener.document.ComponentForm"+(compn+1)+".voc_lb_per_solid_lb_calc"+compn+".value = '");out.print(rs.getString("VOC_LB_PER_SOLID_LB_CALC")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_CALC")); out.print("';
");
out.print("opener.document.ComponentForm"+(compn+1)+".voc_lb_per_solid_lb_up_calc"+compn+".value = '");out.print(rs.getString("VOC_LB_PER_SOLID_LB_UP_CALC")==null?"":rs.getString("VOC_LB_PER_SOLID_LB_UP_CALC")); out.print("';
");

out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_h2o_exempt_lower_calc"+compn+".value = '");out.print(rs.getString("VOC_LESS_H2O_EXEMPT_LOWER_CALC")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_LOWER_CALC")); out.print("';
");
out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_h2o_exempt_calc"+compn+".value = '");out.print(rs.getString("VOC_LESS_H2O_EXEMPT_CALC")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_CALC")); out.print("';
");
out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_h2o_exempt_upper_calc"+compn+".value = '");out.print(rs.getString("VOC_LESS_H2O_EXEMPT_UPPER_CALC")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_UPPER_CALC")); out.print("';
");
out.print("opener.document.ComponentForm"+(compn+1)+".voc_less_h2o_exempt_unit_calc"+compn+".value = '");out.print(rs.getString("VOC_LESS_H2O_EXEMPT_UNIT_CALC")==null?"":rs.getString("VOC_LESS_H2O_EXEMPT_UNIT_CALC")); out.print("';
*/
    }
  }
  catch ( Exception e )
  {
    e.printStackTrace();
  }
  finally
  {
   if ( dbrs != null ) {dbrs.close();}
  }

  DBResultSet compdbrs=null;
  ResultSet comprs=null;

  try
  {
    compdbrs=db.doQuery( "select * from global.composition where material_id = " + materialid1 + " and REVISION_DATE=to_date('" + date + "','MM/DD/YYYY') " );
    comprs=compdbrs.getResultSet();

    while ( comprs.next() )
	{
	out.print("opener.document.ComponentForm"+(compn+1)+".percent"+count+""+compn+".value ='");out.print(comprs.getString("PERCENT")==null?"":comprs.getString("PERCENT")); out.print("';\n");
	out.print("opener.document.ComponentForm"+(compn+1)+".percent_lower"+count+""+compn+".value ='");out.print(comprs.getString("PERCENT_LOWER")==null?"":comprs.getString("PERCENT_LOWER")); out.print("';\n");
	out.print("opener.document.ComponentForm"+(compn+1)+".percent_upper"+count+""+compn+".value ='");out.print(comprs.getString("PERCENT_UPPER")==null?"":comprs.getString("PERCENT_UPPER")); out.print("';\n");
	out.print("opener.document.ComponentForm"+(compn+1)+".cas_number"+count+""+compn+".value ='");out.print(comprs.getString("CAS_NUMBER")==null?"":comprs.getString("CAS_NUMBER")); out.print("';\n");

/*	String hazardous = comprs.getString("HAZARDOUS")==null?"":comprs.getString("HAZARDOUS");

	if ("Y".equalsIgnoreCase(hazardous))
	{
	  out.print("opener.document.ComponentForm"+(compn+1)+".hazardous"+count+""+compn+".value ='Y';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".hazardous"+count+""+compn+".checked = true;\n");
	}
	else
	{
	 out.print("opener.document.ComponentForm"+(compn+1)+".hazardous"+count+""+compn+".value ='';\n");
	 out.print("opener.document.ComponentForm"+(compn+1)+".hazardous"+count+""+compn+".checked = false;\n");
	}*/

/*	String tradesecreat = comprs.getString("TRADE_SECRET")==null?"":comprs.getString("TRADE_SECRET");
	if ("Y".equalsIgnoreCase(tradesecreat))
	{
	  out.print("opener.document.ComponentForm"+(compn+1)+".trade_secret"+count+""+compn+".value ='Y';\n");
	  out.print("opener.document.ComponentForm"+(compn+1)+".trade_secret"+count+""+compn+".checked = true;\n");
	}
	else
	{
	 out.print("opener.document.ComponentForm"+(compn+1)+".trade_secret"+count+""+compn+".value ='';\n");
	 out.print("opener.document.ComponentForm"+(compn+1)+".trade_secret"+count+""+compn+".checked = false;\n");
	}*/

	out.print("opener.document.ComponentForm"+(compn+1)+".remark"+count+""+compn+".value ='");out.print(comprs.getString("REMARK")==null?"":comprs.getString("REMARK")); out.print("';\n");
	String msdschemname = comprs.getString("MSDS_CHEMICAL_NAME")==null?"":comprs.getString("MSDS_CHEMICAL_NAME");
	if (msdschemname.trim().length() > 0)
	{
	 msdschemname = HelpObjs.addescapesForJavascript(msdschemname);
	}

	out.print("opener.document.ComponentForm"+(compn+1)+".msds_chemical_name"+count+""+compn+".value ='");out.print(msdschemname); out.print("';\n");

	count ++;
  }
}
catch ( Exception ewe )
{
  ewe.printStackTrace();
}
finally
{
  compdbrs.close();
}

for ( int rem=count; rem < 50; rem++ )
{
  out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".percent" + rem + "" + compn + ".value = '';\n" );
  out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".percent_lower" + rem + "" + compn + ".value = '';\n" );
  out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".percent_upper" + rem + "" + compn + ".value = '';\n" );
/*  out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".hazardous" + rem + "" + compn + ".value ='';\n" );
  out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".hazardous" + rem + "" + compn + ".checked = false;\n" );*/
  out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".cas_number" + rem + "" + compn + ".value = '';\n" );
/*  out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".trade_secret" + rem + "" + compn + ".value ='';\n" );
  out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".trade_secret" + rem + "" + compn + ".checked = false;\n" );*/
  out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".remark" + rem + "" + compn + ".value = '';\n" );
  out.print( "opener.document.ComponentForm" + ( compn + 1 ) + ".msds_chemical_name" + rem + "" + compn + ".value = '';\n" );
}

out.print( "window.close();\n" );
out.print( " }\n" );
out.print( "// -->\n</SCRIPT>\n" );
//out.print( Msgtemp );
out.print( "<BODY onLoad=\"sendRevDateData()\"><BR><BR>\n" );
out.print( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
out.print( "<INPUT TYPE=\"hidden\" NAME=\"material\" VALUE=\"" + materialid1 + "\">\n" );
out.print( "<INPUT TYPE=\"hidden\" NAME=\"revisionDate\" VALUE=\"" + date + "\">\n" );
out.print( "<CENTER><BR><BR>\n" );
out.print( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
out.print( "</FORM></BODY></HTML>\n" );
//out.print( Msgbody );

return;
}

public void buildLikeRevDate( String materialid,String control_name,String CompanyId1,String MfgID1,String trade_name )
{
//StringBuffer Msgl=new StringBuffer();

nematid_Servlet=bundle.getString( "QUALITY_CONTROL_REVDATE" );
out.print( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC Rev Date","",false ) );
printJavaScripts();
out.print( "function ShowSearch(name,entered)\n" );
out.print( "{\n" );
out.print( " with (entered)\n" );
out.print( " {\n" );
out.print( " loc = \"" + nematid_Servlet + "Action=\" + name.toString() + \"&SearchString=\";\n" );
out.print( " loc = loc + window.document.revDateLike.revdate.value;\n" );
out.print( " loc = loc + \"&CompNum=" + control_name + "\"; \n" );
out.print( " loc = loc + \"&MfgID=" + MfgID1 + "\"; \n" );
out.print( " loc = loc + \"&Company=" + CompanyId1 + "\"; \n" );
out.print( " loc = loc + \"&trade_name=" + trade_name + "\"; \n" );
out.print( " loc = loc + \"&materialid=" + materialid + "\"; \n" );
out.print( " loc = loc + \"&newrevdate=\"; \n" );
out.print( " loc = loc + window.document.revDateLike.newrevdate.value;\n" );
out.print( " }\n" );
out.print( "  window.location.replace(loc);\n" );
out.print( " }\n" );
out.print( "// -->\n </SCRIPT>\n" );
out.print( "<BODY>\n" );
out.print( "<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
out.print( "<INPUT TYPE=\"hidden\" NAME=\"trade_name\" VALUE=\"" + trade_name + "\">\n" );
out.print( "<INPUT TYPE=\"hidden\" NAME=\"mat_description\" VALUE=\"\">\n" );
out.print( "<INPUT TYPE=\"hidden\" NAME=\"Company\" VALUE=\"" + CompanyId1 + "\">\n" );
out.print( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"98%\" >\n" );
out.print( "<TR><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>\n" );
out.print( "<TR><TD ALIGN=\"RIGHT\">\n" );
out.print( "<SELECT CLASS=\"HEADER\" NAME=\"revdate\" size=\"1\">\n" );
out.print( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );

int totalrecords=0;
int count=0;
DBResultSet dbrs = null;
ResultSet rs = null;
//This is the query to get all request ids which need catalog part number
String query="";
if ( materialid.length() > 0 )
{
  query+="select to_char(REVISION_DATE,'MM/DD/YYYY') REVISION_DATE1,REVISION_DATE from msds where MATERIAL_ID = " + materialid + " order by revision_date desc ";
}
else
{
  query+="select to_char(REVISION_DATE,'MM/DD/YYYY') REVISION_DATE1,REVISION_DATE from msds where MATERIAL_ID = 65465465 and ON_LINE = 'Y' and rownum <1 ";
}

try
{
  dbrs=db.doQuery( query );
  rs=dbrs.getResultSet();

  while ( rs.next() )
  {
    totalrecords+=1;
    count+=1;

    String revDate=BothHelpObjs.makeBlankFromNull( rs.getString( "REVISION_DATE1" ) );
    out.print( "<OPTION VALUE=\"" + revDate + "\">" + revDate + "</OPTION>\n" );

  }
}
catch ( Exception e )
{
  e.printStackTrace();
  out.print( "</SELECT>\n" );
  out.print( "</TD>\n<TD>&nbsp;</TD></TR>" );
  out.print( "An Error Occured While Querying the Databse" );
  out.print( "</BODY>\n</HTML>\n" );
  return;
}
finally
{
  if ( dbrs != null ) {dbrs.close();}
  if ( totalrecords == 0 )
  {
    out.print( "</SELECT>\n" );
    out.print( "</TD>\n<TD>&nbsp;</TD></TR>" );
    out.print( "<TR><TD>No Records Found</TD><TD>&nbsp;</TD></TR>\n" );
  }
}

out.print( "</SELECT>\n" );
out.print( "</TD>\n<TD ALIGN=\"LEFT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"olddate\" value=\"Ok\" OnClick=\"ShowSearch(name,this)\" type=\"button\"></TD></TR>" );

out.print( "<TR><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>\n" );
out.print( "<TR><TD ALIGN=\"RIGHT\">(MM/DD/YYYY) <INPUT CLASS=\"HEADER\" type=\"text\" name=\"newrevdate\" value=\"\" SIZE=\"8\" maxlength=\"10\"></TD><TD ALIGN=\"LEFT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"newdate\" value=\"New\" OnClick=\"ShowSearch(name,this)\" type=\"button\"></TD></TR>\n" );
out.print( "<TR><TD>&nbsp;</TD></TR>\n" );
out.print( "</TABLE>\n" );
out.print( "<CENTER>\n" );
out.print( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"cancelbutton\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n" );
out.print( "<INPUT TYPE=\"hidden\" NAME=\"MfgID\" VALUE=\"" + MfgID1 + "\">\n" );
out.print( "<INPUT TYPE=\"hidden\" NAME=\"control_name\" VALUE=\"" + control_name + "\">\n" );
out.print( "</FORM></BODY></HTML>\n" );

return;
}

protected void printJavaScripts()
{
//StringBuffer Msglt=new StringBuffer();

out.print( "<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n" );
out.print( "<!--\n" );
out.print( "function cancel()\n" );
out.print( "{\n" );
out.print( "window.close();\n" );
out.print( "}\n" );

return;
}
}