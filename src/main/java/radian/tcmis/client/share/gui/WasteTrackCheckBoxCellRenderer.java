package radian.tcmis.client.share.gui;


import java.awt.*;
import javax.swing.*;

import javax.swing.table.*;


public final class WasteTrackCheckBoxCellRenderer extends JCheckBox implements TableCellRenderer {

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

    boolean sel = false;
    boolean age = (ageCol>-1?((Boolean)table.getValueAt(row,ageCol)).booleanValue():false);
    boolean transferred = ((Boolean)table.getValueAt(row,column)).booleanValue();

    int[] is = table.getSelectedRows();
    for(int i=0;i<is.length;i++){
      if(is[i] == row) {
        sel = true;
        break;
      }
    }

    if(sel) {
      if (age){
        setBackground(Color.red);
        setForeground(table.getForeground());
      } else {
        setBackground(table.getSelectionBackground());
        setForeground(table.getForeground());
      }
    } else{
      if (age){
        setBackground(Color.pink);
        setForeground(table.getForeground());
      } else {
        setBackground(Color.white);
        setForeground(table.getForeground());
      }
    }

    setOpaque(true);
    setSelected(transferred);
    setHorizontalAlignment(JLabel.CENTER);
    return this;
  }
}



