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

public class AddMemberToGroupsDlg extends JDialog {
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
  FacilityCombo facC = new FacilityCombo();
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
  CmisTable leftTable;
  CmisTable rightTable;
  Hashtable screenDataH;
  Hashtable info;
  JButton newGroupB = new JButton();

  public AddMemberToGroupsDlg(Frame frame, boolean modal,CmisApp cmis,String name, String pid) {
    super(frame, "User Group Membership", modal);
    try {
      nameL.setText(name);
      pidL.setText(pid);
      this.cmis = cmis;
      jbInit();
      facC.setCmisApp(cmis);
      facC.setUseAllFacilities(false);
      if(!cmis.getGroupMembership().isSuperUser()) facC.restrictToAdminFacilities(true);
      synchronized (this){
         facC.loadIt();
      }
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
    panel1.setMaximumSize(new Dimension(500, 465));
    panel1.setPreferredSize(new Dimension(500, 465));
    panel1.setMinimumSize(new Dimension(500, 465));
    panel1.setSize(new Dimension(422, 152));
    panel1.setBorder(ClientHelpObjs.groupBox(""));
    xYLayout1.setWidth(498);
    xYLayout1.setHeight(463);
    bevelPanel1.setLayout(gridBagLayout2);
    bevelPanel1.setBorder(ClientHelpObjs.groupBox(""));
    okB.setMaximumSize(new Dimension(79, 23));
    okB.setText("OK");
    okB.addActionListener(new AddMemberToGroupsDlg_okB_actionAdapter(this));
    cancelB.setMaximumSize(new Dimension(97, 23));
    cancelB.setText("Cancel");
    cancelB.addActionListener(new AddMemberToGroupsDlg_cancelB_actionAdapter(this));
    this.addWindowListener(new AddMemberToGroupsDlg_this_windowAdapter(this));
    panel1.setLayout(xYLayout1);
    delB.setMaximumSize(new Dimension(35, 35));
    addB.setMaximumSize(new Dimension(35, 35));
    applyB.setMaximumSize(new Dimension(83, 23));
    facC.setMaximumSize(new Dimension(200, 24));
    facC.setMinimumSize(new Dimension(200, 24));
    facC.setPreferredSize(new Dimension(200, 24));
    newGroupB.setMaximumSize(new Dimension(180, 23));
    newGroupB.setMinimumSize(new Dimension(160, 23));
    newGroupB.setPreferredSize(new Dimension(180, 23));
    newGroupB.setText("Create New Group");
    newGroupB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        newGroupB_actionPerformed(e);
      }
    });
    panel1.add(bevelPanel1, new XYConstraints(10, 126, 480, 280));
    bevelPanel1.add(addB, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(95, 5, 5, 5), 0, 0));
    bevelPanel1.add(delB, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 75, 5), 0, 0));
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
    facC.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().equalsIgnoreCase("loaded")) {
          facCLoaded();
        }
      }
    });
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
    jPanel2.add(newGroupB, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
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
    ss = ResourceLoader.getImageIcon("images/button/submit.gif");   //for both new group and apply
    newGroupB.setIcon(ss);
    okB.setPreferredSize(new Dimension(79, 23));
    okB.setMinimumSize(new Dimension(79, 23));
    cancelB.setPreferredSize(new Dimension(97, 23));
    cancelB.setMinimumSize(new Dimension(97, 23));
    applyB.setText("Apply");
    applyB.setPreferredSize(new Dimension(83, 23));
    applyB.setIcon(ss);
    applyB.setMinimumSize(new Dimension(83, 23));
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
    jLabel2.setText("Facility:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Personnel ID:");
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Name:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
  }

  public void loadIt(){
    AddMemberGroupThread agt = new AddMemberGroupThread(this);
    agt.start();
  }

  void loadScreen(){
    ClientHelpObjs.setEnabledContainer(this,false);
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("AdminNew",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","LOAD_MEMBER_GROUP_SCREEN");
      query.put("USER_ID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
      query.put("MEMBER_ID",pidL.getText());
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
         GenericDlg.showAccessDenied(cmis.getMain());
         ClientHelpObjs.setEnabledContainer(this,true);
         return;
      }
      info = (Hashtable) result.get("DATA");
      screenDataH = (Hashtable) result.get("TABLE_STYLE");
      /*System.out.println("----- info: "+info);
      if(info.size() < 2){
        groupData = new Object[0][0];
      }else{
        groupData = BothHelpObjs.get2DArrayFromVector(info,4);
      } */
      dataLoaded();
    } catch (Exception ie) {
      //3-20-02 cmis.getMain().stopImage();
      ie.printStackTrace();
    }
    ClientHelpObjs.setEnabledContainer(this,true);
  }
  boolean submitChanges(){
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("AdminNew",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","UPDATE_GROUP_MEMBERSHIP"); //String, String
    query.put("USER_ID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
    query.put("MEMBER_ID",pidL.getText());
    query.put("UPDATE_INFO",info);
    Vector v = new Vector();
    for (int i = 0; i < facC.getItemCount(); i++) {
      v.addElement(facC.getItemAt(i).toString());
    }
    query.put("FACILITY_ID",v);

    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return false;
    }
    Boolean b = (Boolean) result.get("RETURN_CODE");
    GenericDlg gd = new GenericDlg(cmis.getMain(),"User Group Member Update",true);
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

    String cFacID = facC.getSelectedFacId();
    Vector g;
    Vector m;
    if (info.containsKey(cFacID)) {
      Hashtable h = (Hashtable)info.get(cFacID);
      g = (Vector)h.get("GROUP_DESC");
      m = (Vector)h.get("MEMBER_GROUP_DESC");
    }else {
      g = new Vector();
      m = new Vector();
    }

    //g = BothHelpObjs.sort(g);
    //m = BothHelpObjs.sort(m);

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
      cd.setMsg("Do you want to cancel and lose your group membership changes?");
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
    String cFacID = facC.getSelectedFacId();
    Vector gID;
    Vector gDesc;
    Vector mID;
    Vector mDesc;
    Hashtable h;
    if (info.containsKey(cFacID)) {
      h = (Hashtable)info.get(cFacID);
      gDesc = (Vector)h.get("GROUP_DESC");
      gID = (Vector)h.get("GROUP_ID");
      mID = (Vector)h.get("MEMBER_GROUP_ID");
      mDesc = (Vector)h.get("MEMBER_GROUP_DESC");
    }else {
      h = new Hashtable();
      gID = new Vector();
      gDesc = new Vector();
      mID = new Vector();
      mDesc = new Vector();
    }
    int[] rows = leftTable.getSelectedRows();
    for (int i = rows.length-1; i >= 0; i--) {
      String tmp = leftTable.getModel().getValueAt(rows[i],0).toString();
      //first add group to member
      mDesc.addElement(tmp);
      mID.addElement(gID.elementAt(rows[i]).toString());
      //removing group from list
      gDesc.removeElementAt(rows[i]);
      gID.removeElementAt(rows[i]);
    }

    h.put("GROUP_ID",gID);
    h.put("GROUP_DESC",gDesc);
    h.put("MEMBER_GROUP_ID",mID);
    h.put("MEMBER_GROUP_DESC",mDesc);
    info.put(cFacID,h);

    applyB.setEnabled(true);
    changed = true;
    buildScreen();
  }

  void delB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String cFacID = facC.getSelectedFacId();
    Vector gID;
    Vector gDesc;
    Vector mID;
    Vector mDesc;
    Hashtable h;
    if (info.containsKey(cFacID)) {
      h = (Hashtable)info.get(cFacID);
      gDesc = (Vector)h.get("GROUP_DESC");
      gID = (Vector)h.get("GROUP_ID");
      mID = (Vector)h.get("MEMBER_GROUP_ID");
      mDesc = (Vector)h.get("MEMBER_GROUP_DESC");
    }else {
      h = new Hashtable();
      gID = new Vector();
      gDesc = new Vector();
      mID = new Vector();
      mDesc = new Vector();
    }
    int[] rows = rightTable.getSelectedRows();
    for (int i = rows.length-1; i >= 0; i--) {
      String tmp = rightTable.getModel().getValueAt(rows[i],0).toString();
      //first add group to list
      gDesc.addElement(tmp);
      gID.addElement(mID.elementAt(rows[i]).toString());
      //removing group from member
      mDesc.removeElementAt(rows[i]);
      mID.removeElementAt(rows[i]);
    }
    h.put("GROUP_ID",gID);
    h.put("GROUP_DESC",gDesc);
    h.put("MEMBER_GROUP_ID",mID);
    h.put("MEMBER_GROUP_DESC",mDesc);
    info.put(cFacID,h);

    applyB.setEnabled(true);
    changed = true;
    buildScreen();
  }

  void facC_actionPerformed(ActionEvent e) {
    if(!facCLoaded)return;
    buildScreen();
  }

  void applyB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    submitChanges();
    applyB.setEnabled(false);
  }

  void newGroupB_actionPerformed(ActionEvent e) {
    AddUseApprovalGroupDlg agd = new AddUseApprovalGroupDlg(cmis.getMain(),"Adding Use Approval Group",true,cmis,facC.getSelectedItem().toString(),facC.getSelectedFacId(),"","","Approval",false);
    CenterComponent.centerCompOnScreen(agd);
    agd.setVisible(true);
    if (!agd.cancelledFlag) {
      this.loadIt();
    }
  }

}

class AddMemberToGroupsDlg_okB_actionAdapter implements ActionListener{
  AddMemberToGroupsDlg adaptee;

  AddMemberToGroupsDlg_okB_actionAdapter(AddMemberToGroupsDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class AddMemberToGroupsDlg_cancelB_actionAdapter implements ActionListener{
  AddMemberToGroupsDlg adaptee;

  AddMemberToGroupsDlg_cancelB_actionAdapter(AddMemberToGroupsDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class AddMemberToGroupsDlg_this_windowAdapter extends WindowAdapter {
  AddMemberToGroupsDlg adaptee;

  AddMemberToGroupsDlg_this_windowAdapter(AddMemberToGroupsDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
class AddMemberGroupThread extends Thread {
  AddMemberToGroupsDlg parent;

  public AddMemberGroupThread(AddMemberToGroupsDlg parent){
     super("AddMemeberGroupThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadScreen();
  }
}

