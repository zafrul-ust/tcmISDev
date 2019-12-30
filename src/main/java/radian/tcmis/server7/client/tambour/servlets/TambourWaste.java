package radian.tcmis.server7.client.tambour.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.tambour.dbObjs.TambourTcmISDBModel;
import radian.tcmis.server7.client.tambour.helpers.TambourServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class TambourWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      TambourTcmISDBModel db = null;
      try {
        db = new TambourTcmISDBModel("Tambour",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new TambourServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}