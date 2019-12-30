package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;

public class WasteLabelTraveler {
  protected TcmISDBModel db;
  protected String facility = "";
  protected String container_id = "";
  protected String traveler_id = "";
  protected String work_area = "";
  protected String profile = "";
  protected String description = "";
  protected String dot = "";
  protected String mgmt_opt = "";
  protected String vendor_id = "";

  protected int count = 0;

//Get methods
  public String getFacility() {
    return facility;
  }

  public String getContainerId() {
    return container_id;
  }

  public String getTravelerId() {
    return traveler_id;
  }

  public String getWorkArea() {
    return work_area;
  }

  public String getProfile() {
    return profile;
  }

  public String getDescription() {
    return description;
  }

  public String getDot() {
    return dot;
  }

  public String getVendorId() {
    return vendor_id;
  }

  public String getManagementOption() {
    return mgmt_opt;
  }

//Set methods
  public void setFacility(String x) {
    facility = x;
  }

  public void setContainerId(String x) {
    container_id = x;
  }

  public void setTravelerId(String x) {
    traveler_id = x;
  }

  public void setWorkArea(String x) {
    work_area = x;
  }

  public void setProfile(String x) {
    profile = x;
  }

  public void setDescription(String x) {
    description = x;
  }

  public void setDot(String x) {
    dot = x;
  }

  public void setVendorId(String x) {
    vendor_id = x;
  }

  public void setManagementOption(String x) {
    mgmt_opt = x;
  }

  //Constructor
  public WasteLabelTraveler(TcmISDBModel db) {
    this.db = db;
  }

  public WasteLabelTraveler() {
  }

