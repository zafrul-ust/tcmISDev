/*
 SQLWKS> desc catalog_add_request_new
 Column Name                    Null?    Type
 ------------------------------ -------- ----
 REQUEST_ID                     NOT NULL NUMBER
 REQUESTOR                               NUMBER(38)
 REQUEST_DATE                            DATE
 REQUEST_STATUS                          NUMBER
 CATALOG_ID                              VARCHAR2(30)
 CAT_PART_NO                             VARCHAR2(30)
 STOCKED                                 VARCHAR2(8)
 INIT_90_DAY                             NUMBER
 SHELF_LIFE                              NUMBER
 SHELF_LIFE_BASIS                        VARCHAR2(1)
 SHELF_LIFE_UNIT                         VARCHAR2(30)
 STORAGE_TEMP                            VARCHAR2(30)
 VENDOR_CONTACT_NAME                     VARCHAR2(100)
 VENDOR_CONTACT_EMAIL                    VARCHAR2(50)
 VENDOR_CONTACT_PHONE                    VARCHAR2(20)
 VENDOR_CONTACT_FAX                      VARCHAR2(20)
 ITEM_ID                                 NUMBER(38)
 STARTING_VIEW                           NUMBER
 SUGGESTED_VENDOR                        VARCHAR2(80)
 VENDOR_PART_NO                          VARCHAR2(60)
 REQUEST_REJECTED                        VARCHAR2(1)
 LAST_UPDATED                            DATE
 ENGINEERING_EVALUATION                  CHAR(1)
 ENG_EVAL_WORK_AREA                      VARCHAR2(30)
 ENG_EVAL_FACILITY_ID                    VARCHAR2(30)
 ACCOUNT_SYS_ID                          VARCHAR2(12)
 FREE_SAMPLE                             CHAR(1)
 SQLWKS> describe catalog_add_compatibility
 Column Name                    Null?    Type
 ------------------------------ -------- ----
 REQUEST_ID                              NUMBER
 cat_part_no                             VARCHAR2(40)
 PART_DESC                               VARCHAR2(120)
 */

package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.sql.*;

public class CatalogAddRequestNew {
  protected TcmISDBModel db;
  protected Integer request_id = null;
  protected Integer requestor = null;
  protected String request_date = null;
  protected Integer request_status = null;
  protected String catalog_id = null;
  protected String catalog_company_id = null;
  protected String qualityControl = null;
  protected String cat_part_no = null;
  protected String stocked = null;
  /*
      protected String shelf_life = null;
      protected String shelf_life_unit = null;
      protected String shelf_life_basis = null;
      protected String storage_temp = null;
   */
  protected Integer init_90_day = null;
  protected Integer starting_view = null;
  protected String suggested_vendor = null;
  protected String vendor_part_no = null;
  protected String request_rejected = null;
  protected String contact_name = null;
  protected String contact_email = null;
  protected String contact_phone = null;
  protected String contact_fax = null;
  protected Integer item_id = null;
  protected Boolean free_sample = null;
  protected String shelf_life_source = null;
  protected String submit_date = null;
  protected String part_desc = null;
  protected String consumable = null;
  protected String manageKitsAsSingleUnit = null;
  protected String category = null;
  protected String partNumberComment = null;

  //4-16-01
  protected String replaces_part_no = null;

  //trong 3/1/00
  protected String eng_eval_work_area = null;
  protected String eng_eval_facility_id = null;
  protected String engineering_eval = null;
  protected String acc_sys_id = null;
  protected String facility_id = null;
  protected String facility_desc = null; //1-15-02

  //1-15-03
  protected String cat_part_directed_chrg_no = null;
  protected String catalog_item_id = null;

  public String myCol[] = {
      "catalog_id", "cat_part_no", "stocked", "init_90_day", "item_id",
      "suggested_vendor", "vendor_part_no", "vendor_contact_name", "vendor_contact_email",
      "vendor_contact_phone", "vendor_contact_fax", "engineering_evaluation",
      "shelf_life_source", "manage_kits_as_single_unit"};
  public int myRowCtn = 16;

  public static final int STRING = 0;
  public static final int INT = 1;
  public static final int DATE = 2;

  // request levels
  public static final int NEW_MATERIAL = 0;
  public static final int NEW_SIZE_PACK = 1;
  public static final int NEW_PART = 2;
  public static final int NEW_APPROVAL = 3;
  public static final int NEW_GROUP = 4;
  public static final int PENDING_APPROVAL = 5;
  public static final int DRAFT = 6;

  static final int SPEC_VALUES = 4;

  protected boolean insertStatus;

  public CatalogAddRequestNew() throws java.rmi.RemoteException {
    insertStatus = false;
  }

  public CatalogAddRequestNew(TcmISDBModel db) throws java.rmi.RemoteException {
    this.db = db;
    insertStatus = false;
  }

  public void setDB(TcmISDBModel db) {
    this.db = db;
  }

  public void setRequestId(int num) {
    this.request_id = new Integer(num);
  }

  public Integer getRequestId() {
    return request_id;
  }

  public void setRequestor(int id) {
    this.requestor = new Integer(id);
  }

  public Integer getRequestor() {
    return requestor;
  }

  public void setRequestDate(String D) {
    this.request_date = D;
  }

  public String getRequestDate() {
    return request_date;
  }

  public void setRequestStatus(Integer D) {
    this.request_status = D;
  }

  public Integer getRequestStatus() {
    return request_status;
  }

  public void setCatalogId(String D) {
    this.catalog_id = D;
  }

