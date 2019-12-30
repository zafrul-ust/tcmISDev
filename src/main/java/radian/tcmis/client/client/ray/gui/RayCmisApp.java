package radian.tcmis.client.client.ray.gui;

import java.io.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.client.ray.helpers.*;
import java.security.Security;

public class RayCmisApp extends CmisApp {

  RayClientResourceBundle cConstants = new RayClientResourceBundle();
  RayReportPanelBundle reportBundle;
  FileOutputStream fOutStream;

  public RayCmisApp(){
    reportBundle = new RayReportPanelBundle(this);
  }
  //Main method
  static public void main(String[] args) {
    RayCmisApp cmisApp = new RayCmisApp();
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
    RayCmisApp cmisApp = new RayCmisApp();
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
/*
System.out.println("----------- java home: "+System.getProperty("java.home"));
      String fromPath = "C:\\testRename\\";
      //String fromPath = "C:\\Program Files\\Java Web Start\\.javaws\\cache\\http\\Dwww.tcmis.com\\P80\\DMprereleasejnlp\\DMray\\";
      String toPath = "C:\\Program Files\\javaSoft\\JRE\\1.3.1\\lib\\ext\\";
      String[] orgFiles = {"jcert.jar","jsse.jar","jnet.jar"};
      //String[] orgFiles = {"RCjcert.jar","RCjsse.jar","RCjnet.jar"};
      String[] newFiles = {"jcert.jar","jsse.jar","jnet.jar"};
      File org;
      File newF;
      FileInputStream in;
      FileOutputStream out;
      for (int i = 0; i < orgFiles.length; i++) {
        org = new File(fromPath + orgFiles[i]);
        newF = new File(toPath + newFiles[i]);
        in = new FileInputStream(org);
        out = new FileOutputStream(newF);
        if (in != null && out != null) {
          if (newF.exists()) {
            System.out.println("--------- file exist");
          }else {
            System.out.println("------------ copying file");
            int pacSize = 50;
            byte buffer[] = new byte[pacSize];
            int n;
            while ((n = in.read(buffer)) > -1) {
              out.write(buffer,0,n);
            }
            out.close();
            in.close();
          }
        }
      }
      System.out.println("------- done go check it out!!!!");
*/


























