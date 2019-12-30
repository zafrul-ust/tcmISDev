
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
//
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.editMsds;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.helpers.HelpObjs;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 11-04-02
 * loading formcheck.js to facilitate checking for integer values of specific gravity density etc. Operator entering NA in numeric fields which
 * was causing errors.
 *
 * 11-05-02
 * Made Some changes to avoid the nullpointer Exception when looking for PART_ID. Added PART_ID to the hiddenElements and getting it back
 *
 * checking for VVCATALOGSTUFF and doing the VVStuff if it is not already Done
 *
 * 11-15-02
 * adding PERSONAL_PROTECTION to hmis data
 *
 * 01-03-03
 * Adding a text area to enter ALT_DATA_SOURCE information
 * 06-16-03 - Code Clanup Remove DOT Stuff
 * 07-14-03 - Removing the DOT and Solids info from the page
 * 07-31-03 - Showing the Mfg Contact when they select the manufacturer
 * 08-13-03 - Giving the option to Leavel Flash Point Unit Blank
 * 08-18-03 - Giving a message when a cas_number is not found and an error occures
 * 12-22-03 - Code Refactoring, moved vv_stuff to different class
 * 02-18-04 - Adding VAPOR_PRESSURE_DETECT, VAPOR_PRESSURE, VAPOR_PRESSURE_UNIT, VAPOR_PRESSURE_TEMP, VAPOR_PRESSURE_TEMP_UNIT to page
 * 11-10-04 - Added new VOC fields
 */

