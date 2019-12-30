package radian.tcmis.client.share.gui;

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class FinanceApproverDlg extends JDialog {

  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  String prNumber;
  String status;

  CmisApp cmis ;
  RequestsWin requestWin;
  JPanel jPanel6 = new JPanel();
  XYLayout xYLayout4 = new XYLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  JPanel bottomP = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  StaticJLabel commentL = new StaticJLabel();
  DataJLabel nameL = new DataJLabel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  StaticJLabel statusL = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  DataJLabel phoneL = new DataJLabel();
  StaticJLabel jLabel1 = new StaticJLabel();
  DataJLabel emailL = new DataJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  JTextArea reasonT = new JTextArea();
  JScrollPane reasonJSP = new JScrollPane();

  public FinanceApproverDlg(RequestsWin rw,JFrame fr, String title, String prNum, String stat) {
    super(fr, title, false);
    this.requestWin = rw;
    this.prNumber = prNum;
    this.status = stat;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(450, 300));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setParent(CmisApp c){
    cmis = c;
  }

  public void loadIt(){
    FinanceApproverLoadThread x = new FinanceApproverLoadThread(this);
    x.start();
  }
  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    panel1.setLayout(borderLayout4);
    jPanel1.setLayout(xYLayout1);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    panel1.setMaximumSize(new Dimension(520, 380));
    panel1.setMinimumSize(new Dimension(520, 380));
    panel1.setPreferredSize(new Dimension(520, 380));
    panel1.setBorder(ClientHelpObjs.groupBox(""));

    jPanel6.setLayout(gridBagLayout1);
    jPanel7.setLayout(gridBagLayout2);
    jPanel7.setBorder(ClientHelpObjs.groupBox(""));

    bottomP.setLayout(borderLayout1);
    nameL.setText("jLabel1");
    statusL.setText("jLabel2");
    jLabel2.setText("Phone:");
    phoneL.setText("jLabel1");
    jLabel1.setText("E-mail:");
    emailL.setText("jLabel3");
    jLabel3.setText("Reason:");
    reasonT.setLineWrap(true);
    reasonT.setPreferredSize(new Dimension(350, 100));
    reasonT.setWrapStyleWord(true);
    reasonT.setDoubleBuffered(true);
    reasonT.setMaximumSize(new Dimension(400, 100));
    reasonT.setMinimumSize(new Dimension(350, 100));
    reasonT.setEditable(false);
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(jPanel7, new GridBagConstraints2(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(10, 15, 50, 15), 0, 0));
    jPanel7.add(nameL, new GridBagConstraints2(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 8, 5), 0, 0));
    jPanel7.add(statusL, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 8, 5), 0, 0));
    jPanel7.add(jLabel2, new GridBagConstraints2(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 8, 5), 0, 0));
    jPanel7.add(phoneL, new GridBagConstraints2(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 8, 5), 0, 0));
    jPanel7.add(jLabel1, new GridBagConstraints2(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 8, 5), 0, 0));
    jPanel7.add(emailL, new GridBagConstraints2(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 10, 5), 0, 0));
    jPanel7.add(jLabel3, new GridBagConstraints2(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    jPanel7.add(reasonJSP, new GridBagConstraints2(0, 4, 2, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

    jPanel6.add(bottomP, new GridBagConstraints2(1, 1, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(okB, BorderLayout.WEST);
    jPanel6.add(commentL, new GridBagConstraints2(0, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 15, 20, 0), 0, 0));
  }

  void loadItAction() {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("MaterialRequest",cmis);
      Hashtable query = new Hashtable();
      query.put("FUNCTION","GET_FINANCE_APPROVER_INFO"); //String, String
      query.put("USER_ID",cmis.getUserId());
      query.put("REQ_NUM",this.prNumber);
      query.put("STATUS","APPROVED");
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      Boolean bool = (Boolean)result.get("RETURN_CODE");
      if (bool.booleanValue()) {
        Hashtable useApprovalH = (Hashtable)result.get("FINANCE_APPROVER_INFO");
        nameL.setText((String)useApprovalH.get("NAME"));
        if (status.equalsIgnoreCase("approved")) {
          statusL.setText("Approved By:");
        }else {
          statusL.setText("Rejected By:");
        }
        phoneL.setText((String)useApprovalH.get("PHONE"));
        emailL.setText((String)useApprovalH.get("EMAIL"));
        reasonT.setText((String)useApprovalH.get("COMMENT"));
        reasonJSP.getViewport().setView(reasonT);
      }else {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg((String)result.get("MSG"));
        gd.setVisible(true);
      }
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        closeWindow();
      }
    });
  }

  void closeWindow() {
    requestWin.financeDlgOpen = false;
    this.setVisible(false);
  }

  void okB_actionPerformed(ActionEvent e) {
    requestWin.financeDlgOpen = false;
    this.setVisible(false);
  }
}

class FinanceApproverLoadThread extends Thread {
  FinanceApproverDlg parent;
  public FinanceApproverLoadThread(FinanceApproverDlg parent){
     super("FinanceApproverLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}