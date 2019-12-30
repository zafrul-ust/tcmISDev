package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.factory.AlternateApproverFactory;

public class AdminObj extends TcmisServletObj{
  //Client Version
  String function = null;
  Hashtable mySendObj = null;
  Hashtable inData = null;

  public AdminObj(ServerResourceBundle b, TcmISDBModel d ){
     super();
     bundle = b;
     db = d;
  }

  protected void resetAllVars(){
    function = null;
    inData = null;
  }

  protected void getResult(){
    //System.out.println("\n\n----- here in adminobj load facility application combox");
    mySendObj = new Hashtable();
    // using myRecvObj you get the function the way you want
    inData = (Hashtable)myRecvObj;
    function = inData.get("ACTION").toString();
    if(function.equalsIgnoreCase("LOAD_APPROVER_TREE")) {
      loadApproverTree();
    }else if(function.equalsIgnoreCase("UPDATE_APPROVER")) {
      updateApprover();
    }else if(function.equalsIgnoreCase("DELETE_APPROVER")) {
      deleteApprover();
    }else if(function.equalsIgnoreCase("LOAD_CATALOG_COMBO")) {
      loadCatalogCombo();
    }else if(function.equalsIgnoreCase("LOAD_FAC_APP")){
      getFacWorkAreaInfo();
    }else if(function.equalsIgnoreCase("GET_ALTERNATE_APPROVER")){
      getAlternateApproverList();
    }else if(function.equalsIgnoreCase("PROCESS_ALTERNATE_APPROVER")){
      processAlternateApproverList();
    }else if(function.equalsIgnoreCase("PRINT_ALTERNATE_APPROVER")){
      printALternateApproverList();
    }

  }

