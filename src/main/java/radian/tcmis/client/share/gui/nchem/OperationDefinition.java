package radian.tcmis.client.share.gui.nchem;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */


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
import javax.swing.table.*;
import radian.tcmis.both1.helpers.*;
import java.awt.event.*;

public class OperationDefinition extends JPanel {
  CmisApp cmis;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton addB = new JButton();
  JButton reqEditB = new JButton();
  JScrollPane tableJsp = new JScrollPane();
  AttributiveCellTableModel model;
  MultiSpanCellTable processTable;
  String currentFacID = "";
  String currentWorkAreaID = "";
  String currentFacDesc = "";
  String currentWorkAreaDesc = "";
  Hashtable currentFacWorkAreaInfo;
  Vector currentProcessIDV;
  Hashtable currentStaticData;
  Integer currentMaxProcessID = new Integer(0);
  String viewLevel = "";
  ApprPane apprPane;
  Vector currentEditableProcess;

  int processCol = 0;
  int processLockCol = 0;
  int processNameCol = 0;
  int descCol = 0;
  int statusCol = 0;
  int whereCol = 0;
  int addWhereCol = 0;
  int dischargeCol = 0;
  int addDischargeCol = 0;
  int freqCol = 0;
  int freqUnitCol = 0;
  int timeCol = 0;
  int timeUnitCol = 0;
  int processDescCol = 0;
  int newCol = 0;
  int lockInfoCol = 0;
  int multipleRequestCol = 0;
  int multipleRequestDataCol = 0;
  int processNoteCol = 0;
  int noteCol = 0;

