package radian.web.servlets.sales;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.sales.dbObjs.SalesTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.sales.SalesServerResourceBundle;
import radian.web.servlets.share.batchReport;

public class SalesBatchReport extends HttpServlet {
   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SalesTcmISDBModel db = null;
      try
      {
        db = new SalesTcmISDBModel("Sales");
        if (db == null){
         PrintWriter out2 = new PrintWriter (response.getOutputStream());
         out2.println("Starting DB");
         out2.println("DB is null");
         out2.close();
         return;
        }
        batchReport obj = new batchReport((ServerResourceBundle) new SalesServerResourceBundle(),db);
        obj.doPost(request,response);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        db.close();
      }
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request,response);
   }
}