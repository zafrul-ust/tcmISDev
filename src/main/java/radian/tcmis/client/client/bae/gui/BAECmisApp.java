package radian.tcmis.client.client.bae.gui;

import java.io.FileOutputStream;
import java.security.Security;

import radian.tcmis.client.client.bae.helpers.BAEClientResourceBundle;
import radian.tcmis.client.client.bae.helpers.BAEReportPanelBundle;
import radian.tcmis.client.share.gui.CmisApp;
import radian.tcmis.client.share.gui.ReportInputPanel;
import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class BAECmisApp extends CmisApp {

  BAEClientResourceBundle cConstants = new BAEClientResourceBundle();
  BAEReportPanelBundle reportBundle;
  FileOutputStream fOutStream;

  public BAECmisApp(){
    reportBundle = new BAEReportPanelBundle(this);
  }
  //Main method
  static public void main(String[] args) {
    BAECmisApp cmisApp = new BAECmisApp();
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

/*
  static public void main(String[] args) {
    BAECmisApp cmisApp = new BAECmisApp();
    try{
      //System.out.println("Before all probles Start");
      System.setSecurityManager(new TcmSecurityManager());

      cmisApp.makeAdjusting();
      //System.out.println("Args: "+args.length);

      //Tcmis dummy1 = new Tcmis();
      //System.out.println("Done Tcmis Exec");
      Update dummy2 = new Update();
      //System.out.println("Done Update Dummy");

      //for (int j=0;j<args.length;j++) System.out.println("Args("+j+")="+args[j]);

      if (args.length > 1){
       //System.out.println("Args: "+args.length);
       if (args[0].equalsIgnoreCase("poss")){
           cmisApp.checkClient();
           Poss poss = new Poss(cmisApp,args[1]!=null?args[1]:"");
           poss.run();
       }
       cmisApp.exit();
      }
    } catch (Exception e) {e.printStackTrace();} ; // just to avoid dup if Start.java already defined
    cmisApp.run();
    cmisApp.setUserLogon("trong");
    cmisApp.setLogonPass("t");
    cmisApp.setAribaLogon(false);
    cmisApp.run();
  }
  */



























