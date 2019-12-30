package radian.tcmis.server7.share.dbObjs;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;


import java.lang.String;


public class ProcessDetailDBObj  {
   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;
   protected TcmISDBModel db;

   public ProcessDetailDBObj(){
   }

   public ProcessDetailDBObj(TcmISDBModel db) {
     this.db = db;
   }

   public void resetOtherRequestWProcess(String requestID, String userID) {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       //first find out facility and work areas
       String facID = "";
       Vector waIDV = new Vector(50);
       String query = "select distinct facility_id,application from ca_process where request_id = "+requestID;
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         facID = rs.getString("facility_id");
         waIDV.addElement(rs.getString("application"));
       }
       //combining work area
       String waIDs = "";
       for (int i = 0; i < waIDV.size(); i++) {
         waIDs += "'"+(String) waIDV.elementAt(i)+"',";
       }
       //removing the last commas
       if (waIDs.length() > 0) {
         waIDs = waIDs.substring(0,waIDs.length()-1);
       }
       //now get requests that is related
       /*
       query = "select distinct b.request_id,b.requestor,a.process_id from ca_process a, catalog_add_request_new b,catalog_add_user_group c"+
               " where  a.facility_id = '"+facID+"' and a.application in ("+waIDs+")"+
               " and a.process_id in (select process_id from ca_process where fx_process_multiple(facility_id,application,process_id) = 'Y' and request_id = "+requestID+")"+
               " and a.request_id = b.request_id and b.request_status not in (7,9,10,12) and a.request_id = c.request_id"+
               " and a.facility_id = c.facility_id and a.application = c.work_area and a.request_id <> "+requestID+
               " order by requestor,request_id,process_id";
        */
        query = "select distinct b.request_id,b.requestor,b.request_status,to_char(b.request_date ,'MM/DD/YYYY') request_date,a.process_id from ca_process a, catalog_add_request_new b,catalog_add_user_group c"+
                 " where  a.facility_id = '"+facID+"' and a.application in ("+waIDs+")"+
                 " and a.process_id in (select process_id from ca_process where request_id = "+requestID+")"+
                 " and a.request_id = b.request_id and b.request_status not in (7,10) and a.request_id = c.request_id"+
                 " and a.facility_id = c.facility_id and a.application = c.work_area and a.request_id <> "+requestID+
                 " order by requestor,request_id,process_id";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        Hashtable h = new Hashtable(30);
        while (rs.next()){
          String tmp = rs.getString("requestor");
          if (h.containsKey(tmp)) {
            Hashtable innerH = (Hashtable) h.get(tmp);
            Vector requestV = (Vector) innerH.get("REQUEST_IDS");
            Vector requestStatusV = (Vector) innerH.get("REQUEST_STATUS");
            Vector requestDateV = (Vector) innerH.get("REQUEST_DATES");
            Vector processV = (Vector) innerH.get("PROCESS_IDS");
            requestV.addElement(rs.getString("request_id"));
            requestStatusV.addElement(rs.getString("request_status"));
            requestDateV.addElement(rs.getString("request_date"));
            processV.addElement(rs.getString("process_id"));
            innerH.put("REQUEST_IDS",requestV);
            innerH.put("REQUEST_STATUS",requestStatusV);
            innerH.put("REQUEST_DATES",requestDateV);
            innerH.put("PROCESS_IDS",processV);
            h.put(tmp,innerH);
          }else {
            Hashtable innerH = new Hashtable(3);
            Vector requestV = new Vector(20);
            Vector requestStatusV = new Vector(20);
            Vector requestDateV = new Vector(20);
            Vector processV = new Vector(20);
            requestV.addElement(rs.getString("request_id"));
            requestStatusV.addElement(rs.getString("request_status"));
            requestDateV.addElement(rs.getString("request_date"));
            processV.addElement(rs.getString("process_id"));
            innerH.put("REQUEST_IDS",requestV);
            innerH.put("REQUEST_STATUS",requestStatusV);
            innerH.put("REQUEST_DATES",requestDateV);
            innerH.put("PROCESS_IDS",processV);
            h.put(tmp,innerH);
          }
        }
        //now update data and notify requestors
        Enumeration enuma;
        for (enuma = h.keys(); enuma.hasMoreElements();){
          String k = (String) enuma.nextElement();
          Hashtable innerH = (Hashtable) h.get(k);
          Vector requestV = (Vector) innerH.get("REQUEST_IDS");
          String lastRequest = "";
          String msg = "Please review the following info:\n";
          boolean sendMail = true;
          for (int j = 0; j < requestV.size(); j++) {
            String currentRequest = (String)requestV.elementAt(j);
            msg += "Request: "+currentRequest;
            Vector requestStatusV = (Vector) innerH.get("REQUEST_STATUS");
            Vector requestDateV = (Vector) innerH.get("REQUEST_DATES");
            Vector processV = (Vector) innerH.get("PROCESS_IDS");
            if (lastRequest.equalsIgnoreCase(currentRequest)) {
              msg += " - Process: "+(String)processV.elementAt(j)+"\n";
            }else {
              //first check to see whether this request was approved
              if ("9".equalsIgnoreCase((String) requestStatusV.elementAt(j)) || "12".equalsIgnoreCase((String) requestStatusV.elementAt(j))) {
                //if current request is approved before the deleted/rejected request then ignore (break and check another request from list)
                if (HelpObjs.countQuery(db,"select count(*) from catalog_add_approval where approval_date < to_date("+(String)requestDateV.elementAt(j)+", 'MM/DD/YYYY') and request_id = "+currentRequest) > 0) {
                  sendMail = false;
                  break;
                }
              }
              msg += " - Process: "+(String)processV.elementAt(j)+"\n";
              //reset catalog_status(catalog_add_request_new) to draft
              db.doUpdate("update catalog_add_request_new set request_status = starting_view where request_id = "+currentRequest);
            }
            lastRequest = currentRequest;
          }
          //Don't need to notify anyone coz related requests approved before the deleted/rejected request was requested.
          if (sendMail) {
            msg += "\n\nBecause request: "+requestID+" also modified the above process(es) and it was deleted/rejected.";
            HelpObjs.sendMail(db,"Please review your request(s)",msg,(new Integer(k)).intValue(),false);
          }
        }
        //if process(es) on current request does not on any other open request then remove lock
        db.doUpdate("delete from cat_add_process_lock where locked_by_user_id = "+userID+
                    " and process_id in (select process_id from ca_process where fx_process_multiple(facility_id,application,process_id) = 'N' and request_id = "+requestID+")"+
                    " and facility_id = '"+facID+"' and application in ("+waIDs+") and lock_level = 'DRAFT'");
      }catch (Exception e) {
       e.printStackTrace();
       HelpObjs.sendMail(db,"Unable to reset other request(s)","Request ID: "+requestID,86030,false);
     }finally {
       dbrs.close();
     }
   } //end of method

   public Hashtable goRequestProcessChange(Hashtable inData) {
     Hashtable result = new Hashtable(2);
     String facID = (String) inData.get("FACILITY_ID");
     String waID = (String) inData.get("WORK_AREA_ID");
     String viewLevel = (String) inData.get("VIEW_LEVEL");
     Integer userID = (Integer) inData.get("USER_ID");
     Hashtable h = (Hashtable) inData.get("PROCESS");
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       //System.out.println("-------- here in request process lock!!!!!!");
       Enumeration enuma;
       String query = "select process_id,nvl(to_char(locked_by_user_id),' ') locked_by_user_id,nvl(to_char(locked_by_approver_id),' ') locked_by_approver_id,"+
                      " c.last_name||' '||c.first_name user_name,nvl(c.phone,' ') user_phone,nvl(c.email,' ') user_email,"+
                      " d.last_name||' '||d.first_name approver_name,nvl(d.phone,' ') approver_phone,nvl(d.email,' ') approver_email"+
                      " from cat_add_process_lock a,personnel c, personnel d where a.facility_id = '"+facID+"' and a.application = '"+waID+"'"+
                      " and a.locked_by_user_id = c.personnel_id(+) and a.locked_by_approver_id = d.personnel_id(+) and a.process_id in (";
       int count = 0;
       String val = "";
       for (enuma = h.keys(); enuma.hasMoreElements();) {
         Integer key = (Integer) enuma.nextElement();
         val += (String)h.get(key)+",";
         count++;
       }
       //replace the last commas with close parathesis
       if (val.length() > 0) {
         query += val.substring(0,val.length()-1)+")";
       }
       //get locked process
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       Vector lockedProcess = new Vector(count);
       Vector lockedUser = new Vector(count);
       Vector lockedUserName = new Vector(count);
       Vector lockedUserPhone = new Vector(count);
       Vector lockedUserEmail = new Vector(count);
       Vector lockedApprover =new Vector(count);
       Vector lockedApproverName =new Vector(count);
       Vector lockedApproverPhone =new Vector(count);
       Vector lockedApproverEmail =new Vector(count);
       while (rs.next()){
         lockedProcess.addElement(rs.getString("process_id"));
         lockedUser.addElement(rs.getString("locked_by_user_id").trim());
         lockedUserName.addElement(rs.getString("user_name").trim());
         lockedUserPhone.addElement(rs.getString("user_phone").trim());
         lockedUserEmail.addElement(rs.getString("user_email").trim());
         lockedApprover.addElement(rs.getString("locked_by_approver_id").trim());
         lockedApproverName.addElement(rs.getString("approver_name").trim());
         lockedApproverPhone.addElement(rs.getString("approver_phone").trim());
         lockedApproverEmail.addElement(rs.getString("approver_email").trim());
       }
       //determine whether user can edit process or not
       val = "";
       //System.out.println("------------ view level: "+viewLevel+"-"+h);
       for (enuma = h.keys(); enuma.hasMoreElements();) {
         Hashtable innerH = new Hashtable(3);
         Integer key = (Integer) enuma.nextElement();
         val = (String)h.get(key);
         if ("Requestor".equalsIgnoreCase(viewLevel)) {
           if (lockedProcess.contains(val)) {  //if current process is in the locked process list
             String tmpUser = (String)lockedUser.elementAt(lockedProcess.indexOf(val));
             String tmpApprover = (String)lockedApprover.elementAt(lockedProcess.indexOf(val));
             //if approver lock current process and that approver is me
             if (tmpApprover.equals(userID.toString())) {
               innerH.put("EDITABLE","Editable");
               innerH.put("PROCESS_ID",val);
               innerH.put("PROCESS_LOCK_INFO","");
               h.put(key,innerH);
             }else if (!tmpApprover.equals(userID.toString()) && tmpApprover.length() > 1) {  //if approver lock current process and that approver is not me
               innerH.put("EDITABLE","Locked");
               innerH.put("PROCESS_ID",val);
               innerH.put("PROCESS_LOCK_INFO","This request is locked by "+
                          (String)lockedApproverName.elementAt(lockedProcess.indexOf(val))+"\n"+
                          (String)lockedApproverPhone.elementAt(lockedProcess.indexOf(val))+"\n"+
                          (String)lockedApproverEmail.elementAt(lockedProcess.indexOf(val)));
               h.put(key,innerH);
             }else if (!tmpUser.equals(userID.toString())) {   //else if another user lock current process
               innerH.put("EDITABLE","Locked");
               innerH.put("PROCESS_ID",val);
               innerH.put("PROCESS_LOCK_INFO","This request is locked by "+
                          (String)lockedUserName.elementAt(lockedProcess.indexOf(val))+"\n"+
                          (String)lockedUserPhone.elementAt(lockedProcess.indexOf(val))+"\n"+
                          (String)lockedUserEmail.elementAt(lockedProcess.indexOf(val)));
               h.put(key,innerH);
             }else {   //else I am the one with the lock of the current process
               innerH.put("EDITABLE","Editable");
               innerH.put("PROCESS_ID",val);
               innerH.put("PROCESS_LOCK_INFO","");
               h.put(key,innerH);
             }
           }else {   //current process is open for anyone to change
             db.doUpdate("insert into cat_add_process_lock (facility_id,application,process_id,locked_by_user_id,date_locked_by_user,lock_level)"+
                         " values('"+facID+"','"+waID+"','"+val+"',"+userID+",sysdate,'DRAFT')");
             innerH.put("EDITABLE","Editable");
             innerH.put("PROCESS_ID",val);
             innerH.put("PROCESS_LOCK_INFO","");
             h.put(key,innerH);
           }
         }else if ("Approver".equalsIgnoreCase(viewLevel) || "Super Approver".equalsIgnoreCase(viewLevel) ||
                   "New Chem Approver".equalsIgnoreCase(viewLevel) || "Eng Eval Approver".equalsIgnoreCase(viewLevel)) {
           //System.out.println("-------- lock data: "+lockedProcess);
           if (lockedProcess.contains(val)) {  //if current process is in the locked process list
             String tmpVal = (String)lockedApprover.elementAt(lockedProcess.indexOf(val));
             if (tmpVal.equals(userID.toString()) || tmpVal.length() < 1) {  //if no approver is working on the current process then I can
               db.doUpdate("update cat_add_process_lock set locked_by_approver_id = "+userID+",date_locked_by_approver = sysdate where"+
                           " facility_id = '"+facID+"' and application = '"+waID+"' and process_id = '"+val+"'");
               innerH.put("EDITABLE","Editable");
               innerH.put("PROCESS_ID",val);
               innerH.put("PROCESS_LOCK_INFO","");
               h.put(key,innerH);
             }else {  //another approver is working on the current process
               innerH.put("EDITABLE","Locked");
               innerH.put("PROCESS_ID",val);
               innerH.put("PROCESS_LOCK_INFO","This request is locked by "+
                          (String)lockedApproverName.elementAt(lockedProcess.indexOf(val))+"\n"+
                          (String)lockedApproverPhone.elementAt(lockedProcess.indexOf(val))+"\n"+
                          (String)lockedApproverEmail.elementAt(lockedProcess.indexOf(val)));
               h.put(key,innerH);
             }
           }else {  //current process is open for anyone to change
             db.doUpdate("insert into cat_add_process_lock (facility_id,application,process_id,locked_by_user_id,date_locked_by_user,locked_by_approver_id,date_locked_by_approver,lock_level)"+
                         " values('"+facID+"','"+waID+"','"+val+"',"+userID+",sysdate,"+userID+",sysdate,'SUBMITTED')");
             innerH.put("EDITABLE","Editable");
             innerH.put("PROCESS_ID",val);
             innerH.put("PROCESS_LOCK_INFO","");
             h.put(key,innerH);
           }
         }else {  //SHOULDN'T GET HERE BUT JUST INCASE - LOCK ALL PROCESS
           innerH.put("EDITABLE","Locked");
           innerH.put("PROCESS_ID",val);
           innerH.put("PROCESS_LOCK_INFO","");
           h.put(key,innerH);
         }
       }
       result.put("DATA",h);
       result.put("MSG","OK");
     }catch (Exception e) {
       e.printStackTrace();
       result.put("MSG","tcmIS encountered a problem while trying to reset work area lock.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918");
       HelpObjs.sendMail(db,"Unable to reset work area lock","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
     }finally {
       dbrs.close();
     }
     return result;
   } //end of method

   public String goRemoveProcessLock(Hashtable inData) {
     String result = "OK";
     String facID = (String) inData.get("FACILITY_ID");
     String waID = (String) inData.get("WORK_AREA_ID");
     Integer userID = (Integer) inData.get("USER_ID");
     String requestID = (String) inData.get("REQUEST_ID");
     Vector editableProcess = (Vector) inData.get("EDITABLE_PROCESS");
     String viewLevel = (String) inData.get("VIEW_LEVEL");
     try {
       String processIDs = "(";
       for (int i = 0; i < editableProcess.size(); i++) {
         processIDs += (String)editableProcess.elementAt(i)+",";
       }
       //replace the last commas with )
       if (processIDs.length() > 2) {
         processIDs = processIDs.substring(0,processIDs.length()-1)+")";
       }else {
         processIDs += "-1)";
       }
       if ("Requestor".equalsIgnoreCase(viewLevel)) {
         //remove process lock
         /*
         db.doUpdate("delete from cat_add_process_lock where facility_id = '"+facID+"' and application = '"+waID+"' and locked_by_user_id = "+userID+
                     " and lock_level = 'DRAFT' and process_id in "+processIDs);
         */
        db.doUpdate("delete from cat_add_process_lock where locked_by_user_id = "+userID+
                   " and process_id in (select process_id from ca_process where fx_process_multiple(facility_id,application,process_id) = 'N' and request_id = "+requestID+")"+
                   " and facility_id = '"+facID+"' and application = '"+waID+"' and lock_level = 'DRAFT'");
         //remove ca_process* data
         Vector args = new Vector(5);
         args.addElement(requestID);
         args.addElement(facID);
         args.addElement(waID);
         args.addElement("");
         args.addElement("error_val");
         String error = db.doProcedureWithErrorMsg("p_ca_process_delete", args,5);
         if (!"OK".equalsIgnoreCase(error)) {
           result = "tcmIS encountered a problem while trying to remove work area lock.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918";
           HelpObjs.sendMail(db, "Unable to delete ca_process* data","Facility: " + facID + "\nWork area: " + waID +"\nRequestor: " + userID.toString() + "\nError:" +error, 86030, false);
           return result;
         }
       }else if ("Approver".equalsIgnoreCase(viewLevel) || "Super Approver".equalsIgnoreCase(viewLevel) ||
                 "New Chem Approver".equalsIgnoreCase(viewLevel) || "Eng Eval Approver".equalsIgnoreCase(viewLevel)) {
         db.doUpdate("update cat_add_process_lock set locked_by_approver_id = null, date_locked_by_approver = null where"+
                     " facility_id = '"+facID+"' and application = '"+waID+"' and locked_by_approver_id = "+userID+" and process_id in "+processIDs);
       }
     }catch (Exception e) {
       e.printStackTrace();
       result = "tcmIS encountered a problem while trying to remove work area lock.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918";
       HelpObjs.sendMail(db,"Unable to remove work area lock","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
     }
     return result;
   } //end of method

   public String goResetProcessLock(Hashtable inData) {
     String result = "OK";
     String facID = (String) inData.get("FACILITY_ID");
     String waID = (String) inData.get("WORK_AREA_ID");
     Integer userID = (Integer) inData.get("USER_ID");
     Vector editableProcess = (Vector) inData.get("EDITABLE_PROCESS");
     String viewLevel = (String) inData.get("VIEW_LEVEL");
     try {
       //reset user/approver time stamp
       String query = "update cat_add_process_lock set";
       String where = " where facility_id = '"+facID+"' and application = '"+waID+"' and";
       if ("Requestor".equalsIgnoreCase(viewLevel)) {
         query += " date_locked_by_user = sysdate";
         where += " locked_by_user_id = "+userID.intValue();
       }else if ("Approver".equalsIgnoreCase(viewLevel) || "Super Approver".equalsIgnoreCase(viewLevel) ||
                 "New Chem Approver".equalsIgnoreCase(viewLevel) || "Eng Eval Approver".equalsIgnoreCase(viewLevel)) {
         query += " date_locked_by_approver = sysdate";
         where += " locked_by_approver_id = "+userID.intValue();
       }else {
         query += " date_locked_by_user = sysdate";
         where += " locked_by_user_id = "+userID.intValue();
       }
       where += " and process_id in (";
       for (int i = 0; i < editableProcess.size(); i++) {
         where += (String) editableProcess.elementAt(i)+",";
       }
       //replace the last commas with close parathensis
       where = where.substring(0,where.length()-1)+ ")";
       db.doUpdate(query+where);
       //System.out.println("-------- here in reset process lock!!!!!!");
     }catch (Exception e) {
       e.printStackTrace();
       result = "tcmIS encountered a problem while trying to reset process lock.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918";
       HelpObjs.sendMail(db,"Unable to reset process lock","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
     }
     return result;
   } //end of method

   public String goSaveProcessDetail(Hashtable inData) {
     String result = "OK";
     String facID = (String) inData.get("FACILITY_ID");
     String waID = (String) inData.get("WORK_AREA_ID");
     Integer userID = (Integer) inData.get("USER_ID");
     String requestID = (String) inData.get("REQUEST_ID");
     Hashtable dataH = (Hashtable) inData.get("CURRENT_DATA");
     Vector processIDV = (Vector)dataH.get("PROCESS_ID");
     Vector editableProcess = (Vector) inData.get("EDITABLE_PROCESS");
     String viewLevel = (String) inData.get("VIEW_LEVEL");
     String timerAction = (String) inData.get("TIMER_ACTION");
     try {
       //Rather than keeping track of all process for this request.  I will only
       //store process that requestor/approver requested to change
       //for (int i = 0; i < processIDV.size(); i++) {
       //String tmpProcessID = (String) processIDV.elementAt(i);
       for (int ij = 0; ij < editableProcess.size(); ij++ ) {
         String tmpProcessID = (String) editableProcess.elementAt(ij);
         if (dataH.containsKey(tmpProcessID)) {
           //first check to see if process is not lock, this means that requestor/approver just created this process(es)
           if (HelpObjs.countQuery(db,"select count(*) from cat_add_process_lock where facility_id = '"+facID+"' and application = '"+waID+"' and process_id = '"+tmpProcessID+"'") < 1) {
             if ("Requestor".equalsIgnoreCase(viewLevel)) {
               db.doUpdate("insert into cat_add_process_lock (facility_id,application,process_id,locked_by_user_id,date_locked_by_user,lock_level)"+
                           " values('"+facID+"','"+waID+"','"+tmpProcessID+"',"+userID+",sysdate,'DRAFT')");
             }else if ("Approver".equalsIgnoreCase(viewLevel) || "Super Approver".equalsIgnoreCase(viewLevel) ||
                       "New Chem Approver".equalsIgnoreCase(viewLevel) || "Eng Eval Approver".equalsIgnoreCase(viewLevel)) {
               db.doUpdate("insert into cat_add_process_lock (facility_id,application,process_id,locked_by_user_id,date_locked_by_user,lock_level)"+
                           " values('"+facID+"','"+waID+"','"+tmpProcessID+"',"+userID+",sysdate,'SUBMITTED')");
             }
           }
           //next delete ca_process_*
           Vector args = new Vector(5);
           args.addElement(requestID);
           args.addElement(facID);
           args.addElement(waID);
           args.addElement(tmpProcessID);
           args.addElement("error_val");
           String msg = db.doProcedureWithErrorMsg("p_ca_process_delete",args,5);
           if (!"OK".equalsIgnoreCase(msg)) {
             result = "tcmIS encountered a problem while trying to save data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918";
             HelpObjs.sendMail(db,"Procedure failed p_ca_process_delete","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString()+"\nError: "+msg,86030,false);
             return result;
           }
           //Now save user's data
           Hashtable h = (Hashtable) dataH.get(tmpProcessID);
           Vector v;
           int j = 0;
           String query = "";
           String values = "";
           Object[] oa;
           String tmpVal = "";
           String tmpVal2 = "";
           String tmpVal3 = "";
           String tmpVal4 = "";
           String tmpVal5 = "";
           String tmpVal6 = "";
           String tmpVal7 = "";
           String tmpVal8 = "";
           String tmpVal9 = "";
           String tmpVal10 = "";
           String tmpVal11 = "";
           String tmpVal12 = "";
           String tmpVal13 = "";
           String tmpVal14 = "";
           // update ca_process
           v = (Vector) h.get("OPERATION_DEFINITION");
           for (j = 0; j < v.size(); j++) {
             query = "insert into ca_process (request_id,facility_id,application,process_id";
             values = " values("+requestID+",'"+facID+"','"+waID+"',"+tmpProcessID;
             Hashtable innerH = (Hashtable) v.elementAt(j);
             tmpVal = (String) innerH.get("PROCESS_ID");
             tmpVal4 = (String) innerH.get("PROCESS_STATUS");
             tmpVal5 = (String) innerH.get("PROCESS_STATION_TYPE");
             tmpVal6 = (String) innerH.get("PROCESS_DISCHARGE_PATH");
             tmpVal7 = (String) innerH.get("OPERATING_FREQUENCY");
             tmpVal8 = (String) innerH.get("OPERATING_FREQUENCY_UNIT");
             tmpVal9 = (String) innerH.get("OPERATING_TIME");
             tmpVal10 = (String) innerH.get("OPERATING_TIME_UNIT");
             tmpVal2 = (String) innerH.get("PROCESS_NAME");
             tmpVal3 = (String) innerH.get("PROCESS_DESC");
             tmpVal14 = (String) innerH.get("PROCESS_NOTE");
             tmpVal11 = (String) innerH.get("PPE_COMMENTS");
             tmpVal12 = (String) innerH.get("WASTE_COMMENTS");
             tmpVal13 = (String) innerH.get("TRAINING_COMMENTS");
             //first check to make sure that process_station_type and process_discharge_path
             //exits - process_type and process_discharge_path
             if (HelpObjs.countQuery(db,"select count(*) from process_type where facility_id = '"+facID+"' and process_station_type = '"+tmpVal5+"'") < 1) {
               db.doUpdate("insert into process_type (facility_id,process_station_type) values('"+facID+"','"+tmpVal5+"')");
             }
             if (HelpObjs.countQuery(db,"select count(*) from process_discharge_path where facility_id = '"+facID+"' and process_discharge_path = '"+tmpVal6+"'") < 1) {
               db.doUpdate("insert into process_discharge_path (facility_id,process_discharge_path) values('"+facID+"','"+tmpVal6+"')");
             }

             if (!BothHelpObjs.isBlankString(tmpVal4)) {
               query += ",process_status";
               values += ",'"+tmpVal4+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal5)) {
               query += ",process_station_type";
               values += ",'"+tmpVal5+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal6)) {
               query += ",process_discharge_path";
               values += ",'"+tmpVal6+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal7)) {
               query += ",operating_frequency";
               values += ","+tmpVal7;
             }
             if (!BothHelpObjs.isBlankString(tmpVal8)) {
               query += ",operating_frequency_unit";
               values += ",'"+tmpVal8+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal9)) {
               query += ",operating_time";
               values += ","+tmpVal9;
             }
             if (!BothHelpObjs.isBlankString(tmpVal10)) {
               query += ",operating_time_unit";
               values += ",'"+tmpVal10+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal2)) {
               query += ",process_name";
               values += ",'"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tmpVal2)+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal3)) {
               query += ",process_description";
               values += ",'"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tmpVal3)+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal14)) {
               query += ",process_note";
               values += ",'"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tmpVal14)+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal11)) {
               query += ",ppe_comments";
               values += ",'"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tmpVal11)+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal12)) {
               query += ",waste_other_desc";
               values += ",'"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tmpVal12)+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal13)) {
               query += ",training_other_comments";
               values += ",'"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tmpVal13)+"'";
             }
             query += ")";
             values += ")";
             db.doUpdate(query+values);
             //check process_type and process_discharge to see whether user adds a new entry
             if (HelpObjs.countQuery(db,"select count(*) from process_type where facility_id = '"+facID+"' and process_station_type = '"+tmpVal5+"'") < 1) {
               db.doUpdate("insert into process_type(facility_id,process_status_type) values('"+facID+"','"+tmpVal5+"')");
             }
             if (HelpObjs.countQuery(db,"select count(*) from process_discharge_path where facility_id = '"+facID+"' and process_discharge_path = '"+tmpVal6+"'") < 1) {
               db.doUpdate("insert into process_type(facility_id,process_discharge_path) values('"+facID+"','"+tmpVal6+"')");
             }
           } //end of updating ca_process
           v = null;
           // update ca_process_emission_point
           v = (Vector) h.get("EMISSION_POINT");
           for (j = 0; j < v.size(); j++) {
             oa = (Object[]) v.elementAt(j);
             tmpVal = (String)oa[0];
             tmpVal2 = (String)oa[1];
             tmpVal3 = BothHelpObjs.makeBlankFromNull((String)oa[4]);
             db.doUpdate("insert into ca_process_emission_point(request_id,facility_id,application,process_id,fac_emission_point,app_emission_point,process_emission_percentage)"+
                         " values("+requestID+",'"+facID+"','"+waID+"',"+tmpProcessID+",'"+tmpVal2+"','"+tmpVal+"','"+tmpVal3+"')");
           }
           v = null;
           // update ca_process_part_usage
           v = (Vector) h.get("MATERIAL_USED");
           for (j = 0; j < v.size(); j++) {
             query = "insert into ca_process_part_usage (request_id,facility_id,application,process_id";
             values = " values("+requestID+",'"+facID+"','"+waID+"',"+tmpProcessID;
             oa = (Object[]) v.elementAt(j);
             tmpVal6 = BothHelpObjs.makeBlankFromNull((String)oa[10]);           //catalog
             tmpVal = (String)oa[0];                                            //cat_part_no
             tmpVal2 = (String)oa[9];                                           //part_group_no
             tmpVal3 = BothHelpObjs.makeBlankFromNull((String)oa[11]);          //item_id
             tmpVal14 = BothHelpObjs.makeBlankFromNull((String)oa[13]);          //part_id
             tmpVal4 = BothHelpObjs.makeBlankFromNull((String)oa[5]).trim();    //projected_qty
             tmpVal13 = BothHelpObjs.makeBlankFromNull((String)oa[6]).trim();   //projected_unit
             tmpVal5 = BothHelpObjs.makeBlankFromNull((String)oa[7]).trim();    //percentage
             if (!BothHelpObjs.isBlankString(tmpVal6)) {
               query += ",catalog_id";
               values += ",'"+tmpVal6+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal)) {
               query += ",cat_part_no";
               values += ",'"+tmpVal+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal2)) {
               query += ",part_group_no";
               values += ","+tmpVal2;
             }
             if (!BothHelpObjs.isBlankString(tmpVal14)) {
               query += ",part_id";
               values += ","+tmpVal14;
             }
             if (!BothHelpObjs.isBlankString(tmpVal3)) {
               query += ",example_item_id";
               values += ","+tmpVal3;
             }
             if (!BothHelpObjs.isBlankString(tmpVal4)) {
               query += ",projected_quantity_per_cycle";
               values += ","+tmpVal4;
             }
             if (!BothHelpObjs.isBlankString(tmpVal13)) {
               query += ",projected_size_unit";
               values += ",'"+tmpVal13+"'";
             }
             if (!BothHelpObjs.isBlankString(tmpVal5)) {
               query += ",percentage_emitted";
               values += ","+tmpVal5;
             }
             query += ")";
             values += ")";
             db.doUpdate(query+values);
           }
           v = null;
           //update ca_process_waste_stream
           v = (Vector) h.get("WASTE");
           for (j = 0; j < v.size(); j++) {
             oa = (Object[]) v.elementAt(j);
             tmpVal = (String)oa[0];
             tmpVal2 = (String) oa[2];
             tmpVal3 = BothHelpObjs.makeBlankFromNull((String)oa[3]);
             db.doUpdate("insert into ca_process_waste_stream(request_id,facility_id,application,process_id,waste_id,company_waste_code,waste_container_id)"+
                         " values("+requestID+",'"+facID+"','"+waID+"',"+tmpProcessID+","+tmpVal+",'"+tmpVal3+"',"+tmpVal2+")");
           }
           v = null;
           // update ca_process_training
           v = (Vector) h.get("TRAINING");
           for (j = 0; j < v.size(); j++) {
             oa = (Object[]) v.elementAt(j);
             tmpVal = (String)oa[0];
             db.doUpdate("insert into ca_process_training(request_id,facility_id,application,process_id,training_id)"+
                         " values("+requestID+",'"+facID+"','"+waID+"',"+tmpProcessID+",'"+tmpVal+"')");
           }
           v = null;
           //update ca_process_ppe
           v = (Vector) h.get("PPE");
           for (j = 0; j < v.size(); j++) {
             oa = (Object[]) v.elementAt(j);
             tmpVal = (String)oa[0];
             db.doUpdate("insert into ca_process_ppe(request_id,facility_id,application,process_id,personal_protective_equipment)"+
                         " values("+requestID+",'"+facID+"','"+waID+"',"+tmpProcessID+",'"+tmpVal+"')");
           }
           v = null;
         } //process is not in data structure
         //NOW update other request(s) for editable process
         Vector args = new Vector(6);
         args.addElement(requestID);
         args.addElement(facID);
         args.addElement(waID);
         args.addElement((String) editableProcess.elementAt(ij));
         args.addElement(null);
         args.addElement("error_val");
         String error = db.doProcedureWithErrorMsg("p_ca_process_update", args,6);
         if (!"OK".equalsIgnoreCase(error) && error != null) {
           result = "tcmIS encountered a problem while trying to save data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918";
           HelpObjs.sendMail(db, "Unable to update ca_process* data from current request","Facility: " + facID + "\nWork area: " + waID +"\nRequestor: " + userID.toString() + "\nError:" +error, 86030, false);
           return result;
         }
         //if approver save data then reset timer if required
         if ("Approver".equalsIgnoreCase(viewLevel) || "Super Approver".equalsIgnoreCase(viewLevel) ||
             "New Chem Approver".equalsIgnoreCase(viewLevel) || "Eng Eval Approver".equalsIgnoreCase(viewLevel)) {
           if ("SaveNClose".equalsIgnoreCase(timerAction)) {
             //remove approver lock
             db.doUpdate("update cat_add_process_lock set locked_by_approver_id = null,date_locked_by_approver = null where facility_id = '"+facID+"'"+
                         " and application = '"+waID+"' and process_id = "+tmpProcessID+" and locked_by_approver_id = "+userID);
           }else {
             //reset approver lock
             db.doUpdate("update cat_add_process_lock set date_locked_by_approver = sysdate where facility_id = '"+facID+"'"+
                         " and application = '"+waID+"' and process_id = "+tmpProcessID+" and locked_by_approver_id = "+userID);
           }
         }
       } //finish updating process info
     }catch (Exception e) {
       e.printStackTrace();
       result = "tcmIS encountered a problem while trying to save data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918";
       HelpObjs.sendMail(db,"Unable to Save Data","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
     }
     return result;
   }

   public Hashtable getPPEEditInfo(Hashtable inData) {
     Hashtable result = new Hashtable(5);
     String facID = (String) inData.get("FACILITY_ID");
     String waID = (String) inData.get("WORK_AREA");
     Integer userID = (Integer) inData.get("USER_ID");
     String processID = (String) inData.get("PROCESS_ID");
     String[] colHeaders = {"Process ID","Process","PPE","Personal Protective Equipment","Selected"};
     int[] colWidths = {0,140,0,305,0};
     int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING};
     Vector dataV = new Vector(20);
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select pe.personal_protective_equipment ppe,nvl(pe.ppe_description,' ') ppe_desc"+
                      " from vv_ppe pe order by ppe_description";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          Object[] oa = new Object[colHeaders.length];
          int i = 0;
          oa[i++] = "";
          oa[i++] = "";
          oa[i++] = rs.getString("ppe").trim();
          oa[i++] = rs.getString("ppe_desc").trim();
          oa[i++] = new Boolean(false);
          dataV.addElement(oa);
        }
     }catch (Exception e) {
       e.printStackTrace();
       result.put("MSG","tcmIS encountered a problem while trying to load data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918");
       HelpObjs.sendMail(db,"Unable to load PPE Data","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
     }finally {
       dbrs.close();
     }
     result.put("MSG","OK");
     result.put("TABLE_HEADERS",colHeaders);
     result.put("TABLE_WIDTHS",colWidths);
     result.put("TABLE_TYPES",colTypes);
     result.put("TABLE_DATA",dataV);
     return result;
   } //end of method

   public Hashtable getTrainingEditInfo(Hashtable inData) {
     Hashtable result = new Hashtable(5);
     String facID = (String) inData.get("FACILITY_ID");
     String waID = (String) inData.get("WORK_AREA");
     Integer userID = (Integer) inData.get("USER_ID");
     String processID = (String) inData.get("PROCESS_ID");
     String[] colHeaders = {"Process ID","Process","Training","Required Training(s)","Selected"};
     int[] colWidths = {0,140,0,320,0};
     int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING};
     Vector dataV = new Vector(20);
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
        String query = "select nvl(t.training_id,' ') training_id,nvl(t.training_desc,' ') training_desc"+
                      " from training t where t.facility_id = '"+facID+"' order by training_desc";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          Object[] oa = new Object[colHeaders.length];
          int i = 0;
          oa[i++] = "";
          oa[i++] = "";
          oa[i++] = rs.getString("training_id").trim();
          oa[i++] = rs.getString("training_desc").trim();
          oa[i++] = new Boolean(false);
          dataV.addElement(oa);
        }
     }catch (Exception e) {
       e.printStackTrace();
       result.put("MSG","tcmIS encountered a problem while trying to load data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918");
       HelpObjs.sendMail(db,"Unable to load Training Data","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
     }finally {
       dbrs.close();
     }
     result.put("MSG","OK");
     result.put("TABLE_HEADERS",colHeaders);
     result.put("TABLE_WIDTHS",colWidths);
     result.put("TABLE_TYPES",colTypes);
     result.put("TABLE_DATA",dataV);
     return result;
   } //end of method

   public Hashtable getWasteStreamEditInfo(Hashtable inData) {
     Hashtable result = new Hashtable(5);
     String facID = (String) inData.get("FACILITY_ID");
     String waID = (String) inData.get("WORK_AREA");
     Integer userID = (Integer) inData.get("USER_ID");
     String processID = (String) inData.get("PROCESS_ID");
     String requestID = (String) inData.get("REQUEST_ID");
     String[] colHeaders = {"Process ID","Process","Waste ID","Waste Stream","Container","Waste Code","Selected"};
     int[] colWidths = {0,140,0,325,0,0,0};
     int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING};
     Vector dataV = new Vector(20);
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
        String query = "select nvl(to_char(fw.waste_id),' ') waste_id,nvl(fw.waste_desc,' ') waste_desc"+
                       " from vv_facility_waste fw"+
                       " where fw.facility_id = '"+facID+"' order by waste_desc";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          Object[] oa = new Object[colHeaders.length];
          int i = 0;
          oa[i++] = "";
          oa[i++] = "";
          oa[i++] = rs.getString("waste_id").trim();
          oa[i++] = rs.getString("waste_desc").trim();
          oa[i++] = "";
          oa[i++] = "";
          oa[i++] = new Boolean(false);
          dataV.addElement(oa);
        }
     }catch (Exception e) {
       e.printStackTrace();
       result.put("MSG","tcmIS encountered a problem while trying to load data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918");
       HelpObjs.sendMail(db,"Unable to load Waste Stream Data","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
     }finally {
       dbrs.close();
     }
     result.put("MSG","OK");
     result.put("TABLE_HEADERS",colHeaders);
     result.put("TABLE_WIDTHS",colWidths);
     result.put("TABLE_TYPES",colTypes);
     result.put("TABLE_DATA",dataV);
     return result;
   } //end of method

   public Hashtable getMaterialUsedEditInfo(Hashtable inData) {
     Hashtable result = new Hashtable(5);
     String facID = (String) inData.get("FACILITY_ID");
     String waID = (String) inData.get("WORK_AREA");
     Integer userID = (Integer) inData.get("USER_ID");
     String processID = (String) inData.get("PROCESS_ID");
     String requestID = (String) inData.get("REQUEST_ID");
     String[] colHeaders = {"Process ID","Process","Part #","Description","Material Desc","Packaging","Projected QTY Per Cycle","Projected Unit Per Cycle","Percent","Selected","Part Group No","Catalog","Item","Size Unit Options","Part","Color"};
     int[] colWidths = {0,140,100,225,100,225,0,0,0,0,0,0,0,0,0,0};
     int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING};

     Vector dataV = new Vector(20);
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select distinct x.*,decode(p.components_per_item,null,'',1,'',p.components_per_item||' x ') || p.part_size||' '||p.size_unit||' '||p.pkg_style packaging,"+
                      "nvl(fx_unit_conversion_option(p.size_unit,p.material_id),p.size_unit) size_unit_options,"+
                      "p.part_id, m.material_desc from part p,material m,(select catalog_id,cat_part_no,description,"+
                      "part_group_no,item_id from process_part_usage_edit_view"+
                      " where facility_id = '"+facID+"' and application = '"+waID+"'"+
                      " union all"+
                      " select  ca.catalog_id, ca.cat_part_no, nvl(nvl(fi.part_description, cai.material_desc),' ') description,"+
                      "nvl(to_char(ca.part_group_no),' ') part_group_no,nvl(to_char(ca.example_item_id),nvl(to_char(cai.item_id),' ')) item_id"+
                      " from ca_process_part_usage ca, fac_item fi, catalog_add_request_new carn, catalog_add_item cai, catalog_add_user_group caug,"+
                      "(select catalog_id,cat_part_no,material_desc,part_size,size_unit,pkg_style from catalog_add_item cai1 ,catalog_add_request_new carn1"+
                      " where cai1.part_id=1 and carn1.request_id=cai1.request_id and carn1.request_status not in (7,9,10,12)) cai"+
                      " where ca.catalog_id = fi.facility_id(+) and ca.cat_part_no = fi.fac_part_no(+) and ca.request_id = carn.request_id and"+
                      " ca.catalog_id = cai.catalog_id(+) and ca.cat_part_no = cai.cat_part_no(+) and ca.request_id = caug.request_id(+) and"+
                      " ca.facility_id = caug.facility_id(+) and ca.application = caug.work_area(+) and carn.request_status not in (7,9,10,12) and"+
			  				 " carn.company_id = cai.company_id and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 and"+
							 " ca.facility_id = '"+facID+"' and ca.application = '"+waID+"'"+
                      " union all"+
                      " select carn.catalog_id,nvl(carn.cat_part_no,'To Be Determined'||' - '||carn.request_id) cat_part_no,"+
                      "nvl(nvl(fi.part_description, cai.material_desc),' ') description,"+
                      "nvl(to_char(carn.part_group_no),' ') part_group_no,nvl(to_char(cai.item_id),' ') item_id"+
                      " from catalog_add_request_new carn,catalog_add_item cai, fac_item fi where carn.request_id = cai.request_id and cai.line_item = 1 and"+
                      " carn.catalog_id = fi.facility_id(+) and carn.cat_part_no = fi.fac_part_no(+) and carn.request_id = "+requestID+") x "+
                      " where x.item_id = p.item_id and p.material_id = m.material_id"+
                      " order by cat_part_no,part_id";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          Object[] oa = new Object[colHeaders.length];
          int i = 0;
          oa[i++] = "";
          oa[i++] = "";
          oa[i++] = rs.getString("cat_part_no").trim();
          oa[i++] = rs.getString("description").trim();
          oa[i++] = rs.getString("material_desc");
          oa[i++] = rs.getString("packaging").trim();
          oa[i++] = "";
          oa[i++] = "";
          oa[i++] = "";
          oa[i++] = new Boolean(false);
          oa[i++] = rs.getString("part_group_no").trim();
          oa[i++] = rs.getString("catalog_id").trim();
          oa[i++] = rs.getString("item_id").trim();
          oa[i++] = rs.getString("size_unit_options");
          oa[i++] = rs.getString("part_id");
          oa[i++] = "";                                     //place holder so I can decide if row is colored or not
          dataV.addElement(oa);
        }
     }catch (Exception e) {
       e.printStackTrace();
       result.put("MSG","tcmIS encountered a problem while trying to load data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918");
       HelpObjs.sendMail(db,"Unable to load Material Used Data","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
     }finally {
       dbrs.close();
     }
     result.put("MSG","OK");
     result.put("TABLE_HEADERS",colHeaders);
     result.put("TABLE_WIDTHS",colWidths);
     result.put("TABLE_TYPES",colTypes);
     result.put("TABLE_DATA",dataV);
     return result;
   } //end of method

   public Hashtable updateEmissionPoint(Hashtable inData) {
     Hashtable result = new Hashtable(2);
     String facID = (String) inData.get("FACILITY_ID");
     Integer userID = (Integer) inData.get("USER_ID");
     String emissionPtName = (String) inData.get("EMISSION_POINT_NAME");
     try {
       String query = "insert into emission_point (facility_id,emission_point_id,emission_point_name)"+
                      " values('"+facID+"',(select emission_point_seq.nextval),'"+emissionPtName+"')";
     } catch (Exception e) {
       e.printStackTrace();
       result.put("MSG","tcmIS encountered a problem while trying to update.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918");
       HelpObjs.sendMail(db,"Unable to insert Emission Point","Facility: "+facID+"\nRequestor: "+userID.toString()+"\nEmission point: "+emissionPtName,86030,false);
     }
     return result;
   }

   public Hashtable getEmissionPtEditInfo(Hashtable inData) {
     Hashtable result = new Hashtable(5);
     String facID = (String) inData.get("FACILITY_ID");
     String waID = (String) inData.get("WORK_AREA");
     Integer userID = (Integer) inData.get("USER_ID");
     String processID = (String) inData.get("PROCESS_ID");
     String[] colHeaders = {"Process ID","Process","Emission Name","Description","App","Fac","Percent","Selected"};
     int[] colWidths = {0,140,100,225,0,0,0,0};
     int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING,
                       BothHelpObjs.TABLE_COL_TYPE_STRING};
     Vector dataV = new Vector(20);
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select app_emission_point,fac_emission_point,app_emission_point||'-'||fac_emission_point emission_point_name,"+
                      "nvl(app_emission_point_desc,' ') emission_point_desc from fac_app_emission_point"+
                      " where facility_id = '"+facID+"' and application = '"+waID+"' order by app_emission_point_desc";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
          Object[] oa = new Object[colHeaders.length];
          int i = 0;
          oa[i++] = "";
          oa[i++] = "";
          oa[i++] = rs.getString("emission_point_name").trim();
          oa[i++] = rs.getString("emission_point_desc").trim();
          oa[i++] = rs.getString("app_emission_point").trim();
          oa[i++] = rs.getString("fac_emission_point").trim();
          oa[i++] = "";
          oa[i++] = new Boolean(false);
          dataV.addElement(oa);
        }
     }catch (Exception e) {
       e.printStackTrace();
       result.put("MSG","tcmIS encountered a problem while trying to load data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918");
       HelpObjs.sendMail(db,"Unable to load Emission Point Data","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
     }finally {
       dbrs.close();
     }
     result.put("MSG","OK");
     result.put("TABLE_HEADERS",colHeaders);
     result.put("TABLE_WIDTHS",colWidths);
     result.put("TABLE_TYPES",colTypes);
     result.put("TABLE_DATA",dataV);
     return result;
   } //end of method

   public Hashtable getProcessDetail (Hashtable inData) {
     Hashtable result = new Hashtable(20);
     String facID = (String)inData.get("FACILITY_ID");
     String waID = (String)inData.get("WORK_AREA_ID");
     Integer userID = (Integer)inData.get("USER_ID");
     String requestID = (String)inData.get("REQUEST_ID");
     String requestStatus = (String)inData.get("REQUEST_STATUS");
     String viewLevel = (String)inData.get("VIEW_LEVEL");
     DBResultSet dbrs = null;
     ResultSet rs = null;
     DBResultSet dbrs2 = null;
     ResultSet rs2 = null;
     try {
       //first find out if I have any process for this request
       String query = "select nvl(to_char(process_id),' ') process_id from ca_process where facility_id = '"+facID+"' and application = '"+waID+"' and request_id = "+requestID;
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       Vector tmpCatAddProcessIDV = new Vector();
       while (rs.next()){
         tmpCatAddProcessIDV.addElement(rs.getString("process_id"));
       }
       //now get process data
       /*
       query = "select nvl(p.facility_id,' ') facility_id, nvl(p.application,' ') application,"+
       "nvl(to_char(p.process_id),' ') process_id,"+
       "fx_process_multiple(p.facility_id,p.application,p.process_id) process_on_multiple_request,"+
       "nvl(p.process_status,' ') process_status, nvl(p.process_station_type,' ') process_station_type,"+
       "nvl(p.process_discharge_path,' ') process_discharge_path,"+
       "nvl(to_char(p.operating_frequency),' ') operating_frequency,"+
       "nvl(p.operating_frequency_unit,' ') operating_frequency_unit,"+
       "nvl(to_char(p.operating_time),' ') operating_time,"+
       "nvl(p.operating_time_unit,' ') operating_time_unit, nvl(p.process_name,' ') process_name,"+
       "nvl(p.process_description,' ') process_description,"+
       "nvl(p.process_note,' ') process_note,"+
       "nvl(p.fac_emission_point,' ') fac_emission_point, nvl(p.app_emission_point,' ') app_emission_point,"+
       "nvl(p.emission_point_name,' ') emission_point_name,"+
       "nvl(p.emission_point_desc,' ') emission_point_desc,"+
       "nvl(to_char(p.process_emission_percentage),' ') process_emission_percentage,"+
       "nvl(p.catalog_id,' ') catalog_id,"+
       "nvl(p.cat_part_no,' ') cat_part_no,"+
       "nvl(to_char(p.part_group_no),' ') part_group_no,"+
       "nvl(to_char(p.item_id),' ') item_id,"+
       "nvl(p.part_desc,' ') part_desc,"+
       "part_pkg,"+
       "nvl(to_char(p.projected_quantity_per_cycle),' ') projected_quantity_per_cycle,"+
       "nvl(to_char(p.percentage_emitted),' ') percentage_emitted,"+
       "nvl(to_char(p.waste_id),' ') waste_id,"+
       "nvl(to_char(p.company_waste_code),' ') company_waste_code,"+
       "nvl(to_char(p.waste_container_id),' ') waste_container_id,"+
       "nvl(p.waste_desc,' ') waste_desc,"+
       "nvl(p.training_id,' ') training_id,"+
       "nvl(p.training_desc,' ') training_desc,"+
       "nvl(p.ppe,' ') ppe, nvl(p.ppe_desc,' ') ppe_desc,"+
       "nvl(p.waste_other_desc,' ') waste_other_desc,"+
       "nvl(p.training_other_comments,' ') training_other_comments,"+
       "nvl(p.ppe_comments,' ') ppe_comments,nvl(to_char(p.locked_by_user_id),' ') locked_by_user_id,"+
       "nvl(to_char(p.locked_by_approver_id),' ') locked_by_approver_id, nvl(p.user_name,' ') user_name,"+
       "nvl(p.user_phone,' ') user_phone,nvl(p.user_email,' ') user_email, p.approver_name,"+
       "nvl(p.approver_phone,' ') approver_phone,nvl(p.approver_email,' ') approver_email"+
       " from process_edit_detail_view p"+
       */
       query = "select * from process_display_detail_view"+
       " where process_status <> 'Deleted' and facility_id = '"+facID+"' and application = '"+waID+"'"+
       " order by process_id,app_emission_point,fac_emission_point,cat_part_no,part_id,waste_id,training_id,ppe_desc";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       Vector processIDV = new Vector();
       String lastProcessID = "";
       String lastEmissionPointID = "";
       String lastMaterialUsed = "";
       String lastWasteStream = "";
       String lastTraining = "";
       String lastPPE = "";

       Vector tmpEmissionIDV = new Vector(0);
       Vector tmpMaterialUsedIDV = new Vector(0);
       Vector tmpWasteStreamIDV = new Vector(0);
       Vector tmpTrainingIDV = new Vector(0);
       Vector tmpPPEIDV = new Vector(0);

       while (rs.next()){
         String currentProcessID = rs.getString("process_id");
         if (lastProcessID.equals(currentProcessID)) {
           Hashtable h = (Hashtable)result.get(currentProcessID);
           //ship operation definition since there should only on row per process
           //next emission point
           Object[] oa = new Object[6];
           int i = 0;
           String currentValue = rs.getString("app_emission_point")+rs.getString("fac_emission_point");
           currentValue = currentValue.trim();
           if (currentValue.length() > 0 && !BothHelpObjs.isBlankString(currentValue)) {
             //if (!lastEmissionPointID.equals(currentValue)) {
             if (!tmpEmissionIDV.contains(currentValue)) {
               Vector emissionV = (Vector) h.get("EMISSION_POINT");
               oa[i++] = rs.getString("app_emission_point").trim();
               oa[i++] = rs.getString("fac_emission_point").trim();
               oa[i++] = rs.getString("emission_point_name").trim();
               oa[i++] = rs.getString("emission_point_desc").trim();
               oa[i++] = rs.getString("process_emission_percentage").trim();
               oa[i++] = "F";
               emissionV.addElement(oa);
               tmpEmissionIDV.addElement(currentValue);
             }
           }
           lastEmissionPointID = currentValue;
           //material used
           currentValue = rs.getString("catalog_id")+rs.getString("part_group_no")+rs.getString("cat_part_no")+rs.getString("part_id");
           if (currentValue.length() > 0 && !BothHelpObjs.isBlankString(currentValue)) {
             if (!tmpMaterialUsedIDV.contains(currentValue)) {
               Vector materialUsedV = (Vector) h.get("MATERIAL_USED");
               oa = new Object[14];
               i = 0;
               oa[i++] = rs.getString("cat_part_no").trim();
               oa[i++] = rs.getString("part_desc").trim();
               oa[i++] = "Material Desc";
               oa[i++] = BothHelpObjs.makeBlankFromNull(rs.getString("part_pkg"));
               oa[i++] = "";
               oa[i++] = rs.getString("projected_quantity_per_cycle").trim();
               oa[i++] = rs.getString("projected_size_unit");
               oa[i++] = rs.getString("percentage_emitted").trim();
               oa[i++] = "F";
               oa[i++] = rs.getString("part_group_no").trim();
               oa[i++] = rs.getString("catalog_id").trim();
               oa[i++] = rs.getString("item_id").trim();
               oa[i++] = rs.getString("size_unit_options");
               oa[i++] = rs.getString("part_id");
               materialUsedV.addElement(oa);
               tmpMaterialUsedIDV.addElement(currentValue);
             }
           }
           lastMaterialUsed = currentValue;
           //waste stream
           currentValue = rs.getString("waste_id").trim();
           if (currentValue.length() > 0 && !BothHelpObjs.isBlankString(currentValue)) {
             //if (!lastWasteStream.equals(currentValue)) {
             if (!tmpWasteStreamIDV.contains(currentValue)) {
               Vector wasteStreamV = (Vector) h.get("WASTE");
               oa = new Object[5];
               i = 0;
               oa[i++] = currentValue;
               oa[i++] = rs.getString("waste_desc").trim();
               oa[i++] = rs.getString("waste_container_id").trim();
               oa[i++] = rs.getString("company_waste_code").trim();
               oa[i++] = "F";
               wasteStreamV.addElement(oa);
               tmpWasteStreamIDV.addElement(currentValue);
             }
           }
           lastWasteStream = currentValue;
           //training
           currentValue = rs.getString("training_id").trim();
           if (currentValue.length() > 0 && !BothHelpObjs.isBlankString(currentValue)) {
             //if (!lastTraining.equals(currentValue)) {
             if (!tmpTrainingIDV.contains(currentValue)) {
               Vector trainingV = (Vector) h.get("TRAINING");
               oa = new Object[3];
               i = 0;
               oa[i++] = currentValue;
               oa[i++] = rs.getString("training_desc").trim();
               oa[i++] = "F";
               trainingV.addElement(oa);
               tmpTrainingIDV.addElement(currentValue);
             }
           }
           lastTraining = currentValue;
           //ppe
           currentValue = rs.getString("ppe").trim();
           if (currentValue.length() > 0 && !BothHelpObjs.isBlankString(currentValue)) {
             //if (!lastPPE.equals(currentValue)) {
             if (!tmpPPEIDV.contains(currentValue)) {
               Vector ppeV = (Vector) h.get("PPE");
               oa = new Object[3];
               i = 0;
               oa[i++] = currentValue;
               oa[i++] = rs.getString("ppe_desc").trim();
               oa[i++] = "F";
               ppeV.addElement(oa);
               tmpPPEIDV.addElement(currentValue);
             }
           }
           lastPPE = currentValue;
         }else {
           //temporary data
           tmpEmissionIDV = new Vector();
           tmpMaterialUsedIDV = new Vector();
           tmpWasteStreamIDV = new Vector();
           tmpTrainingIDV = new Vector();
           tmpPPEIDV = new Vector();

           processIDV.addElement(currentProcessID);
           Hashtable h = new Hashtable();
           Vector operationDefV = new Vector();
           //first fill in operation definition data
           Hashtable innerH = new Hashtable(19);
           innerH.put("PROCESS_ID",currentProcessID);
           innerH.put("PROCESS_STATUS",rs.getString("process_status").trim());
           String tmpUserL = rs.getString("locked_by_user_id").trim();
           String tmpApproverL = rs.getString("locked_by_approver_id").trim();
           String tmpLockInfo = "";
           String tmpLock = "";
           String processOnMultipleRequest = rs.getString("process_on_multiple_request");
           //if I am the one that lock the current process then check to see if any
           //approver has this process lock right now
           if (tmpUserL.equals(userID.toString())) {
             if (tmpApproverL.length() > 0 && !tmpApproverL.equalsIgnoreCase(userID.toString())) {
               tmpLockInfo = "This process is locked by "+rs.getString("approver_name")+"\n"+rs.getString("approver_phone")+"\n"+rs.getString("approver_email");
               tmpLock = "Locked";
             }else {
               if ("Requestor".equalsIgnoreCase(viewLevel) || "Approver".equalsIgnoreCase(viewLevel) || "Super Approver".equalsIgnoreCase(viewLevel) ||
                   "New Chem Approver".equalsIgnoreCase(viewLevel) || "Eng Eval Approver".equalsIgnoreCase(viewLevel)) {
                 if (tmpCatAddProcessIDV.contains(currentProcessID)) {
                   tmpLock = "Editable";
                   if ("Y".equalsIgnoreCase(processOnMultipleRequest)) {
                     tmpLockInfo = "Myself";
                   }else {
                     tmpLockInfo = "";
                   }
                 }else {
                   tmpLock = "";
                   tmpLockInfo = "Myself";
                 }
               }else {
                 tmpLock = "";
                 tmpLockInfo = "Myself";
               } //end of I am not a requestor or an approver
             }
           }else {  //I am not the one that locked the current process
             //if I am an approver and no other approver has this process lock
             if ("Approver".equalsIgnoreCase(viewLevel) || "Super Approver".equalsIgnoreCase(viewLevel) ||
                 "New Chem Approver".equalsIgnoreCase(viewLevel) || "Eng Eval Approver".equalsIgnoreCase(viewLevel)) {
               if (tmpApproverL.length() > 0 && !tmpApproverL.equalsIgnoreCase(userID.toString())) {
                 tmpLockInfo = "This process is locked by "+rs.getString("approver_name")+"\n"+rs.getString("approver_phone")+"\n"+rs.getString("approver_email");
                 tmpLock = "Locked";
               }else {
                 tmpLockInfo = "";
                 tmpLock = "";
               }
             }else {
               if (tmpUserL.length() > 0) {
                 tmpLockInfo = "This process is locked by "+rs.getString("user_name")+"\n"+rs.getString("user_phone")+"\n"+rs.getString("user_email");
                 tmpLock = "Locked";
               }
             }
           }
           innerH.put("PROCESS_LOCK",tmpLock);
           innerH.put("PROCESS_LOCK_INFO",tmpLockInfo);
           innerH.put("PROCESS_STATION_TYPE",rs.getString("process_station_type").trim());
           innerH.put("PROCESS_DISCHARGE_PATH",rs.getString("process_discharge_path").trim());
           innerH.put("OPERATING_FREQUENCY",rs.getString("operating_frequency").trim());
           innerH.put("OPERATING_FREQUENCY_UNIT",rs.getString("operating_frequency_unit").trim());
           innerH.put("OPERATING_TIME",rs.getString("operating_time").trim());
           innerH.put("OPERATING_TIME_UNIT",rs.getString("operating_time_unit").trim());
           innerH.put("PROCESS_NAME",rs.getString("process_name").trim());
           innerH.put("PROCESS_DESC",rs.getString("process_description").trim());
           innerH.put("PROCESS_NOTE",rs.getString("process_note").trim());
           innerH.put("NEW_PROCESS","F");
           innerH.put("PPE_COMMENTS",rs.getString("ppe_comments").trim());
           innerH.put("WASTE_COMMENTS",rs.getString("waste_other_desc").trim());
           innerH.put("TRAINING_COMMENTS",rs.getString("training_other_comments").trim());
           innerH.put("PROCESS_ON_MULTI_REQUEST",processOnMultipleRequest);
           Vector multipleRequestV = new Vector(30);
           if ("Y".equalsIgnoreCase(processOnMultipleRequest)) {
             try{
             query = "select carn.request_id,nvl(carn.cat_part_no,'To Be Determined'||' - '||carn.request_id) cat_part_no,"+
                     "nvl(fi.part_description,nvl(cai.material_desc,' ')) description,"+
                     "decode(cai.item_id,null,cai.part_size||' '||cai.size_unit||' '||cai.pkg_style,fx_kit_packaging(cai.item_id)) packaging"+
                     " from ca_process ca,catalog_add_request_new carn,catalog_add_item cai,fac_item fi"+
                     " where carn.cat_part_no = fi.fac_part_no(+) and carn.catalog_id = fi.facility_id(+) and ca.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1"+
                     " and ca.request_id = carn.request_id and carn.request_status not in (7,9,10,12) and ca.facility_id = '"+facID+"' and ca.application = '"+waID+"'"+
                     " and ca.request_id <> "+requestID+" and process_id = "+currentProcessID+" order by request_id,cat_part_no,packaging";
             dbrs2 = db.doQuery(query);
             rs2=dbrs2.getResultSet();
             while (rs2.next()){
               Object[] oa = new Object[4];
               oa[0] = rs2.getString("request_id");
               oa[1] = rs2.getString("cat_part_no");
               oa[2] = rs2.getString("description");
               oa[3] = rs2.getString("packaging");
               multipleRequestV.addElement(oa);
             }
             }catch (Exception ee) {
               ee.printStackTrace();
               result.put("MSG","tcmIS encountered a problem while trying to load data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918");
               HelpObjs.sendMail(db,"Unable to load Process On Multiple Data","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
             }finally{
               dbrs2.close();
             }
           }
           multipleRequestV.trimToSize();
           innerH.put("MULTIPLE_REQUEST_DATA",multipleRequestV);
           operationDefV.addElement(innerH);
           //next emission point
           Vector emissionV = new Vector(10);
           Object[] oa = new Object[6];
           int i = 0;
           String currentValue = rs.getString("app_emission_point")+rs.getString("fac_emission_point");
           if (currentValue.length() > 0 && !BothHelpObjs.isBlankString(currentValue)) {
             oa[i++] = rs.getString("app_emission_point").trim();
             oa[i++] = rs.getString("fac_emission_point").trim();
             oa[i++] = rs.getString("emission_point_name").trim();
             oa[i++] = rs.getString("emission_point_desc").trim();
             oa[i++] = rs.getString("process_emission_percentage").trim();
             oa[i++] = "F";
             emissionV.addElement(oa);
             tmpEmissionIDV.addElement(currentValue);
           }
           lastEmissionPointID = currentValue;
           //material used
           Vector materialUsedV = new Vector(20);
           currentValue = rs.getString("catalog_id")+rs.getString("part_group_no")+rs.getString("cat_part_no")+rs.getString("part_id");
           if (currentValue.length() > 0 && !BothHelpObjs.isBlankString(currentValue)) {
             oa = new Object[14];
             i = 0;
             oa[i++] = rs.getString("cat_part_no").trim();
             oa[i++] = rs.getString("part_desc").trim();
             oa[i++] = "Material Desc";
             oa[i++] = BothHelpObjs.makeBlankFromNull(rs.getString("part_pkg"));
             oa[i++] = "";
             oa[i++] = rs.getString("projected_quantity_per_cycle").trim();
             oa[i++] = rs.getString("projected_size_unit");
             oa[i++] = rs.getString("percentage_emitted").trim();
             oa[i++] = "F";
             oa[i++] = rs.getString("part_group_no").trim();
             oa[i++] = rs.getString("catalog_id").trim();
             oa[i++] = rs.getString("item_id").trim();
             oa[i++] = rs.getString("size_unit_options");
             oa[i++] = rs.getString("part_id");
             materialUsedV.addElement(oa);
             tmpMaterialUsedIDV.addElement(currentValue);
           }
           lastMaterialUsed = currentValue;
           //waste stream
           Vector wasteStreamV = new Vector(10);
           currentValue = rs.getString("waste_id");
           if (currentValue.length() > 0 && !BothHelpObjs.isBlankString(currentValue)) {
             oa = new Object[5];
             i = 0;
             oa[i++] = rs.getString("waste_id").trim();
             oa[i++] = rs.getString("waste_desc").trim();
             oa[i++] = rs.getString("waste_container_id").trim();
             oa[i++] = rs.getString("company_waste_code").trim();
             oa[i++] = "F";
             wasteStreamV.addElement(oa);
             tmpWasteStreamIDV.addElement(currentValue);
           }
           lastWasteStream = currentValue;
           //training
           Vector trainingV = new Vector(10);
           currentValue = rs.getString("training_id");
           if (currentValue.length() > 0 && !BothHelpObjs.isBlankString(currentValue)) {
             oa = new Object[3];
             i = 0;
             oa[i++] = rs.getString("training_id").trim();
             oa[i++] = rs.getString("training_desc").trim();
             oa[i++] = "F";
             trainingV.addElement(oa);
             tmpTrainingIDV.addElement(currentValue);
           }
           lastTraining = currentValue;
           //ppe
           Vector ppeV = new Vector(10);
           currentValue = rs.getString("ppe");
           if (currentValue.length() > 0 && !BothHelpObjs.isBlankString(currentValue)) {
             oa = new Object[3];
             i = 0;
             oa[i++] = rs.getString("ppe").trim();
             oa[i++] = rs.getString("ppe_desc").trim();
             oa[i++] = "F";
             ppeV.addElement(oa);
             tmpPPEIDV.addElement(currentValue);
           }
           lastPPE = currentValue;
           //now put all together
           h.put("OPERATION_DEFINITION",operationDefV);
           h.put("EMISSION_POINT",emissionV);
           h.put("MATERIAL_USED",materialUsedV);
           h.put("WASTE",wasteStreamV);
           h.put("TRAINING",trainingV);
           h.put("PPE",ppeV);
           result.put(currentProcessID,h);
         }
         lastProcessID = currentProcessID;
       } //end of while

       //also LOAD STATIC info here
       Hashtable tmpStaticH = new Hashtable(7);
       //status
       query = "select process_status from process_status where process_status <> 'Deleted' order by process_status";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       Vector v = new Vector(20);
       while (rs.next()){
         v.addElement(rs.getString("process_status"));
       }
       v.trimToSize();
       tmpStaticH.put("PROCESS_STATUS",v);
       //process type (where)
       query = "select process_station_type from process_type where facility_id = '"+facID+"' order by process_station_type";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       v = new Vector(20);
       while (rs.next()){
         v.addElement(rs.getString("process_station_type"));
       }
       v.trimToSize();
       tmpStaticH.put("PROCESS_TYPE",v);
       //discharge
       query = "select process_discharge_path from process_discharge_path where facility_id = '"+facID+"' order by process_discharge_path";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       v = new Vector(20);
       while (rs.next()){
         v.addElement(rs.getString("process_discharge_path"));
       }
       v.trimToSize();
       tmpStaticH.put("DISCHARGE",v);
       //frequency units
       v = new Vector(4);
       v.addElement("day");
       v.addElement("week");
       v.addElement("month");
       v.addElement("year");
       tmpStaticH.put("FREQ_UNIT",v);
       //frequency units
       v = new Vector(2);
       v.addElement("minutes");
       v.addElement("hours");
       tmpStaticH.put("TIME_UNIT",v);
       result.put("PROCESS_STATIC_DATA",tmpStaticH);
       //waste container ID
       query = "select waste_container_id,waste_container_desc from vv_fac_waste_container where facility_id = '"+facID+"' order by waste_container_desc";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       v = new Vector(20);
       Vector descV = new Vector(20);
       while (rs.next()){
         v.addElement(rs.getString("waste_container_id"));
         descV.addElement(rs.getString("waste_container_desc"));
       }
       v.trimToSize();
       descV.trimToSize();
       tmpStaticH.put("WASTE_CONTAINER_IDS",v);
       tmpStaticH.put("WASTE_CONTAINER_DESC",descV);

       //maximum process ID
       query = "select max(process_id) process_id from process where facility_id = '"+facID+"' and application = '"+waID+"'";
       query = "select max(process_id) process_id from ("+
               " select process_id from ca_process where facility_id = '"+facID+"' and application = '"+waID+"'"+
               " union all "+
               " select process_id  from process where facility_id = '"+facID+"' and application = '"+waID+"')";

       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       String tmpMaxProcessID = "";
       while (rs.next()){
         tmpMaxProcessID = BothHelpObjs.makeBlankFromNull(rs.getString("process_id"));
       }
       if (tmpMaxProcessID.length() > 0) {
         result.put("MAX_PROCESS_ID", new Integer(tmpMaxProcessID));
       }else {
         result.put("MAX_PROCESS_ID", new Integer(0));
       }

       //get list of size unit
       Vector sizeUnitV = new Vector();
       dbrs = db.doQuery("select size_unit from vv_size_unit order by size_unit");
       rs=dbrs.getResultSet();
       while (rs.next()){
         sizeUnitV.addElement(rs.getString("size_unit"));
       }
       result.put("VV_SIZE_UNIT",sizeUnitV);

       result.put("MSG","OK");
       result.put("PROCESS_ID",processIDV);
     }catch (Exception e) {
       e.printStackTrace();
       result.put("MSG","tcmIS encountered a problem while trying to load data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918");
       HelpObjs.sendMail(db,"Unable to load Process Data","Facility: "+facID+"\nWork area: "+waID+"\nRequestor: "+userID.toString(),86030,false);
     }finally {
       dbrs.close();
     }
     return result;
   } //end of method

}  //end of class

