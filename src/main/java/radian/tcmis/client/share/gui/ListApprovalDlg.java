package radian.tcmis.client.share.gui;

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
import java.beans.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class ListApprovalDlg extends JDialog {

  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  String prNumber;
  String lineItem;
  String status;

  CmisApp cmis ;
  RequestsWin requestWin;
  JScrollPane partJSP = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  XYLayout xYLayout4 = new XYLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel bottomP = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  StaticJLabel commentL = new StaticJLabel();

  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;

  public ListApprovalDlg(RequestsWin rw, JFrame fr, String title, String prNum, String line, String stat) {
    super(fr, title, false);
    this.requestWin = rw;
    this.prNumber = prNum;
    this.lineItem = line;
    this.status = stat;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(700, 400));
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
    ListApprovalLoadThread x = new ListApprovalLoadThread(this);
    x.start();
  }
  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    panel1.setLayout(borderLayout4);
    jPanel1.setLayout(xYLayout1);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    panel1.setMaximumSize(new Dimension(700, 400));
    panel1.setMinimumSize(new Dimension(700, 400));
    panel1.setPreferredSize(new Dimension(700, 400));
    panel1.setBorder(ClientHelpObjs.groupBox(""));

    jPanel6.setLayout(gridBagLayout1);
    jPanel7.setLayout(borderLayout5);


    bottomP.setLayout(borderLayout1);
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(jPanel7, new GridBagConstraints2(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(25, 15, 50, 15), 0, 0));
    jPanel7.add(partJSP, BorderLayout.CENTER);

    jPanel6.add(bottomP, new GridBagConstraints2(1, 1, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(okB, BorderLayout.WEST);
    jPanel6.add(commentL, new GridBagConstraints2(0, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 15, 20, 0), 0, 0));
  }

  void buildTableNew(Hashtable listApprovalInfo) {
    Vector dataV = (Vector) listApprovalInfo.get("TABLE_DATA");
    //close this window no data to display
    if (dataV == null || dataV.size() < 1) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"No Data Found",true);
      gd.setMsg("No data found.");
      gd.setVisible(true);
      this.setVisible(false);
    }

    String[] colHeaders = (String[]) listApprovalInfo.get("TABLE_HEADERS");
    modelDetail = new AttributiveCellTableModel(colHeaders,dataV.size());
    for (int k = dataV.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    //if there are records
    if (dataV.size() > 0) {
      int listCol = ((Integer)listApprovalInfo.get("LIST_COL")).intValue();
      int combineCol = ((Integer)listApprovalInfo.get("ITEM_ID_COL")).intValue();
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      CellFont cellFont =(CellFont)modelDetail.getCellAttribute();
      String lastList = "";
      Vector combineRow = new Vector(29);
      for (int i = 0; i < dataV.size(); i++) {
        Object[] oa = (Object[])dataV.elementAt(i);
        String currentList = (String)oa[listCol];
        modelDetail.addRow(oa);

        //logic for combining general row
        if (lastList.equalsIgnoreCase(currentList)) {
          Vector v = (Vector)combineRow.lastElement();
          v.addElement(new Integer(i));
        }else {
          Vector v = new Vector(29);
          v.addElement(new Integer(i));
          combineRow.addElement(v);
        }
        lastList = currentList;
      } //end of for

      //combining general info
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

        //if this is a list of administrators then use white and blue
        if (combineCol == 0) {
          //color the rest of the columns
          for (int iii = combineCol; iii < colHeaders.length; iii++) {
            int[] col = new int[] {iii};
            if (j % 2 == 0) {
              cellColorAtt.setBackground(new Color(224,226,250),row,col);
            } else {
              cellColorAtt.setBackground(new Color(255, 255, 255), row, col);
            }
          }
        }else {
          //color the rest of the columns - white and green
          for (int iii = combineCol; iii < colHeaders.length; iii++) {
            int[] col = new int[] {iii};
            if (j % 2 == 0) {
              cellColorAtt.setBackground(new Color(224, 250, 226), row, col);
            } else {
              cellColorAtt.setBackground(new Color(255, 255, 255), row, col);
            }
          }
        }
      } //end of outer for

      modelDetail.setColumnPrefWidth((int[]) listApprovalInfo.get("TABLE_WIDTHS"));
      modelDetail.setColumnType((int[]) listApprovalInfo.get("TABLE_TYPES"));
      displayTable = new MultiSpanCellTable(modelDetail);
      partJSP.getViewport().setView(displayTable);
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
      displayTable.setCellSelectionEnabled(false);
      displayTable.setRowSelectionAllowed(false);
  }


  void loadItAction() {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.MATERIAL_REQUEST,cmis);
      Hashtable query = new Hashtable();
      query.put("FUNCTION","GET_LIST_APPROVAL_INFO"); //String, String
      query.put("USER_ID",cmis.getUserId());
      query.put("REQ_NUM",this.prNumber);
      query.put("LINE_ITEM",this.lineItem);
      query.put("STATUS",status);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      Boolean bool = (Boolean)result.get("RETURN_CODE");
      if (bool.booleanValue()) {
        Hashtable useApprovalH = (Hashtable)result.get("LIST_APPROVAL_INFO");
        commentL.setText((String)useApprovalH.get("COMMENT"));
        buildTableNew(useApprovalH);
      }else {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg((String)result.get("MSG"));
        gd.setVisible(true);
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
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        closeWindow();
      }
    });
  }

  void closeWindow() {
    requestWin.listApprovalDlgOpen = false;
    this.setVisible(false);
  }

  void okB_actionPerformed(ActionEvent e) {
    requestWin.listApprovalDlgOpen = false;
    this.setVisible(false);
  }
}

class ListApprovalLoadThread extends Thread {
  ListApprovalDlg parent;
  public ListApprovalLoadThread(ListApprovalDlg parent){
     super("ListApprovalLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

