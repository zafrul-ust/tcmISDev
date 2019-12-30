package radian.tcmis.server7.client.l3.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.l3.dbObjs.L3TcmISDBModel;
import radian.tcmis.server7.client.l3.helpers.L3ServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class L3Waste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      L3TcmISDBModel db = null;
      try {
        db = new L3TcmISDBModel("L3",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new L3ServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}