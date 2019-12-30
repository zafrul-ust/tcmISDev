
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
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.TcmisHttpConnection;

public class FacilityCombo extends JComboBox {
  WorkAreaCombo wac;
  Vector facNames = new Vector();
  Vector facIds = new Vector();
  Vector appIds = new Vector();
  Vector appNames = new Vector();
  Vector facXApp = new Vector();
  Hashtable mrRules = new Hashtable();
  CmisApp cmis;
  String restrictGroup = "";

  //A - active, I - inactive, B - both
  String currentScreen = "";
  SearchPT searchPt;
  boolean catalogRefreshed = false;

  Hashtable accSysH = new Hashtable();
  Hashtable prefAccSysH = new Hashtable();

  boolean restricted = false;
  boolean needWorkArea = true;
  boolean forceSelectWorkArea = false;
  boolean forceSelectFacility = false;
  boolean restrictedToList = false;
  Vector onlyTheseFacIds = new Vector();

  // load types
  public static final String STANDARD = "STANDARD";
  public static final String STANDARD_WITH_HUBS = "STANDARD_WITH_HUBS";
  public static final String STANDARD_AND_DROP = "STANDARD_AND_DROP";
  public static final String DROP_FACS_ONLY = "DROP_FACS_ONLY";
  public static final String HUBS_ONLY = "HUBS_ONLY";
  public static final String PREF_FACS = "PREF_FACS";
  String loadFunction = STANDARD;

  public final String ALL_FAC_NAME = "All Facilities";
  public final String ALL_FAC_ID = "All";
  public final String ALL_APP_NAME = "All Work Areas";
  public final String ALL_APP_NAME2 = "All - Unrestricted";
  public final String ALL_APP_ID = "All";
  public final String ALL_HUBS_NAME = "All Hubs";
  public final String ALL_HUBS_ID = "All";
  public final String SELECT_FACILITY = "Select a Facility";
  public final String SELECT_WORK_AREA = "Select a Work Area";

  public final String ALL_RPT_FAC_ID = "All";
  public final String ALL_RPT_FAC_NAME = "Reportable Facilities";
  boolean useAllRptFacilities = false;

  public final String FACILITY_WIDE = "Facility Wide";
  boolean useFacilityWide = false;

  String holdFac;
  String holdWa;
  boolean loading = false;
  boolean loaded = false;
  boolean reloading = false;
  boolean useAllFacilities = true;
  boolean useAllWorkAreas = true;

  boolean authFacsOnly = false;

  public FacilityCombo(CmisApp cmis) {
    this();
    this.cmis = cmis;
  }
  public FacilityCombo() {
    super();
    wac = new WorkAreaCombo(this);
    this.addActionListener(new FacilityComboActionListener(this));
  }
  public void setCmisApp(CmisApp cmis){
    this.cmis = cmis;
  }

  public void setCatalogScreen(SearchPT searchpt, boolean b) {
    searchPt = searchpt;
    catalogRefreshed = b;
  }

  public FacilityCombo loadAnotherOne(FacilityCombo fc) {
    fc.cmis = this.getCmis();
    fc.facNames = this.getFacNames();
    fc.facIds = this.getFacIds();
    fc.appIds = this.getAppIds();
    fc.appNames = this.getAppNames();
    fc.facXApp = this.getFacXApp();
    fc.resetCombo();
    return fc;
  }

  CmisApp getCmis() {
    return cmis;
  }
  Vector getFacNames() {
    return (Vector)facNames.clone();
  }
  Vector getFacIds() {
    return (Vector)facIds.clone();
  }
  Vector getAppIds() {
    return (Vector)appIds.clone();
  }
  Vector getAppNames() {
    return (Vector)appNames.clone();
  }
  Vector getFacXApp() {
    return (Vector)facXApp.clone();
  }

  public void setUseWorkArea(boolean b){
    needWorkArea = b;
  }

  public WorkAreaCombo getWorkAreaCombo() {
    return wac;
  }
  public void setWorkAreaCombo(WorkAreaCombo wac) {
    this.wac = wac;
  }
  public String getSelectedFacName(){
    if(getSelectedItem().toString().equals(SELECT_FACILITY)){
      return "";
    }
    return getSelectedItem().toString();
  }
  public String getSelectedFacId() {
    if (this.getSelectedItem()==null)return "";
    if(this.getSelectedItem().toString().equals(SELECT_FACILITY)){
      return "";
    }
    String s = this.getSelectedItem().toString();
    if(s.equalsIgnoreCase(ALL_FAC_NAME)) return ALL_FAC_ID;
    if(s.equalsIgnoreCase(ALL_HUBS_NAME)) return ALL_HUBS_ID;

    if(s.equalsIgnoreCase(ALL_RPT_FAC_NAME)) return ALL_RPT_FAC_ID;

    for(int i=0;i<facNames.size();i++) {
      if(s.equals(facNames.elementAt(i).toString())) {
        return facIds.elementAt(i).toString();
      }
    }
    return "";
  }

