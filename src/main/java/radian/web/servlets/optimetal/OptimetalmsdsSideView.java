package radian.web.servlets.optimetal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.optimetal.dbObjs.OptimetalTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.msdsSideView;

public class OptimetalmsdsSideView extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      OptimetalTcmISDBModel db = null;
      try {
        //System.out.println("\n\n============ Optimetalmsdssideview check");
        db = new OptimetalTcmISDBModel("Optimetal");
        if (db == null){
         PrintWriter out2 = new PrintWriter (response.getOutputStream());
         out2.println("Starting DB");
         out2.println("DB is null");
         out2.close();
         return;
        }
        msdsSideView obj = new msdsSideView((ServerResourceBundle) new OptimetalServerResourceBundle(),db);
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
