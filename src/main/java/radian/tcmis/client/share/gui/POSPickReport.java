package radian.tcmis.client.share.gui;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.net.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import com.borland.jbcl.layout.*;

public class POSPickReport extends JDialog {
  //Table
  final String[] searchCols = {" \nMR-Line","Work\nArea","Part\nNumber","Item\nID"," \nQty"};
  final Long colTypeFlag = new Long("11111");
  final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  final int[] colWidths = {60,190,120,50,40};
  static final int MR_LINE_COL = 0;
  static final int WORK_AREA_COL = 1;
  static final int PART_NUMBER_COL = 2;
  static final int ITEM_COL = 3;
  static final int ORDERED_QTY_COL = 4;


  Frame parent;
  CmisApp grandParent;

  JScrollPane jPanel = new JScrollPane();
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  ButtonGroup checkboxGroup1 = new ButtonGroup();
  JButton cancelB = new JButton();
  JButton okB = new JButton();

  StaticJLabel hubL = new StaticJLabel();
  DataJLabel hubT = new DataJLabel();
  StaticJLabel inventoryGroupL = new StaticJLabel();
  DataJLabel inventoryGroupT = new DataJLabel();
  StaticJLabel facilityL = new StaticJLabel();
  DataJLabel facilityT = new DataJLabel();
  StaticJLabel clerkL = new StaticJLabel();
  DataJLabel clerkT = new DataJLabel();
  StaticJLabel customerL = new StaticJLabel();
  DataJLabel customerT = new DataJLabel();
  StaticJLabel timeL = new StaticJLabel();
  DataJLabel timeT = new DataJLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  Vector pickData = null;
  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;
  String hub = "";
  String hubName = "";
  String inventoryGroup = "";
  String facilityID = "";

  public POSPickReport(Frame frame, String title, boolean modal, Vector data) {
    super(frame, "Customer Receipt", modal);
    parent = frame;
    pickData = data;
    try {
      jbInit();
      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setGrandParent(CmisApp m){
    grandParent = m;
  }

  public void setHub(String s){
    this.hub = s;
  }

  public void setHubName(String s){
    this.hubName = s;
  }

  public void setInventoryGroup(String s){
    this.inventoryGroup = s;
  }

  public void setFacilityID(String s){
    this.facilityID = s;
  }


  private void jbInit() throws Exception{
    panel1.setMaximumSize(new Dimension(485, 300));
    panel1.setPreferredSize(new Dimension(485, 300));
    panel1.setMinimumSize(new Dimension(485, 300));
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    cancelB.setText("Close Window");
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    cancelB.setMaximumSize(new Dimension(143, 25));
    cancelB.setMinimumSize(new Dimension(143, 25));
    cancelB.setPreferredSize(new Dimension(143, 25));
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.setMnemonic('0');
    okB.setText("Print");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    okB.setMaximumSize(new Dimension(100, 25));
    okB.setMinimumSize(new Dimension(100, 25));
    okB.setPreferredSize(new Dimension(100, 25));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    clerkL.setText("Clerk: ");
    clerkT.setText("");
    panel1.setLayout(gridBagLayout1);
    panel2.setLayout(new BorderLayout());
    customerL.setText("Customer: ");
    customerT.setText("");
    timeL.setText("Date: ");
    timeT.setText("");
    hubL.setText("Hub: ");
    hubT.setText("");
    inventoryGroupL.setText("POS: ");
    inventoryGroupT.setText("");
    facilityL.setText("Facility: ");
    facilityT.setText("");

    panel1.add(hubL, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    panel1.add(hubT, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 2, 5, 100), 0, 0));
    panel1.add(inventoryGroupL, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(inventoryGroupT, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 2, 5, 100), 0, 0));
    panel1.add(clerkL, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(clerkT, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 2, 5, 100), 0, 0));

