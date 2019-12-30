package radian.tcmis.server7.client.dd.servlets;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.dd.dbObjs.*;
import radian.tcmis.server7.client.dd.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class DDTrackObj extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DDTcmISDBModel db = null;
      try {
        db = new DDTcmISDBModel("DD",(String)request.getHeader("logon-version"));
        TrackObj obj = new TrackObj((ServerResourceBundle) new DDServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}
