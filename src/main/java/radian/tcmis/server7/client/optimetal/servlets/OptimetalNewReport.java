package radian.tcmis.server7.client.optimetal.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.optimetal.dbObjs.*;
import radian.tcmis.server7.client.optimetal.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class OptimetalNewReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      OptimetalTcmISDBModel db = null;
      try {
        db = new OptimetalTcmISDBModel("Optimetal",(String)request.getHeader("logon-version"));
        NewReport obj = new NewReport((ServerResourceBundle) new OptimetalServerResourceBundle(),db);
        obj.setResponse(response);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}