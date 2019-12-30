package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.share.dbObjs.CatAddQc;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import com.tcmis.common.admin.beans.PersonnelBean;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-04-02
 * Showing the suggested supplier information the user enters
 * 11-05-02
 * checking for VVCATALOGSTUFF and doing the VVStuff if it is not already Done
 * 03-20-03 - Adding part Description to the view original request
 * 07-23-03 Code cleanup
 * 12-22-03 - Code Refactoring, moved vv_stuff to different class
 * 02-18-04 - Adding VAPOR_PRESSURE_DETECT, VAPOR_PRESSURE, VAPOR_PRESSURE_UNIT, VAPOR_PRESSURE_TEMP, VAPOR_PRESSURE_TEMP_UNIT to page
 * 11-10-04 - Added new VOC fields
 */

public class tcmIsQualityCheckOriginal
{
	private ServerResourceBundle bundle = null;
	private TcmISDBModel db = null;
	private String Msds_details_Servlet = "";
	private String Msds_orig_Servlet = "";
	private boolean intcmIsApplication = false;
	public tcmIsQualityCheckOriginal(ServerResourceBundle b, TcmISDBModel d)
	{
		bundle = b;
		db = d;
	}

	public void doResult( HttpServletRequest request,HttpServletResponse response,HttpSession orgsession )
	throws ServletException,IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		CatAddQc cataddqcObj=new CatAddQc( db );
		Msds_details_Servlet=bundle.getString( "QUALITY_CONTROL_DETAILS" );
		Msds_orig_Servlet=bundle.getString( "QUALITY_CONTROL" );
		String User_Action=null;
		String StatusofReq="";
		String request_id=null;
		String line_item=null;

