package radian.tcmis.server7.client.doe.servlets;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.doe.dbObjs.DOETcmISDBModel;
import radian.tcmis.server7.client.doe.helpers.DOEServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class DOEWaste extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DOETcmISDBModel db = null;
      try {
        db = new DOETcmISDBModel("DOE",(String)request.getHeader("logon-version"));
        Waste obj = new Waste((ServerResourceBundle) new DOEServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}