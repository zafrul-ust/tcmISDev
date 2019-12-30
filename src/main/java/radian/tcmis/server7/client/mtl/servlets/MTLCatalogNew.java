package radian.tcmis.server7.client.mtl.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.mtl.dbObjs.*;
import radian.tcmis.server7.client.mtl.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class MTLCatalogNew extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      MTLTcmISDBModel db = null;
      try {
        db = new MTLTcmISDBModel("MTL",(String)request.getHeader("logon-version"));
        CatalogNew obj = new CatalogNew((ServerResourceBundle) new MTLServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}
