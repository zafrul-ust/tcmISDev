
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Your description

package radian.tcmis.client.share.gui.nchem;
import java.awt.*;
import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;
import java.awt.event.*;

public class SpecPane extends JPanel {
  StaticJLabel specIdL = new StaticJLabel();
  JTextField specIdT = new JTextField();
  StaticJLabel revL = new StaticJLabel();
  JTextField specRevT = new JTextField();
  StaticJLabel amendL = new StaticJLabel();
  JTextField specAmenT = new JTextField();
  StaticJLabel titleL = new StaticJLabel();
  JTextField specTitleT = new JTextField();
  JButton specB = new JButton();
  String specid="";
  String itemid="";
  CmisApp cmis=null;
  StaticJLabel standardMfgCertL = new StaticJLabel();
  boolean standardMfgCert = false;
  String specUrl = "";
  JCheckBox coc = new JCheckBox("CoC");
  JCheckBox coa = new JCheckBox("CoA");
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  String specLibrary = "";

  public SpecPane(boolean standardMfgCert) {
    try  {
      this.standardMfgCert = standardMfgCert;
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);

    if (standardMfgCert) {
      specTitleT.setText("Std Mfg Cert");
      standardMfgCertL.setText("Standard Manufacturer Cert");
      this.add(standardMfgCertL, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      this.add(coc, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 35, 5, 5), 0, 0));
      this.add(coa, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 35, 5, 5), 0, 0));
    }else {
      specIdL.setText("ID: *");
      specIdT.setMaximumSize(new Dimension(75, 21));
      specIdT.setMinimumSize(new Dimension(75, 21));
      specIdT.setPreferredSize(new Dimension(75, 21));
      revL.setText("Revision:");
      specRevT.setMaximumSize(new Dimension(65, 21));
      specRevT.setMinimumSize(new Dimension(65, 21));
      specRevT.setPreferredSize(new Dimension(65, 21));
      amendL.setText("Amendment:");
      specAmenT.setMaximumSize(new Dimension(75, 21));
      specAmenT.setMinimumSize(new Dimension(75, 21));
      specAmenT.setPreferredSize(new Dimension(75, 21));
      titleL.setText("Title: *");
      specB.setText("View");
      specB.setMaximumSize(new Dimension(75, 21));
      specB.setMinimumSize(new Dimension(75, 21));
      specB.setPreferredSize(new Dimension(75, 21));
      specB.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          specB_actionPerformed(e);
        }
      });
      specB.setEnabled(!BothHelpObjs.isBlankString(specUrl));

      this.add(specIdL, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 2), 0, 0));
      this.add(specIdT, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 2), 0, 0));
      this.add(revL, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 2));
      this.add(specRevT, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 2), 0, 0));
      this.add(amendL, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      this.add(specAmenT, new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
      this.add(titleL, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 0, 0, 0), 0, 0));
      this.add(specTitleT, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 33, 0, 0), 0, 0));
      this.add(specB, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 0, 0, 0), 0, 0));
      this.add(coc, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 20, 0, 0), 0, 0));
      this.add(coa, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0
               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 70, 0, 0), 0, 0));
      specTitleT.setText(" ");
    }
    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  } //end of method

  public void allowEdit(boolean f) {
    specTitleT.setEnabled(f);
    specB.setEnabled(f);
    specRevT.setEnabled(f);
    specAmenT.setEnabled(f);
    specIdT.setEnabled(f);
    coc.setEnabled(f);
    coa.setEnabled(f);
  } //end of method

  void specB_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     try {
       if (!BothHelpObjs.isBlankString(specUrl)) {
         // From the stand alone application
         new URLCall(specUrl, cmis, false);
       }
     } catch (Exception ee) {
       ee.printStackTrace();
     }
  }

  public void setSpecID(String i){
     specid = i;
  }

  public String getSpecID() {
    return specid;
  }

  public void setItemID(String i){
     itemid = i;
  }

  public void setCmis(CmisApp cmis){
     this.cmis = cmis;
  }

  public void setSpecUrl(String s) {
    this.specUrl = s;
  }

  public String getSpecUrl() {
    return specUrl;
  }

  public void setSpecLibrary(String s) {
    this.specLibrary = s;
  }

  public String getSpecLibrary() {
    return specLibrary;
  }

} //end of class

