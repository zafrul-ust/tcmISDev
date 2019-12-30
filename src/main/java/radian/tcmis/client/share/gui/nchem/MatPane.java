
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Your description

package radian.tcmis.client.share.gui.nchem;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import radian.tcmis.client.share.helpers.*;

public class MatPane extends JPanel { //NewChemPanel {
  JTabbedPane tabPane = new JTabbedPane();
  SupplierPane supplier = new SupplierPane();

  Integer partsNum = new Integer(0);
  Vector  partsPanes = new Vector();

  Hashtable unit = null;
  Vector pack = null;
  Vector netwt = null;

  NewChem nchem = null;
  String requestorEditFacMSDSID = "NO";

  JPopupMenu dymmy = new JPopupMenu();

  public MatPane() {
    super();
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setNewChem(NewChem n){
     nchem = n;
  }

  private void jbInit() throws Exception {
    tabPane.setFont(new java.awt.Font("Dialog", 0, 10));
    tabPane.setToolTipText("Right-click to add or delete Parts");
    tabPane.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        tabPane_mouseClicked(e);
      }
    });
    this.setLayout(new BorderLayout());
    this.add(tabPane, BorderLayout.CENTER);
    this.add(supplier, BorderLayout.SOUTH);


    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  }

  public void setPartsNum(Integer i){
    this.partsNum = i;
  }

  public Integer getPartsNum(){
    return this.partsNum ;
  }

  public void setUnit(Hashtable u){
    unit = u;
  }

  public void setPack(Vector p){
    pack = p;
  }

  public void setNetWt(Vector p){
    netwt = p;
  }

  public void setRequestorEditFacMSDSID(String s) {
    requestorEditFacMSDSID = s;
  }

  public void protectNetWt(){
     for (int i=0;i<tabPane.getTabCount();i++){
        KitsPane dummy = (KitsPane) tabPane.getComponentAt(i);
        dummy.setNetWtProtect();
     }
  }


  //trong 11-12-00
  public void printScreenData() {

  }

  public  void setPane(){
    for (int i=0;i<partsNum.intValue();i++){
       KitsPane dummy = new KitsPane(requestorEditFacMSDSID);
       dummy.setUnit(unit);
       dummy.setPack(pack);
       dummy.setNetWt(netwt);
       dummy.setNetWtenable(false);
       dummy.setCmis(nchem.getCmis());
       tabPane.addTab("Part "+(i+1),dummy);
    }
  }

  public JTabbedPane getTabPane(){
     return tabPane;
  }

  void tabPane_mouseClicked(MouseEvent e) {
    if (e.isMetaDown() && ((KitsPane) tabPane.getComponentAt(0)).manufT.isEnabled()) {
       MatPane_MetaPop pop = new MatPane_MetaPop(this);
       pop.show(this,e.getX(),e.getY());
    }
  }

  public void addPart(){
     KitsPane dummy = new KitsPane(requestorEditFacMSDSID);
     dummy.setUnit(unit);
     dummy.setPack(pack);
     dummy.setNetWt(netwt);
     dummy.setNetWtenable(false);
     tabPane.addTab("Part "+(tabPane.getTabCount()+1),dummy);
     partsNum = new Integer(partsNum.intValue()+1);

     FatePane dummyFate = new FatePane();
     nchem.apprPP.fateTab.addTab("Part "+(nchem.apprPP.fateTab.getTabCount()+1),dummyFate);
  }

  public void deletePart(){
     if (tabPane.getTabCount()<2) return;
     int selectedTab = tabPane.getSelectedIndex();
     tabPane.removeTabAt(selectedTab);
     nchem.apprPP.fateTab.removeTabAt(selectedTab);    //also remove the fate table
     partsNum = new Integer(partsNum.intValue()-1);
     for (int i=0;i<tabPane.getTabCount();i++){
        tabPane.setTitleAt(i,"Part "+(i+1));
        nchem.apprPP.fateTab.setTitleAt(i,"Part "+(i+1));
     }
  }

  public SupplierPane getSupplierPane(){
     return supplier;
  }

  public JTabbedPane getKitsPane(){
     return this.tabPane;
  }

  public String getUnitCText(){
     if  (partsNum.intValue()>1) {
        return "kit(s)";
     } else {
        return ((KitsPane) tabPane.getSelectedComponent()).getUnitText();
     }

  }

  public String[] getMatDescs(){
     String[] descs = new String[tabPane.getTabCount()];
     //System.out.println("Tab count:"+tabPane.getTabCount());
     if  (tabPane.getTabCount()>1) {
        //trong 4/25 for (int i=0;i<tabPane.getTabCount()-1;i++){
        for (int i=0;i<tabPane.getTabCount();i++){
          descs[i]= ((KitsPane) tabPane.getComponentAt(i)).getMaterialDesc();
        }
     } else {
        descs[0] = ((KitsPane) tabPane.getSelectedComponent()).getMaterialDesc();
     }
     return descs;
  }

}


class MatPane_MetaPop extends JPopupMenu {

    MatPane parent = null;
    JMenuItem addM = new JMenuItem("Add a new Part");
    JMenuItem deleteM = new JMenuItem("Delete Part");

    public MatPane_MetaPop(MatPane p){
       super("Add / Delete Parts");
       parent = p;
       add(addM);
       add(deleteM);
       addM.setActionCommand("Add");
       deleteM.setActionCommand("Del");
       MatPane_MetaActionListener mal = new   MatPane_MetaActionListener(parent);
       addM.addActionListener(mal);
       deleteM.addActionListener(mal);
       addM.setEnabled(true);
       deleteM.setEnabled(true);
    }
} //end of method

class MatPane_MetaActionListener implements ActionListener {
  MatPane parent = null;

  public MatPane_MetaActionListener(MatPane p){
      parent = p;
  }

  public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().toString().startsWith("Add")) {
        parent.addPart();
      } else if (e.getActionCommand().toString().equalsIgnoreCase("Del")) {
        parent.deletePart();
      }
  }
}
