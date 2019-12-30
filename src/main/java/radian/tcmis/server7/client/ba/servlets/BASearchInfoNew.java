package radian.tcmis.server7.client.ba.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ba.dbObjs.*;
import radian.tcmis.server7.client.ba.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class BASearchInfoNew extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      BATcmISDBModel db = null;
      try {
        db = new BATcmISDBModel("BA",(String)request.getHeader("logon-version"));
        SearchInfoNew obj = new SearchInfoNew((ServerResourceBundle) new BAServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}
