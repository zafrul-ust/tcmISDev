
package radian.tcmis.server7.share.dbObjs;

/*
SQLWKS> desc facility_mr_rules 
Column Name                    Null?    Type
------------------------------ -------- ----
FACILITY_ID                    NOT NULL VARCHAR2(30)
PO_REQUIRED                             VARCHAR2(1)
INDIRECT_OR_DIRECT                      VARCHAR2(1)
APPROVER_REQUIRED                       VARCHAR2(1)
RELEASER_REQUIRED                       VARCHAR2(1)
DIRECT_CHARGE_LABEL_1                   VARCHAR2(30)
DIRECT_CHARGE_LABEL_2                   VARCHAR2(30)
INDIRECT_CHARGE_LABEL_1                 VARCHAR2(30)
INDIRECT_CHARGE_LABEL_2                 VARCHAR2(30)
PR_ACCOUNT_REQUIRED                     VARCHAR2(1)
DIRECT_CHARGE_1_VALIDATION              VARCHAR2(1)
DIRECT_CHARGE_2_VALIDATION              VARCHAR2(1)
INDIRECT_CHARGE_1_VALIDATION            VARCHAR2(1)
INDIRECT_CHARGE_2_VALIDATION            VARCHAR2(1)
*/


import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;



import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class MrRules {

   protected TcmISDBModel db;
   protected String facId;
   protected String poRequired = "";
   protected String indirectOrDirect = "";
   protected String approverRequired = "";
   protected String releaserRequired = "";
   protected String directChargeLabel1 = "";
   protected String directChargeLabel2 = "";
   protected String indirectChargeLabel1 = "";
   protected String indirectChargeLabel2 = "";
   protected String praRequired = "";
   //trong 3/28
   protected String indirectCharge1Val = "";
   protected String indirectCharge2Val = "";
   protected String directCharge1Val = "";
   protected String directCharge2Val = "";

   //trong 4/8
   protected String accSysId;
   public void setAccSysId(String s) {
    this.accSysId = s;
   }
   public String getAccSysId() {
    return this.accSysId;
   }

   public MrRules(TcmISDBModel db){
      this.db = db;
   }

   public void setFacilityId(String s){
     facId = new String(s);
   }
   public void setPoRequired(String s){
     poRequired = new String(s);
   }
   public void setApproverRequired(String s){
     approverRequired = new String(s);
   }
   public void setIndirectOrDirect(String s){
     indirectOrDirect = new String(s);
   }
   public void setReleaserRequired(String s){
     releaserRequired = new String(s);
   }
   public void setDirectChargeLabel1(String s){
     directChargeLabel1= new String(s);
   }
   public void setDirectChargeLabel2(String s){
     directChargeLabel2= new String(s);
   }
   public void setIndirectChargeLabel1(String s){
     indirectChargeLabel1= new String(s);
   }
   public void setIndirectChargeLabel2(String s){
     indirectChargeLabel2 = new String(s);
   }
   public void setPraRequired(String s){
     praRequired = new String(s);
   }

   //trong 3/28
   public void setIndirectCharge1Val(String s){
     indirectCharge1Val = new String(s);
   }
   public void setIndirectCharge2Val(String s){
     indirectCharge2Val = new String(s);
   }
   public void setDirectCharge1Val(String s){
     directCharge1Val = new String(s);
   }
   public void setDirectCharge2Val(String s){
     directCharge2Val = new String(s);
   }
   public String getIndirectCharge1Val(){
     return indirectCharge1Val;
   }
   public String getIndirectCharge2Val(){
     return indirectCharge2Val;
   }
   public String getDirectCharge1Val(){
     return directCharge1Val;
   }
   public String getDirectCharge2Val(){
     return directCharge2Val;
   }


   public String getFacilityId(){
     return facId;
   }
   public String getPoRequired(){
     return poRequired;
   }
   public String getIndirectOrDirect(){
     return indirectOrDirect;
   }
   public String getApproverRequired(){
     return approverRequired;
   }
   public String getReleaserRequired(){
     return releaserRequired;
   }
   public String getDirectChargeLabel1(){
     return directChargeLabel1;
   }
   public String getDirectChargeLabel2(){
     return directChargeLabel2;
   }
   public String getIndirectChargeLabel1(){
     return indirectChargeLabel1;
   }
   public String getIndirectChargeLabel2(){
     return indirectChargeLabel2;
   }
   public String getPraRequired(){
     return praRequired;
   }
   public Vector getDirectChargeLabels(){
     Vector v = new Vector();
     if(!BothHelpObjs.isBlankString(getDirectChargeLabel1())){
       v.addElement(getDirectChargeLabel1());
     }
     if(!BothHelpObjs.isBlankString(getDirectChargeLabel2())){
       v.addElement(getDirectChargeLabel2());
     }
     v.addElement("%");
     return v;
   }
   public Vector getIndirectChargeLabels(){
     Vector v = new Vector();
     if(!BothHelpObjs.isBlankString(getIndirectChargeLabel1())){
       v.addElement(getIndirectChargeLabel1());
     }
     if(!BothHelpObjs.isBlankString(getIndirectChargeLabel2())){
       v.addElement(getIndirectChargeLabel2());
     }
     v.addElement("%");
     return v;
   }

   public void load()throws Exception{
      String query = "select * from pr_rules_view where facility_id = '" + facId+"'";
      query += " and account_sys_id = '"+accSysId+"'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while(rs.next()){
           this.setPoRequired(rs.getString("po_required")==null?"":rs.getString("po_required"));
           this.setIndirectOrDirect(rs.getString("charge_type")==null?"":rs.getString("charge_type"));
           this.setApproverRequired(rs.getString("approver_required")==null?"":rs.getString("approver_required"));
           this.setReleaserRequired(rs.getString("releaser_required")==null?"":rs.getString("releaser_required"));
           this.setDirectChargeLabel1(rs.getString("charge_label_1")==null?"":rs.getString("charge_label_1"));
           this.setDirectChargeLabel2(rs.getString("charge_label_2")==null?"":rs.getString("charge_label_2"));
           this.setIndirectChargeLabel1(rs.getString("charge_display_1")==null?"":rs.getString("charge_display_1"));
           this.setIndirectChargeLabel2(rs.getString("charge_display_2")==null?"":rs.getString("charge_diplay_2"));
           this.setPraRequired(rs.getString("pr_account_required")==null?"n":rs.getString("pr_account_required"));
           //trong 3/28
           this.setDirectCharge1Val(BothHelpObjs.makeBlankFromNull(rs.getString("charge_valid_1")));
           this.setDirectCharge2Val(BothHelpObjs.makeBlankFromNull(rs.getString("charge_valid_2")));

           

         }


      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
   }







   /*
   public void load()throws Exception{
      String query = "select * from pr_rules_view where facility_id = '" + facId+"'";
      query += " and account_sys_id = '"+accSysId+"'";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while(rs.next()){
           this.setPoRequired(rs.getString("po_required")==null?"":rs.getString("po_required"));
           this.setIndirectOrDirect(rs.getString("indirect_or_direct")==null?"":rs.getString("indirect_or_direct"));
           this.setApproverRequired(rs.getString("approver_required")==null?"":rs.getString("approver_required"));
           this.setReleaserRequired(rs.getString("releaser_required")==null?"":rs.getString("releaser_required"));
           this.setDirectChargeLabel1(rs.getString("direct_charge_label_1")==null?"":rs.getString("direct_charge_label_1"));
           this.setDirectChargeLabel2(rs.getString("direct_charge_label_2")==null?"":rs.getString("direct_charge_label_2"));
           this.setIndirectChargeLabel1(rs.getString("indirect_charge_label_1")==null?"":rs.getString("indirect_charge_label_1"));
           this.setIndirectChargeLabel2(rs.getString("indirect_charge_label_2")==null?"":rs.getString("indirect_charge_label_2"));
           this.setPraRequired(rs.getString("pr_account_required")==null?"n":rs.getString("pr_account_required"));
           //trong 3/28
           this.setDirectCharge1Val(BothHelpObjs.makeBlankFromNull(rs.getString("direct_charge_1_validation")));
           this.setDirectCharge2Val(BothHelpObjs.makeBlankFromNull(rs.getString("direct_charge_2_validation")));
           this.setIndirectCharge1Val(BothHelpObjs.makeBlankFromNull(rs.getString("indirect_charge_1_validation")));
           this.setIndirectCharge2Val(BothHelpObjs.makeBlankFromNull(rs.getString("indirect_charge_2_validation")));
         }


      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
   } */
   public boolean needsApprover(){
     return !approverRequired.equalsIgnoreCase("n");
   }
   public boolean needsReleaser(){
     return !releaserRequired.equalsIgnoreCase("n");
   }
  /*
   public static Hashtable getMrRuleHash(TcmISDBModel db,Vector facIds)throws Exception{
     Hashtable out = new Hashtable();
     try{
       for(int i=0;i<facIds.size();i++){
         Hashtable h = new Hashtable();
         String id = facIds.elementAt(i).toString();
         MrRules mr = new MrRules(db);
         mr.setFacilityId(id);
         mr.load();
         h.put("PO_REQ",new Boolean(mr.getPoRequired().equalsIgnoreCase("y")));
         h.put("INDIRECT_OR_DIRECT",new Boolean(mr.getIndirectOrDirect().equalsIgnoreCase("y")));
         h.put("APPR_REQ",new Boolean(mr.getApproverRequired().equalsIgnoreCase("y")));
         h.put("REL_REQ",new Boolean(mr.getReleaserRequired().equalsIgnoreCase("y")));
         h.put("CHARGE_NUM_REQ",new Boolean(mr.getPraRequired().equalsIgnoreCase("y")));
         h.put("DCHARGE_LABEL_1",mr.getDirectChargeLabel1());
         h.put("DCHARGE_LABEL_2",mr.getDirectChargeLabel2());
         h.put("IND_CHARGE_LABEL_1",mr.getIndirectChargeLabel1());
         h.put("IND_CHARGE_LABEL_2",mr.getIndirectChargeLabel2());
         out.put(id,h);
       }
     }catch(Exception e){e.printStackTrace();throw e;}
     return out;
   }  */
}


