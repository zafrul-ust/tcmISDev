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
public class POSRepackWin extends JDialog {
  //Table
  final String[] searchCols = {"Source","Bin","Quantity","Close"};
  final Long colTypeFlag = new Long("1111");
  final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  final int[] colWidths = {150,150,100,130};
  static final int SOURCE_COL = 0;
  static final int BIN_COL = 1;
  static final int QTY_COL = 2;
  static final int CLOSE_COL = 3;

  Frame parent;
  CmisApp grandParent;

  JScrollPane jPanel = new JScrollPane();
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JButton cancelB = new JButton();
  JButton okB = new JButton();
  JButton tapB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;
  String itemID = "";
  String inventoryGroup = "";
  boolean doneFlag = false;

  public POSRepackWin(Frame frame, String title, boolean modal, String itemID) {
    super(frame, title, modal);
    parent = frame;
    this.itemID = itemID;
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

  public void setInventoryGroup(String s) {
    inventoryGroup = s;
  }

  private void jbInit() throws Exception{
    panel1.setMaximumSize(new Dimension(550, 325));
    panel1.setPreferredSize(new Dimension(550, 325));
    panel1.setMinimumSize(new Dimension(550, 325));
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    cancelB.setText("Cancel");
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
    okB.setText("Process");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    okB.setMaximumSize(new Dimension(100, 25));
    okB.setMinimumSize(new Dimension(100, 25));
    okB.setPreferredSize(new Dimension(100, 25));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));

