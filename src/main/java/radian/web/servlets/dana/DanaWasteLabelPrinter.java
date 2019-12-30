package radian.web.servlets.dana;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.dana.dbObjs.DanaTcmISDBModel;
import radian.tcmis.server7.client.dana.helpers.DanaServerResourceBundle;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DanaWasteLabelPrinter extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        WasteLabelPrinter obj = new WasteLabelPrinter((ServerResourceBundle) new DanaServerResourceBundle(),db);
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