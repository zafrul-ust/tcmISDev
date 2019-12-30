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
public class ReceiptDocumentWin extends JDialog {
  //Table
  final String[] searchCols = {"Type","Name","Date","URL"};
  final Long colTypeFlag = new Long("1111");
  final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  final int[] colWidths = {120,120,90,0};
  static final int TYPE_COL = 0;
  static final int NAME_COL = 1;
  static final int DATE_COL = 2;
  static final int URL_COL = 3;

  Frame parent;
  CmisApp grandParent;

  JScrollPane jPanel = new JScrollPane();
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JButton cancelB = new JButton();
  JButton viewB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;
  String receiptId = "";

  public ReceiptDocumentWin(Frame frame, String title, boolean modal, String receiptId) {
    super(frame, title, modal);
    parent = frame;
    this.receiptId = receiptId;
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
    panel1.setMaximumSize(new Dimension(350, 250));
    panel1.setPreferredSize(new Dimension(350, 250));
    panel1.setMinimumSize(new Dimension(350, 250));
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    cancelB.setText("Close");
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    cancelB.setMaximumSize(new Dimension(130, 25));
    cancelB.setMinimumSize(new Dimension(130, 25));
    cancelB.setPreferredSize(new Dimension(130, 25));
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.setMnemonic('0');

    viewB.setText("View Document");
    viewB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewB_actionPerformed(e);
      }
    });
    viewB.setMaximumSize(new Dimension(140, 25));
    viewB.setMinimumSize(new Dimension(140, 25));
    viewB.setPreferredSize(new Dimension(140, 25));
    viewB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));

    panel1.setLayout(gridBagLayout1);
    panel2.setLayout(new BorderLayout());

    panel1.add(panel2, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8, 5, 5, 5), 0, 0));
    panel1.add(viewB, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 30, 9, 0), 0, 0));
    panel1.add(cancelB, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 180, 9, 5), 0, 0));
    this.getContentPane().add(panel1);
    panel1.setBorder(ClientHelpObjs.groupBox(""));
    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  } //end of method

  void loadIt() {
    ReceiptDocumentWinLoadThread iT = new ReceiptDocumentWinLoadThread(this);
    iT.start();
  }

  void loadItAction() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObj",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_RECEIPT_DOCUMENT_DATA"); //String, String
      query.put("RECEIPT_ID",receiptId);
      Hashtable result = cgi.getResultHash(query);
      if (result == null && !grandParent.getMain().stopped){
        GenericDlg.showAccessDenied(grandParent.getMain());
        return;
      }
      if (!((Boolean)result.get("SUCCEEDED")).booleanValue()) {
        GenericDlg.showMessage((String)result.get("MSG"));
        return;
      }
      //if receipt has only one document open it
      Vector receiptDocumentData = (Vector)result.get("DATA");
      //close this window no data to display
      if (receiptDocumentData.size() < 1) {
        GenericDlg gd = new GenericDlg(grandParent.getMain(),"No Data Found",true);
        gd.setMsg("No Data Found.");
        gd.setVisible(true);
        this.setVisible(false);
      }else if (receiptDocumentData.size() == 1) {
        Object[] oa = (Object[])receiptDocumentData.firstElement();
        String url = oa[URL_COL].toString();
        URLCall u = new URLCall(url, grandParent,true);
        this.setVisible(false);
      }else {
        buildTableNew(receiptDocumentData);
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  void buildTableNew(Vector receiptDocumentData) {
    modelDetail = new AttributiveCellTableModel(searchCols,receiptDocumentData.size());
    for (int k = receiptDocumentData.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    if (receiptDocumentData.size() > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      for (int i = 0; i < receiptDocumentData.size(); i++) {
        modelDetail.addRow((Object[]) receiptDocumentData.elementAt(i));
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
    this.setVisible(false);
  }

  void viewB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (displayTable.getSelectedRow() < 0) {
      GenericDlg gd = new GenericDlg(grandParent.getMain(),"Please Select",true);
      gd.setMsg("Please select a document that you want to view.");
      gd.setVisible(true);
    }else {
      String url = displayTable.getModel().getValueAt(displayTable.getSelectedRow(),this.URL_COL).toString();
      if (!BothHelpObjs.isBlankString(url)) {
        URLCall u = new URLCall(url, grandParent,true);
      }else {
        GenericDlg gd = new GenericDlg(grandParent.getMain(),"No Image Found",true);
        gd.setMsg("There is now image available for the selected document.\nPlease select another document.");
        gd.setVisible(true);
      }
    }
  }

  void this_windowClosing(WindowEvent e) {
    setVisible(false);
  } //end of method

} //end of class

class ReceiptDocumentWinLoadThread extends Thread {
 ReceiptDocumentWin parent;
  public ReceiptDocumentWinLoadThread(ReceiptDocumentWin parent){
     super("ReceiptDocumentWinLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}



