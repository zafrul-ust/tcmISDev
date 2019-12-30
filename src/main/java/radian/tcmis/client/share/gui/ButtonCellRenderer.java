package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;

import javax.swing.table.*;
import javax.swing.border.*;



public final class ButtonCellRenderer extends JButton implements TableCellRenderer {

  public ButtonCellRenderer() {
    setOpaque(true);
  }

  public Component getTableCellRendererComponent(
                          JTable table, Object value,
                          boolean isSelected, boolean hasFocus,
                          int row, int column) {
      boolean sel = false;
      int[] is = table.getSelectedRows();
      for(int i=0;i<is.length;i++){
        if(is[i] == row) {
          sel = true;
          break;
        }
      }
      setBorder(new BevelBorder(BevelBorder.RAISED));
      setHorizontalAlignment(JLabel.CENTER);
      if (sel) {
        setForeground(table.getSelectionForeground());
        setBackground(table.getSelectionBackground());
      }else {
        //System.out.println("row: "+row);

        if (row % 2 == 0) {
          setForeground(Color.black);
          setBackground(new Color(255, 255, 200));
        }else {
          setForeground(Color.black);
          setBackground(Color.white);
        }
      }
      setFont(new Font("Dialog", 0, 10));
      setText(value.toString());

      return this;
  }
}



