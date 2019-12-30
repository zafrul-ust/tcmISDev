package radian.web.servlets.ray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.WasteLabelTraveler;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class WasteLabelPrinter {
  ServerResourceBundle bundle=null;
  TcmISDBModel db = null;

  String container_id = "";
  String browser = "";
  String fac = "";
  String trav = "";
  String prof = "";
  String vend = "";
  String desc = "";
  String dot = "";
  String wa = "";
  String mo = "";
  String num = "";
  String msg = "";
  String url = "";
  String inst = "";
  Vector containerId = new Vector();
  Vector data = new Vector();

  boolean gettingLABEL = false;
  boolean gettingBROWSER = false;

  int nav_test = 0;
  public static final int NAVIGATOR = 1;
  public static final int EXPLORER = 2;

  public WasteLabelPrinter(ServerResourceBundle b, TcmISDBModel d){
     bundle = b;
     db = d;
  }


  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  doPost(request,response);
  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    browser = request.getParameter("b");

    num = request.getParameter("num");
    Integer nUm = new Integer(num);

    for (int i=0;i<nUm.intValue();i++){
    container_id = request.getParameter("id" + i);
    containerId.addElement(container_id);
    }

    if ("NN".equalsIgnoreCase(browser)){
     nav_test = 1;
     gettingLABEL = true;
    }else if ("IE".equalsIgnoreCase(browser)){
     nav_test = 2;
     gettingLABEL = true;
    }else if ("x".equalsIgnoreCase(browser)){
     gettingLABEL = false;
     gettingBROWSER = true;
     url = "num=" + num;
     for (int x=0;x<containerId.size();x++){
      url = url + "&id"+x + "=" + containerId.elementAt(x).toString();
     }
    }else {
      nav_test = 2;
      gettingLABEL = true;
    }
    response.setContentType("text/html");
    printOutput(response);
  }

