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

public class EngineeringEvalDlg extends JDialog {

  JPanel panel1 = new JPanel();
  JButton rejectB = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();

  CmisApp cmis ;
  JScrollPane evalJSP = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  XYLayout xYLayout4 = new XYLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel middleP = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel bottomP = new JPanel();
  JButton closeB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  String itemID = null;
  StaticJLabel jLabel1 = new StaticJLabel();
  JTextArea descT = new JTextArea();
  StaticJLabel noteL = new StaticJLabel();
  MultiSpanCellTable engEvalTable = null;
  String[] engEvalCols = {"Facility","Work Area","Status","Date","Comment"};
  int[] engEvalColWidths = {110,130,100,100,150};
  int[] engEvalColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING};
  int FACILITY_COL = 0;
  int WORK_AREA_COL = 0;
  int STATUS_COL = 0;
  int DATE_COL = 0;
  int COMMENT_COL = 0;
  int FACILITY_ID_COL = 0;
  int APPLICATION_COL = 0;
  int REQUEST_ID_COL = 0;
  Vector facAllowReject;


  public EngineeringEvalDlg(JFrame fr, String title,String itemID) {
    super(fr, title, false);
    this.itemID = itemID;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(648, 514));
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
    EngineeringEvalDlgLoadThread x = new EngineeringEvalDlgLoadThread(this);
    x.start();
  }

  void jbInit() throws Exception {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/submit.gif");
    rejectB.setFont(new java.awt.Font("Dialog", 0, 10));
    rejectB.setMaximumSize(new Dimension(99, 24));
    rejectB.setMinimumSize(new Dimension(99, 24));
    rejectB.setPreferredSize(new Dimension(99, 24));
    rejectB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    closeB.setFont(new java.awt.Font("Dialog", 0, 10));
    closeB.setMaximumSize(new Dimension(97, 24));
    closeB.setMinimumSize(new Dimension(97, 24));
    closeB.setPreferredSize(new Dimension(97, 24));
    closeB.setIcon(ss);
    panel1.setLayout(borderLayout4);
    rejectB.setText("Reject");
    rejectB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rejectB_actionPerformed(e);
      }
    });

    panel1.setMaximumSize(new Dimension(520, 380));
    panel1.setMinimumSize(new Dimension(520, 380));
    panel1.setPreferredSize(new Dimension(520, 380));

    jPanel6.setLayout(gridBagLayout1);
    middleP.setLayout(borderLayout5);

    closeB.setText("Close");
    closeB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeB_actionPerformed(e);
      }
    });

    bottomP.setLayout(borderLayout1);
    jLabel1.setText("Desc:");
    descT.setLineWrap(true);
    descT.setWrapStyleWord(true);
    descT.setText("");
    descT.setEditable(false);
    descT.setFont(new java.awt.Font("Dialog", 0, 10));
    descT.setBackground(closeB.getBackground());
    noteL.setText("To activate: Highlight one or more \'Pending\' rows only - If highlight " +
    "area contains other statuses then \'Reject\' feature inactive.");
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(middleP, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 15, 60, 15), 0, 0));
    middleP.add(evalJSP, BorderLayout.CENTER);

    jPanel6.add(bottomP, new GridBagConstraints(0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(rejectB, BorderLayout.WEST);
    bottomP.add(closeB, BorderLayout.EAST);
    jPanel6.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));
    jPanel6.add(descT, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 45, 0, 15), 0, 0));
    jPanel6.add(noteL, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(5, 15, 5, 15), 0, 0));
    rejectB.setEnabled(false);
  }

  void loadItAction() {
    try {
      CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
      //ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_EVAL_DATA"); //String, String
      query.put("ITEM_ID",this.itemID);   //String, String
      query.put("USER_ID",cmis.getUserId());    //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      Boolean b = (Boolean)result.get("RETURN_CODE");
      if (!b.booleanValue()) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg((String)result.get("RETURN_MSG"));
        gd.setVisible(true);
        return;
      }
      buildTable((Hashtable)result.get("ENG_EVAL_DATA"));
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
    }finally {
      //ClientHelpObjs.setEnabledContainer(this,true);
      CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
      rejectB.setEnabled(false);
    }
  } //end of method

  void buildTable(Hashtable searchResult) {
    AttributiveCellTableModel model = new AttributiveCellTableModel(engEvalCols,0);
    if (searchResult == null) {
      model = new AttributiveCellTableModel(engEvalCols,0);
    }else {
      descT.setText((String)searchResult.get("ITEM_DESC"));
      engEvalCols = (String[])searchResult.get("HEADER_COLS");
      engEvalColWidths = (int[])searchResult.get("COL_WIDTHS");
      engEvalColTypes = (int[])searchResult.get("COL_TYPES");
      Vector dataV = (Vector)searchResult.get("EVAL_DATA");
      model = new AttributiveCellTableModel(engEvalCols,dataV.size());
      ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
      //remove row - perpare for coloring of alternate lines
      for (int k = dataV.size() -1; k >= 0; k--){
        model.removeRow(k);
      }
      //fill table
      for (int i = 0; i < dataV.size(); i++) {
        String[] oa = (String[])dataV.elementAt(i);
        model.addRow(oa);
        //color alternate row
        if (i % 2 == 0) {
          int[] rows = new int[]{i};
          for (int k = 0; k < model.getColumnCount(); k++) {
            int[] cols = new int[]{k};
            cellAtt.setBackground(new Color(224,226,250),rows,cols);
          }
        }
      }
    }
    model.setColumnPrefWidth(engEvalColWidths);
    model.setColumnType(engEvalColTypes);
    engEvalTable = new MultiSpanCellTable(model);
    engEvalTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    engEvalTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] engEvalRenderers = new AttributiveCellRendererLine[1];
    Hashtable catStyle = cmis.getMain().search.getCatTableStyle();
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    engEvalRenderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = engEvalTable.getColumnModel();
    int i = 0;
    for (i = 0; i < engEvalTable.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(engEvalRenderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    engEvalTable.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),engEvalRenderers[0].getBorder());
    Enumeration enum1 = engEvalTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    engEvalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<engEvalTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      engEvalTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      engEvalTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        engEvalTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        engEvalTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    engEvalTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        engEvalTable_mouseReleased();
      }
      public void mouseClicked(MouseEvent e) {
        engEvalTable_mouseClicked(e);
      }
    });
    engEvalTable.setToolTipText(" Right mouse click for detail. ");
    middleP.remove(evalJSP);
    evalJSP = new JScrollPane(engEvalTable);
    middleP.add(evalJSP, BorderLayout.CENTER);
    middleP.validate();
    //columns keys
    Hashtable keyH = (Hashtable) searchResult.get("KEY_COLS");
    FACILITY_COL = ((Integer)keyH.get("Facility")).intValue();
    WORK_AREA_COL = ((Integer)keyH.get("Work Area")).intValue();
    STATUS_COL = ((Integer)keyH.get("Status")).intValue();
    DATE_COL = ((Integer)keyH.get("Date")).intValue();
    COMMENT_COL = ((Integer)keyH.get("Comment")).intValue();
    FACILITY_ID_COL = ((Integer)keyH.get("Facility ID")).intValue();
    APPLICATION_COL = ((Integer)keyH.get("Application")).intValue();
    REQUEST_ID_COL = ((Integer)keyH.get("Request ID")).intValue();
    facAllowReject = (Vector)searchResult.get("FACILITY_ALLOW_REJECT");
  }

  void rejectRequest(String rejectReason) {
    //first get all selected requests
    int[] selectedRows = engEvalTable.getSelectedRows();
    Vector requestID = new Vector(selectedRows.length);
    for (int i = 0; i < selectedRows.length; i++) {
      requestID.addElement((String)engEvalTable.getModel().getValueAt(selectedRows[i],REQUEST_ID_COL));
    }

    try {
      CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","REJECT_EVAL"); //String, String
      query.put("ITEM_ID",this.itemID);   //String, String
      query.put("USER_ID",cmis.getUserId());    //String, Integer
      query.put("REJECT_REASON",rejectReason);  //String, String
      query.put("REJECT_REQUEST",requestID);    //String, Vector
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      Boolean b = (Boolean)result.get("RETURN_CODE");
      if (!b.booleanValue()) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg((String)result.get("RETURN_MSG"));
        gd.setVisible(true);
        return;
      }
      buildTable((Hashtable)result.get("ENG_EVAL_DATA"));
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
    }finally {
      CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
      rejectB.setEnabled(false);
    }
  }  //end of method

  void rejectB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    EngineeringEvalRejectDlg RjW = new EngineeringEvalRejectDlg(cmis.getMain(),"Reject Engineering Evaluation",true, "Rejection Reasons:");
    RjW.setParent(cmis);
    RjW.setVisible(true);
    if (RjW.rejectEval) {
      rejectRequest(RjW.rejArea.getText());
    }
  }

  void engEvalTable_mouseReleased() {
    int[] selectedRows = engEvalTable.getSelectedRows();
    for (int i = 0; i < selectedRows.length; i++) {
      //if status is 'Pending Evaluation' and I am allow to reject engineering for the selected facility(ies)
      if (engEvalTable.getModel().getValueAt(selectedRows[i],STATUS_COL).toString().equalsIgnoreCase("Pending Evaluation") &&
          facAllowReject.contains((String)engEvalTable.getModel().getValueAt(selectedRows[i],FACILITY_ID_COL))) {
        rejectB.setEnabled(true);
      }else {
        rejectB.setEnabled(false);
        break;
      }
    }
  }

  void engEvalTable_mouseClicked(MouseEvent e) {
    if (e.isMetaDown()) {
      Point p = e.getPoint();
      int selRow = engEvalTable.rowAtPoint(p);
      ItemCatComponentMetaPopUp cmpu = new ItemCatComponentMetaPopUp(cmis,this.itemID,(String)engEvalTable.getModel().getValueAt(selRow,FACILITY_ID_COL),(String)engEvalTable.getModel().getValueAt(selRow,APPLICATION_COL));
      cmpu.setFacilityDesc((String)engEvalTable.getModel().getValueAt(selRow,FACILITY_COL));
      cmpu.setWorkAreaDesc((String)engEvalTable.getModel().getValueAt(selRow,WORK_AREA_COL));
      middleP.add(cmpu);
      cmpu.show(engEvalTable,e.getX(),e.getY());
      //return;
    }
  }

  void closeB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }
}

class EngineeringEvalDlgLoadThread extends Thread {
  EngineeringEvalDlg parent;
  public EngineeringEvalDlgLoadThread(EngineeringEvalDlg parent){
     super("EngineeringEvalDlgLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

