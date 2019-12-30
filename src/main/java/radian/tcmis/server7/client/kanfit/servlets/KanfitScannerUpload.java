package radian.tcmis.server7.client.kanfit.servlets;
/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.servlets.*;
import radian.tcmis.server7.client.kanfit.dbObjs.*;
import radian.tcmis.server7.client.kanfit.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class KanfitScannerUpload extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      KanfitTcmISDBModel db = null;
      try {
        db = new KanfitTcmISDBModel("Kanfit",(String)request.getHeader("logon-version"));
        ScannerUpload obj = new ScannerUpload((ServerResourceBundle) new KanfitServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}
