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

public class WorkAreaCommentsDlg extends JDialog {
  CmisApp cmis;
  String facID = "";
  String partNo = "";
  String partGroupNo = "";
  String catalogID = "";
  String catalogCompanyId = "";
  boolean allCatalog = false;
  String workAreaID = "";
  String workAreaDesc = "";
  String userName = "";

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel bottomP = new JPanel();
  JPanel topP = new JPanel();
  XYLayout xYLayout2 = new XYLayout();
  JPanel textP = new JPanel();
  StaticJLabel facL = new StaticJLabel();
  DataJLabel facIDT = new DataJLabel();
  StaticJLabel waL = new StaticJLabel();
  DataJLabel waIDT = new DataJLabel();
  StaticJLabel catalogL = new StaticJLabel();
  DataJLabel catalogT = new DataJLabel();
  StaticJLabel partL = new StaticJLabel();
  DataJLabel partNoT = new DataJLabel();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jsp = new JScrollPane();
  JButton printB = new JButton();
  JButton okB = new JButton();
  JButton processB = new JButton();
  JButton newB = new JButton();
  JButton editB = new JButton();

  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;
  String[] colHead = new String[]{"ID","Comments","By","Date","Delete","Status","User ID"};
  final Long colTypeFlag = new Long("1111111");
  final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  protected final int ID_COL = 0;
  protected final int COMMENTS_COL = 1;
  protected final int BY_COL = 2;
  protected final int DATE_COL = 3;
  protected final int DELETE_COL = 4;
  protected final int STATUS_COL = 5;
  protected final int USER_ID_COL = 6;
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public WorkAreaCommentsDlg(CmisApp cmis, String catalogID, String facID, String partNo, String partGroupNo, boolean allCatalog, String workAreaID, String workAreaDesc, String catalogCompanyId) {
    super(cmis.getMain(), "Work Area Comments", true);
    this.cmis = cmis;
    this.facID = facID;
    this.workAreaID = workAreaID;
    this.workAreaDesc = workAreaDesc;
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
    okB.setMaximumSize(new Dimension(92, 25));
    okB.setMinimumSize(new Dimension(92, 25));
    okB.setPreferredSize(new Dimension(92, 25));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/cancel2.gif"));
    printB.setMaximumSize(new Dimension(92, 25));
    printB.setMinimumSize(new Dimension(92, 25));
    printB.setPreferredSize(new Dimension(92, 25));
    printB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));
    processB.setMaximumSize(new Dimension(150, 25));
    processB.setMinimumSize(new Dimension(150, 25));
    processB.setPreferredSize(new Dimension(150, 25));
    processB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));
    newB.setMaximumSize(new Dimension(140, 25));
    newB.setMinimumSize(new Dimension(140, 25));
    newB.setPreferredSize(new Dimension(140, 25));
    newB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif"));
    editB.setMaximumSize(new Dimension(140, 25));
    editB.setMinimumSize(new Dimension(140, 25));
    editB.setPreferredSize(new Dimension(140, 25));
    editB.setIcon(ResourceLoader.getImageIcon("images/button/open.gif"));


    this.setResizable(false);
    panel1.setLayout(xYLayout1);
    this.getContentPane().setSize(700, 400);
    xYLayout1.setHeight(420);
    xYLayout1.setWidth(700);
    okB.setText("Cancel");
    okB.setFont(facL.getFont());
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
    facL.setText("Facility ID:");
    waL.setText("Work Area:");
    waIDT.setText(workAreaDesc);
    catalogL.setText("Catalog ID:");
    partL.setText("Part Number:");
    jPanel1.setLayout(borderLayout1);
    printB.setText("Print");
    printB.setFont(facL.getFont());
    printB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        printB_actionPerformed(e);
      }
    });
    processB.setText("Submit Comments");
    processB.setFont(facL.getFont());
    processB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        processB_actionPerformed(e);
      }
    });
    newB.setText("Add Comments");
    newB.setFont(facL.getFont());
    newB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newB_actionPerformed(e);
      }
    });
    editB.setText("Edit Comments");
    editB.setFont(facL.getFont());
    editB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editB_actionPerformed(e);
      }
    });

    getContentPane().add(panel1);
    panel1.add(topP, new XYConstraints(10, 10, 700, 345));
    topP.add(textP, new XYConstraints(0, 0, 700, 80));
    panel1.add(bottomP, new XYConstraints(10, 366, 700, 45));
    textP.add(facL,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(facIDT,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(waL,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(waIDT,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(catalogL,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(catalogT,  new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(partL,  new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(partNoT,  new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

    topP.add(jPanel1, new XYConstraints(10, 80, 660, 265));
    //bottom
    bottomP.add(processB,null);
    bottomP.add(newB,null);
    bottomP.add(editB,null);
    bottomP.add(printB, null);
    bottomP.add(okB, null);
  } //end of method

  void getData(){
    WorkAreaCommentsDlgLoadThread c = new WorkAreaCommentsDlgLoadThread(this);
    c.start();
  }

  void loadData(){
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),false);
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_WORK_AREA_COMMENTS");
      query.put("FACILITY_ID",facID); //String, String
      query.put("WORK_AREA",workAreaID);    //String, String
      query.put("CATALOG_ID",catalogID); //String, String
      query.put("CATALOG_COMPANY_ID",catalogCompanyId);
      query.put("CAT_PART_NO",partNo); //String, String
      query.put("PART_GROUP_NO",partGroupNo); //String, String
      query.put("USER_ID",cmis.getUserId());   //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Boolean success = (Boolean)result.get("SUCCEEDED");
      if (success.booleanValue()) {
        Hashtable h = (Hashtable)result.get("WORK_AREA_COMMENTS");
        //first set header info
        facIDT.setText((String)h.get("FACILITY_NAME"));
        waIDT.setText(workAreaDesc);
        catalogT.setText((String)h.get("CATALOG_DESC"));
        partNoT.setText(partNo);
        userName = (String)h.get("USER_NAME");
        //now build table
        Vector dataV = (Vector) h.get("WORK_AREAS_COMMENT_DATA");
        Integer tableSize = (Integer)h.get("DEFAULT_TABLE_SIZE");
        buildTable(tableSize.intValue()+dataV.size(),dataV);
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

  void buildTable(int tableSize, Vector dataV) {
    int[] colWidths = new int[colHead.length];
    colWidths[ID_COL] = 0;
    colWidths[COMMENTS_COL] = 400;
    colWidths[BY_COL] = 110;
    colWidths[DATE_COL] = 75;
    colWidths[DELETE_COL] = 75;
    colWidths[STATUS_COL] = 0;
    colWidths[USER_ID_COL] = 0;

    modelDetail = new AttributiveCellTableModel(colHead,tableSize);
    for (int k = tableSize - 1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
    ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
    for (int i = 0; i < dataV.size(); i++) {
      Object[] oa = new Object[colHead.length];
      Hashtable h = (Hashtable)dataV.elementAt(i);
      oa[ID_COL] = (String)h.get("COMMENT_ID");
      oa[COMMENTS_COL] = (String)h.get("COMMENTS");
      oa[BY_COL] = (String)h.get("BY");
      oa[DATE_COL] = (String)h.get("DATE");
      oa[DELETE_COL] = (Boolean)h.get("DELETE");
      oa[STATUS_COL] = (String)h.get("STATUS");
      oa[USER_ID_COL] = (Integer)h.get("USER_ID");
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
    } //end of adding row to table
    modelDetail.setColumnPrefWidth(colWidths);
    modelDetail.setColumnType(colTypes);
    displayTable = new MultiSpanCellTable(modelDetail);
    jsp =  new JScrollPane(displayTable);
    jPanel1.add(jsp,BorderLayout.CENTER);
    jPanel1.validate();
    displayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    displayTable.getTableHeader().setResizingAllowed(true);
    displayTable.addMouseListener(new WorkAreaComments_mouseAdapter(this));
    setTableStyle(modelDetail);
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
        if (i == DELETE_COL) {
          m.getColumn(i).setCellRenderer(new CheckBoxCellRenderer());
          m.getColumn(i).setCellEditor(new CheckBoxCellEditor(new JCheckBox()));
        }else {
          m.getColumn(i).setCellRenderer(renderers[0]);
        }
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

  void highLightTable(MultiSpanCellTable myTable,int selRow, boolean selected) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellColorAtt = (ColoredCell)model.getCellAttribute();
    int[] row = new int[]{selRow};
    if (selected) {
      //highlight select parts Yellow
      for (int i = 0; i < myTable.getColumnCount(); i++) {
         int[] col = new int[]{i};
         cellColorAtt.setBackground(new Color(220,220,100),row,col);
      }
    }else {
      for (int i = 0; i < myTable.getColumnCount(); i++) {
        int[] col = new int[]{i};
        if (selRow % 2 == 0 ) {
          cellColorAtt.setBackground(new Color(224,226,250),row,col);
        }else {
          cellColorAtt.setBackground(new Color(255,255,255),row,col);
        }
      }
    }
    displayTable.repaint();
  } //end of method

  void removeDeletedRowFromTable() {
    for (int i = 0; i < displayTable.getRowCount(); i++) {
      String status = (String)displayTable.getModel().getValueAt(i,STATUS_COL);
      if ("DELETE".equalsIgnoreCase(status)) {
        AttributiveCellTableModel model = (AttributiveCellTableModel)displayTable.getModel();
        model.removeRow(i);
      }
    }
  } //end of method

  void processB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    try{
      Vector dataV = getTableData();
      if (dataV.size() < 1) {
        GenericDlg.showMessage("No Data to process.");
        return;
      }
      TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","PROCESS_WORK_AREA_COMMENTS");
      query.put("USER_ID",cmis.getUserId());
      query.put("FACILITY_ID",facID);
      query.put("WORK_AREA",workAreaID);
      query.put("CATALOG_ID",catalogID);
      query.put("CATALOG_COMPANY_ID",catalogCompanyId);
      query.put("CAT_PART_NO",partNo);
      query.put("PART_GROUP_NO",partGroupNo);
      query.put("WORK_AREA_COMMENTS_DATA",dataV);
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Boolean b = (Boolean)result.get("SUCCEEDED");
      if (b.booleanValue()) {
        GenericDlg.showMessage(result.get("MSG").toString());
        //removeDeletedRowFromTable();
        this.setVisible(false);
        return;
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

  void newB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    EditDlg RjW = new EditDlg(cmis.getMain(),"New Comments",true, "Enter New Comments:");
    RjW.setVisible(true);
    //add new comment to table
    if(!BothHelpObjs.isBlankString(RjW.getText())){
      Object[] oa = new Object[displayTable.getColumnCount()];
      oa[ID_COL] = "";
      oa[COMMENTS_COL] = RjW.getText();
      oa[BY_COL] = userName;
      oa[DATE_COL] = getSystemDate();
      oa[DELETE_COL] = new Boolean(false);
      oa[STATUS_COL] = "NEW";
      oa[USER_ID_COL] = cmis.getUserId();
      addRowToTable(displayTable,oa);
    }
    RjW.dispose();
  } //end of method

  String getSystemDate() {
    Calendar cal = Calendar.getInstance();
    String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
    return cdate;
  }

  void addRowToTable(MultiSpanCellTable myTable,Object[] data) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    int row = model.getRowCount();
    model.insertRow(row,data);
    /*color alternate row
    if (row % 2 == 0) {
      int[] rows = new int[]{row};
      for (int j = 0; j < model.getColumnCount(); j++) {
        int[] cols = new int[]{j};
        cellAtt.setBackground(new Color(224,226,250),rows,cols);
      }
    }*/
    highLightTable(displayTable,row,false);
  } //end of method

  void editB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    int row = displayTable.getSelectedRow();
    //make sure that user selected a row
    if (row < 0) {
      GenericDlg.showMessage("Please select row that you want to edit comments.");
      return;
    }
    //user can only edit row that he/she own
    Integer tempUserID = (Integer)displayTable.getModel().getValueAt(row,USER_ID_COL);
    if (!cmis.getUserId().equals(tempUserID)) {
      GenericDlg.showMessage("You can't edit other user comments.\nPlease select comments that you entered\nor click 'Add Comments' for new comments.");
      return;
    }

    EditDlg RjW = new EditDlg(cmis.getMain(),"Edit Comments",true, "Edit Comments:");
    String tmp = (String)displayTable.getModel().getValueAt(row,COMMENTS_COL);
    if(!BothHelpObjs.isBlankString(tmp))RjW.setText(tmp);
    RjW.setVisible(true);
    if(!BothHelpObjs.isBlankString(RjW.getText())){
      displayTable.getModel().setValueAt(RjW.getText(),row,COMMENTS_COL);
      displayTable.getModel().setValueAt(getSystemDate(),row,DATE_COL);
      displayTable.getModel().setValueAt("UPDATE", row, STATUS_COL);
    }else{
      displayTable.getModel().setValueAt("",row,COMMENTS_COL);
      displayTable.getModel().setValueAt(getSystemDate(),row,DATE_COL);
      displayTable.getModel().setValueAt("DELETE", row, STATUS_COL);
    }
    RjW.dispose();
  } //end of method

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

  void displayTable_mouseClicked(MouseEvent e) {
    int col = ((JTable)e.getSource()).columnAtPoint(new Point(e.getX(),e.getY()));
    int row = ((JTable)e.getSource()).rowAtPoint(new Point(e.getX(),e.getY()));
    if (col == DELETE_COL) {
      //user can only delete row that he/she own
      Integer tempUserID = (Integer)displayTable.getModel().getValueAt(row,USER_ID_COL);
      if (!cmis.getUserId().equals(tempUserID)) {
        GenericDlg.showMessage("You can't delete other user comments.");
        return;
      }
      Boolean b = (Boolean)displayTable.getModel().getValueAt(row,col);
      if (b.booleanValue()) {
        displayTable.getModel().setValueAt(new Boolean(false), row, DELETE_COL);
        displayTable.getModel().setValueAt("UPDATE", row, STATUS_COL);
        highLightTable(displayTable,row,false);
      }else {
        displayTable.getModel().setValueAt(new Boolean(true), row, DELETE_COL);
        displayTable.getModel().setValueAt("DELETE", row, STATUS_COL);
        highLightTable(displayTable,row,true);
      }
    }
  } //end of method

  Vector getTableData() {
    Vector dataV = new Vector(displayTable.getRowCount());
    for (int i = 0; i < displayTable.getRowCount(); i++) {
      Hashtable h = new Hashtable(displayTable.getColumnCount());
      h.put("COMMENT_ID",(String)displayTable.getModel().getValueAt(i,ID_COL));
      h.put("COMMENTS",(String)displayTable.getModel().getValueAt(i,COMMENTS_COL));
      h.put("BY",(String)displayTable.getModel().getValueAt(i,BY_COL));
      h.put("DATE",(String)displayTable.getModel().getValueAt(i,DATE_COL));
      h.put("STATUS",(String)displayTable.getModel().getValueAt(i,STATUS_COL));
      h.put("USER_ID",(Integer)displayTable.getModel().getValueAt(i,USER_ID_COL));
      dataV.addElement(h);
    }
    return dataV;
  } //end of method

  void printB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      try{
        Vector dataV = getTableData();
        if (dataV.size() < 1) {
          GenericDlg.showMessage("No Data to print.");
          return;
        }
        TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObj",cmis);
        Hashtable query = new Hashtable();
        query.put("ACTION","PRINT_WORK_AREA_COMMENTS");
        query.put("USER_ID",cmis.getUserId());
        query.put("FACILITY_NAME",facIDT.getText());
        query.put("WORK_AREA_DESC",waIDT.getText());
        query.put("CATALOG_DESC",catalogT.getText());
        query.put("CAT_PART_NO",partNo);
        query.put("WORK_AREA_COMMENTS_DATA",dataV);
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

class WorkAreaCommentsDlgLoadThread extends Thread {
  WorkAreaCommentsDlg parent;
  public WorkAreaCommentsDlgLoadThread(WorkAreaCommentsDlg parent){
     super("WorkAreaCommentsDlgLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadData();
  }
}

class WorkAreaComments_mouseAdapter extends java.awt.event.MouseAdapter {
  WorkAreaCommentsDlg adaptee;
  WorkAreaComments_mouseAdapter(WorkAreaCommentsDlg adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.displayTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}



