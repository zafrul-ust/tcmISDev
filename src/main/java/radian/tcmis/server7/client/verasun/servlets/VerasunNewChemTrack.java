package radian.tcmis.server7.client.verasun.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.verasun.dbObjs.*;
import radian.tcmis.server7.client.verasun.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class VerasunNewChemTrack extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      VerasunTcmISDBModel db = null;
      try {
        db = new VerasunTcmISDBModel("Verasun",(String)request.getHeader("logon-version"));
        NewChemTrack obj = new NewChemTrack((ServerResourceBundle) new VerasunServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}