package radian.tcmis.server7.share.dbObjs;

/*
SQLWKS> desc pr_rules_view
Column Name                    Null?    Type
------------------------------ -------- ----
FACILITY_ID                    NOT NULL VARCHAR2(30)
ACCOUNT_SYS_ID                 NOT NULL VARCHAR2(12)
CHARGE_TYPE                    NOT NULL CHAR(1)
PO_REQUIRED                             CHAR(1)
APPROVER_REQUIRED                       CHAR(1)
PR_ACCOUNT_REQUIRED                     VARCHAR2(1)
CHARGE_LABEL_1                          VARCHAR2(30)
CHARGE_VALID_1                          CHAR(1)
CHARGE_ID_1                             VARCHAR2(12)
CHARGE_DISPLAY_1                        CHAR(1)
CHARGE_LABEL_2                          VARCHAR2(30)
CHARGE_VALID_2                          CHAR(1)
CHARGE_ID_2                             VARCHAR2(12)
CHARGE_DISPLAY_2                        CHAR(1)
*/

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class PrRules {

   protected TcmISDBModel db;
   protected String facId;
   protected String accountSysId = "";
   protected String chargeType = "";
   protected String poRequired = "";
   protected String approverRequired = "";
   protected String praRequired = "";
   protected String releaserRequired = "";
   protected String chargeLabel1 = "";
   protected String chargeValid1 = "";
   protected String chargeId1 = "";
   protected String chargeDisplay1 = "";
   protected String chargeLabel2 = "";
   protected String chargeValid2 = "";
   protected String chargeId2 = "";
   protected String chargeDisplay2 = "";
   protected String unitPriceRequired = "";
   protected String poSeqRequired = "";
   protected String customerRequisition = "";
   protected String mrNotificationUserGroup = "";

   Vector accSysV = new Vector();
   Hashtable accTypeH = new Hashtable();
   Vector chargeTypeV = new Vector();
   Vector myAccSysV = new Vector();
   Vector numChargeTypesV = new Vector();



   public PrRules(TcmISDBModel db){
      this.db = db;
   }

   //get methods
   public String getFacilityId() {
    return this.facId;
   }
   public String getAccountSysId() {
    return this.accountSysId;
   }
   public String getChargeType() {
    return this.chargeType;
   }
   public String getPoRequired() {
    return this.poRequired;
   }
   public String getApproverRequired() {
    return this.approverRequired;
   }
   public String getPraRequired() {
    return this.praRequired;
   }
   public String getReleaserRequired() {
    return this.releaserRequired;
   }
   public String getChargeLabel1() {
    return this.chargeLabel1;
   }
   public String getChargeValid1() {
    return this.chargeValid1;
   }
   public String getChargeId1() {
    return this.chargeId1;
   }
   public String getChargeDisplay1() {
    return this.chargeDisplay1;
   }
   public String getChargeLabel2() {
    return this.chargeLabel2;
   }
   public String getChargeValid2() {
    return this.chargeValid2;
   }
   public String getChargeId2() {
    return this.chargeId2;
   }
   public String getChargeDisplay2() {
    return this.chargeDisplay2;
   }
   public String getUnitPriceRequired() {
    return this.unitPriceRequired;
   }
   public String getPoSeqRequired() {
    return this.poSeqRequired;
   }
   public String getCustomerRequisition() {
    return this.customerRequisition;
   }
   public String getMrNotificationUserGroup() {
    return this.mrNotificationUserGroup;
   }


   //set methods
   public void setFacilityId(String s) {
    this.facId = s;
   }
   public void setAccountSysId(String s) {
    this.accountSysId = s;
   }
   public void setChargeType(String s) {
    this.chargeType = s;
   }
   public void setPoRequired(String s) {
    this.poRequired = s;
   }
   public void setApproverRequired(String s) {
    this.approverRequired = s;
   }
   public void setReleaserRequired(String s) {
    this.releaserRequired = s;
   }
   public void setPraRequired(String s) {
    this.praRequired = s;
   }
   public void setChargeLabel1(String s) {
    this.chargeLabel1 = s;
   }
   public void setChargeValid1(String s) {
    this.chargeValid1 = s;
   }
   public void setChargeId1(String s) {
    this.chargeId1 = s;
   }
   public void setChargeDisplay1(String s) {
    this.chargeDisplay1 = s;
   }
   public void setChargeLabel2(String s) {
    this.chargeLabel2  = s;
   }
   public void setChargeValid2(String s) {
    this.chargeValid2 = s;
   }
   public void setChargeId2(String s) {
    this.chargeId2 = s;
   }
   public void setChargeDisplay2(String s) {
    this.chargeDisplay2 = s;
   }
   public void setUnitPriceRequired(String s) {
    this.unitPriceRequired = s;
   }
   public void setPoSeqRequired(String s) {
    this.poSeqRequired = s;
   }
   public void setCustomerRequisition(String s) {
    this.customerRequisition = s;
   }
   public void setMrNotificationUserGroup(String s) {
    this.mrNotificationUserGroup = s;
   }

   public Vector getAccSysV() {
    return this.accSysV;
   }
   public Hashtable getAccTypeH() {
    return this.accTypeH;
   }
   public Vector getChargeTypeV(){
    return this.chargeTypeV;
   }
   public Vector getMyAccSysV(){
    return this.myAccSysV;
   }
   public Vector getNumChargeTypesV(){
    return this.numChargeTypesV;
   }


    public void loadAccSys() throws Exception {
    String query = "select * from pr_num_rules_view where facility_id = '"+getFacilityId()+"'";
    query += " order by account_sys_id";


    DBResultSet dbrs1 = null;
    ResultSet rs1 = null;
    try {
      dbrs1 = db.doQuery(query);
      rs1=dbrs1.getResultSet();
      while(rs1.next()) {
        myAccSysV.addElement(BothHelpObjs.makeBlankFromNull(rs1.getString("account_sys_id")));
        numChargeTypesV.addElement(BothHelpObjs.makeBlankFromNull(rs1.getString("num_charge_types")));
      }
    } catch (Exception e1) { e1.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e1.getMessage(),null);   throw e1;
    } finally{
      dbrs1.close();
    }
  }

  public Hashtable packAccSysType(Hashtable h) {
    Hashtable h1 = (Hashtable)h.get("ACC_TYPE_H");
    String accId = (String)h.get("ACC_SYS_ID");
    Hashtable innerH = (Hashtable)h1.get(accId);
    Vector label_1 = (Vector)innerH.get("LABEL_1");
    Vector label_2 = (Vector)innerH.get("LABEL_2");
    Vector ct = (Vector)innerH.get("CHARGE_TYPE");
    Vector poV = (Vector)innerH.get("PO_REQUIRED");
    Vector valid1V = (Vector)innerH.get("VALID_1");
    Vector valid2V = (Vector)innerH.get("VALID_2");
    Vector releaserV = (Vector)innerH.get("RELEASER_REQUIRED");
    Vector chargeId1V = (Vector)innerH.get("CHARGE_ID_1");
    Vector chargeId2V = (Vector)innerH.get("CHARGE_ID_2");
    Vector praV = (Vector)innerH.get("PR_ACCOUNT_REQUIRED");
    Vector approverV = (Vector)innerH.get("APPROVER_REQUIRED");
    Vector display1V = (Vector)innerH.get("DISPLAY_1");
    Vector display2V = (Vector)innerH.get("DISPLAY_2");
    Vector unitPriceRequiredV = (Vector)innerH.get("UNIT_PRICE_REQUIRED");
    Vector poSeqRequiredV = (Vector)innerH.get("PO_SEQ_REQUIRED");
    Vector customerRequisitionV = (Vector)innerH.get("CUSTOMER_REQUISITION");
    Vector mrNotificationUserGroupV = (Vector)innerH.get("MR_NOTIFICATION_USER_GROUP");
    Hashtable result = new Hashtable();

    for (int i = 0; i < ct.size(); i++) {
      Hashtable tempH = new Hashtable();
      String key = accId + ct.elementAt(i).toString();

      Vector tempV = new Vector();
      if (!BothHelpObjs.isBlankString(label_1.elementAt(i).toString()) &&
          !label_1.elementAt(i).toString().equalsIgnoreCase("%")) {
        tempV.addElement(label_1.elementAt(i).toString());
      }
      if (!BothHelpObjs.isBlankString(label_2.elementAt(i).toString()) &&
          !label_2.elementAt(i).toString().equalsIgnoreCase("%")) {
        tempV.addElement(label_2.elementAt(i).toString());
      }
      tempV.addElement("%");
      tempH.put("LABELS",tempV);

      int editable = 4;
      if (display1V.elementAt(i).toString().equalsIgnoreCase("n"))
        editable += 1;
      if (display2V.elementAt(i).toString().equalsIgnoreCase("n"))
        editable += 2;
      tempH.put("EDIT_TABLE",new Integer(editable));

      String temp = new String("");
      if (!BothHelpObjs.isBlankString(poV.elementAt(i).toString()) &&
          !poV.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = poV.elementAt(i).toString();
      }
      tempH.put("PO_REQUIRED",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(releaserV.elementAt(i).toString()) &&
          !releaserV.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = releaserV.elementAt(i).toString();
      }
      tempH.put("RELEASER_REQUIRED",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(approverV.elementAt(i).toString()) &&
          !approverV.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = approverV.elementAt(i).toString();
      }
      tempH.put("APPROVER_REQUIRED",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(praV.elementAt(i).toString()) &&
          !praV.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = praV.elementAt(i).toString();
      }
      tempH.put("PR_ACCOUNT_REQUIRED",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(valid1V.elementAt(i).toString()) &&
          !valid1V.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = valid1V.elementAt(i).toString();
      }
      tempH.put("VALID_1",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(valid2V.elementAt(i).toString()) &&
          !valid2V.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = valid2V.elementAt(i).toString();
      }
      tempH.put("VALID_2",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(chargeId1V.elementAt(i).toString()) &&
          !chargeId1V.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = chargeId1V.elementAt(i).toString();
      }
      tempH.put("CHARGE_ID_1",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(chargeId2V.elementAt(i).toString()) &&
          !chargeId2V.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = chargeId2V.elementAt(i).toString();
      }
      tempH.put("CHARGE_ID_2",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(unitPriceRequiredV.elementAt(i).toString()) &&
          !unitPriceRequiredV.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = unitPriceRequiredV.elementAt(i).toString();
      }
      tempH.put("UNIT_PRICE_REQUIRED",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(poSeqRequiredV.elementAt(i).toString()) &&
          !poSeqRequiredV.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = poSeqRequiredV.elementAt(i).toString();
      }
      tempH.put("PO_SEQ_REQUIRED",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(customerRequisitionV.elementAt(i).toString()) &&
          !customerRequisitionV.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = customerRequisitionV.elementAt(i).toString();
      }
      tempH.put("CUSTOMER_REQUISITION",temp);

      temp = new String("");
      if (!BothHelpObjs.isBlankString(mrNotificationUserGroupV.elementAt(i).toString()) &&
          !mrNotificationUserGroupV.elementAt(i).toString().equalsIgnoreCase("%")) {
        temp = mrNotificationUserGroupV.elementAt(i).toString();
      }
      tempH.put("MR_NOTIFICATION_USER_GROUP",temp);

      result.put(key,tempH);
    }
    return result;
  }

  public void load() throws Exception {
    /* not until Nish add commodity_type tp pr_rules
    String query = "select * from pr_num_rules_view where facility_id = '"+getFacilityId()+"'";
    query += " and commodity_type = 'Material' and account_sys_id = '" +getAccountSysId()+"'";
    query += " order by account_sys_id";
    */
    String query = "select * from pr_num_rules_view where facility_id = '"+getFacilityId()+"'";
    query += " and account_sys_id = '" +getAccountSysId()+"'";
    query += " order by account_sys_id";

    Vector tmpNumChargeType = new Vector();
    DBResultSet dbrs1 = null;
    ResultSet rs1 = null;
    try {
      dbrs1 = db.doQuery(query);
      rs1=dbrs1.getResultSet();
      while(rs1.next()) {
        //numChargeTypesV.addElement(BothHelpObjs.makeBlankFromNull(rs1.getString("num_charge_types")));
        tmpNumChargeType.addElement(BothHelpObjs.makeBlankFromNull(rs1.getString("num_charge_types")));
      }
    } catch (Exception e1) { e1.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e1.getMessage(),null);   throw e1;
    } finally{
      dbrs1.close();
    }

    /* not until Nish add commidity_type to pr_rules
    query = "select * from pr_rules_view where facility_id = '"+getFacilityId()+"'";
    query += " and commodity_type = 'Material' and account_sys_id = '" +getAccountSysId()+"'";
    query += " order by account_sys_id";
    */
    query = "select * from pr_rules_view where facility_id = '"+getFacilityId()+"'";
    query += " and account_sys_id = '" +getAccountSysId()+"'";
    query += " order by account_sys_id";

    Vector chargeTypeV = new Vector();
    Vector poV = new Vector();
    Vector approverV = new Vector();
    Vector releaserV = new Vector();
    Vector praV = new Vector();
    Vector label1V = new Vector();
    Vector label2V = new Vector();
    Vector valid1V = new Vector();
    Vector valid2V = new Vector();
    Vector chargeId1V = new Vector();
    Vector chargeId2V = new Vector();
    Vector display1V = new Vector();
    Vector display2V = new Vector();
    Vector unitPriceRequiredV = new Vector();
    Vector poSeqRequiredV = new Vector();
    Vector customerRequisitionV = new Vector();
    Vector mrNotificationUserGroupV = new Vector();
    Hashtable typeH = new Hashtable();

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String myTempAccSysType = "";
      while(rs.next()){
        this.setFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
        this.setAccountSysId(BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
        this.setChargeType(BothHelpObjs.makeBlankFromNull(rs.getString("charge_type")));
        this.setPoRequired(BothHelpObjs.makeBlankFromNull(rs.getString("po_required")));
        this.setApproverRequired(BothHelpObjs.makeBlankFromNull(rs.getString("approver_required")));
        this.setReleaserRequired(BothHelpObjs.makeBlankFromNull(rs.getString("releaser_required")));
        this.setPraRequired(BothHelpObjs.makeBlankFromNull(rs.getString("pr_account_required")));
        this.setChargeLabel1(BothHelpObjs.makeBlankFromNull(rs.getString("charge_label_1")));
        this.setChargeValid1(BothHelpObjs.makeBlankFromNull(rs.getString("charge_valid_1")));
        this.setChargeId1(BothHelpObjs.makeBlankFromNull(rs.getString("charge_id_1")));
        this.setChargeDisplay1(BothHelpObjs.makeBlankFromNull(rs.getString("charge_display_1")));
        this.setChargeLabel2(BothHelpObjs.makeBlankFromNull(rs.getString("charge_label_2")));
        this.setChargeValid2(BothHelpObjs.makeBlankFromNull(rs.getString("charge_valid_2")));
        this.setChargeId2(BothHelpObjs.makeBlankFromNull(rs.getString("charge_id_2")));
        this.setChargeDisplay2(BothHelpObjs.makeBlankFromNull(rs.getString("charge_display_2")));
        this.setUnitPriceRequired(BothHelpObjs.makeBlankFromNull(rs.getString("unit_price_required")));
        this.setPoSeqRequired(BothHelpObjs.makeBlankFromNull(rs.getString("po_seq_required")));
        this.setCustomerRequisition(BothHelpObjs.makeBlankFromNull(rs.getString("customer_requisition")));
        this.setMrNotificationUserGroup(BothHelpObjs.makeBlankFromNull(rs.getString("mr_notification_user_group")));
        //getting charge_id
        if(!BothHelpObjs.isBlankString(chargeId1)){
          chargeId1V.addElement(chargeId1);
        }else {
          chargeId1V.addElement("%");
        }
        if(!BothHelpObjs.isBlankString(chargeId2)){
          chargeId2V.addElement(chargeId2);
        }else {
          chargeId2V.addElement("%");
        }

        //getting releaser_required
        if(!BothHelpObjs.isBlankString(releaserRequired)){
          releaserV.addElement(releaserRequired);
        }else {
          releaserV.addElement("%");
        }

        //getting approver_required
        if(!BothHelpObjs.isBlankString(approverRequired)){
          approverV.addElement(approverRequired);
        } else {
          approverV.addElement("%");
        }

        //getting charge_type
        if(!BothHelpObjs.isBlankString(chargeType)){
          chargeTypeV.addElement(chargeType);
        } else {
          chargeTypeV.addElement("%");
        }

        //getting po_required
        if(!BothHelpObjs.isBlankString(poRequired)){
          poV.addElement(poRequired);
        } else {
          poV.addElement("%");
        }

        //getting labels
        if(!BothHelpObjs.isBlankString(chargeLabel1)){
          label1V.addElement(chargeLabel1);
        } else {
          label1V.addElement("%");
        }

        if(!BothHelpObjs.isBlankString(chargeLabel2)){
          label2V.addElement(chargeLabel2);
        } else {
          label2V.addElement("%");
        }


        //getting validations
        if(!BothHelpObjs.isBlankString(chargeValid1)){
          valid1V.addElement(chargeValid1);
        } else {
          valid1V.addElement("%");
        }

        if(!BothHelpObjs.isBlankString(chargeValid2)){
          valid2V.addElement(chargeValid2);
        } else {
          valid2V.addElement("%");
        }


        //getting display
        if(!BothHelpObjs.isBlankString(chargeDisplay1)){
          display1V.addElement(chargeDisplay1);
        } else {
          display1V.addElement("%");
        }

        if(!BothHelpObjs.isBlankString(chargeDisplay2)){
          display2V.addElement(chargeDisplay2);
        } else {
          display2V.addElement("%");
        }

        //getting pr_account_required
        if(!BothHelpObjs.isBlankString(praRequired)){
          praV.addElement(praRequired);
        } else {
          praV.addElement("%");
        }

        //getting unit_price_required
        if(!BothHelpObjs.isBlankString(unitPriceRequired)){
          unitPriceRequiredV.addElement(unitPriceRequired);
        } else {
          unitPriceRequiredV.addElement("%");
        }

        //getting po_seq_required
        if(!BothHelpObjs.isBlankString(poSeqRequired)){
          poSeqRequiredV.addElement(poSeqRequired);
        } else {
          poSeqRequiredV.addElement("%");
        }

        //getting customer_requisition
        if(!BothHelpObjs.isBlankString(customerRequisition)){
          customerRequisitionV.addElement(customerRequisition);
        } else {
          customerRequisitionV.addElement("%");
        }

        //getting mr_notification_user_group
        if(!BothHelpObjs.isBlankString(mrNotificationUserGroup)){
          mrNotificationUserGroupV.addElement(mrNotificationUserGroup);
        } else {
          mrNotificationUserGroupV.addElement("%");
        }

      }

      typeH.put("CHARGE_ID_1",chargeId1V);
      typeH.put("CHARGE_ID_2",chargeId2V);
      typeH.put("RELEASER_REQUIRED",releaserV);
      typeH.put("APPROVER_REQUIRED",approverV);
      typeH.put("CHARGE_TYPE",chargeTypeV);
      typeH.put("PO_REQUIRED",poV);
      typeH.put("LABEL_1",label1V);
      typeH.put("LABEL_2",label2V);
      typeH.put("VALID_1",valid1V);
      typeH.put("VALID_2",valid2V);
      typeH.put("DISPLAY_1",display1V);
      typeH.put("DISPLAY_2",display2V);
      typeH.put("PR_ACCOUNT_REQUIRED",praV);
      typeH.put("UNIT_PRICE_REQUIRED",unitPriceRequiredV);
      typeH.put("PO_SEQ_REQUIRED",poSeqRequiredV);
      typeH.put("CUSTOMER_REQUISITION",customerRequisitionV);
      typeH.put("MR_NOTIFICATION_USER_GROUP",mrNotificationUserGroupV);
      //getting num_charge_types for this account sys
      String num_charge_types = (String)tmpNumChargeType.elementAt(0);
      typeH.put("NUM_CHARGE_TYPES",num_charge_types);

      accTypeH.put(accountSysId,typeH);
    } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
    } finally{
      dbrs.close();
    }
  }
}






