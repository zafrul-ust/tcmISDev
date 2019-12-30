package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-18-03 - Added a http host fo the tcmIS.ico link so that the Icon shows up in the bookmark.
 */

public class generateItemMatSeqNumbers  extends HttpServlet implements SingleThreadModel
{
  public void init( ServletConfig config )
     throws ServletException
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
  private boolean intcmIsApplication = false;

  public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,
     IOException
  {
    PrintWriter out=response.getWriter();
    response.setContentType( "text/html" );
    RayTcmISDBModel db=null;
    DBResultSet dbrs=null;
    ResultSet rs=null;
    String User_Action="";
    HttpSession session=request.getSession();

		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		intcmIsApplication = false;
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

    try
    {
      db=new RayTcmISDBModel( "Tcm_Ops" );
      if ( db == null )
      {
        PrintWriter out2=new PrintWriter( response.getOutputStream() );
        out2.println( "Starting DB" );
        out2.println( "DB is null" );
        out2.close();
        return;
      }

      Hashtable loginresult=new Hashtable();
      loginresult=radian.web.loginhelpObj.logintopage( session,request,db,out );
      String auth= ( String ) loginresult.get( "AUTH" );
      String errormsg= ( String ) loginresult.get( "ERROSMSG" );

      if ( auth == null )
      {
        auth="challenge";
      }
      if ( errormsg == null )
      {
        errormsg="";
      }

      if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
      {
        out.println( radian.web.HTMLHelpObj.printCatalogLoginScreen( errormsg,"Generate Seq Numbers" ) );
        out.flush();
      }
      else
      {
        User_Action=request.getParameter( "UserAction" );
        if ( User_Action == null )
          User_Action="New";

        String howmany=request.getParameter( "howmany" );
        if ( howmany == null )
          howmany="0";

        String searchlike=request.getParameter( "searchlike" );
        if ( searchlike == null )
          searchlike="";

        StringBuffer MsgSB=new StringBuffer();
        if ( "generateseq".equalsIgnoreCase( User_Action ) )
        {
          int noofSeqs=0;
          if ( howmany.trim().length() > 0 )
          {
            noofSeqs=Integer.parseInt( howmany );
          }

					MsgSB.append(radian.web.HTMLHelpObj.printHTMLHeader("Generate Seq Numbers", "",intcmIsApplication));
          MsgSB.append( "</HEAD>  \n" );
          MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
          MsgSB.append( radian.web.HTMLHelpObj.PrintCatalogTitleBar( "Generate Seq Numbers" ) );
          MsgSB.append( "<form method=\"POST\" name=\"form1\">\n" );
          MsgSB.append( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"generateseq\">\n" );
          MsgSB.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
          MsgSB.append( "<TR>\n" );
          //Search
          MsgSB.append( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
          MsgSB.append( "<B>Generate:</B>&nbsp;&nbsp;\n" );
          MsgSB.append( "</TD>\n" );
          MsgSB.append( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"howmany\" value=\"" + howmany + "\" SIZE=\"5\"></TD>\n" );

          MsgSB.append( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
          MsgSB.append( "<SELECT CLASS=\"HEADER\"  NAME=\"searchlike\" size=\"1\">\n" );
          if ( searchlike.equalsIgnoreCase( "MATERIAL_ID" ) )
          {
            MsgSB.append( "<option selected value=\"MATERIAL_ID\">Material Ids</option>\n" );
            MsgSB.append( "<option value=\"ITEM_ID\">Item Ids</option>\n" );
            MsgSB.append( "<option value=\"TRADE_SECRET\">CAS Number/Trade Secret</option>\n" );
            MsgSB.append( "<option value=\"MANUFACTURER\">Manufacturer</option>\n" );
          }
          else if ( searchlike.equalsIgnoreCase( "ITEM_ID" ) )
          {
            MsgSB.append( "<option value=\"MATERIAL_ID\">Material Ids</option>\n" );
            MsgSB.append( "<option selected value=\"ITEM_ID\">Item Ids</option>\n" );
            MsgSB.append( "<option value=\"TRADE_SECRET\">CAS Number/Trade Secret</option>\n" );
            MsgSB.append( "<option value=\"MANUFACTURER\">Manufacturer</option>\n" );
          }
          else if ( searchlike.equalsIgnoreCase( "TRADE_SECRET" ) )
          {
            MsgSB.append( "<option value=\"MATERIAL_ID\">Material Ids</option>\n" );
            MsgSB.append( "<option value=\"ITEM_ID\">Item Ids</option>\n" );
            MsgSB.append( "<option selected value=\"TRADE_SECRET\">CAS Number/Trade Secret</option>\n" );
            MsgSB.append( "<option value=\"MANUFACTURER\">Manufacturer</option>\n" );
          }
          else if ( searchlike.equalsIgnoreCase( "MANUFACTURER" ) )
          {
            MsgSB.append( "<option value=\"MATERIAL_ID\">Material Ids</option>\n" );
            MsgSB.append( "<option value=\"ITEM_ID\">Item Ids</option>\n" );
            MsgSB.append( "<option value=\"TRADE_SECRET\">CAS Number/Trade Secret</option>\n" );
            MsgSB.append( "<option selected value=\"MANUFACTURER\">Manufacturer</option>\n" );
          }
          else
          {
            MsgSB.append( "<option value=\"MATERIAL_ID\">Material Ids</option>\n" );
            MsgSB.append( "<option selected value=\"ITEM_ID\">Item Ids</option>\n" );
            MsgSB.append( "<option value=\"TRADE_SECRET\">CAS Number/Trade Secret</option>\n" );
            MsgSB.append( "<option value=\"MANUFACTURER\">Manufacturer</option>\n" );
          }

          MsgSB.append( "</SELECT>\n" );
          MsgSB.append( "</TD>\n" );
          MsgSB.append( "<td width=\"5%\" align=\"right\"><input CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Generate\" name=\"Login\"></td>\n" );
          MsgSB.append( "</TR>\n" );
          MsgSB.append( "</table>\n" );

          if ( noofSeqs > 0 )
          {
            MsgSB.append( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"50\" CLASS=\"moveup\">\n" );
            MsgSB.append( "<TR>\n" );
            MsgSB.append( "<TH WIDTH=\"50\" HEIGHT=\"38\">" + searchlike + "</TH>\n" );
            MsgSB.append( "</TR>\n" );

            for ( int count=0; count < noofSeqs; count++ )
            {
              String seqNumber="";

              if ( searchlike.equalsIgnoreCase( "ITEM_ID" ) )
              {
                try
                {
                  String bquery="select seq_item_id.nextval NEWITEMID from dual";
                  dbrs=db.doQuery( bquery );
                  rs=dbrs.getResultSet();

                  while ( rs.next() )
                  {
                    seqNumber=rs.getString( "NEWITEMID" );
                  }

                }
                catch ( Exception e )
                {
                  e.printStackTrace();
                  throw e;
                }
                finally
                {
                  if (dbrs !=null) {dbrs.close();}
                }
              }

              else if ( searchlike.equalsIgnoreCase( "MANUFACTURER" ) )
              {
                try
                {
                  String bquery="select global.mfg_seq.nextval NEWITEMID from dual";
                  dbrs=db.doQuery( bquery );
                  rs=dbrs.getResultSet();

                  while ( rs.next() )
                  {
                    seqNumber=rs.getString( "NEWITEMID" );
                  }

                }
                catch ( Exception e )
                {
                  e.printStackTrace();
                  throw e;
                }
                finally
                {
                  if (dbrs !=null) {dbrs.close();}
                }
              }
              else if ( searchlike.equalsIgnoreCase( "TRADE_SECRET" ) )
              {
                try
                {
                  String bquery="select trade_secret_seq.nextval NEWITEMID from dual";
                  dbrs=db.doQuery( bquery );
                  rs=dbrs.getResultSet();

                  while ( rs.next() )
                  {
                    seqNumber=rs.getString( "NEWITEMID" );
                  }

                }
                catch ( Exception e )
                {
                  e.printStackTrace();
                  throw e;
                }
                finally
                {
                  if (dbrs !=null) {dbrs.close();}
                }
              }
              else if ( searchlike.equalsIgnoreCase( "MATERIAL_ID" ) )
              {
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
                      seqNumber="" + materialid1 + "";
                    }
                  }
                  catch ( Exception e )
                  {
                    e.printStackTrace();
                  }
                }
              }

              MsgSB.append( "<TR>\n" );
              MsgSB.append( "<TD WIDTH=\"50\" HEIGHT=\"38\">" + seqNumber + "</TD>" );
              MsgSB.append( "</TR>\n" );
            }
            MsgSB.append( "</TABLE>\n" );
          }
        }
        else
        {
          MsgSB.append( "<HTML><HEAD>\n" );
          MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
          String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
          MsgSB.append( "<LINK REL=\"SHORTCUT ICON\" HREF=\""+httphost+"images/buttons/tcmIS.ico\"></LINK>\n" );
          MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
          MsgSB.append( "<TITLE>Generate Seq Numbers</TITLE>\n" );
          MsgSB.append( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
          MsgSB.append( "</HEAD>  \n" );
          MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
          MsgSB.append( radian.web.HTMLHelpObj.PrintCatalogTitleBar( "Generate Seq Numbers" ) );
          MsgSB.append( "<form method=\"POST\" name=\"form1\">\n" );
          MsgSB.append( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"generateseq\">\n" );
          MsgSB.append( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
          MsgSB.append( "<TR>\n" );
          //Search
          MsgSB.append( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
          MsgSB.append( "<B>Generate:</B>&nbsp;&nbsp;\n" );
          MsgSB.append( "</TD>\n" );
          MsgSB.append( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"howmany\" value=\"" + howmany +  "\" SIZE=\"5\"></TD>\n" );

          MsgSB.append( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
          MsgSB.append( "<SELECT CLASS=\"HEADER\"  NAME=\"searchlike\" size=\"1\">\n" );
          if ( searchlike.equalsIgnoreCase( "MATERIAL_ID" ) )
          {
            MsgSB.append( "<option selected value=\"MATERIAL_ID\">Material Ids</option>\n" );
            MsgSB.append( "<option value=\"ITEM_ID\">Item Ids</option>\n" );
            MsgSB.append( "<option value=\"TRADE_SECRET\">CAS Number/Trade Secret</option>\n" );
            MsgSB.append( "<option value=\"MANUFACTURER\">Manufacturer</option>\n" );
          }
          else if ( searchlike.equalsIgnoreCase( "ITEM_ID" ) )
          {
            MsgSB.append( "<option value=\"MATERIAL_ID\">Material Ids</option>\n" );
            MsgSB.append( "<option selected value=\"ITEM_ID\">Item Ids</option>\n" );
            MsgSB.append( "<option value=\"TRADE_SECRET\">CAS Number/Trade Secret</option>\n" );
            MsgSB.append( "<option value=\"MANUFACTURER\">Manufacturer</option>\n" );
          }
          else if ( searchlike.equalsIgnoreCase( "TRADE_SECRET" ) )
          {
            MsgSB.append( "<option value=\"MATERIAL_ID\">Material Ids</option>\n" );
            MsgSB.append( "<option value=\"ITEM_ID\">Item Ids</option>\n" );
            MsgSB.append( "<option selected value=\"TRADE_SECRET\">CAS Number/Trade Secret</option>\n" );
            MsgSB.append( "<option value=\"MANUFACTURER\">Manufacturer</option>\n" );
          }
          else if ( searchlike.equalsIgnoreCase( "MANUFACTURER" ) )
          {
            MsgSB.append( "<option value=\"MATERIAL_ID\">Material Ids</option>\n" );
            MsgSB.append( "<option value=\"ITEM_ID\">Item Ids</option>\n" );
            MsgSB.append( "<option value=\"TRADE_SECRET\">CAS Number/Trade Secret</option>\n" );
            MsgSB.append( "<option selected value=\"MANUFACTURER\">Manufacturer</option>\n" );
          }
          else
          {
            MsgSB.append( "<option value=\"MATERIAL_ID\">Material Ids</option>\n" );
            MsgSB.append( "<option selected value=\"ITEM_ID\">Item Ids</option>\n" );
            MsgSB.append( "<option value=\"TRADE_SECRET\">CAS Number/Trade Secret</option>\n" );
            MsgSB.append( "<option value=\"MANUFACTURER\">Manufacturer</option>\n" );
          }

          MsgSB.append( "</SELECT>\n" );
          MsgSB.append( "</TD>\n" );
          MsgSB.append( "<td width=\"5%\" align=\"right\"><input CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Generate\" name=\"Login\"></td>\n" );
          MsgSB.append( "</TR>\n" );
          MsgSB.append( "</table>\n" );
          MsgSB.append( "</BODY></HTML>    \n" );

        }
        out.println( MsgSB );
        out.close();
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
      return;
    }
  }

  public void doGet( HttpServletRequest request,HttpServletResponse response )
     throws ServletException,IOException
  {
    doPost( request,response );
  }
}