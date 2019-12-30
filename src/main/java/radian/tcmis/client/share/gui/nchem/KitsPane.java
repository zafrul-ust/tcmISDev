package radian.tcmis.client.share.gui.nchem;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

import java.util.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;

import radian.tcmis.client.share.helpers.*;
import java.awt.event.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class KitsPane extends JPanel {
  JPanel topP = new JPanel();
  JPanel bottomP = new JPanel();
  NextManufacturer manufT = new NextManufacturer();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  JButton manufB = new JButton();
  JTextField matDescT = new JTextField();
  StaticJLabel jLabel3 = new StaticJLabel();
  JTextField manufPartT = new JTextField();
  StaticJLabel jLabel4 = new StaticJLabel();
  JTextField sizeT = new JTextField();
  StaticJLabel jLabel5 = new StaticJLabel();
  JComboBox unitC = new JComboBox();
  StaticJLabel jLabel6 = new StaticJLabel();
  JComboBox packC = new JComboBox();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  GridBagLayout gridBagLayout3 = new GridBagLayout();

  CmisApp cmis = null;
  JButton msdsB = new JButton();
  String mat_id = null;
  StaticJLabel jLabel7 = new StaticJLabel();
  JTextField tradeT = new JTextField();
  StaticJLabel jLabel8 = new StaticJLabel();
  JTextField gradeT = new JTextField();
  JTextField dimT = new JTextField();
  JPanel dimensionP = new JPanel();
  StaticJLabel jLabel10 = new StaticJLabel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  StaticJLabel jLabel9 = new StaticJLabel();
  JTextField netwtT = new JTextField();
  JComboBox netwtC = new JComboBox();

  JTextField customerMSDST = new JTextField();
  StaticJLabel cMSDSL = new StaticJLabel();

  Vector netwt=null;
  Hashtable unit = null;
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JCheckBox sampleSizingCheck = new JCheckBox();
  JTextField bundleT = new JTextField();
  StaticJLabel bundleL = new StaticJLabel();
  StaticJLabel byL = new StaticJLabel();
  StaticJLabel mfgStorageL = new StaticJLabel();
  String requestorEditFacMSDSID = "NO";

  public KitsPane(String requestorEditFacMSDSID) {
    this.requestorEditFacMSDSID = requestorEditFacMSDSID;
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public KitsPane() {
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setCmis(CmisApp c){
    cmis = c;
    manufT.setCmisApp(cmis);
  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout4);
    topP.setLayout(gridBagLayout1);
    bottomP.setLayout(gridBagLayout3);
    jLabel1.setText("Manufacturer - 240 chars max: *");
    jLabel2.setText("Material Description - 240 chars max: * (Include all information such as color, type, " +
    "etc for buyer to order)");
    manufB.setFont(new java.awt.Font("Dialog", 0, 10));
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/search_small.gif","Search");
    manufB.setMaximumSize(new Dimension(35, 19));
    manufB.setMinimumSize(new Dimension(35, 19));
    manufB.setPreferredSize(new Dimension(35, 19));
    manufB.setIcon(ss);
    manufB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        manufB_actionPerformed(e);
      }
    });
    jLabel3.setText("Manufacturer Part Number:");
    jLabel4.setText("Size: *");
    jLabel5.setText("Unit: *");
    jLabel6.setText("Package Style: *");

    msdsB.setText("MSDS");
    msdsB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        msdsB_actionPerformed(e);
      }
    });
    msdsB.setRolloverEnabled(true);
    msdsB.setEnabled(false);
    msdsB.setFont(new java.awt.Font("Dialog", 0, 10));
    msdsB.setMaximumSize(new Dimension(87, 21));
    msdsB.setMinimumSize(new Dimension(87, 21));
    msdsB.setPreferredSize(new Dimension(87, 21));              //5-09-01
    ss = ResourceLoader.getImageIcon("images/button/open.gif","open");
    msdsB.setIcon(ss);

    jLabel7.setText("Manufacturer's Trade name - 100 chars max: * (from MSDS)");
    jLabel8.setText("Grade:");
    jLabel10.setText("Net Size:*");
    dimensionP.setLayout(gridBagLayout2);
    jLabel9.setText("Dimension:");
    netwtC.setFont(new java.awt.Font("Dialog", 0, 10));
    netwtC.setMaximumSize(new Dimension(32767, 21));
    netwtC.setMinimumSize(new Dimension(70, 21));
    netwtC.setPreferredSize(new Dimension(128, 21));
    unitC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        unitC_itemStateChanged(e);
      }
    });
    customerMSDST.setMaximumSize(new Dimension(130, 21));
    customerMSDST.setMinimumSize(new Dimension(130, 21));
    customerMSDST.setPreferredSize(new Dimension(130, 21));
    if ("OPTIONAL".equalsIgnoreCase(requestorEditFacMSDSID)) {
      customerMSDST.setEnabled(true);
      cMSDSL.setText("Your MSDS #:");
    }else if ("MANDATORY".equalsIgnoreCase(requestorEditFacMSDSID)) {
      customerMSDST.setEnabled(true);
      cMSDSL.setText("Your MSDS #: *");
    }else {
      customerMSDST.setEnabled(false);
      cMSDSL.setText("Your MSDS #:");
    }
    unitC.setFont(new java.awt.Font("Dialog", 0, 10));
    unitC.setMaximumSize(new Dimension(32767, 21));
    unitC.setMinimumSize(new Dimension(124, 21));
    unitC.setPreferredSize(new Dimension(128, 21));
    packC.setFont(new java.awt.Font("Dialog", 0, 10));
    packC.setMaximumSize(new Dimension(32767, 21));
    packC.setMinimumSize(new Dimension(124, 21));
    packC.setPreferredSize(new Dimension(128, 21));
    topP.setMinimumSize(new Dimension(588, 170));
    topP.setPreferredSize(new Dimension(588, 170));
    bottomP.setMinimumSize(new Dimension(551, 120));
    bottomP.setPreferredSize(new Dimension(617, 120));
    dimT.setMaximumSize(new Dimension(130, 21));
    dimT.setMinimumSize(new Dimension(130, 21));
    dimT.setPreferredSize(new Dimension(130, 21));
    sampleSizingCheck.setText("Sample Sizing");
    sampleSizingCheck.setFont(new java.awt.Font("Dialog", 0, 10));
    bundleL.setText("# per part: *");
    byL.setText("x");
    this.add(topP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(manufT, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 9, 3, 0), 0, 0));
    topP.add(manufB, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 3, 0), 0, 0));
    topP.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 9, 0, 0), 0, 0));
    topP.add(jLabel2, new GridBagConstraints2(0, 2, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 9, 0, 0), 0, 0));
    topP.add(matDescT, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 9, 3, 0), 0, 0));
    topP.add(jLabel7, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 9, 0, 0), 0, 0));
    topP.add(tradeT, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 9, 1, 135), 0, 0));
    topP.add(jLabel8, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(gradeT, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 3, 5), 0, 0));

    topP.add(cMSDSL, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 30), 0, 0));
    topP.add(customerMSDST, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 135, 1, 0), 0, 0));
    topP.add(msdsB, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 1, 5), 0, 0));

    mfgStorageL.setText("Manufacturer's Recommended Shelf Life @ Storage: ");
    topP.add(mfgStorageL, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 9, 2, 0), 0, 0));

    this.add(bottomP, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 1, 0), 0, 0));
    bottomP.add(jLabel3, new GridBagConstraints2(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 9, 0, 0), 0, 0));
    bottomP.add(manufPartT, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 9, 0, 0), 0, 0));
    bottomP.add(jLabel4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 80, 0, 0), 0, 0));
    bottomP.add(sizeT, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 88, 2, 8), 60, 0));
    bottomP.add(bundleL, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 9, 0, 0), 0, 0));
    bottomP.add(bundleT, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 9, 2, 8), 60, 0));
    bottomP.add(byL, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 77, 13, 10), 0, 0));
    bottomP.add(jLabel5, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    bottomP.add(jLabel6, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    bottomP.add(packC, new GridBagConstraints(2, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 0), 20, 0));
    bottomP.add(unitC, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 2, 0), 0, 0));

    bottomP.add(jLabel9, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 3), 0, 0));
    bottomP.add(dimT, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 3), 0, 0));
    bottomP.add(jLabel10, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 3), 20, 0));
    bottomP.add(netwtT, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 3), 20, 0));
    bottomP.add(netwtC, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 3), 0, 0));
    bottomP.add(sampleSizingCheck, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 0), 0, 0));
    /*
    bottomP.add(dimensionP, new GridBagConstraints2(3, 2, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 3), 0, 0));
    dimensionP.add(jLabel10, new GridBagConstraints2(0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    dimensionP.add(dimT, new GridBagConstraints2(1, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));

    dimensionP.add(jLabel9, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    dimensionP.add(netwtT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 20, 0));
    dimensionP.add(netwtC, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    */
    topP.setBorder(ClientHelpObjs.groupBox("New Material"));
    bottomP.setBorder(ClientHelpObjs.groupBox("New Size / Packing"));


    // name all components that needs audit - name / TYPE of audit: TEXT, NUMBER, NONE / Message
    manufT.setName("manufT/"+ClientHelpObjs.AUDIT_TEXT_NON_BLANK+"/Manufacturer is missing on one of the parts of the kit.");
    matDescT.setName("matDescT/"+ClientHelpObjs.AUDIT_TEXT_NON_BLANK+"/Material Description is missing on one of the parts of the kit.");
    tradeT.setName("tradeT/"+ClientHelpObjs.AUDIT_TEXT_NON_BLANK+"/Manufacturer's trade name is missing on one of the parts of the kit.");
    sizeT.setName("sizeT/"+ClientHelpObjs.AUDIT_NUMERIC_NON_ZERO+"/Size is missing on one of the parts of the kit.");
    bundleT.setName("bundleT/"+ClientHelpObjs.AUDIT_NUMERIC_NON_ZERO+"/# per part is missing on one of the parts of the kit.");
    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

  }

  public String getManufT(){
    return manufT.getText();
  }

  public void setUnit(Hashtable u){
    unit = u;
    Vector v = new Vector();
    Enumeration E;
    for (E=unit.keys();E.hasMoreElements();){
       v.addElement(E.nextElement());
    }
    // trong 3/13/00 sort vector
    v = BothHelpObjs.sort(v);

    unitC = ClientHelpObjs.loadChoiceFromVector(unitC,v,false);
    //trong 3/13/00
    unitC.insertItemAt("Select a Unit",0);
    unitC.setSelectedIndex(0);
  }

  public void setPack(Vector p){
    packC = ClientHelpObjs.loadChoiceFromVector(packC,p,false);
    //trong 3/13/00
    packC.insertItemAt("Select a Package Style",0);
    packC.setSelectedIndex(0);
  }

  public void setNetWt(Vector p){
    netwtC = ClientHelpObjs.loadChoiceFromVector(netwtC,p,false);
    //trong 8-2
    netwtC.insertItemAt("Select",0);
    netwtC.setSelectedIndex(0);
  }

  public String getUnitText(){
    //trong 3/21 adding size and the key 'in' into units
    //before return (String) unitC.getSelectedItem() + " " + (String) packC.getSelectedItem();
    String temp = "";
    try {
      int i = Integer.parseInt(bundleT.getText());
      if (i > 1) {
        temp = "cs of "+i + " x ";
      }
    }catch (Exception e) {
      temp = "";
    }
    return temp + sizeT.getText() + " " + (String) unitC.getSelectedItem() + " " + (String) packC.getSelectedItem();
  }

  public String getMaterialDesc(){
    return (String) matDescT.getText();
  }

  void manufB_actionPerformed(ActionEvent e) {
     //choose approver
    SearchManufacturer dlg = new SearchManufacturer(cmis.getMain(),"Search",true);
    dlg.setGrandParent(cmis);
    dlg.setVisible(true);
    if (dlg.getManufacturerId() != null){
      manufT.setUpdating();
      manufT.setText(dlg.getManufacturerDesc());
    }
  }

  void msdsB_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     ClientHelpObjs.goURL(cmis,mat_id,"",cmis.getResourceBundle().getString("APP_NAME"),"",0,URLGrab.MSDS);
  }

  public void setMatID(String i){
     mat_id = i;
  }

  void unitC_itemStateChanged(ItemEvent e) {
     setNetWtProtect();
  }

  public void setNetWtProtect(){
     String selected = (String) unitC.getSelectedItem();
     if (selected==null) return;

     if ((BothHelpObjs.isBlankString(selected)) || (selected.equalsIgnoreCase("Select a Unit")) ||
          (selected.equalsIgnoreCase("Select a Package Style"))) {
       setNetWtenable(false);
       return;
     }
     Boolean b = (Boolean) unit.get(selected);
     if (!b.booleanValue()){
        dimT.setText("");
        netwtT.setText("");
     }
     setNetWtenable(b.booleanValue());
  }

  public void setNetWtenable(boolean f){
    jLabel9.setVisible(f);
    jLabel10.setVisible(f);
    netwtC.setVisible(f);
    netwtT.setVisible(f);
    dimT.setVisible(f);
    dimensionP.revalidate();
    /*
    netwtC.setEnabled(f);
    netwtT.setEnabled(f);
    dimT.setEnabled(f);
    */
  }
}
