
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.nchem;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.gui.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.beans.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.XYLayout;

import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class ApprDetail extends JDialog {
  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JScrollPane appScrollPane = new JScrollPane();
  JButton okB = new JButton();

  String requestId = "";
  Frame parent;
  CmisApp grandParent;

  ApprDetail_ApprDetailLoadThread adlt;
  XYLayout xYLayout2 = new XYLayout();
  JScrollPane matScrollPane = new JScrollPane();
  JPanel jPanel3 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  DataJLabel reqIdL = new DataJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  DataJLabel dateL = new DataJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  DataJLabel requestorL = new DataJLabel();
  StaticJLabel jLabel7 = new StaticJLabel();
  DataJLabel facL = new DataJLabel();
  StaticJLabel jLabel9 = new StaticJLabel();
  DataJLabel statusL = new DataJLabel();
  StaticJLabel jLabel11 = new StaticJLabel();
  DataJLabel workAreaL = new DataJLabel();

  ImageIcon submit = null;
  ImageIcon reject = null;
  JButton rejectB = new JButton();

  // load from resource bundle
  String WORK_AREA = "Work Area";
  String WORK_AREAS = "Work Areas";
  JButton cancelB = new JButton();
  JPanel bottomP = new JPanel();
  StaticJLabel jLabel2 = new StaticJLabel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JPanel middleP = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  MultiSpanCellTable materialTable;
  MultiSpanCellTable approvalDetailTable;
  JButton naB = new JButton();

  public ApprDetail(Frame frame, String title, boolean modal,CmisApp cmis) {
    super(frame, title, modal);
    grandParent = cmis;
    WORK_AREA = grandParent.getResourceBundle().getString("WORK_AREA_NAME");
    WORK_AREAS = grandParent.getResourceBundle().getString("WORK_AREA_NAME_PLURAL");
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setRequestId(String id){
    requestId = id;
  }

  void jbInit() throws Exception {
    this.setResizable(true);
    panel1.setLayout(gridBagLayout3);
    jPanel1.setMinimumSize(new Dimension(530, 175));
    jPanel1.setPreferredSize(new Dimension(530, 175));
    jPanel1.setLayout(xYLayout2);
    okB.setText("Ok");
    okB.setMaximumSize(new Dimension(95, 25));
    okB.setMinimumSize(new Dimension(95, 25));
    okB.setPreferredSize(new Dimension(95, 25));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    submit = ResourceLoader.getImageIcon("images/button/submit.gif");
    rejectB.setIcon(ResourceLoader.getImageIcon("images/button/minus.gif"));
    naB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));

    jLabel1.setText("Request ID:");
    jLabel1.setHorizontalAlignment(4);
    jLabel3.setText("Request Date:");
    jLabel3.setHorizontalAlignment(4);
    jLabel5.setText("Requestor:");
    jLabel5.setHorizontalAlignment(4);
    jLabel7.setText("Status:");
    jLabel7.setHorizontalAlignment(4);
    jLabel9.setText("Facility:");
    jLabel9.setHorizontalAlignment(4);
    jLabel11.setText(WORK_AREA+":");
    jLabel11.setHorizontalAlignment(4);
    jPanel3.setLayout(gridBagLayout1);
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    panel1.setMaximumSize(new Dimension(536, 450));
    panel1.setMinimumSize(new Dimension(536, 450));
    panel1.setPreferredSize(new Dimension(536, 450));
    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel2.gif"));
    cancelB.setVisible(false);
    cancelB.setMaximumSize(new Dimension(95, 25));
    cancelB.setMinimumSize(new Dimension(95, 25));
    cancelB.setPreferredSize(new Dimension(95, 25));
    rejectB.setMaximumSize(new Dimension(95, 25));
    rejectB.setMinimumSize(new Dimension(95, 25));
    rejectB.setPreferredSize(new Dimension(95, 25));
    rejectB.setText("Reject");
    rejectB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rejectB_actionPerformed(e);
      }
    });
    rejectB.setVisible(false);
    naB.setMaximumSize(new Dimension(95, 25));
    naB.setMinimumSize(new Dimension(95, 25));
    naB.setPreferredSize(new Dimension(95, 25));
    naB.setText("N/A");
    naB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        naB_actionPerformed(e);
      }
    });
    naB.setVisible(false);

    jLabel2.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel2.setText("Note: Select row(s) that you want to approve/reject.");
    jLabel2.setVisible(false);
    bottomP.setLayout(gridBagLayout2);
    middleP.setLayout(borderLayout1);
    middleP.setMinimumSize(new Dimension(536, 180));
    middleP.setPreferredSize(new Dimension(260, 180));
    bottomP.setMinimumSize(new Dimension(536, 40));
    bottomP.setPreferredSize(new Dimension(536, 40));
    getContentPane().add(panel1);
    panel1.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(matScrollPane, new XYConstraints(0, 81, 530, 100));
    jPanel1.add(jPanel3, new XYConstraints(0, 0, 530, 80));
    jPanel3.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(4, 0, 4, 3), 0, 0));
    jPanel3.add(reqIdL, new GridBagConstraints2(1, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 150, 0));
    jPanel3.add(jLabel3, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 3), 0, 0));
    jPanel3.add(dateL, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 3, 4, 0), 100, 0));
    jPanel3.add(jLabel5, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(4, 0, 4, 3), 0, 0));
    jPanel3.add(requestorL, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 0, 0));
    jPanel3.add(jLabel7, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(4, 0, 4, 3), 0, 0));
    jPanel3.add(facL, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 3, 4, 0), 0, 0));
    jPanel3.add(jLabel9, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 3), 0, 0));
    jPanel3.add(statusL, new GridBagConstraints2(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 0, 0));
    jPanel3.add(jLabel11, new GridBagConstraints2(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 3), 0, 0));
    jPanel3.add(workAreaL, new GridBagConstraints2(3, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4, 3, 4, 0), 0, 0));
    panel1.add(bottomP, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    bottomP.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 0), 0, 0));
    bottomP.add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(okB, null);
    jPanel2.add(rejectB, null);
    jPanel2.add(naB, null);
    jPanel2.add(cancelB, null);
    panel1.add(middleP, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    middleP.add(appScrollPane, BorderLayout.CENTER);

    buildApprovalTable(new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector());
    buildMatTable(new Vector());

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    this.pack();
    CenterComponent.centerCompOnScreen(this);

  }

  public void loadIt(){
    adlt = new ApprDetail_ApprDetailLoadThread(this);
    adlt.start();
  }

  public void loadScreen() {

    CursorChange.setCursor(grandParent.getMain(),Cursor.WAIT_CURSOR);

    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.NEW_CHEM_TRACKING,grandParent);
    Hashtable query = new Hashtable();
    query.put("ACTION","APPROVAL_DETAIL"); //String, String
    query.put("REQ_ID",requestId); //String, String

    Hashtable result = cgi.getResultHash(query);
    if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(grandParent.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
         CursorChange.setCursor(this.getContentPane(),Cursor.DEFAULT_CURSOR);
         return;
    }

    // load the request info
    Vector v1 = (Vector) result.get("DATA");
    reqIdL.setText(requestId);
    requestorL.setText(v1.elementAt(0).toString());
    dateL.setText(v1.elementAt(1).toString());
    statusL.setText(v1.elementAt(2).toString());
    facL.setText(v1.elementAt(3).toString());
    if(v1.elementAt(4).toString().equals("")){
      workAreaL.setText("All "+WORK_AREAS);
    }else{
      workAreaL.setText(v1.elementAt(4).toString());
    }

    // load the material info
    Vector v2 = (Vector) result.get("MATERIAL_TABLE_DATA");
    buildMatTable(v2);

    // load the approval info
    Vector roles = (Vector) result.get("ROLES");
    Vector stat = (Vector) result.get("STATUS");
    Vector approvers = (Vector) result.get("APPROVERS");
    Vector dates = (Vector) result.get("DATES");
    Vector reasons = (Vector) result.get("REASONS");
    Vector approvalGroup = (Vector) result.get("APPROVAL_GROUP");
    buildApprovalTable(roles,stat,approvers,dates,reasons,approvalGroup);

    CursorChange.setCursor(grandParent.getMain(),Cursor.DEFAULT_CURSOR);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

  void buildApprovalTable(Vector roles,Vector stat,Vector approvers,Vector dates,Vector reasons,Vector approvalGroup){
    String[] colNames = {"Approval Role","Status","Chemical Approvers","Date","Comments"};
    int[] colType = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                     BothHelpObjs.TABLE_COL_TYPE_STRING,
                     BothHelpObjs.TABLE_COL_TYPE_STRING,
                     BothHelpObjs.TABLE_COL_TYPE_STRING,
                     BothHelpObjs.TABLE_COL_TYPE_STRING};
    int[] colWidth = {160,90,160,90,200};
    AttributiveCellTableModel model = new AttributiveCellTableModel(colNames,approvers.size());
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    //remove row - perpare for coloring of alternate lines
    for (int k = approvers.size() -1; k >= 0; k--){
      model.removeRow(k);
    }

    int i=0;
    boolean colorRow = false;
    String lastApprovalGroup = "";
    for (i = 0; i < approvers.size();i++){
      Object[] oa = new Object[colNames.length];
      oa[0] = (String)roles.elementAt(i);
      oa[1] = (String)stat.elementAt(i);
      oa[2] = (String)approvers.elementAt(i);
      oa[3] = (String)dates.elementAt(i);
      oa[4] = (String)reasons.elementAt(i);
      model.addRow(oa);

      if (!lastApprovalGroup.equals((String)approvalGroup.elementAt(i))) {
        if (colorRow) {
          colorRow = false;
        }else {
          colorRow = true;
        }
      }else {
        if (colorRow) {
          colorRow = true;
        }else {
          colorRow = false;
        }
      }
      //color alternate approval group
      if (colorRow) {
        int[] rows = new int[]{i};
        for (int k = 0; k < model.getColumnCount(); k++) {
          int[] cols = new int[]{k};
          cellAtt.setBackground(new Color(224,226,250),rows,cols);
        }
      }
      lastApprovalGroup = (String)approvalGroup.elementAt(i);
    }
    model.setColumnPrefWidth(colWidth);
    model.setColumnType(colType);
    approvalDetailTable = new MultiSpanCellTable(model);
    approvalDetailTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    approvalDetailTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable catStyle = grandParent.getMain().search.getCatTableStyle();
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = approvalDetailTable.getColumnModel();
    for (i = 0; i < approvalDetailTable.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    approvalDetailTable.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = approvalDetailTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    approvalDetailTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<approvalDetailTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      approvalDetailTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      approvalDetailTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        approvalDetailTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        approvalDetailTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }

    middleP.remove(appScrollPane);
    appScrollPane = new JScrollPane(approvalDetailTable);
    middleP.add(appScrollPane,BorderLayout.CENTER);
    middleP.validate();
  }

  void buildMatTable(Vector v){
    String[] colNames = {"Material Desc.","Packaging","Manufacturer"};
    int[] colType = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                     BothHelpObjs.TABLE_COL_TYPE_STRING,
                     BothHelpObjs.TABLE_COL_TYPE_STRING};
    int[] colWidth = {223,160,200};
    AttributiveCellTableModel model = new AttributiveCellTableModel(colNames,v.size());
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    //remove row - perpare for coloring of alternate lines
    for (int k = v.size() -1; k >= 0; k--){
      model.removeRow(k);
    }
    int i=0;
    for (i = 0;i < v.size();i++){
      String[] oa = (String[])v.elementAt(i);
      model.addRow(oa);
      //color alternate row
      if (i % 2 == 0) {
        int[] rows = new int[]{i};
        for (int k = 0; k < model.getColumnCount(); k++) {
          int[] cols = new int[]{k};
          cellAtt.setBackground(new Color(224,226,250),rows,cols);
        }
      }
    }
    model.setColumnPrefWidth(colWidth);
    model.setColumnType(colType);
    materialTable = new MultiSpanCellTable(model);
    materialTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    materialTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable catStyle = grandParent.getMain().search.getCatTableStyle();
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = materialTable.getColumnModel();
    for (i = 0; i < materialTable.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    materialTable.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = materialTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    materialTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<materialTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      materialTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      materialTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        materialTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        materialTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    materialTable.revalidate();

    jPanel1.remove(matScrollPane);
    jPanel1.validate();
    matScrollPane = new JScrollPane(materialTable);
    jPanel1.add(matScrollPane, new XYConstraints(0, 81, 530, 100));
    jPanel1.validate();
  }

  void trackTable_mouseClicked(MouseEvent e) {

  }

  void cancelB_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  void rejectB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

  void naB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

}

