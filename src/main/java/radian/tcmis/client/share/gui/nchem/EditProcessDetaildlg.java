package radian.tcmis.client.share.gui.nchem;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

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
import com.borland.jbcl.layout.XYLayout;
import java.beans.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class EditProcessDetaildlg extends JDialog {
  CmisTable editTable;
  TableSorter sorter = new TableSorter();

  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  XYLayout xYLayout1 = new XYLayout();
  CmisApp cmis ;
  JScrollPane partJSP = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel bottomP = new JPanel();
  JButton cancelB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  StaticJLabel noteL = new StaticJLabel();
  String currentFacilityID = "";
  String currentWorkAreaID = "";
  String currentProcessID = "";
  String currentProcessName = "";
  EmissionPoint emissionPt;
  MaterialUsed mtlUsed;
  ProcessDetailWaste waste;
  String currentScreen = "";
  boolean loadDataOk = true;
  JButton addB = new JButton();
  Vector updateDataV = new Vector(0);
  boolean cancelled = false;
  String requestID = "";
  Hashtable selectedData;

  public EditProcessDetaildlg(JFrame fr, String title, String currentS, String facilityId, String wa, String processID, String processName) {
    super(fr, title, true);
    this.currentScreen = currentS;
    this.currentFacilityID = facilityId;
    this.currentWorkAreaID = wa;
    this.currentProcessID = processID;
    this.currentProcessName = processName;
    try  {
      jbInit();
      pack();
      if ("Emission Point".equalsIgnoreCase(currentScreen)) {
        this.setSize(new Dimension(500, 400));
      }else if ("Materials Used".equalsIgnoreCase(currentScreen)) {
        this.setSize(new Dimension(700, 400));
      }else {
        this.setSize(new Dimension(500, 400));
      }

      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }


  public void setParent(CmisApp c){
    cmis = c;
  }

  public void setRequestID(String s) {
    requestID = s;
  }

  public void setSelectedData(Hashtable h) {
    selectedData = h;
  }

  public void loadIt(){
    loadItAction();
  }

  void jbInit() throws Exception {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setFont(new java.awt.Font("Dialog", 1, 10));
    okB.setMaximumSize(new Dimension(80, 25));
    okB.setMinimumSize(new Dimension(80, 25));
    okB.setPreferredSize(new Dimension(80, 25));
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setFont(new java.awt.Font("Dialog", 1, 10));
    cancelB.setMaximumSize(new Dimension(102, 25));
    cancelB.setMinimumSize(new Dimension(102, 25));
    cancelB.setPreferredSize(new Dimension(102, 25));
    cancelB.setIcon(ss);
    panel1.setLayout(borderLayout4);
    jPanel1.setLayout(xYLayout1);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    editTable = new CmisTable();
    partJSP.getViewport().setView(editTable);
    panel1.setMaximumSize(new Dimension(520, 380));
    panel1.setMinimumSize(new Dimension(520, 380));
    panel1.setPreferredSize(new Dimension(520, 380));
    jPanel6.setLayout(gridBagLayout1);
    jPanel7.setLayout(borderLayout5);
    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    bottomP.setLayout(borderLayout1);
    addB.setFont(new java.awt.Font("Dialog", 1, 10));
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);
    noteL.setText("Note - To edit/remove profile(s) to/from current process. Click on row.");
    jPanel6.add(noteL, new GridBagConstraints2(0, 0, 0, 0, 0.0, 0.0
           ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 15, 5, 5), 0, 0));
     jPanel6.add(jPanel7, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
           ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(40, 15, 50, 15), 0, 0));
    jPanel7.add(partJSP, BorderLayout.CENTER);
    jPanel6.add(bottomP, new GridBagConstraints2(1, 1, 0, 0, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(okB, BorderLayout.WEST);
    bottomP.add(cancelB, BorderLayout.EAST);
  }

  void loadItAction() {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",cmis);
      Hashtable query = new Hashtable();
      String action = "";
      System.out.println("------ current screen: "+currentScreen);
      if ("Emission Point".equalsIgnoreCase(currentScreen)) {
        action = "GET_EMISSION_POINT_EDIT_INFO";
        noteL.setText("Note - To edit/remove Emission Point(s) to/from current process.  Click on row.");
      }else if ("Materials Used".equalsIgnoreCase(currentScreen)) {
        action = "GET_MATERIAL_USED_EDIT_INFO";
        noteL.setText("Note - To edit/remove Material(s) Used to/from current process.  Click on row.");
      }else if ("Waste".equalsIgnoreCase(currentScreen)) {
        action = "GET_WASTE_STREAM_EDIT_INFO";
        noteL.setText("Note - To edit/remove profile(s) to/from current process.  Click on row.");
      }else if ("Training".equalsIgnoreCase(currentScreen)) {
        action = "GET_TRAINING_EDIT_INFO";
        noteL.setText("Note - To edit/remove training(s) to/from current process.  Click on row.");
      }else {
        action = "GET_PPE_EDIT_INFO";
        noteL.setText("Note - To edit/remove PPE(s) to/from current process.  Click on row.");
      }

      query.put("ACTION",action); //String, String
      query.put("FACILITY_ID",this.currentFacilityID); //String, String
      query.put("WORK_AREA",this.currentWorkAreaID); //String, String
      query.put("PROCESS_ID",this.currentProcessID); //String, String
      query.put("USER_ID",cmis.getUserId());
      query.put("REQUEST_ID",requestID);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showMessage("tcmIS encounter a problem while loading data.\nPlease save your current data.\nIf problem persist restart tcmIS and try again.");
        ClientHelpObjs.setEnabledContainer(this,true);
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        loadDataOk = false;
        return;
      }


      Hashtable editInfoH = (Hashtable)result.get("EDIT_INFO");
      if (!"OK".equalsIgnoreCase((String)editInfoH.get("MSG"))) {
        GenericDlg.showMessage((String)editInfoH.get("MSG"));
        loadDataOk = false;
        return;
      }
      //if material used tab then go to method with table that combine rows
      if ("Materials Used".equalsIgnoreCase(currentScreen)) {
        if (editTable != null) {
          partJSP.getViewport().remove(editTable);
        }
        if (displayTable != null) {
          partJSP.getViewport().remove(displayTable);
        }
        buildMaterialUsedTable(editInfoH);
        if (displayTable.getModel().getRowCount()<=0){
          GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
          g.setMsg("No Records found.");
          g.setVisible(true);
          loadDataOk = false;
          return;
        }
      }else {
        if (editTable != null) {
          partJSP.getViewport().remove(editTable);
        }
        if (displayTable != null) {
          partJSP.getViewport().remove(displayTable);
        }

        DbTableModel ctm = buildTableModel(editInfoH);
        sorter = new TableSorter(ctm);
        editTable = new CmisTable(sorter);
        sorter.setColTypeFlag(ctm.getColumnTypesString());
        sorter.addMouseListenerToHeaderInTable(editTable);
        editTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        editTable.addMouseListener(new ProcessDetail_Edit_mouseAdapter(this));

        AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
        Hashtable tableStyle = BothHelpObjs.getTableStyle();
        Color color = (Color)tableStyle.get("COLOR");
        Integer t = (Integer)tableStyle.get("INSET_TOP");
        Integer l = (Integer)tableStyle.get("INSET_LEFT");
        Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
        Integer r = (Integer)tableStyle.get("INSET_RIGHT");
        Integer a = (Integer)tableStyle.get("INSET_ALIGN");
        renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
        //font and foreground and background color for columns heading
        String fontName = (String)tableStyle.get("FONT_NAME");
        Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
        Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
        editTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = editTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
        }
        editTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        editTable.setCellSelectionEnabled(false);
        editTable.getTableHeader().setReorderingAllowed(true);
        WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
        WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();
        editTable.setDefaultRenderer(String.class, colorTableRenderer);
        editTable.setDefaultRenderer(Integer.class, colorTableRenderer);
        editTable.setDefaultRenderer(Double.class, colorTableRenderer);
        editTable.setDefaultRenderer(Boolean.class, checkTableRenderer);

        JCheckBox check = new JCheckBox();
        check.setHorizontalAlignment(JLabel.CENTER);
        editTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor(check));

        // set column widths
        for(int i=0;i<editTable.getColumnCount();i++){
          int width = ctm.getColumnWidth(i);
          editTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
          editTable.getColumn(ctm.getColumnName(i)).setWidth(width);
          if (width==0){
            editTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
            editTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
          }
        }

        partJSP.getViewport().setView(editTable);
        if (editTable.getModel().getRowCount()<=0){
          GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
          g.setMsg("No Records found.");
          g.setVisible(true);
          loadDataOk = false;
          return;
        }
      } //end of not material used tab
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
      loadDataOk = true;
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showMessage("tcmIS encounter a problem while loading data.\nPlease save your current data.\nIf problem persist restart tcmIS and try again.");
        ClientHelpObjs.setEnabledContainer(this,true);
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        loadDataOk = false;
        return;
    }
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }

  void buildMaterialUsedTable(Hashtable searchData) {
    int processCol = 0;
    int processNameCol = 1;
    int partCol = 2;
    //int descCol = 3;
    int materialDescCol = 4;
    //int packagingCol = 5;
    int projQtyCol = 6;
    int projUnitCol = 7;
    int percentCol = 8;
    int selCol = 9;
    int partGroupNoCol = 10;
    int catalogCol = 11;
    //int itemCol = 12;
    int sizeUnitOptionsCol = 13;
    int partIDCol = 14;
    int colorCol = 15;

    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");
    modelDetail = new AttributiveCellTableModel(colHeads,data.size());
    for (int k = data.size() -1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    setMaterialUsedTableStyle(colWidths,colTypes,selCol);
    Vector combineRow = new Vector();
    String lastPartNumber = "";
    for (int i = 0; i < data.size(); i++) {
      Object[] oa = (Object[]) data.elementAt(i);
      String key = (String)oa[partCol]+(String)oa[catalogCol]+(String)oa[partGroupNoCol]+(String)oa[partIDCol];
      String currentPartNumber = (String)oa[partCol]+(String)oa[catalogCol]+(String)oa[partGroupNoCol];
      if (selectedData.containsKey(key)) {
        String[] tmp = (String[]) selectedData.get(key);
        oa[processCol] = currentProcessID;
        oa[processNameCol] = currentProcessName;
        oa[projQtyCol] = tmp[0];
        oa[projUnitCol] = tmp[1];
        oa[percentCol] = tmp[2];
        oa[sizeUnitOptionsCol] = tmp[3];
        oa[selCol] = new Boolean(true);
      }
      modelDetail.addRow(oa);
      //logic for combining kits
      if (lastPartNumber.equalsIgnoreCase(currentPartNumber)) {
        Vector vv = (Vector)combineRow.lastElement();
        vv.addElement(new Integer(i));
      }else {
        Vector vv = new Vector(29);
        vv.addElement(new Integer(i));
        combineRow.addElement(vv);
      }
      lastPartNumber = currentPartNumber;
    } //end of for loop
    if (combineRow.size() > 0) {
      combineTableData(displayTable,combineRow,materialDescCol,colorCol);
    }
  } //end of method

  void setMaterialUsedTableStyle(int[] colWidths, int[] colTypes, int selCol) {
    modelDetail.setEditableFlag(BothHelpObjs.mypow(2,selCol));
    modelDetail.setColumnPrefWidth(colWidths);
    modelDetail.setColumnType(colTypes);
    displayTable = new MultiSpanCellTable(modelDetail);
    displayTable.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    displayTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    displayTable.getTableHeader().setResizingAllowed(true);
    displayTable.addMouseListener(new ProcessDetail_EditMaterialUsed_mouseAdapter(this));
    displayTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = displayTable.getColumnModel();
    int i = 0;
    for (i = 0; i < displayTable.getColumnCount(); i++) {
      if (i == selCol) {
        cm.getColumn(i).setCellRenderer(new CheckBoxCellRenderer());
        cm.getColumn(i).setCellEditor(new CheckBoxCellEditor(new JCheckBox()));
      }else {
        cm.getColumn(i).setCellRenderer(renderers[0]);
      }
    }
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    displayTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = displayTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    displayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<displayTable.getColumnCount();i++){
      int width = modelDetail.getColumnWidth(i);
      displayTable.getColumn(modelDetail.getColumnName(i)).setPreferredWidth(width);
      displayTable.getColumn(modelDetail.getColumnName(i)).setWidth(width);
      if (width==0){
        displayTable.getColumn(modelDetail.getColumnName(i)).setMinWidth(width);
        displayTable.getColumn(modelDetail.getColumnName(i)).setMaxWidth(width);
      }
    }
    partJSP.getViewport().setView(displayTable);
  } //end of method

  void highlightMaterialUsedTable(MultiSpanCellTable myTable,int firstIndex, int lastIndex, int materialDescCol, int colorCol, boolean selected) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellColorAtt = (ColoredCell)model.getCellAttribute();
    Vector combineRow = new Vector();
    for (int k = firstIndex; k <= lastIndex; k++) {
      Vector v = new Vector();
      v.addElement(new Integer(k));
      combineRow.addElement(v);
    }
    for (int j = 0; j < combineRow.size(); j++) {
      Vector v = (Vector)combineRow.elementAt(j);
      int[] row = new int[v.size()];
      for (int k = 0; k < v.size(); k++) {
        Integer tmp = (Integer)v.elementAt(k);
        row[k] = tmp.intValue();
      }
      if (selected) {
        //highlight select parts Yellow
        for (int i = 0; i < myTable.getColumnCount(); i++) {
           int[] col = new int[]{i};
           cellColorAtt.setBackground(new Color(220,220,100),row,col);
        }
      }else {
        for (int i = 0; i < materialDescCol; i++) {
          int[] col = new int[]{i};
          if ("Color".equalsIgnoreCase((String)myTable.getValueAt(row[0],colorCol))) {
          //if (firstIndex % 2 == 0 ) {
            cellColorAtt.setBackground(new Color(224,226,250),row,col);
          }else {
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
        //color kit components
        for (int ii = materialDescCol; ii < myTable.getColumnCount(); ii++) {
          int[] col = new int[]{ii};
          if ("Color".equalsIgnoreCase((String)myTable.getValueAt(row[0],colorCol))) {
          //if (firstIndex % 2 == 0 ) {
            cellColorAtt.setBackground(new Color(224,250,226),row,col);
          }else {
            cellColorAtt.setBackground(new Color(255,255,255),row,col);
          }
        }
      } //end of not selected
    } //end of outer for
    displayTable.repaint();
  }

  void combineTableData(MultiSpanCellTable myTable,Vector combineRow, int materialDescCol, int colorCol) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellColorAtt = (ColoredCell)model.getCellAttribute();
    CellSpan cellAtt = (CellSpan)model.getCellAttribute();
    for (int j = 0; j < combineRow.size(); j++) {
      Vector v = (Vector)combineRow.elementAt(j);
      int[] row = new int[v.size()];
      for (int k = 0; k < v.size(); k++) {
        Integer tmp = (Integer)v.elementAt(k);
        row[k] = tmp.intValue();
      }
      //now combine and color row(s) for Part Data
      for (int i = 0; i < materialDescCol; i++) {
        int[] col = new int[]{i};
        cellAtt.combine(row,col);
        if (j % 2 == 0 ) {
          cellColorAtt.setBackground(new Color(224,226,250),row,col);
        }else {
          cellColorAtt.setBackground(new Color(255,255,255),row,col);
        }
      }
      //color kit components
      for (int ii = materialDescCol; ii < myTable.getColumnCount(); ii++) {
        int[] col = new int[]{ii};
        if (j % 2 == 0 ) {
          cellColorAtt.setBackground(new Color(224,250,226),row,col);
        }else {
          cellColorAtt.setBackground(new Color(255,255,255),row,col);
        }
      }
      //keep track if row is colored or not
      for (int n = 0; n < row.length; n++) {
        if (j % 2 == 0) {
          myTable.getModel().setValueAt("Color", row[n], colorCol);
        }else {
          myTable.getModel().setValueAt("", row[n], colorCol);
        }
      }
    } //end of outer for
    displayTable.repaint();
  }


  DbTableModel buildTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");
    DbTableModel ctm = new DbTableModel(colHeads);
    if ("Emission Point".equalsIgnoreCase(currentScreen)) {
      buildEmissionPtTable(data,ctm);
    /*}else if ("Materials Used".equalsIgnoreCase(currentScreen)) {
      buildMaterialUsedTable(data,ctm);*/
    }else if ("Waste".equalsIgnoreCase(currentScreen)) {
      buildWasteTable(data,ctm);
    }else if ("Training".equalsIgnoreCase(currentScreen)) {
      buildTrainingTable(data,ctm);
    }else if ("PPE".equalsIgnoreCase(currentScreen)) {
      buildPPETable(data,ctm);
    }
    ctm.setColumnPrefWidth(colWidths);
    ctm.setColumnType(colTypes);
    return ctm;
  }  //end of method

  void buildEmissionPtTable(Vector data, DbTableModel ctm) {
    int processCol = 0;
    int processNameCol = 1;
    //int emissionNameCol = 2;
    //int emissionDescCol = 3;
    int appCol = 4;
    int facCol = 5;
    int percentCol = 6;
    int selCol = 7;
    for (int i = 0; i < data.size(); i++) {
      Object[] oa = (Object[]) data.elementAt(i);
      String key = (String)oa[appCol]+(String)oa[facCol];
      if (selectedData.containsKey(key)) {
        String[] tmp = (String[]) selectedData.get(key);
        oa[processCol] = currentProcessID;
        oa[processNameCol] = currentProcessName;
        oa[percentCol] = tmp[0];
        oa[selCol] = new Boolean(true);
      }
      ctm.addRow(oa);
    }
  }
  /*
  void buildMaterialUsedTable(Vector data, DbTableModel ctm) {
    int processCol = 0;
    int processNameCol = 1;
    int partCol = 2;
    //int descCol = 3;
    int materialDescCol = 4;
    //int packagingCol = 5;
    int projQtyCol = 6;
    int projUnitCol = 7;
    int percentCol = 8;
    int selCol = 9;
    int partGroupNoCol = 10;
    int catalogCol = 11;
    //int itemCol = 12;
    int sizeUnitOptionsCol = 13;
    int partIDCol = 14;
    for (int i = 0; i < data.size(); i++) {
      Object[] oa = (Object[]) data.elementAt(i);
      String key = (String)oa[partCol]+(String)oa[catalogCol]+(String)oa[partGroupNoCol]+(String)oa[partIDCol];
      if (selectedData.containsKey(key)) {
        String[] tmp = (String[]) selectedData.get(key);
        oa[processCol] = currentProcessID;
        oa[processNameCol] = currentProcessName;
        oa[projQtyCol] = tmp[0];
        oa[projUnitCol] = tmp[1];
        oa[percentCol] = tmp[2];
        oa[sizeUnitOptionsCol] = tmp[3];
        oa[selCol] = new Boolean(true);
      }
      ctm.addRow(oa);
    }
  }*/

  void buildWasteTable(Vector data, DbTableModel ctm) {
    int processCol = 0;
    int processNameCol = 1;
    int profileCol = 2;
    //int wasteStreamCol = 3;
    int wasteContainerCol = 4;
    int wasteCodeCol = 5;
    int selCol = 6;
    for (int i = 0; i < data.size(); i++) {
      Object[] oa = (Object[]) data.elementAt(i);
      String key = (String)oa[profileCol];
      if (selectedData.containsKey(key)) {
        String[] tmp = (String[]) selectedData.get(key);
        oa[processCol] = currentProcessID;
        oa[processNameCol] = currentProcessName;
        oa[wasteContainerCol] = tmp[0];
        oa[wasteCodeCol] = tmp[1];
        oa[selCol] = new Boolean(true);
      }
      ctm.addRow(oa);
    }
  }

  void buildTrainingTable(Vector data, DbTableModel ctm) {
    int processCol = 0;
    int processNameCol = 1;
    int trainingCol = 2;
    //int descCol = 3;
    int selCol = 4;
    for (int i = 0; i < data.size(); i++) {
      Object[] oa = (Object[]) data.elementAt(i);
      if (selectedData.containsKey((String)oa[trainingCol])) {
        oa[processCol] = currentProcessID;
        oa[processNameCol] = currentProcessName;
        oa[selCol] = new Boolean(true);
      }
      ctm.addRow(oa);
    }
  }

  void buildPPETable(Vector data, DbTableModel ctm) {
    int processCol = 0;
    int processNameCol = 1;
    int ppeCol = 2;
    //int descCol = 3;
    int selCol = 4;
    for (int i = 0; i < data.size(); i++) {
      Object[] oa = (Object[]) data.elementAt(i);
      if (selectedData.containsKey((String)oa[ppeCol])) {
        oa[processCol] = currentProcessID;
        oa[processNameCol] = currentProcessName;
        oa[selCol] = new Boolean(true);
      }
      ctm.addRow(oa);
    }
  }

  void addB_actionPerformed(ActionEvent e) {
    if ("Emission Point".equalsIgnoreCase(currentScreen)) {
      CreateDlg cdlg = new CreateDlg(cmis.getMain(),"New Emission Point",true, "Emission Point Description (20 chars max.): ");
      cdlg.setAuditInput(true,20);
      cdlg.setVisible(true);
      if(!cdlg.cancelled) {
        updateNewEmissionPt(cdlg.getText());
      }
      cdlg.dispose();
    }else if ("Waste Stream".equalsIgnoreCase(currentScreen)) {

    }
  }

  void updateNewEmissionPt(String emissionName) {
    try {
      CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","UPDATE_EMISSION_POINT"); //String, String
      query.put("FACILITY_ID",currentFacilityID);
      query.put("EMISSION_POINT_NAME",emissionName);
      query.put("USER_ID",cmis.getUserId());

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showMessage("tcmIS encounter a problem while loading data.\nPlease save your current data.\nIf problem persist restart tcmIS and try again.");
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }
      CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showMessage("tcmIS encounter a problem while loading data.\nPlease save your current data.\nIf problem persist restart tcmIS and try again.");
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }
  }

  void okB_actionPerformed(ActionEvent e) {
    if ("Emission Point".equalsIgnoreCase(currentScreen)) {
      updateEmissionPointData();
    }else if ("Materials Used".equalsIgnoreCase(currentScreen)) {
      updateMaterialUsedData();
    }else if ("Waste".equalsIgnoreCase(currentScreen)) {
      updateWasteData();
    }else if ("Training".equalsIgnoreCase(currentScreen)) {
      updateTrainingData();
    }else {
      updatePPEData();
    }
    cancelled = false;
    setVisible(false);
  }

  void updatePPEData() {
    int processCol = 0;
    int processNameCol = 1;
    int ppeCol = 2;
    int descCol = 3;
    updateDataV = new Vector(editTable.getRowCount());
    for (int i = 0; i < editTable.getRowCount(); i++) {
      String tmpProcess = (String) editTable.getModel().getValueAt(i,processCol);
      if (currentProcessID.equalsIgnoreCase(tmpProcess)){
        Object[] oa = new Object[3];
        int j = 0;
        oa[j++] = (String) editTable.getModel().getValueAt(i,ppeCol);
        oa[j++] = (String) editTable.getModel().getValueAt(i,descCol);
        oa[j++] = "F";
        updateDataV.addElement(oa);
      }
    }
  } //end of method

  void updateTrainingData() {
    int processCol = 0;
    int processNameCol = 1;
    int trainingCol = 2;
    int descCol = 3;
    updateDataV = new Vector(editTable.getRowCount());
    for (int i = 0; i < editTable.getRowCount(); i++) {
      String tmpProcess = (String) editTable.getModel().getValueAt(i,processCol);
      if (currentProcessID.equalsIgnoreCase(tmpProcess)){
        Object[] oa = new Object[3];
        int j = 0;
        oa[j++] = (String) editTable.getModel().getValueAt(i,trainingCol);
        oa[j++] = (String) editTable.getModel().getValueAt(i,descCol);
        oa[j++] = "F";
        updateDataV.addElement(oa);
      }
    }
  } //end of method
  void updateWasteData() {
    int processCol = 0;
    int processNameCol = 1;
    int profileCol = 2;
    int wasteStreamCol = 3;
    int wasteContainerCol = 4;
    int wasteCodeCol = 5;
    updateDataV = new Vector(editTable.getRowCount());
    for (int i = 0; i < editTable.getRowCount(); i++) {
      String tmpProcess = (String) editTable.getModel().getValueAt(i,processCol);
      if (currentProcessID.equalsIgnoreCase(tmpProcess)){
        Object[] oa = new Object[5];
        int j = 0;
        oa[j++] = (String) editTable.getModel().getValueAt(i,profileCol);
        oa[j++] = (String) editTable.getModel().getValueAt(i,wasteStreamCol);
        oa[j++] = (String) editTable.getModel().getValueAt(i,wasteContainerCol);
        oa[j++] = (String) editTable.getModel().getValueAt(i,wasteCodeCol);
        oa[j++] = "F";
        updateDataV.addElement(oa);
      }
    }
  } //end of method

  void updateMaterialUsedData() {
    int processCol = 0;
    int processNameCol = 1;
    int partCol = 2;
    int descCol = 3;
    int materialDescCol = 4;
    int packagingCol = 5;
    int projQtyCol = 6;
    int projUnitCol = 7;
    int percentCol = 8;
    int selectedCol = 9;
    int partGroupNoCol = 10;
    int catalogCol = 11;
    int itemCol = 12;
    int sizeUnitOptionsCol = 13;
    int partIDCol = 14;
    int colorCol = 15;
    updateDataV = new Vector(displayTable.getRowCount());
    for (int i = 0; i < displayTable.getRowCount(); i++) {
      String tmpProcess = (String) displayTable.getModel().getValueAt(i,processCol);
      if (currentProcessID.equalsIgnoreCase(tmpProcess)){
        Object[] oa = new Object[partIDCol];
        int j = 0;
        oa[j++] = (String) displayTable.getModel().getValueAt(i,partCol);
        oa[j++] = (String) displayTable.getModel().getValueAt(i,descCol);
        oa[j++] = (String) displayTable.getModel().getValueAt(i,materialDescCol);
        oa[j++] = (String) displayTable.getModel().getValueAt(i,packagingCol);
        oa[j++] = "";
        oa[j++] = (String) displayTable.getModel().getValueAt(i,projQtyCol);
        oa[j++] = (String) displayTable.getModel().getValueAt(i,projUnitCol);
        oa[j++] = (String) displayTable.getModel().getValueAt(i,percentCol);
        oa[j++] = "F";
        oa[j++] = (String) displayTable.getModel().getValueAt(i,partGroupNoCol);
        oa[j++] = (String) displayTable.getModel().getValueAt(i,catalogCol);
        oa[j++] = (String) displayTable.getModel().getValueAt(i,itemCol);
        oa[j++] = (String) displayTable.getModel().getValueAt(i,sizeUnitOptionsCol);
        oa[j++] = (String) displayTable.getModel().getValueAt(i,partIDCol);
        updateDataV.addElement(oa);
      }
    }
  } //end of method

  void updateEmissionPointData() {
    int processCol = 0;
    int processNameCol = 1;
    int emissionNameCol = 2;
    int emissionDescCol = 3;
    int appCol = 4;
    int facCol = 5;
    int percentCol = 6;
    updateDataV = new Vector(editTable.getRowCount());
    for (int i = 0; i < editTable.getRowCount(); i++) {
      String tmpProcess = (String) editTable.getModel().getValueAt(i,processCol);
      if (currentProcessID.equalsIgnoreCase(tmpProcess)){
        Object[] oa = new Object[6];
        int j = 0;
        oa[j++] = (String) editTable.getModel().getValueAt(i,appCol);
        oa[j++] = (String) editTable.getModel().getValueAt(i,facCol);
        oa[j++] = (String) editTable.getModel().getValueAt(i,emissionNameCol);
        oa[j++] = (String) editTable.getModel().getValueAt(i,emissionDescCol);
        oa[j++] = (String) editTable.getModel().getValueAt(i,percentCol);
        oa[j++] = "F";
        updateDataV.addElement(oa);
      }
    }
  } //end of method

  void displayTable_mouseClicked(MouseEvent e) {
    int processCol = 0;
    int processNameCol = 1;
    int partCol = 2;
    int descCol = 3;
    int materialDescCol = 4;
    int packagingCol = 5;
    int projQtyCol = 6;
    int projUnitCol = 7;
    int percentCol = 8;
    int selCol = 9;
    int partGroupNoCol = 10;
    int catalogCol = 11;
    int itemCol = 12;
    int sizeUnitOptionsCol = 13;
    int partIDCol = 14;
    int colorCol = 15;

    int firstIndex = 0;
    int lastIndex = firstIndex;
    firstIndex = displayTable.getSelectedRow();
    //determining the first and last index
    int[] index = getGroupIndex(firstIndex,partCol,catalogCol,partGroupNoCol);
    firstIndex = index[0];
    lastIndex = index[1];
    boolean selected = false;
    String processName = displayTable.getModel().getValueAt(firstIndex,processNameCol).toString();
    for (int i = firstIndex; i <= lastIndex; i++) {
      if (processName.length() > 0) {
        displayTable.getModel().setValueAt("",i,processCol);
        displayTable.getModel().setValueAt("",i,processNameCol);
        displayTable.getModel().setValueAt(new Boolean(false),i,selCol);
        selected = false;
      }else {
        displayTable.getModel().setValueAt(currentProcessID,i,processCol);
        displayTable.getModel().setValueAt(currentProcessName,i,processNameCol);
        displayTable.getModel().setValueAt(new Boolean(true),i,selCol);
        selected = true;
      }
    }
    displayTable.setRowSelectionInterval(firstIndex,lastIndex);
    highlightMaterialUsedTable(displayTable,firstIndex,lastIndex,materialDescCol,colorCol,selected);
    displayTable.repaint();
  } //end of method

  int[] getGroupIndex(int firstIndex,int partCol, int catalogCol, int partGroupNoCol) {
    int[] index = new int[2];
    int lastIndex = firstIndex;
    String selectedCatPartGroup = (String) displayTable.getModel().getValueAt(firstIndex, partCol)+
                                  (String) displayTable.getModel().getValueAt(firstIndex, catalogCol)+
                                  (String) displayTable.getModel().getValueAt(firstIndex, partGroupNoCol);
    //determine where the first index going to start
    for (int i = firstIndex - 1; i >= 0; i--) {
      String tempCatPartGroup = (String) displayTable.getModel().getValueAt(i, partCol)+
                                (String) displayTable.getModel().getValueAt(i, catalogCol)+
                                (String) displayTable.getModel().getValueAt(i, partGroupNoCol);
      if (selectedCatPartGroup.equals(tempCatPartGroup)) {
        firstIndex = i;
      }else {
        break;
      }
    }
    //determine where the last index going to end
    for (int i = firstIndex + 1; i < displayTable.getRowCount(); i++) {
      String tempCatPartGroup = (String) displayTable.getModel().getValueAt(i, partCol)+
                                (String) displayTable.getModel().getValueAt(i, catalogCol)+
                                (String) displayTable.getModel().getValueAt(i, partGroupNoCol);
      if (selectedCatPartGroup.equals(tempCatPartGroup)) {
        lastIndex = i;
      }else {
        break;
      }
    }
    index[0] = firstIndex;
    index[1] = lastIndex;
    return index;
  } //end of method

  void editTable_mouseClicked(MouseEvent e) {
    int selectedCol = 0;
    int processIDCol = 0;
    int processNameCol = 0;
    int row   = editTable.getSelectedRow();
    if ("Emission Point".equalsIgnoreCase(currentScreen)) {
      processIDCol = 0;
      processNameCol = 1;
      selectedCol = 7;
    /*}else if ("Materials Used".equalsIgnoreCase(currentScreen)) {
      processIDCol = 0;
      processNameCol = 1;
      selectedCol = 9;*/
    }else if ("Waste".equalsIgnoreCase(currentScreen)) {
      processIDCol = 0;
      processNameCol = 1;
      selectedCol = 6;
    }else if ("Training".equalsIgnoreCase(currentScreen)) {
      processIDCol = 0;
      processNameCol = 1;
      selectedCol = 4;
    }else if ("PPE".equalsIgnoreCase(currentScreen)) {
      processIDCol = 0;
      processNameCol = 1;
      selectedCol = 4;
    }

    String processName = editTable.getModel().getValueAt(row,processNameCol).toString();
    if (processName.length() > 0) {
      editTable.getModel().setValueAt("",row,processIDCol);
      editTable.getModel().setValueAt("",row,processNameCol);
      editTable.getModel().setValueAt(new Boolean(false),row,selectedCol);
    }else {
      editTable.getModel().setValueAt(currentProcessID,row,processIDCol);
      editTable.getModel().setValueAt(currentProcessName,row,processNameCol);
      editTable.getModel().setValueAt(new Boolean(true),row,selectedCol);
    }
    editTable.repaint();
  } //end of method

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    cancelled = true;
    setVisible(false);
  }
}

class ProcessDetail_Edit_mouseAdapter extends java.awt.event.MouseAdapter {
  EditProcessDetaildlg adaptee;
  ProcessDetail_Edit_mouseAdapter(EditProcessDetaildlg adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.editTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}

class ProcessDetail_EditMaterialUsed_mouseAdapter extends java.awt.event.MouseAdapter {
  EditProcessDetaildlg adaptee;
  ProcessDetail_EditMaterialUsed_mouseAdapter(EditProcessDetaildlg adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.displayTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}
