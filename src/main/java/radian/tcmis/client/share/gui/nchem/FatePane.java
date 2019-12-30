
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui.nchem;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import radian.tcmis.client.share.gui.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class FatePane extends JPanel {

  JPanel holder = new JPanel();

  String[] fateCols = {"Description","%"};
  Long colTypeFlag = new Long("11");

  int fateEditableFlag = 4;
  int fateEditableFlag_save = 0;

  int row;
  int lastCol = 0;

  FatePane_FateTable fateTable = new FatePane_FateTable();

  TableOrganizer tOrg = new TableOrganizer();
  CmisTableModel cmisTModelFate = new CmisTableModel();
  JScrollPane JSP = new JScrollPane();

  String title;
  Object[][] fateData = {{"Product","0"},{"Waste Water","0"},{"Air","0"},{"Solid Waste","0"},
                         {"Recycling","0"},{"Hazardous Waste","0"},{"Other","0"},{"Total","0"}} ;

  ApprPane parent;
  MatPane  matPane;
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  DataJLabel matDescL = new DataJLabel();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();

  public FatePane() {
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }


  public FatePane(ApprPane B,MatPane m) {
    parent = B;
    matPane = m;
    row = 0;
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void jbInit() throws Exception{

    this.setTitle("Used Chemical Destination");
    //holder.setSize(new Dimension(300, 300));
    jPanel1.setLayout(borderLayout2);
    this.setLayout(borderLayout1);
    holder.setLayout(borderLayout4);
    fateTable = new FatePane_FateTable();
    matDescL.setText("Material Description");

    tOrg = new TableOrganizer();
    cmisTModelFate = new CmisTableModel();
    jPanel2.setLayout(borderLayout5);
    matDescL.setMaximumSize(new Dimension(382, 15));
    //matDescL.setForeground(Color.black);
    jPanel3.setLayout(borderLayout3);
    matDescL.setPreferredSize(new Dimension(382, 30));
    matDescL.setMinimumSize(new Dimension(300, 30));

    holder.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(matDescL, BorderLayout.CENTER);
    holder.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jPanel1, BorderLayout.CENTER);
    this.add(holder,BorderLayout.CENTER);
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
   */
   //buttons

    ImageIcon ss;



    ss = ResourceLoader.getImageIcon("images/button/cancel.gif","Cancel");


    ss = ResourceLoader.getImageIcon("images/button/ok.gif","Ok");
    fateTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        fateTable_mouseClicked(e);
      }
    });

    //table
    buildTable();

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    jPanel3.setBackground(new Color(160, 160, 160));
  }

  public JTable getTable(){
    return fateTable;
  }

  void buildTable(){
      cmisTModelFate = new CmisTableModel(fateCols,fateData);
      cmisTModelFate.setEditableFlag(fateEditableFlag);
      fateTable = new FatePane_FateTable (cmisTModelFate);
      fateTable.setCellSelectionEnabled(true);
      fateTable.setRowSelectionAllowed(false);
      fateTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      fateTable.setAlign();

      fateTable.setColTypeFlag(colTypeFlag.longValue());
      fateTable.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(KeyEvent e) {
          fateTable_keyPressed(e);
        }
      });

       //Nawaz 09/19/01
      AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
      Hashtable chargeTableStyle =  BothHelpObjs.getTableStyle();
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
      fateTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = fateTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }

      JSP = new JScrollPane(fateTable);

      jPanel1.removeAll();
      jPanel1.add(JSP, BorderLayout.CENTER);

      jPanel1.revalidate();
      holder.revalidate();
  }

  public void setEnabled(boolean b){
      ClientHelpObjs.setEnabledChildOnly(this,b);
      if (!b){
         this.fateEditableFlag = 0;
      } else {
         this.setEditFlag(fateEditableFlag_save);
      }

      fateData = new Object[fateTable.getRowCount()][this.fateTable.getColumnCount()];
      for (int i=0;i<this.fateTable.getRowCount();i++){
         for (int j=0;j<this.fateTable.getColumnCount();j++){
            this.fateData[i][j] = this.fateTable.getModel().getValueAt(i,j);
         }
      }
      this.buildTable();
  }

  public void setRow(int r) {
    row = r;
  }

  public void setTitle(String t) {
    title = t;
  }

  public void setEditFlag(int flag) {
    fateEditableFlag = flag;
    fateEditableFlag_save = flag;
  }

  public void setFateData(Object[][] data) {
    for (int i=0;i<data.length;i++){
       Object[] tmp = data[i];
       for (int j=1;j<tmp.length;j++){ // ignore first col
          if (tmp[j]==null || tmp[j].toString().length()==0) {
             data[i][j] = new Integer(0);
          } else if (tmp[j] instanceof String) {
             try { data[i][j] = new Integer(tmp[j].toString()); }
             catch (Exception e) { data[i][j] = new Integer(0); }
          }
       }
    }

    fateData = data;
  }

  public void setFateCols(String[] cols) {
    fateCols = cols;
  }

  public String[] getFateCols() {
    return fateCols;
  }

  /** adds a column to the fate table, like part1, part2, ...
  */
  public void addColumn(Object[] data) {
    Integer I = new Integer(fateCols.length);
    String[] newCols = new String[fateCols.length+1];
    int i=0;
    for (i=0;i<fateCols.length;i++){
       newCols[i] = fateCols[i];
    }

    newCols[fateCols.length] = "Part "+I.toString();
    this.setFateCols(newCols);
    int col = newCols.length;
    Object[][] tmp = new Object[8][col];
    for(i=0;i<col-1;i++){
       for (int j=0;j<8;j++){
        tmp[j][i] = fateData[j][i];
       }
    }

    for(i=0;i<7;i++) {
      tmp[i][col-1]= data[i];
    }
    this.setFateData(tmp);
  }

  /** adds a column to the fate table, like part1, part2, ...
  */
  public void deleteColumn(int delcol) {
    String[] newCols = new String[fateCols.length-1];
    int i=0;
    for (i=0;i<fateCols.length;i++){
       if (i==delcol) continue;
       newCols[i] = fateCols[i];
    }
    this.setFateCols(newCols);

    int col = fateCols.length;
    Object[][] tmp = new Object[8][col];
    for(i=0;i<col-1;i++) {
       if (i==delcol) continue;
       for (int j=0;j<8;j++){
           tmp[j][i] = fateData[j][i];
       }
    }
    this.setFateData(tmp);
  }

  void setMatLabel(String l){
    matDescL.setText(l);
    matDescL.validate();
    matDescL.repaint();
    jPanel2.validate();
  }

  void changeMaterialLabel(int col){
    if (col==0) { setMatLabel(""); return; }

    for (int i=0;i<matPane.tabPane.getTabCount();i++){
       if (col==(i+1)){
          KitsPane tmp = (KitsPane) matPane.tabPane.getComponentAt(i);
          setMatLabel(tmp.matDescT.getText());
          break;
       }
    }

  }


  public void setCells() {
    int i=0;
    for (i=1;i<fateCols.length;i++){ // Total
       fateData[7][i] = new Integer(0);
    }

    double l = 0;
    String s = "1";
    for (i=1;i<fateCols.length;i++){
       l = l + Math.pow(2,i);
       s = s + "2";
    }
    Double D = new Double(l);
    this.setEditFlag(D.intValue());
    colTypeFlag = new Long(s);

    buildTable();
  }

  public void showme(){
    FatePane_EditCellThread ecT = new FatePane_EditCellThread(this);
    ecT.setRow(0);
    ecT.setCol(0);
    ecT.start();
  }



  public boolean passAudit() {
      for (int j=1;j<fateTable.getColumnCount();j++){
         if (!fateTable.getModel().getValueAt(7,j).toString().equals("100") &&
             !fateTable.getModel().getValueAt(7,j).toString().equals("0")) return false;
      }
      return true;
  }

  void fateTable_keyPressed(KeyEvent e) {
    if (fateTable.isEditing()){
      fateTable.getCellEditor().stopCellEditing();
    }
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {

    }
  }

  void doAudit (){
  }

  void setDesc(MouseEvent e){
     int col = fateTable.columnAtPoint(new Point(e.getX(),e.getY()));
     if (col != lastCol){
        changeMaterialLabel(col);
        lastCol = col;
     }
  }

  void fateTable_mouseClicked(MouseEvent e) {

  }
}