public void printOutput(HttpServletResponse response){
    try{
      PrintWriter out = new PrintWriter (response.getOutputStream());
      if (gettingLABEL){
        buildLABEL(out);
      }else if (gettingBROWSER){
        detectBrowser(out);
      }
      out.flush();
      out.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

public void detectBrowser(PrintWriter out){
//System.out.println("Got Here");
msg = "<html><head>";
msg = msg +
"        <SCRIPT LANGUAGE = \"JavaScript\">\n" +
"<!--\n" +
"    function browserDetect()\n" +
"         {\n" +
"          browserNN = (navigator.appName == \"Netscape\");\n" +
"          browserIE = (navigator.appName == \"Microsoft Internet Explorer\");\n" +
"           if (browserNN){\n" +
"             return \"NN\";\n" +
"           }else if (browserIE){\n" +
"             return \"IE\";\n" +
"           }\n" +
"           }\n" +
"function refresh()\n" +
"{\n" +
"loc=\"/tcmIS/servlet/radian.web.servlets.ray.RayWasteLabelPrinter?" + url + "&b=\" + browserDetect() ;\n" +
"target=\"_self\";\n" +
"window.location=loc;" +
"}\n" +
"// -->\n" +
"</script>\n" +
"</head><body bgcolor=ffffff onLoad=refresh();></body></html>";

  out.println(msg);
  out.close();
}

public void buildLABEL(PrintWriter out){
   try{
      labelData(WasteLabelTraveler.buildWasteLabel(db, containerId));
    }catch(Exception e){}

    String alert = "";
    String label = "";
    Vector v = new Vector();
    int row = 0;
//Build a browser specific Labels
switch(nav_test){
case NAVIGATOR:
    alert = "<script>\n" +
"      function Instructions(str){\n" +
"      NN_Print = window.open(str, \"NN_Print\",\n" +
"        \"toolbar=no,location=no,directories=no,status=no\" +\n" +
"        \",menubar=no,scrollbars=yes,resizable=no,\" +\n" +
"        \"width=400,height=400\");\n" +
"        }      </script>\n";

inst = "/nn.html";

    for (int i=0;i<data.size();i++){
    label = "";
    row++;

    Hashtable h = (Hashtable)data.elementAt(i);
     fac = h.get("FACILITY").toString();
     trav = h.get("TRAVELER_ID").toString();
     prof = h.get("VENDOR_PROFILE_ID").toString();
     vend = h.get("VENDOR_ID").toString();
     desc = h.get("DESCRIPTION").toString();
     dot = h.get("DOT").toString();
     wa = h.get("WORK_AREA").toString();
     mo = h.get("MANAGEMENT_OPTION").toString();
     container_id = h.get("CONTAINER_ID").toString();

label = label + "<!-----------------------Begin Label----------------------->\n" +
"<td><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n</td>" +
"<td align=center valign=center width=50%>\n" +
"<table border=0 width=330 height=450 bgcolor=000000>\n" +
"<tr><td align=center>\n" +
"<table width=330 height=450 cellpadding=0 cellspacing=0 bgcolor=ffffff>\n" +
"<tr><td width=330 align=center colspan=2>\n" +
"<font face=Arial size=4 color=000084><B>Raytheon Waste Traveler</b></font>\n" +
"</td></tr>\n" +
"<tr><td valign=center width=175>\n" +
"<P><font face=Arial size=1>Traveler ID</font><BR>\n" +
"<font size=2 face=Arial color=000084><center>" + trav + "</font>\n" +
"</td><td align=center valign=center bgcolor=000000 width=175>\n" +
"<font face=Arial size=3 color=ff0000><B>\n" +
"Raytheon</font>\n" +
"</td></tr>\n" +
"<tr><td valign=center witdh=175>\n" +
"<P><font face=Arial size=1>Facility</font><font size=2 face=Arial color=000084><center>" + fac + "</font>\n" +
"</td><td valign=center>\n" +
"<P><font face=Arial size=1>Work Area</font><font size=2 face=Arial color=000084><center>" + wa + "</font>\n" +
"</td></tr>\n" +
"<tr><td valign=center witdh=175>\n" +
"<P><font face=Arial size=1>Profile</font><font size=2 face=Arial color=000084><center>" + prof + "</font>\n" +
"</td><td valign=center>\n" +
"<P><font face=Arial size=1>Vendor</font><font size=2 face=Arial color=000084><center>" + vend + "</font>\n" +
"</td></tr>\n" +
"<tr><td align=left valign=center colspan=2>\n" +
"<P><font face=Arial size=1>Description</font><br>\n" +
"<center><table border=1 width=98%><tr>\n" +
"<td bgcolor=D7D8D8>\n" +
"<font face=Arial size=1>\n" + desc +
"</font></td></tr></table><BR>\n" +
"</td></tr>\n" +
"<tr><td align=left valign=center colspan=2>\n" +
"<P><font face=Arial size=1>Management Option</font><br>\n" +
"<center><table border=1 width=98%><tr>\n" +
"<td bgcolor=D7D8D8>\n" +
"<font face=Arial size=1>\n" + mo +
"</font></td></tr></table><BR>\n" +
"</td></tr>\n" +
"<tr><td align=left valign=center colspan=2>\n" +
"<P><font face=Arial size=1>DOT</font><br>\n" +
"<center><table border=1 width=98%><tr>\n" +
"<td bgcolor=D7D8D8>\n" +
"<font face=Arial size=1>\n" + dot +
"</font></td></tr></table><BR>\n" +
"</td></tr>\n" +
"<tr><td colspan=2 align=center>\n" +
"<P>\n" +
"<font face=\"SKANDATA C39\" size=7>" + container_id + "</font><BR>\n" +
"<font face=Arial size=2 color=048331>" + container_id + "</font>\n" +
"<BR>\n" +
"</td></tr>\n" +
"</table>\n" +
"</td></tr>\n" +
"</table>\n" +
"</td>\n" +
"<td><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n</td>" +
"<!-----------------------End Label------------------------>\n";

    if (row%2==0){
label = label +
"</tr>\n" +
"</table>\n" +
"<table border=0 width=650 height=475 cellpadding=0 cellspacing=0>\n" +
"<tr>\n";
    }
    if (row%4==0){
label = label +
"<BR><BR><BR>\n";
    }
    if ((i+1)==data.size()){
     if (data.size()%2!=0){
label = label + "<!-----------------------Begin Spacer----------------------->\n" +
"<td><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n</td>" +
"<td align=center valign=center width=50%>\n" +

"<table border=0 width=330 height=450>\n" +
"<tr><td>&nbsp;</td></tr></table></td>\n" +

"<td><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n</td>" +
"<!-----------------------End Spacer------------------------>\n";

     }
    }
  v.addElement(label);
}




msg = "<html><head>" + alert + "</head>\n" +
"<body bgcolor=ffffff background=\"/images/water.gif\" text=000000>\n" +
//"<body bgcolor=ffffff background=\"/images/water.gif\" text=000000 onload=\"Instructions('" + inst + "')\">\n" +
"<BR><BR><P><table border=0 width=650 height=475 cellpadding=0 cellspacing=0>\n" +
"<tr>\n";

 for (int i=0;i<v.size();i++){
  msg = msg + v.elementAt(i).toString();
 }

msg = msg + "</tr>\n" +
"</table>\n" +
"</body>\n" +
"</html>\n";




break;
case EXPLORER:

    alert = "<script>\n" +
"      function Instructions(str){\n" +
"      IE_Print = window.open(str, \"IE_Print\",\n" +
"        \"toolbar=no,location=no,directories=no,status=no\" +\n" +
"        \",menubar=no,scrollbars=yes,resizable=no,\" +\n" +
"        \"width=400,height=475\");\n" +
"        }      </script>\n";

inst = "/ie.html";

    for (int i=0;i<data.size();i++){
    label = "";
    row++;

    Hashtable h = (Hashtable)data.elementAt(i);
     fac = h.get("FACILITY").toString();
     trav = h.get("TRAVELER_ID").toString();
     prof = h.get("VENDOR_PROFILE_ID").toString();
     vend = h.get("VENDOR_ID").toString();
     desc = h.get("DESCRIPTION").toString();
     dot = h.get("DOT").toString();
     wa = h.get("WORK_AREA").toString();
     mo = h.get("MANAGEMENT_OPTION").toString();
     container_id = h.get("CONTAINER_ID").toString();

label = label + "<!-----------------------Begin Label----------------------->\n" +
"<td><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n</td>" +
"<td align=center valign=center width=50%>\n" +
"<table border=0 width=322 height=450 bgcolor=000000>\n" +
"<tr><td align=center>\n" +
"<table width=322 height=450 cellpadding=0 cellspacing=0 border=1 bgcolor=ffffff>\n" +
"<tr><td width=322 align=center colspan=2>\n" +
"<font face=Arial size=4 color=000084><B>Raytheon Waste Traveler</b></font>\n" +
"</td></tr>\n" +
"<tr><td valign=center width=175>\n" +
"<P><font face=Arial size=1>Traveler ID</font><BR>\n" +
"<font size=2 face=Arial color=000084><center>" + trav + "</font>\n" +
"</td><td align=center valign=center bgcolor=000000 width=175>\n" +
"<font face=Arial size=3 color=ff0000><B>\n" +
"Raytheon</font>\n" +
"</td></tr>\n" +
"<tr><td valign=center witdh=175>\n" +
"<P><font face=Arial size=1>Facility</font><font size=2 face=Arial color=000084><center>" + fac + "</font>\n" +
"</td><td valign=center>\n" +
"<P><font face=Arial size=1>Work Area</font><font size=2 face=Arial color=000084><center>" + wa + "</font>\n" +
"</td></tr>\n" +
"<tr><td valign=center witdh=175>\n" +
"<P><font face=Arial size=1>Profile</font><font size=2 face=Arial color=000084><center>" + prof + "</font>\n" +
"</td><td valign=center>\n" +
"<P><font face=Arial size=1>Vendor</font><font size=2 face=Arial color=000084><center>" + vend + "</font>\n" +
"</td></tr>\n" +
"<tr><td align=left valign=center colspan=2>\n" +
"<P><font face=Arial size=1>Description</font><br>\n" +
"<center><table border=1 width=98%><tr>\n" +
"<td bgcolor=D7D8D8>\n" +
"<font face=Arial size=1>\n" + desc +
"</font></td></tr></table><BR>\n" +
"</td></tr>\n" +
"<tr><td align=left valign=center colspan=2>\n" +
"<P><font face=Arial size=1>Management Option</font><br>\n" +
"<center><table border=1 width=98%><tr>\n" +
"<td bgcolor=D7D8D8>\n" +
"<font face=Arial size=1>\n" + mo +
"</font></td></tr></table><BR>\n" +
"</td></tr>\n" +
"<tr><td align=left valign=center colspan=2>\n" +
"<P><font face=Arial size=1>DOT</font><br>\n" +
"<center><table border=1 width=98%><tr>\n" +
"<td bgcolor=D7D8D8>\n" +
"<font face=Arial size=1>\n" + dot +
"</font></td></tr></table><BR>\n" +
"</td></tr>\n" +
"<tr><td colspan=2 align=center>\n" +
"<P>\n" +
"<font face=\"SKANDATA C39\" size=7>" + container_id + "</font><BR>\n" +
"<font face=Arial size=2 color=048331>" + container_id + "</font>\n" +
"<BR>\n" +
"</td></tr>\n" +
"</table>\n" +
"</td></tr>\n" +
"</table>\n" +
"</td>\n" +
"<td><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n</td>" +
"<!-----------------------End Label------------------------>\n";

    if (row%2==0){
label = label +
"</tr>\n" +
"</table>\n" +
"<table border=0 width=650 height=475 cellpadding=0 cellspacing=0>\n" +
"<tr>\n";
    }
    if (row%4==0){
label = label +
"<BR><BR>\n";
    }
    if ((i+1)==data.size()){
     if (data.size()%2!=0){
label = label + "<!-----------------------Begin Spacer----------------------->\n" +
"<td><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n</td>" +
"<td align=center valign=center width=50%>\n" +

"<table border=0 width=322 height=450>\n" +
"<tr><td>&nbsp;</td></tr></table></td>\n" +

"<td><font size=3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n</td>" +
"<!-----------------------End Spacer------------------------>\n";
     }
    }
  v.addElement(label);
}



msg = "<html><head>" + alert + "</head>\n" +
"<body bgcolor=ffffff background=\"/images/water.gif\" text=000000>\n" +
//"<body bgcolor=ffffff background=\"/images/water.gif\" text=000000 onload=\"Instructions('" + inst + "')\">\n" +
"<BR><P><table border=0 width=650 height=475 cellpadding=0 cellspacing=0>\n" +
"<tr>\n";

 for (int i=0;i<v.size();i++){
  msg = msg + v.elementAt(i).toString();
 }

msg = msg + "</tr>\n" +
"</table>\n" +
"</body>\n" +
"</html>\n";



break;
}


  out.println(msg);
  out.close();
}

protected void labelData(Vector v){
    WasteLabelTraveler wt;
    data = v;
}



  //Get Servlet information
  public String getServletInfo() {
    return "radian.web.servlets.ray.WasteLabelPrinter Information";
  }
}