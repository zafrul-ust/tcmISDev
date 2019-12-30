//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


//import com.borland.dx.dataset.*;
//import com.borland.jbcl.view.*;

import javax.swing.*;
import javax.swing.event.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;


public class MetaPopUp extends JPopupMenu {
  int itemID = 0;
  String pack = null;
  String facID = null;
  String matID = null;
  String specID = null;
  String[] specIds = null;
  String specText = null;
  boolean hasSpec = false;
  boolean hasMSDS = false;
  boolean canRequestPrice = false;
  boolean showPriceRequestStatus = false;
  boolean invForAllFacs = false;
  CmisApp cmis;
  String facPartNum = null;

  SpecPopUpMenu specMenu = null;

  JMenuItem metaMSDS = new JMenuItem("View MSDS");
  //JMenuItem metaSpec = new JMenuItem("Specification");
  JMenuItem metaComp = new JMenuItem("Composition");
  JMenuItem metaSnH = new JMenuItem("Storage & Handling");
  JMenuItem metaProp = new JMenuItem("Properties");
  JMenuItem metaAir = new JMenuItem("Shipping - Air");
  JMenuItem metaInv = new JMenuItem("Inventory");
  JMenuItem metaLand = new JMenuItem("Shipping - Land");
  JMenuItem metaPriceReq = new JMenuItem("Request Price");
  JMenuItem metaPriceReqStatus = new JMenuItem("Price Request Status");

  // this constructor is the old one that doesn't show the price request
  // it is a shell to call the new constructor and defaults to NOT showing
  // the price request
  public MetaPopUp(CmisApp cmis, int item, String pack, String fac, String mat, String spec, boolean hasSpec, boolean hasMSDS) {
    this(cmis, item, pack, fac, mat, spec, hasSpec, hasMSDS, false, false, "");
  }
  public MetaPopUp(CmisApp cmis, int item, String pack, String fac, String mat, String spec, boolean hasSpec, boolean hasMSDS, boolean canRequestPrice, boolean showPriceRequestStatus, String facPartNum) {
    //this(cmis,item,pack,fac,mat,spec,hasSpec,hasMSDS,canRequestPrice,showPriceRequestStatus,facPartNum);
    super();
    this.itemID = item;
    this.cmis = cmis;
    this.pack = pack;
    this.facID = fac;
    this.matID = mat;
    this.specText = spec;
    //this.specIds = spec;
    //this.specID = spec[0];
    this.hasSpec = hasSpec;
    this.hasMSDS = hasMSDS;
    this.canRequestPrice = canRequestPrice;
    this.showPriceRequestStatus = showPriceRequestStatus;
    this.facPartNum = facPartNum;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setInvForAllFacs(boolean b){
    invForAllFacs = b;
  }

  public void setFacPartNum(String s) {
    facPartNum = s;
  }

  void setSpec(String s) {
    specID = new String(s);
  }

  public void setShowPriceRequestStatus(boolean b) {
    showPriceRequestStatus = b;
  }

  public void jbInit() throws Exception{
    specMenu = new SpecPopUpMenu(this,specText);

    // set Action commands
    this.addPopupMenuListener(new PopupMenuListener() {
      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        closeSpecMenu();
      }
      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
      }
      public void popupMenuCanceled(PopupMenuEvent e) {
        closeSpecMenu();
      }
    });

    metaMSDS.setActionCommand("MSDS");
    //metaSpec.setActionCommand("Specification");
    metaComp.setActionCommand("CompView");
    metaProp.setActionCommand("PropView");
    metaSnH.setActionCommand("ShipView");
    metaInv.setActionCommand("Invent");
    metaLand.setActionCommand("LandView");
    metaAir.setActionCommand("AirView");
    metaPriceReq.setActionCommand("Price");
    metaPriceReqStatus.setActionCommand("PriceStatus");
    // add menu items



    add(metaMSDS);
    //add(metaSpec);
    add(specMenu);
    add(metaComp);
    // begin (we don't have this info yet...)
    add(metaProp);
    add(metaSnH);
    add(metaLand);
    add(metaAir);
    // end (don't haves)

    add(metaInv);
    if(canRequestPrice || showPriceRequestStatus) {
      addSeparator();
      // it doesn't make sense to show both
      if(showPriceRequestStatus){
        add(metaPriceReqStatus);
      }else if(canRequestPrice) {
        add(metaPriceReq);
      }
    }

