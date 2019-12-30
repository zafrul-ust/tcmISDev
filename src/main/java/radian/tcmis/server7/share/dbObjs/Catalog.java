/*
 SQLWKS> describe catalog_snapshot
 Column Name                    Null?    Type
 ------------------------------ -------- ----
 ITEM_ID                                 NUMBER
 FAC_ITEM_ID                             VARCHAR2(30)
 MATERIAL_DESC                           VARCHAR2(80)
 GRADE                                   VARCHAR2(30)
 MFG_DESC                                VARCHAR2(60)
 PART_SIZE                               NUMBER
 SIZE_UNIT                               VARCHAR2(30)
 PKG_STYLE                               VARCHAR2(71)
 TYPE                                    VARCHAR2(5)
 PRICE                                   NUMBER
 SHELF_LIFE                              NUMBER
 SHELF_LIFE_UNIT                         VARCHAR2(30)
 USEAGE                                  CHAR(1)
 USEAGE_UNIT                             CHAR(1)
 APPROVAL_STATUS                         VARCHAR2(30)
 PERSONNEL_ID                            NUMBER
 USER_GROUP_ID                           VARCHAR2(30)
 APPLICATION                             VARCHAR2(30)
 FACILITY_ID                             VARCHAR2(30)
 SPEC_ON_LINE                            CHAR(1)
 MSDS_ON_LINE                            CHAR(1)
 MATERIAL_ID                             NUMBER
 SPEC_ID                                 VARCHAR2(400)
 TRADE_NAME                              VARCHAR2(80)
 SPEC_LIBRARY                            CHAR(1)
 MFG_PART_NO                             VARCHAR2(30)
 */
package radian.tcmis.server7.share.dbObjs;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 04-01-03 Bug fixing. It was giving error when all catalogs was choosen and the sort by was catalog
 * 07-14-03 Code Cleanup and added MSDS No
 * 08-29-03 Sending error emails when an query fails
 * 11-10-03 Escaping ' character
 * 11-12-03 Changed the function to get the MSDS number for catalog materials
 * 03-11-04 Changed the noncatalog_material_view query (Non Catalog Stuff) to include facility Id in the where clause and the resultset
 * 03-12-04 Made Changes to accomodate Sikorsky MSDSs. These are MSDS with no material ID
 * 08-02-04 Added '' fac_msds_id to the query
 */

public class Catalog {
  private TcmISDBModel db;
  private boolean union = false;
  private Integer item_id;
  private String fac_item_id;
  private String material_desc;
  private String grade;
  private String mfg_desc;
  private String pkg_style;
  private float part_size;
  private String size_unit;
  private String type;
  private String price;
  private float shelf_life;
  private String shelf_life_unit;
  private String useage;
  private String useage_unit;
  private String approval_status;
  private Integer personnel_id;
  private String user_group_id;
  private String facility_id;
  private String application;
  private String specOn;
  private String msdsOn;
  private Integer matid;
  private String specid;
  private String mfgPartNum;
  //trong 3-27-01
  private int case_qty;

  private boolean msdsdatabase = false;

  public void setMsdsDatabase(boolean sdfg) {
    this.msdsdatabase = sdfg;
  }

  public String getMsdsDatabase() {
    return mfg_desc;
  }

  public Catalog() throws java.rmi.RemoteException {

  }

  public Catalog(TcmISDBModel db) throws java.rmi.RemoteException {
    this.db = db;
  }

  public void setDB(TcmISDBModel db) {
    this.db = db;
  }

  public void setItemId(int id) {
    this.item_id = new Integer(id);
  }

  public Integer getItemId() {
    return item_id;
  }

  public void setFacItemId(String id) {
    this.fac_item_id = id;
  }

  public String getFacItemId() {
    return fac_item_id;
  }

  public void setMaterialDesc(String d) {
    this.material_desc = d;
  }

  public String getMaterialDesc() {
    return material_desc;
  }

  public void setGrade(String d) {
    this.grade = d;
  }

  public String getGrade() {
    return grade;
  }

  public void setMfgDesc(String d) {
    this.mfg_desc = d;
  }

  public String getMfgDesc() {
    return mfg_desc;
  }

  public void setPkgStyle(String d) {
    this.pkg_style = d;
  }

  public void setMfgPartNum(String s) {
    mfgPartNum = s;
  }

  public String getPkgStyle() {
    return pkg_style;
  }

  public void setPartSize(float d) {
    this.part_size = d;
  }

  public float getPartSize() {
    return part_size;
  }

  public void setSizeUnit(String d) {
    this.size_unit = d;
  }

  public String getSizeUnit() {
    return size_unit;
  }

  public void setType(String d) {
    this.type = BothHelpObjs.makeBlankFromNull(d);
  }

  public String getType() {
    return BothHelpObjs.makeBlankFromNull(type);
  }

  public void setPrice(String d) {
    this.price = d;
  }

  public String getPrice() {
    return price;
  }

  public void setShelfLife(float d) {
    this.shelf_life = d;
  }

  public float getShelfLife() {
    return shelf_life;
  }

  public void setShelfLifeUnit(String d) {
    this.shelf_life_unit = d;
  }

  public String getShelfLifeUnit() {
    return shelf_life_unit;
  }

  public void setUseage(String d) {
    this.useage = d;
  }

  public String getUseage() {
    return useage;
  }

  public void setUseageUnit(String d) {
    this.useage_unit = d;
  }

  public String getUseageUnit() {
    return useage;
  }

  public void setApprovalStatus(String d) {
    this.approval_status = d;
  }

  public String getApprovalStatus() {
    return approval_status;
  }

  public void setUnion(boolean value) {
    union = value;
  }

  public void setPersonnelId(int id) {
    this.personnel_id = new Integer(id);
  }

  public Integer getPersonnelId() {
    return personnel_id;
  }

  public void setUserGroupId(String d) {
    this.user_group_id = d;
  }

  public String getUserGroupId() {
    return user_group_id;
  }

  public void setFacilityId(String d) {
    this.facility_id = d;
  }

  public String getFacilityId() {
    return facility_id;
  }

  public void setApplication(String d) {
    this.application = d;
  }

  public String getApplication() {
    return application;
  }

  public void setMsdsOn(String d) {
    this.msdsOn = d;
  }

  public String getMsdsOn() {
    return msdsOn;
  }

  public void setSpecOn(String d) {
    this.specOn = d;
  }

  public String getSpecOn() {
    return specOn;
  }

  public void setMatId(int d) {
    this.matid = new Integer(d);
  }

  public Integer getMatId() {
    return matid;
  }

  public void setSpecId(String d) {
    this.specid = d;
  }

  public String getMfgPartNum() {
    return mfgPartNum;
  }

  public String getSpecId() {
    return specid;
  }

  //trong 3-27-01
  public void setCaseQty(int d) {
    this.case_qty = d;
  }

  public int getCaseQty() {
    return case_qty;
  }

