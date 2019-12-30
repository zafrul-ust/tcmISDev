package radian.web.servlets.dana;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.dana.dbObjs.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class DanaWasteSideView extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //System.out.println("\n\n============= danawastesideview check");
      DanaTcmISDBModel db = null;
      try {
        db = new DanaTcmISDBModel("Dana");
        if (db == null){
         PrintWriter out2 = new PrintWriter (response.getOutputStream());
         out2.println("Starting DB");
         out2.println("DB is null");
         out2.close();
         return;
        }
        wasteSideView obj = new wasteSideView((ServerResourceBundle) new DanaServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request,response);
   }
}