  public OperationDefinition( CmisApp cmis, String facDesc, String waDesc )
  {
    try
    {
      this.cmis=cmis;
      this.currentFacDesc = facDesc;
      this.currentWorkAreaDesc = waDesc;
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

  public void setApprPane(ApprPane ap) {
    apprPane = ap;
  }

  public void loadIt() {
    OperationDefinitionLoadThread x=new OperationDefinitionLoadThread( this );
    x.start();
  }

  void jbInit() throws Exception {
    ImageIcon ss = ResourceLoader.getImageIcon( "images/button/add.gif" );
    addB.setMaximumSize(new Dimension(150, 25));
    addB.setMinimumSize(new Dimension(150, 25));
    addB.setPreferredSize(new Dimension(150, 25));
    addB.setIcon( ss );
    addB.setText("Add Process");
    addB.addActionListener(new OperationDefinition_addB_actionAdapter(this));
    reqEditB.setMaximumSize(new Dimension(200, 25));
    reqEditB.setMinimumSize(new Dimension(200, 25));
    reqEditB.setPreferredSize(new Dimension(200, 25));
    ss = ResourceLoader.getImageIcon( "images/button/submit.gif" );
    reqEditB.setIcon( ss );
    reqEditB.setText("Request Process Change");
    reqEditB.addActionListener(new OperationDefinition_reqEditB_actionAdapter(this));
    tableJsp.setMaximumSize(new Dimension(690, 408));
    tableJsp.setMinimumSize(new Dimension(690, 408));
    tableJsp.setPreferredSize(new Dimension(690, 408));
    this.add(addB, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 210, 0, 0), 0, 0));
    this.add(reqEditB, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 210), 0, 0));
    this.add(tableJsp,  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.validate();
  }

  void loadItAction() {
  }

  public void loadData(String facID, String waID, Hashtable facWorkAreaInfo, Vector editableProcess) {
    currentFacWorkAreaInfo = facWorkAreaInfo;
    currentEditableProcess = editableProcess;
    currentFacID = facID;
    currentWorkAreaID = waID;
    buildTable();
    currentProcessIDV = (Vector)facWorkAreaInfo.get("PROCESS_ID");
    currentStaticData = (Hashtable) facWorkAreaInfo.get("PROCESS_STATIC_DATA");
    currentMaxProcessID = (Integer) facWorkAreaInfo.get("MAX_PROCESS_ID");
    for (int i = 0; i < currentProcessIDV.size(); i++) {
      String tmpProcessID = (String) currentProcessIDV.elementAt(i);
      if (currentFacWorkAreaInfo.containsKey(tmpProcessID)) {
        Hashtable tmpProcessData = (Hashtable) currentFacWorkAreaInfo.get(tmpProcessID);
        buildTableData((Vector) tmpProcessData.get("OPERATION_DEFINITION"));
      } //end of if process is in hashtable
    } //end of for loop

    // allow requestor to add another process
    if ("Viewer".equalsIgnoreCase(viewLevel)) {
      addB.setEnabled(false);
      reqEditB.setEnabled(false);
    }
  } //end of method

  void buildTableData(Vector tmpProcessData) {
    //System.out.println("---------- static data: "+tmpStaticData+"\n"+tmpProcessData);
    for (int i = 0; i < tmpProcessData.size(); i++) {
      Object[] oa = new Object[processTable.getColumnCount()];
      Hashtable innerH = (Hashtable) tmpProcessData.elementAt(i);
      int j = 0;
      oa[j++] = (String) innerH.get("PROCESS_ID");
      oa[j++] = (String) innerH.get("PROCESS_LOCK");
      oa[j++] = (String) innerH.get("PROCESS_NAME");
      String tmpProcessDesc = (String) innerH.get("PROCESS_DESC");
      if (tmpProcessDesc.length() > 0) {
        oa[j++] = "       +     ";
      }else {
        oa[j++] = "";
      }
      oa[j++] = getComboDetail(getProcessStatus((String) innerH.get("PROCESS_STATUS")),(String) innerH.get("PROCESS_STATUS"));
      oa[j++] = getComboDetail((Vector) currentStaticData.get("PROCESS_TYPE"),(String) innerH.get("PROCESS_STATION_TYPE"));
      oa[j++] = "  +  ";
      oa[j++] = getComboDetail((Vector) currentStaticData.get("DISCHARGE"),(String) innerH.get("PROCESS_DISCHARGE_PATH"));
      oa[j++] = "  +  ";
      oa[j++] = (String) innerH.get("OPERATING_FREQUENCY");
      oa[j++] = getComboDetail((Vector) currentStaticData.get("FREQ_UNIT"),(String) innerH.get("OPERATING_FREQUENCY_UNIT"));
      oa[j++] = (String) innerH.get("OPERATING_TIME");
      oa[j++] = getComboDetail((Vector) currentStaticData.get("TIME_UNIT"),(String) innerH.get("OPERATING_TIME_UNIT"));
      oa[j++] = tmpProcessDesc;
      oa[j++] = (String) innerH.get("NEW_PROCESS");
      oa[j++] = (String) innerH.get("PROCESS_LOCK_INFO");
      oa[j++] = (String) innerH.get("PROCESS_ON_MULTI_REQUEST");
      oa[j++] = (Vector) innerH.get("MULTIPLE_REQUEST_DATA");
      String tmpProcessNote = (String) innerH.get("PROCESS_NOTE");
      if (tmpProcessNote.length() > 0) {
        oa[j++] = "       +     ";
      }else {
        oa[j++] = "";
      }
      oa[j++] = tmpProcessNote;
      addRowToTable(processTable, oa,(String) innerH.get("PROCESS_LOCK_INFO") );
      //figuring out whether the process is editable or lock
      if ("Editable".equalsIgnoreCase((String) innerH.get("PROCESS_LOCK"))) {
        if (!currentEditableProcess.contains((String) innerH.get("PROCESS_ID"))) {
          currentEditableProcess.addElement((String) innerH.get("PROCESS_ID"));
        }
      } //end of process is editable
    }
  } //end of method

  Vector getProcessStatus(String s) {
    Vector tmpV = new Vector(10);
    if ("New".equalsIgnoreCase(s)) {
      tmpV.addElement("New");
      tmpV.addElement("Remove");
      tmpV.addElement("Temporary");
    }else if ("Approved".equalsIgnoreCase(s)) {
      tmpV.addElement("Approved");
      tmpV.addElement("Change");
      tmpV.addElement("Remove");
    }else {
      tmpV.addElement("Change");
      tmpV.addElement("Remove");
      tmpV.addElement("Temporary");
    }
    tmpV.trimToSize();
    return tmpV;
  }

  JComboBox getComboDetail(Vector v, String s) {
    JComboBox j = new JComboBox();
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

  void changeTableColor(int row) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)processTable.getModel();
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    int cond = row % 2;
    if (cond == 0) {
      int[] rows = new int[]{row};
      for (int j = 0; j < model.getColumnCount(); j++) {
        int[] cols = new int[]{j};
        cellAtt.setBackground(new Color(224, 226, 250), rows, cols);
      }
    }else {
      int[] rows = new int[]{row};
      for (int j = 0; j < model.getColumnCount(); j++) {
         int[] cols = new int[]{j};
         cellAtt.setBackground(new Color(255,255,255),rows,cols);
      }
    }
  }

  void addRowToTable(MultiSpanCellTable myTable,Object[] data, String lockedByMe) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    int row = model.getRowCount();
    model.insertRow(row,data);

    int cond = row % 2;
    if (cond == 0) {
      int[] rows = new int[]{row};
      for (int j = 0; j < model.getColumnCount(); j++) {
        int[] cols = new int[]{j};
        if ("Myself".equalsIgnoreCase(lockedByMe)) {
          cellAtt.setBackground(new Color(255,255,100), rows, cols);
        }else {
          cellAtt.setBackground(new Color(224, 226, 250), rows, cols);
        }
      }
    }else {
      if ("Myself".equalsIgnoreCase(lockedByMe)) {
        int[] rows = new int[]{row};
        for (int j = 0; j < model.getColumnCount(); j++) {
           int[] cols = new int[]{j};
           cellAtt.setBackground(new Color(255,255,100),rows,cols);
        }
      }
    }
    processTable.repaint();
  }

  void setSelectedRow(int x){
    ListSelectionModel lsm = processTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    processTable.scrollRectToVisible(processTable.getCellRect(x,0,false));
  }

  void processTable_mouseReleased(MouseEvent e) {
   //do nothing at least right now
  }

  void buildMultipleRequestDisplay(Vector dataV) {
    PopUpTableDlg ptd = new PopUpTableDlg(cmis.getMain(),"Process Also Modified by:");
    ptd.buildTable(dataV);
    CenterComponent.centerCompOnScreen(ptd);
    ptd.setVisible(true);
  }

  void processTable_mouseClicked(MouseEvent e) {
    int col = ((JTable)e.getSource()).columnAtPoint(new Point(e.getX(),e.getY()));
    int row = ((JTable)e.getSource()).rowAtPoint(new Point(e.getX(),e.getY()));
    //stop editing
    if (processTable.isEditing()){
      processTable.getCellEditor().stopCellEditing();
    }
    model.setEditableFlag(0);
    //lock by info
    String tmpL = (String) processTable.getModel().getValueAt(row,processLockCol);
    if ("Locked".equalsIgnoreCase(tmpL) && col == processLockCol && e.getClickCount() > 1) {
      GenericDlg.showMessage((String)processTable.getModel().getValueAt(row,lockInfoCol));
      return;
    }
    //process is on multiple requests
    tmpL = (String) processTable.getModel().getValueAt(row,multipleRequestCol);
    if ("Y".equalsIgnoreCase(tmpL) && col == processLockCol && e.getClickCount() > 1) {
      buildMultipleRequestDisplay((Vector) processTable.getModel().getValueAt(row,multipleRequestDataCol));
      return;
    }
    //don't allow user to do any if editable flag is not set
    int editF = 0;
    tmpL = (String) processTable.getModel().getValueAt(row,processLockCol);
    if (!"Editable".equalsIgnoreCase(tmpL) && col > processLockCol) {
      if(col == descCol){
        PopUpEditDlg edlg = new PopUpEditDlg(cmis.getMain(),"Process Description",true, "Process Description:",false);
        String tmp = (String)processTable.getModel().getValueAt(row,processDescCol);
        if(!BothHelpObjs.isBlankString(tmp))edlg.setText(tmp);
        edlg.setVisible(true);
        edlg.dispose();
        return;
      }else if (col == noteCol) {
        PopUpEditDlg edlg = new PopUpEditDlg(cmis.getMain(),"Process Notes",true, "Process Notes:",false);
        String tmp = (String)processTable.getModel().getValueAt(row,processNoteCol);
        if(!BothHelpObjs.isBlankString(tmp))edlg.setText(tmp);
        edlg.setVisible(true);
        edlg.dispose();
        return;
      }else {
        if ("Locked".equalsIgnoreCase(tmpL)) {
          GenericDlg.showMessage("Selected process is locked.\nDouble click on 'Locked' for more detail.");
        }else {
          GenericDlg.showMessage("To start changing this process.\nClick on 'Editable' column to select the process\nand click on 'Request Process Change'");
        }
        model.setEditableFlag(editF);
        return;
      }
    }else {
      if (!"Viewer".equalsIgnoreCase(viewLevel)) {
        editF = BothHelpObjs.mypow(2,processNameCol) + BothHelpObjs.mypow(2,statusCol) + BothHelpObjs.mypow(2,whereCol);
        editF += BothHelpObjs.mypow(2,dischargeCol) + BothHelpObjs.mypow(2,freqCol) + BothHelpObjs.mypow(2,freqUnitCol);
        editF += BothHelpObjs.mypow(2,timeCol) + BothHelpObjs.mypow(2,timeUnitCol);
      }
      model.setEditableFlag(editF);
    }

    //if right mouse clicked
    if (e.isMetaDown()) {
      setSelectedRow(row);
      if (col == processNameCol) {
        DeleteProcessMetaPopUp dmpu;
        if ("T".equals((String)processTable.getModel().getValueAt(row,newCol))) {
          dmpu = new DeleteProcessMetaPopUp(cmis,processTable,row,true);
        }else {
          dmpu = new DeleteProcessMetaPopUp(cmis,processTable,row,false);
        }
        this.add(dmpu);
        dmpu.show(processTable,e.getX(),e.getY());
        processTable.repaint();
        return;
      }
    }

    if(col == descCol){
      PopUpEditDlg edlg = new PopUpEditDlg(cmis.getMain(),"Enter Process Description",true, "Process Description:",true);
      String tmp = (String)processTable.getModel().getValueAt(row,processDescCol);
      if(!BothHelpObjs.isBlankString(tmp))edlg.setText(tmp);
      edlg.setVisible(true);
      if(!BothHelpObjs.isBlankString(edlg.getText())) {
        processTable.getModel().setValueAt("       +     ",row,col);
        processTable.getModel().setValueAt(edlg.getText(),row,processDescCol);
      }else{
        processTable.getModel().setValueAt("",row,col);
        processTable.getModel().setValueAt("",row,processDescCol);
      }
      edlg.dispose();
    }else if(col == noteCol){
      PopUpEditDlg edlg = new PopUpEditDlg(cmis.getMain(),"Enter Process Notes",true, "Process Notes:",true);
      String tmp = (String)processTable.getModel().getValueAt(row,processNoteCol);
      if(!BothHelpObjs.isBlankString(tmp))edlg.setText(tmp);
      edlg.setVisible(true);
      if(!BothHelpObjs.isBlankString(edlg.getText())) {
        processTable.getModel().setValueAt("       +     ",row,col);
        processTable.getModel().setValueAt(edlg.getText(),row,processNoteCol);
      }else{
        processTable.getModel().setValueAt("",row,col);
        processTable.getModel().setValueAt("",row,processNoteCol);
      }
      edlg.dispose();
    }else if(col == addWhereCol) {
      CreateDlg cdlg = new CreateDlg(cmis.getMain(),"Creating a New Operating Location",true, "New Operating Location (30 chars max):");
      cdlg.setAuditInput(true,30);
      cdlg.setVisible(true);
      if (!cdlg.cancelled) {
        if (!BothHelpObjs.isBlankString(cdlg.getText())) {
          String newLoc = cdlg.getText();
          for (int i = 0; i < processTable.getRowCount(); i++) {
            JComboBox tmpC = (JComboBox) processTable.getModel().getValueAt(i,whereCol);
            tmpC.addItem(newLoc);
            processTable.getModel().setValueAt(tmpC, i, whereCol);
          }
        }
      }
      cdlg.dispose();
    }else if(col == addDischargeCol) {
      CreateDlg cdlg = new CreateDlg(cmis.getMain(),"Creating a Discharge Location",true, "New Discharge Location (30 chars max):");
      cdlg.setAuditInput(true,30);
      cdlg.setVisible(true);
      if (!cdlg.cancelled) {
        if(!BothHelpObjs.isBlankString(cdlg.getText())) {
          String newLoc = cdlg.getText();
          for (int i = 0; i < processTable.getRowCount(); i++ ) {
            JComboBox tmpC = (JComboBox) processTable.getModel().getValueAt(i,dischargeCol);
            tmpC.addItem(newLoc);
            processTable.getModel().setValueAt(tmpC, i, dischargeCol);
          }
        }
      }
      cdlg.dispose();
    }
    processTable.repaint();
  } //end of method

  String auditData() {
    String msg = "OK";
    if (processTable.isEditing()){
      processTable.getCellEditor().stopCellEditing();
    }
    //Required at least one PROCESS
    if (processTable.getRowCount() < 1 ) {
      msg = "Please define at least one Process for the selected work area.";
      return msg;
    }
    //steping thru table to make sure that user enter a process name
    for (int i = 0; i < processTable.getRowCount(); i++) {
      String tmp = (String) processTable.getModel().getValueAt(i,processNameCol);
      if (tmp.length() < 1) {
        msg = "Please enter a Process Name.";
        setSelectedRow(i);
        break;
      }
    }
    return msg;
  }

  void saveCurrentData() {
    //stop editing
    if (processTable.isEditing()){
      processTable.getCellEditor().stopCellEditing();
    }
    //step thru data and put into data structure
    int i = 0;
    Vector tmpProcessIDV = new Vector(processTable.getRowCount());   //this will temporary keep track of what process is currently display on table
    for (i = 0; i < processTable.getRowCount(); i++) {
      String tmpID = (String) processTable.getModel().getValueAt(i,processCol);
      tmpProcessIDV.addElement(tmpID);
      Hashtable h = (Hashtable) currentFacWorkAreaInfo.get(tmpID);
      Vector operationDefV = (Vector) h.get("OPERATION_DEFINITION");
      for (int j = 0; j < operationDefV.size(); j++) {
        Hashtable innerH = (Hashtable) operationDefV.elementAt(j);
        innerH.put("PROCESS_ID",tmpID);
        innerH.put("PROCESS_STATUS",((JComboBox) processTable.getModel().getValueAt(i,statusCol)).getSelectedItem().toString());
        innerH.put("PROCESS_STATION_TYPE",((JComboBox) processTable.getModel().getValueAt(i,whereCol)).getSelectedItem().toString());
        innerH.put("PROCESS_DISCHARGE_PATH",((JComboBox) processTable.getModel().getValueAt(i,dischargeCol)).getSelectedItem().toString());
        innerH.put("OPERATING_FREQUENCY",(String) processTable.getModel().getValueAt(i,freqCol));
        innerH.put("OPERATING_FREQUENCY_UNIT",((JComboBox) processTable.getModel().getValueAt(i,freqUnitCol)).getSelectedItem().toString());
        innerH.put("OPERATING_TIME",(String) processTable.getModel().getValueAt(i,timeCol));
        innerH.put("OPERATING_TIME_UNIT",((JComboBox) processTable.getModel().getValueAt(i,timeUnitCol)).getSelectedItem().toString());
        innerH.put("PROCESS_NAME",(String) processTable.getModel().getValueAt(i,processNameCol));
        innerH.put("PROCESS_DESC",(String) processTable.getModel().getValueAt(i,processDescCol));
        innerH.put("PROCESS_NOTE",(String) processTable.getModel().getValueAt(i,processNoteCol));
        innerH.put("NEW_PROCESS",(String) processTable.getModel().getValueAt(i,newCol));
      } //end of inner loop
    } //end of outer loop stepping thru the table
    //now remove all process that is not in table
    for (i = 0; i < currentProcessIDV.size(); i++) {
      String tmp = (String) currentProcessIDV.elementAt(i);
      if (!tmpProcessIDV.contains(tmp)) {
        currentFacWorkAreaInfo.remove(tmp);
        currentProcessIDV.remove(tmp);
      }
    }
  } //end of method

  void buildTable() {
    tableJsp.getViewport().removeAll();
    String[] colHeaders = {"ID","Editable?","Name","Description","Status","Where","`","Discharge"," ","Frequency","Freq. Units","Oper. Time","Time Units","Process Desc","New","Lock Info","Multiple Request","Multiple Request Data","Notes","Process Notes"};
    processCol = 0;
    processLockCol = 1;
    processNameCol = 2;
    descCol = 3;
    statusCol = 4;
    whereCol = 5;
    addWhereCol = 6;
    dischargeCol = 7;
    addDischargeCol = 8;
    freqCol = 9;
    freqUnitCol = 10;
    timeCol = 11;
    timeUnitCol = 12;
    processDescCol = 13;
    newCol = 14;
    lockInfoCol = 15;
    multipleRequestCol = 16;
    multipleRequestDataCol = 17;
    noteCol = 18;
    processNoteCol = 19;
    int[] colWidths = {0,80,100,60,70,100,21,130,21,80,80,80,60,0,0,0,0,0,60,0};
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
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING};
    model = new AttributiveCellTableModel(colHeaders,500); //length of table can be set by server
    int i = 0;
    for (i = model.getRowCount()-1; i >= 0; i--) {
      model.removeRow(i);
    }
    int editF = 0;
    /*
    if (!"Viewer".equalsIgnoreCase(viewLevel)) {
      editF = BothHelpObjs.mypow(2,processNameCol) + BothHelpObjs.mypow(2,statusCol) + BothHelpObjs.mypow(2,whereCol);
      editF += BothHelpObjs.mypow(2,dischargeCol) + BothHelpObjs.mypow(2,freqCol) + BothHelpObjs.mypow(2,freqUnitCol);
      editF += BothHelpObjs.mypow(2,timeCol) + BothHelpObjs.mypow(2,timeUnitCol);
    }*/
    model.setEditableFlag(editF);
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
    processTable = new MultiSpanCellTable(model);
    processTable.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    processTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    processTable.getTableHeader().setResizingAllowed(true);
    processTable.setIntercellSpacing(new Dimension(0,0));
    processTable.addMouseListener(new ProcessTable_mouseAdapter(this));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = processTable.getColumnModel();
    for (i = 0; i < processTable.getColumnCount(); i++) {
      if (i == statusCol || i == whereCol || i == dischargeCol || i == freqUnitCol || i == timeUnitCol ) {
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
    processTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = processTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    processTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<processTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      processTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      processTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        processTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        processTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    tableJsp.getViewport().add(processTable);
  }  //end of method

  void buildTableNotUsed() {
    String[] colHeaders = {"ID","Process","Status","Where","Discharge","Frequency","Freq. Units","Oper. Time","Time Units","Process Desc"};
    int[] colWidths = {50,100,100,100,100,100,100,100,100,100,30};
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
                      BothHelpObjs.TABLE_COL_TYPE_STRING};

    CmisTableModel dtm = new CmisTableModel(colHeaders);
    dtm.setEditableFlag(8);
    JTable myTable = null;
    myTable = new JTable(dtm);
    myTable.setCellSelectionEnabled(true);
    myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    myTable.setDefaultRenderer(JComboBox.class,new JComboBoxCellRenderer());
    myTable.setDefaultEditor(JComboBox.class,new JComboBoxCellEditor(new JComboBox()));
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    myTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = myTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(int i=0;i<myTable.getColumnCount();i++){
      if (colWidths[i]==0){
         myTable.getColumn(myTable.getColumnName(i)).setWidth(colWidths[i]);
         myTable.getColumn(myTable.getColumnName(i)).setMaxWidth(colWidths[i]);
         myTable.getColumn(myTable.getColumnName(i)).setMinWidth(colWidths[i]);
      }
      myTable.getColumn(myTable.getColumnName(i)).setPreferredWidth(colWidths[i]);
    }

    tableJsp.getViewport().add(myTable);
  }

  void reqEditB_actionPerformed(ActionEvent e) {
    if ("Viewer".equalsIgnoreCase(viewLevel)) {
      reqEditB.setEnabled(false);
      return;
    }
    System.out.println("------------ here in req process change");
    if (processTable.isEditing()){
      processTable.getCellEditor().stopCellEditing();
    }
    int selectedRows = processTable.getSelectedRowCount();
    if (selectedRows < 1) {
      GenericDlg.showMessage("Please select at least one process.");
      return;
    }
    Hashtable h = new Hashtable(selectedRows);
    int[] selRows = processTable.getSelectedRows();
    for (int i = 0; i < selectedRows; i++) {
      h.put(new Integer(selRows[i]),(String)processTable.getModel().getValueAt(selRows[i],this.processCol));
    }
    goRequestProcessChange(h);
  } //end of method

  void goRequestProcessChange(Hashtable h) {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj", cmis);
    Hashtable query = new Hashtable(6);
    query.put("ACTION", "REQUEST_PROCESS_CHANGE"); //String, String
    query.put("FACILITY_ID", currentFacID);
    query.put("WORK_AREA_ID", this.currentWorkAreaID);
    query.put("VIEW_LEVEL",viewLevel);
    query.put("USER_ID",cmis.getUserId());
    query.put("PROCESS",h);
    Hashtable result = cgi.getResultHash(query);
    if (result == null) {
      String no = new String("Access denied. Your session is corrupted, please restart the application.");
      GenericDlg err = new GenericDlg(cmis.getMain(), "Denied", true);
      err.setMsg(no);
      err.setVisible(true);
      CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
      return;
    }
    Hashtable tmpH = (Hashtable) result.get("REQ_INFO");
    if (!"OK".equalsIgnoreCase((String) tmpH.get("MSG"))) {
      GenericDlg.showMessage((String) tmpH.get("MSG"));
    }else {
      goAllowProcessChange((Hashtable) tmpH.get("DATA"));
    }
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
  } //end of method

  void goAllowProcessChange(Hashtable h) {
    Enumeration enum1;
    boolean startTimer = false;
    for (enum1 = h.keys(); enum1.hasMoreElements();){
      Integer k = (Integer) enum1.nextElement();
      Hashtable hv = (Hashtable) h.get(k);
      if ("Editable".equalsIgnoreCase((String)hv.get("EDITABLE"))){
        processTable.getModel().setValueAt("Editable",k.intValue(),this.processLockCol);
        if (!currentEditableProcess.contains((String)hv.get("PROCESS_ID"))) {
          currentEditableProcess.addElement((String)hv.get("PROCESS_ID"));
        }
        startTimer = true;
      }else {
        processTable.getModel().setValueAt("Locked",k.intValue(),this.processLockCol);
        processTable.getModel().setValueAt((String)hv.get("PROCESS_LOCK_INFO"),k.intValue(),this.lockInfoCol);
      }
    }
    processTable.repaint();
    //Start timer
    if ("Approver".equalsIgnoreCase(viewLevel) || "Super Approver".equalsIgnoreCase(viewLevel) ||
        "New Chem Approver".equalsIgnoreCase(viewLevel) || "Eng Eval Approver".equalsIgnoreCase(viewLevel)) {
      if (startTimer) {
        apprPane.startTimer();
      }
    }
  } //end of method

  void addB_actionPerformed(ActionEvent e) {
    if ("Viewer".equalsIgnoreCase(viewLevel)) {
      addB.setEnabled(false);
      return;
    }
    Vector operationDefV = new Vector(1);
    Hashtable innerH = new Hashtable(18);
    currentMaxProcessID = new Integer(currentMaxProcessID.intValue()+1);
    innerH.put("PROCESS_ID",currentMaxProcessID.toString());
    innerH.put("PROCESS_LOCK","Editable");
    innerH.put("PROCESS_LOCK_INFO","Editable");
    innerH.put("PROCESS_STATUS","New");
    innerH.put("PROCESS_STATION_TYPE","");
    innerH.put("PROCESS_DISCHARGE_PATH","No Discharge");
    innerH.put("OPERATING_FREQUENCY","");
    innerH.put("OPERATING_FREQUENCY_UNIT","");
    innerH.put("OPERATING_TIME","");
    innerH.put("OPERATING_TIME_UNIT","");
    innerH.put("PROCESS_NAME","");
    innerH.put("PROCESS_DESC","");
    innerH.put("PROCESS_NOTE","");
    innerH.put("NEW_PROCESS","T");
    innerH.put("PPE_COMMENTS","");
    innerH.put("WASTE_COMMENTS","");
    innerH.put("TRAINING_COMMENTS","");
    innerH.put("PROCESS_ON_MULTI_REQUEST","");
    innerH.put("MULTIPLE_REQUEST_DATA",new Vector(0));
    operationDefV.addElement(innerH);
    //update current data
    currentProcessIDV.addElement(currentMaxProcessID.toString());
    currentFacWorkAreaInfo.put("MAX_PROCESS_ID",currentMaxProcessID);
    Hashtable h = new Hashtable(6);
    h.put("OPERATION_DEFINITION",operationDefV);
    h.put("EMISSION_POINT",new Vector(0));
    h.put("MATERIAL_USED",new Vector(0));
    h.put("WASTE",new Vector(0));
    h.put("TRAINING",new Vector(0));
    h.put("PPE",new Vector(0));
    currentFacWorkAreaInfo.put(currentMaxProcessID.toString(),h);
    buildTableData(operationDefV);
  }  //end of method
} //end of class

class OperationDefinitionLoadThread extends Thread {
  OperationDefinition parent;
  boolean runF = true;

  public OperationDefinitionLoadThread (OperationDefinition parent){
     super("OperationDefinitionLoadThread");
     this.parent = parent;
  }

  public void run()
  {
    parent.loadItAction();
  }
}

class OperationDefinition_addB_actionAdapter implements java.awt.event.ActionListener {
  OperationDefinition adaptee;

  OperationDefinition_addB_actionAdapter(OperationDefinition adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.addB_actionPerformed(e);
  }
}

class OperationDefinition_reqEditB_actionAdapter implements java.awt.event.ActionListener {
  OperationDefinition adaptee;

  OperationDefinition_reqEditB_actionAdapter(OperationDefinition adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.reqEditB_actionPerformed(e);
  }
}


class ProcessTable_mouseAdapter extends java.awt.event.MouseAdapter {
  OperationDefinition adaptee;

  ProcessTable_mouseAdapter(OperationDefinition adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.processTable_mouseClicked(e);
  }

  public void mouseReleased(MouseEvent e) {
    adaptee.processTable_mouseReleased(e);
  }
}



