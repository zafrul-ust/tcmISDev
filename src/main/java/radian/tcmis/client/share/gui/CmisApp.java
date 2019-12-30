//Title:      Your Product Name
//Version:
//Copyright:  Copyright (c) 1997
//Author:     Your Name
//Company:    Your Company
//Description:Your description
package radian.tcmis.client.share.gui;


import java.net.*;
import java.awt.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import java.util.*;
import java.io.*;

import radian.tcmis.client.share.httpCgi.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import javax.swing.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import java.awt.Component;

public abstract class CmisApp extends Component implements Runnable  {
  boolean packFrame = false;
  static Integer userId = null;
  static String  userLogon = null;
  static Main mFrame = null;
  static CmisApp cmisApp;
  public  LogonFrame lFrame =  null;
  public static Vector clients = new Vector();
  protected static String chosenClient = null;
  protected InetAddress ip=null;
  protected ConfigFile cFile = null;
  protected PrintWriter debugWriter = null;
  public Window splash = null;
  public CmisApp_SplashThread it;
  UserGroupMembership groupMembership = null;

  Hashtable facXref;
  public Hashtable appXref2D;

  Hashtable catXref= null;
  Hashtable catXfac=null;

  String facPref = null;
  String appPref = null;      //7-03-02

  JFrame dummyFrame = new JFrame();
  TcmisHttpConnection con = null;


  //trong 12-18 adding variables to handle the displaying of the refresh button in 'shipment cost' and 'vendor invoice'
  public boolean refreshShipmentCost = false;
  public boolean refreshVendorInvoice = false;
  public boolean refreshShipmentInfo = false;
  //6-04-01 if client does not use waste then don't show 'Ad Hoc Waste'. Default every clients use waste
  public boolean wasteUser = true;
  public boolean formattedUsage = true;
  public boolean formattedVOC = true;
  public boolean formattedMSDS = true;
  public boolean adHocUsage = true;
  public boolean materialMatrix = true;
  public boolean hourVocUsage = true;

  public boolean displayBaselineDate = false;
  public boolean displayLeadTimeChart = false;

  boolean aribaLogon = false;
  boolean startFromWebStart = false;
  String payLoadID = "0";

  String wDir = null;
  String imagesDir =  null;

  protected String token = null;
  protected boolean connectionEncrypted = true;
  protected boolean isDevelopment = false;
  ClientResourceBundle cConstants = new ClientResourceBundle();
  protected String proxyAuth = null;
  protected String wwwAuth=null;
  protected boolean passedLogon=false;
  protected boolean saveProxyPass=false;
  protected boolean proxyUsed=false;
  protected String proxyName=null;
  protected int proxyPort=0;
  protected boolean saveWwwPass=false;
  protected String logonPass=null;
  protected String logonVersion = null;

  CmisApp_Load_Waiting waitDlg;
  CmisApp_Checkout_Exit_Waiting waitChkoutExitDlg;

  Hashtable isWasteManagerData = null;
  Hashtable facLocAppData = null;
  Vector userFacilityIDList = null;
  Vector userFacilityNameList = null;
  Hashtable loadAllData = null;
  Integer yearOfReport = new Integer(-4);
  Integer numberOfYear = new Integer(7);
  boolean scannerfunctions = false;
  boolean inventoryCount = false;
  String companyId = "";

  public void  exit(){
    if (debugWriter!=null) {
      try {
        debugWriter.close();
      } catch (Exception e){
        e.printStackTrace();
      }
    }
    System.exit(0);
  }

  public void makeAdjusting(){
    // Loading window
    try {
      CmisApp_Waiting w = new CmisApp_Waiting();
      w.start();
      Thread.currentThread().sleep(2000);
    } catch (Exception e){
    };

    //set debug
    synchronized (this){
      cFile = new ConfigFile(this);
    }
    cFile = new ConfigFile(this);
  }

  public void run(){
    makeAdjustingNew();
    startLogon();
  }

