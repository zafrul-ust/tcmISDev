//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import java.util.*;


//import com.borland.jbcl.view.*;

import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.gui.*;

import javax.swing.*;
import radian.tcmis.client.share.helpers.*;


public class WasteMetaPopUp extends JPopupMenu {
  Vector containerId = null;
  int itemID = 0;
  String pack = null;
  String facilityId = null;
  String matID = null;
  String specID = null;
  String[] specIds = null;
  String specText = null;
  CmisApp cmis;
  String facPartNum = null;
  WasteManage wasteManage;
  int selectedR = 0;
  String vendorId = null;
  String storageLocation = null;
  String vendorProfileDesc = null;
  String vendorProfileId = null;
  String stateWasteCode = null;
  boolean fromWasteManage = false;
  boolean fromWasteOrder = false;
  boolean fromWastePickupRequest = false;
  WasteOrder wasteOrder;
  String sizeUnit;
  WastePickupRequest wastePickupRequest;
  WasteTrackWin wasteTrackWin;
  boolean changeTsdfProfile = false;

  JMenuItem metaAddInto = new JMenuItem("Add Waste");
  JMenuItem metaChgProfile = new JMenuItem("Change Profile");
  JMenuItem metaDisp = new JMenuItem("Disposition");
  JMenuItem metaEmptyInto = new JMenuItem("Empty Into");
  JMenu printLabelSubMenu = new JMenu("Print Labels");
  JMenuItem traveler = new JMenuItem("Traveler");
  JMenuItem hazardousWaste = new JMenuItem("Hazardous Waste");
  JMenuItem metaProp = new JMenuItem("Properties");
  JMenuItem metaSealContainer = new JMenuItem("Seal Container");
  JMenuItem metaProfile = new JMenuItem("Waste Profile");
  JMenuItem metaDelete = new JMenuItem("Delete Container(s)");

