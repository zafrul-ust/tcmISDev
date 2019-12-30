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


public class ChangingProfile extends JDialog {

  CmisTable profileTable;
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
  String packaging = null;
  String containerId = null;
  WasteManage wasteManage;
  WasteOrder wasteOrder;
  boolean fromWasteManage = false;
  boolean fromWasteOrder = false;
  boolean fromWastePickupRequest = false;
  WastePickupRequest wastePickupRequest;
  int selectedR = 0;
  boolean changeTsdfProfile = false;


  public ChangingProfile(JFrame fr,String facilityId, String title, String packaging, String containerId, int row) {
    super(fr, title, false);
    this.facilityId = facilityId;
    this.packaging = packaging;
    this.containerId = containerId;
    //this.fromWasteManage = fromWM;
    this.selectedR = row;

    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(550, 600));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setChangeTsdfProfile(boolean b) {
    this.changeTsdfProfile = b;
  }

  public void setWastePickupRequest(WastePickupRequest wpr) {
    this.wastePickupRequest = wpr;
  }
  public void setFromWastePickupRequest(boolean b) {
    this.fromWastePickupRequest = b;
  }

  public void setFromWasteManage(boolean b) {
    this.fromWasteManage = b;
  }
  public void setWasteManage(WasteManage wm) {
    this.wasteManage = wm;
  }
  public void setWasteOrder(WasteOrder wo) {
    this.wasteOrder = wo;
  }
  public void setFromWasteOrder(boolean b) {
    this.fromWasteOrder = b;
  }

  public void setParent(CmisApp c){
    cmis = c;
  }

  public void loadIt(){
    ChangingProfileLoadThread x = new ChangingProfileLoadThread(this);
    x.start();
  }

  void jbInit() throws Exception {
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

  }

  void loadItAction() {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_PROFILE");
      query.put("FAC_ID",this.facilityId); //String, String
      query.put("PACKAGING",this.packaging);
      query.put("CHANGE_TSDF_PROFILE",new Boolean(changeTsdfProfile));
      query.put("CONTAINER_ID",containerId);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      partJSP.getViewport().remove(profileTable);

      Hashtable profileInfoH = (Hashtable)result.get("PROFILE_INFO");

      DbTableModel ctm = buildTableModel(profileInfoH);
      sorter = new TableSorter(ctm);
      profileTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(profileTable);
      profileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      profileTable.addMouseListener(new Change_Profile_mouseAdapter(this));
      profileTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      //profileTable.setCellSelectionEnabled(false);
      profileTable.getTableHeader().setReorderingAllowed(true);

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
      WASTE_ITEM_ID_COL = ((Integer)keyCols.get("WASTE_ITEM_ID")).intValue();

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
    String wasteItemId = profileTable.getModel().getValueAt(profileTable.getSelectedRow(),WASTE_ITEM_ID_COL).toString();
    boolean result = updateRequest(wasteItemId);
    setVisible(false);
    if (result) {
      if (fromWasteManage) {
        /*when I decide to do this I will need more data: state_waste_code,waste_mgmt_option,mgmt_opt_location,waste_category,waste_type, lpp
        String profileId = profileTable.getModel().getValueAt(profileTable.getSelectedRow(),PROFILE_ID_COL).toString();
        String profileDesc = profileTable.getModel().getValueAt(profileTable.getSelectedRow(),PROFILE_DESC_COL).toString();
        wasteManage.wasteTable.setValueAt(wasteItemId,selectedR,wasteManage.WASTE_ITEM_ID_COL);
        wasteManage.wasteTable.setValueAt(profileId,selectedR,wasteManage.VENDOR_PROFILE_ID_COL);
        wasteManage.wasteTable.setValueAt(profileDesc,selectedR,wasteManage.DESC_COL);
        */
        wasteManage.doSearch();
        wasteManage.setSelectedRow(selectedR);
      }else if(fromWasteOrder) {
        wasteOrder.loadItAction("GET_SHIPMENT_LIST");
        wasteOrder.setContainerSelectedRow(selectedR);
      }else {
        wastePickupRequest.loadItAction();
        wastePickupRequest.setSelectedRow(selectedR);
      }
    }
  }

  boolean updateRequest(String wasteItemId) {
    boolean val = true;
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","CHANGE_PROFILE"); //String, String
      query.put("CONTAINER_ID",this.containerId);
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

  void profileTable_mouseClicked(MouseEvent e) {

  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }
}

class ChangingProfileLoadThread extends Thread {
  ChangingProfile parent;
  public ChangingProfileLoadThread(ChangingProfile parent){
     super("ChangingProfileLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class Change_Profile_mouseAdapter extends java.awt.event.MouseAdapter {
  ChangingProfile adaptee;
  Change_Profile_mouseAdapter(ChangingProfile adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.profileTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}