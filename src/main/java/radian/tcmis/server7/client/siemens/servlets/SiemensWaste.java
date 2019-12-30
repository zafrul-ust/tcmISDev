package radian.tcmis.server7.client.siemens.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.siemens.dbObjs.SiemensTcmISDBModel;
import radian.tcmis.server7.client.siemens.helpers.SiemensServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class SiemensWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SiemensTcmISDBModel db = null;
      try {
        db = new SiemensTcmISDBModel("Siemens",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new SiemensServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}