  protected void getAlternateApproverList() {
    try {
      AlternateApproverFactory wacf = new AlternateApproverFactory(db);
      mySendObj.put("ALTERNATE_APPROVERS",wacf.getData(inData));
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("MSG","");
    }catch (Exception e) {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while retrieving alternate approver.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void processAlternateApproverList() {
    try {
      AlternateApproverFactory wacf = new AlternateApproverFactory(db);
      wacf.processAlternateApproverList(inData);
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("MSG", "Data successfully updated.");
    }catch (Exception e) {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while processing alternate approver.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void printALternateApproverList() {
    AlternateApproverReportGenerator awarg = new AlternateApproverReportGenerator();
    String url = awarg.buildReport((Vector)inData.get("ALTERNATE_APPROVER_DATA"),(String)inData.get("FACILITY_DESC"),(String)inData.get("APPROVER_NAME"));
    if (url.length() > 1) {
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("URL",url);
      mySendObj.put("MSG","");
    }else {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while printing alternate approver.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }


  //trong 10-7 will return all facility, workarea, and account_sys from fac_pref
  void getFacWorkAreaInfoNew(){
    try{
      // get input data
      String userId = inData.get("USERID").toString();
      String workAreaStatus = null;

      String currentScreen = (String)inData.get("CURRENT_SCREEN");
      if (currentScreen.equalsIgnoreCase("Catalog") ||currentScreen.equalsIgnoreCase("NewChem")) {
        workAreaStatus = "A";
      }else {
        workAreaStatus = "B";
      }

      // get the fac/app data
      Facility f = new Facility(db);
      Hashtable facAppActSysInfo = new Hashtable();
      if (inData.containsKey("NEW")) {
        facAppActSysInfo = f.getMyFacWorkAreaList(userId,workAreaStatus,currentScreen);
      }else {
        if (currentScreen.equalsIgnoreCase("ReportWin")) {
          facAppActSysInfo = f.getEveryReportFac(userId, workAreaStatus);
        }else {
          facAppActSysInfo = f.getEveryFacNew(userId, workAreaStatus);
        }
      }
      mySendObj.put("ACC_SYS",(Hashtable)facAppActSysInfo.get("ACC_SYS"));
      mySendObj.put("PREF_ACC_SYS",(Hashtable)facAppActSysInfo.get("PREF_ACC_SYS"));
      mySendObj.put("FAC_IDS",(Vector)facAppActSysInfo.get("FAC_ID"));
      mySendObj.put("FAC_NAMES",(Vector)facAppActSysInfo.get("FAC_NAME"));
      mySendObj.put("APP_IDS",(Vector)facAppActSysInfo.get("APP_ID"));
      mySendObj.put("APP_NAMES",(Vector)facAppActSysInfo.get("APP_DESC"));
      mySendObj.put("FAC_X_APP",(Vector)facAppActSysInfo.get("FAC_X_APP"));
      //System.out.println("\n\n\n------ my hashtable: "+mySendObj);
    }catch(Exception e){e.printStackTrace();}
  }

  void getFacWorkAreaInfo(){
    try{
      // get input data
      String function = inData.get("FUNCTION").toString();
      String userId = inData.get("USERID").toString();

      //trong 10-7 I don't want to break anything else
      if(function.equalsIgnoreCase("PREF_FACS") || function.equalsIgnoreCase("STANDARD") || function.equals("STANDARD_AND_DROP")) {
        getFacWorkAreaInfoNew();
      } else {
        // get the fac/app data
        Facility f = new Facility(db);
        String where = "";
        Vector myV = new Vector();

        String r = null;
        String w = null;
        //if(!isRadian) {
        //  r = "lower(company_id) like lower('"+getBundle().getString("DB_CLIENT")+"%')";
        //}
        if(function.equals("STANDARD")){
          w = "facility_type is null and branch_plant is null";
        }else if(function.equals("STANDARD_AND_DROP")){
          w = "branch_plant is null";
        }else if(function.equals("DROP_FACS_ONLY")){
          w = "lower(facility_type) = 'drop'";
        }else if(function.equalsIgnoreCase("HUBS_ONLY")){
          w = "branch_plant is not null";
        }else if(function.equalsIgnoreCase("STANDARD_WITH_HUBS")){
          w = "facility_type is null or branch_plant is not null";
        }else if(function.equalsIgnoreCase("PREF_FACS")){
          w = "facility_id in (select facility_id from fac_pref where personnel_id = "+userId+")";
        }
        if(r != null && w != null){
          where = "where "+r+" and "+w;
        }else if(r != null){
          where = "where "+r;
        }else if(w != null){
          where = "where "+w;
        }

        //trong 4/8
        Hashtable accSys = new Hashtable();
        Hashtable prefAccSys = new Hashtable();
        if (inData.containsKey("NEW")) {
          myV = f.getMyWarehouseList(userId);
        }else {
          myV = f.getEveryFac(where);
        }
        Vector facids = new Vector();
        Vector facnames = new Vector();
        for(int q=0;q<myV.size();q++) {
          Facility f1 = (Facility)myV.elementAt(q);
          f1.load();
          facids.addElement(f1.getFacilityId());
          facnames.addElement(f1.getFacilityName());

          //trong 4/8
          PrRules prRules = new PrRules(db);
          Vector accSysV = new Vector();
          Vector numChargeTypesV = new Vector();
          Hashtable accSysNumTypes = new Hashtable();
          prRules.setFacilityId(f1.getFacilityId());
          prRules.loadAccSys();
          accSysV = prRules.getMyAccSysV();
          numChargeTypesV = prRules.getNumChargeTypesV();
          accSysNumTypes.put("ACC_SYS_V",accSysV);
          accSysNumTypes.put("NUM_CHARGE_TYPES_V",numChargeTypesV);
          String key = f1.getFacilityId();
          accSys.put(key,accSysNumTypes);

          //trong 4/9 get pref_acc_sys
          FacPref fp = new FacPref(db);
          fp.setFacId(f1.getFacilityId());
          fp.setUserId(userId);
          int count = fp.getPrefFacCount();
          if (count > 0) {
            fp.load();
            String prefAcc = fp.getPreAccSysId();
            prefAccSys.put(key,prefAcc);
          }
        }

        //application
        Vector facxapp = new Vector();
        Vector appids = new Vector();
        Vector appnames = new Vector();
        Object[][] oa;
        for(int q=0;q<facids.size();q++) {
          oa = f.getAppsForFac((String)facids.elementAt(q));
          for(int b=0;b<oa.length;b++) {
            facxapp.addElement(facids.elementAt(q));
            appids.addElement(oa[b][0]);
            appnames.addElement(oa[b][1]);
          }
        }
        //Hashtable mrRules = MrRules.getMrRuleHash(db,facids);

        //trong 4/8
        mySendObj.put("ACC_SYS",accSys);
        mySendObj.put("PREF_ACC_SYS",prefAccSys);

        mySendObj.put("FAC_IDS",facids);
        mySendObj.put("FAC_NAMES",facnames);
        mySendObj.put("APP_IDS",appids);
        mySendObj.put("APP_NAMES",appnames);
        mySendObj.put("FAC_X_APP",facxapp);
        //mySendObj.put("FAC_MR_RULES",mrRules);
      } //END OF ELSE
    }catch(Exception e){e.printStackTrace();}
  }



  void loadApproverTree(){
    try{
      MrRules mr = new MrRules(db);
      mr.setFacilityId(inData.get("FAC_ID").toString());
      mr.load();
      mySendObj.put("FAC_NEEDS_APPROVERS",new Boolean(mr.needsApprover()));

      FinanceApproverList fal = new FinanceApproverList(db);
      Vector v = new Vector();
      fal.setFacilityId(inData.get("FAC_ID").toString());
      Vector v1 = fal.getApproversForFacility();
      for(int i=0;i<v1.size();i++){
        FinanceApproverList f1 = (FinanceApproverList)v1.elementAt(i);
        Hashtable h = new Hashtable();
        h.put("NAME",f1.getUserFullName());
        h.put("ID",f1.getPersonnelId());
        h.put("SUPER_ID",f1.getApprover());
        h.put("COST_LIMIT",new Float(f1.getCostLimit()));
        h.put("APPROVAL_LIMIT",new Float(f1.getApprovalLimit()));
        v.addElement(h);
      }
      mySendObj.put("APPROVERS",v);
      mySendObj.put("SHOW_CURRENCY",new Boolean(HelpObjs.showCurrency(db)));
      mySendObj.put("CURRENCY",HelpObjs.getCurrency(db,inData.get("FAC_ID").toString()));
    }catch(Exception e){e.printStackTrace();}
  }

  void updateApprover(){
    String what = "";
    try{
      FinanceApproverList fal = new FinanceApproverList(db);
      String facId = inData.get("FAC_ID").toString();
      Integer pid = new Integer(inData.get("APPROVER_ID").toString());
      String costLimitString = inData.get("COST_LIMIT").toString();
      //cost limit
      float costLimit = 0;
      if(costLimitString.equalsIgnoreCase("unlimited")){
        costLimit = -1;
      }else{
        StringBuffer sb = new StringBuffer(costLimitString);
        StringBuffer sb2 = new StringBuffer();
        for(int i=0;i<sb.length();i++){
          if(sb.charAt(i) == '$' || sb.charAt(i) == ','){
            continue;
          }else if(sb.charAt(i) == '.'){
            break;
          }
          sb2.append(sb.charAt(i));
        }
        costLimitString = sb2.toString();
        Float mF = new Float(costLimitString);
        costLimit = mF.floatValue();
      }
      //approval limit
      String approvalLimitString = inData.get("APPROVAL_LIMIT").toString();
      float approvalLimit = 0;
      if(approvalLimitString.equalsIgnoreCase("unlimited")){
        approvalLimit = -1;
      }else{
        StringBuffer sb = new StringBuffer(approvalLimitString);
        StringBuffer sb2 = new StringBuffer();
        for(int i=0;i<sb.length();i++){
          if(sb.charAt(i) == '$' || sb.charAt(i) == ','){
            continue;
          }else if(sb.charAt(i) == '.'){
            break;
          }
          sb2.append(sb.charAt(i));
        }
        approvalLimitString = sb2.toString();
        Float mF = new Float(approvalLimitString);
        approvalLimit = mF.floatValue();
      }

      String superId = inData.get("SUPER_APPROVER").toString();
      Boolean moveChildren = (Boolean)inData.get("MOVE_CHILDREN");
      if(fal.isValid(facId,pid)){
        //updating
        what = "updated";
        fal.updateApprover(facId,pid,costLimit,approvalLimit,superId,moveChildren,(Integer)inData.get("ADMIN_ID"));
      }else{
        //adding
        what = "added";
        fal.setAdminID((Integer)inData.get("ADMIN_ID"));
        fal.setPersonnelId(pid.intValue());
        fal.setFacilityId(facId);
        fal.setCostLimit(costLimit);
        fal.setApprovalLimit(approvalLimit);
        try{
          if(approvalLimit > -1 ){  //not unlimited approval
            fal.setApprover(new Integer(superId).intValue());
          }
        }catch(Exception ex){
          mySendObj.put("STATUS","no");
          mySendObj.put("MSG","An Error has occurred, no updates were done.");
          return;
        }
        fal.addApprover();
      }
    }catch(Exception e){
      mySendObj.put("STATUS","yes");// say yes because we may have changed something before the error
      mySendObj.put("MSG","An Error has occurred, Please check your data and try again.");
      e.printStackTrace();
      return;
    }
    mySendObj.put("STATUS","yes");
    mySendObj.put("MSG","The approver was "+what+".");
  }

  void deleteApprover(){
    try{
      String facId = inData.get("FAC_ID").toString();
      Integer pid = new Integer(inData.get("APPROVER_ID").toString());
      //first check to see if user can generate MR before removing he/she off of financial approval tree
      String query = "select count(*) from pr_rules pr, user_group_member_application ugma"+
                     " where pr.facility_id = ugma.facility_id and pr.approver_required = 'y'"+
                     " and pr.status = 'A' and ugma.user_group_id = 'GenerateOrders'"+
                     " and ugma.personnel_id = "+pid.intValue()+ " and ugma.facility_id = '"+facId+"'";
      if (HelpObjs.countQuery(db,query) > 0) {
        mySendObj.put("STATUS","NO");
        mySendObj.put("MSG","Selected user cannot be deleted from tree\nbecause this user still has permission to create Material Request\nfor this facility.\n");
      }else {       //otherwise, go ahead and remove user from approval tree
        FinanceApproverList fal = new FinanceApproverList(db);
        fal.setAdminID((Integer)inData.get("ADMIN_ID"));
        fal.setPersonnelId(pid.intValue());
        fal.setFacilityId(facId);
        fal.deleteApprover();
        mySendObj.put("STATUS","OK");
        mySendObj.put("MSG","The approver was deleted.");
      }
    }catch(Exception e){
      mySendObj.put("STATUS","ERROR");// say yes because we may have changed something before the error
      mySendObj.put("MSG","An Error has occurred, Please check your data and try again.");
      e.printStackTrace();
      return;
    }
  }

  void loadCatalogCombo(){
    Vector v = new Vector();
    try{
      int id = ((Integer)inData.get("USER_ID")).intValue();
      v = Facility.getCatalogFacilityInfo(db,id);
    }catch(Exception e){
      e.printStackTrace();
    }
    mySendObj.put("CATALOG_COMBO_INFO",v);
  }

  protected void print(TcmisOutputStreamServer out){
    try {
      out.sendObject((Hashtable) mySendObj);
    }catch(Exception e){
      e.printStackTrace();
    }
  }


}