    // add action listeners
    MetaActionListener mal = new MetaActionListener(this);
    metaMSDS.addActionListener(mal);
    //metaSpec.addActionListener(mal);
    metaComp.addActionListener(mal);
    metaProp.addActionListener(mal);
    metaSnH.addActionListener(mal);
    metaInv.addActionListener(mal);
    metaLand.addActionListener(mal);
    metaAir.addActionListener(mal);
    metaPriceReq.addActionListener(mal);
    metaPriceReqStatus.addActionListener(mal);
  }

  void goInventory() {
    InventFloat IF = new InventFloat(cmis.getMain(),cmis);
    IF.setItem(itemID);
    if(!invForAllFacs){
      IF.setHub("");
      IF.setFacilityId(facID);
    } else {
      IF.setHub("");
      IF.setFacilityId((String)cmis.getXfac().get(cmis.getPrefFac()));
    }
    IF.loadIt();
  }

  void goSpec(ActionEvent e){
    Vector v = specMenu.getInfo(specID);
    String id = v.elementAt(0).toString();
    String lib = v.elementAt(1).toString();
    String on = v.elementAt(2).toString();
    String detail = v.elementAt(3).toString();
    String host = v.elementAt(4).toString();
    if(host.equalsIgnoreCase("pdm")){
      String msg = "www.rscecommerce.com";
      new URLCall("http://www.rscecommerce.com","",cmis);
    }else{
      this.setSpec(id);
      goUrl(e);
    }
  }

  void goPriceRequest(){
    PriceRequestDlg prd = new PriceRequestDlg(cmis.getMain());
    prd.setCmisApp(cmis);
    prd.setFacId(facID);
    prd.setItemId(itemID);
    prd.loadIt();
  }

  void goCheckPriceStatus(){
    goPriceRequest();
  }

  void goUrl(ActionEvent e) {
    int kind = 0;

    if (e.getActionCommand().toString().startsWith("MSDS")) {
      kind = URLGrab.MSDS;
    }else if (e.getActionCommand().toString().startsWith("Comp")) {
      kind = URLGrab.COMP;
    }else if (e.getActionCommand().toString().startsWith("Ship")) {
      kind = URLGrab.STOR;
    }else if (e.getActionCommand().toString().startsWith("Prop")) {
      kind = URLGrab.PROP;
    }else if (e.getActionCommand().toString().startsWith("Land")) {
      kind = URLGrab.LAND;
    }else if (e.getActionCommand().toString().startsWith("Air")) {
      kind = URLGrab.AIR;
    //}else if (e.getActionCommand().toString().startsWith("Spec")) {
    }else if (e.getActionCommand().toString().equalsIgnoreCase(specID)) {
      kind = URLGrab.SPEC;
    }

    //before 6-14-01 ClientHelpObjs.goURL(cmis,matID,specID,this.cmis.getResourceBundle().getString("APP_NAME"),facID,itemID,kind);
    ClientHelpObjs.goURL(cmis,matID,specID,this.cmis.getResourceBundle().getString("APP_NAME"),facID,itemID,kind,specMenu.getNumItems() > 0);
  }

  public void show(Component comp, int x, int y) {
    metaMSDS.setEnabled(true);
    //metaSpec.setEnabled(true);
    metaComp.setEnabled(true);
    metaProp.setEnabled(true);
    metaSnH.setEnabled(true);
    metaLand.setEnabled(true);
    metaAir.setEnabled(true);
    metaInv.setEnabled(true);
    metaPriceReq.setEnabled(true);
    metaPriceReq.setEnabled(true);
    metaPriceReqStatus.setEnabled(true);

    if (!hasMSDS){
      metaMSDS.setEnabled(false);
      metaComp.setEnabled(false);
      metaProp.setEnabled(false);
      metaSnH.setEnabled(false);
      metaLand.setEnabled(false);
      metaAir.setEnabled(false);
    }

// begin (we don't have this info yet...)
      metaProp.setEnabled(false);
      metaSnH.setEnabled(false);
      metaLand.setEnabled(false);
      metaAir.setEnabled(false);
// end (don't haves)



    specMenu.setEnabled(specMenu.getNumItems() > 0);

    // make sure the entire menu is showing
    // this is a real kludge
    int offset = 185;
    int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
    if(comp.getLocationOnScreen().y + y > screenH - offset) {
      y = screenH - offset - comp.getLocationOnScreen().y;
    }
    super.show(comp,x,y);
  }

  void closeSpecMenu() {
    if(specMenu == null) return;
    this.getParent().removeAll();
    specMenu.setVisible(false);
    //specMenu = null;
    cmis.getMain().repaint();
    repaint();
  }
}
class SpecPopUpMenu extends JMenu {
  MetaPopUp you;
  Vector duh;
  String specText;
  Vector id = new Vector();
  Vector lib = new Vector();
  Vector on = new Vector();
  Vector detail = new Vector();
  Vector host = new Vector();
  int num = 0;

