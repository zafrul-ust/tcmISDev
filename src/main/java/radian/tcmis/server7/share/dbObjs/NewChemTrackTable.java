package radian.tcmis.server7.share.dbObjs;

import java.applet.*;
import java.util.*;
import java.text.*;
import java.rmi.server.*;
import java.io.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

/** this object is for building the new Chemical tracking table */
public class NewChemTrackTable {

   //trong 3/29
   boolean evalFlag;
   String facId;
   Vector roleV = new Vector();
   Vector statusV = new Vector();
   Vector appV = new Vector();
   Vector dateV = new Vector();
   Vector reasonV = new Vector();
   Vector appNumV = new Vector();
   Vector roleTypeV = new Vector();
   Vector superUserV = new Vector();
   Vector countV = new Vector();


   protected TcmISDBModel db;
   public NewChemTrackTable() {

   }

   public NewChemTrackTable(TcmISDBModel  db) {
     this.db = db;
   }

   public void setDB(TcmISDBModel  db){
     this.db = db;
   }

   public static Vector getVVStatus(TcmISDBModel  db) throws Exception {
    Vector result = new Vector(10);
    DBResultSet dbrs = null;
    try {
      String query = "select request_status,request_status_desc,approval_group from vv_catalog_add_request_status where approval_group > 0";
      ResultSet rs = null;
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        String[] oa = new String[3];
        oa[0] = BothHelpObjs.makeBlankFromNull(rs.getString("request_status"));
        oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("request_status_desc"));
        oa[2] = BothHelpObjs.makeBlankFromNull(rs.getString("approval_group"));
        result.addElement(oa);
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
    return result;
   }

   public String buildQuery(String userid,String requestor,String reqID, String approver,String facility,String workArea,String searchString,String status,boolean needApp) throws Exception {
    int i;
    String query = "";
    String qu = "";
    Vector wheres = new Vector();
    ResultSet rs = null;
    DBResultSet dbrs =null;
    //if approver or need my approval is part of the search
    String approverStatus = "";
    if (needApp || !BothHelpObjs.isBlankString(approver)) {
      if (needApp) {
        approver = userid;
        requestor = "";
        reqID = "";
        facility = "all";
        workArea = "";
        searchString = "";
      }
      query = "select distinct ars.request_status from chemical_approver ca,vv_chemical_approval_role car,vv_catalog_add_request_status ars"+
              " where ca.facility_id = car.facility_id and ca.active = 'y' and ca.approval_role = car.approval_role and ca.catalog_id = car.catalog_id and"+
              " ca.catalog_company_id = car.catalog_company_id and car.active = 'Y' and car.approval_group = ars.approval_group and ca.personnel_id = "+approver;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        String tmpRole = "";
        while(rs.next()) {
          approverStatus += BothHelpObjs.makeBlankFromNull(rs.getString("request_status"))+",";
        }
        if (BothHelpObjs.isBlankString(approverStatus)) {
          approverStatus = "-1";
        }else {
          //removing the last commas ','
          approverStatus = approverStatus.substring(0,approverStatus.length()-1);
        }
      }catch (Exception e) {
        approverStatus = "-1";
      }finally {
        dbrs.close();
      }
    }

    boolean newMaterial = false;
    boolean newSizePackage = false;
    boolean newPart = false;
    boolean newApproval = false;
    boolean pendQC = false;
    boolean pendPricing = false;
    boolean pendApprovalOrCRA = false;
    boolean rejected = false;
    boolean approved = false;
    boolean pendPN = false;
    boolean noRec = false;

    try{
      i = Integer.parseInt(status);
    }catch(Exception e) {
      i = 0;
    }

