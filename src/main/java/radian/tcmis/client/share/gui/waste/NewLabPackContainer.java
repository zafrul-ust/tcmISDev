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

public class NewLabPackContainer extends JDialog {

  CmisTable newContainerTable;
  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  JButton addB = new JButton();
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
  int STATE_CODE_COL = 0;
  int PACKAGING_COL = 0;

  String containerShipmentId = null;
  Vector bulkShipmentIdV = null;
  WasteManage wasteManage;
  WasteOrder wasteOrder;
  String storageArea = null;
  String vendorId = null;
  String orderNo = null;
  String shipmentId = null;

  //public NewLabPackContainer(JFrame fr,String facilityId, String title, WasteManage wm, String storage, String vendor) {
  public NewLabPackContainer(JFrame fr, String title, WasteOrder wo, String facId, String storage, String vendor) {
    super(fr, title, false);
    this.wasteOrder = wo;
    this.storageArea = storage;
    this.vendorId = vendor;
    this.facilityId = facId;

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

  public void setOrderNo(String s) {
    this.orderNo = s;
  }

  public void setShipmentId(String s) {
    this.shipmentId = s;
  }

  public void loadIt(){
    /*
    NewLabPackContainerLoadThread x = new NewLabPackContainerLoadThread(this);
    x.start();  */
    loadItAction();
  }

  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setFont(new java.awt.Font("Dialog", 0, 10));
    okB.setMaximumSize(new Dimension(110, 25));
    okB.setMinimumSize(new Dimension(110, 25));
    okB.setPreferredSize(new Dimension(110, 25));
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setFont(new java.awt.Font("Dialog", 0, 10));
    cancelB.setMaximumSize(new Dimension(110, 25));
    cancelB.setMinimumSize(new Dimension(110, 25));
    cancelB.setPreferredSize(new Dimension(110, 25));
    cancelB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/add.gif");
    addB.setFont(new java.awt.Font("Dialog", 0, 10));
    addB.setMaximumSize(new Dimension(110, 25));
    addB.setMinimumSize(new Dimension(110, 25));
    addB.setPreferredSize(new Dimension(110, 25));
    addB.setIcon(ss);
    panel1.setLayout(borderLayout4);
    jPanel1.setLayout(xYLayout1);
    okB.setText("Finished");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    addB.setText("Continue");
    addB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });

    newContainerTable = new CmisTable();
    partJSP.getViewport().setView(newContainerTable);
    panel1.setMaximumSize(new Dimension(250, 250));
    panel1.setMinimumSize(new Dimension(250, 250));
    panel1.setPreferredSize(new Dimension(250, 250));

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
    bottomP.add(addB, BorderLayout.WEST);
    bottomP.add(okB, BorderLayout.CENTER);
    bottomP.add(cancelB, BorderLayout.EAST);

  //  ClientHelpObjs.makeDefaultColorsPink(this);
  }

  void loadItAction() {
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_NEW_LAB_PACK_CONTAINER_PROFILE"); //String, String
      query.put("ORDER_NO",this.orderNo);       //String, String
      query.put("FACILITY_ID",facilityId);      //String, String
      query.put("VENDOR_ID",vendorId);          //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      partJSP.getViewport().remove(newContainerTable);

      Hashtable profileInfoH = (Hashtable)result.get("NEW_LAB_PACK_CONTAINER_PROFILE_INFO");

      DbTableModel ctm = buildTableModel(profileInfoH);
      sorter = new TableSorter(ctm);
      newContainerTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(newContainerTable);
      newContainerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      newContainerTable.addMouseListener(new New_Lab_Pack_Container_mouseAdapter(this));

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
      STATE_CODE_COL = ((Integer)keyCols.get("STATE_WASTE_CODES")).intValue();
      PROFILE_DESC_COL = ((Integer)keyCols.get("PROFILE_DESC")).intValue();
      PROFILE_ID_COL = ((Integer)keyCols.get("PROFILE_ID")).intValue();
      PACKAGING_COL = ((Integer)keyCols.get("PACKAGING")).intValue();
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
    //CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
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
    setVisible(false);
    wasteOrder.getLineDetail(new Integer(this.orderNo));
  }

  void addB_actionPerformed(ActionEvent e) {
    int selectedR = newContainerTable.getSelectedRowCount();
    if (selectedR > 0) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
      String title = new String("Add Lab Pack Container(s) to order: "+this.orderNo);
      AddLabPackContainer alpc = new AddLabPackContainer(cmis.getMain(),facilityId,title,storageArea,this,wasteOrder);
      alpc.setParent(cmis);
      String profileDesc = (String)newContainerTable.getModel().getValueAt(newContainerTable.getSelectedRow(),PROFILE_ID_COL);
      profileDesc += " - " + (String)newContainerTable.getModel().getValueAt(newContainerTable.getSelectedRow(),PROFILE_DESC_COL);
      alpc.setVendorProfileDesc(profileDesc);
      alpc.setStateWasteCode((String)newContainerTable.getModel().getValueAt(newContainerTable.getSelectedRow(),this.STATE_CODE_COL));
      String pkg = (String)newContainerTable.getModel().getValueAt(newContainerTable.getSelectedRow(),this.PACKAGING_COL);
      alpc.setPackaging(pkg);
      alpc.setOrderNo(this.orderNo);
      alpc.setShipmentId(this.shipmentId);
      String wiID = (String)newContainerTable.getModel().getValueAt(newContainerTable.getSelectedRow(),this.WASTE_ITEM_ID_COL);
      alpc.setWasteItemID(wiID);
      alpc.loadIt();
      CenterComponent.centerCompOnScreen(alpc);
      alpc.setVisible(true);
      CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    }else {
      GenericDlg g = new GenericDlg(cmis.getMain(),"Select a profile",true);
      g.setMsg("Please select a profile.");
      g.setVisible(true);
    }
  }

  void containerTable_mouseClicked(MouseEvent e) {

  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }
}

class NewLabPackContainerLoadThread extends Thread {
  NewLabPackContainer parent;
  public NewLabPackContainerLoadThread(NewLabPackContainer parent){
     super("NewLabPackContainerLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class New_Lab_Pack_Container_mouseAdapter extends java.awt.event.MouseAdapter {
  NewLabPackContainer adaptee;
  New_Lab_Pack_Container_mouseAdapter(NewLabPackContainer adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.containerTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}