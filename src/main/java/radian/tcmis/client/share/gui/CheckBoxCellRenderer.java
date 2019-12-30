package radian.tcmis.client.share.gui;


import java.awt.*;
import javax.swing.*;

import javax.swing.table.*;


public final class CheckBoxCellRenderer extends JCheckBox implements TableCellRenderer {

  public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {
    setOpaque(true);

    int canCol=-1;
    int reqCanCol=-1;
    boolean dpasCombo = false;
    int dpasCol=-1;
    int deleteCol = -1;
    int criticalCol = -1;
    for (int i=0;i<table.getModel().getColumnCount();i++){
      if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
        if (table.getColumnName(i).equalsIgnoreCase("canceled")){
          canCol = i;
          break;
        }else if (table.getColumnName(i).equalsIgnoreCase("cancel requested")){
          reqCanCol = i;
          break;
        }else if (table.getColumnName(i).equalsIgnoreCase("dpas")){
          dpasCol = i;
          dpasCombo = false;
          break;
        }
      }else if (table.getColumnClass(i).getName().indexOf("JComboBox") > 0){
        if (table.getColumnName(i).equalsIgnoreCase("dpas")){
          dpasCol = i;
          dpasCombo = true;
          break;
        }
      }else if (table.getColumnName(i).equalsIgnoreCase("Delete")){
        deleteCol = i;
        break;
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
    }
    boolean dpasCombo = false;
    int dpasCol=-1;
    for (int i=0;i<table.getModel().getColumnCount();i++){
      if (table.getColumnClass(i).getName().indexOf("JComboBox") > 0){
        if (table.getColumnName(i).equalsIgnoreCase("dpas")){
          dpasCol = i;
          dpasCombo = true;
          break;
        }
      }
    }
    for (int i=0;i<table.getModel().getColumnCount();i++){
      if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
        if (table.getColumnName(i).equalsIgnoreCase("dpas")){
          dpasCol = i;
          dpasCombo = false;
          break;
        }
      }
    }
    */

    boolean crit = ((Boolean)table.getValueAt(row,column)).booleanValue();
    boolean sel = false;
    boolean cancel= (canCol>-1?((Boolean)table.getValueAt(row,canCol)).booleanValue():false);
    boolean reqCancel= (reqCanCol>-1?((Boolean)table.getValueAt(row,reqCanCol)).booleanValue():false);
    boolean deleted= (deleteCol>-1?((Boolean)table.getValueAt(row,deleteCol)).booleanValue():false);

    int[] is = table.getSelectedRows();
    for(int i=0;i<is.length;i++){
      if(is[i] == row) {
        sel = true;
        break;
      }
    }

    boolean dpas = false;
    if (dpasCombo) {
      if (dpasCol > 0) {
        dpas = !((JComboBox)table.getModel().getValueAt(row,dpasCol)).getSelectedItem().toString().startsWith("No");
      }else {
        dpas = false;
      }
    }else {
      if(dpasCol > 0) {
        dpas = ((Boolean)table.getValueAt(row,dpasCol)).booleanValue();
      }else {
        dpas = false;
      }
    }

    if(sel) {
      if (dpas) {
        setBackground(Color.green);
        setForeground(table.getForeground());
       //System.out.println("Selected");
       // if a critical row is selected it should be red
      } else if(reqCancel) {
       //System.out.println(" req cancel");
        setBackground(Color.orange);
        setForeground(table.getForeground());
      } else if(cancel) {
        //System.out.println("cancel");
        setBackground(Color.black);
        setForeground(Color.white);
      } else if(deleted) {
        //System.out.println("deleted");
        setBackground(table.getSelectionBackground());
        setForeground(table.getSelectionForeground());
      } else if(crit) {   //this has to be next to last (else), because the same line can be both "something else" and critical and "something else" win
        //System.out.println("bol");
        setBackground(Color.red);
        setForeground(table.getForeground());
      } else{
        //System.out.println("non");
        setForeground(table.getSelectionForeground());
        setBackground(table.getSelectionBackground());
      }
    }else if (dpas) {
      setBackground(new Color(165,255,165));
      setForeground(table.getForeground());
    }else if(reqCancel) {
      //System.out.println("not Selected reqcancel");
      setBackground(Color.orange);
      setForeground(table.getForeground());
    } else if(cancel) {
      //System.out.println("not Selected cancel");
      setBackground(Color.lightGray);
      setForeground(table.getForeground());
    } else if(deleted) {
      //System.out.println("not Selected deleted");
      setBackground(new Color(220,220,100));
      setForeground(table.getForeground());
    }else if(crit) {        //this has to be next to last (else), because the same line can be both "something else" and critical and "something else" win
      // System.out.println("not Selected bol");
      setBackground(Color.pink);
      setForeground(table.getForeground());
    }else {
      //System.out.println("not Selected non");
      //setForeground(Color.black);
      //setBackground(Color.white);
      // currently it not smart enough to handle different back color from row to row
      if (deleteCol > 0) {
        setForeground(Color.black);
        setBackground(new Color(206,206,206));
        /*
        for (int i = 0; i < table.getRowCount(); i++) {
          if (i % 2 == 0) {
            setBackground(new Color(224,226,250));
          }else {
            setBackground(new Color(255,255,255));
          }
          setForeground(table.getForeground());
        }*/
      }else {
        //System.out.println("not Selected non");
        setForeground(Color.black);
        setBackground(Color.white);
      }
    }

    setOpaque(true);
    this.setSelected(crit);
    setHorizontalAlignment(JLabel.CENTER);
    return this;
  }
}



