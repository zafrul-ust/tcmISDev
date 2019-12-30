package radian.tcmis.server7.share.servlets;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import com.actuate.ereport.common.IViewerInterface;
import com.actuate.ereport.datasrcmgrs.jdbcdatasrc.JDBCHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.Personnel;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.WasteShipmentScreen;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;


public class NewReport extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;

  ACJEngine erw = null;
  JDBCHandler ds = null;
  OutputStream os = null;
  TemplateManager tm = null;
  HttpServletResponse response = null;

  public NewReport(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    function = null;
    inData = null;
  }
  protected void print(TcmisOutputStreamServer out){
    try {
      out.sendObject((Hashtable) mySendObj);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void setResponse(HttpServletResponse r) {
    this.response = r;
  }

  protected void getResult(){
    mySendObj = new Hashtable();
    // using myRecvObj you get the function the way you want
    inData = (Hashtable)myRecvObj;
    String action = (String)inData.get("ACTION");
    if (action.equalsIgnoreCase("search"))
      loadWasteOrderTracking();
    if (action.equalsIgnoreCase("getInitialInfo"))
      getInitialInfo();
    if (action.equalsIgnoreCase("PRINT_SCREEN"))
      goPrintScreen();

  }

  public void goPrintScreen() {
    //System.out.println("\n\n================ GOT HERE: "+inData);
    String facId = (String)inData.get("FACILITY_ID");
    String workArea = BothHelpObjs.makeBlankFromNull((String)inData.get("WORK_AREA"));
    String searchText = BothHelpObjs.makeBlankFromNull((String)inData.get("SEARCH_TEXT"));
    erw = new ACJEngine();
    ACJOutputProcessor ec = new ACJOutputProcessor();
		String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
		ec.setPathForFontMapFile( fontmappath );
    try {
      erw.readTemplate("/home/servlet/radian/web/erw/reports/SearchScreen.erw");   //Query the result directly from database
    } catch(Exception ex) {
      System.out.println("ERROR LOADING TEMPLATE");
    }


    //String query = "select * from waste_traveler_view where traveler_id in (20,125,960)";

    String where = "facility_id = '"+facId+"'";
    if (workArea != null && BothHelpObjs.isBlankString(workArea)){
      where += " and application = '"+workArea+"'";
    }
    //testing purposes
    where += " and item_id = 124572";

    //System.out.println("\n\n--------- where: "+where);

    //setting the where clause
    tm = erw.getTemplateManager();
    tm.setWHEREClause("SEC_00",where);

    try {
      IViewerInterface ivi = erw.generateReport();
      if (!ec.setReportData(ivi,null)) System.exit(0);
    } catch(Exception ex){
      System.out.println("ERROR: While generating report");

    }
    Random ran = new Random();
    int tmp = ran.nextInt();
    Integer tmp2 = new Integer(tmp);
    try {
      ec.generatePDF("/home/httpd/htdocs/reports/searchScreen"+tmp2.toString()+".pdf",null);
    } catch (Exception ex) {
      //System.out.println("++++++++ ERRROR can't generate pdf");
      ex.printStackTrace();
    }


    response.setContentType("text/html");
    try {
      PrintWriter output = new PrintWriter(response.getOutputStream());
      output.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=https://www.tcmis.com/reports/searchScreen"+tmp2.toString()+".pdf\">");
      output.close();
    }catch (Exception re){
      re.printStackTrace();
    }

  }

  public void loadWasteOrderTracking() {
    doSearch();
  }

  public void getInitialInfo() {
    try{
      Integer userId = (Integer)inData.get("USER_ID");
      Hashtable initialInfo = WasteShipmentScreen.getInitialInfo3(db,userId.intValue(),"A");
      Personnel p = new Personnel(db);
      p.setPersonnelId(userId.intValue());
      p.load();
      mySendObj.put("PREFER_FACILITY",p.getFacilityId());
      mySendObj.put("INITIAL_INFO",initialInfo);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void doSearch(){
    /*
    try{
      mySendObj.put("DATA_MODEL",WasteOrderTrackingScreen.doSearch(db,(Hashtable)inData.get("SEARCH_INFO")));
    }catch(Exception e){
      e.printStackTrace();
    }*/
  }
}