    panel1.add(customerL, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 2, 5, 5), 0, 0));
    panel1.add(customerT, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 2, 5, 5), 0, 0));
    panel1.add(facilityL, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 2, 5, 5), 0, 0));
    panel1.add(facilityT, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 2, 5, 5), 0, 0));
    panel1.add(timeL, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 2, 5, 5), 0, 0));
    panel1.add(timeT, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 2, 5, 5), 0, 0));

    panel1.add(panel2, new GridBagConstraints(0, 3, 4, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 5, 5, 5), 0, 0));
    panel1.add(okB, new GridBagConstraints(0, 4, 4, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 110, 9, 0), 0, 0));
    panel1.add(cancelB, new GridBagConstraints(0, 4, 4, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 230, 9, 5), 0, 0));
    //build table
    //buildTableNew();
    this.getContentPane().add(panel1);
    panel1.setBorder(ClientHelpObjs.groupBox(""));
    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    this.pack();
    CenterComponent.centerCompOnScreen(this);
  }

  public void loadIt() {
    buildTableNew();
  }

  void buildTableNew() {
    //close this window no data to display
    if (pickData.size() < 1) {
      GenericDlg gd = new GenericDlg(grandParent.getMain(),"No Data Found",true);
      gd.setMsg("No data found.");
      gd.setVisible(true);
      this.setVisible(false);
    }

    modelDetail = new AttributiveCellTableModel(searchCols,pickData.size());
    for (int k = pickData.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    //if there are records
    if (pickData.size() > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      for (int i = 0; i < pickData.size(); i++) {
        Object[] oa = new Object[searchCols.length];
        Hashtable h = (Hashtable)pickData.elementAt(i);
        //first set clerk and customer name
        if (clerkT.getText().length() < 1) {
          clerkT.setText((String)h.get("CLERK_NAME"));
          customerT.setText((String)h.get("REQUESTOR_NAME"));
          timeT.setText((String)h.get("RECEIPT_DATE"));
          hubT.setText(hubName);
          inventoryGroupT.setText(inventoryGroup);
          facilityT.setText(facilityID);
        }
        //now build table
        oa[this.MR_LINE_COL] = (String)h.get("MR_LINE");
        oa[this.WORK_AREA_COL] = (String)h.get("WORK_AREA");
        oa[this.PART_NUMBER_COL] = (String)h.get("CAT_PART_NO");
        oa[this.ITEM_COL] = (String)h.get("ITEM_ID");
        oa[this.ORDERED_QTY_COL] = (String)h.get("QUANTITY");
        modelDetail.addRow(oa);
        if (i % 2 == 0) {
          int[] row = new int[]{i};
          for (int ii = 0; ii < modelDetail.getColumnCount(); ii++) {
            int[] col = new int[]{ii};
            cellColorAtt.setBackground(new Color(224,250,226),row,col);
          }
        }else {
          int[] row = new int[]{i};
          for (int ii = 0; ii < modelDetail.getColumnCount(); ii++) {
            int[] col = new int[]{ii};
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
      } //end of for

      modelDetail.setColumnPrefWidth(colWidths);
      modelDetail.setColumnType(colTypes);
      displayTable = new MultiSpanCellTable(modelDetail);
      jPanel =  new JScrollPane(displayTable);
      panel2.add(jPanel,BorderLayout.CENTER);
      panel2.validate();
      displayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      displayTable.getTableHeader().setResizingAllowed(true);
      setTableStyle(modelDetail);
    } //end of if data
  } //end of Method

  protected void setTableStyle(AttributiveCellTableModel model) {
      //control by server
      //cell border
      displayTable.setIntercellSpacing(new Dimension(0,0));
      AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
      Hashtable hh = BothHelpObjs.getTableStyle();
      Color color = (Color)hh.get("COLOR");
      Integer t = (Integer)hh.get("INSET_TOP");
      Integer l = (Integer)hh.get("INSET_LEFT");
      Integer b = (Integer)hh.get("INSET_BOTTOM");
      Integer r = (Integer)hh.get("INSET_RIGHT");
      Integer a = (Integer)hh.get("INSET_ALIGN");
      renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());

      TableColumnModel m = displayTable.getColumnModel();
      int i = 0;
      for (i = 0; i < displayTable.getColumnCount(); i++) {
        m.getColumn(i).setCellRenderer(renderers[0]);
      }

      //font and foreground and background color for columns heading
      String fontName = (String)hh.get("FONT_NAME");
      Integer fontStyle = (Integer)hh.get("FONT_STYLE");
      Integer fontSize = (Integer)hh.get("FONT_SIZE");
      displayTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)hh.get("FOREGROUND"),(Color)hh.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = displayTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
      }

      // set column widths
      displayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
      for(i=0;i<displayTable.getColumnCount();i++){
        int width = model.getColumnWidth(i);
        displayTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
        displayTable.getColumn(model.getColumnName(i)).setWidth(width);
        if (width==0){
          displayTable.getColumn(model.getColumnName(i)).setMinWidth(width);
          displayTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
        }
      }
  }

  boolean printScreen() {
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("USER_ID",grandParent.getUserId().toString());
      query.put("ACTION","PRINT_POS_PICK_RECEIPT");
      query.put("POS_PICK_DATA",pickData);
      query.put("CLERK_NAME",clerkT.getText());
      query.put("CUSTOMER_NAME",customerT.getText());
      query.put("HUB",hub);
      query.put("HUB_NAME",hubName);
      query.put("INVENTORY_GROUP",inventoryGroup);
      query.put("FACILITY_ID",facilityID);
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return false;
      }
      Boolean b = (Boolean)result.get("SUCCEEDED");
      if (b.booleanValue()) {
        try {
          // From the stand alone application
          String url = (String)result.get("URL");
          new URLCall(url,grandParent);
          return true;
        } catch (Exception e) {
          e.printStackTrace();
          return false;
        }
      }else {
        GenericDlg gd = new GenericDlg(grandParent.getMain(),"Error",true);
        gd.setMsg(result.get("MSG").toString());
        gd.setVisible(true);
        return false;
      }
    }catch (Exception ee) {
      ee.printStackTrace();
      return false;
    }
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (printScreen()) {

    }
  }

  void this_windowClosing(WindowEvent e) {
    setVisible(false);
  }
} //end of class



