package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.Facility;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.servlets.WebReportUsage;
import radian.web.servlets.ray.RayServerResourceBundle;
import radian.web.servlets.swa.SWAServerResourceBundle;

public class UsageRptWebPage extends HttpServlet implements SingleThreadModel
{
    // Initializing global Variables
    ServerResourceBundle bundle=null;
    TcmISDBModel db = null;
    String fac = "";
    public UsageRptWebPage(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public UsageRptWebPage()
    {

    }

	public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
	{
        String client = new String("");
        String Session = new String("");
        StringBuffer Msg = new StringBuffer();
        String FFacility = new String("");
        String FWorkArea = new String("");
        String FBeginY = new String("");
        String FBeginM = new String("");
        String FEndY = new String("");
        String FEndM = new String("");
        String Wdesc = new String("");

        String WWhomeDirectory = bundle.getString("WEBS_HOME_WWWS");

        String [] calmonths = {"Jan","Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov","Dec"};
        String [] calyears = {"1999","2000", "2001", "2002", "2003", "2004", "2005"};

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        client = bundle.getString("DB_CLIENT");

        try{Session= request.getParameter("Session").toString();}catch (Exception e1){Session = "0";}

        try{FFacility= request.getParameter("FFacility1").toString();}catch (Exception e1){}

        fac = FFacility;
        //System.out.println("*** TEST ***");

        if(Session.equalsIgnoreCase("1"))
        {
         try{FFacility= request.getParameter("fac").toString();}catch (Exception e1){}
         try{FWorkArea= request.getParameter("WorkArea").toString();}catch (Exception e1){}
         try{FBeginY= request.getParameter("BeginY").toString();}catch (Exception e1){}
         try{FBeginM= request.getParameter("BeginM").toString();}catch (Exception e1){}
         try{FEndY= request.getParameter("EndY").toString();}catch (Exception e1){}
         try{FEndM= request.getParameter("EndM").toString();}catch (Exception e1){}
         try{Wdesc= request.getParameter(""+FWorkArea+"").toString();}catch (Exception e1){}

         //System.out.println("These are the Parameters"+client+" "+FFacility+" "+FWorkArea+" "+FBeginY+" "+FBeginM+" "+FEndY+" "+FEndM+"Desc "+Wdesc);
        }

        Hashtable data = null;
        Vector dataV = new Vector();

        if (!(Session.equalsIgnoreCase("1")))
        {
          try
          {
              Facility fac = new Facility(db);
              dataV = fac.getAllFacXRef2();
          }
          catch (Exception e)
          {
              e.printStackTrace();
          }
        }

        if(Session.equalsIgnoreCase("1"))
        {
         //System.out.println("*** Got HERE ***");
          if (FFacility.equalsIgnoreCase("All"))
          {
          buildERROR(out,"Facility must be selected");
          return;
          }
          if (FWorkArea.equalsIgnoreCase("NO"))
          {
          buildERROR(out,"WorkArea must be selected");
          return;
          }

         buildURL(out,client,FFacility, FWorkArea, FBeginY, FBeginM, FEndY, FEndM, Wdesc);
        }
        else
        {
            Msg.append("<HTML><HEAD><TITLE>Materials Used</TITLE>\n");
            Msg.append(printJavaScripts(out, client));
            Msg.append("<BODY BGCOLOR=\"#FFFFFF\"><FONT FACE=\"Arial\"> \n");

            String loc = "";

            if (client.equalsIgnoreCase("Raytheon"))
            {
            loc = ""+WWhomeDirectory+"/tcmIS/servlet/radian.web.servlets.ray.RayUsageRptWebPage";
            }
            else if (client.equalsIgnoreCase("Southwest Airlines"))
            {
            loc = ""+WWhomeDirectory+"/tcmIS/servlet/radian.web.servlets.swa.SWAUsageRptWebPage";
            }
            else if (client.equalsIgnoreCase("Lockheed"))
            {
            loc = ""+WWhomeDirectory+"/tcmIS/servlet/radian.web.servlets.lmco.LMCOUsageRptWebPage";
            }
            else if (client.equalsIgnoreCase("DRS"))
            {
            loc = ""+WWhomeDirectory+"/tcmIS/servlet/radian.web.servlets.drs.DRSUsageRptWebPage";
            }

            Msg.append("<FORM METHOD=\"POST\" NAME=\"MainForm\" TARGET=\"bottom\" ACTION=\""+loc+"?Session=1\">\n");
            Msg.append("<INPUT  TYPE=\"hidden\" VALUE=\"1\" NAME=\"Session\">\n");
            Msg.append("<CENTER><B><FONT SIZE=\"4\" COLOR=\"#000080\">Materials Used</FONT></B></CENTER>\n");
            Msg.append("<TABLE  BORDER=\"0\" WIDTH=\"100%\">\n");
            Msg.append("<TR>\n");

            Msg.append("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" ><IMG SRC=\""+WWhomeDirectory+"/images/logo_s.gif\" BORDER=1 ALIGN=\"top\">\n");
            Msg.append("</TD>\n");

            Msg.append("<TD WIDTH=\"40%\" ALIGN=\"LEFT\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
            Msg.append("<INPUT TYPE=\"submit\" VALUE=\"Search\" NAME=\"B1\">\n");
            Msg.append("</TD>\n");

            Msg.append("<TD WIDTH=\"20%\" ALIGN=\"CENTER\"><A HREF=\""+WWhomeDirectory+"/tcmIS/reports/ray/usage1/help.html\" TARGET=\"bottom\">\n");
            Msg.append("<IMG NAME=\"img1\" ALIGN=\"CENTER\" SRC=\""+WWhomeDirectory+"/images/help_up.gif\" BORDER=0>\n");
            Msg.append("</A>\n");
            Msg.append("</TD>\n");

            Msg.append("<TD WIDTH=\"20%\" ALIGN=\"CENTER\">\n");
            Msg.append("</TD>\n");
            Msg.append("</TR>\n");

            Msg.append("<TR>\n");

            Msg.append("<TD WIDTH=\"10%\" ALIGN=\"right\"><FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000080\"><B>Facility:</B></FONT></TD>\n");
            Msg.append("<TD WIDTH=\"40%\">\n");

            Msg.append("<SELECT NAME=\"fac\" ONCHANGE=\"RefreshPage(this)\" SIZE=\"1\">\n");
            Msg.append("<OPTION "+(fac.equalsIgnoreCase("All")?"selected":"")+" value=\"All\">Select Facility</OPTION>\n");

            for (int i=0; i<dataV.size(); i++)
            {
                data = (Hashtable)dataV.elementAt(i);
                Enumeration E;
                for (E=data.keys(); E.hasMoreElements();)
                {
                    String key = (String) E.nextElement();
                    Msg.append("<OPTION "+(fac.equalsIgnoreCase(data.get(key).toString())?"selected":"")+" value=\""+data.get(key).toString()+"\">"+key+"</OPTION>\n");
                }
            }

            Msg.append("</SELECT>\n");
            Msg.append("</TD>\n");

            Msg.append("<TD WIDTH=\"20%\" ALIGN=\"right\"><FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Begin:</B></FONT></TD>\n");
            Msg.append("<TD WIDTH=\"20%\" ALIGN=\"left\"><SELECT NAME=\"BeginM\" SIZE=\"1\">\n");
            Msg.append("<OPTION VALUE=0>Jan</OPTION>\n");
            Msg.append("<OPTION VALUE=1>Feb</OPTION>\n");
            Msg.append("<OPTION VALUE=2>Mar</OPTION>\n");
            Msg.append("<OPTION VALUE=3>Apr</OPTION>\n");
            Msg.append("<OPTION VALUE=4 SELECTED>May</OPTION>\n");
            Msg.append("<OPTION VALUE=5 >June</OPTION>\n");
            Msg.append("<OPTION VALUE=6>July</OPTION>\n");
            Msg.append("<OPTION VALUE=7>Aug</OPTION>\n");
            Msg.append("<OPTION VALUE=8>Sept</OPTION>\n");
            Msg.append("<OPTION VALUE=9>Oct</OPTION>\n");
            Msg.append("<OPTION VALUE=10>Nov</OPTION>\n");
            Msg.append("<OPTION VALUE=11>Dec</OPTION>\n");
            Msg.append("</SELECT>\n");
            Msg.append("<SELECT NAME=\"BeginY\" SIZE=\"1\">\n");
            Msg.append("<OPTION VALUE=1999 SELECTED>1999</OPTION>\n");
            Msg.append("<OPTION VALUE=2000>2000</OPTION>\n");
            Msg.append("<OPTION VALUE=2001>2001</OPTION>\n");
            Msg.append("<OPTION VALUE=2002>2002</OPTION>\n");
            Msg.append("<OPTION VALUE=2003>2003</OPTION>\n");
            Msg.append("<OPTION VALUE=2004>2004</OPTION>\n");
            Msg.append("<OPTION VALUE=2005>2005</OPTION>\n");
            Msg.append("</SELECT></TD>\n");
            Msg.append("</TR>\n");

            Msg.append("<TR>\n");
            Msg.append("<TD WIDTH=\"10%\" ALIGN=\"right\"><FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000080\"><B>Work Area:</B></FONT></TD>\n");
            Msg.append("<TD WIDTH=\"40%\" ALIGN=\"left\">\n");
            Msg.append("<SELECT NAME=\"WorkArea\">\n");

            StringBuffer Msg1 = new StringBuffer();

            if(Session.equalsIgnoreCase("2"))
            {
            Vector dataV1 = new Vector();
            String query = "select APPLICATION,APPLICATION_DESC from fac_loc_app where FACILITY_ID = '"+FFacility+"'";

            Msg.append("<OPTION value=\"NO\">Select WorkArea</OPTION>\n");

            DBResultSet dbrs = null;
            ResultSet rs = null;
            int test = 1;

            try
            {
                dbrs = db.doQuery(query);
                rs=dbrs.getResultSet();

                 while (rs.next())
                 {
                   Msg.append("<OPTION  VALUE=\""+rs.getString("APPLICATION")+"\">"+rs.getString("APPLICATION")+"-"+rs.getString("APPLICATION_DESC")+"</OPTION>\n");

                   Msg1.append("<INPUT  TYPE=\"hidden\" VALUE=\""+rs.getString("APPLICATION_DESC")+"\" NAME=\""+rs.getString("APPLICATION")+"\">\n");
                   test +=1;
                 }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            Msg.append("</SELECT>\n");
            Msg.append("</TD>\n");
            }

            else
            {
             Msg.append("<OPTION SELECTED VALUE=\"NO\">Select Facility First</OPTION>\n");
             Msg.append(" </SELECT>\n");
             Msg.append("</TD>\n");
            }

            Calendar cal = Calendar.getInstance();
            String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
            int am_pm = cal.get(Calendar.AM_PM);
            int month1 = cal.get(Calendar.MONTH)+1;
            int year1 = cal.get(Calendar.YEAR);
            String time = ""+year1;
            //System.out.println("\n The Date today is "+ cdate +"   ..."+ month1 +""+ year1);

            Msg.append("<TD WIDTH=\"20%\" ALIGN=\"right\"><FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>End:</B></FONT></TD>\n");
            Msg.append("<TD WIDTH=\"20%\" ALIGN=\"\"><SELECT NAME=\"EndM\" SIZE=\"1\">\n");
            for (int i=0; i<calmonths.length; i++)
            {
             if (i+1 == month1)
             {
             Msg.append("<OPTION VALUE="+(i)+" SELECTED>"+calmonths[i]+"</OPTION>\n");
             }
             else
             {
             Msg.append("<OPTION VALUE="+(i)+">"+calmonths[i]+"</OPTION>\n");
             }
            }

            Msg.append("</SELECT>\n");

            Msg.append("<SELECT NAME=\"EndY\" SIZE=\"1\">\n");
            for (int i=0; i<calyears.length; i++)
            {
             if (calyears[i].toString().equalsIgnoreCase(time.trim()))
             {
             Msg.append("<OPTION VALUE="+calyears[i]+" SELECTED>"+calyears[i]+"</OPTION>\n");
             }
             else
             {
             Msg.append("<OPTION VALUE="+calyears[i]+">"+calyears[i]+"</OPTION>\n");
             }
            }

            Msg.append("</SELECT></TD>\n");
            Msg.append("</TR>\n");
            Msg.append("</TABLE>\n");
            Msg.append(Msg1);
            Msg.append("</FORM>\n");
            Msg.append("</FONT> </BODY>\n");
            Msg.append("</HTML>\n");

            out.println(Msg);
        }
		out.close();
    }
     public void buildURL(PrintWriter out,String client1, String Rfacility, String RworkArea, String BeginY, String BeginM, String EndY, String EndM, String descW)
    {
        StringBuffer MsgResult = new StringBuffer();

        //System.out.println("These are the Parameters in build URL"+client1+" "+Rfacility+" "+RworkArea+" "+BeginY+" "+BeginM+" "+EndY+" "+EndM);

        MsgResult.append("<HTML><HEAD><TITLE>Materials Used</TITLE>\n");
        MsgResult.append(printJavaScripts2(out));
        MsgResult.append("<BODY BGCOLOR=\"#FFFFFF\"><FONT FACE=\"Arial\">\n");

        MsgResult.append("<TABLE  BORDER=\"0\" WIDTH=\"100%\">\n");
        MsgResult.append("<TR>\n");
        MsgResult.append("<TD WIDTH=\"80%\"><B>Materials Used : </B>\n");

        MsgResult.append("</TD><TD WIDTH=\"20%\"><B>Date: </B>\n");

        MsgResult.append("<SCRIPT language=\"javascript\">\n");
        MsgResult.append("<!-- \n");
        MsgResult.append("document.write(writeDateLong(3));\n");
        MsgResult.append("//-->     \n");
        MsgResult.append("</SCRIPT>\n");

        MsgResult.append("</B></TD>\n");

        MsgResult.append("</TR><TR>\n");
        MsgResult.append("<TD WIDTH=\"80%\"><B>Facility :</B>"+Rfacility+", <B>Workarea: </B>"+RworkArea+"-"+descW+", <B>From: </B>\n");

        if (BeginM.equalsIgnoreCase("0")){MsgResult.append("JAN "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("1")){MsgResult.append("FEB "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("2")){MsgResult.append("MAR "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("3")){MsgResult.append("APR "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("4")){MsgResult.append("MAY "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("5")){MsgResult.append("JUN "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("6")){MsgResult.append("JUL "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("7")){MsgResult.append("AUG "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("8")){MsgResult.append("SEP "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("9")){MsgResult.append("OCT "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("10")){MsgResult.append("NOV "+BeginY+"");}
        else if (BeginM.equalsIgnoreCase("11")){MsgResult.append("DEC "+BeginY+"");}

        MsgResult.append(",<B>To: </B>\n");
        if (EndM.equalsIgnoreCase("0")){MsgResult.append("JAN "+EndY+"");}
        else if (EndM.equalsIgnoreCase("1")){MsgResult.append("FEB "+EndY+"");}
        else if (EndM.equalsIgnoreCase("2")){MsgResult.append("MAR "+EndY+"");}
        else if (EndM.equalsIgnoreCase("3")){MsgResult.append("APR "+EndY+"");}
        else if (EndM.equalsIgnoreCase("4")){MsgResult.append("MAY "+EndY+"");}
        else if (EndM.equalsIgnoreCase("5")){MsgResult.append("JUN "+EndY+"");}
        else if (EndM.equalsIgnoreCase("6")){MsgResult.append("JUL "+EndY+"");}
        else if (EndM.equalsIgnoreCase("7")){MsgResult.append("AUG "+EndY+"");}
        else if (EndM.equalsIgnoreCase("8")){MsgResult.append("SEP "+EndY+"");}
        else if (EndM.equalsIgnoreCase("9")){MsgResult.append("OCT "+EndY+"");}
        else if (EndM.equalsIgnoreCase("10")){MsgResult.append("NOV "+EndY+"");}
        else if (EndM.equalsIgnoreCase("11")){MsgResult.append("DEC "+EndY+"");}

        MsgResult.append("</TD><TD WIDTH=\"20%\"><B>Time: </B>\n");

        MsgResult.append("<SCRIPT language=\"javascript\">\n");
        MsgResult.append("<!-- \n");
        MsgResult.append("document.write(writeTimeLong(1));\n");
        MsgResult.append("//-->     \n");
        MsgResult.append("</SCRIPT>\n");

        MsgResult.append("</B></TD>\n");
        MsgResult.append("</TR>\n");
        MsgResult.append("</TABLE><BR>\n");

        MsgResult.append("<TABLE  BORDER=\"0\" WIDTH=\"100%\">\n");
        MsgResult.append("<TR>\n");
        MsgResult.append("<TD BGCOLOR=\"#B0BFD0\"><B>Part Number</B></TD><TD BGCOLOR=\"#B0BFD0\"><B>Description</B></TD>\n");
        MsgResult.append("<TD BGCOLOR=\"#B0BFD0\"><B>Packaging</B></TD><TD BGCOLOR=\"#B0BFD0\"><B>Manufacturer</B></TD><TD BGCOLOR=\"#B0BFD0\">\n");
        MsgResult.append("<B>Material ID</B></TD><TD BGCOLOR=\"#B0BFD0\"><B>Material Description</B></TD>\n");
        MsgResult.append("</TR>\n");

        if (client1.startsWith("Ray"))
        {
            //RayTcmISDBModel db = null;
            WebReportUsage obj = new WebReportUsage((ServerResourceBundle) new RayServerResourceBundle(),db);
            try
            {
             //MsgResult.append(obj.createReport("Andover","F003","2000","5","2001","4"));
             MsgResult.append(obj.createReport(client1,Rfacility, RworkArea, BeginY, BeginM, EndY, EndM));
            }
            catch (Exception e)
            {
             e.printStackTrace();
             buildERROR(out," ");
            }
        }
        else if (client1.startsWith("Southwest Airlines"))
        {
            //SWATcmISDBModel db = null;
            WebReportUsage obj = new WebReportUsage((ServerResourceBundle) new SWAServerResourceBundle(),db);
            try{MsgResult.append(obj.createReport(client1,Rfacility, RworkArea, BeginY, BeginM, EndY, EndM));}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out," ");
            }
        }
        MsgResult.append("</TABLE>\n");
        MsgResult.append("</FONT></BODY>\n");
      out.println(MsgResult);
    }

    protected StringBuffer printJavaScripts2(PrintWriter out)
    {
        StringBuffer MsgSB = new StringBuffer();


        MsgSB.append("<SCRIPT TYPE=\"text/javascript\" LANGUAGE=\"JavaScript\">\n");
        MsgSB.append("<!-- \n");
        MsgSB.append("    var Timer = null;\n");
        MsgSB.append("    var winHandle = null;\n");

        MsgSB.append("<!-- Date in Java Script .. Cameron Gregory  http://www.bloke.com/\n");
        MsgSB.append("// http://www.bloke.com/javascript/Date/\n");
        MsgSB.append("// change and modify this if you like, but leave these\n");
        MsgSB.append("// 4 comment lines in tact and unchanged.\n");
        MsgSB.append("function longMonthArray() {\n");
        MsgSB.append("  this[0] = \"January\";  this[1] = \"February\"; this[2] = \"March\";\n");
        MsgSB.append("  this[3] = \"April\";    this[4] = \"May\";      this[5] = \"June\";\n");
        MsgSB.append("  this[6] = \"July\";     this[7] = \"August\";   this[8] = \"September\";\n");
        MsgSB.append("  this[9] = \"October\";  this[10] = \"November\";        this[11] = \"December\";\n");
        MsgSB.append("        return (this);\n");
        MsgSB.append("}\n");
        MsgSB.append("function shortMonthArray() {\n");
        MsgSB.append("  this[0] = \"Jan\";      this[1] = \"Feb\";      this[2] = \"Mar\";\n");
        MsgSB.append("  this[3] = \"Apr\";      this[4] = \"May\";      this[5] = \"Jun\";\n");
        MsgSB.append("  this[6] = \"Jul\";      this[7] = \"Aug\";      this[8] = \"Sep\";\n");
        MsgSB.append("  this[9] = \"Oct\";      this[10] = \"Nov\";     this[11] = \"Dec\";\n");
        MsgSB.append("        return (this);\n");
        MsgSB.append("}\n");
        MsgSB.append("function longDayArray() {\n");
        MsgSB.append("  this[0] = \"Sunday\";   this[1] = \"Monday\";   this[2] = \"Tuesday\";\n");
        MsgSB.append("  this[3] = \"Wednesday\";        this[4] = \"Thursday\"; this[5] = \"Friday\";\n");
        MsgSB.append("  this[6] = \"Saturday\";\n");
        MsgSB.append("        return (this);\n");
        MsgSB.append("}\n");
        MsgSB.append("function shortDayArray() {\n");
        MsgSB.append("  this[0] = \"Sun\"; this[1] = \"Mon\"; this[2] = \"Tue\"; this[3] = \"Wed\";\n");
        MsgSB.append("  this[4] = \"Thu\"; this[5] = \"Fri\"; this[6] = \"Sat\";\n");
        MsgSB.append("        return (this);\n");
        MsgSB.append("}\n");
        MsgSB.append("function getShortYear(year)\n");
        MsgSB.append("{\n");
        MsgSB.append("shortyear =  year%100;\n");
        MsgSB.append("     if (shortyear < 10) shortyear = \"0\"+shortyear;\n");
        MsgSB.append("  return shortyear\n");
        MsgSB.append("}\n");
        MsgSB.append("function getLongYear(year)\n");
        MsgSB.append("{\n");
        MsgSB.append("  if (year > 1900) return year\n");
        MsgSB.append("  return year+1900;\n");
        MsgSB.append("}\n");
        MsgSB.append("function writeDateLong(format)\n");
        MsgSB.append("{\n");
        MsgSB.append("   shortDays = new shortDayArray();\n");
        MsgSB.append("   longDays = new longDayArray();\n");
        MsgSB.append("   shortMonths = new shortMonthArray();\n");
        MsgSB.append("   longMonths = new longMonthArray();\n");
        MsgSB.append("   d = new Date();\n");
        MsgSB.append("   day = d.getDate();\n");
        MsgSB.append("   month = d.getMonth();\n");
        MsgSB.append("  year = d.getYear();\n");
        MsgSB.append("   if (format == 0)\n");
        MsgSB.append("     str = shortDays[d.getDay()] + \" \" + shortMonths[month] +\". \" + day + \", \"+getLongYear(year);\n");
        MsgSB.append("  else if (format == 1)\n");
        MsgSB.append("     str = shortDays[d.getDay()] + \" \" + longMonths[month] + \" \" + day + \", \"+getLongYear(year);\n");
        MsgSB.append("  else if (format == 2)\n");
        MsgSB.append("     str = longDays[d.getDay()] + \" \" + longMonths[month] + \" \" + day + \", \"+getLongYear(year);\n");
        MsgSB.append("  else if (format == 3)\n");
        MsgSB.append("     str = longMonths[month] + \" \" + day + \", \"+getLongYear(year);\n");
        MsgSB.append("   else if (format == 4)\n");
        MsgSB.append("     str = shortDays[d.getDay()] + \" \" + day + \" \" + shortMonths[month] +\". \" +getLongYear(year);\n");
        MsgSB.append("  else if (format == 5)\n");
        MsgSB.append("     str = shortDays[d.getDay()] + \" \" + day + \" \" + longMonths[month] + \" \" +getLongYear(year);\n");
        MsgSB.append("  else if (format == 6)\n");
        MsgSB.append("     str = longDays[d.getDay()] + \" \" + day + \" \" + longMonths[month] + \" \" + getLongYear(year);\n");
        MsgSB.append("  else if (format == 7)\n");
        MsgSB.append("     str = day + \" \" + longMonths[month] + \", \"+getLongYear(year);\n");
        MsgSB.append("  else {\n");
        MsgSB.append("     month++;\n");
        MsgSB.append("     shortyear = getShortYear(year);\n");
        MsgSB.append("     if (format == 8)\n");
        MsgSB.append("       str = month + \"/\" + day + \"/\" + shortyear;\n");
        MsgSB.append("     else if (format == 9)\n");
        MsgSB.append("       str = month + \"/\" + day + \"/\" + getLongYear(year);\n");
        MsgSB.append("     else if (format == 10)\n");
        MsgSB.append("       str = day + \"/\" + month + \"/\" + shortyear;\n");
        MsgSB.append("     else if (format == 11)\n");
        MsgSB.append("       str = day + \"/\" + month + \"/\" + getLongYear(year);\n");
        MsgSB.append("     else if (format == 12)\n");
        MsgSB.append("       str = shortyear + \"/\" + month + \"/\" + day;\n");
        MsgSB.append("     else if (format == 13)\n");
        MsgSB.append("       str = shortyear + \"/\" + month + \"/\" + day;\n");
        MsgSB.append("     else {\n");
        MsgSB.append("        if (day < 10) day = \"0\"/+day\n");
        MsgSB.append("        if (month < 10) month = \"0\"+month\n");
        MsgSB.append("        if (format == 14)\n");
        MsgSB.append("          str = month + \"/\" + day + \"/\" + shortyear;\n");
        MsgSB.append("        else if (format == 15)\n");
        MsgSB.append("          str = month + \"/\" + day + \"/\" + getLongYear(year);\n");
        MsgSB.append("        else if (format == 16)\n");
        MsgSB.append("          str = day + \"/\" + month + \"/\" + shortyear;\n");
        MsgSB.append("        else if (format == 17)\n");
        MsgSB.append("          str = day + \"/\" + month + \"/\" + getLongYear(year);\n");
        MsgSB.append("        else if (format == 18)\n");
        MsgSB.append("          str = shortyear + \"/\" + month + \"/\" + day;\n");
        MsgSB.append("        else if (format == 19)\n");
        MsgSB.append("          str = shortyear + \"/\" + month + \"/\" + day;\n");
        MsgSB.append("        }\n");
        MsgSB.append("     }\n");
        MsgSB.append("  return str;\n");
        MsgSB.append("}\n");
        MsgSB.append("function writeDate()\n");
        MsgSB.append("{\n");
        MsgSB.append("   writeDateLong(0);\n");
        MsgSB.append("}\n");
        MsgSB.append("function writeTimeLong(format)\n");
        MsgSB.append("{\n");
        MsgSB.append("   d = new Date();\n");
        MsgSB.append("   hour=d.getHours();\n");
        MsgSB.append("   min=d.getMinutes();\n");
        MsgSB.append("   sec=d.getSeconds();\n");

        MsgSB.append("var dn=\"AM\" \n");
        MsgSB.append("if (hour>12)\n");
        MsgSB.append("{\n");
        MsgSB.append("dn=\"PM\"\n");
        MsgSB.append("hour=hour-12\n");
        MsgSB.append("}\n");
        MsgSB.append("if (hour==0) hour=12\n");

        MsgSB.append("   if (hour < 10) hour = \"0\"+hour;\n");
        MsgSB.append("   if (min < 10) min = \"0\"+min;\n");
        MsgSB.append("   if (sec < 10) sec = \"0\"+sec;\n");
        MsgSB.append("   if (format == 0)\n");
        MsgSB.append("      str = hour+\":\"+min+\":\"+sec;\n");
        MsgSB.append("   else if (format == 1)\n");
        MsgSB.append("      str = hour+\":\"+min+\" \"+dn;\n");
        MsgSB.append("   return str;\n");
        MsgSB.append("}\n");
        MsgSB.append("function writeTime()\n");
        MsgSB.append("{\n");
        MsgSB.append("   writeTimeLong(0);\n");
        MsgSB.append("}\n");
        MsgSB.append("//-->     \n");
        MsgSB.append("</SCRIPT>\n");
        MsgSB.append("</HEAD>  \n");
        return MsgSB;
        }

    protected StringBuffer printJavaScripts(PrintWriter out, String client)
    {
        StringBuffer MsgSB = new StringBuffer();
        String WWwhomeDirectory = bundle.getString("WEBS_HOME_WWWS");

        MsgSB.append("<SCRIPT TYPE=\"text/javascript\" LANGUAGE=\"JavaScript\">\n");
        MsgSB.append("<!-- \n");
        MsgSB.append("function RefreshPage(entered){  \n");
        MsgSB.append("with (entered)\n");
        MsgSB.append("{\n");
                    // Store the selected option in a variable called ref\n" +
        MsgSB.append("ref= document.MainForm.fac.options[selectedIndex].value;\n");
       //go through the message, letter by letter
        MsgSB.append("var format= \"\"; \n");
        MsgSB.append("for (i=0;i<ref.length;i++)\n");
        MsgSB.append("{\n");
        MsgSB.append("testchar = (ref.charAt(i))\n");
        MsgSB.append("if (testchar == \" \")\n");
        MsgSB.append("{\n");
        MsgSB.append("format = format+ \"%20\";\n");
        MsgSB.append("}\n");
        MsgSB.append("else \n");
        MsgSB.append("{\n");
        MsgSB.append("format = format+(ref.charAt(i));\n");
        MsgSB.append("}\n");
        MsgSB.append("}\n");
        //MsgSB.append("alert(format)\n");
        MsgSB.append( "} \n");

                    if (client.equalsIgnoreCase("Raytheon"))
                    {

                    MsgSB.append("loc = \""+WWwhomeDirectory+"/tcmIS/servlet/radian.web.servlets.ray.RayUsageRptWebPage?Session=2\";\n");
                    }
                    else if (client.equalsIgnoreCase("Southwest Airlines"))
                    {

                    MsgSB.append("loc = \""+WWwhomeDirectory+"/tcmIS/servlet/radian.web.servlets.swa.SWAUsageRptWebPage?Session=2\";\n");
                    }
                    else if (client.equalsIgnoreCase("Lockheed"))
                    {

                    MsgSB.append("loc = \""+WWwhomeDirectory+"/tcmIS/servlet/radian.web.servlets.lmco.LMCOUsageRptWebPage?Session=2\";\n");
                    }
                    else if (client.equalsIgnoreCase("DRS"))
                    {

                    MsgSB.append("loc = \""+WWwhomeDirectory+"/tcmIS/servlet/radian.web.servlets.drs.DRSUsageRptWebPage?Session=2\";\n");
                    }

        MsgSB.append("loc=loc+\"&FFacility1=\"+format; \n");
        //MsgSB.append("alert(loc);\n");
        MsgSB.append("document.location=loc;\n");
        MsgSB.append("} \n");

        /*MsgSB.append("function goHelp(){\n");
        MsgSB.append("window.bottom.location='/tcmIS/reports/ray/usage1/help.html';\n");
        MsgSB.append("window.bottom.document.location='/tcmIS/reports/ray/help.html';\n");
        MsgSB.append("} \n");

        MsgSB.append("if (window.document.images) {\n");
        MsgSB.append("img1on = new Image();   img1on.src = \"https://www.ross.radian.com/images/help_down.gif\";\n");
        MsgSB.append("img1off = new Image();  img1off.src = \"https://www.ross.radian.com/images/help_up.gif\";\n");
        MsgSB.append("}\n");
        MsgSB.append("function imgOn(imgName) {\n");
        MsgSB.append("if (window.document.images) {\n");
        MsgSB.append("window.document[imgName].src = eval(imgName + \"on.src\");\n");
        MsgSB.append("}\n");
        MsgSB.append("}\n");
        MsgSB.append("function imgOff(imgName) {\n");
        MsgSB.append(" if (window.document.images) {\n");
        MsgSB.append(" window.document[imgName].src = eval(imgName + \"off.src\");\n");
        MsgSB.append("}\n");*/
        MsgSB.append("//-->     \n");
        MsgSB.append("</SCRIPT>\n");
        MsgSB.append("</HEAD>  \n");

        return MsgSB;
    }

    public void buildERROR(PrintWriter out, String message)
    {
        String Msg = "";
        Msg +="<HTML><HEAD>\n";
        Msg +="</HEAD>\n";
        Msg +="<BODY><CENTER>\n";
        Msg +="<font face=Arial size=\"3\"><b>An Error Occurred - Please Try Again</b></font><P></P><BR>\n";
        Msg +="<font face=Arial size=\"3\"><b>"+message+"</b></font><P></P><BR>\n";
        Msg +="</CENTER></body></HTML>    \n";
        out.println(Msg);
		out.close();
    }
}

