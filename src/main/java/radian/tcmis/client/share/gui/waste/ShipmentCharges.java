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
public class ShipmentCharges extends JDialog {

  CmisTable wasteTable;
  TableSorter sorter = new TableSorter();
  DbTableModel ctm;                       //for all shipments
  //DbTableModel searchCTM;                 //when hitting search button with specified shipment id
  Hashtable currentData = new Hashtable();

  JPanel panel1 = new JPanel();
  JPanel topP = new JPanel();
  JButton okB = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
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
  Vector shipmentIDV = null;
  boolean shipmentLoaded = false;
  String lastShipmentID = " ";

  int QTY_COL = 0;
  int DESC_COL = 0;
  int RATE_COL = 0;
  int COST_TYPE_COL = 0;
  int orderNo;
  String containerShipmentId = null;
  Vector bulkShipmentIdV = null;

  //trong 9-21
  WasteOrder wasteOrder = null;
  BorderLayout borderLayout2 = new BorderLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  BorderLayout borderLayout6 = new BorderLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();

  int shipmentId;
  WasteVendorInvoice wvi = null;
  boolean infoChanged = false;
  int currentPos = 0;


  public ShipmentCharges(JFrame fr, String title,int shipId,WasteVendorInvoice wvi,int currentPos) {
    super(fr, title, false);
    this.shipmentId = shipId;
    this.wvi = wvi;
    this.currentPos = currentPos;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(400, 300));
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

  public void loadIt(){
    ShipmentChargesEditLoadThread x = new ShipmentChargesEditLoadThread(this);
    x.start();
  }

  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/submit.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setIcon(ss);
    panel1.setLayout(borderLayout4);
    topP.setLayout(gridBagLayout3);
    okB.setText("Update");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    wasteTable = new CmisTable();
    partJSP.getViewport().setView(wasteTable);

    panel1.setMaximumSize(new Dimension(400, 300));
    panel1.setMinimumSize(new Dimension(400, 300));
    panel1.setPreferredSize(new Dimension(400, 300));

    jPanel6.setLayout(gridBagLayout1);
    jPanel7.setLayout(borderLayout5);