  public Hashtable getFacilityCatalog(String userID) throws Exception {
    Hashtable result = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select cf.facility_id,cf.catalog_id,cf.catalog_add_allowed from fac_pref fp, catalog_facility cf" +
          " where fp.personnel_id = " + userID + " and fp.facility_id = cf.facility_id order by cf.facility_id";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      String lastFacID = "";
      while (rs.next()) {
        String currentFacID = rs.getString("facility_id");
        if (lastFacID.equals(currentFacID)) {
          //catalog_id
          Vector v = (Vector) result.get(currentFacID);
          v.addElement(rs.getString("catalog_id"));
          result.put(currentFacID, v);
          //catalog_add_allowed
          Vector v2 = (Vector) result.get(currentFacID + "CATALOG_ADD_ALLOWED");
          v2.addElement(rs.getString("catalog_add_allowed"));
          result.put(currentFacID + "CATALOG_ADD_ALLOWED", v2);
        } else {
          //catalog
          Vector v = new Vector(3);
          v.addElement(rs.getString("catalog_id"));
          result.put(currentFacID, v);
          //catalog_add_allowed
          Vector v2 = new Vector(3);
          v2.addElement(rs.getString("catalog_add_allowed"));
          result.put(currentFacID + "CATALOG_ADD_ALLOWED", v2);
        }
        lastFacID = currentFacID;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return result;
  }

  public String getCompanyId() throws Exception {
    String companyId = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select company_id from company";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        companyId = rs.getString("company_id");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return companyId;
  }

  public void load() throws Exception {
    String query = "select * from catalog_snapshot where item_id = " + item_id + " and facility_id = '" + facility_id + "'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      if (rs.next()) {
        setItemId( (int) rs.getInt("ITEM_ID"));
        setFacItemId(rs.getString("FAC_ITEM_ID"));
        setMaterialDesc(rs.getString("MATERIAL_DESC"));
        setGrade(rs.getString("GRADE"));
        setMfgDesc(rs.getString("MFG_DESC"));
        setPartSize( (float) rs.getFloat("PART_SIZE"));
        setSizeUnit(rs.getString("SIZE_UNIT"));
        setPkgStyle(rs.getString("PKG_STYLE"));
        setType(rs.getString("TYPE"));
        setPrice(BothHelpObjs.makeBlankFromNull(rs.getString("PRICE")));
        setShelfLife( (float) rs.getFloat("SHELF_LIFE"));
        setShelfLifeUnit(rs.getString("SHELF_LIFE_UNIT"));
        setUseage(rs.getString("USEAGE"));
        setUseageUnit(rs.getString("USEAGE_UNIT"));
        setApprovalStatus(rs.getString("APPROVAL_STATUS"));
        setPersonnelId( (int) rs.getInt("PERSONNEL_ID"));
        setUserGroupId(rs.getString("USER_GROUP_ID"));
        setApplication(rs.getString("APPLICATION"));
        setFacilityId(rs.getString("FACILITY_ID"));
        setMsdsOn(rs.getString("MSDS_ON_LINE"));
        setSpecOn(rs.getString("SPEC_ON_LINE"));
        setMatId( (int) rs.getInt("MATERIAL_ID"));
        setSpecId(rs.getString("SPEC_ID"));
        setMfgPartNum(rs.getString("MFG_PART_NO"));
        //trong 3-27-01
        setCaseQty(BothHelpObjs.makeZeroFromNull(rs.getString("CASE_QTY")));
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,
                       "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return;
  }

  //trong 9-29 added load two coz I don't want anything else uses catalog to break
  public void load2() throws Exception {
    String query = "select * from catalog_snapshot where item_id = " + item_id + " and facility_id = '" + facility_id + "'";
    query += " and fac_item_id = '" + this.fac_item_id + "'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      if (rs.next()) {
        setItemId( (int) rs.getInt("ITEM_ID"));
        setFacItemId(rs.getString("FAC_ITEM_ID"));
        setMaterialDesc(rs.getString("MATERIAL_DESC"));
        setGrade(rs.getString("GRADE"));
        setMfgDesc(rs.getString("MFG_DESC"));
        setPartSize( (float) rs.getFloat("PART_SIZE"));
        setSizeUnit(rs.getString("SIZE_UNIT"));
        setPkgStyle(rs.getString("PKG_STYLE"));
        setType(rs.getString("TYPE"));
        setPrice(BothHelpObjs.makeBlankFromNull(rs.getString("PRICE")));
        setShelfLife( (float) rs.getFloat("SHELF_LIFE"));
        setShelfLifeUnit(rs.getString("SHELF_LIFE_UNIT"));
        setUseage(rs.getString("USEAGE"));
        setUseageUnit(rs.getString("USEAGE_UNIT"));
        setApprovalStatus(rs.getString("APPROVAL_STATUS"));
        setPersonnelId( (int) rs.getInt("PERSONNEL_ID"));
        setUserGroupId(rs.getString("USER_GROUP_ID"));
        setApplication(rs.getString("APPLICATION"));
        setFacilityId(rs.getString("FACILITY_ID"));
        setMsdsOn(rs.getString("MSDS_ON_LINE"));
        setSpecOn(rs.getString("SPEC_ON_LINE"));
        setMatId( (int) rs.getInt("MATERIAL_ID"));
        setSpecId(rs.getString("SPEC_ID"));
        setMfgPartNum(rs.getString("MFG_PART_NO"));

        //trong 3-27-01
        setCaseQty(BothHelpObjs.makeZeroFromNull(rs.getString("CASE_QTY")));
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,
                       "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return;
  }

  //trong 2-14-01 pulling data from request_line_item rather than from catalog_snapshot
  //better to view history as well as current.
  public void loadNew(Integer pr_number) throws Exception {
    String query = "select * from pr_history_view where item_id = " + item_id + " and facility_id = '" + facility_id + "'";
    query += " and fac_part_no = '" + this.fac_item_id + "'";
    query += " and pr_number = " + pr_number.intValue();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      if (rs.next()) {
        setType(rs.getString("ITEM_TYPE"));
        setPrice(BothHelpObjs.makeBlankFromNull(rs.getString("UNIT_PRICE")));
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,
                       "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return;
  }

  public synchronized Vector retrieve(String where,
                                      String sortBy) throws Exception {
    //trong 3-27-01
    String query = "select distinct ITEM_ID, FAC_ITEM_ID, MATERIAL_DESC, GRADE, MFG_DESC, PART_SIZE, SIZE_UNIT, PKG_STYLE, TYPE, PRICE , SHELF_LIFE, SHELF_LIFE_UNIT, USEAGE, USEAGE_UNIT, APPROVAL_STATUS, APPLICATION, FACILITY_ID,  MSDS_ON_LINE, SPEC_ON_LINE , MATERIAL_ID, SPEC_ID, MFG_PART_NO, CASE_QTY from catalog_snapshot ";
    //                                 1        2              3           4       5          6          7         8         9     10       11              12           13    14              15                 16           17
    /*String query = "select distinct ITEM_ID, FAC_ITEM_ID, MATERIAL_DESC, GRADE, MFG_DESC, PART_SIZE, SIZE_UNIT, PKG_STYLE, TYPE, PRICE , SHELF_LIFE, SHELF_LIFE_UNIT, USEAGE, USEAGE_UNIT, APPROVAL_STATUS, APPLICATION, FACILITY_ID,  MSDS_ON_LINE, SPEC_ON_LINE , MATERIAL_ID, SPEC_ID, MFG_PART_NO from catalog_snapshot ";
             //                                 1        2              3           4       5          6          7         8         9     10       11              12           13    14              15                 16           17
     */
    if (where != (String)null) {
      query = query + " where " + where;

    }
    if (sortBy != (String)null) {
      query = query + " order by " + sortBy;

    }
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Catalog c;
    Vector allC = new Vector();

    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        c = new Catalog();
        c.setItemId( (int) rs.getInt("ITEM_ID"));
        c.setFacItemId(rs.getString("FAC_ITEM_ID"));
        c.setMaterialDesc(rs.getString("MATERIAL_DESC"));
        c.setGrade(rs.getString("GRADE"));
        c.setMfgDesc(rs.getString("MFG_DESC"));
        c.setPartSize( (float) rs.getFloat("PART_SIZE"));
        c.setSizeUnit(rs.getString("SIZE_UNIT"));
        c.setPkgStyle(rs.getString("PKG_STYLE"));
        c.setType(rs.getString("TYPE"));
        c.setPrice(BothHelpObjs.makeBlankFromNull(rs.getString("PRICE")));
        c.setShelfLife( (float) rs.getFloat("SHELF_LIFE"));
        c.setShelfLifeUnit(rs.getString("SHELF_LIFE_UNIT"));
        c.setUseage(rs.getString("USEAGE"));
        c.setUseageUnit(rs.getString("USEAGE_UNIT"));
        c.setApprovalStatus(rs.getString("APPROVAL_STATUS"));
        c.setApplication(rs.getString("APPLICATION"));
        c.setFacilityId(rs.getString("FACILITY_ID"));
        c.setMsdsOn(rs.getString("MSDS_ON_LINE"));
        c.setSpecOn(rs.getString("SPEC_ON_LINE"));
        c.setMatId( (int) rs.getInt("MATERIAL_ID"));
        c.setSpecId(rs.getString("SPEC_ID"));
        c.setMfgPartNum(rs.getString("MFG_PART_NO"));

        //trong 3-27-01
        c.setCaseQty(BothHelpObjs.makeZeroFromNull(rs.getString("CASE_QTY")));

        allC.addElement(c);
      }
      allC.trimToSize();

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,
                       "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return allC;
  }

  public synchronized Vector retrieveAllCatalog(int uid,
                                                String search_string,
                                                String fac,
                                                String app) throws Exception {
    //trong 3-27-01
    String query = " select a1.ITEM_ID, a1.FAC_ITEM_ID, a1.MATERIAL_DESC, a1.GRADE, a1.MFG_DESC, a1.PART_SIZE, a1.SIZE_UNIT, a1.PKG_STYLE, a1.TYPE, a1.PRICE, a1.SHELF_LIFE, a1.SHELF_LIFE_UNIT, a1.USEAGE, a1.USEAGE_UNIT, a1.APPROVAL_STATUS, a1.APPLICATION, a1.FACILITY_ID ,  a1.MSDS_ON_LINE, a1.SPEC_ON_LINE, a1.MATERIAL_ID, a1.SPEC_ID, a1.MFG_PART_NO, a1.CASE_QTY ";
    //String query = " select a1.ITEM_ID, a1.FAC_ITEM_ID, a1.MATERIAL_DESC, a1.GRADE, a1.MFG_DESC, a1.PART_SIZE, a1.SIZE_UNIT, a1.PKG_STYLE, a1.TYPE, a1.PRICE, a1.SHELF_LIFE, a1.SHELF_LIFE_UNIT, a1.USEAGE, a1.USEAGE_UNIT, a1.APPROVAL_STATUS, a1.APPLICATION, a1.FACILITY_ID ,  a1.MSDS_ON_LINE, a1.SPEC_ON_LINE, a1.MATERIAL_ID, a1.SPEC_ID, a1.MFG_PART_NO ";
    query = query + " from catalog_snapshot a1";
    /* used do be this
             if (search_string != null){
      query = query + ", item_synonym i1 ";
             }
     */
    // Now is this
    Vector syn = null;
    if (search_string != null) {
      syn = doLogic(search_string, "a1");
      query = query + " " + syn.elementAt(0).toString() + " ";
    }

    query = query + " where a1.personnel_id =  " + uid;
    /*  used do be this
             if (search_string != null){
        query = query + "  and ( lower(i1.search_string) like lower('%" + search_string + "%')) " ;
        query = query + "  and a1.item_id = i1.item_id  ";
             }
     */
    // Now is this
    if (search_string != null) {
      query = query + " and " + syn.elementAt(1).toString() + " ";
    }

    if (fac != null) {
      query = query + "  and (a1.facility_id = '" + fac + "') ";
    }
    query = query + " union all  (";
    query = query + " select  a2.ITEM_ID, a2.FAC_ITEM_ID, a2.MATERIAL_DESC, a2.GRADE, a2.MFG_DESC, a2.PART_SIZE, a2.SIZE_UNIT, a2.PKG_STYLE, a2.TYPE, a2.PRICE, a2.SHELF_LIFE, a2.SHELF_LIFE_UNIT, rtrim(a2.USEAGE) USEAGE, rtrim(a2.USEAGE_UNIT) USEAGE_UNIT, ";

    query = query + "max(decode(a2.APPLICATION, '";
    query = query + app;
    query = query + "', a2.APPROVAL_STATUS, 'All', a2.APPROVAL_STATUS,'')) APPROVAL_STATUS, '' APPLICATION,";

    //trong 3-27-01
    query = query + " a2.FACILITY_ID, a2.MSDS_ON_LINE, a2.SPEC_ON_LINE , a2.MATERIAL_ID, a2.SPEC_ID, a2.MFG_PART_NO, a2.CASE_QTY ";
    //query = query + " a2.FACILITY_ID, a2.MSDS_ON_LINE, a2.SPEC_ON_LINE , a2.MATERIAL_ID, a2.SPEC_ID, a2.MFG_PART_NO ";

    query = query + " from catalog_snapshot a2, catalog_snapshot b";
    /* used to be this
             if (search_string != null){
      query = query + ", item_synonym i2 ";
             }
     */
    // Now is this
    Vector syn2 = null;
    if (search_string != null) {
      syn2 = doLogic(search_string, "a2");
      query = query + " " + syn2.elementAt(0).toString() + " ";
    }

    query = query + " where a2.item_id = b.item_id(+) ";
    query = query + " and b.personnel_id(+) =  " + uid;
    query = query + " and b.item_id is null  ";
    query = query + " and a2.personnel_id is null  ";
    //query = query +" and (a2.application = 'All' or a2.application is null)";

    /* used to be this
             if (search_string != null){
        query = query + "  and ( lower(i2.search_string) like lower('%" + search_string + "%')) " ;
        query = query + "  and a2.item_id = i2.item_id  ";
             }
     */
    // now is this
    if (search_string != null) {
      query = query + " and " + syn2.elementAt(1).toString() + " ";
    }

    if (fac != null) {
      query = query + "  and (a2.facility_id = '" + fac + "') ";
    }

    query = query + " group by  a2.ITEM_ID, a2.FAC_ITEM_ID, a2.MATERIAL_DESC, a2.GRADE, a2.MFG_DESC, a2.PART_SIZE, a2.SIZE_UNIT, a2.PKG_STYLE, a2.TYPE, a2.PRICE, a2.SHELF_LIFE, a2.SHELF_LIFE_UNIT, rtrim(a2.USEAGE), rtrim(a2.USEAGE_UNIT), ";
    //trong 3-27-01
    query = query + " a2.FACILITY_ID, a2.MSDS_ON_LINE, a2.SPEC_ON_LINE , a2.MATERIAL_ID, a2.SPEC_ID, a2.MFG_PART_NO, a2.CASE_QTY ";
    //query = query + " a2.FACILITY_ID, a2.MSDS_ON_LINE, a2.SPEC_ON_LINE , a2.MATERIAL_ID, a2.SPEC_ID, a2.MFG_PART_NO ";

    query = query + " )";

    //System.out.println("** Catalog Query* ** : "+query);

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Catalog c;
    Vector allC = new Vector();
    synchronized (this) {
      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        while (rs.next()) {
          c = new Catalog();
          c.setItemId( (int) rs.getInt("ITEM_ID"));
          c.setFacItemId(rs.getString("FAC_ITEM_ID"));
          c.setMaterialDesc(rs.getString("MATERIAL_DESC"));
          c.setGrade(rs.getString("GRADE"));
          c.setMfgDesc(rs.getString("MFG_DESC"));
          c.setPartSize( (float) rs.getFloat("PART_SIZE"));
          c.setSizeUnit(rs.getString("SIZE_UNIT"));
          c.setPkgStyle(rs.getString("PKG_STYLE"));
          c.setType(rs.getString("TYPE"));
          c.setPrice(BothHelpObjs.makeBlankFromNull(rs.getString("PRICE")));
          c.setShelfLife( (float) rs.getFloat("SHELF_LIFE"));
          c.setShelfLifeUnit(rs.getString("SHELF_LIFE_UNIT"));
          c.setUseage(rs.getString("USEAGE"));
          c.setUseageUnit(rs.getString("USEAGE_UNIT"));
          c.setApprovalStatus(rs.getString("APPROVAL_STATUS"));
          c.setApplication(rs.getString("APPLICATION"));
          c.setFacilityId(rs.getString("FACILITY_ID"));
          c.setMsdsOn(rs.getString("MSDS_ON_LINE"));
          c.setSpecOn(rs.getString("SPEC_ON_LINE"));
          c.setMatId( (int) rs.getInt("MATERIAL_ID"));
          c.setSpecId(rs.getString("SPEC_ID"));
          c.setMfgPartNum(rs.getString("MFG_PART_NO"));

          //trong 3-27-01
          c.setCaseQty(BothHelpObjs.makeZeroFromNull(rs.getString("CASE_QTY")));

          allC.addElement(c);
        }
        allC.trimToSize();

      } catch (Exception e) {
        e.printStackTrace();
        radian.web.emailHelpObj.senddatabaseHelpemails("Error in Query for retrieveAllCatalog", "Error occured while running Query for retrieveAllCatalog\nError Msg:\n" + e.getMessage() + "\n\n DB Clinet : " + db.getClient() + " \n\nQuery:  " + query + " ");
        throw e;
      } finally {
        dbrs.close();
      }
    }
    return allC;
  }

