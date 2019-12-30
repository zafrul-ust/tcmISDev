//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.GridBagConstraints2;
import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.appData.*;
import java.beans.*;
import javax.swing.table.*;
import java.util.*;
import java.text.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;
import java.awt.event.*;
import javax.swing.event.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class WasteVendorInvoice extends JPanel {
  CmisApp cmis;
  int shipNum;

  DbTableModel model;
  DbTableModel ctm;
  TableSorter sorter;
  CmisTable rliTable;
  CmisTable costTable;
  DbTableModel costModel;
  CmisTable invoiceTable;
  DbTableModel invoiceModel;
  JTable formReturnedT = new JTable();
  Vector manifestV = new Vector();
  Vector copyV = new Vector();
  Vector certV = new Vector();
  Hashtable vendorInvoiceDataH = null;
  Vector shipmentIdV = null;
  int currentPos;
  Hashtable updateDataH = new Hashtable();
  Vector shipmentStatusV = null;
  Hashtable processH = null;
  Vector processV = null;
  int lastSelectedIndex = 0;

  int EXTENDED_COL = 0;
  int TOTAL_COL = 0;

  //String imagesDir = "d:/projects/java/radian/tcmis/ray/client/client/gui/images/button/navigation/";
  /*String wDir = new String(System.getProperty("user.dir"));
  String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") + (new String("/button/"));
*/
  GridBagLayout gridBagLayout1 = new GridBagLayout();  // shipment tab
  GridBagLayout gridBagLayout2 = new GridBagLayout();  //right panel
  JPanel detailP = new JPanel();
  JScrollPane transferJsp = new JScrollPane();
  JButton firstB = new JButton();
  JButton previousB = new JButton();
  JButton nextB = new JButton();
  JButton lastB = new JButton();
  JButton updateB = new JButton();
  JPanel topP = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel headerP = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  StaticJLabel vendorOrderL = new StaticJLabel();
  //DataJLabel vendorOrderT = new DataJLabel();
  StaticJLabel amountL = new StaticJLabel();
  DataJLabel amountT = new DataJLabel();
  StaticJLabel shipmentL = new StaticJLabel();
  DataJLabel shipmentT = new DataJLabel();
  JPanel bottomP = new JPanel();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JPanel costP = new JPanel();
  GridBagLayout gridBagLayout6 = new GridBagLayout();
  JScrollPane costJsp = new JScrollPane();
  JButton surchargeB = new JButton();
  JButton chargeB = new JButton();
  JButton approveB = new JButton();
  StaticJLabel jLabel1 = new StaticJLabel();
  JComboBox dateC = new JComboBox();
  JPanel invoiceP = new JPanel();
  GridBagLayout gridBagLayout7 = new GridBagLayout();
  JScrollPane invoiceJsp = new JScrollPane();

  JTextField vendorOrderT = new JTextField();
  WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();

  public WasteVendorInvoice() {
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public WasteVendorInvoice(CmisApp cmis) {
    this.cmis = cmis;
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setCmis(CmisApp cmis){
    this.cmis = cmis;
    loadIt();
  }

  public void setShipmentNum(int shipNum) {
    this.shipNum = shipNum;
  }

  public void loadIt(){
    WasteVendorInvoiceLoadThread silt = new WasteVendorInvoiceLoadThread(this,"load");
    silt.start();
  }

  private void jbInit() throws Exception {
    //String wDir = new String(System.getProperty("user.dir"));
    //String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;

    this.setLayout(gridBagLayout1);
    detailP.setMaximumSize(new Dimension(150, 125));
    detailP.setMinimumSize(new Dimension(150, 125));
    detailP.setPreferredSize(new Dimension(150, 125));

    //initializing transfer info table.
    rliTable = new CmisTable();
    transferJsp.getViewport().setView(rliTable);

    costTable = new CmisTable();
    costJsp.getViewport().setView(costTable);

    invoiceTable = new CmisTable();
    invoiceJsp.getViewport().setView(invoiceTable);

    ImageIcon ss = null;
    //this.setBorder(ClientHelpObjs.groupBox("Vendor Invoice"));
    topP.setBorder(ClientHelpObjs.groupBox("Navigation"));
    topP.setMinimumSize(new Dimension(308, 50));
    topP.setPreferredSize(new Dimension(308, 50));
    bottomP.setBorder(ClientHelpObjs.groupBox("Cost Info."));
    ss = ResourceLoader.getImageIcon("images/button/first1.gif");
    firstB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        firstB_mousePressed(e);
      }

      public void mouseReleased(MouseEvent e) {
        firstB_mouseReleased(e);
      }

      public void mouseClicked(MouseEvent e) {
        firstB_mouseClicked(e);
      }
    });
    firstB.setBorder(null);
    firstB.setMaximumSize(new Dimension(18, 18));
    firstB.setMinimumSize(new Dimension(18, 18));
    firstB.setPreferredSize(new Dimension(18, 18));
    firstB.setIcon(ss);

    ss = ResourceLoader.getImageIcon("images/button/prev1.gif");
    previousB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        previousB_mousePressed(e);
      }

      public void mouseReleased(MouseEvent e) {
        previousB_mouseReleased(e);
      }

      public void mouseClicked(MouseEvent e) {
        previousB_mouseClicked(e);
      }
    });
    previousB.setBorder(null);
    previousB.setMaximumSize(new Dimension(18, 18));
    previousB.setMinimumSize(new Dimension(18, 18));
    previousB.setPreferredSize(new Dimension(18, 18));
    previousB.setIcon(ss);

    ss = ResourceLoader.getImageIcon("images/button/next1.gif");
    nextB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        nextB_mousePressed(e);
      }

      public void mouseReleased(MouseEvent e) {
        nextB_mouseReleased(e);
      }

      public void mouseClicked(MouseEvent e) {
        nextB_mouseClicked(e);
      }
    });
    nextB.setBorder(null);
    nextB.setMaximumSize(new Dimension(18, 18));
    nextB.setMinimumSize(new Dimension(18, 18));
    nextB.setPreferredSize(new Dimension(18, 18));
    nextB.setIcon(ss);

    ss = ResourceLoader.getImageIcon("images/button/last1.gif");
    lastB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        lastB_mousePressed(e);
      }

      public void mouseReleased(MouseEvent e) {
        lastB_mouseReleased(e);
      }

      public void mouseClicked(MouseEvent e) {
        lastB_mouseClicked(e);
      }
    });
    lastB.setBorder(null);
    lastB.setMaximumSize(new Dimension(18, 18));
    lastB.setMinimumSize(new Dimension(18, 18));
    lastB.setPreferredSize(new Dimension(18, 18));
    lastB.setIcon(ss);

    updateB.setFont(new java.awt.Font("Dialog", 0, 10));
    updateB.setMaximumSize(new Dimension(90, 25));
    updateB.setMinimumSize(new Dimension(90, 25));
    updateB.setPreferredSize(new Dimension(90, 25));
    updateB.setText("Update");
    updateB.setIcon(ResourceLoader.getImageIcon("images/button/save.gif","Update"));
    updateB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateB_actionPerformed(e);
      }
    });

    topP.setLayout(gridBagLayout3);
    headerP.setLayout(gridBagLayout4);
    vendorOrderL.setText("Vendor Order:");
    amountL.setText("Total Amount:");
    shipmentL.setText("Radian Shipment:");

    bottomP.setLayout(gridBagLayout5);
    bottomP.setMinimumSize(new Dimension(500, 231));
    bottomP.setPreferredSize(new Dimension(500, 231));
    headerP.setMinimumSize(new Dimension(266, 45));
    headerP.setPreferredSize(new Dimension(266, 45));
    costP.setLayout(gridBagLayout6);
    costP.setMinimumSize(new Dimension(295, 24));
    costP.setPreferredSize(new Dimension(295, 24));
    surchargeB.setFont(new java.awt.Font("Dialog", 0, 10));
    surchargeB.setMaximumSize(new Dimension(105, 25));
    surchargeB.setMinimumSize(new Dimension(105, 25));
    surchargeB.setPreferredSize(new Dimension(105, 25));
    surchargeB.setText("Surcharge");
    surchargeB.setToolTipText("Add/Edit Surcharge  ");
    surchargeB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        surchargeB_actionPerformed(e);
      }
    });
    surchargeB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Add"));
    chargeB.setFont(new java.awt.Font("Dialog", 0, 10));
    chargeB.setMaximumSize(new Dimension(150, 25));
    chargeB.setMinimumSize(new Dimension(150, 25));
    chargeB.setPreferredSize(new Dimension(150, 25));
    chargeB.setText("Shipment Charges");
    chargeB.setToolTipText("Add/Edit Shipment Charges  ");
    chargeB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        chargeB_actionPerformed(e);
      }
    });
    chargeB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Add"));
    approveB.setFont(new java.awt.Font("Dialog", 0, 10));
    approveB.setMaximumSize(new Dimension(110, 25));
    approveB.setMinimumSize(new Dimension(110, 25));
    approveB.setPreferredSize(new Dimension(110, 25));
    approveB.setText("Approve");
    approveB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        approveB_actionPerformed(e);
      }
    });
    jLabel1.setText("Approvals:");
    //approveB.setIcon(ResourceLoader.getImageIcon("images/button/checked.gif","Approve"));
    dateC.setMaximumSize(new Dimension(130, 21));
    dateC.setMinimumSize(new Dimension(130, 21));
    dateC.setPreferredSize(new Dimension(130, 21));

    invoiceP.setLayout(gridBagLayout7);
    invoiceP.setMinimumSize(new Dimension(160, 24));
    invoiceP.setPreferredSize(new Dimension(160, 24));
    vendorOrderT.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        vendorOrderT_actionPerformed(e);
      }
    });
    vendorOrderT.setMaximumSize(new Dimension(100, 21));
    vendorOrderT.setMinimumSize(new Dimension(100, 21));
    vendorOrderT.setPreferredSize(new Dimension(100, 21));
    this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(updateB, new GridBagConstraints2(9, 0, 1, 2, 0.5, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 10, 0), 0, 0));
    topP.add(nextB, new GridBagConstraints2(2, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    topP.add(previousB, new GridBagConstraints2(1, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    topP.add(firstB, new GridBagConstraints2(0, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    topP.add(lastB, new GridBagConstraints2(3, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    topP.add(shipmentL, new GridBagConstraints2(4, 0, 1, 2, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 0, 0));
    topP.add(shipmentT, new GridBagConstraints2(5, 0, 1, 2, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 10, 0), 0, 0));
    topP.add(surchargeB, new GridBagConstraints2(6, 1, 1, 2, 0.5, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 10, 0), 0, 0));
    topP.add(chargeB, new GridBagConstraints2(7, 1, 1, 2, 0.5, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 10, 0), 0, 0));
    topP.add(approveB, new GridBagConstraints2(10, 1, 1, 2, 0.5, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 10, 0), 0, 0));


    detailP.setLayout(gridBagLayout2);
    this.add(bottomP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    bottomP.add(headerP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    headerP.add(vendorOrderL, new GridBagConstraints2(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    headerP.add(vendorOrderT, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 75, 5, 0), 0, 0));
    headerP.add(amountL, new GridBagConstraints2(0, 2, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    headerP.add(amountT, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 85, 0, 0), 0, 0));
    headerP.add(costP, new GridBagConstraints2(2, 0, 1, 3, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    costP.add(costJsp, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    headerP.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    headerP.add(dateC, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 60, 5, 0), 0, 0));
    headerP.add(invoiceP, new GridBagConstraints2(1, 0, 1, 3, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
    invoiceP.add(invoiceJsp, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    bottomP.add(detailP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    detailP.add(transferJsp, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
  }

  public void loadItAction(String action) {
     CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
     try{
      //ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_VENDOR_INVOICE_INFO_TAB"); //String, String
      query.put("MANAGER_ID",cmis.getUserId());
      query.put("SHIPMENT_NUM",new Integer(shipNum));

      System.out.println("sendData:"+query);
      Hashtable result = cgi.getResultHash(query);
      ClientHelpObjs.setEnabledContainer(this,true);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      vendorInvoiceDataH = (Hashtable)result.get("VENDOR_INVOICE_DATA");
      vendorInvoiceDataH.put("TABLE_STYLE",(Hashtable)result.get("TABLE_STYLE"));
      shipmentIdV = (Vector)vendorInvoiceDataH.get("SHIPMENT_ID");
      shipmentStatusV = (Vector)vendorInvoiceDataH.get("SHIPMENT_STATUS");
      processH = (Hashtable)vendorInvoiceDataH.get("INVOICE_PROCESS");
      //getting the extended and total col so I could calculate the total amount for a given shipment
      EXTENDED_COL = ((Integer)result.get("EXTENDED_COL")).intValue();
      TOTAL_COL = ((Integer)result.get("TOTAL_COL")).intValue();

      ClientHelpObjs.setEnabledContainer(this,true);
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      ClientHelpObjs.setEnabledContainer(this,true);
      this.revalidate();
    }

    currentPos = 0;
    buildVendorInvoiceData(currentPos);

    dateC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
        //System.out.println("in item state Change");
        buildProcessData();
        }
      });

    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }

  public void buildProcessData() {
    int selectedIndex = dateC.getSelectedIndex();
    //System.out.println("---------- got here: "+selectedIndex);
    if (selectedIndex == lastSelectedIndex || selectedIndex < 0) {
      System.out.println("--------- do nothing");
    }else {

    lastSelectedIndex = selectedIndex;
    String shipmentId = shipmentIdV.elementAt(currentPos).toString();
    String myStatus = shipmentStatusV.elementAt(currentPos).toString();    //3-12-01

    //System.out.println("--------- date "+processV);

    String key = (String)processV.elementAt(selectedIndex);

    key = shipmentId + key;

    if (currentPos == shipmentIdV.size()-1) {
      firstB.setEnabled(true);
      previousB.setEnabled(true);
      nextB.setEnabled(false);
      lastB.setEnabled(false);
    } else if (currentPos == 0) {
      firstB.setEnabled(false);
      previousB.setEnabled(false);
      nextB.setEnabled(true);
      lastB.setEnabled(true);
    } else {
      firstB.setEnabled(true);
      previousB.setEnabled(true);
      nextB.setEnabled(true);
      lastB.setEnabled(true);
    }

    //Hashtable innerH = (Hashtable)vendorInvoiceDataH.get(shipmentId);
    Hashtable innerH = (Hashtable)vendorInvoiceDataH.get(key);
    buildInvoiceTableModel(innerH);
    buildVendorInvoiceTable();

    costModel = (DbTableModel)innerH.get("EXTRA_COST_TABLE");
    buildCostTable();

    Vector vendorInvoiceV = null;
    if (updateDataH.containsKey(shipmentId)){
      Hashtable myH = (Hashtable)updateDataH.get(shipmentId);
      vendorInvoiceV = (Vector)myH.get("VENDOR_INVOICE");
    } else {
      vendorInvoiceV = (Vector)innerH.get("VENDOR_INVOICE");
    }
    buildInvoiceTable(vendorInvoiceV);

    //trong 3-05-01
    if(myStatus.equalsIgnoreCase("draft") || myStatus.equalsIgnoreCase("ammended") ) {
      if (selectedIndex == 0) {
        approveB.setEnabled(true);
        approveB.setText("Approve");
        setEnableFields(true);
        approveB.setIcon(ResourceLoader.getImageIcon("images/button/checked.gif","Approve"));
      }else {
        approveB.setEnabled(false);
        setEnableFields(false);
      }
    }else if (myStatus.equalsIgnoreCase("approved")) {
      approveB.setEnabled(true);
      approveB.setText("Allow Edit");
      setEnableFields(false);
      approveB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif","Edit"));
    }else if (myStatus.equalsIgnoreCase("invoiced")) {
      approveB.setEnabled(true);
      approveB.setText("Ammend");
      setEnableFields(false);
    }else if (myStatus.equalsIgnoreCase("mixed")) {
      //String msg = "The invoice for this shipment was corrupted.\nPlease contract Ron Achord for assistance";
      GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
      g.setMsg((String)vendorInvoiceDataH.get("MIXED_STATUS"));
      g.setVisible(true);
      approveB.setEnabled(false);
      setEnableFields(false);
    }else if (myStatus.equalsIgnoreCase("locked")) {
      GenericDlg g = new GenericDlg(cmis.getMain(),"Action",true);
      g.setMsg((String)vendorInvoiceDataH.get("LOCKED_STATUS"));
      g.setVisible(true);
      approveB.setEnabled(false);
      setEnableFields(false);
    }

    vendorOrderT.setText((String)innerH.get("VENDOR_SHIPMENT_ID"));
    shipmentT.setText(shipmentId);

    amountT.setText(calculateShipmentTotal());

    //if there are only one shipment for this order
    disableNavigation();

    } //end of else
  }

  public void reLoadItAction() {
     CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
     try{
      //ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_VENDOR_INVOICE_INFO_TAB"); //String, String
      query.put("MANAGER_ID",cmis.getUserId());
      query.put("SHIPMENT_NUM",new Integer(shipNum));

      System.out.println("sendData:"+query);
      Hashtable result = cgi.getResultHash(query);
      ClientHelpObjs.setEnabledContainer(this,true);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      vendorInvoiceDataH = (Hashtable)result.get("VENDOR_INVOICE_DATA");
      vendorInvoiceDataH.put("TABLE_STYLE",(Hashtable)result.get("TABLE_STYLE"));
      shipmentIdV = (Vector)vendorInvoiceDataH.get("SHIPMENT_ID");
      shipmentStatusV = (Vector)vendorInvoiceDataH.get("SHIPMENT_STATUS");
      processH = (Hashtable)vendorInvoiceDataH.get("INVOICE_PROCESS");

      //getting the extended and total col so I could calculate the total amount for a given shipment
      EXTENDED_COL = ((Integer)result.get("EXTENDED_COL")).intValue();
      TOTAL_COL = ((Integer)result.get("TOTAL_COL")).intValue();

      ClientHelpObjs.setEnabledContainer(this,true);
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      ClientHelpObjs.setEnabledContainer(this,true);
      this.revalidate();
    }

    //currentPos = 0;
    buildVendorInvoiceData(currentPos);

    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }

  public void buildVendorInvoiceData(int pos) {
    String shipmentId = shipmentIdV.elementAt(pos).toString();

    String myStatus = shipmentStatusV.elementAt(pos).toString();    //3-12-01
    Hashtable pht = (Hashtable)processH.get(shipmentId);
    Vector processC = (Vector)pht.get("PROCESS");
    Vector timeV = (Vector)pht.get("TIME");

    Vector approveV = new Vector();
    processV = new Vector();

    String key = "";
    if (myStatus.equalsIgnoreCase("draft") || myStatus.equalsIgnoreCase("ammended")) {
      key = (String)processC.elementAt(0);
      for (int n = 0; n < processC.size(); n++) {
        approveV.addElement(timeV.elementAt(n));
        processV.addElement(processC.elementAt(n));
      }
    }else {
      key = (String)processC.elementAt(1);
      for (int n = 1; n < processC.size(); n++) {
        approveV.addElement(timeV.elementAt(n));
        processV.addElement(processC.elementAt(n));
      }
    }

    key = shipmentId + key;

    currentPos = pos;
    if (pos == shipmentIdV.size()-1) {
      firstB.setEnabled(true);
      previousB.setEnabled(true);
      nextB.setEnabled(false);
      lastB.setEnabled(false);
    } else if (pos == 0) {
      firstB.setEnabled(false);
      previousB.setEnabled(false);
      nextB.setEnabled(true);
      lastB.setEnabled(true);
    } else {
      firstB.setEnabled(true);
      previousB.setEnabled(true);
      nextB.setEnabled(true);
      lastB.setEnabled(true);
    }

    Hashtable innerH = (Hashtable)vendorInvoiceDataH.get(key);
    buildInvoiceTableModel(innerH);
    buildVendorInvoiceTable();

    buildExtraCostTableModel((Hashtable) innerH.get("EXTRA_COST_TABLE_DATA"));
    buildCostTable();

    Vector vendorInvoiceV = null;
    String vendorShipID = "";
    //this is for vendor invoice info NEED to modify builVendorInvoiceTable()
    if (updateDataH.containsKey(shipmentId)){
      Hashtable myH = (Hashtable)updateDataH.get(shipmentId);
      vendorInvoiceV = (Vector)myH.get("VENDOR_INVOICE");
      vendorShipID = (String)myH.get("VENDOR_SHIPMENT_ID");
    } else {
      vendorInvoiceV = (Vector)innerH.get("VENDOR_INVOICE");
      vendorShipID = (String)innerH.get("VENDOR_SHIPMENT_ID");
    }
    buildInvoiceTable(vendorInvoiceV);

    //trong 3-05-01
    dateC.removeAllItems();
    dateC = ClientHelpObjs.loadChoiceFromVector(dateC,approveV);
    Color keep = shipmentT.getForeground();
    dateC.setForeground(keep);
    dateC.setSelectedIndex(0);
    if (myStatus.equalsIgnoreCase("approved")) {
      approveB.setText("Allow Edit");
      setEnableFields(false);
      approveB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif","Edit"));
    }else if(myStatus.equalsIgnoreCase("draft")) {
      approveB.setText("Approve");
      setEnableFields(true);
      approveB.setIcon(ResourceLoader.getImageIcon("images/button/checked.gif","Approve"));
    }else if (myStatus.equalsIgnoreCase("invoiced")) {
      approveB.setText("Ammend");
      setEnableFields(false);
    }else if (myStatus.equalsIgnoreCase("ammended")) {       //ammend invoice
      approveB.setText("Approve");
      setEnableFields(true);
      approveB.setIcon(ResourceLoader.getImageIcon("images/button/checked.gif","Approve"));
    }else if (myStatus.equalsIgnoreCase("mixed")) {
      GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
      g.setMsg((String)vendorInvoiceDataH.get("MIXED_STATUS"));
      g.setVisible(true);
      approveB.setEnabled(false);
      setEnableFields(false);
    }else if (myStatus.equalsIgnoreCase("locked")) {
      GenericDlg g = new GenericDlg(cmis.getMain(),"Action",true);
      g.setMsg((String)vendorInvoiceDataH.get("LOCKED_STATUS"));
      g.setVisible(true);
      approveB.setEnabled(false);
      setEnableFields(false);
    }

    vendorOrderT.setText(vendorShipID);
    shipmentT.setText(shipmentId);

    amountT.setText(calculateShipmentTotal());

    //if there are only one shipment for this order
    disableNavigation();
  }

  void buildInvoiceTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");

    model = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      model.addRow((Object[]) data.elementAt(i));
    }
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
  }  //end of method

  void buildExtraCostTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");

    costModel = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      costModel.addRow((Object[]) data.elementAt(i));
    }
    costModel.setColumnPrefWidth(colWidths);
    costModel.setColumnType(colTypes);
  }  //end of method


  public void disableNavigation(){
    //if there are only one shipment for this order
    if (shipmentIdV.size() == 1) {
      firstB.setEnabled(false);
      previousB.setEnabled(false);
      nextB.setEnabled(false);
      lastB.setEnabled(false);
    }
  }

  public void saveUpdateData(int pos){
    String shipmentId = shipmentIdV.elementAt(pos).toString();
    Hashtable myH = new Hashtable();
    Vector v = new Vector();
    if(invoiceTable.isEditing()){
      invoiceTable.getCellEditor().stopCellEditing();
    }
    for (int i = 0; i < invoiceTable.getRowCount(); i++) {
      Hashtable h = new Hashtable();
      h.put("INVOICE",BothHelpObjs.makeBlankFromNull((String)invoiceTable.getModel().getValueAt(i,0)));
      h.put("DATE",BothHelpObjs.makeBlankFromNull((String)invoiceTable.getModel().getValueAt(i,1)));
      h.put("AMOUNT",BothHelpObjs.makeBlankFromNull((String)invoiceTable.getModel().getValueAt(i,2)));
      h.put("RECEIVED",BothHelpObjs.makeBlankFromNull((String)invoiceTable.getModel().getValueAt(i,3)));
      v.addElement(h);
    }
    myH.put("VENDOR_INVOICE",v);

    myH.put("VENDOR_SHIPMENT_ID",(String)vendorOrderT.getText());    //3-37-01

    updateDataH.put(shipmentId,myH);
  }


  public String calculateShipmentTotal() {
    double totalCost = 0.0;
    Double costT = null;
    //extra cost
    for (int j = 0; j < costTable.getRowCount(); j++) {
      costT = (Double)costTable.getModel().getValueAt(j,EXTENDED_COL);
      totalCost += costT.doubleValue();
    }

    //container cost
    for (int i = 0; i < rliTable.getRowCount(); i++) {
      costT = (Double)rliTable.getModel().getValueAt(i,TOTAL_COL);
      totalCost += costT.doubleValue();
    }

    DecimalFormat myFormatter = new DecimalFormat("$###,###.00");
    String outp = myFormatter.format(totalCost);

    return outp;
  }

  public void buildCostTable(){
    costJsp.getViewport().remove(costTable);
    sorter = new TableSorter(costModel);
    costTable = new CmisTable(sorter);
    sorter.setColTypeFlag(costModel.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(costTable);

    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable)vendorInvoiceDataH.get("TABLE_STYLE");
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
    costTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = costTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    costTable.getTableHeader().setResizingAllowed(true);
    costTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    //costTable.setCellSelectionEnabled(false);
    costTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    costTable.getTableHeader().setReorderingAllowed(true);

    costTable.setDefaultRenderer(String.class, colorTableRenderer);
    costTable.setDefaultRenderer(Integer.class, colorTableRenderer);
    costTable.setDefaultRenderer(Double.class, colorTableRenderer);
    costTable.setDefaultRenderer(Float.class, colorTableRenderer);

    // set column widths
    for(int i=0;i<costTable.getColumnCount();i++){
      int width = costModel.getColumnWidth(i);
      costTable.getColumn(costModel.getColumnName(i)).setPreferredWidth(width);
      costTable.getColumn(costModel.getColumnName(i)).setWidth(width);
      if (width==0){
        costTable.getColumn(costModel.getColumnName(i)).setMinWidth(width);
        costTable.getColumn(costModel.getColumnName(i)).setMaxWidth(width);
      }
    }
    costJsp.getViewport().setView(costTable);
  }

  public void buildInvoiceTable(Vector vendorInvoice){
    final String[] invoiceColHeads = {"Invoice","Date","Amount","Received"};
    final int[] invoiceColWidths = {60,70,47,70};
    final int[] invoiceColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                                   BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                                   BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                                   BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING};
    DbTableModel invoiceModel = new DbTableModel(invoiceColHeads);
    for (int j = 0; j < vendorInvoice.size(); j++) {
      Object[] oa = new Object[invoiceColHeads.length];
      Hashtable hh = (Hashtable)vendorInvoice.elementAt(j);
      oa[0] = hh.get("INVOICE");
      oa[1] = hh.get("DATE");
      oa[2] = hh.get("AMOUNT");
      oa[3] = hh.get("RECEIVED");
      invoiceModel.addRow(oa);
    }
    invoiceModel.setColumnPrefWidth(invoiceColWidths);
    invoiceModel.setColumnType(invoiceColTypes);

    invoiceJsp.getViewport().remove(invoiceTable);
    sorter = new TableSorter(invoiceModel);
    invoiceModel.setEditableFlag(15);       //allow edit to all fields
    invoiceTable = new CmisTable(sorter);
    sorter.setColTypeFlag(invoiceModel.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(invoiceTable);

    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable)vendorInvoiceDataH.get("TABLE_STYLE");
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
    invoiceTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = invoiceTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    invoiceTable.getTableHeader().setResizingAllowed(true);
    invoiceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    //invoiceTable.setCellSelectionEnabled(false);
    invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    invoiceTable.getTableHeader().setReorderingAllowed(true);

    invoiceTable.setDefaultRenderer(String.class, colorTableRenderer);
    invoiceTable.setDefaultRenderer(Integer.class, colorTableRenderer);
    invoiceTable.setDefaultRenderer(Double.class, colorTableRenderer);
    invoiceTable.setDefaultRenderer(Float.class, colorTableRenderer);

    // set column widths
    for(int i=0;i<invoiceTable.getColumnCount();i++){
      int width = invoiceModel.getColumnWidth(i);
      invoiceTable.getColumn(invoiceModel.getColumnName(i)).setPreferredWidth(width);
      invoiceTable.getColumn(invoiceModel.getColumnName(i)).setWidth(width);
      if (width==0){
        invoiceTable.getColumn(invoiceModel.getColumnName(i)).setMinWidth(width);
        invoiceTable.getColumn(invoiceModel.getColumnName(i)).setMaxWidth(width);
      }
    }
    invoiceJsp.getViewport().setView(invoiceTable);
  }

  public void buildVendorInvoiceTable(){
    transferJsp.getViewport().remove(rliTable);
    sorter = new TableSorter(model);
    rliTable = new CmisTable(sorter);
    sorter.setColTypeFlag(model.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(rliTable);

    rliTable.getTableHeader().setResizingAllowed(true);
    rliTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    //rliTable.setCellSelectionEnabled(false);
    rliTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    rliTable.getTableHeader().setReorderingAllowed(true);
    rliTable.addMouseListener(new WasteVendorInvoiceTab_rliTable_mouseAdapter(this));

    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable)vendorInvoiceDataH.get("TABLE_STYLE");
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
    rliTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = rliTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    // set column widths
    for(int i=0;i<rliTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      rliTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      rliTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        rliTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        rliTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }

    transferJsp.getViewport().setView(rliTable);
    detailP.repaint();
  }

  public void updateShipmentInvoiceData() {
     try{
      //ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","UPDATE_SHIPMENT_INVOICE_DATA"); //String, String
      query.put("SHIPMENT_INVOICE_UPDATE_INFO",updateDataH);
      query.put("SHIPMENT_ID",shipmentIdV);

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      String msg = (String)result.get("MESSAGE");
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Update Shipment Vendor Invoice",true);
      gd.setMsg(msg);
      gd.setVisible(true);

    }catch(Exception e){
      e.printStackTrace();
    }finally{
      //ClientHelpObjs.setEnabledContainer(this,true);
      this.revalidate();
    }
    disableNavigation();
  }


  void firstB_mousePressed(MouseEvent e) {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/first2.gif");
    firstB.setIcon(ss);
  }
  void firstB_mouseReleased(MouseEvent e) {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/first1.gif");
    firstB.setIcon(ss);
  }

  void firstB_mouseClicked(MouseEvent e) {
    saveUpdateData(currentPos);
    buildVendorInvoiceData(0);
  }

  void previousB_mousePressed(MouseEvent e) {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/prev2.gif");
    previousB.setIcon(ss);
  }
  void previousB_mouseReleased(MouseEvent e) {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/prev1.gif");
    previousB.setIcon(ss);
  }

  void previousB_mouseClicked(MouseEvent e) {
    saveUpdateData(currentPos);
    buildVendorInvoiceData(currentPos-1);
  }

  void nextB_mousePressed(MouseEvent e) {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/next2.gif");
    nextB.setIcon(ss);
  }
  void nextB_mouseReleased(MouseEvent e) {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/next1.gif");
    nextB.setIcon(ss);
  }

  void nextB_mouseClicked(MouseEvent e) {
    saveUpdateData(currentPos);
    buildVendorInvoiceData(currentPos+1);
  }

  void lastB_mousePressed(MouseEvent e) {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/last2.gif");
    lastB.setIcon(ss);
  }
  void lastB_mouseReleased(MouseEvent e) {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/last1.gif");
    lastB.setIcon(ss);
  }

  void lastB_mouseClicked(MouseEvent e) {
    saveUpdateData(currentPos);
    buildVendorInvoiceData(shipmentIdV.size()-1);
  }


  void rliTable_mouseClicked(MouseEvent e) {
  }

  void updateB_actionPerformed(ActionEvent e) {
    saveUpdateData(currentPos);
    updateShipmentInvoiceData();
  }

  void surchargeB_actionPerformed(ActionEvent e) {
    cmis.refreshShipmentCost = true;
    editSurcharge();
  }
  void editSurcharge(){
    String shipmentId = shipmentIdV.elementAt(currentPos).toString();
    String title = "Add/Edit surcharge for shipment ID: "+shipmentId;
    Integer shipId = new Integer(shipmentId);
    SurchargeEdit se = new SurchargeEdit(cmis.getMain(),title,shipId.intValue(),this,currentPos);

    se.setParent(cmis);
    se.loadIt();
    CenterComponent.centerCompOnScreen(se);
    se.setVisible(true);
  }

  void chargeB_actionPerformed(ActionEvent e) {
    cmis.refreshShipmentCost = true;
    editShipmentCharges();
  }
  void editShipmentCharges(){
    String shipmentId = shipmentIdV.elementAt(currentPos).toString();
    String title = "Add/Edit Shipment Charges for shipment ID: "+shipmentId;
    Integer shipId = new Integer(shipmentId);
    ShipmentCharges sc = new ShipmentCharges(cmis.getMain(),title,shipId.intValue(),this,currentPos);

    sc.setParent(cmis);
    sc.loadIt();
    CenterComponent.centerCompOnScreen(sc);
    sc.setVisible(true);
  }

  public void goApproveInvoice() {
     CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
     try{
      //ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","APPROVE_INVOICE"); //String, String
      query.put("MANAGER_ID",cmis.getUserId());
      query.put("SHIPMENT_NUM",new Integer(shipNum));
      query.put("SHIPMENT_ID",new Integer(shipmentT.getText()));

      Hashtable result = cgi.getResultHash(query);
      ClientHelpObjs.setEnabledContainer(this,true);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Boolean ok = (Boolean)result.get("SUCCEEDED");
      String cStatus = (String)result.get("STATUS");
      if (ok.booleanValue()) {
        /*updateShipmentStatus(currentPos,cStatus);   //int, String
        buildVendorInvoiceData(currentPos);
        reLoadItAction();*/
        vendorInvoiceDataH = (Hashtable)result.get("VENDOR_INVOICE_DATA");
        shipmentIdV = (Vector)vendorInvoiceDataH.get("SHIPMENT_ID");
        shipmentStatusV = (Vector)vendorInvoiceDataH.get("SHIPMENT_STATUS");
        processH = (Hashtable)vendorInvoiceDataH.get("INVOICE_PROCESS");

        //getting the extended and total col so I could calculate the total amount for a given shipment
        EXTENDED_COL = ((Integer)result.get("EXTENDED_COL")).intValue();
        TOTAL_COL = ((Integer)result.get("TOTAL_COL")).intValue();

        buildVendorInvoiceData(currentPos);

      }else {
        String msg = (String)result.get("MSG");
        GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
        g.setMsg(msg);
        g.setVisible(true);
      }
      //vendorInvoiceDataH = (Hashtable)result.get("VENDOR_INVOICE_DATA");
      //shipmentIdV = (Vector)vendorInvoiceDataH.get("SHIPMENT_ID");

      //getting the extended and total col so I could calculate the total amount for a given shipment
      //EXTENDED_COL = ((Integer)result.get("EXTENDED_COL")).intValue();
      //TOTAL_COL = ((Integer)result.get("TOTAL_COL")).intValue();

      //ClientHelpObjs.setEnabledContainer(this,true);
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      //ClientHelpObjs.setEnabledContainer(this,true);
      //this.revalidate();
    }

    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }

  void updateShipmentStatus(int pos, String cStatus) {
    shipmentStatusV.setElementAt(cStatus,pos);
  }
  /*
  void updateShipmentStatus(String shID, String cStatus) {
    Hashtable innerH = (Hashtable)vendorInvoiceDataH.get(shID);
    innerH.put("STATUS",cStatus);
  }
  */

  void goEditInvoice(String action) {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
     try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION",action); //String, String
      query.put("MANAGER_ID",cmis.getUserId());
      query.put("SHIPMENT_NUM",new Integer(shipNum));
      query.put("SHIPMENT_ID",new Integer(shipmentT.getText()));

      Hashtable result = cgi.getResultHash(query);
      ClientHelpObjs.setEnabledContainer(this,true);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Boolean ok = (Boolean)result.get("SUCCEEDED");
      String cStatus = (String)result.get("STATUS");
      if (ok.booleanValue()) {
        /*updateShipmentStatus(currentPos,cStatus);   //String, String
        buildVendorInvoiceData(currentPos);
        reLoadItAction();*/
        vendorInvoiceDataH = (Hashtable)result.get("VENDOR_INVOICE_DATA");
        shipmentIdV = (Vector)vendorInvoiceDataH.get("SHIPMENT_ID");
        shipmentStatusV = (Vector)vendorInvoiceDataH.get("SHIPMENT_STATUS");
        processH = (Hashtable)vendorInvoiceDataH.get("INVOICE_PROCESS");

        //getting the extended and total col so I could calculate the total amount for a given shipment
        EXTENDED_COL = ((Integer)result.get("EXTENDED_COL")).intValue();
        TOTAL_COL = ((Integer)result.get("TOTAL_COL")).intValue();

        buildVendorInvoiceData(currentPos);

      }else {
        String msg = (String)result.get("MSG");
        GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
        g.setMsg(msg);
        g.setVisible(true);
      }

    }catch(Exception e){
      e.printStackTrace();
    }finally{

    }

    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }

  void approveB_actionPerformed(ActionEvent e) {
    String myText = approveB.getText();
    if (myText.equalsIgnoreCase("Approve")) {
      goApproveInvoice();
    }else if (myText.startsWith("Allow")) {
      goEditInvoice("ALLOW_EDIT");
    }else {
      goEditInvoice("RETRACT_INVOICE");
    }
  }

  void setEnableFields(boolean b) {
    surchargeB.setEnabled(b);
    chargeB.setEnabled(b);
    updateB.setEnabled(b);
    rliTable.setEnabled(b);
    costJsp.getViewport().setEnabled(b);
    costTable.setEnabled(b);
    invoiceJsp.setEnabled(b);
    invoiceTable.setEnabled(b);
  }

  void vendorOrderT_actionPerformed(ActionEvent e) {

  }

} //end of class


class WasteVendorInvoiceLoadThread extends Thread{
  WasteVendorInvoice sitp;
  String action;
  public WasteVendorInvoiceLoadThread(WasteVendorInvoice sitp,String action){
    super();
    this.sitp = sitp;
    this.action = action;
  }
  public void run(){
    if(action.equalsIgnoreCase("load")){
      sitp.loadItAction("load");
    }else if(action.equalsIgnoreCase("reload")){
      sitp.loadItAction("reload");
    }
  }
}

class WasteVendorInvoiceTab_rliTable_mouseAdapter extends java.awt.event.MouseAdapter {
  WasteVendorInvoice adaptee;
  WasteVendorInvoiceTab_rliTable_mouseAdapter(WasteVendorInvoice adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.rliTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}