  public void makeAdjustingNew(){
    try {
      this.proxyName = System.getProperty("proxyHost");
      if (!BothHelpObjs.isBlankString(proxyName)) {
        this.proxyUsed = true;
        String tmpPort = System.getProperty("proxyPort");
        if (!BothHelpObjs.isBlankString(tmpPort)) {
          this.proxyPort = Integer.parseInt(tmpPort);
        }
        this.proxyAuth = "";
        this.wwwAuth = "";
      }else {
        this.proxyUsed = false;
        this.proxyPort = 0;
        this.proxyName = "";
        this.proxyAuth = "";
        this.wwwAuth = "";
      }
      System.out.println("------- proxy info: "+proxyPort+" name: "+proxyName+" auth: "+proxyAuth+" www: "+wwwAuth);
    }catch (Exception ep) {
      GenericDlg gd = new GenericDlg(this.getMain(),"Error",true);
      gd.setMsg("Problem occurred while configuring proxy.");
      gd.setVisible(true);
      exit();
    }
  }

  void changeProxyConfig(){
        cFile = new ConfigFile(this);
        if (cFile.isProxyUsed())
        {
          //Use this line to get the proxy hosts
          String proxyHost = System.getProperties().getProperty("http.proxyHost");
          String proxyPort = System.getProperties().getProperty("http.proxyPort");
          sun.net.www.http.HttpClient.resetProperties();
          this.proxyUsed = true;
          this.proxyPort = Integer.parseInt((cFile.getProxyPort().trim().length()==0?"0":cFile.getProxyPort().trim()));
          this.proxyName = cFile.getProxy();

          if (cFile.getProxyBasic()!=null&&cFile.getProxyBasic().length()>0){
            String m = cFile.getProxyBasic().trim();
            String l = getUserLogon();
            String p = getLogonPass();
            //System.out.println("m:"+m+" l:"+l+" p:"+p);
            if (l==null || p==null || m.indexOf(l)!=0 || m.indexOf(p)<0){
            } else {
              m = m.substring(m.indexOf(p)+p.length());
              this.proxyAuth = m;
            }
          }
        }  else {
          this.proxyUsed = false;
          this.proxyPort = 0;
          this.proxyName = "";
          this.proxyAuth = "";
        }

        if (cFile.getWWWBasic()!=null&&cFile.getWWWBasic().length()>0){
          String m = cFile.getWWWBasic().trim();
          String l = getUserLogon();
          String p = getLogonPass();
          if (l==null || p==null || m.indexOf(l)!=0 || m.indexOf(p)<0){
          } else {
            m = m.substring(m.indexOf(p)+p.length());
            this.wwwAuth = m;
          }
        }
  }

  JFrame createDummyIconFrame(){
       JFrame temp = new JFrame();
       Image tcmisicon = ResourceLoader.getImageIcon("images/gif/tcmicon.gif").getImage();
       ImageIcon tcmisgif = ResourceLoader.getImageIcon("images/gif/haastcm.gif");
       JButton gif = new JButton(tcmisgif);
       temp.getContentPane().add(gif);
       temp.pack();
       temp.setIconImage(tcmisicon);
       temp.setSize(new Dimension(420,240));
       CenterComponent.centerCompOnScreen(temp);
       return temp;
  }

  //11-09-01
  void startWaitDlg() {
    try {
      waitDlg = new CmisApp_Load_Waiting();
      waitDlg.start();
      Thread.currentThread().sleep(2000);
    } catch (Exception e){
    };
  }
  void stopWaitDlg() {
    waitDlg.stopLoad();
  }
  void startCheckoutExitWaitDlg(String title, String msg1) {
    try {
      waitChkoutExitDlg = new CmisApp_Checkout_Exit_Waiting(title,msg1);
      waitChkoutExitDlg.start();
      //Thread.currentThread().sleep(2000);
    } catch (Exception e){
    };
  }
  void stopCheckoutExitWaitDlg() {
    waitChkoutExitDlg.stopLoad();
  }

  void startLogon(){
    lFrame = new LogonFrame();
    lFrame.setIconImage(ResourceLoader.getImageIcon("images/gif/tcmicon.gif").getImage());
    lFrame.setParent((Object) this);

    // either to use logon frame or not (i.e Seagate does not use logon frame)
    if (getStartFromWebStart()) {
      lFrame.getAuth();
      startWaitDlg();
    }else {
      if ("Yes".equalsIgnoreCase(getResourceBundle().getString("USE_GLOBAL_PASSWORD"))) {
        lFrame.tPass.setText(getResourceBundle().getString("GLOBAL_PASSWORD"));
        lFrame.tPass.setEnabled(false);
      }
      startWaitDlg();
      lFrame.show();
    }
    // from here, the logon will call this.logonPass();
    return;
  }

