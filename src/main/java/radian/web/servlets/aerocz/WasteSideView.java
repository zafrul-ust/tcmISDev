package radian.web.servlets.aerocz;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.WideView;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 *  This class is used to provide the profile of the waste, its disposition and other information about
 *  the waste.
 *
 *@author     Shaik_Nawaz
 *@created    November 27, 2000
 */

public class WasteSideView
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;

    private String title = "";
    private String vendor_id = "";
    private String company_name = "";
    private String country = "";
    private String address1 = "";
    private String address2 = "";
    private String address3 = "";
    private String city = "";
    private String state = "";
    private String zip = "";
    private String contact_name = "";
    private String phone = "";
    private String email = "";
    private String fax = "";
    private String url = "";
    private String man_option = "";
    private String man_lo_address1 = "";
    private String man_lo_address2 = "";
    private String man_lo_address3 = "";
    private String man_op_city = "";
    private String man_op_State_id = "";
    private String man_op_Postal_code = "";
    private String man_op_Country = "";
    private String epa_idd = "";
    private String vendor_profile_id = "";
    private String description = "";
    private String man_option_desc = "";
    private String man_option_comname = "";
    private Vector data = null;

    public WasteSideView(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public WasteSideView()
    {
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,
        IOException
    {
        doPost(request, response);
    }

    //Process the HTTP Post request
    /**
     *  This method checks the request for what is being asked and calles the corresponding method to
     *  fulfill the request.
     *
     *@param  request               Description of Parameter
     *@param  response              Description of Parameter
     *@exception  ServletException  Description of Exception
     *@exception  IOException       Description of Exception
     */
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,
        IOException
    {
        String waste_profile_id = request.getParameter("id");
        String act = request.getParameter("act");
        String session = request.getParameter("SESSION");

        PrintWriter out = new PrintWriter(response.getOutputStream());
        response.setContentType("text/html");

        try
        {
          if ( session == null )
          {
            buildHTML( out,waste_profile_id,act );
          }
          else if ( session.equalsIgnoreCase( "0" ) )
          {
            buildTitle( out,waste_profile_id,act);
          }
          else if ( session.equalsIgnoreCase( "1" ) )
          {
            buildSideView( out,waste_profile_id );
          }
          else if ( session.equalsIgnoreCase( "2" ) )
          {
            buildWASTE( out,act,waste_profile_id );
          }
        }
        catch ( Exception exw )
        {
          exw.printStackTrace();
        }
        finally
        {
          out.flush();
          out.close();
        }
    }


    /**
     *  This method build the HTML page which has the frames in it which call this servlet again
     *  this method is called when the session parameter is null. or when this servlet is called the first time
     *
     *@param  out  Description of Parameter
     */
    public void buildHTML(PrintWriter out,String waste_profile_id1,String act1)
    {
        if (act1 == null)
        {
            act1 = "waste";
        }
        StringBuffer MsgSB=new StringBuffer();

        MsgSB.append("<html><title>Waste Tracking Information</title>\n");
        MsgSB.append("<frameset rows=\"77,*\">\n");
        MsgSB.append("<frame src=\"/tcmIS/servlet/radian.web.servlets.aerocz.AeroczWasteSideView?SESSION=0&id=" + waste_profile_id1 + "\" name=\"title\" scrolling=\"auto\">\n");
        MsgSB.append("<frameset cols=\"170,*\">\n");
        MsgSB.append("<frame src=\"/tcmIS/servlet/radian.web.servlets.aerocz.AeroczWasteSideView?SESSION=1&id=" + waste_profile_id1 + "\" name=\"opts\" scrolling=\"auto\">\n");
        MsgSB.append("<frame src=\"/tcmIS/servlet/radian.web.servlets.aerocz.AeroczWasteSideView?SESSION=2&act=" + act1 + "&id=" + waste_profile_id1 + "\" name=\"data\">\n");
        MsgSB.append("</frameset>\n");
        MsgSB.append("</frameset>\n");
        MsgSB.append("</html>\n");
        out.println(MsgSB);
        out.close();
    }

    /**
     *  This method builds the left frame on the page. Which has the different options the user can choose from
     *
     *@param  out  Description of Parameter
     */
    public void buildSideView(PrintWriter out,String waste_profile_ids)
    {
        StringBuffer MsgSv=new StringBuffer();
        MsgSv.append("<html><head><title>SideView</title>\n");
        MsgSv.append(    "</head>\n");
        MsgSv.append(    "<body bgcolor=\"#ffffff\" text=000000 link=000066 alink=8E236B vlink=4A766E>\n");
        //========== Begin MouseOvers ==========//
        MsgSv.append(    "        <SCRIPT LANGUAGE = \"JavaScript\">\n");
        MsgSv.append(    "<!--\n");
        MsgSv.append(    "        if (document.images) {\n");
        MsgSv.append(    "            img1on = new Image();   img1on.src = \"/images/buttons/waste/2profile.jpg\";\n");
        MsgSv.append(    "            img1off = new Image();  img1off.src = \"/images/buttons/waste/1profile.jpg\";\n");
        MsgSv.append(    "            img2on = new Image();   img2on.src = \"/images/buttons/waste/2prop.jpg\";\n");
        MsgSv.append(    "            img2off = new Image();  img2off.src = \"/images/buttons/waste/1prop.jpg\";\n");
        MsgSv.append(    "            img3on = new Image();   img3on.src = \"/images/buttons/waste/2disp.jpg\";\n");
        MsgSv.append(    "            img3off = new Image();  img3off.src = \"/images/buttons/waste/1disp.jpg\";\n");
        MsgSv.append(    "         }\n");
        MsgSv.append(    "    function imgOn(imgName) {\n");
        MsgSv.append(    "            if (document.images) {\n");
        MsgSv.append(    "                document[imgName].src = eval(imgName + \"on.src\");\n");
        MsgSv.append(    "            }\n");
        MsgSv.append(    "    }\n");
        MsgSv.append(    "    function imgOff(imgName) {\n");
        MsgSv.append(    "            if (document.images) {\n");
        MsgSv.append(    "                document[imgName].src = eval(imgName + \"off.src\");\n");
        MsgSv.append(    "            }\n");
        MsgSv.append(    "    }\n");
        MsgSv.append(    "// -->\n");
        MsgSv.append(    "</script>\n");
        //=========== End MouseOvers ===========//
        MsgSv.append(    "<font size=1 face=Arial color=000000>");
        MsgSv.append(    "<a href=\"/tcmIS/servlet/radian.web.servlets.aerocz.AeroczWasteSideView?SESSION=2&act=profile&id=" + waste_profile_ids + "\" target=\"data\"");
        MsgSv.append(    " onMouseOver=\"imgOn('img1')\" onMouseOut=\"imgOff('img1')\">");
        MsgSv.append(    "<img name=\"img1\" src=\"/images/buttons/waste/1profile.jpg\" border=0>");
        MsgSv.append(    "</a><BR><BR>\n");

       MsgSv.append(     "<a href=\"/tcmIS/servlet/radian.web.servlets.aerocz.AeroczWasteSideView?SESSION=2&act=prop&id=" + waste_profile_ids + "\" target=\"data\"");
       MsgSv.append(     " onMouseOver=\"imgOn('img2')\" onMouseOut=\"imgOff('img2')\">");
       MsgSv.append(     "<img name=\"img2\" src=\"/images/buttons/waste/1prop.jpg\" border=0>");
       MsgSv.append(     "</a><BR><BR>\n");

       MsgSv.append(     "<a href=\"/tcmIS/servlet/radian.web.servlets.aerocz.AeroczWasteSideView?SESSION=2&act=disp&id=" + waste_profile_ids + "\" target=\"data\"");
       MsgSv.append(     " onMouseOver=\"imgOn('img3')\" onMouseOut=\"imgOff('img3')\">");
       MsgSv.append(     "<img name=\"img3\" src=\"/images/buttons/waste/1disp.jpg\" border=0>");
       MsgSv.append(     "</a><BR><BR>\n");
       MsgSv.append(     "</center> \n");
       MsgSv.append(     "</body></html> \n");

       out.println(MsgSv);
       out.close();
    }

    /**
     *  This method builds the HTML for the top frame which has the title information
     *
     *@param  out  Description of Parameter
     */
    public void buildTitle( PrintWriter out,String waste_profile_idt,String actt )
    {
      actt="title";
      StringBuffer Msgt=new StringBuffer();

      try
      {
        buildData2( WideView.getContentVector( db,waste_profile_idt,actt,"" ),out,actt );
      }
      catch ( Exception et )
      {
        et.printStackTrace();
      }

      Msgt.append( "<html><HEAD>\n" );
      Msgt.append( "<body bgcolor=\"#FFFFFF\">\n" );
      Msgt.append( "<table  border=\"0\" width=\"100%\">\n" );
      Msgt.append( "<tr align=\"center\">\n" );
      Msgt.append( "<td width=\"50%\"><img src=\"/images/logo_s.gif\" border=1 align=\"top\"></td>\n" );
      Msgt.append( "<td width=\"50%\"><font face=Arial size=\"4\" color=\"#000080\"><b>Waste Tracking</b></font></td>\n" );
      Msgt.append( "</tr>\n" );
      Msgt.append( "</table>\n" );
      Msgt.append( "<table width=\"100%\" border=0 cellpadding=2 cellspacing=0 bgcolor=\"#7093DB\">\n" );
      Msgt.append( "<tr align=\"center\">\n" );
      Msgt.append( "<td valign\"CENTER\">\n" );
      Msgt.append( "<table width=\"100%\" border=0 cellspacing=0 cellpadding=0 bgcolor=\"#ffffff\">\n" );
      Msgt.append( "<tr align=\"center\" >\n" );
      Msgt.append( "<td width=\"15%\"><font face=Arial size=\"3\"face=times><B>" + title + "</B></font></td>\n" );
      Msgt.append( "<td width=\"15%\"><font face=Arial size=\"3\"><B>" + vendor_profile_id + "</B></font></td>\n" );
      Msgt.append( "<td width=\"30%\"><font face=Arial size=\"3\" color=\"#000080\">" + company_name + "</font></td>\n" );
      Msgt.append( "<td width=\"40%\"><font face=Arial size=\"3\"><B>" + description + "</B></font></td>\n" );
      Msgt.append( "</tr>\n" );
      Msgt.append( "</table>\n" );
      Msgt.append( "</td>\n" );
      Msgt.append( "</tr></table>\n" );
      Msgt.append( "</font></body></HEAD></html>\n" );

      out.println( Msgt );
    }

    /**
     *  This method builds the HTML which has the actuall information on the right frame which the user wants to see.
     *  it is the important frame.
     *
     *
     *@param  out            Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public void buildWASTE(PrintWriter out,String actw,String waste_profile_idw) throws Exception
    {
        if (actw == null)
        {
            actw = "profile";
        }
        StringBuffer Msgw=new StringBuffer();

        if (actw.equalsIgnoreCase("prop"))
        {
            try
            {
                WideView sv = new WideView();
                data = sv.getContentVector(db, waste_profile_idw, actw, " ");
            }
            catch (Exception e)
            {
                System.out.println("*** Error on open DB for Wasteside view ***");
                e.printStackTrace();
                throw e;
            }
        }
        else
        {
            try
            {
                buildData2(WideView.getContentVector(db,waste_profile_idw,actw, ""), out,actw);
            }
            catch (Exception e)
            {
                return;
            }
        }

        if ( actw.equalsIgnoreCase( "profile" ) )
        {
          if ( url != null && !url.equalsIgnoreCase( "null" ) )
          {
            Msgw.append( "<html><head><META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\"></head></html>\n" );
            out.println( Msgw );
          }
          else
          {
            Msgw.append( "<html><HEAD>\n" );
            Msgw.append( "<body bgcolor=ffffff text=000000><center>\n" );
            Msgw.append( "<img src=\"/images/buttons/waste/tcmprofile.jpg\">\n" );
            Msgw.append( "<BR><BR><BR><h1>Profile Not Available</h1><BR><BR>\n" );
            Msgw.append( "</center></body>\n" );
            Msgw.append( "</HEAD></html>\n" );
            out.println( Msgw );
          }
        }
        else if ( actw.equalsIgnoreCase( "prop" ) )
        {
          Hashtable hD=null;
          Vector data1=null;

          Msgw.append( "<html>\n" );
          Msgw.append( "<HEAD>\n" );
          Msgw.append( "<TITLE> Waste Properties </TITLE>\n" );
          Msgw.append( "</HEAD>\n" );
          Msgw.append( "<body bgcolor=\"#ffffff\" text=\"#000000\"><center>\n" );
          Msgw.append( "<img src=\"/images/buttons/waste/tcmprop.jpg\" alt=\"Properties of the Chemical\">\n" );
          Msgw.append( "</center>\n" );
          Msgw.append( "<BR><TABLE width=\"80%\" border=1 cellpadding=1 >\n" );
          Msgw.append( "<TR>\n" );
          Msgw.append( "<th bgcolor=\"000066\">\n" );
          Msgw.append( "<font face=Arial size=3 color=\"#E6E8FA\"><b>Parameters</b></font>\n" );
          Msgw.append( "</th>\n" );
          Msgw.append( "<th bgcolor=\"000066\">\n" );
          Msgw.append( "<font face=Arial size=3 color=\"#E6E8FA\"><b>Value</b></font>\n" );
          Msgw.append( "</th>\n" );
          Msgw.append( "<th bgcolor=\"000066\">\n" );
          Msgw.append( "<font face=Arial size=3 color=\"#E6E8FA\"><b>Unit</b></font>\n" );
          Msgw.append( "</th></TR>\n" );

          data1= ( Vector ) data.elementAt( 0 );
          //Parameters
          for ( int i=0; i < data1.size(); i++ )
          {
            hD= ( Hashtable ) data1.elementAt( i );
            String parameter= ( hD.get( "PARAMETER" ) == null ? "&nbsp;" : hD.get( "PARAMETER" ).toString() );
            String value= ( hD.get( "VALUE" ) == null ? "&nbsp;" : hD.get( "VALUE" ).toString() );
            String unit= ( hD.get( "UNIT" ) == null ? "&nbsp; " : hD.get( "UNIT" ).toString() );
            Msgw.append( "<tr align=\"center\">\n" );
            Msgw.append( "<td width=\"10%\"><font face=Arial>" + parameter + "</font></td>\n" );
            Msgw.append( "<td width=\"10%\"><font face=Arial>" + value + "</font></td>\n" );
            Msgw.append( "<td width=\"10%\"><font face=Arial>" + unit + "</font></td>\n" );
            Msgw.append( "</tr>\n" );
          }

          Msgw.append( "</TABLE>\n" );
          Msgw.append( "<BR><TABLE width=\"80%\" border=1 cellpadding=1>\n" );
          Msgw.append( "<TR align=\"left\">\n" );
          Msgw.append( "<th bgcolor=\"000066\">\n" );
          Msgw.append( "<font face=Arial size=3 color=\"#E6E8FA\"><b>Shipping</b></font>\n" );
          Msgw.append( "</th></tr>\n" );

          data1= ( Vector ) data.elementAt( 1 );
          //Shipping
          for ( int i=0; i < data1.size(); i++ )
          {
            hD= ( Hashtable ) data1.elementAt( i );
            String shipping= ( hD.get( "SHIPPING" ) == null ? " " : hD.get( "SHIPPING" ).toString() );
            Msgw.append( "<tr align=\"left\">\n" );
            Msgw.append( "<td><font face=Arial>" + shipping + "</font></td>\n" );
            Msgw.append( "</tr>\n" );
          }
          Msgw.append( "</TABLE>\n");

          Msgw.append( "<br>\n" );
          Msgw.append( "<TABLE width=\"80%\" border=1 cellpadding=1>\n" );
          Msgw.append( "<TR align=\"left\">\n" );
          Msgw.append( "<th bgcolor=\"000066\">\n" );
          Msgw.append( "<font face=Arial size=3 color=\"#E6E8FA\"><b>EPA Codes</b></FONT>\n" );
          Msgw.append( "</th></tr>\n" );

          data1= ( Vector ) data.elementAt( 2 );
          //Epacodes
          for ( int i=0; i < data1.size(); i++ )
          {
            hD= ( Hashtable ) data1.elementAt( i );
            String epacodes= ( hD.get( "WASTE_EPA_CODE" ) == null ? " " : hD.get( "WASTE_EPA_CODE" ).toString() );
            Msgw.append( "<tr align=\"left\">\n" );
            Msgw.append( "<td><font face=Arial>" + epacodes + "</font></td>\n" );
            Msgw.append( "</tr>\n" );
          }
          Msgw.append( "</TABLE>\n");

          Msgw.append( "<br>\n" );
          Msgw.append( "<TABLE width=\"80%\" border=1 cellpadding=1>\n" );
          Msgw.append( "<TR align=\"left\">\n" );
          Msgw.append( "<th bgcolor=\"000066\">\n" );
          Msgw.append( "<font face=Arial size=3 color=\"#E6E8FA\"><b>State Waste Codes</b></FONT>\n" );
          Msgw.append( "</th></tr>\n" );

          data1= ( Vector ) data.elementAt( 3 );
          //Statecodes
          for ( int i=0; i < data1.size(); i++ )
          {
            hD= ( Hashtable ) data1.elementAt( i );
            String wastestatecodes= ( hD.get( "WASTE_STATE_CODE" ) == null ? " " : hD.get( "WASTE_STATE_CODE" ).toString() );
            Msgw.append( "<tr align=\"left\">\n" );
            Msgw.append( "<td><font face=Arial>" + wastestatecodes + "</font></td>\n" );
            Msgw.append( "</tr>\n" );
          }
          Msgw.append( "</TABLE>\n");

          Msgw.append( "<br>\n" );
          Msgw.append( "<TABLE width=\"80%\" border=1 cellpadding=1>\n" );
          Msgw.append( "<TR>\n" );
          Msgw.append( "<th bgcolor=\"000066\">\n" );
          Msgw.append( "<font face=Arial size=3 color=\"#E6E8FA\"><b>Constituents</b></FONT>\n" );
          Msgw.append( "</th>\n" );
          Msgw.append( "<th bgcolor=\"000066\">\n" );
          Msgw.append( "<font face=Arial size=3 color=\"#E6E8FA\"><b>Percent</b></FONT>\n" );
          Msgw.append( "</th></TR>\n");

          data1= ( Vector ) data.elementAt( 4 );
          //Constituents
          for ( int i=0; i < data1.size(); i++ )
          {
            hD= ( Hashtable ) data1.elementAt( i );
            String constituent= ( hD.get( "CONSTITUENT" ) == null ? " " : hD.get( "CONSTITUENT" ).toString() );
            String percent= ( hD.get( "VALUE" ) == null ? " " : hD.get( "VALUE" ).toString() );
            Msgw.append( "<tr align=\"center\">\n" );
            Msgw.append( "<td width=\"20%\"><font face=Arial>" + constituent + "</font></td>\n" );
            Msgw.append( "<td width=\"20%\"><font face=Arial>" + percent + "</font></td>\n" );
            Msgw.append( "</tr>\n" );
          }
          Msgw.append( "</TABLE>\n" );
          Msgw.append( "</BODY></HTML>\n");
          out.println( Msgw );
        }
        else if ( actw.equalsIgnoreCase( "disp" ) )
        {
          Msgw.append( "<html>\n" );
          Msgw.append( "<body bgcolor=ffffff text=000000>\n" );
          Msgw.append( "<font face=Arial>\n" );
          Msgw.append( "<center><img src=\"/images/buttons/waste/tcmdisp.jpg\">\n" );
          Msgw.append( "<hr>\n" );
          Msgw.append( "<table border=0 cellpadding=30 cellspacing=2 width=75% align=center>\n" );
          Msgw.append( "<tr>\n" );
          Msgw.append( "<td valign=\"top\"><center>\n" );
          Msgw.append( "<!---------------------------------------------------------------->\n" );
          Msgw.append( "<table bgcolor=000066 border=0 cellpadding=1 cellspacing=0>\n" );
          Msgw.append( "<tr>\n" );
          Msgw.append( " <td>\n" );
          Msgw.append( "<font face=Arial size=2 color=\"#E6E8FA\"><center><B>&nbsp; Waste Vendor &nbsp;</B></font>\n" );
          Msgw.append( "</td>\n" );
          Msgw.append( "</tr>\n" );
          Msgw.append( "<tr>\n" );
          Msgw.append( "<td>\n" );
          Msgw.append( "<table border=0 bgcolor=\"#E6E8FA\" cellpadding=0 cellspacing=0 width=100%>\n" );
          Msgw.append( "<tr>\n" );
          Msgw.append( "<td><center>\n" );
          Msgw.append( "<table border=0 cellpadding=1 cellspacing=1 bgcolor=\"#cfcfcf\">\n" );
          Msgw.append( "<tr>\n" );
          Msgw.append( "<td valign=\"top\"><font face=Arial color=000066 size=2><B>Address: </B></td>\n" );
          Msgw.append( "<td><font face=Arial size=2>" + company_name + "<BR>" + address1 + "\n");

          if ( ( address2.length() != 0 ) && ( address2 != " " ) )
          {
            Msgw.append( "<BR>" + address2 + "\n");

          }
          if ( ( address3.length() != 0 ) && ( address3 != " " ) )
          {
            Msgw.append( "<BR>" + address3 + "\n");

          }

          Msgw.append( "" + city + ", " + state + "&nbsp;&nbsp;" + zip + " <BR>" + country + " <BR>\n" );
          Msgw.append( "</td>\n" );
          Msgw.append( "</tr>\n" );
          Msgw.append( "<tr>\n" );
          Msgw.append( " <td valign=\"top\"><font face=Arial color=000066 size=2><B>EPA Code: </B>\n" );
          Msgw.append( "</td>\n" );
          Msgw.append( "<td>\n" );
          Msgw.append( "<font face=Arial>\n" );
          Msgw.append( "<font face=Arial size=2>" + epa_idd + "\n" );
          Msgw.append( "</td>\n" );
          Msgw.append( "</tr>\n" );
          Msgw.append( "<tr>\n" );
          Msgw.append( " <td valign=\"top\"><font face=Arial color=000066 size=2><B>Contact: </B>\n" );
          Msgw.append( "</td>\n" );
          Msgw.append( "<td>\n" );
          Msgw.append( "<font face=Arial>\n" );
          Msgw.append( "<font face=Arial size=2>" + contact_name + "<BR>" + phone + "<BR>" + fax + "<BR>" + email + "\n" );
          Msgw.append( "</td>\n" );
          Msgw.append( "</tr>\n" );
          Msgw.append( " </table>\n" );
          Msgw.append( "</td>\n" );
          Msgw.append( "</tr>\n" );
          Msgw.append( "</table>\n" );
          Msgw.append( "</td>\n" );
          Msgw.append( "</tr>\n" );
          Msgw.append( "</table>\n" );
          Msgw.append( "</td>\n" );
          Msgw.append( "<!---------------------------------------------------------------->\n" );
          Msgw.append( "<td valign=\"top\"><center>\n" );
          Msgw.append( "<!---------------------------------------------------------------->\n" );
          Msgw.append( "<table bgcolor=000066 border=0 cellpadding=1 cellspacing=0>\n" );
          Msgw.append( "<tr><td>\n" );
          Msgw.append( "<font face=Arial size=2 color=\"#E6E8FA\"><center><B>&nbsp; Management Option &nbsp;</B></font>\n" );
          Msgw.append( "</td></tr>\n" );
          Msgw.append( "<tr><td>\n" );
          Msgw.append( "<table border=0 bgcolor=\"#E6E8FA\" cellpadding=0 cellspacing=0 width=100%>\n" );
          Msgw.append( "<tr><td><center>\n" );
          Msgw.append( "<table border=0 cellpadding=1 cellspacing=1 bgcolor=\"#cfcfcf\" width=100%>\n" );

          Msgw.append( "<tr><td valign=\"top\"><font face=Arial size=2 color=000066><B>Option: </B></td><td></B><font face=Arial size=2>" + man_option + " - " + man_option_desc + "<BR></td></tr>\n" );
          Msgw.append( "<tr><td valign=\"top\"><font face=Arial color=000066 size=2><B>Address: </B></td><td><font face=Arial size=2>" + man_option_comname + "\n" );
          Msgw.append( "<BR>" + man_lo_address1 + "\n" );

          if ( ( man_lo_address2.length() != 0 ) && ( man_lo_address2 != " " ) )
          {
            Msgw.append( "<BR>" + man_lo_address2 + "\n");
          }

          if ( ( man_lo_address3.length() != 0 ) && ( man_lo_address3 != " " ) )
          {
            Msgw.append( "<BR>" + man_lo_address3 + "\n");
          }

          if ( man_op_city.length() != 0 )
          {
            Msgw.append( "" + man_op_city + ", " + man_op_State_id + "&nbsp;&nbsp;" + man_op_Postal_code + " <BR>\n" );
          }

          Msgw.append( "" + man_op_Country + "</td></tr>\n" );
          Msgw.append( "<tr><td valign=\"top\"><font face=Arial color=000066 size=2><B>EPA Code: </B></td><td><font face=Arial size=2>" + "\n" );
          Msgw.append( "" + epa_idd + /*mgt_op_epa_id + */ "</td></tr>\n" );
          Msgw.append( "<tr><td valign=\"top\"><font face=Arial color=000066 size=2><B>Location: </B></td><td><font face=Arial size=2>" + "\n" );
          Msgw.append( "" + man_option_comname + "</td></tr></table>\n" );
          Msgw.append( "</td></tr>\n" );
          Msgw.append( "</table>\n" );
          Msgw.append( "</td></tr>\n" );
          Msgw.append( "</table>\n" );
          Msgw.append( "<!---------------------------------------------------------------->\n" );
          Msgw.append( "</td></tr>\n" );
          Msgw.append( "<tr><td valign=\"top\"><center>\n" );
          Msgw.append( "<!---------------------------------------------------------------->\n" );
          Msgw.append( "<table bgcolor=000066 border=0 cellpadding=1 cellspacing=0>\n" );
          Msgw.append( "<tr><td>\n" );
          Msgw.append( "<font face=Arial size=2 color=\"#E6E8FA\"><center><B>&nbsp; Transporter 1 &nbsp;</B></font>\n" );
          Msgw.append( "</td></tr>\n" );
          Msgw.append( "<tr><td>\n" );
          Msgw.append( "<table border=0 bgcolor=\"#E6E8FA\" cellpadding=0 cellspacing=0 width=100%>\n" );
          Msgw.append( "<tr><td><center>\n" );
          Msgw.append( "<table border=0 cellpadding=1 cellspacing=1 bgcolor=\"#cfcfcf\">\n" );
          Msgw.append( "<tr>\n" );
          Msgw.append( "<td valign=\"top\"><font face=Arial color=000066 size=2><B>Address: </B></td>\n" );
          Msgw.append( "<td><font face=Arial size=2>" + company_name + "<BR>" + address1 + "\n");
          if ( ( address2.length() != 0 ) && ( address2 != " " ) )
          {
            Msgw.append( "<BR>" + address2 + "\n");
          }
          if ( ( address3.length() != 0 ) && ( address3 != " " ) )
          {
            Msgw.append( "<BR>" + address3 + "\n");
          }
          Msgw.append( "" + city + ", " + state + "&nbsp;&nbsp;" + zip + " <BR>" + country + " <BR>\n" );
          Msgw.append( "</td>\n" );
          Msgw.append( "</tr>\n" );
          Msgw.append( "<tr><td valign=\"top\"><font face=Arial color=000066 size=2><B>EPA Code: </B></td><td><font face=Arial>\n" );
          Msgw.append( "<font face=Arial size=2>" + epa_idd + "</td></tr>\n" );
          Msgw.append( "<tr><td valign=\"top\"><font face=Arial color=000066 size=2><B>Contact: </B></td><td><font face=Arial>\n" );
          Msgw.append( "<font face=Arial size=2>" + contact_name + "<BR>" + phone + "<BR>" + fax + "<BR>" + email + "</td></tr>\n" );
          Msgw.append( "</table>\n" );
          Msgw.append( "</td></tr>\n" );
          Msgw.append( "</table>\n" );
          Msgw.append( "</td></tr>\n" );
          Msgw.append( "</table>\n" );
          Msgw.append( "<!---------------------------------------------------------------->\n" );

          boolean transporter2=false;
          if ( transporter2 )
          {
            Msgw.append( "</td><td valign=\"top\"><center>\n" );
            Msgw.append( "<!---------------------------------------------------------------->\n" );
            Msgw.append( "<table bgcolor=000066 border=0 cellpadding=1 cellspacing=0>\n" );
            Msgw.append( "<tr><td>\n" );
            Msgw.append( "<font face=Arial size=2 color=\"#E6E8FA\"><center><B>&nbsp; Transporter 2 &nbsp;</B></font>\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "<tr><td>\n" );
            Msgw.append( "<table border=0 bgcolor=\"#E6E8FA\" cellpadding=0 cellspacing=0 width=100%>\n" );
            Msgw.append( "<tr><td><center>\n" );
            Msgw.append( "<table border=0 cellpadding=1 cellspacing=1 bgcolor=\"#cfcfcf\">\n" );
            Msgw.append( "<tr>\n" );
            Msgw.append( "<td valign=\"top\"><font face=Arial color=000066 size=2><B>Address: </B></td>\n" );
            Msgw.append( "<td><font face=Arial size=2>" + company_name + "<BR>" + address1 + "\n");
            if ( ( address2.length() != 0 ) && ( address2 != " " ) )
            {
              Msgw.append( "<BR>" + address2 + "\n");

            }
            if ( ( address3.length() != 0 ) && ( address3 != " " ) )
            {
              Msgw.append( "<BR>" + address3 + "\n");

            }
            Msgw.append( "" + city + ", " + state + "&nbsp;&nbsp;" + zip + " <BR>" + country + " <BR>\n" );
            Msgw.append( "</td>\n" );
            Msgw.append( "</tr>\n" );
            Msgw.append( "</tr><tr><td valign=\"top\"><font face=Arial color=000066 size=2><B>EPA Code: </B></td><td><font face=Arial>\n" );
            Msgw.append( "<font face=Arial size=2>" + epa_idd + /*trans2_epa_id +*/ "</td></tr>\n" );
            Msgw.append( "<tr><td valign=\"top\"><font face=Arial color=000066 size=2><B>Contact: </B></td><td><font face=Arial>\n" );
            Msgw.append( "<font face=Arial size=2>" + contact_name + "<BR>" + phone + "<BR>" + fax + "<BR>" + email + "</td></tr>\n" );
            Msgw.append( "</table>\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "</table>\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "</table>\n" );
            Msgw.append( "<!---------------------------------------------------------------->\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "<tr><td valign=\"top\"><center>\n" );
            Msgw.append( "<!---------------------------------------------------------------->\n" );
            Msgw.append( "<table bgcolor=000066 border=0 cellpadding=1 cellspacing=0>\n" );
            Msgw.append( "<tr><td>\n" );
            Msgw.append( "<font face=Arial size=2 color=\"#E6E8FA\"><center><B>&nbsp; Manifest Facility &nbsp;</B></font>\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "<tr><td>\n" );
            Msgw.append( "<table border=0 bgcolor=\"#E6E8FA\" cellpadding=0 cellspacing=0 width=100%>\n" );
            Msgw.append( "<tr><td><center>\n" );
            Msgw.append( "<table border=0 cellpadding=1 cellspacing=1 bgcolor=\"#cfcfcf\">\n" );
            Msgw.append( "<tr>\n" );
            Msgw.append( "<td valign=\"top\"><font face=Arial color=000066 size=2><B>Address: </B></td><td><font face=Arial size=2>" + company_name + "<BR>" + address1 + "\n");
            if ( ( address2.length() != 0 ) && ( address2 != " " ) )
            {
              Msgw.append( "<BR>" + address2 + "\n");

            }
            if ( ( address3.length() != 0 ) && ( address3 != " " ) )
            {
              Msgw.append( "<BR>" + address3 + "\n");

            }
            Msgw.append( "" + city + ", " + state + "&nbsp;&nbsp;" + zip + " <BR>" + country + " <BR>\n" );
            Msgw.append( "</td>\n" );
            Msgw.append( "</tr>\n" );
            Msgw.append( "<tr><td valign=\"top\"><font face=Arial color=000066 size=2><B>Contact: </B></td><td><font face=Arial>\n" );
            Msgw.append( "<font face=Arial size=2>" + contact_name + "<BR>" + phone + "<BR>" + fax + "<BR>" + email + "</td></tr></table>\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "</table>\n" );
            Msgw.append( "\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "</table>\n" );
            Msgw.append( "<!---------------------------------------------------------------->\n" );
            Msgw.append( "\n" );
            Msgw.append( "</td><td valign=\"top\">&nbsp;</td></tr>\n" );
            Msgw.append( "</table>\n" );
            Msgw.append( "</center>\n" );
            Msgw.append( "</body></html>\n" );
          }
          else
          {
            Msgw.append( "</td><td valign=\"top\"><center>\n" );
            Msgw.append( "<!---------------------------------------------------------------->\n" );
            Msgw.append( "<table bgcolor=000066 border=0 cellpadding=1 cellspacing=0>\n" );
            Msgw.append( "<tr><td>\n" );
            Msgw.append( "<font face=Arial size=2 color=\"#E6E8FA\"><center><B>&nbsp;  Manifest Facility &nbsp;</B></font>\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "<tr><td>\n" );
            Msgw.append( "<table border=0 bgcolor=\"#E6E8FA\" cellpadding=0 cellspacing=0 width=100%>\n" );
            Msgw.append( "<tr><td><center>\n" );
            Msgw.append( "<table border=0 cellpadding=1 cellspacing=1 bgcolor=\"#cfcfcf\">\n" );
            Msgw.append( "<tr>\n" );
            Msgw.append( "<td valign=\"top\"><font face=Arial color=000066 size=2><B>Address: </B></td><td><font face=Arial size=2>" + company_name + "<BR>" + address1 + "\n");
            if ( ( address2.length() != 0 ) && ( address2 != " " ) )
            {
              Msgw.append( "<BR>" + address2 + "\n");

            }
            if ( ( address3.length() != 0 ) && ( address3 != " " ) )
            {
              Msgw.append( "<BR>" + address3 + "\n");

            }
            Msgw.append( "" + city + ", " + state + "&nbsp;&nbsp;" + zip + " <BR>" + country + " <BR>\n" );
            Msgw.append( "</td>\n" );
            Msgw.append( "</tr>\n" );
            Msgw.append( "<tr><td valign=\"top\"><font face=Arial color=000066 size=2><B>EPA Code: </B></td><td><font face=Arial>\n" );
            Msgw.append( "<font face=Arial size=2>" + epa_idd + "</td></tr>\n" );
            Msgw.append( "<tr><td valign=\"top\"><font face=Arial color=000066 size=2><B>Contact: </B></td><td><font face=Arial>\n" );
            Msgw.append( "<font face=Arial size=2>" + contact_name + "<BR>" + phone + "<BR>" + fax + "<BR>" + email + "</td></tr>\n" );
            Msgw.append( "</table>\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "</table>\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "</table>\n" );
            Msgw.append( "<!---------------------------------------------------------------->\n" );
            Msgw.append( "</td></tr>\n" );
            Msgw.append( "</table>\n" );
            Msgw.append( "</center>\n" );
            Msgw.append( "</font></body></html>\n" );
          }
          out.println( Msgw );
        }
        out.close();
      }

      /**
       *  Description of the Method
       *
       *@param  v    Description of Parameter
       *@param  out  Description of Parameter
       */
      protected void buildData( Vector v,PrintWriter out )
      {
        WideView sv;
        int count=0;

        if ( v.size() == 1 )
        {
          sv= ( WideView ) v.elementAt( 0 );
          //epa_code=sv.getEpaWasteCode();
          //stw_state=sv.getStateWasteState();
          //stw_code=sv.getStateWasteCode();
        }
        else
        {
          for ( int j=0; j <= v.size(); j++ )
          {
            sv= ( WideView ) v.elementAt( j );
            //epa_code=sv.getEpaWasteCode();
            //stw_state=sv.getStateWasteState();
            //stw_code=sv.getStateWasteCode();
          }
        }
      }


      /**
       *  Description of the Method
       *
       *@param  v    Description of Parameter
       *@param  out  Description of Parameter
       */
      protected void buildData2( Vector v,PrintWriter out,String actbd2 )
      {

        for ( int j=0; j < v.size(); j++ )
        {
          WideView sv= ( WideView ) v.elementAt( j );
          if ( actbd2.equalsIgnoreCase( "profile" ) )
          {
            url=sv.getURL();
          }
          else if ( actbd2.equalsIgnoreCase( "prop" ) )
          {
            //epa_id=sv.getEpaId();
            //prop_ship_name=sv.getProperShippingName();
            //ship_haz_class=sv.getShippingHazardClass();
            //ship_id_num=sv.getShippingIdNumber();
            //packing_group=sv.getPackingGroup();
          }
          else if ( actbd2.equalsIgnoreCase( "disp" ) )
          {
            if ( sv.getContactName() != null )
            {
              contact_name=sv.getContactName();
            }
            else
            {
              contact_name="<font color=red face=arial size=2>Not Available</font>";
            }
            if ( sv.getPhone() != null )
            {
              phone=sv.getPhone();
            }
            if ( sv.getFax() != null )
            {
              fax=sv.getFax();
            }
            if ( sv.getEmail() != null )
            {
              email=sv.getEmail();
            }
            if ( sv.getCompanyName() != null )
            {
              company_name=sv.getCompanyName();
            }
            if ( sv.getAddress1() != null )
            {
              address1=sv.getAddress1();
            }
            if ( sv.getAddress2() != null )
            {
              address2=sv.getAddress1();
            }
            if ( sv.getAddress3() != null )
            {
              address3=sv.getAddress1();
            }
            city=sv.getCity();
            state=sv.getState();
            zip=sv.getZip();
            country=sv.getCountry();
            vendor_id=sv.getVendorId();
            //state_id=sv.getStateId();
            man_option=sv.getManagementOption();
            //man_location=sv.getManagementOptionLocation();
            man_option_desc=sv.getman_option_desc();
            man_option_comname=sv.getman_option_comname();
            man_lo_address1=sv.getman_op_Address1();
            man_lo_address2=sv.getman_op_Address2();
            man_lo_address3=sv.getman_op_Address3();
            man_op_city=sv.getman_op_City();
            man_op_State_id=sv.getman_op_State();
            man_op_Postal_code=sv.getman_op_Zip();
            man_op_Country=sv.getman_op_Country();
            epa_idd=sv.getEpaId();
            //mgt_op_epa_id=sv.getMGMT_OP_EPA_ID();
            //trans2_epa_id=sv.getTRANS_2_EPA_ID();
          }
          else if ( actbd2.equalsIgnoreCase( "title" ) )
          {
            title=sv.getTitle();
            vendor_profile_id=sv.getvendor_profile_id();
            description=sv.getdescription();
            company_name=sv.getCompanyName();

          }
        }
      }
    }