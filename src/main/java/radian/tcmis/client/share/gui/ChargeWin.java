
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import radian.tcmis.client.share.helpers.*;
import java.beans.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class ChargeWin extends JDialog {

  JPanel holder = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();


  final String[] chargeCols = {"Charge #","Pct (1-100)"};
  final Long colTypeFlag = new Long("12");

  int chargeEditableFlag = 3;
  Main grandparent;
  int row;

  CmisTable chargeTable = new CmisTable();

  TableOrganizer tOrg = new TableOrganizer();
  TableSorter  sorterCharge = new TableSorter();
  CmisTableModel cmisTModelCharge = new CmisTableModel();
  JScrollPane JSP = new JScrollPane();


  String title;
  Vector chargeV = new Vector();
  Vector pctV = new Vector();
  TCMBevelPanel parent;
  Object[][] tempData = {{"","100"},{"",""},{"",""},{"",""},{"",""},
                         {"",""},{"",""},{"",""},{"",""},{"",""},
                         {"",""},{"",""},{"",""},{"",""},{"",""}};

  JPanel jPanel1 = new JPanel();
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JButton B = new JButton();
  JButton C = new JButton();

  public ChargeWin(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      this.getContentPane().add(holder);
      pack();
      //colors and fonts
      ClientHelpObjs.makeDefaultColors(this);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ChargeWin(Frame frame) {
    this(frame, "", false);
  }

  public ChargeWin(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public ChargeWin(Frame frame, String title) {
    this(frame, title, false);
  }

  public ChargeWin(Main passed,TCMBevelPanel B) {
    this(passed,"",true);
    grandparent = passed;
    parent = B;
    row = 0;
    title = (String)null;
  }

  public void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    this.setTitle("Charge Number Table");
    holder.setSize(new Dimension(300, 300));
    jPanel1.setLayout(borderLayout2);

    C.setText("Cancel");
    C.addKeyListener(new ChargeWin_C_keyAdapter(this));
    C.addActionListener(new ChargeWin_C_actionAdapter(this));
    C.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    B.setText("Ok");
    B.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    B.addKeyListener(new ChargeWin_B_keyAdapter(this));
    B.addFocusListener(new ChargeWin_B_focusAdapter(this));
    B.addActionListener(new ChargeWin_B_actionAdapter(this));
    holder.setLayout(borderLayout1);
    chargeTable = new CmisTable();
// CBK-begin
    chargeTable.setToolTipText(null);
// CBK-end
    tOrg = new TableOrganizer();
    sorterCharge = new TableSorter();
    cmisTModelCharge = new CmisTableModel();
    holder.add(jPanel1, BorderLayout.CENTER);
    holder.add(panel1, BorderLayout.SOUTH);
    panel1.add(panel2, null);
    panel2.add(B, null);
    panel2.add(C, null);
  }


  public void setRow(int r) {
    row = r;
  }

  public void setTitle(String t) {
    title = t;
  }

  public void setEditFlag(int flag) {
    chargeEditableFlag = flag;
  }

  public void setChargeVector(Vector V) {
    chargeV = V;
  }

  public void setPctVector(Vector V) {
    pctV = V;
  }

  public void pushChargeVector() {
      parent.setChargeVector(chargeV);
  }

  public void pushPctVector() {
      parent.setPctVector(pctV);
  }

  public void setCells() {
    int i;
    Object [][] data = new Object[15][chargeCols.length];
    for (i=0;i<chargeV.size();i++) {
      data[i][0] = chargeV.elementAt(i);
      data[i][1] = pctV.elementAt(i);
    }
    for (int j=i;j<15;j++){
      data[j][0] = "";
      data[j][1] = "";
    }
    cmisTModelCharge = new CmisTableModel(chargeCols,data);
    cmisTModelCharge.setEditableFlag(chargeEditableFlag);
    sorterCharge = new TableSorter(cmisTModelCharge);
    sorterCharge.setColTypeFlag(colTypeFlag.longValue());
    chargeTable = new CmisTable (sorterCharge);

// CBK-begin
    chargeTable.setToolTipText(null);
// CBK-end
    //sorterCharge.addMouseListenerToHeaderInTable(chargeTable);
    tOrg = new TableOrganizer(chargeTable);
    chargeTable = (CmisTable) tOrg.getJTable();
    chargeTable.addMouseListener(new ChargeWin_chargeTable_mouseAdapter(this));
    chargeTable.addKeyListener(new ChargeWin_chargeTable_keyAdapter(this));
    chargeTable.addPropertyChangeListener(new ChargeWin_chargeTable_propertyChangeAdapter(this));
    chargeTable.addFocusListener(new ChargeWin_chargeTable_focusAdapter(this));
    chargeTable.addComponentListener(new ChargeWin_chargeTable_componentAdapter(this));

    chargeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    chargeTable.setColTypeFlag(colTypeFlag.longValue());
    chargeTable.setAlign();
    //start editing
//    chargeTable.

    if (!chargeTable.editCellAt(0,0)){
      B.requestFocus();
    }

    holder.remove(JSP);
    JSP = new JScrollPane(chargeTable);
    holder.add(JSP, BorderLayout.CENTER);
    holder.validate();
    holder.setVisible(true);
    this.validate();
  }

  public void showme(){
    EditCellThread ecT = new EditCellThread(this);
    ecT.setRow(0);
    ecT.setCol(0);
    ecT.start();
  }

  public void init() {
      if (title == (String)null) {
        this.setTitle("Enter Charge Number(s)");
      } else {
        this.setTitle(title);
      }

      cmisTModelCharge = new CmisTableModel(chargeCols,tempData);
      cmisTModelCharge.setEditableFlag(chargeEditableFlag);
      sorterCharge = new TableSorter(cmisTModelCharge);
      sorterCharge.setColTypeFlag(colTypeFlag.longValue());
      chargeTable = new CmisTable (sorterCharge);
// CBK-begin
      chargeTable.setToolTipText(null);
// CBK-end

      tOrg = new TableOrganizer(chargeTable);
      chargeTable = (CmisTable) tOrg.getJTable();
      chargeTable.setCellSelectionEnabled(true);
      chargeTable.setRowSelectionAllowed(false);
      chargeTable.addMouseListener(new ChargeWin_chargeTable_mouseAdapter(this));
      chargeTable.addKeyListener(new ChargeWin_chargeTable_keyAdapter(this));
      chargeTable.addPropertyChangeListener(new ChargeWin_chargeTable_propertyChangeAdapter(this));
      chargeTable.addFocusListener(new ChargeWin_chargeTable_focusAdapter(this));
      chargeTable.addComponentListener(new ChargeWin_chargeTable_componentAdapter(this));

      sorterCharge.addMouseListenerToHeaderInTable(chargeTable);
      chargeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      chargeTable.setColTypeFlag(colTypeFlag.longValue());
      chargeTable.setAlign();

      JSP = new JScrollPane(chargeTable);

      jPanel1.add(JSP, BorderLayout.CENTER);
      if (chargeV.size() != 0) {
        setCells();
      }

      Dimension dlgSize = this.getPreferredSize();
      Dimension frmSize = grandparent.getSize();
      Point loc = grandparent.getLocation();
      this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
      this.setModal(true);
      this.setSize(300,300);
  }


  public boolean passAudit() {
      int i;
      Double val = new Double(0.0);
      double tot = 0.0;
      for (i=0;i<pctV.size();i++) {
         val = new Double((String)pctV.elementAt(i));
         tot = tot + val.doubleValue();
      }
      if (tot == 100.0) {
        return true;
      } else {
        return false;
      }
  }

  void closeWin(){
      // This stops the editing on the basket table
      // and ensures any values changed are kept even
      // if the user doesn't press ENTER after changing
      if (chargeTable.isEditing()) {
        chargeTable.getCellEditor().stopCellEditing();
      }


      chargeV = new Vector();
      pctV = new Vector();
      if (chargeEditableFlag > 0) {
        int i,j,k;
        k = 0;
        j = 0;
        for (i=0;i<chargeTable.getModel().getRowCount();i++) {
          if (chargeTable.getModel().getValueAt(i,0) == null || chargeTable.getModel().getValueAt(i,1) == null){
             GenericDlg err = new GenericDlg(grandparent,"Math Error",true);
             String no = new String("Null element");
             err.setMsg(no);
             err.setVisible(true);
             return;
          }

          if (((String)chargeTable.getModel().getValueAt(i,0)).length() != 0){
            if (((String)chargeTable.getModel().getValueAt(i,1)).length() == 0){
              GenericDlg err = new GenericDlg(grandparent,"Math Error",true);
              String no = new String("Please enter a percentage for the charge number");
              err.setMsg(no);
              err.setVisible(true);
              return;
            }
            j++;
            k = i;
            try {
              Integer test = new Integer(chargeTable.getModel().getValueAt(i,1).toString());
            } catch (Exception e1){
              try {
               Double test = new Double(chargeTable.getModel().getValueAt(i,1).toString());
              } catch (Exception e){
               GenericDlg err = new GenericDlg(grandparent,"Math Error",true);
               String no = new String("Please enter a number for percentage on line "+i);
               err.setMsg(no);
               err.setVisible(true);
               return;
              }
            }
            chargeV.addElement(chargeTable.getModel().getValueAt(i,0).toString());
            pctV.addElement(chargeTable.getModel().getValueAt(i,1).toString());
          }
        }
        chargeV.trimToSize();
        pctV.trimToSize();
        if (passAudit()) {
          pushChargeVector();
          pushPctVector();
          this.setVisible(false);
        } else {
          GenericDlg err = new GenericDlg(grandparent,"Math Error",true);
          String no = new String("Percentages must add up to 100");
          err.setMsg(no);
          err.setVisible(true);
          return;
        }
      } else {
        this.setVisible(false);
      }
  }

  void B_actionPerformed(ActionEvent e) {
       ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
       closeWin();
  }

  void C_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    this.setVisible(false);
  }

  void chargeTable_mouseClicked(MouseEvent e) {
  }

  void chargeTable_keyPressed(KeyEvent e) {
    if (chargeTable.isEditing()){
      chargeTable.getCellEditor().stopCellEditing();
    }
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      closeWin();
    }
  }

  void chargeTable_componentShown(ComponentEvent e) {
  }

  void chargeTable_focusLost(FocusEvent e) {

  }

  void chargeTable_focusGained(FocusEvent e) {
  }

  void chargeTable_propertyChange(PropertyChangeEvent e) {
  }

  void B_focusGained(FocusEvent e) {
       if (chargeTable.isEditing()){
             TabThread tT = new TabThread(this);
             tT.start();
       }
  }

  void B_keyPressed(KeyEvent e) {
      //key pressed on this focused
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        closeWin();
      }
  }

  void C_keyPressed(KeyEvent e) {
      //key pressed on this focused - cancel
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        this.setVisible(false);
      }
  }

}