  public void checkClient() throws Exception{
    try {
      this.setToken(null);
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CHECK_CLIENT,this);
      Hashtable query = new Hashtable();
      query.put("ACTION","SET_TOKEN"); //String, String
      Hashtable result = cgi.getResultHash(query);
      String client = (((Vector) result.get("CLIENTS")).elementAt(0).toString());
      String tk = ((Vector) result.get("TOKEN")).elementAt(0).toString();
      clients = new Vector();
      if (client.equalsIgnoreCase("ERROR")){  // error message
          GenericDlg dd = new GenericDlg(this.lFrame,"Message",true);
          dd.setMsg(tk);
          dd.setVisible(true);
          this.exit();
      } else if (client.indexOf("MSG")>-1){  // system message
          GenericDlg dd = new GenericDlg(this.lFrame,"Message",true);
          dd.setMsg(tk.substring(0,tk.indexOf("~")));
          dd.setVisible(true);
          this.setToken(tk.substring(tk.indexOf("~")+1));
          clients.addElement(client.substring(client.indexOf("~")+1));
      } else {
          this.setToken(tk);
          clients.addElement(client);
      }
    } catch (Exception e) {
      e.printStackTrace(); throw e;
    }
  }

  protected void startSplashThread(){
    it = new CmisApp_SplashThread(this);
    it.start();
  }

  public void startSplash(){

  }

  public void stopSplash(){

  }

  public Vector getClients(){
     return clients;
  }

  public UserGroupMembership getGroupMembership(){
    return groupMembership;
  }
  public void setGroupMembership(Vector id,Vector desc, Vector fac){
    groupMembership = new UserGroupMembership(this);
    groupMembership.setGroups(id,desc,fac);
  }

  public Hashtable getXfac(){
     return facXref;
  }

  public void setXfac(Hashtable x){
     facXref = x;
  }

  public void setappXfac(Hashtable x){
     appXref2D = x;
  }

  public void setToken(String tok){
     token = tok;
  }

  public String getToken(){
     return token;
  }

  public void setConnectionEncrypted(boolean b){
     connectionEncrypted = b;
  }

  public boolean isConnectionEncrypted(){
     return connectionEncrypted ;
  }

  public void setUserId(String uid){
    userId = new Integer(uid);
  }

  public Integer getUserId(){
    return userId;
  }

  public void setUserLogon(String uid){
    userLogon = new String(uid);
  }

  public String getUserLogon(){
    return userLogon;
  }

  public void setLogonPass(String f){
    logonPass = new String(f);
  }

  public String getLogonPass(){
    return logonPass;
  }

  public void setPrefFac(String f){
    facPref = new String(f);
  }

  public String getPrefFac(){
    return facPref;
  }

  public void setPrefApp(String s){
    appPref = s;
  }

  public String getPrefApp(){
    return appPref;
  }

  public void logonPass(boolean pass){

    if (!pass){
       this.exit();
    }

    this.setPassedLogon(pass); // to be use by httpconnect
    synchronized (this){
      if (isSaveProxyPass()){
        cFile = new ConfigFile(this);
        String l = getUserLogon();
        String p = getLogonPass();
        String a = getProxyAuth();
        cFile.setProxyBasic(l+p+a);
        cFile.update();
       }
    }
    synchronized (this){
      if (isSaveWwwPass()){
        cFile = new ConfigFile(this);
        String l = getUserLogon();
        String p = getLogonPass();
        String a = getWwwAuth();
        cFile.setWWWBasic(l+p+a);
        cFile.update();
      }
    }

    // kill logon frame
    lFrame.setVisible(false);
    lFrame.dispose();
    goMain();
  }

  public void goMain(){
    mFrame = this.initMain();  // should be overwritten to build custom mains
    mFrame.setParent((Object) this);
    mFrame.setGroupOptions();
    fixFrame(mFrame);
    mFrame.showMain();
  }

  protected Main initMain(){
      return new Main();
  }

  void fixFrame(Frame frame){
    //Pack frames that have useful preferred size info, e.g. from their layout
    //Validate frames that have preset sizes
    if (packFrame)
      frame.pack();
    else
      frame.validate();

    CenterComponent.centerCompOnScreen(frame);
    try{
      frame.setIconImage(ResourceLoader.getImageIcon("images/gif/tcmicon.gif").getImage());
    } catch (Exception e){ e.printStackTrace(); }
  }


  public Main getMain(){
    return mFrame;
  }

  public void setAribaLogon(boolean b) {
    aribaLogon = b;
  }
  public boolean getAribaLogon() {
    return aribaLogon;
  }
  public void setStartFromWebStart(boolean b) {
    startFromWebStart = b;
  }
  public boolean getStartFromWebStart() {
    return startFromWebStart;
  }
  public void setPayLoadID(String s) {
    this.payLoadID = s;
  }
  public String getPayLoadID() {
    return this.payLoadID;
  }
  public void setLogonVersion(String s) {
    this.logonVersion = s;
  }
  public String getLogonVersion() {
    return this.logonVersion;
  }

  public void restartApp(Object frame){
    ((JFrame)frame).setVisible(false);

    setAribaLogon(false);   //10-29-01 since user is re-logon then it is okay to say that he/she is not coming from Ariba (Seagate)

    lFrame = new LogonFrame();
    lFrame.setIconImage(ResourceLoader.getImageIcon("images/gif/tcmicon.gif").getImage());
    lFrame.setParent((Object) this);
    lFrame.setRelogonFlag(true);      //10-29-01 setting relogon flag
    if ("Yes".equalsIgnoreCase(getResourceBundle().getString("USE_GLOBAL_PASSWORD"))) {
      lFrame.tPass.setText(getResourceBundle().getString("GLOBAL_PASSWORD"));
      lFrame.tPass.setEnabled(false);
    }
    lFrame.show();
    // from here, the logon will call this.logonPass();
  }


  void restartVersion(){
     boolean jre = false;

     try {
       synchronized (this) {
          execProg();
       }
     } catch (Exception e){
       e.printStackTrace();
     }

     try { Thread.currentThread().sleep(3000); } catch (Exception e){};
     this.exit();
  }

  void execProg() throws Exception
  {
  /*      Runtime r = Runtime.getRuntime();
        String wDir = new String(System.getProperty("user.dir"));
        wDir = wDir+System.getProperty("file.separator");
        String prog = "notepad.exe tmp/updt/tcmis.readme";
        r.exec(prog);
  */
  }

  void forkApp(String what)
  {
       /*String prog;
       Runtime r = Runtime.getRuntime();
       String wDir = new String(System.getProperty("user.dir"));
       wDir = wDir+System.getProperty("file.separator");
       prog = wDir +(new ClientResourceBundle()).getString("BAT_FILE");
       try {
        // first change
        BufferedReader in = new BufferedReader(new FileReader(prog));
        BufferedWriter out   = new BufferedWriter(new FileWriter(prog+".dummy"));

        String ln = new String();
        while ((ln=in.readLine()) != null){
          if (what.equalsIgnoreCase("jrew")){
             int i = ln.indexOf("jre.exe");
             if (i > 0){
                ln = ln.substring(0,i) + "jrew.exe" + ln.substring(i+("jre.exe").length());
             }
          } else if (what.equalsIgnoreCase("jre")){
             int i = ln.indexOf("jrew.exe");
             if (i > 0){
                ln = ln.substring(0,i) + "jre.exe" + ln.substring(i+"jrew.exe".length());
             }
          }
          // // System.out.println("Line:"+ln);
          out.write(ln,0,ln.length());
          out.newLine();
        }
        in.close();
        out.close();
        File temp = new File(prog);
        temp.delete();
        File temp2 = new File(prog+".dummy");
        temp2.renameTo(temp);

        prog = wDir + getResourceBundle().getString("EXEC_FILE");
        // // System.out.println("Exec:2 "+prog);
        r.exec(prog);
        this.exit();
       } catch (Exception e){
        e.printStackTrace();
       }*/
  }

  int checkVersion()
  {
    String nowVersion = new String("");
    int updated = 0;
    boolean needsRestart = false;
    //if (true) return 0;
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CHECK_VERSION,this);
    Hashtable query = new Hashtable();
    query.put("VERSION",getResourceBundle().getString("VERSION")); //String, String
    query.put("ACTION","OLD"); //String, String
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(new JFrame(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return 2;
    }
    Vector nowVersionVector = (Vector) result.get("VERSIONS"); // comes version, restart flag
    nowVersion = (String)((Vector) result.get("LAST_VERSION")).elementAt(0);
    if (nowVersion.equals("99")) return 99;  // use it to block logon and give maint. msg
    if (((String)((Vector) result.get("RESTART")).elementAt(0)).equals("M")) needsRestart=true;
    if (!nowVersion.equals(getResourceBundle().getString("VERSION"))){
      UpdateVersion up = new UpdateVersion(this);
      updated = up.updateVersionRemote(nowVersion,this);
      if (updated == 0){
         String no = new String("An error has occurr. Please restart the application and try again");
         GenericDlg err = new GenericDlg(new JFrame(),"Update",true);
         err.setMsg(no);
         err.setVisible(true);
         return 2;
      }
      if (updated == 2){
         String no = new String("The version you are using can not be automatically updated. You should uninstall this version, ");
         no = no + "go to  http://www.tcmis.com/tcmIS/install/"+getResourceBundle().getString("CLIENT_INITIALS")+" to install the newest version.";
         GenericDlg err = new GenericDlg(new JFrame(),"Update",true);
         err.setMsg(no);
         err.setVisible(true);
         return 2;
      }
      if (updated == 1 && needsRestart) return 1;
      // Got here got an error
      String no = new String("An error has occurr. Please restart the application and try again");
      GenericDlg err = new GenericDlg(new JFrame(),"Update",true);
      err.setMsg(no);
      err.setVisible(true);

    }
    return 0;

  }

  public void registerConnetion(TcmisHttpConnection con){
     this.con = con;
  }

  public PrintWriter getDebugWriter(){
     return debugWriter;
  }

  public String getWwwAuth(){
     return wwwAuth;
  }
  public void setWwwAuth(String p){
      wwwAuth = p;
  }
  public boolean isPassedLogon(){
     return passedLogon;
  }
  public void setPassedLogon(boolean p){
      passedLogon = p;
  }
  public boolean isSaveProxyPass(){
     return saveProxyPass;
  }
  public void setSaveProxyPass(boolean p){
      saveProxyPass = p;
  }
  public String getProxyAuth(){
     return proxyAuth;
  }
  public void setProxyAuth(String p){
      proxyAuth = p;
  }
  public String getProxyName(){
     return proxyName;
  }
  public void setProxyName(String p){
      proxyName = p;
  }
  public boolean isProxyUsed(){
     return proxyUsed;
  }
  public void setProxyUsed(boolean p){
      proxyUsed = p;
  }
  public boolean isSaveWwwPass(){
     return saveWwwPass;
  }
  public void setSaveWwwPass(boolean p){
      saveWwwPass = p;
  }
  public int getProxyPort(){
     return proxyPort;
  }
  public void setProxyPort(int p){
      proxyPort = p;
  }

  public void setCatxRef(Hashtable c){
      catXref = c;
  }
  public void setCatxFac(Hashtable c){
      catXfac = c;
  }
  public Hashtable getCatxRef(){
      return catXref;
  }
  public Hashtable getCatxFac(){
      return catXfac;
  }





  public abstract  ClientResourceBundle getResourceBundle();
  public abstract ReportInputPanel[] getReportPanels();
}


