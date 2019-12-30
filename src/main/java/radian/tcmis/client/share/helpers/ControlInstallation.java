package radian.tcmis.client.share.helpers;

import java.io.*;
import java.net.*;
import javax.jnlp.ExtensionInstallerService;
import javax.jnlp.ServiceManager;

/*
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.GridBagConstraints2;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.httpCgi.*;
*/

public class ControlInstallation implements Runnable {
  ExtensionInstallerService eis = null;
  public ControlInstallation(){
    try {
      eis = (ExtensionInstallerService)ServiceManager.lookup("javax.jnlp.ExtensionInstallerService");
    }catch (Exception e) {
      System.out.println("Error: Unable to instantiate ExtensionInstallerService.");
      return;
    }
    Thread t = new Thread(this);
    t.start();
  } //end of method

  public void run() {
    //setupDownloadWindow();
    installationOptionalPackage();
    installationCompleted(true);
  }

  public void setupDownloadWindow() {
    eis.setHeading("Updating JRE extension");
    eis.setStatus("Updating JRE secure extension.  Please wait...");
  }

  public void installationCompleted(boolean success) {
    if (success) {
      eis.installSucceeded(false);  //install success, don't reboot
    }else {
      //handle failure
      eis.installFailed();
    }
  }

  public void installationOptionalPackage() {
    String optionalPackagesJarDirWin = "\\lib\\ext\\";
    String[] jarNames = {"jcert.jar","jnet.jar","jsse.jar"};
    String jarServerPath = "http://198.180.200.219/prereleasejnlp/ray/";
    URL jreURL = null;
    try {
      jreURL = new URL("http://java.sun.com/products/autodl/j2se");
    }catch (Exception e) {
      System.out.println("creating URL: "+e);
    }
    String jrePath = eis.getInstalledJRE(jreURL,"1.3.1_01");
    File javaFile = new File(jrePath);  //where jre is located
    String jreRoot = javaFile.getParentFile().getParent();
    File libDir = new File(jreRoot+optionalPackagesJarDirWin);
    if (!libDir.exists()) {
      libDir.mkdir();
    }
    //now go and get jar files
    for (int i = 0; i < jarNames.length; i++) {
      File jarFile = new File(jreRoot+optionalPackagesJarDirWin+jarNames[i]);
      if (jarFile.exists()) {
        System.out.println("------- file already exist.");
        continue;
      }
      try {
        FileOutputStream fos = new FileOutputStream(jarFile);
        URL jarURL = new URL(jarServerPath+jarNames[i]);
        URLConnection jarConn = jarURL.openConnection();
        InputStream is = jarConn.getInputStream();
        int q;
        while ((q = is.read()) != -1) {
          fos.write(q);
        }
        is.close();
        fos.close();
      }catch (Exception e) {
        System.out.println("Creating & Writing "+jarNames[i]+": "+e);
      }
    }
  } //end of method

  public static void main(String[] args) {
    ControlInstallation installer = new ControlInstallation();
  } //end of main

} //end of class