  public String getCatalogId() {
    return catalog_id;
  }

  public void setCatalogCompanyId(String D) {
    this.catalog_company_id = D;
  }

  public String getCatalogCompanyId() {
    return catalog_company_id;
  }

  public void setQualityControl(String D) {
    this.qualityControl = D;
  }

  public String getQualityControl() {
    return qualityControl;
  }

  //trong 3/1/00
  public String getFacilityId() {
    return facility_id;
  }

  public void setFacilityId(String s) {
    facility_id = s;
  }

  public String getAccSysId() {
    return acc_sys_id;
  }

  public String getEngEvalFacilityId() {
    return eng_eval_facility_id;
  }

  public String getEngEvalWorkArea() {
    return eng_eval_work_area;
  }

  public String getEngineeringEval() {
    return engineering_eval;
  }

  public void setEngineeringEval(String D) {
    this.engineering_eval = D;
  }

  public void setAccSysId(String D) {
    this.acc_sys_id = D;
  }

  public void setCatPartNum(String D) {
    this.cat_part_no = D;
  }

  public String getCatPartNum() {
    return cat_part_no;
  }

  //4-16-01
  public void setReplacePartNum(String D) {
    this.replaces_part_no = D;
  }

  public String getReplacePartNum() {
    return replaces_part_no;
  }

  public void setStocked(String D) {
    this.stocked = D;
  }

  public String getStocked() {
    return stocked;
  }

  public void setInit90Day(Integer D) {
    this.init_90_day = D;
  }

  public Integer getInit90Day() {
    return init_90_day;
  }

  public void setManageKitsAsSingleUnit(String D) {
    this.manageKitsAsSingleUnit = D;
  }

  public String getManageKitsAsSingleUnit() {
    return manageKitsAsSingleUnit;
  }

  public void setItemId(Integer D) {
    this.item_id = D;
  }

  public Integer getItemId() {
    return item_id;
  }

  public void setStartingView(Integer D) {
    this.starting_view = D;
  }

  public Integer getStartingView() {
    return starting_view;
  }

  public void setSuggestedVendor(String D) {
    this.suggested_vendor = D;
  }

  public String getSuggestedVendor() {
    return suggested_vendor;
  }

  public void setVendorPartNo(String D) {
    this.vendor_part_no = D;
  }

  public String getVendorPartNo() {
    return vendor_part_no;
  }

  public void setRequestRejected(String D) {
    this.request_rejected = D;
  }

  public String getRequestRejected() {
    return request_rejected;
  }

  public void setContactName(String D) {
    this.contact_name = D;
  }

  public String getContactName() {
    return contact_name;
  }

  public void setContactPhone(String D) {
    this.contact_phone = D;
  }

  public String getContactPhone() {
    return contact_phone;
  }

  public void setContactFax(String D) {
    this.contact_fax = D;
  }

  public String getContactFax() {
    return contact_fax;
  }

  public void setContactEmail(String D) {
    this.contact_email = D;
  }

  public String getContactEmail() {
    return contact_email;
  }

  public void setFreeSample(Boolean D) {
    this.free_sample = D;
  }

  public Boolean getFreeSample() {
    return free_sample;
  }

  //1-15-02
  public String getFacilityDesc() {
    return facility_desc;
  }

  //3-7-02
  public String getShelfLifeSource() {
    return shelf_life_source;
  }

  public void setShelfLifeSource(String s) {
    shelf_life_source = s;
  }

  public String getSubmitDate() {
    return submit_date;
  }

  public void setSubmitDate(String s) {
    submit_date = s;
  }

  public String getPartDesc() {
    return part_desc;
  }

  public void setPartDesc(String s) {
    part_desc = s;
  }

  //1-15-03
  public String getCatPartDirectedChargeNo() {
    return cat_part_directed_chrg_no;
  }

  public void setCatPartDirectedChargeNo(String s) {
    cat_part_directed_chrg_no = s;
  }

  public String getCatalogItemID() {
    return catalog_item_id;
  }

  public void setCatalogItemID(String s) {
    catalog_item_id = s;
  }

  public String getConsumableOption() {
    return consumable;
  }

  public void setConsumableOption(String s) {
    consumable = s;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String s) {
    category = s;
  }

  public String getPartNumberComment() {
    return partNumberComment;
  }

  public void setPartNumberComment(String s) {
    partNumberComment = s;
  }

  public String getNowDate() throws Exception {
    String query = "select to_char(sysdate,'MM/dd/yyyy') from dual";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String next = new String("");
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        next = rs.getString(1);
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return next;
  }

