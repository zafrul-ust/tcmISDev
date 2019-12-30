package radian.tcmis.server7.client.hans_sasserath.servlets;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.hans_sasserath.dbObjs.*;
import radian.tcmis.server7.client.hans_sasserath.helpers.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class HansSasserathNChemObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException,  IOException
  {
      doPost(request,response);
  }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      HansSasserathTcmISDBModel db = null;
      try {
        db = new HansSasserathTcmISDBModel("HansSasserath",(String)request.getHeader("logon-version"));
        NChemObj obj = new NChemObj((ServerResourceBundle) new HansSasserathServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}