public class editMSDSQualityCheck
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private Vector part_size_unit = null;
    private Vector pgk_style = null;
    private Vector storage_temp = null;
    private Vector physicalState = null;
    private Vector category;
    private Vector vocunit = null;
    private Vector densityunit = null;
    private Vector vvsphazard = null;
    private Vector vvppe = null;
	private Vector vvvpunit = null;
	private Vector vvvpdetect = null;
	private Vector vocsource = null;
    private boolean completeSuccess = true ;
    private String editmsdserror = "";
    private boolean intcmIsApplication = false;

    public editMSDSQualityCheck(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResult(HttpServletRequest request, HttpServletResponse response,HttpSession msdssession)  throws ServletException,  IOException
    {
        PrintWriter out = response.getWriter();

        PersonnelBean personnelBean = (PersonnelBean) msdssession.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        intcmIsApplication = false;
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

        String personnelid= msdssession.getAttribute( "PERSONNELID" ) == null ? "" : msdssession.getAttribute( "PERSONNELID" ).toString();
        editMsds editMsdsObj = new editMsds(db);

        String User_Action=request.getParameter( "Action" );
        if ( User_Action == null )
          User_Action="New";

        String CompanyId=request.getParameter( "Company" );
        if ( CompanyId == null )
          CompanyId="";

        String company_selected=request.getParameter( "company" );
        if ( company_selected == null )
          company_selected="0";

        if (company_selected.length() < 1){company_selected = "0";}

        try
        {
          String donevvstuff=msdssession.getAttribute( "VVCATALOGSTUFF" ) == null ? "" : msdssession.getAttribute( "VVCATALOGSTUFF" ).toString();
          if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
          {
              msdssession.setAttribute("CATEGORY",radian.web.vvHelpObj.getcategory(db));
              msdssession.setAttribute("PKG_STYLE",radian.web.vvHelpObj.getpkgstyle(db));
              msdssession.setAttribute("SIZE_UNIT",radian.web.vvHelpObj.getsizeunit(db));
              msdssession.setAttribute("STORAGE_TEMP",radian.web.vvHelpObj.getstoragetemp(db));
              msdssession.setAttribute("WEIGHT_UNIT",radian.web.vvHelpObj.getweightunit(db));
              msdssession.setAttribute("ITEM_TYPE",radian.web.vvHelpObj.getitemtype(db));
              msdssession.setAttribute("PHYSICAL_STATE",radian.web.vvHelpObj.getphysicalstate(db));
              msdssession.setAttribute("VOC_UNIT",radian.web.vvHelpObj.getvocunit(db));
              msdssession.setAttribute("DENSITY_UNIT",radian.web.vvHelpObj.getdensityunit(db));
              msdssession.setAttribute("SP_HAZARD",radian.web.vvHelpObj.getsphazard(db));
              msdssession.setAttribute("PPE",radian.web.vvHelpObj.getppe(db));
			  msdssession.setAttribute( "VAPOR_PRESSURE_UNIT",radian.web.vvHelpObj.getvapourpressunit(db) );
			  msdssession.setAttribute( "VAPOR_PRESSURE_DETECT",radian.web.vvHelpObj.getvapourpressdetect(db) );
			  msdssession.setAttribute( "VOC_SOURCE",radian.web.vvHelpObj.getvocsource(db) );

              Vector compnayids = new Vector();
              String companyArrayJs = "";
              String CompanyName1 = "";

              compnayids = radian.web.vvHelpObj.getcatCompanyIds(db);
              companyArrayJs = radian.web.vvHelpObj.getCatalogJs(db,compnayids).toString();

              int index = Integer.parseInt(company_selected);
              CompanyName1 = compnayids.elementAt(index).toString();
              msdssession.setAttribute("COMPANY_IDS",compnayids);
              msdssession.setAttribute("CATALOGS_JS",companyArrayJs);

              msdssession.setAttribute("VVCATALOGSTUFF","Yes");
            }

            if ( User_Action.equalsIgnoreCase( "save" ) )
            {
              part_size_unit= ( Vector ) msdssession.getAttribute( "SIZE_UNIT" );
              pgk_style= ( Vector ) msdssession.getAttribute( "PKG_STYLE" );
              storage_temp= ( Vector ) msdssession.getAttribute( "STORAGE_TEMP" );
              category= ( Vector ) msdssession.getAttribute( "CATEGORY" );
              physicalState= ( Vector ) msdssession.getAttribute( "PHYSICAL_STATE" );
              vocunit= ( Vector ) msdssession.getAttribute( "VOC_UNIT" );
              densityunit= ( Vector ) msdssession.getAttribute( "DENSITY_UNIT" );
              vvsphazard= ( Vector ) msdssession.getAttribute( "SP_HAZARD" );
              vvppe= ( Vector ) msdssession.getAttribute( "PPE" );
			  vvvpunit = ( Vector ) msdssession.getAttribute( "VAPOR_PRESSURE_UNIT" );
			  vvvpdetect = ( Vector ) msdssession.getAttribute( "VAPOR_PRESSURE_DETECT" );
			  vocsource= ( Vector ) msdssession.getAttribute( "VOC_SOURCE" );

              Hashtable retrn_data= ( Hashtable ) msdssession.getAttribute( "MSDS_COMPONENT_DATA" );
              Vector ComponentData1= ( Vector ) retrn_data.get( "DATA" );
              Vector synch_data=SynchServerData( request,ComponentData1 );

              int total1= ( ( Integer ) ( retrn_data.get( "TOTAL_NUMBER" ) ) ).intValue();
              Vector updateresults=UpdateDetails( synch_data,personnelid,total1 );

              retrn_data.remove( "DATA" );
              retrn_data.put( "DATA",synch_data );
              msdssession.setAttribute( "MSDS_COMPONENT_DATA",retrn_data );

              if ( completeSuccess )
              {
                buildDetails( out,"Saved the Data Successfully",retrn_data,CompanyId );
              }
              else
              {
                buildDetails( out,"An Error Occured when saving Data<BR>"+editmsdserror+"",retrn_data,CompanyId );
              }

              retrn_data=null;
            }
            else if ( User_Action.equalsIgnoreCase( "BrandNew" ) )
            {
              part_size_unit= ( Vector ) msdssession.getAttribute( "SIZE_UNIT" );
              pgk_style= ( Vector ) msdssession.getAttribute( "PKG_STYLE" );
              storage_temp= ( Vector ) msdssession.getAttribute( "STORAGE_TEMP" );
              category= ( Vector ) msdssession.getAttribute( "CATEGORY" );
              physicalState= ( Vector ) msdssession.getAttribute( "PHYSICAL_STATE" );
              vocunit= ( Vector ) msdssession.getAttribute( "VOC_UNIT" );
              densityunit= ( Vector ) msdssession.getAttribute( "DENSITY_UNIT" );
              vvsphazard= ( Vector ) msdssession.getAttribute( "SP_HAZARD" );
              vvppe= ( Vector ) msdssession.getAttribute( "PPE" );
			  vvvpunit = ( Vector ) msdssession.getAttribute( "VAPOR_PRESSURE_UNIT" );
			  vvvpdetect = ( Vector ) msdssession.getAttribute( "VAPOR_PRESSURE_DETECT" );
			  vocsource= ( Vector ) msdssession.getAttribute( "VOC_SOURCE" );

              Hashtable Data=new Hashtable();
              Data=editMsdsObj.getMSDSData();

              msdssession.setAttribute( "MSDS_COMPONENT_DATA",Data );

              try
              {
                buildDetails( out,"",Data,CompanyId );
              }
              catch ( Exception budetails )
              {
                budetails.printStackTrace();
              }
            }
            else
            {
              part_size_unit=new Vector();
              pgk_style=new Vector();
              storage_temp=new Vector();
              category=new Vector();
              donevvstuff="";
              donevvstuff=msdssession.getAttribute( "VVCATALOGSTUFF" ) == null ? "" : msdssession.getAttribute( "VVCATALOGSTUFF" ).toString();

              if ( donevvstuff.equalsIgnoreCase( "Yes" ) )
              {
                part_size_unit= ( Vector ) msdssession.getAttribute( "SIZE_UNIT" );
                pgk_style= ( Vector ) msdssession.getAttribute( "PKG_STYLE" );
                storage_temp= ( Vector ) msdssession.getAttribute( "STORAGE_TEMP" );
                category= ( Vector ) msdssession.getAttribute( "CATEGORY" );
                physicalState= ( Vector ) msdssession.getAttribute( "PHYSICAL_STATE" );
                vocunit= ( Vector ) msdssession.getAttribute( "VOC_UNIT" );
                densityunit= ( Vector ) msdssession.getAttribute( "DENSITY_UNIT" );
                vvsphazard= ( Vector ) msdssession.getAttribute( "SP_HAZARD" );
                vvppe= ( Vector ) msdssession.getAttribute( "PPE" );
				vvvpunit = ( Vector ) msdssession.getAttribute( "VAPOR_PRESSURE_UNIT" );
				vvvpdetect = ( Vector ) msdssession.getAttribute( "VAPOR_PRESSURE_DETECT" );
				vocsource= ( Vector ) msdssession.getAttribute( "VOC_SOURCE" );
              }
              else
              {
                msdssession.setAttribute( "CATEGORY",radian.web.vvHelpObj.getcategory(db) );
                msdssession.setAttribute( "PKG_STYLE",radian.web.vvHelpObj.getpkgstyle(db) );
                msdssession.setAttribute( "SIZE_UNIT",radian.web.vvHelpObj.getsizeunit(db) );
                msdssession.setAttribute( "STORAGE_TEMP",radian.web.vvHelpObj.getstoragetemp(db) );
                msdssession.setAttribute( "WEIGHT_UNIT",radian.web.vvHelpObj.getweightunit(db) );
                msdssession.setAttribute( "ITEM_TYPE",radian.web.vvHelpObj.getitemtype(db) );
                msdssession.setAttribute( "PHYSICAL_STATE",radian.web.vvHelpObj.getphysicalstate(db) );
                msdssession.setAttribute( "VOC_UNIT",radian.web.vvHelpObj.getvocunit(db) );
                msdssession.setAttribute( "DENSITY_UNIT",radian.web.vvHelpObj.getdensityunit(db) );
				msdssession.setAttribute( "VAPOR_PRESSURE_UNIT",radian.web.vvHelpObj.getvapourpressunit(db) );
				msdssession.setAttribute( "VAPOR_PRESSURE_DETECT",radian.web.vvHelpObj.getvapourpressdetect(db) );
				msdssession.setAttribute( "VOC_SOURCE",radian.web.vvHelpObj.getvocsource(db) );

                msdssession.setAttribute( "VVCATALOGSTUFF","Yes" );
              }

              Hashtable Data=new Hashtable();
              Data=editMsdsObj.getMSDSData();
              msdssession.setAttribute( "MSDS_COMPONENT_DATA",Data );

              try
              {
                buildDetails( out,"",Data,CompanyId );
              }
              catch ( Exception budetails )
              {
                budetails.printStackTrace();
              }
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

  public void buildTabs( int totalNum,Vector DataT,PrintWriter out )
  {
    //StringBuffer Msgd3=new StringBuffer();

    int numofTabs=totalNum * 2 + 2;
    int usedWith=0;

    out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"800\">\n" );
    out.println( "<TR>\n" );
    out.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );

    Hashtable hD1=new Hashtable();
    hD1= ( Hashtable ) DataT.elementAt( 0 );
    int MaterialId=BothHelpObjs.makeZeroFromNull( hD1.get( "MATERIAL_ID" ).toString() );

    if (MaterialId > 0)
    {
      out.println("<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component1_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_selected_tab\" onClick=\"togglePage('Component1', '');\"> 1\n");
    }
    else
    {
      out.println("<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component1_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_nomatID_selected\" onClick=\"togglePage('Component1', '');\"> 1\n");
    }

    out.println( "</TD>\n" );
    out.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );

    usedWith+=36;

    if ( totalNum > 1 )
    {
      for ( int count=1; count < totalNum; count++ )
      {
        hD1=new Hashtable();
        hD1= ( Hashtable ) DataT.elementAt( count );

        MaterialId=BothHelpObjs.makeZeroFromNull( hD1.get( "MATERIAL_ID" ).toString() );
        if ( MaterialId > 0 )
        {
          out.println("<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component"+(count+1)+"_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_tab\" onClick=\"togglePage('Component"+(count+1)+"', '');\"> "+(count+1)+"\n");
        }
        else
        {
          out.println("<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component"+(count+1)+"_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_nomatID\" onClick=\"togglePage('Component"+(count+1)+"', '');\"> "+(count+1)+"\n");
        }
        out.println( "</TD>\n" );
        out.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );
        usedWith+=33;
      }
    }

    out.println( "<TD WIDTH=\"" + ( 800 - usedWith ) + "\" HEIGHT=\"15\" CLASS=\"tab_buffer\">&nbsp;</TD>\n" );
    out.println( "</TR>\n" );
    out.println( "<TR>\n<TD CLASS=\"tab\" COLSPAN=" + numofTabs + " HEIGHT=\"2\"></TD></TR>\n" );
    out.println( "</TABLE>\n" );

    return;
  }

  public void buildDetails( PrintWriter out,String Message,Hashtable DataH,String CompanyId1 )
     throws Exception
  {

    int total= ( ( Integer ) ( DataH.get( "TOTAL_NUMBER" ) ) ).intValue();
    //System.out.println( "Total Number : " + total );

    String Msds_qc_Servlet=radian.web.tcmisResourceLoader.geteditmsdsurl();
    Vector msdsComponentData= ( Vector ) DataH.get( "DATA" );

    //StringBuffer Msgd1=new StringBuffer();

    out.println( radian.web.HTMLHelpObj.printHTMLHeader( "" + CompanyId1 + " MSDS Cataloging","editmsdsqcdetails.js",intcmIsApplication ) );
    out.println( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
    out.println( "</HEAD>\n" );

    if ( total == 0 )
    {
      out.println( "<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\" onload=\"resetApplicationTimer()\">\n" );
    }
    else if ( "Processed The request Successfully".equalsIgnoreCase( Message ) )
    {
      out.println( "<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\" onload=\"resetApplicationTimer()\">\n" );
    }
    else
    {
      out.println( "<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\" onload =\"initTabs()\">\n" );
	}

    buildArrayofCom( total,out );
    out.println( radian.web.HTMLHelpObj.PrintCatalogTitleBar( "<B>Maintain MSDS</B>\n" ) );
    out.println( "<FORM METHOD=\"POST\" name=\"MainForm\" onSubmit=\"return SubmitOnlyOnce()\" action=\"" + Msds_qc_Servlet + "\" >\n" );
    out.println( "<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" CELLPADDING=\"5\">\n" );

    if ( Message.length() > 1 )
    {
      out.println( "<TR>\n<TD WIDTH=\"100%\" COLSPAN=\"7\" ALIGN=\"CENTER\">\n" );
      out.println( "<FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>" + Message + "</B></FONT>&nbsp;\n" );
      out.println( "</TD>\n</TR>\n" );
    }

    //QCStatus
    out.println( "<TR>\n<TD WIDTH=\"75%\" ALIGN=\"LEFT\">\n" );
    out.println( "<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Maintain MSDS</B></FONT>&nbsp;\n" );
    out.println( "</TD>\n" );

    if ( !"Processed The request Successfully".equalsIgnoreCase( Message ) )
	{
	//out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"reset\" value=\"Reset\" name=\"Reset\"></TD>\n");
    out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Save\" name=\"Save\" onclick= \"return actionValue(name,this)\"></TD>\n");
    out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"New\" name=\"BrandNew\" onclick= \"return actionValue(name,this)\"></TD>\n");
    //out.println("<TD WIDTH=\"8%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\" name=\"searchmfgidlike\" value=\"View Request\" OnClick=\"ShowOriginal(this.form)\"></TD>\n");
	}

    out.println( "</TR>\n" );
    out.println( "</TABLE>\n" );


    out.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Details\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" NAME=\"request_id\" VALUE=\"\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" NAME=\"totalComps\" VALUE=\"" + total + "\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" NAME=\"CompanyId\" VALUE=\"" + CompanyId1 + "\">\n" );


    if ( "Processed The request Successfully".equalsIgnoreCase( Message ) )
    {
      out.println( "This Request Has passed MSDS stage.\n" );
    }
    else
    {
      for ( int i=0; i < total; i++ )
      {
        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) msdsComponentData.elementAt( i );
        buildHiddenElements( hD,i,out );
      }

      out.println( "</FORM>\n" );

      for ( int i=0; i < total; i++ )
      {
        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) msdsComponentData.elementAt( i );

	    out.println("<DIV ID=\"Component"+(i+1)+"\" STYLE=\"display: none;\">\n");
	    out.println("<FORM METHOD=\"POST\" name=\"ComponentForm"+(i+1)+"\" action=\""+Msds_qc_Servlet+"Session=Update\" onsubmit =\"return CheckValues(this)\">\n");

	    buildDivs(hD,i,out);
	    out.println("</FORM>\n");
	    out.println("</DIV>\n");
	  }
	}

    out.println( "</BODY>\n</HTML>\n" );
    //out.println( Msgd1 );
  }

  public void buildHiddenElements( Hashtable DataH,int TabNumber,PrintWriter out )
  {
    //StringBuffer MsgHE=new StringBuffer();

    try
    {
      Hashtable materialData=null;
      Hashtable msdsData=null;
      Hashtable compositionData=null;
      Vector ofCompositionData=null;

      materialData= ( Hashtable ) DataH.get( "MATERIAL_DATA" );
      msdsData= ( Hashtable ) DataH.get( "MSDS_DATA" );
      ofCompositionData= ( Vector ) DataH.get( "COMPOSITION_DATA" );

      out.println( "<INPUT TYPE=\"hidden\" NAME=\"partid" + TabNumber + "\" VALUE=\"" + DataH.get( "PART_ID" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"materialdesc" + TabNumber + "\" VALUE=\"" + materialData.get( "MATERIAL_DESC" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"manufacturer" + TabNumber + "\" VALUE=\"" + materialData.get( "MANUFACTURER" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"materialid" + TabNumber + "\" VALUE=\"" + materialData.get( "MATERIAL_ID" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"mfgid" + TabNumber + "\" VALUE=\"" + materialData.get( "MFG_ID" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"mfgphone" + TabNumber + "\" VALUE=\"" + materialData.get( "PHONE" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"mfgurl" + TabNumber + "\" VALUE=\"" + materialData.get( "MFG_URL" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"mfgcontact" + TabNumber + "\" VALUE=\"" + materialData.get( "CONTACT" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"tradename" + TabNumber + "\" VALUE=\"" + materialData.get( "MFG_TRADE_NAME" ).toString() + "\">\n" );
      /*out.println("<INPUT TYPE=\"hidden\" NAME=\"landdot"+TabNumber+"\" VALUE=\""+materialData.get("LANDDOT").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"airdot"+TabNumber+"\" VALUE=\""+materialData.get("AIRDOT").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"waterdot"+TabNumber+"\" VALUE=\""+materialData.get("WATERDOT").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"erg"+TabNumber+"\" VALUE=\""+materialData.get("ERG").toString()+"\">\n");*/
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"revdate" + TabNumber + "\" VALUE=\"" + msdsData.get( "REVISION_DATE" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"content" + TabNumber + "\" VALUE=\"" + msdsData.get( "CONTENT" ).toString() + "\">\n" );
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("EMERGENCY_PHONE").toString()+"\">\n");
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"spegravity" + TabNumber + "\" VALUE=\"" + msdsData.get( "SPECIFIC_GRAVITY" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"nfpah" + TabNumber + "\" VALUE=\"" + msdsData.get( "HEALTH" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"nfpar" + TabNumber + "\" VALUE=\"" + msdsData.get( "FLAMMABILITY" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"nfpaf" + TabNumber + "\" VALUE=\"" + msdsData.get( "REACTIVITY" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"nfpas" + TabNumber + "\" VALUE=\"" + msdsData.get( "SPECIFIC_HAZARD" ).toString() + "\">\n" );

      out.println( "<INPUT TYPE=\"hidden\" NAME=\"hmish" + TabNumber + "\" VALUE=\"" + msdsData.get( "HEALTH" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"hmisr" + TabNumber + "\" VALUE=\"" + msdsData.get( "FLAMMABILITY" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"hmisf" + TabNumber + "\" VALUE=\"" + msdsData.get( "REACTIVITY" ).toString() + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"hmiss" + TabNumber + "\" VALUE=\"" + msdsData.get( "PERSONAL_PROTECTION" ).toString() + "\">\n" );

      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("COMPATIBILITY").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("STORAGE_TEMP").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("PPE").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("SIGNAL_WORD").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("TARGET_ORGAN").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("ROUTE_OF_ENTRY").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("SKIN").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("EYES").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("INHALATION").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("INJECTION").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("BOILING_POINT").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"flashpoint"+TabNumber+"\" VALUE=\""+msdsData.get("FLASH_POINT").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("PH").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("FREEZING_POINT").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"density"+TabNumber+"\" VALUE=\""+msdsData.get("DENSITY").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("INGESTION").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("ON_LINE").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"denunits"+TabNumber+"\" VALUE=\""+msdsData.get("DENSITY_UNIT").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"physicalstate"+TabNumber+"\" VALUE=\""+msdsData.get("PHYSICAL_STATE").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"vocunits"+TabNumber+"\" VALUE=\""+msdsData.get("VOC_UNIT").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("TSCA_12B").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("SARA_311_312_ACUTE").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("SARA_311_312_CHRONIC").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("SARA_311_312_FIRE").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("SARA_311_312_PRESSURE").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("SARA_311_312_REACTIVITY").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("OSHA_HAZARD").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("TSCA_LIST").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("MIXTURE").toString()+"\">\n");

      out.println("<INPUT TYPE=\"hidden\" NAME=\"voc"+TabNumber+"\" VALUE=\""+msdsData.get("VOC").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"voclower"+TabNumber+"\" VALUE=\""+msdsData.get("VOC_LOWER").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"vocupper"+TabNumber+"\" VALUE=\""+msdsData.get("VOC_UPPER").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("REVIEWED_BY").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("REVIEW_DATE").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"msdsremark"+TabNumber+"\" VALUE=\""+msdsData.get("REMARK").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"flashpointunits"+TabNumber+"\" VALUE=\""+msdsData.get("FLASH_POINT_UNIT").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"percsolids"+TabNumber+"\" VALUE=\""+msdsData.get("SOLIDS").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"percsolidslower"+TabNumber+"\" VALUE=\""+msdsData.get("SOLIDS_LOWER").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"percsolidsupper"+TabNumber+"\" VALUE=\""+msdsData.get("SOLIDS_UPPER").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"percsolidsunit"+TabNumber+"\" VALUE=\""+msdsData.get("SOLIDS_UNIT").toString()+"\">\n");
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"solids_source" + TabNumber + "\" VALUE=\"" + msdsData.get( "SOLIDS_SOURCE" ).toString() + "\">\n" );
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("SOLIDS_PERCENT").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("SOLIDS_LOWER_PERCENT").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("SOLIDS_UPPER_PERCENT").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("VOC_LOWER_PERCENT").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("VOC_UPPER_PERCENT").toString()+"\">\n");
      //out.println("<INPUT TYPE=\"hidden\" NAME=\"cccc"+TabNumber+"\" VALUE=\""+msdsData.get("VOC_PERCENT").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"altdatasource"+TabNumber+"\" VALUE=\""+msdsData.get("ALT_DATA_SOURCE").toString()+"\">\n");
	  out.println("<INPUT TYPE=\"hidden\" NAME=\"vappressdet"+TabNumber+"\" VALUE=\""+msdsData.get("VAPOR_PRESSURE_DETECT").toString()+"\">\n");
	  out.println("<INPUT TYPE=\"hidden\" NAME=\"vappress"+TabNumber+"\" VALUE=\""+msdsData.get("VAPOR_PRESSURE").toString()+"\">\n");
	  out.println("<INPUT TYPE=\"hidden\" NAME=\"vappressunit"+TabNumber+"\" VALUE=\""+msdsData.get("VAPOR_PRESSURE_UNIT").toString()+"\">\n");
	  out.println("<INPUT TYPE=\"hidden\" NAME=\"vappresstemp"+TabNumber+"\" VALUE=\""+msdsData.get("VAPOR_PRESSURE_TEMP").toString()+"\">\n");
	  out.println("<INPUT TYPE=\"hidden\" NAME=\"vappresstempunt"+TabNumber+"\" VALUE=\""+msdsData.get("VAPOR_PRESURE_TEMP_UNIT").toString()+"\">\n");
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"vapor_pressure_source" + TabNumber + "\" VALUE=\"" + msdsData.get( "VAPOR_PRESSURE_SOURCE" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"vapor_pressure_lower" + TabNumber + "\" VALUE=\"" + msdsData.get( "VAPOR_PRESSURE_LOWER" ).toString() + "\">\n" );
		out.println( "<INPUT TYPE=\"hidden\" NAME=\"vapor_pressure_upper" + TabNumber + "\" VALUE=\"" + msdsData.get( "VAPOR_PRESSURE_UPPER" ).toString() + "\">\n" );

	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"voc_source" + TabNumber + "\" VALUE=\"" + msdsData.get( "VOC_SOURCE" ).toString() + "\">\n" );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"voc_less_h2o_exempt" + TabNumber + "\" VALUE=\"" + msdsData.get( "VOC_LESS_H2O_EXEMPT" ).toString() + "\">\n" );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"voc_less_h2o_exempt_unit" + TabNumber + "\" VALUE=\"" + msdsData.get( "VOC_LESS_H2O_EXEMPT_UNIT" ).toString() + "\">\n" );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"voc_less_h2o_exempt_lower" + TabNumber + "\" VALUE=\"" + msdsData.get( "VOC_LESS_H2O_EXEMPT_LOWER" ).toString() + "\">\n " );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"voc_less_h2o_exempt_upper" + TabNumber + "\" VALUE=\"" + msdsData.get( "VOC_LESS_H2O_EXEMPT_UPPER" ).toString() + "\">\n" );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"voc_less_h2o_exempt_source" + TabNumber + "\" VALUE=\"" + msdsData.get( "VOC_LESS_H2O_EXEMPT_SOURCE" ).toString() + "\">\n " );

	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"voc_lb_per_solid_lb" + TabNumber + "\" VALUE=\"" + msdsData.get( "VOC_LB_PER_SOLID_LB" ).toString() + "\">\n" );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"voc_lb_per_solid_lb_source" + TabNumber + "\" VALUE=\"" + msdsData.get( "VOC_LB_PER_SOLID_LB_SOURCE" ).toString() + "\">\n" );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"voc_lb_per_solid_lb_lower" + TabNumber + "\" VALUE=\"" + msdsData.get( "VOC_LB_PER_SOLID_LB_LOWER" ).toString() + "\">\n" );
	  out.println( "<INPUT TYPE=\"hidden\" NAME=\"voc_lb_per_solid_lb_upper" + TabNumber + "\" VALUE=\"" + msdsData.get( "VOC_LB_PER_SOLID_LB_UPPER" ).toString() + "\">\n" );

      int compositionexists = ofCompositionData.size();

      for (int z=0; z < compositionexists; z++)
      {
      compositionData = new Hashtable();
      compositionData = (Hashtable) ofCompositionData.elementAt(z);

      out.println("<INPUT TYPE=\"hidden\" NAME=\"percent"+z+""+TabNumber+"\" VALUE=\""+compositionData.get("PERCENT").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"percent_lower"+z+""+TabNumber+"\" VALUE=\""+compositionData.get("PERCENT_LOWER").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"percent_upper"+z+""+TabNumber+"\" VALUE=\""+compositionData.get("PERCENT_UPPER").toString()+"\">\n");
      /*out.println("<INPUT TYPE=\"hidden\" NAME=\"hazardous"+z+""+TabNumber+"\" VALUE=\""+compositionData.get("HAZARDOUS").toString()+"\">\n");*/
      out.println("<INPUT TYPE=\"hidden\" NAME=\"cas_number"+z+""+TabNumber+"\" VALUE=\""+compositionData.get("CAS_NUMBER").toString()+"\">\n");
      /*out.println("<INPUT TYPE=\"hidden\" NAME=\"trade_secret"+z+""+TabNumber+"\" VALUE=\""+compositionData.get("TRADE_SECRET").toString()+"\">\n");*/
      out.println("<INPUT TYPE=\"hidden\" NAME=\"remark"+z+""+TabNumber+"\" VALUE=\""+compositionData.get("REMARK").toString()+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"msds_chemical_name"+z+""+TabNumber+"\" VALUE=\""+compositionData.get("MSDS_CHEMICAL_NAME").toString()+"\">\n");

     }

     for (int rem=compositionexists; rem < 50; rem++)
     {
      out.println("<INPUT TYPE=\"hidden\" NAME=\"percent"+rem+""+TabNumber+"\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"percent_as_cas"+rem+""+TabNumber+"\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"percent_lower"+rem+""+TabNumber+"\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"percent_upper"+rem+""+TabNumber+"\" VALUE=\"\">\n");
      /*out.println("<INPUT TYPE=\"hidden\" NAME=\"hazardous"+rem+""+TabNumber+"\" VALUE=\"\">\n");*/
      out.println("<INPUT TYPE=\"hidden\" NAME=\"cas_number"+rem+""+TabNumber+"\" VALUE=\"\">\n");
      /*out.println("<INPUT TYPE=\"hidden\" NAME=\"trade_secret"+rem+""+TabNumber+"\" VALUE=\"\">\n");*/
      out.println("<INPUT TYPE=\"hidden\" NAME=\"remark"+rem+""+TabNumber+"\" VALUE=\"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"msds_chemical_name"+rem+""+TabNumber+"\" VALUE=\"\">\n");
     }
   }
   catch ( Exception e112 )
   {
     e112.printStackTrace();
   }

   return;
}

 public void buildDivs( Hashtable DataH,int TabNumber,PrintWriter out )
 {
   //StringBuffer Msgd=new StringBuffer();
   Hashtable materialData=null;
   Hashtable msdsData=null;
   Hashtable compositionData=null;
   Vector ofCompositionData=null;
   materialData= ( Hashtable ) DataH.get( "MATERIAL_DATA" );
   msdsData= ( Hashtable ) DataH.get( "MSDS_DATA" );
   ofCompositionData= ( Vector ) DataH.get( "COMPOSITION_DATA" );

   try
   {
     out.println( "\n" );
     out.println( "<TABLE BORDER=0 CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=100% >\n" );
     //Material title
     out.println( "<TR>\n<TD WIDTH=\"20%\" COLSPAN=\"8\" ALIGN=\"LEFT\"><U><B>Material</B></U></TD></TR>\n" );

     //Mfg ID
     out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Mfg ID: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"INVISIBLEHEADBLUE\" name=\"mfgid" + TabNumber + "\" value=\"" );
     out.println(materialData.get("MFG_ID"));
     out.println("\" SIZE=\"7\" readonly><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchmfgidlike\" value=\"...\" OnClick=\"getmfgID(this.form,"+TabNumber+")\" type=\"button\"></TD>\n");
     //MANUFACTURER
     out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Manufacturer: </B></TD><TD COLSPAN=\"5\" CLASS=\"Inbluel\" WIDTH=\"25%\" >");
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"manufacturer" + TabNumber + "\" value=\"" );
     out.println( materialData.get( "MANUFACTURER" ) );
     out.println( "\" SIZE=\"60\"></TD>\n" );
     /*//Land DOT
     out.println( "<TD WIDTH=\"8%\" CLASS=\"Inbluer\">&nbsp;</TD><TD WIDTH=\"20%\" CLASS=\"Inbluel\">" );
     out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"landdot"+TabNumber+"\" value=\"");
     out.println(materialData.get("LANDDOT"));
     out.println( "&nbsp;</TD></TR>\n" );*/
     out.println( "</TR>\n" );

     String mfgurl=materialData.get( "MFG_URL" ) == null ? "" : materialData.get( "MFG_URL" ).toString().trim();
     if ( !( mfgurl.startsWith( "http://" ) || mfgurl.startsWith( "https://" ) ) && ( mfgurl.length() > 0 ) )
     {
       mfgurl="http://" + mfgurl;
     }
     String mfgcontact=materialData.get( "CONTACT" ).toString();
		 String mfgPhone=materialData.get( "PHONE" ).toString();
     //Mfg Phone
     out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Mfg Phone: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
		 out.println( "<div name=\"mfgphonediv" + TabNumber + "\" id=\"mfgphonediv" + TabNumber + "\">" );
			out.println( materialData.get( "PHONE" ) );
			out.println( "</div><INPUT TYPE=\"hidden\" name=\"mfgphone" + TabNumber + "\" VALUE=\""+mfgPhone+"\"><INPUT TYPE=\"hidden\" name=\"mfgurl" + TabNumber + "\" VALUE=\""+mfgurl+"\"><INPUT TYPE=\"hidden\" name=\"mfgcontact" + TabNumber + "\" VALUE=\""+mfgcontact+"\"></TD>\n" );
     //MANUFACTURER Contact
     out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Mfg Contact: </B></TD><TD COLSPAN=\"5\" CLASS=\"Inwhitel\" ID=\"mfgurlinfo"+TabNumber+"\" WIDTH=\"25%\" >" );
     if ( ( mfgcontact.length() > 0 ) && ( mfgurl.length() > 0 ) )
     {
       out.println( "<A onclick=\"javascript:window.open('" + mfgurl + "')\" STYLE=\"color:#0000ff;cursor:hand\">" + mfgcontact + "</A>" );
     }
     else if ( mfgurl.length() > 0 )
     {
       out.println( "<A onclick=\"javascript:window.open('" + mfgurl + "')\" STYLE=\"color:#0000ff;cursor:hand\">" + mfgurl + "</A>" );
     }
     else if ( mfgcontact.length() > 0 )
     {
       out.println( mfgcontact );
     }

     out.println( "</TD>\n" );
     /*//Land DOT
     //out.println( "<TD WIDTH=\"8%\" CLASS=\"Inwhiter\"><B>Land DOT: </B></TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">" );
     out.println( "<TD WIDTH=\"8%\" CLASS=\"Inwhiter\">&nbsp;</TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"landdot" + TabNumber + "\" value=\"" );
     out.println( materialData.get( "LANDDOT" ) );
     out.println( "\" SIZE=\"20\"></TD></TR>\n" );
     out.println( "&nbsp;</TD></TR>\n" );*/
     out.println( "</TR>\n" );

     //Mfg email
	 out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Mfg Email: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
	 out.println( "<INPUT type=\"text\" CLASS=\"INVISIBLEHEADBLUE\" name=\"mfgemail" + TabNumber + "\" value=\"" );
	 out.println( materialData.get( "EMAIL" ) );
	 out.println( "\" SIZE=\"20\" readonly></TD>\n" );
	 //MANUFACTURER Notes
	 out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Mfg Notes: </B></TD><TD COLSPAN=\"5\" CLASS=\"Inbluel\" ID=\"mfgnotes"+TabNumber+"\" WIDTH=\"25%\" >" );
	 out.println( materialData.get( "NOTES" ) );
	 out.println( "</TD>\n" );
	 out.println( "</TR>\n" );

     //Material ID
     out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Material ID: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"INVISIBLEHEADWHITE\" name=\"materialid" + TabNumber + "\" value=\"" );
     out.println( materialData.get( "MATERIAL_ID" ) );
     out.println("\" SIZE=\"7\" readonly><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchmatlike"+TabNumber+"\" value=\"...\" OnClick=\"getmaterialID(this.form,"+TabNumber+")\" type=\"button\">\n");
     out.println("</TD>\n");
     //MATERIAL_DESC
     out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Material Description: </B></TD><TD COLSPAN=\"5\" WIDTH=\"25%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"materialdesc" + TabNumber + "\" value=\"" );
		 out.print( HelpObjs.findReplace( materialData.get( "MATERIAL_DESC" ).toString().trim(),"\"","&#34;" ) );
     out.println( "\" SIZE=\"60\" MAXLENGTH=\"200\"></TD>\n" );
     /*//Air Dot
     //out.println( "<TD WIDTH=\"8%\" CLASS=\"Inbluer\"><B>Air DOT: </B></TD><TD WIDTH=\"20%\" CLASS=\"Inbluel\">" );
     out.println( "<TD WIDTH=\"8%\" CLASS=\"Inwhiter\">&nbsp;</TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"airdot" + TabNumber + "\" value=\"" );
     out.println( materialData.get( "AIRDOT" ) );
     out.println( "\" SIZE=\"20\"></TD></TR>\n" );
     out.println( "&nbsp;</TD></TR>\n" );*/
     out.println( "</TR>\n" );

     //ERG
     //out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>ERG: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
     out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhiter\">&nbsp;</TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
     /*out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"erg" + TabNumber + "\" value=\"" );
     out.println( materialData.get( "ERG" ) );
     out.println( "\" SIZE=\"5\"></TD>\n" );*/
     out.println( "&nbsp;</TD>\n" );
     //MFG_TRADE_NAME
     out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Trade Name: </B></TD><TD COLSPAN=\"5\" WIDTH=\"25%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"tradename" + TabNumber + "\" value=\"" );
		 out.print( HelpObjs.findReplace( materialData.get( "MFG_TRADE_NAME" ).toString().trim(),"\"","&#34;" ) );
     out.println( "\" SIZE=\"60\" MAXLENGTH=\"200\"></TD>\n" );
     /*//Water DOT
     //out.println( "<TD WIDTH=\"8%\" CLASS=\"Inwhiter\"><B>Water DOT: </B></TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">" );
     out.println( "<TD WIDTH=\"8%\" CLASS=\"Inwhiter\">&nbsp;</TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"waterdot" + TabNumber + "\" value=\"" );
     out.println( materialData.get( "WATERDOT" ) );
     out.println( "\" SIZE=\"20\"></TD></TR>\n" );
     out.println( "&nbsp;</TD></TR>\n" );*/
    out.println( "</TR>\n" );

     //MSDS title
     out.println( "<TR>\n<TD WIDTH=\"20%\" COLSPAN=\"8\" ALIGN=\"LEFT\"><U><B>MSDS</B></U></TD></TR>\n" );

     //Rev Date
     out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Rev Date: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"INVISIBLEHEADBLUE\" name=\"revdate" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "REVISION_DATE" ) );
     out.println("\" maxlength=\"10\" SIZE=\"10\" readonly><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchmfgidlike\" value=\"...\" OnClick=\"getrevdate(this.form,"+TabNumber+")\" type=\"button\"></TD>\n");
     //Content
     out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Content: </B></TD><TD COLSPAN=\"5\" WIDTH=\"25%\" CLASS=\"Inbluel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"content" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "CONTENT" ) );
     out.println( "\" SIZE=\"65\">\n" );
     //View MSDS
     out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"msdsbutton"+TabNumber+"\" value=\"View File\" OnClick=\"Showfile(this.form,"+TabNumber+")\" type=\"button\"></TD>\n");
     out.println("</TR>\n" );

     // NFPA H
     out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>NFPA:&nbsp;&nbsp;&nbsp;Health</B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"nfpah" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "HEALTH" ) );
     out.println( "\" SIZE=\"2\" MAXLENGTH=\"30\"></TD>\n" );
     //F
     out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Flammability: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"nfpaf" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "FLAMMABILITY" ) );
     out.println( "\" SIZE=\"2\" MAXLENGTH=\"30\"></TD>\n" );
     //R
     out.println( "<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Reactivity: </B></TD><TD WIDTH=\"8%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"nfpar" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "REACTIVITY" ) );
     out.println( "\" SIZE=\"2\" MAXLENGTH=\"30\"></TD>\n" );
     //S
     out.println( "<TD WIDTH=\"8%\" CLASS=\"Inwhiter\"><B>Special: </B></TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">" );
     /*out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"nfpas" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "SPECIFIC_HAZARD" ) );
     out.println( "\" SIZE=\"2\" MAXLENGTH=\"30\"></TD></TR>\n" );*/
     String spechaz = msdsData.get( "SPECIFIC_HAZARD" ).toString();
     out.println( "<SELECT CLASS=\"HEADER\" NAME=\"nfpas" + TabNumber + "\" size=\"1\">\n" );
     out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
     out.println( radian.web.HTMLHelpObj.getDropDown( vvsphazard,spechaz ) );
     out.println( "</SELECT>\n</TD>\n" );
     out.println( "</TR>\n" );

     /*	    out.println("<TD WIDTH=\"8%\" CLASS=\"Inwhiter\">&nbsp;</TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">");
                 out.println("&nbsp;</TD></TR>\n");*/

     // HMIS H
     out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>HMIS:&nbsp;&nbsp;&nbsp;Health</B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"hmish" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "HMIS_HEALTH" ) );
     out.println( "\" SIZE=\"2\" MAXLENGTH=\"30\"></TD>\n" );
     //F
     out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Flammability: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"hmisf" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "HMIS_FLAMMABILITY" ) );
     out.println( "\" SIZE=\"2\" MAXLENGTH=\"30\"></TD>\n" );
     //R
     out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Reactivity: </B></TD><TD WIDTH=\"8%\" CLASS=\"Inbluel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"hmisr" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "HMIS_REACTIVITY" ) );
     out.println( "\" SIZE=\"2\" MAXLENGTH=\"30\"></TD>\n" );

     //PERSONAL_PROTECTION
     out.println( "<TD WIDTH=\"8%\" CLASS=\"Inbluer\"><B>Personal Protection: </B></TD><TD WIDTH=\"20%\" CLASS=\"Inbluel\">" );
     /*out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"hmiss" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "PERSONAL_PROTECTION" ) );
     out.println( "\" SIZE=\"2\" MAXLENGTH=\"5\"></TD></TR>\n" );*/
     String pppe = msdsData.get( "PERSONAL_PROTECTION" ).toString();
     out.println( "<SELECT CLASS=\"HEADER\" NAME=\"hmiss" + TabNumber + "\" size=\"1\">\n" );
     out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
     out.println( radian.web.HTMLHelpObj.getDropDown( vvppe,pppe ) );
     out.println( "</SELECT>\n</TD>\n" );
     out.println( "</TR>\n" );

     //Specific Gravity
     out.println( "<TR>\n<TD WIDTH=\"10%\"  CLASS=\"Inwhiter\"><B>Specific Gravity: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"spegravity" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "SPECIFIC_GRAVITY" ) );
     out.println( "\" SIZE=\"15\"></TD>\n" );
     //Density
     out.println( "<TD WIDTH=\"10%\"  CLASS=\"Inwhiter\"><B>Density: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
     out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"density" + TabNumber + "\" value=\"" );
     out.println( msdsData.get( "DENSITY" ) );
     out.println( "\" SIZE=\"15\"></TD>\n" );
     //Units
     out.println( "<TD WIDTH=\"10%\"  CLASS=\"Inwhiter\"><B>Units: </B></TD><TD WIDTH=\"8%\" CLASS=\"Inwhitel\">" );
     String densityun=msdsData.get( "DENSITY_UNIT" ).toString();
     out.println( "<SELECT CLASS=\"HEADER\" NAME=\"denunits" + TabNumber + "\" size=\"1\">\n" );
     out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
     out.println( radian.web.HTMLHelpObj.getDropDown( densityunit,densityun ) );
     out.println( "</SELECT>\n</TD>\n" );
     //Blank
     out.println( "<TD WIDTH=\"8%\"  CLASS=\"Inwhiter\"><B>&nbsp;</B></TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">" );
     out.println( "&nbsp;</TD></TR>\n" );

	 //Flash Point
	  out.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Flash Point: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">");
	  out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"flashpoint"+TabNumber+"\" value=\"");
	  out.println(msdsData.get("FLASH_POINT"));out.println("\" SIZE=\"5\">&nbsp;&nbsp;\n");
	  out.println( "<SELECT CLASS=\"HEADER\" NAME=\"flashpointunits" + TabNumber + "\" size=\"1\">\n" );
	  if ( "F".equalsIgnoreCase( msdsData.get( "FLASH_POINT_UNIT" ).toString() ) )
	  {
		out.println( "<option value=\"\">None</option>\n" );
		out.println( "<option selected value=\"F\">F</option>\n" );
	  }
	  else
	  {
		out.println( "<option selected value=\"\">None</option>\n" );
		out.println( "<option value=\"F\">F</option>\n" );
	  }
	  out.println( "</SELECT>\n" );
	  out.println( "</TD>\n" );

	  //out.println(msdsData.get("FLASH_POINT"));out.println("\" SIZE=\"5\">&nbsp;&nbsp;F</TD>\n");
	  //Units
	  /*out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Units: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">");
	  out.println("<SELECT CLASS=\"HEADER\" NAME=\"flashpointunits"+TabNumber+"\" size=\"1\">\n");
	  if ("C".equalsIgnoreCase(msdsData.get("FLASH_POINT_UNIT").toString()))
	  {
	  out.println("<option value=\"F\">F</option>\n");
	  out.println("<option selected value=\"C\">C</option>\n");
	  }
	  else
	  {
	  out.println("<option selected value=\"F\">F</option>\n");
	  out.println("<option value=\"C\">C</option>\n");
	  }
	  out.println("</SELECT>\n");
	  out.println("</TD>\n");*/

	  //Physical State
	  out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Physical State: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">");
	  out.println("<SELECT CLASS=\"HEADER\" NAME=\"physicalstate"+TabNumber+"\" size=\"1\">\n");
	  String phystate = msdsData.get("PHYSICAL_STATE").toString();
	  out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
	  out.println(radian.web.HTMLHelpObj.getDropDown(physicalState,phystate));
	  out.println("</SELECT>\n");
	  out.println("</TD>\n");

	  //FLASH POINT UNIT
	  out.println("<TD WIDTH=\"8%\" CLASS=\"Inbluer\"><B>&nbsp;</B></TD><TD WIDTH=\"8%\" VALIGN=\"MIDDLE\" CLASS=\"Inbluel\">");
	  //out.println("&nbsp;<INPUT type=\"hidden\" NAME=\"flashpointunits"+TabNumber+"\" value=\"F\"></TD>\n");
	  out.println("&nbsp;</TD>\n");

	  //BLANK
	  out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>&nbsp;</B></TD><TD WIDTH=\"20%\" CLASS=\"Inbluel\">");
	  out.println("&nbsp;</TD></TR>\n");

	  //VOC
	  out.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>VOC: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">");
	  out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"voc"+TabNumber+"\" value=\"");
	  out.println(msdsData.get("VOC"));out.println("\" SIZE=\"5\"></TD>\n");
	  //VOC Lower
	  out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>VOC Lower: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">");
	  out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"voclower"+TabNumber+"\" value=\"");
	  out.println(msdsData.get("VOC_LOWER"));out.println("\" SIZE=\"5\"></TD>\n");
	  //VOC Upper
	  out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>VOC Upper: </B></TD><TD WIDTH=\"8%\" CLASS=\"Inwhitel\">");
	  out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"vocupper"+TabNumber+"\" value=\"");
	  out.println(msdsData.get("VOC_UPPER"));out.println("\" SIZE=\"5\"></TD>\n");
	  //Units
	  out.println("<TD WIDTH=\"8%\" CLASS=\"Inwhiter\"><B>Units:<BR><BR>Source:</B></TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">");
	  String vocun = msdsData.get("VOC_UNIT").toString();
	  out.println("<SELECT CLASS=\"HEADER\" NAME=\"vocunits"+TabNumber+"\" size=\"1\">\n");
	  out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
	  out.println(radian.web.HTMLHelpObj.getDropDown(vocunit,vocun));
	  out.println("</SELECT>\n<BR>\n");
	  String vocleswatersource = msdsData.get("VOC_SOURCE").toString();
	  out.println("<SELECT CLASS=\"HEADER\" NAME=\"voc_source"+TabNumber+"\" size=\"1\">\n");
	  out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
	  out.println(radian.web.HTMLHelpObj.getDropDown(vocsource,vocleswatersource));
	  out.println("</SELECT>\n</TD>\n");

	  //VOC Less Water and Exempt
	  out.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>VOC Less Water and Exempt: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">");
	  out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"voc_less_h2o_exempt"+TabNumber+"\" value=\"");
	  out.println(msdsData.get("VOC_LESS_H2O_EXEMPT"));out.println("\" SIZE=\"5\"></TD>\n");
	  //VOC Lower
	  out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>VOC Less Water and Exempt Lower: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">");
	  out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"voc_less_h2o_exempt_lower"+TabNumber+"\" value=\"");
	  out.println(msdsData.get("VOC_LESS_H2O_EXEMPT_LOWER"));out.println("\" SIZE=\"5\"></TD>\n");
	  //VOC Less Water and Exempt Upper
	  out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>VOC Less Water and Exempt Upper: </B></TD><TD WIDTH=\"8%\" CLASS=\"Inbluel\">");
	  out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"voc_less_h2o_exempt_upper"+TabNumber+"\" value=\"");
	  out.println(msdsData.get("VOC_LESS_H2O_EXEMPT_UPPER"));out.println("\" SIZE=\"5\"></TD>\n");
	  //Units
	  out.println("<TD WIDTH=\"8%\" CLASS=\"Inbluer\"><B>Units:<BR><BR>Source: </B></TD><TD WIDTH=\"20%\" CLASS=\"Inbluel\">");
	  String vocleswaterunit = msdsData.get("VOC_LESS_H2O_EXEMPT_UNIT").toString();
	  out.println("<SELECT CLASS=\"HEADER\" NAME=\"voc_less_h2o_exempt_unit"+TabNumber+"\" size=\"1\">\n");
	  out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
	  out.println(radian.web.HTMLHelpObj.getDropDown(vocunit,vocleswaterunit));
	  out.println("</SELECT>\n<BR>\n");
	  String voc_source = msdsData.get("VOC_LESS_H2O_EXEMPT_SOURCE").toString();
	  out.println("<SELECT CLASS=\"HEADER\" NAME=\"voc_less_h2o_exempt_source"+TabNumber+"\" size=\"1\">\n");
	  out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
	  out.println(radian.web.HTMLHelpObj.getDropDown(vocsource,voc_source));
	  out.println("</SELECT>\n</TD>\n");

	  // Solids
	  out.println( "<TR>\n<TD WIDTH=\"10%\"  CLASS=\"Inwhiter\"><B>Solids: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
	  out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"percsolids" + TabNumber + "\" value=\"" );
	  out.println( msdsData.get( "SOLIDS" ) );
	  out.println( "\" SIZE=\"5\"></TD>\n" );
	  // Solids Lower
	  out.println( "<TD WIDTH=\"10%\"  CLASS=\"Inwhiter\"><B>Solids Lower: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
	  out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"percsolidslower" + TabNumber + "\" value=\"" );
	  out.println( msdsData.get( "SOLIDS_LOWER" ) );
	  out.println( "\" SIZE=\"5\"></TD>\n" );
	  // Solids Upper
	  out.println( "<TD WIDTH=\"10%\"  CLASS=\"Inwhiter\"><B>Solids Upper: </B></TD><TD WIDTH=\"8%\" CLASS=\"Inwhitel\">" );
	  out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"percsolidsupper" + TabNumber + "\" value=\"" );
	  out.println( msdsData.get( "SOLIDS_UPPER" ) );
	  out.println( "\" SIZE=\"5\"></TD>\n" );
	  //Units
	  out.println( "<TD WIDTH=\"8%\"  CLASS=\"Inwhiter\"><B>Units:<BR><BR>Source: </B></TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">" );
	  String solidsun=msdsData.get( "SOLIDS_UNIT" ).toString();
	  out.println( "<SELECT CLASS=\"HEADER\" NAME=\"percsolidsunit" + TabNumber + "\" size=\"1\">\n" );
	  out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	  out.println( radian.web.HTMLHelpObj.getDropDown( vocunit,solidsun ) );
	  out.println( "</SELECT>\n<BR>\n" );
	  String solids_source = msdsData.get("SOLIDS_SOURCE").toString();
	  out.println("<SELECT CLASS=\"HEADER\" NAME=\"solids_source"+TabNumber+"\" size=\"1\">\n");
	  out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
	  out.println(radian.web.HTMLHelpObj.getDropDown(vocsource,solids_source));
	  out.println("</SELECT>\n</TD>\n");

	  // VOC Solids
	  out.println("<TR>\n<TD WIDTH=\"10%\"  CLASS=\"Inbluer\"><B>VOC Solids: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">");
	  out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"voc_lb_per_solid_lb"+TabNumber+"\" value=\"");
	  out.println(msdsData.get("VOC_LB_PER_SOLID_LB"));out.println("\" SIZE=\"5\"></TD>\n");
	  // VOC Solids Lower
	  out.println("<TD WIDTH=\"10%\"  CLASS=\"Inbluer\"><B>VOC Solids Lower: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">");
	  out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"voc_lb_per_solid_lb_lower"+TabNumber+"\" value=\"");
	  out.println(msdsData.get("VOC_LB_PER_SOLID_LB_LOWER"));out.println("\" SIZE=\"5\"></TD>\n");
	  // VOC Solids Upper
	  out.println("<TD WIDTH=\"10%\"  CLASS=\"Inbluer\"><B>VOC Solids Upper: </B></TD><TD WIDTH=\"8%\" CLASS=\"Inbluel\">");
	  out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"voc_lb_per_solid_lb_upper"+TabNumber+"\" value=\"");
	  out.println(msdsData.get("VOC_LB_PER_SOLID_LB_UPPER"));out.println("\" SIZE=\"5\"></TD>\n");
	  //VOC Solids Source
	  out.println("<TD WIDTH=\"8%\"  CLASS=\"Inbluer\"><B>Source: </B></TD><TD WIDTH=\"20%\" CLASS=\"Inbluel\">");
	  String vocsolidsun = msdsData.get("VOC_LB_PER_SOLID_LB_SOURCE").toString();
	  out.println("<SELECT CLASS=\"HEADER\" NAME=\"voc_lb_per_solid_lb_source"+TabNumber+"\" size=\"1\">\n");
	  out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
	  out.println(radian.web.HTMLHelpObj.getDropDown(vocsource,vocsolidsun));
	  out.println("</SELECT>\n</TD>\n");

 //VAPOR_PRESSURE_SOURCE
 out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>VP Source: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
 String vapor_pressure_source = msdsData.get("VAPOR_PRESSURE_SOURCE").toString();
 out.println("<SELECT CLASS=\"HEADER\" NAME=\"vapor_pressure_source"+TabNumber+"\" size=\"1\">\n");
 out.println("<OPTION VALUE=\"\">Please Select</OPTION>\n");
 out.println(radian.web.HTMLHelpObj.getDropDown(vocsource,vapor_pressure_source));
 out.println("</SELECT>\n</TD>\n");

