/*
package radian.tcmis.client.share.gui.waste;

public class AddInto {

  public AddInto() {
  }
}   */

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


public class AddInto extends JDialog {

  CmisTable chargeTable;
  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JButton okB = new JButton();
  //BorderLayout borderLayout3 = new BorderLayout();

  String facilityId;
  String storageLocation;

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
  JLabel profileL = new JLabel();
  JLabel toProfileL = new JLabel();
  JLabel actSysL = new JLabel();
  JLabel amountL = new JLabel();
  JTextField amountT = new JTextField();
  JLabel unitL = new JLabel();
  JComboBox unitC = new JComboBox();
  JComboBox actSysC = new JComboBox();

  int PROFILE_DESC_COL = 0;
  int PROFILE_ID_COL = 0;

  String orderNo;
  String containerShipmentId = null;
  Vector bulkShipmentIdV = null;
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JPanel rightP = new JPanel();
  JPanel leftP = new JPanel();


  public AddInto(JFrame fr,String facilityId, String title, String storageLocation) {
    super(fr, title, false);
    this.facilityId = facilityId;
    this.storageLocation = storageLocation;

    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(400, 400));
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
    AddIntoLoadThread x = new AddIntoLoadThread(this);
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
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    chargeTable = new CmisTable();
    partJSP.getViewport().setView(chargeTable);
    panel1.setMaximumSize(new Dimension(520, 380));
    panel1.setMinimumSize(new Dimension(520, 380));
    panel1.setPreferredSize(new Dimension(520, 380));

    jPanel6.setLayout(gridBagLayout1);
    jPanel7.setLayout(gridBagLayout3);
    leftP.setLayout(borderLayout5);
    rightP.setLayout(gridBagLayout4);

    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    toProfileL.setText("To Profile:");
    actSysL.setText("Account System:");
    amountL.setText("Amount:");
    unitL.setText("Unit:");
    profileL.setText("Test Profile");

