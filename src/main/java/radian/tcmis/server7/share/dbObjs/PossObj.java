/*
SQL> describe poss_add_request
 Name                            Null?    Type
 ------------------------------- -------- ----
 REQUEST_ID                               NUMBER
 POSS_NO                                  VARCHAR2(12)
 TRADENAME                                VARCHAR2(30)
 DESCRIPTION                              VARCHAR2(400)
 MANUFACTURER                             VARCHAR2(223)
 POSS_SIZE                                VARCHAR2(25)
 MANUFACTURERS_PART                       VARCHAR2(315)
 DWG_OR_SPEC_NUMBER                       VARCHAR2(200)
 TESTING_REQS_DOCUMENT                    VARCHAR2(100)
 QA_ATT_PKG_ATT                           VARCHAR2(298)
 DUP_INSTOCK                              VARCHAR2(1)
 DUP_PARTNO                               VARCHAR2(15)
 NEW_POSS                                 VARCHAR2(1)
 REPLACE_POSS                             VARCHAR2(1)
 REPL_PARTNO                              VARCHAR2(15)
 ISS_TOZERO_DELETE                        VARCHAR2(1)
 DEL_UPON_RECV                            VARCHAR2(1)
 DISP_OTHER                               VARCHAR2(1)
 DISP_OTHER_TEXT                          VARCHAR2(255)
 EST_MON_USAGE                            VARCHAR2(15)
 STORE                                    VARCHAR2(25)
 SL_INIT_RETEST                           VARCHAR2(22)
 AGE                                      VARCHAR2(2)
 ORD_FACILITY                             VARCHAR2(255)
 ORD_SOURCE_CD                            VARCHAR2(255)
 APPROVED_USERS                           VARCHAR2(100)
 MSDS_NUMBER                              VARCHAR2(95)
 SUGGESTED_VENDOR                         VARCHAR2(223)
 MAX_SUGGESTED_VENDOR_PART_NO             VARCHAR2(315)
 PROPER_SHIPPING_NAME                     VARCHAR2(100)
 HAZARD_CLASS                             VARCHAR2(20)
 UN_NUMBER                                VARCHAR2(15)
 NA_NUMBER                                VARCHAR2(15)
 PACKING_GROUP                            VARCHAR2(10)
 NFPA_HMIS_NUMBERS                        VARCHAR2(23)
 REQUESTOR_LAST                           VARCHAR2(30)
 REQUESTOR_FIRST                          VARCHAR2(30)
 REQUESTOR_MIDDLE                         VARCHAR2(30)
 MFG_ADDRESS                             VARCHAR2(500)
 MFG_PHONE                               VARCHAR2(100)
 DISTRIBUTOR_ADDRESS                     VARCHAR2(500)
 DISTRIBUTOR_PHONE                       VARCHAR2(100)
 ADDITIONAL_SPECS                        VARCHAR2(100)
 SPECIAL_NOTES                           VARCHAR2(500)

*/

