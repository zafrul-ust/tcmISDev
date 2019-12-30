package radian.tcmis.client.share.gui;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */
import java.awt.*;
import javax.swing.*;

public class JComboBoxCellRenderer extends TcmisCellRenderer {
  public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus,int row, int column) {
      Component comp = super.getTableCellRendererComponent(table, value,isSelected,hasFocus,row,column);
      try {
        if(((JComboBox)value).getSelectedItem()!=null){
          ((JLabel) comp).setText(((JComboBox)value).getSelectedItem().toString());
        } else {
          ((JLabel) comp).setText(((JComboBox)value).getItemAt(0).toString());
        }
      } catch (Exception e){
        ((JLabel) comp).setText("");
      }
    return this;
  }
}
