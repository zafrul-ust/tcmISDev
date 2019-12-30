package radian.tcmis.server7.client.getrag.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.getrag.dbObjs.GetragTcmISDBModel;
import radian.tcmis.server7.client.getrag.helpers.GetragServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GetragWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GetragTcmISDBModel db = null;
      try {
        db = new GetragTcmISDBModel("Getrag",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new GetragServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}