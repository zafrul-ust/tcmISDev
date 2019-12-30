/*
SQL> describe catalog_add_approval;
 Name                            Null?    Type
 ------------------------------- -------- ----
 REQUEST_ID                      NOT NULL NUMBER
 CHEMICAL_APPROVER               NOT NULL NUMBER
 APPROVAL_DATE                            DATE
 STATUS                                   VARCHAR2(12)
 REASON                                   VARCHAR2(200)
 APPROVAL_ROLE                   NOT NULL VARCHAR2(30)
 FACILITY_ID                     NOT NULL VARCHAR2(30)
*/

package radian.tcmis.server7.share.dbObjs;



import radian.tcmis.server7.share.db.*;
import java.util.*;

import com.tcmis.common.util.DateHandler;

import java.sql.*;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

/** this object containing NewChemical Approval info */
public class NewChemApproval {

    protected TcmISDBModel db;
    protected String role;
    protected Integer reqId;
    protected Vector approvers;
    protected String date;
    protected String status = "";    //trong 3/30
    protected String reason;
    protected Integer approver;
    protected Integer roleType;
    protected String facilityId;
    protected int active=1;
    protected String catalogId;

    public static final int STRING = 0;
    public static final int INT    = 1;
    public static final int DATE   = 2;

    //trong 3/29
    public boolean evalFlag = false;
    protected int apprFlag = 1;
    Vector roleV = new Vector();

    public NewChemApproval() {

    }

    public NewChemApproval(TcmISDBModel  db) {
        this.db = db;
    }

    public void setDB(TcmISDBModel  db){
        this.db = db;
    }

    public void setRole(String s){
        if(s==null)s="";
        role = s;
    }
    public void setRoleType(int s){
        roleType = new Integer(s);
    }
    public void setReqId(int s){
        reqId = new Integer(s);
    }
    public void setApprovers(Vector s){
        approvers = s;
    }
    public void setDate(String s){
        if(s==null)s="";
        date = s;
    }
    public void setFacilityId(String s){
        facilityId = s;
    }
    public String getFacilityId() {
        return facilityId;
    }
    public void setCatalogId(String s){
        catalogId = s;
    }
    public void setStatus(String s){
        if(s==null)s="";
        status = s;
    }
    public void setReason(String s){
        if(s==null)s="";
        reason = s;
    }
    public void setApprover(int a){
        approver = new Integer(a);
    }

    public Integer getApprover(){
        return approver;
    }

    public String getRole(){
        return role;
    }
    public Integer getRoleType(){
        return roleType;
    }
    public Integer getReqId(){
        return reqId;
    }
    public Vector getApprovers(){
        return approvers;
    }
    public Vector getApproversNames() throws Exception{
        Vector v = new Vector();
        try{
            Vector a = getApprovers();
            for(int x=0;x<a.size();x++){
                radian.tcmis.server7.share.dbObjs.Personnel p = new radian.tcmis.server7.share.dbObjs.Personnel(db);
                p.setPersonnelId(Integer.parseInt(a.elementAt(x).toString()));
                p.load();
                v.addElement(p.getFullName());
            }
        }catch(Exception e){e.printStackTrace();}
        //v = BothHelpObjs.sort(v);   // so we can bring numbers together
        return v;
    }
    public String getDate(){
        return date;
    }
    public String getStatus(){
        return status;
    }
    public String getReason(){
        return reason;
    }

    public Hashtable getCatalogAddRequestInfo(String requestId) {
        Hashtable result = new Hashtable();
        String query = "select distinct carn.catalog_company_id,cai.item_id,carn.temporary_item_id,carn.create_temporary_item"+
                " from catalog_add_request_new carn, catalog_add_item cai where carn.company_id = cai.company_id and"+
                " carn.request_id = cai.request_id and cai.line_item = 1 and part_id = 1 and carn.request_id = "+requestId;
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()) {
                result.put("CREATE_TEMPORARY_ITEM",BothHelpObjs.makeBlankFromNull(rs.getString("create_temporary_item")));
                result.put("CATALOG_COMPANY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("catalog_company_id")));
                result.put("ITEM_ID",BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
                result.put("TEMPORARY_ITEM_ID",BothHelpObjs.makeBlankFromNull(rs.getString("temporary_item_id")));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbrs.close();
        }
        return result;
    } //end of method

    public String getContactInfo(String constant) {
        String result = "";
        String query = "select nvl(value,' ') value from tcmis_constant where constant = '"+constant+"'";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getString("value");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbrs.close();
        }
        return result;
    } //end of method

