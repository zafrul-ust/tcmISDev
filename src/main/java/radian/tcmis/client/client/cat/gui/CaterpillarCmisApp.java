package radian.tcmis.client.client.cat.gui;

import java.io.FileOutputStream;
import java.security.Security;

import radian.tcmis.client.client.cat.helpers.CaterpillarClientResourceBundle;
import radian.tcmis.client.client.cat.helpers.CaterpillarReportPanelBundle;
import radian.tcmis.client.share.gui.CmisApp;
import radian.tcmis.client.share.gui.ReportInputPanel;
import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class CaterpillarCmisApp extends CmisApp {

  CaterpillarClientResourceBundle cConstants = new CaterpillarClientResourceBundle();
  CaterpillarReportPanelBundle reportBundle;
  FileOutputStream fOutStream;

  public CaterpillarCmisApp(){
    reportBundle = new CaterpillarReportPanelBundle(this);
  }
  //Main method
  static public void main(String[] args) {
    CaterpillarCmisApp cmisApp = new CaterpillarCmisApp();
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




























