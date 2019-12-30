//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
//import radian.tcmis.client.share.reports.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
//import radian.tcmis.client.share.reports.appData.*;
import java.beans.*;
import radian.tcmis.client.share.gui.*;
import java.awt.Component;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class WasteMaterialCatalog extends JDialog {

  CmisTable wasteMaterialTable;
  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  String facilityId;
  JTable myTable = new JTable();

  CmisApp cmis ;
  JScrollPane partJSP = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  XYLayout xYLayout4 = new XYLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel bottomP = new JPanel();
  JButton cancelB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();

  int QTY_COL = 0;
  int ORDER_COL = 0;
  int ITEM_ID_COL = 0;
  int VENDOR_PART_NO_COL = 0;
  int DESC_COL = 0;
  int LPP_COL = 0;
  int PACKAGING_COL = 0;
  int SPEC_COL = 0;

  String storageArea = null;
  String vendorId = null;
  int orderNo = 0;
  String shipmentID = null;
  Hashtable accumulationPts = null;
  Vector accountSysIDV = null;
  String mySelectedAccSys = null;
  Vector currentItem = null;
  String actSysId = null;
  WasteAddEditMaterialRequest waemr;

  StaticJLabel noteL = new StaticJLabel();

  public WasteMaterialCatalog(JFrame fr, WasteAddEditMaterialRequest waemr, String title, String facID, String vendorID) {
    super(fr, title, false);
    this.facilityId = facID;
    this.vendorId = vendorID;
    this.waemr = waemr;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(600, 350));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }


  public void setParent(CmisApp c){
    cmis = c;
  }

  public void setOrderNo(int n) {
    this.orderNo = n;
  }

  public void setShipmentId(String val) {
    this.shipmentID = val;
  }

  public void setCurrentItem(Vector v) {
    this.currentItem = v;
  }

  public void setActSysIDs(Vector v) {
    this.accountSysIDV = v;
  }

  public void setActSysId(String val) {
    this.actSysId = val;
  }


  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setFont(new java.awt.Font("Dialog", 0, 10));
    okB.setMaximumSize(new Dimension(130, 24));
    okB.setMinimumSize(new Dimension(130, 24));
    okB.setPreferredSize(new Dimension(130, 24));
    okB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    cancelB.setFont(new java.awt.Font("Dialog", 0, 10));
    cancelB.setMaximumSize(new Dimension(130, 24));
    cancelB.setMinimumSize(new Dimension(130, 24));
    cancelB.setPreferredSize(new Dimension(130, 24));
    cancelB.setIcon(ss);
    ss = ResourceLoader.getImageIcon("images/button/add.gif");
    panel1.setLayout(borderLayout4);
    jPanel1.setLayout(xYLayout1);
    okB.setText("Continue");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });


    wasteMaterialTable = new CmisTable();
    partJSP.getViewport().setView(wasteMaterialTable);
    panel1.setMaximumSize(new Dimension(250, 250));
    panel1.setMinimumSize(new Dimension(250, 250));
    panel1.setPreferredSize(new Dimension(250, 250));

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
    noteL.setText("Note - To add/remove item(s) to/from current order.  Click on the 'Order' column.");
    jPanel6.add(noteL, new GridBagConstraints2(0, 0, 0, 0, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 15, 5, 5), 0, 0));
    jPanel6.add(jPanel7, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(40, 15, 50, 15), 0, 0));
    jPanel7.add(partJSP, BorderLayout.CENTER);

    jPanel6.add(bottomP, new GridBagConstraints2(1, 1, 0, 0, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(okB, BorderLayout.CENTER);
    bottomP.add(cancelB, BorderLayout.EAST);

  //  ClientHelpObjs.makeDefaultColorsPink(this);
  }

  void buildTable(Hashtable profileH){

    partJSP.getViewport().remove(wasteMaterialTable);

    DbTableModel model = buildTableModel(profileH);
    model.setEditableFlag(1);
    sorter = new TableSorter(model);
    wasteMaterialTable = new CmisTable(sorter);
    sorter.setColTypeFlag(model.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(wasteMaterialTable);

    wasteMaterialTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    wasteMaterialTable.addMouseListener(new Cat_Material_mouseAdapter(this));

    //Nawaz 09/19/01
        AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
        Hashtable chargeTableStyle = (Hashtable)profileH.get("TABLE_STYLE");
        Color color = (Color)chargeTableStyle.get("COLOR");
        Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
        Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
        Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
        Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
        Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
        renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
        //font and foreground and background color for columns heading
        String fontName = (String)chargeTableStyle.get("FONT_NAME");
        Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
        Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
        wasteMaterialTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = wasteMaterialTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
        }

        wasteMaterialTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        wasteMaterialTable.setCellSelectionEnabled(false);
        wasteMaterialTable.getTableHeader().setReorderingAllowed(true);
        WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
        WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();
        wasteMaterialTable.setDefaultRenderer(String.class, colorTableRenderer);
        wasteMaterialTable.setDefaultRenderer(Integer.class, colorTableRenderer);
        wasteMaterialTable.setDefaultRenderer(Double.class, colorTableRenderer);
        wasteMaterialTable.setDefaultRenderer(Boolean.class, checkTableRenderer);

        JCheckBox check = new JCheckBox();
        check.setHorizontalAlignment(JLabel.CENTER);
        wasteMaterialTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor(check));

        for (int k = 0; k < wasteMaterialTable.getColumnCount(); k++) {
          int width = model.getColumnWidth(k);
          wasteMaterialTable.getColumn(model.getColumnName(k)).setPreferredWidth(width);
          wasteMaterialTable.getColumn(model.getColumnName(k)).setWidth(width);
          if (width==0){
            wasteMaterialTable.getColumn(model.getColumnName(k)).setMinWidth(width);
            wasteMaterialTable.getColumn(model.getColumnName(k)).setMaxWidth(width);
          }
        }
    partJSP.getViewport().setView(wasteMaterialTable);
  }

  DbTableModel buildTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("TABLE_TYPES");
    Vector data = (Vector) searchData.get("TABLE_DATA");

    DbTableModel ctm = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      ctm.addRow((Object[]) data.elementAt(i));
    }
    ctm.setColumnPrefWidth(colWidths);
    ctm.setColumnType(colTypes);
    return ctm;
  }  //end of method


  public void loadItAction() {
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_WASTE_MATERIAL_CATALOG"); //String, String
      query.put("FAC_ID",this.facilityId); //String, String
      query.put("VENDOR_ID",this.vendorId);
      query.put("SHIPMENT_ID",this.shipmentID);  //String, String
      query.put("ORDER_NO",new Integer(this.orderNo));  //String, Integer
      query.put("CURRENT_ITEM",this.currentItem);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      Hashtable profileInfoH = (Hashtable)result.get("WASTE_MATERIAL_CATALOG_INFO");

      profileInfoH.put("TABLE_STYLE",(Hashtable)result.get("TABLE_STYLE"));
      buildTable(profileInfoH);
      //column
      Hashtable keyCols = (Hashtable)profileInfoH.get("KEY_COLS");
      QTY_COL = ((Integer)keyCols.get("Qty")).intValue();
      ORDER_COL = ((Integer)keyCols.get("Order")).intValue();
      ITEM_ID_COL = ((Integer)keyCols.get("Item ID")).intValue();
      VENDOR_PART_NO_COL = ((Integer)keyCols.get("Vendor Part No")).intValue();
      LPP_COL = ((Integer)keyCols.get("Price")).intValue();
      DESC_COL = ((Integer)keyCols.get("Description")).intValue();
      PACKAGING_COL = ((Integer)keyCols.get("Packaging")).intValue();
      SPEC_COL = ((Integer)keyCols.get("Specification")).intValue();

      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }
    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(wasteMaterialTable.isEditing()){
      wasteMaterialTable.getCellEditor().stopCellEditing();
    }
    Vector wasteItemV = new Vector();
    if (wasteMaterialTable.getSelectedRowCount() < 1) {
      GenericDlg g = new GenericDlg(cmis.getMain(),"Select One",true);
      g.setMsg("Please select at least one item.");
      g.setVisible(true);
      return;
    }else {
      //audit qty and accumulation points
      //first get boolean column
      int bolCol = -1;
      for (int j=0;j<wasteMaterialTable.getColumnCount();j++){
        if (wasteMaterialTable.getColumnClass(j).getName().indexOf("Boolean") > 0){
          bolCol = j;
          break;
        }
      }

      for (int i = 0; i < wasteMaterialTable.getRowCount(); i++) {
        Boolean b = (Boolean)wasteMaterialTable.getModel().getValueAt(i,bolCol);
        if (b.booleanValue()) {
          String qty = BothHelpObjs.makeBlankFromNull(wasteMaterialTable.getModel().getValueAt(i,QTY_COL).toString());
          if (preAudit(qty,i)) {
            Hashtable h = new Hashtable();
            h.put("QTY",qty);
            h.put("ITEM_ID",BothHelpObjs.makeBlankFromNull(wasteMaterialTable.getModel().getValueAt(i,ITEM_ID_COL).toString()));
            h.put("VENDOR_PART_NO",BothHelpObjs.makeBlankFromNull(wasteMaterialTable.getModel().getValueAt(i,VENDOR_PART_NO_COL).toString()));
            h.put("DESC",BothHelpObjs.makeBlankFromNull(wasteMaterialTable.getModel().getValueAt(i,DESC_COL).toString()));
            h.put("PACKAGING",BothHelpObjs.makeBlankFromNull(wasteMaterialTable.getModel().getValueAt(i,PACKAGING_COL).toString()));
            h.put("SPEC",BothHelpObjs.makeBlankFromNull(wasteMaterialTable.getModel().getValueAt(i,SPEC_COL).toString()));
            h.put("CURRENT_PRICE",BothHelpObjs.makeBlankFromNull(wasteMaterialTable.getModel().getValueAt(i,LPP_COL).toString()));
            wasteItemV.addElement(h);
          }else {
            return;
          }
        }
      }

      if (wasteItemV.size() <= 0) {
        GenericDlg g = new GenericDlg(cmis.getMain(),"Select One",true);
        g.setMsg("To add item(s) to current order.  Click on the 'Order' column.");
        g.setVisible(true);
        return;
      }

      //getting account sys id
      if (BothHelpObjs.isBlankString(actSysId)) {
        showAccSysDlg();
        waemr.actSysT.setText(mySelectedAccSys);
      }

      //System.out.println("---- in waste material catalog: "+wasteItemV);
      waemr.setVisible(true);
      setVisible(false);
      waemr.addLineItem(wasteItemV);
    }
  }

  public void showAccSysDlg() {
    boolean selected = false;
    String title = "Select an Account System for: " + this.facilityId;

    if (accountSysIDV.size() == 1) {
      mySelectedAccSys = (String)accountSysIDV.elementAt(0);
    } else {
      while (!selected) {
        AccountSystemDlg asd = new AccountSystemDlg(cmis.getMain(),title,true);
        for (int i = 0; i < accountSysIDV.size(); i++) {
          asd.accSysC.addItem(accountSysIDV.elementAt(i).toString());
        }
        asd.accSysC.addItem("Select an Account System");
        asd.accSysC.setSelectedItem("Select an Account System");
        asd.setVisible(true);
        mySelectedAccSys = asd.getSelectedAccSys();
        if (!mySelectedAccSys.equalsIgnoreCase("Select an Account System"))
          selected = true;
      }
    }
  }

  boolean preAudit(String qty, int row) {
    try{
      int p = Integer.parseInt(qty);
      if (p < 0) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg("You must enter only positive integer in qty column.");
        gd.setVisible(true);
        auditFailed(row);
        return false;
      }
    }catch(Exception e){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg("You must enter only positive integer in qty column.");
      gd.setVisible(true);
      auditFailed(row);
      return false;
    }

    return true;
  }
  void auditFailed(int i){
    setSelectedRow(i);
  }
  void setSelectedRow(int x){
    ListSelectionModel lsm = wasteMaterialTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    wasteMaterialTable.scrollRectToVisible(wasteMaterialTable.getCellRect(x,0,false));
  }

  boolean goCreateWasteOrderItem(Vector reqV) {
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    Boolean bol = null;
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","CREATE_WASTE_MATERIAL_REQUEST"); //String, String
      query.put("FAC_ID",this.facilityId); //String, String
      query.put("ACCOUNT_SYS_ID",mySelectedAccSys);   //String, String
      query.put("VENDOR_ID",this.vendorId);
      query.put("SHIPMENT_ID",this.shipmentID);  //String, String
      query.put("ORDER_NO",new Integer(this.orderNo));  //String, Integer
      query.put("REQUESTER_ID",cmis.getUserId());     //String, Integer
      query.put("REQUEST_INFO",reqV);
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return false;
      }

      //Hashtable profileInfoH = (Hashtable)result.get("WASTE_MATERIAL_CATALOG_INFO");
      bol = (Boolean)result.get("SUCCEED");

      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return false;
    }
    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
    return (bol.booleanValue());
  }


  void wasteMaterialTable_mouseClicked(MouseEvent e) {
    int bolCol = -1;
    for (int i=0;i<wasteMaterialTable.getModel().getColumnCount();i++){
      if (wasteMaterialTable.getColumnClass(i).getName().indexOf("Boolean") > 0){
        bolCol = i;
        break;
      }
    }
    int row = wasteMaterialTable.getSelectedRow();
    if(wasteMaterialTable.getModel().getValueAt(row,bolCol).toString().equalsIgnoreCase("true") && wasteMaterialTable.getSelectedColumn() == ORDER_COL) {
        wasteMaterialTable.getModel().setValueAt(new Boolean(false),row,bolCol);
        wasteMaterialTable.getModel().setValueAt(new String(""),row,ORDER_COL);
    }else if (wasteMaterialTable.getSelectedColumn() == ORDER_COL) {
      wasteMaterialTable.getModel().setValueAt(new Integer(this.orderNo),row,ORDER_COL);
      wasteMaterialTable.getModel().setValueAt(new Boolean(true),row,bolCol);
    }
    wasteMaterialTable.repaint();
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }
}

