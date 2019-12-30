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

public class PPE extends JPanel {
  CmisApp cmis;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton addB = new JButton();
  JScrollPane tableJsp = new JScrollPane();
  JComboBox processC = new JComboBox();
  boolean firstTime = true;
  AttributiveCellTableModel model;
  MultiSpanCellTable ppeTable;
  String viewLevel = "";
  String currentFacID = "";
  String currentWorkAreaID = "";
  String currentFacDesc = "";
  String currentWorkAreaDesc = "";
  Hashtable currentFacWorkAreaInfo;
  Vector currentProcessIDV;
  PPEComboBoxItemListener cbil;
  String lastSelectedProcessID = "";
  int ppeCol = 0;
  int descCol = 0;
  int newCol = 0;
  String requestID = "";
  StaticJLabel commentL = new StaticJLabel();
  JTextArea commentTextArea = new JTextArea();
  JScrollPane textAreaJsp = new JScrollPane();
  Vector currentEditableProcess;

  public PPE( CmisApp cmis )
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
    PPELoadThread x=new PPELoadThread( this );
    x.start();
  }

  void jbInit() throws Exception {
    ImageIcon ss=ResourceLoader.getImageIcon( "images/button/add.gif" );
    processC.setMaximumSize(new Dimension(250, 25));
    processC.setMinimumSize(new Dimension(250, 25));
    processC.setPreferredSize(new Dimension(250, 25));
    cbil = new PPEComboBoxItemListener(this);
    addB.setMaximumSize(new Dimension(180, 25));
    addB.setMinimumSize(new Dimension(180, 25));
    addB.setPreferredSize(new Dimension(180, 25));
    addB.setIcon( ss );
    addB.setText("Add/Remove PPE");
    addB.addActionListener(new PPE_addB_actionAdapter(this));
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
    String[] colHeaders = {"PPE","Personal Protective Equipment","New"};
    ppeCol = 0;
    descCol = 1;
    newCol = 2;
    int[] colWidths = {0,685,0};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING};
    model = new AttributiveCellTableModel(colHeaders,100); //length of table can be set by server
    int i = 0;
    for (i = model.getRowCount()-1; i >= 0; i--) {
      model.removeRow(i);
    }
    Integer editF = new Integer(0);
    model.setEditableFlag(editF.intValue());
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
    ppeTable = new MultiSpanCellTable(model);
    ppeTable.setCellSelectionEnabled(false);                                //selecting the entire row upon mouse clicked
    ppeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    ppeTable.getTableHeader().setResizingAllowed(true);
    ppeTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = ppeTable.getColumnModel();
    for (i = 0; i < ppeTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    ppeTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = ppeTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    ppeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<ppeTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      ppeTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      ppeTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        ppeTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        ppeTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    tableJsp.getViewport().add(ppeTable);
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
        buildTableData((Vector) tmpProcessData.get("PPE"));
        Vector operationDefV = (Vector) tmpProcessData.get("OPERATION_DEFINITION");
        for (int j = 0; j < operationDefV.size(); j++) {
          Hashtable innerH = (Hashtable) operationDefV.elementAt(j);
          commentTextArea.setText((String) innerH.get("PPE_COMMENTS"));
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
      }else {
        addB.setEnabled(true);
        commentTextArea.setEnabled(true);
      }
    }else {
      addB.setEnabled(false);
      commentTextArea.setEnabled(false);
    }
  } //end of method

  void saveCurrentData() {
    //stop editing
    if (ppeTable.isEditing()){
      ppeTable.getCellEditor().stopCellEditing();
    }
    //step thru data and put into data structure
    int i = 0;
    String currentProcessID = (String) currentProcessIDV.elementAt(currentProcessIDV.indexOf(lastSelectedProcessID));
    if (currentFacWorkAreaInfo.containsKey(currentProcessID)) {
      Hashtable h = (Hashtable) currentFacWorkAreaInfo.get(currentProcessID);
      Vector ppeV = (Vector) h.get("PPE");
      //first remove data from vector then update it with current display data
      ppeV.removeAllElements();
      for (i = 0; i < ppeTable.getRowCount(); i++) {
        Object[] oa = new Object[ppeTable.getColumnCount()];
        int j = 0;
        oa[j++] = (String) ppeTable.getModel().getValueAt(i,this.ppeCol);
        oa[j++] = (String) ppeTable.getModel().getValueAt(i,this.descCol);
        oa[j++] = (String) ppeTable.getModel().getValueAt(i,this.newCol);
        ppeV.addElement(oa);
      } //end of outer loop stepping thru the table
      Vector operationDefV = (Vector) h.get("OPERATION_DEFINITION");
      for (int j = 0; j < operationDefV.size(); j++) {
        Hashtable innerH = (Hashtable) operationDefV.elementAt(j);
        innerH.put("PPE_COMMENTS", commentTextArea.getText().trim());
      }
    } //end of data structure contains current selected process
  } //end of method


  String auditData() {
    String result = "OK";
    //comments is too long 2000 chars max.
    if (commentTextArea.getText().length() >= 2000) {
      result = "Comments cannot contain more than 2000 characters.";
    }
    return result;
  }

  void addB_actionPerformed(ActionEvent e) {
    EditProcessDetaildlg epddlg = new EditProcessDetaildlg(cmis.getMain(),"Add/Remove PPE for "+currentFacDesc+"/"+currentWorkAreaDesc,
                                                           "PPE",currentFacID,currentWorkAreaID,(String)currentProcessIDV.elementAt(processC.getSelectedIndex()),(String)processC.getSelectedItem());
    epddlg.setParent(cmis);
    epddlg.setRequestID(requestID);
    //need to find out current emission points for selected process
    Hashtable tmpDataH = new Hashtable(ppeTable.getRowCount());
    for (int k = 0; k < ppeTable.getRowCount(); k++) {
      String key = (String)ppeTable.getModel().getValueAt(k,ppeCol);
      String[] oa = new String[0];
      tmpDataH.put(key,oa);
    }
    epddlg.setSelectedData(tmpDataH);
    epddlg.loadIt();
    if (epddlg.loadDataOk) {
      CenterComponent.centerCompOnScreen(epddlg);
      epddlg.setVisible(true);
      if (!epddlg.cancelled) {
        //first remove data from table
        for (int j = ppeTable.getRowCount() -1; j >= 0; j--) {
          AttributiveCellTableModel model = (AttributiveCellTableModel)ppeTable.getModel();
          model.removeRow(j);
        }
        //now update table from user's selection
        Vector v = epddlg.updateDataV;
        for (int i = 0; i < v.size(); i++) {
          Object[] oa = (Object[]) v.elementAt(i);
          addRowToTable(ppeTable,oa);
        }
      }
    }
    epddlg.dispose();
  }  //end of method

  void processC_itemStateChanged(ItemEvent e) {
    if (e.getStateChange() == e.SELECTED) {
      //now load data for selected process
      if (!"Select a Process".equalsIgnoreCase((String) processC.getSelectedItem())) {
        //first save display data
        saveCurrentData();
        //next delete row from table and reload it from data
        //empty table
        AttributiveCellTableModel model = (AttributiveCellTableModel)ppeTable.getModel();
        for (int i = ppeTable.getRowCount() -1; i >= 0; i--) {
          model.removeRow(i);
        }
        //RELOAD table from data
        String tmpProcessID = (String) currentProcessIDV.elementAt(processC.getSelectedIndex());
        if (currentFacWorkAreaInfo.containsKey(tmpProcessID)) {
          Hashtable tmpProcessData = (Hashtable) currentFacWorkAreaInfo.get(tmpProcessID);
          buildTableData((Vector) tmpProcessData.get("PPE"));
          Vector operationDefV = (Vector) tmpProcessData.get("OPERATION_DEFINITION");
          for (int j = 0; j < operationDefV.size(); j++) {
            Hashtable innerH = (Hashtable) operationDefV.elementAt(j);
            commentTextArea.setText((String) innerH.get("PPE_COMMENTS"));
          }
        }else { //end of if process is in hashtable
          commentTextArea.setText("");
        }
        if (currentEditableProcess.contains(tmpProcessID)) {
          if ("Viewer".equalsIgnoreCase(viewLevel)) {
            addB.setEnabled(false);
            commentTextArea.setEnabled(false);
          }else {
            addB.setEnabled(true);
            commentTextArea.setEnabled(true);
          }
        }else {
          addB.setEnabled(false);
          commentTextArea.setEnabled(false);
        }
      }else {
        //empty table
        AttributiveCellTableModel model = (AttributiveCellTableModel)ppeTable.getModel();
        for (int i = ppeTable.getRowCount() -1; i >= 0; i--) {
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
      addRowToTable(ppeTable,oa);
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
    ppeTable.repaint();
  }

  void setSelectedRow(int x){
    ListSelectionModel lsm = ppeTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    ppeTable.scrollRectToVisible(ppeTable.getCellRect(x,0,false));
  } //end of method

} //end of class

class PPELoadThread extends Thread {
  PPE parent;
  boolean runF = true;

  public PPELoadThread (PPE parent){
     super("PPELoadThread");
     this.parent = parent;
  }

  public void run()
  {
    parent.loadItAction();
  }
}

class PPEComboBoxItemListener implements java.awt.event.ItemListener {
  PPE adaptee;

  PPEComboBoxItemListener(PPE adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.processC_itemStateChanged(e);
  }
}

class PPE_addB_actionAdapter implements java.awt.event.ActionListener {
  PPE adaptee;

  PPE_addB_actionAdapter(PPE adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.addB_actionPerformed(e);
  }
}