  public Hashtable getSpecVNew() throws Exception {
    String query = null;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable h = new Hashtable();
    Hashtable res = new Hashtable();
    query = new String("select * from catalog_add_spec ");
    query = query + " where request_id=" + this.request_id;
    rs = null;
    String specid = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        res = new Hashtable();
        specid = rs.getString("SPEC_ID");
        if (BothHelpObjs.isBlankString(specid)) {
          continue;
        }
        res.put("SPEC_ID", BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_ID")));
        res.put("SPEC_VERSION", BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_VERSION")));
        res.put("SPEC_AMENDMENT", BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_AMENDMENT")));
        res.put("SPEC_TITLE", BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_TITLE")));
        res.put("SPEC_NAME", BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_NAME")));
        res.put("ONLINE", "N");

        if (h.containsKey(specid)) {
          h.remove(specid);

        }
        h.put(specid, res);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return h;
  }

  public Hashtable getSpecV() throws Exception {
    String query = null;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable h = new Hashtable();
    Hashtable res = new Hashtable();
    // get all spec id for cat_part_no and spec_id as part_no
    if (this.getCatPartNum().length() > 0) {
      query = "select a.spec_id, a.spec_name, a.spec_title, a.spec_version, a.spec_amendment, nvl(a.on_line,'N') on_line,decode(a.content,null,'','https://www.tcmis.com/spec/'||a.content) content, b.coc, b.coa, b.spec_library ";
      query += "from spec a, fac_spec b ";
      query += "where b.FACILITY_ID='" + this.getCatalogId()+"'";
      query += " and b.FAC_PART_NO='" + this.getCatPartNum()+"'";
      query += " and b.spec_id <> 'No Specification'";
      query += " and b.spec_id = a.spec_id";
      query += " and b.spec_library = a.spec_library";
      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        while (rs.next()) {
          res = new Hashtable();
          if (BothHelpObjs.isBlankString(rs.getString("SPEC_ID"))) {
            continue;
          }
          if ( ( (String) rs.getString("SPEC_ID")).equalsIgnoreCase("No Specification")) {
            continue;
          }
          res.put("SPEC_ID", rs.getString("SPEC_ID"));
          res.put("SPEC_VERSION", BothHelpObjs.makeBlankFromNull(rs.getString("spec_version")));
          res.put("SPEC_AMENDMENT", BothHelpObjs.makeBlankFromNull(rs.getString("spec_amendment")));
          res.put("SPEC_TITLE", BothHelpObjs.makeBlankFromNull(rs.getString("spec_title")));
          res.put("SPEC_NAME", BothHelpObjs.makeBlankFromNull(rs.getString("spec_name")));
          res.put("ONLINE", BothHelpObjs.makeBlankFromNull(rs.getString("on_line")));
          res.put("SPEC_URL",BothHelpObjs.makeBlankFromNull(rs.getString("content")));
          res.put("COC",new Boolean("Y".equalsIgnoreCase(BothHelpObjs.makeBlankFromNull(rs.getString("coc")))));
          res.put("COA",new Boolean("Y".equalsIgnoreCase(BothHelpObjs.makeBlankFromNull(rs.getString("coa")))));
          res.put("SPEC_LIBRARY",BothHelpObjs.makeBlankFromNull(rs.getString("spec_library")));
          h.put(rs.getString("SPEC_ID"), res);
        }
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
        throw e;
      } finally {
        dbrs.close();
      }
    }
    //get spec from catalog_add_spec
    query = new String("select * from catalog_add_spec ");
    query = query + " where spec_id <> 'No Specification' and request_id=" + this.request_id;
    rs = null;
    String specid = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        res = new Hashtable();
        specid = rs.getString("SPEC_ID");
        if (BothHelpObjs.isBlankString(specid)) {
          continue;
        }
        res.put("SPEC_ID", BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_ID")));
        res.put("SPEC_VERSION", BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_VERSION")));
        res.put("SPEC_AMENDMENT", BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_AMENDMENT")));
        res.put("SPEC_TITLE", BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_TITLE")));
        res.put("SPEC_NAME", BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_NAME")));
        res.put("ONLINE", "N");
        res.put("SPEC_URL","");
        res.put("COC",new Boolean("Y".equalsIgnoreCase(BothHelpObjs.makeBlankFromNull(rs.getString("coc")))));
        res.put("COA",new Boolean("Y".equalsIgnoreCase(BothHelpObjs.makeBlankFromNull(rs.getString("coa")))));
        res.put("SPEC_LIBRARY",BothHelpObjs.makeBlankFromNull(rs.getString("spec_library")));
        if (!h.containsKey(specid)) {
          h.put(specid, res);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return h;
  } //end of method

  /*
     SQL> describe catalog_add_flow_down;
    Name                            Null?    Type
    ------------------------------- -------- ----
    REQUEST_ID                      NOT NULL NUMBER
    FLOW_DOWN                       NOT NULL VARCHAR2(10)
   */
  public Vector getFlowV() throws Exception {
    String query = new String("select * from catalog_add_flow_down ");
    query = query + " where request_id=" + this.request_id;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector resV = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        resV.addElement(rs.getString("FLOW_DOWN") == null ? " " : rs.getString("FLOW_DOWN"));
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return resV;
  }

  //trong improve this by getting all data from one place (view) rather than getting it from multiple tables
  public static Hashtable getApprovalInformationNew(TcmISDBModel db, int req) throws Exception {
    String query = new String("select * from cat_add_user_group_desc_view ");
    query = query + " where request_id=" + req;

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable resH = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      //int cj = 5;      //6-08-01 for testing purpose
      while (rs.next()) {
        Hashtable values = new Hashtable();
        String facid = BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_ID"));
        String waid = BothHelpObjs.makeBlankFromNull(rs.getString("WORK_AREA"));
        String uid = BothHelpObjs.makeBlankFromNull(rs.getString("USER_GROUP_ID"));

        values.put("FAC", BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_NAME")));
        values.put("WA", BothHelpObjs.makeBlankFromNull(rs.getString("APPLICATION_DISPLAY")));
        values.put("FACID", facid);
        values.put("WAID", waid);
        values.put("PROCESS", BothHelpObjs.makeBlankFromNull(rs.getString("PROCESS_DESC")));
        values.put("UGID", uid);
        values.put("DIRECTED_CHARGE",BothHelpObjs.makeBlankFromNull(rs.getString("CHARGE_NUMBER")));

        //6-06-01 need to determine whether or not client is using paint booth
        if (usesPaintBooth(db)) { //assuming the is only one approval record per request (SWA)
          Hashtable tmph = getPaintBoothInfoForRequest(db, req);
          if (tmph.containsKey("PAINT_BOOTH")) {
            values.put("PAINT_BOOTH", (Boolean) tmph.get("PAINT_BOOTH"));
            values.put("PAINT_BOOTH_PERCENT", (String) tmph.get("PERCENT"));
          } else {
            values.put("PAINT_BOOTH", new Boolean(false));
            values.put("PAINT_BOOTH_PERCENT", "");
          }
        }

        values.put("UG", BothHelpObjs.makeBlankFromNull(rs.getString("USER_GROUP_DESC")));
        values.put("QTY1", BothHelpObjs.makeBlankFromNull(rs.getString("QUANTITY_1")));
        values.put("PER1", BothHelpObjs.makeBlankFromNull(rs.getString("PER_1")));
        values.put("UNIT1", BothHelpObjs.makeBlankFromNull(rs.getString("PERIOD_1")));
        values.put("QTY2", BothHelpObjs.makeBlankFromNull(rs.getString("QUANTITY_2")));
        values.put("PER2", BothHelpObjs.makeBlankFromNull(rs.getString("PER_2")));
        values.put("UNIT2", BothHelpObjs.makeBlankFromNull(rs.getString("PERIOD_2")));

        String table = "catalog_add_item";
        String where = "request_id = '" + req + "'";
        int partCount = HelpObjs.countQuery(db, table, where);
        for (int jj = 0; jj < partCount; jj++) {
          int pid = jj + 1;
          String q = "select * from catalog_add_fate_pad_view " +
              " where request_id=" + req +
              " and facility_id = '" + facid + "' and " +
              " work_area = '" + waid + "' and " +
              " user_group_id = '" + uid + "' and " +
              " part_id = '" + pid + "' order by fate_id";

          DBResultSet dbrs2 = null;
          ResultSet rs2 = null;
          try {
            dbrs2 = db.doQuery(q);
            rs2 = dbrs2.getResultSet();
            int partid = 0;
            int partid_hold = 0;
            int j = 0;
            Vector v = new Vector();
            //  need to come back to do it right
            while (rs2.next()) {
              partid = BothHelpObjs.makeZeroFromNull(rs2.getString("PART_ID"));
              v.addElement(BothHelpObjs.makeBlankFromNull(rs2.getString("FATE_ID")));
              v.addElement(BothHelpObjs.makeBlankFromNull(rs2.getString("FATE_PERCENTAGE")));
            }

            partid -= 1;
            values.put("FATE" + partid, v);
            //System.out.println("\n\n\n========== part id : " + partid + " " + v + " fate " + values.get("FATE"+partid) + " \n\n\n");
          } catch (Exception e) {
            e.printStackTrace();
            throw e;
          } finally {
            dbrs2.close();
          }
        }
        //System.out.println("\n\n\n========== values : " +values +" \n\n\n");

        String key = facid + waid;
        resH.put(key, values);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return resH;
  }

  //6-11-01
  public static Hashtable getPaintBoothInfoForRequest(TcmISDBModel db, int request) throws Exception {
    String query = "select percentage from catalog_add_category_view where subcategory = 'paint booth' and request_id = " + request;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable h = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) { //assuming only one subcategory per request paint_booth or hanger
        h.put("PAINT_BOOTH", new Boolean(true));
        h.put("PERCENT", BothHelpObjs.makeBlankFromNull(rs.getString("percentage")));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return (h);
  }

  //6-06-01
  public static boolean usesPaintBooth(TcmISDBModel db) throws Exception {
    String query = "select feature_mode from tcmis_feature where feature = 'newChem.3.paintBooth'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String val = "";
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        val = BothHelpObjs.makeBlankFromNull(rs.getString("feature_mode"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return (!"0".equalsIgnoreCase(val));
  }

  // before trong 4/27
  public static Hashtable getApprovalInformation(TcmISDBModel db, int req) throws Exception {
    String query = new String("select * from catalog_add_user_group ");
    query = query + " where request_id=" + req;

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable resH = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Hashtable values = new Hashtable();
        String facid = BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_ID"));
        String waid = BothHelpObjs.makeBlankFromNull(rs.getString("WORK_AREA"));
        String uid = BothHelpObjs.makeBlankFromNull(rs.getString("USER_GROUP_ID"));

        Facility fac = new Facility(db);
        fac.setFacilityId(facid);
        fac.load();
        ApplicationView app = new ApplicationView(db);
        app.setApplication(waid);
        app.setFacilityId(facid);
        app.load();

        values.put("FAC", BothHelpObjs.makeBlankFromNull(fac.getFacilityName()));
        values.put("WA", waid + " - " + BothHelpObjs.makeBlankFromNull(app.getApplicationDesc()));
        values.put("FACID", facid);
        values.put("WAID", waid);
        values.put("PROCESS", BothHelpObjs.makeBlankFromNull(rs.getString("PROCESS_DESC")));
        values.put("UGID", uid);

        UserGroup ug = new UserGroup(db);
        ug.setGroupId(uid);
        ug.setGroupFacId(facid);
        ug.load();

        values.put("UG", BothHelpObjs.makeBlankFromNull(ug.getGroupDesc()));
        values.put("QTY1", BothHelpObjs.makeBlankFromNull(rs.getString("QUANTITY_1")));
        values.put("PER1", BothHelpObjs.makeBlankFromNull(rs.getString("PER_1")));
        values.put("UNIT1", BothHelpObjs.makeBlankFromNull(rs.getString("PERIOD_1")));
        values.put("QTY2", BothHelpObjs.makeBlankFromNull(rs.getString("QUANTITY_2")));
        values.put("PER2", BothHelpObjs.makeBlankFromNull(rs.getString("PER_2")));
        values.put("UNIT2", BothHelpObjs.makeBlankFromNull(rs.getString("PERIOD_2")));

        /* before trong 4/27
                     String q = "select * from catalog_add_fate_new " +
                " where request_id="+req +
                " and facility_id = '"+facid+"' and "+
                " work_area = '"+waid+"' and " +
                " user_group_id = '"+uid+"' order by part_id";  */

        String q = "select * from catalog_add_fate_pad_view " +
            " where request_id=" + req +
            " and facility_id = '" + facid + "' and " +
            " work_area = '" + waid + "' and " +
            " user_group_id = '" + uid + "' order by part_id,fate_id";

        DBResultSet dbrs2 = null;
        ResultSet rs2 = null;
        try {
          dbrs2 = db.doQuery(q);
          rs2 = dbrs2.getResultSet();
          int partid = 0;
          int partid_hold = 0;
          int j = 0;
          Vector v = new Vector();
          while (rs2.next()) {
            partid = BothHelpObjs.makeZeroFromNull(rs2.getString("PART_ID"));
            if (partid != partid_hold && j > 0) {
              values.put("FATE" + partid_hold, v);
              v = new Vector();
            }
            j++;
            partid_hold = partid;
            v.addElement(BothHelpObjs.makeBlankFromNull(rs2.getString("FATE_ID")));
            v.addElement(BothHelpObjs.makeBlankFromNull(rs2.getString("FATE_PERCENTAGE")));
            //System.out.println("\n\n\n========== part id in while : " + partid + " " + v + " \n\n\n");
          }

          values.put("FATE" + partid, v);
        } catch (Exception e) {
          e.printStackTrace();
          throw e;
        } finally {
          dbrs2.close();
        }

        String key = facid + waid;
        resH.put(key, values);

      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return resH;
  }

  public void delete() throws Exception {
    this.deleteComp(request_id);
    this.deleteSpec(request_id);
    this.deleteFlow(request_id);

    String query = "delete catalog_add_request_new where request_id = " + request_id.toString();
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
  }

  //1-15-02 contains facility_desc
  public void load3() throws Exception {
    String query = "select a.*,b.facility_name,b.facility_id,cai.item_id,cai.suggested_vendor,cai.vendor_contact_name,cai.vendor_contact_email,cai.vendor_contact_phone,cai.vendor_contact_fax"+
	                " from catalog_add_request_new a,catalog_add_item cai, facility b where a.eng_eval_facility_id = b.facility_id and a.request_id = " + request_id.toString()+
		  				 " and a.company_id = cai.company_id and a.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1";
	 DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        request_id = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("REQUEST_ID")));
        requestor = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("REQUESTOR")));
        request_date = BothHelpObjs.formatDate("toCharfromDB", BothHelpObjs.makeBlankFromNull(rs.getString("REQUEST_DATE")));
        request_status = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("REQUEST_STATUS")));
        catalog_id = BothHelpObjs.makeBlankFromNull(rs.getString("CATALOG_ID"));
        cat_part_no = BothHelpObjs.makeBlankFromNull(rs.getString("CAT_PART_NO"));
        replaces_part_no = BothHelpObjs.makeBlankFromNull(rs.getString("REPLACES_PART_NO"));
        stocked = BothHelpObjs.makeBlankFromNull(rs.getString("STOCKED"));
		  init_90_day = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("INIT_90_DAY")));

		  item_id = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("ITEM_ID")));
        starting_view = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("STARTING_VIEW")));
        suggested_vendor = BothHelpObjs.makeBlankFromNull(rs.getString("SUGGESTED_VENDOR"));
        vendor_part_no = BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_PART_NO"));
        request_rejected = BothHelpObjs.makeBlankFromNull(rs.getString("REQUEST_REJECTED"));
        contact_name = BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_CONTACT_NAME"));
        contact_email = BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_CONTACT_EMAIL"));
        contact_fax = BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_CONTACT_FAX"));
        contact_phone = BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_CONTACT_PHONE"));
        engineering_eval = BothHelpObjs.makeBlankFromNull(rs.getString("ENGINEERING_EVALUATION"));
        eng_eval_work_area = BothHelpObjs.makeBlankFromNull(rs.getString("ENG_EVAL_WORK_AREA"));
        eng_eval_facility_id = BothHelpObjs.makeBlankFromNull(rs.getString("ENG_EVAL_FACILITY_ID"));
        acc_sys_id = BothHelpObjs.makeBlankFromNull(rs.getString("ACCOUNT_SYS_ID"));
        String freeS = BothHelpObjs.makeBlankFromNull(rs.getString("FREE_SAMPLE"));
        free_sample = new Boolean(freeS.equalsIgnoreCase("Y"));
        facility_desc = BothHelpObjs.makeBlankFromNull(rs.getString("facility_name"));
        facility_id = BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_ID"));
		  catalog_company_id = BothHelpObjs.makeBlankFromNull(rs.getString("CATALOG_COMPANY_ID"));
		  break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return;
  }

  public Hashtable getPOSSInfo(String reqID) throws Exception {
    Hashtable result = new Hashtable(11);
    String query = "select carn.shelf_life,carn.shelf_life_basis,carn.shelf_life_unit,cai.item_id,carn.part_description,carn.cat_part_no,carn.catalog_id,carn.part_group_no,carn.catalog_company_id," +
        "carn.init_90_day,carn.stocked,c.spec_library from catalog_add_request_new carn, catalog_add_item cai, catalog c where carn.catalog_id = c.catalog_id and carn.request_id = " + reqID+
		  " and carn.company_id = cai.company_id and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1";
	 DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        result.put("SHELF_LIFE", BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life")));
        result.put("SHELF_LIFE_BASIS", rs.getString("shelf_life_basis"));
        result.put("SHELF_LIFE_UNIT", rs.getString("shelf_life_unit"));
        result.put("ITEM_ID", rs.getString("item_id"));
        result.put("PART_DESCRIPTION", BothHelpObjs.makeBlankFromNull(rs.getString("part_description")));
        result.put("CAT_PART_NO", rs.getString("cat_part_no"));
        result.put("CATALOG_ID", rs.getString("catalog_id"));
        result.put("PART_GROUP_NO", rs.getString("part_group_no"));
        result.put("ESTIMATE_QUARTERLY_USAGE", BothHelpObjs.makeBlankFromNull(rs.getString("init_90_day")));
        result.put("STOCKED", rs.getString("stocked"));
        result.put("SPEC_LIBRARY", rs.getString("spec_library"));
		  result.put("CATALOG_COMPANY_ID",rs.getString("catalog_company_id"));
		}
    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();
    }
    return result;
  }

  public void load() throws Exception {
    String query = "select carn.*,cai.item_id,cai.suggested_vendor,cai.vendor_contact_name,cai.vendor_contact_email,cai.vendor_contact_phone,"+
	  					 "cai.vendor_contact_fax from catalog_add_request_new carn, catalog_add_item cai where carn.request_id = " + request_id.toString()+
		  				 " and carn.company_id = cai.company_id and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1";
	 DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        request_id = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("REQUEST_ID")));
        requestor = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("REQUESTOR")));
        request_date = BothHelpObjs.formatDate("toCharfromDB", BothHelpObjs.makeBlankFromNull(rs.getString("REQUEST_DATE")));
        request_status = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("REQUEST_STATUS")));
        catalog_id = BothHelpObjs.makeBlankFromNull(rs.getString("CATALOG_ID"));
		  catalog_company_id = BothHelpObjs.makeBlankFromNull(rs.getString("CATALOG_COMPANY_ID"));
		  cat_part_no = BothHelpObjs.makeBlankFromNull(rs.getString("CAT_PART_NO"));
        replaces_part_no = BothHelpObjs.makeBlankFromNull(rs.getString("REPLACES_PART_NO"));
        part_desc = BothHelpObjs.makeBlankFromNull(rs.getString("part_description"));
        stocked = BothHelpObjs.makeBlankFromNull(rs.getString("STOCKED"));
		  init_90_day = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("INIT_90_DAY")));

		  manageKitsAsSingleUnit = BothHelpObjs.makeBlankFromNull(rs.getString("MANAGE_KITS_AS_SINGLE_UNIT"));
        item_id = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("ITEM_ID")));
        starting_view = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("STARTING_VIEW")));
        suggested_vendor = BothHelpObjs.makeBlankFromNull(rs.getString("SUGGESTED_VENDOR"));
        vendor_part_no = BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_PART_NO"));
        request_rejected = BothHelpObjs.makeBlankFromNull(rs.getString("REQUEST_REJECTED"));
        contact_name = BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_CONTACT_NAME"));
        contact_email = BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_CONTACT_EMAIL"));
        contact_fax = BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_CONTACT_FAX"));
        contact_phone = BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR_CONTACT_PHONE"));
        engineering_eval = BothHelpObjs.makeBlankFromNull(rs.getString("ENGINEERING_EVALUATION"));
        eng_eval_work_area = BothHelpObjs.makeBlankFromNull(rs.getString("ENG_EVAL_WORK_AREA"));
        eng_eval_facility_id = BothHelpObjs.makeBlankFromNull(rs.getString("ENG_EVAL_FACILITY_ID"));
        acc_sys_id = BothHelpObjs.makeBlankFromNull(rs.getString("ACCOUNT_SYS_ID"));
        String freeS = BothHelpObjs.makeBlankFromNull(rs.getString("FREE_SAMPLE"));
        free_sample = new Boolean(freeS.equalsIgnoreCase("Y"));
        shelf_life_source = BothHelpObjs.makeBlankFromNull(rs.getString("SHELF_LIFE_SOURCE"));
        submit_date = BothHelpObjs.formatDate("toCharfromDB", BothHelpObjs.makeBlankFromNull(rs.getString("SUBMIT_DATE")));
        cat_part_directed_chrg_no = BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_directed_chrg_no"));
        catalog_item_id = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_item_id"));
        consumable = BothHelpObjs.makeBlankFromNull(rs.getString("consumable"));
        category = BothHelpObjs.makeBlankFromNull(rs.getString("category"));
        partNumberComment = BothHelpObjs.makeBlankFromNull(rs.getString("part_no_comment"));
        break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return;
  }

  //trong 1-15-01
  public boolean isEngEval(int reId) throws Exception {
    boolean eval = false;
    String query = "select engineering_evaluation from catalog_add_request_new where request_id = " + reId;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String value = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        value = BothHelpObjs.makeBlankFromNull(rs.getString("engineering_evaluation"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      return eval;
    } finally {
      dbrs.close();
    }
    return value.equalsIgnoreCase("y");
  }

  public String getEngEvalType(int reId) throws Exception {
    String query = "select eval_type from catalog_add_request_new where request_id = " + reId;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String value = "";
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        value = BothHelpObjs.makeBlankFromNull(rs.getString("eval_type"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      return value;
    } finally {
      dbrs.close();
    }
    if (value.equalsIgnoreCase("new")) {
      value = "newEval";
    } else if (value.equalsIgnoreCase("reorder")) {
      value = "oldEval";
    } else if (value.equalsIgnoreCase("convert")) {
      value = "convertEval";
    } else {
      value = "normal";
    }

    return value;
  }

  public void insert() throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String dummy = new String("");

    String query = new String("insert into catalog_add_request_new (request_id,requestor,request_date)");
    query = query + " values (" + request_id.toString() + "," + requestor.toString() + ",";
    if (request_date.equals("nowDate")) {
      query = query + " SYSDATE)";
    } else {
      try {
        dbrs = db.doQuery("select to_char(sysdate,'HH24:Mi:SS') from dual");
        rs = dbrs.getResultSet();
        if (rs.next()) {
          dummy = " " + rs.getString(1);
        }
      } catch (Exception e) {
        throw e;
      } finally {
        dbrs.close();
      }
      query = query + " to_date('" + request_date + dummy + "','MM/dd/yyyy HH24:MI:SS'))";
    }

    try {
      db.doUpdate(query);
      setInsertStatus(true);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      setInsertStatus(false);
      throw e;
    }
  }

  public void insert(String col, String val, int type) throws Exception {
    Integer I;
    DBResultSet dbrs = null;
    ResultSet rs = null;

    String query = new String("update catalog_add_request_new set " + col + " = ");
    switch (type) {
      case INT:
        I = new Integer(val);
        query = query + I.toString();
        break;
      case DATE:
        //System.out.println("\n\n------ what's going on: " + val);
        if (val.equals("nowDate")) {
          query = query + " SYSDATE";
        } else {
          query = query + " to_date('" + val + "','MM/dd/yyyy HH24:MI:SS')";
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
      db.doUpdate(query);
      setInsertStatus(true);

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      setInsertStatus(false);
      throw e;
    }
  }

  //12-14-01
  public void updateShelfLifeTemp(String catalogID, String catPartNo) throws Exception {
    String query = "update catalog_add_request_new set (storage_temp,shelf_life,shelf_life_unit,shelf_life_basis) = ";
    query += "(select storage_temp,decode(shelf_life_days,'-1','',shelf_life_days),decode(shelf_life_days,'-1','Indefinite','days'),shelf_life_basis ";
    query += "from catalog_part_shelf_life where rownum = 1 and cat_part_no = '" + catPartNo + "' and catalog_id = '" + catalogID + "') ";
    query += "where request_id = " + this.getRequestId();
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  //trong 3/30
  public void insert(Vector v) throws Exception {
    String query = "update catalog_add_request_new set ";
    for (int i = 0; i < v.size(); i++) {
      query += myCol[i].toLowerCase() + " = '" + v.elementAt(i).toString() + "', ";
    }
    //removing the last ','
    query = query.substring(0, query.length() - 2);
    query += " where request_id = " + request_id.toString();

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }

  }

  public void insertIntoCatalogAddUserGroup(String oldid, String newid) throws Exception {
    String query = "insert into catalog_add_user_group (request_id, facility_id,work_area,";
    query += " user_group_id, process_desc, quantity_1, per_1, period_1, quantity_2, per_2, period_2)";
    query += " select '" + newid + "', facility_id,work_area, user_group_id, process_desc,";
    query += " quantity_1, per_1, period_1, quantity_2, per_2, period_2 from catalog_add_user_group";
    query += " where request_id = '" + oldid + "'";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
  }

  public void insertIntoCatalogAddFateNew(String oldid, String newid) throws Exception {
    String query = "insert into catalog_add_fate_new (request_id, facility_id,work_area,";
    query += " user_group_id, part_id, fate_id, fate_percentage)";
    query += " select '" + newid + "', facility_id,work_area, user_group_id, part_id,";
    query += " fate_id, fate_percentage from catalog_add_fate_new";
    query += " where request_id = '" + oldid + "'";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
  }

  //specs
  void deleteSpec(Integer reqid, String spec) throws Exception {

    String query = null;

    int count = HelpObjs.countQuery(db, "catalog_add_spec", "request_id=" + reqid.toString() + " and spec_id ='" + spec + "'");
    if (count > 0) {
      query = "delete catalog_add_spec where request_id=" + reqid + " and spec_id ='" + spec + "'";
      try {
        db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
        throw e;
      }
    }

  }

  void deleteSpec(Integer reqid) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = null;

    int count = HelpObjs.countQuery(db, "catalog_add_spec", "request_id=" + reqid.toString());
    if (count > 0) {
      query = "delete catalog_add_spec where request_id=" + reqid;
      try {
        db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
        throw e;
      }
    }

  }

  public void insertSpec(Integer reqid, String spec, String version, String amend, String name, String title, String date) throws Exception {

    if (BothHelpObjs.isBlankString(spec)) {
      return;
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = null;

    deleteSpec(reqid, spec);
    String maxLineItem = getMaxLineItem(reqid.toString());
    query = new String("insert into catalog_add_spec (request_id,spec_title, spec_id,spec_version,spec_amendment,spec_name,line_item,spec_source)");
    query = query + " values ( " + reqid +
        ",'" + (title == null || title.length() < 1 ? spec : title) + "'" +
        ",'" + spec + "'" +
        ",'" + version + "'" +
        ",'" + amend + "'" +
        ",'" + name + "'" +
        ",'" + maxLineItem + "'" + 
        ",'catalog_add_spec')";
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      setInsertStatus(false);
      throw e;
    }
  }

  String getMaxLineItem(String reqid) {
        String maxLineItem = "1";
        String query = "select nvl(max(line_item)+1,1) max_line_item from catalog_add_spec where request_id = "+reqid;

        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                maxLineItem = BothHelpObjs.makeBlankFromNull(rs.getString("max_line_item"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            dbrs.close();
        }
        return maxLineItem;
  } //end of method

  //
  //flow
  void deleteFlow(Integer reqid, String flow) throws Exception {
    DBResultSet dbrs = null;

    int count = HelpObjs.countQuery(db, "catalog_add_flow_down", "request_id=" + reqid.toString() + " and flow_down='" + flow + "'");
    if (count > 0) {
      String query = "delete catalog_add_flow_down where request_id=" + reqid + " and flow_down='" + flow + "'";
      try {
        db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
        throw e;
      }
    }
  }

  void deleteFlow(Integer reqid) throws Exception {

    String query = null;

    int count = HelpObjs.countQuery(db, "catalog_add_flow_down", "request_id=" + reqid.toString());
    if (count > 0) {
      query = "delete catalog_add_flow_down where request_id=" + reqid;
      try {
        db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
        throw e;
      }
    }
  }

  public void insertFlow(Integer reqid, String flow) throws Exception {

    if (BothHelpObjs.isBlankString(flow)) {
      return;
    }

    String query = null;

    this.deleteFlow(reqid, flow);

    query = new String("insert into catalog_add_flow_down (request_id,flow_down)");
    query = query + " values ( " + reqid +
        ",'" + flow + "')";

    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }

  }

  //

  public void deleteComp(Integer reqid) throws Exception {
    Integer I;
    DBResultSet dbrs = null;
    ResultSet rs = null;

    String query = new String("delete catalog_add_compatibility where request_id = " + reqid);
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
  }

  public void insertComp(Vector comp) throws Exception {
    Integer I;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = new String();

    this.deleteComp(request_id);

    for (int i = 0; i < comp.size(); i = i + 2) {
      query = " insert into catalog_add_compatibility (REQUEST_ID,cat_part_no,PART_DESC) ";
      query = query + " values (" + request_id.toString() + ",'" + comp.elementAt(i).toString() + "','" + comp.elementAt(i + 1).toString() + "')";
      try {
        db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
        setInsertStatus(false); ;
        throw e;
      }
    }
  }

  public void setInsertStatus(boolean b) {
    this.insertStatus = b;
  }

  public boolean getInsertStatus() {
    return this.insertStatus;
  }

  public int getNext() throws Exception {
    String query = "select request_seq.nextval from dual";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int next = 0;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        next = (int) rs.getInt(1);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return next;
  }

  public void commit() throws Exception {
    return;
  }

  public void rollback() throws Exception {
    return;
  }

  public int getCount() throws Exception {
    String query = "select count(*) from catalog_add_request_new where request_id = " + request_id.toString();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int i = -1;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        i = (int) rs.getInt(1);
        break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return i;
  }

  public void setDefaultShelfLifeSource(int reqID, String itemID) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      //first find source
      String query = "select distinct nvl(c.source,' ') source,nvl(c.inventory_group,' ') inventory_group from" +
          " catalog_add_request_new a, facility b, component_inventory_group c, part d" +
          " where a.request_id = " + reqID + " and d.item_id = " + itemID + " and a.eng_eval_facility_id = b.facility_id" +
          " and b.inventory_group_default = c.inventory_group and c.item_id = d.item_id and c.part_id = d.part_id";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      String source = "";
      String inventoryGroup = "";
      while (rs.next()) {
        source = rs.getString("source");
        inventoryGroup = rs.getString("inventory_group");
      }
      //next find manage_kits_as_single_unit
      String manageKitsAsSingleUnit = "N";
      if (inventoryGroup.length() > 0) {
        query = "select fx_inseparable_item(" + itemID + ",'" + inventoryGroup + "') manage_kits_as_single_unit from dual";
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        while (rs.next()) {
          manageKitsAsSingleUnit = rs.getString("manage_kits_as_single_unit");
        }
      }
      //now update catalog_add_request_new
      boolean updateFlag = false;
      query = "update catalog_add_request_new set";
      if (source.length() > 0) {
        if ("Manufacturer".equalsIgnoreCase(source)) {
          query += " shelf_life_source = 'Mfg',";
        } else {
          query += " shelf_life_source = 'User',";
        }
        updateFlag = true;
      } else {
        query += " shelf_life_source = 'Mfg',";
        updateFlag = true;
      }
      if ("Y".equalsIgnoreCase(manageKitsAsSingleUnit)) {
        query += "manage_kits_as_single_unit = 'Y',";
        updateFlag = true;
      } else {
        query += "manage_kits_as_single_unit = null,";
        updateFlag = true;
      }
      if (updateFlag) {
        //remove last commas ','
        query = query.substring(0, query.length() - 1);
        query += " where request_id = " + reqID;
        db.doUpdate(query);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
  } //end of method

} //end of class
