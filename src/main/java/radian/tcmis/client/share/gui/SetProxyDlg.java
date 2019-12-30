
//Title:        SetProxyDlg
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Chuck King
//Company:      Radian International
//Description:  Displays a dialog box for changing the proxy and proxy port


package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import java.util.*;

import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class SetProxyDlg extends JDialog {
  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  StaticJLabel lProxy = new StaticJLabel();
  StaticJLabel lPort = new StaticJLabel();
  JTextField tProxy = new JTextField();
  JTextField tPort = new JTextField();
  JButton bOk = new JButton();
  JButton bCancel = new JButton();
  JButton bTest = new JButton();
  JButton bHelp = new JButton();
  Frame parent;
  ConfigFile cf;
  Properties prop;
  Thread mainThread;
  Thread myThread;
  boolean proxyWorks = false;
  boolean setNewProxy = false;
  CmisApp grandParent = null;

  /*String wDir = new String(System.getProperty("user.dir"));
  String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
*/
  public SetProxyDlg(Frame frame, CmisApp p) {
    super(frame, "tcmIS/Set Proxy", true);
    grandParent = p;
    cf = new ConfigFile(grandParent);

    tProxy.setText(cf.getProxy());
    if (tProxy.getText().equalsIgnoreCase("proxy")) {
       tProxy.setText("");
    }
    tPort.setText(cf.getProxyPort());
    prop = System.getProperties();
    parent = frame;
    myThread = new Thread("proxyTestThread");
    try {
      jbInit();
      this.getContentPane().add(panel1);
      pack();
      CenterComponent.centerCompOnScreen(this);
      //colors and fonts
      ClientHelpObjs.makeDefaultColors(this);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception{
    xYLayout1.setWidth(414);
    xYLayout1.setHeight(173);
    this.setResizable(false);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    panel1.setLayout(xYLayout1);
    lProxy.setText("Proxy:");
    lPort.setText("Port:");
    bOk.setText("OK");
    bOk.addActionListener(new SetProxyDlg_bOk_actionAdapter(this));
    bCancel.setActionCommand("");
    bCancel.setText("Cancel");
    bCancel.addActionListener(new SetProxyDlg_bCancel_actionAdapter(this));
    bTest.setText("Test");
    bHelp.setText("Help");
    bHelp.addActionListener(new SetProxyDlg_bHelp_actionAdapter(this));
    bTest.addActionListener(new SetProxyDlg_bTest_actionAdapter(this));
    bCancel.addActionListener(new SetProxyDlg_bCancel_actionAdapter(this));
    panel1.setLayout(xYLayout1);
    panel1.add(lProxy, new XYConstraints(82, 24, -1, -1));
    panel1.add(lPort, new XYConstraints(89, 66, -1, -1));
    panel1.add(tProxy, new XYConstraints(134, 22, 179, -1));
    panel1.add(tPort, new XYConstraints(134, 64, 179, -1));
    panel1.add(bOk, new XYConstraints(4, 116, 100, 37));
    panel1.add(bCancel, new XYConstraints(106, 116, 126, 37));
    panel1.add(bTest, new XYConstraints(234, 116, 86, 37));
    panel1.add(bHelp, new XYConstraints(322, 116, 86, 37));

    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));

    bCancel.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    bTest.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));
    bHelp.setIcon(ResourceLoader.getImageIcon("images/button/help.gif"));

    // colorss
    ClientHelpObjs.makeDefaultColors(this);

  }
  void bCancel_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
       doCancel();
  }
  void this_windowClosing(WindowEvent e) {
       doCancel();
  }

  void bTest_actionPerformed(ActionEvent e) {
       ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
       doTest();
  }

  void bOk_actionPerformed(ActionEvent e) {
       ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
       doOk();
  }

  void bHelp_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    doHelp();
  }

  public static boolean setTheProxy(Frame parent, boolean askFirst,CmisApp p) {
    if (askFirst) {
      ConfirmDlg cd = new ConfirmDlg(parent,"tcmIS/Set Proxy",true);
      cd.setMsg("Do you use a proxy?");
      CenterComponent.centerCompOnComp(parent, cd);
      cd.setVisible(true);
      if(!cd.getConfirmationFlag()) {
        return false;
      }
    }
    SetProxyDlg spd = new SetProxyDlg(parent,p);
    CenterComponent.centerCompOnComp(parent, spd);
    spd.setVisible(true);
    return spd.setNewProxy;
  }

  private void doOk() {
    synchronized (this){
      cf.setProxy(tProxy.getText());
      cf.setProxyPort(tPort.getText());
      cf.update();
      this.setNewProxy = true;
      this.setVisible(false);
      grandParent.changeProxyConfig();
    }
  }

  private void doCancel() {
    this.setNewProxy = false;
    this.setVisible(false);
  }

  private void doTest() {
    mainThread = Thread.currentThread();
    proxyWorks = false;
    //myThread = new Thread(this);
    //myThread.start();
    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    tProxy.setCursor(this.getCursor());
    tPort.setCursor(this.getCursor());
    try {
      testIT();
    } catch (Exception e){}

    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    tProxy.setCursor(new Cursor(Cursor.TEXT_CURSOR));
    tPort.setCursor(new Cursor(Cursor.TEXT_CURSOR));

    proxyStatusDialog();

  }


  private void doHelp() {
    ClientHelpObjs.goHelp(grandParent);
  }

  protected void testIT() {
    String oldP = null;
    String oldPP = null;
    try {
        proxyWorks = false;

        ConfigFile cf = new ConfigFile(grandParent);
        oldP = cf.getProxy();
        oldPP = cf.getProxyPort();
        cf.setProxy(tProxy.getText().trim());
        cf.setProxyPort(tPort.getText().trim());
        cf.update();
        grandParent.changeProxyConfig();

        //Try to get servlet

        TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.PROXY,grandParent);
        Hashtable query = new Hashtable();
        query.put("PROXY","TEST"); //String, String
        Hashtable result = cgi.getResultHash(query);
        System.out.println("Proxy result:"+result);
        if (result.containsKey("WORKS")) {
          System.out.println("Got the works");
          proxyWorks = ((Boolean)result.get("WORKS")).booleanValue();
        }

    } catch (Exception e) {
        e.printStackTrace();
        proxyWorks = false;
    }

    cf = new ConfigFile(grandParent);
    cf.setProxy(oldP);
    cf.setProxyPort(oldPP);
    cf.update();
    grandParent.changeProxyConfig();

  }

  private void proxyStatusDialog() {
    GenericDlg gd = new GenericDlg(this.parent, "tcmIS/Proxy Test", true);
    if (proxyWorks) {
      gd.setMsg("The proxy/port combination is working.");
    } else {
      gd.setMsg("The proxy/port combination is not working.");
    }
    CenterComponent.centerCompOnScreen(gd);
    gd.setVisible(true);
    proxyWorks = false;
  }

}

class SetProxyDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  SetProxyDlg adaptee;

  SetProxyDlg_bOk_actionAdapter(SetProxyDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class SetProxyDlg_bCancel_actionAdapter implements java.awt.event.ActionListener{
  SetProxyDlg adaptee;

  SetProxyDlg_bCancel_actionAdapter(SetProxyDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bCancel_actionPerformed(e);
  }
}

class SetProxyDlg_bTest_actionAdapter implements java.awt.event.ActionListener{
  SetProxyDlg adaptee;

  SetProxyDlg_bTest_actionAdapter(SetProxyDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bTest_actionPerformed(e);
  }
}


class SetProxyDlg_bHelp_actionAdapter implements java.awt.event.ActionListener{
  SetProxyDlg adaptee;

  SetProxyDlg_bHelp_actionAdapter(SetProxyDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bHelp_actionPerformed(e);
  }
}


































