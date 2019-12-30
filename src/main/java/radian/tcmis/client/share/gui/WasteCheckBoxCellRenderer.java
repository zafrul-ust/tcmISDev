package radian.tcmis.client.share.gui;


import java.awt.*;
import javax.swing.*;

import javax.swing.table.*;


public final class WasteCheckBoxCellRenderer extends JCheckBox implements TableCellRenderer {

  public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {
    int ageCol=-1;
    for (int i=0;i<table.getModel().getColumnCount();i++){
      if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
        if (table.getColumnName(i).equalsIgnoreCase("Age Warning")){
          ageCol = i;
          break;
        }
      }
    }
    /*
    int reqCanCol=-1;
    for (int i=0;i<table.getModel().getColumnCount();i++){
      if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
        if (table.getColumnName(i).equalsIgnoreCase("cancel requested")){
          reqCanCol = i;
          break;
        }
      }
    }*/

    boolean crit = ((Boolean)table.getValueAt(row,column)).booleanValue();
    boolean sel = false;
    boolean age = (ageCol>-1?((Boolean)table.getValueAt(row,ageCol)).booleanValue():false);
    //boolean reqCancel= (reqCanCol>-1?((Boolean)table.getValueAt(row,reqCanCol)).booleanValue():false);


    int[] is = table.getSelectedRows();
    for(int i=0;i<is.length;i++){
      if(is[i] == row) {
        sel = true;
        break;
      }
    }

    if (sel) {
      if(crit){
        setBackground(Color.yellow);
        setForeground(table.getForeground());
      }else if (age) {
        setBackground(Color.red);
        setForeground(table.getForeground());
      }else{
        setBackground(new Color(204,204,255));
        setForeground(table.getForeground());
      }
    }else {
      if (age){
        setBackground(Color.pink);
        setForeground(table.getForeground());
      } else if(crit){
        setBackground(new Color(255,255,185));
        setForeground(table.getForeground());
      } else{
        setBackground(Color.white);
        setForeground(table.getForeground());
      }
    }

    setOpaque(true);
    this.setSelected(crit);
    setHorizontalAlignment(JLabel.CENTER);
    return this;
  }
}


/*
    if(sel && crit && reqCancel){
      setBackground(Color.yellow);
      setForeground(table.getForeground());
    } else if(sel && crit && age){
      setBackground(Color.red);
      setForeground(table.getForeground());
    } else if (reqCancel){
      setBackground(Color.orange);
      setForeground(table.getForeground());
    } else if (age && sel){
      setBackground(Color.red);
      setForeground(table.getForeground());
    } else if (age){
      setBackground(Color.pink);
      setForeground(table.getForeground());
    } else if(sel && crit){
      setBackground(Color.orange);
      setForeground(table.getForeground());
    } else if (crit){
      setBackground(Color.yellow);
      setForeground(Color.black);
      //setForeground(table.getForeground());
    } else if(sel){
      setBackground(new Color(204,204,255));
      setForeground(table.getForeground());
    } else{
      setBackground(Color.white);
      setForeground(Color.black);
      //setForeground(table.getForeground());
    }
*/



