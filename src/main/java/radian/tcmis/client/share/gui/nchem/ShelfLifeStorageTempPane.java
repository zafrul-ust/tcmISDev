//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Your description

package radian.tcmis.client.share.gui.nchem;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.beans.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;

public class ShelfLifeStorageTempPane extends JPanel {
  CmisApp cmis=null;

  StaticJLabel shelfLifeL = new StaticJLabel("Shelf life *: ");
  JTextField qtyT = new JTextField("");
  JComboBox shelfLifeUnitC = new JComboBox();
  StaticJLabel fromL = new StaticJLabel("from *: ");
  JRadioButton rRB = new JRadioButton(" Date of Receipt");
  JRadioButton mfgRB = new JRadioButton(" Date of Mfg.");
  StaticJLabel storageL = new StaticJLabel("at Storage temp *: ");
  JComboBox storageTempC = new JComboBox();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  ButtonGroup typeGroup = new ButtonGroup();
  boolean dataLoaded = false;
  StaticJLabel labelColorL = new StaticJLabel("Color *: ");
  JComboBox labelColorC = new JComboBox();
  boolean showLabelColor = false;

  public ShelfLifeStorageTempPane(boolean showLabelColor) {
    try  {
      this.showLabelColor = showLabelColor;
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  } //end of method

  public void setCmis(CmisApp cmis){
     this.cmis = cmis;
  } //end of method

  public void setDataLoaded(boolean b) {
    dataLoaded = b;
  }

  private void jbInit() throws Exception {
    qtyT.setMaximumSize(new Dimension(80, 21));
    qtyT.setMinimumSize(new Dimension(80, 21));
    qtyT.setPreferredSize(new Dimension(80, 21));
    shelfLifeUnitC.setMaximumSize(new Dimension(75, 21));
    shelfLifeUnitC.setMinimumSize(new Dimension(75, 21));
    shelfLifeUnitC.setPreferredSize(new Dimension(75, 21));

    storageTempC.setMaximumSize(new Dimension(90, 21));
    storageTempC.setMinimumSize(new Dimension(90, 21));
    storageTempC.setPreferredSize(new Dimension(90, 21));

    labelColorC.setMaximumSize(new Dimension(90, 21));
    labelColorC.setMinimumSize(new Dimension(90, 21));
    labelColorC.setPreferredSize(new Dimension(90, 21));

    jPanel1.setMaximumSize(new Dimension(280, 85));
    jPanel1.setMinimumSize(new Dimension(280, 85));
    jPanel1.setPreferredSize(new Dimension(280, 85));
    jPanel1.setLayout(gridBagLayout2);
    this.setMaximumSize(new Dimension(280, 85));
    this.setMinimumSize(new Dimension(280, 85));
    this.setPreferredSize(new Dimension(280, 85));
    this.setLayout(borderLayout1);
    this.add(jPanel1,null);
    jPanel1.add(shelfLifeL, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(qtyT, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 60, 0, 0), 0, 0));
    jPanel1.add(shelfLifeUnitC, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 150, 0, 0), 0, 0));

    jPanel1.add(fromL, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(rRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 40, 0, 0), 0, 0));
    jPanel1.add(mfgRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 150, 0, 0), 0, 0));

    jPanel1.add(storageL, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(storageTempC, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 100, 0, 0), 0, 0));
    if (showLabelColor) {
      jPanel1.add(labelColorL, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
      jPanel1.add(labelColorC, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
          , GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 50, 0, 0), 0, 0));
    }

    //add radio button
    rRB.setSelected(true);
    mfgRB.setSelected(false);
    typeGroup.add(rRB);
    typeGroup.add(mfgRB);
    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  } //end of method

  public void setPeriod(Vector p){
     p.removeElement("months");
     p.removeElement("years");
     shelfLifeUnitC = ClientHelpObjs.loadChoiceFromVector(shelfLifeUnitC,p,false) ;
     shelfLifeUnitC.insertItemAt("Select One",0);
     shelfLifeUnitC.setSelectedIndex(0);
     shelfLifeUnitC.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        showBasis();
      }
    });
    shelfLifeUnitC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent evt) {
        showBasis();
      }
    });
  }

  public void showBasis(){
    String tempShelf = shelfLifeUnitC.getSelectedItem().toString();
    if (tempShelf.equalsIgnoreCase("Select One") || tempShelf.equalsIgnoreCase("Indefinite")) {
      fromL.setVisible(false);
      rRB.setVisible(false);
      mfgRB.setVisible(false);
      qtyT.setVisible(false);
    }else {
      fromL.setVisible(true);
      rRB.setVisible(true);
      mfgRB.setVisible(true);
      qtyT.setVisible(true);
    }
  } //end of method

  public String auditShelfLife() {
    String msg = null;
    String tempShelf = shelfLifeUnitC.getSelectedItem().toString();
    String num = qtyT.getText();
    if (tempShelf.equalsIgnoreCase("Select One")) {
      msg = "Please select a shelf life's period";
      return msg;
    }
    if (!tempShelf.equalsIgnoreCase("Indefinite")) {
      try {
        Integer n = new Integer(num);
        if (n.intValue() <= 0) {
          msg = "Please enter only whole number for shelf life.";
          return msg;
        }
      } catch(Exception e) {
        msg = "Please enter only whole number for shelf life.";
        return msg;
      }
    }
    return msg;
  } //end of method

  void allowEdit(boolean b) {
    this.qtyT.setEnabled(b);
    this.shelfLifeUnitC.setEnabled(b);
    this.rRB.setEnabled(b);
    this.mfgRB.setEnabled(b);
    this.storageTempC.setEnabled(b);
    this.labelColorC.setEnabled(b);
  }

  public void setTemperature(Vector p){
    storageTempC = ClientHelpObjs.loadChoiceFromVector(storageTempC,p,false) ;
    if (!storageTempC.getItemAt(0).toString().equalsIgnoreCase("Select a Temperature"))
      storageTempC.insertItemAt("Select a Temperature",0);
    storageTempC.setSelectedIndex(0);
  } //end of method

  public String auditTemp() {
    String msg = null;
    if (storageTempC.getSelectedItem().toString().equalsIgnoreCase("Select a Temperature")) {
      msg = "Please select a storage temperature.";
    }
    return msg;
  } //end of method

  public void setLabelColor(Vector p){
    labelColorC = ClientHelpObjs.loadChoiceFromVector(labelColorC,p,false) ;
    if (!labelColorC.getItemAt(0).toString().equalsIgnoreCase("Select a Color"))
      labelColorC.insertItemAt("Select a Color",0);
    labelColorC.setSelectedIndex(0);
  } //end of method

  public String auditLabelColor() {
    String msg = null;
    if (labelColorC.getSelectedItem().toString().equalsIgnoreCase("Select a Color")) {
      msg = "Please select a color.";
    }
    return msg;
  } //end of method


  void setDefaultShelfLifeStorageTemp(Hashtable componentH) {
    qtyT.setText((String) componentH.get("SHELF_LIFE"));
    if (!BothHelpObjs.isBlankString((String) componentH.get("SHELF_LIFE_UNIT"))) {
      shelfLifeUnitC.setSelectedItem((String) componentH.get("SHELF_LIFE_UNIT"));
    }
    if ("M".equalsIgnoreCase((String) componentH.get("SHELF_LIFE_BASIS"))){
      mfgRB.setSelected(true);
      rRB.setSelected(false);
    } else {
      mfgRB.setSelected(false);
      rRB.setSelected(true);
    }
    if (!BothHelpObjs.isBlankString((String) componentH.get("STORAGE_TEMP"))){
       storageTempC.setSelectedItem((String) componentH.get("STORAGE_TEMP"));
    } else {
       storageTempC.setSelectedIndex(0);
    }
    //label color
    if (showLabelColor) {
      if (!BothHelpObjs.isBlankString((String) componentH.get("LABEL_COLOR"))){
         labelColorC.setSelectedItem((String) componentH.get("LABEL_COLOR"));
      }
    }
  } //end of method

  void saveShelfLifeStorageTemp(Hashtable h) {
    h.put("shelfT",qtyT.getText());
    h.put("shelfC",BothHelpObjs.makeBlankFromNull((String)shelfLifeUnitC.getSelectedItem()));
    if (mfgRB.isSelected()){
      h.put("shelfBasis","M");
    } else {
      h.put("shelfBasis","R");
    }
    h.put("tempC",BothHelpObjs.makeBlankFromNull((String)storageTempC.getSelectedItem()));
    if (showLabelColor) {
      h.put("labelColor",BothHelpObjs.makeBlankFromNull((String)labelColorC.getSelectedItem()));
    }else {
      h.put("labelColor","");
    }
  } //end of method

} //end of class

