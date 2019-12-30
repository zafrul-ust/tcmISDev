package radian.tcmis.server7.client.fedco.servlets;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.fedco.dbObjs.*;
import radian.tcmis.server7.client.fedco.helpers.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class FedcoNChemObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException,  IOException
  {
      doPost(request,response);
  }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      FedcoTcmISDBModel db = null;
      try {
        db = new FedcoTcmISDBModel("Fedco",(String)request.getHeader("logon-version"));
        NChemObj obj = new NChemObj((ServerResourceBundle) new FedcoServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}