  public String getSelectedWorkAreaID() {
    if(wac.getSelectedItem().toString().equals(SELECT_WORK_AREA)){
      return "";
    }
    if(getSelectedItem().toString().equals(SELECT_FACILITY)){
      return "";
    }
    String facID = getSelectedFacId();
    String waDesc = wac.getSelectedItem().toString();
    if(waDesc.equals(ALL_APP_NAME)||waDesc.equals(ALL_APP_NAME2) ||waDesc.equals(FACILITY_WIDE) )return ALL_APP_ID;
    for(int i=0;i<appNames.size();i++) {
      if(appNames.elementAt(i).toString().equals(waDesc)) {
        if(facXApp.elementAt(i).toString().equals(facID)) {
          return appIds.elementAt(i).toString();
        }
      }
    }
    return "";
  }

  public void reloadIt() {
    if(this.getSelectedIndex() < 0){
      holdFac = null;
      holdWa = null;
    }else{
      holdFac = this.getSelectedItem().toString();
      holdWa = wac.getSelectedItem().toString();
    }
    loaded = false;
    loadIt();
  }
  public void loadIt(){
    FacilityComboLoadThread aw = new FacilityComboLoadThread(this);
    aw.start();
  }
  public void resetCombo() {
    loading = true;
    loadThis();
    loading = false;
    loadWac();
  }

  public Hashtable getAccSysH(){
    return accSysH;
  }
  public Hashtable getPrefAccSysH(){
    return prefAccSysH;
  }

  public void setCurrentScreen(String s) {
    this.currentScreen = s;
  }



  public void loadFacilityData() {
    loading = true;
    loaded = false;
    Hashtable result = cmis.facLocAppData;
    if (result == null) {
      TcmisHttpConnection cgi = new TcmisHttpConnection("AdminObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","LOAD_FAC_APP"); //String, String
      query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
      query.put("FUNCTION",loadFunction);
      query.put("CURRENT_SCREEN",BothHelpObjs.makeBlankFromNull(this.currentScreen));  //String,String
      query.put("NEW","new");
      result = cgi.getResultHash(query);
    }
    if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(cmis.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return;
    }
    accSysH = (Hashtable)result.get("ACC_SYS");
    prefAccSysH = (Hashtable)result.get("PREF_ACC_SYS");

    facIds = (Vector)result.get("FAC_IDS");
    facNames = (Vector)result.get("FAC_NAMES");
    appIds = (Vector)result.get("APP_IDS");
    appNames = (Vector)result.get("APP_NAMES");
    facXApp = (Vector)result.get("FAC_X_APP");
    mrRules = (Hashtable)result.get("FAC_MR_RULES");

    if(facIds.size() == 1 && BothHelpObjs.isBlankString(facIds.elementAt(0).toString())){
      facIds = new Vector();
      facNames = new Vector();
      appIds = new Vector();
      appNames = new Vector();
      facXApp = new Vector();
    }

    //some how 'All' got added to facIds and not facNames
    if (facIds.size() != facNames.size()) {
      if ("All".equalsIgnoreCase((String)facIds.elementAt(0))) {
        facIds.removeElementAt(0);
      }
      if ("All".equalsIgnoreCase((String)facNames.elementAt(0))) {
        facNames.removeElementAt(0);
      }
    }

    loadThis();
    if(reloading){
      reloading = false;
      if(!BothHelpObjs.isBlankString(holdFac)){
        setSelectedItem(holdFac);
      }
      if(!BothHelpObjs.isBlankString(holdWa)){
        wac.setSelectedItem(holdWa);
      }
    }
    loading = false;
    if(getSelectedIndex() < 0 && getItemCount() > 0) setSelectedIndex(0);
    loadWac();
    loaded = true;
    firePropertyChange("loaded",0,1);
  }

  public void setUseAllRptFacilities(boolean b){
    useAllRptFacilities = b;
  }

