package radian.tcmis.server7.client.zwl.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.zwl.dbObjs.*;
import radian.tcmis.server7.client.zwl.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class ZWLCatalog extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ZWLTcmISDBModel db = null;
      try {
        db = new ZWLTcmISDBModel("ZWL",(String)request.getHeader("logon-version"));
        Catalog obj = new Catalog((ServerResourceBundle) new ZWLServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

































