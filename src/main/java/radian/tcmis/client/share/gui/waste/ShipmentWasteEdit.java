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
import com.borland.jbcl.layout.XYLayout;
//import radian.tcmis.client.share.reports.appData.*;
import java.beans.*;
import radian.tcmis.client.share.gui.*;


import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class ShipmentWasteEdit extends JDialog {

  CmisTable wasteTable;
  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  //BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  String facilityId;
  String vendor;
  String storageArea;

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

  int SELECTED_COL = 0;
  int WASTE_REQUEST_ID_COL = 0;
  int LINE_ITEM_COL = 0;
  int ORDER_NUMBER_COL = 0;
  int CONTAINER_ID_COL = 0;
  int CONTAINER_TYPE_COL = 0;
  int SHIPMENT_ID_COL = 0;
  int FACILITY_ID_COL = 0;
  String orderNo;
  String containerShipmentId = null;
  Vector bulkShipmentIdV = null;

  //trong 9-21
  WasteOrder wasteOrder = null;
  StaticJLabel noteL = new StaticJLabel();

  public ShipmentWasteEdit(JFrame fr, String facilityId,String storageArea,String vendor, String title,String orderNo) {
    super(fr, title, false);

    this.facilityId = facilityId;
    this.vendor = vendor;
    this.storageArea = storageArea;
    this.orderNo = orderNo;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(648, 514));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }


  public void setParent(CmisApp c){
    cmis = c;
  }

  //trong 9-21
  public void setWasteOrder(WasteOrder wo) {
    this.wasteOrder = wo;
  }

  public void setContainerShipmentId(String s) {
    this.containerShipmentId = s;
  }

  public void loadIt(){
    /*
    ShipmentWasteEditLoadThread x = new ShipmentWasteEditLoadThread(this);
    x.start();  */
    loadItAction();
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

    wasteTable = new CmisTable();
    partJSP.getViewport().setView(wasteTable);
   /* wasteTable.addMouseListener(new Shipment_Edit_mouseAdapter(this));
    wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    wasteTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
     */
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
    noteL.setText("Note - To edit/remove container(s) to/from current order.  Click on the 'Order' column.");
    jPanel6.add(noteL, new GridBagConstraints2(0, 0, 0, 0, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 15, 5, 5), 0, 0));
    jPanel6.add(jPanel7, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(40, 15, 50, 15), 0, 0));
    jPanel7.add(partJSP, BorderLayout.CENTER);
    /*
    jPanel6.add(jPanel7, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(15, 15, 50, 15), 0, 0));
    jPanel7.add(partJSP, BorderLayout.CENTER);  */

    jPanel6.add(bottomP, new GridBagConstraints2(1, 1, 0, 0, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(okB, BorderLayout.WEST);
    bottomP.add(cancelB, BorderLayout.EAST);

  //  ClientHelpObjs.makeDefaultColorsPink(this);
  }

  void loadItAction() {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_SHIPMENT_EDIT_INFO"); //String, String
      query.put("FAC_ID",facilityId); //String, String
      query.put("VENDOR",vendor); //String, String
      query.put("STORAGE_AREA",storageArea); //String, String
      query.put("ORDER_NO",orderNo);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      partJSP.getViewport().remove(wasteTable);

      Hashtable editInfoH = (Hashtable)result.get("EDIT_INFO");

      DbTableModel ctm = buildTableModel(editInfoH);
      sorter = new TableSorter(ctm);
      wasteTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(wasteTable);
      wasteTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      wasteTable.addMouseListener(new Shipment_Edit_mouseAdapter(this));

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
      wasteTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = wasteTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }
      wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      wasteTable.setCellSelectionEnabled(false);
      wasteTable.getTableHeader().setReorderingAllowed(true);
      WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
      WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();
      wasteTable.setDefaultRenderer(String.class, colorTableRenderer);
      wasteTable.setDefaultRenderer(Integer.class, colorTableRenderer);
      wasteTable.setDefaultRenderer(Double.class, colorTableRenderer);
      wasteTable.setDefaultRenderer(Boolean.class, checkTableRenderer);

      JCheckBox check = new JCheckBox();
      check.setHorizontalAlignment(JLabel.CENTER);
      wasteTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor(check));

      // set column widths
      for(int i=0;i<wasteTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        wasteTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        wasteTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          wasteTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          wasteTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
      }

      //column
      Hashtable keyCols = (Hashtable)result.get("KEY_COLS");
      SELECTED_COL = ((Integer)keyCols.get("SELECTED_ROW")).intValue();
      WASTE_REQUEST_ID_COL = ((Integer)keyCols.get("WASTE_REQUEST_ID")).intValue();
      LINE_ITEM_COL = ((Integer)keyCols.get("LINE_ITEM")).intValue();
      ORDER_NUMBER_COL = ((Integer)keyCols.get("ORDER_NUMBER")).intValue();
      CONTAINER_ID_COL = ((Integer)keyCols.get("CONTAINER_ID")).intValue();
      CONTAINER_TYPE_COL = ((Integer)keyCols.get("BULK_OR_CONTAINER")).intValue();
      SHIPMENT_ID_COL = ((Integer)keyCols.get("SHIPMENT_ID")).intValue();
      FACILITY_ID_COL = ((Integer)keyCols.get("FACILITY_ID")).intValue();

      partJSP.getViewport().setView(wasteTable);
      if (wasteTable.getModel().getRowCount()<=0){
        GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
        g.setMsg("No Records found.");
        g.setVisible(true);
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
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    Vector updateV = new Vector();

    for(int i = 0; i < wasteTable.getRowCount(); i++) {
      if (wasteTable.getModel().getValueAt(i,SELECTED_COL).toString().equalsIgnoreCase("true")){
        Hashtable h = new Hashtable();
        h.put("CONTAINER_TYPE",wasteTable.getModel().getValueAt(i,CONTAINER_TYPE_COL));
        h.put("CONTAINER_ID",wasteTable.getModel().getValueAt(i,CONTAINER_ID_COL));
        h.put("SHIPMENT_ID",wasteTable.getModel().getValueAt(i,SHIPMENT_ID_COL));
        h.put("FACILITY_ID",wasteTable.getModel().getValueAt(i,FACILITY_ID_COL));
        updateV.addElement(h);
      }
    }
    updateShipmentRequest(updateV);
    Integer shipNum = new Integer(orderNo);
    wasteOrder.getLineDetail(shipNum);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    setVisible(false);
  }

  void updateShipmentRequest(Vector updateV) {
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","UPDATE_SHIPMENT_REQUEST"); //String, String
      query.put("UPDATE_VECTOR",updateV);
      query.put("ORDER_NO",orderNo);
      query.put("CONTAINER_SHIPMENT_ID",containerShipmentId);

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }
  }

  void wasteTable_mouseClicked(MouseEvent e) {
    int row   = wasteTable.getSelectedRow();
    //Waste already on order.
    String num = wasteTable.getModel().getValueAt(row,ORDER_NUMBER_COL).toString();
    if ((e.getClickCount() > 1) && (num.length() > 0) && (!num.equals(orderNo))) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Waste already on order.",true);
        gd.setMsg("Please choose another line item.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
    } else {
     //editing the shipment list.
      if(wasteTable.getModel().getValueAt(row,SELECTED_COL).toString().equalsIgnoreCase("true") && wasteTable.getSelectedColumn() == ORDER_NUMBER_COL) {
        wasteTable.getModel().setValueAt(new Boolean(false),row,SELECTED_COL);
        wasteTable.getModel().setValueAt(new String(""),row,ORDER_NUMBER_COL);
      }else if (wasteTable.getSelectedColumn() == ORDER_NUMBER_COL) {
        wasteTable.getModel().setValueAt(new Boolean(true),row,SELECTED_COL);
        wasteTable.getModel().setValueAt(new String(orderNo),row,ORDER_NUMBER_COL);
      }
      wasteTable.repaint();
    }
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  //  goViewReport();
  }
}

class ShipmentWasteEditLoadThread extends Thread {
  ShipmentWasteEdit parent;
  public ShipmentWasteEditLoadThread(ShipmentWasteEdit parent){
     super("ShipmentWasteEditLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class Shipment_Edit_mouseAdapter extends java.awt.event.MouseAdapter {
  ShipmentWasteEdit adaptee;
  Shipment_Edit_mouseAdapter(ShipmentWasteEdit adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.wasteTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}
