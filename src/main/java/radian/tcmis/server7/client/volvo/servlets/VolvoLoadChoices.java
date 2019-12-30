package radian.tcmis.server7.client.volvo.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.volvo.dbObjs.*;
import radian.tcmis.server7.client.volvo.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class VolvoLoadChoices extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      VolvoTcmISDBModel db = null;
      try {
        db = new VolvoTcmISDBModel("Volvo",(String)request.getHeader("logon-version"));
        LoadChoices obj = new LoadChoices((ServerResourceBundle) new VolvoServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}