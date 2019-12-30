package radian.web.servlets.ommc;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.ommc.dbObjs.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class OMMCWasteSideView extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //System.out.println("\n\n============= ommcwastesideview check");
      OMMCTcmISDBModel db = null;
      try {
        db = new OMMCTcmISDBModel("OMMC");
        if (db == null){
         PrintWriter out2 = new PrintWriter (response.getOutputStream());
         out2.println("Starting DB");
         out2.println("DB is null");
         out2.close();
         return;
        }
        WasteSideView obj = new WasteSideView((ServerResourceBundle) new OMMCServerResourceBundle(),db);
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