//VAPOR_PRESSURE
 out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>VP: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">");
 out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"vappress"+TabNumber+"\" value=\"");
 out.println(msdsData.get("VAPOR_PRESSURE"));out.println("\" SIZE=\"5\"></TD>\n");

//VAPOR_PRESSURE_LOWER
 out.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>VP Lower: </B></TD><TD WIDTH=\"8%\" CLASS=\"Inwhitel\">");
 out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"vapor_pressure_lower"+TabNumber+"\" value=\"");
 out.println(msdsData.get("VAPOR_PRESSURE_LOWER"));out.println("\" SIZE=\"5\"></TD>\n");

//VAPOR_PRESSURE_UPPER
 out.println("<TD WIDTH=\"8%\" CLASS=\"Inwhiter\"><B>VP Upper: </B></TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">");
 out.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"vapor_pressure_upper"+TabNumber+"\" value=\"");
 out.println(msdsData.get("VAPOR_PRESSURE_UPPER"));out.println("\" SIZE=\"5\"></TD>\n");
 out.println("</TR>\n" );

//
 out.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\">&nbsp;</TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">");
 out.println("&nbsp;</TD>\n");

//VAPOR_PRESSURE_DETECT
 out.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>VP Detect: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
 String vappressdet=msdsData.get( "VAPOR_PRESSURE_DETECT" ).toString();
 out.println( "<SELECT CLASS=\"HEADER\" NAME=\"vappressdet" + TabNumber + "\" size=\"1\">\n" );
 out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
 out.println( radian.web.HTMLHelpObj.getDropDown( vvvpdetect,vappressdet ) );
 out.println( "</SELECT>\n</TD>\n" );

