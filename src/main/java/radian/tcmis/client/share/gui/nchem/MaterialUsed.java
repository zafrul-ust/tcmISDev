package radian.tcmis.client.share.gui.nchem;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

import java.awt.event.ActionEvent;
import com.borland.jbcl.layout.GridBagConstraints2;
import com.borland.jbcl.layout.XYLayout;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import radian.tcmis.client.share.helpers.ClientHelpObjs;
import radian.tcmis.client.share.helpers.TableSorter;
import java.awt.*;
import java.lang.Process;
import java.io.*;
import java.util.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.gui.*;
import javax.swing.*;
import radian.tcmis.both1.helpers.*;
import javax.swing.table.*;
import java.awt.event.*;

public class MaterialUsed extends JPanel {
  CmisApp cmis;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton addB = new JButton();
  JScrollPane tableJsp = new JScrollPane();
  JComboBox processC = new JComboBox();
  boolean firstTime = true;
  AttributiveCellTableModel model;
  MultiSpanCellTable mtlUsedTable;
  String viewLevel = "";
  String currentFacID = "";
  String currentWorkAreaID = "";
  String currentFacDesc = "";
  String currentWorkAreaDesc = "";
  Hashtable currentFacWorkAreaInfo;
  Vector currentProcessIDV;
  MtlUsedComboBoxItemListener cbil;
  String lastSelectedProcessID = "";
  int partCol = 0;
  int descCol = 0;
  int materialDescCol = 0;
  int packagingCol = 0;
  int processCycleCol = 0;
  int projectedQtyCol = 0;
  int projectedUnitCol = 0;
  int percentCol = 0;
  int newCol = 0;
  int partGroupNoCol = 0;
  int catalogCol = 0;
  int itemCol = 0;
  int sizeUnitOptionsCol = 0;
  int partIDCol = 0;
  String requestID = "";
  String tmpProcessCycle = "";
  Vector currentEditableProcess;

  public MaterialUsed( CmisApp cmis )
  {
    try
    {
      this.cmis=cmis;
      jbInit();
    }
    catch ( Exception ex )
    {
      ex.printStackTrace();
    }
  }

  public void setParent(CmisApp c) {
    cmis=c;
  }

  public void setViewLevel(String s) {
    viewLevel = s;
  }

  public void setProcessIDV(Vector v) {
    currentProcessIDV = v;
  }

  public void setRequestID(String s) {
    requestID = s;
  }

  public void loadIt() {
    MaterialUsedLoadThread x=new MaterialUsedLoadThread( this );
    x.start();
  }

