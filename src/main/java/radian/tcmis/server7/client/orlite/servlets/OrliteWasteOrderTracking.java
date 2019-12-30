package radian.tcmis.server7.client.orlite.servlets;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.orlite.dbObjs.*;
import radian.tcmis.server7.client.orlite.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class OrliteWasteOrderTracking extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      OrliteTcmISDBModel db = null;
      try {
        db = new OrliteTcmISDBModel("Orlite",(String)request.getHeader("logon-version"));
        WasteOrderTracking obj = new WasteOrderTracking((ServerResourceBundle) new OrliteServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}
