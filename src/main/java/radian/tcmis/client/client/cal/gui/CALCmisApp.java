package radian.tcmis.client.client.cal.gui;

import java.io.FileOutputStream;
import java.security.Security;

import radian.tcmis.client.client.cal.helpers.CALClientResourceBundle;
import radian.tcmis.client.client.cal.helpers.CALReportPanelBundle;
import radian.tcmis.client.share.gui.CmisApp;
import radian.tcmis.client.share.gui.ReportInputPanel;
import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class CALCmisApp extends CmisApp {

  CALClientResourceBundle cConstants = new CALClientResourceBundle();
  CALReportPanelBundle reportBundle;
  FileOutputStream fOutStream;

  public CALCmisApp(){
    reportBundle = new CALReportPanelBundle(this);
  }
  //Main method
  static public void main(String[] args) {
    CALCmisApp cmisApp = new CALCmisApp();
    // running using webstart
    try{
      System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol");
      Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
      if (args.length == 5)
      {
        cmisApp.setUserLogon(args[0]);
        cmisApp.setLogonPass(args[1]);
        if ("y".equalsIgnoreCase(args[2])) {      //coming from Ariba
          cmisApp.setAribaLogon(true);
        }else {
          cmisApp.setAribaLogon(false);
        }
        cmisApp.setPayLoadID(args[3]);
        cmisApp.setStartFromWebStart(true);
        cmisApp.setLogonVersion(args[4]);
      }else {
         cmisApp.setStartFromWebStart(false);
      }
      cmisApp.run();
    } catch (Exception e) {e.printStackTrace();} ; // just to avoid dup if Start.java already defined
  }

  public ClientResourceBundle getResourceBundle(){
         return (ClientResourceBundle) cConstants;
  }
  public ReportInputPanel[] getReportPanels() {
    return reportBundle.getReports();
  }

}




























