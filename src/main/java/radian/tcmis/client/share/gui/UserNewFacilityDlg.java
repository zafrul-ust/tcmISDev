package radian.tcmis.client.share.gui;

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
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class UserNewFacilityDlg extends JDialog {
  private CmisApp cmis ;
  private CmisTable editTable;
  private TableSorter sorter = new TableSorter();

  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout4 = new BorderLayout();
  private JScrollPane tableJSP = new JScrollPane();
  private JPanel jPanel6 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JPanel jPanel7 = new JPanel();
  private BorderLayout borderLayout5 = new BorderLayout();
  private JPanel bottomP = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();

  private JButton cancelB = new JButton();
  private JButton okB = new JButton();
  private StaticJLabel noteL = new StaticJLabel();
  private boolean loadDataOk = true;
  private boolean cancelled = false;
  private Vector facilityIDV;
  private Hashtable dataH;
  private Hashtable newFacilityList = new Hashtable();
  private Hashtable facilityActiveList = new Hashtable();

  public UserNewFacilityDlg(JFrame fr, String title, Vector facIDV) {
    super(fr, title, true);
    this.facilityIDV = facIDV;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(500, 400));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }


  public void setParent(CmisApp c){
    cmis = c;
  }

  public Hashtable getNewFacilityList() {
    return newFacilityList;
  }

  public Hashtable getFacilityActiveList() {
    return facilityActiveList;
  }

  public Hashtable getWorkAreaData() {
    return dataH;
  }

  public boolean getLoaded() {
    return loadDataOk;
  }

  public boolean getCancelled() {
    return cancelled;
  }

  public void loadIt(){
    loadItAction();
  }

  void jbInit() throws Exception {
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
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
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    editTable = new CmisTable();
    tableJSP.getViewport().setView(editTable);
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
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);
    noteL.setText("Note - To add/remove facilities to/from list. Click on row.");
    jPanel6.add(noteL, new GridBagConstraints2(0, 0, 0, 0, 0.0, 0.0
           ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 15, 5, 5), 0, 0));
     jPanel6.add(jPanel7, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
           ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(40, 15, 50, 15), 0, 0));
    jPanel7.add(tableJSP, BorderLayout.CENTER);
    jPanel6.add(bottomP, new GridBagConstraints2(1, 1, 0, 0, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(okB, BorderLayout.WEST);
    bottomP.add(cancelB, BorderLayout.EAST);
  }

  void loadItAction() {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("UserProfileNew",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","LOAD_FACILITY_DATA"); //String, String
      query.put("USER_ID",cmis.getUserId());    //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showMessage("tcmIS encounter a problem while loading data.\nPlease save your current data.\nIf problem persist restart tcmIS and try again.");
        ClientHelpObjs.setEnabledContainer(this,true);
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        loadDataOk = false;
        return;
      }

      tableJSP.getViewport().remove(editTable);
      dataH = (Hashtable)result.get("DATA");
      if (!"OK".equalsIgnoreCase((String)result.get("MSG"))) {
        GenericDlg.showMessage((String)result.get("MSG"));
        loadDataOk = false;
        return;
      }

      DbTableModel ctm = buildTableModel();
      sorter = new TableSorter(ctm);
      editTable = new CmisTable(sorter);
      sorter.setColTypeFlag(ctm.getColumnTypesString());
      sorter.addMouseListenerToHeaderInTable(editTable);
      editTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      editTable.addMouseListener(new NewFacility_Edit_mouseAdapter(this));

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

      tableJSP.getViewport().setView(editTable);
      if (editTable.getModel().getRowCount()<=0){
        GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
        g.setMsg("No Records found.");
        g.setVisible(true);
        loadDataOk = false;
        return;
      }
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

  DbTableModel buildTableModel() {
    String[] colHeads = {"ID","Name","Selected","Active"};
    int[] colWidths = {140,305,0,0};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING};
    Vector data = (Vector) dataH.get("FACILITY_DATA");
    DbTableModel ctm = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      Object[] oa = (Object[]) data.elementAt(i);
      if (facilityIDV.contains((String)oa[0])) {
        oa[2] = new Boolean(true);
      }
      ctm.addRow(oa);
    }
    ctm.setColumnPrefWidth(colWidths);
    ctm.setColumnType(colTypes);
    return ctm;
  }  //end of method

  boolean auditTableOk() {
    boolean val = false;
    //make sure the at least one facility is in the list
    for (int i = 0; i < editTable.getRowCount(); i++) {
      Boolean selected = (Boolean) editTable.getModel().getValueAt(i,2);
      if (selected.booleanValue()) {
        val = true;
        break;
      }
    }
    return val;
  }

  void okB_actionPerformed(ActionEvent e) {
    //check to make sure that there is at least work area selected
    if (!auditTableOk()) {
      GenericDlg.showMessage("Please select at least one facility.");
      return;
    }
    updateFacilityList();
    cancelled = false;
    setVisible(false);
  }

  void updateFacilityList() {
    if (newFacilityList != null) {
      newFacilityList = null;
      facilityActiveList = null;
    }
    newFacilityList = new Hashtable(editTable.getRowCount());
    facilityActiveList = new Hashtable(editTable.getRowCount());
    for (int i = 0; i < editTable.getRowCount(); i++) {
      Boolean selected = (Boolean) editTable.getModel().getValueAt(i,2);
      if (selected.booleanValue()) {
        newFacilityList.put((String)editTable.getModel().getValueAt(i,0),(String)editTable.getModel().getValueAt(i,1));  //key ID - data DESC
        facilityActiveList.put((String)editTable.getModel().getValueAt(i,0),(String)editTable.getModel().getValueAt(i,3));  //key ID - data Active
      }
    }
  } //end of method

  void editTable_mouseClicked(MouseEvent e) {
    int selectedCol = 2;
    int[] rows   = editTable.getSelectedRows();
    for (int i = 0; i < rows.length; i++) {
      Boolean selected = (Boolean) editTable.getModel().getValueAt(rows[i],selectedCol);
      if (selected.booleanValue()) {
        editTable.getModel().setValueAt(new Boolean(false),rows[i],selectedCol);
      }else {
        editTable.getModel().setValueAt(new Boolean(true),rows[i],selectedCol);
      }
    }
    editTable.repaint();
  } //end of method

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    cancelled = true;
    setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    cancelled = true;
    setVisible(false);
  } //end of method
} //end of class

class NewFacility_Edit_mouseAdapter extends java.awt.event.MouseAdapter {
  UserNewFacilityDlg adaptee;
  NewFacility_Edit_mouseAdapter(UserNewFacilityDlg adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.editTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}
