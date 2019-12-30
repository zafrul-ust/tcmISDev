package radian.web.servlets.share.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.actuate.ereport.common.IViewerInterface;
import com.actuate.ereport.datasrcmgrs.jdbcdatasrc.JDBCHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 07-02-03 - Code Clean up
 * 06-25-04 Adding log statements to trace a memory usage issue
 */

public class processDetail
{
  private TcmISDBModel db=null;
  private ServerResourceBundle bundle=null;
  private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
  public processDetail( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
      PrintWriter out=response.getWriter();
      response.setContentType( "text/html" );
      try
      {
        String session="";
        session=request.getParameter( "session" );
        if ( session == null )
          session="";

        String process="";
        process=request.getParameter( "process" );
        if ( process == null )
          process="";

        String facID="";
        facID=request.getParameter( "facID" );
        if ( facID == null )
          facID="";

        String selworkarea="";
        selworkarea=request.getParameter( "selworkarea" );
        if ( selworkarea == null )
          selworkarea="";

        String selworkareaDesc="";
        selworkareaDesc=request.getParameter( "selworkareadesc" );
        if ( selworkareaDesc == null )
          selworkareaDesc="";

        String catalogID="";
        catalogID=request.getParameter( "catalogID" );
        if ( catalogID == null )
          catalogID="";

        String partNo="";
        partNo=request.getParameter( "partNo" );
        if ( partNo == null )
          partNo="";

        String partGroupNo="";
        partGroupNo=request.getParameter( "partGroupNo" );
        if ( partGroupNo == null )
          partGroupNo="";


        if ( session.equalsIgnoreCase( "1" ) )
        {
          buildURL( out,process,facID,selworkarea,selworkareaDesc,catalogID,partNo,partGroupNo );
        }
        else
        {
          buildHTML( out,process,facID,selworkarea,selworkareaDesc,catalogID,partNo,partGroupNo );
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

    public void buildURL( PrintWriter out,String process,String facID,String selworkarea, String selworkareadesc,String catalogID,String partNo,String partGroupNo )
    {
      ACJEngine en=new ACJEngine();
      en.setDebugMode( true );
      en.setX11GfxAvailibility( false );
      en.setTargetOutputDevice( ACJConstants.PDF );
      TemplateManager tm=null;
      JDBCHandler ds=null;
	  Random rand=new Random();
	  int tmpReq=rand.nextInt();
	  Integer tmpReqNum=new Integer( tmpReq );

      ACJOutputProcessor eClient=new ACJOutputProcessor();
      String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
      String templatpath=radian.web.tcmisResourceLoader.getreportstempplatepath();
      String writefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();
      String hosturl=radian.web.tcmisResourceLoader.gethosturl();
	  //en.setCacheOption(true, ""+writefilepath+"procdeta"+tmpReqNum.toString()+".joi");
      eClient.setPathForFontMapFile( fontmappath );
      ds = new JDBCHandler();

      String updateprintdate="";
      String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
      String url=wwwHome + "reports/temp/procdeta" + tmpReqNum.toString() + ".pdf";

      try
      {
        en.readTemplate( "" + templatpath + "processdetail.jod" );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }

      String whereclause = "";
      if ( "All".equalsIgnoreCase( process ) )
      {
         whereclause = "FACILITY_ID='"+facID+"' and APPLICATION='"+selworkarea+"' ";
      }
      else
      {
        whereclause = "FACILITY_ID='"+facID+"' and APPLICATION='"+selworkarea+"' and PROCESS_ID='"+process+"'";
      }

      tm=en.getTemplateManager();
      tm.setWHEREClause( "SEC_00",whereclause );
      tm.setLabel( "WORKAREANAME",selworkareadesc);
      tm.setLabel( "FACILITYNAME",facID);
      tm.setLabel( "PARTNUM",partNo );
      tm.setLabel( "MAIN_PARTNUMBER",partNo );
      tm.setLabel( "MAIN_PART_GRP_NUMBER",partGroupNo );
      tm.setLabel( "MAIN_CATALOG_ID",catalogID );

      // Connect to database.
      try
      {
        reoprtlog.info("buildURL    Start "+tmpReqNum.toString()+" ");
		//String client=bundle.getString( "DB_CLIENT" );
        String dbDriver="oracle.jdbc.driver.OracleDriver";
        String phoenixUrl=radian.web.databaseResourceLoader.getproddburl();
        //String DRSPhoenixUserName="drs";
        //String DRSPhoenixPasswd="irt3ch";
        String LMCOPhoenixUserName=radian.web.databaseResourceLoader.getlmcouserid();
        String LMCOPhoenixPasswd=radian.web.databaseResourceLoader.getlmcopassword();
        //String rayPhoenixUserName="raytheon";
        //String rayPhoenixPasswd="r8the0n";
        //String SeagatePhoenixUserName="seagate";
        //String SeagatePhoenixPasswd="drives";
        //String SWAPhoenixUserName="swa";
        //String SWAPhoenixPasswd="a1rl1n3";

        ds.connect( dbDriver,phoenixUrl,LMCOPhoenixUserName,LMCOPhoenixPasswd,true );
        en.setDataSource( ds );

        IViewerInterface ivi=en.generateReport();
        if ( eClient.setReportData( ivi,null ) )
        {
          eClient.setPDFProperty( "FileName","" + writefilepath + "procdeta" + tmpReqNum.toString() + ".pdf" );
          eClient.generatePDF();
        }

        ds.Disconnect();
      }
      catch ( Exception e2222 )
      {
        e2222.printStackTrace();
        url = "";
      }
	  reoprtlog.info("buildURL    Done  "+tmpReqNum.toString()+"");
      StringBuffer MsgSB = new StringBuffer();
      if (url.length() > 0)
      {
        MsgSB.append( "<HTML><HEAD>\n" );
        MsgSB.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n" );
        MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
        MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
        MsgSB.append( "<TITLE>Process Detail Printer</TITLE>\n" );
        MsgSB.append( "</HEAD>  \n" );
        MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
        MsgSB.append( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
        MsgSB.append( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n" );
        MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Report</b></font><P></P><BR>\n" );
        MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n" );
        MsgSB.append( "</CENTER>\n" );
        MsgSB.append( "</BODY></HTML>    \n" );
      }
      else
      {
        MsgSB.append(radian.web.HTMLHelpObj.printMessageinTable("An error occured. <BR>Please try again or call your tcmIS Coustomer Support Representative."));
      }
      out.println(MsgSB);
    }

    public void buildHTML(PrintWriter out,String process,String facID,String selworkarea,String selworkareadesc,String catalogID,String partNo,String partGroupNo)
    {
        StringBuffer MsgSB = new StringBuffer();
        MsgSB.append("<HTML><HEAD><TITLE>Report Generator</TITLE>\n");
        MsgSB.append("<SCRIPT TYPE=\"text/javascript\" LANGUAGE=\"JavaScript\">\n");
        MsgSB.append("<!-- \n");
        MsgSB.append("    function winClose(){    // close all open pop-up windows\n");
        MsgSB.append("      if ((winHandle != null) && !winHandle.closed) winHandle.close()\n");
        MsgSB.append("      }\n");

        MsgSB.append( "function openwin2 (str)\n");
        MsgSB.append(    "        {\n");
        MsgSB.append(    "    winHandle = window.open(\"/reports/Timer.html\", \"Timer1\",\n");
        MsgSB.append(    "        \"toolbar=no,location=no,directories=no,status=no\" +\n");
        MsgSB.append(    "        \",menubar=no,scrollbars=no,resizable=no,\" +\n");
        MsgSB.append(    "        \",width=200,height=150\");\n");
        MsgSB.append(    "  return winHandle; \n");
        MsgSB.append(    "        }\n");

        MsgSB.append("function DoLoad()\n");
        MsgSB.append("{ \n");
        MsgSB.append("    var loc = \"\"; \n");
        MsgSB.append("openwin2(); \n");

        String redirecturl =bundle.getString( "PROCESS_DETAIL_URL" );

        MsgSB.append("loc=loc + \""+redirecturl+"session=1&process="+process+"&facID="+facID+"&selworkarea="+selworkarea+"&selworkareadesc="+URLEncoder.encode(selworkareadesc)+"&catalogID="+catalogID+"&partNo="+partNo+"&partGroupNo="+partGroupNo+"\";\n");
        MsgSB.append("window.location.replace(loc);\n");
        //MsgSB.append("alert(loc);\n");
        MsgSB.append("} \n");
        MsgSB.append("//-->     \n");
        MsgSB.append("</SCRIPT>\n");
        MsgSB.append("</HEAD>  \n");
        MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\" onload=\"DoLoad()\" onUnload=\"winClose()\">  \n");
        MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
        MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Report Generator</b></font><P></P><BR>\n");
        MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Report</b></font><P></P><BR>\n");
        MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n");
        MsgSB.append("</CENTER></BODY></HTML>    \n");
        out.println(MsgSB);
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        doPost(request,response);
    }
}
