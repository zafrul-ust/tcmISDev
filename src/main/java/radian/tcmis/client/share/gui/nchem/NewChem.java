//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian Internationalposi
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui.nchem;


import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;
//import radian.tcmis.client.share.reports.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
public class NewChem extends JPanel   {

  StaticJLabel facLabel = new StaticJLabel("Facility:");
  StaticJLabel appLabel = new StaticJLabel("Work Area:");

  CmisApp cmis;
  boolean dataLoaded = false;
  boolean facCLoaded = false;
  protected static String MAT = "Material / Size";
  protected static String PART = "Catalog Part";
  protected static String APP = "Approval";
  protected static String REQUESTOR = "Requestor";
  protected static String VIEWER = "Viewer";
  protected static String APPROVER = "Approver";
  protected static String SUPER_APPROVER = "Super Approver";

  Integer req_id = null;
  ImageIcon ss = new ImageIcon();

  MatPane matPP   = new MatPane();
  PartPane partPP;
  ApprPane apprPP = new ApprPane();

  EvalMRPane evalMRP = null;
  JPanel evalP = new JPanel();
  JToggleButton evalB = new JToggleButton();

  // type ahead
  NextNameTextField manufT = new NextNameTextField();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel lPanel = new JPanel();
  JPanel rPanel = new JPanel();
  JPanel actionP = new JPanel();
  JPanel screenP = new JPanel();
  JPanel infoP = new JPanel();
  JPanel jPanel6 = new JPanel();
  StaticJLabel jLabel2 = new StaticJLabel();
  BorderLayout borderLayout2 = new BorderLayout();
  DataJLabel statusL = new DataJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  DataJLabel viewL = new DataJLabel();
  StaticJLabel jLabel5 = new StaticJLabel();
  DataJLabel reqL = new DataJLabel();
  JPanel jPanel7 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JToggleButton appB = new JToggleButton();
  JToggleButton partB = new JToggleButton();
  JToggleButton matB = new JToggleButton();

  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel9 = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton approveB = new JButton();
  JButton deleteB = new JButton();
  JButton submitB = new JButton();
  CardLayout cardLayout1 = new CardLayout();
  JPanel partP = new JPanel();
  JPanel appP = new JPanel();
  JPanel matP = new JPanel();

  ButtonGroup toggleGroup = new ButtonGroup();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JButton draftB = new JButton();
  BorderLayout borderLayout5 = new BorderLayout();
  BorderLayout borderLayout6 = new BorderLayout();
  BorderLayout borderLayout7 = new BorderLayout();

  Hashtable matHash;
  Hashtable partHash;
  Hashtable apprHash;

  ApprovalStatus ncas = null;

  /** Static values
  */
  Hashtable unit=null;  // value and if netwt is needed
  Vector netwt = null;
  Vector pack=null;
  Vector period=null;
  Vector temps=null;
  Vector labelColors=null;
  boolean requireLabelColor = false;
  boolean cigRestricted = false;
  Hashtable groups=null;
  boolean isRequestor=false;
  String item_id = null;
  boolean ncasLoadDone = false;
  int istatus =0;
  int view=1;
  ApprDlg ncaDlg = null;
  int startView = 0;
  String origPart="";
  Boolean passedQC = new Boolean(false);

  //trong 3/1/00
  String eval = new String("normal");
  Integer prNumber;
  Integer approver_type = new Integer(0);
  Boolean isEval;
  String myFacilityDesc = null;
  String myWorkAreaDesc = null;
  String myCatalog = null;
  boolean unitPackAudit = true;  //trong 4/20 decide whether or not to audit unit and packaging style
  boolean qtyTmpAudit = true;    //trong 9-19 decide whether or not to audit qty and temperature

  boolean auditSupplierInfo = true;   //5-13-01 does not require info for SWA Eng Eval
  boolean auditOrderTypeInfo = true;  //5-13-01 does not require info for SWA Eng Eval

  boolean isAddMetaOK = true;
  static final int PROTECT_REQUESTOR=0;
  static final int PROTECT_VIEWER=1;
  static final int PROTECT_APPROVER=2;
  static final int PROTECT_SUPER_APPROVER=3;
  StaticJLabel jLabel1 = new StaticJLabel();
  DataJLabel dateL = new DataJLabel();
  String status;
  static final int NEW_MATERIAL = 0;
  static final int NEW_SIZE_PACK = 1;
  static final int NEW_PART = 2;
  static final int NEW_APPROVAL = 3;
  static final int NEW_GROUP = 4;
  static final int PENDING_APPROVAL = 5;
  static final int DRAFT = 6;
  static final int REJECTED = 7;
  static final int APPROVED = 8;
  static final int APPROVED_RADIAN = 9;
  static final int BANNED = 10;
 // static final int CONVERT_TO_CATALOG = 11;
 // static final int ENGINEERING_EVALUATION = 12;
  JButton appDetailB = new JButton();

  Hashtable dynamicH = new Hashtable();

  String requestorName = "";
  String currentStatus = "";
  String submitDate = "";

  Hashtable newChemDetailH = null;

  Hashtable catalogDirectedChargeInfo = null;
  Vector catPartDirectedChargeRequired = null;
  String catalogItemID = "";
  String replacePartNum = "";
  Boolean approvalDetailRequired = new Boolean(true);
  Hashtable catalogConsumableOptionH = null;
  String requestorEditFacMSDSID = "NO";
  Boolean verifyCategoryRequired = new Boolean(false);
  Boolean categoryStatusOverrideByApprover = new Boolean(false);

  String catAddSingleApp = "";

