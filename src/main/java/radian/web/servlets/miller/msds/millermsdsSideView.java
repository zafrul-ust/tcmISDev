package radian.web.servlets.miller.msds;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import java.net.HttpURLConnection;
import radian.tcmis.server7.share.db.DBResultSet;
import java.sql.ResultSet;

/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *10-13-03 New Miller MSDS
 *03-01-04 Showing departments this MSDS is attached to
 */
public class millermsdsSideView
{
    private ServerResourceBundle msdsbundle=null;
    private TcmISDBModel db = null;
    private boolean gettingHTML = false;
    private boolean gettingMSDS = false;
    private boolean gettingSIDE = false;
    private boolean gettingTITLE = false;
    private boolean showPrinticon = false;
    private String printiconurl = "";
    private String wwwhomeurl = "";
    private String clinetname = "";

    public millermsdsSideView( ServerResourceBundle b,TcmISDBModel d )
    {
      msdsbundle=b;
      db=d;
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,
        IOException {
        doPost(request,response);
    }

    //Process the HTTP Post request
    public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,
        IOException {
        wwwhomeurl = radian.web.tcmisResourceLoader.gethosturl();

        clinetname = msdsbundle.getString("DB_CLIENT_NAME");
        if ( "Tcm_Ops".equalsIgnoreCase(clinetname))
        {
          clinetname = "internal";
        }
        //System.out.println("clinetname  in MSDS      "+clinetname);

        String session = request.getParameter("SESSION");
        if (session == null){session = "4";}

        if ((session == null) || (session.equalsIgnoreCase("4"))){gettingHTML = true;}
        else if (session.equalsIgnoreCase("0")){gettingTITLE = true;}
        else if (session.equalsIgnoreCase("1")){gettingSIDE = true;}
        else if (session.equalsIgnoreCase("2")){gettingMSDS = true;}

        response.setContentType("text/html");

        try
        {
          PrintWriter out=new PrintWriter( response.getOutputStream() );
          if ( gettingHTML )
          {
            buildHTML( request,out );
          }
          else if ( gettingSIDE )
          {
            buildSideView( request,out );
          }
          else if ( gettingMSDS )
          {
            buildMSDS( request,out );
          }
          else if ( gettingTITLE )
          {
            buildTitle( request,out );
          }

          out.flush();
          out.close();
        }
        catch ( Exception e )
        {
          e.printStackTrace();
        }
      }

    public void buildHTML(HttpServletRequest requesthtml, PrintWriter out)
    {
	  String trade_name="";
	  trade_name=requesthtml.getParameter( "trade_name" );
	  if ( trade_name == null )
		trade_name="";

	  String content="";
	  content=requesthtml.getParameter( "content" );
	  if ( content == null )
		content="";

	  String customer_msds_no="";
	  customer_msds_no=requesthtml.getParameter( "customer_msds_no" );
	  if ( customer_msds_no == null )
		customer_msds_no="";


        String session = requesthtml.getParameter("SESSION");
        if (session == null){session = "4";}

        StringBuffer Msg=new StringBuffer();
		//String msdsservlet = msdsbundle.getString("VIEW_MSDS");
		String msdsservlet = "/tcmIS/miller/corpViewMsds?";

        Msg.append("<HTML><TITLE>MSDS Information</title>\n");
        Msg.append("<FRAMESET rows=\"70,*\">\n");
        Msg.append("<FRAME src=\""+msdsservlet+"SESSION=0&customer_msds_no=" +customer_msds_no +"\" name=\"title\" scrolling=\"auto\">\n");
        Msg.append("<FRAMESET cols=\"170,*\">\n");
        Msg.append("<FRAME src=\""+msdsservlet+"SESSION=1&customer_msds_no=" +customer_msds_no +"\" name=\"opts\" scrolling=\"auto\">\n");
        Msg.append("<FRAME src=\""+msdsservlet+"SESSION=2&content=" +content +"\" name=\"data\">\n");
        Msg.append("</FRAMESET>\n");
        Msg.append("</FRAMESET>\n");

        Msg.append("</html>\n");
        out.println(Msg);
    }

	public void buildSideView( HttpServletRequest requestside,PrintWriter out )
	{
	  String customer_msds_no="";
	  customer_msds_no=requestside.getParameter( "customer_msds_no" );
	  if ( customer_msds_no == null )
		customer_msds_no="";

	  StringBuffer Msgsideview=new StringBuffer();
	  String departmentlist = "";

	  Msgsideview.append( "<HTML><HEAd><TITLE>SideView</title>\n" );
	  Msgsideview.append( "<SCRIPT SRC=\"/clientscripts/printframesmsds.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  Msgsideview.append( "</HEAD>\n" );
	  Msgsideview.append( "<BODY BGCOLOR=\"#ffffff\" text=\"#000000\" link=\"#000066\" alink=\"#8E236B\" vlink=\"#4A766E\" onLoad=\"window.focus()\">\n" );
	  if ( customer_msds_no.trim().length() > 0 )
	  {
		Msgsideview.append( "<FONT FACE=\"Arial\" SIZE=\"2\"><B>MSDS No:</B>&nbsp;&nbsp;" + customer_msds_no + "</FONT><BR>\n" );
	  }

	DBResultSet dbrs = null;
	ResultSet rs = null;
	String searchquery = "select distinct department from fac_bldg_floor_msds where CUSTOMER_MSDS_NO = '"+customer_msds_no+"' ";

	try
	{
	  dbrs=db.doQuery( searchquery );
	  rs=dbrs.getResultSet();

	  while ( rs.next() )
	  {
		departmentlist += rs.getString( "DEPARTMENT" ) == null ? "" : rs.getString( "DEPARTMENT" ) + ",";
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

	departmentlist = departmentlist.substring(0, departmentlist.length() - 1);
	Msgsideview.append( "<FONT FACE=\"Arial\" SIZE=\"2\"><B>Departments:</B>&nbsp;&nbsp;" + departmentlist + "</FONT><BR>\n" );

	  Msgsideview.append( "</CENTER>\n" );
	  Msgsideview.append( "</body></html> \n" );
	  out.println( Msgsideview );

  }

  public void buildTitle( HttpServletRequest requesttitle,PrintWriter out )
  {
	String trade_name="";
	trade_name=requesttitle.getParameter( "trade_name" );
	if ( trade_name == null )
	  trade_name="";

	String customer_msds_no="";
	customer_msds_no=requesttitle.getParameter( "customer_msds_no" );
	if ( customer_msds_no == null )
	  customer_msds_no="";

	StringBuffer labelStr=new StringBuffer();
	StringBuffer tifStr=new StringBuffer();
	StringBuffer badimgStr=new StringBuffer();
	StringBuffer jscript=new StringBuffer();
	StringBuffer tailStr=new StringBuffer();
	String notshow="";
	String msds_hold="";
	String date_hold="";
	String mat_desc_hold="";
	String contentspec="";

	    DBResultSet dbrs = null;
		ResultSet rs = null;
		String searchquery = "select * from customer_msds where CUSTOMER_MSDS_NO = '"+customer_msds_no+"' ";

		try
		{
		  dbrs=db.doQuery( searchquery );
		  rs=dbrs.getResultSet();

		  while ( rs.next() )
		  {
			trade_name = rs.getString( "TRADE_NAME" ) == null ? "" : rs.getString( "TRADE_NAME" );
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

        String router= "title";
        jscript.append("<HTML>\n");
        jscript.append("<SCRIPT SRC=\"/clientscripts/printframesmsds.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");

        jscript.append("<body bgcolor=white text=000000>\n<center>\n");
        jscript.append("<table width=100% height=100% border=0 cellpadding=0 cellspacing=0>\n");
        jscript.append("<tr><td width=15% align=\"center\" valign=\"center\">\n");
        jscript.append("<img src=\""+wwwhomeurl+"images/buttons/"+clinetname.toLowerCase()+"/"+clinetname.toLowerCase()+".gif\">\n");
        jscript.append("</td><td width=\"58%\" valign=\"center\">\n");
        jscript.append("<center><table border=0 cellpadding=2 cellspacing=0 bgcolor=\"#7093DB\">\n");
        jscript.append("<tr><td><table border=0 cellspacing=0 cellpadding=0 bgcolor=\"#ffffff\">\n");
        jscript.append("<tr><td><FONT size=4 face=Arial><b>&nbsp;&nbsp;" + trade_name +"\n");
        jscript.append("&nbsp;&nbsp;</FONT></td></tr></table>\n");
        jscript.append("</td></tr></table></center><center>\n");

		String helpurl = msdsbundle.getString("HELP_SERVLET_PATH");
        tailStr.append("<td><a href=\"#\" onMouseOver=\"imgOn('imghelp')\" onMouseOut=\"imgOff('imghelp')\" onClick=\"openwin(\'/tcmIS/help/new/\')\">\n");
        tailStr.append("<img name=\"imghelp\" src=\""+wwwhomeurl+"images/help_up.gif\" border=0></a>\n");
        tailStr.append("</td></tr></table>\n");
        tailStr.append("</body></html>\n");

        String WWWhomeDirectory = msdsbundle.getString("WEBS_HOME_WWWS");

        /*if ((act.equalsIgnoreCase("msds")) ||(act.equalsIgnoreCase("spec")))
        {
		String erroremailurl = radian.web.tcmisResourceLoader.getmsdserrorurl();
        badimgStr.append("<td align=\"right\" valign=\"center\">\n");
        badimgStr.append("<a href=\"#\" onClick=\"openwin23(\'"+erroremailurl+"spec_hold="+spec_hold+"&catpartno="+catpartno+"&act="+act+"&material_id="+id+"&revision_date="+date_hold+"&cl="+msdsbundle.getString("DB_CLIENT")+"&facility="+BothHelpObjs.addSpaceForUrl(facility)+"&url="+msds_hold+"\')\">\n");
        badimgStr.append("<img name=\"img2\" src=\""+wwwhomeurl+"images/docx2.gif\" alt=\"Please click to report a problem with the current image\" border=0></a></td>\n");
        }*/

        badimgStr.append(tailStr);
        tifStr.append(badimgStr);
        labelStr.append(tifStr);
        jscript.append(labelStr);
        //msg = headStr + labelStr + tifStr + badimgStr + tailStr;
        out.println(jscript);
    }

    public StringBuffer doAutoDetect( String content,String content2,String radios3, String plug1 ,String action, String id1, String urlfacility1, String catpartno1)
    {
      StringBuffer Msgauto =new StringBuffer();
	  String msdsservlet = msdsbundle.getString("VIEW_MSDS");

      if ( plug1.equalsIgnoreCase( "null" ) || plug1 == null || plug1.equalsIgnoreCase( "" ) )
      {
            Msgauto.append("<HTML><HEAd><TITLE>Main View</title>\n");
            Msgauto.append("        <SCRIPT LANGUAGE = \"JavaScript\">\n");
            Msgauto.append("<!--\n");
            Msgauto.append("    function plugdetect(plugName)\n");
            Msgauto.append("         {\n");
            Msgauto.append("          browserNN = (navigator.appName == \"Netscape\");\n");
            Msgauto.append("          browserIE = (navigator.appName == \"Microsoft Internet Explorer\");\n");
            Msgauto.append("           if (browserNN){\n");
            Msgauto.append("             if (navigator.plugins[plugName]) return true;\n");
            Msgauto.append("             else return false;\n");
            Msgauto.append("           }else if (browserIE){\n");
            Msgauto.append("             return true;\n");
            Msgauto.append("           }\n");
            Msgauto.append("           }\n");
            Msgauto.append("    function detect(){\n");
            Msgauto.append("         if (plugdetect(\"Adobe Acrobat\") == true){\n");
            Msgauto.append("             return \"Y\";\n \n");
            Msgauto.append("         } else {  \n");
            Msgauto.append("             return \"N\";}\n");
            Msgauto.append("    }\n");
            Msgauto.append("function AutoDetect()\n");
            Msgauto.append("{\n");
            // Store the selected option in a variable called ref\n");
            Msgauto.append("ref=\"" + radios3 + "\";\n");
            // Count the position of the optional &\n");
            Msgauto.append("splitcharacter=ref.lastIndexOf(\"*\");\n");
            Msgauto.append("if (splitcharacter!=-1) {loc=ref.substring(0,splitcharacter);\n");
            Msgauto.append("target=ref.substring(splitcharacter+1,1000).toLowerCase();}\n");
            Msgauto.append("else {loc=ref; target=\"_self\";};\n");
            if ( action.equalsIgnoreCase( "spec" ) )
            {
              Msgauto.append("loc=loc + \"&plug=\" + detect() ;\n");
            }
            else
            {
              Msgauto.append("plug=detect();\n if(plug==\"N\"){loc=\""+msdsservlet+"SESSION=2&act=msds&id=" + id1 + "&date=null&facility=" + urlfacility1 + "&catpartno=" + catpartno1 + "&spec=null&plug=N\";\n}else{loc=\"" + content + "\";}\n");
            }
            // create a varible called lowloc to store loc in lowercase\n");
            //characters.\n");
            // Skip the rest of this function if the selected optionvalue is\n");
            //"false".\n");
            Msgauto.append("parent.frames[target].location=loc;\n");
            Msgauto.append("}\n");
            Msgauto.append("// -->\n");
            Msgauto.append("</script>\n");
            Msgauto.append("</head>\n");
            Msgauto.append("<body bgcolor=\"#ffffff\" text=000000 link=000066 alink=8E236B vlink=4A766E onLoad=\"AutoDetect()\"></body></html>\n");
        }
        else if ( plug1.startsWith( "Y" ) )
        {
          Msgauto.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + content + "\">\n");
        }
        else
        {
            Msgauto.append( "<HTML><body bgcolor=white text=000000>\n<center><table border=0 width=50%>\n" );
            // "<img src=\""+wwwhomeurl+"images/buttons/spec/tcmspec.gif\">\n");
            Msgauto.append("<tr><td><BR><P><FONT face=arial size=3 COLOR=ff0000><b>Adobe Acrobat Reader</b></FONT>\n");
            Msgauto.append("<FONT size=2 face=Arial> is required to view most MSDS and Specification files.<BR><BR>\n");
            Msgauto.append("Please check with your Computer Support Desk or Network administrator if you have specific procedures for the installation of this software.\n");
            Msgauto.append("If you do not have this software installed, you may download it \n");
            Msgauto.append("free at this link:<BR><BR><BR>\n");
            Msgauto.append("<center><a href=\"http://www.adobe.com/products/acrobat/readstep.html\" target=\"_new\">\n");
            Msgauto.append("<img src=\""+wwwhomeurl+"images/buttons/getacro.gif\" border=0 alt=\"Adobe Acrobat Reader\"></a><BR><BR>\n");
            Msgauto.append("Once you have this software installed, please follow the directions below to configure it as a Netscape plugin. </center>\n");
            Msgauto.append("</td></tr>\n<tr><td><BR><HR>\n");
            Msgauto.append("<P><FONT size=2 face=Arial>Please click the button below to download the file:</FONT>\n");
            Msgauto.append("<FORM method=\"GET\" action=\"" + content + "\">\n");
            Msgauto.append("<center><input type=\"Submit\" value=\"" + content2 + "\"></td></tr></table></form></body></html><BR><HR>\n");
            Msgauto.append("<center><h3>Configuring Adobe as a plugin</h3><table width=80% border=0><tr><td align=left>\n");
            Msgauto.append("<FONT size=2 face=Arial><BR><BR>Download and install Adobe Acrobat Reader from the link above. Please follow the guide below to \n");
            Msgauto.append("set Adobe Acrobat Reader as a browser plugin:<BR>\n");
            Msgauto.append("<ul><li>Select 'Find' -> 'Files or Folders' from the windows Start Menu</li>\n");
            Msgauto.append("<li>Locate the file: 'nppdf32.dll' on your C:\\ drive</li>\n");
            Msgauto.append("<li>Copy the file to your browser PlugIns directory:\n");
            Msgauto.append("<B>'Program&nbsp;Files\\Netscape\\Communicator\\Program\\Plugins'</b> for Netscape,\n");
            Msgauto.append("<B>'Program&nbsp;Files\\Plus\\Internet&nbsp;Explorer\\Plugins'</b> for MS Internet Explorer, \n");
            Msgauto.append("on Windows systems.</li>\n");
            Msgauto.append("<li>Restart your active browser to update the plugin registry.\n");
            Msgauto.append("</ul><BR><BR></td></tr></table>\n");
        }
        return Msgauto;
    }

	public void buildMSDS( HttpServletRequest requestmsds,PrintWriter out )
	   throws IOException
	{
	  String msdscontent="";
	  msdscontent=requestmsds.getParameter( "content" );
	  if ( msdscontent == null )
		msdscontent="";


	  String testurl2="";
	  if ( msdscontent.startsWith( "https" ) )
	  {
		String testurl1=msdscontent.substring( 5 );
		testurl2="http" + testurl1;
	  }
	  else
	  {
		testurl2=msdscontent;
	  }

	  int isss=0;

	  //System.out.println(testurl2);

	  try
	  {
		URL url=new URL( testurl2 );
		HttpURLConnection huc= ( HttpURLConnection ) url.openConnection();
		isss=huc.getResponseCode();
	  }
	  catch ( IOException ex1 )
	  {
		isss=404;
		//System.out.print( "testurl2  " + testurl2 + "   Error  " + ex1.getMessage() );
	  }

	  StringBuffer Msgmsds=new StringBuffer();
	  if ( isss == 404 )
	  {
		Msgmsds.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + wwwhomeurl + "gen/msds_not_found.html\">\n" );
	  }
	  else
	  {
		if ( isss == 404 || msdscontent.trim().equalsIgnoreCase( "" + wwwhomeurl + "gen/msds_not_found.html" ) )
		{
		  Msgmsds.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + wwwhomeurl + "gen/msds_not_found.html\">\n" );
		}
		else
		{
		  Msgmsds.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + msdscontent + "\">\n" );
		}

	  }

	  out.println( Msgmsds );
  }
}