//VAPOR_PRESSURE_UNIT
 out.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>VP Pressure Unit: </B></TD><TD WIDTH=\"8%\" CLASS=\"Inbluel\">");
 String vappressunit = msdsData.get("VAPOR_PRESSURE_UNIT").toString();
 out.println("<SELECT CLASS=\"HEADER\" NAME=\"vappressunit"+TabNumber+"\" size=\"1\">\n");
 out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
 out.println(radian.web.HTMLHelpObj.getDropDown(vvvpunit,vappressunit));
 out.println("</SELECT>\n</TD>\n");

//VAPOR_PRESSURE_TEMP and UNIT
 out.println( "<TD WIDTH=\"8%\" CLASS=\"Inbluer\"><B>VP Temp: </B></TD><TD WIDTH=\"20%\" CLASS=\"Inbluel\">" );
 out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"vappresstemp" + TabNumber + "\" value=\"" );
 out.println( msdsData.get( "VAPOR_PRESSURE_TEMP" ) );
 out.println( "\" SIZE=\"5\">&nbsp;&nbsp;\n" );
 out.println("<SELECT CLASS=\"HEADER\" NAME=\"vappresstempunt"+TabNumber+"\" size=\"1\">\n");
 out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
 if ("F".equalsIgnoreCase(msdsData.get("VAPOR_PRESURE_TEMP_UNIT").toString()))
 {
	out.println("<option value=\"C\">C</option>\n");
	out.println("<option selected value=\"F\">F</option>\n");
 }
 else if ("C".equalsIgnoreCase(msdsData.get("VAPOR_PRESURE_TEMP_UNIT").toString()))
 {
	out.println("<option selected value=\"C\">C</option>\n");
	out.println("<option value=\"F\">F</option>\n");
 }
 else
 {
	out.println("<option value=\"C\">C</option>\n");
	out.println("<option value=\"F\">F</option>\n");
 }
 out.println("</SELECT>\n");
 out.println("</TD>\n</TR>\n" );