		PersonnelBean personnelBean = (PersonnelBean) orgsession.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
			intcmIsApplication = true;
		}

		User_Action=request.getParameter( "Action" );
		if ( User_Action == null )
			User_Action="New";

		request_id=request.getParameter( "request_id" );
		if ( request_id == null )
			request_id="";

		line_item=request.getParameter( "line_item" );
		if ( line_item == null )
			line_item="";

		String company_selected=request.getParameter( "company" );
		if ( company_selected == null )
			company_selected="0";
		if ( company_selected.length() < 1 )
		{
			company_selected="0";
		}

		try
		{
			String donevvstuff=orgsession.getAttribute( "VVCATALOGSTUFF" ) == null ? "" : orgsession.getAttribute( "VVCATALOGSTUFF" ).toString();

			if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
			{
				orgsession.setAttribute( "CATEGORY",radian.web.vvHelpObj.getcategory(db) );
				orgsession.setAttribute( "PKG_STYLE",radian.web.vvHelpObj.getpkgstyle(db) );
				orgsession.setAttribute( "SIZE_UNIT",radian.web.vvHelpObj.getsizeunit(db) );
				orgsession.setAttribute( "STORAGE_TEMP",radian.web.vvHelpObj.getstoragetemp(db) );
				orgsession.setAttribute( "WEIGHT_UNIT",radian.web.vvHelpObj.getweightunit(db) );
				orgsession.setAttribute( "ITEM_TYPE",radian.web.vvHelpObj.getitemtype(db) );
				orgsession.setAttribute( "PHYSICAL_STATE",radian.web.vvHelpObj.getphysicalstate(db) );
				orgsession.setAttribute( "VOC_UNIT",radian.web.vvHelpObj.getvocunit(db) );
				orgsession.setAttribute( "DENSITY_UNIT",radian.web.vvHelpObj.getdensityunit(db) );
				orgsession.setAttribute( "SP_HAZARD",radian.web.vvHelpObj.getsphazard(db) );
				orgsession.setAttribute( "PPE",radian.web.vvHelpObj.getppe(db) );
				orgsession.setAttribute( "VAPOR_PRESSURE_UNIT",radian.web.vvHelpObj.getvapourpressunit(db) );
				orgsession.setAttribute( "VAPOR_PRESSURE_DETECT",radian.web.vvHelpObj.getvapourpressdetect(db) );
				orgsession.setAttribute( "VOC_SOURCE",radian.web.vvHelpObj.getvocsource(db) );

				Vector compnayids=new Vector();
				String companyArrayJs="";

				compnayids=radian.web.vvHelpObj.getcatCompanyIds(db);
				companyArrayJs=radian.web.vvHelpObj.getCatalogJs( db,compnayids ).toString();

				orgsession.setAttribute( "COMPANY_IDS",compnayids );
				orgsession.setAttribute( "CATALOGS_JS",companyArrayJs );

				orgsession.setAttribute( "VVCATALOGSTUFF","Yes" );
			}

			if ( User_Action.equalsIgnoreCase( "Original" ) )
			{
				Hashtable Data=new Hashtable();
				Data=cataddqcObj.getOrigHashtableData( request_id );
				Hashtable HeaderData=new Hashtable();
				HeaderData=cataddqcObj.getHeaderData( request_id, line_item );
				StatusofReq=cataddqcObj.getRequestStatus( request_id, line_item);

				buildDetails( out,Data,HeaderData );
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

	public void buildTabs( int totalNum,PrintWriter out )
	{
		//StringBuffer Msgd3=new StringBuffer();

		int numofTabs=totalNum * 2 + 2;
		int usedWith=0;

		out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"500\">\n" );
		out.println( "<TR><TD COLSPAN=" + numofTabs + " HEIGHT=\"15\"></TD></TR>\n" );
		out.println( "<TR>\n" );
		out.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );
		out.println( "<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component1_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_selected_tab\" onClick=\"togglePage('Component1', '');\"> 1\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );

		usedWith+=36;

		if ( totalNum > 1 )
		{
			for ( int count=1; count < totalNum; count++ )
			{
				out.println( "<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component" + ( count + 1 ) + "_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_tab\" onClick=\"togglePage('Component" + ( count + 1 ) + "', '');\"> " + ( count + 1 ) + "\n" );
				out.println( "</TD>\n" );
				out.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );
				usedWith+=33;
			}
		}

		out.println( "<TD WIDTH=\"" + ( 800 - usedWith ) + "\" HEIGHT=\"15\" CLASS=\"tab_buffer\">&nbsp;</TD>\n" );
		out.println( "</TR>\n" );
		out.println( "<TR><TD CLASS=\"tab\" COLSPAN=" + numofTabs + " HEIGHT=\"2\"></TD></TR>\n" );
		out.println( "</TABLE>\n" );

		return;
	}

	public void buildDetails( PrintWriter out,Hashtable DataH,Hashtable HeaderData1 )
	{
		int total= ( ( Integer ) ( DataH.get( "TOTAL_NUMBER" ) ) ).intValue();

		Vector ComponentData= ( Vector ) DataH.get( "DATA" );
		//StringBuffer Msgd1=new StringBuffer();
		
		out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Original Submitted Data","catoriginaldetails.js",intcmIsApplication ) );
		out.println( "</HEAD>\n" );
		out.println( "<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\" onload =\"initTabs()\">\n" );
		buildArrayofCom( total,out);
		out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "<B>Catalog Add QC Original Data</B>\n" ) );
		out.println( "<BR><FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Suggested Supplier Information:</B></FONT>&nbsp;\n" );
		//HEADER Information
		out.println( "<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" CELLPADDING=\"0\">\n" );

		out.println( "<TR>\n<TD HEIGHT=\"20\" CLASS=\"Inwhiter\" WIDTH=\"10%\"><B>Supplier:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
		out.println( HeaderData1.get( "SUGGESTED_VENDOR" ) );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inwhiter\" WIDTH=\"10%\"><B>Contact:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
		out.println( HeaderData1.get( "VENDOR_CONTACT_NAME" ) );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inwhiter\" WIDTH=\"10%\"><B>&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inwhitel\">&nbsp;</TD>\n" );

		out.println( "</TR>\n" );

		out.println( "<TR>\n<TD HEIGHT=\"20\" CLASS=\"Inbluer\" WIDTH=\"10%\"><B>Email:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inbluel\">" );
		out.println( HeaderData1.get( "VENDOR_CONTACT_EMAIL" ) );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inbluer\" WIDTH=\"10%\"><B>Phone:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"15%\" CLASS=\"Inbluel\">" );
		out.println( HeaderData1.get( "VENDOR_CONTACT_PHONE" ) );
		out.println( "</TD>\n" );

		out.println( "<TD HEIGHT=\"20\" CLASS=\"Inbluer\" WIDTH=\"10%\"><B>Fax:&nbsp;&nbsp;</B></TD>\n" );
		out.println( "<TD HEIGHT=\"20\" WIDTH=\"10%\" CLASS=\"Inbluel\">" );
		out.println( HeaderData1.get( "VENDOR_CONTACT_FAX" ) );
		out.println( "</TD>\n" );

		out.println( "</TR>\n</TABLE>\n" );
		out.println( "<DIV><B>Part Description:&nbsp;&nbsp;</B>\n" );
		out.println( HeaderData1.get( "PART_DESCRIPTION" ) );
		out.println( "</DIV><br/>" );
		
		out.println( "<DIV CLASS=\"Inbluer\"><B>Additional Comments:&nbsp;&nbsp;</B>\n" );
		out.println( HeaderData1.get( "MESSAGE_TO_APPROVERS" ) );
		out.println( "</DIV><br/>" );

		buildTabs( total,out );

		for ( int i=0; i < total; i++ )
		{
			Hashtable hD=new Hashtable();
			hD= ( Hashtable ) ComponentData.elementAt( i );

			out.println( "<DIV ID=\"Component" + ( i + 1 ) + "\" STYLE=\"display: none;\">\n" );
			out.println( "<FORM METHOD=\"POST\" name=\"ComponentForm" + ( i + 1 ) + "\" action=\"" + Msds_details_Servlet +
			"Session=Update\" onsubmit =\"return CheckValues(this)\">\n" );
			buildDivs( hD,out );
			out.println( "</FORM>\n" );
			out.println( "</DIV>\n" );
		}

		out.println( "</BODY>\n</HTML>\n" );

		//out.println( Msgd1 );
		return;
	}

	public void buildDivs( Hashtable DataH ,PrintWriter out)
	{
		//StringBuffer Msgd=new StringBuffer();

		try
		{
			out.println( "\n" );
			//SEGATE MSDS No
			out.println( "<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" CELLPADDING=\"3\">\n" );
			//MANUFACTURER
			out.println( "<TR>\n<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inbluer\"><B>Manufacturer: </B></TD><TD HEIGHT=\"30\" COLSPAN=\"3\" CLASS=\"Inbluel\" WIDTH=\"30%\" >" );
			out.println( DataH.get( "MANUFACTURER" ) );
			out.println( "</TD>\n" );

			/*out.println( "<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inbluer\"><B>&nbsp;</B></TD><TD HEIGHT=\"30\" WIDTH=\"30%\" CLASS=\"Inbluer\">" );
        out.println( "&nbsp;</TD>\n</TR>\n\n" );*/

			out.println( "</TR>\n" );

			//MATERIAL_DESC
			out.println( "<TR>\n<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inwhiter\"><B>Material Description: </B></TD><TD HEIGHT=\"30\" COLSPAN=\"3\" WIDTH=\"30%\" CLASS=\"Inwhitel\">" );
			out.println( DataH.get( "MATERIAL_DESC" ) );
			out.println( "\n" );
			out.println( "</TD>\n" );

			//Material ID
			//out.println( "<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inwhiter\"><B>&nbsp; </B></TD><TD HEIGHT=\"30\" WIDTH=\"30%\" CLASS=\"Inwhitel\">" );
			//out.println(DataH.get("MATERIAL_ID"));
			//out.println( "&nbsp;</TD>\n</TR>\n" );

			out.println( "</TR>\n" );

			//GRADE
			out.println( "<TR><TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inbluel\"><B>Grade: </B><BR>" );
			out.println( DataH.get( "GRADE" ) );
			out.println( "</TD>\n" );

			//SAMPLE_SIZE
			out.println( "<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inbluel\"><B>Sample Size: </B><BR>" );
			out.println( DataH.get( "SAMPLE_SIZE" ) );
			out.println( "</TD>\n" );

			//TRADE_NAME
			out.println( "<TD HEIGHT=\"30\" WIDTH=\"20%\" COLSPAN=\"2\" CLASS=\"Inbluel\"><B>Trade Name: </B><BR>" );
			out.println( DataH.get( "TRADE_NAME" ) );
			out.println( "</TD></TR>\n" );

			//# OF COMPONENTS
			out.println( "<TR>\n<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inwhitel\"><B># Comp: </B><BR>" );
			out.println( DataH.get( "NO_OF_COMPONENTS" ) );
			out.println( "</TD>\n" );
			//Part Size
			out.println( "<TD HEIGHT=\"30\" WIDTH=\"30%\" CLASS=\"Inwhitel\"><B>Part Size: </B><BR>" );
			out.println( DataH.get( "PART_SIZE" ) );
			out.println( "</TD>\n" );
			//Size Unit
			out.println( "<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inwhitel\"><B>Size Unit: </B><BR>" );
			String size_units=DataH.get( "PART_SIZE_UNIT" ).toString();
			out.println( size_units );
			out.println( "</TD>\n" );

			//Packaging Style
			out.println( "<TD HEIGHT=\"30\" WIDTH=\"30%\" CLASS=\"Inwhitel\"><B>Packaging Style: </B><BR>" );
			String packaging_style=DataH.get( "PACKAGING_STYLE" ).toString();
			out.println( packaging_style );
			out.println( "</TD></TR>\n" );

			//Dimension
			out.println( "<TR>\n<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inbluel\"><B>Dimension: </B><BR>" );
			out.println( DataH.get( "DIMENSION" ) );
			out.println( "</TD>\n" );
			//Net Wt
			out.println( "<TD HEIGHT=\"30\" WIDTH=\"30%\" CLASS=\"Inbluel\"><B>Net Wt: </B><BR>" );
			out.println( DataH.get( "NET_WEIGHT" ) );
			out.println( "</TD>\n" );
			//Net Wt Unit
			out.println( "<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inbluel\"><B>Net Wt Unit: </B><BR>" );
			String net_weight_unit=DataH.get( "NET_WEIGHT_UNIT" ).toString();
			out.println( net_weight_unit );
			out.println( "</TD>\n" );
			//MFG_PART_NO
			out.println( "<TD HEIGHT=\"30\" WIDTH=\"30%\" CLASS=\"Inbluel\"><B>Mfg Part #: </B><BR>" );
			out.println( DataH.get( "MFG_PART_NO" ) );
			out.println( "</TD></TR>\n" );

			//Label Color
			out.println( "<TR>\n<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inwhitel\"><B>Label Color: </B><BR>" );
			out.println( DataH.get( "LABEL_COLOR" ) );
			out.println( "</TD>\n" );

			out.println( "<TD HEIGHT=\"30\" WIDTH=\"30%\" CLASS=\"Inwhitel\">&nbsp;<BR>" );
			out.println( "</TD>\n" );

			out.println( "<TD HEIGHT=\"30\" WIDTH=\"20%\" CLASS=\"Inwhitel\">&nbsp;<BR>" );
			out.println( "</TD>\n" );

			out.println( "<TD HEIGHT=\"30\" WIDTH=\"30%\" CLASS=\"Inwhitel\">&nbsp;<BR>" );
			out.println( "</TD></TR>\n" );

			out.println( "</TABLE>\n" );
		}
		catch ( Exception e1 )
		{
			e1.printStackTrace();
			out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occured While Building Divs" ) );
			return;
		}

		return;
	}
}