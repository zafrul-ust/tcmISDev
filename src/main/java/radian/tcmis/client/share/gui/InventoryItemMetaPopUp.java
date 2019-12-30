package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.*;


public class InventoryItemMetaPopUp extends JPopupMenu {
  CmisApp cmis;
  String itemID;
  String facID;
  String hub;
  String partNo;
  String catalogId;
  String packaging;
  String tradeName;
  String mfg;
  String catalogCompanyId = "";
  String partGroupNo = "";
  String inventoryGroup = "";

  public InventoryItemMetaPopUp(CmisApp cmis,String itemID, String facID, String partNo, String catalogID) {
    super();
    this.cmis = cmis;
    this.itemID = itemID;
    this.facID = facID;
    this.catalogId = catalogID;
    this.partNo = partNo;
  }

  public void setCatalogCompanyId (String s) {
    this.catalogCompanyId = s;
  }

  public void setPartGroupNo (String s) {
    this.partGroupNo = s;
  }

  public void setInventoryGroup (String s) {
    this.inventoryGroup = s;
  }


  public void setPackaging(String s) {
    this.packaging = s;
  }
  public void setTradeName(String s) {
    this.tradeName = s;
  }
  public void setMfg(String s) {
    this.mfg = s;
  }

  public void loadData() {
    JMenuItem mi;
    InventoryItemMetaActionListener mal = new InventoryItemMetaActionListener(this);

    mi = new JMenuItem("Inventory Detail");
    mi.setFont(new java.awt.Font("Dialog", 0, 10));
    add(mi);
    mi.addActionListener(mal);
    //manufacturer literature
    mi = new JMenuItem("Mfg Lit.");
    mi.setFont(new java.awt.Font("Dialog", 0, 10));
    add(mi);
    mi.addActionListener(mal);
    mi.setEnabled(false);
    //item image
    mi = new JMenuItem("Image");
    mi.setFont(new java.awt.Font("Dialog", 0, 10));
    add(mi);
    mi.addActionListener(mal);
    mi.setEnabled(false);
  }

  void goImage(){

  }

  /*
  void goItemInventoryFloat() {
    InventFloat IF = new InventFloat(cmis.getMain(),cmis);
    Integer item = new Integer(itemID);
    IF.setItem(item.intValue());
    IF.setHub("");
    IF.setFacilityId(facID);
    IF.setPackaging(packaging);
    IF.setTradeName(tradeName);
    IF.setMfg(mfg);
    IF.loadIt();
  }*/

  void goItemInventoryFloat(){
   try {
     String client = cmis.getResourceBundle().get("CLIENT_INITIALS").toString();
     if ("Boeing".equalsIgnoreCase(client)) {
       client = "ula";
     }
     String url = "https://www.tcmis.com/tcmIS/"+client.toLowerCase()+"/inventorydetail.do?catalogCompanyId="+cmis.companyId+
                  "&catPartNo="+partNo+
                  "&inventoryGroup="+inventoryGroup+
                  "&catalogId="+catalogId+
                  "&partGroupNo="+partGroupNo;
     new URLCall(url,cmis,false);
   } catch (Exception e) {
     e.printStackTrace();
   }
 } //end of method


  /*
  void goItemInventoryFloat() {
    //if all hub
    if ("All".equalsIgnoreCase(hub)) {
      //System.out.println("All hub");
      InventItemDetail inventID = new InventItemDetail(cmis.getMain(),"Inventory for Item: "+itemID,facID,hub,itemID);
      inventID.setParent(cmis);
      inventID.loadIt();
      CenterComponent.centerCompOnScreen(inventID);
      inventID.setVisible(true);
    }else {
      InventFloat IF = new InventFloat(cmis.getMain(),cmis);
      Integer item = new Integer(itemID);
      IF.setItem(item.intValue());
      IF.setHub(hub);
      IF.setFacilityId(facID);
      IF.loadIt();
    }
  }
  */

  void goMfgLit() {

  }

  public void show(Component comp, int x, int y) {
    loadData();
    // make sure the entire menu is showing
    // this is a real kludge
    int offset = 185;
    int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
    if(comp.getLocationOnScreen().y + y > screenH - offset) {
      y = screenH - offset - comp.getLocationOnScreen().y;
    }
    super.show(comp,x,y);
  }  //end of method
}    //end of class

class InventoryItemMetaActionListener implements ActionListener {
  InventoryItemMetaPopUp you;
  public InventoryItemMetaActionListener(InventoryItemMetaPopUp you){
    this.you = you;
  }
  public void actionPerformed(ActionEvent e) {
      //System.out.println("action commamd string: "+e.getActionCommand().toString());
      if (e.getActionCommand().toString().startsWith("Inventory")) {
        you.goItemInventoryFloat();
        //System.out.println("Item inventory clicked:");
      }else if (e.getActionCommand().toString().startsWith("Mfg")) {
        you.goMfgLit();
        //System.out.println("Part inventory clicked:");
      }else if (e.getActionCommand().toString().startsWith("Image")) {
        you.goImage();
        //System.out.println("SPEC clicked:");
      }
  }
}

/*
  public boolean loadData() {
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CATALOG_TABLE,cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","GET_ITEM_INVENTORY_INFO"); //String, String
    query.put("ITEM_ID",itemID);  //String, Integer
    Hashtable result = cgi.getResultHash(query);
    if (result == null && !cmis.getMain().stopped){
      GenericDlg.showAccessDenied(cmis.getMain());
      return false;
    }
    Boolean b = (Boolean)result.get("SUCCEED");
    if (b.booleanValue()) {
      JMenuItem mi;
      InventoryItemMetaActionListener mal = new InventoryItemMetaActionListener(this);

      mi = new JMenuItem("Inventory Detail");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
      //manufacturer literature
      mi = new JMenuItem("Mfg Lit.");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
      mi.setEnabled(false);
      //item image
      mi = new JMenuItem("Image");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
      mi.setEnabled(false);

    }else {
      GenericDlg err = new GenericDlg(cmis.getMain(),"ERROR",true);
      err.setMsg((String)result.get("MSG"));
      err.show();
    }
    return b.booleanValue();
  }
*/