//Title:      Your Product Name
//Version:
//Copyright:  Copyright (c) 1997
//Author:     Your Name
//Company:    Your Company
//Description:Your description
package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.awt.Component;

import radian.tcmis.client.share.helpers.ClientResourceBundle;
import radian.tcmis.client.share.helpers.ClientHelpObjs;
import radian.tcmis.client.share.gui.nchem.*;
import radian.tcmis.client.share.gui.admin.*;
import radian.tcmis.client.share.gui.waste.*;
import radian.tcmis.client.share.httpCgi.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class Main extends JFrame {

  //Temporary Images Directory
  /*String tmpwDir = new String(System.getProperty("user.dir"));
  String tempDir = tmpwDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") + (new String("/button/"));
*/

  //const
  static final int SEARCHPT = 0;
  static final int INVENTORY = 1;
  static final int REQUEST = 3;
  static final int USER_PROFILE = 4;
  static final int TRACKING = 5;
  public  static final int NEW_CHEM = 6;
  public  static final int NEW_CHEM_MAT = 7;
  static final int ADMIN = 8;
  public  static final int NEW_CHEM_TRACKING = 9;
  static final int REPORT = 10;
  public static final int WASTE = 11;
  public static final int WASTE_REQUEST = 12;
  public static final int WASTE_TRACKING = 13;
  public static final int MANAGE_WASTE = 14;
  public static final int WASTE_ORDER = 15;
  public static final int WASTE_ORDER_TRACKING = 16;
  public static final int CABINET = 17;
  public static final int INVENTORY_COUNT = 18;

  Hashtable newChemDetailH = null;      //3-13-02
  Hashtable requestDataH = null;
  Hashtable wasteRequestDataH = null;

  //trong 3/1/00
  String newChemEval = new String("normal");
  boolean showPrintScreen = true;


  //thread
  Main_MoveFrameThread mT;
  //Processing
  int frameDelay = 100;
  Image[] img ;
  long lastDisplay =0;
  int currentFrame =0;
  int numFrames;

  //parent
  CmisApp parent = null;

  //to assure the name is always the same
  public Vector profileChanged = new Vector();

  public ClientResourceBundle crb;

  public boolean stopped = false;

  // winpop
  public Hashtable countLabel = new Hashtable();
  static PopupMenu helpPop;
  Window helpWin;
  public SearchPT search;
  public InventWin IW ;
  public TrackWin TW ;
  public RequestsWin RW;
  public UserProfileWin UP;
  public NewChem ncmat;
  //new public AdminWinNew admin;
  public AdminWin admin;
  public NChemTrackWin ncTrack;
  public ReportWin report;
  public WastePanel waste;
  public WasteManage WasteManage;
  public WastePickupRequest wasteRequest;
  public WasteTrackWin wasteTrack;
  public WasteOrder wasteOrder;
  public WasteOrderTrack wasteOrderTrack;

  JButton imgControl = new JButton();
  JButton sndControl = new JButton();
  String imagesDir = null;

  BorderLayout borderLayout1 = new BorderLayout();
  JMenuBar menuBar1 = new JMenuBar();
  JMenu menuFile = new JMenu();
  JToolBar buttonBar = new JToolBar();
  JMenuItem menuExit = new JMenuItem();
  JMenuItem menuMR = new JMenuItem();
  JMenuItem menuInv = new JMenuItem();
  JMenu menuHelp = new JMenu();
  JMenuItem helpAbout = new JMenuItem();
  JMenuItem tcmisHelp = new JMenuItem();
  public Main_StatusBar statusBar = new Main_StatusBar();
  JTabbedPane tabsetPanel1 = new JTabbedPane();
  JMenuItem menuTrack = new JMenuItem();
  JMenuItem menuDoc = new JMenuItem();
  JMenuItem menuOrder = new JMenuItem();
  JMenuItem menuEnvironmental = new JMenuItem();
  JMenuItem menuHealth = new JMenuItem();
  JMenuItem menuChem = new JMenuItem();
  JMenuItem menuNewChemTrack = new JMenuItem();
  JMenuItem menuAdmin = new JMenuItem();
  JMenuItem menuReport = new JMenuItem();
  JMenuItem menuProfile = new JMenuItem();
  JMenuItem menuWaste = new JMenuItem();
  JMenuItem menuWasteManage = new JMenuItem();
  JMenuItem menuWasteTrack = new JMenuItem();
  JMenuItem menuWasteOrderTrack = new JMenuItem();
  JMenuItem menuStop = new JMenuItem();
  JMenuItem menuRlogin = new JMenuItem();
  JMenuItem menuPrint = new JMenuItem();
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JToolBar bottomBar = new JToolBar();
  JPanel panel3 = new JPanel();
  JPanel panel4 = new JPanel();
  JPanel panel6 = new JPanel();
  JPanel panel7 = new JPanel();
  JPanel imgPan = new JPanel();
  Main_ProcessingCanvas canvas1 = new Main_ProcessingCanvas(this);
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();
  BorderLayout borderLayout6 = new BorderLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();

  JPanel wasteBar = new JPanel();

  JPanel progPanel = new JPanel();
  public JProgressBar progBar = new JProgressBar();
  Main_JButtonAnimation animationButton = new Main_JButtonAnimation();

  JPanel panel5 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  BorderLayout borderLayout7 = new BorderLayout();
  CardLayout cardLayout1 = new CardLayout();
  CardLayout cardLayout2 = new CardLayout();

  Main_AnimationThread animation = null;


  //actions
  AbstractAction catalogAction;
  AbstractAction inventAction;
  AbstractAction trackAction;
  AbstractAction jdeAction;
  AbstractAction docAction;
  AbstractAction profileAction;
  AbstractAction nchemAction;
  AbstractAction adminAction;
  AbstractAction wasteAction;

  AbstractAction reportAction;
  AbstractAction stopAction;
  AbstractAction relogonAction;
  AbstractAction scrprtAction;
  AbstractAction prtrepAction;


  //buttons
  JButton catalogB = new JButton();
  JButton inventB = new JButton();
  JButton trackB = new JButton();
  JButton jdeB = new JButton();
  JButton docB = new JButton();
  JButton profileB = new JButton();
  JButton nchemB = new JButton();
  JButton adminB = new JButton();
  JButton ncTrackB = new JButton();
  JButton wasteB = new JButton();
  JButton manageWasteB = new JButton();
  JButton wasteTrackB = new JButton();
  JButton wasteOrderTrackB = new JButton();

  JButton stopB = new JButton();
  JButton relogonB = new JButton();
  JButton scrprtB = new JButton();
  JButton prtrepB = new JButton();
  JButton reportB = new JButton();
  JButton helpB = new JButton();

  //Nawaz 09-26-01 Seperator
  JButton sepB = new JButton();
  JButton sepB1 = new JButton();
  JButton sepB2 = new JButton();
  JButton sepB3 = new JButton();
  JButton sepB4 = new JButton();

  //menubar panels
  JPanel wasteP = new JPanel();
  JMenuItem menuscanDownload = new JMenuItem();
  downloadtoscanner dlts;

  JMenuItem menuInventoryCount = new JMenuItem();
  InventoryCount ic;

  //Construct the frame
  public Main() {
    try {
      jbInit();

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void showMain(){
     // ****rfz doesnt work ***parent.dummyFrame.toFront();
     /*
     animation = new Main_AnimationThread(animationButton,"Processing");
     animation.start();
     //3-19-02 animation.suspend();
     this.stopImage();   */

     synchronized (this){
       this.show();
       this.toFront();
     }
     // ****rfz doesnt work ***parent.dummyFrame.toFront();

     //trong 9-13 comment this for lmco and drs coz they don't user waste need to put it back for raytheon
     //4-24-01 use this method since we are hitting the server/database then we should also get the info about creating report
     this.isUserWasteManager();

     //CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
     goSearch(3);
  }

  public void isUserWasteManager() {
    try{
      Hashtable result = parent.isWasteManagerData;
      if (result == null) {
        TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",this.parent);
        Hashtable query = new Hashtable();
        query.put("ACTION","IS_WASTE_MANAGER"); //String, String
        query.put("USER_ID",this.getUserId());
        result = cgi.getResultHash(query);
      }
      if (result == null){
        GenericDlg.showAccessDenied(parent.getMain());
        return;
      }
      Integer num = (Integer)result.get("WASTE_MANAGER_FOR");
      if (num.intValue() < 1) {
        menuWasteManage.setEnabled(false);
        menuWasteOrderTrack.setEnabled(false);
        manageWasteB.setEnabled(false);
        wasteOrderTrackB.setEnabled(false);
      }
      //4-24-01
      Integer val = (Integer)result.get("CAN_CREATE_REPORT");
      if (val.intValue() < 1) {
        this.menuReport.setEnabled(false);
        this.reportB.setEnabled(false);
      }

      //6-1-01 main features
      setMainFeature((Hashtable)result.get("TCMIS_FEATURE"));

    }catch(Exception e){
      e.printStackTrace();
      return;
    }
  }

  /*
  public void isUserWasteManager() {
    try{
      Hashtable result = parent.isWasteManagerData;
      Integer num = (Integer)result.get("WASTE_MANAGER_FOR");
      if (num.intValue() < 1) {
        menuWasteManage.setEnabled(false);
        menuWasteOrderTrack.setEnabled(false);
        manageWasteB.setEnabled(false);
        wasteOrderTrackB.setEnabled(false);
      }
      Integer val = (Integer)result.get("CAN_CREATE_REPORT");
      if (val.intValue() < 1) {
        this.menuReport.setEnabled(false);
        this.reportB.setEnabled(false);
      }
      setMainFeature((Hashtable)result.get("TCMIS_FEATURE"));
    }catch(Exception e){
      e.printStackTrace();
      return;
    }
  } */

  //6-1-01
  public void setMainFeature(Hashtable featureH) {
    //catalog screen
    if (Integer.parseInt(featureH.get("main.catalog").toString()) < 1) {
      this.menuMR.setEnabled(false);
      this.catalogB.setEnabled(false);
    }

    //order tracking
    if (Integer.parseInt(featureH.get("main.orderTrack").toString()) < 1) {
      this.menuTrack.setEnabled(false);
      this.trackB.setEnabled(false);
    }

    //inventory
    if (Integer.parseInt(featureH.get("main.inventory").toString()) < 1) {
      this.menuInv.setEnabled(false);
      this.inventB.setEnabled(false);
    }

    //new chemical tracking
    if (Integer.parseInt(featureH.get("main.newChemTrack").toString()) < 1) {
      this.menuNewChemTrack.setEnabled(false);
      this.ncTrackB.setEnabled(false);
    }

    //print screen
    if (Integer.parseInt(featureH.get("main.printScreen").toString()) < 1) {
      this.menuPrint.setEnabled(false);
      this.scrprtB.setEnabled(false);
      showPrintScreen = false;
    }

    //admin
    if (Integer.parseInt(featureH.get("main.admin").toString()) < 1) {
      this.menuAdmin.setEnabled(false);
      this.adminB.setEnabled(false);
    }

    //6-1-01 if client does use waste module the disable it
    if (Integer.parseInt(featureH.get("main.wasteUser").toString()) < 1) {
      menuWaste.setEnabled(false);
      menuWasteTrack.setEnabled(false);
      menuWasteManage.setEnabled(false);
      menuWasteOrderTrack.setEnabled(false);
      wasteB.setEnabled(false);
      wasteTrackB.setEnabled(false);
      manageWasteB.setEnabled(false);
      wasteOrderTrackB.setEnabled(false);
      parent.wasteUser = false;
    }

    //6-27-01 since this is where the others features are setup.  It is not clean
    //but it will be okay for now
    if (Integer.parseInt(featureH.get("main.report.formattedUsage").toString()) < 1) {
      parent.formattedUsage = false;
    }
    if (Integer.parseInt(featureH.get("main.report.formattedVOC").toString()) < 1) {
      parent.formattedVOC = false;
    }
    if (Integer.parseInt(featureH.get("main.report.formattedMSDS").toString()) < 1) {
      parent.formattedMSDS = false;
    }
    if (Integer.parseInt(featureH.get("main.report.adHocUsage").toString()) < 1) {
      parent.adHocUsage = false;
    }
    if (Integer.parseInt(featureH.get("main.report.materialMatrix").toString()) < 1) {
      parent.materialMatrix = false;
    }
    if (Integer.parseInt(featureH.get("main.report.hourlyVOCUsage").toString()) < 1) {
      parent.hourVocUsage = false;
    }

    if (Integer.parseInt(featureH.get("main.rightMouseClick.displayBaselineDate").toString()) > 0) {
      parent.displayBaselineDate = true;
    }
    if (Integer.parseInt(featureH.get("main.rightMouseClick.displayLeadTimeChart").toString()) > 0) {
      parent.displayLeadTimeChart = true;
    }

  } //end of method

  public void setParent(Object parent){
    this.parent = (CmisApp) parent;
    //this.setTitle("tcmIS");
    this.setTitle("Licensed to "+this.parent.getResourceBundle().getString("APP_NAME"));
    if (this.parent.isDevelopment){
       Main_Title_Thread mtt = new Main_Title_Thread(this);
       mtt.start();
    }

    try {
      if (this.parent.isConnectionEncrypted()){
        imgControl.setIcon(ResourceLoader.getImageIcon("images/gif/locksafe.gif"));
      } else {
        imgControl.setIcon(ResourceLoader.getImageIcon("images/gif/unlcksafe.gif"));
        //imgControl.setIcon(new ImageIcon((Image) getToolkit().getImage(imagesDir+"/gif/unlcksafe.gif")));
      }
    } catch (Exception e){ e.printStackTrace(); }

    CmisApp c = (CmisApp)parent;
    if(c.getGroupMembership().isAdminAtSomeFacility() ||
       c.getGroupMembership().isSuperUser()){
    }else{
      menuFile.remove(menuAdmin);
    }
  }

  public CmisApp getCmisApp(){
    return parent;
  }

  public void setWaitCursor(boolean t){}

  //Component initialization
  public void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    //this is for muting the sound
    crb = new ClientResourceBundle();
    crb.setSoundMuted(false);

    // winhelp
    setHelpWin();
    // center screen
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(766, 571));

    this.addWindowListener(new Main_this_windowAdapter(this));
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(screenSize.width/2 - 766/2, screenSize.height/2 -543/2);
    //set xYlayout for bevel

    menuFile.setText("Application");
    menuFile.setFont(new java.awt.Font("Dialog", 0, 10));

    buildBarAction();
    buildBar1Action();

    sndControl.setText("");
    sndControl.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goToggleMute();
      }
    });
    setSoundImage();

    imgControl.setText("");

    progBar.setForeground(Color.darkGray);
    progBar.setMaximumSize(new Dimension(150, 18));
    progBar.setMinimumSize(new Dimension(150, 16));

    panel4.setLayout(cardLayout1);

    panel4.setPreferredSize(new Dimension(90, 37));
    progBar.setPreferredSize(new Dimension(150, 18));


    //tabsetPanel1.setLabels(new String[] {"Catalog"});
    tabsetPanel1.setTabPlacement(JTabbedPane.BOTTOM);
    tabsetPanel1.setFont(new java.awt.Font("Dialog", 0, 10));
    tabsetPanel1.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        ClientHelpObjs.setTabbedPaneForegroundColor(tabsetPanel1);
        setButtonStatus();       //5-10-01
      }
    });

    //trong 3-3-01 right mouse click to delete tab
    tabsetPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        tabsetPanel1_mouseClicked(e);
      }
    });



    // tabsetPanel1.addSelectionListener(new Main_tabsetPanel1_selectionAdapter(this));
    menuTrack.setText("      Chemical Order Tracking");
    menuTrack.setActionCommand("Tracking");
    menuTrack.setFont(new java.awt.Font("Dialog", 0, 10));
    menuTrack.addActionListener(new Main_menuTrack_actionAdapter(this));
    menuDoc.setText("Document Repository");
    menuDoc.setActionCommand("DocMgr");
    menuDoc.setFont(new java.awt.Font("Dialog", 0, 10));
    menuDoc.addActionListener(new Main_menuDoc_actionAdapter(this));
    menuOrder.setText("Order Managment");
    menuOrder.setActionCommand("Order");
    menuOrder.addActionListener(new Main_menuOrder_actionAdapter(this));
    menuNewChemTrack.setText("      Cat Add Tracking");
    menuNewChemTrack.setActionCommand("Cat Add Tracking");
    menuNewChemTrack.setFont(new java.awt.Font("Dialog", 0, 10));
    menuNewChemTrack.addActionListener(new Main_menuNewChemTrack_actionAdapter(this));

    menuEnvironmental.setText("Environmental");
    menuEnvironmental.setActionCommand("Environmental");
    menuEnvironmental.setEnabled(false);
    menuHealth.setText("Health and Safety");
    menuHealth.setActionCommand("Health");
    menuChem.setText("Add New Chemical");
    menuChem.setActionCommand("Chem");
    menuChem.setFont(new java.awt.Font("Dialog", 0, 10));
    menuAdmin.setText("      Administrative Functions");
    menuAdmin.setActionCommand("Administrative Functions");
    menuAdmin.setFont(new java.awt.Font("Dialog", 0, 10));
    menuProfile.setText("      Change User Profile");
    menuProfile.setActionCommand("Change User Profile");
    menuProfile.setFont(new java.awt.Font("Dialog", 0, 10));
    menuProfile.addActionListener(new Main_menuProfile_actionAdapter(this));

    // trong 8-7
    menuWaste.setText("      Waste Catalog");
    menuWaste.setActionCommand("Waste Catalog");
    menuWaste.setFont(new java.awt.Font("Dialog", 0, 10));
    menuWaste.addActionListener(new Main_menuWaste_actionAdapter(this));
    menuWasteTrack.setText("      Waste Transfer Mgt.");
    menuWasteTrack.setActionCommand("Waste Transfer Mgt.");
    menuWasteTrack.setFont(new java.awt.Font("Dialog", 0, 10));
    menuWasteTrack.addActionListener(new Main_menuWasteTrack_actionAdapter(this));

    menuWasteManage.setText("      Waste Management");
    menuWasteManage.setActionCommand("Waste Management");
    menuWasteManage.setFont(new java.awt.Font("Dialog", 0, 10));
    menuWasteManage.addActionListener(new Main_menuWasteManage_actionAdapter(this));
    menuWasteOrderTrack.setText("      Waste Order Tracking");
    menuWasteOrderTrack.setActionCommand("Waste Order Tracking");
    menuWasteOrderTrack.setFont(new java.awt.Font("Dialog", 0, 10));
    menuWasteOrderTrack.addActionListener(new Main_menuWasteOrderTrack_actionAdapter(this));


    menuReport.setText("      Reports");
    menuReport.setActionCommand("Reports");
    menuReport.setFont(new java.awt.Font("Dialog", 0, 10));
    menuReport.addActionListener(new Main_menuReport_actionAdapter(this));
    menuStop.setText("      Stop");
    menuStop.setActionCommand("Stop");
    menuStop.setFont(new java.awt.Font("Dialog", 0, 10));
    menuStop.addActionListener(new Main_menuStop_actionAdapter(this));
    menuRlogin.setText("      Change Logon");
    menuRlogin.setActionCommand("Change Logon");
    menuRlogin.setFont(new java.awt.Font("Dialog", 0, 10));
    menuRlogin.addActionListener(new Main_menuRlogin_actionAdapter(this));
    menuPrint.setText("      Print");
    menuPrint.setActionCommand("Print");
    menuPrint.setFont(new java.awt.Font("Dialog", 0, 10));
    menuPrint.addActionListener(new Main_menuPrint_actionAdapter(this));
    menuHealth.setEnabled(false);
    panel1.setLayout(borderLayout6);
    panel2.setLayout(borderLayout5);
    imgPan.setLayout(xYLayout3);
    //panel3.setLayout(borderLayout4);
    panel5.setLayout(borderLayout7);
    panel6.setLayout(borderLayout4);
    //tabsetPanel1.setLabels(null);
    //tabsetPanel1.setSelectedIndex(-1);
    menuExit.setText("Exit");
    menuExit.setFont(new java.awt.Font("Dialog", 0, 10));
    menuMR.setText("      Chemical Catalog");
    menuMR.setActionCommand("reqMaterial");
    menuMR.setFont(new java.awt.Font("Dialog", 0, 10));
    menuInv.setText("      Chemical Inventory");
    menuInv.setActionCommand("invent");
    menuInv.setFont(new java.awt.Font("Dialog", 0, 10));
    menuHelp.setText("Help");
    menuHelp.setFont(new java.awt.Font("Dialog", 0, 10));
    helpAbout.setText("About");
    helpAbout.setFont(new java.awt.Font("Dialog", 0, 10));
    helpAbout.addActionListener(new Main_helpAbout_actionAdapter(this));
    tcmisHelp.setText("tcmIS Help");
    tcmisHelp.setFont(new java.awt.Font("Dialog", 0, 10));
    tcmisHelp.addActionListener(new Main_tcmisHelp_actionAdapter(this));

    menuMR.addActionListener(new Main_menuMR_actionAdapter(this));
    menuInv.addActionListener(new Main_menuInv_actionAdapter(this));
    menuExit.addActionListener(new main_menuExit_actionAdapter(this));

    JMenuItem menuCTitle = new JMenuItem();
    menuCTitle.setText("Chemical...");
    menuCTitle.setFont(new java.awt.Font("Dialog", 0, 10));
    menuCTitle.disable();
    //menuCTitle.setEnabled(false);

    buttonBar.setAlignmentY((float) 0.2);
    progPanel.setFont(new java.awt.Font("Dialog", 0, 10));
    menuscanDownload.setFont(new java.awt.Font("Dialog", 0, 10));
    menuscanDownload.setToolTipText("");
    menuscanDownload.setActionCommand("Scanner Download");
    menuscanDownload.setSelected(false);
    menuscanDownload.setSelectedIcon(null);
    menuscanDownload.setText("      Cabinet Management");
    menuscanDownload.addActionListener(new Main_menuscanDownload_actionAdapter(this));

    menuInventoryCount.setFont(new java.awt.Font("Dialog", 0, 10));
    menuInventoryCount.setToolTipText("");
    menuInventoryCount.setActionCommand("Inventory Count");
    menuInventoryCount.setSelected(false);
    menuInventoryCount.setSelectedIcon(null);
    menuInventoryCount.setText("      Inventory Count");
    menuInventoryCount.addActionListener(new Main_menuInventoryCount_actionAdapter(this));

    menuFile.add(menuCTitle);
    menuFile.add(menuMR);
    menuFile.add(menuTrack);
    menuFile.add(menuInv);
    menuFile.add(menuNewChemTrack);

    // trong 8-7
    //trong 9-13 comment this for lmco and drs coz they don't user waste need to put it back for raytheon
    menuFile.addSeparator();
    JMenuItem menuWTitle = new JMenuItem();
    menuWTitle.setText("Waste...");
    //menuWTitle.setEnabled(false);
    menuWTitle.disable();
    menuFile.add(menuWTitle);
    menuFile.add(menuWaste);
    menuFile.add(menuWasteTrack);
    menuFile.add(menuWasteManage);
    menuFile.add(menuWasteOrderTrack);

    menuFile.addSeparator();
    JMenuItem menuRTitle = new JMenuItem();
    menuRTitle.setText("Reports...");
    menuRTitle.disable();
    //menuRTitle.setEnabled(false);
    menuFile.add(menuRTitle);
    menuFile.add(menuReport);

    menuFile.addSeparator();
    JMenuItem menuATitle = new JMenuItem();
    menuATitle.setText("Maintenance...");
    menuATitle.disable();
    //menuATitle.setEnabled(false);
    menuFile.add(menuATitle);
    menuFile.add(menuProfile);
    menuFile.add(menuAdmin);
    menuFile.add(menuscanDownload);
    menuFile.add(menuInventoryCount);

    menuFile.addSeparator();
    JMenuItem menuUTitle = new JMenuItem();
    menuUTitle.setText("Utilities...");
    menuUTitle.disable();
    //menuUTitle.setEnabled(false);
    menuFile.add(menuUTitle);
    menuFile.add(menuStop);
    menuFile.add(menuRlogin);
    menuFile.add(menuPrint);

    menuWTitle.setFont(new java.awt.Font("Dialog", 0, 10));
    menuATitle.setFont(new java.awt.Font("Dialog", 0, 10));
    menuRTitle.setFont(new java.awt.Font("Dialog", 0, 10));
    menuUTitle.setFont(new java.awt.Font("Dialog", 0, 10));

    menuFile.addSeparator();
    menuFile.add(menuExit);

    menuBar1.add(menuFile);
    menuBar1.add(menuHelp);

    progPanel.setLayout(xYLayout4);
    imgPan.add(sndControl, new XYConstraints(1, 5, 20, 16));
    imgPan.add(imgControl, new XYConstraints(19, 5, 20, 16));
    progPanel.add(progBar, new XYConstraints(1, 5, 140, 16));
    progPanel.setMaximumSize(new Dimension(150, 21));
    progPanel.setPreferredSize(new Dimension(150, 21));
    panel2.add(imgPan, BorderLayout.EAST);
    panel2.add(progPanel, BorderLayout.CENTER);

    animationButton.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
    animationButton.setMaximumSize(new Dimension(88,40));

    this.setIconImage(ResourceLoader.getImageIcon("images/gif/tcmicon.gif").getImage());
    //this.setIconImage((Image) getToolkit().getImage(imagesDir+"/gif/tcmicon.gif"));

    this.setJMenuBar(menuBar1);
    this.getContentPane().add(tabsetPanel1, BorderLayout.CENTER);
    this.getContentPane().add(buttonBar, BorderLayout.NORTH);

    Border bb = buttonBar.getBorder();
    //9-24-01 buttonBar.setBorder( new CompoundBorder(new BevelBorder(BevelBorder.RAISED),bb));
    buttonBar.setFloatable(true);
    buttonBar.setPreferredSize(new Dimension(766,45));
    buttonBar.setMaximumSize(new Dimension(766,45));
    buttonBar.setMinimumSize(new Dimension(766,45));
    //9-24-01 tabsetPanel1.setBorder(new BevelBorder(BevelBorder.RAISED));
    bottomBar.add(statusBar);
    bottomBar.add(panel2);
    bottomBar.setPreferredSize(new Dimension(766,25));
    bottomBar.setMaximumSize(new Dimension(898989,25));
    bottomBar.setMinimumSize(new Dimension(766,25));
    bb = buttonBar.getBorder();

    this.getContentPane().add(bottomBar, BorderLayout.SOUTH);
    countLabel.put(new Integer(SEARCHPT),"");
    countLabel.put(new Integer(INVENTORY),"");
    countLabel.put(new Integer(REQUEST),"");
    countLabel.put(new Integer(TRACKING),"");
    countLabel.put(new Integer(NEW_CHEM),"");
    countLabel.put(new Integer(NEW_CHEM_MAT),"");
    countLabel.put(new Integer(ADMIN),"");
    countLabel.put(new Integer(NEW_CHEM_TRACKING),"");

    // trong 8-7
    countLabel.put(new Integer(WASTE),"");
    countLabel.put(new Integer(MANAGE_WASTE),"");
    countLabel.put(new Integer(WASTE_TRACKING),"");


    countLabel.put(new Integer(REPORT),"");
    menuHelp.add(tcmisHelp);
    menuHelp.add(helpAbout);
    ClientHelpObjs.setTabbedPaneForegroundColor(tabsetPanel1);
  }

  public SearchPT getCurrentScreen(){
      //scrPanel =  (SearchPT) tabsetPanel1.getSelectedPage();
      return  search;
  }

  public void print(){
    Toolkit tK = getToolkit().getDefaultToolkit();
    PrintJob pJob = tK.getPrintJob(null, "Printing Screen",null);
    if (pJob != null){
      Graphics graph = pJob.getGraphics();
      ((Container) tabsetPanel1.getSelectedComponent()).printAll(graph);
      graph.dispose();
      pJob.end();
    }
  }

  protected void buildBarAction() {
    buttonBar.setBorderPainted(true);
    buttonBar.setFloatable(true);

    // panel5 not being used now.....
    panel5.setPreferredSize(new Dimension(400,37));
    panel5.setMaximumSize(new Dimension(666,37));

    animationButton.setMaximumSize(new Dimension(88,40));
    animationButton.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
    buttonBar.add(animationButton);

    //buttonBar.addSeparator();
    //buttonBar.addSeparator();
    //buttonBar.addSeparator();
    //buttonBar.addSeparator();
    //buttonBar.addSeparator();

    buttonBar.addSeparator(new Dimension(4,42));

    /*
    sepB.setPreferredSize(new Dimension(4,42));
    sepB.setMinimumSize(new Dimension(4,42));
    sepB.setMaximumSize(new Dimension(4,42));
    sepB.setBorder(null);
    sepB.setIcon(sb);
    buttonBar.add(sepB);*/

    ImageIcon sb = ResourceLoader.getImageIcon("images/button/sep.jpg");

    //catalog --------------------------------------  (panel3)
    ImageIcon cb = ResourceLoader.getImageIcon("images/button/chemical1.jpg");

    catalogB.setToolTipText("Chemical Catalog ");
    catalogB.setPreferredSize(new Dimension(44,42));
    catalogB.setMinimumSize(new Dimension(44,42));
    catalogB.setMaximumSize(new Dimension(44,42));
    catalogB.setBorder(null);
    catalogB.setIcon(cb);
    catalogB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goSearch(1);
      }
    });
    catalogB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Chemical Catalog");
        catalogB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        catalogB.setToolTipText("Chemical Catalog ");
        ImageIcon ss = ResourceLoader.getImageIcon("images/button/chemical3.jpg");
        catalogB.setIcon(ss);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        catalogB.setBorder(null);

        ImageIcon ss = ResourceLoader.getImageIcon("images/button/chemical1.jpg");
        catalogB.setIcon(ss);
      }
      public void mousePressed(MouseEvent e) {
        catalogB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        catalogB_mouseReleased();
      }
    });
    buttonBar.add(catalogB);

    // order tracking ------------------------------------------
