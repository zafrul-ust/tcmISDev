
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;

import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class LogonFrame extends JFrame {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel topPanel = new JPanel();
  JPanel bottonPanel = new JPanel();
  StaticJLabel lName = new StaticJLabel();
  StaticJLabel lPass = new StaticJLabel();
  JTextField tName = new JTextField();
  JPasswordField tPass = new JPasswordField();
  JButton bOk = new JButton();
  JButton bCancel = new JButton();
  JButton bSetProxy = new JButton();
  GridBagLayout gridBagLayout = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();

  Image cB;
  int frameDelay = 300;
  Image[] img ;
  long lastDisplay =0;
  int currentFrame =0;
  int numFrames;
  boolean logonPassed = false;
  LogonFrame_LogonThread fT;
  String client = null;
  // erase water WaterAnfyWrapper imgIntroPanel = null;
  LogonFrame_MyBevelPanel imgIntroPanelStatic = new LogonFrame_MyBevelPanel();

  CmisApp parent = null;
  javax.swing.JButton bHelp = new javax.swing.JButton();

  boolean reLogonFlag = false;
  public void setRelogonFlag(boolean b) {
    reLogonFlag = b;
  }
  public boolean getRelogonFlag() {
    return reLogonFlag;
  }

  public LogonFrame(String title) {
    super(title);
    try  {
      jbInit();
      pack();
      // mine
      this.setSize(new Dimension(440, 410));
      CenterComponent.centerCompOnScreenNHigher(this,2);
      tName.requestFocus();
      ClientHelpObjs.makeDefaultColors(this);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public LogonFrame() {
    this("");
  }

  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout);
    topPanel.setLayout(gridBagLayout2);
    bottonPanel.setLayout(gridBagLayout3);
    lName.setText("Login Name:");
    lPass.setText("Password:");
    bOk.setMaximumSize(new Dimension(110, 23));
    bOk.setMinimumSize(new Dimension(110, 23));
    bOk.setPreferredSize(new Dimension(110, 23));
    bOk.setText("Ok");
    bCancel.setMaximumSize(new Dimension(110, 23));
    bCancel.setMinimumSize(new Dimension(110, 23));
    bCancel.setPreferredSize(new Dimension(110, 23));
    bCancel.setText("Cancel");
    bSetProxy.setText("Set Proxy");
    tName.setMaximumSize(new Dimension(150, 21));
    tName.setMinimumSize(new Dimension(150, 21));
    tName.setPreferredSize(new Dimension(150, 21));
    tName.setHorizontalAlignment(SwingConstants.LEADING);
    tPass.setMaximumSize(new Dimension(150, 21));
    tPass.setMinimumSize(new Dimension(150, 21));
    tPass.setPreferredSize(new Dimension(150, 21));
    bHelp.setMaximumSize(new Dimension(110, 23));
    bHelp.setMinimumSize(new Dimension(110, 23));
    bHelp.setPreferredSize(new Dimension(110, 23));
    jPanel1.setMaximumSize(new Dimension(420, 400));
    jPanel1.setMinimumSize(new Dimension(420, 400));
    jPanel1.setPreferredSize(new Dimension(420, 400));
    topPanel.setMaximumSize(new Dimension(410, 270));
    topPanel.setMinimumSize(new Dimension(410, 270));
    topPanel.setPreferredSize(new Dimension(410, 270));
    imgIntroPanelStatic.setMaximumSize(new Dimension(400, 265));
    imgIntroPanelStatic.setMinimumSize(new Dimension(400, 265));
    imgIntroPanelStatic.setPreferredSize(new Dimension(400, 265));
    bottonPanel.setMaximumSize(new Dimension(410, 105));
    bottonPanel.setMinimumSize(new Dimension(410, 105));
    bottonPanel.setPreferredSize(new Dimension(410, 105));
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(topPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    topPanel.add(imgIntroPanelStatic, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(bottonPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
    bottonPanel.add(lName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    bottonPanel.add(tName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 110, 0, 0), 0, 0));
    bottonPanel.add(lPass, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    bottonPanel.add(tPass, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 110, 0, 0), 0, 0));
    bottonPanel.add(bOk, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    bottonPanel.add(bCancel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 115, 0, 0), 0, 0));
    bottonPanel.add(bHelp, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 230, 0, 0), 0, 0));

    bottonPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
    topPanel.setBorder(new BevelBorder(BevelBorder.RAISED));


    this.setResizable(false);

    //buttons
    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/ok.gif","Search");
    bOk.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif","Search");
    bCancel.setIcon(ss);

    bHelp.setText("Help");
    ss = ResourceLoader.getImageIcon("images/button/help.gif","Help");
    bHelp.setIcon(ss);
    bHelp.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bHelp_actionPerformed(e);
      }
    });

    tPass.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        tPass_keyPressed(e);
      }
    });
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    bOk.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOk_actionPerformed(e);
      }
    });
    ss = ResourceLoader.getImageIcon("images/button/submit.gif","Search");
    bSetProxy.setIcon(ss);
    bSetProxy.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bSetProxy_actionPerformed(e);
      }
    });
    //imgIntroPanel.setVisible(false);
  }

  // customization methods
  public void setParent(Object parent){
    // nedd to set here because it is breaking if we set on the init.
    this.parent = (CmisApp) parent;
    this.setTitle("tcmIS Logon");
  }

  public void getAuth(){


    if(fT == null || !fT.isAlive()) {
      fT = new LogonFrame_LogonThread(this);
      fT.start();
    }
  }

  public String getChosenClient(){
     return client;
  }

  void getAuthAction(){
    int i = -1;
    //10-26-01
    //record username and pass so connection can use proxy auth
    if (this.isVisible()) {
      if (tPass.getPassword().length<1 || tName.getText().length() <1){
        imgIntroPanelStatic.setVisible(true);
        String no = new String("Please enter a username and password.");
        GenericDlg err = new GenericDlg(this,"Error",true);
        err.setMsg(no);
        err.show();
        return;
      }
      parent.setUserLogon(this.tName.getText().trim());
      parent.setLogonPass(new String(this.tPass.getPassword()).trim());
    }
    // Wait Cursor
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);

    try{
        //First needs to get client and build connection
        parent.checkClient();       // who is using the system?, set security
        client = parent.clients.elementAt(0).toString();
    }catch(Exception e){
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        imgIntroPanelStatic.setVisible(true);
        String no = new String("Unable to establish connection.");
        GenericDlg err = new GenericDlg(this,"Error",true);
        err.setMsg(no);
        err.show();
        //return;
        parent.exit();   // 6-18-02 replace for line above
    }

    Hashtable result = null;
    try {
      InetAddress ip= InetAddress.getLocalHost();
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CHECK_LOGON,parent);
      Hashtable query = new Hashtable();
      query.put("CN",parent.getUserLogon()); //String, String
      query.put("PWD",parent.getLogonPass());
      query.put("O",parent.getResourceBundle().getString("ORGANIZATION")); //String, String
      query.put("OU",client); //String, String
      query.put("IP",ip.getHostAddress());
      query.put("NEW","new");
      query.put("VERSION",parent.getResourceBundle().getString("VERSION"));
      query.put("CONNECTION","ENCRYPTED"); // type of connection
      query.put("ARIBA_LOGON",parent.getAribaLogon()?"T":"F");
      query.put("PAYLOAD_ID",parent.getPayLoadID());
      result = cgi.getResultHash(query);
      i = Integer.parseInt((String)result.get("PASSED"));
      //Try radian
      if (i == 1){
         query = new Hashtable();
         query.put("CN",parent.getUserLogon()); //String, String
         query.put("PWD",parent.getLogonPass());
         query.put("O",parent.getResourceBundle().getString("ORGANIZATION")); //String, String
         query.put("OU","Radian"); //String, String
         query.put("IP",ip.getHostAddress());
         query.put("NEW","new");
         query.put("VERSION",parent.getResourceBundle().getString("VERSION"));
         query.put("CONNECTION","ENCRYPTED"); // type of connection
         query.put("ARIBA_LOGON",parent.getAribaLogon()?"T":"F");
         query.put("PAYLOAD_ID",parent.getPayLoadID());
         result = cgi.getResultHash(query);
         i = Integer.parseInt((String)result.get("PASSED"));
      }
      if (i==99){  // Developer logon
         ConfirmDlg cfd = new ConfirmDlg(this,"Logon as someone else",true);
         cfd.setMsg("Do you want to logon as someone else?");
         cfd.setTextTVisible(true);
         cfd.setVisible(true);
         if (cfd.getConfirmationFlag()){
            query = new Hashtable();
            query.put("CN",cfd.getTextT().trim()); //String, String
            query.put("O",parent.getResourceBundle().getString("ORGANIZATION")); //String, String
            query.put("OU",client); //String, String
            query.put("PWD","");
            query.put("IP",ip.getHostAddress());
            query.put("NEW","new");
            query.put("VERSION",parent.getResourceBundle().getString("VERSION"));
            query.put("CONNECTION","DEVELOPER"); // type of connection
            query.put("ARIBA_LOGON",parent.getAribaLogon()?"T":"F");
            query.put("PAYLOAD_ID",parent.getPayLoadID());
            result = cgi.getResultHash(query);
            i = Integer.parseInt((String)result.get("PASSED"));
         } else {
            i = 0;
         }
      }
      if (i == 0){
        parent.setUserId((String)result.get("USER_ID"));
        parent.setUserLogon(tName.getText());
        //go check version
        Vector groupids = (Vector) result.get("GROUPIDS");
        Vector groupdesc = (Vector) result.get("GROUPDESC");
        Vector groupFacs = (Vector) result.get("GROUPFACS");
        parent.setGroupMembership(groupids,groupdesc,groupFacs);

        //connection
        String connection = (String) result.get("CONNECTION");
        if (!connection.equals("ENCRYPTED")){
           String no = new String("We can not set an Encrypted connection. Please try again.");
           GenericDlg err = new GenericDlg(this,"Security",true);
           err.setMsg(no);
           err.show();
           parent.exit();   //6-18-02 replace for line above
        }
        // DB Instance
        //String dbInst = ((Vector) result.get("CONNECTION")).elementAt(1).toString();
        String dbInst = (String) result.get("DB_INST");
        // add test to the main title
        parent.isDevelopment = (dbInst!=null && dbInst.indexOf("dev") > -1);

        //isWasteManager data
        parent.isWasteManagerData = (Hashtable)result.get("IS_WASTE_MANAGER_DATA");
        parent.facLocAppData = (Hashtable)result.get("FAC_LOC_APP_DATA");
        parent.userFacilityIDList = (Vector)parent.facLocAppData.get("FAC_IDS");
        parent.userFacilityNameList = (Vector)parent.facLocAppData.get("FAC_NAMES");
        parent.loadAllData = (Hashtable)result.get("LOAD_ALL_DATA");
        parent.yearOfReport = (Integer)((Hashtable)result.get("YEAR_OF_REPORT")).get("YEAR_OF_REPORT");
        parent.numberOfYear = (Integer)((Hashtable)result.get("YEAR_OF_REPORT")).get("NUM_OF_YEAR");
        parent.scannerfunctions = ((Boolean)((Hashtable)result.get("LOAD_ALL_DATA")).get("SCANNER_FUCNTIONS")).booleanValue();
        parent.inventoryCount = ((Boolean)((Hashtable)result.get("LOAD_ALL_DATA")).get("INVENTORY_COUNT")).booleanValue();
        parent.companyId = ((String)((Hashtable)result.get("LOAD_ALL_DATA")).get("COMPANY_ID")).toString();

        logonPassed = true;
        parent.logonPass(logonPassed);
      } else {
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        imgIntroPanelStatic.setVisible(true);
        String no = new String("Invalid Logon. Please try again.");
        GenericDlg err = new GenericDlg(this,"Error",true);
        err.setMsg(no);
        err.show();
        parent.exit();    //6-18-02
      }
    } catch (Exception e) {
         e.printStackTrace();
    } finally {
        imgIntroPanelStatic.setVisible(true);
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    }

  }
  /* erase water
  public WaterAnfyWrapper getWaterAnfyWrapper(){
      return null;//imgIntroPanel;
  }
  */

  public void killAnimation(){
      Thread[] allt = new Thread[Thread.currentThread().activeCount()];
      Thread.currentThread().enumerate(allt);
      for (int i=0;i<allt.length;i++){
         if (allt[i].getName().indexOf("AnWater.class") > 0){
           synchronized (this){
            ThreadGroup tg = allt[i].getThreadGroup();
            tg.stop();
           }
           break;
         }
      }
  }

  public boolean getLogonPass(){
      return logonPassed;
  }

  void doOK(){
       getAuth();

  }

  void doCancel(){
       logonPassed = false;
       parent.logonPass(logonPassed);
  }

  void doHelp(){
       ClientHelpObjs.goHelp(parent);
  }


  void doProxy(){
       if (SetProxyDlg.setTheProxy(this, false,parent)){
           // Reset proprieties to use new hashtable
           parent.changeProxyConfig();
       }
  }

  void bOk_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      doOK();
  }

  void bCancel_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      doCancel();
  }

  void bHelp_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      doHelp();
  }

  void bSetProxy_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      doProxy();
  }

  void tPass_keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         doOK();
      }
  }


}

class LogonFrame_MyBevelPanel extends JPanel {

  public LogonFrame_MyBevelPanel(){
     super();
     this.setBorder(new BevelBorder(BevelBorder.RAISED));
  }

  public void paint(Graphics g){
      Image tcmis = ResourceLoader.getImageIcon("images/gif/haastcm.gif").getImage();
      g.drawImage(tcmis,0,0,this);
  }
}

class LogonFrame_LogonThread extends Thread {
  LogonFrame parent;

  public LogonFrame_LogonThread(LogonFrame parent){
     super("LogonThread");
     this.parent = parent;
  }

  public void run(){
     parent.getAuthAction();
  }
}