class CmisApp_SplashThread extends Thread {
  CmisApp parent;
  public CmisApp_SplashThread(CmisApp parent){
    super("SplashThread");
    this.parent = parent;
  }

  public void run(){
     parent.startSplash();
     this.stop();
  }
}

class CmisApp_Dialog1 extends JDialog {
  JPanel panel1 = new JPanel();
  JProgressBar pb = new JProgressBar();
  XYLayout xYLayout1 = new XYLayout();
  JLabel jLabel1 = new JLabel();

  public CmisApp_Dialog1(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension childSize = this.getSize();
      if (childSize.height > screenSize.height)
        childSize.height = screenSize.height;
      if (childSize.width > screenSize.width)
        childSize.width = screenSize.width;
      this.setLocation((screenSize.width - childSize.width) / 2, (screenSize.height - childSize.height) / 2);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public CmisApp_Dialog1() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(64);
    xYLayout1.setWidth(177);
    jLabel1.setText("Loading  .....");
    getContentPane().add(panel1);
    panel1.add(pb, new XYConstraints(17, 34, 145, -1));
    panel1.add(jLabel1, new XYConstraints(42, 10, 93, -1));

  }

  public void setValue(int n){
    pb.setValue(n);
    pb.revalidate();
    pb.repaint();
  }

