package radian.tcmis.server7.client.abm.servlets;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.abm.dbObjs.*;
import radian.tcmis.server7.client.abm.helpers.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ABMNChemObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException,  IOException
  {
      doPost(request,response);
  }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ABMTcmISDBModel db = null;
      try {
        db = new ABMTcmISDBModel("ABM",(String)request.getHeader("logon-version"));
        NChemObj obj = new NChemObj((ServerResourceBundle) new ABMServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}