  public void setDbModel(TcmISDBModel db) {
    this.db = db;
  }

//Build label information
  public static Vector buildWasteLabel(TcmISDBModel db, Vector containerId) throws Exception {
    Vector v = new Vector();
    String query = "";

    if (containerId.elementAt(0).toString().equalsIgnoreCase("traveler")) {
      try {
        query = "select * from waste_traveler_view where traveler_id=" + containerId.elementAt(1);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Hashtable result = new Hashtable();
      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        Hashtable h = new Hashtable();

        while (rs.next()) {
          if (rs.getString("facility_id") != null) {
            h.put("FACILITY", rs.getString("facility_id"));
          } else {
            h.put("FACILITY", "none");
          }
          if (rs.getString("traveler_id") != null) {
            h.put("TRAVELER_ID", rs.getString("traveler_id"));
          } else {
            h.put("TRAVELER_ID", "none");
          }
          if (rs.getString("generation_point") != null) {
            h.put("WORK_AREA", rs.getString("generation_point"));
          } else {
            h.put("WORK_AREA", "none");
          }
          if (rs.getString("vendor_profile_id") != null) {
            h.put("VENDOR_PROFILE_ID", rs.getString("vendor_profile_id"));
          } else {
            h.put("VENDOR_PROFILE_ID", "none");
          }
          if (rs.getString("description") != null) {
            h.put("DESCRIPTION", rs.getString("description"));
          } else {
            h.put("DESCRIPTION", "none");
          }
          if (rs.getString("proper_shipping_name") == null && rs.getString("hazard_class") == null && rs.getString("shipping_id") == null && rs.getString("packing_group") == null) {
            h.put("DOT", "none");
          } else {
            h.put("DOT", rs.getString("proper_shipping_name") + "\n" + rs.getString("hazard_class") + "\n" + rs.getString("shipping_id") + "\n" + rs.getString("packing_group"));
          }
          if (rs.getString("management_option_desc") != null) {
            h.put("MANAGEMENT_OPTION", rs.getString("management_option_desc"));
          } else {
            h.put("MANAGEMENT_OPTION", "none");
          }
          if (rs.getString("company_name") != null) {
            h.put("VENDOR_ID", rs.getString("company_name"));
          } else {
            h.put("VENDOR_ID", "none");
          }
          if (rs.getString("container_id") != null) {
            h.put("CONTAINER_ID", rs.getString("container_id"));
          } else {
            h.put("CONTAINER_ID", "none");
          }

          v.addElement(h);
        }
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(WasteLabelTraveler): " + e.getMessage(), null);
        throw e;
      } finally {
        dbrs.close();
      }
    } else {
      for (int i = 0; i < containerId.size(); i++) {
        try {
          query = "select * from waste_traveler_view where container_id=" + containerId.elementAt(i);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Hashtable result = new Hashtable();
        try {
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          Hashtable h = new Hashtable();

          while (rs.next()) {
            if (rs.getString("facility_id") != null) {
              h.put("FACILITY", rs.getString("facility_id"));
            } else {
              h.put("FACILITY", "none");
            }
            if (rs.getString("traveler_id") != null) {
              h.put("TRAVELER_ID", rs.getString("traveler_id"));
            } else {
              h.put("TRAVELER_ID", "none");
            }
            if (rs.getString("generation_point") != null) {
              h.put("WORK_AREA", rs.getString("generation_point"));
            } else {
              h.put("WORK_AREA", "none");
            }
            if (rs.getString("vendor_profile_id") != null) {
              h.put("VENDOR_PROFILE_ID", rs.getString("vendor_profile_id"));
            } else {
              h.put("VENDOR_PROFILE_ID", "none");
            }
            if (rs.getString("description") != null) {
              h.put("DESCRIPTION", rs.getString("description"));
            } else {
              h.put("DESCRIPTION", "none");
            }
            if (rs.getString("proper_shipping_name") == null && rs.getString("hazard_class") == null && rs.getString("shipping_id") == null && rs.getString("packing_group") == null) {
              h.put("DOT", "none");
            } else {
              h.put("DOT", rs.getString("proper_shipping_name") + "\n" + rs.getString("hazard_class") + "\n" + rs.getString("shipping_id") + "\n" + rs.getString("packing_group"));
            }
            if (rs.getString("management_option_desc") != null) {
              h.put("MANAGEMENT_OPTION", rs.getString("management_option_desc"));
            } else {
              h.put("MANAGEMENT_OPTION", "none");
            }
            if (rs.getString("company_name") != null) {
              h.put("VENDOR_ID", rs.getString("company_name"));
            } else {
              h.put("VENDOR_ID", "none");
            }
            if (rs.getString("container_id") != null) {
              h.put("CONTAINER_ID", rs.getString("container_id"));
            } else {
              h.put("CONTAINER_ID", "none");
            }

            v.addElement(h);
          }
        } catch (Exception e) {
          e.printStackTrace();
          HelpObjs.monitor(1, "Error object(WasteLabelTraveler): " + e.getMessage(), null);
          throw e;
        } finally {
          dbrs.close();
        }
      }
    }
    return v;
  }

//Query the database and return values
  public static Vector getLabelInfo(TcmISDBModel db, String container_id) throws Exception {
    Vector v = new Vector();
    int count = 0;
    String query = "";
    try {
      query = "select * from waste_traveler_view where container_id=" + container_id;
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();

      while (rs.next()) {
        count++;
        WasteLabelTraveler wt = new WasteLabelTraveler(db);

        if (rs.getString("facility_id") != null) {
          wt.setFacility(rs.getString("facility_id"));
        } else {
          wt.setFacility("none");
        }
        if (rs.getString("traveler_id") != null) {
          wt.setTravelerId(rs.getString("traveler_id"));
        } else {
          wt.setTravelerId("none");
        }
        if (rs.getString("generation_point") != null) {
          wt.setWorkArea(rs.getString("generation_point"));
        } else {
          wt.setWorkArea("none");
        }
        if (rs.getString("vendor_profile_id") != null) {
          wt.setProfile(rs.getString("vendor_profile_id"));
        } else {
          wt.setProfile("none");
        }
        if (rs.getString("description") != null) {
          wt.setDescription(rs.getString("description"));
        } else {
          wt.setDescription("none");
        }
        if (rs.getString("proper_shipping_name") == null && rs.getString("hazard_class") == null && rs.getString("shipping_id") == null && rs.getString("packing_group") == null) {
          wt.setDot("none");
        } else {
          wt.setDot(rs.getString("proper_shipping_name") + "\n" + rs.getString("hazard_class") + "\n" + rs.getString("shipping_id") + "\n" + rs.getString("packing_group"));
        }
        if (rs.getString("management_option_desc") != null) {
          wt.setManagementOption(rs.getString("management_option_desc"));
        } else {
          wt.setManagementOption("none");
        }
        if (rs.getString("company_name") != null) {
          wt.setVendorId(rs.getString("company_name"));
        } else {
          wt.setVendorId("none");
        }

        v.addElement(wt);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(WasteLabelTraveler): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return v;
  }
}
