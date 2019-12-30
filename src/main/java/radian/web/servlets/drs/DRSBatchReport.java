package radian.web.servlets.drs;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import radian.tcmis.server7.client.drs.dbObjs.DRSTcmISDBModel;
import radian.web.servlets.share.batchReport;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.drs.DRSServerResourceBundle;

public class DRSBatchReport extends HttpServlet {
   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DRSTcmISDBModel db = null;
      try
      {
        db = new DRSTcmISDBModel("DRS");
        if (db == null){
         PrintWriter out2 = new PrintWriter (response.getOutputStream());
         out2.println("Starting DB");
         out2.println("DB is null");
         out2.close();
         return;
        }
        batchReport obj = new batchReport((ServerResourceBundle) new DRSServerResourceBundle(),db);
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
