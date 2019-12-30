package radian.tcmis.server7.client.pge.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.pge.dbObjs.*;
import radian.tcmis.server7.client.pge.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class PGEUserProfileNew extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      PGETcmISDBModel db = null;
      try {
        db = new PGETcmISDBModel("PGE",(String)request.getHeader("logon-version"));
        UserProfileNew obj = new UserProfileNew((ServerResourceBundle) new PGEServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }

}
