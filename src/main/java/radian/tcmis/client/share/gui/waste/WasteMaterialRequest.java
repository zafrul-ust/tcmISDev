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
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;


public class WasteMaterialRequest extends JDialog {

  CmisTable newWasteMaterialTable;
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

  String storageArea = null;
  String vendorId = null;
  int orderNo = 0;
  String shipmentID = null;

  public WasteMaterialRequest(JFrame fr, String title, String facID, String vendorID) {
    super(fr, title, false);
    this.facilityId = facID;
    this.vendorId = vendorID;
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

  public void setOrderNo(int n) {
    this.orderNo = n;
  }

  public void setShipmentId(String val) {
    this.shipmentID = val;
  }

  public void loadIt(){
    AddMaterialLoadThread x = new AddMaterialLoadThread(this);
    x.start();
  }

  void jbInit() throws Exception {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/add.gif");
    addB.setIcon(ss);
    panel1.setLayout(borderLayout4);
    jPanel1.setLayout(xYLayout1);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    addB.setText("Add");
    addB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });

    newWasteMaterialTable = new CmisTable();
    partJSP.getViewport().setView(newWasteMaterialTable);
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
  }

  void loadItAction() {
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_REQUESTED_WASTE_MATERIAL"); //String, String
      query.put("FAC_ID",this.facilityId); //String, String
      query.put("VENDOR_ID",this.vendorId);
      query.put("SHIPMENT_ID",this.shipmentID);  //String, String
      query.put("ORDER_NO",new Integer(this.orderNo));  //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }


      partJSP.getViewport().remove(newWasteMaterialTable);

      Hashtable profileInfoH = (Hashtable)result.get("REQUESTED_WASTE_MATERIAL_INFO");

      DbTableModel ctm = (DbTableModel)profileInfoH.get("DATA_MODEL");
      sorter = new TableSorter(ctm);
      newWasteMaterialTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(newWasteMaterialTable);
      newWasteMaterialTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      newWasteMaterialTable.addMouseListener(new Add_Material_mouseAdapter(this));
      newWasteMaterialTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      newWasteMaterialTable.setCellSelectionEnabled(false);
      newWasteMaterialTable.getTableHeader().setReorderingAllowed(true);

      // set column widths
      for(int i=0;i<newWasteMaterialTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        newWasteMaterialTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        newWasteMaterialTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          newWasteMaterialTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          newWasteMaterialTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
      }

      partJSP.getViewport().setView(newWasteMaterialTable);

      ClientHelpObjs.setEnabledContainer(this,true);
      ClientHelpObjs.setComboBoxUpdateUi(this);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }
    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }

  void addB_actionPerformed(ActionEvent e) {
    Vector v = getExistingItem();
    WasteMaterialCatalog wmc = new WasteMaterialCatalog(cmis.getMain(),null,"Waste Material Profiles",this.facilityId,this.vendorId);
    wmc.setShipmentId(this.shipmentID);
    wmc.setOrderNo(this.orderNo);
    wmc.setParent(cmis);
    wmc.setCurrentItem(v);
    wmc.loadItAction();
    CenterComponent.centerCompOnScreen(wmc);
    wmc.setVisible(true);
  }
  Vector getExistingItem() {
    Vector result = new Vector();
    for (int i = 0; i < newWasteMaterialTable.getRowCount(); i++) {
      String myItem = newWasteMaterialTable.getModel().getValueAt(i,0).toString();
      result.addElement(myItem);
    }
    return result;
  }

  void containerTable_mouseClicked(MouseEvent e) {

  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }
}

class AddMaterialLoadThread extends Thread {
  WasteMaterialRequest parent;
  public AddMaterialLoadThread(WasteMaterialRequest parent){
     super("AddMaterialLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class Add_Material_mouseAdapter extends java.awt.event.MouseAdapter {
  WasteMaterialRequest adaptee;
  Add_Material_mouseAdapter(WasteMaterialRequest adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.containerTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}