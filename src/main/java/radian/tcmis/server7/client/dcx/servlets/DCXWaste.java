package radian.tcmis.server7.client.dcx.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.dcx.dbObjs.DCXTcmISDBModel;
import radian.tcmis.server7.client.dcx.helpers.DCXServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class DCXWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DCXTcmISDBModel db = null;
      try {
        db = new DCXTcmISDBModel("DCX",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new DCXServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}