  public WasteMetaPopUp(CmisApp cmis, int item) {
    super();
    this.itemID = item;
    this.cmis = cmis;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public WasteMetaPopUp(CmisApp cmis) {
    super();
    this.cmis = cmis;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setChangeTsdfProfile(boolean b) {
    this.changeTsdfProfile = b;
  }

  public void jbInit() throws Exception{

    metaAddInto.setActionCommand("AddInto");
    metaChgProfile.setActionCommand("ChgProfile");
    metaDisp.setActionCommand("DispView");
    metaEmptyInto.setActionCommand("EmptyInto");
    traveler.setActionCommand("Traveler");
    hazardousWaste.setActionCommand("HazardousWaste");
    metaProp.setActionCommand("PropView");
    metaSealContainer.setActionCommand("SealContainer");
    metaProfile.setActionCommand("Profile");
    metaDelete.setActionCommand("DeleteContainer");

    // add menu items
    add(metaProfile);
    add(metaDisp);
    add(metaProp);
    addSeparator();
    printLabelSubMenu.add(hazardousWaste);
    printLabelSubMenu.add(traveler);
    add(printLabelSubMenu);
    addSeparator();
    add(metaAddInto);
    add(metaEmptyInto);
    add(metaSealContainer);
    addSeparator();
    add(metaChgProfile);
    addSeparator();
    add(metaDelete);

    // add action listeners
    MetaActionListener mal = new MetaActionListener(this);
    metaAddInto.addActionListener(mal);
    metaChgProfile.addActionListener(mal);
    metaDisp.addActionListener(mal);
    metaEmptyInto.addActionListener(mal);
    traveler.addActionListener(mal);
    hazardousWaste.addActionListener(mal);
    metaProp.addActionListener(mal);
    metaSealContainer.addActionListener(mal);
    metaProfile.addActionListener(mal);
    metaDelete.addActionListener(mal);

    metaAddInto.setEnabled(false);
    metaChgProfile.setEnabled(false);
    metaEmptyInto.setEnabled(false);
    printLabelSubMenu.setEnabled(false);
    metaSealContainer.setEnabled(false);
    metaDelete.setEnabled(false);
  }

  //trong 8-18
  public void enableMenu(String value) {
    if (value.equalsIgnoreCase("AddInto")) {
      metaAddInto.setEnabled(true);
    }else if (value.equalsIgnoreCase("ChgProfile")) {
      metaChgProfile.setEnabled(true);
    }else if (value.equalsIgnoreCase("EmptyInto")) {
      metaEmptyInto.setEnabled(true);
    }else if (value.equalsIgnoreCase("PrintLabel")) {
      printLabelSubMenu.setEnabled(true);
    }else if (value.equalsIgnoreCase("SealContainer")) {
      metaSealContainer.setEnabled(true);
    }else if (value.equalsIgnoreCase("DeleteContainer")) {
      metaDelete.setEnabled(true);
    }else {
      metaSealContainer.setEnabled(true);
    }
  }
  public void setSizeUnit(String value) {
    this.sizeUnit = value;
  }
  public void setPackaging(String value) {
    this.pack = value;
  }
  public void setFacilityId(String value) {
    this.facilityId = value;
  }
  public void setWasteManagement(WasteManage wm) {
    this.wasteManage = wm;
  }
  public void setSelectedRow(int row) {
    this.selectedR = row;
  }
  public void setVendorProfileDesc(String value) {
    this.vendorProfileDesc = value;
  }
  public void setVendorProfileId(String value) {
    this.vendorProfileId = value;
  }
  public void setStateWasteCode(String value) {
    this.stateWasteCode = value;
  }
  public void setWasteTrackWin(WasteTrackWin wtw) {
    this.wasteTrackWin = wtw;
  }

  void goUrl(ActionEvent e) {
    int kind = 0;

    if (e.getActionCommand().toString().startsWith("Profile")) {
      kind = URLGrab.PROFILE;
    }else if (e.getActionCommand().toString().startsWith("DispView")) {
      kind = URLGrab.DISP;
    }else if (e.getActionCommand().toString().startsWith("PropView")) {
      kind = URLGrab.WPROP;
    }

    ClientHelpObjs.goWasteURL(cmis,this.cmis.getResourceBundle().getString("APP_NAME"),itemID,kind);
  }

  //trong 5/20
  public void setContainerId(Vector containerId) {
    this.containerId = containerId;
  }
  public void setVendorId(String vendor) {
    this.vendorId = vendor;
  }
  public void setStorageLocation(String storage) {
    this.storageLocation = storage;
  }
  public void setFromWasteManage(boolean b) {
    this.fromWasteManage = b;
  }
  public void setWasteOrder(WasteOrder wo) {
    this.wasteOrder = wo;
  }
  public void setWastePickupRequest(WastePickupRequest wpr) {
    this.wastePickupRequest = wpr;
  }
  public void setFromWastePickupRequest(boolean b) {
    this.fromWastePickupRequest = b;
  }
  public void setFromWasteOrder(boolean b) {
    this.fromWasteOrder = b;
  }


  void goChangeProfile(ActionEvent e) {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    String title = new String("Change Profile To:");
    String contId = (String)containerId.elementAt(0);
    ChangingProfile cp;
    if (this.fromWasteManage) {
      cp = new ChangingProfile(cmis.getMain(),facilityId,title,pack,contId,selectedR);
      cp.setFromWasteManage(true);
      cp.setWasteManage(wasteManage);
    }else if (fromWasteOrder) {
      cp = new ChangingProfile(cmis.getMain(),facilityId,title,pack,contId,selectedR);
      cp.setFromWasteOrder(true);
      cp.setWasteOrder(wasteOrder);
    }else {
      cp = new ChangingProfile(cmis.getMain(),facilityId,title,pack,contId,selectedR);
      cp.setFromWastePickupRequest(true);
      cp.setWastePickupRequest(wastePickupRequest);
    }
    cp.setChangeTsdfProfile(this.changeTsdfProfile);
    cp.setParent(cmis);
    cp.loadIt();
    CenterComponent.centerCompOnScreen(cp);
    cp.setVisible(true);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }
  void goEmptyInto(ActionEvent e) {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    String title = new String("Empty Into:");
    EmptyInto ei = new EmptyInto(cmis.getMain(),facilityId,title,storageLocation,this.vendorId,wasteManage);
    ei.setParent(cmis);
    ei.setFromContainerId(new Integer(containerId.firstElement().toString()));
    ei.setVendorProfileId(this.vendorProfileId);
    ei.loadIt();
    CenterComponent.centerCompOnScreen(ei);
    ei.setVisible(true);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }
  void goSealContainer(ActionEvent e) {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    String title = new String("Seal:");
    String contId = (String)containerId.elementAt(0);
    SealContainerDlg sd = new SealContainerDlg(cmis.getMain(),title,wasteManage,contId,selectedR);
    sd.setParent(cmis);
    sd.loadIt();
    CenterComponent.centerCompOnScreen(sd);
    sd.setVisible(true);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }
  void goAddInto(ActionEvent e) {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    String title = new String("Add Waste:");
    AddInto2 ai = new AddInto2(cmis.getMain(),facilityId,title,storageLocation,selectedR);
    ai.setParent(cmis);
    ai.setAddIntoContainerId(new Integer(containerId.firstElement().toString()));
    ai.setVendorProfileDesc(vendorProfileDesc);
    ai.setStateWasteCode(stateWasteCode);
    ai.setPackaging(pack);
    ai.setSizeUnit(sizeUnit);
    ai.loadIt();
    CenterComponent.centerCompOnScreen(ai);
    ai.setVisible(true);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }
  void goDeleteContainer(ActionEvent e){
    //first ask user for confirmation
    ConfirmDlg cfdlg = new ConfirmDlg(cmis.getMain(),"Deleting Container(s)",true);
    cfdlg.textArea1.setText("Once the container is deleted.\nIt is not recoverable.\nDo you want to proceed?");
    CenterComponent.centerCompOnScreen(cfdlg);
    cfdlg.setVisible(true);
    if (!cfdlg.yesFlag) {
      return;
    }

    boolean val = false;
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","DELETE_CONTAINER");         //String, String
      query.put("CONTAINER_IDS",this.containerId);     //String, Vector
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }
      Boolean b = (Boolean)result.get("RETURN_CODE");
      if(!b.booleanValue()){
        val = false;
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg(result.get("MSG").toString());
        gd.setVisible(true);
      }else{
        val = true;
        GenericDlg rd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
        rd.setMsg(result.get("MSG").toString());
        rd.setVisible(true);
      }
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception ee) {
        ee.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }
    if (val && this.fromWasteManage) {
      wasteManage.doSearch();
    }else {
      wasteTrackWin.fillWasteTrackTable("search");
    }
  }

