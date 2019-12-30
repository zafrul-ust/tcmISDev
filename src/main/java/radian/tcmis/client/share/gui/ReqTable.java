

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;

import java.awt.*;
import radian.tcmis.client.share.gui.Main;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;


public class ReqTable extends CmisTable{
       Main parent;
       RequestsWin reqWin;
       public ReqTable(TableModel t){
         super(t);
         setRenderers();
       }
       public void setParent(Main p){
              parent = p;
       }
       public void setReqWin(RequestsWin reqWin){
         this.reqWin = reqWin;
       }
       public Main getFrame(){
              return parent;
       }
       public void editingStopped(ChangeEvent e){
              int myRow = getEditingRow();
              int myCol = getEditingColumn();
              if (getModel().getValueAt(getEditingRow(),getEditingColumn()) == null) {
                  super.editingStopped(e);
                  return;
              }
              super.editingStopped(e);
              reqWin.tableEditingStopped(myRow,myCol);
       }

       public void setParentFrame(Main m){
          parent = m;
       }

       public void setRenderers() {
         setDefaultRenderer(String.class, new ReqTable_TableRenderer());
         setDefaultRenderer(Integer.class, new ReqTable_TableRenderer());
         setDefaultRenderer(Double.class, new ReqTable_TableRenderer());
         setDefaultRenderer(Boolean.class, new CheckBoxCellRenderer());
         setDefaultRenderer(JComboBox.class,new ReqTable_TableRenderer());
       }
}

class ReqTable_TableRenderer extends TcmisCellRenderer {
  public Component getTableCellRendererComponent(
                          JTable table, Object value,
                          boolean isSelected, boolean hasFocus,
                          int row, int column) {

    super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
    int bolCol = 0;
    boolean crit = false;
    for (int i=0;i<table.getModel().getColumnCount();i++){
      if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
        bolCol = i;
        break;
      }
    }
    crit = ((Boolean)table.getValueAt(row,bolCol)).booleanValue();

    boolean sel = false;
    int[] is = table.getSelectedRows();
    for(int i=0;i<is.length;i++){
      if(is[i] == row) {
        sel = true;
        break;
      }
    }

    setBackground(Color.white);
    if(sel && crit){
      setBackground(Color.red);
    }else{
       if (crit){
        setBackground(Color.pink);
       }else{
          if(sel){
            setBackground(new Color(204,204,255));
          }else{
            setBackground(Color.white);
          }
       }
    }
    setForeground(table.getForeground());

    if(value.getClass().getName().indexOf("String") > 0) {
      if(value.toString().equals("+")){
        setHorizontalAlignment(JLabel.CENTER);
      }else{
        this.setHorizontalAlignment(JLabel.LEFT);
      }
    }else if(table.getColumnName(column).equalsIgnoreCase("PO")){
      Component comp = super.getTableCellRendererComponent(table, value,
                          isSelected,hasFocus,
                          row,column);

      ((JLabel) comp).setText(((JComboBox)value).getSelectedItem().toString());
      return this;
    }else{
      this.setHorizontalAlignment(JLabel.LEFT);
    }
    return this;
  }
}

class RequestsWin_BoxCellRenderer extends  DefaultTableCellRenderer {

  public Component getTableCellRendererComponent(
                          JTable table, Object value,
                          boolean isSelected, boolean hasFocus,
                          int row, int column) {

      Component comp = super.getTableCellRendererComponent(table, value,
                          isSelected,hasFocus,
                          row,column);
      ((JLabel) comp).setText(((JComboBox)value).getSelectedItem().toString());

      int bolCol = 0;
      for (int i=0;i<table.getModel().getColumnCount();i++){
        if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
          bolCol = i;
          break;
        }
      }
      if (isSelected) {
        setForeground(table.getSelectionForeground());
        setBackground(table.getSelectionBackground());
      }else if (((Boolean) table.getModel().getValueAt(row,bolCol)).booleanValue()){
        setBackground(Color.red);
        setForeground(table.getForeground());
      }else {
        setForeground((new JLabel()).getForeground());
        setBackground(table.getBackground());
      }
      return comp;
   }
}


























