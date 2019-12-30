// This snippet creates a new dialog box
// with buttons on the bottom.

//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:

package radian.tcmis.client.share.gui.admin;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class AddUseApprovalDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JPanel bevelPanel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JPanel jPanel1 = new JPanel();
  CmisApp cmis;
  boolean canceled;
  boolean facCLoaded = false;
  boolean dataLoaded = false;
  boolean changed = false;

  JPanel jPanel2 = new JPanel();
  StaticJLabel jLabel1 = new StaticJLabel();
  DataJLabel nameL = new DataJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  DataJLabel pidL = new DataJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  //FacilityCombo facC = new FacilityCombo();
  JComboBox facC = new JComboBox();
  StaticJLabel groupAtFacL = new StaticJLabel();
  StaticJLabel memOfGroupL = new StaticJLabel();
  JButton addB = new JButton();
  JButton delB = new JButton();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane groupScrollP = new JScrollPane();
  JScrollPane memberScrollP = new JScrollPane();
  JList groupList = new JList();
  JList memberList = new JList();
  Object[][] groupData;
  JButton applyB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  Vector currentFacIDs = null;
  Hashtable screenDataH = null;
  CmisTable leftTable;
  CmisTable rightTable;
  TableSorter sorter;
  Hashtable currentDetailH;
  String lastFacID;

  public AddUseApprovalDlg(Frame frame, boolean modal,CmisApp cmis,String name, String pid, Vector cFacID) {
    super(frame, "Use Approval", modal);
    try {
      nameL.setText(name);
      pidL.setText(pid);
      this.cmis = cmis;
      this.currentFacIDs = cFacID;
      jbInit();
      facC = ClientHelpObjs.loadChoiceFromVector(facC,cFacID);
      lastFacID = facC.getItemAt(0).toString();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    getContentPane().add(panel1);
    pack();
     // Colors
    ClientHelpObjs.makeDefaultColors(this);
    validate();
    repaint();
  }

  public void setVisible(boolean b){
    super.setVisible(b);
  }

  private void jbInit() throws Exception {
    setResizable(false);
    panel1.setBorder(ClientHelpObjs.groupBox(""));
    panel1.setMaximumSize(new Dimension(500, 465));
    panel1.setPreferredSize(new Dimension(500, 465));
    panel1.setMinimumSize(new Dimension(500, 465));
    panel1.setSize(new Dimension(422, 152));
    xYLayout1.setWidth(498);
    xYLayout1.setHeight(463);
    bevelPanel1.setLayout(gridBagLayout2);
    bevelPanel1.setBorder(ClientHelpObjs.groupBox(""));
    okB.setMaximumSize(new Dimension(79, 35));
    okB.setText("OK");
    okB.addActionListener(new AddUseApprovalDlg_okB_actionAdapter(this));
    cancelB.setMaximumSize(new Dimension(97, 35));
    cancelB.setText("Cancel");
    cancelB.addActionListener(new AddUseApprovalDlg_cancelB_actionAdapter(this));
    this.addWindowListener(new AddUseApprovalDlg_this_windowAdapter(this));
    panel1.setLayout(xYLayout1);
    delB.setMaximumSize(new Dimension(35, 35));
    addB.setMaximumSize(new Dimension(35, 35));
    applyB.setMaximumSize(new Dimension(83, 35));
    facC.setMaximumSize(new Dimension(150, 24));
    facC.setMinimumSize(new Dimension(150, 24));
    facC.setPreferredSize(new Dimension(150, 24));
    panel1.add(bevelPanel1, new XYConstraints(10, 126, 480, 280));
    //bevelPanel1.add(groupAtFacL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
    //        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 27), 129, 0));
    //bevelPanel1.add(memOfGroupL, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
    //        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bevelPanel1.add(addB, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(95, 5, 5, 5), 0, 0));
    bevelPanel1.add(delB, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 89, 5), 0, 0));
    bevelPanel1.add(jPanel3, new GridBagConstraints2(2, 1, 1, 2, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(memberScrollP, BorderLayout.CENTER);
    bevelPanel1.add(jPanel4, new GridBagConstraints2(0, 1, 1, 2, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(groupScrollP, BorderLayout.CENTER);
    panel1.add(jPanel1, new XYConstraints(12, 416, 480, 40));
    jPanel1.add(applyB, null);
    jPanel1.add(okB, null);
    jPanel1.add(cancelB, null);
    jPanel2.setLayout(gridBagLayout1);
    jPanel2.setBorder(ClientHelpObjs.groupBox(""));
    panel1.add(jPanel2, new XYConstraints(10, 10, 480, 115));
    jPanel2.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(nameL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel3, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(pidL, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel2, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(facC, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */

    ImageIcon ss = null;
    ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setIcon(ss);

    ss = ResourceLoader.getImageIcon("images/button/next1.gif");
    addB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/prev1.gif");
    delB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/submit.gif");
    okB.setPreferredSize(new Dimension(79, 35));
    okB.setMinimumSize(new Dimension(79, 35));
    cancelB.setPreferredSize(new Dimension(97, 35));
    cancelB.setMinimumSize(new Dimension(97, 35));
    applyB.setText("Apply");
    applyB.setPreferredSize(new Dimension(83, 35));
    applyB.setIcon(ss);
    applyB.setMinimumSize(new Dimension(83, 35));
    applyB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        applyB_actionPerformed(e);
      }
    });

    facC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        facC_actionPerformed(e);
      }
    });

    jPanel3.setLayout(borderLayout2);
    jPanel4.setLayout(borderLayout1);
    delB.setPreferredSize(new Dimension(35, 35));
    delB.setMinimumSize(new Dimension(35, 35));
    delB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delB_actionPerformed(e);
      }
    });
    addB.setPreferredSize(new Dimension(35, 35));
    addB.setMinimumSize(new Dimension(35, 35));
    addB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });
    memOfGroupL.setText("Approval Authority");
    //groupAtFacL.setText("Work Areas");
    jLabel2.setText("Facility:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Personnel ID:");
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Name:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
  }

  public void loadIt(){
    AddUseApprovalThread agt = new AddUseApprovalThread(this);
    agt.start();
  }

  void saveCurrentDetail() {
    if (currentDetailH.containsKey(lastFacID)) {
      Vector v = (Vector)currentDetailH.get(lastFacID);
      v.removeAllElements();
      for (int i = 0; i < rightTable.getRowCount(); i++) {
        v.addElement((String)rightTable.getModel().getValueAt(i,0));
      }
      currentDetailH.put(lastFacID,v);
    }else {
      Vector v = new Vector();
      for (int i = 0; i < rightTable.getRowCount(); i++) {
        v.addElement((String)rightTable.getModel().getValueAt(i,0));
      }
      currentDetailH.put(lastFacID,v);
    }
    lastFacID = facC.getSelectedItem().toString();
  }

  void loadScreen(){
    ClientHelpObjs.setEnabledContainer(this,false);
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("AdminNew",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","LOAD_DATA");
      query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
      query.put("MEMBER_ID",pidL.getText());
      query.put("FACILITIES",currentFacIDs);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
         GenericDlg.showAccessDenied(cmis.getMain());
         ClientHelpObjs.setEnabledContainer(this,true);
         return;
      }

      Boolean b = (Boolean) result.get("RETURN_CODE");
      if (!b.booleanValue()) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg((String)result.get("MSG"));
        gd.setVisible(true);
        setVisible(false);
      }else {
        screenDataH = (Hashtable) result.get("SCREEN_DATA");
        currentDetailH = (Hashtable) screenDataH.get("USE_APPROVER");
        buildScreen();
        facC.addItemListener(new java.awt.event.ItemListener(){
          public void itemStateChanged(ItemEvent evt) {
            saveCurrentDetail();
            buildScreen();
          }
        });
      }
    } catch (Exception ie) {
      //cmis.getMain().stopImage();
      ie.printStackTrace();
    }
    ClientHelpObjs.setEnabledContainer(this,true);
  }
  boolean submitChanges(){
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("AdminNew",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","UPDATE_USE_APPROVAL"); //String, String
    query.put("MEMBER_ID",pidL.getText());
    query.put("UPDATE_DATA",currentDetailH);
    query.put("FACILITIES",currentFacIDs);

    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return false;
    }
    Boolean b = (Boolean) result.get("RETURN_CODE");
    GenericDlg gd = new GenericDlg(cmis.getMain(),"Use Approval Update",true);
    gd.setMsg((String)result.get("MSG"));
    gd.setVisible(true);
    ClientHelpObjs.setEnabledContainer(this,true);
    return b.booleanValue();
  }

  void facCLoaded(){
    facCLoaded = true;
    finishedLoading();
  }
  void dataLoaded(){
    dataLoaded = true;
    finishedLoading();
  }
  void finishedLoading(){
    if(facCLoaded && dataLoaded){
      buildScreen();
    }
  }

  void buildScreen(){
    if(facC.getSelectedIndex() < 0)facC.setSelectedIndex(0);

    String cFacID = facC.getSelectedItem().toString();

    Vector g = new Vector();
    if (screenDataH.containsKey(cFacID)) {
      g = (Vector)screenDataH.get(cFacID);
    }

    Vector m = new Vector();
    if (currentDetailH.containsKey(cFacID)) {
      m = (Vector)currentDetailH.get(cFacID);
    }

    Hashtable tableStyle = (Hashtable)screenDataH.get("TABLE_STYLE");

    jPanel4.remove(groupScrollP);
    jPanel3.remove(memberScrollP);

    String[] leftCols = (String[])tableStyle.get("LEFT_TABLE_HEADER");
    Object[][] oa = new Object[g.size()][leftCols.length];
    for (int i = 0; i < g.size(); i++) {
      oa[i][0] = g.elementAt(i).toString();
    }
    DbTableModel ctm = new DbTableModel(leftCols,oa);
    //sorter = new TableSorter(ctm);
    //leftTable = new CmisTable(sorter);
    //sorter.setColTypeFlag(ctm.getColumnTypesString());
    //sorter.addMouseListenerToHeaderInTable(leftTable);
    leftTable = new CmisTable(ctm);
    leftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    leftTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Integer colWidth = (Integer)tableStyle.get("COL_WIDTH");
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = leftTable.getColumnModel();
    for (int i = 0; i < leftTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    leftTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = leftTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    for(int i=0;i< leftTable.getColumnCount();i++){
      leftTable.getColumn(leftTable.getColumnName(i)).setPreferredWidth(colWidth.intValue());
    }

    //right table
    String[] rightCols = (String[])tableStyle.get("RIGHT_TABLE_HEADER");
    Object[][] roa = new Object[m.size()][rightCols.length];
    for (int i = 0; i < m.size(); i++) {
      roa[i][0] = m.elementAt(i).toString();
    }
    DbTableModel rightCtm = new DbTableModel(rightCols,roa);
    //rightSorter = new TableSorter(rightCtm);
    //rightTable = new CmisTable(rightSorter);
    //rightSorter.setColTypeFlag(rightCtm.getColumnTypesString());
    //rightSorter.addMouseListenerToHeaderInTable(rightTable);
    rightTable = new CmisTable(rightCtm);
    rightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    rightTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    TableColumnModel rcm = rightTable.getColumnModel();
    for (int i = 0; i < rightTable.getColumnCount(); i++) {
      rcm.getColumn(i).setCellRenderer(renderers[0]);
    }
    rightTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    enum1 = rightTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    for(int i=0;i< rightTable.getColumnCount();i++){
      rightTable.getColumn(rightTable.getColumnName(i)).setPreferredWidth(colWidth.intValue());
    }

    memberScrollP = new JScrollPane(rightTable);
    groupScrollP = new JScrollPane(leftTable);

    jPanel4.add(groupScrollP,BorderLayout.CENTER);
    jPanel3.add(memberScrollP,BorderLayout.CENTER);

    jPanel4.validate();
    jPanel3.validate();

    ClientHelpObjs.setEnabledContainer(this,true);
    enableButtons();
  }

  void enableButtons(){
    addB.setEnabled(true);
    delB.setEnabled(true);
    applyB.setEnabled(changed);
  }

  public boolean getCanceled(){
    return canceled;
  }

  // OK
  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (changed) {
      if(submitChanges())setVisible(false);
    }else {
      setVisible(false);
    }
   }

  // Cancel
  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(changed){
      ConfirmDlg cd = new ConfirmDlg(cmis.getMain(),"Cancel Confirm",true);
      cd.setMsg("Cancel will not update any changes.\nDo you want to proceed?");
      cd.setVisible(true);
      if(!cd.getConfirmationFlag())return;
    }
    canceled = true;
    setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    canceled = true;
    setVisible(false);
  }

  void addB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (leftTable.getSelectedRow() < 0) {
      GenericDlg cd = new GenericDlg(cmis.getMain(),"Selection Error",true);
      cd.setMsg("Please select a work area that you want to add.");
      cd.setVisible(true);
      return;
    }
    String cFacID = facC.getSelectedItem().toString();
    Vector workAreaV;
    if (screenDataH.containsKey(cFacID)) {
      workAreaV = (Vector)screenDataH.get(cFacID);
    }else {
      workAreaV = new Vector();
    }
    Vector appWorkAreaV;
    if (currentDetailH.containsKey(cFacID)) {
      appWorkAreaV = (Vector)currentDetailH.get(cFacID);
    }else {
      appWorkAreaV = new Vector();
    }
    int[] rows = leftTable.getSelectedRows();
    //DbTableModel ctm = (DbTableModel)rightTable.getModel();
    for (int i = 0; i < rows.length; i++) {
      String tmp = leftTable.getModel().getValueAt(rows[i],0).toString();
      for (int j = 0; j < rightTable.getRowCount(); j++) {
        if (tmp.equals(rightTable.getModel().getValueAt(j,0).toString())) {
          GenericDlg cd = new GenericDlg(cmis.getMain(),"Selection Error",true);
          cd.setMsg("The work area(s) that you selected already added to user authority.");
          cd.setVisible(true);
          return;
        }
      }
      //first add work area to approval authority
      appWorkAreaV.addElement(tmp);
      //removing work area from list
      workAreaV.removeElementAt(rows[i]-i);

      //Object[] oa = {tmp};
      //ctm.addRow(oa);
    }

    //now sort vector then add it back to hashtable
    appWorkAreaV = BothHelpObjs.sort(appWorkAreaV);
    screenDataH.put(cFacID,workAreaV);
    currentDetailH.put(cFacID,appWorkAreaV);

    applyB.setEnabled(true);
    changed = true;

    buildScreen();
  }

  void delB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (rightTable.getSelectedRow() < 0) {
      GenericDlg cd = new GenericDlg(cmis.getMain(),"Selection Error",true);
      cd.setMsg("Please select a work area that\nyou want to remove from user approval authority.");
      cd.setVisible(true);
      return;
    }
    String cFacID = facC.getSelectedItem().toString();
    Vector workAreaV;
    if (screenDataH.containsKey(cFacID)) {
      workAreaV = (Vector)screenDataH.get(cFacID);
    }else {
      workAreaV = new Vector();
    }
    Vector appWorkAreaV;
    if (currentDetailH.containsKey(cFacID)) {
      appWorkAreaV = (Vector)currentDetailH.get(cFacID);
    }else {
      appWorkAreaV = new Vector();
    }
    //DbTableModel ctm = (DbTableModel)rightTable.getModel();
    int[] rows = rightTable.getSelectedRows();
    for (int i = 0; i < rows.length; i++) {
      String tmp = rightTable.getModel().getValueAt(rows[i],0).toString();
      //first add work area back to work area list
      workAreaV.addElement(tmp);
      //removing work area from approval authority
      appWorkAreaV.removeElementAt(rows[i]-i);

      //ctm.removeRow(rows[i]);
    }

    //now sort vector then add it back to hashtable
    workAreaV = BothHelpObjs.sort(workAreaV);
    screenDataH.put(cFacID,workAreaV);
    currentDetailH.put(cFacID,appWorkAreaV);

    applyB.setEnabled(true);
    changed = true;

    buildScreen();
  }


  void facC_actionPerformed(ActionEvent e) {
   /* if(!facCLoaded)return;
    buildScreen();*/
  }

  void applyB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    saveCurrentDetail();
    submitChanges();
    applyB.setEnabled(false);
    changed = false;
  }

}

class AddUseApprovalDlg_okB_actionAdapter implements ActionListener{
  AddUseApprovalDlg adaptee;

  AddUseApprovalDlg_okB_actionAdapter(AddUseApprovalDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class AddUseApprovalDlg_cancelB_actionAdapter implements ActionListener{
  AddUseApprovalDlg adaptee;

  AddUseApprovalDlg_cancelB_actionAdapter(AddUseApprovalDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class AddUseApprovalDlg_this_windowAdapter extends WindowAdapter {
  AddUseApprovalDlg adaptee;

  AddUseApprovalDlg_this_windowAdapter(AddUseApprovalDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
class AddUseApprovalThread extends Thread {
  AddUseApprovalDlg parent;

  public AddUseApprovalThread(AddUseApprovalDlg parent){
     super("AddUseApprovalThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadScreen();
  }
}
