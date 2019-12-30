package radian.tcmis.client.client.imco.gui;

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
import radian.tcmis.client.client.imco.helpers.*;
import java.security.Security;

import javax.swing.*;

public class IMCOCmisApp extends CmisApp {

  IMCOClientResourceBundle cConstants = new IMCOClientResourceBundle();
  IMCOReportPanelBundle reportBundle;
  FileOutputStream fOutStream;

  public IMCOCmisApp(){
    reportBundle = new IMCOReportPanelBundle(this);
  }
  //Main method
  static public void main(String[] args) {
    IMCOCmisApp cmisApp = new IMCOCmisApp();
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




























