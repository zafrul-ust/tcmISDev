package radian.tcmis.server7.client.gkn.servlets;
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
import radian.tcmis.server7.client.gkn.dbObjs.*;
import radian.tcmis.server7.client.gkn.helpers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class GKNAdHocBatch extends HttpServlet {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
   }
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GKNTcmISDBModel db = null;
      try {
        db = new GKNTcmISDBModel("GKN",(String)request.getHeader("logon-version"));
        AdHocBatch obj = new AdHocBatch((ServerResourceBundle) new GKNServerResourceBundle(),db);
        obj.doPost(request,response);
      } catch (Exception e){
        e.printStackTrace();
      } finally {
        db.close();
      }
   }
}
