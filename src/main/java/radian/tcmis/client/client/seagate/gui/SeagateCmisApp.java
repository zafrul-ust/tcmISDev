package radian.tcmis.client.client.seagate.gui;

import java.awt.*;
import java.awt.event.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.GridBagConstraints2;
import java.net.*;
import java.io.*;
import java.util.*;

import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.client.seagate.helpers.*;
import java.security.Security;

import javax.swing.*;

public class SeagateCmisApp extends CmisApp {

  SeagateClientResourceBundle cConstants = new SeagateClientResourceBundle();
  SeagateReportPanelBundle reportBundle;
  FileOutputStream fOutStream;

  public SeagateCmisApp(){
    reportBundle = new SeagateReportPanelBundle(this);
  }
  //Main method
  static public void main(String[] args) {
    SeagateCmisApp cmisApp = new SeagateCmisApp();
    // running using webstart
    try{
      System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol");
      Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
      if (args.length == 5)
      {
        System.out.println("---------- args: "+args[0]+" - "+args[1]+" - "+args[2]+" - "+args[3]+" - "+args[4]);
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
    /*
    //begin
    try{
      //System.out.println("Before all probles Start");
      System.setSecurityManager(new TcmSecurityManager());

      cmisApp.makeAdjusting();
      //System.out.println("Args: "+args.length);

      //Tcmis dummy1 = new Tcmis();
      //System.out.println("Done Tcmis Exec");
      //Update dummy2 = new Update();
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
    cmisApp.setStartFromWebStart(false);
    cmisApp.run();
    //end
  }
    */

  public ClientResourceBundle getResourceBundle(){
         return (ClientResourceBundle) cConstants;
  }
  public ReportInputPanel[] getReportPanels() {
    return reportBundle.getReports();
  }

}




























