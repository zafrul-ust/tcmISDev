//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.util.*;
import java.beans.*;
import java.net.URLEncoder;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class ApprovedProcessDlg extends JDialog {
  CmisApp cmis;
  String facID = "";
  String partNo = "";
  String partGroupNo = "";
  String catalogID = "";
  String catalogCompanyId = "";
  String workAreaID = "";
  String workAreaDesc = "";
  Hashtable processData;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel bottomP = new JPanel();
  JPanel topP = new JPanel();
  JButton okB = new JButton();
  XYLayout xYLayout2 = new XYLayout();
  JPanel textP = new JPanel();
  StaticJLabel catalogL = new StaticJLabel();
  DataJLabel catalogT = new DataJLabel();
  StaticJLabel jLabel1 = new StaticJLabel();
  DataJLabel facIDT = new DataJLabel();
  StaticJLabel waL = new StaticJLabel();
  DataJLabel waT = new DataJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  DataJLabel partNoT = new DataJLabel();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jsp = new JScrollPane();
  JButton printB = new JButton();

  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;
  String[] colHead = new String[]{"Process ID","Process"};
  final Long colTypeFlag = new Long("11");
  final int[] colWidths = {0,255};
  final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  protected final int PROCESS_ID_COL = 0;
  protected final int PROCESS_NAME_COL = 1;
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public ApprovedProcessDlg(CmisApp cmis, String catalogID, String facID, String partNo, String partGroupNo, String workAreaID, String workAreaDesc, Hashtable processData, String catalogCompanyId) {
    super(cmis.getMain(), "Approved Process", true);
    this.cmis = cmis;
    this.facID = facID;
    this.partNo = partNo;
    this.partGroupNo = partGroupNo;
    this.catalogID = catalogID;
    this.workAreaID = workAreaID;
    this.workAreaDesc = workAreaDesc;
    this.processData = processData;
    this.catalogCompanyId = catalogCompanyId;
    try  {
      jbInit();
      pack();
      loadData();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    okB.setMaximumSize(new Dimension(80, 35));
    okB.setMinimumSize(new Dimension(80, 35));
    okB.setPreferredSize(new Dimension(80, 35));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    printB.setMaximumSize(new Dimension(92, 35));
    printB.setMinimumSize(new Dimension(92, 35));
    printB.setPreferredSize(new Dimension(92, 35));
    printB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));

    this.setResizable(false);
    panel1.setLayout(xYLayout1);

    this.getContentPane().setSize(300, 400);
    xYLayout1.setHeight(420);
    xYLayout1.setWidth(300);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    catalogT.setText(catalogID);
    facIDT.setText(facID);
    waT.setText(workAreaDesc);
    partNoT.setText(partNo);
    topP.setLayout(xYLayout2);
    textP.setLayout(gridBagLayout1);
    catalogL.setText("Catalog ID:");
    jLabel1.setText("Facility ID:");
    waL.setText("Work Area:");
    jLabel3.setText("Part Number:");
    jPanel1.setLayout(borderLayout1);
    printB.setText("Print");
    printB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        printB_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(topP, new XYConstraints(10, 10, 280, 345));
    topP.add(textP, new XYConstraints(0, 0, 280, 80));
    panel1.add(bottomP, new XYConstraints(10, 366, 280, 45));
    textP.add(catalogL,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(catalogT,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(jLabel1,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    textP.add(facIDT,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    textP.add(waL,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    textP.add(waT,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    textP.add(jLabel3,  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    textP.add(partNoT,  new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    topP.add(jPanel1, new XYConstraints(10, 80, 260, 265));
    //bottom
    bottomP.add(okB, null);
    bottomP.add(printB, null);
  } //end of method

  void loadData(){
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),false);
    try{
      buildTable();
      this.repaint();
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    }catch(Exception e){e.printStackTrace();}
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),true);
  }

  void buildTable() {
    //close this window no data to display
    if (!processData.containsKey("PROCESS_ID")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"No Data Found",true);
      gd.setMsg("No data found.");
      gd.setVisible(true);
      this.setVisible(false);
    }

    Vector processIDV = (Vector)processData.get("PROCESS_ID");
    Vector processNameV = (Vector)processData.get("PROCESS_NAME");
    modelDetail = new AttributiveCellTableModel(colHead,processIDV.size());
    for (int k = processIDV.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    //if there are records
    if (processIDV.size() > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      for (int i = 0; i < processIDV.size(); i++) {
        Object[] oa = new Object[colHead.length];
        oa[PROCESS_ID_COL] = (String)processIDV.elementAt(i);
        oa[PROCESS_NAME_COL] = (String)processNameV.elementAt(i);
        modelDetail.addRow(oa);
        //color row when alternate
        for (int ii = 0; ii < colHead.length; ii++) {
          int[] col = new int[] {ii};
          int[] row = new int[] {i};
          if (i % 2 == 0) {
            cellColorAtt.setBackground(new Color(224, 226, 250), row, col);
          }else {
            cellColorAtt.setBackground(new Color(255, 255, 255), row, col);
          }
        }
      } //end of for
      modelDetail.setColumnPrefWidth(colWidths);
      modelDetail.setColumnType(colTypes);
      displayTable = new MultiSpanCellTable(modelDetail);
      jsp =  new JScrollPane(displayTable);
      jPanel1.add(jsp,BorderLayout.CENTER);
      jPanel1.validate();
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
  } //end of method

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

  void goShowProcessdetail(String process) {
    String procurl="";

    if ( process == null || process.length() == 0 ) {
      procurl+="process=";
    }else{
      procurl+="process=" +URLEncoder.encode(process);
    }

    if ( facID == null || facID.length() == 0 ) {
      procurl+="&facID=";
    }else {
      procurl+="&facID=" +URLEncoder.encode(facID);
    }

    if ( workAreaID == null || workAreaID.length() == 0 ) {
      procurl+="&selworkarea=";
    }else {
      procurl+="&selworkarea=" +URLEncoder.encode(workAreaID);
    }

    if ( workAreaDesc == null || workAreaDesc.length() == 0 ) {
      procurl+="&selworkareadesc=";
    }else {
      procurl+="&selworkareadesc=" +URLEncoder.encode(workAreaDesc);
    }


    if ( catalogID == null || catalogID.length() == 0 ) {
      procurl+="&catalogID=";
    }else{
      procurl+="&catalogID=" +URLEncoder.encode(catalogID);
    }

    if ( catalogCompanyId == null || catalogCompanyId.length() == 0 ) {
      procurl+="&catalogCompanyId=";
    }else{
      procurl+="&catalogCompanyId=" +URLEncoder.encode(catalogCompanyId);
    }

    if ( partNo == null || partNo.length() == 0 ){
      procurl+="&partNo=";
    }else{
      procurl+="&partNo=" +URLEncoder.encode(partNo);
    }

    if ( partGroupNo == null || partGroupNo.length() == 0 ) {
      procurl+="&partGroupNo=";
    }else{
      procurl+="&partGroupNo=" +URLEncoder.encode(partGroupNo);
    }

    URLGrab U=new URLGrab( 12,procurl,cmis );
  }

  void printB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      try{
        if (displayTable.getRowCount() <= 0) {
          GenericDlg.showMessage("No data to print.");
          return;
        }
        if (displayTable.getSelectedRow() < 0) {
          GenericDlg.showMessage("Please select a process that you want to print.");
          return;
        }
        //now show print process detail
        goShowProcessdetail((String)displayTable.getModel().getValueAt(displayTable.getSelectedRow(),PROCESS_ID_COL));
      }catch (Exception ee) {
        ee.printStackTrace();
        return;
      }
  } //end of method
} //end of class



