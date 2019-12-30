package radian.tcmis.server7.client.maeet.servlets;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.maeet.dbObjs.*;
import radian.tcmis.server7.client.maeet.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class MAEETWasteOrderTracking extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      MAEETTcmISDBModel db = null;
      try {
        db = new MAEETTcmISDBModel("MAEET",(String)request.getHeader("logon-version"));
        WasteOrderTracking obj = new WasteOrderTracking((ServerResourceBundle) new MAEETServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}
