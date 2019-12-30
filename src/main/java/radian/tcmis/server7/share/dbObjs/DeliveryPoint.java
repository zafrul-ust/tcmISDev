/*
 *  Copyright 1999-2000
 *
 *  URS Corporation, Radian
 *  All rights reserved
 */
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.sql.*;

/**
 * Description of the Class
 *
 * @created   January 16, 2001
 */
public class DeliveryPoint {

  /**
   * Description of the Field
   */
  protected TcmISDBModel db;
  /**
   * Description of the Field
   */
  protected String facility_id;
  /**
   * Description of the Field
   */
  protected String location_id;
  /**
   * Description of the Field
   */
  protected String delivery_point;
  /**
   * Description of the Field
   */
  protected String delivery_point_desc;

  protected String status = "";

  protected String source = "";
  protected String dockSelectionRule = "";
  protected String deliveryPointSelectionRule = "";

  public void setStatus(String status) {
    this.status = status;
  }
  public void setSource(String source) {
    this.source = source;
  }
  public void setDockSelectionRule(String dockSelectionRule) {
    this.dockSelectionRule = dockSelectionRule;
  }
  public void setDeliveryPointSelectionRule(String deliveryPointSelectionRule) {
    this.deliveryPointSelectionRule = deliveryPointSelectionRule;
  }

  /**
   * Constructor for the DeliveryPoint object
   *
   * @param db  Description of Parameter
   */
  public DeliveryPoint(TcmISDBModel db) {
    this.db = db;
  }

  /**
   * Sets the DeliveryPointDesc attribute of the DeliveryPoint object
   *
   * @param s  The new DeliveryPointDesc value
   */
  public void setDeliveryPointDesc(String s) {
    delivery_point_desc = new String(s);
  }

  /**
   * Sets the DeliveryPoint attribute of the DeliveryPoint object
   *
   * @param s  The new DeliveryPoint value
   */
  public void setDeliveryPoint(String s) {
    delivery_point = new String(s);
  }

  /**
   * Sets the LocationId attribute of the DeliveryPoint object
   *
   * @param s  The new LocationId value
   */
  public void setLocationId(String s) {
    location_id = new String(s);
  }

  /**
   * Sets the FacilityId attribute of the DeliveryPoint object
   *
   * @param s  The new FacilityId value
   */
  public void setFacilityId(String s) {
    facility_id = new String(s);
  }

  /**
   * Gets the DeliveryPointDesc attribute of the DeliveryPoint object
   *
   * @return   The DeliveryPointDesc value
   */
  public String getDeliveryPointDesc() {
    return delivery_point_desc;
  }

  /**
   * Gets the DeliveryPoint attribute of the DeliveryPoint object
   *
   * @return   The DeliveryPoint value
   */
  public String getDeliveryPoint() {
    return delivery_point;
  }

  /**
   * Gets the LocationId attribute of the DeliveryPoint object
   *
   * @return   The LocationId value
   */
  public String getLocationId() {
    return location_id;
  }

  /**
   * Gets the FacilityId attribute of the DeliveryPoint object
   *
   * @return   The FacilityId value
   */
  public String getFacilityId() {
    return facility_id;
  }

  /**
   * Gets the DeliveryPointsForFacility attribute of the DeliveryPoint object
   *
   * @param facId          Description of Parameter
   * @return               The DeliveryPointsForFacility value
   * @exception Exception  Description of Exception
   */
  public Vector getDocksNDeliveryPointsForFacility(String facId, Vector allDocks) throws Exception {
    Vector outV = new Vector();
    String query = "select a.location_id,a.delivery_point,nvl(a.delivery_point_desc,a.delivery_point) delivery_point_desc from fac_loc_delivery_point a, facility_dock b where"+
                   " a.facility_id = '" + facility_id + "' and a.facility_id = b.facility_id and a.location_id = b.dock_location_id";
    if ("A".equalsIgnoreCase(status)) {
      query += " and a.status = 'A' and b.status = 'A'";
    }
    query += " order by a.location_id,a.delivery_point_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Hashtable h = new Hashtable();
        String tmpDock = rs.getString("location_id");
        //docks
        if (!allDocks.contains(tmpDock)) {
          allDocks.addElement(tmpDock);
        }
        //delivery points
        h.put("DOCK", tmpDock);
        h.put("DEL_POINT", rs.getString("delivery_point"));
        if (rs.getString("delivery_point_desc") == null) {
          h.put("DEL_POINT_DESC", "");
        } else {
          h.put("DEL_POINT_DESC", rs.getString("delivery_point_desc"));
        }
        outV.addElement(h);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return outV;
  } //end of method