//    trackB = ClientHelpObjs.getStandardJButton("","status_chem.gif","Chemical Order Tracking",ClientHelpObjs.BORDER_RAISED,30,30);
    ImageIcon tb = ResourceLoader.getImageIcon("images/button/orders1.jpg");
    trackB.setToolTipText("Chemical Order Tracking ");
    trackB.setPreferredSize(new Dimension(44,42));
    trackB.setMinimumSize(new Dimension(44,42));
    trackB.setMaximumSize(new Dimension(44,42));
    trackB.setBorder(null);
    trackB.setIcon(tb);
    trackB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goTracking(1);
      }
    });
    trackB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Chemical Order Tracking");
        trackB.setToolTipText("Chemical Order Tracking ");
        trackB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon ss = ResourceLoader.getImageIcon("images/button/orders3.jpg");
        trackB.setIcon(ss);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        trackB.setBorder(null);
        ImageIcon ss = ResourceLoader.getImageIcon("images/button/orders1.jpg");
        trackB.setIcon(ss);
      }
      public void mousePressed(MouseEvent e) {
        trackB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        trackB_mouseReleased();
      }
    });
    buttonBar.add(trackB);

    //inventory -------------------------------------
//    inventB = ClientHelpObjs.getStandardJButton("","inven.gif","Inventory",ClientHelpObjs.BORDER_RAISED,30,30);
    ImageIcon ib = ResourceLoader.getImageIcon("images/button/inventory1.jpg");
    inventB.setToolTipText("Inventory ");
    inventB.setPreferredSize(new Dimension(44,42));
    inventB.setMinimumSize(new Dimension(44,42));
    inventB.setMaximumSize(new Dimension(44,42));
    inventB.setBorder(null);
    inventB.setIcon(ib);
    inventB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goInvent(1);
      }
    });
    inventB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Inventory");
        inventB.setToolTipText("Inventory ");
        inventB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon ib = ResourceLoader.getImageIcon("images/button/inventory3.jpg");
        inventB.setIcon(ib);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        inventB.setBorder(null);
        ImageIcon ib = ResourceLoader.getImageIcon("images/button/inventory1.jpg");
        inventB.setIcon(ib);
      }
      public void mousePressed(MouseEvent e) {
        inventB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        inventB_mouseReleased();
      }
    });
    buttonBar.add(inventB);

    // new chem tracking -----------------------------------------------------
