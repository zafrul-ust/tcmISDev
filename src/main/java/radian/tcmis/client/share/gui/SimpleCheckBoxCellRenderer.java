package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public final class SimpleCheckBoxCellRenderer extends JCheckBox implements TableCellRenderer {
  public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {
    setOpaque(true);
    boolean sel = false;
    int[] is = table.getSelectedRows();
    for(int i=0;i<is.length;i++){
      if(is[i] == row) {
        sel = true;
        break;
      }
    }
    /*
    if (sel) {
      setBackground(Color.GREEN);
      setForeground(Color.blue);
      System.out.println("------- here selected");
    }else {
      AttributiveCellTableModel ctm = (AttributiveCellTableModel) table.getModel();
      ColoredCell cellAtt = (ColoredCell) ctm.getCellAttribute();
      int[] cols = new int[] {2};
      for (int i = 0; i < table.getRowCount(); i++) {
        int[] rows = new int[] {i};
        if (i % 2 == 0) {
          cellAtt.setBackground(new Color(224, 226, 250),rows,cols);
          cellAtt.setForeground(table.getForeground(),rows,cols);
        }else {
          cellAtt.setBackground(new Color(250, 250, 250),rows,cols);
          cellAtt.setForeground(table.getForeground(),rows,cols);
        }
      }
    }*/
    if (value instanceof JCheckBox) {
      this.setSelected( ( (JCheckBox) value).isSelected());
      System.out.println("------ here: "+row+":"+column+"-check");
    }else {
      System.out.println("------ here: "+row+":"+column+"-"+value.toString());
    }
    setHorizontalAlignment(JLabel.CENTER);
    return this;
  }
}

/*
package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.beans.*;

public class SimpleCheckBoxCellRenderer extends TcmisCellRenderer {
  public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus,int row, int column) {
      Component comp = super.getTableCellRendererComponent(table, value,isSelected,hasFocus,row,column);
      try {
        ((JCheckBox)value).setSelected(((JCheckBox)value).isSelected());
        /*
        if(((JComboBox)value).getSelectedItem()!=null){
          (((JLabel) comp).setText(((JComboBox)value).getSelectedItem().toString()));
        } else {
          (((JLabel) comp).setText(((JComboBox)value).getItemAt(0).toString()));
        } /
      } catch (Exception e){
        ((JCheckBox)value).setSelected(false);
      }
    return this;
  }
}
*/




