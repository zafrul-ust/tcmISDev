package radian.web.servlets.ray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Hashtable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
import radian.web.barcode.BarCodePage;
import radian.web.servlets.share.dropShipReceiving;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class RayNewDropShipReceiving extends HttpServlet implements SingleThreadModel
{
  public void init( ServletConfig config ) throws ServletException
  {
    super.init( config );
  }

  /**
   * HTTP POST handler.
   *
   * @param request               An object implementing the request context
   * @param response              An object implementing the response context
   * @exception ServletException  A wrapper for all exceptions thrown by the
   *      servlet.
   */

  public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
    PrintWriter out=response.getWriter();
    RayTcmISDBModel db=null;
    dropShipReceiving obj=null;
    BarCodePage obj1=null;
    HttpSession session=request.getSession();
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		boolean intcmIsApplication = false;
		if (personnelBean !=null)
		{
			 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
			 if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
				intcmIsApplication = true;
			 }
		}


    try
    {
      db=new RayTcmISDBModel( "Ray" );
      if ( db == null )
      {
        PrintWriter out2=new PrintWriter( response.getOutputStream() );
        out2.println( "Starting DB" );
        out2.println( "DB is null" );
        out2.close();
        return;
      }

      ServerResourceBundle bundleray=new RayServerResourceBundle();
      HeaderFooter hf=new HeaderFooter( bundleray,db );

      Hashtable loginresult = new Hashtable();
      loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
      String auth = (String)loginresult.get("AUTH");
      String errormsg = (String)loginresult.get("ERROSMSG");

      if (auth == null) {auth = "challenge";}
      if (errormsg == null) {errormsg = "";}

      if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
      {
        session.setAttribute( "clientlogin","challenge" );
        out.println( hf.printclientLoginScreen( errormsg,"Dropship Receiving","tcmisclientreceiving.gif",intcmIsApplication ) );
        out.flush();
      }
      else
      {
        String donetestocunt=BothHelpObjs.makeBlankFromNull( ( String ) session.getAttribute( "DONETESTCOUNT" ) );
        if ( donetestocunt == null )
        {
          donetestocunt="No";
        }

        obj=new dropShipReceiving( bundleray,db );

        if ( !"yes".equalsIgnoreCase( donetestocunt ) )
        {
          String personnelid= session.getAttribute( "CLIENTPERSONNELID" ) == null ? "" : session.getAttribute( "CLIENTPERSONNELID" ).toString();

          int testCount=DbHelpers.countQuery( db,"select count(*) from user_group_member_location where personnel_id = " + personnelid + "" );
          if ( testCount > 0 )
          {
            obj.setupdateStatus( true );
            session.setAttribute( "DONETESTCOUNT","yes" );
            session.setAttribute( "UPDATERECEIVE","yes" );
          }
          else
          {
            obj.setupdateStatus( false );
            session.setAttribute( "DONETESTCOUNT","yes" );
            session.setAttribute( "UPDATERECEIVE","No" );
          }
        }

        String updatestatus= ( String ) session.getAttribute( "UPDATERECEIVE" );
        if ( "yes".equalsIgnoreCase( updatestatus ) )
        {
          obj.setupdateStatus( true );
        }
        else
        {
          obj.setupdateStatus( false );
        }

        String PaperSize="";
        String HubNo="";
        HubNo=request.getParameter( "HubNo" );
        if ( HubNo == null )
        {
          HubNo="ERROR";
        }

        String User_Action=request.getParameter( "UserAction" );
        if ( User_Action == null )
        {
          User_Action="New";
        }
        /*
        String generate_labels=request.getParameter( "generate_labels" );
        if ( generate_labels == null )
        {
          generate_labels="No";
        }

        PaperSize=request.getParameter( "paperSize" );
        if ( PaperSize == null )
        {
          PaperSize="31";
        }
        String contBin=request.getParameter( "contBin" );
        if ( contBin == null )
        {
          contBin="";
        }

        //System.out.println( "Checkbok   " + generate_labels + " Size  " + PaperSize + "  User Action  :" + User_Action );
        if ( "GenerateLabels".equalsIgnoreCase( User_Action ) )
        {
          Vector sequencenumbers=new Vector();
          sequencenumbers= ( Vector ) session.getAttribute( "LABEL_SEQUENCE_NUMBERS" );
          obj1=new BarCodePage( bundleray,db,PaperSize );
          obj1.setconBin( contBin );

          StringBuffer MsgSB=new StringBuffer();
          String labelsurl="";

          labelsurl=obj1.generateContainerLabel( sequencenumbers );

          if ( labelsurl.length() > 1 )
          {
            MsgSB.append( "<HTML><HEAD>\n" );
            MsgSB.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + labelsurl + "\">\n" );
            MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
            MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
            MsgSB.append( "<TITLE>Container Label Generator</TITLE>\n" );
            MsgSB.append( "</HEAD>  \n" );
            MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
            MsgSB.append( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
            MsgSB.append( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n" );
            MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Labels</b></font><P></P><BR>\n" );
            MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n" );
            MsgSB.append( "</CENTER>\n" );
            MsgSB.append( "</BODY></HTML>    \n" );
          }
          else
          {
            MsgSB.append( "<HTML><HEAD>\n" );
            MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
            MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
            MsgSB.append( "<TITLE>Container Label Generator</TITLE>\n" );
            MsgSB.append( "</HEAD>  \n" );
            MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
            MsgSB.append( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
            MsgSB.append( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n" );
            MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>An Error Occured Producing Labels</b></font><P></P><BR>\n" );
            MsgSB.append( "</CENTER>\n" );
            MsgSB.append( "</BODY></HTML>    \n" );
          }
          out.println( MsgSB );
          out.close();
        }
        else
        {
          obj.doPost( request,response );
        }
        */
       obj.doPost( request,response );
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      return;
    }
    finally
    {
      db.close();
      obj=null;
      return;
    }
  }

  public void doGet( HttpServletRequest request,HttpServletResponse response )
     throws ServletException,IOException
  {
    doPost( request,response );
  }
}