//    ncTrackB = ClientHelpObjs.getStandardJButton("","nctrack.gif","New Chemical Tracking",ClientHelpObjs.BORDER_RAISED,30,30);
    ImageIcon nb = ResourceLoader.getImageIcon("images/button/newcat1.jpg");
    ncTrackB.setToolTipText("Cat Add Tracking ");
    ncTrackB.setPreferredSize(new Dimension(44,42));
    ncTrackB.setMinimumSize(new Dimension(44,42));
    ncTrackB.setMaximumSize(new Dimension(44,42));
    ncTrackB.setBorder(null);
    ncTrackB.setIcon(nb);
    ncTrackB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goNewChemTrack(1);
      }
    });
    ncTrackB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Cat Add Tracking");
        ncTrackB.setToolTipText("Cat Add Tracking ");
        ncTrackB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon nb = ResourceLoader.getImageIcon("images/button/newcat3.jpg");
        ncTrackB.setIcon(nb);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        ncTrackB.setBorder(null);
        ImageIcon nb = ResourceLoader.getImageIcon("images/button/newcat1.jpg");
        ncTrackB.setIcon(nb);
      }
      public void mousePressed(MouseEvent e) {
        ncTrackB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        ncTrackB_mouseReleased();
      }
    });
    buttonBar.add(ncTrackB);

    // trong 8-7
    // waste catalog -----------------------------------------------------
    //trong 9-13 comment this for lmco and drs coz they don't user waste need to put it back for raytheon

    //Nawaz 26-09-01 Remove the separators to use the space available. Did the same with other statements in this class
    //buttonBar.addSeparator();
    //buttonBar.addSeparator();

    //buttonBar.addSeparator(new Dimension(3,42));
    sepB1.setPreferredSize(new Dimension(4,42));
    sepB1.setMinimumSize(new Dimension(4,42));
    sepB1.setMaximumSize(new Dimension(4,42));
    sepB1.setBorder(null);
    sepB1.setIcon(sb);
    buttonBar.add(sepB1);


    ImageIcon wb = ResourceLoader.getImageIcon("images/button/waste1.jpg");
    wasteB.setToolTipText("Waste Catalog ");
    wasteB.setPreferredSize(new Dimension(44,42));
    wasteB.setMinimumSize(new Dimension(44,42));
    wasteB.setMaximumSize(new Dimension(44,42));
    wasteB.setBorder(null);
    wasteB.setIcon(wb);

    wasteB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goWaste(1);
      }
    });
    wasteB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Waste Catalog");
        wasteB.setToolTipText("Waste Catalog ");
        wasteB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon wb = ResourceLoader.getImageIcon("images/button/waste3.jpg");
        wasteB.setIcon(wb);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        wasteB.setBorder(null);
        ImageIcon wb = ResourceLoader.getImageIcon("images/button/waste1.jpg");
        wasteB.setIcon(wb);
      }
      public void mousePressed(MouseEvent e) {
        wasteB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        wasteB_mouseReleased();
      }
    });

    buttonBar.add(wasteB);
    //wasteBar.add(wasteB);

    // waste pickup tracking -------------------------------------------------
    ImageIcon wtb = ResourceLoader.getImageIcon("images/button/pickup1.jpg");
    wasteTrackB.setToolTipText("Waste Transfer Mgt.");
    wasteTrackB.setPreferredSize(new Dimension(44,42));
    wasteTrackB.setMinimumSize(new Dimension(44,42));
    wasteTrackB.setMaximumSize(new Dimension(44,42));
    wasteTrackB.setBorder(null);
    wasteTrackB.setIcon(wtb);
    wasteTrackB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goWasteTracking(1);
      }
    });
    wasteTrackB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Waste Transfer Mgt.");
        wasteTrackB.setToolTipText("Waste Transfer Mgt.");
        wasteTrackB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon wtb = ResourceLoader.getImageIcon("images/button/pickup3.jpg");
        wasteTrackB.setIcon(wtb);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        wasteTrackB.setBorder(null);
        ImageIcon wtb = ResourceLoader.getImageIcon("images/button/pickup1.jpg");
        wasteTrackB.setIcon(wtb);
      }
      public void mousePressed(MouseEvent e) {
        wasteTrackB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        wasteTrackB_mouseReleased();
      }
    });
    buttonBar.add(wasteTrackB);
    //wasteBar.add(wasteTrackB,null);

    // manage waste -----------------------------------------------------
    ImageIcon mwb = ResourceLoader.getImageIcon("images/button/mgt1.jpg");
    manageWasteB.setToolTipText("Waste Management ");
    manageWasteB.setPreferredSize(new Dimension(44,42));
    manageWasteB.setMinimumSize(new Dimension(44,42));
    manageWasteB.setMaximumSize(new Dimension(44,42));
    manageWasteB.setBorder(null);
    manageWasteB.setIcon(mwb);
    manageWasteB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goWasteManage(1);
      }
    });
    manageWasteB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Waste Management");
        manageWasteB.setToolTipText("Waste Management ");
        manageWasteB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon mwb = ResourceLoader.getImageIcon("images/button/mgt3.jpg");
        manageWasteB.setIcon(mwb);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        manageWasteB.setBorder(null);
        ImageIcon mwb = ResourceLoader.getImageIcon("images/button/mgt1.jpg");
         manageWasteB.setIcon(mwb);
      }
      public void mousePressed(MouseEvent e) {
        manageWasteB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        manageWasteB_mouseReleased();
      }
    });
    buttonBar.add(manageWasteB);
    //wasteBar.add(manageWasteB,null);

    //waste order tracking ---------------------------------------------------
    ImageIcon wob = ResourceLoader.getImageIcon("images/button/shipping1.jpg");
    wasteOrderTrackB.setToolTipText("Waste Order Tracking ");
    wasteOrderTrackB.setPreferredSize(new Dimension(44,42));
    wasteOrderTrackB.setMinimumSize(new Dimension(44,42));
    wasteOrderTrackB.setMaximumSize(new Dimension(44,42));
    wasteOrderTrackB.setBorder(null);
    wasteOrderTrackB.setIcon(wob);
    wasteOrderTrackB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goWasteOrderTracking(1);
      }
    });
    wasteOrderTrackB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Waste Order Tracking");
        wasteOrderTrackB.setToolTipText("Waste Order Tracking ");
        wasteOrderTrackB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon wob = ResourceLoader.getImageIcon("images/button/shipping3.jpg");
        wasteOrderTrackB.setIcon(wob);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        wasteOrderTrackB.setBorder(null);
        ImageIcon wob = ResourceLoader.getImageIcon("images/button/shipping1.jpg");
        wasteOrderTrackB.setIcon(wob);
      }
      public void mousePressed(MouseEvent e) {
        wasteOrderTrackB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        wasteOrderTrackB_mouseReleased();
      }
    });
    buttonBar.add(wasteOrderTrackB);

     //report -----------------------------------------------------------------
    sepB2.setMinimumSize(new Dimension(4,42));
    sepB2.setMaximumSize(new Dimension(4,42));
    sepB2.setBorder(null);
    sepB2.setIcon(sb);
    buttonBar.add(sepB2);


    ImageIcon rb = ResourceLoader.getImageIcon("images/button/reports1.jpg");
    reportB.setToolTipText("Reports ");
    reportB.setPreferredSize(new Dimension(44,42));
    reportB.setMinimumSize(new Dimension(44,42));
    reportB.setMaximumSize(new Dimension(44,42));
    reportB.setBorder(null);
    reportB.setIcon(rb);
    reportB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goReport(1);
      }
    });
    reportB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Reports");
        reportB.setToolTipText("Reports ");
        reportB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon rb = ResourceLoader.getImageIcon("images/button/reports3.jpg");
        reportB.setIcon(rb);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        reportB.setBorder(null);
        ImageIcon rb = ResourceLoader.getImageIcon("images/button/reports1.jpg");
        reportB.setIcon(rb);
      }
      public void mousePressed(MouseEvent e) {
        reportB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        reportB_mouseReleased();
      }
    });
    buttonBar.add(reportB);


    // user profile ----------------------------------------------
    sepB3.setPreferredSize(new Dimension(4,42));
    sepB3.setMinimumSize(new Dimension(4,42));
    sepB3.setMaximumSize(new Dimension(4,42));
    sepB3.setBorder(null);
    sepB3.setIcon(sb);
    buttonBar.add(sepB3);


    ImageIcon pb = ResourceLoader.getImageIcon("images/button/users1.jpg");
    profileB.setToolTipText("User Profile ");
    profileB.setPreferredSize(new Dimension(44,42));
    profileB.setMinimumSize(new Dimension(44,42));
    profileB.setMaximumSize(new Dimension(44,42));
    profileB.setBorder(null);
    profileB.setIcon(pb);
    profileB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goUserProfile(1);
      }
    });
    profileB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("User Profile");
        profileB.setToolTipText("User Profile ");
        profileB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon pb = ResourceLoader.getImageIcon("images/button/users3.jpg");
        profileB.setIcon(pb);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        profileB.setBorder(null);
        ImageIcon pb = ResourceLoader.getImageIcon("images/button/users1.jpg");
        profileB.setIcon(pb);
      }
      public void mousePressed(MouseEvent e) {
        profileB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        profileB_mouseReleased();
      }
    });
    buttonBar.add(profileB);

    //admin -----------------------------------------------------------------
    ImageIcon ab = ResourceLoader.getImageIcon("images/button/admin1.jpg");
    adminB.setToolTipText("Administrative Functions ");
    adminB.setPreferredSize(new Dimension(44,42));
    adminB.setMinimumSize(new Dimension(44,42));
    adminB.setMaximumSize(new Dimension(44,42));
    adminB.setBorder(null);
    adminB.setIcon(ab);
    adminB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goAdmin(1);
      }
    });
    adminB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Administrative Functions");
        adminB.setToolTipText("Administrative Functions ");
        adminB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon ss = ResourceLoader.getImageIcon("images/button/admin3.jpg");
        adminB.setIcon(ss);

      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        adminB.setBorder(null);
        ImageIcon ss = ResourceLoader.getImageIcon("images/button/admin1.jpg");
        adminB.setIcon(ss);

      }
      public void mousePressed(MouseEvent e) {
        adminB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        adminB_mouseReleased();
      }
    });
    buttonBar.add(adminB);



    // add panel3 --------------------------------------------
    /*
    panel3.setMaximumSize(new Dimension(99999,40));
    panel3.setPreferredSize(new Dimension(300,40));
    buttonBar.add(panel3);
    */

  }

  protected void buildBar1Action() {
    //Actions
    //buttonBar.addSeparator();
    //buttonBar.addSeparator();
    //buttonBar.addSeparator(new Dimension(3,42));
    ImageIcon sb1 = ResourceLoader.getImageIcon("images/button/sep.jpg");
    sepB4.setPreferredSize(new Dimension(4,42));
    sepB4.setMinimumSize(new Dimension(4,42));
    sepB4.setMaximumSize(new Dimension(4,42));
    sepB4.setBorder(null);
    sepB4.setIcon(sb1);
    buttonBar.add(sepB4);


    // stop --------------------------------------------------------((panel7)------
    ImageIcon sb = ResourceLoader.getImageIcon("images/button/stop1.jpg");
    stopB.setToolTipText("Stop ");
    stopB.setPreferredSize(new Dimension(44,42));
    stopB.setMinimumSize(new Dimension(44,42));
    stopB.setMaximumSize(new Dimension(44,42));
    stopB.setBorder(null);
    stopB.setIcon(sb);
    stopB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goStop();
      }
    });
    stopB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Stop");
        stopB.setToolTipText("Stop ");
        stopB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon sb = ResourceLoader.getImageIcon("images/button/stop3.jpg");
        stopB.setIcon(sb);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        stopB.setBorder(null);
        ImageIcon sb = ResourceLoader.getImageIcon("images/button/stop1.jpg");
        stopB.setIcon(sb);
      }
      public void mousePressed(MouseEvent e) {
        stopB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        stopB_mouseReleased();
      }
    });
    buttonBar.add(stopB);


    // relogon --------------------------------------------------------------
