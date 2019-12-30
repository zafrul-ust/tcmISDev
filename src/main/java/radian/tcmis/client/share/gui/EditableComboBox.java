
//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;

import java.awt.event.*;

import java.util.*;
import javax.swing.*;

import radian.tcmis.client.share.helpers.*;

public abstract class EditableComboBox extends JComboBox {

  Vector items = new Vector();

  boolean loading = false;
  boolean loaded = false;
  boolean reloading = false;

  public EditableComboBox() {
    super();
    try  {
      loading = true;
      jbInit();
      loading = false;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  void insertEditedItem(String s){
    loading = true;
    boolean fl = false;
    if(items.isEmpty() || !items.contains(s)) {
      items.addElement(s);
      addItem(s.toUpperCase());
      this.setSelectedItem(s);
      this.revalidate();
    }
    loading = false;
  }

  void loadThis(Vector v) {
    loading = true;
    if (this.getItemCount() > 0) this.removeAllItems();
    ClientHelpObjs.loadChoiceFromVector(this,v);
    ClientHelpObjs.choiceSort(this);
    setSelectedIndex(0);
    loading = false;
  }

  private void jbInit() throws Exception {
    this.addActionListener(new EditableComboBoxActionListener(this));
    this.setEditable(true);
  }

  abstract void  doAction();
}

class EditableComboBoxActionListener implements ActionListener{
  EditableComboBox fc;
  public EditableComboBoxActionListener(EditableComboBox fc) {
    this.fc = fc;
  }
   public void actionPerformed(ActionEvent e){
     fc.doAction();
   }
}
