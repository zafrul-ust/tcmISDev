//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
//import radian.tcmis.client.share.reports.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.gui.*;


import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class EmptyInto extends JDialog {

  CmisTable profileTable;
  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  String facilityId;
  String storageLocation;
  Integer fromContainerId;

  CmisApp cmis ;
  JScrollPane partJSP = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  XYLayout xYLayout4 = new XYLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel bottomP = new JPanel();
  JButton cancelB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  StaticJLabel fromContainerL = new StaticJLabel();
  StaticJLabel toContainerL = new StaticJLabel();
  StaticJLabel sizeL = new StaticJLabel();
  JTextField sizeT = new JTextField();
  JLabel unitL = new JLabel();
  JComboBox unitC = new JComboBox();
  JLabel containerL = new JLabel();

  int PROFILE_DESC_COL = 0;
  int PROFILE_ID_COL = 0;
  int CONTAINER_ID_COL = 0;
  int SIZE_UNIT_COL = 0;

  String orderNo;
  String containerShipmentId = null;
  Vector bulkShipmentIdV = null;
  String vendorId = null;
  WasteManage wasteManage;
  String vendorProfileId = null;


  public EmptyInto(JFrame fr,String facilityId, String title, String storageLocation, String vendorId, WasteManage wm) {
    super(fr, title, false);
    this.facilityId = facilityId;
    this.storageLocation = storageLocation;
    this.vendorId = vendorId;
    this.wasteManage = wm;

    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(600, 600));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }


  public void setParent(CmisApp c){
    cmis = c;
  }

  public void setFromContainerId(Integer fci) {
    this.fromContainerId = fci;
    containerL.setText(fromContainerId.toString());
  }

  public void setVendorProfileId(String vpi) {
    this.vendorProfileId = vpi;
  }

  public void loadIt(){
    EmptyIntoLoadThread x = new EmptyIntoLoadThread(this);
    x.start();
  }

  void jbInit() throws Exception {
   /* String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setIcon(ss);
    panel1.setLayout(borderLayout4);
    jPanel1.setLayout(xYLayout1);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    profileTable = new CmisTable();
    partJSP.getViewport().setView(profileTable);
    panel1.setMaximumSize(new Dimension(520, 380));
    panel1.setMinimumSize(new Dimension(520, 380));
    panel1.setPreferredSize(new Dimension(520, 380));

    jPanel6.setLayout(gridBagLayout1);
    jPanel7.setLayout(borderLayout5);

    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    fromContainerL.setText("From Container:");
    toContainerL.setText("To Container:");
    sizeL.setText("Amount:");
    unitL.setText("");

    bottomP.setLayout(borderLayout1);
    sizeT.setPreferredSize(new Dimension(80, 24));
    sizeT.setMaximumSize(new Dimension(80, 24));
    sizeT.setMinimumSize(new Dimension(80, 24));
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);

    jPanel6.add(fromContainerL, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0                           //trong 8-21
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 15, 50, 0), 0, 0));
    jPanel6.add(containerL, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 100, 50, 0), 0, 0));
    jPanel6.add(sizeL, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 200, 50, 0), 0, 0));
    jPanel6.add(sizeT, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(8, 250, 50, 0), 0, 0));
    jPanel6.add(unitL, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 340, 50, 0), 0, 0));
    /*jPanel6.add(unitC, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(8, 370, 50, 0), 0, 0));
      */


    jPanel6.add(jPanel7, new GridBagConstraints2(0, 2, 0, 0, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(50, 15, 50, 15), 0, 0));
    jPanel7.add(toContainerL, BorderLayout.NORTH);  //trong 8-21
    jPanel7.add(partJSP, BorderLayout.CENTER);

    jPanel6.add(bottomP, new GridBagConstraints2(1, 1, 0, 0, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(okB, BorderLayout.WEST);
    bottomP.add(cancelB, BorderLayout.EAST);

  //  ClientHelpObjs.makeDefaultColorsPink(this);
  }

  void loadItAction() {
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_ACCUMULATION_CONTAINER_PROFILE"); //String, String
      query.put("FAC_ID",this.facilityId); //String, String
      query.put("STORAGE_LOCATION",storageLocation);
      query.put("VENDOR_ID",this.vendorId);
      query.put("VENDOR_PROFILE_ID",this.vendorProfileId);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      partJSP.getViewport().remove(profileTable);

      Hashtable profileInfoH = (Hashtable)result.get("CONTAINER_PROFILE_INFO");

      DbTableModel ctm = buildTableModel(profileInfoH);
      sorter = new TableSorter(ctm);
      profileTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(profileTable);
      profileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      profileTable.addMouseListener(new EmptyInto_mouseAdapter(this));
      profileTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      //profileTable.setCellSelectionEnabled(false);
      profileTable.getTableHeader().setReorderingAllowed(true);
      profileTable.addMouseListener(new EmptyInto_profileTable_mouseAdapter(this));

      // set column widths
      for(int i=0;i<profileTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        profileTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        profileTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          profileTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          profileTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
      }

      //column
      Hashtable keyCols = (Hashtable)result.get("KEY_COLS");
      PROFILE_DESC_COL = ((Integer)keyCols.get("PROFILE_DESC")).intValue();
      PROFILE_ID_COL = ((Integer)keyCols.get("PROFILE_ID")).intValue();
      CONTAINER_ID_COL = ((Integer)keyCols.get("CONTAINER_ID")).intValue();
      SIZE_UNIT_COL = ((Integer)keyCols.get("SIZE_UNIT")).intValue();

      partJSP.getViewport().setView(profileTable);
      if (profileTable.getModel().getRowCount()<=0){
        GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
        g.setMsg("No Records found.");
        g.setVisible(true);
        this.setVisible(false);
      }
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }
    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
  }

  DbTableModel buildTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");

    DbTableModel ctm = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      ctm.addRow((Object[]) data.elementAt(i));
    }
    ctm.setColumnPrefWidth(colWidths);
    ctm.setColumnType(colTypes);
    return ctm;
  }  //end of method


  void profileTable_mouseClicked(MouseEvent e){
    String unit = profileTable.getModel().getValueAt(profileTable.getSelectedRow(),SIZE_UNIT_COL).toString();
    unitL.setText(unit);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);

    String amount = BothHelpObjs.makeBlankFromNull(sizeT.getText());
    if (BothHelpObjs.isBlankString(amount)) {
      GenericDlg g = new GenericDlg(cmis.getMain(),"ERROR",true);
      g.setMsg("Please enter a positive number for amount.");
      g.setVisible(true);
      return;
    }
    try {
      int amt = Integer.parseInt(amount);
    }catch (Exception ee) {
      ee.printStackTrace();
      GenericDlg g = new GenericDlg(cmis.getMain(),"ERROR",true);
      g.setMsg("Please enter a positive number for amount.");
      g.setVisible(true);
      return;
    }

    if (profileTable.getSelectedRow() < 0) {
      GenericDlg g = new GenericDlg(cmis.getMain(),"ERROR",true);
      g.setMsg("Please select a container to empty into.");
      g.setVisible(true);
      return;
    }

    if (updateRequest()) {
      setVisible(false);
      wasteManage.doSearch();
    }else {
      return;
    }
  }

  boolean updateRequest() {
    boolean val = true;
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","UPDATE_EMPTY_INTO"); //String, String
      query.put("FROM_CONTAINER_ID",fromContainerId);
      String toContId = profileTable.getModel().getValueAt(profileTable.getSelectedRow(),CONTAINER_ID_COL).toString();
      Integer toContainerId = new Integer(toContId);
      query.put("TO_CONTAINER_ID",toContainerId);
      String temp = BothHelpObjs.makeBlankFromNull(sizeT.getText());
      Integer amount = new Integer(temp);
      query.put("AMOUNT",amount);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return false;
      }
      Boolean b = (Boolean)result.get("RETURN_CODE");
      if(!b.booleanValue()){
        val = false;
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg(result.get("MSG").toString());
        gd.setVisible(true);
      }else{
        GenericDlg rd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
        rd.setMsg(result.get("MSG").toString());
        rd.setVisible(true);
      }
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return false;
    }
    return val;
  }

  void containerTable_mouseClicked(MouseEvent e) {
   /*
    int row   = profileTable.getSelectedRow();
    //Waste already on order.
    String num = profileTable.getModel().getValueAt(row,ORDER_NUMBER_COL).toString();
    if ((e.getClickCount() > 1) && (num.length() > 0) && (!num.equals(orderNo))) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Waste already on order.",true);
        gd.setMsg("Please choose another line item.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
    } else {
     //editing the shipment list.
      if(profileTable.getModel().getValueAt(row,SELECTED_COL).toString().equalsIgnoreCase("true")) {
        profileTable.getModel().setValueAt(new Boolean(false),row,SELECTED_COL);
        profileTable.getModel().setValueAt(new String(""),row,ORDER_NUMBER_COL);
      }else {
        profileTable.getModel().setValueAt(new Boolean(true),row,SELECTED_COL);
        profileTable.getModel().setValueAt(new String(orderNo),row,ORDER_NUMBER_COL);
      }
      profileTable.repaint();
    }    */
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }
}

class EmptyIntoLoadThread extends Thread {
  EmptyInto parent;
  public EmptyIntoLoadThread(EmptyInto parent){
     super("EmptyIntoLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class EmptyInto_mouseAdapter extends java.awt.event.MouseAdapter {
  EmptyInto adaptee;
  EmptyInto_mouseAdapter(EmptyInto adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.containerTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}

class EmptyInto_profileTable_mouseAdapter extends java.awt.event.MouseAdapter {
  EmptyInto adaptee;
  EmptyInto_profileTable_mouseAdapter(EmptyInto adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.profileTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}