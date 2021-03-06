package radian.tcmis.server7.client.l3.servlets;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.l3.dbObjs.*;
import radian.tcmis.server7.client.l3.helpers.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class L3NChemObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException,  IOException
  {
      doPost(request,response);
  }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      L3TcmISDBModel db = null;
      try {
        db = new L3TcmISDBModel("L3",(String)request.getHeader("logon-version"));
        NChemObj obj = new NChemObj((ServerResourceBundle) new L3ServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}