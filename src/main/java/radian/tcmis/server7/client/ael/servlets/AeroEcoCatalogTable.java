package radian.tcmis.server7.client.ael.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.ael.dbObjs.*;
import radian.tcmis.server7.client.ael.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class AeroEcoCatalogTable extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      AeroEcoTcmISDBModel db = null;
      try {
        db = new AeroEcoTcmISDBModel("AeroEco",(String)request.getHeader("logon-version"));
        CatalogTable obj = new CatalogTable((ServerResourceBundle) new AeroEcoServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

