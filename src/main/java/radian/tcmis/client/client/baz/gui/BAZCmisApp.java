package radian.tcmis.client.client.baz.gui;

import java.io.FileOutputStream;
import java.security.Security;

import radian.tcmis.client.client.baz.helpers.BAZClientResourceBundle;
import radian.tcmis.client.client.baz.helpers.BAZReportPanelBundle;
import radian.tcmis.client.share.gui.CmisApp;
import radian.tcmis.client.share.gui.ReportInputPanel;
import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class BAZCmisApp extends CmisApp {

  BAZClientResourceBundle cConstants = new BAZClientResourceBundle();
  BAZReportPanelBundle reportBundle;
  FileOutputStream fOutStream;

  public BAZCmisApp(){
    reportBundle = new BAZReportPanelBundle(this);
  }
  //Main method
  static public void main(String[] args) {
    BAZCmisApp cmisApp = new BAZCmisApp();
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




























