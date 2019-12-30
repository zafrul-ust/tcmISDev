package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


class ComboBoxEnableRenderer extends JLabel implements ListCellRenderer {

  public ComboBoxEnableRenderer() {
    setOpaque(true);
    setBorder(new EmptyBorder(1, 1, 1, 1));
  }

  public Component getListCellRendererComponent( JList list,
         Object value, int index, boolean isSelected, boolean cellHasFocus) {
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    if (! ((ComboBoxCanEnable)value).isEnabled()) {
      setBackground(list.getBackground());
      setForeground(UIManager.getColor("Label.disabledForeground"));
    }
    setFont(list.getFont());
    setText((value == null) ? "" : value.toString());
    return this;
  }
}




