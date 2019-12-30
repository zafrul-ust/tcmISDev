
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
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.GridBagConstraints2;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import java.awt.Component;
import java.beans.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class ApprDlg extends ApprDetail {

  Hashtable rolesToAppr = null;
  //ApprDlg_ApprT_TrackTable trackTable2 = null;
  MultiSpanCellTable approvalTable;
  NewChem matPane = null;
  String facility=null;
  CmisApp cmis;

  //trong 3/30
  boolean myapproved = true;
  public boolean getApproved(){
    return myapproved;
  }

  public ApprDlg(Frame frame, String title, boolean modal,CmisApp cmis) {
    super(frame, title, modal,cmis);
    this.cmis = cmis;
    okB.setText("Approve");
    okB.setIcon(submit);
    rejectB.setVisible(true);
    if ("CAL".equalsIgnoreCase(cmis.getResourceBundle().get("CLIENT_INITIALS").toString())) {
      naB.setVisible(true);
    }
    cancelB.setVisible(true);
    jLabel2.setVisible(true);
  }

  void setRolesToApprove(Hashtable rh){
      rolesToAppr = rh;
  }

  void setMatPane(NewChem p){
      this.matPane = p;
  }

  void setFacility(String p){
      this.facility = p;
  }

  void buildApprovalTable(Vector roles,Vector stat,Vector approvers,Vector dates,Vector reasons,Vector approvalGroup){
    if (rolesToAppr == null) return;
    String[] colNames = {"Approval Role","Comments"};
    int[] colType = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                     BothHelpObjs.TABLE_COL_TYPE_STRING};
    int[] colWidth = {150,373};
    AttributiveCellTableModel model = new AttributiveCellTableModel(colNames,rolesToAppr.size());
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    //remove row - perpare for coloring of alternate lines
    for (int k = rolesToAppr.size() -1; k >= 0; k--){
      model.removeRow(k);
    }

    int i=0;
    for (Enumeration E = rolesToAppr.keys();E.hasMoreElements();i++){
      Object[] oa = new Object[colNames.length];
      String approvalRole = E.nextElement().toString();
      Vector info = new Vector();
      info = (Vector) rolesToAppr.get(approvalRole);
      oa[0] = approvalRole;
      oa[1] = new String(info.elementAt(3).toString());
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
    model.setEditableFlag(2);
    approvalTable = new MultiSpanCellTable(model);
    approvalTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    approvalTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable catStyle = cmis.getMain().search.getCatTableStyle();
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = approvalTable.getColumnModel();
    for (i = 0; i < approvalTable.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    approvalTable.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = approvalTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    approvalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<approvalTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      approvalTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      approvalTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        approvalTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        approvalTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }
    middleP.remove(appScrollPane);
    appScrollPane = new JScrollPane(approvalTable);
    middleP.add(appScrollPane,BorderLayout.CENTER);
    middleP.validate();
  }

  void rejectB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    ConfirmNewDlg md = new ConfirmNewDlg(grandParent.getMain(),"Confirmation",true);
    md.setMsg("Rejecting this request will terminate it from all other Catalog Add processes.");
    md.setVisible(true);
    if(md.getConfirmationFlag()) {
      doApproval("Reject");
    }
  }

  void naB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    doApproval("NA");
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    doApproval("Approve");
  }

  void doApproval(String action){
    if (approvalTable.isEditing()) {
      approvalTable.getCellEditor().stopCellEditing();
    }

    if (approvalTable.getSelectedRowCount() < 1) {
      GenericDlg.showMessage("Please select row(s) that you wanted to approve/reject.");
      return;
    }

    CursorChange.setCursor(this.getContentPane(),Cursor.WAIT_CURSOR);
    boolean approving = false;
    String msg = new String("");
    String[] resultvalue = new String[2];
    Vector sendData = new Vector();
    int[] selectedRows = approvalTable.getSelectedRows();
    for (int i=0;i<selectedRows.length;i++){
      approving = true;
      String[] oa = new String[2];
      oa[0] = (String)approvalTable.getModel().getValueAt(selectedRows[i],0);       //approval role
      oa[1] = (String)approvalTable.getModel().getValueAt(selectedRows[i],1);       //comments
      sendData.addElement(oa);
    }

    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",grandParent);
      Hashtable query = new Hashtable();
      query.put("ACTION",action); //String, String
      query.put("USER_ID",(new Integer(grandParent.getUserId().intValue())).toString());
      query.put("REQ_ID",requestId);
      query.put("ROLES_DATA",sendData);
      query.put("FAC_ID",facility);
      //get data from new chem detail and save it (approver can change data)
      matPane.buildDataHash();
      query.put("MAT_DATA",matPane.matHash);
      query.put("PART_DATA",matPane.partHash);
      query.put("APPR_DATA",matPane.apprHash);
      query.put("EVAL_TYPE",matPane.eval);
      //Engineering evaluation request MR Info
      if (matPane.eval.equalsIgnoreCase("newEval") && matPane.evalB.isVisible()) {
        query.put("MR_INFO",matPane.evalMRP.buildData());
      }

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(grandParent.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }
      //String full_status = (String) result.get("FULL_STATUS");    //trong
      GenericDlg.showMessage((String) result.get("MESSAGE"));
      if (((String) result.get("MESSAGE")).equalsIgnoreCase("Request processed.")){
        myapproved = false;
        this.setVisible(false);
      }
    } catch (Exception e){
      e.printStackTrace();
      msg = e.getMessage();
    }
    CursorChange.setCursor(this.getContentPane(),Cursor.DEFAULT_CURSOR);
  }

}

class ApprDlg_ApprTable_TableModel extends DefaultTableModel {

       public ApprDlg_ApprTable_TableModel(Object[][] data, Object[] columnIds) {
          super(data,columnIds);
       }
       public ApprDlg_ApprTable_TableModel(String[] columnIds) {
          super(columnIds,0);
       }
       public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
       }
       public boolean isCellEditable(int r, int c){
            if (c>1 && c<5) return true;
            return false;
       }
       public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue,row,column);
       }

}


class ApprDlg_ApprT_TrackTable extends CmisTable {
   ApprDetail parent;
   public ApprDlg_ApprT_TrackTable(Object[][] o1, Object[] o2){
     super(o1,o2);
   }
   public ApprDlg_ApprT_TrackTable(TableModel t){
     super(t);
   }
   public ApprDlg_ApprT_TrackTable(){
     super();
   }
   public void setParent(ApprDlg p) {
     parent = p;
   }
   public void editingStopped(ChangeEvent e){
     super.editingStopped(e);
     /*
     if (getModel().getValueAt(getEditingRow(),getEditingColumn()) == null) {
       super.editingStopped(e);
       return;
     }
     */
   }
}

class ApprDlg_CheckBoxCellRenderer extends JCheckBox implements TableCellRenderer {

        public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {

            setOpaque(true);

            if ((value != null && ((Boolean)value).booleanValue())){
                setSelected(true);
            }
	          else {
                setSelected(false);
	          }

            if (isSelected) {
	              setForeground(table.getSelectionForeground());
	              setBackground(table.getSelectionBackground());
	          } else {

                setForeground(table.getForeground());

	              setBackground(table.getBackground());

            }

            setHorizontalAlignment(JLabel.CENTER);
            this.repaint();
            return this;

        }

}


