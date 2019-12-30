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
public class MRAllocationReport extends JDialog {
  //Table
  final String[] searchCols = {" \nMR-Line"," \nWork Area"," \nPart Number"," \nType","Delivery\nType"," \nNeeded","Ordered\nQty"," \nStatus"," \nREF","(Allocated)\nQty","(Projected)\nDelivery"," \nNotes","Receipt\nDocument"};
  final Long colTypeFlag = new Long("111111111111");
  final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  final int[] colWidths = {65,160,95,30,60,80,50,70,75,75,100,250,0};
  static final int MR_LINE_COL = 0;
  static final int WORK_AREA_COL = 1;
  static final int PART_NUMBER_COL = 2;
  static final int ITEM_TYPE_COL = 3;
  static final int DELIVERY_TYPE_COL = 4;
  static final int NEED_DATE_COL = 5;
  static final int ORDERED_QTY_COL = 6;
  static final int STATUS_COL = 7;
  static final int SOURCE_COL = 8;
  static final int ALLOCATED_QTY_COL = 9;
  static final int PROJECTED_DELIVERY_COL = 10;
  static final int NOTES_COL = 11;
  static final int RECEIPT_DOCUMENT_COL = 12;

  Frame parent;
  CmisApp grandParent;

  CmisTable searchTable = new CmisTable();
  TableOrganizer tOrg = new TableOrganizer();
  TableSorter  sorterSearch = new TableSorter();
  CmisTableModel cmisTModelSearch = new CmisTableModel();
  JScrollPane jPanel = new JScrollPane();

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  ButtonGroup checkboxGroup1 = new ButtonGroup();
  JButton cancelB = new JButton();
  JButton okB = new JButton();

  StaticJLabel facL = new StaticJLabel();
  DataJLabel facT = new DataJLabel();
  StaticJLabel requestorL = new StaticJLabel();
  DataJLabel requestorT = new DataJLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  Vector mrAllocationInfo = null;
  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;
  String requestorName = "";
  String facilityName = "";

