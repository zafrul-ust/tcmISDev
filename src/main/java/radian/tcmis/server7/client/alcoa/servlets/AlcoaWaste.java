package radian.tcmis.server7.client.alcoa.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.alcoa.dbObjs.AlcoaTcmISDBModel;
import radian.tcmis.server7.client.alcoa.helpers.AlcoaServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class AlcoaWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AlcoaTcmISDBModel db = null;
      try {
        db = new AlcoaTcmISDBModel("Alcoa",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new AlcoaServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}