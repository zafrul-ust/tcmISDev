package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;

import javax.swing.table.*;



public final class ColoredCellRender extends JTextArea implements TableCellRenderer {

        public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {

          for (int i = 0; i < table.getRowCount(); i++) {
            String tmp = (String)table.getModel().getValueAt(i,1);
            if ("S".equalsIgnoreCase(tmp)) {
                setBackground(table.getSelectionBackground());
                setForeground(Color.blue);
            }else if ("N".equalsIgnoreCase(tmp)) {
                setBackground(table.getSelectionBackground());
                setForeground(Color.green);
	          } else {
                setForeground(table.getForeground());
	              setBackground(table.getBackground());
            }
            setText((String)table.getModel().getValueAt(i,0));
          }
          setOpaque(true);
          this.repaint();
          return this;
        }

}


