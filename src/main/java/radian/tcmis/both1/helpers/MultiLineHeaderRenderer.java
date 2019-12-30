package radian.tcmis.both1.helpers;

/* (swing1.1beta3) */

import java.io.*;
import java.util.*;
//import java.awt.*;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.Border;


/**
 * @version 1.0 11/09/98
 */
public class MultiLineHeaderRenderer extends JList implements TableCellRenderer {
  public MultiLineHeaderRenderer(Color fore, Color back, Border border) {
    setOpaque(false);
    setForeground(fore);
    setBackground(back);
    //setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setBorder(border);
    ListCellRenderer renderer = getCellRenderer();
    ((JLabel)renderer).setHorizontalAlignment(JLabel.CENTER);
    setCellRenderer(renderer);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
    setFont(table.getFont());
    String str = (value == null) ? "" : value.toString();
    BufferedReader br = new BufferedReader(new StringReader(str));
    String line;
    Vector v = new Vector();
    try {
      while ((line = br.readLine()) != null) {
        v.addElement(line);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    setListData(v);
    return this;
  }
}