  public void setUseAllFacilities(boolean b){
    useAllFacilities = b;
  }
  public void setForceSelectFacility(boolean b){
    forceSelectFacility = b;
  }
  public void setForceSelectWorkArea(boolean b){
    forceSelectWorkArea = b;
  }

  public void setUseAllWorkAreas(boolean b){
    useAllWorkAreas = b;
  }
  public void setLoadType(String s){
    loadFunction = s;
  }
  void loadThis() {
    if (this.getItemCount() > 0) this.removeAllItems();
    if(restricted){
      buildAllowedFacs();
      Vector v = new Vector();
      for(int i=0;i<onlyTheseFacIds.size();i++){
        for(int j=0;j<facIds.size();j++){
          if(facIds.elementAt(j).toString().equals(onlyTheseFacIds.elementAt(i).toString())){
            v.addElement(facNames.elementAt(j).toString());
            break;
          }
        }
      }
     ClientHelpObjs.loadChoiceFromVector(this,v);
    }else{
     ClientHelpObjs.loadChoiceFromVector(this,facNames);
    }
    if(this.getItemCount() > 0){
      ClientHelpObjs.choiceSort(this);
      setSelectedIndex(0);
    }
    if(useAllFacilities && !restricted) {
      addAllFacilities();
    }

    if (useAllRptFacilities) {
      addAllRptFacilities();
    }

    if(forceSelectFacility && getItemCount() > 1){
      insertItemAt(SELECT_FACILITY,0);
      setSelectedIndex(0);
    }
}

  void loadWac() {
    if(!needWorkArea)return;
    if(loading || getItemCount() == 0)return;

    wac.setLoading(true);
    if (wac.getItemCount() > 0) wac.removeAllItems();
    String myFacName = this.getSelectedItem().toString();
    if(BothHelpObjs.isBlankString(myFacName)){
      wac.setLoading(false);
      return;
    }
    if(myFacName.equals(ALL_FAC_NAME) || myFacName.equals(ALL_RPT_FAC_NAME)) {
      wac.addItem(ALL_APP_NAME);
      wac.setLoading(false);
      return;
    }
    String myFacID = this.getSelectedFacId();
    Vector v = new Vector();
    for(int x=0;x<facXApp.size();x++) {
      if( myFacID.equals((String)facXApp.elementAt(x)) ) {
        v.addElement((String)appNames.elementAt(x));
      }
    }
    if(v.isEmpty()){
      wac.setLoading(false);
      return;
    }
    Vector v2 = BothHelpObjs.sort(v);
    DefaultComboBoxModel model = new DefaultComboBoxModel(v2);
    wac.setModel(model);
    if(useAllWorkAreas) {
      addAllWorkAreas();
    }
    if(forceSelectWorkArea && wac.getItemCount() > 1){
      wac.insertItemAt(SELECT_WORK_AREA,0);
      wac.setSelectedIndex(0);
    }
    wac.firePropertyChange("loaded",0,1);
    wac.setLoading(false);

    if (wac.getItemCount() == 1 && catalogRefreshed) {
      searchPt.setWorkAreaActionChanged();
    }
    if (searchPt != null) {
      searchPt.setSelectedFacilityInfo();
    }
  } //end of method

  public void setSelectedFacId(String facId){
    for(int i=0;i<facIds.size();i++){
      if(facId.equals(facIds.elementAt(i).toString())){
        setSelectedItem(facNames.elementAt(i).toString());
        break;
      }
    }
  }

  public void restrictToAdminFacilities(boolean b){
    restrictToGroup("administrator",b);
  }
  protected void buildAllowedFacs(){
    if(restrictedToList)return;
    onlyTheseFacIds = cmis.getGroupMembership().getFacsForGroup(restrictGroup);
  }

  public void restrictToGroup(String groupID, boolean b){
   restrictedToList = false;
   restrictGroup = new String(groupID);
    restricted = b;
    if(b){
      buildAllowedFacs();
    }else{
      onlyTheseFacIds = new Vector();
    }
    if(loaded){
      loadThis();
    }
    revalidate();
  }
  public void restrictToTheseFacIds(Vector v){
    restrictedToList = true;
    onlyTheseFacIds = v;
    restricted = true;
  }
  public boolean isLoading(){
    return loading;
  }

  public boolean isLoaded(){
    return loaded;
  }