//    relogonB = ClientHelpObjs.getStandardJButton("","relogon.gif","Change Logon",ClientHelpObjs.BORDER_RAISED,30,30);
    ImageIcon rlb = ResourceLoader.getImageIcon("images/button/relogin1.jpg");
    relogonB.setToolTipText("Change Logon ");
    relogonB.setPreferredSize(new Dimension(44,42));
    relogonB.setMinimumSize(new Dimension(44,42));
    relogonB.setMaximumSize(new Dimension(44,42));
    relogonB.setBorder(null);
    relogonB.setIcon(rlb);
    relogonB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goRelogon();
      }
    });
    relogonB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Change Logon");
        relogonB.setToolTipText("Change Logon ");
        relogonB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon rlb = ResourceLoader.getImageIcon("images/button/relogin3.jpg");
        relogonB.setIcon(rlb);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        relogonB.setBorder(null);
        ImageIcon rlb = ResourceLoader.getImageIcon("images/button/relogin1.jpg");
        relogonB.setIcon(rlb);
      }
      public void mousePressed(MouseEvent e) {
        relogonB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        relogonB_mouseReleased();
      }
    });
    //Nawaz 01-17-02
    //buttonBar.add(relogonB);

    // screen print -----------------------------------------------------------------
//    scrprtB = ClientHelpObjs.getStandardJButton("","screenprint.gif","Print Screen Data",ClientHelpObjs.BORDER_RAISED,30,30);
    ImageIcon spb = ResourceLoader.getImageIcon("images/button/printscn1.jpg");
    scrprtB.setToolTipText("Print Screen Data ");
    scrprtB.setPreferredSize(new Dimension(44,42));
    scrprtB.setMinimumSize(new Dimension(44,42));
    scrprtB.setMaximumSize(new Dimension(44,42));
    scrprtB.setBorder(null);
    scrprtB.setIcon(spb);
    scrprtB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goPrintScreenData();
      }
    });
    scrprtB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        setStatusBarText("Print Screen Data");
        scrprtB.setToolTipText("Print Screen Data ");
        scrprtB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        ImageIcon spb = ResourceLoader.getImageIcon("images/button/printscn3.jpg");
        scrprtB.setIcon(spb);
      }
      public void mouseExited(MouseEvent e) {
        setStatusBarText("");
        scrprtB.setBorder(null);
        ImageIcon spb = ResourceLoader.getImageIcon("images/button/printscn1.jpg");
        scrprtB.setIcon(spb);
      }
      public void mousePressed(MouseEvent e) {
        scrprtB_mousePressed();
      }
      public void mouseReleased(MouseEvent e) {
        scrprtB_mouseReleased();
      }
    });
    buttonBar.add(scrprtB);
  }

  public void setStatusBarText(String t){
     statusBar.setText(t);
  }

  //File | Exit action performed
  public void filemenuExit_actionPerformed(ActionEvent e) {
    parent.exit();
  }

  //Help | About action performed
 void helpAbout_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    AboutBox dlg = new AboutBox((Main) this,"About");
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }
  void tcmisHelp_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    ClientHelpObjs.goHelp(getCmisApp());
  }

  // help Pop
  protected void setHelpPop(String item){
   helpPop = new PopupMenu("help");
   helpPop.add(new MenuItem(item));

  }

  protected void setHelpWin(){
   helpWin = new Window(this);
   helpWin.setBackground(new Color(255, 255, 190));
  }

  protected void setHelpWinLabel(String item){
   Label l = new Label();
   l.setText("    " + item);
   Rectangle rec = l.getBounds();
   rec.height = rec.height - 2;
   rec.width = rec.width - 1;
   l.setBounds(rec);
   helpWin.removeAll();
   helpWin.add(l,BorderLayout.CENTER);
   helpWin.setSize(l.getMinimumSize());
   helpWin.validate();
  }

  public Integer getUserId(){
    return parent.getUserId();
  }

  void goPrintScreen(){
    this.print();

  }

  void goPrintScreenData(){
    if (tabsetPanel1 == null) return;
    Component comp = tabsetPanel1.getSelectedComponent();
    if (comp == null) return;

    String label  = null;
    if (comp instanceof SearchPT){
      ((SearchPT)comp).printTableData();
    }else if(comp instanceof UserProfileWin){
      ((UserProfileWin)comp).printScreenData();
    }else if(comp instanceof RequestsWin){
      ((RequestsWin)comp).printScreenData();
    }else if(comp instanceof InventWin){
      ((InventWin)comp).printScreenData();
    }else if(comp instanceof TrackWin){
      ((TrackWin)comp).printScreenData();
    }else if(comp instanceof WastePanel){
      ((WastePanel)comp).printScreenData();
    }else if(comp instanceof WasteTrackWin){
      ((WasteTrackWin)comp).printScreenData();
    }else if(comp instanceof WasteManage){
      ((WasteManage)comp).printScreenData();
    }else if(comp instanceof WasteOrderTrack){
      ((WasteOrderTrack)comp).printScreenData();
    }else if(comp instanceof AdminWin){
      ((AdminWin)comp).printScreenData();
    }else if(comp instanceof NChemTrackWin){
      ((NChemTrackWin)comp).printScreenData();
    }else if(comp instanceof NewChem){
      ((NewChem)comp).printScreenData();
    }else if(comp instanceof WastePickupRequest){
      ((WastePickupRequest)comp).printScreenData();
    }else if(comp instanceof WasteOrder){
      ((WasteOrder)comp).printScreenData();
    }

  }


  void goPrintReport(){
    this.print();

  }

  void goWebConnect(){
  /*
    Runtime r = Runtime.getRuntime();
    String wDir = new String(System.getProperty("user.dir"));
    try {
      String prog = "jre\\1.1\\bin\\jrew.exe -classpath jre\\1.1\\lib\\rt.jar;classes\\jbcl.zip;classes\\jgl.zip;classes\\tcmISClBoth.jar;classes\\tcmISClShare.jar;classes\\swingall.jar;classes radian.tcmis.client.share.gui.WebConnectMain" ;
      r.exec(prog);
    } catch (Exception e0) {
      e0.printStackTrace();
    }
*/
  }

  void goDocMgr(int i){
  }

  void goStop(){
       this.stopImage();
       CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
       progBar.setValue(progBar.getMaximum());
       stopped  = true;

       Thread [] tA = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
       Thread.currentThread().getThreadGroup().enumerate(tA);
       for (int i=0;i<tA.length;i++){
          if (tA[i].getName().equals("SearchPTThread")){
             tA[i].stop();
             search.stateChanged("Load stopped");
          }
          if (tA[i].getName().equals("SearchPTLoadThread")){
             tA[i].stop();
             search.stateChanged("Load stopped");
          }
          if (tA[i].getName().equals("InventThread")){
             tA[i].stop();
             IW.stateChanged("Load stopped");
          }
          if (tA[i].getName().equals("InventLoadThread")){
             tA[i].stop();
             IW.stateChanged("Load stopped");
          }
          if (tA[i].getName().equals("Loging")){
             tA[i].stop();
          }
          if (tA[i].getName().equals("TrackThread")){
             tA[i].stop();
             TW.stateChanged("Load stopped");
          }
          if (tA[i].getName().equals("TrackLoadThread")){
             tA[i].stop();
             TW.stateChanged("Load stopped");
          }
          if (tA[i].getName().equals("UserProfileThread")){
             tA[i].stop();
             UP.stateChanged("Load stopped");
          }
          if (tA[i].getName().equals("UserProfileLoadThread")){
             tA[i].stop();

          }
          if (tA[i].getName().equals("InventFloatThread")){
             tA[i].stop();
          }
          if (tA[i].getName().equals("ProgThread")){
             tA[i].stop();
          }
          if (tA[i].getName().equals("AdminWinLoadThread")){
             tA[i].stop();
             admin.stateChanged("Load stopped");
          }
          if (tA[i].getName().equals("NewChemTrackWinLoadThread")){
             tA[i].stop();
             ncTrack.stateChanged("Load stopped");
          }
          if (tA[i].getName().equals("ReportWinLoadThread")){
             tA[i].stop();
             report.stateChanged("Load stopped");
          }
       }
  }

  void goSearch(int i) {
     //if (i != 3) this.startImage();
     if (profileChanged.contains(new Integer(SEARCHPT))){
       profileChanged.removeElement(new Integer(SEARCHPT));
     }

     mT = new Main_MoveFrameThread(this,SEARCHPT,i,0);
     mT.start();
  }

  void goSearchAction(int i){
     if(i == 1 || i == 3){
       //if i == 1 then go to server to load catalog screen data again
       if (i == 1) {
        parent.loadAllData = null;
       }
       search = null;
       System.gc();
       search = new SearchPT(parent);
       search.updateMR = false;
       search.setPrNumber(0);
       search.loadIt();
     }

     replaceWin(search,ResourceLoader.getImageIcon("images/button/catalog_tab.jpg"),"Chemical");
     search.setSplitLocation();
  }
  //this is will be call in MR screen when user hit 'Return to Catalog'
  void goSearchAction(int i, int pr){
    parent.loadAllData = null;
    search = null;
    System.gc();
    search = new SearchPT(parent);
    search.updateMR = true;
    search.setPrNumber(pr);
    search.loadIt();

    replaceWin(search,ResourceLoader.getImageIcon("images/button/catalog_tab.jpg"),"Chemical");
    search.setSplitLocation();

    deleteTab(RW,"Material Request");
  }

  /*
  public final void goNewChem(int i) {
     this.startImage();
     if (profileChanged.contains(new Integer(NEW_CHEM))){
       profileChanged.removeElement(new Integer(NEW_CHEM));
     }

     mT = new Main_MoveFrameThread(this,NEW_CHEM,i,0);
     mT.start();
  }

  void goNewChemAction(int i){
     if(i == 1){
       newchem = null;
       newchem = new NewChemWin();
       newchem.setParent(parent);
       newchem.loadIt();
     }

     newchem.setVisible(true);
     replaceWin(newchem,new ImageIcon((Image) getToolkit().getImage(imagesDir+"/button/nwmat.gif")),"New Chemical");
  }
  */
  void goInvent(int i) {
     //this.startImage();
     if (profileChanged.contains(new Integer(INVENTORY))){
       profileChanged.removeElement(new Integer(INVENTORY));
     }
     mT = new Main_MoveFrameThread(this,INVENTORY,i,0);
     mT.start();
  }

  void goInventAction(int i) {
    if(i == 1){
       parent.facLocAppData = null;
       IW = null;
       IW = new InventWin(parent);
       //IW.setParent(parent);
       IW.loadIt();
    }

    IW.setVisible(true);
    replaceWin(IW,ResourceLoader.getImageIcon("images/button/inven_tab.gif"),"Inventory");

    //this.stopImage();
  }


  void goTracking(int i) {
     //this.startImage();
     if (profileChanged.contains(new Integer(TRACKING))){
       profileChanged.removeElement(new Integer(TRACKING));
     }

     mT = new Main_MoveFrameThread(this,TRACKING,i,0);
     mT.start();
  }

  void goTrackingAction(int  i) {
    parent.facLocAppData = null;
    if(i == 1){
       TW = null;
       TW = new TrackWin(parent);
       //TW.setParent(parent);
       TW.loadIt();
    }

    TW.setVisible(true);
    replaceWin(TW,ResourceLoader.getImageIcon("images/button/status_tab.gif"),"Order Tracking");


    //this.stopImage();
  }

  //******************* trong change 1/7/00
  public void goWasteTracking(int i) {
     //this.startImage();
     if (profileChanged.contains(new Integer(WASTE_TRACKING))){
       profileChanged.removeElement(new Integer(WASTE_TRACKING));
     }

     mT = new Main_MoveFrameThread(this,WASTE_TRACKING,i,0);
     mT.start();
  }
  void goWasteTrackingAction(int  i) {
    if(i == 1){
       wasteTrack = null;
       wasteTrack = new WasteTrackWin();
       wasteTrack.setCmis(parent);
    }
    replaceWin(wasteTrack,ResourceLoader.getImageIcon("images/button/forklift1.gif"),"Waste Transfer Mgt.");
    wasteTrack.setVisible(true);
    //this.stopImage();
  }

  public void goWasteOrderTracking(int i) {
     //this.startImage();
     if (profileChanged.contains(new Integer(WASTE_ORDER_TRACKING))){
       profileChanged.removeElement(new Integer(WASTE_ORDER_TRACKING));
     }

     mT = new Main_MoveFrameThread(this,WASTE_ORDER_TRACKING,i,0);
     mT.start();
  }

  void goWasteOrderTrackingAction(int  i) {
   parent.facLocAppData = null;
   if(i == 1){
       wasteOrderTrack = null;
       wasteOrderTrack = new WasteOrderTrack(parent);
      // wasteOrderTrack.setCmis(parent);
    }
    replaceWin(wasteOrderTrack,ResourceLoader.getImageIcon("images/button/shippingsmall.jpg"),"Waste Order Tracking");
    wasteOrderTrack.setVisible(true);
    //this.stopImage();
  }

  void goAdmin(int i) {
     //this.startImage();
     if (profileChanged.contains(new Integer(ADMIN))){
       profileChanged.removeElement(new Integer(ADMIN));
     }

     mT = new Main_MoveFrameThread(this,ADMIN,i,0);
     mT.start();
  }

  void goAdminAction(int  i) {
    parent.facLocAppData = null;
    if(i == 1){
       admin = null;
       admin = new AdminWin(parent);
       //admin = new AdminWinNew(parent,search.facChoice.getFacIds(),search.facChoice.getFacNames());
       //admin = new AdminWinNew(parent);
       admin.loadIt();
    }

    admin.setVisible(true);
    replaceWin(admin,ResourceLoader.getImageIcon("images/button/adminsmall.jpg"),"Administrative");
  }

  void goNewChemTrack(int i) {
     //this.startImage();
     if (profileChanged.contains(new Integer(NEW_CHEM_TRACKING))){
       profileChanged.removeElement(new Integer(NEW_CHEM_TRACKING));
     }

     mT = new Main_MoveFrameThread(this,NEW_CHEM_TRACKING,i,0);
     mT.start();
  }

  void goNewChemTrackAction(int  i) {
    if(i == 1){
       ncTrack = null;
       ncTrack = new NChemTrackWin(parent);
       //ncTrack.setParent(parent);
       ncTrack.loadIt();
    }
    ncTrack.setVisible(true);
    replaceWin(ncTrack,ResourceLoader.getImageIcon("images/button/nctrack_tab.gif"),"Cat Add Tracking");
  }


  void goReport(int i) {
     if (profileChanged.contains(new Integer(REPORT))){
       profileChanged.removeElement(new Integer(REPORT));
     }

     mT = new Main_MoveFrameThread(this,REPORT,i,0);
     mT.start();
  }

  void goReportAction(int  i) {
    parent.facLocAppData = null;
    if(i == 1){
       report = null;
       report = new ReportWin(parent);
    }

    report.setVisible(true);
    replaceWin(report,ResourceLoader.getImageIcon("images/button/reportsmall.jpg"),"Reports");
  }

  void goUserProfile(int i) {
     //this.startImage();
     if (profileChanged.contains(new Integer(USER_PROFILE))){
       profileChanged.removeElement(new Integer(USER_PROFILE));
     }
     mT = new Main_MoveFrameThread(this,USER_PROFILE,i,0);
     mT.start();
  }

  void goUserProfileAction(int  i) {
    if(i == 1){
       UP = null;
       UP = new UserProfileWin(parent);
       //UP.setParent(parent);
       UP.loadIt();
    }

    UP.setVisible(true);
    replaceWin(UP,ResourceLoader.getImageIcon("images/button/profile_tab.gif"),"Change User Profile");

  }

  public void goWaste(int i) {
     //this.startImage();
     mT = new Main_MoveFrameThread(this,WASTE,i,0);
     mT.start();
  }

  void goWasteAction(int  i) {
    waste = null;
    waste = new WastePanel();
    waste.setCmis(parent);
    replaceWin(waste,ResourceLoader.getImageIcon("images/button/drumsmall.jpg"),"Waste Catalog");
    waste.setVisible(true);
    //this.stopImage();
  }

  //adding manage waste panel onto the main panel 1/24
   public void goWasteManage(int i) {
     //this.startImage();
     mT = new Main_MoveFrameThread(this,MANAGE_WASTE,i,0);
     mT.start();
  }
  void goWasteManageAction(int  i) {
    parent.facLocAppData = null;
    if(i == 1){
       WasteManage = null;
       WasteManage = new WasteManage();
       WasteManage.setCmis(parent);
    }
    replaceWin(WasteManage,ResourceLoader.getImageIcon("images/button/drumarrow1.jpg"),"Waste Management");
    WasteManage.setVisible(true);
  }

  void goRelogon(){
   // this.setVisible(false);
    parent.restartApp(this);
  }

  public void goRequest(int i, int rNum, Hashtable requestDataH) {
     this.requestDataH = requestDataH;
     mT = new Main_MoveFrameThread(this,REQUEST,i,rNum);
     mT.start();
  }

  public void goRequest(int i, int rNum) {
     //this.startImage();
     this.requestDataH = null;
     mT = new Main_MoveFrameThread(this,REQUEST,i,rNum);
     mT.start();
  }

  void goRequestAction(int i, int rNum){
    if (i == 1){
      goSearch(1);
    }
    if (i == 2){  // request deleted
      deleteTab(RW,"Material Request");
      //this.stopImage();
    } else {
      RW = null;
      RW = new RequestsWin(parent);
      RW.setPrNum(rNum);
      RW.setRequestDataH(requestDataH);
      RW.loadIt();
      RW.setVisible(true);
      replaceWin(RW,ResourceLoader.getImageIcon("images/button/nwprt.gif"),"Material Request");
    }
  }

  //4-24-03
  public void goCabinetManage() {
    //this.startImage();
    this.requestDataH = null;
    mT = new Main_MoveFrameThread(this, CABINET, 1, 0);
    mT.start();
  }
  void goCabinetManageAction(int i){
    if (i == 2){  // request deleted
      deleteTab(RW,"Cabinet Management");
    } else {
      dlts = null;
      dlts = new downloadtoscanner(parent);
      dlts.loadIt();
      dlts.setVisible(true);
      replaceWin(dlts,ResourceLoader.getImageIcon("images/button/nwprt.gif"),"Cabinet Management");
    }
  }

  public void goInventoryCount() {
    //this.startImage();
    this.requestDataH = null;
    mT = new Main_MoveFrameThread(this, INVENTORY_COUNT, 1, 0);
    mT.start();
  }
  void goInventoryCountAction(int i){
    if (i == 2){  // request deleted
      deleteTab(RW,"Inventory Count");
    } else {
      ic = null;
      ic = new InventoryCount(parent);
      ic.loadIt();
      ic.setVisible(true);
      replaceWin(ic,ResourceLoader.getImageIcon("images/button/nwprt.gif"),"Inventory Count");
    }
  }

   public void goWasteRequest(int i, int rNum) {
     this.wasteRequestDataH = null;
     mT = new Main_MoveFrameThread(this,WASTE_REQUEST,i,rNum);
     mT.start();
  }

  public void goWasteRequest(int i, int rNum, Hashtable wasteRequestDataH) {
     this.wasteRequestDataH = wasteRequestDataH;
     mT = new Main_MoveFrameThread(this,WASTE_REQUEST,i,rNum);
     mT.start();
  }

  void goWasteRequestAction(int i, int rNum){
    if (i == 1){
      goWaste(1);
    }
    if (i == 2){  // request deleted
      deleteTab(wasteRequest,"Waste Mgt. Request");
      //this.stopImage();
    } else {
      wasteRequest = null;
      wasteRequest = new WastePickupRequest(parent);
      wasteRequest.setPrNum(rNum);
      wasteRequest.setRequestDataH(wasteRequestDataH);
      wasteRequest.loadIt();
      replaceWin(wasteRequest,ResourceLoader.getImageIcon("images/button/drumarrow1.jpg"),"Waste Mgt. Request");
      wasteRequest.setVisible(true);
    }
  }

  public void goWasteOrder(int i, int rNum) {
     //this.startImage();

     mT = new Main_MoveFrameThread(this,WASTE_ORDER,i,rNum);
     mT.start();
  }

  void goWasteOrderAction(int i, int rNum){
    if (i == 1){
      goWasteManage(1);
    }
    if (i == 2){  // request deleted
      deleteTab(wasteOrder,"Waste Order");
      //this.stopImage();
    } else {
      wasteOrder = null;
      wasteOrder = new WasteOrder(parent);
      wasteOrder.setShipNum(rNum);
      wasteOrder.loadIt();
      replaceWin(wasteOrder,ResourceLoader.getImageIcon("images/button/drumsmall.jpg"),"Waste Order");
      wasteOrder.setVisible(true);
    }
  }

  //trong 3/1/00
  public void setNewChemEval(String s) {
    newChemEval = s;
  }

  //3-13-02 trying to find a way to pass new chem detail the hashtable
  public final void goNewChemMat(int i, int rNum, Hashtable newChemDetailH) {
     this.newChemDetailH = newChemDetailH;
     //this.startImage();
     mT = new Main_MoveFrameThread(this,NEW_CHEM_MAT,i,rNum);
     mT.start();
  }


  public final void goNewChemMat(int i, int rNum) {
     //this.startImage();
     mT = new Main_MoveFrameThread(this,NEW_CHEM_MAT,i,rNum);
     mT.start();
  }

  void goNewChemMatAction(int i, int rNum){
    if (i == 1){
      goSearch(1);
    }
    String tabL = "Cat Add Detail";
    if (i == 2){  // request deleted
      deleteTab(ncmat,tabL);
      //this.stopImage();
    } else {
      ncmat = null;
      ncmat = new NewChem(parent);
      //ncmat.setParent(parent);
      ncmat.setReqId(rNum);
      ncmat.setNewChemDetailHash(this.newChemDetailH);
      ncmat.loadIt();
      //trong 3/1/00
      /*if (newChemEval.equalsIgnoreCase("evaluation")) {
        ncmat.setEval(newChemEval);
        System.out.println("!!!!!!!!!!!! in main eval " + newChemEval);
      }*/ //trong 3/21
      //ncmat.setEval(newChemEval);
      //System.out.println("!!!!!!!!!!!! in main eval " + newChemEval);

      ncmat.setVisible(true);
      replaceWin(ncmat,ResourceLoader.getImageIcon("images/button/nwprt.gif"),tabL);
    }
  }

  void goToggleMute() {
    crb.setSoundMuted(!crb.getSoundMuted());
    setSoundImage();
  }

  void setSoundImage() {
    try{
      if (this.crb == null || !this.crb.getSoundMuted()){
        //not muted
        sndControl.setIcon(ResourceLoader.getImageIcon("images/gif/sound.gif"));
      }else{
        // muted
        sndControl.setIcon(ResourceLoader.getImageIcon("images/gif/nosound.gif"));
      }
    }catch(Exception e){}
  }


  protected String getMovingTitle(){  // should be overwritten if dif than TEST
    return "EARLY RELEASE - PLEASE TEST";
  }

  void replaceWin(JPanel post,ImageIcon icon, String winLabel) {
     int i = tabsetPanel1.indexOfTab(winLabel);
     if (i >= 0) tabsetPanel1.removeTabAt(i);
     try { tabsetPanel1.insertTab(winLabel,icon,post,winLabel,i); }
     catch (Exception e){ tabsetPanel1.addTab(winLabel,icon,post); }
     post.setVisible(true);
     tabsetPanel1.validate();
     tabsetPanel1.setSelectedComponent(post);
     tabsetPanel1.repaint();
  }

  void deleteTab(JPanel post,String tabName){
     int i = tabsetPanel1.indexOfComponent(post);
     if (i >= 0) tabsetPanel1.removeTabAt(i);
     tabsetPanel1.setSelectedIndex(0);
     tabsetPanel1.validate();
     tabsetPanel1.repaint();
  }

  public void startImage(){
     //animation.resume();
     animation = new Main_AnimationThread(animationButton,"Processing");
     animation.start();
  }

  public void stopImage(){
     /*
     animation.suspend();
     animationButton.changeIcon(true);
     */
     animation.stopRun();
     //animationButton.changeIcon(true);
  }

  void changeStatusBar(){
    if (tabsetPanel1 == null) return;
    Component comp = tabsetPanel1.getSelectedComponent();
    if (comp == null){
      statusBar.setText("");
      return;
    }
    String label  = null;
    if (search!=null && comp.equals(search) && (label=(String)countLabel.get(new Integer(SEARCHPT)))!= null){
      if (profileChanged.contains(new Integer(SEARCHPT))){
          goSearch(1);
          return;
      }
      statusBar.setText(label);
    } else if (IW!= null && comp.equals(IW) && (label=(String)countLabel.get(new Integer(INVENTORY)))!= null){
      if (profileChanged.contains(new Integer(INVENTORY))) {
          goInvent(1);
          return;
      }
      statusBar.setText((String)countLabel.get(new Integer(INVENTORY)));
    } else if (RW!= null && comp.equals(RW)&& (label=(String)countLabel.get(new Integer(REQUEST)))!= null){
      //if (profileChanged.contains(new Integer(REQUEST)))
      //    goRequest(1);
      statusBar.setText((String)countLabel.get(new Integer(REQUEST)));
    } else if (TW!= null && comp.equals(TW)&& (label=(String)countLabel.get(new Integer(TRACKING)))!= null){
      if (profileChanged.contains(new Integer(TRACKING))){
          goTracking(1);
          return;
      }
      statusBar.setText((String)countLabel.get(new Integer(TRACKING)));
    } else if (TW!= null && comp.equals(TW)&& (label=(String)countLabel.get(new Integer(USER_PROFILE)))!= null){
      if (profileChanged.contains(new Integer(USER_PROFILE))){
          goUserProfile(1);
          return;
      }
      statusBar.setText((String)countLabel.get(new Integer(USER_PROFILE)));
    } else if (ncmat!= null && comp.equals(ncmat)&& (label=(String)countLabel.get(new Integer(NEW_CHEM_MAT)))!= null){
      statusBar.setText((String)countLabel.get(new Integer(NEW_CHEM_MAT)));
    } else if (ncTrack!= null && comp.equals(ncTrack)&& (label=(String)countLabel.get(new Integer(NEW_CHEM_TRACKING)))!= null){
      if (profileChanged.contains(new Integer(NEW_CHEM_TRACKING))){
          goNewChemTrack(1);
          return;
      }
      statusBar.setText((String)countLabel.get(new Integer(NEW_CHEM_TRACKING)));
    } else if (waste!= null && comp.equals(waste)&& (label=(String)countLabel.get(new Integer(WASTE)))!= null){
      if (profileChanged.contains(new Integer(WASTE))){
          goWaste(1);
          return;
      }
      statusBar.setText((String)countLabel.get(new Integer(WASTE)));
    } else {
      statusBar.setText("");
    }
  }


  void buttonBar_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (e.getActionCommand().equals("Catalog")) {
       goSearch(1);
    } else if (e.getActionCommand().equals("Inventory")) {
       goInvent(1);
    } else if (e.getActionCommand().equals("Request Tracking")) {
       goTracking(1);
    } else if (e.getActionCommand().equals("Enviromental")) {
       new URLCall("/docmgr/oracle.oe",parent);
    } else if (e.getActionCommand().equals("Health and Safety")) {
       new URLCall("/docmgr/oracle.hs",parent);
    } else if (e.getActionCommand().equals("Document Repository")) {
       goDocMgr(1);
    } else if (e.getActionCommand().equals("Order Management")) {
       goWebConnect();
    } else if (e.getActionCommand().equals("Change User Profile")) {
       goUserProfile(1);
    } else if (e.getActionCommand().equals("Administrative Functions")) {
       goAdmin(1);
    } else if (e.getActionCommand().equals("Cat Add Tracking")) {
       goNewChemTrack(1);
    } else if (e.getActionCommand().equals("Reports")) {
       goReport(1);
    }
  }


  public void setGroupOptions() {
    if (!parent.groupMembership.isSuperUser() && !parent.groupMembership.isAdminAtSomeFacility())
    {
      //set button bar icons invisible
      adminB.setVisible(true);
      //set menu options disabled
      menuDoc.setEnabled(false);
      menuOrder.setEnabled(false);
      menuChem.setEnabled(false);
      menuAdmin.setEnabled(false);
    }
    if (!parent.scannerfunctions)
    {
      menuFile.remove(menuscanDownload);
    }
    if (!parent.inventoryCount) {
      menuFile.remove(menuInventoryCount);
    }
  } //end of method

  void menuExit_actionPerformed(ActionEvent e) {
    parent.exit();
  }

  // Material request
  void menuMR_actionPerformed(ActionEvent e) {
     goSearch(1);
  }

  void menuInv_actionPerformed(ActionEvent e) {
     goInvent(1);
  }

  void menuProfile_actionPerformed(ActionEvent e) {
     goUserProfile(1);
  }
  void menuWaste_actionPerformed(ActionEvent e) {
     goWaste(1);
  }

  void menuWasteManage_actionPerformed(ActionEvent e) {
     goWasteManage(1);
  }

  void menuWasteTrack_actionPerformed(ActionEvent e){
      goWasteTracking(1);
  }

  void menuWasteOrderTrack_actionPerformed(ActionEvent e){
      goWasteOrderTracking(1);
  }

  void menuAdmin_actionPerformed(ActionEvent e) {
     goAdmin(1);
  }
  void menuReport_actionPerformed(ActionEvent e) {
     goReport(1);
  }
  void menuStop_actionPerformed(ActionEvent e) {
     goStop();
  }
  void menuRlogin_actionPerformed(ActionEvent e) {
     goRelogon();
  }
  void menuPrint_actionPerformed(ActionEvent e) {
     goPrintScreenData();
  }

  // Request Tracking
  void menuTrack_actionPerformed(ActionEvent e) {
     goTracking(1);
  }

  // New Chem. Tracking
  void menuNewChemTrack_actionPerformed(ActionEvent e) {
     goNewChemTrack(1);
  }

  //Document mangr
  void menuDoc_actionPerformed(ActionEvent e) {
     goDocMgr(1);
  }

  //Order magr JDE
  void menuOrder_actionPerformed(ActionEvent e) {

  }

  void this_windowClosed(WindowEvent e) {
    this.setVisible(false);
    parent.exit();
  }

  void tabsetPanel1_stateChanged(ChangeEvent e) {
     changeStatusBar();
  }

  //5-10-01 disable print screen button if user in report tab
  void setButtonStatus() {
    Component comp = tabsetPanel1.getSelectedComponent();
    if (!showPrintScreen) {
      this.menuPrint.setEnabled(false);
      this.scrprtB.setEnabled(false);
    }else {
      if (report != null) {
        if (comp.equals(report)) {
          this.menuPrint.setEnabled(false);
          this.scrprtB.setEnabled(false);
        }else {
          this.menuPrint.setEnabled(true);
          this.scrprtB.setEnabled(true);
        }
      }
    }
  }

  //trong 3-03-01
  void tabsetPanel1_mouseClicked(MouseEvent e) {
    if (e.isMetaDown() && tabsetPanel1.getComponentCount() > 1 && !tabsetPanel1.getSelectedComponent().equals(search)) {
       Main_MetaPop pop = new Main_MetaPop(this);
       pop.show(tabsetPanel1,e.getX(),e.getY());
    }
  }

  void this_windowClosing(WindowEvent e) {
    parent.exit();
  }

    public void catalogB_mousePressed(){
      //System.out.println(imagesDir);
      catalogB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/chemical3.jpg");
      catalogB.setIcon(ss);
    }

    public void catalogB_mouseReleased(){
      catalogB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
      catalogB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/chemical3.jpg");
      catalogB.setIcon(ss);
    }
    public void inventB_mousePressed(){
      //System.out.println(imagesDir);
      inventB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/inventory3.jpg");
      inventB.setIcon(ss);
    }
    public void inventB_mouseReleased(){
      inventB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
      inventB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/inventory3.jpg");
      inventB.setIcon(ss);
    }
    public void trackB_mousePressed(){
    //System.out.println(imagesDir);
    trackB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/orders3.jpg");
      trackB.setIcon(ss);
    }
    public void trackB_mouseReleased(){
    trackB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    trackB.setFocusPainted(false);
      ImageIcon ss =ResourceLoader.getImageIcon("images/button/orders3.jpg");
      trackB.setIcon(ss);
    }
    public void profileB_mousePressed(){
    //System.out.println(imagesDir);
    profileB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/users3.jpg");
      profileB.setIcon(ss);
    }
    public void profileB_mouseReleased(){
    profileB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    profileB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/users3.jpg");
      profileB.setIcon(ss);
    }
    public void adminB_mousePressed(){
    //System.out.println(imagesDir);
    adminB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/admin3.jpg");
      adminB.setIcon(ss);
    }
    public void adminB_mouseReleased(){
    adminB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    adminB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/admin3.jpg");
      adminB.setIcon(ss);
    }
    public void reportB_mousePressed(){
    //System.out.println(imagesDir);
    reportB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/reports3.jpg");
      reportB.setIcon(ss);
    }
    public void reportB_mouseReleased(){
    reportB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    reportB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/reports3.jpg");
      reportB.setIcon(ss);
    }
    public void stopB_mousePressed(){
    //System.out.println(imagesDir);
    stopB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/stop3.jpg");
      stopB.setIcon(ss);
    }
    public void stopB_mouseReleased(){
    stopB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    stopB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/stop3.jpg");
      stopB.setIcon(ss);
    }
    public void relogonB_mousePressed(){
    //System.out.println(imagesDir);
    relogonB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/relogin3.jpg");
      relogonB.setIcon(ss);
    }
    public void relogonB_mouseReleased(){
    relogonB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    relogonB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/relogin3.jpg");
      relogonB.setIcon(ss);
    }
    public void scrprtB_mousePressed(){
    //System.out.println(imagesDir);
    scrprtB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/printscn3.jpg");
      scrprtB.setIcon(ss);
    }
    public void scrprtB_mouseReleased(){
    scrprtB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    scrprtB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/printscn3.jpg");
      scrprtB.setIcon(ss);
    }
    public void helpB_mousePressed(){
    helpB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/help3.jpg");
      helpB.setIcon(ss);
    }
    public void helpB_mouseReleased(){
    helpB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    helpB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/help3.jpg");
      helpB.setIcon(ss);
    }
    public void ncTrackB_mousePressed(){
    ncTrackB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/newcat3.jpg");
      ncTrackB.setIcon(ss);
    }
    public void ncTrackB_mouseReleased(){
    ncTrackB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    ncTrackB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/newcat3.jpg");
      ncTrackB.setIcon(ss);
    }
    public void wasteTrackB_mousePressed(){
    wasteTrackB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/pickup3.jpg");
      wasteTrackB.setIcon(ss);
    }
    public void wasteTrackB_mouseReleased(){
    wasteTrackB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    wasteTrackB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/pickup3.jpg");
      wasteTrackB.setIcon(ss);
    }
    public void manageWasteB_mousePressed(){
    manageWasteB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/mgt3.jpg");
      manageWasteB.setIcon(ss);
    }
    public void manageWasteB_mouseReleased(){
    manageWasteB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    manageWasteB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/mgt3.jpg");
      manageWasteB.setIcon(ss);
    }
    public void wasteOrderTrackB_mousePressed(){
    wasteOrderTrackB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/shipping3.jpg");
      wasteOrderTrackB.setIcon(ss);
    }
    public void wasteOrderTrackB_mouseReleased(){
    wasteOrderTrackB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    wasteOrderTrackB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/shipping3.jpg");
      wasteOrderTrackB.setIcon(ss);
    }
    public void wasteB_mousePressed(){
    wasteB.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/waste3.jpg");
      wasteB.setIcon(ss);
    }
    public void wasteB_mouseReleased(){
    wasteB.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
    wasteB.setFocusPainted(false);
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/waste3.jpg");
      wasteB.setIcon(ss);
    }

  //04-21-03 Nawaz
  void menuscanDownload_actionPerformed(ActionEvent e)
  {
    this.goCabinetManage();
  }

  void menuInventoryCount_actionPerformed(ActionEvent e)
  {
    this.goInventoryCount();
  }

} //end of class


