package radian.web.servlets.dana;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import radian.tcmis.server7.client.dana.dbObjs.DanaTcmISDBModel ;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;



public abstract class WastePickup extends HttpServlet implements SingleThreadModel {
  //Initialize global variables
  Vector data = null;
  PrintWriter out = null;
  String client = null;
  boolean next = false;
  boolean union = true;




  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  //Process the HTTP Post request

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("========== got here");
    this.doPost(request,response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    DanaTcmISDBModel db = null;
    client = new String("");


    String order = "500";

    response.setContentType("text/html");
    out = new PrintWriter (response.getOutputStream());
    // Open db
    data = null;
    try {
      db = new DanaTcmISDBModel(getClient());

      WasteOrder wo = new WasteOrder(db);
      data = wo.getOrderEmailDetail(order);
      System.out.println("========== got here: "+data);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("*** Error on open DataBase  ***");
      return;
    } finally {
      db.close();
    }





    Hashtable hD = null;

    for (int i=0;i<data.size();i++){  // last  occ. is num recs
        //headers
        if (i%10==0){
        out.println(""+
        "<tr>                                                                           \n"+
        "  <td bgcolor=\"#B0BFD0\" width=\"12%\" height=\"38\"><strong>Vendor Profile</strong></td>                \n"+
        "  <td bgcolor=\"#B0BFD0\" width=\"12%\" height=\"38\"><strong>Packaging</strong></td>                    \n"+
	      "  <td bgcolor=\"#B0BFD0\" width=\"12%\" height=\"38\"><strong>Quantity</strong></td>                \n"+

        "</tr>");
        }



        hD = (Hashtable) data.elementAt(i);

        String container_size = (hD.get("CONTAINER_SIZE")==null?"":hD.get("container_size").toString());
        String size_unit = (hD.get("SIZE_UNIT")==null?"":hD.get("size_unit").toString());
        String container_type = (hD.get("CONTAINER_TYPE")==null?"":hD.get("container_type").toString());
        String vendor_profile_id = (hD.get("vendor_profile_id")==null?"":hD.get("vendor_profile_id").toString());
        String tcmis_order_no = (hD.get("TCMIS_ORDER_NUMBER")==null?"":hD.get("tcmis_order_no").toString());
        String quantity = (hD.get("QUANTITY")==null?"":hD.get("quantity").toString());

        out.println("td width = 12%\">"+vendor_profile_id+"</td>");
        out.println("td width = 19%\">"+container_size+"</td>");
        out.println("td width = 12%\">"+quantity+"</td>");


    out.println("</table>");

    out.println("</body></html>");
    out.close();

  }
//Get Servlet information






  }




  /*protected void printHtmlNoData(DanaTcmISDBModel db){
        out.println("<tr>");
        out.println("<td width=\"5%\">");
        out.println("</td>");
        out.println("<td width=\"16%\">"+" No records found. "+"</td>");
        out.println("</tr></table>");
        out.println("</body></html>");
        out.close();
  }*/




  protected abstract String  getClient();
    protected abstract ServerResourceBundle getBundle();

}


/*msg += "  <html><head>
          <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
          <meta name="GENERATOR" content="Mozilla/4.75 [en] (Win98; U) [Netscape]">
          <title>Waste pickup</title>
          </head>
          <body text="#000000" bgcolor="#FDF5E6" link="#0000FF" vlink="#FF00FF">
          <center><img src="../PROGRAM FILES/COFFEECUP SOFTWARE/Working/tcmwaste.JPG" width="307" height="85" alt="" border="0" align=""></center>
          <p><br>
          <center>
          <h1>
          <b><font color="#000099"><font size=+4>Welcome to tcmIS Waste Pickup</font></font></b></h1></center>
          <center>
          <p><br><i><u><font color="#990000">PLEASE ENTER THE FOLLOWING INFORMATION:</font></u></i></center><p><br>";






msg += "  <p><b><i><font color="#3366FF"><font size=+2>Vendor order No&nbsp;<input type="text" name="" value="">
          </font></font></i></b><font color="#00CC00">&nbsp;&nbsp;</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

msg += "  <b><i><font color="#3366FF"><font size=+2>Planned Pickup (dd/mm/yyyy)</font></font></i></b>
          <input type="text" name="" value="">";

msg += "  <center><p><input type="button" value="Submit"></center><p><br><br>";

msg += "  <B><font color="#3366FF">tcmIS Order No:<B></font>" + order_number;

msg += "  <center><table width=900 bordercolor="#0000FF" bgcolor="#33CCCC" cellspacing="3" cellpadding="3" border="2">
          <tr>
            <th>Vendor ProfileD</th>
            <th>Packaging</th>
            <th>Quantity</th>
          <tr>
          </table>
          </center>
          </body>
          </html>";

  //out.println(msg);
  out.close();
}*/

