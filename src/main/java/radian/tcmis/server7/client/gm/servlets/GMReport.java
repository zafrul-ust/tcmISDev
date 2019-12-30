package radian.tcmis.server7.client.gm.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.gm.dbObjs.*;
import radian.tcmis.server7.client.gm.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GMReport extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GMTcmISDBModel db = null;
      try {
        db = new GMTcmISDBModel("GM",(String)request.getHeader("logon-version"));
        Report obj = new Report((ServerResourceBundle) new GMServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}