    boolean statusSet = i>0;
    if(i >= 1024 ) {           // return no record
      noRec = true;
      i = i - 1024;
    }
    if(i >= 512 ) {           // pending PN
      pendPN = true;
      i = i - 512;
    }
    if(i >= 256 ) {           //  new Approval
      newApproval = true;
      i = i - 256;
    }
    if(i >= 128 ) {           // new Size/Packaging
      newSizePackage = true;
      i = i - 128;
    }
    if(i >= 64 ) {            // pending Pricing
      pendPricing = true;
      i = i - 64;
    }
    if(i >= 32) {             // rejected
      rejected = true;
      i = i - 32;
    }
    if(i >= 16) {             // ready to order
      approved = true;
      i = i - 16;
    }
    if(i >= 8) {              // pending QC
      pendQC = true;
      i = i - 8;
    }
    if(i >= 4) {              // new Part
      newPart = true;
      i = i - 4;
    }
    if(i >= 2) {              // new material
      newMaterial = true;
      i = i - 2;
    }
    if(i >= 1) {              // pending approval(SWA,Raytheon,DRS...) or CRA (Seagate)
      pendApprovalOrCRA = true;
      i = i - 1;
    }

    String s;
    if(reqID.length() > 0) {
      try{
        i = Integer.parseInt(reqID);
        wheres.addElement("request_id = "+i);
        requestor = "";
        //3-20-02 user could only see request for his/her fac_pref and approver at
        //facility = "";
        workArea = "";
        searchString = "";
        statusSet = false;
      }catch(Exception e){
        throw e;
      }
    }

    // requestor
    if(requestor.length() > 0) {
      try{
        i = Integer.parseInt(requestor);
        wheres.addElement("requestor = '"+i+"'");
      }catch(Exception e){wheres.addElement("requestor = '"+i+"'");}
    }

