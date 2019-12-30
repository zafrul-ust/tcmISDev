package radian.tcmis.server7.client.algat.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.algat.dbObjs.*;
import radian.tcmis.server7.client.algat.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class AlgatSearchInfoNew extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AlgatTcmISDBModel db = null;
      try {
        db = new AlgatTcmISDBModel("Algat",(String)request.getHeader("logon-version"));
        SearchInfoNew obj = new SearchInfoNew((ServerResourceBundle) new AlgatServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}
