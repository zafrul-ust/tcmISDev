package radian.tcmis.server7.client.kemfast.servlets;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.kemfast.dbObjs.*;
import radian.tcmis.server7.client.kemfast.helpers.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class KemfastNChemObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException,  IOException
  {
      doPost(request,response);
  }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      KemfastTcmISDBModel db = null;
      try {
        db = new KemfastTcmISDBModel("Kemfast",(String)request.getHeader("logon-version"));
        NChemObj obj = new NChemObj((ServerResourceBundle) new KemfastServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}