class main_menuExit_actionAdapter implements java.awt.event.ActionListener{
  Main adaptee;

  main_menuExit_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.menuExit_actionPerformed(e);
  }
}

class Main_buttonBar_actionAdapter implements java.awt.event.ActionListener{
  Main adaptee;

  Main_buttonBar_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.buttonBar_actionPerformed(e);
  }
}

class Main_menuMR_actionAdapter implements java.awt.event.ActionListener{
  Main adaptee;

  Main_menuMR_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.menuMR_actionPerformed(e);
  }
}

class Main_menuInv_actionAdapter implements java.awt.event.ActionListener{
  Main adaptee;

  Main_menuInv_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.menuInv_actionPerformed(e);
  }
}

class Main_helpAbout_actionAdapter implements java.awt.event.ActionListener{
  Main adaptee;

  Main_helpAbout_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.helpAbout_actionPerformed(e);
  }
}
class Main_tcmisHelp_actionAdapter implements java.awt.event.ActionListener{
  Main adaptee;

  Main_tcmisHelp_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.tcmisHelp_actionPerformed(e);
  }
}


class Main_menuTrack_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;

  Main_menuTrack_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.menuTrack_actionPerformed(e);
  }
}

class Main_menuNewChemTrack_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;

  Main_menuNewChemTrack_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.menuNewChemTrack_actionPerformed(e);
  }
}

