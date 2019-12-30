package radian.tcmis.server7.client.optimetal.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.optimetal.dbObjs.OptimetalTcmISDBModel;
import radian.tcmis.server7.client.optimetal.helpers.OptimetalServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class OptimetalWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      OptimetalTcmISDBModel db = null;
      try {
        db = new OptimetalTcmISDBModel("Optimetal",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new OptimetalServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}