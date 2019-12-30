package radian.tcmis.server7.share.servlets;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.both1.helpers.ReadDataFromFile;
import radian.tcmis.server7.share.db.ResourceLibrary;
import radian.tcmis.server7.share.dbObjs.CreateMRForPentaconScanObj;

public class CreateMRForPentaconScan extends HttpServlet {
  RayTcmISDBModel db = null;
  private final String[] headerColumn = {"BARCODE","CAT_PART_NO","COMPANY_ID","FACILITY_ID"};

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    PrintWriter out = response.getWriter();
    response.setContentType("Text/html");
    //System.out.println("\n\n------- got here create MR for Pentacon Scan");
    try {
      //get database connection
      db = new RayTcmISDBModel("Ray","2");
      if (db == null) {
        out.println("Starting DB");
        out.println("DB is null");
        out.close();
        return;
      }
      //load data from properties file
      ResourceLibrary resourceLibrary = new ResourceLibrary("PentaconScan");
      String localDir = resourceLibrary.getString("local.dir");
      String action = request.getParameter("action");
      if ("load".equalsIgnoreCase(action)) {
        String fileName = request.getParameter("file_name");
        if (fileName == null) {
          HelpObjs.sendMail(db, "JRun Scheduler", "No file was pass to servlets", 86030, false);
          return;
        }
        //read file
        ReadDataFromFile readDataFromFile = new ReadDataFromFile();
        Vector dataV = readDataFromFile.readFile(localDir+fileName,",",headerColumn);
        if (dataV.size() < 0) {
          HelpObjs.sendMail(db, "JRun Scheduler", "No data from file: "+fileName, 86030, false);
          return;
        }
        //otherwise continue the process
        //load data into stage table
        if (loadDataIntoStageTable(dataV,fileName)) {
          //Now create MR
          createMR();
        }
      }else {
        createMR();
      }
      //System.out.println("------- Create MR for Pentacon Scan DONE");
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db, "JRun Scheduler", "Error occurred while scheduler processing create mr for pentacon scan ", 86030, false);
    } finally {
      db.close();
    }
  } //end of doGet

  private boolean loadDataIntoStageTable(Vector dataV, String fileName) {
    boolean result = false;
    try {
      CreateMRForPentaconScanObj createMRForPentaconScanObj = new CreateMRForPentaconScanObj(db);
      if (createMRForPentaconScanObj.loadDataIntoStageTable(dataV,fileName)) {
        result = true;
      }
    }catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  } //end of method

  private void createMR() {
    //System.out.println("------- create MR HERE");
    try {
      CreateMRForPentaconScanObj createMRForPentaconScanObj = new CreateMRForPentaconScanObj(db);
      createMRForPentaconScanObj.createMRFromStageTable();
    }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db, "Creating MR from stage table failed", "Error occurred while creating mr for pentacon scan", 86030, false);
    }
  } //end of method

} //end of class