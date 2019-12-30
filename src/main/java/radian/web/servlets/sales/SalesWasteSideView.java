package radian.web.servlets.sales;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.sales.dbObjs.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class SalesWasteSideView extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //System.out.println("\n\n============= saleswastesideview check");
      SalesTcmISDBModel db = null;
      try {
        db = new SalesTcmISDBModel("Sales");
        if (db == null){
         PrintWriter out2 = new PrintWriter (response.getOutputStream());
         out2.println("Starting DB");
         out2.println("DB is null");
         out2.close();
         return;
        }
        WasteSideView obj = new WasteSideView((ServerResourceBundle) new SalesServerResourceBundle(),db);
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