class Main_menuDoc_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;

  Main_menuDoc_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.menuDoc_actionPerformed(e);
  }
}

class Main_menuOrder_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;

  Main_menuOrder_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.menuOrder_actionPerformed(e);
  }
}

class Main_MoveFrameThread extends Thread {
  Main parent;
  int action;
  int reload;
  int reload2;

  public Main_MoveFrameThread(Main parent,int action,int reload, int reload2){
     super("MainThread");
     this.parent = parent;
     this.action = action;
     this.reload = reload;
     this.reload2 = reload2;
  }

  public void run(){
    switch (action){
      case Main.SEARCHPT:
        parent.goSearchAction(reload);
        break;
      case Main.INVENTORY:
        parent.goInventAction(reload);
        break;
      case Main.REQUEST:
        parent.goRequestAction(reload,reload2);
        break;
      case Main.TRACKING:
        parent.goTrackingAction(reload);
        break;
      case Main.USER_PROFILE:
        parent.goUserProfileAction(reload);
        break;
      case Main.NEW_CHEM_MAT:
        parent.goNewChemMatAction(reload,reload2);
        break;
      case Main.ADMIN:
        parent.goAdminAction(reload);
        break;
      case Main.NEW_CHEM_TRACKING:
        parent.goNewChemTrackAction(reload);
        break;
      case Main.REPORT:
        parent.goReportAction(reload);
        break;
      case Main.WASTE:
        parent.goWasteAction(reload);
        break;
      case Main.WASTE_REQUEST:
        parent.goWasteRequestAction(reload,reload2);
        break;
      case Main.WASTE_TRACKING:
        parent.goWasteTrackingAction(reload);
        break;
      case Main.WASTE_ORDER_TRACKING:
        parent.goWasteOrderTrackingAction(reload);
        break;
      case Main.MANAGE_WASTE:
        parent.goWasteManageAction(reload);
        break;
      case Main.WASTE_ORDER:
        parent.goWasteOrderAction(reload,reload2);
        break;
      case Main.CABINET:
        parent.goCabinetManageAction(reload);
        break;
      case Main.INVENTORY_COUNT:
        parent.goInventoryCountAction(reload);
        break;
      default:
    }
  }
}