  public Vector retrieveAllCatalogPure(String search_string,
                                       String fac,
                                       String sortby) throws Exception {
    //trong 3-37-01
    String query = " select a2.ITEM_ID, a2.FAC_ITEM_ID, a2.MATERIAL_DESC, a2.GRADE, a2.MFG_DESC, a2.PART_SIZE, a2.SIZE_UNIT, a2.PKG_STYLE, a2.TYPE, a2.PRICE, a2.SHELF_LIFE, a2.SHELF_LIFE_UNIT, a2.USEAGE, a2.USEAGE_UNIT, a2.APPROVAL_STATUS, a2.APPLICATION, a2.FACILITY_ID ,  a2.MSDS_ON_LINE, a2.SPEC_ON_LINE, a2.MATERIAL_ID, a2.SPEC_ID, a2.MFG_PART_NO, a2.CASE_QTY ";
    //String query = " select a2.ITEM_ID, a2.FAC_ITEM_ID, a2.MATERIAL_DESC, a2.GRADE, a2.MFG_DESC, a2.PART_SIZE, a2.SIZE_UNIT, a2.PKG_STYLE, a2.TYPE, a2.PRICE, a2.SHELF_LIFE, a2.SHELF_LIFE_UNIT, a2.USEAGE, a2.USEAGE_UNIT, a2.APPROVAL_STATUS, a2.APPLICATION, a2.FACILITY_ID ,  a2.MSDS_ON_LINE, a2.SPEC_ON_LINE, a2.MATERIAL_ID, a2.SPEC_ID, a2.MFG_PART_NO ";
    query = query + " from catalog_snapshot a2 ";

    Vector syn = null;
    if (search_string != null) {
      syn = doLogic(search_string, "a2");
      query = query + " " + syn.elementAt(0).toString() + " ";
    }

    query = query + " where a2.personnel_id is null  ";
    query = query + " and (a2.application = 'All' or a2.application is null)";
    if (search_string != null) {
      query = query + " and " + syn.elementAt(1).toString() + " ";
    }
    if (fac != null && fac.length() > 0) {
      query = query + "  and (a2.facility_id = '" + fac + "') ";
    }

    //sortby
    if (sortby != null) {
      query = query + "  order by a2." + sortby;
    }

    // // System.out.println("** Catalog Query* ** : "+query);

    DBResultSet dbrs = null;
    ResultSet rs = null;

    Vector allC = new Vector();
    Catalog c = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();

      while (rs.next()) {
        c = new Catalog();
        c.setItemId( (int) rs.getInt("ITEM_ID"));
        c.setFacItemId(rs.getString("FAC_ITEM_ID"));
        c.setMaterialDesc(rs.getString("MATERIAL_DESC"));
        c.setGrade(rs.getString("GRADE"));
        c.setMfgDesc(rs.getString("MFG_DESC"));
        c.setPartSize( (float) rs.getFloat("PART_SIZE"));
        c.setSizeUnit(rs.getString("SIZE_UNIT"));
        c.setPkgStyle(rs.getString("PKG_STYLE"));
        c.setType(rs.getString("TYPE"));
        c.setPrice(BothHelpObjs.makeBlankFromNull(rs.getString("PRICE")));
        c.setShelfLife( (float) rs.getFloat("SHELF_LIFE"));
        c.setShelfLifeUnit(rs.getString("SHELF_LIFE_UNIT"));
        c.setUseage(rs.getString("USEAGE"));
        c.setUseageUnit(rs.getString("USEAGE_UNIT"));
        c.setApprovalStatus(rs.getString("APPROVAL_STATUS"));
        c.setApplication(rs.getString("APPLICATION"));
        c.setFacilityId(rs.getString("FACILITY_ID"));
        c.setMsdsOn(rs.getString("MSDS_ON_LINE"));
        c.setSpecOn(rs.getString("SPEC_ON_LINE"));
        c.setMatId( (int) rs.getInt("MATERIAL_ID"));
        c.setSpecId(rs.getString("SPEC_ID"));
        c.setMfgPartNum(rs.getString("MFG_PART_NO"));

        //trong 3-27-01
        c.setCaseQty(BothHelpObjs.makeZeroFromNull(rs.getString("CASE_QTY")));

        allC.addElement(c);
      }
      allC.trimToSize();

    } catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.senddatabaseHelpemails("Error in Query for retrieveAllCatalogPure", "Error occured while running Query for retrieveAllCatalogPure\nError Msg:\n" + e.getMessage() + "\n\n DB Clinet : " + db.getClient() + " \n\nQuery:  " + query + " ");
      throw e;
    } finally {
      dbrs.close();
    }
    return allC;
  }

	public String doSearchLogic(String searchColumns, String search) {
		Hashtable r = getLogicHash(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " lower("+searchColumns+") like lower('%" + search + "%')";
			return result;
		}

		//contains operation in search text
		result += " lower("+searchColumns+") like lower('%" + likes.elementAt(0).toString().trim() + "%') ";
		for (int i = 0; i < opers.size(); i++) {
			String op = opers.elementAt(i).toString();
			String lk = "like";
			if (op.equalsIgnoreCase("but not")) {
				op = "and";
				lk = "not like";
			}
			String searchS = likes.elementAt(i + 1).toString().trim();
			result += " " + op + " lower("+searchColumns+") " + lk + " lower('%" + searchS + "%') ";
		}
		return result;
	}

  public Vector retrieveAllCatalogPureLimited(String search_string, String fac, String sortby, String client) throws Exception {
    search_string = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(search_string);
    String query = "select company_id,item_id,msds_no,material_id,replace(string_agg (DISTINCT cat_part_no),',',', ') cat_part_no,"+
                   "catalog_id,mfg_desc,msds_on_line,decode(fx_locale_trade_name(company_id,facility_id,material_id),null,trade_name,trade_name||chr(10)||'('||"+
									 "fx_locale_trade_name(company_id,facility_id,material_id)||')') trade_name,"+
									 "facility_id,fac_msds_id from (";
    query = query + "select  /*+ RULE */ a.company_id, '0' ITEM_ID,fx_company_catalog_msds_id(b.MATERIAL_ID,'" + (fac == null ? "All" : fac) + "') MSDS_NO,b.material_id,"+
                    "a.cat_part_no || DECODE (string_agg (a.status), 'O', ' (Obsolete)', '') cat_part_no,a.catalog_id, b.mfg_desc, b.MSDS_ON_LINE, b.trade_name,";
    if (fac != null) {
      query = query + "'" + fac + "' FACILITY_ID ";
    } else {
      query = query + "' ' FACILITY_ID ";
    }
    query = query + " ,'' fac_msds_id from cat_part_all_view a, catalog_item_view b, cat_part_synonym syn";
    query = query + " where  a.item_id = b.item_id";
    query = query + " and nvl(a.bundle,'EA') = nvl(b.bundle,'EA')";
	 if (!BothHelpObjs.isBlankString(search_string)) {
		query += " and (";
		query += "("+doSearchLogic("search_string1",search_string)+")";
		query += " or ("+doSearchLogic("search_string2",search_string)+")";
		query += " or ("+doSearchLogic("search_string3",search_string)+")";
		query += " or ("+doSearchLogic("search_string4",search_string)+")";
		query += " or ("+doSearchLogic("search_string5",search_string)+")";
		query += ")";
	 }

	 query = query + " and a.cat_part_no = syn.cat_part_no and a.catalog_id=syn.catalog_id ";
    query = query + "and a.status in (select status from catalog_part_status where show_msds='y') ";

    if (fac != null) {
      query = query + "and a.catalog_id in (select catalog_id from catalog_facility where facility_id='" + fac + "') ";
    }
    //inner and outter group by
    query += "group by fx_company_catalog_msds_id (b.material_id, '" + (fac == null ? "All" : fac) + "'),b.material_id,a.cat_part_no,"+
             "a.catalog_id,b.mfg_desc,b.msds_on_line,b.trade_name,a.company_id)"+
             " group by item_id,msds_no,material_id,catalog_id,mfg_desc,msds_on_line,trade_name,facility_id,fac_msds_id,company_id ";

    if (union) {
      if (this.msdsdatabase) { //Full Database
        query = query + "union all select distinct user company_id, '0' ITEM_ID,a2.fac_msds_id MSDS_NO,a2.MATERIAL_ID, a2.FAC_ITEM_ID cat_part_no,' ' catalog_id, a2.MFG_DESC, a2.MSDS_ON_LINE, a2.TRADE_NAME,";
        if (fac != null) {
          query = query + "'" + fac + "' FACILITY_ID";
        } else {
          query = query + "' ' FACILITY_ID";
        }
        query += ",a2.fac_msds_id from non_catalog_snapshot a2";

        Vector syn2 = null;
        if (search_string != null) {
          syn2 = doLogicM(search_string, "a2");
          query = query + " " + syn2.elementAt(0).toString() + " ";
        }

        if (search_string != null) {
          query = query + " where " + syn2.elementAt(1).toString() + " ";
        }
      }
      //Non Catalog
      query = query + "union all";
      query += " select company_id,'0' item_id,fac_msds_id msds_no,material_id,cat_part_no,";
      query += "' ' catalog_id,mfg_desc,msds_on_line,trade_name,' ' facility_id,fac_msds_id";
      query += " from (select company_id,fac_msds_id msds_no,material_id,fac_item_id cat_part_no,";
      query += "mfg_desc,msds_on_line,trade_name,fac_msds_id,";
      query += "row_number() over (partition by material_id order by fac_msds_id nulls last) material_rank,";
      query += "row_number() over (partition by material_id, fac_msds_id order by facility_id) msds_rank";

      query = query + " from noncatalog_material_view a2";
	  String tmpWhere = (fac == null ? "" : " where facility_id='" + fac + "' ");
	  if (!BothHelpObjs.isBlankString(search_string)) {
        if (tmpWhere.length() == 0) {
            tmpWhere += " where "+doSearchLogic("search",search_string);
        }else {
            tmpWhere += " and ("+doSearchLogic("search",search_string)+")";
        }
      }
	  query += tmpWhere+") where material_rank = 1 or (msds_rank = 1 and msds_no is not null) ";
	 }

    if (sortby != null) {
      if (sortby.equalsIgnoreCase("TRADE_NAME")) {
        query = query + "  order by TRADE_NAME";
      } else if ( (union) && (sortby.equalsIgnoreCase("CATALOG_ID"))) {
        query = query + "  order by FACILITY_ID";
      } else if (sortby.equalsIgnoreCase("MFG_DESC")) {
        query = query + "  order by MFG_DESC";
      } else if (sortby.equalsIgnoreCase("FACILITY_ID")) {
        query = query + "  order by FACILITY_ID desc";
      } else if (sortby.equalsIgnoreCase("MATERIAL_ID")) {
        query = query + "  order by MATERIAL_ID";
      } else if (sortby.equalsIgnoreCase("FAC_ITEM_ID")) {
        query = query + "  order by cat_part_no desc";
      } else if (sortby.equalsIgnoreCase("MSDS_ID")) {
        query = query + "  order by FAC_MSDS_ID";
      }
    }

	 DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    Vector allC = new Vector();

    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      int i = 0;
      while (rs.next()) {
        i++;
        Integer id = new Integer( (int) rs.getInt("ITEM_ID")) == null ? new Integer(0) : new Integer( (int) rs.getInt("ITEM_ID"));
        result = new Hashtable();

        result.put("ITEM_ID", id);
        result.put("MATERIAL_ID", rs.getString("MATERIAL_ID") == null ? "" : rs.getString("MATERIAL_ID"));
        result.put("FAC_MSDS_ID", rs.getString("FAC_MSDS_ID") == null ? "" : rs.getString("FAC_MSDS_ID"));
        result.put("CATALOG_ID", rs.getString("CATALOG_ID") == null ? "" : rs.getString("CATALOG_ID"));
        if (client.equalsIgnoreCase("Southwest Airlines") || client.equalsIgnoreCase("BAE") || client.equalsIgnoreCase("NO FACILITY")) {
          result.put("FACILITY_ID", " ");
        } else {
          result.put("FACILITY_ID", rs.getString("FACILITY_ID") == null ? "" : rs.getString("FACILITY_ID"));
        }

        result.put("FAC_ITEM_ID", rs.getString("cat_part_no") == null ? "" : rs.getString("cat_part_no"));
        result.put("MATERIAL_DESC", " ");
        result.put("TRADE_NAME", rs.getString("TRADE_NAME") == null ? "" : rs.getString("TRADE_NAME"));
        result.put("MFG_DESC", rs.getString("MFG_DESC") == null ? "" : rs.getString("MFG_DESC"));
        result.put("MSDS_ON_LINE", rs.getString("MSDS_ON_LINE") == null ? "" : rs.getString("MSDS_ON_LINE"));
        result.put("SPEC_ON_LINE", " ");
        result.put("MFG_PART_NO", " ");
        result.put("MSDS_NO", rs.getString("MSDS_NO") == null ? "" : rs.getString("MSDS_NO"));

        allC.addElement(result);
      }
      // put qty on last 2 occur of the vector
      allC.addElement(new Integer(i));
      allC.trimToSize();
    } catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.senddatabaseHelpemails("Error in Query for retrieveAllCatalogPureLimited", "Error occured while running Query for retrieveAllCatalogPureLimited\nError Msg:\n" + e.getMessage() + "\n\n DB Clinet : " + db.getClient() + " \n\nQuery:  " + query + " ");
      throw e;
    } finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }

    return allC;
  }

  ///////////////////////////////////////////////////////////////MOdified
  public Vector retrieveAllCatalogPureLimited2(String search_string,
                                               String fac,
                                               String sortby,
                                               String startfrom,
                                               Integer from,
                                               Integer interval, String client) throws Exception {

    search_string = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(search_string);
    String query = "";
    if (client.equalsIgnoreCase("Southwest Airlines") || client.equalsIgnoreCase("BAE")) {
      query = " select  distinct a2.ITEM_ID, a2.FAC_ITEM_ID, a2.MATERIAL_DESC, a2.TRADE_NAME, a2.MFG_DESC,a2.MSDS_ON_LINE, a2.SPEC_ON_LINE, a2.MATERIAL_ID, a2.FAC_MSDS_ID, a2.MFG_PART_NO ";
    } else {
      query = " select  distinct a2.ITEM_ID, a2.FAC_ITEM_ID, a2.MATERIAL_DESC, a2.TRADE_NAME, a2.MFG_DESC,a2.FACILITY_ID,a2.MSDS_ON_LINE, a2.SPEC_ON_LINE, a2.MATERIAL_ID, a2.FAC_MSDS_ID, a2.MFG_PART_NO ";
    }
    query = query + " from catalog_snapshot a2 ";

    Vector syn = null;
    if (search_string != null) {
      syn = doLogic(search_string, "a2");
      query = query + " " + syn.elementAt(0).toString() + " ";
    }

    query = query + " where a2.personnel_id is null  ";
    //query = query +" and (a2.application = 'All' or a2.application is null)";
    if (search_string != null) {
      query = query + " and " + syn.elementAt(1).toString() + " ";
    }
    if (fac != null) {
      query = query + "  and (a2.facility_id = '" + fac + "') ";
    }
    ///////////////////////////////////////////Modified
    //UnionAll checkbox
    if (union) {
      // query = query + "union all select distinct a2.ITEM_ID, a2.FAC_ITEM_ID, a2.MATERIAL_DESC, a2.TRADE_NAME, a2.MFG_DESC, a2.FACILITY_ID,  a2.MSDS_ON_LINE, a2.SPEC_ON_LINE, a2.MATERIAL_ID, a2.FAC_MSDS_ID, a2.MFG_PART_NO ";
      if (client.equalsIgnoreCase("Southwest Airlines") || client.equalsIgnoreCase("BAE")) {
        query = query + "union all select  distinct a2.ITEM_ID, a2.FAC_ITEM_ID, a2.MATERIAL_DESC, a2.TRADE_NAME, a2.MFG_DESC,a2.MSDS_ON_LINE, a2.SPEC_ON_LINE, a2.MATERIAL_ID, a2.FAC_MSDS_ID, a2.MFG_PART_NO ";
      } else {
        query = query + "union all select  distinct a2.ITEM_ID, a2.FAC_ITEM_ID, a2.MATERIAL_DESC, a2.TRADE_NAME, a2.MFG_DESC,a2.FACILITY_ID,a2.MSDS_ON_LINE, a2.SPEC_ON_LINE, a2.MATERIAL_ID, a2.FAC_MSDS_ID, a2.MFG_PART_NO ";
      }
      query = query + " from non_catalog_snapshot a2 ";

      Vector syn2 = null;
      if (search_string != null) {
        syn2 = doLogicM(search_string, "a2");
        query = query + " " + syn2.elementAt(0).toString() + " ";
      }

      if (search_string != null) {
        query = query + " where " + syn2.elementAt(1).toString() + " ";
      }
    }
    ///////////////////////////////////////////Modified
    //sortby
    if (sortby != null) {
      if (startfrom != null) {
        query = query + " and " + sortby + " > " + (sortby.equalsIgnoreCase("ITEM_ID") ? "" : "'") + startfrom + (sortby.equalsIgnoreCase("ITEM_ID") ? "" : "'");
      }
      //before query = query + "  order by a2." + sortby ;
      //trong 9-13
      if (sortby.equalsIgnoreCase("TRADE_NAME")) {
        query = query + "  order by TRADE_NAME";
      } else if (sortby.equalsIgnoreCase("MFG_DESC")) {
        query = query + "  order by MFG_DESC";
      } else if (sortby.equalsIgnoreCase("FACILITY_ID")) {
        query = query + "  order by FACILITY_ID";
      } else if (sortby.equalsIgnoreCase("MATERIAL_ID")) {
        query = query + "  order by MATERIAL_ID";
      } else if (sortby.equalsIgnoreCase("FAC_ITEM_ID")) {
        query = query + "  order by FAC_ITEM_ID";
      } else if (sortby.equalsIgnoreCase("MSDS_ID")) {
        query = query + "  order by FAC_MSDS_ID";
      }

    }

    //System.out.println("\n\n** Catalog Query* ** : "+query+"\n\n");
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    Vector allC = new Vector();
    synchronized (this) {
      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        int i = 0;
        while (rs.next()) {
          i++;
          if (i < from.intValue()) {
            continue;
          }
          if (i > from.intValue() + interval.intValue() - 1) {
            // find count
            continue;
          }
          Integer id = new Integer( (int) rs.getInt(1)) == null ? new Integer(0) : new Integer( (int) rs.getInt(1));
              // if you want to block dup items // if (result.contains(id)) continue;
          result = new Hashtable();
          result.put("ITEM_ID", id);
          result.put("MATERIAL_ID",
                     rs.getString("MATERIAL_ID") == null ? "" : rs.getString("MATERIAL_ID"));
          result.put("FAC_MSDS_ID",
                     rs.getString("FAC_MSDS_ID") == null ? "" : rs.getString("FAC_MSDS_ID"));

          if (client.equalsIgnoreCase("Southwest Airlines") || client.equalsIgnoreCase("BAE")) {
            result.put("FACILITY_ID", " ");
          } else {
            result.put("FACILITY_ID",
                       rs.getString("FACILITY_ID") == null ? "" : rs.getString("FACILITY_ID"));
          }

          result.put("FAC_ITEM_ID",
                     rs.getString("FAC_ITEM_ID") == null ? "" : rs.getString("FAC_ITEM_ID"));
          result.put("MATERIAL_DESC",
                     rs.getString("MATERIAL_DESC") == null ? "" : rs.getString("MATERIAL_DESC"));
          result.put("TRADE_NAME",
                     rs.getString("TRADE_NAME") == null ? "" : rs.getString("TRADE_NAME"));
          result.put("MFG_DESC",
                     rs.getString("MFG_DESC") == null ? "" : rs.getString("MFG_DESC"));

          result.put("MSDS_ON_LINE",
                     rs.getString("MSDS_ON_LINE") == null ? "" : rs.getString("MSDS_ON_LINE"));
          result.put("SPEC_ON_LINE",
                     rs.getString("SPEC_ON_LINE") == null ? "" : rs.getString("SPEC_ON_LINE"));
          result.put("MFG_PART_NO",
                     rs.getString("MFG_PART_NO") == null ? "" : rs.getString("MFG_PART_NO"));

          allC.addElement(result);
        }
        // put qty on last 2 occur of the vector
        allC.addElement(new Integer(i));

        allC.trimToSize();

      } catch (Exception e) {
        e.printStackTrace();
        radian.web.emailHelpObj.senddatabaseHelpemails("Error in Query for retrieveAllCatalogPureLimited2", "Error occured while running Query for retrieveAllCatalogPureLimited2\nError Msg:\n" + e.getMessage() + "\n\n DB Clinet : " + db.getClient() + " \n\nQuery:  " + query + " ");
        throw e;
      } finally {
        dbrs.close();
      }
    }
    return allC;
  }

  public synchronized Vector retrieveApprovedOnly(int uid, String fac, String app, String search_string) throws Exception {
    String query = " select distinct a1.ITEM_ID, a1.FAC_ITEM_ID, a1.MATERIAL_DESC, a1.GRADE, a1.MFG_DESC, a1.PART_SIZE, a1.SIZE_UNIT, a1.PKG_STYLE, a1.TYPE, a1.PRICE, a1.SHELF_LIFE, a1.SHELF_LIFE_UNIT, a1.USEAGE, a1.USEAGE_UNIT, a1.APPROVAL_STATUS, a1.APPLICATION, a1.FACILITY_ID,  a1.MSDS_ON_LINE, a1.SPEC_ON_LINE , a1.MATERIAL_ID, a1.SPEC_ID, a1.MFG_PART_NO, a1.CASE_QTY ";
    query = query + " from catalog_snapshot a1";

    Vector syn = null;
    if (search_string != null) {
      syn = doLogic(search_string, "a1");
      query = query + " " + syn.elementAt(0).toString() + " ";
    }

    query = query + " where (a1.personnel_id =  " + uid + " or a1.user_group_id='All') ";
    query = query + "  and (a1.facility_id = '" + fac + "') ";
    query = query + "  and (a1.application in ('" + app + "','All')) ";

    if (search_string != null) {
      query = query + " and " + syn.elementAt(1).toString() + " ";
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Catalog c;
    Vector allC = new Vector();

    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        c = new Catalog();
        c.setItemId( (int) rs.getInt("ITEM_ID"));
        c.setFacItemId(rs.getString("FAC_ITEM_ID"));
        c.setMaterialDesc(rs.getString("MATERIAL_DESC"));
        c.setGrade(rs.getString("GRADE"));
        c.setMfgDesc(rs.getString("MFG_DESC"));
        c.setPartSize( (float) rs.getFloat("PART_SIZE"));
        c.setSizeUnit(rs.getString("SIZE_UNIT"));
        c.setPkgStyle(rs.getString("PKG_STYLE"));
        c.setType(rs.getString("TYPE"));
        c.setPrice(BothHelpObjs.makeBlankFromNull(rs.getString("PRICE")));
        c.setShelfLife( (float) rs.getFloat("SHELF_LIFE"));
        c.setShelfLifeUnit(rs.getString("SHELF_LIFE_UNIT"));
        c.setUseage(rs.getString("USEAGE"));
        c.setUseageUnit(rs.getString("USEAGE_UNIT"));
        c.setApprovalStatus(rs.getString("APPROVAL_STATUS"));
        c.setApplication(rs.getString("APPLICATION"));
        c.setFacilityId(rs.getString("FACILITY_ID"));
        c.setMsdsOn(rs.getString("MSDS_ON_LINE"));
        c.setSpecOn(rs.getString("SPEC_ON_LINE"));
        c.setMatId( (int) rs.getInt("MATERIAL_ID"));
        c.setSpecId(rs.getString("SPEC_ID"));
        c.setMfgPartNum(rs.getString("MFG_PART_NO"));

        //trong 3-27-01
        c.setCaseQty(BothHelpObjs.makeZeroFromNull(rs.getString("CASE_QTY")));

        allC.addElement(c);
      }
      allC.trimToSize();

    } catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.senddatabaseHelpemails("Error in Query for retrieveApprovedOnly", "Error occured while running Query for retrieveApprovedOnly\nError Msg:\n" + e.getMessage() + "\n\n DB Clinet : " + db.getClient() + " \n\nQuery:  " + query + " ");
      throw e;
    } finally {
      dbrs.close();
    }
    return allC;
  }

  public Vector doLogicAllCatalog(String search, String snapName) {

    Vector result = new Vector(); // first instance before where, second after where...

    Hashtable r = getLogicHash(search);
    Vector opers = (Vector) r.get("OPERATORS");
    Vector likes = (Vector) r.get("LIKES");
    String bWhere = "";
    String aWhere = "";

    if (opers.size() < 1) {
      bWhere = ", cat_part_syn_all syn0 ";
      aWhere = " (lower(syn0.search_string) like lower('%" + search + "%')) ";
      aWhere = aWhere + "  and " + snapName + ".cat_part_no = syn0.cat_part_no ";
      aWhere = aWhere + "  and " + snapName + ".catalog_id = syn0.catalog_id ";
      result.addElement(bWhere);
      result.addElement(aWhere);
      return result;
    }

    bWhere = bWhere + ", cat_part_synonym syn0 ";
    aWhere = snapName + ".cat_part_no = syn0.cat_part_no and ";
    aWhere += snapName + ".catalog_id = syn0.catalog_id  ";

    aWhere = aWhere + " and ( (lower(syn0.search_string) like lower('%" + likes.elementAt(0).toString().trim() + "%')";
    for (int i = 0; i < opers.size(); i++) {
      String op = opers.elementAt(i).toString();
      String lk = "like";
      if (op.equalsIgnoreCase("but not")) {
        op = "and";
        lk = "not like";
      }

      aWhere = aWhere + " " + op + " lower(syn0.search_string) " + lk + " lower('%" + likes.elementAt(i + 1).toString().trim() + "%')" + (i == 0 ? ")" : "");

    }
    aWhere = aWhere + " ) ";

    result.addElement(bWhere);
    result.addElement(aWhere);
    return result;
  }

  public Vector doLogic(String search, String snapName) {

    Vector result = new Vector(); // first instance before where, second after where...

    Hashtable r = getLogicHash(search);
    Vector opers = (Vector) r.get("OPERATORS");
    Vector likes = (Vector) r.get("LIKES");
    String bWhere = "";
    String aWhere = "";
    //no operator
    if (opers.size() < 1) {
      bWhere = ", cat_part_synonym syn0 ";
      aWhere = " ( lower(syn0.search_string1) like lower('%" + search + "%') or";
      aWhere += " lower(syn0.search_string2) like lower('%" + search + "%') or";
      aWhere += " lower(syn0.search_string3) like lower('%" + search + "%') or";
      aWhere += " lower(syn0.search_string4) like lower('%" + search + "%') or";
      aWhere += " lower(syn0.search_string5) like lower('%" + search + "%')) ";
      aWhere = aWhere + "  and " + snapName + ".cat_part_no = syn0.cat_part_no ";
      aWhere = aWhere + "  and " + snapName + ".catalog_id = syn0.catalog_id ";
      result.addElement(bWhere);
      result.addElement(aWhere);
      return result;
    }

    //contains operation in search text
    bWhere = bWhere + ", cat_part_synonym syn0 ";
    aWhere = snapName + ".cat_part_no = syn0.cat_part_no and ";
    aWhere += snapName + ".catalog_id = syn0.catalog_id  ";

    //aWhere = aWhere + " and ( (lower(syn0.search_string) like lower('%"+likes.elementAt(0).toString().trim()+"%')";
    aWhere += " and ( (lower(syn0.search_string1) like lower('%" + likes.elementAt(0).toString().trim() + "%') or";
    aWhere += " lower(syn0.search_string2) like lower('%" + likes.elementAt(0).toString().trim() + "%') or";
    aWhere += " lower(syn0.search_string3) like lower('%" + likes.elementAt(0).toString().trim() + "%') or";
    aWhere += " lower(syn0.search_string4) like lower('%" + likes.elementAt(0).toString().trim() + "%') or";
    aWhere += " lower(syn0.search_string5) like lower('%" + likes.elementAt(0).toString().trim() + "%')) ";
    for (int i = 0; i < opers.size(); i++) {
      String op = opers.elementAt(i).toString();
      String lk = "like";
      if (op.equalsIgnoreCase("but not")) {
        op = "and";
        lk = "not like";
      }
      String searchS = likes.elementAt(i + 1).toString().trim();
      //aWhere += " "+ op + " (lower(syn0.search_string1) "+lk+" lower('%"+searchS+"%')"+(i==0?")":"");
      aWhere += " " + op + " (lower(syn0.search_string1) " + lk + " lower('%" + searchS + "%') or";
      aWhere += " lower(syn0.search_string2) " + lk + " lower('%" + searchS + "%') or";
      aWhere += " lower(syn0.search_string3) " + lk + " lower('%" + searchS + "%') or";
      aWhere += " lower(syn0.search_string4) " + lk + " lower('%" + searchS + "%') or";
      aWhere += " lower(syn0.search_string5) " + lk + " lower('%" + searchS + "%')) ";
    }
    aWhere += " ) ";

    result.addElement(bWhere);
    result.addElement(aWhere);
    return result;
  }

  public String doItemCatSearchLogic(String search) {
    Hashtable r = getLogicHash(search);
    Vector opers = (Vector) r.get("OPERATORS");
    Vector likes = (Vector) r.get("LIKES");
    String result = "";
    //no operator
    if (opers.size() < 1) {
      result = " a.item_id||lower(substr(a.item_desc,1,3990)) like lower('%" + search + "%')";
      return result;
    }

    //contains operation in search text
    result += " ( a.item_id||lower(substr(a.item_desc,1,3990) ) like lower('%" + likes.elementAt(0).toString().trim() + "%') ";
    for (int i = 0; i < opers.size(); i++) {
      String op = opers.elementAt(i).toString();
      String lk = "like";
      if (op.equalsIgnoreCase("but not")) {
        op = "and";
        lk = "not like";
      }
      String searchS = likes.elementAt(i + 1).toString().trim();
      result += " " + op + " a.item_id||lower(substr(a.item_desc,1,3990) ) " + lk + " lower('%" + searchS + "%') ";
    }
    result += " ) ";

    return result;
  }

  ////////////////////////////////////////////////Modified

  protected Vector doLogicM(String search, String snapName) {

    Vector result = new Vector(); // first instance before where, second after where...

    Hashtable r = getLogicHash(search);
    Vector opers = (Vector) r.get("OPERATORS");
    Vector likes = (Vector) r.get("LIKES");
    String bWhere = "";
    String aWhere = "";

    if (opers.size() < 1) {
      bWhere = ", material_synonym syn0 ";
      aWhere = " ( lower(syn0.search_string) like lower('%" + search + "%')) ";
      aWhere = aWhere + "  and " + snapName + ".material_id = syn0.material_id  ";
      result.addElement(bWhere);
      result.addElement(aWhere);
      return result;
    }

    /*
             for (int i=0;i<likes.size();i++){
       bWhere = bWhere + ", material_synonym syn"+i+" ";
             }
     */
    bWhere = bWhere + ", material_synonym syn0 ";
    aWhere = snapName + ".material_id = syn0.material_id  ";
    /*
             for (int i=0;i<opers.size();i++){
       int j = i+1;
       String op = opers.elementAt(i).toString();
       if (op.equalsIgnoreCase("but not")) op = "and";
       aWhere = " ( " +aWhere+" "+op+" "+snapName+".material_id = syn0.material_id )";
             }
     */
    aWhere = aWhere + " and ( (lower(syn0.search_string) like lower('%" + likes.elementAt(0).toString().trim() + "%')";
    for (int i = 0; i < opers.size(); i++) {
      String op = opers.elementAt(i).toString();
      String lk = "like";
      if (op.equalsIgnoreCase("but not")) {
        op = "and";
        lk = "not like";
      }

      aWhere = aWhere + " " + op + " lower(syn0.search_string) " + lk + " lower('%" + likes.elementAt(i + 1).toString().trim() + "%')" + (i == 0 ? ")" : "");

    }
    aWhere = aWhere + " ) ";

    result.addElement(bWhere);
    result.addElement(aWhere);
    return result;
  }

  ///////////////////////////////////////////////////////////////MOdified

  protected Hashtable getLogicHash(String search) {
    Hashtable result = new Hashtable();
    String tmp = search.toLowerCase();
    Vector strV = new Vector();
    Vector operV = new Vector();

    while (true) {
      int a = (tmp.indexOf(" and ") < 0 ? 5000 : (tmp.indexOf(" and ") + 1));
      int o = (tmp.indexOf(" or ") < 0 ? 5000 : (tmp.indexOf(" or ") + 1));
      int n = (tmp.indexOf(" but not ") < 0 ? 5000 : (tmp.indexOf(" but not ") + 1));
      String sTmp = "";
      if (a < o && a < n) {
        sTmp = tmp.substring(0, a);
        sTmp.trim();
        strV.addElement(sTmp);
        operV.addElement("and");
        if (tmp.length() > (a + 3)) {
          tmp = tmp.substring(a + 3);
          tmp.trim();
          continue;
        }
      } else if (o < a && o < n) {
        sTmp = tmp.substring(0, o);
        sTmp.trim();
        strV.addElement(sTmp);
        operV.addElement("or");
        if (tmp.length() > (o + 3)) {
          tmp = tmp.substring(o + 3);
          tmp.trim();
          continue;
        }
      } else if (n < a && n < o) {
        sTmp = tmp.substring(0, n);
        sTmp.trim();
        strV.addElement(sTmp);
        operV.addElement("but not");
        if (tmp.length() > (n + 7)) {
          tmp = tmp.substring(n + 7);
          tmp.trim();
          continue;
        }
      } else {
        strV.addElement(tmp);
        break;
      }
    }

    result.put("OPERATORS", operV);
    result.put("LIKES", strV);

    return result;
  }

  public Vector getPartInfoForItem(int itemId) throws Exception {
    Vector v = new Vector();
    String query = "select * from item_detail_view where item_id = " + itemId;

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Hashtable h = new Hashtable();
        h.put("PART_ID", rs.getString("part_id"));
        h.put("GRADE",
              rs.getString("grade") == null ? "" : rs.getString("grade"));
        h.put("PKG", rs.getString("pkg_size"));
        h.put("DESC", rs.getString("material_desc"));
        v.addElement(h);
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,
                       "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return v;
  }

  public static String getItemType(TcmISDBModel db1,
                                   String facPartNum,
                                   String fac) throws Exception {
    String s = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String q = "select type from catalog_snapshot where fac_item_id = '" + facPartNum + "' and facility_id = '" + fac + "'";
      dbrs = db1.doQuery(q);
      rs = dbrs.getResultSet();
      if (rs.next()) {
        s = rs.getString("type");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return s;
  }

  public Vector retrieveAllNonCatalogPureLimited(String search_string, String fac, String sortby, String client) throws Exception {
    search_string = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(search_string);
    String query = "";
    query = " select  distinct a2.fac_msds_id MSDS_NO,a2.ITEM_ID, a2.FAC_ITEM_ID, a2.MATERIAL_DESC, a2.TRADE_NAME, a2.MFG_DESC,a2.MSDS_ON_LINE, a2.SPEC_ON_LINE, a2.MATERIAL_ID, a2.FAC_MSDS_ID, a2.MFG_PART_NO,";
    if (fac != null) {
      query = query + "'" + fac + "' FACILITY_ID ";
    } else {
      query = query + "' ' FACILITY_ID ";
    }

    query = query + " from non_catalog_snapshot a2";

    Vector syn2 = null;
    if (search_string != null) {
      syn2 = doLogicM(search_string, "a2");
      query = query + " " + syn2.elementAt(0).toString() + " ";
    }

    if (search_string != null) {
      query = query + " where " + syn2.elementAt(1).toString() + " ";
    }

    //sortby
    if (sortby != null) {
      if (sortby.equalsIgnoreCase("TRADE_NAME")) {
        query = query + "  order by TRADE_NAME";
      } else if (sortby.equalsIgnoreCase("MFG_DESC")) {
        query = query + "  order by MFG_DESC";
      } else if (sortby.equalsIgnoreCase("FACILITY_ID")) {
        query = query + "  order by FACILITY_ID desc";
      } else if (sortby.equalsIgnoreCase("MATERIAL_ID")) {
        query = query + "  order by MATERIAL_ID";
      } else if (sortby.equalsIgnoreCase("FAC_ITEM_ID")) {
        query = query + "  order by FAC_ITEM_ID desc";
      } else if (sortby.equalsIgnoreCase("MSDS_ID")) {
        query = query + "  order by FAC_MSDS_ID";
      }
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    Vector allC = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      int i = 0;
      while (rs.next()) {
        i++;
        Integer id = new Integer( (int) rs.getInt("ITEM_ID")) == null ? new Integer(0) : new Integer( (int) rs.getInt("ITEM_ID"));
        result = new Hashtable();
        result.put("ITEM_ID", id);
        result.put("MATERIAL_ID", rs.getString("MATERIAL_ID") == null ? "" : rs.getString("MATERIAL_ID"));
        result.put("FAC_MSDS_ID", rs.getString("FAC_MSDS_ID") == null ? "" : rs.getString("FAC_MSDS_ID"));
        result.put("CATALOG_ID", "");
        if (client.equalsIgnoreCase("Southwest Airlines") || client.equalsIgnoreCase("BAE")) {
          result.put("FACILITY_ID", " ");
        } else {
          result.put("FACILITY_ID", rs.getString("FACILITY_ID") == null ? "" : rs.getString("FACILITY_ID"));
        }
        result.put("FAC_ITEM_ID", rs.getString("FAC_ITEM_ID") == null ? "" : rs.getString("FAC_ITEM_ID"));
        result.put("MATERIAL_DESC", rs.getString("MATERIAL_DESC") == null ? "" : rs.getString("MATERIAL_DESC"));
        result.put("TRADE_NAME", rs.getString("TRADE_NAME") == null ? "" : rs.getString("TRADE_NAME"));
        result.put("MFG_DESC", rs.getString("MFG_DESC") == null ? "" : rs.getString("MFG_DESC"));
        result.put("MSDS_ON_LINE", rs.getString("MSDS_ON_LINE") == null ? "" : rs.getString("MSDS_ON_LINE"));
        result.put("SPEC_ON_LINE", rs.getString("SPEC_ON_LINE") == null ? "" : rs.getString("SPEC_ON_LINE"));
        result.put("MFG_PART_NO", rs.getString("MFG_PART_NO") == null ? "" : rs.getString("MFG_PART_NO"));
        result.put("MSDS_NO", rs.getString("MSDS_NO") == null ? "" : rs.getString("MSDS_NO"));

        allC.addElement(result);
      }
      // put qty on last 2 occur of the vector
      allC.addElement(new Integer(i));
      allC.trimToSize();
    } catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.senddatabaseHelpemails("Error in Query for retrieveAllNonCatalogPureLimited", "Error occured while running Query for retrieveAllNonCatalogPureLimited\nError Msg:\n" + e.getMessage() + "\n\n DB Clinet : " + db.getClient() + " \n\nQuery:  " + query + " ");
      throw e;
    } finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }

    return allC;
  }

  public Vector retrieveAllNonCompanyCatalog(String search_string, String fac, String sortby, String client) throws Exception {
    search_string = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(search_string);
    String query = "";
     query += " select msds_no,item_id,fac_item_id,material_desc,";
     query += "trade_name,mfg_desc,msds_on_line,spec_on_line,material_id,fac_msds_id,mfg_part_no";
     if (fac == null) {
        query += ",' ' facility_id";
     }else {
        query += ",'"+fac+"' facility_id";
     }
     query += " from (select fac_msds_id msds_no,item_id,fac_item_id,material_desc,";
     query += "trade_name,mfg_desc,msds_on_line,spec_on_line,material_id,fac_msds_id,mfg_part_no,";
     query += "row_number() over (partition by material_id order by fac_msds_id nulls last) material_rank,";
     query += "row_number() over (partition by material_id, fac_msds_id order by facility_id) msds_rank";

     query = query + " from noncatalog_material_view a2";
	 String tmpWhere = (fac == null ? "" : " where facility_id='" + fac + "' ");
	 if (!BothHelpObjs.isBlankString(search_string)) {
        if (tmpWhere.length() == 0) {
            tmpWhere += " where "+doSearchLogic("search",search_string);
        }else {
            tmpWhere += " and ("+doSearchLogic("search",search_string)+")";
        }
     }
	 query += tmpWhere+") where material_rank = 1 or (msds_rank = 1 and msds_no is not null) ";

     if (union) {
      query = query + "union all select  distinct a2.fac_msds_id MSDS_NO,a2.ITEM_ID,a2.FAC_ITEM_ID,a2.MATERIAL_DESC,a2.TRADE_NAME,a2.MFG_DESC,a2.MSDS_ON_LINE,a2.SPEC_ON_LINE,a2.MATERIAL_ID,a2.FAC_MSDS_ID,a2.MFG_PART_NO,";
      if (fac != null) {
        query = query + "'" + fac + "' FACILITY_ID";
      } else {
        query = query + "' ' FACILITY_ID";
      }
      query = query + " from non_catalog_snapshot a2";

      Vector syn2 = null;
      if (search_string != null) {
        syn2 = doLogicM(search_string, "a2");
        query = query + " " + syn2.elementAt(0).toString() + " ";
      }

      if (search_string != null) {
        query = query + " where " + syn2.elementAt(1).toString() + " ";
      }
    }

    if (sortby != null) {

      if (sortby.equalsIgnoreCase("TRADE_NAME")) {
        query = query + "  order by TRADE_NAME";
      } else if (sortby.equalsIgnoreCase("MFG_DESC")) {
        query = query + "  order by MFG_DESC";
      } else if (sortby.equalsIgnoreCase("FACILITY_ID")) {
        query = query + "  order by FACILITY_ID desc";
      } else if (sortby.equalsIgnoreCase("MATERIAL_ID")) {
        query = query + "  order by MATERIAL_ID";
      } else if (sortby.equalsIgnoreCase("FAC_ITEM_ID")) {
        query = query + "  order by FAC_ITEM_ID desc";
      } else if (sortby.equalsIgnoreCase("MSDS_ID")) {
        query = query + "  order by FAC_MSDS_ID";
      }
      /*else if ( ( fac == null ) && ( sortby.equalsIgnoreCase( "CATALOG_ID" ) ) )
             {
        query=query + "  order by CATALOG_ID";
             }*/
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    Vector allC = new Vector();

    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      int i = 0;
      while (rs.next()) {
        i++;
        Integer id = new Integer( (int) rs.getInt("ITEM_ID")) == null ? new Integer(0) : new Integer( (int) rs.getInt("ITEM_ID"));
        result = new Hashtable();
        result.put("ITEM_ID", id);
        result.put("MATERIAL_ID", rs.getString("MATERIAL_ID") == null ? "" : rs.getString("MATERIAL_ID"));
        result.put("FAC_MSDS_ID", rs.getString("FAC_MSDS_ID") == null ? "" : rs.getString("FAC_MSDS_ID"));
        result.put("CATALOG_ID", "");
        if (client.equalsIgnoreCase("Southwest Airlines") || client.equalsIgnoreCase("BAE") || client.equalsIgnoreCase("NO FACILITY")) {
          result.put("FACILITY_ID", " ");
        } else {
          result.put("FACILITY_ID", rs.getString("FACILITY_ID") == null ? "" : rs.getString("FACILITY_ID"));
        }
        result.put("FAC_ITEM_ID", rs.getString("FAC_ITEM_ID") == null ? "" : rs.getString("FAC_ITEM_ID"));
        result.put("MATERIAL_DESC", rs.getString("MATERIAL_DESC") == null ? "" : rs.getString("MATERIAL_DESC"));
        result.put("TRADE_NAME", rs.getString("TRADE_NAME") == null ? "" : rs.getString("TRADE_NAME"));
        result.put("MFG_DESC", rs.getString("MFG_DESC") == null ? "" : rs.getString("MFG_DESC"));
        result.put("MSDS_ON_LINE", rs.getString("MSDS_ON_LINE") == null ? "" : rs.getString("MSDS_ON_LINE"));
        result.put("SPEC_ON_LINE", rs.getString("SPEC_ON_LINE") == null ? "" : rs.getString("SPEC_ON_LINE"));
        result.put("MFG_PART_NO", rs.getString("MFG_PART_NO") == null ? "" : rs.getString("MFG_PART_NO"));
        result.put("MSDS_NO", rs.getString("MSDS_NO") == null ? "" : rs.getString("MSDS_NO"));

        allC.addElement(result);
      }
      // put qty on last 2 occur of the vector
      allC.addElement(new Integer(i));
      allC.trimToSize();
    } catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.senddatabaseHelpemails("Error in Query for retrieveAllNonCompanyCatalog", "Error occured while running Query for retrieveAllNonCompanyCatalog\nError Msg:\n" + e.getMessage() + "\n\n DB Clinet : " + db.getClient() + " \n\nQuery:  " + query + " ");
      throw e;
    } finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }

    return allC;
  }

  //Nawaz 07-15-02
  public Vector retrieveNeededData(String search_string, String sortby) throws Exception {
    search_string = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(search_string);
    String query = "";
    query = " SELECT distinct a2.fac_msds_id MSDS_NO,a2.MATERIAL_DESC,a2.TRADE_NAME,a2.MFG_DESC,a2.MSDS_ON_LINE,a2.MATERIAL_ID FROM global.non_catalog_snapshot a2";
	 if (!BothHelpObjs.isBlankString(search_string)) {
		query += " where "+doSearchLogic("to_char(material_id)||' '||material_desc||' '||mfg_desc||' '||trade_name",search_string);
	 }

	 if (sortby.equalsIgnoreCase("TRADE_NAME")) {
      query = query + "  order by TRADE_NAME";
    } else if (sortby.equalsIgnoreCase("MFG_DESC")) {
      query = query + "  order by MFG_DESC";
    } else if (sortby.equalsIgnoreCase("MATERIAL_ID")) {
      query = query + "  order by MATERIAL_ID";
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    Vector allC = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      int i = 0;
      while (rs.next()) {
        i++;
        result = new Hashtable();
        result.put("ITEM_ID", " ");
        result.put("MATERIAL_ID", rs.getString("MATERIAL_ID") == null ? "" : rs.getString("MATERIAL_ID"));
        result.put("FAC_MSDS_ID", " ");
        result.put("CATALOG_ID", " ");
        result.put("FACILITY_ID", " ");
        result.put("FAC_ITEM_ID", "");
        result.put("MATERIAL_DESC", rs.getString("MATERIAL_DESC") == null ? "" : rs.getString("MATERIAL_DESC"));
        result.put("TRADE_NAME", rs.getString("TRADE_NAME") == null ? "" : rs.getString("TRADE_NAME"));
        result.put("MFG_DESC", rs.getString("MFG_DESC") == null ? "" : rs.getString("MFG_DESC"));
        result.put("MSDS_ON_LINE", rs.getString("MSDS_ON_LINE") == null ? "" : rs.getString("MSDS_ON_LINE"));
        result.put("SPEC_ON_LINE", " ");
        result.put("MFG_PART_NO", " ");
        result.put("MSDS_NO", rs.getString("MSDS_NO") == null ? "" : rs.getString("MSDS_NO"));

        allC.addElement(result);
      }
      // put qty on last 2 occur of the vector
      allC.addElement(new Integer(i));
      allC.trimToSize();
    } catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.senddatabaseHelpemails("Error in Query for retrieveNeededData", "Error occured while running Query for retrieveNeededData\nError Msg:\n" + e.getMessage() + "\n\n DB Clinet : " + db.getClient() + " \n\nQuery:  " + query + " ");
      throw e;
    } finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }
    return allC;
  }
}
