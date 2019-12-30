package radian.web.servlets.share;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Vector;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.noMatSideView;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import java.net.HttpURLConnection;
import radian.tcmis.server7.share.helpers.HelpObjs;

/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2004
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 * 03-12-04 Made Changes to accomodate Sikorsky MSDSs. These are MSDS with no material ID
 *
 */
public class noMatmsdsView
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

    public noMatmsdsView( ServerResourceBundle b,TcmISDBModel d )
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

        if ((session == null) || (session.equalsIgnoreCase("4"))){
          gettingHTML = true;
        }
        else if (session.equalsIgnoreCase("0")){
          gettingTITLE = true;
        }
        else if (session.equalsIgnoreCase("1")){
          gettingSIDE = true;
        }
        else if (session.equalsIgnoreCase("2")){
          gettingMSDS = true;
        }

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
        String rev_date = requesthtml.getParameter("date");
        String showspec = requesthtml.getParameter("showspec");
		if ( showspec == null )
		  showspec="";
		showspec.trim();

        String plug = requesthtml.getParameter("plug");
        String id = requesthtml.getParameter("id");
        String facility = requesthtml.getParameter("facility");
        if (facility==null){facility="";}
        //facility = facility.trim();
        facility = HelpObjs.findReplace(facility," ","");
        String URLfacility = facility;
        URLfacility = URLfacility.replace(' ','+');

        String catpartno = requesthtml.getParameter("catpartno");
		if ( catpartno == null )
		  catpartno="";
		catpartno.trim();

        String act = requesthtml.getParameter("act");
		if ( showspec == null )
		  showspec="msds";

        String client= requesthtml.getParameter("cl");
        if (client == null) {client = "";}
        if (client.length() > 0){client = BothHelpObjs.addSpaceForUrl(client);}

        String spec = requesthtml.getParameter("spec");
		if ( spec == null )
		  spec="";
		spec.trim();

        String session = requesthtml.getParameter("SESSION");
        if (session == null){session = "4";}

        StringBuffer Msg=new StringBuffer();
		String msdsservlet = msdsbundle.getString("VIEW_NON_MAT_MSDS");

        Msg.append("<HTML><TITLE>MSDS Information</title>\n");
        Msg.append("<FRAMESET rows=\"70,*\">\n");
        Msg.append("<FRAME src=\""+msdsservlet+"SESSION=0&act=" + act + "&id=" + id +"&date=" + rev_date + "&facility=" + URLfacility + "&catpartno=" + catpartno + "&spec=" + spec +"&cl="+client+"\" name=\"title\" scrolling=\"auto\">\n");
        Msg.append("<FRAMESET cols=\"170,*\">\n");
        Msg.append("<FRAME src=\""+msdsservlet+"SESSION=1&act=" + act + "&id=" +id +"&date=" + rev_date + "&facility=" + URLfacility + "&catpartno=" + catpartno + "&spec=" + spec +"&cl="+client+"&showspec=" + showspec + "\" name=\"opts\" scrolling=\"auto\">\n");
        Msg.append("<FRAME src=\""+msdsservlet+"SESSION=2&act=" + act + "&id=" +id +"&date=" + rev_date + "&facility=" + URLfacility + "&catpartno=" + catpartno + "&spec=" + spec +"&cl="+client+"&plug=" + plug + "\" name=\"data\">\n");
        Msg.append("</FRAMESET>\n");
        Msg.append("</FRAMESET>\n");

        Msg.append("</html>\n");
        out.println(Msg);
    }

    public void buildSideView( HttpServletRequest requestside, PrintWriter out )
    {
      String notshowmsds="";
      String nottoshowspec="No Records";
	  String msdsservlet = msdsbundle.getString("VIEW_NON_MAT_MSDS");
      StringBuffer Msgsideview = new StringBuffer();
      String revdateforlinks = requestside.getParameter("date");
      String showspec = requestside.getParameter("showspec");
	  if ( showspec == null )
		showspec="";
	  showspec.trim();

      String id = requestside.getParameter("id");
      String facility = requestside.getParameter("facility");
      if (facility==null){facility="";}
      facility = facility.trim();
      String URLfacility = facility;
      URLfacility = URLfacility.replace(' ','+');

      String catpartno=requestside.getParameter( "catpartno" );
      if ( catpartno == null )
        catpartno="";
      catpartno.trim();

      String act = requestside.getParameter("act");
      String spec = requestside.getParameter("spec");
	  if ( spec == null )
		spec="";
	  spec.trim();


     if (act == null){act="msds";}

     String heal="";
     String flam="";
     String react="";
     String haz="";
     String radios="";
     String radios2="";

     String hmisheal = "";
     String hmisflam = "";
     String hmisreact = "";
     String hmisppe = "";
     String clientmsdsno = "";

     Hashtable alldatamsds=new Hashtable();
     Hashtable alldataspec=new Hashtable();
	 boolean showspecdrop = false;
	 if ( spec.length() > 0 ) {showspecdrop = true;}

	 noMatSideView svObj = new noMatSideView(msdsbundle);
     try
     {
       alldatamsds=buildData( requestside,svObj.getSideViewVector( db,revdateforlinks,id,facility),revdateforlinks );
     }
     catch ( Exception e )
     {
       e.printStackTrace();
       notshowmsds = "No Records";
     }

     if ( showspecdrop )
     {
       try
       {
         alldataspec=buildData3( svObj.getSpecVector( db,id,catpartno,facility ) );
       }
       catch ( Exception e )
       {
         e.printStackTrace();
         nottoshowspec = "No Records";
       }

       if ( alldataspec == null )
       {
         nottoshowspec="No Records";
       }
       else
       {
         nottoshowspec=alldataspec.get( "SHOWRECORDS" ).toString();
         radios2=alldataspec.get( "RADIOS2" ).toString();
       }
     }

        if (alldatamsds == null)
        {
          notshowmsds = "No Records";
        }
        else
        {
          notshowmsds=alldatamsds.get( "SHOWRECORDS" ).toString();
          radios=alldatamsds.get( "RADIOS" ).toString();
          heal=alldatamsds.get( "HEALTH" ).toString();
          flam=alldatamsds.get( "FLAMABILITY" ).toString();
          react=alldatamsds.get( "REACTIVITY" ).toString();
          haz=alldatamsds.get( "HAZARD" ).toString();

          hmisheal=alldatamsds.get( "HMIS_HEALTH" ).toString();
          hmisflam=alldatamsds.get( "HMIS_FLAMABILITY" ).toString();
          hmisreact=alldatamsds.get( "HMIS_REACTIVITY" ).toString();
          hmisppe=alldatamsds.get( "HMIS_PPE" ).toString();

          clientmsdsno=alldatamsds.get( "MSDS_NO" ).toString();

          revdateforlinks = alldatamsds.get( "REVDATELINK" ).toString();
        }

        if ( ( notshowmsds.equalsIgnoreCase( "No Records" ) ) && ( nottoshowspec.equalsIgnoreCase( "No Records" ) ) )
        {
          Msgsideview.append("<HTML><HEAd><TITLE>SideView</title>\n<BODY><FONT FACE=\"Arial\"><BR><BR><BR><B>No Data\n");
          Msgsideview.append("</B></FONT></BODY>\n");
          out.println( Msgsideview );
          return;
        }

        Msgsideview.append("<HTML><HEAd><TITLE>SideView</title>\n");
        Msgsideview.append("<SCRIPT SRC=\"/clientscripts/printframesmsds.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
        Msgsideview.append("</HEAD>\n");
        Msgsideview.append("<BODY BGCOLOR=\"#ffffff\" text=\"#000000\" link=\"#000066\" alink=\"#8E236B\" vlink=\"#4A766E\" onLoad=\"window.focus()\">\n");
        if (clientmsdsno.trim().length() > 0)
        {
          Msgsideview.append( "<FONT FACE=\"Arial\" SIZE=\"2\"><B>MSDS No:</B>&nbsp;&nbsp;" + clientmsdsno + "</FONT><BR>\n" );
        }
        //=======Specifications=======//
        if ( showspecdrop )
        {
          Msgsideview.append( "<P>\n" );
          Msgsideview.append( "<FORM ACTION=\"\" METHOD=POST>\n" );
          Msgsideview.append( "<TABLE CELLSPACING=\"0\" CELLPADDING=\"1\" BORDER=\"0\" BGCOLOR=\"#7093DB\" WIDTH=\"100%\">\n" );
          Msgsideview.append( "<TR><TD>\n" );
          Msgsideview.append( "<TABLE CELLSPACING=\"0\" CELLPADDING=\"0\" BORDER=\"0\" BGCOLOR=\"#FFFFFF\" WIDTH=\"100%\">\n" );
          Msgsideview.append( "<TR><TD ALIGN=\"LEFT\" BGCOLOR=\"#7093DB\">\n" );
          Msgsideview.append( "<FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#E6E8FA\"><B><center>Specifications<BR></FONT>\n" );
          Msgsideview.append( "<FONT FACE=\"Arial\" SIZE=\"1\" COLOR=\"#000000\">\n" );
          Msgsideview.append( "Available&nbsp;Specs&nbsp;are&nbsp;linked:<BR>\n" );
          Msgsideview.append( "</TD></TR>\n" );
          Msgsideview.append( "<TR><TD ALIGN=\"CENTER\">\n" );
          Msgsideview.append( "<FONT FACE=\"Arial\" SIZE=\"1\">\n" );
          Msgsideview.append( "<SELECT NAME=\"specList\" onChange=\"DropDownMenuSpec(this)\">\n" );
          Msgsideview.append( "<option value=\"*false\" selected>Please Select</option>\n" );
          Msgsideview.append( "" + radios2 + "</select>\n" );
          Msgsideview.append( "</FONT></FONT>\n" );
          Msgsideview.append( "</TD></TR>\n" );
          Msgsideview.append( "</TABLE>\n" );
          Msgsideview.append( "</TD></TR>\n" );
          Msgsideview.append( "</TABLE>\n" );
          Msgsideview.append( "</FORM>\n" );
        }

        if ( act.equalsIgnoreCase( "spec" ) )
        {
          Msgsideview.append( "<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n" );
          Msgsideview.append( "<!--\n" );
          Msgsideview.append( "  winact = \"spec\"\n" );
          Msgsideview.append( "  specrevdate = \"" + revdateforlinks + "\"\n" );
          Msgsideview.append( "  specid = \"" + id + "\"\n" );

          Msgsideview.append( "// -->\n" );
          Msgsideview.append( "</SCRIPT>\n" );
        }

        Msgsideview.append( "<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n" );
        Msgsideview.append( "<!--\n" );
        Msgsideview.append( "  clientname = \"" + clinetname + "\"\n" );
        Msgsideview.append( "// -->\n" );
        Msgsideview.append( "</SCRIPT>\n" );

        Msgsideview.append( "<FORM ACTION=\"\"  NAME=\"MSDSForm\" METHOD=POST>\n" );
        Msgsideview.append( "<TABLE CELLSPACING=\"0\" CELLPADDING=\"1\" BORDER=\"0\" BGCOLOR=\"#7093DB\" WIDTH=\"100%\">\n" );
        Msgsideview.append( "<TR>\n" );
        Msgsideview.append( "<TD>\n" );
        Msgsideview.append( "<TABLE CELLSPACING=\"0\" CELLPADDING=\"0\" BORDER=\"0\" BGCOLOR=\"#FFFFFF\" WIDTH=\"100%\">\n" );
        Msgsideview.append( "<TR>\n" );
        Msgsideview.append( "<TD ALIGN=\"LEFT\" BGCOLOR=\"#7093DB\">\n" );
        Msgsideview.append( "<FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#E6E8FA\"><B><center>MSDS<BR></FONT>\n" );
        Msgsideview.append( "<FONT FACE=\"Arial\" SIZE=\"1\" COLOR=\"#000000\">Revision Date</FONT>\n" );
        Msgsideview.append( "&nbsp;&nbsp;&nbsp;<BR>\n" );
        Msgsideview.append( "</TD>\n" );
        Msgsideview.append( "</TR>\n" );
        Msgsideview.append( "<TR>\n" );
        Msgsideview.append( "<TD ALIGN=\"CENTER\">\n" );
        Msgsideview.append( "<FONT FACE=\"Arial\" SIZE=\"1\">\n" );
        Msgsideview.append( "<SELECT NAME=\"msdsList\" onChange=\"DropDownMenuMSDS(this)\">\n" );
        Msgsideview.append( "" + radios + "</select>\n" );
        Msgsideview.append( "</FONT>\n" );
        Msgsideview.append( "</TD>\n" );
        Msgsideview.append( "</TR>\n" );
        Msgsideview.append( "</TABLE>\n" );
        Msgsideview.append( "</TD>\n" );
        Msgsideview.append( "</TR>\n" );
        Msgsideview.append( "</TABLE>\n" );

        String qurl="";
        if ( spec == null ) {spec = "";}
        if ( facility == null ) {facility = "";}
        if ( catpartno == null ) {catpartno = "";}

        if ( spec.length() != 0 )
        {
          qurl=qurl + "&spec=" + spec;
        }

        if ( facility.length() != 0 )
        {
          qurl=qurl + "&facility=" + BothHelpObjs.addSpaceForUrl( facility );
        }

        if ( catpartno.length() != 0 )
        {
          qurl=qurl + "&catpartno=" + catpartno;
        }

        if ( act.equalsIgnoreCase( "msds" ) )
        {
          Msgsideview.append( "<BR><center><a href=\""+msdsservlet+"SESSION=2&act=msds&id=" + id + "" + qurl + "&" + "date=" + revdateforlinks + "&plug=\" target=\"data\"\n" );
          Msgsideview.append( "onClick=\"doTitle('img5')\">\n" );
          Msgsideview.append( "<img name=\"img5\" src=\"" + wwwhomeurl + "images/buttons/2msds.gif\" border=0>\n" );
          Msgsideview.append( "</a><INPUT TYPE=\"hidden\" NAME=\"msdsactive\" VALUE=\"msds\"><BR><BR>\n\n" );

          Msgsideview.append( "<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n" );
          Msgsideview.append( "<!--\n" );
          Msgsideview.append( "  winact = \"msds\";\n" );
          Msgsideview.append( "// -->\n" );
          Msgsideview.append( "</SCRIPT>\n" );

        }
        else
        {
          Msgsideview.append( "<BR><center><a href=\""+msdsservlet+"SESSION=2&act=msds&id=" + id + "" + qurl + "&" + "date=" + revdateforlinks + "&plug=\" target=\"data\"\n" );
          Msgsideview.append( "onClick=\"doTitle('img5')\">\n" );
          Msgsideview.append( "<img name=\"img5\" src=\"" + wwwhomeurl + "images/buttons/1msds.gif\" border=0>\n" );
          Msgsideview.append( "</a><INPUT TYPE=\"hidden\" NAME=\"msdsactive\" VALUE=\"msds\" disabled ><BR><BR>\n" );
        }

        if ( act.equalsIgnoreCase( "comp" ) )
        {
          Msgsideview.append( "<center><a href=\""+msdsservlet+"SESSION=2&act=comp&id=" + id + "&" + "date=" + revdateforlinks + "&facility="+facility+"\" target=\"data\"\n" );
          Msgsideview.append( "onClick=\"doTitle('img1')\">\n" );
          Msgsideview.append( "<img name=\"img1\" src=\"" + wwwhomeurl + "images/buttons/comp/2comp.gif\" border=0>\n" );
          Msgsideview.append( "</a><INPUT TYPE=\"hidden\" NAME=\"composition\" VALUE=\"comp\"><BR><BR>\n" );

          Msgsideview.append( "<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n" );
          Msgsideview.append( "<!--\n" );
          Msgsideview.append( "  winact = \"comp\";\n" );
          Msgsideview.append( "// -->\n" );
          Msgsideview.append( "</SCRIPT>\n" );
        }
        else
        {
          Msgsideview.append( "<center><a href=\""+msdsservlet+"SESSION=2&act=comp&id=" + id + "&" + "date=" + revdateforlinks + "&facility="+facility+"\" target=\"data\"\n" );
          Msgsideview.append( "onClick=\"doTitle('img1')\">\n" );
          Msgsideview.append( "<img name=\"img1\" src=\""+wwwhomeurl+"images/buttons/comp/1comp.gif\" border=0>\n" );
          Msgsideview.append( "</a><INPUT TYPE=\"hidden\" NAME=\"composition\" VALUE=\"comp\" disabled ><BR><BR>\n" );
        }

    if ( act.equalsIgnoreCase( "prop" ) )
    {
      Msgsideview.append( "<a href=\""+msdsservlet+"SESSION=2&act=prop&id=" + id + "&" + "date=" + revdateforlinks + "&facility="+facility+"\" target=\"data\"\n" );
      Msgsideview.append( "onClick=\"doTitle('img2')\">\n" );
      Msgsideview.append( "<img name=\"img2\" src=\""+wwwhomeurl+"images/buttons/prop/2prop.gif\" border=0>\n" );
      Msgsideview.append( "</a><INPUT TYPE=\"hidden\" NAME=\"properties\" VALUE=\"prop\"><BR><BR>\n" );

      Msgsideview.append( "<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n" );
      Msgsideview.append( "<!--\n" );
      Msgsideview.append( "  winact = \"prop\";\n" );
      Msgsideview.append( "// -->\n" );
      Msgsideview.append( "</SCRIPT>\n" );
    }
    else
    {
      Msgsideview.append( "<a href=\""+msdsservlet+"SESSION=2&act=prop&id=" + id + "&" + "date=" + revdateforlinks + "&facility="+facility+"\" target=\"data\"\n" );
      Msgsideview.append( "onClick=\"doTitle('img2')\">\n" );
      Msgsideview.append( "<img name=\"img2\" src=\""+wwwhomeurl+"images/buttons/prop/1prop.gif\" border=0>\n" );
      Msgsideview.append( "</a><INPUT TYPE=\"hidden\" NAME=\"properties\" VALUE=\"prop\" disabled ><BR><BR>\n" );
    }

    /*if ( act.equalsIgnoreCase("lists"))
    {
      Msgsideview.append( "<a href=\""+msdsservlet+"SESSION=2&act=lists&id=" + id + "&" + "date=" + revdateforlinks + "&facility="+facility+"\" target=\"data\"\n" );
      Msgsideview.append( "onClick=\"doTitle('img3')\">\n" );
      Msgsideview.append( "<img name=\"img3\" src=\""+wwwhomeurl+"images/buttons/lists/2lists.gif\" border=0>\n" );
      Msgsideview.append( "</a><INPUT TYPE=\"hidden\" NAME=\"lists\" VALUE=\"lists\"><BR><BR>\n" );

      Msgsideview.append( "<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n" );
      Msgsideview.append( "<!--\n" );
      Msgsideview.append( "  winact = \"lists\";\n" );
      Msgsideview.append( "// -->\n" );
      Msgsideview.append( "</SCRIPT>\n" );
    }*/

    {
      //Msgsideview.append( "<a href=\""+msdsservlet+"SESSION=2&act=lists&id=" + id + "&" + "date=" + revdateforlinks + "&facility="+facility+"\" target=\"data\"\n" );
      //Msgsideview.append( "onClick=\"doTitle('img3')\">\n" );
      Msgsideview.append( "<img name=\"img3\" src=\""+wwwhomeurl+"images/buttons/lists/1lists.gif\" border=0 STYLE=\"display: none;\">\n" );
      Msgsideview.append( "<INPUT TYPE=\"hidden\" NAME=\"lists\" VALUE=\"lists\" disabled ><BR><BR>\n" );
    }


    Msgsideview.append("<INPUT TYPE=\"hidden\" NAME=\"landdetail\" VALUE=\"msds\" disabled>\n<INPUT TYPE=\"hidden\" NAME=\"airdetail\" VALUE=\"msds\" disabled>\n");
    Msgsideview.append("</FORM>\n");

    Msgsideview.append("<CENTER><B>NFPA</B>\n");
    if ( ( flam.equalsIgnoreCase( "&nbsp;" ) ) && ( heal.equalsIgnoreCase( "&nbsp;" ) ) && ( react.equalsIgnoreCase( "&nbsp;" ) ) )
    {
      Msgsideview.append( "<BR>Not Listed<BR>\n" );
    }
    else
    {
    Msgsideview.append("<table background=\""+wwwhomeurl+"images/buttons/nfpa.gif\" width=80 height=82 cellpadding=0 cellspacing=0 border=0>\n");
    Msgsideview.append("<tr><td valign=\"center\" align=\"center\"><FONT size=\"1\" face=Arial COLOR=ffffff><p><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
    Msgsideview.append("<B><FONT size=2>" + flam + "</FONT>\n");
    Msgsideview.append("</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BR>&nbsp;<B><FONT size=2>" + heal + "</FONT>\n");
    Msgsideview.append("</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<FONT COLOR=000000><B>\n");
    Msgsideview.append("<FONT size=2>" + react + "</FONT></b>&nbsp;<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>\n");
    Msgsideview.append("<FONT size=2>" + haz + "</FONT></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BR><BR>\n");
    Msgsideview.append("</td>\n");
    Msgsideview.append("</tr>\n");
    Msgsideview.append("</table>\n");
    }

    Msgsideview.append("<BR><B>HMIS</B>\n");
    if ( ( hmisflam.length() == 0 ) && ( hmisheal.length() == 0 ) && ( hmisreact.length() == 0 ) && ( hmisppe.length() == 0 ) )
    {
      Msgsideview.append( "<BR>Not Listed<BR>\n" );
    }
    else
    {
    Msgsideview.append( "<BR><TABLE height=63 cellSpacing=0 cellPadding=0 width=60 border=0>\n" );
    Msgsideview.append( "<TR><TD BGCOLOR=#0000ff vAlign=TOP height=15 align=middle><FONT face=Arial color=#ffffff size=2><B>"+hmisheal+"</B></FONT></TD></TR>\n" );
    Msgsideview.append( "<TR><TD BGCOLOR=#ff0000 vAlign=TOP height=15 align=middle><FONT face=Arial color=#ffffff size=2><B>"+hmisflam+"</B></FONT></TD></TR>\n" );
    Msgsideview.append( "<TR><TD BGCOLOR=#ffff00 vAlign=TOP height=15 align=middle><FONT face=Arial color=#000000 size=2><B>"+hmisreact+"</B></FONT></TD></TR>\n" );
    Msgsideview.append( "<TR><TD BGCOLOR=#ffffff vAlign=TOP height=15 align=middle><FONT face=Arial color=#000000 size=2><B>"+hmisppe+"</B></FONT></TD></TR>\n" );
    Msgsideview.append( "</TABLE>\n" );
    }

    if ( showPrinticon )
    {
      Msgsideview.append( "<BR><A HREF=\"#\" ID=\"printlinkie\" onclick=\"javascript:window.open('" + printiconurl + "')\" STYLE=\"color:#0000ff;cursor:hand;\"><U>Open MSDS</U></A>\n" );
      Msgsideview.append( "<SCRIPT LANGUAGE=\"Javascript\" TYPE=\"text/javascript\">\n" );
      Msgsideview.append( "<!--\n" );
      Msgsideview.append( "insertprintLink('" + printiconurl + "');\n" );
      Msgsideview.append( "// -->\n" );
      Msgsideview.append( "</SCRIPT>\n" );
    }
    Msgsideview.append("</CENTER>\n");
    Msgsideview.append( "</body></html> \n" );
    out.println(Msgsideview);

    alldatamsds= null;
    alldataspec= null;
  }

    public void buildTitle(HttpServletRequest requesttitle, PrintWriter out)
    {
         String rev_date = requesttitle.getParameter("date");
         String id = requesttitle.getParameter("id");
         String rev_date_hold = requesttitle.getParameter("date");
         String spec_hold = requesttitle.getParameter("spec");
         String facility = requesttitle.getParameter("facility");
         if (facility==null){facility="";}
         String URLfacility = facility;
         URLfacility = URLfacility.replace(' ','+');

         String catpartno = requesttitle.getParameter("catpartno");
         String act = requesttitle.getParameter("act");
         String spec = requesttitle.getParameter("spec");

         String hasTIF = requesttitle.getParameter("hasTIF");
         if (hasTIF == null){hasTIF = "N";}

         if (act == null){act="msds";}

         StringBuffer labelStr = new StringBuffer();
         StringBuffer tifStr = new StringBuffer();
         StringBuffer badimgStr = new StringBuffer();
         StringBuffer jscript = new StringBuffer();
         StringBuffer tailStr = new StringBuffer();
         String notshow = "";
         String msds_hold = "";
         String date_hold = "";
         String mat_desc_hold = "";
         String contentspec = "";

         if (rev_date == null){rev_date="null";}
         if (id == null){id="null";}
         if (spec == null){spec="null";}
         if (catpartno == null){catpartno="null";}

		 noMatSideView svObj = new noMatSideView(msdsbundle);

         /*
         try {
            URL url = new URL("http","www.tcmis.com",80,"/cgi-bin/find_tif.cgi");
            HttpURLConnection hc = (HttpURLConnection) url.openConnection();
            hc.setRequestMethod("POST");
            hc.setDoOutput(true);
            String data = "act=" + act + "&id=" + id + "&spec=" + spec + "&date=" + rev_date_hold + "&cl=ray";
            OutputStreamWriter osw = new OutputStreamWriter(hc.getOutputStream());
            osw.write(data);
            osw.close();
            hc.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
            String response = br.readLine();
            if (response.startsWith("Y")) {
                hasTIF = new String("Y");
            } else {
                hasTIF = new String("N");
            }
            hc.disconnect();
            //System.out.println("\n---------- hastif: "+hasTIF);
                  } catch (Exception E) {
            System.out.println(E);
                  }
        */

        String router= "title";
        jscript.append("<HTML>\n");
        jscript.append("<SCRIPT SRC=\"/clientscripts/printframesmsds.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");

        Vector titlev=null;
        try
        {
          titlev=svObj.getContentVector( db,rev_date,id,"title",spec,catpartno,facility);
        }
        catch ( Exception ex )
        {
          notshow ="No Records";
        }
        if ( titlev.elementAt( 0 ).toString().equalsIgnoreCase( "No Records" ) )
        {
          notshow="No Records";
        }
        else
        {
          for ( int j=0; j < titlev.size(); j++ )
          {
            noMatSideView sv= ( noMatSideView ) titlev.elementAt( j );
            if (spec_hold==null ||spec.equalsIgnoreCase("null")){spec_hold = sv.getSpecId();}
            if (rev_date_hold==null ||rev_date.equalsIgnoreCase("null")){rev_date_hold = sv.getDate2();}
            msds_hold=sv.getContent();
            date_hold=sv.getDate2();
            String mat_desc_hold_temp =sv.getMaterial();
			if (mat_desc_hold_temp.trim().length() > 0)
			{
			  mat_desc_hold = mat_desc_hold_temp;
		    }
          }
        }

        jscript.append("<body bgcolor=white text=000000>\n<center>\n");
        jscript.append("<table width=100% height=100% border=0 cellpadding=0 cellspacing=0>\n");
        jscript.append("<tr><td width=15% align=\"center\" valign=\"center\">\n");
        jscript.append("<img src=\""+wwwhomeurl+"images/buttons/"+clinetname.toLowerCase()+"/"+clinetname.toLowerCase()+".gif\">\n");
        jscript.append("</td><td width=\"58%\" valign=\"center\">\n");
        jscript.append("<center><table border=0 cellpadding=2 cellspacing=0 bgcolor=\"#7093DB\">\n");
        jscript.append("<tr><td><table border=0 cellspacing=0 cellpadding=0 bgcolor=\"#ffffff\">\n");
        jscript.append("<tr><td><FONT size=4 face=Arial><b>&nbsp;&nbsp;" + mat_desc_hold +"\n");
        jscript.append("&nbsp;&nbsp;</FONT></td></tr></table>\n");
        jscript.append("</td></tr></table></center><center>\n");

		String helpurl = msdsbundle.getString("HELP_SERVLET_PATH");
        tailStr.append("<td><a href=\"#\" onMouseOver=\"imgOn('imghelp')\" onMouseOut=\"imgOff('imghelp')\" onClick=\"openwin(\'/tcmIS/help/new/\')\">\n");
        tailStr.append("<img name=\"imghelp\" src=\""+wwwhomeurl+"images/help_up.gif\" border=0></a>\n");
        tailStr.append("</td></tr></table>\n");
        tailStr.append("</body></html>\n");

        String WWWhomeDirectory = msdsbundle.getString("WEBS_HOME_WWWS");

        if ((act.equalsIgnoreCase("msds")) ||(act.equalsIgnoreCase("spec")))
        {
		String erroremailurl = radian.web.tcmisResourceLoader.getmsdserrorurl();
        badimgStr.append("<td align=\"right\" valign=\"center\">\n");
        badimgStr.append("<a href=\"#\" onClick=\"openwin23(\'"+erroremailurl+"spec_hold="+spec_hold+"&catpartno="+catpartno+"&act="+act+"&material_id="+id+"&revision_date="+date_hold+"&cl="+msdsbundle.getString("DB_CLIENT")+"&facility="+BothHelpObjs.addSpaceForUrl(facility)+"&url="+msds_hold+"\')\">\n");
        badimgStr.append("<img name=\"img2\" src=\""+wwwhomeurl+"images/docx2.gif\" alt=\"Please click to report a problem with the current image\" border=0></a></td>\n");
        }

        if ((act.equalsIgnoreCase("msds")) && (msds_hold.equalsIgnoreCase(" ")) && !(notshow.equalsIgnoreCase("No Records")))
        {
            String msdsDir = "/MSDS/";
            int msdsIndex = msds_hold.lastIndexOf(msdsDir) + msdsDir.length();
            msds_hold = msds_hold.substring(msdsIndex);
            labelStr.append("<FONT size=1 face=Arial COLOR=green>MSDS: <FONT COLOR=000000>" + rev_date_hold + "</FONT></center></td>\n");
            if (hasTIF.startsWith("Y"))
            {
                String tifImg = msds_hold;
                if (tifImg.endsWith("pdf"))
                {
                    tifImg = tifImg.substring(0,tifImg.lastIndexOf(".")+1);
                    tifImg = tifImg + "tif";
                    tifStr.append("<td align=\"right\" valign=\"center\">&nbsp;\n");
                    tifStr.append("<a href=\""+wwwhomeurl+"MSDS/tifs/" +tifImg+ "\" target=\"data\">\n");
                    tifStr.append("<img name=\"tif\" src=\""+wwwhomeurl+"images/tif.gif\"></a></td>\n");
                }
                else
                {
                    // Only worry about PDF
                }
            }
            else
            {
                // No TIF available
            }
        }
        else if (act.equalsIgnoreCase("spec"))
        {
           labelStr.append("<FONT size=1 face=Arial COLOR=green>Specification: <FONT COLOR=000000>" + spec_hold + "</FONT></center></td>\n");
           /* badimgStr = "<td align=\"right\" valign=\"center\">\n");
       "<a href=\"#\" onClick=\"openwin2(\'"+wwwhomeurl+"cgi-bin/gen/problem_image.cgi?pi=" + spec_hold + "&doc=spec\')\">\n");
       "<img name=\"img2\" src=\""+wwwhomeurl+"images/docx2.gif\" alt=\"Please click to report a problem with the current image\" border=0></a></td>\n";*/

            if (hasTIF.startsWith("Y"))
            {
                String tifImg = contentspec;
                if (tifImg.endsWith("pdf"))
                {
                    tifImg = spec_hold;
                    tifImg = tifImg + ".tif";
                    tifStr.append("<td align=\"right\" valign=\"center\">&nbsp;\n");
                    tifStr.append("<a href=\""+wwwhomeurl+"OpSupport/Raytheon/spec_drawings/" +tifImg+ "\" target=\"data\">\n");
                    tifStr.append("<img name=\"tif\" src=\""+wwwhomeurl+"images/tif.gif\"></a></td>\n");
                }
                else
                {
                    // Only worry about PDF
                }
            }
            else
            {
                // No TIF available
            }
        }
        else
        {
            labelStr.append("<FONT size=1 face=Arial COLOR=green>Revision Date: <FONT COLOR=000000>" + date_hold + "</FONT></center></td>\n");
            //Use rev_date_hold if you want the latest date always
            //labelStr = "<FONT size=1 face=Arial COLOR=green>Revision Date: <FONT COLOR=000000>" + rev_date + "</FONT></center></td>\n";
        }
        router="";
        badimgStr.append(tailStr);
        tifStr.append(badimgStr);
        labelStr.append(tifStr);
        jscript.append(labelStr);
        //msg = headStr + labelStr + tifStr + badimgStr + tailStr;
        out.println(jscript);
        titlev=null;
    }

    public StringBuffer doAutoDetect( String content,String content2,String radios3, String plug1 ,String action, String id1, String urlfacility1, String catpartno1)
    {
      StringBuffer Msgauto =new StringBuffer();
	  String msdsservlet = msdsbundle.getString("VIEW_NON_MAT_MSDS");

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

    public void buildMSDS(HttpServletRequest requestmsds ,PrintWriter out)throws IOException
    {
     String rev_date = requestmsds.getParameter("date");
     String plug = requestmsds.getParameter("plug");
     String id = requestmsds.getParameter("id");
     String facility = requestmsds.getParameter("facility");
     String listId = requestmsds.getParameter("listid");
     String lookupListId = requestmsds.getParameter("listid");
     String currentRow = requestmsds.getParameter("currentrow");
     String parentName = requestmsds.getParameter("parentname");
     String childName = requestmsds.getParameter("childname");
     if (facility==null){facility="";}
     String URLfacility = facility;
     URLfacility = URLfacility.replace(' ','+');

     String catpartno = requestmsds.getParameter("catpartno");
     String act = requestmsds.getParameter("act");

     String spec = requestmsds.getParameter("spec");
     if (act == null){act="msds";}
     if ( spec == null ){spec="";}

     String msdscontent="";
     String notshow1="";
     String material_desc="";
     String composition="";
     String density="";
     String flash_point="";
     String boiling_point="";
     String freezing_point="";
     String spec_grav="";
     String phy_state="";
     String trade_name="";
     String radios3 = "";
     String ground_ship="";
     String air_ship="";
     String hazard_class="";
     String un_num="";
     String na_num="";
     String pack_group="";
     //list
     String listResult = "";
     String subListResult = "";
     String compoundResult = "";
     int count = 0;
     String manufacturer = "";

     StringBuffer Msgmsds = new StringBuffer();
	 noMatSideView svObj = new noMatSideView(msdsbundle);
	 Vector titlev=null;
	 try
	 {
	   titlev=svObj.getContentVector(db,
                                         rev_date,
                                         id,
                                         act,
                                         spec,
                                         catpartno,
                                         facility,
                                         listId,
                                         lookupListId,
                                         currentRow);
	 }
	 catch ( Exception ex )
	 {
	   notshow1="No Records";
	 }

  if ( titlev.elementAt( 0 ).toString().equalsIgnoreCase( "No Records" ) )
  {
    notshow1 ="No Records";
  }
  else
  {
    for ( int j=0; j < titlev.size(); j++ )
    {
      noMatSideView sv= ( noMatSideView ) titlev.elementAt( j );
      if ( act.equalsIgnoreCase( "msds" ) )
      {
        msdscontent=sv.getContent();
      }
      else if ( act.equalsIgnoreCase( "spec" ) )
      {
        msdscontent=sv.getContent();
        radios3=sv.getRadios3();
      }
      else if ( act.equalsIgnoreCase( "comp" ) )
      {
        material_desc=sv.getMaterial();
        composition += sv.getComposition();
        if(j == 0) {
          trade_name = sv.getTradeName();
          manufacturer = sv.getManufacturer();
        }
      }
      else if ( act.equalsIgnoreCase( "prop" ) )
      {
        density=sv.getDensity();
        flash_point=sv.getFlash();
        boiling_point=sv.getBoiling();
        trade_name=sv.getTradeName();
        phy_state=sv.getPhysicalState();
        spec_grav=sv.getSpecGrav();
        freezing_point = sv.getFreezing();
        if (trade_name == null){trade_name = "<td><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=red>Not Entered</FONT></FONT></td>\n</tr>";}
        if (boiling_point.startsWith("null")){boiling_point = "noshow";}
        if (flash_point.startsWith("null")){flash_point = "noshow";}
        if (density.startsWith("null")){density = "noshow";}
        if (phy_state.startsWith("null")){phy_state = "noshow";}
        if (spec_grav.startsWith("null")){spec_grav = "noshow";}
        if (freezing_point.startsWith("null")){freezing_point = "noshow";}

        ground_ship=sv.getGroundShippingName();
        air_ship=sv.getAirShippingName();
        hazard_class=sv.getHazardClass();
        un_num=sv.getUnNumber();
        na_num=sv.getNaNumber();
        pack_group = sv.getPackingGroup();
        if(j == 0) {
          manufacturer = sv.getManufacturer();
        }
      }
      else if(act.equalsIgnoreCase("lists") || act.equalsIgnoreCase("sublist")) {
        if(j == 0) {
          manufacturer = sv.getManufacturer();
          trade_name = sv.getTradeName();
        }

        String startLink = "<a href=\"" + msdsbundle.getString("VIEW_NON_MAT_MSDS") +
             "SESSION=2&id=" + id + "&date=" + rev_date +
             "&act=sublist&listid=" + sv.getListId() + "#" + sv.getListId() +
             "\">";
         String compoundLink = "<a href=\"" + msdsbundle.getString("VIEW_NON_MAT_MSDS") +
             "SESSION=2&act=compound&listid=" + sv.getListId() +
             "\" target=\"new\">";

         String color = "";
         //alternate row colors
         if(j % 2 == 1) {
           color = "bgcolor=\"#E6E8FA\"";
         }
        listResult += "<tr><td " + color + " ALIGN=\"CENTER\" WIDTH=\"5%\"><FONT FACE=\"Arial\" SIZE=\"2\">";
        //add anchor tag
        listResult += "<a name=\"" + sv.getListId() + "\">&nbsp;</a>";
        //display + sign with link if it has child records
        if((sv.getSubList()).equalsIgnoreCase("Yes")) {
          //display - sign if it's expanded
          if(act.equalsIgnoreCase("sublist") &&
             listId.equalsIgnoreCase(sv.getListId())) {
            listResult += "<a href=\"" + msdsbundle.getString("VIEW_NON_MAT_MSDS") +
             "SESSION=2&id=" + id + "&date=" + rev_date +
             "&act=lists#" + sv.getListId() + "\"><img src=\"" + wwwhomeurl +
             "images/minus.jpg\"></a>";
          }
          else {
            listResult += startLink + "<img src=\"" + wwwhomeurl +
                "images/plus.jpg\"></a>";
          }
        }
        else {
          listResult += "&nbsp";
        }
        listResult += "</FONT></td><td " + color + " ALIGN=\"LEFT\" WIDTH=\"65%\"><FONT FACE=\"Arial\" SIZE=\"2\"><b>" +
            "<a href=\"" + msdsbundle.getString("VIEW_NON_MAT_MSDS") +
            "SESSION=2&act=compound&parentname=" + sv.getListName() + "&listid=" + sv.getListId() +
            "\" target=\"new\">" + sv.getListName() + "</a></b></FONT></td>" +
            "<td " + color + " ALIGN=\"CENTER\" WIDTH=\"15%\"><FONT FACE=\"Arial\" SIZE=\"2\">";
       if(sv.getOnList().equalsIgnoreCase("Yes")) {
         listResult += "<a href=\"" + msdsbundle.getString("VIEW_NON_MAT_MSDS") +
             "SESSION=2&act=compoundpercent&date=" + rev_date + "&id=" + id + "&parentname=" + sv.getListName() +
             "&listid=" + sv.getListId() +
             "\" target=\"new\">" + sv.getOnList() + "</a>";
       }
       else {
         listResult += sv.getOnList();
       }
       listResult += "</font></td>" + "<td " + color + " ALIGN=\"CENTER\" WIDTH=\"15%\"><FONT FACE=\"Arial\" SIZE=\"2\">";
        //only display percent if it is not 0
        if(sv.getPercent() != null && !(sv.getPercent().equalsIgnoreCase("0"))) {
          listResult += sv.getPercent();
        }
        else {
          listResult += "&nbsp;";
        }
        listResult += "</font></td></tr>\n";
        if(act.equalsIgnoreCase("sublist") &&
           listId.equalsIgnoreCase(sv.getListId())) {

          for(int i=0;i<sv.getSubListVector().size();i++) {
            String subColor = "";
            if(i % 2 == 0)
              subColor = "";
            listResult += "<tr><td " + subColor + ">&nbsp;</td><td " + subColor + "><FONT FACE=\"Arial\" SIZE=\"2\"><I>" + "<a href=\"" +
                msdsbundle.getString("VIEW_NON_MAT_MSDS") +
                "SESSION=2&act=compound&parentname=" + sv.getListName() +
                "&childname=" + ((Vector)sv.getSubListVector().elementAt(i)).elementAt(0) + "&listid=" +
                ((Vector)sv.getSubListVector().elementAt(i)).elementAt(1) +
                "\" target=\"new\">"
                + ((Vector)sv.getSubListVector().elementAt(i)).elementAt(0) +
                "</a></I></font></td><td " + subColor + " align=\"center\"><FONT FACE=\"Arial\" SIZE=\"2\">";
            if(((String)((Vector)sv.getSubListVector().elementAt(i)).elementAt(2)).equalsIgnoreCase("Yes")) {
              listResult += "<a href=\"" + msdsbundle.getString("VIEW_NON_MAT_MSDS") +
                  "SESSION=2&act=compoundpercent&date=" + rev_date + "&id=" + id + "&parentname=" + sv.getListName() +
                  "&childname=" + ((Vector)sv.getSubListVector().elementAt(i)).elementAt(0) +
                  "&listid=" + ((Vector)sv.getSubListVector().elementAt(i)).elementAt(1) +
                  "\" target=\"new\">" + ((Vector)sv.getSubListVector().elementAt(i)).elementAt(2) + "</a>";
            }
            else {
              listResult += ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(2);
            }
            listResult += "</font></td><td " + subColor + " align=\"center\"><FONT FACE=\"Arial\" SIZE=\"2\">";
            if(((Vector)sv.getSubListVector().elementAt(i)).elementAt(3) != null) {
              listResult += ((Vector)sv.getSubListVector().elementAt(i)).elementAt(3);
            }
            else {
              listResult += "&nbsp;";
            }
            listResult += "</font></td></tr>\n";
          }
        }
      }
      else if(act.equalsIgnoreCase("compound")) {
        String color = "";
        //alternate row colors
        if(j % 2 == 1) {
          color = "bgcolor=\"#E6E8FA\"";
        }

        compoundResult += "<tr><td " + color + " valign=\"top\" align=\"right\"><FONT FACE=\"Arial\" SIZE=\"2\">" + sv.getCasNumber() + "</font></td>" +
                          "<td " + color + " valign=\"top\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"2\">" + sv.getRptChemical() + "</font></td></tr>";
        //set count to see if we need more results later
        count = sv.getCount();
      }
      else if(act.equalsIgnoreCase("compoundpercent")) {
        String color = "";
        //alternate row colors
        if(j % 2 == 1) {
          color = "bgcolor=\"#E6E8FA\"";
        }

        compoundResult += "<tr><td " + color + " valign=\"top\" align=\"right\"><FONT FACE=\"Arial\" SIZE=\"2\">" + sv.getPercent() + "</font></td>" +
                          "<td " + color + " valign=\"top\" align=\"right\"><FONT FACE=\"Arial\" SIZE=\"2\">" + sv.getCasNumber() + "</font></td>" +
                          "<td " + color + " valign=\"top\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"2\">" + sv.getRptChemical() + "</font></td></tr>";
      }

    }
  }

  String testurl2="";
  if ( msdscontent.startsWith( "https" ) )
  {
    String testurl1=msdscontent.substring( 5 );
    testurl2="http" + testurl1;
  }
  else
  {
    testurl2 = msdscontent;
  }

  int isss = 0;

  if ( act.equalsIgnoreCase( "msds" ) && !testurl2.endsWith("/") )
  {
    try
    {
      URL url=new URL( testurl2 );
      HttpURLConnection huc= ( HttpURLConnection ) url.openConnection();
      isss=huc.getResponseCode();
    }
    catch ( IOException ex1 )
    {
      isss = 404;
    }
  }
  else if ( act.equalsIgnoreCase( "spec" ) )
  {
    if ( msdscontent.startsWith( "http://" ) )
    {
      testurl2=msdscontent;
    }
    else
    {
      testurl2=""+wwwhomeurl+"OpSupport/Raytheon/spec_drawings/" + msdscontent;
    }
    if ( !testurl2.endsWith("/") )
    {
      try
      {
        if ( testurl2.startsWith( "https" ) )
        {
          testurl2="http" + testurl2.substring( 5 );
        }

        URL url=new URL( testurl2 );
        HttpURLConnection huc= ( HttpURLConnection ) url.openConnection();
        isss=huc.getResponseCode();
      }
      catch ( IOException ex2 )
      {
        isss = 404;
      }
    }
  }

  if ( isss == 404 )
  {
    Msgmsds.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url="+wwwhomeurl+"gen/msds_not_found.html\">\n" );
  }
  else if ( act.equalsIgnoreCase( "msds" ) )
  {
    if ( isss == 404 || msdscontent.trim().equalsIgnoreCase( ""+wwwhomeurl+"gen/msds_not_found.html" ) )
    {
      Msgmsds.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url="+wwwhomeurl+"gen/msds_not_found.html\">\n" );
    }
    else if ( msdscontent.endsWith( ".pdf" ) )
    {
      radios3=msdscontent + "*data";
      String content2=msdscontent.substring( 33 );
      Msgmsds.append( doAutoDetect( msdscontent,content2,radios3,plug,act,id,URLfacility,catpartno) );
    }
    else
    {
      Msgmsds.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + msdscontent + "\">\n" );
    }

  }
  else if ( act.equalsIgnoreCase( "spec" ) )
  {
    if ( msdscontent.startsWith( "http" ) )
    {
      Msgmsds.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + msdscontent + "\">\n" );
    }
    else
    {
      if ( msdscontent.endsWith( ".pdf" ) )
      {
        String content2="";
        content2=msdscontent;
        msdscontent=""+wwwhomeurl+"OpSupport/Raytheon/spec_drawings/" + msdscontent;
        Msgmsds.append(doAutoDetect( msdscontent,content2,radios3,plug,act,id,URLfacility,catpartno ));
      }
      else
      {
        Msgmsds.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url="+wwwhomeurl+"OpSupport/Raytheon/spec_drawings/" + msdscontent + "\">\n" );
      }
    }
  }
  else if(act.equalsIgnoreCase("comp"))
  {
      Msgmsds.append( "<HTML><HEAd>\n");
      Msgmsds.append( "<TITLE>Chemical Composition for " + material_desc + "</title>\n");
      Msgmsds.append( "</head>\n");
      Msgmsds.append( "<body bgcolor=\"#FFFFFF\"><center>\n");
      Msgmsds.append( "<BR><BR>\n");
      Msgmsds.append( "<div align=\"center\"><center>\n");
      Msgmsds.append( "<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"90%\" >\n");
      Msgmsds.append( "<tr><td colspan=\"5\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Trade Name:</b>&nbsp;" + material_desc + "</FONT></TD></tr>\n");
      Msgmsds.append( "<tr><td colspan=\"5\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Manufacturer:</b>&nbsp;" + manufacturer + "</FONT></TD></tr>\n");
      Msgmsds.append( "<tr><td colspan=\"5\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Revision Date:</b>&nbsp;" + rev_date + "</FONT></TD></tr>\n");

      Msgmsds.append( "    <tr>\n");
      Msgmsds.append( "        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"15%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>CAS No</B></FONT></TD>\n");
      Msgmsds.append( "        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"55%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>Name</B></FONT></TD>\n");
      Msgmsds.append( "        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"10%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>Lower %</B></FONT></TD>\n");
      Msgmsds.append( "        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"10%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>Upper %</B></FONT></TD>\n");
      Msgmsds.append( "        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"10%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>Average* %</B></FONT></TD>\n");
      Msgmsds.append( "    </tr>\n");
      Msgmsds.append(composition);
      Msgmsds.append( "\n</table></center></div><BR>\n");
      Msgmsds.append( " *  The average is calculated according to the following EPA guidence:<BR>\n");
      Msgmsds.append( "<center><TABLE BORDER=\"1\" CELLPADDING=\"5\" WIDTH=\"70%\" ><TR><TD>\n");
      Msgmsds.append( "The following guidelines were observed in estimating concentrations of toxic\n");
      Msgmsds.append( "chemicals in mixtures when only limited information is available: If you\n" );
      Msgmsds.append( "know the lower and upper bound concentrations of a toxic chemical in a\n" );
      Msgmsds.append( "mixture, use the midpoint of these two concentrations for threshold\n" );
      Msgmsds.append( "determinations. If you know only the lower bound concentration, you\n" );
      Msgmsds.append( "should subtract out the percentages of anyother known components to\n" );
      Msgmsds.append( "determine a reasonable upper bound concentration, and then determine a\n" );
      Msgmsds.append( "midpoint. If you have no information other than the lower bound\n" );
      Msgmsds.append( "concentration, calculate a midpoint assuming an upper bound\n" );
      Msgmsds.append( "concentration of 100 percent. If you only know the upper bound\n" );
      Msgmsds.append( "concentration, you must use it for threshold determinations. In cases\n" );
      Msgmsds.append( "where you only have a concentration range available, you should use the\n" );
      Msgmsds.append( "midpoint of the range extremes. 16 Toxic Release Inventory Reporting\n" );
      Msgmsds.append( "Forms and Instructions\n</TD></TR></center></table>\n" );
      Msgmsds.append( "</body></html>\n" );
    }
    else if ( act.equalsIgnoreCase( "prop" ) )
    {
      Msgmsds.append( "<HTML><HEAd>\n");
      Msgmsds.append( "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
      Msgmsds.append( "<meta name=\"GENERATOR\" content=\"Microsoft FrontPage 2.0\">\n");
      Msgmsds.append( "<TITLE>Properties for " + trade_name + "</title>\n");
      Msgmsds.append( "</head>\n");
      Msgmsds.append( "<body bgcolor=\"#FFFFFF\"><center>\n");
      Msgmsds.append( "<p>\n");
      Msgmsds.append( "<p>\n");
      Msgmsds.append( "<div align=\"center\"><center>\n");
      Msgmsds.append( "<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"65%\" >\n");
      Msgmsds.append( "<tr><td colspan=\"2\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Trade Name:</b>&nbsp;" + trade_name + "</FONT></TD></tr>\n");
      Msgmsds.append( "<tr><td colspan=\"2\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Manufacturer:</b>&nbsp;" + manufacturer + "</FONT></TD></tr>\n");
      Msgmsds.append( "<tr><td colspan=\"2\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Revision Date:</b>&nbsp;" + rev_date + "</FONT></TD></tr>\n");

      Msgmsds.append( "<tr><td colspan=2 bgcolor=\"#000066\" align=\"center\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#ffffff\"><B>Material Properties</B></FONT></td></tr>\n");
      if (!(phy_state.equalsIgnoreCase("noshow")))
      {
          Msgmsds.append( "<tr>\n");
          Msgmsds.append( "<td><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#000066\"><B>Physical State</B></FONT></td>\n<td><FONT FACE=\"Arial\" SIZE=\"2\">" + phy_state +"\n");
      }
      if (!(spec_grav.equalsIgnoreCase("noshow")))
      {
          Msgmsds.append( "<tr>\n");
          Msgmsds.append( "<td BGCOLOR=\"E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#000066\"><B>Specific Gravity</B></FONT></td>\n<td BGCOLOR=\"E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">" + spec_grav +"\n");
      }
      if (!(density.equalsIgnoreCase("noshow")))
      {
          Msgmsds.append( "<tr>\n");
          Msgmsds.append( "<td><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#000066\"><B>Density</B></FONT></td>\n<td><FONT FACE=\"Arial\" SIZE=\"2\">" + density  +"\n");
      }
      if (!(freezing_point.equalsIgnoreCase("noshow")))
      {
          Msgmsds.append( "<tr>\n");
          Msgmsds.append( "<td BGCOLOR=\"E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#000066\"><B>Freezing Point</B></FONT></td>\n<td BGCOLOR=\"E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">" + freezing_point  +"\n");
        }
        if ( ! ( boiling_point.equalsIgnoreCase( "noshow" ) ) )
        {

          Msgmsds.append( "<tr>\n" );
          Msgmsds.append( "<td><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#000066\"><B>Boiling Point</td>\n<td><FONT FACE=\"Arial\" SIZE=\"2\">" + boiling_point + "\n" );
        }
        if ( ! ( flash_point.equalsIgnoreCase( "noshow" ) ) )
        {
          Msgmsds.append( "<tr>\n" );
          Msgmsds.append( "<td BGCOLOR=\"E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#000066\"><B>Flash Point</B></FONT></td>\n<td BGCOLOR=\"E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">" + flash_point + "\n" );
        }

        Msgmsds.append( "</table>\n" );
        Msgmsds.append( "</center></div><BR><BR><center>\n" );
        Msgmsds.append( "</body>\n" );
        Msgmsds.append( "</html>\n" );
      }
      else if (act.equalsIgnoreCase("lists") || act.equalsIgnoreCase("sublist")) {
        Msgmsds.append( "<HTML><HEAd>\n");
        Msgmsds.append( "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
        Msgmsds.append( "<meta name=\"GENERATOR\" content=\"Microsoft FrontPage 2.0\">\n");
        Msgmsds.append( "<TITLE>Lists for " + listId + "</title>\n");
        Msgmsds.append( "</head>\n");
        Msgmsds.append( "<body bgcolor=\"#FFFFFF\"><center>\n");
        Msgmsds.append( "<p>\n");
        Msgmsds.append( "<div align=\"center\"><center>\n");
        Msgmsds.append( "<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"65%\" >\n");
        Msgmsds.append( "<tr><td colspan=\"4\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Trade Name:</b>&nbsp;" + trade_name + "</FONT></TD></tr>\n");
        Msgmsds.append( "<tr><td colspan=\"4\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Manufacturer:</b>&nbsp;" + manufacturer + "</FONT></TD></tr>\n");
        Msgmsds.append( "<tr><td colspan=\"4\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Revision Date:</b>&nbsp;" + rev_date + "</FONT></TD></tr>\n");
        Msgmsds.append( "    <tr>\n");
        Msgmsds.append( "        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"10%\" FACE=\"Arial\" COLOR=\"#ffffff\">&nbsp;</FONT></TD>\n");
        Msgmsds.append( "        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"60%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>List</B></FONT></TD>\n");
        Msgmsds.append( "        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"15%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>On List</B></FONT></TD>\n");
        Msgmsds.append( "        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"15%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>%</B></FONT></TD>\n");
        Msgmsds.append( "    </tr>\n");
        Msgmsds.append(listResult);
        Msgmsds.append( "</table>\n" );
        Msgmsds.append( "</center></div><BR><BR><center>\n" );
        Msgmsds.append( "</body>\n" );
        Msgmsds.append( "</html>\n" );

      }
      else if (act.equalsIgnoreCase("compound")) {
        Msgmsds.append( "<HTML><HEAd>\n");
        Msgmsds.append( "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
        Msgmsds.append( "<meta name=\"GENERATOR\" content=\"Microsoft FrontPage 2.0\">\n");
        Msgmsds.append( "<TITLE>Lists for " + listId + "</title>\n");
        Msgmsds.append( "</head>\n");
        Msgmsds.append( "<body bgcolor=\"#FFFFFF\"><center>\n");
        Msgmsds.append( "<p>\n");
        Msgmsds.append( "<div align=\"center\"><center>\n");
        Msgmsds.append( "<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"100%\" >\n");
        if(childName == null || childName.length() == 0) {
          childName = "N/A";
        }
        Msgmsds.append( "<tr><td colspan=\"2\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"3\"><b>List:</b>&nbsp;" + parentName + "</FONT></td><tr>\n");
        Msgmsds.append( "<tr><td colspan=\"2\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"3\"><b>Sub List:</b>&nbsp;" + childName + "</FONT></td><tr>\n");
        Msgmsds.append( "<tr><td bgcolor=\"#000066\" align=\"center\" width=\"15%\"><FONT FACE=\"Arial\" SIZE=\"3\" COLOR=\"#ffffff\"><B>CAS</B></FONT></td>" +
                        "<td bgcolor=\"#000066\" align=\"center\" width=\"85%\"><FONT FACE=\"Arial\" SIZE=\"3\" COLOR=\"#ffffff\"><B>CHEMICAL</B></FONT></td></tr>\n");
        Msgmsds.append(compoundResult);
        if(count > 200) {
          //check if it's the first 200 rows, if not add 200
          String message1 = "";
          String message2 = "";
          if(currentRow == null) {
            //add 200 to currentRow
            currentRow = "200";
          }
          else {
            int i = new Integer(currentRow).intValue();
            i += 200;
            currentRow = new Integer(i).toString();
          }
          if(currentRow.equalsIgnoreCase("200")) {
            //first rows, no "previous" option displayed
            message1 = "&nbsp;";
          }
          else {
            //display "previous" message
            int previousRow = new Integer(currentRow).intValue() - 400;
            message1 = "<a href=\"" + msdsbundle.getString("VIEW_NON_MAT_MSDS") +
                         "SESSION=2&act=compound&parentname=" + parentName + "&childname=" + childName + "&listid=" + listId +
                         "&currentrow=" + previousRow + "\">Previous</a>";

          }
          if(count < new Integer(currentRow).intValue()) {
            //don't display "next" message
            message2 = "&nbsp;";
          }
          else {
            //display "next" message
            message2 = "<a href=\"" + msdsbundle.getString("VIEW_NON_MAT_MSDS") +
                       "SESSION=2&act=compound&parentname=" + parentName + "&childname=" + childName + "&listid=" + listId +
                       "&currentrow=" + currentRow + "\">Next</a>";

          }
          Msgmsds.append("<tr><td>" + message1 + "</td><td>" + message2 + "</td></tr>");

        }
      }
      else if (act.equalsIgnoreCase("compoundpercent")) {
          Msgmsds.append( "<HTML><HEAd>\n");
          Msgmsds.append( "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
          Msgmsds.append( "<meta name=\"GENERATOR\" content=\"Microsoft FrontPage 2.0\">\n");
          Msgmsds.append( "<TITLE>Lists for " + listId + "</title>\n");
          Msgmsds.append( "</head>\n");
          Msgmsds.append( "<body bgcolor=\"#FFFFFF\"><center>\n");
          Msgmsds.append( "<p>\n");
          Msgmsds.append( "<div align=\"center\"><center>\n");
          Msgmsds.append( "<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"100%\" >\n");
          if(childName == null || childName.length() == 0) {
            childName = "N/A";
          }
          Msgmsds.append( "<tr><td colspan=\"3\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"3\"><b>List:</b>&nbsp;" + parentName + "</FONT></td><tr>\n");
          Msgmsds.append( "<tr><td colspan=\"3\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"3\"><b>Sub List:</b>&nbsp;" + childName + "</FONT></td><tr>\n");
          Msgmsds.append( "<tr><td bgcolor=\"#000066\" align=\"center\" width=\"5%\"><FONT FACE=\"Arial\" SIZE=\"3\" COLOR=\"#ffffff\"><B>PERCENT</B></FONT></td>" +
                          "<td bgcolor=\"#000066\" align=\"center\" width=\"15%\"><FONT FACE=\"Arial\" SIZE=\"3\" COLOR=\"#ffffff\"><B>CAS</B></FONT></td>" +
                          "<td bgcolor=\"#000066\" align=\"center\" width=\"80%\"><FONT FACE=\"Arial\" SIZE=\"3\" COLOR=\"#ffffff\"><B>CHEMICAL</B></FONT></td></tr>\n");
          Msgmsds.append(compoundResult);
      }
      Msgmsds.append( "</table>\n" );
      Msgmsds.append( "</center></div><BR><BR><center>\n" );
      Msgmsds.append( "</body>\n" );
      Msgmsds.append( "</html>\n" );

      out.println( Msgmsds );
      titlev=null;
    }

    protected Hashtable buildData(HttpServletRequest requestsidedata,Vector v,String revdatelnk)
    {
        noMatSideView sv;
        String testforme = "";
        String testforme1 = "";
        String NoRecords = "";
        String content1 = "";
        Hashtable dataall = new Hashtable();
		String msdsservlet = msdsbundle.getString("VIEW_NON_MAT_MSDS");

        //String rev_date = requestsidedata.getParameter("date");
        String showspec=requestsidedata.getParameter( "showspec" );
        String id=requestsidedata.getParameter( "id" );
        String facility=requestsidedata.getParameter( "facility" );
        if ( facility == null )
        {
          facility="";
        }

        String catpartno=requestsidedata.getParameter( "catpartno" );
        String act=requestsidedata.getParameter( "act" );
        String client=requestsidedata.getParameter( "cl" );
        String spec=requestsidedata.getParameter( "spec" );

        if (act == null){act="msds";}

        String heal = "";
        String flam = "";
        String react = "";
        String haz = "";
        String radios = "";

        String hmisheal = "";
        String hmisflam = "";
        String hmisreact = "";
        String hmisppe = "";
        String clmsdsno = "";

        int K = 0;

        String testurlis = "";

        if (v == null)
        {
           NoRecords="No Records";
        }
        else if ( v.elementAt( 0 ).toString().equalsIgnoreCase( "No Records" ) )
        {
          NoRecords="No Records";
        }
        else
        {
          if ( v.size() == 0 )
          {
            testurlis="<option value=\"*notav\" selected >Not Available</option>\n";
            radios+=testurlis;
          }
          else if ( v.size() == 1 )
          {
            sv= ( noMatSideView ) v.elementAt( 0 );

            content1=sv.getContent();
            if ( content1.endsWith( ".txt" ) || content1.endsWith( ".html" ) )
            {
              showPrinticon=true;
              printiconurl=content1;
            }

              testforme=sv.getrevdated();
              testforme1=sv.getLatestDate();
              flam=sv.getFlam();
              haz=sv.getHaz();
              heal=sv.getHeal();
              react=sv.getReact();

              hmisheal = sv.gethimshealth();
              hmisflam= sv.gethmisflamma();
              hmisreact=sv.gethmisreacti();
              hmisppe=sv.gethmisppe();
              clmsdsno = sv.getclmsdsno();

              String testdate="";
              testdate=sv.getRadios();
              String selected="";
              {
                selected="selected";
              }

              if ( testdate.equalsIgnoreCase( "notav" ) )
              {
                testurlis="<option value=\"*notav\" " + selected + ">" + testforme1 + "</option>\n";
              }
              else
              {
                String qurl="";
                if ( spec == null ){spec="";}
                if ( facility == null ){facility="";}
                if ( catpartno == null ){catpartno="";}
                if ( showspec == null ){showspec="";}

                qurl=qurl + "&spec=" + spec;
                qurl=qurl + "&facility=" + BothHelpObjs.addSpaceForUrl( facility );
                qurl=qurl + "&catpartno=" + catpartno;
                qurl=qurl + "&showspec=" + showspec;
                qurl=qurl + "&cl=" + client;

                testurlis="<option value=\""+msdsservlet+"SESSION=4&id=" + id + "" + qurl + "&" + "date=" + testforme1 + "*data\" " + selected + ">" + testforme1 + "</option>\n";
              }
              radios+=testurlis;

              if ( revdatelnk == null || revdatelnk.equalsIgnoreCase( "null" ) )
              {
                revdatelnk=sv.getLatestDate();
              }
          }
          else
          {
            for ( int j=0; j < v.size(); j++ )
            {
              sv= ( noMatSideView ) v.elementAt( j );
              content1=sv.getContent();

                testforme=sv.getrevdated();
                testforme1=sv.getLatestDate();
                if ( ( revdatelnk == null || revdatelnk.equalsIgnoreCase( "null" ) ) && ( j == 0 ) )
                {
                  heal=sv.getHeal();
                  haz=sv.getHaz();
                  react=sv.getReact();
                  flam=sv.getFlam();

                  hmisheal = sv.gethimshealth();
                  hmisflam=sv.gethmisflamma();
                  hmisreact=sv.gethmisreacti();
                  hmisppe=sv.gethmisppe();
                  clmsdsno = sv.getclmsdsno();

                  K=K + 1;
                }
                else
                {
                  if ( ! ( K > 0 ) )
                  {
                    if ( testforme.equalsIgnoreCase( testforme1 ) )
                    {
                      heal=sv.getHeal();
                      haz=sv.getHaz();
                      react=sv.getReact();
                      flam=sv.getFlam();

                      hmisheal = sv.gethimshealth();
                      hmisflam=sv.gethmisflamma();
                      hmisreact=sv.gethmisreacti();
                      hmisppe=sv.gethmisppe();
                      clmsdsno = sv.getclmsdsno();
                    }
                  }
                }

                String testdate="";
                String selected="";

                if ( testforme.equalsIgnoreCase( testforme1 ) )
                {
                  selected="selected";
                  if ( content1.endsWith( ".txt" ) || content1.endsWith( ".html" ) )
                  {
                    showPrinticon=true;
                    printiconurl=content1;
                  }
                }
                else
                {
                  selected="";
                }

                if ( testdate.equalsIgnoreCase( "notav" ) )
                {
                  testurlis="<option value=\"*notav\" " + selected + ">" + testforme1 + "</option>\n";
                }
                else
                {
                  String qurl="";
                  if ( spec == null )
                  {
                    spec="";
                  }
                  if ( facility == null )
                  {
                    facility="";
                  }
                  if ( catpartno == null )
                  {
                    catpartno="";
                  }
                  if ( showspec == null )
                  {
                    showspec="";
                  }

                  qurl=qurl + "&spec=" + spec;
                  qurl=qurl + "&facility=" + BothHelpObjs.addSpaceForUrl( facility );
                  qurl=qurl + "&catpartno=" + catpartno;
                  qurl=qurl + "&showspec=" + showspec;
                  qurl=qurl + "&cl=" + client;

                  testurlis="<option value=\""+msdsservlet+"SESSION=4&id=" + id + "" + qurl + "&" + "date=" + testforme1 + "*data\" " + selected + ">" + testforme1 + "</option>\n";
                }
                radios+=testurlis;

                if ( revdatelnk == null || revdatelnk.equalsIgnoreCase( "null" ) )
                {
                  revdatelnk=sv.getLatestDate();
                }
              }
            }
        }

        /*if (radios == null ) {radios = "";}
        if (heal == null ) {heal = "";}
        if (flam == null ) {flam = "";}
        if (react == null ) {react = "";}
        if (haz == null ) {haz = "";}*/

        dataall.put("SHOWRECORDS",NoRecords);
        dataall.put("RADIOS",radios);
        dataall.put("HEALTH",heal);
        dataall.put("FLAMABILITY",flam);
        dataall.put("REACTIVITY",react);
        dataall.put("HAZARD",haz);

        dataall.put("HMIS_HEALTH",hmisheal);
        dataall.put("HMIS_FLAMABILITY",hmisflam);
        dataall.put("HMIS_REACTIVITY",hmisreact);
        dataall.put("HMIS_PPE",hmisppe);
        dataall.put("MSDS_NO",clmsdsno);

        dataall.put("REVDATELINK",revdatelnk);

        return dataall;
      }


      protected Hashtable buildData3( Vector v )
      {
        String NoRecords="";
        String radios2="";
        Hashtable data3=new Hashtable();

        if ( v == null )
        {
          NoRecords="No Records";
        }

        if ( v.elementAt( 0 ).toString().equalsIgnoreCase( "No Records" ) )
        {
          NoRecords="No Records";
        }
        else
        {
          for ( int j=0; j < v.size(); j++ )
          {
            noMatSideView sv= ( noMatSideView ) v.elementAt( j );
            radios2+=sv.getRadios2();
          }
        }

        data3.put( "SHOWRECORDS",NoRecords );
        data3.put( "RADIOS2",radios2 );
        return data3;
    }

    //Get Servlet information
    public String getServletInfo() {
        return "radian.web.servlets.share.msdsSideView Information";
    }
}
