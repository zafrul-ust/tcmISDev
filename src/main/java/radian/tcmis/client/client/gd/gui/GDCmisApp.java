package radian.tcmis.client.client.gd.gui;

import java.io.FileOutputStream;
import java.security.Security;

import radian.tcmis.client.client.gd.helpers.GDClientResourceBundle;
import radian.tcmis.client.client.gd.helpers.GDReportPanelBundle;
import radian.tcmis.client.share.gui.CmisApp;
import radian.tcmis.client.share.gui.ReportInputPanel;
import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class GDCmisApp extends CmisApp {

  GDClientResourceBundle cConstants = new GDClientResourceBundle();
  GDReportPanelBundle reportBundle;
  FileOutputStream fOutStream;

  public GDCmisApp(){
    reportBundle = new GDReportPanelBundle(this);
  }
  //Main method
  static public void main(String[] args) {
    GDCmisApp cmisApp = new GDCmisApp();
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




