class ChargeWin_B_actionAdapter implements java.awt.event.ActionListener{
  ChargeWin adaptee;

  ChargeWin_B_actionAdapter(ChargeWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.B_actionPerformed(e);
  }
}

class ChargeWin_C_actionAdapter implements java.awt.event.ActionListener{
  ChargeWin adaptee;

  ChargeWin_C_actionAdapter(ChargeWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.C_actionPerformed(e);
  }
}

class ChargeWin_chargeTable_mouseAdapter extends java.awt.event.MouseAdapter {
  ChargeWin adaptee;

  ChargeWin_chargeTable_mouseAdapter(ChargeWin adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.chargeTable_mouseClicked(e);
  }
}



class ChargeWin_chargeTable_keyAdapter extends java.awt.event.KeyAdapter {
  ChargeWin adaptee;

  ChargeWin_chargeTable_keyAdapter(ChargeWin adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.chargeTable_keyPressed(e);
  }
}

class ChargeWin_chargeTable_componentAdapter extends java.awt.event.ComponentAdapter {
  ChargeWin adaptee;

  ChargeWin_chargeTable_componentAdapter(ChargeWin adaptee) {
    this.adaptee = adaptee;
  }

  public void componentShown(ComponentEvent e) {
    adaptee.chargeTable_componentShown(e);
  }
}