  public void addAllRptFacilities() {
    int i = this.getItemCount();
    boolean hasAll = false;
    for(int j=0;j<i;j++) {
      if(this.getItemAt(j).toString().equalsIgnoreCase(ALL_RPT_FAC_NAME)) {
        hasAll = true;
        break;
      }
    }
    if(!hasAll){
      this.insertItemAt(ALL_RPT_FAC_NAME,0);
    }
  }

  public void addAllFacilities() {
    int i = this.getItemCount();
    boolean hasAll = false;
    for(int j=0;j<i;j++) {
      if(this.getItemAt(j).toString().equalsIgnoreCase(ALL_FAC_NAME) ||
         this.getItemAt(j).toString().equalsIgnoreCase(ALL_HUBS_NAME)){
        hasAll = true;
        break;
      }
    }
    if(!hasAll){
      if(this.loadFunction.equals(this.HUBS_ONLY)){
        this.insertItemAt(ALL_HUBS_NAME,0);
      }else{
        this.insertItemAt(ALL_FAC_NAME,0);
      }
      this.setSelectedIndex(0);
    }
  }

  public void setUseFacilityWide(boolean b) {
    useFacilityWide = b;
  }
  void addAllWorkAreas() {
    int i = wac.getItemCount();
    boolean hasAll = false;
    for(int j=0;j<i;j++) {
      if (!useFacilityWide) {
        if(wac.getItemAt(j).toString().equalsIgnoreCase(ALL_APP_NAME)  ||
           wac.getItemAt(j).toString().equalsIgnoreCase(ALL_APP_NAME2) ){
          hasAll = true;
          break;
        }
      }else {
        if(wac.getItemAt(j).toString().equalsIgnoreCase(FACILITY_WIDE)) {
          hasAll = true;
          break;
        }
      }
    }
    if(!hasAll){
      if (!useFacilityWide) {
        wac.insertItemAt(ALL_APP_NAME,0);
      }else {
        wac.insertItemAt(FACILITY_WIDE,0);
      }
      wac.setSelectedIndex(0);
    }
  }

  // Facility MR Rules info
  public boolean isPoRequired(){
    Hashtable h = (Hashtable)mrRules.get(this.getSelectedFacId());
    Boolean B = (Boolean)h.get("PO_REQ");
    return B.booleanValue();
  }
  public boolean useIndirectDirectCharges(){
    Hashtable h = (Hashtable)mrRules.get(this.getSelectedFacId());
    Boolean B = (Boolean)h.get("INDIRECT_OR_DIRECT");
    return B.booleanValue();
  }
  public boolean isApproverRequired(){
    Hashtable h = (Hashtable)mrRules.get(this.getSelectedFacId());
    Boolean B = (Boolean)h.get("APPR_REQ");
    return B.booleanValue();
  }
  public boolean isReleaserRequired(){
    Hashtable h = (Hashtable)mrRules.get(this.getSelectedFacId());
    Boolean B = (Boolean)h.get("REL_REQ");
    return B.booleanValue();
  }
  public boolean isChargeNumRequired(){
    Hashtable h = (Hashtable)mrRules.get(this.getSelectedFacId());
    Boolean B = (Boolean)h.get("CHARGE_NUM_REQ");
    return B.booleanValue();
  }
  public String getDirectChargeLabel1(){
    Hashtable h = (Hashtable)mrRules.get(this.getSelectedFacId());
    return h.get("DCHARGE_LABEL_1").toString();
  }
  public String getDirectChargeLabel2(){
    Hashtable h = (Hashtable)mrRules.get(this.getSelectedFacId());
    return h.get("DCHARGE_LABEL_2").toString();
  }
  public String getIndirectChargeLabel1(){
    Hashtable h = (Hashtable)mrRules.get(this.getSelectedFacId());
    return h.get("IND_CHARGE_LABEL_1").toString();
  }
  public String getIndirectChargeLabel2(){
    Hashtable h = (Hashtable)mrRules.get(this.getSelectedFacId());
    return h.get("IND_CHARGE_LABEL_2").toString();
  }
}

class FacilityComboLoadThread extends Thread {
 FacilityCombo parent;

  public FacilityComboLoadThread(FacilityCombo parent){
     super("FacilityComboLoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadFacilityData();
  }
}
class FacilityComboActionListener implements ActionListener {
  FacilityCombo fc;
  public FacilityComboActionListener(FacilityCombo fc) {
    this.fc = fc;
  }
   public void actionPerformed(ActionEvent e){
     fc.loadWac();

     fc.firePropertyChange("Changed",0,1);

   }
}