    cancelB.setText("Exit");
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    bottomP.setLayout(borderLayout1);

    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);

    jPanel6.add(topP, new GridBagConstraints2(0, 0, 0, 0, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    /*
    shipmentIDL.setText("Shipment ID: ");
    topP.add(shipmentIDL, new GridBagConstraints2(0, 0, 0, 0, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 20, 20, 0), 0, 0));
    topP.add(shipmentT, new GridBagConstraints2(0, 0, 0, 0, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 100, 20, 0), 0, 0));
    //topP.add(searchB, new GridBagConstraints2(0, 0, 0, 0, 0.0, 0.0
    //        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 300, 20, 0), 0, 0));
     */
    jPanel6.add(jPanel7, new GridBagConstraints2(0, 1, 0, 0, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(60, 15, 50, 15), 0, 0));
    jPanel7.add(partJSP, BorderLayout.CENTER);

    jPanel6.add(bottomP, new GridBagConstraints2(0, 2, 0, 0, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(okB, BorderLayout.WEST);
    bottomP.add(cancelB, BorderLayout.EAST);

  //  ClientHelpObjs.makeDefaultColorsPink(this);
  }

  void loadItAction() {
    try {
      //CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_CHARGES_FOR_SHIPMENT"); //String, String
      query.put("SHIPMENT_ID",new Integer(this.shipmentId));   //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      Boolean suc = (Boolean)result.get("SUCCEEDED");
      if (suc.booleanValue()) {
        Hashtable editInfoH = (Hashtable)result.get("SHIPMENT_CHARGES_INFO");

        ctm = buildTableModel(editInfoH);
        partJSP.setVisible(true);
        buildTable((Hashtable)result.get("TABLE_STYLE"));
        //column
        Hashtable keyCols = (Hashtable)editInfoH.get("KEY_COLS");
        QTY_COL = ((Integer)keyCols.get("Qty")).intValue();
        DESC_COL = ((Integer)keyCols.get("Cost Desc")).intValue();
        RATE_COL = ((Integer)keyCols.get("Rate")).intValue();
        COST_TYPE_COL = ((Integer)keyCols.get("Cost Type")).intValue();
      }else {
        GenericDlg g = new GenericDlg(cmis.getMain(),"ERROR",true);
        g.setMsg(result.get("MSG").toString());
        g.setVisible(true);
        setVisible(false);
      }
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }

    ClientHelpObjs.setEnabledContainer(this,true);
    this.validate();
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



  void buildTable(Hashtable Result1) {
    partJSP.getViewport().remove(wasteTable);
    ctm.setEditableFlag(BothHelpObjs.mypow(2,QTY_COL));
    wasteTable = new CmisTable(ctm);
    wasteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    wasteTable.setCellSelectionEnabled(false);
    wasteTable.getTableHeader().setReorderingAllowed(true);

    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    //Hashtable chargeTableStyle = (Hashtable)result.get("TABLE_STYLE");
    Color color = (Color)Result1.get("COLOR");
    Integer t = (Integer)Result1.get("INSET_TOP");
    Integer l = (Integer)Result1.get("INSET_LEFT");
    Integer b = (Integer)Result1.get("INSET_BOTTOM");
    Integer r = (Integer)Result1.get("INSET_RIGHT");
    Integer a = (Integer)Result1.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    /*TableColumnModel cm = wasteTable.getColumnModel();
    for (int i = 0; i < wasteTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }*/
    //font and foreground and background color for columns heading
    String fontName = (String)Result1.get("FONT_NAME");
    Integer fontStyle = (Integer)Result1.get("FONT_STYLE");
    Integer fontSize = (Integer)Result1.get("FONT_SIZE");
    wasteTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)Result1.get("FOREGROUND"),(Color)Result1.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = wasteTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

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

    partJSP.getViewport().setView(wasteTable);

    wasteTable.setEnabled(true);
    okB.setEnabled(true);

    if (wasteTable.getModel().getRowCount()<=0){
      GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
      g.setMsg("No Records found.");
      g.setVisible(true);
    }
  }

  void saveCurrentData() {
    if (wasteTable.isEditing()) {
      wasteTable.getCellEditor().stopCellEditing();
    }

    if (lastShipmentID.startsWith("Select")) {
      return;
    }
    Hashtable tempData = new Hashtable();
    for (int i = 0; i < wasteTable.getRowCount(); i++) {
      tempData.put(new Integer(i),wasteTable.getModel().getValueAt(i,QTY_COL).toString());
    }
    currentData.put(lastShipmentID,tempData);
  }

  void setSelectedRow(int x){
    ListSelectionModel lsm = wasteTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    wasteTable.scrollRectToVisible(wasteTable.getCellRect(x,0,false));
  }


  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (wasteTable.isEditing()) {
      wasteTable.getCellEditor().stopCellEditing();
    }
    Vector updateV = new Vector();
    for(int i = 0; i < wasteTable.getRowCount(); i++) {
      Hashtable h = new Hashtable();
      String myTempQty = BothHelpObjs.makeBlankFromNull(wasteTable.getModel().getValueAt(i,QTY_COL).toString());
      if (myTempQty.startsWith("-")) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg("Please enter a positive real number for quantity.");
        gd.setVisible(true);
        setSelectedRow(i);
        return;
      }

      if (myTempQty.length() > 0) {
        try {
          Float tempQty = Float.valueOf(myTempQty);
        }catch (Exception ee) {
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
          gd.setMsg("Please enter a positive real number for quantity.");
          gd.setVisible(true);
          setSelectedRow(i);
          return;
        }
      }

      h.put("QTY",wasteTable.getModel().getValueAt(i,QTY_COL));
      h.put("COST_TYPE",wasteTable.getModel().getValueAt(i,COST_TYPE_COL));
      updateV.addElement(h);
    }
    updateShipmentCharges(updateV);
  }

  void updateShipmentCharges(Vector updateV) {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","UPDATE_SHIPMENT_CHARGES"); //String, String
      query.put("UPDATE_VECTOR",updateV);
      query.put("SHIPMENT_ID",new Integer(shipmentId));    //String, Integer

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      Boolean suc = (Boolean)result.get("SUCCEEDED");
      if (suc.booleanValue()) {
        infoChanged = true;
        GenericDlg g = new GenericDlg(cmis.getMain(),"SUCCEEDED",true);
        g.setMsg(result.get("MSG").toString());
        g.setVisible(true);
      } else {
        GenericDlg g = new GenericDlg(cmis.getMain(),"ERROR",true);
        g.setMsg(result.get("MSG").toString());
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

  void wasteTable_mouseClicked(MouseEvent e) {
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
    if (infoChanged) {
      wvi.reLoadItAction();
      wvi.buildVendorInvoiceData(currentPos);
    }
  }
}

class ShipmentChargesEditLoadThread extends Thread {
  ShipmentCharges parent;
  public ShipmentChargesEditLoadThread(ShipmentCharges parent){
     super("ShipmentChargesEditLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class ShipmentCharges_Edit_mouseAdapter extends java.awt.event.MouseAdapter {
  ShipmentCharges adaptee;
  ShipmentCharges_Edit_mouseAdapter(ShipmentCharges adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.wasteTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}

