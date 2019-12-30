package radian.tcmis.server7.client.zf.servlets;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.zf.dbObjs.*;
import radian.tcmis.server7.client.zf.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ZFUserProfileNew extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ZFTcmISDBModel db = null;
      try {
        db = new ZFTcmISDBModel("ZF",(String)request.getHeader("logon-version"));
        UserProfileNew obj = new UserProfileNew((ServerResourceBundle) new ZFServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }

}