class ChargeWin_chargeTable_focusAdapter extends java.awt.event.FocusAdapter {
  ChargeWin adaptee;

  ChargeWin_chargeTable_focusAdapter(ChargeWin adaptee) {
    this.adaptee = adaptee;
  }

  public void focusLost(FocusEvent e) {
    adaptee.chargeTable_focusLost(e);
  }

  public void focusGained(FocusEvent e) {
    adaptee.chargeTable_focusGained(e);
  }
}

class ChargeWin_chargeTable_propertyChangeAdapter implements java.beans.PropertyChangeListener {
  ChargeWin adaptee;

  ChargeWin_chargeTable_propertyChangeAdapter(ChargeWin adaptee) {
    this.adaptee = adaptee;
  }

  public void propertyChange(PropertyChangeEvent e) {
    adaptee.chargeTable_propertyChange(e);
  }
}

class EditCellThread extends Thread {
  ChargeWin parent;
  int row;
  int col;

  public EditCellThread(ChargeWin parent){
     super("EditCellThread");
     this.parent = parent;
  }

  public void setRow(int r){
     row = r;
  }

  public void setCol(int c){
     col = c;
  }

  public void run(){
    while (!parent.chargeTable.isShowing()){};
    while (!parent.chargeTable.isVisible()){};

    try{
     sleep((long)1000);
    } catch (Exception e){};

    parent.chargeTable.setRowSelectionInterval(row,row);
    parent.chargeTable.setColumnSelectionInterval(col,col);

    if (parent.chargeTable.editCellAt(row,col)){
      parent.chargeTable.requestFocus();
      MouseEvent ev = new MouseEvent(parent.chargeTable.getEditorComponent(),MouseEvent.MOUSE_CLICKED,(new Date()).getTime()+100,InputEvent.BUTTON1_MASK,5,5,2,false);
      parent.chargeTable.getEditorComponent().dispatchEvent(ev);
      parent.chargeTable.getEditorComponent().requestFocus();
    }
    this.stop();
  }
}