  /**
   * Gets the getWasteObsoleteDeliveryPoint attribute of the DeliveryPoint object
   *
   * @param facid          Description of Parameter
   * @param appid          Description of Parameter
   * @return               The PrefDelPoints value
   * @exception Exception  Description of Exception
   */
  public Hashtable getWasteObsoleteDeliveryPoint(String facid) throws Exception {
    Hashtable result = new Hashtable(2);
    Vector v = new Vector();
    Vector docks = new Vector(5);
    String query = "select delivery_point, nvl(delivery_point_desc,delivery_point) delivery_point_desc, location_id from fac_loc_delivery_point" +
        " where facility_id = '" + facid + "' and scrap_obsolete = 'Y' order by location_id,delivery_point_desc, delivery_point";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Hashtable out = new Hashtable();
        out.put("DEL_POINT", rs.getString("delivery_point"));
        if (BothHelpObjs.isBlankString(rs.getString("delivery_point_desc"))) {
          out.put("DEL_POINT_DESC", "");
        } else {
          out.put("DEL_POINT_DESC", rs.getString("delivery_point_desc"));
        }
        String dock = rs.getString("location_id");
        out.put("DOCK", dock);
        v.addElement(out);

        if (!docks.contains(dock)) {
          docks.addElement(dock);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    result.put("DELIVERY_POINT", v);
    result.put("DOCK", docks);
    return result;
  } //end of method

  //Hashtable getPrefDelPoints(String facid, String appid)throws Exception{
  /**
   * Gets the PrefDelPoints attribute of the DeliveryPoint object
   *
   * @param facid          Description of Parameter
   * @param appid          Description of Parameter
   * @return               The PrefDelPoints value
   * @exception Exception  Description of Exception
   */
  public Vector getPrefDelPoints(String facid, String appid) throws Exception {
    Vector v = new Vector();
    String query = "select b.delivery_point, nvl(b.delivery_point_desc,b.delivery_point) delivery_point_desc, b.location_id ";
    query = query + "  from fac_loc_app a, fac_loc_delivery_point b";
    query = query + " where a.facility_id = '" + facility_id + "' and a.application = '" + appid + "' and ";
    query = query + "a.facility_id = b.facility_id and a.location_id = b.location_id and a.delivery_point = b.delivery_point";
    query = query + " order by b.location_id,delivery_point_desc, delivery_point";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Hashtable out = new Hashtable();
        out.put("DEL_POINT", rs.getString("delivery_point"));
        if (BothHelpObjs.isBlankString(rs.getString("delivery_point_desc"))) {
          out.put("DEL_POINT_DESC", "");
        } else {
          out.put("DEL_POINT_DESC", rs.getString("delivery_point_desc"));
        }
        out.put("DOCK", rs.getString("location_id"));
        v.addElement(out);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return v;
  } //end of method

  //if there dock and deliver to in edi_shipto_mapping
  public Hashtable getDockNDelPointFromEdiShipto(String facid, String appid, String catalogId, String catPartNo, String partGroupNo) {
    Hashtable result = new Hashtable(5);
    result.put("SUCCESS","OK");
    String query = "select nvl(a.default_delivery_point,' ') delivery_point, nvl(a.haas_shipto_location_id, ' ') location_id," +
                   "nvl(dock_selection_rule,' ') dock_selection_rule, nvl(delivery_point_selection_rule,' ') delivery_point_selection_rule"+
                   " from edi_shipto_mapping a" +
                   " where a.default_facility_id = '" + facility_id + "' and a.default_application = '" + appid + "' and" +
                   " a.default_catalog_id = '" + catalogId + "' and a.default_cat_part_no = '" + catPartNo + "' and a.default_part_group_no = " + partGroupNo;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      boolean ediShiptoHasData = false;
      while (rs.next()) {
        dockSelectionRule = rs.getString("dock_selection_rule");
        deliveryPointSelectionRule = rs.getString("delivery_point_selection_rule");
        result.put("LOCATION_ID", rs.getString("location_id"));
        result.put("DELIVERY_POINT", rs.getString("delivery_point"));
        ediShiptoHasData = true;
      }
      if (ediShiptoHasData) {
        //get dock and delivery point info if dock is FIXED
        if ("FIXED".equalsIgnoreCase(dockSelectionRule)) {
          source = "EDI_SHIPTO_MAPPING";
          result.put("DOCK_N_DELIVER_TO_INFO", this.getDelPointsForDock(facid, appid));
        } else {
          Vector allDocks = new Vector();
          result.put("DOCK_N_DELIVER_TO_INFO", getDocksNDeliveryPointsForFacility(facid, allDocks));
          result.put("ALL_DOCKS", allDocks);
        }
      }else {
        result.put("SUCCESS","NO DATA");
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      result.put("SUCCESS","ERROR");
    } finally {
      dbrs.close();
    }
    return result;
  } //end of method

  /**
   * Gets the PrefDelPoints attribute of the DeliveryPoint object
   *
   * @param facid          Description of Parameter
   * @param appid          Description of Parameter
   * @return               The PrefDelPoints value
   * @exception Exception  Description of Exception
   */
  public Vector getDelPointsForDock(String facid, String appid) throws Exception {
    Vector v = new Vector();
    String query = "";
    if ("EDI_SHIPTO_MAPPING".equalsIgnoreCase(source)) {
      query = "select b.delivery_point, nvl(b.delivery_point_desc,b.delivery_point) delivery_point_desc, b.location_id " +
          " from edi_shipto_mapping a, fac_loc_delivery_point b" +
          " where a.default_facility_id = '" + facility_id + "' and a.default_application = '" + appid + "' and" +
          " a.dock_selection_rule = 'FIXED' and a.default_facility_id = b.facility_id" +
          " and a.haas_shipto_location_id = b.location_id";
      if ("FIXED".equalsIgnoreCase(deliveryPointSelectionRule)) {
        query += " and a.default_delivery_point = b.delivery_point";
      }
    }else {
      query = "select b.delivery_point, nvl(b.delivery_point_desc,b.delivery_point) delivery_point_desc, b.location_id " +
          " from fac_loc_app a, fac_loc_delivery_point b" +
          " where a.facility_id = '" + facility_id + "' and a.application = '" + appid + "' and" +
          " a.dock_selection_rule = 'FIXED' and a.facility_id = b.facility_id" +
          " and a.location_id = b.location_id";
    }

    if ("A".equalsIgnoreCase(status)) {
      query += " and b.status = 'A'";
    }
    query += " order by b.location_id,delivery_point_desc, delivery_point";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Hashtable out = new Hashtable();
        out.put("DEL_POINT", rs.getString("delivery_point"));
        if (BothHelpObjs.isBlankString(rs.getString("delivery_point_desc"))) {
          out.put("DEL_POINT_DESC", "");
        } else {
          out.put("DEL_POINT_DESC", rs.getString("delivery_point_desc"));
        }
        out.put("DOCK", rs.getString("location_id"));
        v.addElement(out);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return v;
  } //end of method

  public String getDefaultDockForWorkArea(String facId, String appId) {
    String result = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select nvl(location_id,' ') location_id from fac_loc_app where dock_selection_rule = 'DEFAULT'"+
                     " and facility_id = '"+facId+"' and application = '"+appId+"'";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        result = rs.getString("location_id");
      }
    } catch (Exception e) {
      result = "";
    } finally {
      dbrs.close();
    }
    return result;
  } //end of method

  public String getDefaultDeliverToForWorkArea(String facId, String appId) {
    String result = "";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select nvl(delivery_point,' ') delivery_point from fac_loc_app where delivery_point_selection_rule = 'DEFAULT'"+
                     " and facility_id = '"+facId+"' and application = '"+appId+"'";
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        result = rs.getString("delivery_point");
      }
    } catch (Exception e) {
      result = "";
    } finally {
      dbrs.close();
    }
    return result;
  } //end of method


  public Vector getFixedDockNDelPointForWorkArea(String facid, String appid) throws Exception {
    Vector v = new Vector();
    String query = "select b.delivery_point, nvl(b.delivery_point_desc,b.delivery_point) delivery_point_desc, b.location_id" +
        " from fac_loc_app a, fac_loc_delivery_point b" +
        " where a.facility_id = '" + facility_id + "' and a.application = '" + appid + "' and" +
        " a.dock_selection_rule = 'FIXED' and" +
        " a.delivery_point_selection_rule = 'FIXED' and"+
        " a.facility_id = b.facility_id and a.location_id = b.location_id and a.delivery_point = b.delivery_point" +
        " order by location_id,delivery_point_desc, delivery_point";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Hashtable out = new Hashtable();
        out.put("DEL_POINT", rs.getString("delivery_point"));
        if (BothHelpObjs.isBlankString(rs.getString("delivery_point_desc"))) {
          out.put("DEL_POINT_DESC", "");
        } else {
          out.put("DEL_POINT_DESC", rs.getString("delivery_point_desc"));
        }
        out.put("DOCK", rs.getString("location_id"));
        v.addElement(out);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return v;
  } //end of method

  /**
   * Determines if a delivery point is already defined for the given facility and location.
   *
   * @param fac            The facility_id
   * @param loc            The location_id
   * @param delpt          The delivery_point
   * @return               <tt>true if the delivery point exists, otherwise <tt>false</tt>.
   * @exception Exception  Description of Exception
   */
  public boolean deliveryPointExists(String fac, String loc, String delpt) throws Exception {
    try {
      return (DbHelpers.countQuery(db, "fac_loc_delivery_point", "where facility_id =  '" + HelpObjs.singleQuoteToDouble(fac)
                                   + "' and location_id = '" + HelpObjs.singleQuoteToDouble(loc)
                                   + "' and delivery_point = '" + HelpObjs.singleQuoteToDouble(delpt) + "'")) > 0;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Adds a new delivery point for the specified facility and location if it is not
   * already defined.
   *
   * @param fac            The facility_id
   * @param loc            The location_id
   * @param delpt          The delivery_point
   * @param desc           The delivery_point description
   * @exception Exception  Description of Exception
   */
  public void addDeliveryPoint(String fac, String loc, String delpt, String desc) throws Exception {
    try {
      if (deliveryPointExists(fac, loc, delpt)) {
        return;
      }

      String query = "insert into fac_loc_delivery_point ";
      query = query + "(facility_id, location_id, delivery_point, delivery_point_desc)";
      query = query + " values ('" + HelpObjs.singleQuoteToDouble(fac)
          + "','" + HelpObjs.singleQuoteToDouble(loc)
          + "','" + HelpObjs.singleQuoteToDouble(delpt)
          + "','" + HelpObjs.singleQuoteToDouble(desc) + "')";

      db.doUpdate(query);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Description of the Method
   *
   * @exception Exception  Description of Exception
   */
  public void load() throws Exception {
    String query = "select * from fac_loc_delivery_point where ";
    query = query + "facility_id = '" + facility_id + "' and ";
    query = query + "location_id = '" + location_id + "' and ";
    query = query + "delivery_point = '" + delivery_point + "'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      if (rs.next()) {
        this.setDeliveryPointDesc(rs.getString("delivery_point_desc"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
  }

}