  public NewChem(){
    try {
      jbInit();
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  public NewChem(CmisApp cmis) {
    this.cmis = cmis;
    try {
      jbInit();
      ClientHelpObjs.setEnabledContainer(this,false);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  //3-13-02
  public void setNewChemDetailHash(Hashtable h) {
    this.newChemDetailH = h;
  }

  public final void setReqId(int i){
    req_id = new Integer(i);
  }

  //trong 3/1/00
  public void setEval(String s) {
    eval = s;
  }
  public void setPrNumber(Integer s) {
    prNumber = s;
  }


  public void loadItAction(){
    //long sTime = new java.util.Date().getTime();      //2-27-02
    ClientHelpObjs.setEnabledContainer(this,false);
    // Request ID
    reqL.setText(req_id.toString());
    //3-13-02
    Hashtable result = null;
    if (newChemDetailH == null) {
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","LOAD_SCREEN"); //String, String
      query.put("USER_ID",cmis.getUserId().toString());
      query.put("REQ_ID",(new Integer(req_id.intValue())).toString());
      query.put("EVAL",new Boolean(!eval.equalsIgnoreCase("normal")));
      result = cgi.getResultHash(query);
    }else {
      result = newChemDetailH;
    } //end of 3-13-02


    if (result == null){
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    Hashtable scrData = (Hashtable) result.get("SCREEN_DATA");
    if (scrData.containsKey("MESSAGE")){ // error
        GenericDlg.showMessage((String) scrData.get("MESSAGE") );
        // Delete tab and go to search
        cmis.getMain().goNewChemMat(2,req_id.intValue());
        //cmis.getMain().stopImage();
        CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
        return;
    }

    this.eval = (String)result.get("ENG_EVAL_TYPE");
    catAddSingleApp = (String)result.get("CAT_ADD_SINGLE_APP");
    loadStaticComponents(scrData);
    partPP = new PartPane(requireLabelColor,cigRestricted);
    // Needs to get msds and partnum
    Hashtable dyn = (Hashtable) scrData.get("DYNAMIC_DATA");
    dynamicH = dyn;
    status =  (String) dyn.get("REQUEST_STATUS");
    Hashtable item = (Hashtable) dyn.get("ITEM_0");
    String mafCheck=null;
    if (item!=null) mafCheck = (String) item.get("MAT_DESC");

    dataLoaded = true;
    // done with load
    loadDone();

    // builds material panel
    matPP = new MatPane();
    matPP.setRequestorEditFacMSDSID(requestorEditFacMSDSID);
    String parts = (String) dyn.get("PARTS_NUM");
    matPP.setPartsNum(new Integer(parts));

    toggleGroup.setSelected(matB.getModel(),true);
    //Material
    matPP.setNewChem(this);
    matPP.setUnit(unit);
    matPP.setNetWt(netwt);
    matPP.setPack(pack);
    matPP.setPane();  // fix tab panel
    matPP.setToolTipText("Right mouse click to add/delete parts.");
    matP.add(matPP,BorderLayout.CENTER);
    matP.revalidate();

    // Part
    partPP.setCmisApp(cmis);
    partPP.setNewChem(this);
    partPP.setPeriod(period);
    partPP.setTemperature(temps);
    partPP.setLabelColor(labelColors);
    if (eval.equalsIgnoreCase("newEval") || eval.equalsIgnoreCase("oldEval") || "Y".equals(catAddSingleApp)){
      if (eval.equalsIgnoreCase("newEval") || eval.equalsIgnoreCase("oldEval")) {
        partPP.setEngEval(true);
      }
      myCatalog = (String)result.get("CATALOG_ID");
    }
    //trong 4/18
    partPP.setDynData(dyn);
    partPP.setUserId(cmis.getUserId().toString());
    partPP.setPane();
    partP.add(partPP,BorderLayout.CENTER);
    partP.revalidate();

    // Approval
    apprPP.setCmisApp(cmis);
    apprPP.setNewChem(this);
    apprPP.setPeriod(period);
    apprPP.setGroups(groups);
    if (eval.equalsIgnoreCase("newEval") || eval.equalsIgnoreCase("oldEval") || "Y".equals(catAddSingleApp)){
      myFacilityDesc = (String)result.get("FACILITY_ID");
      myWorkAreaDesc = (String)result.get("WORK_AREA");
      if (eval.equalsIgnoreCase("newEval") || eval.equalsIgnoreCase("oldEval")) {
        apprPP.setEngEval(true);
      }
    }
    apprPP.setPane();
    appP.add(apprPP,BorderLayout.CENTER);
    appP.revalidate();

    // Rigth Panel
    rPanel.revalidate();

    // Load dyn data
    loadDynamicComponents(scrData);

    //4-10-02 Eng Eval here because isEval flag won't be set until loadDynamicComponents is call
    if (isEval.booleanValue()) {
      if (result.containsKey("ENG_EVAL_MR_INFO")) {
        evalMRP = new EvalMRPane();
        evalMRP.setCmisApp(cmis);
        evalMRP.setNewChem(this);
        evalMRP.setEngEvalMRData((Hashtable)result.get("ENG_EVAL_MR_INFO"));
        evalMRP.setPane();
        evalP.add(evalMRP,BorderLayout.CENTER);
        evalP.revalidate();
      }
    }

    // load the approval info
    Vector roles = (Vector) result.get("ROLES");
    Vector stat = (Vector) result.get("STATUS");
    Vector approvers = (Vector) result.get("APPROVERS");
    Vector dates = (Vector) result.get("DATES");
    Vector reasons = (Vector) result.get("REASONS");
    Vector appNums = (Vector) result.get("APPROVERS_NUMS");
    Vector roleType = (Vector) result.get("ROLE_TYPE");
    Vector appSuperUser = (Vector) result.get("SUPER_USER");
    // ncas will be used again on the approval dlg window....
    ncas = new ApprovalStatus(roles,stat,approvers,dates,reasons,appNums,roleType,appSuperUser);
    ncasLoadDone = true;

    // protect components
    loadViewInfo();
    protectScreen(view,result.containsKey("ENG_EVAL_MR_INFO"));  //whether to display section 4 (eng eval)

    matPP.protectNetWt();
    matP.setVisible(true);
    partP.setVisible(false);
    appP.setVisible(false);
    evalP.setVisible(false);

    if ("Draft".equalsIgnoreCase(currentStatus) && "Requestor".equalsIgnoreCase(viewL.getText())) {
      jPanel9.add(submitB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      jPanel9.add(draftB, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    }else {
      jPanel9.add(appDetailB, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
      //allows approvers to save data
      if (view == NewChem.PROTECT_APPROVER || view == NewChem.PROTECT_SUPER_APPROVER) {
        draftB.setText("Save");
        jPanel9.add(draftB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      }else {
        jPanel9.add(submitB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        submitB.setEnabled(false);
      }
    } //end of else (not draft status)
    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);

  }  //end of method

  void loadViewInfo(){
     if (isRequestor && istatus < NEW_GROUP  ){                           //1-22-02 replaces <= with <
         view=this.PROTECT_REQUESTOR;
     } else if (istatus == 7 || istatus == 9 ||  istatus == 10){         //replaces 8 with 7
          view=this.PROTECT_VIEWER;
     } else if (istatus  >= NEW_GROUP  ){  // need to check approvers and roles 1-22-02 replaces > with >=
         loadRolesInfo();
     } else {                             // user working on
         view=this.PROTECT_VIEWER;
     }

  }

  //modified 1-27-02 only allowing approver(s) at current status to approve
  protected void loadRolesInfo(){
     if (ncas.needsApproval(cmis.getUserId().intValue())){
        if (dynamicH.containsKey("APPROVERS")) {
          Vector tmpV = (Vector)dynamicH.get("APPROVERS");
          if (ncas.isSuperUser(cmis.getUserId().intValue()) && tmpV.contains(cmis.getUserId().toString())){
            view = this.PROTECT_SUPER_APPROVER;
            approver_type = new Integer(12);        //trong 3/26 allow this user full access to all edit fields
          } else if (tmpV.contains(cmis.getUserId().toString())) {
            view = this.PROTECT_APPROVER;
            approver_type = ncas.getApproverType(cmis.getUserId().intValue());
          } else {
            view = this.PROTECT_VIEWER;
            approver_type = new Integer(0);          //trong 3/29 just a viewer not allow to edit any field
          }
        }else {
          view = this.PROTECT_VIEWER;
          approver_type = new Integer(0);           //trong 3/29 just a viewer not allow to edit any field
        }
     } else {
        view = this.PROTECT_VIEWER;
        approver_type = new Integer(0);             //trong 3/29 just a viewer not allow to edit any field
     }
  }

  protected void loadStaticComponents(Hashtable res){
    // Static
    Hashtable hash = (Hashtable) res.get("STATIC_DATA");
    if (hash.containsKey("UNITS")) unit = (Hashtable) hash.get("UNITS");
    if (hash.containsKey("NETWT")) netwt = (Vector) hash.get("NETWT");
    if (hash.containsKey("PACKAGES")) pack = (Vector) hash.get("PACKAGES");
    if (hash.containsKey("PERIOD")) period = (Vector) hash.get("PERIOD");
    if (hash.containsKey("TEMPERATURES")) temps = (Vector) hash.get("TEMPERATURES");
    if (hash.containsKey("LABEL_COLORS")) labelColors = (Vector) hash.get("LABEL_COLORS");
    if (hash.containsKey("REQUIRE_LABEL_COLOR")) requireLabelColor = ((Boolean) hash.get("REQUIRE_LABEL_COLOR")).booleanValue();
    if (hash.containsKey("COMPONENT_INVENTORY_GROUP_RESTRICTED")) cigRestricted = ((Boolean) hash.get("COMPONENT_INVENTORY_GROUP_RESTRICTED")).booleanValue();
    /**
      Groups are hashtable with keys being the facility and then a Vector
      that contains ID and DESC as keys for a hash
    */
    if (hash.containsKey("GROUPS")) groups = (Hashtable) hash.get("GROUPS");
    catalogDirectedChargeInfo = (Hashtable)hash.get("CATALOG_DIRECTED_CHARGE_INFO");
    catalogConsumableOptionH = (Hashtable)hash.get("CATALOG_CONSUMABLE_OPTION_INFO");
    if (hash.containsKey("APPROVAL_DETAIL_REQUIRED")) {
      approvalDetailRequired = (Boolean) hash.get("APPROVAL_DETAIL_REQUIRED");
    }
    if (hash.containsKey("EDIT_CUSTOMER_MSDS")) {
      requestorEditFacMSDSID = (String) hash.get("EDIT_CUSTOMER_MSDS");
    }
  }

  protected void loadDynamicComponents(Hashtable res){
    /** DYNAMIC_DATA contains the data from already created request
    */
    Hashtable hash = (Hashtable) res.get("DYNAMIC_DATA");
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    // decide requestor
    //12-20-01
    requestorName = (String) hash.get("REQUESTOR_NAME");
    currentStatus = (String) hash.get("STATUS_DESC");

    if ("Draft".equalsIgnoreCase(currentStatus)) {
      rPanel.setBorder(ClientHelpObjs.groupBox("Section: "+MAT+"   Requestor: "+requestorName+"   Status: "+currentStatus+" "));
    }else {
      submitDate = (String) hash.get("SUBMIT_DATE");
      rPanel.setBorder(ClientHelpObjs.groupBox("Section: "+MAT+"   Requestor: "+requestorName+"   Status: "+currentStatus+"   Submitted: "+submitDate));
    }
    ClientHelpObjs.makeDefaultColors(this);

    String requestor=(String) hash.get("REQUESTOR");
    isRequestor=false;
    if (requestor.equalsIgnoreCase(cmis.getUserId().toString())) isRequestor=true;

    String isEvalFlag = (String)hash.get("ENGINEERING_EVAL");
    isEval = new Boolean(isEvalFlag.equalsIgnoreCase("Y"));
    if (eval.equalsIgnoreCase("newEval")) {
      statusL.setText("New Eng Eval");
    }else if (eval.equalsIgnoreCase("oldEval")) {
      statusL.setText("Reorder Eng Eval");
    } else if (eval.equalsIgnoreCase("convertEval")) {
      statusL.setText("Convert Eng Eval to Cat");
    } else if (eval.equalsIgnoreCase("normal") && isEval.booleanValue()){
      statusL.setText("Eng Eval");
    } else {
      statusL.setText((String) hash.get("STARTING_VIEW_DESC"));            //12-20-01 this field use to be status, we changed to starting point instead and move status over to border title
    }

    // View
    istatus = Integer.parseInt(status);
    dateL.setText((String) hash.get("REQUEST_DATE"));

    //trong 5/6
    if (isEval.booleanValue() && !eval.equalsIgnoreCase("convertEval")) {
      Boolean freeS = (Boolean) hash.get("FREE_SAMPLE");
      Hashtable tcmISFeature = (Hashtable)hash.get("TCMIS_FEATURE");
      String featureMode = (String)tcmISFeature.get("newChem.1.supplier");
      if (featureMode.equalsIgnoreCase("1")) {  // "1" <=> number one
        matPP.supplier.showFreeSample(freeS.booleanValue());
      }else {
        matPP.supplier.showFreeSampleNotRequired(freeS.booleanValue());
        auditSupplierInfo = false;
      }
    }

    // Part
    // hold this for audit on change and for Eng Eval
    origPart = (String) hash.get("CAT_PART_NO");
    partPP.setConsumableOption((String)hash.get("CONSUMABLE"));
    Hashtable tcmISFeature = (Hashtable) dynamicH.get("TCMIS_FEATURE");
    String featureMode = (String)tcmISFeature.get("newChem.2.category");
    if ("1".equalsIgnoreCase(featureMode)) {
      partPP.setCategoryOption(true);
      partPP.setSelectedCategory((Collection)hash.get("CATEGORY"));
      partPP.setSelectedCategoryComment((Hashtable)hash.get("CATEGORY_COMMENT"));
      partPP.setCategoryDelimiter((String)hash.get("CATEGORY_DELIMITER"));
      partPP.setCategoryStatus((String)hash.get("CATEGORY_STATUS"));
      partPP.setCategoryStatusForButton((String)hash.get("CATEGORY_STATUS_FOR_BUTTON"));
      partPP.displayCatalogPartCategoryOption();
      verifyCategoryRequired = (Boolean)hash.get("VERIFY_CATEGORY_REQUIRED");
    }

    catPartDirectedChargeRequired = (Vector)hash.get("CATALOG_PART_DIRECTED_CHARGE_REQUIRED_NEW");
    item_id = (String) hash.get("ITEM_ID");
    catalogItemID = (String) hash.get("CATALOG_ITEM_ID");
    replacePartNum = (String)hash.get("REPLACES_PART_NO");
    String currentClient = cmis.getResourceBundle().getString("CLIENT_INITIALS").toString();
    currentClient = currentClient.toUpperCase();
    if (eval.equalsIgnoreCase("convertEval") && origPart.startsWith(currentClient+"-EVAL")) {
      partPP.partNumT.setText("");
    } else {
      if (catalogItemID.length() > 1) {
        partPP.partNumT.setText(catalogItemID);
      }else {
        partPP.partNumT.setText(origPart);
      }
    }
    if (!BothHelpObjs.isBlankString((String) hash.get("CATALOG_ID"))) {
      partPP.selectCatalogID((String) hash.get("CATALOG_ID"));
    }

    // Kits Pane
    // catalog from searchpt
    Hashtable item = (Hashtable) hash.get("ITEM_0");
    if (item==null) return;
    String mafCheck = (String) item.get("MAT_DESC");
    if (BothHelpObjs.makeBlankFromNull(mafCheck).length()==0) return;

    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);

    JTabbedPane jt = matPP.getTabPane();
    for (int i=0;i<jt.getTabCount();i++){
       KitsPane kp = (KitsPane) jt.getComponentAt(i);
       item = (Hashtable) hash.get("ITEM_"+i);
       kp.msdsB.setEnabled(((String) item.get("MSDS")).equals("Y"));
       kp.manufT.setText((String) item.get("MANUF"));
       kp.matDescT.setText((String) item.get("MAT_DESC"));
       kp.manufPartT.setText((String) item.get("MFG_PART_ID"));
       kp.sizeT.setText((String) item.get("PART_SIZE"));
       kp.setMatID((String) item.get("MAT_ID"));
       if (!BothHelpObjs.isBlankString((String) item.get("SIZE_UNIT"))) kp.unitC.setSelectedItem((String) item.get("SIZE_UNIT"));
       if (!BothHelpObjs.isBlankString((String) item.get("PKG_STYLE"))) kp.packC.setSelectedItem((String) item.get("PKG_STYLE"));
       if (!BothHelpObjs.isBlankString((String) item.get("DIMENSION"))) kp.dimT.setText((String) item.get("DIMENSION"));
       if (!BothHelpObjs.isBlankString((String) item.get("NETWT"))) kp.netwtT.setText((String) item.get("NETWT"));
       if (!BothHelpObjs.isBlankString((String) item.get("NETWT_UNIT"))) kp.netwtC.setSelectedItem((String) item.get("NETWT_UNIT"));
       kp.gradeT.setText((String) item.get("GRADE"));
       kp.tradeT.setText((String) item.get("TRADE"));
       kp.customerMSDST.setText((String)item.get("CUSTOMER_MSDS"));
       kp.bundleT.setText((String)item.get("NUMBER_PER_PART"));
       kp.sampleSizingCheck.setSelected("Y".equalsIgnoreCase((String)item.get("SAMPLE_SIZING")));
       kp.mfgStorageL.setText("Manufacturer's Recommended Shelf Life @ Storage: "+(String)item.get("MFG_SHELF_LIFE_STORAGE"));
       kp.revalidate();
    }
    // start view
    startView = BothHelpObjs.makeZeroFromNull((String) hash.get("STARTING_VIEW"));
    //Approved by QC
    passedQC = (Boolean)hash.get("PASSED_QC");
    // Supplier
    matPP.supplier.supNameT.setText((String) hash.get("SUGGESTED_VENDOR"));
    matPP.supplier.supContT.setText((String) hash.get("CONTACT_NAME"));
    matPP.supplier.supEmailT.setText((String) hash.get("CONTACT_EMAIL"));
    matPP.supplier.supFaxT.setText((String) hash.get("CONTACT_FAX"));
    matPP.supplier.supPhoneT.setText((String) hash.get("CONTACT_PHONE"));

    // hold this for audit on change
    partPP.replacePartNumT.setText(replacePartNum);
    partPP.partDesc.setText((String)hash.get("PART_DESC"));
    partPP.partNumberComments = (String)hash.get("PART_NUMBER_COMMENT");
    if (((String) hash.get("STOCKED")).toLowerCase().startsWith("o")){
       partPP.oorR.setSelected(true);
       partPP.mmR.setSelected(false);
       partPP.initQtyT.setText((String) hash.get("INIT_90_DAY"));
    } else {
       partPP.oorR.setSelected(false);
       partPP.mmR.setSelected(true);
       partPP.initQtyT.setText((String) hash.get("INIT_90_DAY"));
    }
    CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
    //setting shelf life option
    partPP.setShelfLifeStorageTempOption((String)hash.get("SHELF_LIFE_OPTION"));
    partPP.setNumberOfComponent(matPP.getTabPane().getTabCount());
    partPP.loadShelfLifeStorage(hash);
    partPP.setDefaultShelfLifeStorageTemp(hash);

    //Flows
    Vector f = (Vector) hash.get("FLOWS");
    if (f!=null && f.size()>0) partPP.addVectorToQFlowList(f);

    //Specs
    Hashtable sh = (Hashtable) hash.get("SPECS");
    if (sh.size() > 0) {
      Enumeration E;
      for (E=sh.keys();E.hasMoreElements();){
        Hashtable dh = (Hashtable) sh.get(E.nextElement());
        //create new spec tabs
        //if it is a Std Mfg Cert then insert it at the begining
        if ("Std Mfg Cert".equalsIgnoreCase((String) dh.get("SPEC_ID"))) {
          partPP.addStandardMfgCert();
          jt = (JTabbedPane) partPP.getSpecTabs();
          SpecPane sp = (SpecPane) jt.getComponentAt(0);
          sp.coc.setSelected( ( (Boolean) dh.get("COC")).booleanValue());
          sp.coa.setSelected( ( (Boolean) dh.get("COA")).booleanValue());
          sp.setSpecLibrary((String) dh.get("SPEC_LIBRARY"));
        }else {
          partPP.addSpec();
          jt = (JTabbedPane) partPP.getSpecTabs();
          SpecPane sp = (SpecPane) jt.getComponentAt(jt.getTabCount()-1);
          sp.setSpecID( (String) dh.get("SPEC_ID"));
          sp.specIdT.setText( (String) dh.get("SPEC_NAME"));
          sp.specRevT.setText( (String) dh.get("SPEC_VERSION"));
          sp.specAmenT.setText( (String) dh.get("SPEC_AMENDMENT"));
          sp.specTitleT.setText( (String) dh.get("SPEC_TITLE"));
          sp.specB.setEnabled( ( (String) dh.get("ONLINE")).toLowerCase().startsWith("y"));
          sp.setSpecUrl( (String) dh.get("SPEC_URL"));
          sp.coc.setSelected( ( (Boolean) dh.get("COC")).booleanValue());
          sp.coa.setSelected( ( (Boolean) dh.get("COA")).booleanValue());
          sp.setSpecLibrary((String) dh.get("SPEC_LIBRARY"));
        }
      }
    }

    // Approval
    featureMode = (String)tcmISFeature.get("newChem.3.paintBooth");
    if (featureMode.equalsIgnoreCase("1")) {  // "1" <=> number one
      apprPP.setPaintBooth(true);
      apprPP.paintBoothPercent = (String)dynamicH.get("PAINT_BOOTH_PERCENT");
    }else {
      apprPP.setPaintBooth(false);
      apprPP.topP.remove(apprPP.paintBoothP);
      apprPP.jCheckBox1.setVisible(false);
    }

    Hashtable ah = (Hashtable) hash.get("APPROVAL_HASH");
    apprPP.setTableHash(ah);

    if (apprPP.facC.getItemCount() == 0) {
      apprPP.changeFacilityWorkArea();
    }
    apprPP.buildTable(null);
  } //end of method

  public void loadIt(){
    // one one thread
    NewChem_LoadThread iT = new NewChem_LoadThread(this,"load");
    iT.start();
  }

  public void loadDone(){
    if(dataLoaded){
      ClientHelpObjs.setEnabledContainer(this,true);
      ClientHelpObjs.setComboBoxUpdateUi(this);
      this.revalidate();
    }
  }

  public void jbInit() throws Exception {
    // Tooltip
    this.setLayout(borderLayout1);
    ToolTipManager.sharedInstance().setEnabled(false);
    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    lPanel.setLayout(verticalFlowLayout1);
    jPanel6.setLayout(gridBagLayout5);
    jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel2.setText("Starting Point:");
    infoP.setLayout(borderLayout2);
    statusL.setFont(new java.awt.Font("Dialog", 1, 10));
    statusL.setHorizontalAlignment(SwingConstants.CENTER);
    statusL.setText("New Size");
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setText("View:");
    viewL.setFont(new java.awt.Font("Dialog", 1, 10));
    viewL.setHorizontalAlignment(SwingConstants.CENTER);
    viewL.setText("Requestor");
    jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel5.setText("Request Number:");
    reqL.setFont(new java.awt.Font("Dialog", 1, 10));
    reqL.setHorizontalAlignment(SwingConstants.CENTER);
    reqL.setText("0000");
    screenP.setLayout(borderLayout3);
    jPanel7.setLayout(gridBagLayout2);
    evalB.setPreferredSize(new Dimension(110, 21));
    evalB.setMaximumSize(new Dimension(107, 21));
    evalB.setMinimumSize(new Dimension(107, 21));
    evalB.setText("Section 4 of 4");
    evalB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        evalB_actionPerformed(e);
      }
    });
    evalB.setEnabled(false);

    appB.setPreferredSize(new Dimension(110, 21));
    appB.setMaximumSize(new Dimension(107, 21));
    appB.setMinimumSize(new Dimension(107, 21));
    appB.setText("Section 3 of 4");
    appB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        appB_actionPerformed(e);
      }
    });
    partB.setPreferredSize(new Dimension(110, 21));
    partB.setMaximumSize(new Dimension(107, 21));
    partB.setMinimumSize(new Dimension(107, 21));
    partB.setText("Section 2 of 4");
    partB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        partB_actionPerformed(e);
      }
    });
    matB.setPreferredSize(new Dimension(110, 21));
    matB.setMaximumSize(new Dimension(107, 21));
    matB.setMinimumSize(new Dimension(107, 21));
    matB.setText("Section 1 of 4");
    matB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        matB_actionPerformed(e);
      }
    });
    matP.setLayout(borderLayout5);
    actionP.setLayout(borderLayout4);
    jPanel9.setLayout(gridBagLayout4);
    approveB.setMaximumSize(new Dimension(79, 21));
    approveB.setMinimumSize(new Dimension(79, 21));
    approveB.setPreferredSize(new Dimension(107, 21));
    approveB.setText("Approve");
    approveB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        approveB_actionPerformed(e);
      }
    });
    deleteB.setMaximumSize(new Dimension(71, 21));
    deleteB.setMinimumSize(new Dimension(71, 21));
    deleteB.setPreferredSize(new Dimension(107, 21));
    deleteB.setText("Delete");
    deleteB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteB_actionPerformed(e);
      }
    });
    submitB.setMaximumSize(new Dimension(73, 21));
    submitB.setMinimumSize(new Dimension(73, 21));
    submitB.setPreferredSize(new Dimension(107, 21));
    submitB.setText("Submit");
    submitB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submitB_actionPerformed(e);
      }
    });
    rPanel.setLayout(cardLayout1);
    partP.setBackground(Color.yellow);
    partP.setLayout(borderLayout6);
    appP.setBackground(Color.blue);
    appP.setLayout(borderLayout7);
    draftB.setMaximumSize(new Dimension(61, 21));
    draftB.setMinimumSize(new Dimension(61, 21));
    draftB.setPreferredSize(new Dimension(107, 21));
    draftB.setText("Draft");
    draftB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        draftB_actionPerformed(e);
      }
    });
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 10));
    jLabel1.setText("Requested On:");
    dateL.setFont(new java.awt.Font("Dialog", 1, 10));
    dateL.setText("01/01/00");
    appDetailB.setMaximumSize(new Dimension(73, 21));
    appDetailB.setMinimumSize(new Dimension(73, 21));
    appDetailB.setPreferredSize(new Dimension(107, 21));
    appDetailB.setText("Appr.Detail");
    appDetailB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        appDetailB_actionPerformed(e);
      }
    });
    //evalMRP.setFont(new java.awt.Font("Dialog", 0, 10));
    this.add(lPanel, BorderLayout.WEST);
    lPanel.add(infoP, null);
    infoP.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(jLabel2, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel6.add(statusL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel6.add(jLabel3, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel6.add(viewL, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel6.add(jLabel5, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel6.add(reqL, new GridBagConstraints2(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel6.add(jLabel1, new GridBagConstraints2(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel6.add(dateL, new GridBagConstraints2(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    lPanel.add(screenP, null);
    screenP.add(jPanel7, BorderLayout.CENTER);
    jPanel7.add(evalB, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel7.add(appB, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel7.add(partB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0));
    jPanel7.add(matB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0));
    lPanel.add(actionP, null);
    actionP.add(jPanel9, BorderLayout.CENTER);
    jPanel9.add(approveB, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel9.add(deleteB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
    /*
    jPanel9.add(submitB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    /*
    jPanel9.add(draftB, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    jPanel9.add(appDetailB, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));  */
    this.add(rPanel, BorderLayout.CENTER);
    rPanel.add(matP, "jPanel8");
    rPanel.add(partP, "jPanel1");
    rPanel.add(appP, "jPanel2");
    rPanel.add(evalP, BorderLayout.CENTER);

    actionP.setBorder(ClientHelpObjs.groupBox("Actions"));
    screenP.setBorder(ClientHelpObjs.groupBox("Sections"));
    infoP.setBorder(ClientHelpObjs.groupBox("Request Info."));

    rPanel.setBorder(ClientHelpObjs.groupBox("Section: "+MAT));
    toggleGroup.add(matB);
    toggleGroup.add(partB);
    toggleGroup.add(appB);
    toggleGroup.add(evalB);
    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
  }

  public CmisApp getCmis(){
    return cmis;
  }

  //trong 11-12-00
  /**
   * This method will determine which screen to print.
   *
  */
  public void printScreenData(){
    if (matB.isSelected()) {
      printMaterialScreenData();
    }else if (partB.isSelected()) {
      printPartScreenData();
    }else if (appB.isSelected()) {
      printApprovalScreenData();
    }
  }
  /**
   * This method will gather all the information from Section: Material / Size
   * and send it to server (NChemObj.java).  From there, the report will be
   * generate.
   *
  */
  public void printMaterialScreenData() {
    buildDataHash();
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","PRINT_MATERIAL_SCREEN"); //String, String
      query.put("USER_ID",cmis.getUserId().toString());
      query.put("REQ_ID",(new Integer(req_id.intValue())).toString());
      query.put("VIEW",this.viewL.getText());
      query.put("STATUS",this.currentStatus);
      query.put("DATE",this.dateL.getText());
      query.put("MATERIAL_DATA",matHash);

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      Boolean suc = (Boolean)result.get("SUCCESS");
      if(suc.booleanValue()) {
        // From the stand alone application
        String url = (String)result.get("URL");
        new URLCall(url,cmis);
      }else {
        GenericDlg.showMessage((String)result.get("MESSAGE"));
      }
    } catch (Exception e){
      e.printStackTrace();
      return;
    }
    this.repaint();
    this.revalidate();
  }
  /**
   * This method will gather all the information from Section: Catalog Part
   * and send it to server (NChemObj.java).  From there, the report will bef
   * generate.
   *
  */
  public void printPartScreenData() {
    buildDataHash();
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","PRINT_PART_SCREEN"); //String, String
      query.put("USER_ID",cmis.getUserId().toString());
      query.put("REQ_ID",(new Integer(req_id.intValue())).toString());
      query.put("VIEW",this.viewL.getText());
      query.put("STATUS",this.currentStatus);
      query.put("DATE",this.dateL.getText());
      query.put("PART_DATA",partHash);

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Boolean suc = (Boolean)result.get("SUCCESS");
      if(suc.booleanValue()) {
        // From the stand alone application
        String url = (String)result.get("URL");
        new URLCall(url,cmis);
      }else {
        GenericDlg.showMessage((String)result.get("MESSAGE"));
      }
    } catch (Exception e){
      e.printStackTrace();
      return;
    }
    this.repaint();
    this.revalidate();
  }
  /**
   * This method will gather all the information from Section: Approval
   * and send it to server (NChemObj.java).  From there, the report will be
   * generate.
   *
  */
  public void printApprovalScreenData() {
    buildDataHash();
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","PRINT_APPROVAL_SCREEN"); //String, String
      query.put("USER_ID",cmis.getUserId().toString());
      query.put("REQ_ID",(new Integer(req_id.intValue())).toString());
      query.put("VIEW",this.viewL.getText());
      query.put("STATUS",this.currentStatus);
      query.put("DATE",this.dateL.getText());
      query.put("APPROVAL_DATA",apprHash);
      query.put("MATERIAL_DATA",matHash);
      query.put("FACILITY_ID",apprPP.getFacilityID());
      //Nawaz 01-17-02
      query.put("FACILITY_NAME",apprPP.getFacilityDesc((String)apprPP.facC.getSelectedItem()));

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      Boolean suc = (Boolean)result.get("SUCCESS");
      if(suc.booleanValue()) {
        // From the stand alone application
        String url = (String)result.get("URL");
        new URLCall(url,cmis);
      }else {
        GenericDlg.showMessage((String)result.get("MESSAGE"));
      }
    } catch (Exception e){
      e.printStackTrace();
      return;
    }
    this.repaint();
    this.revalidate();
  }



  public void stateChanged(String s){
  }

  public void setParent(CmisApp cmis){
     this.cmis = cmis;
  }

  String doScreenAudit(Container what){
    // kits
    String message=null;
    if (what instanceof MatPane){
      for (int i=0;i<matPP.getTabPane().getTabCount();i++){
         message=null;
         Component[] allComponents = ClientHelpObjs.getAllComponents((Container) matPP.getTabPane().getComponentAt(i));
         message = audit(allComponents);
         if (message!=null) return message;
      }
    } else {
      //PartPane
      message=null;
      Component[] allComponents = ClientHelpObjs.getAllComponents(what);
      message = audit(allComponents);
      if (message!=null) return message;
    }
    return null;
  }

  void buildDataHash(){
    Hashtable h = new Hashtable();
    matHash = new Hashtable();
    partHash = new Hashtable();
    apprHash = new Hashtable();
    for (int i=0;i<matPP.getTabPane().getTabCount();i++){
       KitsPane kit = (KitsPane) matPP.getTabPane().getComponentAt(i);
       h = new Hashtable();
       h.put("manufT",kit.manufT.getText());
       h.put("matDescT",kit.matDescT.getText());
       h.put("manufPartT",kit.manufPartT.getText());
       h.put("sizeT",kit.sizeT.getText());
       h.put("unitC",BothHelpObjs.makeBlankFromNull((String)kit.unitC.getSelectedItem()));
       h.put("packC",BothHelpObjs.makeBlankFromNull((String)kit.packC.getSelectedItem()));
       h.put("dimT",kit.dimT.getText());
       h.put("netwtT",kit.netwtT.getText());
       h.put("netwtC",BothHelpObjs.makeBlankFromNull((String)kit.netwtC.getSelectedItem()));

       //11-08-01
       h.put("customerMSDS",BothHelpObjs.makeBlankFromNull(kit.customerMSDST.getText()));

       h.put("numberPerPart",BothHelpObjs.makeBlankFromNull(kit.bundleT.getText()));
       h.put("sampleSizing",kit.sampleSizingCheck.isSelected()?"Y":"N");

       //5-09-01
       if (dynamicH.containsKey("ITEM_"+i)) {
        Hashtable item = (Hashtable) dynamicH.get("ITEM_"+i);
        h.put("matID",item.get("MAT_ID").toString());
       }else {
        h.put("matID","");
       }

       h.put("gradeT",BothHelpObjs.makeBlankFromNull((String)kit.gradeT.getText()));
       h.put("tradeT",BothHelpObjs.makeBlankFromNull((String)kit.tradeT.getText()));
       matHash.put("KIT_"+i,h);
    }
    // supplier
    h = new Hashtable();
    h.put("supNameT",matPP.supplier.supNameT.getText());
    h.put("supContT",matPP.supplier.supContT.getText());
    h.put("supEmailT",matPP.supplier.supEmailT.getText());
    h.put("supPhoneT",matPP.supplier.supPhoneT.getText());
    h.put("supFaxT",matPP.supplier.supFaxT.getText());
    h.put("freeSample",new Boolean(matPP.supplier.freeSample.isSelected()));   //trong 5/6
    matHash.put("SUPPLIER",h);

    //part
    h = new Hashtable();
    h.put("catalogC",BothHelpObjs.makeBlankFromNull(partPP.getCatalogID()));
    h.put("catalogCompanyId",BothHelpObjs.makeBlankFromNull(partPP.getCatalogCompanyId()));
    //part number
    if (partPP.jLabel2.getText().startsWith("Material ID")) {
      if (isEval.booleanValue()) {
        h.put("partNumT",origPart);
        h.put("replacePartNumT", "");
        h.put("catalogItemID", "");
      }else {
        String tmpCatalogItemID = partPP.partNumT.getText().trim();
        h.put("partNumT",tmpCatalogItemID.substring(0, tmpCatalogItemID.indexOf("/")));
        h.put("replacePartNumT", "");
        h.put("catalogItemID", tmpCatalogItemID);
      }
    }else {
      if (isEval.booleanValue()) {
        h.put("partNumT",origPart);
      }else {
        h.put("partNumT", partPP.partNumT.getText().trim());
      }
      h.put("replacePartNumT",BothHelpObjs.makeBlankFromNull(partPP.replacePartNumT.getText().trim()));
      h.put("catalogItemID","");
    }
    h.put("consumable",partPP.getConsumableOption());
    h.put("category",partPP.getSelectedCategory());
    h.put("category_comment",partPP.getSelectedCategoryComment());
    h.put("category_delimiter",partPP.getCategoryDelimiter());
    h.put("category_status_override_by_approver",categoryStatusOverrideByApprover);
    h.put("partDesc",partPP.partDesc.getText().trim());
    h.put("partNumberComment",partPP.partNumberComments);
    //type and estimated qty
    if (partPP.oorR.isSelected()){
      h.put("OORMM","OOR");
      h.put("initQty",partPP.initQtyT.getText());
    } else {
      h.put("OORMM","MM");
      h.put("initQty",partPP.initQtyT.getText()); //trong 3/20
    }
    h.put("UNIT",partPP.unit1L.getText());
    //shelf life & storage temp
    partPP.saveShelfLifeStorageTemp(matHash);
    h.put("shelfLifeOption",partPP.mfgRB.isSelected()?"Mfg":"User");
    if (partPP.userRB.isSelected()) {
      h.put("sameShelfLifeStorageTemp",partPP.sameShelfLifeRB.isSelected()?"Y":"N");
    }else {
      h.put("sameShelfLifeStorageTemp","N");
    }
    partHash.put("PART_DATA",h) ;
    // flow
    h = new Hashtable();
    h.put("QUALITY_FLOW",partPP.getQualityFlow());
    h.put("PACKAGE_FLOW",partPP.getPackagingFlow());
    partHash.put("FLOW_DOWN",h) ;
    // Spec
    partHash.put("SPECS",partPP.getSpecs());

    //group
    apprHash.put("ALL_DATA",apprPP.getTableHash());
  } //end of method

  protected String audit(Component[] comp){
    Hashtable hash = ClientHelpObjs.buildCompValueHash(comp);

    return ClientHelpObjs.audit(hash);
  }


  public MatPane getMatPane(){
     return matPP;
  }

  public PartPane getPartPane(){
     return partPP;
  }

  public ApprPane getApprPane(){
     return apprPP;
  }

  void matB_actionPerformed(ActionEvent e) {
      goMat();
  }

  void goMat(){
       if ("Draft".equalsIgnoreCase(currentStatus)) {
        rPanel.setBorder(ClientHelpObjs.groupBox("Section: "+MAT+"   Requestor: "+requestorName+"   Status: "+currentStatus+" "));
       }else {
        rPanel.setBorder(ClientHelpObjs.groupBox("Section: "+MAT+"   Requestor: "+requestorName+"   Status: "+currentStatus+"   Submitted: "+submitDate));
       }
       matP.setVisible(true);
       partP.setVisible(false);
       appP.setVisible(false);
       evalP.setVisible(false);
       evalB.setSelected(false);
       matB.setSelected(true);
       partB.setSelected(false);
       appB.setSelected(false);
       rPanel.remove(partP);
       rPanel.remove(appP);
       rPanel.remove(evalP);
       rPanel.add(matP, BorderLayout.CENTER);
       rPanel.revalidate();
       ClientHelpObjs.makeDefaultColors(this);
  }

  void partB_actionPerformed(ActionEvent e) {
       goPart();
  }

  void goPart(){
       /** see if units or kits
       */
       partPP.setUnitText( matPP.getUnitCText() );
       partPP.setNumberOfComponent(matPP.getTabPane().getTabCount());

       if (eval.equalsIgnoreCase("oldEval") || eval.equalsIgnoreCase("newEval")) {
          Hashtable tcmISFeature = (Hashtable) dynamicH.get("TCMIS_FEATURE");
          String featureMode = (String)tcmISFeature.get("newChem.2.orderType");
          if (featureMode.equalsIgnoreCase("1")) {  // "1" <=> number one
            partPP.setEvalflag();
          }else {
            partPP.setEvalflagNotRequired();
            auditOrderTypeInfo = false;
          }
          partPP.addAndSelectCatalog(myCatalog);

          partPP.catalogC.setEnabled(false);
          partPP.partNumT.setText((String)dynamicH.get("CAT_PART_NO"));
          partPP.partNumT.setEnabled(false);
          partPP.replacePartNumT.setEnabled(false);
       }

       if ("Y".equals(catAddSingleApp)) {
        partPP.addAndSelectCatalog(myCatalog);
        partPP.catalogC.setEnabled(false);
       }
       apprPP.canChangeCatFacWA();

       if ("Draft".equalsIgnoreCase(currentStatus)) {
        rPanel.setBorder(ClientHelpObjs.groupBox("Section: "+PART+"   Requestor: "+requestorName+"   Status: "+currentStatus+" "));
       }else {
        rPanel.setBorder(ClientHelpObjs.groupBox("Section: "+PART+"   Requestor: "+requestorName+"   Status: "+currentStatus+"   Submitted: "+submitDate));
       }
       partPP.displayCatalogPartConsumableOption(partPP.getCatalogID());
       partP.setVisible(true);
       appP.setVisible(false);
       evalP.setVisible(false);
       evalB.setSelected(false);
       matP.setVisible(false);
       matB.setSelected(false);
       partB.setSelected(true);
       appB.setSelected(false);
       rPanel.remove(matP);
       rPanel.remove(appP);
       rPanel.remove(evalP);
       rPanel.add(partP, BorderLayout.CENTER);
       rPanel.revalidate();
       ClientHelpObjs.makeDefaultColors(this);
  }


  void appB_actionPerformed(ActionEvent e) {
       goAppr();
  }

  void goAppr(){
      if (apprPP.facC.getItemCount() == 0) {
        apprPP.changeFacilityWorkArea();
      }

      if (eval.equalsIgnoreCase("oldEval") || eval.equalsIgnoreCase("newEval") || "Y".equals(catAddSingleApp)) {
        apprPP.facC.setSelectedItem(myFacilityDesc);
        apprPP.workAreaC.setSelectedItem(myWorkAreaDesc);
        apprPP.facC.setEnabled(false);
        apprPP.workAreaC.setEnabled(false);
      }
      apprPP.canChangeCatFacWA();

       /** set matrerial desc. on fate panel
       */
       if ("Draft".equalsIgnoreCase(currentStatus)) {
        rPanel.setBorder(ClientHelpObjs.groupBox("Section: "+APP+"   Requestor: "+requestorName+"   Status: "+currentStatus+" "));
       }else {
        rPanel.setBorder(ClientHelpObjs.groupBox("Section: "+APP+"   Requestor: "+requestorName+"   Status: "+currentStatus+"   Submitted: "+submitDate));
       }
       apprPP.setMaterialDescText( matPP.getMatDescs() );

       evalB.setSelected(false);
       evalP.setVisible(false);
       appP.setVisible(true);
       matP.setVisible(false);
       partP.setVisible(false);
       matB.setSelected(false);
       partB.setSelected(false);
       appB.setSelected(true);
       rPanel.remove(matP);
       rPanel.remove(partP);
       rPanel.remove(evalP);
       rPanel.add(appP, BorderLayout.CENTER);
       rPanel.revalidate();

       apprPP.paintT.setEnabled(apprPP.jCheckBox1.isSelected());
       apprPP.percentL.setEnabled(apprPP.jCheckBox1.isSelected());
       apprPP.jTextArea1.setForeground(Color.black);
       apprPP.jTextArea1.setFont(new java.awt.Font("Dialog", 0, 10));

       Hashtable tcmISFeature = (Hashtable) dynamicH.get("TCMIS_FEATURE");
       String featureMode = (String)tcmISFeature.get("newChem.3.paintBooth");
       if (featureMode.equalsIgnoreCase("1")) {  // "1" <=> number one
        apprPP.setPaintBooth(true);
        apprPP.paintBoothPercent = (String)dynamicH.get("PAINT_BOOTH_PERCENT");
       }else {
        apprPP.setPaintBooth(false);
        apprPP.topP.remove(apprPP.paintBoothP);
        apprPP.jCheckBox1.setVisible(false);
       }

       apprPP.displayProcessDetail();
       ClientHelpObjs.makeDefaultColors(this);
  }

  void evalB_actionPerformed(ActionEvent e) {
    /*
    String msg = partPP.auditQty();
    if (msg != null) {
      GenericDlg.showMessage(msg);
      goPart();
      return;
    }*/
    goEval();
  }
  void goEval(){
    evalP.setVisible(true);
    appP.setVisible(false);
    matP.setVisible(false);
    partP.setVisible(false);
    matB.setSelected(false);
    partB.setSelected(false);
    appB.setSelected(false);
    evalB.setSelected(true);

    rPanel.remove(matP);
    rPanel.remove(partP);
    rPanel.remove(appP);
    rPanel.add(evalP, BorderLayout.CENTER);
    rPanel.revalidate();

    if ("Draft".equalsIgnoreCase(currentStatus)) {
      rPanel.setBorder(ClientHelpObjs.groupBox("Section: Material Request   Requestor: "+requestorName+"   Status: "+currentStatus+" "));
    }else {
      rPanel.setBorder(ClientHelpObjs.groupBox("Section: Material Request   Requestor: "+requestorName+"   Status: "+currentStatus+"   Submitted: "+submitDate));
    }
    //setting qty for mr
    //evalMRP.qtyL.setText(partPP.initQtyT.getText());
    String tmpDesc = "";
    for (int i=0;i<matPP.getTabPane().getTabCount();i++){
      KitsPane kit = (KitsPane) matPP.getTabPane().getComponentAt(i);
      tmpDesc += kit.matDescT.getText() + " || ";
    }
    //removing the last ' || '
    tmpDesc = tmpDesc.substring(0,tmpDesc.length()-4);
    evalMRP.descT.setText(tmpDesc);

    ClientHelpObjs.makeDefaultColors(this);
    if (evalMRP.directRB.isVisible()) {
      evalMRP.directRB.setEnabled(!evalMRP.usesDirectedCharge);
      evalMRP.inDirectRB.setEnabled(!evalMRP.usesDirectedCharge);
    }
  }

  //trong 4/13
  public boolean checkAllSections() {
    String msg = null;
    //4-15-02 I am going to create my own audit method to audit material section
    //since this section is active for only new material / eng eval then audit otherwise, don't.
    //old - msg=doScreenAudit(matPP);
    if (status.equalsIgnoreCase("0")) {
      msg = auditNewMaterial();
    }
    if (msg!=null){
      GenericDlg.showMessage(msg);
      goMat();
      return false;
    }
    //audit customer MSDS #
    if ("MANDATORY".equalsIgnoreCase(requestorEditFacMSDSID) ) {
      msg = auditCustomerMSDS();
    }
    if (msg!=null){
      GenericDlg.showMessage(msg);
      goMat();
      return false;
    }

    //do not audit if the status is new facility part and new work area approval
    if ( !status.equalsIgnoreCase("2") && !status.equalsIgnoreCase("3") && unitPackAudit ) {
      msg = auditNewSizePackaging();
      if (msg != null) {
        GenericDlg.showMessage(msg);
        goMat();
        return false;
      }
    }
    //trong 5/6 if Eng Eval audit: supplier name, contact name, and phone number
    if (isEval.booleanValue() && !eval.equalsIgnoreCase("convertEval") && auditSupplierInfo) {       //5-13-01
      if (BothHelpObjs.isBlankString(BothHelpObjs.makeBlankFromNull(matPP.supplier.supNameT.getText())) ||
          BothHelpObjs.isBlankString(BothHelpObjs.makeBlankFromNull(matPP.supplier.supContT.getText())) ||
          BothHelpObjs.isBlankString(BothHelpObjs.makeBlankFromNull(matPP.supplier.supPhoneT.getText()))) {
        msg = new String("Please enter supplier name, contact name, and contact phone number \n for this Engineering Evaluation request.");
        GenericDlg.showMessage(msg);
        goMat();
        return false;
      }
    }

    //audit: shelf life and storage temperature
    //do not audit if the status is new work area approval
    if (!isEval.booleanValue()) {
      if (!status.equalsIgnoreCase("3")) {
        if (!cigRestricted) {
          msg = partPP.auditShelfLife();
          if (msg != null) {
            GenericDlg.showMessage(msg);
            goPart();
            return false;
          }
          msg = partPP.auditTemp();
          if (msg != null) {
            GenericDlg.showMessage(msg);
            goPart();
            return false;
          }
          if (requireLabelColor) {
            msg = partPP.auditLabelColor();
            if (msg != null) {
              GenericDlg.showMessage(msg);
              goPart();
              return false;
            }
          }
        }
      }
    }
    //if category is visible then make sure that user pick a category before submit
    if (partPP.getCategoryOption()) {
      msg = partPP.auditSelectedCategory();
      if (msg != null) {
        GenericDlg.showMessage(msg);
        goPart();
        return false;
      }
    }

    //audit: initial quantity of an OOR and catalog directed charge if required
    //do not audit if the status is new work area approval
    //if (qtyTmpAudit) {
    if (qtyTmpAudit && !isEval.booleanValue()) {
      if (auditOrderTypeInfo) {
        msg = partPP.auditQty();
        if (msg != null) {
          GenericDlg.showMessage(msg);
          goPart();
          return false;
        }
      }
    } //end of if qtyTmpAudit

    if (!isEval.booleanValue()) {
      //part description
      if (partPP.partDesc.getText().length() > 250) {
        msg = "The maximum length of the Part Description is 250.";
      }
      if (msg != null) {
        GenericDlg.showMessage(msg);
        goPart();
        return false;
      }
      msg=doScreenAudit(partPP);
      if (msg != null) {
        GenericDlg.showMessage(msg);
        goPart();
        return false;
      }
      //audit spec data
      for (int i = 0; i < partPP.getSpecTabs().getTabCount(); i++) {
        SpecPane sp = (SpecPane) partPP.getSpecTabs().getComponentAt(i);
        //if it is not a std mfg cert then make sure users entered something for required fields
        if (!"Std Mfg Cert".equalsIgnoreCase(sp.getSpecID())) {
          if (BothHelpObjs.isBlankString(sp.specIdT.getText()) ||
              BothHelpObjs.isBlankString(sp.specTitleT.getText())) {
            GenericDlg.showMessage("Please enter spec's info");
            goPart();
            return false;
          }
        }
      }

      //if facility required use approval then audit
      if (approvalDetailRequired.booleanValue()) {
        msg = doScreenAudit(apprPP);
        if (msg != null) {
          GenericDlg.showMessage(msg);
          goAppr();
          return false;
        }
        if (apprPP.tableHash == null || apprPP.tableHash.size() < 1) {
          GenericDlg.showMessage("You need to enter at least one approval record.");
          goAppr();
          return false;
        }
        //catalog part directed charge
        if (!isEval.booleanValue()) {
          if (catPartDirectedChargeRequired.contains(partPP.getCatalogID())) {
            msg = apprPP.auditCatalogPartDirectedCharge();
            if (msg != null) {
              GenericDlg.showMessage(msg);
              goAppr();
              return false;
            }
          }
        }
      }else {
        //ask user whether he/she leaving section 3 by mistake or not
        if (!isEval.booleanValue() &&
            "Requestor".equalsIgnoreCase(viewL.getText())) {
          if (apprPP.tableHash == null || apprPP.tableHash.size() < 1) {
            ConfirmNewDlg cdlg = new ConfirmNewDlg(this.cmis.getMain(),
                "Confirmation", true);
            cdlg.setMsg("You have not entered any 'Approval' information (Section 3) for this request.  Click 'Continue' to process or 'Cancel' to enter use approval data.");
            cdlg.setVisible(true);
            //if user mistakingly hit submit without enter use approval data then allow them to enter data
            if (!cdlg.yesFlag) {
              goAppr();
              return false;
            }
          }
        }
      } //end of approval detail is not required
    } //end of if not eval
    //4-18-02 audit eng eval data
    Integer currentStatus = new Integer(status);
    if (isEval.booleanValue() && evalB.isEnabled() && currentStatus.intValue() < 5) {
      msg = evalMRP.auditMR();
      if (!BothHelpObjs.isBlankString(msg)){
        GenericDlg.showMessage(msg);
        goEval();
        return false;
      }
      //set eval missing data
      //setEvalMissingSection();
      partPP.initQtyT.setText(evalMRP.qtyT.getText());
    }
    return true;
  } //end of method

  public String auditCustomerMSDS(){
    String msg = null;
    JTabbedPane jt = matPP.getTabPane();
    for (int i=0;i<jt.getTabCount();i++){
       KitsPane kp = (KitsPane) jt.getComponentAt(i);
       //customer MSDS #
       if (BothHelpObjs.isBlankString(kp.customerMSDST.getText())) {
         msg = "Your MSDS # is missing on one of the parts of the kit.";
         return msg;
       }else if (kp.customerMSDST.getText().length() > 40) {
         msg = "The maximum length of Your MSDS # is 40.";
         return msg;
       }
    }
    return msg;
  } //end of method

  public String auditNewMaterial(){
    String msg = null;
    JTabbedPane jt = matPP.getTabPane();
    for (int i=0;i<jt.getTabCount();i++){
       KitsPane kp = (KitsPane) jt.getComponentAt(i);
       if (BothHelpObjs.isBlankString(kp.manufT.getText())) {
        msg = "Manufacturer is missing on one of the parts of the kit.";
        return msg;
       }else if (kp.manufT.getText().length() > 240) {
        msg = "The maximum length of Manufacturer is 240.";
        return msg;
       }
       if (BothHelpObjs.isBlankString(kp.matDescT.getText())) {
        msg = "Material Description is missing on one of the parts of the kit.";
        return msg;
       }else if (kp.matDescT.getText().length() > 240) {
        msg = "The maximum length of Material Description is 240.";
        return msg;
       }
       if (BothHelpObjs.isBlankString(kp.tradeT.getText())) {
        msg = "Manufacturer's trade name is missing on one of the parts of the kit.";
        return msg;
       }else if (kp.tradeT.getText().length() > 100) {
        msg = "The maximum length of Manufacturer's Trade Name is 100.";
        return msg;
       }
    }
    return msg;
  }

  public String auditNewSizePackaging(){
    String msg = null;
    JTabbedPane jt = matPP.getTabPane();
    for (int i=0;i<jt.getTabCount();i++){
       KitsPane kp = (KitsPane) jt.getComponentAt(i);

       if (BothHelpObjs.isBlankString(kp.bundleT.getText())) {
        msg = "You did not enter a value for '# per part' for one of the parts of the kit.";
        return msg;
       }else {
        try {
          Integer nb = new Integer(kp.bundleT.getText());
          if (nb.intValue() < 0) {
            msg = "Please enter only whole number for '# per part'.";
            return msg;
          }
        } catch(Exception eb) {
          msg = "Please enter only whole number for '# per part'.";
          return msg;
        }
       }
       //part size
       try {
          float f = Float.parseFloat(kp.sizeT.getText());
          if (f < 0) {
            msg = "Please enter a positive number value for 'Size' for one of the parts of the kit.";
            return msg;
          }
       } catch (Exception se) {
          msg = "Please enter a positive number for 'Size' for one of the parts of the kit.";
          return msg;
       }
       //size unit
       if (kp.unitC.getSelectedItem().toString().equalsIgnoreCase("Select a Unit")) {
        msg = "You did not select a Unit for one of the parts of the kit.";
        return msg;
       }
       //packaging style
       if (kp.packC.getSelectedItem().toString().equalsIgnoreCase("Select a Package Style")) {
        msg = "You did not select a Package Style for one of the parts of the kit.";
        return msg;
       }
       //net size and net weight unit
       if (kp.netwtT.isVisible()) {
        String num = kp.netwtT.getText();
        if (num == null) {
          msg = "You did not enter a value for 'Net Size' for one of the parts of the kit.";
          return msg;
        }else {
          try {
            float n = Float.parseFloat(num);
            if (n < 0) {
              msg = "Please enter only positive number for 'Net Size' for one of the parts of the kit.";
              return msg;
            }
          } catch(Exception e) {
            msg = "Please enter only positive number for 'Net Size' for one of the parts of the kit.";
            return msg;
          }
        }
        if (kp.netwtC.getSelectedIndex() == 0) {
          msg = "You did not select a unit for 'Net Size' for one of the parts of the kit.";
          return msg;
        }
       }
    }
    return msg;
  }

  void submitB_actionPerformed(ActionEvent e) {
    NewChem_LoadThread iT = new NewChem_LoadThread(this,"submit");
    iT.start();
  }

  void submitData() {
    if (checkAllSections()) {
       CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
       buildDataHash();

       Hashtable h = sendData("SUBMIT");
       String ok = (String)h.get("OK");
       CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
       //if (ok.equals("OK") && (eval.equalsIgnoreCase("normal") || eval.equalsIgnoreCase("convertEval"))) {
       if (ok.equals("OK")) {
         statusL.setText("Waiting Approval");
         this.istatus=this.PENDING_APPROVAL;
         protectScreen(NewChem.PROTECT_VIEWER,evalB.isVisible());
         if (h.containsKey("SEAGATE_URL")) {
          new URLCall((String)h.get("SEAGATE_URL"),(String)h.get("CRA_DATA"),cmis);
         }
       }
    }
  }

  Hashtable sendData(String s) {
       Hashtable values = new Hashtable();
       try{
          TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",cmis);
          Hashtable query = new Hashtable();
          query.put("ACTION",s); //String, String
          query.put("USER_ID",cmis.getUserId().toString());
          query.put("REQ_ID",(new Integer(req_id.intValue())).toString());
          query.put("MAT_DATA",matHash);
          query.put("PART_DATA",partHash);
          query.put("APPR_DATA",apprHash);
          query.put("EVAL_TYPE",eval);
          query.put("CATEGORY_OPTION", new Boolean(partPP.getCategoryOption()));
          //Engineering evaluation request MR Info
          if ((eval.equalsIgnoreCase("newEval") || eval.equalsIgnoreCase("oldEval"))  && evalB.isVisible()) {
            query.put("MR_INFO",evalMRP.buildData());
          }
          Hashtable result = cgi.getResultHash(query);
          if (result == null){
            GenericDlg.showAccessDenied(cmis.getMain());
            return null;
          }
          //trong 3/3/00 , 5-06-01 prompt user for MSDS unless they just hit save
          if (!s.equals("DRAFT")){
             GenericDlg.showMessage((String) result.get("MESSAGE"));
             if (result.containsKey("SEAGATE_URL")) {
              values.put("SEAGATE_URL",(String)result.get("SEAGATE_URL"));
              values.put("CRA_DATA",(String)result.get("CRA_DATA"));
             }
          } else {
            if (((String) result.get("MESSAGE")).startsWith("Error")) {
              //GenericDlg.showMessage((String) result.get("MESSAGE"));
              values.put("MESSAGE",(String)result.get("MESSAGE"));
            }else {
              //GenericDlg.showMessage("Records were successfully stored!");
              values.put("MESSAGE","Records were successfully stored!");
            }
          }
          values.put("OK",((String) result.get("MESSAGE")).startsWith("Error")?"1":"OK");
          if (result.containsKey("CATEGORY_STATUS")) {
            if (partPP.getCategoryOption()) {
              partPP.setCategoryStatus((String)result.get("CATEGORY_STATUS"));
              partPP.setCategoryStatusForButton((String)result.get("CATEGORY_STATUS_FOR_BUTTON"));
              partPP.displayCatalogPartCategoryOption();
            }
          }
          return values;
       } catch (Exception e){
          e.printStackTrace();
          values.put("OK",e.getMessage());
          return (values);
       }
  }


  //trong 3/25
  void approverAllowEdit() {
    int n = approver_type.intValue();
    this.matPP.supplier.allowEdit(true);
    switch(n) {
      case 1:
        break;
      case 2:
        break;
      case 3:
        break;
      case 4:
        if (!isEval.booleanValue()) {
          this.partPP.allowEdit(true);
        }
        break;
      case 5:
        if (!isEval.booleanValue()) {
          this.apprPP.allowEdit(true);
        }
        break;
      case 6:
        if (!isEval.booleanValue()) {
          this.partPP.allowEdit(true);
          this.apprPP.allowEdit(true);
        }
        break;
      case 7:
        if (isEval.booleanValue()) {
          this.partPP.allowEdit(true);
        }
        break;
      case 8:
        if (isEval.booleanValue()) {
          this.apprPP.allowEdit(true);
        }
        break;
      case 9:
        if (isEval.booleanValue()) {
          this.apprPP.allowEdit(true);
          this.partPP.allowEdit(true);
        }
        break;
      case 10:
        this.partPP.allowEdit(true);
        break;
      case 11:
        this.apprPP.allowEdit(true);
        break;
      case 12:
        this.partPP.allowEdit(true);
        this.apprPP.allowEdit(true);
        break;
      default:
    }
  }



  void protectScreen(int type, boolean displayEval){
      // MSDS hold values
      JTabbedPane jt = matPP.getTabPane();
      Boolean[] msds = new Boolean[jt.getTabCount()];
      for (int i=0;i<jt.getTabCount();i++){
        KitsPane kp = (KitsPane) jt.getComponentAt(i);
        if (dynamicH.containsKey("ITEM_"+i)) {
          Hashtable myH = (Hashtable)dynamicH.get("ITEM_"+i);
          String contMSDS = BothHelpObjs.makeBlankFromNull((String)myH.get("MSDS"));
          msds[i] = new Boolean("Y".equalsIgnoreCase(contMSDS));
        }else {
          msds[i] = new Boolean(false);
        }
      }
      //Spec
      jt = partPP.getSpecTabs();
      Boolean[] spec = new Boolean[jt.getTabCount()];
      for (int i=0;i<jt.getTabCount();i++){
        SpecPane sp = (SpecPane) jt.getComponentAt(i);
        spec[i] = new Boolean(sp.specB.isEnabled());
        //spec[i] = new Boolean(true);
      }

      ClientHelpObjs.setEnabledContainer(this,true);
      switch (type) {
         case NewChem.PROTECT_REQUESTOR:
             ClientHelpObjs.setEnabledChildOnly(this,true);
             this.approveB.setEnabled(false);
             isAddMetaOK=true;
             viewL.setText(this.REQUESTOR);
             this.matPP.supplier.allowEdit(true);
             matPP.revalidate();
             //trong 9-19 don't audit qty and temperature and shelf life
             if (startView == this.NEW_APPROVAL) {
              this.qtyTmpAudit = false;
             }

             // 1-12-02 user are not allow to edit customer MSDS field
             jt = matPP.getTabPane();
             for (int i=0;i<msds.length;i++){
              KitsPane kp = (KitsPane) jt.getComponentAt(i);
              kp.customerMSDST.setEnabled(false);
              //4-2-02 if user wants new size packaging then disable material
              if (startView == this.NEW_SIZE_PACK) {
                kp.manufT.setEnabled(false);
                kp.manufB.setEnabled(false);
                kp.matDescT.setEnabled(false);
                kp.gradeT.setEnabled(false);
                kp.tradeT.setEnabled(false);
              }
             }
             break;
         case NewChem.PROTECT_APPROVER:
             ClientHelpObjs.setEnabledChildOnly(this,false);
             isAddMetaOK=false;
             //trong 3/25 setting the screen for each approver type
             approverAllowEdit();
             apprPP.facC.setEnabled(false);
             partPP.catalogC.setEnabled(false);
             if (isEval.booleanValue()) {
              if (approver_type.intValue() == 2 || approver_type.intValue() == 3 ||
                  approver_type.intValue() > 6) {
                this.appB.setEnabled(true);
                this.matB.setEnabled(true);
                this.partB.setEnabled(true);
                this.approveB.setEnabled(true);
                this.draftB.setEnabled(true);
                viewL.setText("Eng Eval Approver");
                partPP.partNumT.setEnabled(false);
                //4-16-01
                partPP.replacePartNumT.setEnabled(false);
                apprPP.workAreaC.setEnabled(false);
                unitPackAudit = false;      //trong 4/20 not to audit unit and packing style if it is an eng eval
              } else {
                viewL.setText(this.VIEWER);
              }

              //trong 9-19 don't audit qty and temperature and shelf life
              if (startView == this.NEW_APPROVAL) {
                this.qtyTmpAudit = false;
              }
             } else {
              if (approver_type.intValue() == 1 || (approver_type.intValue() > 2 &&
                  approver_type.intValue() < 7) || approver_type.intValue() > 9) {
                this.appB.setEnabled(true);
                this.matB.setEnabled(true);
                this.partB.setEnabled(true);
                this.approveB.setEnabled(true);
                this.draftB.setEnabled(true);
                //trong 3/31
                jt = matPP.getTabPane();
                for (int i=0;i<jt.getTabCount();i++){
                  KitsPane kp = (KitsPane) jt.getComponentAt(i);
                  ClientHelpObjs.setEnabledChildOnly(kp,true);
                }
                viewL.setText("New Chem Approver");
                if (startView > this.NEW_SIZE_PACK ) unitPackAudit = false;  //trong 4/20 not to audit unit packing style if
              } else {
                viewL.setText(this.VIEWER);
              }
              //trong 9-19 don't audit qty and temperature and shelf life
              if (startView == this.NEW_APPROVAL) {
                this.qtyTmpAudit = false;
              }
             }
             break;
         case NewChem.PROTECT_SUPER_APPROVER:
             ClientHelpObjs.setEnabledChildOnly(this,true);
             isAddMetaOK=true;
             viewL.setText(this.SUPER_APPROVER);
             this.approveB.setEnabled(true);
             this.submitB.setEnabled(false);
             this.draftB.setEnabled(true);
             this.deleteB.setEnabled(false);
             if (startView > this.NEW_SIZE_PACK ) unitPackAudit = false;  //trong 4/20 not to audit unit packing style if
             //trong 9-19 don't audit qty and temperature and shelf life
              if (startView == this.NEW_APPROVAL) {
                this.qtyTmpAudit = false;
              }
             break;
         case NewChem.PROTECT_VIEWER:
             ClientHelpObjs.setEnabledChildOnly(this,false);
             isAddMetaOK=false;
             this.matB.setEnabled(true);
             this.partB.setEnabled(true);
             this.appB.setEnabled(true);
             viewL.setText(this.VIEWER);
             break;
         default:

      }

      if (startView > this.NEW_SIZE_PACK ){
          ClientHelpObjs.setEnabledChildOnly(matP,false);
          this.matPP.supplier.allowEdit(true);
          partPP.allowEditShelfLifeNStorageOverride(true);
      }
      if (startView > this.NEW_PART){
          ClientHelpObjs.setEnabledChildOnly(partP,false);
          //allow user to select OOR or MM at any time
          partPP.oorR.setEnabled(true);
          partPP.mmR.setEnabled(true);
          this.matPP.supplier.allowEdit(true);
      }
      if (istatus  > NEW_GROUP ){
          this.appDetailB.setEnabled(true);
      } else {
          this.appDetailB.setEnabled(false);
      }

      //9-11-02 if this request already passed TCM QC then no other approver roles can change material info section (section 1 of 3/4)
      if (passedQC.booleanValue()) {
        ClientHelpObjs.setEnabledChildOnly(matP,false);
        this.matPP.supplier.allowEdit(true);
        unitPackAudit = false;
        //partPP.allowEditShelfLifeNStorageOverride(true);
      }

      //YOUR MSDS Number
      // 1-12-02 allow approver or requestor (can edit MSDS) to edit customer MSDS field
      //note msds.length is the number of components
      jt = matPP.getTabPane();
      if (type == NewChem.PROTECT_APPROVER || type == NewChem.PROTECT_SUPER_APPROVER || "OPTIONAL".equalsIgnoreCase(requestorEditFacMSDSID) ||
           "MANDATORY".equalsIgnoreCase(requestorEditFacMSDSID)) {
        for (int i=0;i<msds.length;i++){
          KitsPane kp = (KitsPane) jt.getComponentAt(i);
          kp.customerMSDST.setEnabled(true);
          if ("MANDATORY".equalsIgnoreCase(requestorEditFacMSDSID)) {
           kp.cMSDSL.setText("Your MSDS #: *");
          }
        }
      }

      // MSDS
      for (int i=0;i<msds.length;i++){
        KitsPane kp = (KitsPane) jt.getComponentAt(i);
        kp.msdsB.setEnabled(msds[i].booleanValue());
      }

      // SPEC
      jt = partPP.getSpecTabs();
      for (int i=0;i<spec.length;i++){
        SpecPane sp = (SpecPane) jt.getComponentAt(i);
        sp.specB.setEnabled(spec[i].booleanValue());
      }

      //trong 4/19
      //initial qty
      partPP.initQtyT.setEnabled(true);
      if (!this.isRequestor && isEval.booleanValue() ) {
        partPP.initQtyT.setEnabled(false);
      }

      //category
      partPP.categoryStatusB.setEnabled(true);
      partPP.categoryB.setEnabled(true);
      if (type == NewChem.PROTECT_REQUESTOR ||
          type == NewChem.PROTECT_APPROVER ||
          type == NewChem.PROTECT_SUPER_APPROVER) {
        partPP.categoryEditable = true;
      }
      partPP.partNumberCommentB.setEnabled(true);

      //4-11-02  Eng Eval (Section 4 of 4) button
      if (isEval.booleanValue() && displayEval) {
        matB.setText("Section 1 of 2");
        partB.setVisible(false);
        partB.setEnabled(false);
        appB.setVisible(false);
        appB.setEnabled(false);
        evalB.setText("Section 2 of 2");
        evalB.setVisible(true);
        evalB.setEnabled(true);
        evalMRP.noteB.setEnabled(true);
      }else {
        matB.setText("Section 1 of 3");
        partB.setText("Section 2 of 3");
        appB.setText("Section 3 of 3");
        evalB.setVisible(false);
        evalB.setEnabled(false);
      }
  }

  public String getItemID(){
    return item_id;
  }

  public void loadNCAS_Thread(){
     ncasLoadDone = false;
     try{
        // Load Roles
        TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.NEW_CHEM_TRACKING,cmis);
        Hashtable query = new Hashtable();
        query.put("ACTION","APPROVAL_DETAIL"); //String, String
        query.put("REQ_ID",(new Integer(req_id.intValue())).toString());


        query.put("USER_ID",cmis.getUserId().toString()); //trong 3/27


        query.put("USE_NEW_TABLE","yes");
        Hashtable result = cgi.getResultHash(query);
        if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg.showMessage(no);
         return;
        }

        // load the approval info
        Vector roles = (Vector) result.get("ROLES");
        Vector stat = (Vector) result.get("STATUS");
        Vector approvers = (Vector) result.get("APPROVERS");
        Vector dates = (Vector) result.get("DATES");
        Vector reasons = (Vector) result.get("REASONS");
        Vector appNums = (Vector) result.get("APPROVERS_NUMS");
        Vector roleType = (Vector) result.get("ROLE_TYPE");
        Vector appSuperUser = (Vector) result.get("SUPER_USER");
        // ncas will be used again on the approval dlg window....
        ncas = new ApprovalStatus(roles,stat,approvers,dates,reasons,appNums,roleType,appSuperUser);
       // end
     } catch (Exception e) {
        e.printStackTrace();
     } finally {
        ncasLoadDone = true;
     }

  }

  // delete request
  void deleteB_actionPerformed(ActionEvent e) {
    NewChem_LoadThread iT = new NewChem_LoadThread(this,"delete");
    iT.start();
  }

  void deleteData() {
      try{
          ClientHelpObjs.setEnabledContainer(this,false);

          TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",cmis);
          Hashtable query = new Hashtable();
          query.put("ACTION","DELETE"); //String, String
          query.put("USER_ID",cmis.getUserId().toString());
          query.put("REQ_ID",(new Integer(req_id.intValue())).toString());

          Hashtable result = cgi.getResultHash(query);

          ClientHelpObjs.setEnabledContainer(this,true);

          if (result == null){
            GenericDlg.showAccessDenied(cmis.getMain());
            ClientHelpObjs.setEnabledContainer(this,true);
            return;
          }

          GenericDlg.showMessage((String) result.get("MESSAGE"));

          cmis.getMain().goNewChemMat(2,req_id.intValue());
       } catch (Exception e1){
          e1.printStackTrace();
       }
  }

  boolean categoryStatusPassed() {
    boolean result = true;
    String tmpCategoryStatus = partPP.getCategoryStatus();
    if (tmpCategoryStatus.indexOf("Incomplete") > 0) {
      DisplayTextDlg dlg = new DisplayTextDlg(cmis.getMain(),"Information");
      dlg.setMsg("Category selections must be completed.\n\n"+tmpCategoryStatus);
      dlg.setVisible(true);
      result = false;
    }else if (tmpCategoryStatus.indexOf("Fail") > 0) {
      ConfirmNewDlg md = new ConfirmNewDlg(cmis.getMain(),"Category Confirmation",true);
      md.setMsg("At least one category selection fails criteria.  Do you want to continue the part approval?\n\n"+tmpCategoryStatus);
      md.setVisible(true);
      if(md.getConfirmationFlag()){
        result = true;
        categoryStatusOverrideByApprover = new Boolean(true);
      }else {
        result = false;
      }
    }
    return result;
  } //end of method

  // approve it
  void approveB_actionPerformed(ActionEvent e) {
    if (checkAllSections()) {
      //make sure the the facility combo is fill and preselected
      if (apprPP.facC.getSelectedIndex() < 0) {
        apprPP.changeFacilityWorkArea();
      }
      //check to see if verify category is required
      if (verifyCategoryRequired.booleanValue()) {
        if (!categoryStatusPassed()) {
          return;
        }
      }

      ncaDlg = new ApprDlg(cmis.getMain(),"Roles Needing my Approval",true,cmis);
      ncaDlg.setMatPane(this);
      ncaDlg.setRequestId(req_id.toString());
      ncaDlg.setFacility(apprPP.getFacilityID());
      Hashtable rh = ncas.getRolesToApprove(cmis.getUserId().intValue());
      ncaDlg.setRolesToApprove(rh);
      ncaDlg.loadIt();
      ncaDlg.setVisible(true);
      approveB.setEnabled(ncaDlg.getApproved());
    }
  }

  public Hashtable getUpdatingData(){

        return new Hashtable();
  }

  // save for later change
  void draftB_actionPerformed(ActionEvent e) {
    NewChem_LoadThread iT = new NewChem_LoadThread(this,"save");
    iT.start();
  }

  public void saveData(boolean showMessage){
     buildDataHash();
     Hashtable h = sendData("DRAFT");
     if (showMessage) {
       GenericDlg.showMessage((String)h.get("MESSAGE"));
     }
  }

  void appDetailB_actionPerformed(ActionEvent e) {
    ApprDetail dlg = new ApprDetail(cmis.getMain(),"Approval Detail",true,cmis);
    dlg.setRequestId(req_id.toString());
    dlg.loadIt();
    dlg.show();
  }

}

class NewChem_LoadThread extends Thread {
  NewChem parent;
  String action = "";
  public NewChem_LoadThread(NewChem parent, String action){
     super("NewChem_LoadThread");
     this.parent = parent;
     this.action = action;
  }
  public void run(){
    if ("load".equalsIgnoreCase(action)) {
     parent.loadItAction();
    }else if ("delete".equalsIgnoreCase(action)) {
     parent.deleteData();
    }else if ("submit".equalsIgnoreCase(action)) {
     parent.submitData();
    }else if ("save".equalsIgnoreCase(action)) {
     parent.saveData(true);
    }
  }
}


class NewChem_LoadNCAS extends Thread {

   NewChem parent;

   public NewChem_LoadNCAS(NewChem parent){
      super("NewChem_LoadNCAS");
      this.parent = parent;
   }

   public void run(){
      parent.loadNCAS_Thread();
   }
}