class FatePane_EditCellThread extends Thread {
  FatePane parent;
  int row;
  int col;

  public FatePane_EditCellThread(FatePane parent){
     super("FatePane_EditCellThread");
     this.parent = parent;
  }

  public void setRow(int r){
     row = r;
  }

  public void setCol(int c){
     col = c;
  }

  public void run(){
    while (!parent.fateTable.isShowing()){};
    while (!parent.fateTable.isVisible()){};

    try{
     sleep((long)1000);
    } catch (Exception e){};

    parent.fateTable.setRowSelectionInterval(row,row);
    parent.fateTable.setColumnSelectionInterval(col,col);

    if (parent.fateTable.editCellAt(row,col)){
      parent.fateTable.requestFocus();
      MouseEvent ev = new MouseEvent(parent.fateTable.getEditorComponent(),MouseEvent.MOUSE_CLICKED,(new Date()).getTime()+100,InputEvent.BUTTON1_MASK,5,5,2,false);
      parent.fateTable.getEditorComponent().dispatchEvent(ev);
      parent.fateTable.getEditorComponent().requestFocus();
    }
    this.stop();
  }
}


class FatePane_FateTable extends CmisTable{

       public FatePane_FateTable(){
         super();
       }

       public FatePane_FateTable(Object[][] o1, Object[] o2){
         super(o1,o2);
       }

