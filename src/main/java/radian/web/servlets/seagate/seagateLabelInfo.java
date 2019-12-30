package radian.web.servlets.seagate;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.client.seagate.dbObjs.SeagateTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.Password;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.labelInfo;

public class seagateLabelInfo  extends HttpServlet
{
  public void init( ServletConfig config )  throws ServletException
  {
    super.init( config );
  }

  public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
    SeagateTcmISDBModel db=null;
    String User_ID="";
    PrintWriter out=response.getWriter();
    response.setContentType( "text/html" );

    try
    {
      db=new SeagateTcmISDBModel( "Seagate" );
      if ( db == null )
      {
        PrintWriter out2=new PrintWriter( response.getOutputStream() );
        out2.println( "Starting DB" );
        out2.println( "DB is null" );
        out2.close();
        return;
      }

      HttpSession sessionlable=request.getSession();
	  String auth= sessionlable.getAttribute( "loginState" ) == null ? "" : sessionlable.getAttribute( "loginState" ).toString();
      String loginId1="";
      String passwd="";
      String errormsg="";

      if ( "challenge".equals( auth ) )
      {
        loginId1= ( String ) request.getParameter( "loginId" );
        passwd= ( String ) request.getParameter( "passwd" );
        if ( loginId1 == null )
          loginId1="";
        if ( passwd == null )
          passwd="";
      }

      if ( "challenge".equals( auth ) && ( loginId1.length() > 0 ) )
      {
        String company=request.getPathInfo();
        if ( company != null )
          company=company.substring( 1 ).toUpperCase();
        if ( company == null )
          company="SEAGATE";
        sessionlable.setAttribute( "COMPANYID",company );

        try
        {
          Password pw=new Password( db,loginId1,passwd );
          String personnelId=pw.getUserId();
          if ( personnelId == null )
          {
            errormsg="Server Authentication Error.<BR>\n";
            errormsg+="This Logon Id is not registered, Please re-enter your Logon Id, Password and try again.\n";
            out.println( radian.web.HTMLHelpObj.printSeagateLoginScreen( errormsg,"Label Information" ) );
            out.flush();
            return;
          }
          else
          {
            int testuserGroup=DbHelpers.countQuery( db,"select count(*) from user_group_member where user_group_id = 'LabelInfo' and personnel_id = " + personnelId + "" );
            if ( testuserGroup > 0 )
            {
              auth="authenticated1";
              sessionlable.setAttribute( "PERSONNELID",personnelId );
              sessionlable.setAttribute( "loginState","authenticated1" );
              sessionlable.setAttribute( "loginId",loginId1 );
            }
            else
            {
              errormsg="This Logon Id is not Setup to Enter Label Information, Please contact your CSR for Further Information.\n";
              out.println( radian.web.HTMLHelpObj.printSeagateLoginScreen( errormsg,"Label Information" ) );
              out.flush();
              return;
            }
          }
        }
        catch ( Exception e )
        {
          errormsg="<font face=Arial size=3>Server Authentication Error<BR>\n";
          errormsg+="Please Enter your Login-Id, Password and try again.</font>\n";
          out.println( radian.web.HTMLHelpObj.printSeagateLoginScreen( errormsg,"Label Information" ) );
          out.flush();
          return;
        }
      }
      else if ( auth.equalsIgnoreCase( "authenticated1" ) )
      {

      }
      else
      {
        sessionlable.setAttribute( "loginState","challenge" );
      }

      if ( ( sessionlable.isNew() ) || ! ( auth.equalsIgnoreCase( "authenticated1" ) ) )
      {
        out.println( radian.web.HTMLHelpObj.printSeagateLoginScreen( errormsg,"Label Information" ) );
        out.flush();
      }
      else
      {
        labelInfo obj=new labelInfo( ( ServerResourceBundle )new SeagateServerResourceBundle(),db );
        obj.doResponse( request,response,sessionlable );
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
    finally
    {
      db.close();
    }
  }

  public void doGet( HttpServletRequest request,
                     HttpServletResponse response )
     throws ServletException,
     IOException
  {
    doPost( request,response );
  }
}