  public void goUrl(String labelType) {
    int kind = 0;
    kind = URLGrab.LABEL;
    ClientHelpObjs.goLabelURL(cmis,this.cmis.getResourceBundle().getString("APP_NAME"),containerId,kind,labelType);
  }

  public void show(Component comp, int x, int y) {
    metaProfile.setEnabled(true);
    metaDisp.setEnabled(true);
    metaProp.setEnabled(true);

    // make sure the entire menu is showing
    // this is a real kludge
    int offset = 185;
    int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
    if(comp.getLocationOnScreen().y + y > screenH - offset) {
      y = screenH - offset - comp.getLocationOnScreen().y;
    }
    super.show(comp,x,y);
  }

}

class MetaActionListener implements ActionListener {
  WasteMetaPopUp you;
  public MetaActionListener(WasteMetaPopUp you){
    this.you = you;
  }

  public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().toString().equalsIgnoreCase("Traveler")) {
        you.goUrl("traveler");
      }else if (e.getActionCommand().toString().equalsIgnoreCase("HazardousWaste")) {
        you.goUrl("hazardous");
      }else if (e.getActionCommand().toString().equalsIgnoreCase("ChgProfile")) {
        you.goChangeProfile(e);
      }else if (e.getActionCommand().toString().equalsIgnoreCase("EmptyInto")) {
        you.goEmptyInto(e);
      }else if (e.getActionCommand().toString().equalsIgnoreCase("SealContainer")) {
        you.goSealContainer(e);
      }else if (e.getActionCommand().toString().equalsIgnoreCase("AddInto")) {
        you.goAddInto(e);
      }else if (e.getActionCommand().toString().equalsIgnoreCase("DeleteContainer")) {
        you.goDeleteContainer(e);
      }else{
        you.goUrl(e);
      }
  }
}

