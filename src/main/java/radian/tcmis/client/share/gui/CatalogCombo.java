
//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;

import java.awt.event.*;
import java.beans.*;
import java.util.*;
//import com.borland.jbcl.view.*;
import javax.swing.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;

public class CatalogCombo extends JComboBox {
  FacilityCombo fc;
  //trong 1-29-01
  // A - active, I - inactive, B - both
  //String workAreaStatus = "A";
  String currentScreen = "NewChem";

  CmisApp cmis;
  Vector catInfo;

  boolean fcLoaded = false;

  boolean loading = false;
  boolean loaded = false;
  boolean restrictToGroup = false;
  String groupId = "";

  public CatalogCombo() {
    super();
  }

  public CatalogCombo(CmisApp cmis) {
     this(cmis,FacilityCombo.STANDARD,false);
  }

  public CatalogCombo(CmisApp cmis,String facType,boolean useAllFac) {
    this();
    fc = new FacilityCombo();
    fc.setUseAllFacilities(true);
    fc.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().equalsIgnoreCase("loaded")) {
          fcLoaded = true;
          dataLoaded();
        }
      }
    });
    this.addActionListener(new CatalogComboActionListener(this));
    this.setCmisApp(cmis,facType,useAllFac);
  }

  public void setCmisApp(CmisApp cmis, String facType, boolean useAllFac){
    this.cmis = cmis;
    fc.setCmisApp(cmis);
    fc.setUseAllFacilities(useAllFac);
    fc.setUseAllWorkAreas(true);
    fc.setLoadType(facType);

    //trong 1-29-01
    //fc.setWorkAreaStatus(workAreaStatus);
    fc.setCurrentScreen(this.currentScreen);

    fc.loadIt();
    if (useAllFac)  fc.addAllFacilities();
  }
  public CmisApp getCmis() {
    return cmis;
  }
  public FacilityCombo getFacilityCombo() {
    return fc;
  }

  public void loadIt(){
    CatalogComboLoadThread aw = new CatalogComboLoadThread(this);
    aw.start();
  }

  public void loadCatalogData() {
    loading = true;
    loaded = false;
    TcmisHttpConnection cgi = new TcmisHttpConnection("AdminObj",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","LOAD_CATALOG_COMBO"); //String, String
    query.put("USER_ID",cmis.getUserId());
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      return;
    }
    catInfo = (Vector)result.get("CATALOG_COMBO_INFO");
    Vector v = new Vector();
    loaded = true;
    setCatalogList();
    /*
    for(int i=0;i<catInfo.size();i++){
      Hashtable h = (Hashtable)catInfo.elementAt(i);
      v.addElement(h.get("CATALOG_DESC"));
    }
    v = BothHelpObjs.sort(v);
    DefaultComboBoxModel model = new DefaultComboBoxModel(v);
    this.setModel(model);
    */

    loading = false;
    //loaded = true;
    dataLoaded();

  }
  void setCatalogList(){
    if(!loaded)return;
    Vector v = new Vector();
    for(int i=0;i<catInfo.size();i++){
      Hashtable h = (Hashtable)catInfo.elementAt(i);
      System.out.println("Catalogs:"+(String) h.get("CATALOG_DESC"));
      Vector myFacs = cmis.getGroupMembership().getFacsForGroup(groupId);
      if(restrictToGroup){
        Vector f = (Vector)h.get("FAC_IDS");
        for(int j=0;j<f.size();j++){
          if(myFacs.contains(f.elementAt(j))){
            if(!v.contains(f.elementAt(j))){
              v.addElement(h.get("CATALOG_DESC"));
            }
            break;
          }
        }
      }else{
        v.addElement(h.get("CATALOG_DESC"));
      }
    }
    v = BothHelpObjs.sort(v);
    DefaultComboBoxModel model = new DefaultComboBoxModel(v);
    this.setModel(model);
  }

  public String getSelectedCatalogId(){
    Hashtable h = getHashForDesc(getSelectedCatalogDesc());
    return h.get("CATALOG_ID").toString();
  }
  public String getSelectedCatalogDesc(){
    return this.getSelectedItem().toString();
  }

  void loadFc(){
    if(!loaded || loading)return;
    Hashtable h = getHashForDesc(getSelectedCatalogDesc());
    Vector v = (Vector) h.get("FAC_IDS");
    if(restrictToGroup){
      Vector u = new Vector();
      Vector f = cmis.getGroupMembership().getFacsForGroup(groupId);
      for(int i=v.size()-1;i>=0;i--){
        String s = v.elementAt(i).toString();
        for(int j=0;j<f.size();j++){
          if(f.elementAt(j).toString().equalsIgnoreCase(s)){
            u.addElement(s);
            break;
          }
        }
      }
      v = u;
    }
    fc.restrictToTheseFacIds(v);
    fc.loadThis();
    if (fc.useAllFacilities) fc.addAllFacilities();
  }
  public void restrictToGroup(boolean b, String s){
    restrictToGroup = b;
    groupId = s;
    setCatalogList();
  }

  private Hashtable getHashForId(String s){
    Hashtable h = null;
    for(int i=0;i<catInfo.size();i++){
      h = (Hashtable) catInfo.elementAt(i);
      if(h.get("CATALOG_ID").toString().equals(s))return h;
    }
    return h;
  }
  private Hashtable getHashForDesc(String s){
    Hashtable h = null;
    for(int i=0;i<catInfo.size();i++){
      h = (Hashtable) catInfo.elementAt(i);
      if(h.get("CATALOG_DESC").toString().equals(s))return h;
    }
    return h;
  }
  private void dataLoaded(){
    if(loaded && fcLoaded){
      loadFc();
      firePropertyChange("loaded",0,1);
    }
  }
}

class CatalogComboLoadThread extends Thread {
 CatalogCombo parent;
  public CatalogComboLoadThread(CatalogCombo parent){
     super("CatalogComboLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadCatalogData();
  }
}
class CatalogComboActionListener implements ActionListener {
  CatalogCombo cc;
  public CatalogComboActionListener(CatalogCombo cc) {
    this.cc = cc;
  }
   public void actionPerformed(ActionEvent e){
     cc.loadFc();
   }
}