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

public class WasteDraftInvoice extends JPanel {
  CmisApp cmis;
  int shipNum;

  DbTableModel model;
  DbTableModel ctm;
  TableSorter sorter;
  CmisTable rliTable;
  CmisTable costTable;
  DbTableModel costModel;
  JTable formReturnedT = new JTable();
  Vector manifestV = new Vector();
  Vector copyV = new Vector();
  Vector certV = new Vector();
  Hashtable costDataH = null;
  Vector shipmentIdV = null;
  int currentPos;

  int EXTENDED_COL = 0;
  int TOTAL_COL = 0;
  //Hashtable updateDataH = new Hashtable();

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
  JPanel topP = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel headerP = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  StaticJLabel vendorOrderL = new StaticJLabel();
  DataJLabel vendorOrderT = new DataJLabel();
  StaticJLabel amountL = new StaticJLabel();
  DataJLabel amountT = new DataJLabel();
  StaticJLabel shipmentLL = new StaticJLabel();
  DataJLabel shipmentT = new DataJLabel();
  JPanel bottomP = new JPanel();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JPanel costP = new JPanel();
  JScrollPane costJsp = new JScrollPane();
  GridBagLayout gridBagLayout6 = new GridBagLayout();


  public WasteDraftInvoice() {
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public WasteDraftInvoice(CmisApp cmis) {
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
    WasteDraftInvoiceLoadThread silt = new WasteDraftInvoiceLoadThread(this,"load");
    silt.start();
  }

  private void jbInit() throws Exception {
    //String wDir = new String(System.getProperty("user.dir"));
    //String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    //String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") + (new String("/button/"));
    this.setLayout(gridBagLayout1);
    detailP.setMaximumSize(new Dimension(150, 125));
    detailP.setMinimumSize(new Dimension(150, 125));
    detailP.setPreferredSize(new Dimension(150, 125));

    //initializing transfer info table.
    rliTable = new CmisTable();
    transferJsp.getViewport().setView(rliTable);

    costTable = new CmisTable();
    costJsp.getViewport().setView(costTable);

    ImageIcon ss = null;
    //this.setBorder(ClientHelpObjs.groupBox("Draft Invoice"));
    topP.setBorder(ClientHelpObjs.groupBox("Navigation"));
    topP.setMinimumSize(new Dimension(308, 55));
    topP.setPreferredSize(new Dimension(308, 55));
    bottomP.setBorder(ClientHelpObjs.groupBox("Cost Info."));
    //topP.setMinimumSize(new Dimension(488, 50));
    //topP.setPreferredSize(new Dimension(488, 50));
    //headerP.setBorder(ClientHelpObjs.groupBox("Header Info"));
    //headerP.setMinimumSize(new Dimension(278, 80));
    //headerP.setPreferredSize(new Dimension(278, 80));
    //detailP.setBorder(ClientHelpObjs.groupBox("Detail Info"));
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


    topP.setLayout(gridBagLayout3);
    headerP.setLayout(gridBagLayout4);
    vendorOrderL.setText("Vendor Order:");
    amountL.setText("Total Amount:");
    shipmentLL.setText("Radian Shipment:");

    bottomP.setLayout(gridBagLayout5);
    bottomP.setMinimumSize(new Dimension(500, 231));
    bottomP.setPreferredSize(new Dimension(500, 231));
    headerP.setMinimumSize(new Dimension(266, 45));
    headerP.setPreferredSize(new Dimension(266, 45));
    costP.setLayout(gridBagLayout6);
    this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(nextB, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    topP.add(previousB, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    topP.add(firstB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    topP.add(lastB, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    topP.add(shipmentLL, new GridBagConstraints2(4, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 0, 0));
    topP.add(shipmentT, new GridBagConstraints2(5, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));


    detailP.setLayout(gridBagLayout2);
    this.add(bottomP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    bottomP.add(headerP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 0), 0, 0));
    headerP.add(vendorOrderL, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
    headerP.add(vendorOrderT, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 85, 5, 0), 0, 0));
    headerP.add(amountL, new GridBagConstraints2(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
    headerP.add(amountT, new GridBagConstraints2(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 85, 0, 0), 0, 0));
    headerP.add(costP, new GridBagConstraints2(1, 0, 1, 2, 1.0, 2.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    costP.add(costJsp, new GridBagConstraints2(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 1.0, 1.0
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
      query.put("ACTION","GET_DRAFT_INVOICE_INFO_TAB"); //String, String
      query.put("MANAGER_ID",cmis.getUserId());
      query.put("SHIPMENT_NUM",new Integer(shipNum));

      System.out.println("sendData:"+query);
      Hashtable result = cgi.getResultHash(query);
      ClientHelpObjs.setEnabledContainer(this,true);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      costDataH = (Hashtable)result.get("COST_DATA");
      costDataH.put("TABLE_STYLE",(Hashtable)result.get("TABLE_STYLE"));
      shipmentIdV = (Vector)costDataH.get("SHIPMENT_ID");
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
    buildDraftData(currentPos);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }

  public void buildDraftData(int pos) {
    String shipmentId = shipmentIdV.elementAt(pos).toString();
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

    Hashtable innerH = (Hashtable)costDataH.get(shipmentId);
    buildCostTableModel(innerH);
    buildDraftInvoiceTable();

    buildExtraCostTableModel((Hashtable) innerH.get("EXTRA_COST_TABLE_DATA"));
    buildCostTable();

    vendorOrderT.setText((String)innerH.get("VENDOR_SHIPMENT_ID"));
    shipmentT.setText(shipmentId);

    amountT.setText(calculateShipmentTotal());

    //if there are only one shipment for this order
    if (shipmentIdV.size() == 1) {
      firstB.setEnabled(false);
      previousB.setEnabled(false);
      nextB.setEnabled(false);
      lastB.setEnabled(false);
    }
  }

  void buildCostTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("COST_TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("COST_TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("COST_TABLE_TYPES");
    Vector data = (Vector) searchData.get("COST_TABLE_DATA");

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
    //costT = new Double(totalCost);

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
    Hashtable chargeTableStyle = (Hashtable)costDataH.get("TABLE_STYLE");
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
    costTable.setCellSelectionEnabled(false);
    costTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    costTable.getTableHeader().setReorderingAllowed(true);
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


  public void buildDraftInvoiceTable(){
    transferJsp.getViewport().remove(rliTable);
    sorter = new TableSorter(model);
    rliTable = new CmisTable(sorter);
    sorter.setColTypeFlag(model.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(rliTable);

    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable)costDataH.get("TABLE_STYLE");
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

    rliTable.getTableHeader().setResizingAllowed(true);
    rliTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    rliTable.setCellSelectionEnabled(false);
    rliTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    rliTable.getTableHeader().setReorderingAllowed(true);
    rliTable.addMouseListener(new WasteDraftInvoiceTab_rliTable_mouseAdapter(this));

    WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
    rliTable.setDefaultRenderer(String.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Integer.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Double.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Float.class, colorTableRenderer);

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

  void firstB_mousePressed(MouseEvent e) {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/first2.gif");
    firstB.setIcon(ss);
  }
  void firstB_mouseReleased(MouseEvent e) {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/first1.gif");
    firstB.setIcon(ss);
  }

  void firstB_mouseClicked(MouseEvent e) {
    buildDraftData(0);
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
    buildDraftData(currentPos-1);
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
    buildDraftData(currentPos+1);
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
    buildDraftData(shipmentIdV.size()-1);
  }


  void rliTable_mouseClicked(MouseEvent e) {
  }

  void updateB_actionPerformed(ActionEvent e) {
    //updateShipmentCostData();
  }

} //end of class


class WasteDraftInvoiceLoadThread extends Thread{
  WasteDraftInvoice sitp;
  String action;
  public WasteDraftInvoiceLoadThread(WasteDraftInvoice sitp,String action){
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

class WasteDraftInvoiceTab_rliTable_mouseAdapter extends java.awt.event.MouseAdapter {
  WasteDraftInvoice adaptee;
  WasteDraftInvoiceTab_rliTable_mouseAdapter(WasteDraftInvoice adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.rliTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}



