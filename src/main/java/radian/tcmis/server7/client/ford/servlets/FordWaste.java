package radian.tcmis.server7.client.ford.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ford.dbObjs.FordTcmISDBModel;
import radian.tcmis.server7.client.ford.helpers.FordServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class FordWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      FordTcmISDBModel db = null;
      try {
        db = new FordTcmISDBModel("Ford",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new FordServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}