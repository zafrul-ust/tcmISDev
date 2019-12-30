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
public class POSTapWin extends JDialog {
  //Table
  final String[] searchCols = {"Source","Bin"};
  final Long colTypeFlag = new Long("11");
  final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  final int[] colWidths = {168,167};
  static final int SOURCE_COL = 0;
  static final int BIN_COL = 1;

  Frame parent;
  CmisApp grandParent;

  JScrollPane jPanel = new JScrollPane();
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JButton cancelB = new JButton();
  JButton tapB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;
  String itemID = "";
  StaticJLabel sourceL = new StaticJLabel();
  DataJLabel sourceT = new DataJLabel();
  String inventoryGroup = "";
  StaticJLabel binL = new StaticJLabel();
  JComboBox binC = new JComboBox();
  JButton addBinB = new JButton();
  Vector binV = new Vector();
  boolean doneFlag = false;
  String receiptID = "";
  String bin = "";

  public POSTapWin(Frame frame, String title, boolean modal, String itemID) {
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
    panel1.setMaximumSize(new Dimension(350, 250));
    panel1.setPreferredSize(new Dimension(350, 250));
    panel1.setMinimumSize(new Dimension(350, 250));
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

    tapB.setText("Process");
    tapB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tapB_actionPerformed(e);
      }
    });
    tapB.setMaximumSize(new Dimension(100, 25));
    tapB.setMinimumSize(new Dimension(100, 25));
    tapB.setPreferredSize(new Dimension(100, 25));
    tapB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));

    panel1.setLayout(gridBagLayout1);
    panel2.setLayout(new BorderLayout());
    sourceL.setText("Source: ");
    binL.setText("Put Into: ");
    binC.setMaximumSize(new Dimension(143, 23));
    binC.setMinimumSize(new Dimension(143, 23));
    binC.setPreferredSize(new Dimension(143, 23));

    addBinB.setMaximumSize(new Dimension(23, 23));
    addBinB.setMinimumSize(new Dimension(23, 23));
    addBinB.setPreferredSize(new Dimension(23, 23));
    addBinB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif"));
    addBinB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addBinB_actionPerformed(e);
      }
    });

    panel1.add(sourceL, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 30, 5, 5), 0, 0));
    panel1.add(sourceT, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 75, 5, 5), 0, 0));
    panel1.add(binL, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 5, 5, 180), 0, 0));
    panel1.add(binC, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 5, 5, 35), 0, 0));
    panel1.add(addBinB, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    panel1.add(panel2, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 5, 5, 5), 0, 0));
    panel1.add(tapB, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 30, 9, 0), 0, 0));
    panel1.add(cancelB, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 150, 9, 5), 0, 0));
    this.getContentPane().add(panel1);
    panel1.setBorder(ClientHelpObjs.groupBox(""));
    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  } //end of method

  void loadIt() {
    POSTapWinLoadThread iT = new POSTapWinLoadThread(this);
    iT.start();
  }

  void loadItAction() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_POS_TAP_DATA"); //String, String
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
      binC.removeAllItems();
      binV = BothHelpObjs.sort((Vector)result.get("BIN"));
      binC = ClientHelpObjs.loadChoiceFromVector(binC,binV);
      DataJLabel tmpT = new DataJLabel();
      tmpT.setText("TEST");
      binC.setForeground(tmpT.getForeground());
      binC.setFont(tmpT.getFont());
    }catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  void goTap() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","POS_TAP"); //String, String
      query.put("ITEM_ID",itemID);
      query.put("INVENTORY_GROUP",inventoryGroup);
      query.put("RECEIPT_ID",sourceT.getText());
      query.put("BIN",binC.getSelectedItem().toString());
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return;
      }
      if (!((Boolean)result.get("RETURN_CODE")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("RETURN_MSG"));
        return;
      }
      receiptID = (String)result.get("RECEIPT_ID");
      bin = (String)result.get("BIN");
      doneFlag = true;
    }catch (Exception e) {
      e.printStackTrace();
      doneFlag = false;
    }
  } //end of method


  void buildTableNew(Vector repackData) {
    //close this window no data to display
    if (repackData.size() < 1) {
      GenericDlg gd = new GenericDlg(grandParent.getMain(),"No Data Found",true);
      gd.setMsg("No inventory to Tap.");
      gd.setVisible(true);
      this.setVisible(false);
    }

    modelDetail = new AttributiveCellTableModel(searchCols,repackData.size());
    for (int k = repackData.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    //if there are records +BothHelpObjs.mypow(2,ITEM_COL)
    if (repackData.size() > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      for (int i = 0; i < repackData.size(); i++) {
        modelDetail.addRow((Object[]) repackData.elementAt(i));
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
      displayTable.addMouseListener(new POSTap_table_mouseAdapter(this));
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

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    doneFlag = false;
    this.setVisible(false);
  }

  void addBinB_actionPerformed(ActionEvent e) {
    POSAddBinWin pabw = new POSAddBinWin(grandParent.getMain(),"Select a Bin for: "+inventoryGroup,true,inventoryGroup);
    pabw.setGrandParent(grandParent);
    pabw.loadIt();
    CenterComponent.centerCompOnScreen(pabw);
    pabw.setVisible(true);
    if (pabw.doneFlag) {
      String selectedBin = pabw.binC.getSelectedItem().toString();
      binC.removeAllItems();
      binV.addElement(selectedBin);
      binV = BothHelpObjs.sort(binV);
      binC = ClientHelpObjs.loadChoiceFromVector(binC,binV);
      binC.setSelectedItem(selectedBin);
      DataJLabel tmpT = new DataJLabel();
      tmpT.setText("TEST");
      binC.setForeground(tmpT.getForeground());
      binC.setFont(tmpT.getFont());
    }
  } //end of method

  void tapB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (sourceT.getText().length() < 1) {
      GenericDlg.showMessage("Please select a Source.");
      return;
    }
    if (binC.getSelectedItem().toString().startsWith("Select")) {
      GenericDlg.showMessage("Please select a Bin.");
      return;
    }
    goTap();
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    doneFlag = false;
    setVisible(false);
  } //end of method

  void table_mouseClicked(MouseEvent e) {
    sourceT.setText(displayTable.getValueAt(displayTable.getSelectedRow(),SOURCE_COL).toString());
  } //end of method

  void table_mouseReleased(MouseEvent e) {
    sourceT.setText(displayTable.getValueAt(displayTable.getSelectedRow(),SOURCE_COL).toString());
  } //end of method


} //end of class

class POSTap_table_mouseAdapter extends java.awt.event.MouseAdapter {
  POSTapWin adaptee;

  POSTap_table_mouseAdapter(POSTapWin adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.table_mouseClicked(e);
  }

  public void mouseReleased(MouseEvent e) {
    adaptee.table_mouseReleased(e);
  }
} //end of method

class POSTapWinLoadThread extends Thread {
 POSTapWin parent;
  public POSTapWinLoadThread(POSTapWin parent){
     super("POSTapWinLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}



