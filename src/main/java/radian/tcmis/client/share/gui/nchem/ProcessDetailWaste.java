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

public class ProcessDetailWaste extends JPanel {
  CmisApp cmis;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton addB = new JButton();
  JScrollPane tableJsp = new JScrollPane();
  JComboBox processC = new JComboBox();
  boolean firstTime = true;
  AttributiveCellTableModel model;
  MultiSpanCellTable wasteTable;
  String viewLevel = "";
  String currentFacID = "";
  String currentWorkAreaID = "";
  String currentFacDesc = "";
  String currentWorkAreaDesc = "";
  Hashtable currentFacWorkAreaInfo;
  Vector currentProcessIDV;
  WasteComboBoxItemListener cbil;
  String lastSelectedProcessID = "";
  int wasteIDCol = 0;
  int wasteStreamCol = 0;
  int wasteContainerCol = 0;
  int wasteCodeCol = 0;
  int newCol = 0;
  String requestID = "";
  StaticJLabel commentL = new StaticJLabel();
  JTextArea commentTextArea = new JTextArea();
  JScrollPane textAreaJsp = new JScrollPane();
  Vector currentEditableProcess;

  public ProcessDetailWaste( CmisApp cmis )
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
    ProcessDetailWasteLoadThread x=new ProcessDetailWasteLoadThread( this );
    x.start();
  }

  void jbInit() throws Exception {
    ImageIcon ss=ResourceLoader.getImageIcon( "images/button/add.gif" );
    processC.setMaximumSize(new Dimension(250, 25));
    processC.setMinimumSize(new Dimension(250, 25));
    processC.setPreferredSize(new Dimension(250, 25));
    cbil = new WasteComboBoxItemListener(this);
    addB.setMaximumSize(new Dimension(200, 25));
    addB.setMinimumSize(new Dimension(200, 25));
    addB.setPreferredSize(new Dimension(200, 25));
    addB.setIcon( ss );
    addB.setText("Add/Remove Waste Stream");
    addB.addActionListener(new ProcessDetailWaste_addB_actionAdapter(this));
    tableJsp.setMaximumSize(new Dimension(690, 288));
    tableJsp.setMinimumSize(new Dimension(690, 288));
    tableJsp.setPreferredSize(new Dimension(690, 288));
    this.add(processC, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(addB, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(tableJsp,  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    commentL.setText("Comments: ");
    commentL.setMaximumSize(new Dimension(680, 15));
    commentL.setMinimumSize(new Dimension(680, 15));
    commentL.setPreferredSize(new Dimension(680, 15));
    textAreaJsp.setMaximumSize(new Dimension(690, 95));
    textAreaJsp.setMinimumSize(new Dimension(690, 95));
    textAreaJsp.setPreferredSize(new Dimension(690, 95));
    commentTextArea.setLineWrap(true);
    commentTextArea.setWrapStyleWord(true);
    commentTextArea.setBorder(ClientHelpObjs.groupBox(""));
    this.add(commentL,  new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.BOTH, new Insets(0, 10, 0, 0), 0, 0));
    this.add(textAreaJsp,  new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    textAreaJsp.getViewport().add(commentTextArea);
    this.validate();
  }

  void loadItAction() {
    System.out.println( "Here" );
  }

  void buildTable() {
    tableJsp.getViewport().removeAll();
    String[] colHeaders = {"Waste ID","Waste Stream","Container","Waste Code","New"};
    wasteIDCol = 0;
    wasteStreamCol = 1;
    wasteContainerCol = 2;
    wasteCodeCol = 3;
    newCol = 4;
    int[] colWidths = {0,425,160,100,0};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
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
    /*
    if (!"Viewer".equalsIgnoreCase(viewLevel)) {
      editF = BothHelpObjs.mypow(2,wasteContainerCol) + BothHelpObjs.mypow(2,wasteCodeCol);
    }*/
    model.setEditableFlag(editF);
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
    wasteTable = new MultiSpanCellTable(model);
    wasteTable.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    wasteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    wasteTable.getTableHeader().setResizingAllowed(true);
    wasteTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = wasteTable.getColumnModel();
    for (i = 0; i < wasteTable.getColumnCount(); i++) {
      if (i == wasteContainerCol) {
        cm.getColumn(i).setCellRenderer(new JComboBoxCellRenderer());
        cm.getColumn(i).setCellEditor(new JComboBoxCellEditor(new JComboBox()));
      }else {
        cm.getColumn(i).setCellRenderer(renderers[0]);
      }
    }
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    wasteTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = wasteTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    wasteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<wasteTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      wasteTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      wasteTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        wasteTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        wasteTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    //wasteTable.addMouseListener(new SearchPT_basketTable_mouseAdapter(this));
    tableJsp.getViewport().add(wasteTable);
  }  //end of method

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
        buildTableData((Vector) tmpProcessData.get("WASTE"));
        Vector operationDefV = (Vector) tmpProcessData.get("OPERATION_DEFINITION");
        for (int j = 0; j < operationDefV.size(); j++) {
          Hashtable innerH = (Hashtable) operationDefV.elementAt(j);
          commentTextArea.setText((String) innerH.get("WASTE_COMMENTS"));
        }
      }else { //end of if process is in hashtable
        commentTextArea.setText("");
      }
    }else {
      commentTextArea.setText("");
    }
    lastSelectedProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
    if (currentEditableProcess.contains(lastSelectedProcessID)) {
      // allow requestor to add another process
      if ("Viewer".equalsIgnoreCase(viewLevel) ||
          "Select a Process".equalsIgnoreCase( (String) processC.getSelectedItem())) {
        addB.setEnabled(false);
        commentTextArea.setEnabled(false);
        model.setEditableFlag(0);
      }else {
        addB.setEnabled(true);
        commentTextArea.setEnabled(true);
        int editF = 0;
        if (!"Viewer".equalsIgnoreCase(viewLevel)) {
          editF = BothHelpObjs.mypow(2,wasteContainerCol) + BothHelpObjs.mypow(2,wasteCodeCol);
        }
        model.setEditableFlag(editF);
      }
    }else {
      addB.setEnabled(false);
      commentTextArea.setEnabled(false);
      model.setEditableFlag(0);
    }
  } //end of method

  JComboBox getComboDetail(String s) {
    Hashtable currentStaticData = (Hashtable) this.currentFacWorkAreaInfo.get("PROCESS_STATIC_DATA");
    Vector descV = (Vector) currentStaticData.get("WASTE_CONTAINER_DESC");
    Vector IDV = (Vector) currentStaticData.get("WASTE_CONTAINER_IDS");
    JComboBox j = new JComboBox();
    j = ClientHelpObjs.loadChoiceFromVector(j,descV);
    int tmpIndex = IDV.indexOf(s);
    if (tmpIndex >= 0) {
      j.setSelectedIndex(IDV.indexOf(s));
    }else {
      j.setSelectedIndex(0);
    }
    j.setEditable(false);
    j.setMaximumRowCount(descV.size());
    j.revalidate();
    return j;
  }

  String getContainerIDFromDesc(String s) {
    Hashtable currentStaticData = (Hashtable) this.currentFacWorkAreaInfo.get("PROCESS_STATIC_DATA");
    Vector descV = (Vector) currentStaticData.get("WASTE_CONTAINER_DESC");
    Vector IDV = (Vector) currentStaticData.get("WASTE_CONTAINER_IDS");
    return ((String)IDV.elementAt(descV.indexOf(s)));
  }

  String getContainerDescFromID(String s) {
    Hashtable currentStaticData = (Hashtable) this.currentFacWorkAreaInfo.get("PROCESS_STATIC_DATA");
    Vector descV = (Vector) currentStaticData.get("WASTE_CONTAINER_DESC");
    Vector IDV = (Vector) currentStaticData.get("WASTE_CONTAINER_IDS");
    return ((String)descV.elementAt(IDV.indexOf(s)));
  }

  void saveCurrentData() {
    //stop editing
    if (wasteTable.isEditing()){
      wasteTable.getCellEditor().stopCellEditing();
    }
    //step thru data and put into data structure
    int i = 0;
    String currentProcessID = (String) currentProcessIDV.elementAt(currentProcessIDV.indexOf(lastSelectedProcessID));
    if (currentFacWorkAreaInfo.containsKey(currentProcessID)) {
      Hashtable h = (Hashtable) currentFacWorkAreaInfo.get(currentProcessID);
      Vector mtlUsedV = (Vector) h.get("WASTE");
      //first remove data from vector then update it with current display data
      mtlUsedV.removeAllElements();
      for (i = 0; i < wasteTable.getRowCount(); i++) {
        Object[] oa = new Object[wasteTable.getColumnCount()];
        int j = 0;
        oa[j++] = (String) wasteTable.getModel().getValueAt(i,this.wasteIDCol);
        oa[j++] = (String) wasteTable.getModel().getValueAt(i,this.wasteStreamCol);
        oa[j++] = getContainerIDFromDesc(((JComboBox) wasteTable.getModel().getValueAt(i,wasteContainerCol)).getSelectedItem().toString());
        oa[j++] = (String) wasteTable.getModel().getValueAt(i,this.wasteCodeCol);
        oa[j++] = (String) wasteTable.getModel().getValueAt(i,this.newCol);
        mtlUsedV.addElement(oa);
      } //end of outer loop stepping thru the table
      Vector operationDefV = (Vector) h.get("OPERATION_DEFINITION");
      for (int j = 0; j < operationDefV.size(); j++) {
        Hashtable innerH = (Hashtable) operationDefV.elementAt(j);
        innerH.put("WASTE_COMMENTS", commentTextArea.getText().trim());
      }
    } //end of data structure contains current selected process
  } //end of method


  String auditData() {
    //stop editing
    if (wasteTable.isEditing()){
      wasteTable.getCellEditor().stopCellEditing();
    }
    String result = "OK";
    for (int i = 0; i < wasteTable.getRowCount(); i++) {
      String tmp = (String)wasteTable.getModel().getValueAt(i,wasteCodeCol);
      if (tmp.length() > 30) {
        setSelectedRow(i);
        result = "Waste Code can't be more than 30 characters.";
      }
    } //end of stepping thru table
    //comments is too long 2000 chars max.
    if (commentTextArea.getText().length() >= 2000) {
      result = "Comments cannot contain more than 2000 characters.";
    }
    return result;
  } //end of method

  void addB_actionPerformed(ActionEvent e) {
    //stop editing
    if (wasteTable.isEditing()){
      wasteTable.getCellEditor().stopCellEditing();
    }
    EditProcessDetaildlg epddlg = new EditProcessDetaildlg(cmis.getMain(),"Add/Remove Waste Stream for "+currentFacDesc+"/"+currentWorkAreaDesc,
                                                           "Waste",currentFacID,currentWorkAreaID,(String)currentProcessIDV.elementAt(processC.getSelectedIndex()),(String)processC.getSelectedItem());
    epddlg.setParent(cmis);
    epddlg.setRequestID(requestID);
    //need to find out current emission points for selected process
    Hashtable tmpDataH = new Hashtable(wasteTable.getRowCount());
    for (int k = 0; k < wasteTable.getRowCount(); k++) {
      String key = (String)wasteTable.getModel().getValueAt(k,wasteIDCol);
      String[] oa = new String[2];
      oa[0] = getContainerIDFromDesc(((JComboBox) wasteTable.getModel().getValueAt(k,wasteContainerCol)).getSelectedItem().toString());
      oa[1] = (String)wasteTable.getModel().getValueAt(k,wasteCodeCol);
      tmpDataH.put(key,oa);
    }
    epddlg.setSelectedData(tmpDataH);
    epddlg.loadIt();
    if (epddlg.loadDataOk) {
      CenterComponent.centerCompOnScreen(epddlg);
      epddlg.setVisible(true);
      if (!epddlg.cancelled) {
        //first remove data from table
        for (int j = wasteTable.getRowCount() -1; j >= 0; j--) {
          AttributiveCellTableModel model = (AttributiveCellTableModel)wasteTable.getModel();
          model.removeRow(j);
        }
        //now update table from user's selection
        Vector v = epddlg.updateDataV;
        for (int i = 0; i < v.size(); i++) {
          Object[] oa = (Object[]) v.elementAt(i);
          //add combobox for container type
          oa[wasteContainerCol] = getComboDetail(oa[wasteContainerCol].toString());
          addRowToTable(wasteTable,oa);
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
        AttributiveCellTableModel model = (AttributiveCellTableModel)wasteTable.getModel();
        for (int i = wasteTable.getRowCount() -1; i >= 0; i--) {
          model.removeRow(i);
        }
        //RELOAD table from data
        String tmpProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
        if (currentFacWorkAreaInfo.containsKey(tmpProcessID)) {
          Hashtable tmpProcessData = (Hashtable) currentFacWorkAreaInfo.get(tmpProcessID);
          buildTableData((Vector) tmpProcessData.get("WASTE"));
          Vector operationDefV = (Vector) tmpProcessData.get("OPERATION_DEFINITION");
          for (int j = 0; j < operationDefV.size(); j++) {
            Hashtable innerH = (Hashtable) operationDefV.elementAt(j);
            commentTextArea.setText((String) innerH.get("WASTE_COMMENTS"));
          }
        }else { //end of if process is in hashtable
          commentTextArea.setText("");
        }
        if (currentEditableProcess.contains(tmpProcessID)) {
          if ("Viewer".equalsIgnoreCase(viewLevel)) {
            addB.setEnabled(false);
            commentTextArea.setEnabled(false);
            model.setEditableFlag(0);
          }else {
            addB.setEnabled(true);
            commentTextArea.setEnabled(true);
            int editF = 0;
            if (!"Viewer".equalsIgnoreCase(viewLevel)) {
              editF = BothHelpObjs.mypow(2,wasteContainerCol) + BothHelpObjs.mypow(2,wasteCodeCol);
            }
            model.setEditableFlag(editF);
          }
        }else {
          addB.setEnabled(false);
          commentTextArea.setEnabled(false);
          model.setEditableFlag(0);
        }
      }else {
        //empty table
        AttributiveCellTableModel model = (AttributiveCellTableModel)wasteTable.getModel();
        for (int i = wasteTable.getRowCount() -1; i >= 0; i--) {
          model.removeRow(i);
        }
        addB.setEnabled(false);
        commentTextArea.setText("");
        commentTextArea.setEnabled(false);
      }
      lastSelectedProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
    }
  } //end of process

  void buildTableData(Vector materialUsedData) {
    for (int i = 0; i < materialUsedData.size(); i++) {
      Object[] oa = (Object[]) materialUsedData.elementAt(i);
      String tmp = (String) oa[0];
      //shipping blank part used
      tmp = tmp.trim();
      if (tmp.length() < 1) {
        continue;
      }
      //add combobox for container type
      oa[wasteContainerCol] = getComboDetail(oa[wasteContainerCol].toString());
      addRowToTable(wasteTable,oa);
    }
  }

  void addRowToTable(MultiSpanCellTable myTable,Object[] data) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    int row = model.getRowCount();
    model.insertRow(row,data);
    //alternate color
    int cond = row % 2;
    if (cond == 0) {
       int[] rows = new int[]{row};
       for (int j = 0; j < model.getColumnCount(); j++) {
             int[] cols = new int[]{j};
             cellAtt.setBackground(new Color(224,226,250),rows,cols);
        }
    }
    wasteTable.repaint();
  }

  void setSelectedRow(int x){
    ListSelectionModel lsm = wasteTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    wasteTable.scrollRectToVisible(wasteTable.getCellRect(x,0,false));
  } //end of method

} //end of class

class ProcessDetailWasteLoadThread extends Thread {
  ProcessDetailWaste parent;
  boolean runF = true;

  public ProcessDetailWasteLoadThread (ProcessDetailWaste parent){
     super("ProcessDetailWasteLoadThread");
     this.parent = parent;
  }

  public void run()
  {
    parent.loadItAction();
  }
}

class WasteComboBoxItemListener implements java.awt.event.ItemListener {
  ProcessDetailWaste adaptee;

  WasteComboBoxItemListener(ProcessDetailWaste adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.processC_itemStateChanged(e);
  }
}

class ProcessDetailWaste_addB_actionAdapter implements java.awt.event.ActionListener {
  ProcessDetailWaste adaptee;

  ProcessDetailWaste_addB_actionAdapter(ProcessDetailWaste adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.addB_actionPerformed(e);
  }
}