class TabThread extends Thread {
  ChargeWin parent;

  public TabThread(ChargeWin parent){
     super("TabThread");
     this.parent = parent;
  }

  public void run(){
    int row = parent.chargeTable.getEditingRow();
    int col = parent.chargeTable.getEditingColumn();
    if (col == 0){
      col++;
    } else {
      col--;
      row++;
    }
    //parent.chargeTable.editingStopped(new ChangeEvent(parent.chargeTable));
    if (parent.chargeTable.isEditing()) {
      parent.chargeTable.getCellEditor().stopCellEditing();
    }

    while (parent.chargeTable.isEditing()){};
    EditCellThread ecT = new EditCellThread(parent);
    ecT.setRow(row);
    ecT.setCol(col);
    ecT.start();
    this.stop();
  }
}

class ChargeWin_B_focusAdapter extends java.awt.event.FocusAdapter {
  ChargeWin adaptee;

  ChargeWin_B_focusAdapter(ChargeWin adaptee) {
    this.adaptee = adaptee;
  }

  public void focusGained(FocusEvent e) {
    adaptee.B_focusGained(e);
  }
}

class ChargeWin_B_keyAdapter extends java.awt.event.KeyAdapter {
  ChargeWin adaptee;

  ChargeWin_B_keyAdapter(ChargeWin adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.B_keyPressed(e);
  }
}

class ChargeWin_C_keyAdapter extends java.awt.event.KeyAdapter {
  ChargeWin adaptee;

  ChargeWin_C_keyAdapter(ChargeWin adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.C_keyPressed(e);
  }
}






























