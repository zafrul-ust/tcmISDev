package radian.tcmis.server7.client.hans_sasserath.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.hans_sasserath.dbObjs.HansSasserathTcmISDBModel;
import radian.tcmis.server7.client.hans_sasserath.helpers.HansSasserathServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class HansSasserathWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HansSasserathTcmISDBModel db = null;
      try {
        db = new HansSasserathTcmISDBModel("HansSasserath",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new HansSasserathServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}