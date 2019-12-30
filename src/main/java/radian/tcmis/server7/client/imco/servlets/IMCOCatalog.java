package radian.tcmis.server7.client.imco.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.imco.dbObjs.*;
import radian.tcmis.server7.client.imco.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class IMCOCatalog extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      IMCOTcmISDBModel db = null;
      try {
        db = new IMCOTcmISDBModel("IMCO",(String)request.getHeader("logon-version"));
        Catalog obj = new Catalog((ServerResourceBundle) new IMCOServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

































