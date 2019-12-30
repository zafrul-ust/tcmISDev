package radian.tcmis.server7.share.servlets;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;

public class TestServlets extends HttpServlet {
  RayTcmISDBModel db = null;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PrintWriter out = response.getWriter();
    response.setContentType("Text/html");
    System.out.println("\n\n------- got here Test Servlets");
    try {

      /*code creator begin
      //get database connection
      db = new RayTcmISDBModel("TCM_OPS","1");
      if (db == null) {
        out.println("Starting DB");
        out.println("DB is null");
        out.close();
        return;
      }
      //creating beans and factory
      CodeCreator codeCreator = new CodeCreator();
      codeCreator.createBean("com.tcmis.internal.hub","consolidated_bol_header_view","c:\\temp\\codecreator\\",db.getConnection());
      codeCreator.createFactory("com.tcmis.internal.hub","consolidated_bol_header_view","c:\\temp\\codecreator\\",db.getConnection());
      codeCreator.createBean("com.tcmis.internal.hub","consolidated_bol_detail_view","c:\\temp\\codecreator\\",db.getConnection());
      codeCreator.createFactory("com.tcmis.internal.hub","consolidated_bol_detail_view","c:\\temp\\codecreator\\",db.getConnection());
      */ //end of code creator


      /*manually fix cabinet scan data
      //get database connection
      db = new RayTcmISDBModel("Tcm_Ops");
      if (db == null) {
        out.println("Starting DB");
        out.println("DB is null");
        out.close();
        return;
      }
      scannerDownloadDBObj s = new scannerDownloadDBObj(db);
      s.douploaddata(new Hashtable());
      *///end here

      //String postingMsg = BothHelpObjs.sendSslPost( "https://www.tcmis.com/tcmIS/oip/punchout/checkout?payloadID=20050304101536.608697609.185137@okoracl7.okla.seagate.com","payloadID=20050304101536.608697609.185137@okoracl7.okla.seagate.com","text/html" );
      //String postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/cgi-bin/rpq.cgi?item=449334","&item=449334&per=86030&fac=Andover&bp=2101&comp=RAYTHEON","text/html");

      /*get database connection
      //looping to get next number from sequence
      db = new RayTcmISDBModel("RAY","2");
      if (db == null) {
        out.println("Starting DB");
        out.println("DB is null");
        out.close();
        return;
      }
      for (int i = 0; i < 140; i++) {
        HelpObjs.getNextValFromSeq(db, "fac_app_part_comment_seq");
      }
      */

      /*sending test email
      db = new RayTcmISDBModel("DOE","2");
      if (db == null) {
        out.println("Starting DB");
        out.println("DB is null");
        out.close();
        return;
      }
      String[] receiver = {"rbarbara@slac.stanford.edu"};
      String[] cc = {"bostic@slac.stanford.edu"};
      HelpObjs.javaSendMail("test email","This is a test from tcmIS.  Please reply if you get this.",receiver,cc);
      */

      System.out.println("------- Test Servlets DONE");
    } catch (Exception e) {
      e.printStackTrace();
    }
  } //end of doGet
} //end of class