  public MRAllocationReport(Frame frame, String title, boolean modal, Vector data, String rName, String fName) {
    super(frame, "MR Allocation Report", modal);
    parent = frame;
    mrAllocationInfo = data;
    requestorName = rName;
    facilityName = fName;
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

  private void jbInit() throws Exception{
    panel1.setMaximumSize(new Dimension(700, 400));
    panel1.setPreferredSize(new Dimension(700, 400));
    panel1.setMinimumSize(new Dimension(700, 400));
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
    facL.setText("Facility: ");
    panel1.setLayout(gridBagLayout1);
    panel2.setLayout(new BorderLayout());
    requestorL.setText("Requestor: ");
    requestorT.setText(requestorName);
    facT.setText(facilityName);
    panel1.add(facL, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 10, 5, 5), 0, 0));
    panel1.add(facT, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    panel1.add(requestorL, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 10, 5, 5), 0, 0));
    panel1.add(requestorT, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    panel1.add(panel2, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 5, 5, 5), 0, 0));
    panel1.add(okB, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 100, 9, 0), 0, 0));
    panel1.add(cancelB, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 210, 9, 5), 0, 0));
    //build table
    buildTableNew();
    this.getContentPane().add(panel1);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    this.pack();
    CenterComponent.centerCompOnScreen(this);
  }

  void buildTableNew() {
    //close this window no data to display
    if (mrAllocationInfo == null || mrAllocationInfo.size() < 1) {
      GenericDlg gd = new GenericDlg(grandParent.getMain(),"No Data Found",true);
      gd.setMsg("No data found.");
      gd.setVisible(true);
      this.setVisible(false);
    }

    modelDetail = new AttributiveCellTableModel(searchCols,mrAllocationInfo.size());
    for (int k = mrAllocationInfo.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    //if there are records
    if (mrAllocationInfo.size() > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      CellFont cellFont =(CellFont)modelDetail.getCellAttribute();
      String lastMRLine = "";
      Vector combineRow = new Vector(29);
      //before 2-17-04 int combineCol = this.NEED_DATE_COL;
      int combineCol = this.STATUS_COL;
      int combineNeededNQtyCol = this.STATUS_COL;
      String lastNeededNQty = "";
      Vector combineNeededNQtyRow = new Vector(29);
      for (int i = 0; i < mrAllocationInfo.size(); i++) {
        Object[] oa = new Object[searchCols.length];
        Hashtable h = (Hashtable)mrAllocationInfo.elementAt(i);
        String currentMRLine = (String)h.get("MR_LINE");
        String currentNeededNQty = currentMRLine+(String)h.get("NEED_DATE")+(String)h.get("ORDERED_QTY");
        oa[this.MR_LINE_COL] = currentMRLine;
        oa[this.WORK_AREA_COL] = (String)h.get("WORK_AREA");
        oa[this.PART_NUMBER_COL] = (String)h.get("CAT_PART_NO");
        oa[this.ITEM_TYPE_COL] = (String)h.get("ITEM_TYPE");
        oa[this.DELIVERY_TYPE_COL] = (String)h.get("DELIVERY_TYPE");
        oa[this.NEED_DATE_COL] = (String)h.get("NEED_DATE");
        oa[this.ORDERED_QTY_COL] = (String)h.get("ORDERED_QTY");
        oa[this.STATUS_COL] = (String)h.get("STATUS");
        oa[this.SOURCE_COL] = (String)h.get("SOURCE");
        oa[this.ALLOCATED_QTY_COL] = (String)h.get("ALLOCATED_QTY");
        oa[this.PROJECTED_DELIVERY_COL] = (String)h.get("PROJECTED_DELIVERY");
        oa[this.NOTES_COL] = (String)h.get("NOTES");
        oa[this.RECEIPT_DOCUMENT_COL] = (String)h.get("RECEIPT_DOCUMENT");
        modelDetail.addRow(oa);

        //if we have receipt document then highlight REF column to get user attention
        if (!BothHelpObjs.isBlankString((String)h.get("RECEIPT_DOCUMENT"))) {
          int[] fontRows = {i};
          int[] fontCols = {SOURCE_COL};
          Font font = new Font((String)h.get("SOURCE"),Font.BOLD,12);
          cellFont.setFont(font, fontRows, fontCols);
        }

        //logic for combining general row
        if (lastMRLine.equalsIgnoreCase(currentMRLine)) {
          Vector v = (Vector)combineRow.lastElement();
          v.addElement(new Integer(i));
        }else {
          Vector v = new Vector(29);
          v.addElement(new Integer(i));
          combineRow.addElement(v);
        }
        //logic for combining needed and qty
        if (lastNeededNQty.equalsIgnoreCase(currentNeededNQty)) {
          Vector vv = (Vector)combineNeededNQtyRow.lastElement();
          vv.addElement(new Integer(i));
        }else {
          Vector vv = new Vector(29);
          vv.addElement(new Integer(i));
          combineNeededNQtyRow.addElement(vv);
        }
        lastNeededNQty = currentNeededNQty;
        lastMRLine = currentMRLine;
      } //end of for

      //combining general info
      combineCol = this.NEED_DATE_COL;
      for (int j = 0; j < combineRow.size(); j++) {
        Vector v = (Vector)combineRow.elementAt(j);
        int[] row = new int[v.size()];
        for (int k = 0; k < v.size(); k++) {
          Integer tmp = (Integer)v.elementAt(k);
          row[k] = tmp.intValue();
        }
        //now combine and color row(s)
        for (int i = 0; i < combineCol; i++) {
          int[] col = new int[]{i};
          cellAtt.combine(row,col);
          if (j % 2 == 0 ) {
            cellColorAtt.setBackground(new Color(224,226,250),row,col);
          }else {
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
      } //end of outer for
      //combining needed and qty
      combineNeededNQtyCol = this.STATUS_COL;
      for (int jj = 0; jj < combineNeededNQtyRow.size(); jj++) {
        Vector vv = (Vector)combineNeededNQtyRow.elementAt(jj);
        int[] row = new int[vv.size()];
        for (int kk = 0; kk < vv.size(); kk++) {
          Integer tmp = (Integer)vv.elementAt(kk);
          row[kk] = tmp.intValue();
        }
        //now combine needed and qty and color row(s)
        for (int ii = combineCol; ii < combineNeededNQtyCol; ii++) {
          int[] col = new int[]{ii};
          cellAtt.combine(row,col);
          if (jj % 2 == 0 ) {
            cellColorAtt.setBackground(new Color(224,250,226),row,col);
          }else {
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
        //color the rest of the columns
        for (int iii = combineNeededNQtyCol; iii < searchCols.length; iii++) {
          int[] col = new int[]{iii};
          if (jj % 2 == 0 ) {
            cellColorAtt.setBackground(new Color(224,250,226),row,col);
          }else {
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
      } //end of out for

      modelDetail.setColumnPrefWidth(colWidths);
      modelDetail.setColumnType(colTypes);
      displayTable = new MultiSpanCellTable(modelDetail);
      jPanel =  new JScrollPane(displayTable);
      panel2.add(jPanel,BorderLayout.CENTER);
      panel2.validate();
      displayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      displayTable.getTableHeader().setResizingAllowed(true);
      displayTable.addMouseListener(new Allocation_table_mouseAdapter(this));
      setTableStyle(modelDetail);
    } //end of if data
  } //end of Method

  void table_mouseClicked(MouseEvent e) {
    //Table clicked
    Point p = e.getPoint();
    int rowSel = displayTable.rowAtPoint(p);
    int colSel = displayTable.columnAtPoint(p);
    if (e.getClickCount() > 0 && colSel == SOURCE_COL) {
      String receiptDocument = displayTable.getModel().getValueAt(rowSel,RECEIPT_DOCUMENT_COL).toString();
      if (!BothHelpObjs.isBlankString(receiptDocument)) {
        String receiptId = displayTable.getModel().getValueAt(rowSel, RECEIPT_DOCUMENT_COL).toString();
        ReceiptDocumentWin ptw = new ReceiptDocumentWin(grandParent.getMain(), "Documents for receipt: " + receiptId, true, receiptId);
        ptw.setGrandParent(grandParent);
        ptw.loadIt();
        CenterComponent.centerCompOnScreen(ptw);
        ptw.setVisible(true);
      }
    }
  } //end of method

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
      TcmisHttpConnection cgi = new TcmisHttpConnection("TrackObj",grandParent);
      Hashtable query = new Hashtable();
      query.put("USER_ID",grandParent.getUserId().toString());
      query.put("ACTION","PRINT_ORDER_STATUS");
      query.put("MR_ALLOCATION_INFO",mrAllocationInfo);
      query.put("FACILITY",facT.getText());
      query.put("REQUESTOR",requestorT.getText());
      query.put("REQ_NUM","0");       //just a place holder since the server is expecting it
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
      this.setVisible(false);
    }
  }

  void this_windowClosing(WindowEvent e) {
    setVisible(false);
  }
} //end of class

class Allocation_table_mouseAdapter extends java.awt.event.MouseAdapter {
  MRAllocationReport adaptee;

  Allocation_table_mouseAdapter(MRAllocationReport adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.table_mouseClicked(e);
  }
} //end of method