    if ("My Facilities".equalsIgnoreCase(facility) && "My Work Areas".equalsIgnoreCase(workArea)) {
      wheres.addElement("(facility_id,application) in (select fla.facility_id,fla.application from fac_loc_app fla,fac_pref fp"+
                        " where fp.facility_id = fla.facility_id and nvl((select 'Y' from user_group_member_application x where"+
                        " x.personnel_id = fp.personnel_id and x.facility_id = fla.facility_id and x.application = decode(x.application,'All','All',fla.application)"+
                        " and user_group_id in ('DisplayApplication','GenerateOrders')),'N') = 'Y' and fp.personnel_id = "+userid+")");
    }else {
      // facility
      if(facility.length() > 0 && !facility.equalsIgnoreCase("all")) {
        if ("My Facilities".equalsIgnoreCase(facility)) {
          wheres.addElement("(facility_id in ((select facility_id from fac_pref where personnel_id = "+userid+") union (select facility_id from chemical_approver where personnel_id = "+userid+")) or facility_id is null)");
        }else {
          wheres.addElement("facility_id = '" + facility + "'"); //trong change
        }
      }
      // work area
      if(workArea.length() > 0 && !workArea.equalsIgnoreCase("all")) {
        wheres.addElement("application = '"+workArea+"'");
      }
    }
    if (needApp || !BothHelpObjs.isBlankString(approver)) {
      query = "select distinct t.* from new_chem_to_approve_view t where t.request_status in ("+approverStatus+")"+
              " and (t.material_desc <> 'Material added from POSS' or t.material_desc is null) and t.personnel_id = "+approver;
    }else {
      // status
      if(statusSet) {
        String pendingCRA = "";
        Vector ss = new Vector();
        qu = "(request_status in (";
        if (noRec) qu = qu + "-1,";
        if (newMaterial)    qu = qu + "0,";
        if (newSizePackage) qu = qu + "1,";
        if (newPart)        qu = qu + "2,";
        if (newApproval)    qu = qu + "3,";
        if (pendQC) qu = qu + "5,";
        if (pendPricing) qu = qu + "8,";                  //1-12-02 adding 6
        if ("Seagate".equalsIgnoreCase(db.getClient())) {
          if (pendApprovalOrCRA) {
            qu = qu + "6,";
            pendingCRA = "request_status_desc like '%Pending CRA%'";
          }
        }else {
          if (pendApprovalOrCRA)    qu = qu + "6,";         //1-12-02 replacing 6 with 0,1,2,3
        }
        if (rejected) qu = qu + "7,";
        if (approved) qu = qu + "9,12,13,";
        if (pendPN) qu = qu + "11,";
        if (qu.endsWith(",")) qu = qu.substring(0,qu.length() -1);
        qu = qu + ")";

        if (!BothHelpObjs.isBlankString(pendingCRA)) {
          qu += " or "+pendingCRA+")";
        }else {
          qu += ")";
        }
        wheres.addElement(qu);
      }
      qu = "";

      if(searchString.length() > 0) {
        qu = qu + "(lower(material_desc) like lower('%"+searchString+"%') or ";
        qu = qu + "lower(manufacturer) like lower('%"+searchString+"%') or ";
        qu = qu + "lower(mfg_catalog_id) like lower('%"+searchString+"%') or ";
        qu = qu + "lower(cat_part_no) like lower('%"+searchString+"%')) ";       //trong 6/5/00
        wheres.addElement(qu);
      }

      String where = "";
      if(wheres.size() == 1){
        where = wheres.elementAt(0).toString();
      }else{
        for(int io=0;io<wheres.size();io++) {
          if(io==0) where = wheres.elementAt(io).toString();
            else where = where + " and " + wheres.elementAt(io).toString();
        }
      }
      query = "select * from new_chem_tracking_view where " + where +" and (material_desc <> 'Material added from POSS' or material_desc is null) order by request_id, part_id";
    }  //end of not needing my approval/approver
    return query;
   }

   /** this method returns the table data in a 2 diminsional
   object array. The column numbers are defined in BothHelpObjs*/
   public Object [][] getTableData(String userid,String requestor,String reqID, String approver,String facility,String workArea,String searchStringIn,String status,boolean needApp) throws Exception {
    String searchString = HelpObjs.singleQuoteToDouble(searchStringIn);
    Hashtable cols = BothHelpObjs.getNewChemTrackCols();
    int numCols = cols.size();
    Object[][] tableData = null;
    Vector reqItems = new Vector();
    ResultSet rs = null;
    DBResultSet dbrs =null;
    try {
      String query = this.buildQuery(userid,requestor,reqID,approver,facility,workArea,searchStringIn,status,needApp);
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int x = 0;
      while(rs.next()) {
        Object[] oa = new Object[cols.size()];
        oa[((Integer)cols.get("REQ_ID")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("request_id"));
        oa[((Integer)cols.get("REQUESTOR")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("name"));
        oa[((Integer)cols.get("REQ_DATE")).intValue()] = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("request_date")));
        oa[((Integer)cols.get("REQ_STATUS")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("request_status_desc"));
        oa[((Integer)cols.get("FAC_ID")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("facility_name"));
        oa[((Integer)cols.get("APPLICATION")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("application_display"));
        oa[((Integer)cols.get("FAC_PART_NUM")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no"));
        oa[((Integer)cols.get("ITEM_ID")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        oa[((Integer)cols.get("MAT_DESC")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("material_desc"));
        oa[((Integer)cols.get("MANUFACTURER")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("manufacturer"));
        oa[((Integer)cols.get("PACKAGING")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
        oa[((Integer)cols.get("MFG_CATALOG_ID")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("mfg_catalog_id"));
        oa[((Integer)cols.get("CUSTOMER_REQ_ID")).intValue()] = BothHelpObjs.makeBlankFromNull(rs.getString("customer_request_id"));
        reqItems.addElement(oa);
      }
      tableData = new Object[reqItems.size()][cols.size()];
      for (int ii = 0 ; ii < reqItems.size(); ii++) {
        Object[] tmp = (Object[])reqItems.elementAt(ii);
        for (int j = 0; j < tmp.length; j++) {
          tableData[ii][j] = (String)tmp[j];
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    } finally{
      dbrs.close();
    }
    return tableData;
   }

   /** returns a vector of distinct request_ids for all request that the approver has
   approved (or rejected) regardless of status or facility*/
   public Vector getAllApprovals(String approver) throws Exception {
     Vector v = new Vector();
     String myQuery = "select request_id ";
     myQuery = myQuery + "from catalog_add_request_view_new  ";
     myQuery = myQuery + "where chemical_approver = " + approver+" ";
     myQuery = myQuery + "and active is not null ";          //1-11-02 replacing 'status' with 'active'
     myQuery = myQuery + "order by request_id";

     Hashtable h = new Hashtable();
     ResultSet rs = null;
     DBResultSet dbrs =null;

     try {
       dbrs = db.doQuery(myQuery);
       rs=dbrs.getResultSet();
        while(rs.next()) {
            h.put(rs.getString(1),"1");
        }


     } catch(Exception e) {e.printStackTrace();
     } finally{
             dbrs.close();

     }
      for (Enumeration E = h.keys();E.hasMoreElements();){
        v.addElement((String)E.nextElement());
      }
     return v;
   }

   /** returns a vector of distinct request_ids for all request that the approver can
   approve and haven't been approved by someone else and status = 5, regardless of facility*/
   public Vector getPotentialApprovals(String approver) throws Exception {
     Vector v = new Vector();
     String myQuery = "select v.request_id, v.approval_role, v.chemical_approver ";
     myQuery = myQuery + "from catalog_add_request_view_new v, catalog_add_request_new r ";
     myQuery = myQuery + "where r.request_status = 5 ";
     myQuery = myQuery + "and v.chemical_approver = " + approver+" ";
     myQuery = myQuery + "and v.status is null ";
     myQuery = myQuery + "and r.request_id = v.request_id ";
     myQuery = myQuery + "order by request_id, approval_role ";

     Hashtable h = new Hashtable();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       dbrs = db.doQuery(myQuery);
       rs=dbrs.getResultSet();
        while(rs.next()) {
          String myReqId = rs.getString(1);
          String myAppRole = rs.getString(2);
          String myWhere = "where approval_date is not null and lower(status) in ('approved','rejected') and ";
          myWhere = myWhere + " request_id = '"+myReqId+"' and approval_role = '"+myAppRole+"'";
          if(DbHelpers.countQuery(db,"catalog_add_request_view_new", myWhere) < 1) {
            h.put(myReqId,"1");
          }
        }
     }catch(Exception e) {e.printStackTrace();
     } finally{
             dbrs.close();

     }
      for (Enumeration E = h.keys();E.hasMoreElements();){
        v.addElement((String)E.nextElement());
      }
     return v;
  }

  public Vector getApprDetailData(String reqID) {
    Vector big1 = new Vector();
    try {
      NewChemApproval nca = new NewChemApproval(db);
      Vector v = nca.getAllRoles(Integer.parseInt(reqID));
      if(v.size() < 1) return big1;
      for(int x=0;x<v.size();x++){
        NewChemApproval n = new NewChemApproval(db);
        n.setReqId(Integer.parseInt(reqID));
        n.setRole(v.elementAt(x).toString());
        n.setEvalFlag(this.evalFlag);
        //System.out.println("\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!! ooo yeah: set it" + this.evalFlag);
        n.load();
        if (n.getActive()==0){
           continue;
        }
        String role = n.getRole();
        Integer rType = n.getRoleType();

        // set status
        String status = n.getStatus();
        if(status.length() < 1) {
          CatalogAddRequestNew car = new CatalogAddRequestNew(db);
          car.setRequestId(Integer.parseInt(reqID));
          car.load();
          if(car.getRequestStatus().intValue() == 5) {
            status = "Pending";
          }
        }
        Vector names = n.getApproversNames();
        // format date
        String date = n.getDate();
        if(date.length() > 0) {
          date = BothHelpObjs.formatDate("toCharfromDB",date);
        }
        String reason = n.getReason();
        Vector appNum = n.getApprovers();
        if(names == null || names.isEmpty()){
          names.addElement("");
        }
        for(int y=0;y<names.size();y++){
          roleV.addElement(role);
          statusV.addElement(status);
          appV.addElement(names.elementAt(y).toString());
          dateV.addElement(date);
          reasonV.addElement(reason);
          appNumV.addElement(appNum.elementAt(y).toString());
          roleTypeV.addElement(rType.toString());
          superUserV.addElement(n.getSuperUserForApprover(appNum.elementAt(y).toString()));
        }
      }
    }catch(Exception e) {
      e.printStackTrace();
      return (new Vector());
    }

    big1.addElement(roleV);
    big1.addElement(statusV);
    big1.addElement(appV);
    big1.addElement(dateV);
    big1.addElement(reasonV);
    big1.addElement(appNumV);
    big1.addElement(roleTypeV);
    big1.addElement(superUserV);
    return big1;
  }
  public Vector getMatDetailData(String reqID) {
    Object[][] oa = null;
    try{
      CatalogAddItemNew cai = new CatalogAddItemNew(db);
      cai.setRequestId(Integer.parseInt(reqID));
      int count = cai.getCount();
      oa = new Object[count][3];
      for(int x=1;x<=count;x++){
        cai.setPartId(new Integer(x));
        cai.load();
        oa[x-1][0] = cai.getMaterialDesc();

        Float n = cai.getPartSize();
        String s1 = "";
        if(n == null) {
          s1 = "";
        }else{
          s1 = n.toString();
        }
        String s2 = cai.getSizeUnit();
        if(s2 == null) s2 = "";
        String s3 = cai.getPkgStyle();
        if(s3 == null) s3 = "";


        oa[x-1][1] = cai.getNumberPerPart()+" x "+ s1 +" "+ s2 +" "+ s3;
        oa[x-1][2] = cai.getManufacturer();
      }
    }catch(Exception e){e.printStackTrace();}
    return BothHelpObjs.getVectorFrom2DArray(oa);
  }

  //trong 3/29
  public void setChemFlag(String s) {
    if (s.equalsIgnoreCase("y")) {
      this.evalFlag = true;
    } else {
      this.evalFlag = false;
    }
  }
  public void setFacility(String s) {
    this.facId = s;
  }

  //Nawaz 01-17-02
  public String getQuery(String userid,String requestor,String reqID, String approver,String facility,String workArea,String searchStringIn,String status,boolean needApp) throws Exception
  {
    // CBK start
    String searchString = HelpObjs.singleQuoteToDouble(searchStringIn);
    // CBK end

    int i;

    String query = "";
    String qu = "";
    Vector wheres = new Vector();

    DBResultSet dbrs = null;
    ResultSet rs = null;

    //if approver or need my approval is part of the search
    if (needApp || !BothHelpObjs.isBlankString(approver)) {
      if (needApp) {
        approver = userid;
        requestor = "";
        reqID = "";
        facility = "";
        workArea = "";
        searchString = "";
      }
      //get role for approver - NOTE one role per approver
      query = "select distinct(approval_role) from chemical_approver where active = 'y' and personnel_id = "+approver;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        String tmpRole = "";
        while(rs.next()) {
          tmpRole = BothHelpObjs.makeBlankFromNull(rs.getString("approval_role"));
        }
        //determine what status to give it
        if ("CSM PN".equalsIgnoreCase(tmpRole)) {
          status = "512";
        }else if ("EHS".equalsIgnoreCase(tmpRole)) {
          status = "1";
        }else if ("Pricing".equalsIgnoreCase(tmpRole)) {
          status = "64";
        }else if ("Material QC".equalsIgnoreCase(tmpRole) || "Item QC".equalsIgnoreCase(tmpRole) ) {
          status = "8";
        }else {
          status = "1024";
        }
      }catch (Exception e) {
        status = "1024";
      }
    }

    boolean newMaterial = false;
    boolean newSizePackage = false;
    boolean newPart = false;
    boolean newApproval = false;
    boolean pendQC = false;
    boolean pendPricing = false;
    boolean pendCRA = false;
    boolean rejected = false;
    boolean approved = false;
    boolean pendCSMPN = false;
    boolean noRec = false;

    try{
      i = Integer.parseInt(status);
    }catch(Exception e) {
      i = 0;
    }

    boolean statusSet = i>0;
    if(i >= 1024 ) {           // return no record
      noRec = true;
      i = i - 1024;
    }
    if(i >= 512 ) {           // pending CSM PN
      pendCSMPN = true;
      i = i - 512;
    }
    if(i >= 256 ) {           //  new Approval
      newApproval = true;
      i = i - 256;
    }
    if(i >= 128 ) {           // new Size/Packaging
      newSizePackage = true;
      i = i - 128;
    }
    if(i >= 64 ) {            // pending Pricing
      pendPricing = true;
      i = i - 64;
    }
    if(i >= 32) {             // rejected
      rejected = true;
      i = i - 32;
    }
    if(i >= 16) {             // ready to order
      approved = true;
      i = i - 16;
    }
    if(i >= 8) {              // pending QC
      pendQC = true;
      i = i - 8;
    }
    if(i >= 4) {              // new Part
      newPart = true;
      i = i - 4;
    }
    if(i >= 2) {              // new material
      newMaterial = true;
      i = i - 2;
    }
    if(i >= 1) {              // pending CRA
      pendCRA = true;
      i = i - 1;
    }

    String s;
    if(reqID.length() > 0) {
      try{
        i = Integer.parseInt(reqID);
        wheres.addElement("request_id = '"+i+"'");
        requestor = "";
        facility = "";
        workArea = "";
        searchString = "";
        statusSet = false;
      }catch(Exception e){
        wheres.addElement("request_id = '"+i+"'");;
      }
    }

    // requestor
    if(requestor.length() > 0) {
      try{
        i = Integer.parseInt(requestor);
        wheres.addElement("requestor = '"+i+"'");
      }catch(Exception e){wheres.addElement("requestor = '"+i+"'");}
    }

    // facility
    if(facility.length() > 0 && !facility.equalsIgnoreCase("all")) {
      wheres.addElement("facility_id = '"+facility+"'");               //trong change
    }

    // work area
    if(workArea.length() > 0 && !workArea.equalsIgnoreCase("all")) {
      wheres.addElement("application = '"+workArea+"'");
    }

    // status
    if(statusSet) {
      Vector ss = new Vector();
      qu = "";
      qu = "request_status in (";
      if (noRec) qu = qu + "-1,";
      if (newMaterial)    qu = qu + "0,";
      if (newSizePackage) qu = qu + "1,";
      if (newPart)        qu = qu + "2,";
      if (newApproval)    qu = qu + "3,";
      if (pendQC) qu = qu + "5,";
      if (pendPricing) qu = qu + "8,";        //1-12-02 adding 6
      if (pendCRA)    qu = qu + "6,";    //1-12-02 replacing 6 with 0,1,2,3
      if (rejected) qu = qu + "7,";
      if (approved) qu = qu + "9,12,13,";
      if (pendCSMPN) qu = qu + "11,";
      if (qu.endsWith(",")) qu = qu.substring(0,qu.length() -1);
      qu = qu + ")";
      wheres.addElement(qu);
    }

    qu = "";
    if(searchString.length() > 0) {
      qu = qu + "(lower(material_desc) like lower('%"+searchString+"%') or ";
      qu = qu + "lower(manufacturer) like lower('%"+searchString+"%') or ";
      qu = qu + "lower(mfg_catalog_id) like lower('%"+searchString+"%') or ";
      qu = qu + "lower(cat_part_no) like lower('%"+searchString+"%')) ";       //trong 6/5/00
      wheres.addElement(qu);
    }

    String where = "";
    if(wheres.size() == 1){
      where = wheres.elementAt(0).toString();
    }else{
      for(int io=0;io<wheres.size();io++) {
        if(io==0) where = wheres.elementAt(io).toString();
        else where = where + " and " + wheres.elementAt(io).toString();
      }
    }

    query = "select * from new_chem_tracking_view ";
    query = query + "where " + where + " ";
    query = query + "order by request_id, part_id";

    //System.out.println("Query on NewChemTrack\n"+query);

    return query;
   }

}