class Main_ProcessingCanvas extends Canvas{
  Main parent;
  public Main_ProcessingCanvas(Main fr){
     super();
     parent = fr ;
     this.setSize(100,40);
  }
   public void paint(Graphics g){
      g.drawImage(parent.img[parent.currentFrame],0,0,this);
  }
}

class Main_this_windowAdapter extends java.awt.event.WindowAdapter {
  Main adaptee;

  Main_this_windowAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosed(WindowEvent e) {
    adaptee.this_windowClosed(e);
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

class Main_menuProfile_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;
  Main_menuProfile_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.menuProfile_actionPerformed(e);
  }
}

class Main_menuReport_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;
  Main_menuReport_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.menuReport_actionPerformed(e);
  }
}
class Main_menuStop_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;
  Main_menuStop_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.menuStop_actionPerformed(e);
  }
}
class Main_menuRlogin_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;
  Main_menuRlogin_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.menuRlogin_actionPerformed(e);
  }
}
class Main_menuPrint_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;
  Main_menuPrint_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.menuPrint_actionPerformed(e);
  }
}




class Main_menuWaste_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;
  Main_menuWaste_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.menuWaste_actionPerformed(e);
  }
}

class Main_menuWasteManage_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;
  Main_menuWasteManage_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.menuWasteManage_actionPerformed(e);
  }
}