  public void setPbMax(int n){
    pb.setMaximum(n);
  }
}

class CmisApp_Waiting extends Thread
{
    public CmisApp_Waiting(){
        super("CmisApp_Waiting");
    }

    public void run(){
        JFrame f = new JFrame("Dummy");
        CmisApp_Dialog1 wait = new CmisApp_Dialog1(f,"tcmIS",false);
        wait.setPbMax(100);
        wait.setValue(0);
        wait.setVisible(true);
        for (int i=0;i<100;i++){
                wait.setValue(i+1);
                try { this.sleep(200); } catch (Exception e) { e.printStackTrace(); };      //1-29-02 change from 20 to 200
        }
        wait.setVisible(false);
    }
}

//11-09-01
class CmisApp_Load_Waiting extends Thread
{
    CmisApp_Dialog1 wait;
    JFrame f;
    public CmisApp_Load_Waiting(){
      super("CmisApp_Waiting");
      f = new JFrame("Dummy");
      wait = new CmisApp_Dialog1(f,"tcmIS",false);
    }

    public void run(){
        wait.setPbMax(100);
        wait.setValue(0);
        wait.setVisible(true);
        for (int i=0;i<100;i++){
          wait.setValue(i+1);
          try { this.sleep(600); } catch (Exception e) { e.printStackTrace(); };
        }
    }
    public void stopLoad() {
      wait.setVisible(false);
    }
}

