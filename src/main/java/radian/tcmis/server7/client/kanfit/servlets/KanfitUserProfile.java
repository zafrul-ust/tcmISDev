package radian.tcmis.server7.client.kanfit.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.kanfit.dbObjs.*;
import radian.tcmis.server7.client.kanfit.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class KanfitUserProfile extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      KanfitTcmISDBModel db = null;
      try {
        db = new KanfitTcmISDBModel("Kanfit",(String)request.getHeader("logon-version"));
        UserProfile obj = new UserProfile((ServerResourceBundle) new KanfitServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