  SpecPopUpMenu(MetaPopUp you, String specText) {
    super("Specification");
    this.you = you;
    this.specText = specText;

    StringTokenizer st1 = new StringTokenizer(specText,";",false);
    while(st1.hasMoreElements()){
      String s = st1.nextElement().toString();
      StringTokenizer st2 = new StringTokenizer(s,":",false);
      String holdID = st2.nextElement().toString();
      if(BothHelpObjs.isBlankString(holdID) || holdID.equalsIgnoreCase("no specification")){
        continue;
      }
      num++;
      id.addElement(holdID);
      lib.addElement(st2.nextElement().toString());
      detail.addElement(st2.nextElement().toString());
      on.addElement(st2.nextElement().toString());
      host.addElement(st2.nextElement().toString());
    }

    SpecMetaActionListener mal = new SpecMetaActionListener(you,this);
    for(int i=0;i<id.size();i++) {
      String showText = getTextForItem(id.elementAt(i).toString(),detail.elementAt(i).toString());
      JMenuItem jmi = new JMenuItem(showText);
      jmi.setEnabled(on.elementAt(i).toString().equalsIgnoreCase("y") || host.elementAt(i).toString().equalsIgnoreCase("pdm"));
      jmi.setActionCommand(id.elementAt(i).toString());
      jmi.addActionListener(mal);
      add(jmi);
    }
  }
  String getTextForItem(String id,String detail){
    if(BothHelpObjs.isBlankString(detail)){
      return new String(id);
    }
    return id+" ("+detail+")";
  }
  public int getNumItems(){
    return num;
  }
  public Vector getInfo(String s){
    Vector v = new Vector();
    for(int i=0;i<id.size();i++) {
      if(s.equals(id.elementAt(i).toString())){
      //if(s.equals(getTextForItem(id.elementAt(i).toString(),detail.elementAt(i).toString()))){
        v.addElement(id.elementAt(i).toString());
        v.addElement(lib.elementAt(i).toString());
        v.addElement(on.elementAt(i).toString());
        v.addElement(detail.elementAt(i).toString());
        v.addElement(host.elementAt(i).toString());
        break;
      }
    }
    return v;
  }

  public void setVisible(boolean b){
    MenuElement[] me = this.getSubElements();
    for(int i=0;i<me.length;i++){
      JMenuItem jmi = null;
      try{
        jmi = (JMenuItem)me[i];
      }catch(Exception e){
        try{
          JPopupMenu w = (JPopupMenu) me[i];
          w.setVisible(b);
        }catch(Exception e1){
          // // System.out.println("going window catch");
        }
        continue;
      }
      jmi.setVisible(b);
    }
    super.setVisible(b);
  }
}

class MetaActionListener implements ActionListener {
  MetaPopUp you;
  public MetaActionListener(MetaPopUp you){
    this.you = you;
  }
  public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().toString().startsWith("Inv")) {
        you.goInventory();
      }else if (e.getActionCommand().toString().equalsIgnoreCase("Price")) {
        you.goPriceRequest();
      }else if (e.getActionCommand().toString().equalsIgnoreCase("PriceStatus")) {
        you.goCheckPriceStatus();
      //}else if (e.getActionCommand().toString().equalsIgnoreCase("Specification")) {
      }else if (e.getActionCommand().toString().equalsIgnoreCase(you.specID)) {
        you.goSpec(e);
      }else{
        you.goUrl(e);
      }
  }
}
class SpecMetaActionListener extends MetaActionListener {
  SpecPopUpMenu specMen;
  public SpecMetaActionListener(MetaPopUp you, SpecPopUpMenu specMen){
    super(you);
    this.specMen = specMen;
  }
  public void actionPerformed(ActionEvent e) {
    you.setSpec(e.getActionCommand());
   /* MenuElement[] me = specMen.getSubElements();
    for(int i=0;i<me.length;i++) {
System.out.println("looping");
      JMenuItem jmi;
      try{
        jmi = (JMenuItem) me[i];
      }catch(Exception ex){System.out.println("in catch"); ex.printStackTrace();
continue;}
System.out.println("after the catch");
      if(jmi.isSelected()) {
System.out.println("found it:"+jmi.getText());
        you.setSpec(jmi.getText());
        break;
      }
    }*/
    super.actionPerformed(e);
  }
}
