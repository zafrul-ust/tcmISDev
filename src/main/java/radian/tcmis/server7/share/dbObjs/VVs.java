package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.sql.*;
import java.util.*;

public class VVs {

  public static Vector getVVPkgStyle(TcmISDBModel db) throws Exception {
    String query = "";
    if (db.getClient().equalsIgnoreCase("Seagate")) {
      query = "select VV_PKG_STYLE PKG_STYLE from cont_type_pkg_style_link order by VV_PKG_STYLE";
    } else {
      query = "select PKG_STYLE from vv_pkg_style order by PKG_STYLE";
    }
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector v = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        v.addElement(rs.getString("PKG_STYLE"));
      }

    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();

    }
    return v;
  }

  public static Vector getVVPeriodUnit(TcmISDBModel db) throws Exception {
    String query = "select PERIOD_UNIT from vv_period_unit order by PERIOD_UNIT";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector v = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        v.addElement(rs.getString("PERIOD_UNIT"));
      }

    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();

    }
    return v;
  }

  public static Vector getVVNetWt(TcmISDBModel db) throws Exception {
    String query = "";
    if (db.getClient().equalsIgnoreCase("Seagate")) {
      query = "select a.FROM_UNIT from size_unit_conversion a, cont_size_size_unit_link b where a.SP_GRAVITY_REQUIRED = 'N' and a.to_unit = 'lb' and a.from_unit = b.vv_size_unit order by FROM_UNIT";
    } else {
      query = "select FROM_UNIT from size_unit_conversion where to_unit = 'lb' order by FROM_UNIT";
    }
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector v = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        v.addElement(rs.getString("FROM_UNIT"));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();
    }
    return v;
  }

  public static Hashtable getSizeUnitNetWtRequired(TcmISDBModel db) throws Exception {
    String query = "select SIZE_UNIT,net_wt_required from size_unit_view order by SIZE_UNIT";
    /* it doesn't matter which client I am. 4-28-02
           String query = "";
           if (db.getClient().equalsIgnoreCase("Seagate")) {
      query = "select SIZE_UNIT,net_wt_required from size_unit_view order by SIZE_UNIT";
           }else {
      query = "select SIZE_UNIT,net_wt_required from wt_size_unit_view order by SIZE_UNIT";
           }  */
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector v = new Vector();
    Hashtable unit = new Hashtable();
    Hashtable result = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        String sizeUnit = BothHelpObjs.makeBlankFromNull(rs.getString("SIZE_UNIT"));
        v.addElement(sizeUnit);
        String netWt = BothHelpObjs.makeBlankFromNull(rs.getString("net_wt_required"));
        unit.put(sizeUnit, new Boolean("Y".equalsIgnoreCase(netWt)));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();
    }
    result.put("SIZE_UNIT", v);
    result.put("NET_WT_REQUIRED", unit);
    return result;
  }

  public static Vector getVVSizeUnit(TcmISDBModel db) throws Exception {
    String query = "select SIZE_UNIT from vv_size_unit order by SIZE_UNIT";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector v = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        v.addElement(rs.getString("SIZE_UNIT"));
      }

    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();

    }
    return v;
  }

  public static String getVVAddRequestStatus(TcmISDBModel db, int i) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = "select REQUEST_STATUS_DESC  from VV_CATALOG_ADD_REQUEST_STATUS ";
    query = query + " where request_status = " + i;
    String s = "";
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        s = rs.getString("REQUEST_STATUS_DESC");
        break;
      }

    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();

    }
    return s;
  }

  public static Vector getVVAddRequestStatus(TcmISDBModel db, Integer value) throws Exception {
    String query = "select REQUEST_STATUS_DESC  from VV_CATALOG_ADD_REQUEST_STATUS ";
    if (value.intValue() > 0) {
      query = query + " where request_status = " + value;
    }
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector v = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        v.addElement(rs.getString("REQUEST_STATUS_DESC"));
      }

    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();

    }
    return v;
  }

  public static boolean hasMSDS(TcmISDBModel db, Integer matid) throws Exception {
    String query = "select count(*) from (select on_line from MSDS where upper(on_line) = 'Y' and material_id = "+matid+
                   " union all "+
                   "select on_line from msds_locale_view where upper(on_line) = 'Y' and material_id = "+matid+")";
    int count = 0;
    try {
      count = HelpObjs.countQuery(db, query);
    } catch (Exception e) {
      throw e;
    }
    return (count > 0);
  }

  /*
      public static boolean hasMSDS(TcmISDBModel db, Integer matid) throws Exception {
     String query = "select ON_LINE  from MSDS ";
     query = query + " where material_id = " + matid;
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String  res = null;
     try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
         res =  rs.getString("ON_LINE");
         break;
        }
    } catch (Exception e) {
      throw e;
      } finally{
            dbrs.close();
    }
    return (res!=null && res.equalsIgnoreCase("Y"));
      }
   */

  public static Object[][] getAllManufacturers(TcmISDBModel db, String keyIn, String searchBy) throws Exception {
    String key = HelpObjs.singleQuoteToDouble(keyIn);
    String query = "select * from manufacturer where ";
    if (searchBy.equalsIgnoreCase("manufacturer_id")) {
      try {
        Integer I = new Integer(key);
      } catch (Exception e) {
        Object[][] uhOh = null;
        return uhOh;
      }
      query = query + "mfg_id = '" + key + "'";
    } else {
      query = query + "lower(mfg_desc) like lower('%" + key + "%')";
    }
    query = query + " order by mfg_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector vID = new Vector();
    Vector vDesc = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        vID.addElement(rs.getString("mfg_id"));
        vDesc.addElement(rs.getString("mfg_desc"));
      }

    } catch (Exception e) {

      throw e;
    } finally {
      dbrs.close();

    }
    Object[][] oa = null;
    if (vID.size() < 1) {
      return oa;
    }
    oa = new Object[vID.size()][2];
    for (int x = 0; x < vID.size(); x++) {
      oa[x][0] = vID.elementAt(x).toString();
      oa[x][1] = vDesc.elementAt(x).toString();
    }
    return oa;
  }

  public static Object[][] getNextManufacturer(TcmISDBModel db, String keyIn) throws Exception {
    String key = HelpObjs.singleQuoteToDouble(keyIn);
    String query = "select * from manufacturer where ";
    query = query + "lower(mfg_desc) like lower('" + key + "%')";
    query = query + " order by mfg_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector vID = new Vector();
    Vector vDesc = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        vID.addElement(rs.getString("mfg_id"));
        vDesc.addElement(rs.getString("mfg_desc"));
        break;
      }

    } catch (Exception e) {

      throw e;
    } finally {
      dbrs.close();

    }
    Object[][] oa = null;
    if (vID.size() < 1) {
      return oa;
    }
    oa = new Object[vID.size()][2];
    for (int x = 0; x < vID.size(); x++) {
      oa[x][0] = vID.elementAt(x).toString();
      oa[x][1] = vDesc.elementAt(x).toString();
    }
    return oa;
  }

  public static Object[][] getAllFlowDows(TcmISDBModel db, String keyIn, String searchBy, String type) throws Exception {
    String key = HelpObjs.singleQuoteToDouble(keyIn);
    String query = "select distinct * from vv_flow_down where " +
        "lower(" + searchBy + ") like lower('%" + key + "%')" +
        " and lower(flow_down_type) = lower('" + type + "')" +
        " order by " + searchBy;

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector vID = new Vector();
    Vector vDesc = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        vID.addElement(rs.getString("flow_down"));
        vDesc.addElement(rs.getString("flow_down_desc"));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();
    }
    Object[][] oa = null;
    if (vID.size() < 1) {
      return oa;
    }
    oa = new Object[vID.size()][2];
    for (int x = 0; x < vID.size(); x++) {
      oa[x][0] = vID.elementAt(x).toString();
      oa[x][1] = vDesc.elementAt(x).toString();
    }
    return oa;
  }

  //trong 3/20
  public static Object[][] getCatalogFlowDows(TcmISDBModel db, String keyIn, String searchBy, String type, String catalog) throws Exception {
    String key = HelpObjs.singleQuoteToDouble(keyIn);
    String query = "select distinct * from vv_flow_down where catalog_id = '" + catalog + "'";
    query += " and lower(" + searchBy + ") like lower('%" + key + "%')" +
        " and lower(flow_down_type) = lower('" + type + "')" +
        " order by " + searchBy;

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector vID = new Vector();
    Vector vDesc = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        vID.addElement(rs.getString("flow_down"));
        vDesc.addElement(rs.getString("flow_down_desc"));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();
    }
    Object[][] oa = null;
    if (vID.size() < 1) {
      return oa;
    }
    oa = new Object[vID.size()][2];
    for (int x = 0; x < vID.size(); x++) {
      oa[x][0] = vID.elementAt(x).toString();
      oa[x][1] = vDesc.elementAt(x).toString();
    }
    return oa;
  }

  public static Vector getAllCountries(TcmISDBModel db) throws Exception {
    Vector v = new Vector();
    Vector name = new Vector();
    Vector abbrev = new Vector();
    String query = "select *  from vv_country order by country";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        abbrev.addElement(rs.getString("COUNTRY_ABBREV"));
        name.addElement(rs.getString("COUNTRY"));
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    v.addElement(abbrev);
    v.addElement(name);
    return v;
  }

  public static Vector getCountriesShortList(TcmISDBModel db) throws Exception {
    Vector v = new Vector();
    Vector name = new Vector();
    Vector abbrev = new Vector();
    String query = "select *  from vv_country where country_abbrev in('BRA','CAN','FRA','DEU','ITA','MEX','ESP','GBR','USA','JPN') order by country";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        abbrev.addElement(rs.getString("COUNTRY_ABBREV"));
        name.addElement(rs.getString("COUNTRY"));
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    v.addElement(abbrev);
    v.addElement(name);
    return v;
  }

  public static Vector getVVTemperatures(TcmISDBModel db, String reqID) throws Exception {
    String query = "select a.storage_temp from inventory_group_storage_temp a, catalog_add_request_new b,catalog_add_item cai,facility c" +
        " where b.request_id = " + reqID + " and b.eng_eval_facility_id = c.facility_id and b.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1 and" +
        " c.inventory_group_default = a.inventory_group order by display_order";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector v = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        v.addElement(rs.getString("storage_temp"));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();

    }
    return v;
  }

  public static Vector getLabelColors(TcmISDBModel db, String reqID) throws Exception {
    String query = "select iglc.label_color from inventory_group_label_color iglc, catalog_add_request_new b,facility c" +
        " where b.request_id = " + reqID + " and b.eng_eval_facility_id = c.facility_id and" +
        " c.inventory_group_default = iglc.inventory_group order by iglc.label_color";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector v = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        v.addElement(rs.getString("label_color"));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();

    }
    return v;
  }

  public static Boolean getRequireLabelColor(TcmISDBModel db, String reqID) throws Exception {
    String query = "select fx_require_label_color(" + reqID + ") require_label_color from dual";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Boolean result = new Boolean(false);
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        result = new Boolean("Y".equalsIgnoreCase(rs.getString("require_label_color")));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();

    }
    return result;
  }

  public static Boolean isComponentInventoryGroupRestricted(TcmISDBModel db, String reqId) throws Exception {
    String query = "select count(*) from component_inventory_group cig, catalog_part_item_group cpig , catalog_part_inventory cpi,"+
	  					 " facility f, catalog_add_request_new carn, catalog_add_item cai"+
                   " where carn.request_id = "+reqId+" and carn.engineering_evaluation = 'n' and cai.item_id is not null and"+
		  				 " carn.company_id = cai.company_id and carn.request_id = cai.request_id and cai.line_item = 1 and"+
						 " carn.eng_eval_facility_id = f.facility_id and cai.item_id = cpig.item_id and carn.catalog_id = cpi.catalog_id and carn.catalog_company_id = cpi.catalog_company_id and"+
                   " cpig.status in ('A','D','R') and cpig.catalog_id = cpi.catalog_id and cpig.cat_part_no = cpi.cat_part_no and"+
                   " cpig.part_group_no = cpi.part_group_no and cpi.inventory_group = f.inventory_group_default and"+
                   " cpi.inventory_group = cig.inventory_group and cpig.item_id = cig.item_id";
    Boolean result = new Boolean(false);
    try {
      result = new Boolean(HelpObjs.countQuery(db,query) > 0);
    } catch (Exception e) {
      throw e;
    }
    return result;
  }

} //end of class
