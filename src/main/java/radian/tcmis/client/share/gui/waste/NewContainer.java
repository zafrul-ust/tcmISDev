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
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
//import radian.tcmis.client.share.reports.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
//import radian.tcmis.client.share.reports.appData.*;
import java.beans.*;
import radian.tcmis.client.share.gui.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class NewContainer extends JDialog {

  CmisTable newContainerTable;
  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  String facilityId;

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

  int PROFILE_DESC_COL = 0;
  int PROFILE_ID_COL = 0;
  int WASTE_ITEM_ID_COL = 0;

  String orderNo;
  String containerShipmentId = null;
  Vector bulkShipmentIdV = null;
  WasteManage wasteManage;
  String storageArea = null;
  String vendorId = null;
  boolean showWasteTag = false;

  public NewContainer(JFrame fr,String facilityId, String title, WasteManage wm, String storage, String vendor) {
    super(fr, title, false);
    this.facilityId = facilityId;
    this.wasteManage = wm;
    this.storageArea = storage;
    this.vendorId = vendor;

    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(600, 400));
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
    NewContainerLoadThread x = new NewContainerLoadThread(this);
    x.start();
  }

  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
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

    newContainerTable = new CmisTable();
    partJSP.getViewport().setView(newContainerTable);
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

    bottomP.setLayout(borderLayout1);
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(jPanel7, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(15, 15, 50, 15), 0, 0));
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
      query.put("ACTION","GET_NEW_CONTAINER_PROFILE"); //String, String
      query.put("FAC_ID",this.facilityId); //String, String
      query.put("VENDOR_ID",this.vendorId);
      query.put("STORAGE_AREA",this.storageArea);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      partJSP.getViewport().remove(newContainerTable);

      Hashtable profileInfoH = (Hashtable)result.get("NEW_CONTAINER_PROFILE_INFO");
      if (result.containsKey("SHOW_WASTE_TAG")) {
        showWasteTag = ((Boolean)result.get("SHOW_WASTE_TAG")).booleanValue();
      }

      DbTableModel ctm = buildTableModel(profileInfoH);
      sorter = new TableSorter(ctm);
      newContainerTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(newContainerTable);
      newContainerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      newContainerTable.addMouseListener(new New_Container_mouseAdapter(this));

        //Nawaz 09/19/01
      AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
      Hashtable chargeTableStyle = (Hashtable)result.get("TABLE_STYLE");
      Color color = (Color)chargeTableStyle.get("COLOR");
      Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
      Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
      Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
      Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
      Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
      renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
      //font and foreground and background color for columns heading
      String fontName = (String)chargeTableStyle.get("FONT_NAME");
      Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
      Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
      newContainerTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = newContainerTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }

      WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();

      newContainerTable.setDefaultRenderer(String.class, colorTableRenderer);
      newContainerTable.setDefaultRenderer(Integer.class, colorTableRenderer);
      newContainerTable.setDefaultRenderer(Double.class, colorTableRenderer);

      newContainerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      newContainerTable.setCellSelectionEnabled(false);
      newContainerTable.getTableHeader().setReorderingAllowed(true);

      // set column widths
      for(int i=0;i<newContainerTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        newContainerTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        newContainerTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          newContainerTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          newContainerTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
      }

      //column
      Hashtable keyCols = (Hashtable)result.get("KEY_COLS");
      PROFILE_DESC_COL = ((Integer)keyCols.get("PROFILE_DESC")).intValue();
      PROFILE_ID_COL = ((Integer)keyCols.get("PROFILE_ID")).intValue();
      WASTE_ITEM_ID_COL = ((Integer)keyCols.get("WASTE_ITEM_ID")).intValue();

      partJSP.getViewport().setView(newContainerTable);
      if (newContainerTable.getModel().getRowCount()<=0){
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


  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (showWasteTag) {
      GetDateNTextDlg getDateNTextDlg = new GetDateNTextDlg(cmis.getMain(), "Accumulation Start Date");
      getDateNTextDlg.setParent(cmis);
      getDateNTextDlg.setDateLabel("Start Date:");
      getDateNTextDlg.setTextLabel("Waste Tag #:");
      getDateNTextDlg.loadItAction();
      CenterComponent.centerCompOnScreen(getDateNTextDlg);
      getDateNTextDlg.setVisible(true);
      if (getDateNTextDlg.okClicked()) {
        if (updateRequest(getDateNTextDlg.dateT.getText().trim(),getDateNTextDlg.textT.getText().trim())) {
          wasteManage.doSearch();
        }
      }
    }else {
      GetDateDlg getDateDlg = new GetDateDlg(cmis.getMain(), "Accumulation Start Date");
      getDateDlg.setParent(cmis);
      getDateDlg.setDateLabel("Start Date:");
      getDateDlg.loadItAction();
      CenterComponent.centerCompOnScreen(getDateDlg);
      getDateDlg.setVisible(true);
      if (getDateDlg.okClicked()) {
        if (updateRequest(getDateDlg.dateT.getText(),"")) {
          wasteManage.doSearch();
        }
      }
    }
    setVisible(false);
  }

  boolean updateRequest(String accumulationDate, String wasteTag) {
    boolean val = true;
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","CREATE_NEW_CONTAINER"); //String, String
      query.put("FAC_ID",this.facilityId);
      query.put("STORAGE_AREA",this.storageArea);
      query.put("USER_ID",cmis.getUserId());
      query.put("ACCUMULATION_DATE",accumulationDate);
      query.put("WASTE_TAG",wasteTag);
      String wasteItemId = newContainerTable.getModel().getValueAt(newContainerTable.getSelectedRow(),WASTE_ITEM_ID_COL).toString();
      query.put("WASTE_ITEM_ID",wasteItemId);

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        val = false;
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
    int row   = newContainerTable.getSelectedRow();
    //Waste already on order.
    String num = newContainerTable.getModel().getValueAt(row,ORDER_NUMBER_COL).toString();
    if ((e.getClickCount() > 1) && (num.length() > 0) && (!num.equals(orderNo))) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Waste already on order.",true);
        gd.setMsg("Please choose another line item.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
    } else {
     //editing the shipment list.
      if(newContainerTable.getModel().getValueAt(row,SELECTED_COL).toString().equalsIgnoreCase("true")) {
        newContainerTable.getModel().setValueAt(new Boolean(false),row,SELECTED_COL);
        newContainerTable.getModel().setValueAt(new String(""),row,ORDER_NUMBER_COL);
      }else {
        newContainerTable.getModel().setValueAt(new Boolean(true),row,SELECTED_COL);
        newContainerTable.getModel().setValueAt(new String(orderNo),row,ORDER_NUMBER_COL);
      }
      newContainerTable.repaint();
    }    */
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }
}

class NewContainerLoadThread extends Thread {
  NewContainer parent;
  public NewContainerLoadThread(NewContainer parent){
     super("NewContainerLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class New_Container_mouseAdapter extends java.awt.event.MouseAdapter {
  NewContainer adaptee;
  New_Container_mouseAdapter(NewContainer adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.containerTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}