class Cat_Material_mouseAdapter extends java.awt.event.MouseAdapter {
  WasteMaterialCatalog adaptee;
  Cat_Material_mouseAdapter(WasteMaterialCatalog adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.wasteMaterialTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}

class WasteMaterialCatalog_BoxCellEditor extends DefaultCellEditor implements
      ItemListener, ActionListener {

    protected JComboBox currentBox = null;

	  public WasteMaterialCatalog_BoxCellEditor(JComboBox box) {
		  super(box);
      currentBox = box;
	  }

	  // These methods implement the TableCellEditor interface
	  public Component getTableCellEditorComponent(JTable table, Object value,
						   boolean isSelected, int row, int column) {

		  //((JComboBox)editorComponent).setSelectedItem(((JComboBox)value).getSelectedItem());
      editorComponent = (JComboBox) value;
      ((JComboBox)editorComponent).setFont(table.getFont());
      ((JComboBox)editorComponent).addItemListener(this);
      ((JComboBox)editorComponent).addActionListener(this);
      currentBox = (JComboBox) editorComponent;
      currentBox.addItemListener(this);
      currentBox.addActionListener(this);
      // rfz allow editing .... currentBox.setEditable(false);
      table.setRowSelectionInterval(row,row);
      table.repaint();

		  return editorComponent;
	  }

	  public Object getCellEditorValue() {
		  return currentBox;
	  }

	  public boolean isCellEditable(EventObject evt) {
		  if (evt instanceof MouseEvent) {
			  if (((MouseEvent)evt).getClickCount() >=
							getClickCountToStart()) {
				  return true;
			  }
		  }
		  return false;
	  }


	  public void cancelCellEditing() {
        fireEditingStopped();
	  }

    public void itemStateChanged(ItemEvent e){
        fireEditingStopped();
    }
     public void actionPerformed(ActionEvent e) {
	      fireEditingStopped();
	  }
}


class WasteMaterialCatalog_TableRender extends TcmisCellRenderer {
  public Component getTableCellRendererComponent(
                          JTable table, Object value,
                          boolean isSelected, boolean hasFocus,
                          int row, int column) {


      Component comp = super.getTableCellRendererComponent(table, value,
                          isSelected,hasFocus,
                          row,column);
      try {
        int bolCol = -1;
        for (int i=0;i<table.getModel().getColumnCount();i++){
          if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
            bolCol = i;
            break;
          }
        }

        if(((JComboBox)value).getSelectedItem()!=null){
          ((JLabel) comp).setText(((JComboBox)value).getSelectedItem().toString());
        } else {
          ((JLabel) comp).setText(((JComboBox)value).getItemAt(0).toString());
        }

        if (isSelected) {
          if (((Boolean) table.getModel().getValueAt(row,bolCol)).booleanValue()) {
                setForeground(Color.black);
                setBackground(Color.orange);
          }else{
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
          }
        }else if (((Boolean) table.getModel().getValueAt(row,bolCol)).booleanValue()) {
          setForeground(Color.black);
          setBackground(Color.yellow);
        }else{
          setForeground(Color.black);
          setBackground(Color.white);
        }
      } catch (Exception e){
        ((JLabel) comp).setText("");
      }

    return this;
  }
}

