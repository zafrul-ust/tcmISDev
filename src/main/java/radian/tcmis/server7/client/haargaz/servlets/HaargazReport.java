package radian.tcmis.server7.client.haargaz.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.haargaz.dbObjs.*;
import radian.tcmis.server7.client.haargaz.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class HaargazReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HaargazTcmISDBModel db = null;
      try {
        db = new HaargazTcmISDBModel("Haargaz",(String)request.getHeader("logon-version"));
        Report obj = new Report((ServerResourceBundle) new HaargazServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}