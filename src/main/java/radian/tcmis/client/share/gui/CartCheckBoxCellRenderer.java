package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;

import javax.swing.table.*;


public final class CartCheckBoxCellRenderer extends JCheckBox implements TableCellRenderer {

  public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {

    boolean crit = ((Boolean)table.getValueAt(row,column)).booleanValue();
    boolean sel = false;

    int[] is = table.getSelectedRows();
    for(int i=0;i<is.length;i++){
      if(is[i] == row) {
        sel = true;
        break;
      }
    }

    if(sel) {
      if(crit) {
        //System.out.println("bol");
        setBackground(Color.red);
        setForeground(table.getForeground());
      } else{
        //System.out.println("non");
        setForeground(table.getSelectionForeground());
        setBackground(table.getSelectionBackground());
      }
    }else {
      if (crit) {
        setBackground(Color.pink);
        setForeground(table.getForeground());
      }else {
        //System.out.println("not Selected non");
        if ((row % 2) == 0) {
          setBackground(new Color(224,226,250));
          setForeground(Color.black);
        }else {
          setForeground(Color.black);
          setBackground(Color.white);
        }
      }
    }

    setOpaque(true);
    this.setSelected(crit);
    setHorizontalAlignment(JLabel.CENTER);
    return this;
  }
}


