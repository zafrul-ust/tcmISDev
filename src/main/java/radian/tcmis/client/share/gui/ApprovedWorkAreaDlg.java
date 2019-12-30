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

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class ApprovedWorkAreaDlg extends JDialog {
  CmisApp cmis;
  String facID;
  String partNo;
  String partGroupNo;
  String catalogID;
  String catalogCompanyId = "";
  boolean allCatalog;

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
  StaticJLabel jLabel3 = new StaticJLabel();
  DataJLabel partNoT = new DataJLabel();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jsp = new JScrollPane();
  JButton printB = new JButton();

  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;
  String[] colHead = new String[]{"Facility","Work Areas","User Group","Restriction 1","Restriction 2","Members"};
  final Long colTypeFlag = new Long("111111");
  final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  protected final int FACILITY_NAME_COL = 0;
  protected final int WORK_AREAS_COL = 1;
  protected final int USER_GROUP_COL = 2;
  protected final int RESTRICTION_1_COL = 3;
  protected final int RESTRICTION_2_COL = 4;
  protected final int MEMBERS_COL = 5;
  Vector dataV;
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public ApprovedWorkAreaDlg(CmisApp cmis, String catalogID, String facID, String partNo, String partGroupNo, boolean allCatalog, String catalogCompanyId) {
    super(cmis.getMain(), "Approved Work Areas", true);
    this.cmis = cmis;
    this.facID = facID;
    this.partNo = partNo;
    this.partGroupNo = partGroupNo;
    this.catalogID = catalogID;
    this.allCatalog = allCatalog;
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
    if (allCatalog) {
      this.getContentPane().setSize(620, 400);
      xYLayout1.setHeight(420);
      xYLayout1.setWidth(620);
    }else {
      this.getContentPane().setSize(520, 400);
      xYLayout1.setHeight(420);
      xYLayout1.setWidth(520);
    }
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    catalogT.setText(catalogID);
    facIDT.setText(facID);
    partNoT.setText(partNo);
    partNoT.setToolTipText(partNoT.getText());
    topP.setLayout(xYLayout2);
    textP.setLayout(gridBagLayout1);
    catalogL.setText("Catalog ID:");
    jLabel1.setText("Facility ID:");
    jLabel3.setText("Part Number:");
    jPanel1.setLayout(borderLayout1);
    printB.setText("Print");
    printB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        printB_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    if (allCatalog) {
      panel1.add(topP, new XYConstraints(10, 10, 600, 345));
      topP.add(textP, new XYConstraints(0, 0, 600, 80));
      panel1.add(bottomP, new XYConstraints(10, 366, 600, 45));
    }else {
      panel1.add(topP, new XYConstraints(10, 10, 500, 345));
      topP.add(textP, new XYConstraints(0, 0, 500, 80));
      panel1.add(bottomP, new XYConstraints(10, 366, 500, 45));
    }

    //don't show facility in header if all catalog is picked
    if (allCatalog) {
      textP.add(catalogL,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      textP.add(catalogT,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      textP.add(jLabel3,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
              ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      textP.add(partNoT,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
              ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    }else {
      textP.add(catalogL,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      textP.add(catalogT,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      textP.add(jLabel1,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      textP.add(facIDT,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      textP.add(jLabel3,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      textP.add(partNoT,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    }
    if (allCatalog) {
      topP.add(jPanel1, new XYConstraints(10, 80, 580, 265));
    }else {
      topP.add(jPanel1, new XYConstraints(10, 80, 480, 265));
    }
    //bottom
    bottomP.add(okB, null);
    bottomP.add(printB, null);
  } //end of method

  void getData(){
    ApprovedWorkAreasDlgLoadThread c = new ApprovedWorkAreasDlgLoadThread(this);
    c.start();
  }

  void loadData(){
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),false);
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_APPROVED_WORK_AREAS");
      query.put("FACILITY_ID",facID); //String, String
      query.put("CATALOG_ID",catalogID); //String, String
      query.put("CATALOG_COMPANY_ID",catalogCompanyId);
 System.out.println("catalog company:"+catalogCompanyId+"-");
      query.put("PART_NO",partNo); //String, String
      query.put("PART_GROUP_NO",partGroupNo); //String, String
      query.put("ALL_CATALOG",new Boolean(allCatalog));  //String, Boolean
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Boolean success = (Boolean)result.get("SUCCEEDED");
      if (success.booleanValue()) {
        dataV = (Vector) result.get("APPROVED_WORK_AREAS_DATA");
        buildTable();
        this.repaint();
      }else {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg((String)result.get("MSG"));
        gd.setVisible(true);
        this.setVisible(false);
      }
     ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    }catch(Exception e){e.printStackTrace();}
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),true);
  }

  void buildTable() {
    //close this window no data to display
    if (dataV.size() < 1) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"No Data Found",true);
      gd.setMsg("No data found.");
      gd.setVisible(true);
      this.setVisible(false);
    }

    //don't show facility if all catalog is picked
    int[] colWidths = new int[colHead.length];
    if (allCatalog) {
      colWidths[FACILITY_NAME_COL] = 100;
      colWidths[WORK_AREAS_COL] = 235;
      colWidths[USER_GROUP_COL] = 80;
      colWidths[RESTRICTION_1_COL] = 75;
      colWidths[RESTRICTION_2_COL] = 75;
      colWidths[MEMBERS_COL] = 150;
    }else {
      colWidths[FACILITY_NAME_COL] = 0;
      colWidths[WORK_AREAS_COL] = 235;
      colWidths[USER_GROUP_COL] = 80;
      colWidths[RESTRICTION_1_COL] = 75;
      colWidths[RESTRICTION_2_COL] = 75;
      colWidths[MEMBERS_COL] = 150;
    }

    modelDetail = new AttributiveCellTableModel(colHead,dataV.size());
    for (int k = dataV.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    //if there are records
    if (dataV.size() > 0) {
      CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
      ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
      for (int i = 0; i < dataV.size(); i++) {
        Object[] oa = new Object[colHead.length];
        Hashtable h = (Hashtable)dataV.elementAt(i);
        //set facility if not all catalog
        if (!allCatalog && i == 0) {
          facIDT.setText((String)h.get("FACILITY_NAME"));
        }
        oa[FACILITY_NAME_COL] = (String)h.get("FACILITY_NAME");
        oa[WORK_AREAS_COL] = (String)h.get("WORK_AREAS");
        oa[USER_GROUP_COL] = (String)h.get("USER_GROUP");
        oa[RESTRICTION_1_COL] = (String)h.get("RESTRICTION_1");
        oa[RESTRICTION_2_COL] = (String)h.get("RESTRICTION_2");
        oa[MEMBERS_COL] = (String)h.get("MEMBERS");
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
      Enumeration enuma = displayTable.getColumnModel().getColumns();
      while (enuma.hasMoreElements()) {
        ((TableColumn)enuma.nextElement()).setHeaderRenderer(renderer);
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

  void printB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      try{
        if (dataV.size() < 1) {
          GenericDlg.showMessage("No Data to print.");
          return;
        }
        TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObj",cmis);
        Hashtable query = new Hashtable();
        query.put("ACTION","PRINT_APPROVED_WORK_AREAS");
        query.put("CATALOG_ID",catalogID);
        query.put("CATALOG_COMPANY_ID",catalogCompanyId);
        query.put("FACILITY_NAME",facIDT.getText());
        query.put("PART_NO",partNo);
        query.put("APPROVED_WORKAREA_DATA",dataV);
        query.put("ALL_CATALOG",new Boolean(allCatalog));
        Hashtable result = cgi.getResultHash(query);
        if(result == null){
          GenericDlg.showAccessDenied(cmis.getMain());
          return;
        }
        Boolean b = (Boolean)result.get("SUCCEEDED");
        if (b.booleanValue()) {
          try {
            // From the stand alone application
            String url = (String)result.get("URL");
            new URLCall(url,cmis);
            return;
          } catch (Exception eee) {
            eee.printStackTrace();
            return;
          }
        }else {
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
          gd.setMsg(result.get("MSG").toString());
          gd.setVisible(true);
          return;
        }
      }catch (Exception ee) {
        ee.printStackTrace();
        return;
      }
  } //end of method
} //end of class

class ApprovedWorkAreasDlgLoadThread extends Thread {
  ApprovedWorkAreaDlg parent;
  public ApprovedWorkAreasDlgLoadThread(ApprovedWorkAreaDlg parent){
     super("ApprovedWorkAreasDlgLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadData();
  }
}


