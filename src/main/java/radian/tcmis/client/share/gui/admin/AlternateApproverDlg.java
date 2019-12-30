//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.admin;

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
import radian.tcmis.client.share.gui.*;

public class AlternateApproverDlg extends JDialog {
  CmisApp cmis;
  String facilityID = "";
  String approverName = "";
  Integer approverID;

  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel bottomP = new JPanel();
  JPanel topP = new JPanel();
  XYLayout xYLayout2 = new XYLayout();
  JPanel textP = new JPanel();
  StaticJLabel facilityL = new StaticJLabel();
  DataJLabel facilityT = new DataJLabel();
  StaticJLabel approverNameL = new StaticJLabel();
  DataJLabel approverNameT = new DataJLabel();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jsp = new JScrollPane();

  JButton printB = new JButton();
  JButton okB = new JButton();
  JButton processB = new JButton();
  JButton newB = new JButton();

  AttributiveCellTableModel modelDetail;
  MultiSpanCellTable displayTable;
  String[] colHead = new String[]{"Last Name","First Name","Phone","Email","Delete","User ID"};
  final Long colTypeFlag = new Long("111111");
  final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING,
                          BothHelpObjs.TABLE_COL_TYPE_STRING};
  protected final int LAST_NAME_COL = 0;
  protected final int FIRST_NAME_COL = 1;
  protected final int PHONE_COL = 2;
  protected final int EMAIL_COL = 3;
  protected final int DELETE_COL = 4;
  protected final int ALTERNATE_APROVER_ID_COL = 5;
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public AlternateApproverDlg(CmisApp cmis, String facilityID, String approverName, Integer approverID) {
    super(cmis.getMain(), "Alternate Approver", true);
    this.cmis = cmis;
    this.facilityID = facilityID;
    this.approverName = approverName;
    this.approverID = approverID;
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
    newB.setMaximumSize(new Dimension(180, 25));
    newB.setMinimumSize(new Dimension(180, 25));
    newB.setPreferredSize(new Dimension(180, 25));
    newB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif"));

    this.setResizable(false);
    panel1.setLayout(xYLayout1);
    this.getContentPane().setSize(700, 400);
    xYLayout1.setHeight(420);
    xYLayout1.setWidth(700);
    okB.setText("Cancel");
    okB.setFont(approverNameL.getFont());
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    facilityT.setText(facilityID);
    approverNameT.setText(approverName);
    topP.setLayout(xYLayout2);
    textP.setLayout(gridBagLayout1);
    facilityL.setText("Facility:");
    approverNameL.setText("Approver:");
    jPanel1.setLayout(borderLayout1);
    printB.setText("Print");
    printB.setFont(approverNameL.getFont());
    printB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        printB_actionPerformed(e);
      }
    });
    processB.setText("Save Data");
    processB.setFont(approverNameL.getFont());
    processB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        processB_actionPerformed(e);
      }
    });
    newB.setText("New Alternate Approver");
    newB.setFont(approverNameL.getFont());
    newB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newB_actionPerformed(e);
      }
    });

    getContentPane().add(panel1);
    panel1.add(topP, new XYConstraints(10, 10, 700, 345));
    topP.add(textP, new XYConstraints(0, 0, 700, 80));
    panel1.add(bottomP, new XYConstraints(10, 366, 700, 45));
    textP.add(facilityL,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(facilityT,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(approverNameL,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    textP.add(approverNameT,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
          ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

    topP.add(jPanel1, new XYConstraints(10, 80, 660, 265));
    //bottom
    bottomP.add(processB,null);
    bottomP.add(newB,null);
    bottomP.add(printB, null);
    bottomP.add(okB, null);
  } //end of method

  void getData(){
    AlternateApproverDlgLoadThread c = new AlternateApproverDlgLoadThread(this);
    c.start();
  }

  void loadData(){
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),false);
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("AdminObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_ALTERNATE_APPROVER");
      query.put("FACILITY_ID",facilityID);      //String, String
      query.put("APPROVER_ID",approverID);      //String, Integer
      query.put("USER_ID",cmis.getUserId());    //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Boolean success = (Boolean)result.get("SUCCEEDED");
      if (success.booleanValue()) {
        Hashtable h = (Hashtable)result.get("ALTERNATE_APPROVERS");
        //first set header info
        facilityT.setText((String)h.get("FACILITY_NAME"));
        //now build table
        Vector dataV = (Vector) h.get("ALTERNATE_APPROVER_DATA");
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
    colWidths[LAST_NAME_COL] = 130;
    colWidths[FIRST_NAME_COL] = 130;
    colWidths[PHONE_COL] = 150;
    colWidths[EMAIL_COL] = 175;
    colWidths[DELETE_COL] = 75;
    colWidths[ALTERNATE_APROVER_ID_COL] = 0;

    modelDetail = new AttributiveCellTableModel(colHead,tableSize);
    for (int k = tableSize - 1; k >= 0; k--){
      modelDetail.removeRow(k);
    }
    CellSpan cellAtt = (CellSpan)modelDetail.getCellAttribute();
    ColoredCell cellColorAtt = (ColoredCell)modelDetail.getCellAttribute();
    for (int i = 0; i < dataV.size(); i++) {
      Object[] oa = new Object[colHead.length];
      Hashtable h = (Hashtable)dataV.elementAt(i);
      oa[LAST_NAME_COL] = (String)h.get("LAST_NAME");
      oa[FIRST_NAME_COL] = (String)h.get("FIRST_NAME");
      oa[PHONE_COL] = (String)h.get("PHONE");
      oa[EMAIL_COL] = (String)h.get("EMAIL");
      oa[DELETE_COL] = (Boolean)h.get("DELETE");
      oa[ALTERNATE_APROVER_ID_COL] = (Integer)h.get("ALTERNATE_APPROVER_ID");
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
    displayTable.addMouseListener(new AlternateApprover_mouseAdapter(this));
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

  void processB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    try{
      Vector dataV = getTableData();
      if (dataV.size() < 1) {
        GenericDlg.showMessage("No Data to process.");
        return;
      }
      TcmisHttpConnection cgi = new TcmisHttpConnection("AdminObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","PROCESS_ALTERNATE_APPROVER");
      query.put("USER_ID",cmis.getUserId());
      query.put("FACILITY_ID",facilityID);
      query.put("APPROVER_ID",approverID);
      query.put("ALTERNATE_APPROVER_DATA",dataV);
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
    //add new alternate approver to table
    SearchPersonnel dlg = new SearchPersonnel(cmis.getMain(),"Search",true);
    dlg.setGrandParent(cmis);
    dlg.setPersonType("PERSONNEL");
    dlg.setVisible(true);
    if (dlg.getValueId() != null){
      Object[] oa = new Object[displayTable.getColumnCount()];
      oa[LAST_NAME_COL] = dlg.getValueLast();
      oa[FIRST_NAME_COL] = dlg.getValueFirst();
      oa[PHONE_COL] = dlg.getPhone();
      oa[EMAIL_COL] = dlg.getEmail();
      oa[DELETE_COL] = new Boolean(false);
      oa[ALTERNATE_APROVER_ID_COL] = dlg.getValueId();
      addRowToTable(displayTable,oa);
    }
    dlg.dispose();
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
    highLightTable(displayTable,row,false);
  } //end of method

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

  void displayTable_mouseClicked(MouseEvent e) {
    int col = ((JTable)e.getSource()).columnAtPoint(new Point(e.getX(),e.getY()));
    int row = ((JTable)e.getSource()).rowAtPoint(new Point(e.getX(),e.getY()));
    if (col == DELETE_COL) {
      Boolean b = (Boolean)displayTable.getModel().getValueAt(row,col);
      if (b.booleanValue()) {
        displayTable.getModel().setValueAt(new Boolean(false), row, DELETE_COL);
        highLightTable(displayTable,row,false);
      }else {
        displayTable.getModel().setValueAt(new Boolean(true), row, DELETE_COL);
        highLightTable(displayTable,row,true);
      }
    }
  } //end of method

  Vector getTableData() {
    Vector dataV = new Vector(displayTable.getRowCount());
    for (int i = 0; i < displayTable.getRowCount(); i++) {
      Hashtable h = new Hashtable(displayTable.getRowCount());
      h.put("LAST_NAME",(String)displayTable.getModel().getValueAt(i,LAST_NAME_COL));
      h.put("FIRST_NAME",(String)displayTable.getModel().getValueAt(i,FIRST_NAME_COL));
      h.put("PHONE",(String)displayTable.getModel().getValueAt(i,PHONE_COL));
      h.put("EMAIL",(String)displayTable.getModel().getValueAt(i,EMAIL_COL));
      h.put("DELETE",(Boolean)displayTable.getModel().getValueAt(i,DELETE_COL));
      h.put("ALTERNATE_APPROVER_ID",(Integer)displayTable.getModel().getValueAt(i,ALTERNATE_APROVER_ID_COL));
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
        TcmisHttpConnection cgi = new TcmisHttpConnection("AdminObj",cmis);
        Hashtable query = new Hashtable();
        query.put("ACTION","PRINT_ALTERNATE_APPROVER");
        query.put("USER_ID",cmis.getUserId());
        query.put("FACILITY_DESC",facilityT.getText());
        query.put("APPROVER_NAME",approverNameT.getText());
        query.put("ALTERNATE_APPROVER_DATA",dataV);
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

class AlternateApproverDlgLoadThread extends Thread {
  AlternateApproverDlg parent;
  public AlternateApproverDlgLoadThread(AlternateApproverDlg parent){
     super("AlternateApproverDlgLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadData();
  }
}

class AlternateApprover_mouseAdapter extends java.awt.event.MouseAdapter {
  AlternateApproverDlg adaptee;
  AlternateApprover_mouseAdapter(AlternateApproverDlg adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.displayTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}