package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class PossObj {
   protected TcmISDBModel db;
   protected Vector data;

   protected Integer request_id = null;

   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;

   public static String[] fields =
        {
        "POSS_NO",
        "TRADENAME",
        "DESCRIPTION",
        "MANUFACTURER",
        "POSS_SIZE",
        "MANUFACTURERS_PART",
        "DWG_OR_SPEC_NUMBER",
        "TESTING_REQS_DOCUMENT",
        "QA_ATT_PKG_ATT",
        "DUP_INSTOCK",
        "DUP_PARTNO",
        "NEW_POSS",
        "REPLACE_POSS",
        "REPL_PARTNO",
        "ISS_TOZERO_DELETE",
        "DEL_UPON_RECV",
        "DISP_OTHER",
        "DISP_OTHER_TEXT",
        "EST_MON_USAGE",
        "STORE",
        "SL_INIT_RETEST",
        "AGE",
        "ORD_FACILITY",
        "ORD_SOURCE_CD",
        "APPROVED_USERS",
        "MSDS_NUMBER",
        "SUGGESTED_VENDOR",
        "MAX_SUGGESTED_VENDOR_PART_NO",
        "PROPER_SHIPPING_NAME",
        "HAZARD_CLASS",
        "UN_NUMBER",
        "NA_NUMBER",
        "PACKING_GROUP",
        "NFPA_HMIS_NUMBERS",
        "REQUESTOR_LAST",
        "REQUESTOR_FIRST",
        "REQUESTOR_MIDDLE",
        "MFG_ADDRESS",
        "MFG_PHONE",
        "DISTRIBUTOR_ADDRESS",
        "DISTRIBUTOR_PHONE",
        "ADDITIONAL_SPECS",
        "SPECIAL_NOTES"
        };

    public static String[] file_fields =
        {
        "part number:",
        "trade name:",
        "description:",
        "manufacturer:",
        "container size:",
        "mfg part no:",
        "dwg or spec number:",
        "testing requirements doc:",
        "quality/packaging flowdown reqs:",
        "duplicate instock:",
        "duplicate part no:",
        "new poss:",
        "replace poss:",
        "replaced part no:",
        "issue to 0 then del:",
        "delete upon receipt:",
        "use other disposition:",
        "other disposition desc:",
        "monthly usage est:",
        "storage conditions:",
        "shelf life/retest instructions:",
        "age:",
        "approved facility:",
        "approved source code:",
        "approved users:",
        "msds number:",
        "suggested vendor:",
        "suggested vendor part no:",
        "proper shipping name:",
        "hazard class:",
        "un number:",
        "na number:",
        "packing group:",
        "npfa-hmis numbers:",
        "requestor last name:",
        "requestor first name:",
        "requestor mi:",
        "mfg address:",
        "mfg tele:",
        "distrib address:",
        "distrib tele:",
        "additional specs:",
        "special notes:"
        };



   public PossObj() throws Exception {

   }

   public PossObj(TcmISDBModel db) throws Exception {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setRequestId(int num) {
     this.request_id = new Integer(num);
   }

   public Integer getRequestId() {
     return request_id;
   }

   void buildCatalogAddRequest(int reqID) throws Exception {
      String query = "select distinct par.poss_no,par.repl_partno,p.personnel_id,par.distributor_phone,par.suggested_vendor,par.est_mon_usage,par.store,"+
                     "par.description,par.manufacturer,par.manufacturers_part,par.tradename,par.poss_size,par.special_notes"+
                     " from poss_add_request par, personnel p"+
                     " where trim(lower(par.requestor_last)) = trim(lower(p.last_name(+))) and trim(lower(par.requestor_first)) = trim(lower(p.first_name(+)))"+
                     " and trim(lower(par.requestor_middle)) = trim(lower(p.mid_initial(+))) and par.request_id = "+reqID;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      StringBuffer carnColVal = new StringBuffer("insert into catalog_add_request_new (request_id,request_date,catalog_id,stocked,starting_view,engineering_evaluation,shelf_life_source,catalog_company_id,");
      StringBuffer carnVal = new StringBuffer(" values("+reqID+",sysdate,'Tucson','OOR',0,'n','Mfg','RAYTHEON',");
      StringBuffer caiColVal = new StringBuffer("insert into catalog_add_item (request_id,");
      StringBuffer caiVal = new StringBuffer(" values("+reqID+",");
      String[] possFacility = {"Tucson Airport"};
      String tmpPartNo = "";
      String tmpComment = "New POSS";
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        String tmp = "";
        while (rs.next()) {
          //catalog_add_request_new
          tmp = rs.getString("poss_no");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 3) {
              carnColVal.append("cat_part_no,");
              tmp = HelpObjs.singleQuoteToDouble(tmp);
              carnVal.append("'"+tmp+"',");
              tmpPartNo = tmp;
            }
          }
          tmp = rs.getString("repl_partno");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              carnColVal.append("replaces_part_no,");
              carnVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
          tmp = rs.getString("personnel_id");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              carnColVal.append("requestor,");
              carnVal.append(tmp+",");
            }
          }else {
            carnColVal.append("requestor,");
            carnVal.append("86030,");
          }
          tmp = rs.getString("distributor_phone");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              carnColVal.append("vendor_contact_phone,");
              carnVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
          tmp = rs.getString("suggested_vendor");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              carnColVal.append("suggested_vendor,");
              carnVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
          tmp = rs.getString("est_mon_usage");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              carnColVal.append("poss_est_mon_usage,");
              carnVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
          tmp = rs.getString("store");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              carnColVal.append("poss_store,");
              carnVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
          //catalog_add_item
          tmp = rs.getString("description");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              caiColVal.append("material_desc,");
              caiVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
              //also put it in part description as well
              carnColVal.append("part_description,");
              carnVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
          tmp = rs.getString("manufacturer");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              caiColVal.append("manufacturer,");
              caiVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
          tmp = rs.getString("manufacturers_part");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              caiColVal.append("mfg_catalog_id,");
              caiVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
          tmp = rs.getString("tradename");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              caiColVal.append("mfg_trade_name,");
              caiVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
          tmp = rs.getString("poss_size");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              caiColVal.append("poss_size,");
              caiVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
          tmp = rs.getString("special_notes");
          if (tmp != null) {
            tmp = tmp.trim();
            if (tmp.length() > 0) {
              caiColVal.append("poss_special_note,");
              caiVal.append("'"+HelpObjs.singleQuoteToDouble(tmp)+"',");
            }
          }
        } //end of while
        //decide wether to go to MSDS or QC
        carnColVal.append("qc_status,");
        if (tmpPartNo.length() > 0) {
          //if part no is in cpig then don't go to MSDS
          if (HelpObjs.countQuery(db,"select count(*) from catalog_part_item_group where cat_part_no = '"+tmpPartNo+"' and catalog_id = 'Tucson'") > 0) {
            carnVal.append("'Pending QC',");
            tmpComment = "POSSAR";
          }else {
            carnVal.append("'Pending MSDS',");
          }
          //adding POSS number to comment
          tmpComment += " - "+tmpPartNo;
        }else {
          carnVal.append("'Pending MSDS',");
        }
        //so I don't have to remove the last commas I will go ahead and add on something with value
        carnColVal.append("submit_date,request_status,approval_group_seq,poss,eng_eval_facility_id)");
        carnVal.append("sysdate,6,1,'Y','"+possFacility[0]+"')");
        caiColVal.append("part_id)");
        caiVal.append("1)");

        //first insert into catalog_add_request_new
        db.doUpdate(carnColVal.toString()+carnVal.toString());
        //next catalog_add_item
        db.doUpdate(caiColVal.toString()+caiVal.toString());
        //catalog_add_user_group allows multiple facility
        for (int i = 0; i < possFacility.length; i++) {
          query = "insert into catalog_add_user_group (request_id,facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,quantity_2,per_2,period_2)"+
                " values("+reqID+",'"+possFacility[i]+"','All','All','POSS material',0,0,'days',0,0,'days')";
          db.doUpdate(query);
        } //end of for loop

        //save user data
        query = "insert into catalog_add_item_orig (request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
              "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
              "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,poss_size,poss_special_note)"+
              " (select request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
              "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
              "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,poss_size,poss_special_note "+
              "from catalog_add_item where request_id = "+reqID+")";
        db.doUpdate(query);

        //catalog_add_item_qc
        query = "insert into catalog_add_item_qc (request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
              "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
              "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,status,comments)"+
              " (select request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,"+
              "material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,"+
              "mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,"+
              "decode(material_id,null,'Pending MSDS','0','Pending MSDS','Pending QC'),'"+tmpComment+"' from catalog_add_item where request_id = "+reqID+")";
        db.doUpdate(query);
      }catch (Exception e) {
        throw e;
      }finally {
        dbrs.close();
      }
   }  //end of method

   public void updateTable(Vector d,String fileName) throws Exception {
     int nx = 0;
     try {
       //First get a reqID from sequence
       nx = HelpObjs.getNextValFromSeq(db,"request_seq");
       if ( nx == 0) {
         radian.web.emailHelpObj.sendtrongemail("Unable to get request ID from sequence.","POSS file: "+fileName);
         return;
       }
       //next build POSS Request
       String[] data = createPOSSRequest(d,nx);
       if ("OK".equalsIgnoreCase(data[0])) {
         //Next build catalog add request
         buildCatalogAddRequest(nx);
         //send mail
         sendPOSSEmail(nx,data[1],data[2]);
       }
     } catch (Exception e){
       e.printStackTrace();
       radian.web.emailHelpObj.sendtrongemail("Error creating POSS request","Error creating POSS request - request_id ("+nx+")\n"+e.getMessage()+"\nPOSS file: "+fileName);
     }
   } //end of method

   String[] createPOSSRequest(Vector data, int requestID) throws Exception {
     String[] returnVal = new String[3];
     returnVal[0] = "OK";
     try {
       //Then build POSS data
       String last = null;
       String first = null;
       String partN = null;
       //System.out.println("Starting");
       String totalValue = new String("");
       StringBuffer colVal = new StringBuffer("insert into poss_add_request (request_id,");
       StringBuffer val = new StringBuffer(" values("+requestID+",");
       int db_i=0; // index for db fields
       int file_i=0; // index for file fileds
       for (int i=0;i<data.size();i++){ // index for file lines
			 /*
			System.out.println("Inserting:"+data.elementAt(i)+" Size:"+data.elementAt(i).toString().length()+
                            " on field:"+file_fields[file_i]+" size: "+file_fields[file_i].length()+
                            " from DBfield:"+fields[db_i]+" size: "+fields[db_i].length());
         */
         String ttmp = (data.elementAt(i)==null?null:(String) data.elementAt(i));
         if (ttmp==null) continue;
         if (ttmp.indexOf(file_fields[file_i])>-1){ // starting a field
           // insert and renew
           if (file_i>0){
            colVal.append(fields[db_i-1]+",");
            val.append("'"+HelpObjs.singleQuoteToDouble(totalValue)+"',");
            totalValue = "";
          }

          if (fields[db_i].equalsIgnoreCase("REQUESTOR_LAST")){
            if (ttmp.length()>29) ttmp=ttmp.substring(0,28);
            last = new String(ttmp.substring(ttmp.indexOf(":")+1).trim());
          }
          if (fields[db_i].equalsIgnoreCase("REQUESTOR_FIRST")){
            if (ttmp.length()>29) ttmp=ttmp.substring(0,28);
            first = new String(ttmp.substring(ttmp.indexOf(":")+1).trim());
          }
          if (fields[db_i].equalsIgnoreCase("POSS_NO")){
            partN = new String(ttmp.substring(ttmp.indexOf(":")+1).trim());
          }

          totalValue += ttmp.substring(ttmp.indexOf(":")+1).trim();
          db_i++;
          file_i++;
          continue;
        }
        totalValue += "" + ttmp;
      }
      colVal.append(fields[db_i-1]+")");
      val.append("'"+HelpObjs.singleQuoteToDouble(totalValue)+"')");
      //insert create poss_add_request
      db.doUpdate(colVal.toString()+val.toString());
      returnVal[1] = (last==null?"":last)+", "+(first==null?"":first);
      returnVal[2] = (partN==null?"":partN);
     }catch (Exception e) {
       throw e;
     }
     return returnVal;
   }

   void sendPOSSEmail(int requestID, String requestor, String partNo) throws Exception {
     String emsg = "";
     try {
       String esub = new String("Request " + requestID + " was CREATED by POSS.");
       emsg = new String("Status     : APPROVED by POSS\n");
       emsg += "Request Id : " + requestID + "\n";
       emsg += "Requestor  : " + requestor + "\n";
       emsg += "POSS number: " + partNo + "\n";
       UserGroup ug = new UserGroup(db);
       ug.setGroupId("POSSEmail");
       ug.setGroupFacId("Phoenix Hub");
       //System.out.println(emsg);
       HelpObjs.sendMail(ug, esub, emsg);
     }catch (Exception e) {
       throw e;
     }
   }

   public static String updateTableOld(TcmISDBModel db,Vector d) throws Exception {
     Vector data = d;

     //First create a reqID
     int nx = 0;
     CatalogAddRequestNew car = null;
     CatalogAddItem cai =null;
     PossObj poss = null;
     try {
      car = new CatalogAddRequestNew(db);
      nx = car.getNext();
      if ( nx == 0) {
        return "No CatalogAddRequest Produced";
      }

      car.setRequestId(nx);
      //car.setRequestor(Integer.parseInt("18864")); // need to change to the raytheon approver id
      car.setRequestor(Integer.parseInt("3101")); // Gary Maley
      car.setRequestDate(new String("nowDate")); // sysdate update
      car.insert();
      car.commit();

      car.insert("REQUEST_STATUS","0",CatalogAddRequestNew.INT);
      car.insert("STARTING_VIEW","0",CatalogAddRequestNew.INT);

      //clear flag
      car.insert("REQUEST_REJECTED","",car.STRING);
      car.insert("REQUEST_STATUS","8",car.INT);  // approved
      car.commit();

      // Add a record on catalogAddItem just to register for tracking
      cai = new CatalogAddItem(db);
      cai.setRequestId(nx);
      cai.setPartId(new Integer(1));
      cai.insert();
      cai.insert("MATERIAL_DESC","Material added from POSS",cai.STRING);
     } catch (Exception e){
      e.printStackTrace();
      if (cai!=null) cai.delete();
      if (car!=null) car.delete();
      return "Error on inserting CatalogAddRequest. Error: " + e.getMessage();
     }

     String last = null;
     String first = null;
     String partN = null;

     try{
      //Then build POSS
      poss = new PossObj(db);
      poss.setRequestId(nx);
      poss.insert();
      //System.out.println("Starting");
      String totalValue = new String("");
      int db_i=0; // index for db fields
      int file_i=0; // index for file fileds
      for (int i=0;i<data.size();i++){ // index for file lines
        String ttmp = (data.elementAt(i)==null?null:(String) data.elementAt(i));
        if (ttmp==null) continue;
        if (ttmp.indexOf(file_fields[file_i])>-1){ // starting a field
          // insert and renew
          if (file_i>0){
             poss.insert(fields[db_i-1],totalValue,poss.STRING);
             totalValue = new String("");
          }

          if (fields[db_i].equalsIgnoreCase("REQUESTOR_LAST")){
            if (ttmp.length()>29) ttmp=ttmp.substring(0,28);
            last = new String(ttmp.substring(ttmp.indexOf(":")+1).trim());
          }
          if (fields[db_i].equalsIgnoreCase("REQUESTOR_FIRST")){
            if (ttmp.length()>29) ttmp=ttmp.substring(0,28);
            first = new String(ttmp.substring(ttmp.indexOf(":")+1).trim());
          }
          if (fields[db_i].equalsIgnoreCase("POSS_NO")){
            partN = new String(ttmp.substring(ttmp.indexOf(":")+1).trim());
          }

          totalValue += ttmp.substring(ttmp.indexOf(":")+1).trim();
          db_i++;
          file_i++;
          continue;
        }
        totalValue += "\n" + ttmp;
      }
      poss.insert(fields[db_i-1],totalValue,poss.STRING); //last value

      // add requestor to CatalogAddRequest...
      //System.out.println("Got last:"+last+" and first:"+first);
      Personnel p = new Personnel(db);
      String w = "lower(last_name) like lower('%"+last+"%') and lower(first_name) like lower('%"+first+"%')";
      Vector vp = p.retrieve(w,null);
      //System.out.println("Got back from personnel:"+w);
      if (vp!=null&&vp.size()>0){
        p = (Personnel)vp.elementAt(0);
        Integer pid = p.getPersonnelId();
        //System.out.println("Got id:"+pid);
        car.insert("REQUESTOR",pid.toString(),car.INT);
      }

     } catch (Exception e){
      e.printStackTrace();
      if (cai!=null) cai.delete();
      if (car!=null) car.delete();
      if (poss!=null) poss.delete();
      return "Error on inserting PossAddRequest. Error: " + e.getMessage();
     }
     // Send mail
     String esub = new String("Request "+poss.getRequestId()+" was CREATED by POSS.");
     String emsg = new String("Status     : APPROVED by POSS\n");
     emsg += "Request Id : "+poss.getRequestId()+"\n";
     emsg += "Requestor  : "+(last==null?"":last)+", "+(first==null?"":first)+"\n";
     emsg += "POSS number: "+(partN==null?"":partN)+"\n";
     //HelpObjs.sendMail(db,esub,emsg,19143,true); // Steve Buffum
     HelpObjs.sendMail(db,esub,emsg,19349,true); // Veronica Morton
     HelpObjs.sendMail(db,esub,emsg,86161,true); // Stephanie Harlan
     //HelpObjs.sendMail(db,esub,emsg,18864,true); // Rodrigo Zerlotti
     HelpObjs.sendMail(db,esub,emsg,9,true); // Patricia Mosher
     HelpObjs.sendMail(db,esub,emsg,6153,true); // Lisa Gregg
     HelpObjs.sendMail(db,esub,emsg,421,true); // Sharyn Holden

     return "tcmIS update successfull.";
   }

   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
      ResultSet rs = null;

     val = HelpObjs.singleQuoteToDouble(val);
     String query = new String("update poss_add_request set " + col + " = ");
     switch (type) {
       case INT:
         I = new Integer(val);
         query = query + I.toString();
         break;
       case DATE:
         try {
            String q  = "select to_char(sysdate,'HH24:Mi:SS') from dual";
           dbrs = db.doQuery(q );
           rs=dbrs.getResultSet();
           if (rs.next()) {
	            val = val + " " + rs.getString(1);
           }

         } catch (Exception e){
           throw e;
       } finally{
             dbrs.close();
           }
         if (val.equals("nowDate")){
           query = query + " SYSDATE ";
         } else {
           query = query + " to_date('"+val+"','MM/dd/yyyy HH24:MI:SS')";
         }
         break;
       case STRING:
         query = query + "'" + val + "'";
         break;
       default:
         query = query + "'" + val + "'";
         break;
     }
     query = query + " where request_id = " + request_id.toString();
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();


    } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);

       throw e;
       } finally{
             dbrs.close();

     }
   }

   public void insert()  throws Exception {
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String dummy = new String("");

     String query = new String("insert into poss_add_request (request_id)");
     query = query + " values (" + request_id.toString()+")" ;

     try {
        db.doUpdate(query);
    } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);

       throw e;
       }
   }


   public void delete() throws Exception {
     String query = "delete poss_add_request where request_id = " + request_id.toString();
     DBResultSet dbrs = null;
     try {
        db.doUpdate(query);


     } catch (Exception e) {
       e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
   }

}