       public FatePane_FateTable(TableModel t){
         super(t);
       }

       public void editingStopped(ChangeEvent e){
           int c = getEditingColumn();
           int r = getEditingRow();
           if (c<0 || r<0) {
                super.editingStopped(e);
                return;
           }
           if (getModel().getValueAt(r,c) == null) {
                  getModel().setValueAt(new Integer(0),r,c);
                  super.editingStopped(e);
                  return;
           }

           String bef = getModel().getValueAt(r,c).toString();
           super.editingStopped(e);
           String aft = getModel().getValueAt(r,c).toString();
           boolean pass = false;
           try {
              Integer t = new Integer(aft);
              if (t.intValue()>=0 && t.intValue()<=100) pass = true;
           } catch (Exception e2){ };
           if (!pass){
              GenericDlg err;
              String no = new String("Please enter a number (0-100)");
              err = new GenericDlg(new Frame(),"Incorrect Format",true);
              err.setMsg(no);
              err.show();

              getModel().setValueAt(new Integer(bef),r,c);
              return;
           }
           int sum = 0;
           for (int j=0;j<7;j++){
             Object tmp = this.getModel().getValueAt(j,c);
             if (tmp!=null){
                   try {
                      Integer tmp2 = new Integer(tmp.toString());
                      sum = sum + tmp2.intValue();
                   } catch (Exception e2){ // don't care
                   }
             }
             this.getModel().setValueAt(new Integer(sum),7,c);
           }
           this.setAlign();
       }

       public void setAlign() {
        DefaultTableCellRenderer DCR;
        TableColumn TCol;
        for (int i=1;i<getColumnCount();i++){
            String S = this.getColumnName(i);
            DCR = new DefaultTableCellRenderer();
            DCR.setHorizontalAlignment(JLabel.RIGHT);
            TCol = this.getColumn(S);
            TCol.setCellRenderer(DCR);
        }
       }

       public boolean isCellEditable(int r, int c){
              if (r==7) return false;
              if (c==0) return false;
              return true;
       }

}




























