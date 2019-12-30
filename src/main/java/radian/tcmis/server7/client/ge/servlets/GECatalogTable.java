package radian.tcmis.server7.client.ge.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ge.dbObjs.*;
import radian.tcmis.server7.client.ge.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GECatalogTable extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GETcmISDBModel db = null;
      try {
        db = new GETcmISDBModel("GE",(String)request.getHeader("logon-version"));
        CatalogTable obj = new CatalogTable((ServerResourceBundle) new GEServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