class CmisApp_Checkout_Exit_Waiting extends Thread
{
    CheckoutDlg wait;
    JFrame f;
    public CmisApp_Checkout_Exit_Waiting(String title,String msg){
      super("CmisApp_Checkout_Exit_Waiting");
      f = new JFrame();
      wait = new CheckoutDlg(f,title,false,msg);
    }

    public void run(){
      wait.setVisible(true);
      /*
      wait.setPbMax(100);
      wait.setValue(0);
      wait.setVisible(true);
      for (int i=0;i<100;i++){
        wait.setValue(i+1);
        try { this.sleep(400); } catch (Exception e) { e.printStackTrace(); };
      } */
    }
    public void stopLoad() {
      wait.setVisible(false);
    }
}

class CheckoutDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  String msg;
  //JProgressBar pb = new JProgressBar();

  public CheckoutDlg(Frame frame, String title, boolean modal,String msg) {
    super(frame, title, modal);
    this.msg = msg;
    try  {
      jbInit();
      pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension childSize = this.getSize();
      if (childSize.height > screenSize.height)
        childSize.height = screenSize.height;
      if (childSize.width > screenSize.width)
        childSize.width = screenSize.width;
      this.setLocation((screenSize.width - childSize.width) / 2, (screenSize.height - childSize.height) / 2);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    panel1.setLayout(gridBagLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setText(msg);
    panel1.setMaximumSize(new Dimension(200, 7));
    panel1.setMinimumSize(new Dimension(200, 7));
    panel1.setPreferredSize(new Dimension(200, 7));
    getContentPane().add(panel1);
    panel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.validate();
    this.repaint();
  }

  public void setMsg(String s) {
    jLabel1.setText(s);
    this.repaint();
  }

  /*
  public void setValue(int n){
    pb.setValue(n);
    pb.revalidate();
    pb.repaint();
  }

  public void setPbMax(int n){
    pb.setMaximum(n);
  }  */
}