class Main_menuWasteTrack_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;
  Main_menuWasteTrack_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.menuWasteTrack_actionPerformed(e);
  }
}

class Main_menuWasteOrderTrack_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;
  Main_menuWasteOrderTrack_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.menuWasteOrderTrack_actionPerformed(e);
  }
}

class Main_AnimationThread extends Thread {
  Main_JButtonAnimation r;
  public Main_AnimationThread(Runnable r,String s){
    super(r,s);
    this.r = (Main_JButtonAnimation)r;
  }

  public void stopRun() {
    r.stop();
  }

}   //end of class

class Main_Title_Thread extends Thread {

   Main parent;

   public Main_Title_Thread(Main p){
    super("Title Thread");
    this.parent = p;
   }

   public void run(){
     String title = parent.getTitle();

     while (true){
        String mTitle = title + "          ";
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle," ");
        String mov = parent.getMovingTitle();
        for (int i=0;i<mov.length();i++){
          mTitle = setMe(mTitle,mov.substring(i,i+1));
        }
        mTitle = setMe(mTitle," ");
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle,"*");
        mTitle = setMe(mTitle,"*");
     }
   }

   String setMe(String title, String label){
   title = title + label;
   parent.setTitle(title);
   //Nawaz 09-28-01
   //parent.setTitle("tcmIS");

      try { this.sleep(300);} catch (Exception e){ e.printStackTrace();}
      return title;
   }
}

class Main_StatusBar extends JPanel{

   BorderLayout b1 = new BorderLayout();
   JLabel label = new JLabel("Welcome to tcmIS. Please wait....");

   public Main_StatusBar(){
      this.setLayout(b1);
      this.add(label,BorderLayout.CENTER);
      label.setVerticalAlignment(JLabel.CENTER);
      label.setHorizontalAlignment(JLabel.LEFT);
      label.setVisible(true);
      label.setFont(new Font("Dialog", 1, 10));
      this.setPreferredSize(new Dimension(500,20));
      this.setMaximumSize(new Dimension(500,20));
      this.setVisible(true);
   }

   public void setText(String l){
      label.setText("     " + l);
      label.validate();
      label.repaint();
      this.validate();
      this.repaint();
   }
}

class Main_JButtonAnimation extends JButton implements Runnable {
  Image img[] = { ResourceLoader.getImageIcon("images/gif/3blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/4blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/5blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/6blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/4blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/5blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/3blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/6blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/5blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/3blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/4blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/5blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/3blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/4blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/6blue.gif").getImage(),
                  ResourceLoader.getImageIcon("images/gif/5blue.gif").getImage()  };
  int frameDelay = 200;
  long lastDisplay =0;
  int currentFrame =0;
  int numFrames = 16;
  boolean stop = false;

  public Main_JButtonAnimation(){
     super();
     this.setText("");
     changeIcon(true);
  }

  public void changeIcon(boolean stopAnimation){
      Image tcmis = null;
      if (stopAnimation){
        tcmis = ResourceLoader.getImageIcon("images/gif/0blue.gif").getImage();
      } else {
        tcmis = img[currentFrame];
      }
      ImageIcon tcmis2 = new ImageIcon(tcmis);
      this.setIcon(tcmis2);
      this.setVisible(true);
      this.validate();
  }

  //3-19-02
  public void run(){
      //System.out.println("------------ here in run: "+stop);
      do{
        long time = System.currentTimeMillis();
        synchronized(this) {
          if (stop) {
            this.changeIcon(true);
            stop = false;
            return;
          }
        }
        if (time - lastDisplay > frameDelay){
           this.changeIcon(false);
           try {
             Thread.sleep(frameDelay);
           } catch (Exception e){};
           ++currentFrame;
           currentFrame %= numFrames;
           lastDisplay = time;
        }
      } while (true);
  }

  public synchronized void stop() {
    //System.out.println("--------- here in stop");
    stop = true;
  }
  /*
  public void run(){
      do{
        long time = System.currentTimeMillis();
        if (time - lastDisplay > frameDelay){
           this.changeIcon(false);
           try {
             Thread.sleep(frameDelay);
           } catch (Exception e){};
           ++currentFrame;
           currentFrame %= numFrames;
           lastDisplay = time;
        }
      } while (true);
  }   */
}


class Main_MetaPop extends JPopupMenu {
    Main parent = null;
    JMenuItem deleteM = new JMenuItem("Delete Tab");
    JMenuItem cancelM = new JMenuItem("Cancel");
    JMenuItem cancelM2 = new JMenuItem("");


    public Main_MetaPop(Main p){
       super("Delete Tab");
       parent = p;
       add(deleteM);
       addSeparator();
       add(cancelM);
       deleteM.setActionCommand("Del");
       Main_MetaActionListener mal = new Main_MetaActionListener(parent);
       deleteM.addActionListener(mal);
       deleteM.setEnabled(true);
    }
}

class Main_MetaActionListener implements ActionListener {
  Main parent = null;

  public Main_MetaActionListener(Main p){
      parent = p;
  }

  public void actionPerformed(ActionEvent e) {
    Component comp = parent.tabsetPanel1.getSelectedComponent();
    parent.deleteTab((JPanel)comp,"Delete");
  }
}

class Main_menuscanDownload_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;

  Main_menuscanDownload_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.menuscanDownload_actionPerformed(e);
  }
}

class Main_menuInventoryCount_actionAdapter implements java.awt.event.ActionListener {
  Main adaptee;

  Main_menuInventoryCount_actionAdapter(Main adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.menuInventoryCount_actionPerformed(e);
  }
}

