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
import radian.tcmis.both1.helpers.*;
import javax.swing.table.*;
import java.awt.event.*;

public class EmissionPoint extends JPanel {
  CmisApp cmis;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton addB = new JButton();
  JScrollPane tableJsp = new JScrollPane();
  JComboBox processC = new JComboBox();
  boolean firstTime = true;
  AttributiveCellTableModel model;
  MultiSpanCellTable emissionPtTable;
  int appCol = 0;
  int facCol = 0;
  int nameCol = 0;
  int descCol = 0;
  int percentCol = 0;
  int newCol = 0;
  String viewLevel = "";
  String currentFacID = "";
  String currentWorkAreaID = "";
  String currentFacDesc = "";
  String currentWorkAreaDesc = "";
  Hashtable currentFacWorkAreaInfo;
  Vector currentProcessIDV;
  ComboBoxItemListener cbil;
  String lastSelectedProcessID = "";
  String requestID = "";
  Vector currentEditableProcess;

  public EmissionPoint( CmisApp cmis )
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
    EmissionPointLoadThread x=new EmissionPointLoadThread( this );
    x.start();
  }

  void jbInit() throws Exception {
    ImageIcon ss=ResourceLoader.getImageIcon( "images/button/add.gif" );
    processC.setMaximumSize(new Dimension(250, 25));
    processC.setMinimumSize(new Dimension(250, 25));
    processC.setPreferredSize(new Dimension(250, 25));
    cbil = new ComboBoxItemListener(this);
    addB.setMaximumSize(new Dimension(210, 25));
    addB.setMinimumSize(new Dimension(210, 25));
    addB.setPreferredSize(new Dimension(210, 25));
    addB.setIcon( ss );
    addB.setText("Add/Remove Emission Point");
    addB.addActionListener(new EmissionPoint_addB_actionAdapter(this));
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

  String auditData() {
    //stop editing
    if (emissionPtTable.isEditing()){
      emissionPtTable.getCellEditor().stopCellEditing();
    }
    String result = "OK";
    float perSum = 0;
    for (int i = 0; i < emissionPtTable.getRowCount(); i++) {
      try {
        perSum += Float.parseFloat((String)emissionPtTable.getModel().getValueAt(i,percentCol));
      }catch (Exception e) {
        setSelectedRow(i);
        result = "Percentage must be a number.";
      }
    } //end of stepping thru table
    if (emissionPtTable.getRowCount() > 0) {
      if (perSum != 100) {
        result = "The sum of the percentage must equal to 100.";
      }
    }
    return result;
  }

  void saveCurrentData() {
    //stop editing
    if (emissionPtTable.isEditing()){
      emissionPtTable.getCellEditor().stopCellEditing();
    }
    //step thru data and put into data structure
    int i = 0;
    String currentProcessID = (String) currentProcessIDV.elementAt(currentProcessIDV.indexOf(lastSelectedProcessID));
    if (currentFacWorkAreaInfo.containsKey(currentProcessID)) {
      Hashtable h = (Hashtable) currentFacWorkAreaInfo.get(currentProcessID);
      Vector emissionPtV = (Vector) h.get("EMISSION_POINT");
      //first remove data from vector then update it with current display data
      emissionPtV.removeAllElements();
      for (i = 0; i < emissionPtTable.getRowCount(); i++) {
        Object[] oa = new Object[emissionPtTable.getColumnCount()];
        int j = 0;
        oa[j++] = (String) emissionPtTable.getModel().getValueAt(i,this.appCol);
        oa[j++] = (String) emissionPtTable.getModel().getValueAt(i,this.facCol);
        oa[j++] = (String) emissionPtTable.getModel().getValueAt(i,this.nameCol);
        oa[j++] = (String) emissionPtTable.getModel().getValueAt(i,this.descCol);
        oa[j++] = (String) emissionPtTable.getModel().getValueAt(i,this.percentCol);
        emissionPtV.addElement(oa);
      } //end of outer loop stepping thru the table
    } //end of data structure contains current selected process
  } //end of method

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
        //save display data
        saveCurrentData();
        //next delete row from table and reload it from data
        //empty table
        AttributiveCellTableModel model = (AttributiveCellTableModel)emissionPtTable.getModel();
        for (int i = emissionPtTable.getRowCount() - 1; i >= 0 ; i--) {
          model.removeRow(i);
        }
        //RELOAD table from data
        String tmpProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
        if (currentFacWorkAreaInfo.containsKey(tmpProcessID)) {
          Hashtable tmpProcessData = (Hashtable) currentFacWorkAreaInfo.get(tmpProcessID);
          buildTableData((Vector) tmpProcessData.get("EMISSION_POINT"));
        } //end of if process is in hashtable
        if (currentEditableProcess.contains(tmpProcessID)) {
          if ("Viewer".equalsIgnoreCase(viewLevel)) {
            addB.setEnabled(false);
            model.setEditableFlag(0);
          }else {
            addB.setEnabled(true);
            int editF = 0;
            if (!"Viewer".equalsIgnoreCase(viewLevel)) {
              editF = BothHelpObjs.mypow(2,percentCol);
            }
            model.setEditableFlag(editF);
          }
        }else {
          addB.setEnabled(false);
          model.setEditableFlag(0);
        }
      }else {
        //empty table
        AttributiveCellTableModel model = (AttributiveCellTableModel)emissionPtTable.getModel();
        for (int i = emissionPtTable.getRowCount() - 1; i >= 0 ; i--) {
          model.removeRow(i);
        }
        addB.setEnabled(false);
      }
      lastSelectedProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
    }
  } //end of process

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
        buildTableData((Vector) tmpProcessData.get("EMISSION_POINT"));
      } //end of if process is in hashtable
    }
    lastSelectedProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
    if (currentEditableProcess.contains(lastSelectedProcessID)) {
      // allow requestor to add another process
      if ("Viewer".equalsIgnoreCase(viewLevel) || "Select a Process".equalsIgnoreCase( (String) processC.getSelectedItem())) {
        addB.setEnabled(false);
        model.setEditableFlag(0);
      }else {
        addB.setEnabled(true);
        int editF = 0;
        if (!"Viewer".equalsIgnoreCase(viewLevel)) {
          editF = BothHelpObjs.mypow(2,percentCol);
        }
        model.setEditableFlag(editF);
      }
    }else {
      addB.setEnabled(false);
      model.setEditableFlag(0);
    }
  } //end of method

  void buildTableData(Vector emissionData) {
    for (int i = 0; i < emissionData.size(); i++) {
      Object[] oa = (Object[]) emissionData.elementAt(i);
      String tmp = (String) oa[0];
      //shipping blank emission point
      tmp = tmp.trim();
      if (tmp.length() < 1) {
        continue;
      }
      addRowToTable(emissionPtTable,oa);
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
    emissionPtTable.repaint();
  }

  void setSelectedRow(int x){
    ListSelectionModel lsm = emissionPtTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    emissionPtTable.scrollRectToVisible(emissionPtTable.getCellRect(x,0,false));
  }


  void buildTable() {
    tableJsp.getViewport().removeAll();
    String[] colHeaders = {"App","Fac","Name","Description","Percent %","New"};
    appCol = 0;
    facCol = 1;
    nameCol = 2;
    descCol = 3;
    percentCol = 4;
    newCol = 5;
    int[] colWidths = {0,0,190,390,100,0};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
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
    /*
    if (!"Viewer".equalsIgnoreCase(viewLevel)) {
      editF = BothHelpObjs.mypow(2,percentCol);
    }*/
    model.setEditableFlag(editF);
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
    emissionPtTable = new MultiSpanCellTable(model);
    emissionPtTable.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    emissionPtTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    emissionPtTable.getTableHeader().setResizingAllowed(true);
    emissionPtTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = emissionPtTable.getColumnModel();
    for (i = 0; i < emissionPtTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    emissionPtTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = emissionPtTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    emissionPtTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<emissionPtTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      emissionPtTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      emissionPtTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        emissionPtTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        emissionPtTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    //emissionPtTable.addMouseListener(new SearchPT_basketTable_mouseAdapter(this));
    tableJsp.getViewport().add(emissionPtTable);
  }

  void addB_actionPerformed(ActionEvent e) {
    //stop editing
    if (emissionPtTable.isEditing()){
      emissionPtTable.getCellEditor().stopCellEditing();
    }
    EditProcessDetaildlg epddlg = new EditProcessDetaildlg(cmis.getMain(),"Add/Remove Emission Point for "+currentFacDesc+"/"+currentWorkAreaDesc,
                                                           "Emission Point",currentFacID,currentWorkAreaID,(String)currentProcessIDV.elementAt(processC.getSelectedIndex()),(String)processC.getSelectedItem());
    epddlg.setParent(cmis);
    epddlg.setRequestID(requestID);
    //need to find out current emission points for selected process
    Hashtable tmpDataH = new Hashtable(emissionPtTable.getRowCount());
    for (int k = 0; k < emissionPtTable.getRowCount(); k++) {
      String key = (String)emissionPtTable.getModel().getValueAt(k,appCol)+(String)emissionPtTable.getModel().getValueAt(k,facCol);
      String[] oa = new String[1];
      oa[0] = (String)emissionPtTable.getModel().getValueAt(k,percentCol);
      tmpDataH.put(key,oa);
    }
    epddlg.setSelectedData(tmpDataH);
    epddlg.loadIt();
    if (epddlg.loadDataOk) {
      CenterComponent.centerCompOnScreen(epddlg);
      epddlg.setVisible(true);
      if (!epddlg.cancelled) {
        //first remove data from table
        for (int j = emissionPtTable.getRowCount() -1 ; j >= 0; j--) {
          AttributiveCellTableModel model = (AttributiveCellTableModel)emissionPtTable.getModel();
          model.removeRow(j);
        }
        //now update table from user's selection
        Vector v = epddlg.updateDataV;
        for (int i = 0; i < v.size(); i++) {
          Object[] oa = (Object[]) v.elementAt(i);
          addRowToTable(emissionPtTable,oa);
        }
      }
    }
    epddlg.dispose();
  }  //end of method

} //end of class

class EmissionPointLoadThread extends Thread {
  EmissionPoint parent;
  boolean runF = true;

  public EmissionPointLoadThread (EmissionPoint parent){
     super("EmissionPointLoadThread");
     this.parent = parent;
  }

  public void run()
  {
    parent.loadItAction();
  }
}

class ComboBoxItemListener implements java.awt.event.ItemListener {
  EmissionPoint adaptee;

  ComboBoxItemListener(EmissionPoint adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.processC_itemStateChanged(e);
  }
}

class EmissionPoint_addB_actionAdapter implements java.awt.event.ActionListener {
  EmissionPoint adaptee;

  EmissionPoint_addB_actionAdapter(EmissionPoint adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.addB_actionPerformed(e);
  }
}


