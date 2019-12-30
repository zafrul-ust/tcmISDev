package radian.tcmis.client.share.gui;

import java.awt.event.*;
import javax.swing.*;

class ComboBoxEnableListener implements ActionListener {
  JComboBox combo;
  Object currentItem;

  ComboBoxEnableListener(JComboBox combo) {
    this.combo  = combo;
    combo.setSelectedIndex(0);
    currentItem = combo.getSelectedItem();
  }

  public void actionPerformed(ActionEvent e) {
    Object tempItem = combo.getSelectedItem();
    if (! ((ComboBoxCanEnable)tempItem).isEnabled()) {
      combo.setSelectedItem(currentItem);
    } else {
      currentItem = tempItem;
    }
  }
}