  void jbInit() throws Exception {
    ImageIcon ss=ResourceLoader.getImageIcon( "images/button/add.gif" );
    processC.setMaximumSize(new Dimension(250, 25));
    processC.setMinimumSize(new Dimension(250, 25));
    processC.setPreferredSize(new Dimension(250, 25));
    cbil = new MtlUsedComboBoxItemListener(this);
    addB.setMaximumSize(new Dimension(180, 25));
    addB.setMinimumSize(new Dimension(180, 25));
    addB.setPreferredSize(new Dimension(180, 25));
    addB.setIcon( ss );
    addB.setText("Add/Remove Parts");
    addB.addActionListener(new MaterialUsed_addB_actionAdapter(this));
    tableJsp.setMaximumSize(new Dimension(690, 408));
    tableJsp.setMinimumSize(new Dimension(690, 408));
    tableJsp.setPreferredSize(new Dimension(690, 408));
    this.add(processC, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(addB, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(tableJsp,  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.validate();
  }

  void loadItAction() {
    System.out.println( "Here" );
  }

  public void loadData(String facID, String waID, Hashtable facWorkAreaInfo, Vector editableProcess) {
    currentFacWorkAreaInfo = facWorkAreaInfo;
    currentEditableProcess = editableProcess;
    currentFacID = facID;
    currentWorkAreaID = waID;
    buildTable();
    //set selected process to the last process selected
    processC.removeItemListener(cbil);
    if (currentProcessIDV.indexOf(lastSelectedProcessID) >= 0 ) {
      processC.setSelectedIndex(currentProcessIDV.indexOf(lastSelectedProcessID));
    }else {
      processC.setSelectedIndex(0);
    }
    processC.addItemListener(cbil);
    //now load data for selected process
    if (!"Select a Process".equalsIgnoreCase((String) processC.getSelectedItem())) {
      String tmpProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
      if (currentFacWorkAreaInfo.containsKey(tmpProcessID)) {
        Hashtable tmpProcessData = (Hashtable) currentFacWorkAreaInfo.get(tmpProcessID);
        //get current process cycle
        tmpProcessCycle = "";
        Vector operationDefV = (Vector) tmpProcessData.get("OPERATION_DEFINITION");
        for (int j = 0; j < operationDefV.size(); j++) {
          Hashtable innerH = (Hashtable) operationDefV.elementAt(j);
          tmpProcessCycle = (String) innerH.get("OPERATING_TIME")+" "+(String) innerH.get("OPERATING_TIME_UNIT");
        }
        //now build table
        buildTableData((Vector) tmpProcessData.get("MATERIAL_USED"));
      } //end of if process is in hashtable
    }
    lastSelectedProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
    if (currentEditableProcess.contains(lastSelectedProcessID)) {
      // allow requestor to add another process
      if ("Viewer".equalsIgnoreCase(viewLevel) ||
          "Select a Process".equalsIgnoreCase( (String) processC.getSelectedItem())) {
        addB.setEnabled(false);
        model.setEditableFlag(0);
      }else {
        addB.setEnabled(true);
        int editF = 0;
        if (!"Viewer".equalsIgnoreCase(viewLevel)) {
          editF = BothHelpObjs.mypow(2,projectedQtyCol) + BothHelpObjs.mypow(2,projectedUnitCol) + BothHelpObjs.mypow(2,percentCol);
        }
        model.setEditableFlag(editF);
      }
    }else {
      addB.setEnabled(false);
      model.setEditableFlag(0);
    }
  } //end of method

  void saveCurrentData() {
    //stop editing
    if (mtlUsedTable.isEditing()){
      mtlUsedTable.getCellEditor().stopCellEditing();
    }
    //step thru data and put into data structure
    int i = 0;
    String currentProcessID = (String) currentProcessIDV.elementAt(currentProcessIDV.indexOf(lastSelectedProcessID));
    if (currentFacWorkAreaInfo.containsKey(currentProcessID)) {
      Hashtable h = (Hashtable) currentFacWorkAreaInfo.get(currentProcessID);
      Vector mtlUsedV = (Vector) h.get("MATERIAL_USED");
      //first remove data from vector then update it with current display data
      mtlUsedV.removeAllElements();
      for (i = 0; i < mtlUsedTable.getRowCount(); i++) {
        Object[] oa = new Object[mtlUsedTable.getColumnCount()];
        int j = 0;
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.partCol);
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.descCol);
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.materialDescCol);
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.packagingCol);
        oa[j++] = "";
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.projectedQtyCol);
        oa[j++] = ((JComboBox) mtlUsedTable.getModel().getValueAt(i,this.projectedUnitCol)).getSelectedItem().toString();
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.percentCol);
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.newCol);
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.partGroupNoCol);
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.catalogCol);
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.itemCol);
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.sizeUnitOptionsCol);
        oa[j++] = (String) mtlUsedTable.getModel().getValueAt(i,this.partIDCol);
        mtlUsedV.addElement(oa);
      } //end of outer loop stepping thru the table
    } //end of data structure contains current selected process
  } //end of method


  String auditData() {
    //stop editing
    if (mtlUsedTable.isEditing()){
      mtlUsedTable.getCellEditor().stopCellEditing();
    }
    String result = "OK";
    //Only audit if current process is editable and not viewer
    if (currentEditableProcess.contains(lastSelectedProcessID)) {
      if (!"Viewer".equalsIgnoreCase(viewLevel)) {
        for (int i = 0; i < mtlUsedTable.getRowCount(); i++) {
          String sizeUnit = ((JComboBox) mtlUsedTable.getModel().getValueAt(i,this.projectedUnitCol)).getSelectedItem().toString();
          if ("Select One".equalsIgnoreCase(sizeUnit)) {
            return "Please select a Proj. Unit/Cycle";
          }
          try {
            float tmp = Float.parseFloat((String)mtlUsedTable.getModel().getValueAt(i,projectedQtyCol));
          }catch (Exception e) {
            setSelectedRow(i);
            return "Projected Qty per Cycle must be a number.";
          }
          try {
            float perSum = Float.parseFloat((String)mtlUsedTable.getModel().getValueAt(i,percentCol));
          }catch (Exception e) {
            setSelectedRow(i);
            return "Percentage must be a number.";
          }
        } //end of stepping thru table
      } //end of if not viewer
    } //end of last process is an editable process
    return result;
  } //end of method

  void buildTable() {
    tableJsp.getViewport().removeAll();
    String[] colHeaders = {"Part #","Description","Material Desc","Packaging","Process Cycle","Proj. Qty/Cycle","Proj. Unit/Cycle","% Emitted","New","Part Group No","Catalog","Item","Size Unit Options","Part"};
    partCol = 0;
    descCol = 1;
    materialDescCol = 2;
    packagingCol = 3;
    processCycleCol = 4;
    projectedQtyCol = 5;
    projectedUnitCol = 6;
    percentCol = 7;
    newCol = 8;
    partGroupNoCol = 9;
    catalogCol = 10;
    itemCol = 11;
    sizeUnitOptionsCol = 12;
    partIDCol = 13;

    int[] colWidths = {160,225,100,100,80,80,100,100,0,0,0,0,0,0};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
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
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING};

    model = new AttributiveCellTableModel(colHeaders,100); //length of table can be set by server
    int i = 0;
    for (i = model.getRowCount()-1; i >= 0; i--) {
      model.removeRow(i);
    }
    int editF = 0;
    model.setEditableFlag(editF);
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
    mtlUsedTable = new MultiSpanCellTable(model);
    mtlUsedTable.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    mtlUsedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    mtlUsedTable.getTableHeader().setResizingAllowed(true);
    mtlUsedTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = mtlUsedTable.getColumnModel();
    for (i = 0; i < mtlUsedTable.getColumnCount(); i++) {
      if (i == projectedUnitCol) {
        cm.getColumn(i).setCellRenderer(new JComboBoxCellRenderer());
        cm.getColumn(i).setCellEditor(new JComboBoxCellEditor(new JComboBox()));
      }else{
        cm.getColumn(i).setCellRenderer(renderers[0]);
      }
    }
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    mtlUsedTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = mtlUsedTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    mtlUsedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<mtlUsedTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      mtlUsedTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      mtlUsedTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        mtlUsedTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        mtlUsedTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    tableJsp.getViewport().add(mtlUsedTable);
  }  //end of method

  void addB_actionPerformed(ActionEvent e) {
    //stop editing
    if (mtlUsedTable.isEditing()){
      mtlUsedTable.getCellEditor().stopCellEditing();
    }
    EditProcessDetaildlg epddlg = new EditProcessDetaildlg(cmis.getMain(),"Add/Remove Materials Used for "+currentFacDesc+"/"+currentWorkAreaDesc,
                                                           "Materials Used",currentFacID,currentWorkAreaID,(String)currentProcessIDV.elementAt(processC.getSelectedIndex()),(String)processC.getSelectedItem());
    epddlg.setParent(cmis);
    epddlg.setRequestID(requestID);
    //need to find out current emission points for selected process
    Hashtable tmpDataH = new Hashtable(mtlUsedTable.getRowCount());
    for (int k = 0; k < mtlUsedTable.getRowCount(); k++) {
      String key = (String)mtlUsedTable.getModel().getValueAt(k,partCol)+(String)mtlUsedTable.getModel().getValueAt(k,catalogCol)+
                   (String)mtlUsedTable.getModel().getValueAt(k,partGroupNoCol)+(String)mtlUsedTable.getModel().getValueAt(k,partIDCol);
      String[] oa = new String[4];
      oa[0] = (String)mtlUsedTable.getModel().getValueAt(k,projectedQtyCol);
      oa[1] = ((JComboBox) mtlUsedTable.getModel().getValueAt(k,this.projectedUnitCol)).getSelectedItem().toString();
      oa[2] = (String)mtlUsedTable.getModel().getValueAt(k,percentCol);
      oa[3] = (String)mtlUsedTable.getModel().getValueAt(k,sizeUnitOptionsCol);
      tmpDataH.put(key,oa);
    }
    epddlg.setSelectedData(tmpDataH);
    epddlg.loadIt();
    if (epddlg.loadDataOk) {
      CenterComponent.centerCompOnScreen(epddlg);
      epddlg.setVisible(true);
      if (!epddlg.cancelled) {
        //first remove data from table
        for (int j = mtlUsedTable.getRowCount() -1; j >= 0; j--) {
          AttributiveCellTableModel model = (AttributiveCellTableModel)mtlUsedTable.getModel();
          model.removeRow(j);
        }
        //now update table from user's selection
        Vector v = epddlg.updateDataV;
        Vector combineRow = new Vector();
        String lastPartNumber = "";
        for (int i = 0; i < v.size(); i++) {
          Object[] oa = (Object[]) v.elementAt(i);
          oa[processCycleCol] = tmpProcessCycle;
          String currentPartNumber = (String)oa[partCol]+(String)oa[catalogCol]+(String)oa[partGroupNoCol];
          String tmpSizeUnit = "";
          Object tmpObj = (Object)oa[projectedUnitCol];
          if (tmpObj.getClass().getName().equals("javax.swing.JComboBox")) {
            tmpSizeUnit = ((JComboBox)tmpObj).getSelectedItem().toString();
          }else {
            tmpSizeUnit = tmpObj.toString();
          }
          oa[projectedUnitCol] = getComboDetail((String)oa[sizeUnitOptionsCol],tmpSizeUnit);
          addRowToTable(mtlUsedTable,oa);
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
        }
        if (combineRow.size() > 0) {
          combineTableData(mtlUsedTable,combineRow);
        }
      }
    }
    epddlg.dispose();
  }  //end of method

  void processC_itemStateChanged(ItemEvent e) {
    if (e.getStateChange() == e.SELECTED) {
      //now load data for selected process
      if (!"Select a Process".equalsIgnoreCase((String) processC.getSelectedItem())) {
        //first make sure that
        String msg = auditData();
        if (!"OK".equalsIgnoreCase(msg)) {
          processC.removeItemListener(cbil);
          processC.setSelectedIndex(currentProcessIDV.indexOf(lastSelectedProcessID));
          processC.addItemListener(cbil);
          GenericDlg.showMessage(msg);
          return;
        }
        //first save display data
        saveCurrentData();
        //next delete row from table and reload it from data
        //empty table
        AttributiveCellTableModel model = (AttributiveCellTableModel)mtlUsedTable.getModel();
        for (int i = mtlUsedTable.getRowCount() -1; i >= 0; i--) {
          model.removeRow(i);
        }
        //RELOAD table from data
        String tmpProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
        if (currentFacWorkAreaInfo.containsKey(tmpProcessID)) {
          Hashtable tmpProcessData = (Hashtable) currentFacWorkAreaInfo.get(tmpProcessID);
          //get current process cycle
          tmpProcessCycle = "";
          Vector operationDefV = (Vector) tmpProcessData.get("OPERATION_DEFINITION");
          for (int j = 0; j < operationDefV.size(); j++) {
            Hashtable innerH = (Hashtable) operationDefV.elementAt(j);
            tmpProcessCycle = (String) innerH.get("OPERATING_TIME")+" "+(String) innerH.get("OPERATING_TIME_UNIT");
          }
          //now build table
          buildTableData((Vector) tmpProcessData.get("MATERIAL_USED"));
        } //end of if process is in hashtable
        if (currentEditableProcess.contains(tmpProcessID)) {
          if ("Viewer".equalsIgnoreCase(viewLevel)) {
            addB.setEnabled(false);
            model.setEditableFlag(0);
          }else {
            addB.setEnabled(true);
            int editF = 0;
            if (!"Viewer".equalsIgnoreCase(viewLevel)) {
              editF = BothHelpObjs.mypow(2,projectedQtyCol) +BothHelpObjs.mypow(2,projectedUnitCol) + BothHelpObjs.mypow(2,percentCol);
            }
            model.setEditableFlag(editF);
          }
        }else {
          addB.setEnabled(false);
          model.setEditableFlag(0);
        }
      }else {
        //empty table
        AttributiveCellTableModel model = (AttributiveCellTableModel)mtlUsedTable.getModel();
        for (int i = mtlUsedTable.getRowCount() -1; i >= 0; i--) {
          model.removeRow(i);
        }
        addB.setEnabled(false);
      }
      lastSelectedProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
    }
  } //end of process

  void buildTableData(Vector materialUsedData) {
    Vector combineRow = new Vector();
    String lastPartNumber = "";
    for (int i = 0; i < materialUsedData.size(); i++) {
      Object[] oa = (Object[]) materialUsedData.elementAt(i);
      String currentPartNumber = (String)oa[partCol]+(String)oa[catalogCol]+(String)oa[partGroupNoCol];
      String tmp = (String) oa[0];
      //shipping blank part used
      tmp = tmp.trim();
      if (tmp.length() < 1) {
        continue;
      }
      oa[processCycleCol] = tmpProcessCycle;
      String tmpSizeUnit = "";
      Object tmpObj = (Object)oa[projectedUnitCol];
      if (tmpObj.getClass().getName().equals("javax.swing.JComboBox")) {
        tmpSizeUnit = ((JComboBox)tmpObj).getSelectedItem().toString();
      }else {
        tmpSizeUnit = tmpObj.toString();
      }
      oa[projectedUnitCol] = getComboDetail((String)oa[sizeUnitOptionsCol],tmpSizeUnit);
      addRowToTable(mtlUsedTable,oa);
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
    }
    if (combineRow.size() > 0) {
      combineTableData(mtlUsedTable,combineRow);
    }
  } //end of method

  JComboBox getComboDetail(String sizeUnitOptions, String s) {
    JComboBox j = new JComboBox();
    Vector v = new Vector();
    if (BothHelpObjs.isBlankString(sizeUnitOptions)) {
      v.addElement("No Size Unit");
    }else {
      StringTokenizer st = new StringTokenizer(sizeUnitOptions,";");
      if (st.countTokens() == 1) {
        v.addElement(sizeUnitOptions);
      }else {
        while (st.hasMoreElements()) {
          v.addElement(st.nextToken());
        }
      }
    }
    if (v.size() > 1) {
      v.insertElementAt("Select One",0);
    }

    j = ClientHelpObjs.loadChoiceFromVector(j,v);
    if (v.contains(s)) {
      j.setSelectedItem(s);
    }else {
      j.setSelectedIndex(0);
    }
    j.setEditable(false);
    j.setMaximumRowCount(v.size());
    j.revalidate();
    return j;
  }

  void combineTableData(MultiSpanCellTable myTable,Vector combineRow) {
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
    } //end of outer for
    mtlUsedTable.repaint();
  }


  void addRowToTable(MultiSpanCellTable myTable,Object[] data) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    int row = model.getRowCount();
    model.insertRow(row,data);
    mtlUsedTable.repaint();
  }

  void setSelectedRow(int x){
    ListSelectionModel lsm = mtlUsedTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    mtlUsedTable.scrollRectToVisible(mtlUsedTable.getCellRect(x,0,false));
  } //end of method

} //end of class

class MaterialUsedLoadThread extends Thread {
  MaterialUsed parent;
  boolean runF = true;

  public MaterialUsedLoadThread (MaterialUsed parent){
     super("MaterialUsedLoadThread");
     this.parent = parent;
  }

  public void run()
  {
    parent.loadItAction();
  }
}

class MtlUsedComboBoxItemListener implements java.awt.event.ItemListener {
  MaterialUsed adaptee;

  MtlUsedComboBoxItemListener(MaterialUsed adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.processC_itemStateChanged(e);
  }
}

class MaterialUsed_addB_actionAdapter implements java.awt.event.ActionListener {
  MaterialUsed adaptee;

  MaterialUsed_addB_actionAdapter(MaterialUsed adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.addB_actionPerformed(e);
  }
}