class ApprDetail_ApprDetailLoadThread extends Thread {
 ApprDetail parent;

  public ApprDetail_ApprDetailLoadThread(ApprDetail parent){
     super("ApprDetailLoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadScreen();
  }
}

class ApprDetail_ApprTable_TableModel extends DefaultTableModel {

       public ApprDetail_ApprTable_TableModel(Object[][] data, Object[] columnIds) {
          super(data,columnIds);
       }
       public ApprDetail_ApprTable_TableModel(String[] columnIds) {
          super(columnIds,0);
       }
       public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
       }
       public boolean isCellEditable(int r, int c){
            return false;
       }
       public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue,row,column);
       }

}



class ApprDetail_ApprTable_trackTable_mouseAdapter extends java.awt.event.MouseAdapter {
  ApprDetail adaptee;

  ApprDetail_ApprTable_trackTable_mouseAdapter(ApprDetail adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    //adaptee.trackTable_mouseClicked(e);
  }
}
class ApprDetail_ApprT_TrackTable extends CmisTable {
   ApprDetail parent;
   public ApprDetail_ApprT_TrackTable(Object[][] o1, Object[] o2){
     super(o1,o2);
   }
   public ApprDetail_ApprT_TrackTable(TableModel t){
     super(t);
   }
   public ApprDetail_ApprT_TrackTable(){
     super();
   }
   public void setParent(ApprDetail p) {
     parent = p;
   }
   public void editingStopped(ChangeEvent e){
     if (getModel().getValueAt(getEditingRow(),getEditingColumn()) == null) {
       super.editingStopped(e);
       return;
     }
   }
}

class ApprDetail_CellRenderer extends DefaultTableCellRenderer {
  static final Color color1 = Color.lightGray;
  static final Color color2 = Color.white;


  public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {
     Object o = table.getModel().getValueAt(row,column);
     this.setValue(o.toString());
     this.setFont(new Font(this.getFont().getName(),this.getFont().PLAIN, this.getFont().getSize()));

     if(row == 0) {
       setBackground(color1);
       return this;
     }

     boolean on = true;
     Color current = color1;

     int j=0;
     for(int i=1;i<=row;i++){
       String s = table.getModel().getValueAt(i,0).toString();
       if(!BothHelpObjs.isBlankString(s)) {
         if(on){
           current = color2;
         }else{
           current = color1;
         }
         j++;
         on = !on;
       }
     }
     int r = j%2;
     setBackground(current);
     return this;
  }
}