    public void processSeagateReactivatedRequest() throws Exception {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String requestIds = "";
        try {
            //if sql returns something other than 0 (zero) for request_id, it means that this request was once approved/rejected from CRA
            String query = "select carn.eng_eval_facility_id facility_id,carn.catalog_id,carn.catalog_company_id,carn.request_status,scas.radian_request_id,nvl(caa.request_id,'0') request_id"+
                    " from seagate_chem_approval_stage scas,catalog_add_approval caa,catalog_add_request_new carn"+
                    " where processed_status is null and radian_request_id is not null and lower(cra_status) = 'reactivated' and"+
                    " scas.radian_request_id = carn.request_id and scas.radian_request_id = caa.request_id(+) and"+
                    " caa.approval_role(+) = 'CRA'";
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                String requestId = rs.getString("radian_request_id");
                //if there is no record in catalog_add_approval and the carn.request_status is not 7 (rejected)
                //then mark request for "Reactivated"
                if ("0".equalsIgnoreCase(rs.getString("request_id")) && !"7".equalsIgnoreCase(rs.getString("request_status"))) {
                    markCRARequestsAsProcessed(requestId,"Reactivated");
                }else if ("7".equalsIgnoreCase(rs.getString("request_status"))) {
                    //delete previously approved/rejected record from CRA
                    db.doUpdate("delete from catalog_add_approval where approval_role = 'CRA' and request_id = "+requestId);
                    //reset catalog_add_request_new.request_status = 5 (pending CRA)
                    db.doUpdate("update catalog_add_request_new set request_status = 5,approval_group_seq=1 where request_id = "+requestId);
                    markCRARequestsAsProcessed(requestId,"Reactivated");
                }else {
                    //holding requests so I can approved parts from use_approval
                    requestIds += rs.getString("radian_request_id") + ",";
                }
            }
            if (!BothHelpObjs.isBlankString(requestIds)) {
                //remove last "," commas
                requestIds = requestIds.substring(0,requestIds.length()-1);
                //approve parts from use_approval
                setApprovalStatusForParts(requestIds,"approved");
                //mark requests as processed
                markCRARequestsAsProcessed(requestIds,"Processed");
            }
        }catch (Exception e) {
            if (!BothHelpObjs.isBlankString(requestIds)) {
                //remove last "," commas
                requestIds = requestIds.substring(0,requestIds.length()-1);
                //mark requests as processed
                markCRARequestsAsProcessed(requestIds,"Error");
                HelpObjs.sendMail(db,"JRun Scheduler","Error occurred while scheduler processing 'Reactivated' request: "+requestIds,86030,false);
            }
            throw e;
        }finally {
            dbrs.close();
        }
    } //end of method

    public void processSeagateInactiveRequest() throws Exception {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String requestIds = "";
        try {
            //if sql returns something other than 0 (zero) for request_id, it means that this request was once approved/rejected from CRA
            //therefore, I'm not inserting a record into catalog_add_approval for it
            String query = "select carn.eng_eval_facility_id facility_id,carn.catalog_id,carn.catalog_company_id,carn.request_status,scas.radian_request_id,nvl(caa.request_id,'0') request_id"+
                    " from seagate_chem_approval_stage scas,catalog_add_approval caa,catalog_add_request_new carn"+
                    " where processed_status is null and radian_request_id is not null and lower(cra_status) = 'inactive' and"+
                    " scas.radian_request_id = carn.request_id and scas.radian_request_id = caa.request_id(+) and"+
                    " caa.approval_role(+) = 'CRA'";
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                if ("0".equalsIgnoreCase(rs.getString("request_id"))) {
                    //if 0 then add rejected request
                    rejectCRARequest(rs.getString("catalog_id"),rs.getString("catalog_company_id"),rs.getString("facility_id"),rs.getString("radian_request_id"),"insert");
                }else {
                    //if request is not at Ready to Order (9) status
                    if (!"9".equalsIgnoreCase(rs.getString("request_status"))) {
                        //otherwise, set record as rejected from CRA
                        rejectCRARequest(rs.getString("catalog_id"),rs.getString("catalog_company_id"), rs.getString("facility_id"), rs.getString("radian_request_id"), "update");
                    }
                }
                //holding requests so I can unapproved parts from use_approval
                requestIds += rs.getString("radian_request_id")+",";
            }
            if (!BothHelpObjs.isBlankString(requestIds)) {
                //remove last "," commas
                requestIds = requestIds.substring(0,requestIds.length()-1);
                //unapprove parts from use_approval
                setApprovalStatusForParts(requestIds,"unapproved");
                //mark requests as processed
                markCRARequestsAsProcessed(requestIds,"Processed");
            }
        }catch (Exception e) {
            if (!BothHelpObjs.isBlankString(requestIds)) {
                //remove last "," commas
                requestIds = requestIds.substring(0,requestIds.length()-1);
                //mark requests as processed
                markCRARequestsAsProcessed(requestIds,"Error");
                HelpObjs.sendMail(db,"JRun Scheduler","Error occurred while scheduler processing 'Inactive' request: "+requestIds,86030,false);
            }
            throw e;
        }finally {
            dbrs.close();
        }
    } //end of method

    void markCRARequestsAsProcessed(String requestIds,String processStatus) throws Exception {
        try {
            db.doUpdate("update seagate_chem_approval_stage set processed_date = sysdate,processed_status = '"+processStatus+"'"+
                    " where radian_request_id in ("+requestIds+") and processed_status is null");
        }catch (Exception e) {
            throw e;
        }
    } //end of method


    void setApprovalStatusForParts(String requestIds, String status) throws Exception {
        try {
            db.doUpdate("update use_approval set approval_status = '"+status+"' where (catalog_id,catalog_company_id,facility_id,application,fac_part_no)"+
                    " in (select catalog_id,catalog_company_id,eng_eval_facility_id,eng_eval_work_area,cat_part_no from catalog_add_request_new"+
                    " where cat_part_no is not null and request_id in ("+requestIds+"))");
        }catch (Exception e) {
            throw e;
        }
    } //end of method


    void rejectCRARequest(String catalogId,String catalogCompanyId,String facilityId, String requestId, String type) throws Exception {
        try {
            //first set catalog_add_request_new.request_status to rejected
            db.doUpdate("update catalog_add_request_new set request_status = 7,approval_group_seq = 0 where request_id = " + requestId);
            if ("insert".equalsIgnoreCase(type)) {
                //next insert rejected record in catalog_add_approval
                db.doUpdate("insert into catalog_add_approval(request_id,chemical_approver,approval_date,status,reason,approval_role,facility_id,catalog_id,catalog_company_id)" +
                        " values(" + requestId + ",-1,sysdate,'Rejected',null,'CRA','" + facilityId + "','" + catalogId + "','"+catalogCompanyId+"')");
            }else {
                db.doUpdate("update catalog_add_approval set status = 'Rejected',approval_date = sysdate,reason = null where approval_role = 'CRA' and request_id = "+requestId);
            }
        }catch (Exception e) {
            throw e;
        }
    } //end of method

    //processing "Active" record from seagate_chem_approval_stage (i.e. every 30 min. - JRun scheduler)
    public void processSeagateNewChemApproval() throws Exception {
        //first get all unprocess request
        String query = "select x.REQUESTOR_ID,decode(x.FACILITY_ID,'NORMANDALE','NRM','SHAKOPEE','SHAK','PITTSBURGH','WATERFRONT','FREMONT',x.building_id,'MILPITAS',x.building_id,x.facility_id) facility_id,x.FACILITY_DESC,x.BUILDING_ID,x.BUILDING_DESC,"+
                "x.PROCESS_DESC,x.REQUESTER_DEPT_ID,x.CAT_PART_NO,x.CHEMICAL_NAME,x.SEAGATE_MSDS_NUMBER,"+
                "x.MSDS_TRADE_NAME,x.CONTAINER_TYPE,x.UNIT_OF_MEASURE,"+
                "x.CONTAINER_SIZE,x.KIT_FLAG,x.RADIAN_REQUEST_ID,x.SEAGATE_REQUEST_ID,x.LOCATION_ID,"+
                "x.LOCATION_DESC,x.PROCESSED_DATE,x.REQUESTOR_ID,x.SEAGATE_REQUEST_ID "+
                "from seagate_chem_approval_stage x "+
                "where ((processed_status is null and lower(cra_status) = 'active') or (processed_status = 'Reactivated' and lower(cra_status) = 'reactivated'))"+
                " and radian_request_id is not null order by radian_request_id";

        DBResultSet dbrs = null;
        ResultSet rs = null;
        Hashtable ftpData = new Hashtable();
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                String currentRequestId = BothHelpObjs.makeBlankFromNull(rs.getString("radian_request_id"));
                if (ftpData.containsKey(currentRequestId)) {
                    Vector requestDataV = (Vector)ftpData.get(currentRequestId);
                    Hashtable h = new Hashtable();
                    h.put("REQUEST_ID",currentRequestId);
                    h.put("PROCESS_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("process_desc")));
                    h.put("CHEMICAL_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("chemical_name")));
                    h.put("SEAGATE_MSDS_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("seagate_msds_number")));
                    h.put("CAT_PART_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no")));
                    h.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
                    h.put("BUILDING_ID",BothHelpObjs.makeBlankFromNull(rs.getString("building_id")));
                    h.put("LOCATION_ID",BothHelpObjs.makeBlankFromNull(rs.getString("location_id")));
                    h.put("CONTAINER_TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("container_type")));
                    h.put("CONTAINER_SIZE",BothHelpObjs.makeBlankFromNull(rs.getString("container_size")));
                    h.put("UNIT_OF_MEASURE",BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_measure")));
                    h.put("SEAGATE_REQUESTOR_ID",BothHelpObjs.makeBlankFromNull(rs.getString("requestor_id")));
                    h.put("SEAGATE_REQUEST_ID",BothHelpObjs.makeBlankFromNull(rs.getString("seagate_request_id")));
                    requestDataV.addElement(h);
                }else {
                    Vector requestDataV = new Vector();
                    Hashtable h = new Hashtable();
                    h.put("REQUEST_ID",currentRequestId);
                    h.put("PROCESS_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("process_desc")));
                    h.put("CHEMICAL_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("chemical_name")));
                    h.put("SEAGATE_MSDS_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("seagate_msds_number")));
                    h.put("CAT_PART_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no")));
                    h.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
                    h.put("BUILDING_ID",BothHelpObjs.makeBlankFromNull(rs.getString("building_id")));
                    h.put("LOCATION_ID",BothHelpObjs.makeBlankFromNull(rs.getString("location_id")));
                    h.put("CONTAINER_TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("container_type")));
                    h.put("CONTAINER_SIZE",BothHelpObjs.makeBlankFromNull(rs.getString("container_size")));
                    h.put("UNIT_OF_MEASURE",BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_measure")));
                    h.put("SEAGATE_REQUESTOR_ID",BothHelpObjs.makeBlankFromNull(rs.getString("requestor_id")));
                    h.put("SEAGATE_REQUEST_ID",BothHelpObjs.makeBlankFromNull(rs.getString("seagate_request_id")));
                    requestDataV.addElement(h);
                    ftpData.put(currentRequestId,requestDataV);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            dbrs.close();
        }
        //next check and update data
        String facID = "";
        Vector applicationV = new Vector();
        String processDesc = "";
        String catPartNo = "";
        String chemicalName = "";
        String seagateMSDSNumber = "";
        String containerType = "";
        String containerSize = "";
        String unitOfMeasure = "";
        String startingView = "";
        //for each request compare requested data with ftp data
        Enumeration enu = ftpData.keys();
        while (enu.hasMoreElements()) {
            String reqID = (String)enu.nextElement();
            boolean approvedByQC = HelpObjs.countQuery(db,"select count(*) from catalog_add_approval where approval_role = 'QC' and request_id = "+reqID) > 0;
            try {
                //set the process date to sysdate
                query = "update seagate_chem_approval_stage set processed_date = sysdate where processed_date is null and radian_request_id = "+reqID;
                db.doUpdate(query);

                query = "select * from new_chem_url_view where request_id = "+reqID;
                dbrs = db.doQuery(query);
                rs=dbrs.getResultSet();
                while (rs.next()){
                    //the reason for this is that the work area is different
                    if (applicationV.size() == 0) {
                        facID = BothHelpObjs.makeBlankFromNull(rs.getString("tcm_facility_id"));
                        processDesc = BothHelpObjs.makeBlankFromNull(rs.getString("process_desc"));
                        catPartNo = BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no"));
                        chemicalName = BothHelpObjs.makeBlankFromNull(rs.getString("mfg_trade_name"));
                        seagateMSDSNumber = BothHelpObjs.makeBlankFromNull(rs.getString("customer_msds_number"));
                        containerSize = BothHelpObjs.makeBlankFromNull(rs.getString("part_size"));
                        unitOfMeasure = BothHelpObjs.makeBlankFromNull(rs.getString("size_unit"));
                        containerType = BothHelpObjs.makeBlankFromNull(rs.getString("pkg_style"));
                        startingView = BothHelpObjs.makeBlankFromNull(rs.getString("starting_view"));
                    }
                    applicationV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("application")));
                }
                //compare the data
                String ftpFacID = "";
                String ftpCatPartNo = "";
                String ftpChemicalName = "";
                String ftpSeagateMSDSNumber = "";
                String ftpContainerType = "";
                String ftpContainerSize = "";
                String ftpUnitOfMeasure = "";
                String ftpSeagateRequestID = "";
                Vector tmpRequestDataV = (Vector)ftpData.get(reqID);
                Vector ftpApplicationV = new Vector(tmpRequestDataV.size());
                for (int kk = 0; kk < tmpRequestDataV.size(); kk++) {
                    Hashtable h = (Hashtable)tmpRequestDataV.elementAt(kk);
                    //the reason for this is because all these data are the same, only work area and process desc (possibly) are different
                    if (kk == 0) {
                        ftpFacID = (String)h.get("FACILITY_ID");
                        ftpCatPartNo = (String)h.get("CAT_PART_NUMBER");
                        ftpChemicalName = (String)h.get("CHEMICAL_NAME");
                        ftpSeagateMSDSNumber = (String)h.get("SEAGATE_MSDS_NUMBER");
                        ftpContainerType = (String)h.get("CONTAINER_TYPE");
                        ftpContainerSize = (String)h.get("CONTAINER_SIZE");
                        ftpUnitOfMeasure = (String)h.get("UNIT_OF_MEASURE");
                        ftpSeagateRequestID = (String)h.get("SEAGATE_REQUEST_ID");
                    }
                    String ftpProcessDesc = (String)h.get("PROCESS_DESC");
                    String ftpApplication = (String)h.get("BUILDING_ID")+"/"+(String)h.get("LOCATION_ID");

                    if (!ftpFacID.equalsIgnoreCase(facID) || !applicationV.contains(ftpApplication)) {
                        int count = HelpObjs.countQuery(db,"select count(*) from fac_loc_app where facility_id = '"+ftpFacID+"' and application = '"+ftpApplication+"'");
                        if (count == 0) {
                            query = "update seagate_chem_approval_stage set processed_status = 'Error' where (processed_status is null or processed_status = 'Reactivated') and radian_request_id = "+reqID;
                            db.doUpdate(query);
                            HelpObjs.sendMail(db,"New Chemical add for Seagate (request_id: "+reqID+" - work area: "+ftpApplication+") Unknown Facility/Work Area","User requested a new chemical add to an unknown facility/work area",86030,true);  //During test I go ahead and send this to me (later on Ronnie)
                            continue;
                        }else {
                            //create a new record for work areas that was added from CRA
                            query = "insert into catalog_add_user_group (request_id,facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,quantity_2,per_2,period_2)";
                            query += " select request_id,'"+ftpFacID+"','"+ftpApplication+"',user_group_id,'"+ftpProcessDesc+"',";
                            query += "quantity_1,per_1,period_1,quantity_2,per_2,period_2 from catalog_add_user_group where request_id = "+reqID+" and rownum < 2";
                            db.doUpdate(query);
                        }
                    }else {
                        //if user change process_desc
                        if (!ftpProcessDesc.equalsIgnoreCase(processDesc)) {
                            query = "update catalog_add_user_group set process_desc = '"+ftpProcessDesc+"' where facility_id = '"+ftpFacID+"' and work_area = '"+ftpApplication+"' and request_id = "+reqID;
                            db.doUpdate(query);
                        }
                    }
                    ftpApplicationV.addElement(ftpApplication);
                }
                //now delete work areas that was deleted from CRA
                for (int jj = 0; jj < applicationV.size(); jj++) {
                    String tmpApp = (String)applicationV.elementAt(jj);
                    if (!ftpApplicationV.contains(tmpApp)) {
                        query = "delete from catalog_add_user_group where request_id = "+reqID+" and facility_id = '"+facID+"' and work_area = '"+tmpApp+"'";
                        db.doUpdate(query);
                    }
                }

                //if user change cat_part_number - catalog_add_request_new
                if (!ftpCatPartNo.equalsIgnoreCase(catPartNo) && !BothHelpObjs.isBlankString(ftpCatPartNo)) {
                    if (BothHelpObjs.isBlankString(catPartNo)) {
                        CatalogAddRequestNew cat = new CatalogAddRequestNew(db);
                        cat.setRequestId(Integer.parseInt(reqID));
                        cat.insert("cat_part_no",ftpCatPartNo,CatalogAddRequestNew.STRING);
                    }else {
                        if (!catPartNo.equalsIgnoreCase(ftpCatPartNo)) {
                            //set the process error date to sysdate so we can tell whether or not we process this request or not
                            query = "update seagate_chem_approval_stage set processed_status = 'Error' where (processed_status is null or processed_status = 'Reactivated') and radian_request_id = "+reqID;
                            db.doUpdate(query);
                            HelpObjs.sendMail(db,"New Chemical add for Seagate (request_id: "+reqID+")","cat_part_no changed in CRA",86030,true);  //During test I go ahead and send this to me (later on Ronnie)
                        }
                        return;
                    }
                }

                //if user change other field in catalog_add_item
                String message = "";
                CatalogAddItemNew catI = new CatalogAddItemNew(db);
                catI.setRequestId(Integer.parseInt(reqID));
                catI.setPartId(new Integer(1));
                if (!ftpSeagateMSDSNumber.equalsIgnoreCase(seagateMSDSNumber)) {
                    catI.insert("customer_msds_number",ftpSeagateMSDSNumber,CatalogAddItemNew.STRING);
                }
                if (!ftpContainerSize.equalsIgnoreCase(containerSize)) {
                    if (!approvedByQC) {
                        catI.insert("part_size",ftpContainerSize,CatalogAddItemNew.INT);
                    }else {
                        message += "\nOur part_size = "+containerSize+"\nCRA part_size = "+ftpContainerSize;
                    }
                }
                if (!ftpUnitOfMeasure.equalsIgnoreCase(unitOfMeasure)) {
                    query = "select count(*) from cont_size_size_unit_link where unit_code = "+ftpUnitOfMeasure;
                    if (HelpObjs.countQuery(db,query) > 0) {
                        if (("0".equalsIgnoreCase(startingView) || "1".equalsIgnoreCase(startingView)) && !approvedByQC ) {
                            HelpObjs.insertUpdateTable(db,"update catalog_add_item set size_unit = (select VV_SIZE_UNIT from cont_size_size_unit_link where preferred = 'y' and unit_code = "+ftpUnitOfMeasure+") where request_id = "+reqID);
                        }else {
                            message += "\nOur size_unit: "+unitOfMeasure+"\nCRA size_unit = "+ftpUnitOfMeasure;
                        }
                    }else {
                        message += "\nOur size_unit: "+unitOfMeasure+"\nCRA size_unit = "+ftpUnitOfMeasure;
                    }
                }
                if (!ftpContainerType.equalsIgnoreCase(containerType)) {
                    query = "select count(*) from cont_type_pkg_style_link where vv_pkg_style = '"+ftpContainerType+"'";
                    if (HelpObjs.countQuery(db,query) > 0) {
                        if (("0".equalsIgnoreCase(startingView) || "1".equalsIgnoreCase(startingView)) && !approvedByQC) {
                            catI.insert("pkg_style",ftpContainerType,CatalogAddItemNew.STRING);
                        }else {
                            message += "\nOur pkg_style: "+containerType+"\nCRA pkg_style = "+ftpContainerType;
                        }
                    }else {
                        message += "\nOur pkg_style: "+containerType+"\nCRA pkg_style = "+ftpContainerType;
                    }
                }
                //12-10-01 ignore CRA changes for this field, 4-15-02 need to update this field
                if (!ftpChemicalName.equalsIgnoreCase(chemicalName)) {
                    if (!approvedByQC) {
                        catI.insert("mfg_trade_name",ftpChemicalName,CatalogAddItemNew.STRING);
                    }else {
                        message += "\nOur mfg_trade_name: "+chemicalName+"\nCRA mfg_trade_name = "+ftpChemicalName;
                    }
                }

                //now set the processed status to okay
                query = "update seagate_chem_approval_stage set processed_status = 'Processed' where (processed_status is null or processed_status = 'Reactivated') and radian_request_id = "+reqID;
                db.doUpdate(query);

                //add approval record for CRA
                query = "insert into catalog_add_approval (company_id,request_id,facility_id,catalog_id,catalog_company_id,approval_role,chemical_approver,status,approval_date,reason)"+
                        " select company_id,request_id,'"+ftpFacID+"',catalog_id,catalog_company_id,'CRA',-1,'Approved',sysdate,'Auto approved by tcmIS' from catalog_add_request_new where request_id = "+reqID;
                db.doUpdate(query);

                //set seagate request id
                if (!BothHelpObjs.isBlankString(ftpSeagateRequestID)) {
                    db.doUpdate("update catalog_add_request_new set customer_request_id = " + ftpSeagateRequestID + " where request_id = " + reqID);
                }

                //send email to if data is different from cra
                if (message.length() > 0) {
                    UserGroup userGroup = new UserGroup(db);
                    userGroup.setGroupFacId("All");
                    userGroup.setGroupId("CRANofication");
                    HelpObjs.sendMail(userGroup,"CRA data is different for request: "+reqID,message);
                }

                //set next status and send email
                NewChemApproval nca = new NewChemApproval(db);
                String[] result = nca.setNextStatus(reqID);
                String done = result[0];
                String eval = result[1];
                String nextGroup = result[2];
                //send email to user if the end of approval process is reach.  Otherwise, continue to the next approval group
                if ("Done".equalsIgnoreCase(done)) {
                    //first check to see if tcmIS need to generate a part_no
                    //if request doesn't contains a part_no
                    if (HelpObjs.countQuery(db,"select count(*) from catalog_add_request_new where (cat_part_no is null or vsize(cat_part_no) < 2 or ascii(cat_part_no) = 160 or cat_part_no like '%?%') and request_id = "+reqID) > 0) {
                        Calendar cal = Calendar.getInstance();
                        Integer temp = new Integer(cal.get(cal.YEAR));
                        String partNo = "";
                        if ("FEC".equalsIgnoreCase(db.getClient())) {
                            partNo = temp.toString().substring(3)+db.getClient().toUpperCase()+"-";
                        }else {
                            partNo = temp.toString().substring(2)+db.getClient().toUpperCase()+"-";
                        }
                        Integer partSeq = new Integer(HelpObjs.getNextValFromSeq(db,"part_seq"));
                        partNo += partSeq.toString();
                        HelpObjs.insertUpdateTable(db,"update catalog_add_request_new set cat_part_no = '"+partNo+"' where request_id = "+reqID);
                    }
                    //add part to catalog
                    try {
                        //call store procedure to add part to catalog
                        Vector v = new Vector();
                        v.addElement(reqID);
                        v.addElement("error_val");
                        String val = db.doInvoiceProcedure("p_add_cat_part_request",v);     //the naming of this method is my bad.  I didn't think ahead
                        //System.out.println("--------- value from procedure: "+val+"*");
                        if (!BothHelpObjs.isBlankString(val) && !val.startsWith("OK")) {
                            String esub = "Error while calling procedure to add part to catalog (request #"+reqID+")";
                            String emsg = "Procedure: p_add_cat_part has an error.\n";
                            emsg += val;
                            HelpObjs.sendMail(db,esub,emsg,86030,false);
                        }
                    }catch (Exception ee) {
                        ee.printStackTrace();
                        String esub = "Error while processing CRA request (request #"+reqID+")";
                        String emsg = "Procedure: p_add_cat_part throw an exception.\n";
                        HelpObjs.sendMail(db,esub,emsg,86030,false);
                    }
                    nca.sendUserEmail(reqID);
                }else {
                    //sending email to the next group/group seq
                    if (nextGroup.equalsIgnoreCase("New group")) {
                        nca.sendApproversEmail(reqID);
                    }
                }
            }catch (Exception ee) {
                //set the process error date to sysdate so we can tell whether or not we process this request or not
                query = "update seagate_chem_approval_stage set processed_status = 'Error' where (processed_status is null or processed_status = 'Reactivated') and radian_request_id = "+reqID;
                db.doUpdate(query);
                ee.printStackTrace();
                throw ee;
            }finally {
                dbrs.close();
            }
        }
    }

    //no longer need this method because we are going to treat POSS requests like any other requests
    /*
  public String[] setPOSSNextStatus(String reqID, String sView, String aGroup, String aGroupSeq) throws Exception {
    String[] result = new String[4];
    result[0] = "Next Status";
    result[1] = "n";
    result[2] = "New group";
    result[3] = "Y";          //POSS request
    DBResultSet dbrs = null;
    try {
      ResultSet rs = null;
      String query = "select distinct caa.approval_role from catalog_add_approval caa,vv_chemical_approval_role car where caa.approval_role = car.approval_role and"+
	            " caa.facility_id = car.facility_id and caa.catalog_id = car.catalog_id and caa.catalog_company_id = car.catalog_company_id and car.approval_group = "+aGroup+" and car.approval_group_seq = "+aGroupSeq+" and caa.request_id = "+reqID;
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String aApprovalRole = "";
      while (rs.next()) {
        aApprovalRole = rs.getString("approval_role");
      }

      //next approval info
      query = "select a.approval_role,a.approval_group,a.approval_group_seq from vv_chemical_approval_role a, catalog_add_approval b"+
              " where a.poss_material = 'Y' and a.facility_id = b.facility_id(+) and a.approval_role = b.approval_role(+) and b.approval_role is null and b.request_id(+) = "+reqID+
              " and a.catalog_id = b.catalog_id(+) and a.catalog_company_id = b.catalog_company_id(+) and a.active = 'Y'"+
              " and (a.facility_id,a.catalog_id,a.catalog_company_id) = (select eng_eval_facility_id,catalog_id,catalog_company_id from catalog_add_request_new where request_id = "+reqID+")"+
              " order by a.approval_group,a.approval_group_seq,a.approval_role";

      Vector aGroupV = new Vector(3);
      Vector aGroupSeqV = new Vector(3);
      Vector approvalRoleV = new Vector(3);
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        aGroupV.addElement(rs.getString("approval_group"));
        aGroupSeqV.addElement(rs.getString("approval_group_seq"));
        approvalRoleV.addElement(rs.getString("approval_role"));
      }

      int currentGroupPos = 0;
      if (currentGroupPos + 1 == aGroupV.size() || aGroupV.size() == 0) {    //this mean that we reached the end of the approval process
        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
        result[0] = "Done";
      }else {                                          //otherwise, pass the request to the next approval group and approval group seq
        //if next group and seq is the same as current then don't send email
        if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos)) &&
            aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos))) {
          result[2] = "Same group";
        }
        //set request status to the next status
        query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos)+
                ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos)+") where request_id = "+reqID;

        //if the next group is Pricing check to see if item has a price quote
        if ("Pricing".equalsIgnoreCase((String)approvalRoleV.elementAt(currentGroupPos))) {
          result[2] = "Punchout group";       //treating pricing like punchout since buyers doesn't want email notification
          dbrs = db.doQuery("select distinct a.catalog_id,a.cat_part_no,cai.item_id,f.facility_id,f2.branch_plant from catalog_add_request_new a,catalog_add_item cai,"+
                            "facility f,facility f2 where a.request_id = "+reqID+" and a.eng_eval_facility_id = f.facility_id and f.preferred_warehouse = f2.facility_id"+
			 						 " and a.company_id = cai.company_id and a.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1");
          rs=dbrs.getResultSet();
          String cItemID = "0";
          String facID = "";
          String tmpCatPartNo = "";
          String tmpCatalogID = "";
          String branchPlant = "";
          String companyName = "";
          while (rs.next()){
            cItemID = rs.getString("item_id");
            facID = rs.getString("facility_id");
            branchPlant = rs.getString("branch_plant");
            tmpCatPartNo = BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no"));
            tmpCatalogID = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id"));
          }
          if ("Ray".equalsIgnoreCase(db.getClient())) {
            companyName = "RAYTHEON";
          }else if ("LMCO".equalsIgnoreCase(db.getClient())) {
            companyName = "LOCKHEED";
          }else if ("SD".equalsIgnoreCase(db.getClient())) {
            companyName = "SAUER_DANFOSS";
          }else {
            companyName = db.getClient().toUpperCase();
          }

          if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
            query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
            result[0] = "Done";
          }else {  //Pricing is not the last approval role for this request, then skip pricing and set to next group
            //client requested that we not wait for price quote at this point
            if (HelpObjs.countQuery(db,"select count(*) from tcmis_feature where feature = 'AutoPricing' and feature_mode = 1") > 0) {
              //auto approve for PRICING
              query = "insert into catalog_add_approval(company_id,request_id,chemical_approver,approval_date,status,reason,approval_role,facility_id,catalog_id,catalog_company_id)"+
                      " select company_id,request_id,-1,sysdate,'Approved','Auto approved','Pricing','"+facID+"',catalog_id,catalog_company_id from catalog_add_request_new where request_id = "+reqID;
              db.doUpdate(query);

              query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                       ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
              //if next group and seq is the same as current then don't send email
              if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos+1)) &&
                  aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos+1))) {
                result[2] = "Same group";
              }else {
                result[2] = "New group";
              }
            }else { //else it is required price quote in the approval process
              query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos)+
                       ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos)+") where request_id = "+reqID;
              //if next group and seq is the same as current then don't send email
              if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos)) &&
                  aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos))) {
                result[2] = "Same group";
              }else {
                result[2] = "New group";
              }
            } //end of else (required price quote)
          }  //end of Pricing is not the last group

          if (!BothHelpObjs.isBlankString(cItemID) && !"0".equalsIgnoreCase(cItemID)) {
            if (HelpObjs.countQuery(db,"select count(*) from price_quote_view where request_date > sysdate - 365 and item_id = "+cItemID) == 0) {
              String postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/cgi-bin/rpq.cgi?item="+cItemID,"&item="+cItemID+"&per=86030&fac="+facID+"&bp="+branchPlant+"&comp="+companyName,"text/html");
            }
          }
        }  //end of Pricing
      } //end of not reaching the end
      //set request status to next status
      db.doUpdate(query);
    }catch (Exception e) {
      throw e;
    }finally {
      dbrs.close();
    }

    //if the last approval is reach then update the rest of the POSS facilities
    if ("Done".equalsIgnoreCase(result[0])) {
      //update catalog_add_user_group with other POSS facilities
      String[] possFacility = {"Camden","Louisville","NAPI","Tucson Rita Road"};
      String tmp = "";
      for (int i = 0; i < possFacility.length; i++) {
        tmp = "insert into catalog_add_user_group (request_id,facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,quantity_2,per_2,period_2)"+
              " select request_id,'"+possFacility[i]+"' facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,quantity_2,per_2,period_2 from catalog_add_user_group"+
              " where facility_id = 'Tucson Airport' and request_id = "+reqID;
        db.doUpdate(tmp);
      } //end of for loop
    } //end of Done

    return result;
  } //end of method
  */

    public void updatePossFacility(String requestId) {
        try {
            //update catalog_add_user_group with other POSS facilities
            String tmp = "insert into catalog_add_user_group (company_id,request_id,facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,quantity_2,per_2,period_2)"+
                    " select caug.company_id,caug.request_id,fr.scope facility_id,'All' work_area,'All' user_group_id,caug.process_desc"+
                    ",caug.quantity_1,caug.per_1,caug.period_1,caug.quantity_2,caug.per_2,caug.period_2"+
                    " from catalog_add_user_group caug, feature_release fr"+
                    " where caug.facility_id = 'Tucson Airport' and caug.request_id = "+requestId+
                    " and caug.company_id = fr.company_id and fr.feature = 'PossFacility'"+
                    " and fr.scope not in (select facility_id from catalog_add_user_group where request_id = "+requestId+")";
            db.doUpdate(tmp);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }  //end of method

    void approvalRoleMaterialExistForFacility(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
            String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
            String qcStatus = (String) approvalDetailH.get("QC_STATUS");
            String chemType = (String) approvalDetailH.get("CHEM_TYPE");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            String query = "select count(*) count from part p,catalog_part_item_group cpig,catalog_add_item cai,catalog_add_request_new carn"+
                    " where exists (select * from use_approval where facility_id = carn.eng_eval_facility_id and"+
                    " approval_status = 'approved' and catalog_id = cpig.catalog_id and fac_part_no = cpig.cat_part_no) and"+
                    " cai.material_id = p.material_id and carn.catalog_id = cpig.catalog_id and carn.catalog_company_id = cpig.company_id and cpig.status = 'A' and"+
                    " p.item_id = cpig.item_id and carn.request_id = cai.request_id and cai.line_item = 1 and carn.request_id = "+reqID;
            ResultSet rs = null;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            int materialExistForFacility = 0;
            while (rs.next()) {
                materialExistForFacility = rs.getInt("count");
            } //end of while
            //if material exist for facility then auto approve
            if (materialExistForFacility > 0) {
                //auto approved
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                //if not set to approval role
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                db.doUpdate(query);
                //if next group and seq is the same as current then don't send email
                if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos+1)) &&
                        aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos+1))) {
                    result[2] = "Same group";
                }else {
                    result[2] = "New group";
                }
            } //end of item not verified
            //set local variable to null
            query = null;
            rs = null;
            aGroupV = null;
            aGroupSeqV = null;
            aGroup = null;
            aGroupSeq = null;
            qcStatus = null;
            chemType = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            dbrs.close();
        }
    } //end of method

    void approvalRoleCustomIndexing(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
            String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            ResultSet rs = null;
            String query = "";
            String msdsIndexed = "Y";
            try {
                query = "select company_id,line_item,part_id,material_id,pkg_company_msds.fx_msds_verified(material_id,company_id) custom_msds_verified from catalog_add_item cai where request_id = " + reqID;
                dbrs = db.doQuery(query);
                rs = dbrs.getResultSet();
                while (rs.next()) {
                    query = " set custom_indexing_status = ";
                    if ("N".equals(rs.getString("custom_msds_verified"))) {
                        msdsIndexed = "N";
                        query += "'Pending'";
                    }else {
                        query += "'Approved'";
                    }
                    query += " where company_id = '"+rs.getString("company_id")+"' and request_id = "+reqID+
                            " and line_item = "+rs.getString("line_item")+" and part_id = "+rs.getString("part_id");
                    //catalog_add_item
                    db.doUpdate("update catalog_add_item"+query);
                    //catalog_add_item_qc
                    db.doUpdate("update catalog_add_item_qc"+query);
                } //end of while
            }catch (Exception ee) {
                ee.printStackTrace();
            }finally {
                dbrs.close();
            }

            //if item is verified then auto approve msds indexing
            if ("Y".equalsIgnoreCase(msdsIndexed)) {
                //auto approved MSDS Indexing
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                //if qc_status is not set then set qc_status and qc_date here
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+")";
                query += ",qc_status = 'Pending Custom Indexing'";
                query += " where request_id = "+reqID;
                db.doUpdate(query);
                //if next group and seq is the same as current then don't send email
                if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos+1)) &&
                        aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos+1))) {
                    result[2] = "Same group";
                }else {
                    result[2] = "New group";
                }

            } //end of item not verified
            //set local variable to null
            query = null;
            rs = null;
            msdsIndexed = null;
            aGroupV = null;
            aGroupSeqV = null;
            aGroup = null;
            aGroupSeq = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    void approvalRoleSdsQc(String requestId, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
            String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");
            String startingView = (String) approvalDetailH.get("STARTING_VIEW");

            StringBuilder query = null;
            if (!isMsdsRequiredQc(requestId)) {
                //auto approved SDS QC
                approvalRoleAutoApproved(requestId,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(requestId);
                    }else {
                        query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(requestId);
                    }
                    db.doUpdate(query.toString());
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                //if qc_status is not set or setted to Pending Translation then set qc_status and qc_date here
                //the reason for Pending Translation is that any translation has to take place before sending requests to our catalog group
                query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
                query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
                query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(")");
                query.append(",qc_status = 'Pending [M]SDS QC'");
                query.append(" where request_id = ").append(requestId);
                db.doUpdate(query.toString());
                //if next group and seq is the same as current then don't send email
                if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
                        aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
                    result[2] = "Same group";
                }else {
                    result[2] = "New group";
                }
            } //end of item not verified
            //set local variable to null
            query = null;
            aGroupV = null;
            aGroupSeqV = null;
            aGroup = null;
            aGroupSeq = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    private boolean isMsdsRequiredQc(String requestId) {
        boolean result = false;
        DBResultSet dbrs = null;
        try {
            StringBuilder query = new StringBuilder("select cai.company_id,cai.line_item,cai.part_id,cai.material_id");
            query.append(",pkg_company_msds.fx_msds_max_rev_date(cai.material_id,cai.company_id) customer_revision_date");
            query.append(",pkg_company_msds.fx_msds_max_rev_date(cai.material_id) global_revision_date");
            query.append( " from catalog_add_item cai");
            query.append(" where nvl(cai.line_status,'xx') <> 'Rejected' and cai.request_id = ").append(requestId);
            dbrs = db.doQuery(query.toString());
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
                //check to see if material and revision date need to be qced
                query = new StringBuilder("select sum(x) from (");
                query.append(" select count(*) x from msds_qc where material_id = ").append(rs.getBigDecimal("MATERIAL_ID"));
                query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(rs.getDate("GLOBAL_REVISION_DATE")));
                query.append(" and submit_date is not null and approve_date is null");
                query.append(" union all");
                query.append(" select count(*) x from company_msds_qc where material_id = ").append(rs.getBigDecimal("MATERIAL_ID"));
                query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(rs.getDate("CUSTOMER_REVISION_DATE")));
                query.append(" and company_id = '").append(rs.getString("COMPANY_ID")).append("'");
                query.append(" and submit_date is not null and approve_date is null");
                query.append(")");
                //if has data in msds_qc or company_msds_qc
                StringBuilder caiQc = new StringBuilder(" ,status = ");
                StringBuilder updateQuery = new StringBuilder(" set msds_qc_status = ");
                if (!dataCountIsZero(query.toString())) {
                    result = true;
                    updateQuery.append("'Pending'");
                    caiQc.append("'Pending MSDS'");
                }else {
                    updateQuery.append("'Approved'");
                    caiQc.append("'Approved QC'");
                }
                StringBuilder whereQ = new StringBuilder(" where company_id = '").append(rs.getString("COMPANY_ID")).append("' and request_id = ").append(requestId);
                whereQ.append(" and line_item = ").append(rs.getBigDecimal("LINE_ITEM")).append(" and part_id = ").append(rs.getString("PART_ID"));
                //update catalog_add_item
                StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
                tmpQ.append(updateQuery).append(whereQ);
                db.doUpdate(tmpQ.toString());
                //update catalog_add_item_qc
                tmpQ = new StringBuilder("update catalog_add_item_qc");
                tmpQ.append(updateQuery).append(caiQc).append(whereQ);
                db.doUpdate(tmpQ.toString());
            }
        }catch(Exception e) {
            e.printStackTrace();
            result = false;
        }
        finally {
            dbrs.close();
        }
        return result;
    }

    public boolean dataCountIsZero(String query) {
        boolean result = false;
        DBResultSet dbrs = null;
        try {
            dbrs = db.doQuery(query);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                String tmpVal = rs.getString(1);
                if ("0".equalsIgnoreCase(tmpVal)) {
                    result = true;
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbrs.close();
        }
        return result;
    }

    void approvalRoleMsdsIndexing(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
            String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            ResultSet rs = null;
            String query = "";
            String msdsIndexed = "Y";
            try {
                query = "select company_id,line_item,part_id,material_id,pkg_company_msds.fx_msds_indexed(material_id) msds_indexed from catalog_add_item cai where request_id = " + reqID;
                dbrs = db.doQuery(query);
                rs = dbrs.getResultSet();
                while (rs.next()) {
                    query = " set msds_indexing_status = ";
                    if ("N".equals(rs.getString("msds_indexed"))) {
                        msdsIndexed = "N";
                        query += "'Pending'";
                    }else {
                        query += "'Approved'";
                    }
                    query += " where company_id = '"+rs.getString("company_id")+"' and request_id = "+reqID+
                            " and line_item = "+rs.getString("line_item")+" and part_id = "+rs.getString("part_id");
                    //catalog_add_item
                    db.doUpdate("update catalog_add_item"+query);
                    //catalog_add_item_qc
                    db.doUpdate("update catalog_add_item_qc"+query);
                } //end of while
            }catch (Exception ee) {
                ee.printStackTrace();
            }finally {
                dbrs.close();
            }

            //if item is verified then auto approve msds indexing
            if ("Y".equalsIgnoreCase(msdsIndexed)) {
                //auto approved MSDS Indexing
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12,msds_indexing_status = 'Approved' where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9,msds_indexing_status = 'Approved' where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    query = "update catalog_add_request_new set msds_indexing_status = 'Approved' where request_id = "+reqID;
                    db.doUpdate(query);
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                //if qc_status is not set then set qc_status and qc_date here
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+")";
                query += ",msds_indexing_status = 'Pending'";
                query += " where request_id = "+reqID;
                db.doUpdate(query);
                //if next group and seq is the same as current then don't send email
                if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos+1)) &&
                        aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos+1))) {
                    result[2] = "Same group";
                }else {
                    result[2] = "New group";
                }

            } //end of item not verified
            //set local variable to null
            query = null;
            rs = null;
            msdsIndexed = null;
            aGroupV = null;
            aGroupSeqV = null;
            aGroup = null;
            aGroupSeq = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    void approvalRoleQcMaterial(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
            String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
            String qcStatus = (String) approvalDetailH.get("QC_STATUS");
            String chemType = (String) approvalDetailH.get("CHEM_TYPE");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            ResultSet rs = null;
            String query = "";
            String msdsVerified = "Y";
            try {
                query = "select pkg_company_msds.fx_msds_verified(cai.material_id,null,fx_facility_default_locale(f.company_id, f.facility_id)) msds_verified"+
                        " from catalog_add_item cai,catalog_add_request_new carn, facility f where cai.request_id = " + reqID+
                        " and cai.company_id = carn.company_id and cai.request_id = carn.request_id"+
                        " and f.company_id = carn.company_id and f.facility_id = carn.eng_eval_facility_id";
                dbrs = db.doQuery(query);
                rs = dbrs.getResultSet();
                while (rs.next()) {
                    if ("N".equals(rs.getString("msds_verified"))) {
                        msdsVerified = "N";
                        break;
                    }
                } //end of while
            }catch (Exception ee) {
                ee.printStackTrace();
            }finally {
                dbrs.close();
            }

            //if item is verified then auto approve materia qc
            if ("Y".equalsIgnoreCase(msdsVerified)) {
                //catalog_add_item.material_qc_status = Pending
                query = "update catalog_add_item set material_qc_status = 'Approved' where request_id = "+reqID;
                db.doUpdate(query);
                //catalog_add_item_qc.material_qc_status = Pending
                query = "update catalog_add_item_qc set material_qc_status = 'Approved' where request_id = "+reqID;
                db.doUpdate(query);
                //auto approved material qc
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                //catalog_add_item.material_qc_status = Pending
                query = "update catalog_add_item set material_qc_status = 'Pending' where request_id = "+reqID;
                db.doUpdate(query);
                //catalog_add_item_qc.material_qc_status = Pending
                query = "update catalog_add_item_qc set material_qc_status = 'Pending' where request_id = "+reqID;
                db.doUpdate(query);
                //if qc_status is not set then set qc_status and qc_date here
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+")";
                query += ",qc_status = 'Pending Material QC'";
                query += " where request_id = "+reqID;
                db.doUpdate(query);
                //if next group and seq is the same as current then don't send email
                if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos+1)) &&
                        aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos+1))) {
                    result[2] = "Same group";
                }else {
                    result[2] = "New group";
                }

            } //end of item not verified
            //set local variable to null
            query = null;
            rs = null;
            msdsVerified = null;
            aGroupV = null;
            aGroupSeqV = null;
            aGroup = null;
            aGroupSeq = null;
            qcStatus = null;
            chemType = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    void approvalRoleQcItem(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
            String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
            String qcStatus = (String) approvalDetailH.get("QC_STATUS");
            String chemType = (String) approvalDetailH.get("CHEM_TYPE");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            ResultSet rs = null;
            String query = "";
            String itemVerified = "";
            try {
                query = "select fx_item_verified(cai.item_id) item_verified from catalog_add_item cai where line_item = 1 and part_id = 1 and request_id = " + reqID;
                dbrs = db.doQuery(query);
                rs = dbrs.getResultSet();
                while (rs.next()) {
                    itemVerified = rs.getString("item_verified");
                } //end of while
            }catch (Exception ee) {
                ee.printStackTrace();
            }finally {
                dbrs.close();
            }

            //if item is verified then auto approve item qc
            if ("Y".equalsIgnoreCase(itemVerified)) {
                //catalog_add_item.item_qc_status = Pending
                query = "update catalog_add_item set item_qc_status = 'Approved' where request_id = "+reqID;
                db.doUpdate(query);
                //catalog_add_item_qc.item_qc_status = Pending
                query = "update catalog_add_item_qc set item_qc_status = 'Approved' where request_id = "+reqID;
                db.doUpdate(query);
                //auto approved item qc
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                //catalog_add_item.item_qc_status = Pending
                query = "update catalog_add_item set item_qc_status = 'Pending' where request_id = "+reqID;
                db.doUpdate(query);
                //catalog_add_item_qc.item_qc_status = Pending
                query = "update catalog_add_item_qc set item_qc_status = 'Pending' where request_id = "+reqID;
                db.doUpdate(query);
                //if qc_status is not set then set qc_status and qc_date here
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+")";
                query += ",qc_status = 'Pending Item'";
                query += " where request_id = "+reqID;
                db.doUpdate(query);
                //if next group and seq is the same as current then don't send email
                if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos+1)) &&
                        aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos+1))) {
                    result[2] = "Same group";
                }else {
                    result[2] = "New group";
                }

            } //end of item not verified
            //set local variable to null
            query = null;
            rs = null;
            itemVerified = null;
            aGroupV = null;
            aGroupSeqV = null;
            aGroup = null;
            aGroupSeq = null;
            qcStatus = null;
            chemType = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    void approvalRoleMsds(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            ResultSet rs = null;
            String query = "";
            boolean requiredMSDS = false;

            //determine whether MSDS is required or not
            try {
                query = "select m.trade_name from catalog_add_item cai,part p,material m" +
                        " where cai.line_item = 1 and cai.request_id = "+reqID+" and cai.item_id = p.item_id and"+
                        " p.material_id = m.material_id";
                dbrs = db.doQuery(query);
                rs = dbrs.getResultSet();
                while (rs.next()) {
                    String tmp = BothHelpObjs.makeBlankFromNull(rs.getString("trade_name"));
                    if (!tmp.startsWith("MSDS Not Required")) {
                        requiredMSDS = true;
                        break;
                    }
                } //end of while
            }catch (Exception ee) {
                ee.printStackTrace();
            }finally {
                dbrs.close();
            }
            //if MSDS is not required
            if (!requiredMSDS) {
                //auto approved
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                db.doUpdate(query);
                result[2] = "New Group";
            }
            //set local variable to null
            rs = null;
            query = null;
            aGroupV = null;
            aGroupSeqV = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    void approvalRolePartNumber(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            String query = "";
            //if part number is not empty
            if (HelpObjs.countQuery(db,"select count(*) from catalog_add_request_new where (cat_part_no is null or vsize(cat_part_no) < 2 or ascii(cat_part_no) = 160 or cat_part_no like '%?%')  and request_id = " + reqID) == 0) {
                //auto approved
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                db.doUpdate(query);
                result[2] = "New Group";
            }
            //set local variable to null
            query = null;
            aGroupV = null;
            aGroupSeqV = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    void approvalRoleCRA(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            ResultSet rs = null;
            //allowing CRA to run parallel with others approval group
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");
            Integer tempCurrentGroup = new Integer(aGroup);
            String query = "select approval_group from vv_catalog_add_request_status where punchout = 'Y'";
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            int punchoutGroup = 0;
            while (rs.next()){
                punchoutGroup = rs.getInt("approval_group");
            }
            //adjusting current group position
            if (tempCurrentGroup.intValue() < punchoutGroup) {
                query = "select count(*) from vv_catalog_add_request_status ars,vv_chemical_approval_role car,catalog_add_approval caa"+
                        " where ars.punchout = 'Y' and ars.approval_group = car.approval_group and car.approval_role = caa.approval_role"+
                        " and car.facility_id = caa.facility_id and car.catalog_id = caa.catalog_id and car.catalog_company_id = caa.catalog_company_id and caa.request_id = "+reqID;
                if (HelpObjs.countQuery(db,query) > 0) {
                    int tempCurrentGroupPos = currentGroupPos;
                    if (tempCurrentGroupPos+1 < aGroupV.size()) {
                        tempCurrentGroupPos++;
                    }
                    Integer tempGroup = new Integer((String)aGroupV.elementAt(tempCurrentGroupPos));
                    if (punchoutGroup == tempGroup.intValue()) {
                        if (tempCurrentGroupPos+1 < aGroupV.size()) {
                            currentGroupPos++;
                            if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                                if ("Y".equalsIgnoreCase(eEvaluation)) {
                                    query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                                }else {
                                    query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                                }
                                db.doUpdate(query);
                                result[0] = "Done";
                            }else {
                                nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                                setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                            }
                        }
                    }
                }else {
                    query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                            ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                    db.doUpdate(query);
                    result[2] = "Punchout group";
                }
            }else {
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                db.doUpdate(query);
                result[2] = "Punchout group";
            }
            //set local variable to null
            approvalRoleV = null;
            aGroupV = null;
            aGroup = null;
            aGroupSeqV = null;
            tempCurrentGroup = null;
            query = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            dbrs.close();
        }
    } //end of method

    void approvalRoleSourcing(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            ResultSet rs = null;
            String query = "select PKG_CONTRACT_SETUP.fx_need_pricing(cai.item_id,carn.catalog_id,carn.cat_part_no,carn.part_group_no,carn.eng_eval_facility_id,carn.catalog_company_id) need_pricing"+
                    " from catalog_add_request_new carn, catalog_add_item cai where carn.company_id = cai.company_id"+
                    " and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 and carn.request_id = "+reqID;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            boolean autoApprovedContractPricing = false;
            while (rs.next()) {
                String tmp = rs.getString("need_pricing");
                if ("N".equalsIgnoreCase(tmp)) {
                    autoApprovedContractPricing = true;
                }
            } //end of while

            if (autoApprovedContractPricing) {
                //auto approve Contract Pricing
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                //set next status to next approval role
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                db.doUpdate(query);
                result[2] = "New Group";
            }
            //set local variables to null
            approvalRoleV = null;
            aGroupV = null;
            aGroupSeqV = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            dbrs.close();
        }
    } //end of method

    public boolean isPriceQuoteRequired(String reqID, String itemID) throws Exception{
        boolean priceQuoteRequired = false;
        String query = "select fx_fac_part_base_price(eng_eval_facility_id,catalog_id,cat_part_no,part_group_no,catalog_company_id) baseline_price"+
                " from catalog_add_request_new where request_id = "+reqID;
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            String baseLinePrice = "";
            while (rs.next()){
                baseLinePrice = rs.getString("baseline_price");
            }

            if (BothHelpObjs.isBlankString(baseLinePrice)) {
                priceQuoteRequired = HelpObjs.countQuery(db,"select count(*) from price_quote_last_stat_view where last_quote_date > sysdate -365 and item_id = "+itemID) == 0;
            }
        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }finally {
            dbrs.close();
        }
        return priceQuoteRequired;
    } //end of method

    void approvalRolePricing(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            ResultSet rs = null;
            result[2] = "Punchout group";       //treating pricing like punchout since buyers doesn't want email notification
            dbrs = db.doQuery("SELECT DISTINCT a.catalog_id,a.cat_part_no,cai.item_id,f.facility_id,f.inventory_group_default,igd.hub branch_plant"+
                    " FROM catalog_add_request_new a,catalog_add_item cai,facility f,inventory_group_definition igd"+
                    " WHERE a.request_id = "+reqID+" AND a.eng_eval_facility_id = f.facility_id AND"+
                    " a.company_id = cai.company_id and a.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 and"+
                    " f.inventory_group_default = igd.inventory_group");
            rs=dbrs.getResultSet();
            String cItemID = "0";
            String facID = "";
            String defaultInventoryGroup = "";
            String tmpCatPartNo = "";
            String tmpCatalogID = "";
            String branchPlant = "";
            String companyName = "";
            while (rs.next()){
                cItemID = rs.getString("item_id");
                facID = rs.getString("facility_id");
                defaultInventoryGroup = rs.getString("inventory_group_default");
                branchPlant = rs.getString("branch_plant");
                tmpCatPartNo = BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no"));
                tmpCatalogID = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id"));
            }
            if ("Ray".equalsIgnoreCase(db.getClient())) {
                companyName = "RAYTHEON";
            }else if ("LMCO".equalsIgnoreCase(db.getClient())) {
                companyName = "LOCKHEED";
            }else if ("SD".equalsIgnoreCase(db.getClient())) {
                companyName = "SAUER_DANFOSS";
            }else {
                companyName = db.getClient().toUpperCase();
            }
            //7-02-02 if starting from new_part then check price_quote_request
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
            String sView = (String) approvalDetailH.get("STARTING_VIEW");
            String eEvaluation = (String) approvalDetailH.get("EVAULATION");
            String query = "";
            //System.out.println("--------- current group pricing: "+currentGroupPos+"-approval_group: "+aGroupV+"=a_group_seq:"+aGroupSeqV);
            boolean requiredPriceQuote = false;
            if ("2".equalsIgnoreCase(sView) || "3".equalsIgnoreCase(sView)) {
                requiredPriceQuote = isPriceQuoteRequired(reqID,cItemID);
                //if price_quote_request already exist or fac_item contains catalog_price then ship over Pricing
                if (!requiredPriceQuote) {
                    //if Pricing is the last group in the approval process then declare as finish.  Otherwise, skip to next group
                    if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                        if ("Y".equalsIgnoreCase(eEvaluation)) {
                            query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                        }else {
                            query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                        }
                        db.doUpdate(query);
                        result[0] = "Done";
                    }else {  //Pricing is not the last approval role for this request, then skip pricing and set to next group
                        //auto approve for PRICING
                        approvalRoleAutoApproved(reqID,nextApprovalRole);
                        //now find the next approval role
                        currentGroupPos++;
                        if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                            if ("Y".equalsIgnoreCase(eEvaluation)) {
                                query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                            }else {
                                query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                            }
                            db.doUpdate(query);
                            result[0] = "Done";
                        }else {
                            //System.out.println("--------- next status current group pricing: "+currentGroupPos+"-approval_group: "+aGroupV+"=a_group_seq:"+aGroupSeqV);
                            nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                            setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                        }
                    }
                }else { //requiring price quote
                    if (currentGroupPos == aGroupV.size()) {    //this mean that we reached the end of the approval process
                        if ("Y".equalsIgnoreCase(eEvaluation)) {
                            query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                        }else {
                            query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                        }
                        db.doUpdate(query);
                        result[0] = "Done";
                    }else {  //Pricing is not the last approval role for this request, then skip pricing and set to next group
                        //Raytheon requested that we not wait for price quote at this point
                        if (HelpObjs.countQuery(db,"select count(*) from tcmis_feature where feature = 'AutoPricing' and feature_mode = 1") > 0) {
                            //auto approve for PRICING
                            approvalRoleAutoApproved(reqID,nextApprovalRole);
                            //now find the next approval role
                            currentGroupPos++;
                            if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                                if ("Y".equalsIgnoreCase(eEvaluation)) {
                                    query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                                }else {
                                    query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                                }
                                db.doUpdate(query);
                                result[0] = "Done";
                            }else {
                                nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                                setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                            }
                        }else { //else it is not Raytheon
                            query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                                    ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                            db.doUpdate(query);
                            //if next group and seq is the same as current then don't send email
                            if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos+1)) &&
                                    aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos+1))) {
                                result[2] = "Same group";
                            }else {
                                result[2] = "New group";
                            }
                        } //end of else it is not Raytheon
                    }  //end of Pricing is not the last group
                } //end of requiring price quote
            }else {
                if (currentGroupPos == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {  //Pricing is not the last approval role for this request, then skip pricing and set to next group
                    //Raytheon requested that we not wait for price quote at this point
                    if (HelpObjs.countQuery(db,"select count(*) from tcmis_feature where feature = 'AutoPricing' and feature_mode = 1") > 0) {
                        //auto approve for PRICING
                        approvalRoleAutoApproved(reqID,nextApprovalRole);
                        //now find the next approval role
                        currentGroupPos++;
                        if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                            if ("Y".equalsIgnoreCase(eEvaluation)) {
                                query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                            }else {
                                query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                            }
                            db.doUpdate(query);
                            result[0] = "Done";
                        }else {
                            nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                            setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                        }
                    }else { //else it is not Raytheon
                        query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                                ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                        db.doUpdate(query);
                        //if next group and seq is the same as current then don't send email
                        if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos+1)) &&
                                aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos+1))) {
                            result[2] = "Same group";
                        }else {
                            result[2] = "New group";
                        }
                    } //end of else it is not Raytheon
                }  //end of Pricing is not the last group
                requiredPriceQuote = true;
            }
            //request price quote
            if (!BothHelpObjs.isBlankString(cItemID) && !"0".equalsIgnoreCase(cItemID) && requiredPriceQuote) {
                if (HelpObjs.countQuery(db,"select count(*) from price_quote_view where request_date > sysdate - 365 and item_id = "+cItemID) == 0) {
                    String postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/cgi-bin/rpq.cgi?item="+cItemID,"&item="+cItemID+"&per=86030&fac="+facID+"&bp="+branchPlant+"&comp="+companyName,"text/html");
                }
            }
            //set local variables to null
            cItemID = null;
            facID = null;
            defaultInventoryGroup = null;
            tmpCatPartNo = null;
            tmpCatalogID = null;
            branchPlant = null;
            companyName = null;
            approvalRoleV = null;
            aGroupV = null;
            aGroup = null;
            aGroupSeqV = null;
            aGroupSeq = null;
            sView = null;
            eEvaluation = null;
            query = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            dbrs.close();
        }
    } //end of method

    boolean requiredCatalogPrice(String reqID) {
        boolean result = false;
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            String query = "select fx_fac_part_catalog_price(eng_eval_facility_id,catalog_id,cat_part_no,part_group_no,catalog_company_id) catalog_price"+
                    " from catalog_add_request_new where request_id = "+reqID;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            String catalogPrice = "";
            while (rs.next()) {
                catalogPrice = rs.getString("catalog_price");
            }
            if (BothHelpObjs.isBlankString(catalogPrice)) {
                result = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = true;
        }finally {
            dbrs.close();
        }
        return result;
    } //end of method

    void approvalRoleProductionMaterial(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");
            String startingView = (String) approvalDetailH.get("STARTING_VIEW");

            StringBuilder query = new StringBuilder("select count(*) from catalog_add_request_new where production_material = 'Y' and request_id = ").append(reqID);
            if (dataCountIsZero(query.toString())) {
                //auto approve production material
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
                        query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
                    }else {
                        query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
                    }
                    db.doQuery(query.toString());
                    result[0] = "Done";
                }else {
                    //handle parallel approval roles with auto approval
                    //continue to go thru next approval roles if it's in the same group
                    //reason for +1 is next approval role
                    if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
                        if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
                                aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
                            nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                            setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                        }
                    }
                }
            }else {
                //set next status to directed charge
                query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
                query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
                query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
                db.doQuery(query.toString());
                //if next group and seq is the same as current then don't send email
                String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
                String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
                if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
                        aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
                    result[2] = "Same group";
                }else {
                    result[2] = "New group";
                }
                //handle parallel approval roles with auto approval
                //continue to go thru next approval roles if it's in the same group
                //reason for +1 is next approval role
                //reason for +2 is next next approval role
                if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
                    if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
                            aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
                        nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
                        setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
                    }
                }
            }
            //set local variables to null
            approvalRoleV = null;
            aGroupV = null;
            aGroupSeqV = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    void approvalRoleDirectedCharge(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");
            String query = "";
            if (HelpObjs.countQuery(db,"select count(*) from catalog_add_user_group where charge_number is null and request_id = "+reqID) == 0) {
                //auto approve directed charge
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                //set next status to directed charge
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                db.doUpdate(query);
                result[2] = "New Group";
            }
            //set local variables to null
            approvalRoleV = null;
            aGroupV = null;
            aGroupSeqV = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    void approvalRoleLabelInfo(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");
            String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
            String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
            //check to see if need label info, if not auto approve Label Info
            String query = "select count(*) from inventory_group_item_label a, catalog_add_request_new b, catalog_add_item cai, facility f where a.health is not null and a.flamability is not null and a.reactivity is not null"+
                    " and a.chemical_storage is not null and a.signal_word is not null and a.personal_protective_equipment is not null and a.disposal_code is not null"+
                    " and a.hazard_code1 is not null and a.item_id = cai.item_id and a.inventory_group = f.inventory_group_default and b.eng_eval_facility_id = f.facility_id and b.request_id = "+reqID+
                    " and b.company_id = cai.company_id and b.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1";
            if (HelpObjs.countQuery(db,query) < 1) {
                //set next status to Label Info
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                db.doUpdate(query);
                result[2] = "New Group";
                //if next group and seq is the same as current then call next approval group as well.
                //In this case, the next group is Pricing
                String tmpQuery = "select count(*) from catalog_add_request_new carn, vv_chemical_approval_role car, vv_chemical_approval_role car2"+
                        " where carn.eng_eval_facility_id = car.facility_id and carn.catalog_id = car.catalog_id and carn.catalog_company_id = car.catalog_company_id"+
                        " and car.approval_role = 'Label Info' and car.active = 'Y' and carn.eng_eval_facility_id = car2.facility_id"+
                        " and carn.catalog_id = car2.catalog_id and carn.catalog_company_id = car2.catalog_company_id and car2.approval_role = 'Pricing' and car2.active = 'Y'"+
                        " and car.approval_group = car2.approval_group and car.approval_group_seq = car2.approval_group_seq"+
                        " and carn.requesT_id = "+reqID;
                if (HelpObjs.countQuery(db,tmpQuery) > 0) {
                    DBResultSet dbrs = null;
                    String cItemID = "0";
                    String facID = "";
                    String defaultInventoryGroup = "";
                    String tmpCatPartNo = "";
                    String tmpCatalogID = "";
                    String branchPlant = "";
                    String companyName = "";
                    if ("Ray".equalsIgnoreCase(db.getClient())) {
                        companyName = "RAYTHEON";
                    }else if ("LMCO".equalsIgnoreCase(db.getClient())) {
                        companyName = "LOCKHEED";
                    }else if ("SD".equalsIgnoreCase(db.getClient())) {
                        companyName = "SAUER_DANFOSS";
                    }else {
                        companyName = db.getClient().toUpperCase();
                    }
                    try {
                        ResultSet rs = null;
                        dbrs = db.doQuery("select distinct a.catalog_id,a.catalog_company_id,a.cat_part_no,cai.item_id,f.facility_id,f.inventory_group_default,f2.branch_plant from catalog_add_request_new a,catalog_add_item cai," +
                                "facility f,facility f2 where a.request_id = " + reqID + " and a.eng_eval_facility_id = f.facility_id and f.preferred_warehouse = f2.facility_id"+
                                " and a.company_id = cai.company_id and a.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1");
                        rs = dbrs.getResultSet();
                        while (rs.next()) {
                            cItemID = rs.getString("item_id");
                            facID = rs.getString("facility_id");
                            defaultInventoryGroup = rs.getString("inventory_group_default");
                            branchPlant = rs.getString("branch_plant");
                            tmpCatPartNo = BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no"));
                            tmpCatalogID = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id"));
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        dbrs.close();
                    }
                    //Seagate always require price quotes
                    boolean requiredPriceQuote = true;
                    if (!BothHelpObjs.isBlankString(cItemID) && !"0".equalsIgnoreCase(cItemID) && requiredPriceQuote) {
                        if (HelpObjs.countQuery(db,"select count(*) from price_quote_view where request_date > sysdate - 365 and item_id = "+cItemID) == 0) {
                            String postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/cgi-bin/rpq.cgi?item="+cItemID,"&item="+cItemID+"&per=86030&fac="+facID+"&bp="+branchPlant+"&comp="+companyName,"text/html");
                        }
                    }
                }
            }else {
                //auto approve Label Info
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }
            //set local variables to null
            approvalRoleV = null;
            aGroupV = null;
            aGroupSeqV = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    void approvalRoleItarSpec(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            //if request has specs marked as ITAR
            StringBuilder query = new StringBuilder("select count(*) from catalog_add_spec where itar = 'Y' and request_id = ").append(reqID);
            //no specs are marked as ITAR
            if (this.dataCountIsZero(query.toString())) {
                //auto approve Spec
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
                    }else {
                        query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
                    }
                    db.doQuery(query.toString());
                    result[0] = "Done";
                }else {
                    //handle parallel approval roles with auto approval
                    //continue to go thru next approval roles if it's in the same group
                    //reason for +1 is next approval role
                    if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
                        if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
                                aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
                            nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                            setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                        }
                    }
                }
            }else {
                //set next status to ITAR Spec
                query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate,approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
                query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
                query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
                db.doQuery(query.toString());
                //if next group and seq is the same as current then don't send email
                String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
                String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
                if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
                        aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
                    result[2] = "Same group";
                }else {
                    result[2] = "New group";
                }
                //handle parallel approval roles with auto approval
                //continue to go thru next approval roles if it's in the same group
                //reason for +1 is next approval role
                //reason for +2 is next next approval role
                if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
                    if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
                            aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
                        nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
                        setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
                    }
                }
            }
            //set local variables to null
            approvalRoleV = null;
            aGroupV = null;
            aGroupSeqV = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    void approvalRoleTCMSpec(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            ResultSet rs = null;
            String query = "select nvl(spec_library,'xxx') spec_library from catalog_add_spec where request_id = "+reqID;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            boolean autoApprovedSpec = true;
            while (rs.next()) {
                if (!"global".equalsIgnoreCase(rs.getString("spec_library"))) {
                    autoApprovedSpec = false;
                    break;
                }
            } //end of while

            if (autoApprovedSpec) {
                //auto approve Spec
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                //catalog_add_spec_qc
                //first remove record
                db.doUpdate("delete from catalog_add_spec_qc where request_id = "+reqID);
                //catalog_add_spec_qc
                query = "insert into catalog_add_spec_qc (company_id,request_id,spec_id,spec_name,spec_title"+
                        ",spec_version,spec_amendment,spec_date,content,on_line,spec_library,coc,coa,itar,line_item,spec_source)"+
                        " select company_id,request_id,spec_id,spec_name,spec_title"+
                        ",spec_version,spec_amendment,spec_date,content,on_line,spec_library,coc,coa,itar,line_item,"+
                        "decode(spec_source,null,'catalog_add_spec_qc','catalog_add_spec','catalog_add_spec_qc',spec_source)"+
                        " from catalog_add_spec where request_id = "+reqID;
                db.doUpdate(query);

                //set next status to TCM Spec
                query = "update catalog_add_request_new set qc_status = 'Pending Spec', approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                db.doUpdate(query);
                result[2] = "New Group";
            }
            //set local variables to null
            approvalRoleV = null;
            aGroupV = null;
            aGroupSeqV = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            dbrs.close();
        }
    } //end of method

    void approvalRoleTCMProcess(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");
            String query = "";

            //auto approve Process
            approvalRoleAutoApproved(reqID,nextApprovalRole);
            //now find the next approval role
            currentGroupPos++;
            if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                if ("Y".equalsIgnoreCase(eEvaluation)) {
                    query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                }else {
                    query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                }
                db.doUpdate(query);
                result[0] = "Done";
            }else {
                nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }
            //set local variables to null
            approvalRoleV = null;
            aGroupV = null;
            aGroupSeqV = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method


    public void setCatPartNeedsReview(String reqID, String userid) {
        String query = "select distinct nvl(carn.cat_part_no,' ') cat_part_no, nvl(carn.old_cat_part_no,' ') old_cat_part_no, nvl(carn.cat_add_part_needs_review,'Y') part_needs_review,cai.item_id"+
                " from catalog_add_request_new carn, catalog_add_item cai where carn.company_id = cai.company_id and carn.request_id = cai.request_id"+
                " and cai.line_item = 1 and cai.part_id = 1 and carn.request_id = "+reqID;
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            String catPartNo = "";
            String oldCatPartNo = "";
            String partNeedsReview = "";
            String itemId = "";
            while (rs.next()){
                catPartNo = rs.getString("cat_part_no");
                oldCatPartNo = rs.getString("old_cat_part_no");
                partNeedsReview = rs.getString("part_needs_review");
                itemId = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
            }
            if ("N".equals(partNeedsReview)) {
                if (!catPartNo.equals(oldCatPartNo)) {
                    query = "update catalog_add_request_new set cat_add_part_needs_review = fx_cat_add_part_needs_review(catalog_id,cat_part_no,'"+itemId+"',"+userid+",catalog_company_id)"+
                            ",old_cat_part_no = cat_part_no where request_id = "+reqID;
                    db.doUpdate(query);
                }
            }else {
                query = "update catalog_add_request_new set cat_add_part_needs_review = fx_cat_add_part_needs_review(catalog_id,cat_part_no,'"+itemId+"',"+userid+",catalog_company_id)"+
                        ",old_cat_part_no = cat_part_no where request_id = "+reqID;
                db.doUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbrs.close();
        }
    } //end of method

    void approvalRolePartNeedReview(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        DBResultSet dbrs = null;
        try {
            Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
            Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
            Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
            String eEvaluation = (String) approvalDetailH.get("EVALUATION");

            ResultSet rs = null;
            String query = "select cat_add_part_needs_review from catalog_add_request_new carn where request_id = "+reqID;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            String partNeedReview = "Y";
            while (rs.next()) {
                partNeedReview = rs.getString("cat_add_part_needs_review");
            } //end of while

            if ("N".equalsIgnoreCase(partNeedReview)) {
                //auto approve part
                approvalRoleAutoApproved(reqID,nextApprovalRole);
                //now find the next approval role
                currentGroupPos++;
                if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
                    if ("Y".equalsIgnoreCase(eEvaluation)) {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                    }else {
                        query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                    }
                    db.doUpdate(query);
                    result[0] = "Done";
                }else {
                    nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                    setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                }
            }else {
                //set next status
                query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                db.doUpdate(query);
                result[2] = "New Group";
            }
            //set local variables to null
            approvalRoleV = null;
            aGroupV = null;
            aGroupSeqV = null;
            eEvaluation = null;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            dbrs.close();
        }
    } //end of method

    String[] approvalRoleAutoApproved(String reqID, String approvalRole) throws Exception {
        String[] result = new String[2];
        try {
            String query = "insert into catalog_add_approval(company_id,request_id,chemical_approver,approval_date,status,reason,approval_role,facility_id,catalog_id,catalog_company_id)"+
                    " select distinct carn.company_id,carn.request_id,-1,sysdate,'Approved','Auto approved','"+approvalRole+"',caug.facility_id,carn.catalog_id,carn.catalog_company_id from"+
                    " catalog_add_user_group caug, catalog_add_request_new carn where caug.request_id = carn.request_id and carn.request_id = "+reqID;
            db.doUpdate(query);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    } //end of method

    void setNextApprovalRole(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
        try {
            Vector roleFunctionV = (Vector) approvalDetailH.get("ROLE_FUNCTION");
            String roleFunction = (String)roleFunctionV.elementAt(currentGroupPos+1);
            if ("Auto Approved".equalsIgnoreCase(roleFunction)) {
                approvalRoleAutoApproved(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            } else if ("Verify Material QC".equalsIgnoreCase(roleFunction)) {
                approvalRoleQcMaterial(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Item".equalsIgnoreCase(roleFunction)) {
                approvalRoleQcItem(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify CRA".equalsIgnoreCase(roleFunction)) {
                approvalRoleCRA(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Pricing".equalsIgnoreCase(roleFunction)) {
                approvalRolePricing(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Production Material".equalsIgnoreCase(roleFunction)) {
                approvalRoleProductionMaterial(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Sourcing".equalsIgnoreCase(roleFunction)) {
                approvalRoleSourcing(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Label".equalsIgnoreCase(roleFunction)) {
                approvalRoleLabelInfo(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify ITAR Spec".equalsIgnoreCase(roleFunction)) {
                approvalRoleItarSpec(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Spec".equalsIgnoreCase(roleFunction)) {
                approvalRoleTCMSpec(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Material".equalsIgnoreCase(roleFunction)) {
                approvalRoleMaterialExistForFacility(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Part".equalsIgnoreCase(roleFunction)) {
                approvalRolePartNeedReview(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify All".equalsIgnoreCase(roleFunction)) {
                approvalRoleTCMProcess(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Directed Charge".equalsIgnoreCase(roleFunction)) {
                approvalRoleDirectedCharge(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Msds".equalsIgnoreCase(roleFunction)) {
                approvalRoleMsds(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Custom Indexing".equalsIgnoreCase(roleFunction)) {
                approvalRoleCustomIndexing(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify MSDS Indexing".equalsIgnoreCase(roleFunction)) {
                approvalRoleMsdsIndexing(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify Part Number".equalsIgnoreCase(roleFunction)) {
                approvalRolePartNumber(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else if ("Verify SDS QC".equalsIgnoreCase(roleFunction)) {
                approvalRoleSdsQc(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            }else {
                Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
                Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
                String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
                String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
                String query = "update catalog_add_request_new set approval_group_seq = "+(String)aGroupSeqV.elementAt(currentGroupPos+1)+
                        ",request_status = (select request_status from vv_catalog_add_request_status where approval_group = "+(String)aGroupV.elementAt(currentGroupPos+1)+") where request_id = "+reqID;
                db.doUpdate(query);
                //if next group and seq is the same as current then don't send email
                if (aGroup.equals((String)aGroupV.elementAt(currentGroupPos+1)) &&
                        aGroupSeq.equals((String)aGroupSeqV.elementAt(currentGroupPos+1))) {
                    result[2] = "Same group";
                }else {
                    result[2] = "New group";
                }
                //set local variable to null
                query = null;
                aGroupV = null;
                aGroupSeqV = null;
                aGroup = null;
                aGroupSeq = null;
            } //end of next approval role is not a special role need additional attention
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

  void approvalRoleAutoApproved(String reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
          Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
          String eEvaluation = (String) approvalDetailH.get("EVALUATION");
          String startingView = (String) approvalDetailH.get("STARTING_VIEW");

          //auto approve list approval
          approvalRoleAutoApproved(reqID,nextApprovalRole);
          //now find the next approval role
          currentGroupPos++;
          if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
              StringBuilder query;
              if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
                  query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
              }else {
                  query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
              }
              db.doQuery(query.toString());
              result[0] = "Done";
          }else {
              //handle parallel approval roles with auto approval
              //continue to go thru next approval roles if it's in the same group
              //reason for +1 is next approval role
              if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
                  if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
                      aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
                      nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
                      setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
                  }
              }
          }

			//set local variables to null
			approvalRoleV = null;
			aGroupV = null;
			eEvaluation = null;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	} //end of method

    public String[] setNextStatus(String reqID) throws Exception {
        String[] result = new String[4];
        result[0] = "Next Status";
        result[2] = "New group";
        result[3] = "N";          //not a POSS request
        DBResultSet dbrs = null;
        try {
            ResultSet rs = null;
            //first get request info
            String query = "select carn.eng_eval_facility_id,carn.poss,carn.qc_status,carn.engineering_evaluation,carn.starting_view,ars.approval_group,nvl(carn.approval_group_seq,'0') approval_group_seq"+
                    " from catalog_add_request_new carn,vv_catalog_add_request_status ars where carn.request_status = ars.request_status and"+
                    " carn.request_id = "+reqID;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            String eEvaluation = "";
            String sView = "";
            String aGroup = "0";
            String aGroupSeq = "0";
            String aApprovalRole = "";
            String qcStatus = "";
            String poss = "N";
            while (rs.next()){
                eEvaluation = BothHelpObjs.makeBlankFromNull(rs.getString("engineering_evaluation"));
                sView = BothHelpObjs.makeBlankFromNull(rs.getString("starting_view"));
                aGroup = rs.getString("approval_group");
                aGroupSeq = rs.getString("approval_group_seq");
                qcStatus = BothHelpObjs.makeBlankFromNull(rs.getString("qc_status"));
                poss = rs.getString("poss");
                setFacilityId(rs.getString("eng_eval_facility_id"));
            }

            //if POSS
            if ("Y".equalsIgnoreCase(poss)) {
                result[3] = "Y";          // POSS request
                //return (setPOSSNextStatus(reqID,sView,aGroup,aGroupSeq));
            }
            //OTHERWISE, RUN logic below for everything else

            result[1] = eEvaluation;
            query = "select distinct caa.approval_role from catalog_add_approval caa,vv_chemical_approval_role car where caa.approval_role = car.approval_role and"+
                    " caa.facility_id = car.facility_id and caa.catalog_id = car.catalog_id and caa.catalog_company_id = car.catalog_company_id and car.approval_group = "+aGroup+
                    " and car.approval_group_seq = "+aGroupSeq+" and caa.request_id = "+reqID+
                    " and car.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = "+reqID+")";
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()) {
                aApprovalRole = rs.getString("approval_role");
            }

            //next approval info
            String chemType = "new_material";        //set this so if for some reason I search with this search then it should be a hint
            if ("Y".equalsIgnoreCase(eEvaluation)) {
                if ("2".equalsIgnoreCase(sView)) {
                    chemType = "reorder_eng_eval";
                }else {
                    chemType = "eng_eval";
                }
            }else {
                if ("0".equalsIgnoreCase(sView)) {
                    chemType = "new_material";
                }else if ("1".equalsIgnoreCase(sView)) {
                    chemType = "new_size";
                }else if ("2".equalsIgnoreCase(sView)) {
                    chemType = "new_part";
                }else if ("3".equalsIgnoreCase(sView)) {
                    chemType = "new_approval";
                }else if ("4".equalsIgnoreCase(sView)) {
                    chemType = "new_approval";
                }else if ("5".equalsIgnoreCase(sView)) {
                    chemType = "new_approval";
                }else if ("6".equalsIgnoreCase(sView)) {
                    chemType = "new_material";
                }else if ("7".equalsIgnoreCase(sView)) {
                    chemType = "new_approval";    //new approval code
                }else {
                    chemType = "new_material";
                }
            }

            query = "select a.approval_role,a.approval_group,a.approval_group_seq,a.role_function from vv_chemical_approval_role a, catalog_add_approval b"+
                    " where a."+chemType+" = 'Y' and a.facility_id = b.facility_id(+) and a.approval_role = b.approval_role(+) and b.approval_role is null and b.request_id(+) = "+reqID+
                    " and a.active = 'Y' and a.catalog_id = b.catalog_id(+) and a.catalog_company_id = b.catalog_company_id(+)"+
                    " and (a.facility_id,a.catalog_id,a.catalog_company_id) = (select eng_eval_facility_id,catalog_id,catalog_company_id from catalog_add_request_new where request_id = "+reqID+")"+
                    " and a.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = "+reqID+")"+
                    " order by a.approval_group,a.approval_group_seq,a.approval_role";

            Vector aGroupV = new Vector();
            Vector aGroupSeqV = new Vector();
            Vector approvalRoleV = new Vector();
            Vector roleFunctionV = new Vector();
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                aGroupV.addElement(rs.getString("approval_group"));
                aGroupSeqV.addElement(rs.getString("approval_group_seq"));
                approvalRoleV.addElement(rs.getString("approval_role"));
                roleFunctionV.addElement(rs.getString("role_function"));
            }

            //if above query return no record then move status to complete
            if (aGroupV.size() == 0) {
                if ("Y".equalsIgnoreCase(eEvaluation)) {
                    query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                }else {
                    query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                }
                db.doUpdate(query);
                result[0] = "Done";
                return result;
            }  //end of no record returned

            int currentGroupPos = -1;
            if (currentGroupPos+1 == aGroupSeqV.size()) {  //reach the end of the approval seq
                if ("Y".equalsIgnoreCase(eEvaluation)) {
                    query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 12 where request_id = "+reqID;
                }else {
                    query = "update catalog_add_request_new set approval_group_seq = 0,request_status = 9 where request_id = "+reqID;
                }
                result[0] = "Done";
            }else {  //otherwise, find and set the next approval role, group and seq
                String nextApprovalRole = (String)approvalRoleV.elementAt(currentGroupPos+1);
                Hashtable approvalDetailH = new Hashtable(13);
                approvalDetailH.put("APPROVAL_ROLE",approvalRoleV);
                approvalDetailH.put("APPROVAL_GROUP",aGroupV);
                approvalDetailH.put("APPROVAL_GROUP_SEQ",aGroupSeqV);
                approvalDetailH.put("CURRENT_ROLE",aApprovalRole);
                approvalDetailH.put("CURRENT_GROUP",aGroup);
                approvalDetailH.put("CURRENT_GROUP_SEQ",aGroupSeq);
                approvalDetailH.put("STARTING_VIEW",sView);
                approvalDetailH.put("EVALUATION",eEvaluation);
                approvalDetailH.put("QC_STATUS",qcStatus);
                approvalDetailH.put("POSS",poss);
                approvalDetailH.put("CHEM_TYPE",chemType);
                approvalDetailH.put("ROLE_FUNCTION",roleFunctionV);
                setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
            } //end of going the thru the next approval role
        }catch (Exception e) {
            result[0] = "Error";
            e.printStackTrace();
            HelpObjs.sendMail(db,"Set next status failed (request ID: "+reqID+")","Unable to set next status for request.",86030,false);
            throw e;
        }finally {
            dbrs.close();
        }
        //System.out.println("\n\n------- result: "+result[0]+" - "+result[1]+" - "+result[2]);
        return result;
    }

    public Hashtable getApprovalDetailInfo(String reqID) {
        Hashtable result = new Hashtable(8);
        DBResultSet dbrs = null;
        try {
            String query = "select p.last_name||', '||p.first_name requestor_name,ars.request_status_desc,carn.request_date,"+
                    "f.facility_name,cai.material_desc,cai.manufacturer,"+
                    "cai.components_per_item||' x '||cai.part_size||' '||cai.size_unit||' in '||cai.pkg_style packaging"+
                    " from catalog_add_request_new carn,catalog_add_item cai,facility f,personnel p,vv_catalog_add_request_status ars"+
                    " where carn.request_id = cai.request_id and "+
                    " carn.requestor = p.personnel_id and carn.request_status = ars.request_status and"+
                    " carn.eng_eval_facility_id = f.facility_id and carn.request_id = "+reqID;
            ResultSet rs = null;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            Vector headerV = new Vector(5);
            Vector materialV = new Vector(10);
            boolean firstTime = true;
            while (rs.next()){
                if (firstTime) {
                    headerV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("requestor_name")));
                    headerV.addElement(BothHelpObjs.formatDate("toCharfromDB",rs.getString("request_date")));
                    headerV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("request_status_desc")));
                    headerV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
                    headerV.addElement(" ");
                    firstTime = false;
                }
                String[] oa = new String[3];
                oa[0] = BothHelpObjs.makeBlankFromNull(rs.getString("material_desc"));
                oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
                oa[2] = BothHelpObjs.makeBlankFromNull(rs.getString("manufacturer"));
                materialV.addElement(oa);
            }
            result.put("HEADER_INFO",headerV);
            result.put("MATERIAL_INFO",materialV);

            //now get approval role list
            query = "select * from cat_add_status_view where request_id = "+reqID+
                    " and application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = "+reqID+")"+
                    " order by approval_group,approval_group_seq,approval_role,chemical_approvers";
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            Vector roleV = new Vector();
            Vector statusV = new Vector();
            Vector approverNameV = new Vector();
            Vector dateV = new Vector();
            Vector reasonV = new Vector();
            Vector approvalGroup = new Vector();
            while (rs.next()){
                roleV.addElement(rs.getString("approval_role"));
                statusV.addElement(rs.getString("status"));
                approverNameV.addElement(rs.getString("chemical_approvers"));
                dateV.addElement(BothHelpObjs.formatDate("toCharfromDB",rs.getString("approval_date")));
                reasonV.addElement(rs.getString("reason"));
                approvalGroup.addElement(rs.getString("approval_group"));
            }
            result.put("ROLE",roleV);
            result.put("STATUS",statusV);
            result.put("APPROVER_NAME",approverNameV);
            result.put("DATE",dateV);
            result.put("REASON",reasonV);
            result.put("APPROVAL_GROUP",approvalGroup);
        }catch (Exception e) {
            e.printStackTrace();
            HelpObjs.sendMail(db,"Get approval detail info failed (request ID: "+reqID+")","Unable to get approval detail info (newChemApproval)",86030,false);
        }finally {
            dbrs.close();
        }
        return result;
    } //end of method

    public Vector getRoleNeedingApproval (String reqID) {
        Vector result = new Vector(7);
        DBResultSet dbrs = null;
        try {
            String query = "select ca.approval_role,ca.personnel_id,p.last_name||', '||p.first_name approver_name,car.role_type,ca.super_user"+
                    " from catalog_add_request_new carn,chemical_approver ca,"+
                    "vv_chemical_approval_role car,vv_catalog_add_request_status ars,personnel p where carn.request_status = ars.request_status and"+
                    " ars.approval_group = car.approval_group and carn.catalog_id = car.catalog_id and carn.catalog_company_id = car.catalog_company_id and"+
                    " carn.approval_group_seq = car.approval_group_seq and car.active = 'Y' and car.approval_role = ca.approval_role and"+
                    " ca.personnel_id = p.personnel_id and car.facility_id = ca.facility_id and car.catalog_id = ca.catalog_id and car.catalog_company_id = ca.catalog_company_id and ca.active = 'y' and"+
                    " 'Y'=decode(decode(carn.engineering_evaluation,'Y',decode(carn.starting_view,2,8888,9999),carn.starting_view)"+
                    ",0,car.new_material,1,car.new_size,2,car.new_part,3,car.new_approval,8888,car.reorder_eng_eval,9999,car.eng_eval,'Y') and"+
                    " car.facility_id = carn.eng_eval_facility_id and carn.request_id = "+reqID+
                    " and ca.approval_role not in (select a.approval_role"+
                    " from catalog_add_approval a,chemical_approver ca,vv_chemical_approval_role car"+
                    " where a.request_id = "+reqID+" and a.approval_role = ca.approval_role and"+
                    " a.chemical_approver = ca.personnel_id and a.facility_id = ca.facility_id and ca.active = 'y' and"+
                    " a.catalog_id = ca.catalog_id and a.catalog_company_id = ca.catalog_company_id and ca.catalog_id = car.catalog_id and ca.catalog_company_id = car.catalog_company_id and"+
                    " ca.facility_id = car.facility_id and ca.approval_role = car.approval_role and car.active = 'Y')"+
                    " and car.application = ca.application and car.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = "+reqID+")";

            ResultSet rs = null;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            Vector roleV = new Vector(10);
            Vector statusV = new Vector(10);
            Vector approverNameV = new Vector(10);
            Vector dateV = new Vector(10);
            Vector reasonV = new Vector(10);
            Vector approverV = new Vector(10);
            Vector roleTypeV = new Vector(10);
            Vector superUserV = new Vector(10);
            while (rs.next()){
                roleV.addElement(rs.getString("approval_role"));
                statusV.addElement("Pending");
                approverNameV.addElement(rs.getString("approver_name"));
                dateV.addElement(" ");
                reasonV.addElement(" ");
                approverV.addElement(rs.getString("personnel_id"));
                roleTypeV.addElement(rs.getString("role_type"));
                superUserV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("super_user")));
            }
            result.addElement(roleV);
            result.addElement(statusV);
            result.addElement(approverNameV);
            result.addElement(dateV);
            result.addElement(reasonV);
            result.addElement(approverV);
            result.addElement(roleTypeV);
            result.addElement(superUserV);
        }catch (Exception e) {
            e.printStackTrace();
            HelpObjs.sendMail(db,"Get approval detail failed (request ID: "+reqID+")","Unable to get approval detail",86030,false);
        }finally {
            dbrs.close();
        }
        return result;
    }

    public void sendHubEmail(String reqID) {
        DBResultSet dbrs = null;
        try {
            String query = "select distinct carn.cat_part_no,cai.item_id,carn.init_90_day,ugm.personnel_id,p.last_name||', '||p.first_name full_name,"+
                    "p.email,p.phone,carn.eng_eval_facility_id facility_id "+
                    "from catalog_add_request_new carn,catalog_add_item cai,user_group_member ugm,facility f,personnel p "+
                    "where carn.eng_eval_facility_id = f.facility_id and p.personnel_id = carn.requestor and "+
                    "f.preferred_warehouse = ugm.facility_id and ugm.user_group_id in ('CSR','HubManager') and "+
                    "carn.company_id = cai.company_id and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 and "+
                    "carn.stocked = 'MM' and carn.starting_view < 3 and carn.request_id = "+reqID;
            ResultSet rs = null;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            String esub = "";
            String emsg = "";
            boolean firstTime = true;
            Vector hubPersonnel = new Vector(10);
            while (rs.next()){
                //header info
                if (firstTime) {
                    esub = " New MM Request "+reqID+".";
                    emsg += "Requestor                  : "+rs.getString("full_name")+"\n";
                    emsg += "Phone                      : "+BothHelpObjs.makeBlankFromNull(rs.getString("phone"))+"\n";
                    emsg += "Email                      : "+BothHelpObjs.makeBlankFromNull(rs.getString("email"))+"\n";
                    emsg += "facility                   : "+BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"))+"\n";
                    emsg += "Request Id                 : "+reqID+"\n";
                    emsg += "Part Number                : "+BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no"))+"\n";
                    emsg += "Item ID                    : "+BothHelpObjs.makeBlankFromNull(rs.getString("item_id"))+"\n";
                    emsg += "Estimated quarterly usage  : "+BothHelpObjs.makeBlankFromNull(rs.getString("init_90_day"))+"\n\n";
                    firstTime = false;
                }
                //make sure that a user will only be notify once if he/she is listed for both CSR and HubManager
                String per = rs.getString("personnel_id");
                if (!hubPersonnel.contains(per)) {
                    hubPersonnel.addElement(per);
                }
            }
            //now send out email
            for (int i = 0; i < hubPersonnel.size(); i++) {
                HelpObjs.sendMail(db,esub,emsg,Integer.parseInt((String)hubPersonnel.elementAt(i)),false);
            }
        }catch (Exception e) {
            e.printStackTrace();
            HelpObjs.sendMail(db,"Send confirmation email failed (request ID: "+reqID+")","Unable to send email to user",86030,false);
        }finally {
            dbrs.close();
        }
    }

    public String getFacWorkAreaForRequest(String reqID) {
        String result = "";
        DBResultSet dbrs = null;
        try {
            String query = "select nvl(f.facility_name,f.facility_id) facility_name, nvl(caug.process_desc,' ') process_desc,"+
                    "decode(caug.work_area,null,' ','All','All Work Areas',nvl(fla.application_desc,fla.application)) application_desc"+
                    " from catalog_add_user_group caug, facility f, fac_loc_app fla"+
                    " where caug.facility_id = f.facility_id and caug.facility_id = fla.facility_id(+) and caug.work_area = fla.application(+)"+
                    " and caug.request_id = "+reqID+ " order by f.facility_name,fla.application_desc";
            ResultSet rs = null;
            dbrs = db.doQuery(query);
            rs = dbrs.getResultSet();
            String lastFac = "";
            while (rs.next()) {
                String tmpFac = rs.getString("facility_name");
                if (tmpFac.equalsIgnoreCase(lastFac)) {
                    result +=  "\nWork Area            : "+rs.getString("application_desc")+
                            "\nUse and Process Desc : "+rs.getString("process_desc");
                }else {
                    if (result.length() > 0) {
                        result += "\nFacility             : "+tmpFac+
                                "\nWork Area            : "+rs.getString("application_desc")+
                                "\nUse and Process Desc : "+rs.getString("process_desc");
                    }else {
                        result += "Facility             : "+tmpFac+
                                "\nWork Area            : "+rs.getString("application_desc")+
                                "\nUse and Process Desc : "+rs.getString("process_desc");
                    }
                }
                lastFac = tmpFac;
            }
        }catch (Exception e) {
            e.printStackTrace();
            HelpObjs.sendMail(db,"Get facility work areas for request (request ID: "+reqID+")","Get facility work areas for request failed",86030,false);
        }finally {
            dbrs.close();
        }
        return result;
    } //end of method

    public String getMSDSLink(String reqID, String tmpLink) {
        String result = "";
        DBResultSet dbrs = null;
        try {
            String query = "select distinct p.material_id from catalog_add_item cai, part p"+
                    " where cai.item_id = p.item_id and cai.line_item = 1 and cai.part_id = 1 and cai.request_id = "+reqID;
            ResultSet rs = null;
            dbrs = db.doQuery(query);
            rs = dbrs.getResultSet();
            while (rs.next()) {
                if (result.length() > 0) {
                    result += "\n"+tmpLink+"/ViewMsds?act=msds&id="+rs.getString("material_id");
                }else {
                    result += tmpLink + "/ViewMsds?act=msds&id=" + rs.getString("material_id");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            HelpObjs.sendMail(db,"Get facility work areas for request (request ID: "+reqID+")","Get facility work areas for request failed",86030,false);
        }finally {
            dbrs.close();
        }
        return result;
    } //end of method

    public void sendUserEmail(String reqID) {
        DBResultSet dbrs = null;
        try {
            String query = "select distinct carn.requestor,carn.cat_part_no,cai.item_id,carn.engineering_evaluation,carn.request_status,"+
                    "caa.approval_role,caa.status,caa.approval_date,p.last_name||', '||p.first_name approver_name,caa.reason,"+
                    "nvl(part_description,fx_material_desc(cai.item_id)) part_desc,p2.last_name||', '||p2.first_name requestor_name"+
                    " from catalog_add_request_new carn,catalog_add_item cai,catalog_add_approval caa,personnel p, personnel p2"+
                    " where carn.request_id = caa.request_id and caa.chemical_approver = p.personnel_id and carn.requestor = p2.personnel_id and"+
                    " carn.company_id = cai.company_id and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 and carn.request_id = "+reqID;
            ResultSet rs = null;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            String requestor = "86030";
            String esub = "";
            String emsg = "";
            boolean firstTime = true;
            String facWaInfo = getFacWorkAreaForRequest(reqID);
            while (rs.next()){
                //header info
                if (firstTime) {
                    requestor = rs.getString("requestor");
                    String requestType = BothHelpObjs.makeBlankFromNull(rs.getString("engineering_evaluation"));
                    String requestStatus = BothHelpObjs.makeBlankFromNull(rs.getString("request_status"));
                    if ("7".equalsIgnoreCase(requestStatus)) {
                        esub = " Your Request "+reqID+" was REJECTED.";
                        emsg = "Status     : REJECTED\n";
                    }else {
                        if ("Y".equalsIgnoreCase(requestType)) {
                            esub = "Your ENGINEERING EVALUATION Request "+reqID+" was APPROVED for all Roles.";
                            emsg = "Status     : APPROVED\n"+
                                    "It is now in our buyer(s) queque.\n";
                        }else {
                            esub = "Your Request "+reqID+" was APPROVED for all Roles.";
                            emsg = "Status     : APPROVED\n"+
                                    "You are now approved to order the below part number.\n";
                        }
                    }
                    emsg += "Request Id : "+reqID+"\n";
                    emsg += "Part Number: "+BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no"))+"\n";
                    emsg += "Part Desc  : "+BothHelpObjs.makeBlankFromNull(rs.getString("part_desc"))+"\n";
                    emsg += "Item ID    : "+BothHelpObjs.makeBlankFromNull(rs.getString("item_id"))+"\n";
                    emsg += "Requestor  : "+BothHelpObjs.makeBlankFromNull(rs.getString("requestor_name"))+"\n";
                    if (facWaInfo.length() > 0) {
                        emsg += facWaInfo+"\n\n";
                    }else {
                        emsg += "\n";
                    }
                    emsg += "Roles:\n--------------------------\n";
                    firstTime = false;
                }

                emsg += "Name  : "+BothHelpObjs.makeBlankFromNull(rs.getString("approval_role"))+"\n" ;
                emsg += "Status: "+rs.getString("status")+"\n";
                emsg += "Date  : "+BothHelpObjs.formatDate("toCharfromDB",rs.getString("approval_date"))+"\n";
                emsg += "By    : "+BothHelpObjs.makeBlankFromNull(rs.getString("approver_name"))+"\n" ;
                emsg += "Notes : "+BothHelpObjs.makeBlankFromNull(rs.getString("reason"))+"\n\n";
            }
            //add link
            String tmpLink = "https://www.tcmis.com/tcmIS/"+db.getClient().toLowerCase();
            //for accessing tcmIS application
            if (HelpObjs.countQuery(db,"select count(*) from feature_release where feature = 'NewCatalogAddProcess' and active = 'Y'") > 0) {
                emsg += tmpLink+"/home.do\n";
            }else {
                emsg += tmpLink+"/Register\n";
            }

            //for msds links
            String msdsLink = getMSDSLink(reqID,tmpLink);
            if (msdsLink.length() > 0) {
                emsg += "\n\nMSDS Link(s):\n--------------------------\n";
                emsg += "\n"+msdsLink;
            }

            //now send out email to requestor
            HelpObjs.sendMail(db,esub,emsg,Integer.parseInt(requestor),false);

            //send email to addition people
            if (HelpObjs.countQuery(db,"select count(*) from catalog_add_request_new carn where carn.company_id = 'UTC' and carn.catalog_id = 'PWA AM' and"+
                    " carn.eng_eval_facility_id = 'PWA- TEC' and carn.request_id = "+reqID) > 0) {
                if (HelpObjs.countQuery(db,"select count(*) from catalog_add_request_new carn, pwa_catalog_item_id_exception b where"+
                        " carn.cat_part_no = b.catalog_item_id and carn.request_id = "+reqID) > 0) {
                    query = "select personnel_id from user_group_member where user_group_id = 'CatAddNotification' and"+
                            " (facility_id = 'All' or facility_id = (select eng_eval_facility_id from catalog_add_request_new where request_id = "+reqID+"))";
                }else {
                    query = "";
                }
            }else {
                query = "select personnel_id from user_group_member where user_group_id = 'CatAddNotification' and"+
                        " (facility_id = 'All' or facility_id = (select eng_eval_facility_id from catalog_add_request_new where request_id = "+reqID+"))";
            }
            if (query.length() > 0) {
                dbrs = db.doQuery(query);
                rs=dbrs.getResultSet();
                while (rs.next()) {
                    //don't send email if user is requestor
                    if (!requestor.equalsIgnoreCase(rs.getString("personnel_id"))) {
                        HelpObjs.sendMail(db, esub, emsg, rs.getInt("personnel_id"), false);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            HelpObjs.sendMail(db,"Send confirmation email failed (request ID: "+reqID+")","Unable to send email to user",86030,false);
        }finally {
            dbrs.close();
        }
    }

    public void sendApproversEmail(String reqID) {
        DBResultSet dbrs = null;
        try {
            String query = "select distinct c.catalog_desc,cai.item_id,carn.engineering_evaluation,carn.cat_part_no,"+
                    "decode(carn.part_description,null,decode(cai.item_id,null,'',fx_item_desc(cai.item_id,'MA')),carn.part_description) part_description,"+
                    "p1.last_name || ', ' || p1.first_name  requestor_name,p1.phone,f.facility_name,"+
                    "ca.approval_role,p2.last_name || ', ' || p2.first_name  approver_name,ca.personnel_id approver,car.approval_group,"+
                    "car.catalog_add_process_url"+
                    " from catalog_add_request_new carn,catalog_add_item cai,personnel p1,facility f,"+
                    "chemical_approver ca,personnel p2,vv_catalog_add_request_status ars,vv_chemical_approval_role car,catalog c"+
                    " where carn.requestor = p1.personnel_id and carn.catalog_id = car.catalog_id and carn.catalog_company_id = car.catalog_company_id and"+
                    " carn.company_id = cai.company_id and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 and"+
                    " carn.request_status = ars.request_status and carn.catalog_id = c.catalog_id and carn.catalog_company_id = c.catalog_company_id and carn.eng_eval_facility_id = car.facility_id and"+
                    " carn.approval_group_seq = car.approval_group_seq and carn.eng_eval_facility_id = ca.facility_id and carn.eng_eval_facility_id = f.facility_id and"+
                    " ars.approval_group = car.approval_group and car.approval_role = ca.approval_role and car.active = 'Y' and ca.active = 'y' and"+
                    " car.facility_id = ca.facility_id and car.catalog_id = ca.catalog_id and car.catalog_company_id = ca.catalog_company_id and ca.personnel_id = p2.personnel_id and"+
                    " 'Y'=decode(decode(carn.engineering_evaluation,'Y',decode(carn.starting_view,2,8888,9999),carn.starting_view)"+
                    ",0,car.new_material,1,car.new_size,2,car.new_part,3,car.new_approval,8888,car.reorder_eng_eval,9999,car.eng_eval,'Y')"+
                    " and car.email_notification = 'Y' and carn.request_id = "+reqID+
                    " and car.application = ca.application and car.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = "+reqID+")";
            ResultSet rs = null;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            Vector approverV = new Vector(20);
            String esub = "";
            String emsg = "Status     : WAITING APPROVAL\n";
            boolean firstTime = true;
            String approvalGroup = "";
            String approvalRole = "";
            String processURL = "";
            while (rs.next()){
                //header info
                if (firstTime) {
                    String requestType = BothHelpObjs.makeBlankFromNull(rs.getString("engineering_evaluation"));
                    if ("Y".equalsIgnoreCase(requestType)) {
                        esub = "tcmIS New ENGINEERING EVALUATION Request "+reqID+" is waiting for your approval("+BothHelpObjs.makeBlankFromNull(rs.getString("facility_name"))+").";
                    }else {
                        esub = "tcmIS New Chemical Request "+reqID+" is waiting for your approval("+BothHelpObjs.makeBlankFromNull(rs.getString("facility_name"))+").";
                    }
                    emsg += "Request Id : "+reqID+"\n";
                    emsg += "Requestor  : "+BothHelpObjs.makeBlankFromNull(rs.getString("requestor_name"))+"\n";
                    emsg += "Phone      : "+BothHelpObjs.makeBlankFromNull(rs.getString("phone"))+"\n";
                    emsg += "Facility   : "+BothHelpObjs.makeBlankFromNull(rs.getString("facility_name"))+"\n";
                    emsg += "Work Area  : "+getWorkAreaDesc(reqID)+"\n";
                    emsg += "Catalog    : "+BothHelpObjs.makeBlankFromNull(rs.getString("catalog_desc"))+"\n";
                    emsg += "Part Number: "+BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no"))+"\n";
                    emsg += "Part Desc. : "+BothHelpObjs.makeBlankFromNull(rs.getString("part_description"))+"\n";
                    emsg += "Item ID    : "+BothHelpObjs.makeBlankFromNull(rs.getString("item_id"))+"\n\n";
                    emsg += "Roles:\n--------------------------\n";
                    firstTime = false;
                    approvalGroup = rs.getString("approval_group");
                }
                //get all approvers ID
                String approver = BothHelpObjs.makeBlankFromNull(rs.getString("approver"));
                if (!approverV.contains(approver)) {
                    approverV.addElement(approver);
                }
                String appRole = BothHelpObjs.makeBlankFromNull(rs.getString("approval_role"));
                processURL = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_add_process_url"));
                approvalRole = appRole;

                emsg += "Name  : "+appRole+"\n" ;
                emsg += "Status: Pending\n" ;
                emsg += "Date  : \n";
                emsg += "By    : "+BothHelpObjs.makeBlankFromNull(rs.getString("approver_name"))+"\n" ;
                emsg += "Notes : \n";
                //additional URL
                if (processURL.length() > 0) {
                    emsg += "URL   : "+processURL+reqID+"\n\n";
                }else {
                    emsg +="\n\n";
                }
            }

            if ("SWA".equalsIgnoreCase(db.getClient()) && "2".equalsIgnoreCase(approvalGroup)) {     //2-14-03 send email if SWA approvers are notify
                query = "select personnel_id from user_group_member where user_group_id = 'CatAddNotification'";
                dbrs = db.doQuery(query);
                rs=dbrs.getResultSet();
                while (rs.next()) {
                    String tmpPer = rs.getString("personnel_id");
                    if (!approverV.contains(tmpPer)) {
                        approverV.addElement(tmpPer);
                    }
                }
            }

            //adding link for approvers
            emsg += "Click link below to log into tcmIS:\n";
            if (HelpObjs.countQuery(db,"select count(*) from feature_release where feature = 'NewCatalogAddProcess' and active = 'Y'") > 0) {
                emsg += "https://www.tcmis.com/tcmIS/"+db.getClient().toLowerCase()+"/home.do\n\n";
            }else {
                emsg += "https://www.tcmis.com/tcmIS/"+db.getClient().toLowerCase()+"/Register\n\n";
            }

            //add company in subject for catalog group
            if (approvalRole.startsWith("TCM") || approvalRole.startsWith("QC")) {
                esub += " ("+db.getClient()+")";
            }

            //now send out email
            for (int i = 0; i < approverV.size(); i++) {
                HelpObjs.sendMail(db,esub,emsg,Integer.parseInt((String)approverV.elementAt(i)),false);
            }
        }catch (Exception e) {
            e.printStackTrace();
            HelpObjs.sendMail(db,"Send confirmation email failed (request ID: "+reqID+")","Unable to send email to chemical approvers",86030,false);
        }finally {
            dbrs.close();
        }
    }

    String getWorkAreaDesc(String requestId) {
        String result = "";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            String query = "select string_agg(application_desc) application_desc from fac_loc_app fla,catalog_add_user_group caug where caug.request_id = "+requestId+
                    " and fla.company_id = caug.company_id and fla.facility_id = caug.facility_id and fla.application = caug.work_area";
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()) {
                result = BothHelpObjs.makeBlankFromNull(rs.getString("application_desc"));
            }
            if (result.length() == 0) {
                result = "All Work Areas";
            }
        }catch (Exception e) {
            e.printStackTrace();
            HelpObjs.sendMail(db,"get work areas desc failed (request ID: "+requestId+")","NewChemicalApproval.getWOrkAreaDesc",86030,false);
        }finally {
            dbrs.close();
        }
        return result;
    }

    void sendApproversEmail(String reqid, String role){
        //Send email  to the approvers
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            //String reqid = (String) inData.get("REQ_ID");
            CatalogAddRequestNew car = new CatalogAddRequestNew(db);
            car.setRequestId(Integer.parseInt(reqid));
            car.load3();
            Personnel p = new Personnel(db);
            p.setPersonnelId(car.getRequestor().intValue());
            p.load();

            String esub = "";
            String emsg = "";

            esub = new String("tcmIS New Chemical Request "+reqid+" is waiting for your approval.");
            emsg = new String("Status     : WAITING APPROVAL\n");
            emsg += "Request Id : "+reqid+"\n";
            emsg += "Requestor  : "+p.getFullName()+"\n";
            emsg += "Phone      : "+p.getPhone()+"\n";
            emsg += "Facility   : "+car.getFacilityDesc()+"\n";
            emsg += "Catalog    : "+car.getCatalogId()+"\n";
            emsg += "Part Number: "+car.getCatPartNum()+"\n\n";
            emsg += "Item ID    : "+car.getItemId()+"\n\n";

            if ("CSM PN".equalsIgnoreCase(role)) {
                emsg += "https://www.tcmis.com/tcmIS/seagate/cataloging?Session=1&request_id="+reqid+"\n\n";
            }

            //adding link for approvers
            emsg += "Click link below to log into tcmIS:\n";
            if (HelpObjs.countQuery(db,"select count(*) from feature_release where feature = 'NewCatalogAddProcess' and active = 'Y'") > 0) {
                emsg += "https://www.tcmis.com/tcmIS/"+db.getClient().toLowerCase()+"/home.do\n\n";
            }else {
                emsg += "https://www.tcmis.com/tcmIS/"+db.getClient().toLowerCase()+"/Register\n\n";
            }


            String query = "select personnel_id from chemical_approver where active = 'y' and facility_id = '"+car.getFacilityId()+"' and approval_role = '"+role+"'";
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                int nn = BothHelpObjs.makeZeroFromNull(rs.getString("personnel_id"));
                HelpObjs.sendMail(db,esub,emsg,nn,true);
            }

            if ("Pricing".equalsIgnoreCase(role)) {
                //System.out.println("\n\n"+car.getItemId());
                if (car.getItemId().intValue() != 0) {
                    query = "select count(*) from price_quote_view where request_date > sysdate - 365 and item_id = "+car.getItemId();
                    if (HelpObjs.countQuery(db,query) == 0) {
                        String postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/cgi-bin/sgpq.cgi?item="+car.getItemId(),"&item="+car.getItemId()+"&per=86030","text/html");
                        //System.out.println("\n\n"+postingMsg);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            HelpObjs.sendMail(db,role+" email error (request_id: "+reqid+")","Error occurred while trying to send email to "+role,86030,false);   //Trong
        }finally {
            dbrs.close();
        }
    } //end of method

    void sendConfirmationEmail(String reqid){
        //Send email  to the approvers
        try {
            //String reqid = (String) inData.get("REQ_ID");
            CatalogAddRequestNew car = new CatalogAddRequestNew(db);
            car.setRequestId(Integer.parseInt(reqid));
            car.load3();
            Personnel p = new Personnel(db);
            p.setPersonnelId(car.getRequestor().intValue());
            p.load();

            //build the material table data
            NewChemTrackTable nctt = new NewChemTrackTable(db);
            Vector big1 = nctt.getApprDetailData(reqid);
            Vector roleV = new Vector();
            Vector statusV =  new Vector();
            Vector appV =  new Vector();
            Vector dateV =  new Vector();
            Vector reasonV =  new Vector();
            Vector appNumV =  new Vector();
            Vector roleTypeV =  new Vector();
            if(big1 != null && big1.size() > 0){
                roleV = (Vector)big1.elementAt(0);
                statusV = (Vector)big1.elementAt(1);
                appV = (Vector)big1.elementAt(2);
                dateV = (Vector)big1.elementAt(3);
                reasonV = (Vector)big1.elementAt(4);
                appNumV = (Vector)big1.elementAt(5);
                roleTypeV = (Vector)big1.elementAt(6);
            }

            String esub = "";
            String emsg = "";

            esub = new String("tcmIS New Chemical Request "+reqid+" is waiting for your approval.");
            emsg = new String("Status     : WAITING APPROVAL\n");
            emsg = emsg + "Request Id : "+reqid+"\n";
            emsg = emsg + "Requestor  : "+p.getFullName()+"\n";
            emsg = emsg + "Phone      : "+p.getPhone()+"\n";
            emsg = emsg + "Facility   : "+car.getFacilityDesc()+"\n";
            emsg = emsg + "Catalog    : "+car.getCatalogId()+"\n";
            emsg = emsg + "Part Number: "+car.getCatPartNum()+"\n\n";

            emsg = emsg + "Roles:\n--------------------------\n";

            for (int i=0;i<roleV.size();i++){
                emsg = emsg + "Name  : " + (roleV.elementAt(i)==null?"":roleV.elementAt(i))+"\n" ;
                emsg = emsg + "Status: " + (statusV.elementAt(i)==null?"":statusV.elementAt(i))+"\n" ;
                emsg = emsg + "Date  : " + (dateV.elementAt(i)==null?"":BothHelpObjs.formatDate("toCharfromDB",dateV.elementAt(i).toString()))+"\n";
                emsg = emsg + "By    : " ;

                Personnel p2 = new Personnel(db);
                p2.setPersonnelId(Integer.parseInt(appNumV.elementAt(i).toString()));
                p2.load();
                emsg = emsg + (p2.getFullName()==null?"":p2.getFullName()) +  "\n" ;
                emsg = emsg + "Notes : " + (reasonV.elementAt(i)==null?"":reasonV.elementAt(i))+ "\n\n";
            }

            Vector hol = new Vector();
            for (int i=0;i<appNumV.size();i++){
                int nn = Integer.parseInt(appNumV.elementAt(i).toString());
                if (!hol.contains(new Integer(nn))){
                    hol.addElement(new Integer(nn));
                    HelpObjs.sendMail(db,esub,emsg,nn,true);
                }
            }
            //System.out.println("\n\n"+car.getItemId());
            if (car.getItemId().intValue() != 0) {
                String query = "select count(*) from price_quote_view where request_date > sysdate - 365 and item_id = "+car.getItemId();
                //query += " and lower(requestor_company_id) = lower('"+db.getClient()+"')";
                if (HelpObjs.countQuery(db,query) == 0) {
                    String postingMsg = BothHelpObjs.sendSslPost("https://www.tcmis.com/cgi-bin/sgpq.cgi?item="+car.getItemId(),"&item="+car.getItemId()+"&per=86030","text/html");
                    //System.out.println("\n\n"+postingMsg);
                }
            }else {
                HelpObjs.sendMail(db,"New Chemical add without item (request_id: "+reqid+")",emsg,19349,true);   //Ronnie
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    } //end of method


    //trong change 4/1 i only want the role that need to approve new chem/eng eval
    public Vector getAllRoles(int id) throws Exception{
        Vector v = new Vector();
        DBResultSet dbrs = null;
        try {
            // this is assuming we have one facility for each request
            Hashtable h = CatalogAddRequestNew.getApprovalInformation(db,id);

            Enumeration E=h.keys();
            if (E==null || !E.hasMoreElements()) return v;
            String key = (String) E.nextElement();
            Hashtable h2 = (Hashtable) h.get(key);
            String query;
            CatalogAddRequestNew catR = new CatalogAddRequestNew(db);
            catR.setRequestId(id);
            catR.load();
            String evalFlag = catR.getEngineeringEval();
            if (evalFlag.equalsIgnoreCase("y")) {
                query = "select distinct approval_role,role_type from chemical_approver_role_view where facility_id = '"+(String) h2.get("FACID") +"'";
                query += " and role_type in(2,3,7,8,9,10,11,12)";
                query += " and lower(active) = 'y'";
            } else {
                query = "select distinct approval_role,role_type from chemical_approver_role_view where facility_id = '"+(String) h2.get("FACID") +"'";
                query += " and role_type in(1,3,4,5,6,10,11,12)";
                query += " and lower(active) = 'y'";
            }

            ResultSet rs = null;
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                v.addElement(rs.getString("approval_role"));
            }
            //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ my vector: " + v);
        }catch (Exception e) {
            e.printStackTrace();
            HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }finally {
            dbrs.close();
        }
        return v;
    }

    public int getActive(){
        return active;
    }

    public void load()  throws Exception {
        String query= null;
        apprFlag = 1;
        //System.out.println("ooooooooooooooooooooooooooooooooooooooo: " + evalFlag);
        if(evalFlag) {
            String table = "catalog_add_request_view_new2";
            String where = "request_id = '" + reqId +"' and approval_role = '"+role+"' and lower(active) = 'y'";
            where += " and role_type in (2,3,7,8,9,10,11,12)";
            int x = HelpObjs.countQuery(db,table,where);
            if (x==0) {
                active=0;
                setApprFlag(0);
            }

            table = "catalog_add_request_view_new2";
            where = "request_id = '" + reqId +"' and approval_role = '"+role+"' and status is not null";
            where += " and role_type in (2,3,7,8,9,10,11,12)";
            x = HelpObjs.countQuery(db,table,where);

            //System.out.println("WHAT IIIIIIIIIIIIIIIIIIIIISS X: " + x + "   " + apprFlag);

            query = "select * from catalog_add_request_view_new2 where request_id = '" + reqId +"' and approval_role = '"+role+"' and lower(active) = 'y'";
            query += " and role_type in (2,3,7,8,9,10,11,12)";
            if(x>0){
                query = query + " and status is not null";
            }
        }else {
            String table = "catalog_add_request_view_new2";
            String where = "request_id = '" + reqId +"' and approval_role = '"+role+"' and lower(active) = 'y'";
            where += " and role_type in (1,3,4,5,6,10,11,12)";
            int x = HelpObjs.countQuery(db,table,where);
            if (x==0) active=0;


            table = "catalog_add_request_view_new2";
            where = "request_id = '" + reqId +"' and approval_role = '"+role+"' and status is not null";
            where += " and role_type in (1,3,4,5,6,10,11,12)";
            x = HelpObjs.countQuery(db,table,where);
            if (x == 0) setApprFlag(0);

            query = "select * from catalog_add_request_view_new2 where request_id = '" + reqId +"' and approval_role = '"+role+"' and lower(active) = 'y'";
            query += " and role_type in (1,3,4,5,6,10,11,12)";
            if(x>0){
                query = query + " and status is not null";
            }
        }

        //System.out.println("*** MY FLLLLLLAAAGGGGG: \n"+apprFlag);
        Vector tmp = new Vector();
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                tmp.addElement(new Integer(rs.getInt("chemical_approver")));
                setDate(rs.getString("approval_date"));
                setStatus(rs.getString("status"));
                setReason(rs.getString("reason"));
                setRoleType(rs.getInt("role_type"));
                setFacilityId(rs.getString("facility_id"));
            }
            setApprovers(tmp);


        } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null); dbrs.close(); throw e;
        } finally{
            dbrs.close();
        }
        //System.out.println("*** MY FLLLLLLAAAGGGGG: "+apprFlag);
    }

    public void delete() throws Exception {
        Integer I;
        String where = " request_id= " + reqId.toString() + " and approval_role ='" + role + "' and chemical_approver="+approver+ " and facility_id ='" + facilityId + "'";
        int records = HelpObjs.countQuery(db,"catalog_add_approval"," request_id= " + reqId.toString() + " and approval_role ='" + role + "' and chemical_approver="+approver+" and facility_id ='" + facilityId + "'") ;
        if (records < 1){
            return;
        }

        String query = new String("delete catalog_add_approval where " + where);
        try {
            db.doUpdate(query);


        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;

        }
    }

    public String getFullStatus() throws Exception {
        Vector allRoles = this.getAllRoles(this.reqId.intValue());
        if (allRoles == null || allRoles.size() == 0) return "5";  // no roles, leave pending approval
        NewChemApproval nca = new NewChemApproval(db);
        nca.setReqId(this.reqId.intValue());
        nca.setEvalFlag(this.evalFlag);       //trong 3/29
        boolean approved = true;
        for (int i=0;i<allRoles.size();i++){
            nca.setRole(allRoles.elementAt(i).toString());
            nca.load();
            if (nca.getStatus().equalsIgnoreCase("banned")) return "10";
            if (nca.getStatus().equalsIgnoreCase("rejected")) return "7";
            if (!nca.getStatus().equalsIgnoreCase("approved") || nca.getApprFlag() == 0) approved = false;


        }
        //System.out.println("\n\n------ approved: "+approved);
        return (approved?"8":"5");  // approved  or pending
    }


    public void clearAllRoles() throws Exception {
        if (this.reqId == null || this.reqId.intValue()==0) return;
        Integer I;

        String where = " request_id= " + reqId.toString();
        int records = HelpObjs.countQuery(db,"catalog_add_approval"," request_id= " + reqId.toString()) ;
        if (records < 1){
            return;
        }

        String query = new String("delete catalog_add_approval where " + where);
        try {
            db.doUpdate(query);


        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }
    }

    public Vector[] getApprovalRoleInfo() throws Exception{
        Vector facs = new Vector();
        Vector roles = new Vector();
        String query = "select * from vv_chemical_approval_role";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                facs.addElement(rs.getString("facility_id"));
                roles.addElement(rs.getString("approval_role"));
            }


        } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
        } finally{
            dbrs.close();
        }
        return new Vector[] {facs,roles};
    }


    public void addChemicalApprover(String facId,String role,int personnelId) throws Exception{
        if(personnelId == 0) throw new NumberFormatException("personnel_id is '0'.");
        String where = "where facility_id = '"+facId+"' and approval_role = '"+role+"' and personnel_id = "+personnelId;
        String query = "";
        int num = HelpObjs.countQuery(db,"select count(*) from chemical_approver "+where);
        if (num >0){
            query = "update chemical_approver set active = 'y' "+where;
        }else{
            query = "insert into chemical_approver (facility_id, approval_role,personnel_id,active) values ('"+facId+"','"+role+"',"+personnelId+",'y')";
        }


        try {
            db.doUpdate(query);
        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }
    }
    public void deleteChemicalApprover(String facId,String role,int personnelId) throws Exception{

        String query = "update chemical_approver set active = null where facility_id = '"+facId+"' and approval_role = '"+role+"' and personnel_id = "+personnelId;
        try {
            db.doUpdate(query);
        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }
    }

    public void addChemicalApprovalRole(String facId,String role,String type) throws Exception{
        String where = "where facility_id = '"+facId+"' and approval_role = '"+role+"'";

        int num = HelpObjs.countQuery(db,"select count(*) from vv_chemical_approval_role "+where);
        if (num > 0)return;

        String myType = new String(type);
        if(BothHelpObjs.isBlankString(type)) myType = "null";

        String query = "insert into vv_chemical_approval_role (facility_id, approval_role,role_type) values ('"+facId+"','"+role+"',"+myType+")";

        try {
            db.doUpdate(query);
        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }
    }

    //trong 2/27
    public void setApprFlag(int n) {
        this.apprFlag = n;
    }
    public int getApprFlag(){
        return this.apprFlag;
    }
    public Vector getRoleTypeDesc(String facId) throws Exception{
        Vector roleTypeDesc = new Vector();
        Vector tempRoleV = new Vector();
        String query = "select distinct a.approval_role, b.role_type_desc from vv_chemical_approval_role a,";
        query += " vv_chemical_approval_role_type b";
        query += " where a.role_type = b.role_type and a.facility_id = '"+facId+"' order by a.approval_role";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                roleTypeDesc.addElement(rs.getString("role_type_desc"));
                tempRoleV.addElement(rs.getString("approval_role"));
            }
        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }finally {
            dbrs.close();
        }
        setRoleV(tempRoleV);
        return roleTypeDesc;
    }
    public void setRoleV(Vector v){
        this.roleV = v;
    }
    public Vector getRoleV(){
        return this.roleV;
    }


    public void updateChemicalApprovalRole(String facId,String role,String type) throws Exception{
        String where = "where facility_id = '"+facId+"' and approval_role = '"+role+"'";

        String myType = new String(type);
        if(BothHelpObjs.isBlankString(type)) myType = "null";

        String query = "update vv_chemical_approval_role set role_type = "+myType;
        query += where;
        try {
            db.doUpdate(query);
        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }
    }
    public int findOtherApprovers() throws Exception {
        Vector v = new Vector();
        String active = new String("y");
        String query = "select * from chemical_approver where facility_id = '"+facilityId+"' and approval_role = '"+role+"'";
        query += " and active = '"+active+"'";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                v.addElement(rs.getString("personnel_id"));
            }
        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }finally {
            dbrs.close();
        }
        String n = v.elementAt(0).toString();
        Integer num = new Integer(n);
        //System.out.println("ffffffffffffffffffffff in newchemapproval findotherapprovers: " + num.intValue());
        return num.intValue();
    }

    public Boolean deleteChemicalApprovalRole(String facId,String role) throws Exception{
        String countQ =  "select count(*) from catalog_add_approval a, catalog_add_request_new b where a.approval_role = '"+role+"' and a.request_id = b.request_id and b.facility_id = '"+facId+"'";
        if(HelpObjs.countQuery(db,countQ) > 0){
            return new Boolean(false);
        }


        String query = "delete chemical_approver where facility_id = '"+facId+"' and approval_role = '"+role+"'";
        try {
            db.doUpdate(query);

        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }


        query = "delete vv_chemical_approval_role where facility_id = '"+facId+"' and approval_role = '"+role+"'";

        try {
            db.doUpdate(query);


        } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }
        return new Boolean(true);
    }

    public Vector getAllApprovers() throws Exception{
        Vector v = new Vector();
        String query = "select * from chemical_approver where active = 'y' order by facility_id, approval_role";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            Personnel p = new Personnel(db);
            Facility f = new Facility(db);
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                f.setFacilityId(rs.getString("facility_id"));
                f.load();
                v.addElement(f.getFacilityName());
                v.addElement(rs.getString("approval_role"));
                p.setPersonnelId(Integer.parseInt(rs.getString("PERSONNEL_ID")));
                p.load();
                v.addElement(p.getFullName());
                v.addElement(p.getPersonnelId().toString());
            }


        } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
        } finally{
            dbrs.close();
        }
        return v;
    }

    public String getSuperUserForApprover(String appNum) throws Exception{
        Vector v = new Vector();
        String query = "select super_user from chemical_approver where active = 'y' ";
        query +=       "and personnel_id ="+Integer.parseInt(appNum)+" and facility_id = '"+facilityId+"' ";
        query +=       "and approval_role='"+this.role+"'";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String res = new String(" ");
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                res = BothHelpObjs.makeBlankFromNull(rs.getString("super_user"));  //trong 3/29
            }


        } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
        } finally{
            dbrs.close();
        }
        //System.out.println("\n\n\n+++++++++++++++ checking to see if user is SUPER USER: " + res);
        return res;
    }


    public Vector getApprovalRoleForFacility(String facId) throws Exception{
        Vector v = new Vector();
        try{
            Vector[] va = getApprovalRoleInfo();
            for(int i=0;i<va[0].size();i++){
                if(va[0].elementAt(i).toString().equals(facId)){
                    v.addElement(va[1].elementAt(i));
                }
            }
        }catch(Exception e){e.printStackTrace();throw e;

        }
        return v;
    }

    //trong 3/29
    public void setEvalFlag(boolean b){
        this.evalFlag = b;

        //System.out.println("##############################3 got here: " + evalFlag);

    }

}