//Comments
 out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\" ALIGN=\"LEFT\"><B>Comments: </B></TD>\n" );
 out.println( "<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" COLSPAN=\"7\">" );
 out.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"msdsremark" + TabNumber + "\" value=\"" );
 out.print( HelpObjs.findReplace( msdsData.get( "REMARK" ).toString().trim(),"\"","&#34;" ) );
 out.println( "\" SIZE=\"59\" maxlength=\"60\"></TD>\n</TR>" );

//ALT_DATA_SOURCE
 out.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Alt Data Source: </B></TD>\n" );
 out.println("<TD WIDTH=\"15%\" CLASS=\"Inbluel\" COLSPAN=\"7\">");
 out.println("<TEXTAREA name=\"altdatasource"+TabNumber+"\" rows=\"3\" cols=\"100\">\n");out.println(msdsData.get("ALT_DATA_SOURCE").toString().trim());out.println("\n</TEXTAREA></TD>\n");
 out.println( "</TR>\n" );

     out.println( "</TABLE>\n" );

     //out.println("<INPUT TYPE=\"hidden\" NAME=\"chemcomp_size"+TabNumber+"\" VALUE=\""+DataH.get("NO_OF_CHEMICAL_COMP").toString()+"\">\n");

     out.println( "<TABLE BORDER=0 CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=100%>\n" );
     //Composition title
     out.println( "<TR>\n<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\"><U><B>Composition</B></U></TD>\n" );
     out.println("<TD VALIGN=\"MIDDLE\" ALIGN=\"LEFT\">\n");
     out.println("<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"ItIsaButton\" OnClick=\"getCasNum(this.form)\" value=\"Lookup CAS Num\" type=\"button\">\n</TD>\n");
     //<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchmfgidlike\" value=\"...\" OnClick=\"getmfgID(this.form,"+TabNumber+")\" type=\"button\"></TD>\n");
     out.println( "</TR>\n" );
     out.println( "</TABLE>\n" );

     out.println("<TABLE id=composition_table"+(TabNumber+1)+" BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
     //out.println("<TABLE id=composition_table"+(TabNumber+1)+" BORDER=0 CELLSPACING=\"0\" CELLPADDING=\"2\" WIDTH=100% >\n");
     //Table Header
     out.println( "<TR>\n<TH HEIGHT=\"35\" WIDTH=\"15%\">CAS Num</TH>\n" );
     out.println( "<TH HEIGHT=\"35\" WIDTH=\"30%\">MSDS Chemical Name</TH>\n" );
     out.println( "<TH HEIGHT=\"35\" WIDTH=\"5%\">%</TH>\n" );
     out.println( "<TH HEIGHT=\"35\" WIDTH=\"5%\">% Lower</TH>\n" );
     out.println( "<TH HEIGHT=\"35\" WIDTH=\"5%\">% Upper</TH>\n" );
     /*out.println( "<TH HEIGHT=\"35\" WIDTH=\"3%\">Hazardous</TH>\n" );
     out.println( "<TH HEIGHT=\"35\" WIDTH=\"3%\">Trade Secret</TH>\n" );*/
     out.println( "<TH HEIGHT=\"35\" WIDTH=\"20%\">Comments</TH>\n" );
     out.println( "</TR>\n" );

     int compositionexists=ofCompositionData.size();

     for ( int z=0; z < compositionexists; z++ )
     {
       compositionData=new Hashtable();
       compositionData= ( Hashtable ) ofCompositionData.elementAt( z );
       String Color=" ";
       if ( z % 2 == 0 )
       {
         Color="CLASS=\"Inwhite";
       }
       else
       {
         Color="CLASS=\"Inblue";
       }

       String linestatus = (String) compositionData.get("LINE_STATUS".toString());
       if ("N".equalsIgnoreCase(linestatus))
       {
         Color="CLASS=\"red";
       }

       out.println("<TR>\n");
       out.println("<TD WIDTH=\"15%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"cas_number"+z+""+TabNumber+"\" value=\"");out.println(compositionData.get("CAS_NUMBER".toString()));out.println("\" SIZE=\"15\"></TD>\n");
       out.println("<TD WIDTH=\"30%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"msds_chemical_name"+z+""+TabNumber+"\" value=\"");out.println(compositionData.get("MSDS_CHEMICAL_NAME".toString()));out.println("\" SIZE=\"45\"></TD>\n");
       out.println("<TD WIDTH=\"5%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"percent"+z+""+TabNumber+"\" value=\"");out.println(compositionData.get("PERCENT".toString()));out.println("\" SIZE=\"4\"></TD>\n");
       out.println("<TD WIDTH=\"5%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"percent_lower"+z+""+TabNumber+"\" value=\"");out.println(compositionData.get("PERCENT_LOWER".toString()));out.println("\" SIZE=\"4\"></TD>\n");
       out.println("<TD WIDTH=\"5%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"percent_upper"+z+""+TabNumber+"\" value=\"");out.println(compositionData.get("PERCENT_UPPER".toString()));out.println("\" SIZE=\"4\"></TD>\n");

       /*String hazard = compositionData.get("HAZARDOUS".toString()).toString();
       out.println("<TD WIDTH=\"3%\" "+Color+"\"><INPUT type=\"checkbox\" name=\"hazardous"+z+""+TabNumber+"\" value=\""+hazard+"\"");
       out.println(" "+("Y".equalsIgnoreCase(hazard)?"checked":" ")+"></TD>\n");

       String tradesecreat = compositionData.get("TRADE_SECRET".toString()).toString();
       out.println("<TD WIDTH=\"3%\" "+Color+"\"><INPUT type=\"checkbox\" name=\"trade_secret"+z+""+TabNumber+"\" value=\""+tradesecreat+"\"");
       out.println(" "+("Y".equalsIgnoreCase(tradesecreat)?"checked":" ")+"></TD>\n");
*/
       out.println("<TD WIDTH=\"20%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"remark"+z+""+TabNumber+"\" value=\"");out.println(compositionData.get("REMARK".toString()));out.println("\" SIZE=\"25\" MAXLENGTH=\"30\"></TD>\n");
       out.println("</TR>\n");
     }

     for ( int rem=compositionexists; rem < 50; rem++ )
     {
       String Color=" ";
       if ( rem % 2 == 0 )
       {
         Color="CLASS=\"Inwhite";
       }
       else
       {
         Color="CLASS=\"Inblue";
       }

       out.println("<TR>\n");
       out.println("<TD WIDTH=\"15%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"cas_number"+rem+""+TabNumber+"\" value=\"\" SIZE=\"15\"></TD>\n");
       out.println("<TD WIDTH=\"30%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"msds_chemical_name"+rem+""+TabNumber+"\" value=\"\" SIZE=\"45\"></TD>\n");
       out.println("<TD WIDTH=\"5%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"percent"+rem+""+TabNumber+"\" value=\"\" SIZE=\"4\"></TD>\n");
       out.println("<TD WIDTH=\"5%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"percent_lower"+rem+""+TabNumber+"\" value=\"\" SIZE=\"4\"></TD>\n");
       out.println("<TD WIDTH=\"5%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"percent_upper"+rem+""+TabNumber+"\" value=\"\" SIZE=\"4\"></TD>\n");
       /*out.println("<TD WIDTH=\"3%\" "+Color+"\"><INPUT type=\"checkbox\" name=\"hazardous"+rem+""+TabNumber+"\" value=\"Y\"></TD>\n");
       out.println("<TD WIDTH=\"3%\" "+Color+"\"><INPUT type=\"checkbox\" name=\"trade_secret"+rem+""+TabNumber+"\" value=\"Y\"></TD>\n");
       */
       out.println("<TD WIDTH=\"20%\" "+Color+"l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"remark"+rem+""+TabNumber+"\" value=\"\" SIZE=\"25\" MAXLENGTH=\"30\"></TD>\n");
       out.println("</TR>\n");

     }
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

 private Vector UpdateDetails( Vector in_data,String logonid,int total )
 {
   editMsds editMsdsObj1=new editMsds( db );
   Vector new_data=new Vector();

   try
   {
     for ( int i=0; i < total; i++ )
     {
       Hashtable hD=new Hashtable();
       hD= ( Hashtable ) in_data.elementAt( i );

       //line_result=editMsdsObj1.updateMsdsData( hD,logonid ); // update database
       Hashtable updmsdsres = editMsdsObj1.updateMsdsData( hD,logonid );
       Boolean line_result= ( Boolean ) updmsdsres.get( "MSDS_UPDATE_FLAG" );
       String msdserrormsg = ( String ) updmsdsres.get( "MSDS_ERROR_MSG" );

       if ( false == line_result.booleanValue() )
       {
         completeSuccess=false;
         hD.remove( "UPDATE_FLAG" );
         hD.put( "UPDATE_FLAG",new Boolean( false ) );
         editmsdserror +=msdserrormsg;
       }
	   else
	   {
       Vector ofCompositionData = null;
       ofCompositionData= ( Vector ) hD.get( "COMPOSITION_DATA" );
       Hashtable materialData= ( Hashtable ) hD.get( "MATERIAL_DATA" );
       Hashtable msdsData= ( Hashtable ) hD.get( "MSDS_DATA" );

       String material_id=materialData.get( "MATERIAL_ID" ).toString();
       String revision_date=msdsData.get( "REVISION_DATE" ).toString();

       Hashtable compres = editMsdsObj1.updatecompositionData( ofCompositionData,material_id,revision_date );
       Boolean comresult= ( Boolean ) compres.get( "COMP_UPDATE_FLAG" );
       Vector ofCompositionData1 = ( Vector ) compres.get( "COMP_DATA_VECTOR" );
       String comperrormsg = ( String ) compres.get( "COMP_ERROR_MSG" );

       if (false == comresult.booleanValue())
       {
         completeSuccess=false;
         hD.remove( "UPDATE_FLAG" );
         hD.put( "UPDATE_FLAG",new Boolean( false ) );
         editmsdserror +=comperrormsg;
       }

       hD.remove( "COMPOSITION_DATA" );
       hD.put( "COMPOSITION_DATA",ofCompositionData1 );
	   }

       new_data.addElement( hD );
     } //end of for
   }
   catch ( Exception e )
   {
     completeSuccess=false;
     e.printStackTrace();
   }

   return new_data;
 }

 private Vector SynchServerData( HttpServletRequest request,Vector in_data )
 {
   Vector new_data=new Vector();

   Hashtable materialData=null;
   Hashtable msdsData=null;
   Hashtable compositionData=null;
   Vector cofCompositionData=null;
   Vector ofCompositionData=null;

   try
   {
     int i=0; //used for detail lines
     for ( int loop=0; loop < in_data.size(); loop++ )
     {
       i++;
       Hashtable hD=new Hashtable();
       hD = ( Hashtable)( in_data.elementAt(loop));

	    materialData = (Hashtable) hD.get("MATERIAL_DATA");
	    msdsData = (Hashtable) hD.get("MSDS_DATA");

	    cofCompositionData = new Vector();
	    cofCompositionData = (Vector) hD.get("COMPOSITION_DATA");
	    ofCompositionData = new Vector();

        String partid = "";
        try{ partid = request.getParameter(("partid"+loop)).toString();}catch (Exception e){ partid = "";}
        hD.remove("PART_ID");
        hD.put("PART_ID", partid.trim() );

        String mfg_id = "";
        try{ mfg_id = request.getParameter(("mfgid"+loop)).toString();}catch (Exception e){ mfg_id = "";}
        materialData.remove("MFG_ID");
        materialData.put("MFG_ID", mfg_id.trim() );

        String mfgurl = "";
        try{ mfgurl = request.getParameter(("mfgurl"+loop)).toString();}catch (Exception e){ mfgurl = "";}
        materialData.remove("MFG_URL");
        materialData.put("MFG_URL", mfgurl.trim() );

        String mfgphone = "";
        try{ mfgphone = request.getParameter(("mfgphone"+loop)).toString();}catch (Exception e){ mfgphone = "";}
        materialData.remove("PHONE");
        materialData.put("PHONE", mfgphone.trim() );

        String mfgcontact = "";
        try{ mfgcontact = request.getParameter(("mfgcontact"+loop)).toString();}catch (Exception e){ mfgcontact = "";}
        materialData.remove("CONTACT");
        materialData.put("CONTACT", mfgcontact.trim() );

        String manufacturer= "";
        try{ manufacturer = request.getParameter(("manufacturer"+loop)).toString();}catch (Exception e){ manufacturer = "";}
        materialData.remove("MANUFACTURER");
        materialData.put("MANUFACTURER", manufacturer.trim() );

        String material_id= "";
        try{ material_id = request.getParameter(("materialid"+loop)).toString();}catch (Exception e){ material_id = "";}
        materialData.remove("MATERIAL_ID");
        materialData.put("MATERIAL_ID", material_id );

        String material_desc= "";
        try{ material_desc = request.getParameter(("materialdesc"+loop)).toString();}catch (Exception e){ material_desc = "";}
        materialData.remove("MATERIAL_DESC");
        materialData.put("MATERIAL_DESC", material_desc );

        String tradename= "";
        try{ tradename = request.getParameter(("tradename"+loop)).toString();}catch (Exception e){tradename = "";}
        materialData.remove("MFG_TRADE_NAME");
        materialData.put("MFG_TRADE_NAME", tradename );

    /*String landdot= "";
        try{ landdot = request.getParameter(("landdot"+loop)).toString();}catch (Exception e){ landdot = "";}
        materialData.remove("LANDDOT");
        materialData.put("LANDDOT", landdot.trim() );

    String airdot= "";
        try{ airdot = request.getParameter(("airdot"+loop)).toString();}catch (Exception e){ airdot = "";}
        materialData.remove("AIRDOT");
        materialData.put("AIRDOT", airdot.trim() );

    String waterdot= "";
        try{ waterdot = request.getParameter(("waterdot"+loop)).toString();}catch (Exception e){ waterdot = "";}
        materialData.remove("WATERDOT");
        materialData.put("WATERDOT", waterdot.trim() );

    String erg = "";
        try{ erg = request.getParameter(("erg"+loop)).toString();}catch (Exception e){ erg = "";}
        materialData.remove("ERG");
        materialData.put("ERG", erg.trim() );
*/
    hD.remove("MATERIAL_DATA");
    hD.put("MATERIAL_DATA",materialData);

        String revdate= "";
        try{ revdate = request.getParameter(("revdate"+loop)).toString();}catch (Exception e){ revdate = "";}
        msdsData.remove("REVISION_DATE");
        msdsData.put("REVISION_DATE", revdate.trim() );

        String content= "";
        try{ content = request.getParameter(("content"+loop)).toString();}catch (Exception e){ content = "";}
        msdsData.remove("CONTENT");
        msdsData.put("CONTENT", content.trim() );

        String physicalstate= "";
        try{ physicalstate = request.getParameter(("physicalstate"+loop)).toString();}catch (Exception e){ physicalstate = "";}
        msdsData.remove("PHYSICAL_STATE");
        msdsData.put("PHYSICAL_STATE", physicalstate.trim() );

        String nfpah= "";
        try{ nfpah = request.getParameter(("nfpah"+loop)).toString();}catch (Exception e){ nfpah = "";}
        msdsData.remove("HEALTH");
        msdsData.put("HEALTH", nfpah.trim() );

        String nfpar= "";
        try{ nfpar = request.getParameter(("nfpar"+loop)).toString();}catch (Exception e){ nfpar = "";}
        msdsData.remove("REACTIVITY");
        msdsData.put("REACTIVITY", nfpar.trim() );

        String nfpaf= "";
        try{ nfpaf = request.getParameter(("nfpaf"+loop)).toString();}catch (Exception e){ nfpaf = "";}
        msdsData.remove("FLAMMABILITY");
        msdsData.put("FLAMMABILITY", nfpaf.trim() );

        String nfpas= "";
        try{ nfpas = request.getParameter(("nfpas"+loop)).toString();}catch (Exception e){ nfpas = "";}
        msdsData.remove("SPECIFIC_HAZARD");
        msdsData.put("SPECIFIC_HAZARD", nfpas.trim() );

        String hmish= "";
        try{ hmish = request.getParameter(("hmish"+loop)).toString();}catch (Exception e){ hmish = "";}
        msdsData.remove("HMIS_HEALTH");
        msdsData.put("HMIS_HEALTH", hmish.trim() );

        String hmisr= "";
        try{ hmisr = request.getParameter(("hmisr"+loop)).toString();}catch (Exception e){ hmisr = "";}
        msdsData.remove("HMIS_REACTIVITY");
        msdsData.put("HMIS_REACTIVITY", hmisr.trim() );

        String hmisf= "";
        try{ hmisf = request.getParameter(("hmisf"+loop)).toString();}catch (Exception e){ hmisf = "";}
        msdsData.remove("HMIS_FLAMMABILITY");
        msdsData.put("HMIS_FLAMMABILITY", hmisf.trim() );

        String hmiss= "";
        try{ hmiss = request.getParameter(("hmiss"+loop)).toString();}catch (Exception e){ hmiss = "";}
        msdsData.remove("PERSONAL_PROTECTION");
        msdsData.put("PERSONAL_PROTECTION", hmiss.trim() );

        String spegravity= "";
        try{ spegravity = request.getParameter(("spegravity"+loop)).toString();}catch (Exception e){ spegravity = "";}
        msdsData.remove("SPECIFIC_GRAVITY");
        msdsData.put("SPECIFIC_GRAVITY", spegravity.trim() );

        String density= "";
        try{ density = request.getParameter(("density"+loop)).toString();}catch (Exception e){ density = "";}
        msdsData.remove("DENSITY");
        msdsData.put("DENSITY", density.trim() );

        String denunits= "";
        try{ denunits = request.getParameter(("denunits"+loop)).toString();}catch (Exception e){ denunits = "";}
        msdsData.remove("DENSITY_UNIT");
        msdsData.put("DENSITY_UNIT", denunits.trim() );

	    String voc= "";
	    try{ voc = request.getParameter(("voc"+loop)).toString();}catch (Exception e){ voc = "";}
	    msdsData.remove("VOC");
	    msdsData.put("VOC", voc.trim() );

	    String voclower= "";
	    try{ voclower = request.getParameter(("voclower"+loop)).toString();}catch (Exception e){ voclower = "";}
	    msdsData.remove("VOC_LOWER");
	    msdsData.put("VOC_LOWER", voclower.trim() );

	    String vocupper= "";
	    try{ vocupper = request.getParameter(("vocupper"+loop)).toString();}catch (Exception e){ vocupper = "";}
	    msdsData.remove("VOC_UPPER");
	    msdsData.put("VOC_UPPER", vocupper.trim() );

	    String vocunits= "";
	    try{ vocunits = request.getParameter(("vocunits"+loop)).toString();}catch (Exception e){ vocunits = "";}
	    msdsData.remove("VOC_UNIT");
	    msdsData.put("VOC_UNIT", vocunits.trim() );

	    String percsolids= "";
	    try{ percsolids = request.getParameter(("percsolids"+loop)).toString();}catch (Exception e){ percsolids = "";}
	    msdsData.remove("SOLIDS");
	    msdsData.put("SOLIDS", percsolids.trim() );

	    String percsolidslower= "";
	    try{ percsolidslower = request.getParameter(("percsolidslower"+loop)).toString();}catch (Exception e){ percsolidslower = "";}
	    msdsData.remove("SOLIDS_LOWER");
	    msdsData.put("SOLIDS_LOWER", percsolidslower.trim() );

	    String percsolidsupper= "";
	    try{ percsolidsupper = request.getParameter(("percsolidsupper"+loop)).toString();}catch (Exception e){ percsolidsupper = "";}
	    msdsData.remove("SOLIDS_UPPER");
	    msdsData.put("SOLIDS_UPPER", percsolidsupper.trim() );

	    String percsolidsunit= "";
	    try{ percsolidsunit = request.getParameter(("percsolidsunit"+loop)).toString();}catch (Exception e){ percsolidsunit = "";}
	    msdsData.remove("SOLIDS_UNIT");
	    msdsData.put("SOLIDS_UNIT", percsolidsunit.trim() );

	    String flashpoint= "";
	    try{ flashpoint = request.getParameter(("flashpoint"+loop)).toString();}catch (Exception e){ flashpoint = "";}
	    msdsData.remove("FLASH_POINT");
	    msdsData.put("FLASH_POINT", flashpoint.trim() );

	    String flashpointunits= "";
	    try{ flashpointunits = request.getParameter(("flashpointunits"+loop)).toString();}catch (Exception e){ flashpointunits = "";}
	    msdsData.remove("FLASH_POINT_UNIT");
	    msdsData.put("FLASH_POINT_UNIT", flashpointunits.trim() );

	    String msdsremark = "";
	    try{ msdsremark = request.getParameter(("msdsremark"+loop)).toString();}catch (Exception e){ msdsremark = "";}
	    msdsData.remove("REMARK");
	    msdsData.put("REMARK", msdsremark.trim() );

        String altdatasource = "";
	    try{ altdatasource = request.getParameter(("altdatasource"+loop)).toString();}catch (Exception e){ altdatasource = "";}
	    msdsData.remove("ALT_DATA_SOURCE");
	    msdsData.put("ALT_DATA_SOURCE", altdatasource.trim() );

		String vappressdet = "";
		try{ vappressdet = request.getParameter(("vappressdet"+loop)).toString();}catch (Exception e){ vappressdet = "";}
		msdsData.remove("VAPOR_PRESSURE_DETECT");
		msdsData.put("VAPOR_PRESSURE_DETECT", vappressdet.trim() );

		String vappress = "";
		try{ vappress = request.getParameter(("vappress"+loop)).toString();}catch (Exception e){ vappress = "";}
		msdsData.remove("VAPOR_PRESSURE");
		msdsData.put("VAPOR_PRESSURE", vappress.trim() );

		String vappressunit = "";
		try{ vappressunit = request.getParameter(("vappressunit"+loop)).toString();}catch (Exception e){ vappressunit = "";}
		msdsData.remove("VAPOR_PRESSURE_UNIT");
		msdsData.put("VAPOR_PRESSURE_UNIT", vappressunit.trim() );

		String vappresstemp = "";
		try{ vappresstemp = request.getParameter(("vappresstemp"+loop)).toString();}catch (Exception e){ vappresstemp = "";}
		msdsData.remove("VAPOR_PRESSURE_TEMP");
		msdsData.put("VAPOR_PRESSURE_TEMP", vappresstemp.trim() );

		String vappresstempunt = "";
		try{ vappresstempunt = request.getParameter(("vappresstempunt"+loop)).toString();}catch (Exception e){ vappresstempunt = "";}
		msdsData.remove("VAPOR_PRESURE_TEMP_UNIT");
		msdsData.put("VAPOR_PRESURE_TEMP_UNIT", vappresstempunt.trim() );

		String vapor_pressure_source = "";
		try{ vapor_pressure_source = request.getParameter(("vapor_pressure_source"+loop)).toString();}catch (Exception e){ vapor_pressure_source = "";}
		msdsData.remove("VAPOR_PRESSURE_SOURCE");
		msdsData.put("VAPOR_PRESSURE_SOURCE", vapor_pressure_source.trim() );

		String vapor_pressure_upper = "";
		try{ vapor_pressure_upper = request.getParameter(("vapor_pressure_upper"+loop)).toString();}catch (Exception e){ vapor_pressure_upper = "";}
		msdsData.remove("VAPOR_PRESSURE_UPPER");
		msdsData.put("VAPOR_PRESSURE_UPPER", vapor_pressure_upper.trim() );

		String vapor_pressure_lower = "";
		try{ vapor_pressure_lower = request.getParameter(("vapor_pressure_lower"+loop)).toString();}catch (Exception e){ vapor_pressure_lower = "";}
		msdsData.remove("VAPOR_PRESSURE_LOWER");
		msdsData.put("VAPOR_PRESSURE_LOWER", vapor_pressure_lower.trim() );

		String voc_source= request.getParameter("voc_source"+loop);
		if ( voc_source == null )
		voc_source="";
		msdsData.remove("VOC_SOURCE");
		msdsData.put("VOC_SOURCE", voc_source.trim() );

		String voc_less_h2o_exempt= request.getParameter("voc_less_h2o_exempt"+loop);
		if ( voc_less_h2o_exempt == null )
		voc_less_h2o_exempt="";
		msdsData.remove("VOC_LESS_H2O_EXEMPT");
		msdsData.put("VOC_LESS_H2O_EXEMPT", voc_less_h2o_exempt.trim() );

		String voc_less_h2o_exempt_unit= request.getParameter("voc_less_h2o_exempt_unit"+loop);
		if ( voc_less_h2o_exempt_unit == null )
		voc_less_h2o_exempt_unit="";
		msdsData.remove("VOC_LESS_H2O_EXEMPT_UNIT");
		msdsData.put("VOC_LESS_H2O_EXEMPT_UNIT", voc_less_h2o_exempt_unit.trim() );

		String voc_less_h2o_exempt_lower= request.getParameter("voc_less_h2o_exempt_lower"+loop);
		if ( voc_less_h2o_exempt_lower == null )
		voc_less_h2o_exempt_lower="";
		msdsData.remove("VOC_LESS_H2O_EXEMPT_LOWER");
		msdsData.put("VOC_LESS_H2O_EXEMPT_LOWER", voc_less_h2o_exempt_lower.trim() );

		String voc_less_h2o_exempt_upper= request.getParameter("voc_less_h2o_exempt_upper"+loop);
		if ( voc_less_h2o_exempt_upper == null )
		voc_less_h2o_exempt_upper="";
		msdsData.remove("VOC_LESS_H2O_EXEMPT_UPPER");
		msdsData.put("VOC_LESS_H2O_EXEMPT_UPPER", voc_less_h2o_exempt_upper.trim() );

		String voc_less_h2o_exempt_source= request.getParameter("voc_less_h2o_exempt_source"+loop);
		if ( voc_less_h2o_exempt_source == null )
		voc_less_h2o_exempt_source="";
		msdsData.remove("VOC_LESS_H2O_EXEMPT_SOURCE");
		msdsData.put("VOC_LESS_H2O_EXEMPT_SOURCE", voc_less_h2o_exempt_source.trim() );

		String voc_lb_per_solid_lb= request.getParameter("voc_lb_per_solid_lb"+loop);
		if ( voc_lb_per_solid_lb == null )
		voc_lb_per_solid_lb="";
		msdsData.remove("VOC_LB_PER_SOLID_LB");
		msdsData.put("VOC_LB_PER_SOLID_LB", voc_lb_per_solid_lb.trim() );

		String voc_lb_per_solid_lb_source= request.getParameter("voc_lb_per_solid_lb_source"+loop);
		if ( voc_lb_per_solid_lb_source == null )
		voc_lb_per_solid_lb_source="";
		msdsData.remove("VOC_LB_PER_SOLID_LB_SOURCE");
		msdsData.put("VOC_LB_PER_SOLID_LB_SOURCE", voc_lb_per_solid_lb_source.trim() );

		String solids_source= request.getParameter("solids_source"+loop);
		if ( solids_source == null )
		solids_source="";
		msdsData.remove("SOLIDS_SOURCE");
		msdsData.put("SOLIDS_SOURCE", solids_source.trim() );

		String voc_lb_per_solid_lb_lower= request.getParameter("voc_lb_per_solid_lb_lower"+loop);
		if ( voc_lb_per_solid_lb_lower == null )
		voc_lb_per_solid_lb_lower="";
		msdsData.remove("VOC_LB_PER_SOLID_LB_LOWER");
		msdsData.put("VOC_LB_PER_SOLID_LB_LOWER", voc_lb_per_solid_lb_lower.trim() );

		String voc_lb_per_solid_lb_upper= request.getParameter("voc_lb_per_solid_lb_upper"+loop);
		if ( voc_lb_per_solid_lb_upper == null )
		voc_lb_per_solid_lb_upper="";
		msdsData.remove("VOC_LB_PER_SOLID_LB_UPPER");
		msdsData.put("VOC_LB_PER_SOLID_LB_UPPER", voc_lb_per_solid_lb_upper.trim() );

	    hD.remove("MSDS_DATA");
	    hD.put("MSDS_DATA",msdsData);

        int compositionexists=ofCompositionData.size();
        for (int rem=0; rem < compositionexists; rem++)
       {
       compositionData = new Hashtable();
       compositionData = (Hashtable) cofCompositionData.elementAt(rem);

       String percent = "";
       try{ percent = request.getParameter(("percent"+rem+""+loop)).toString();}catch (Exception e){ percent = "";}
       compositionData.remove("PERCENT");
       compositionData.put("PERCENT", percent.trim() );

       String percent_lower = "";
       try{ percent_lower = request.getParameter(("percent_lower"+rem+""+loop)).toString();}catch (Exception e){ percent_lower = "";}
       compositionData.remove("PERCENT_LOWER");
       compositionData.put("PERCENT_LOWER", percent_lower.trim() );

       String percent_upper = "";
       try{ percent_upper = request.getParameter(("percent_upper"+rem+""+loop)).toString();}catch (Exception e){ percent_upper = "";}
       compositionData.remove("PERCENT_UPPER");
       compositionData.put("PERCENT_UPPER", percent_upper.trim() );

/*       String hazardous = "";
       try{ hazardous = request.getParameter(("hazardous"+rem+""+loop)).toString();}catch (Exception e){ hazardous = "";}
       compositionData.remove("HAZARDOUS");
       compositionData.put("HAZARDOUS", hazardous.trim() );*/

       String cas_number = "";
       try{ cas_number = request.getParameter(("cas_number"+rem+""+loop)).toString();}catch (Exception e){ cas_number = "";}
       compositionData.remove("CAS_NUMBER");
       compositionData.put("CAS_NUMBER", cas_number.trim() );

/*       String trade_secret = "";
       try{ trade_secret = request.getParameter(("trade_secret"+rem+""+loop)).toString();}catch (Exception e){ trade_secret = "";}
       compositionData.remove("TRADE_SECRET");
       compositionData.put("TRADE_SECRET", trade_secret.trim() );*/

       String remark = "";
       try{ remark = request.getParameter(("remark"+rem+""+loop)).toString();}catch (Exception e){ remark = "";}
       compositionData.remove("REMARK");
       compositionData.put("REMARK", remark.trim() );

       String msds_chemical_name = "";
       try{ msds_chemical_name = request.getParameter(("msds_chemical_name"+rem+""+loop)).toString();}catch (Exception e){ msds_chemical_name = "";}
       compositionData.remove("MSDS_CHEMICAL_NAME");
       compositionData.put("MSDS_CHEMICAL_NAME", msds_chemical_name.trim() );

       ofCompositionData.addElement(compositionData);
       }

      for (int rem=compositionexists; rem < 50; rem++)
      {
      compositionData = new Hashtable();

      String percent = "";
      try{ percent = request.getParameter(("percent"+rem+""+loop)).toString();}catch (Exception e){ percent = "";}
      compositionData.put("PERCENT", percent.trim() );

      String percent_lower = "";
      try{ percent_lower = request.getParameter(("percent_lower"+rem+""+loop)).toString();}catch (Exception e){ percent_lower = "";}
      compositionData.put("PERCENT_LOWER", percent_lower.trim() );

      String percent_upper = "";
      try{ percent_upper = request.getParameter(("percent_upper"+rem+""+loop)).toString();}catch (Exception e){ percent_upper = "";}
      compositionData.put("PERCENT_UPPER", percent_upper.trim() );

/*      String hazardous = "";
      try{ hazardous = request.getParameter(("hazardous"+rem+""+loop)).toString();}catch (Exception e){ hazardous = "";}
      compositionData.put("HAZARDOUS", hazardous.trim() );*/

      String cas_number = "";
      try{ cas_number = request.getParameter(("cas_number"+rem+""+loop)).toString();}catch (Exception e){ cas_number = "";}
      compositionData.put("CAS_NUMBER", cas_number.trim() );

/*      String trade_secret = "";
      try{ trade_secret = request.getParameter(("trade_secret"+rem+""+loop)).toString();}catch (Exception e){ trade_secret = "";}
      compositionData.put("TRADE_SECRET", trade_secret.trim() );*/

      String remark = "";
      try{ remark = request.getParameter(("remark"+rem+""+loop)).toString();}catch (Exception e){ remark = "";}
      compositionData.put("REMARK", remark.trim() );

      String msds_chemical_name = "";
      try{ msds_chemical_name = request.getParameter(("msds_chemical_name"+rem+""+loop)).toString();}catch (Exception e){ msds_chemical_name = "";}
      compositionData.put("MSDS_CHEMICAL_NAME", msds_chemical_name.trim() );

      ofCompositionData.addElement(compositionData);
      }

	    hD.remove("COMPOSITION_DATA");
	    hD.put("COMPOSITION_DATA",ofCompositionData);

        new_data.addElement(hD);
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();

    }
    return new_data;
  }
}