    tapB.setText("Tap");
    tapB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tapB_actionPerformed(e);
      }
    });
    tapB.setMaximumSize(new Dimension(100, 25));
    tapB.setMinimumSize(new Dimension(100, 25));
    tapB.setPreferredSize(new Dimension(100, 25));
    tapB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif"));

    panel1.setLayout(gridBagLayout1);
    panel2.setLayout(new BorderLayout());
    panel1.add(panel2, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 5, 5, 5), 0, 0));
    panel1.add(tapB, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 80, 9, 0), 0, 0));
    panel1.add(okB, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 195, 9, 0), 0, 0));
    panel1.add(cancelB, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 305, 9, 5), 0, 0));
    this.getContentPane().add(panel1);
    panel1.setBorder(ClientHelpObjs.groupBox(""));

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  } //end of method

  void loadIt() {
    POSRepackWinLoadThread iT = new POSRepackWinLoadThread(this);
    iT.start();
  }

  void loadItAction() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_POS_REPACK_DATA"); //String, String
      query.put("ITEM_ID",itemID);
      query.put("INVENTORY_GROUP",inventoryGroup);
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return;
      }
      if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("RETURN_MSG"));
        return;
      }
      buildTableNew((Vector)result.get("DATA"));
    }catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  void goCloseReceipt() {
    if (displayTable.isEditing()) {
      displayTable.getCellEditor().stopCellEditing();
    }
    try {
      int selectedRow = displayTable.getSelectedRow();
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","POS_CLOSE_RECEIPT"); //String, String
      query.put("ITEM_ID",itemID);
      query.put("INVENTORY_GROUP",inventoryGroup);
      query.put("RECEIPT_ID",displayTable.getModel().getValueAt(selectedRow,SOURCE_COL).toString());
      query.put("BIN",displayTable.getModel().getValueAt(selectedRow,BIN_COL).toString());
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return;
      }
      if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("RETURN_MSG"));
        return;
      }
      deleteReceipt(selectedRow);
    }catch (Exception e) {
      e.printStackTrace();
      doneFlag = false;
    }
  } //end of method

  Vector buildRepackData() {
    Vector v = new Vector(displayTable.getRowCount());
    for (int i = 0; i < displayTable.getRowCount(); i++) {
      Hashtable h = new Hashtable(3);
      String qty = displayTable.getModel().getValueAt(i,QTY_COL).toString();
      if (BothHelpObjs.isBlankString(qty)) {
        continue;
      }
      try {
        int tmpQty = Integer.parseInt(qty);
        if (tmpQty > 0) {
          h.put("RECEIPT_ID",displayTable.getModel().getValueAt(i,SOURCE_COL).toString());
          h.put("BIN",displayTable.getModel().getValueAt(i,BIN_COL).toString());
          h.put("QTY",qty);
          v.addElement(h);
        }
      }catch (Exception e) {
        v.removeAllElements();
      }
    }
    return v;
  } //end of method

  void goProcessRepack() {
    if (displayTable.isEditing()) {
      displayTable.getCellEditor().stopCellEditing();
    }
    Vector repackData = buildRepackData();
    if (repackData.size() < 1) {
      GenericDlg.showMessage("Please enter the qty for each Source and Bin that you want to repack.");
      return;
    }
    try {
      int selectedRow = displayTable.getSelectedRow();
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","PROCESS_POS_REPACK"); //String, String
      query.put("ITEM_ID",itemID);
      query.put("INVENTORY_GROUP",inventoryGroup);
      query.put("REPACK_DATA",repackData);
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return;
      }
      if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("RETURN_MSG"));
        return;
      }
      doneFlag = true;
    }catch (Exception e) {
      e.printStackTrace();
      doneFlag = false;
    }
  } //end of method

  void deleteReceipt(int row){
    if (displayTable.isEditing()) {
      displayTable.getCellEditor().stopCellEditing();
    }
    AttributiveCellTableModel model = (AttributiveCellTableModel)displayTable.getModel();
    model.removeRow(row);
  }

  void buildTableNew(Vector repackData) {
    //close this window no data to display
    if (repackData.size() < 1) {
      GenericDlg gd = new GenericDlg(grandParent.getMain(),"No Data Found",true);
      gd.setMsg("No Inventory for repack.");
      gd.setVisible(true);
      this.setVisible(false);
    }

    modelDetail = new AttributiveCellTableModel(searchCols,repackData.size());
    for (int k = repackData.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    modelDetail.setEditableFlag(BothHelpObjs.mypow(2,QTY_COL));            //allow user to edit actual qty column
    //if there are records
    if (repackData.size() > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      CellFont cellFont =(CellFont)modelDetail.getCellAttribute();
      for (int i = 0; i < repackData.size(); i++) {
        modelDetail.addRow((Object[]) repackData.elementAt(i));
        //set cell font
        Font font = new Font("test",Font.BOLD,10);
        cellFont.setFont(font, i, CLOSE_COL);
        //set cell color
        if (i % 2 == 0) {
          int[] row = new int[]{i};
          for (int ii = 0; ii < searchCols.length; ii++) {
            int[] col = new int[]{ii};
            cellColorAtt.setBackground(new Color(224,226,250),row,col);
          }
        }else {
          int[] row = new int[]{i};
          for (int ii = 0; ii < searchCols.length; ii++) {
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
      displayTable.addMouseListener(new POSRepack_table_mouseAdapter(this));
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
      /*
      for (i = 0; i < displayTable.getColumnCount(); i++) {
        if (i == CLOSE_COL) {
          m.getColumn(i).setCellRenderer(new ButtonCellRenderer());
        }else {
          m.getColumn(i).setCellRenderer(renderers[0]);
        }
      }*/
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

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    doneFlag = false;
    this.setVisible(false);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goProcessRepack();
    if (doneFlag) {
      this.setVisible(false);
    }
  }

  void tapB_actionPerformed(ActionEvent e) {
    POSTapWin ptw = new POSTapWin(grandParent.getMain(),"Tap Item: "+itemID+"     - Inventory Group: "+inventoryGroup,true,itemID);
    ptw.setGrandParent(grandParent);
    ptw.setInventoryGroup(inventoryGroup);
    ptw.loadIt();
    CenterComponent.centerCompOnScreen(ptw);
    ptw.setVisible(true);
    if (ptw.doneFlag) {
      Object[] oa = new Object[4];
      oa[0] = ptw.receiptID;
      oa[1] = ptw.bin;
      oa[2] = "";
      oa[3] = "Close";
      addRowToTable(displayTable,oa);
    }
  }

  void addRowToTable(MultiSpanCellTable myTable,Object[] data) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    int i = model.getRowCount();
    model.insertRow(i,data);
    if (i % 2 == 0) {
      int[] row = new int[]{i};
      for (int ii = 0; ii < searchCols.length; ii++) {
         int[] col = new int[]{ii};
         cellAtt.setBackground(new Color(224,226,250),row,col);
      }
    }else {
      int[] row = new int[]{i};
      for (int ii = 0; ii < searchCols.length; ii++) {
         int[] col = new int[]{ii};
         cellAtt.setBackground(new Color(255,255,255),row,col);
      }
    }
    displayTable.repaint();
  } //end of method

  void this_windowClosing(WindowEvent e) {
    setVisible(false);
  } //end of method

  void table_mouseClicked(MouseEvent e) {
    //Table clicked
    JFrame F;
    int rowSel = displayTable.getSelectedRow();
    Point p = e.getPoint();
    int colSel = displayTable.columnAtPoint(p);
    if (e.getClickCount() > 0 && colSel == CLOSE_COL) {
      ConfirmNewDlg cdlg = new ConfirmNewDlg(this.grandParent.getMain(),"Confirmation", true);
      cdlg.setMsg("        *** Warning ***\nClosing this source: "+displayTable.getModel().getValueAt(rowSel,SOURCE_COL).toString()+" will not make it\navaliable for dispensing/repackaging.");
      cdlg.setVisible(true);
      if (!cdlg.yesFlag) {
        return;
      }
      goCloseReceipt();
    }
  } //end of method

} //end of class

class POSRepack_table_mouseAdapter extends java.awt.event.MouseAdapter {
  POSRepackWin adaptee;

  POSRepack_table_mouseAdapter(POSRepackWin adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.table_mouseClicked(e);
  }
} //end of method

class POSRepackWinLoadThread extends Thread {
 POSRepackWin parent;
  public POSRepackWinLoadThread(POSRepackWin parent){
     super("POSRepackWinLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}