    bottomP.setLayout(borderLayout1);
    amountT.setPreferredSize(new Dimension(80, 24));
    amountT.setMaximumSize(new Dimension(80, 24));
    amountT.setMinimumSize(new Dimension(80, 24));
    leftP.setMaximumSize(new Dimension(200, 200));
    leftP.setMinimumSize(new Dimension(150, 150));
    leftP.setPreferredSize(new Dimension(150, 150));
    unitC.setMaximumSize(new Dimension(80, 24));
    unitC.setMinimumSize(new Dimension(80, 24));
    unitC.setPreferredSize(new Dimension(80, 24));
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);

    jPanel6.add(toProfileL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 15, 20, 0), 0, 0));
    jPanel6.add(profileL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 90, 20, 0), 0, 0));

    jPanel6.add(jPanel7, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(50, 15, 50, 15), 0, 0));
    jPanel7.add(actSysL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(18, 15, 50, 0), 0, 0));
    jPanel7.add(actSysC, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 130, 50, 0), 0, 0));

    jPanel7.add(leftP, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(50, 15, 0, 0), 0, 0));
    leftP.add(partJSP, BorderLayout.CENTER);

    jPanel7.add(rightP, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(50, 0, 0, 0), 0, 0));
    rightP.add(amountL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 10, 20, 0), 0, 0));
    rightP.add(amountT, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 80, 20, 0), 0, 0));
    rightP.add(unitL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(85, 10, 10, 0), 0, 0));
    rightP.add(unitC, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(78, 70, 10, 10), 0, 0));

    /*
    jPanel6.add(jPanel7, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(50, 15, 50, 15), 0, 0));

    jPanel7.add(actSysL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 15, 10, 0), 0, 0));
    jPanel7.add(actSysC, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 80, 10, 0), 0, 0));

    jPanel7.add(amountL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(50, 10, 10, 0), 0, 0));
    jPanel7.add(amountT, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(48, 100, 10, 10), 0, 0));

    jPanel7.add(unitL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(70, 10, 10, 0), 0, 0));
    jPanel7.add(unitC, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(68, 100, 10, 10), 0, 0));

    jPanel7.add(partJSP, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(90, 15, 0, 0), 0, 0));  */

    jPanel6.add(bottomP, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
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
      query.put("ACTION","GET_CONTAINER_PROFILE"); //String, String
      query.put("FAC_ID",this.facilityId); //String, String
      query.put("STORAGE_LOCATION",storageLocation);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      partJSP.getViewport().remove(chargeTable);

      Hashtable profileInfoH = (Hashtable)result.get("CONTAINER_PROFILE_INFO");

      DbTableModel ctm = (DbTableModel)profileInfoH.get("DATA_MODEL");
      sorter = new TableSorter(ctm);
      chargeTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(chargeTable);
      chargeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      chargeTable.addMouseListener(new AddInto_mouseAdapter(this));
      chargeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      chargeTable.setCellSelectionEnabled(false);
      chargeTable.getTableHeader().setReorderingAllowed(true);

      /*
      WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
      WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();

      chargeTable.setDefaultRenderer(String.class, colorTableRenderer);
      chargeTable.setDefaultRenderer(Integer.class, colorTableRenderer);
      chargeTable.setDefaultRenderer(Double.class, colorTableRenderer);
      chargeTable.setDefaultRenderer(Boolean.class, checkTableRenderer);

      JCheckBox check = new JCheckBox();
      check.setHorizontalAlignment(JLabel.CENTER);
      chargeTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor(check));
      */

      // set column widths
      for(int i=0;i<chargeTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        chargeTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        chargeTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          chargeTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          chargeTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
      }

      //column
      Hashtable keyCols = (Hashtable)result.get("KEY_COLS");
      PROFILE_DESC_COL = ((Integer)keyCols.get("PROFILE_DESC")).intValue();
      PROFILE_ID_COL = ((Integer)keyCols.get("PROFILE_ID")).intValue();

      partJSP.getViewport().setView(chargeTable);
      if (chargeTable.getModel().getRowCount()<=0){
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

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    Vector updateV = new Vector();
    /*
    for(int i = 0; i < chargeTable.getRowCount(); i++) {
      if (chargeTable.getModel().getValueAt(i,SELECTED_COL).toString().equalsIgnoreCase("true")){
        Hashtable h = new Hashtable();
        h.put("CONTAINER_TYPE",chargeTable.getModel().getValueAt(i,CONTAINER_TYPE_COL));
        h.put("CONTAINER_ID",chargeTable.getModel().getValueAt(i,CONTAINER_ID_COL));
        h.put("SHIPMENT_ID",chargeTable.getModel().getValueAt(i,SHIPMENT_ID_COL));
        h.put("FACILITY_ID",chargeTable.getModel().getValueAt(i,FACILITY_ID_COL));
        updateV.addElement(h);
      }
    } */
    //go to server side and change the selected container's unknown profile to selected profile
    updateRequest(updateV);

    //change the unknown profile to the selected profile (search table)



    setVisible(false);
  }

  void updateRequest(Vector updateV) {
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","UPDATE_CHANGE_PROFILE_REQUEST"); //String, String
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

  void containerTable_mouseClicked(MouseEvent e) {
   /*
    int row   = chargeTable.getSelectedRow();
    //Waste already on order.
    String num = chargeTable.getModel().getValueAt(row,ORDER_NUMBER_COL).toString();
    if ((e.getClickCount() > 1) && (num.length() > 0) && (!num.equals(orderNo))) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Waste already on order.",true);
        gd.setMsg("Please choose another line item.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
    } else {
     //editing the shipment list.
      if(chargeTable.getModel().getValueAt(row,SELECTED_COL).toString().equalsIgnoreCase("true")) {
        chargeTable.getModel().setValueAt(new Boolean(false),row,SELECTED_COL);
        chargeTable.getModel().setValueAt(new String(""),row,ORDER_NUMBER_COL);
      }else {
        chargeTable.getModel().setValueAt(new Boolean(true),row,SELECTED_COL);
        chargeTable.getModel().setValueAt(new String(orderNo),row,ORDER_NUMBER_COL);
      }
      chargeTable.repaint();
    }    */
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }
}

class AddIntoLoadThread extends Thread {
  AddInto parent;
  public AddIntoLoadThread(AddInto parent){
     super("AddIntoLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class AddInto_mouseAdapter extends java.awt.event.MouseAdapter {
  AddInto adaptee;
  AddInto_mouseAdapter(AddInto adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.containerTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}

/*
 jPanel6.add(jPanel7, new GridBagConstraints2(0, 2, 0, 0, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(50, 15, 50, 15), 0, 0));
    jPanel7.add(actSysL, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(18, 15, 50, 0), 0, 0));
    jPanel7.add(actSysC, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 130, 50, 0), 0, 0));

    jPanel7.add(leftP, new GridBagConstraints2(0, 1, 0, 0, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(50, 15, 0, 0), 0, 0));
    leftP.add(partJSP, BorderLayout.CENTER);

    jPanel7.add(rightP, new GridBagConstraints2(0, 1, 0, 0, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(50, 0, 0, 0), 0, 0));
    rightP.add(amountL, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 10, 20, 0), 0, 0));
    rightP.add(amountT, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 80, 20, 0), 0, 0));
    rightP.add(unitL, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(15, 10, 100, 0), 0, 0));
    rightP.add(unitC, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(8, 70, 100, 10), 0, 0));
*/
