package radian.tcmis.server7.client.team.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.team.dbObjs.*;
import radian.tcmis.server7.client.team.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class TeamCatalog extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      TeamTcmISDBModel db = null;
      try {
        db = new TeamTcmISDBModel("Team",(String)request.getHeader("logon-version"));
        Catalog obj = new Catalog((ServerResourceBundle) new TeamServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}

































