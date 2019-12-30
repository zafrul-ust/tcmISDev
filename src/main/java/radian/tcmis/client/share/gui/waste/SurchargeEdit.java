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

public class SurchargeEdit extends JDialog {

  CmisTable wasteTable;
  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
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

  int ITEM_COL = 0;
  int SURCHARGE_COL = 0;
  int COST_COL = 0;
  int WASTE_ITEM_ID_COL = 0;
  int SURCHARGE_ID_COL = 0;
  int BULK_OR_CONTAINER_COL = 0;
  int orderNo;
  int shipId;
  String containerShipmentId = null;
  Vector bulkShipmentIdV = null;

  //trong 9-21
  WasteOrder wasteOrder = null;

  WasteVendorInvoice wvi = null;
  boolean infoChanged = false;
  int currentPos = 0;



  public SurchargeEdit(JFrame fr, String title,int shipId,WasteVendorInvoice wvi, int currentPos) {
    super(fr, title, false);
    this.shipId = shipId;
    this.wvi = wvi;
    this.currentPos = currentPos;
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

  public void loadIt(){
    SurchargeEditLoadThread x = new SurchargeEditLoadThread(this);
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
    jPanel1.setLayout(xYLayout1);
    okB.setText("Update");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    wasteTable = new CmisTable();
    partJSP.getViewport().setView(wasteTable);

    panel1.setMaximumSize(new Dimension(520, 380));
    panel1.setMinimumSize(new Dimension(520, 380));
    panel1.setPreferredSize(new Dimension(520, 380));

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
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_SURCHARGE_INFO"); //String, String
      query.put("SHIPMENT_ID",new Integer(shipId));   //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      Boolean suc = (Boolean)result.get("SUCCEEDED");
      if (suc.booleanValue()) {
        partJSP.getViewport().remove(wasteTable);

        Hashtable editInfoH = (Hashtable)result.get("SURCHARGE_INFO");
        //column
        Hashtable keyCols = (Hashtable)editInfoH.get("KEY_COLS");
        ITEM_COL = ((Integer)keyCols.get("Item")).intValue();
        SURCHARGE_COL = ((Integer)keyCols.get("Surcharge")).intValue();
        COST_COL = ((Integer)keyCols.get("Cost")).intValue();
        SURCHARGE_ID_COL = ((Integer)keyCols.get("Surcharge ID")).intValue();
        WASTE_ITEM_ID_COL = ((Integer)keyCols.get("Waste Item ID")).intValue();
        BULK_OR_CONTAINER_COL = ((Integer)keyCols.get("Bulk Or Container")).intValue();

        DbTableModel ctm = buildTableModel(editInfoH);
        ctm.setEditableFlag(BothHelpObjs.mypow(2,COST_COL));
        sorter = new TableSorter(ctm);
        wasteTable = new CmisTable(sorter);
        sorter.setColTypeFlag(ctm.getColumnTypesString());
        sorter.addMouseListenerToHeaderInTable(wasteTable);

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
        wasteTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = wasteTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
        }

        wasteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //wasteTable.setCellSelectionEnabled(false);
        wasteTable.getTableHeader().setReorderingAllowed(true);
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
        if (wasteTable.getModel().getRowCount()<=0){
          GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
          g.setMsg("No Records found.");
          g.setVisible(true);
        }
        ClientHelpObjs.setEnabledContainer(this,true);
        this.validate();
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
    if (wasteTable.isEditing()) {
      wasteTable.getCellEditor().stopCellEditing();
    }
    Vector updateV = new Vector();
    for(int i = 0; i < wasteTable.getRowCount(); i++) {
      Hashtable h = new Hashtable();
      h.put("COST",wasteTable.getModel().getValueAt(i,COST_COL));
      h.put("SURCHARGE_ID",wasteTable.getModel().getValueAt(i,SURCHARGE_ID_COL));
      h.put("WASTE_ITEM_ID",wasteTable.getModel().getValueAt(i,WASTE_ITEM_ID_COL));
      h.put("BULK_OR_CONTAINER",wasteTable.getModel().getValueAt(i,BULK_OR_CONTAINER_COL));
      updateV.addElement(h);
    }
    updateSurcharge(updateV);
  }

  void updateSurcharge(Vector updateV) {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","UPDATE_SURCHARGE"); //String, String
      query.put("UPDATE_VECTOR",updateV);
      query.put("SHIPMENT_ID",new Integer(this.shipId));    //String, Integer

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
  /*
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
      if(wasteTable.getModel().getValueAt(row,SELECTED_COL).toString().equalsIgnoreCase("true")) {
        wasteTable.getModel().setValueAt(new Boolean(false),row,SELECTED_COL);
        wasteTable.getModel().setValueAt(new String(""),row,ORDER_NUMBER_COL);
      }else {
        wasteTable.getModel().setValueAt(new Boolean(true),row,SELECTED_COL);
        wasteTable.getModel().setValueAt(new String(orderNo),row,ORDER_NUMBER_COL);
      }
      wasteTable.repaint();
    }                             */
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

class SurchargeEditLoadThread extends Thread {
  SurchargeEdit parent;
  public SurchargeEditLoadThread(SurchargeEdit parent){
     super("SurchargeEditLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class Surcharge_Edit_mouseAdapter extends java.awt.event.MouseAdapter {
  SurchargeEdit adaptee;
  Surcharge_Edit_mouseAdapter(SurchargeEdit adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.wasteTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}