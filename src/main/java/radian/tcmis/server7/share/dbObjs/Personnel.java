package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
import oracle.jdbc.OracleTypes;
import java.math.BigDecimal;

//Raj added code to add password to personnel 10/25/2001

public class Personnel {
  protected TcmISDBModel db;
  protected Integer personnel_id = null;
  protected String mail_location = null;
  protected String facility_id = null;
  protected String first_name = null;
  protected String last_name = null;
  protected String mid_initial = null;
  protected String phone = null;
  protected String alt_phone = null;
  protected String pager = null;
  protected String email = null;
  protected String shipping_location = null;
  protected String fax = null;
  protected String proxy = null;
  protected String port = null;
  protected Vector groups = null;
  protected Vector releasers = null;
  protected Hashtable approvers = null;
  protected Vector preffacs = null;
  protected String userLogon = null;
  protected String prefAccSysId = null;
  protected String password = null;

  public Personnel() throws Exception {

  }

  public Personnel(TcmISDBModel db) throws Exception {
    this.db = db;
  }

  public void setDB(TcmISDBModel db) {
    this.db = db;
  }

  public String deleteUser(Integer personnelId) {
    String result = "";
    ResultSet rs = null;
    Connection connect1 = null;
    CallableStatement cs = null;
    try {
      connect1 = db.getConnection();
      cs = connect1.prepareCall("{call P_SET_USER_COMPANY(?,?,?,?,?)}");
      cs.setString(1, null);
      cs.setString(2, personnelId.toString());
      cs.setString(3, "None");
      cs.setString(4, "-1");
      cs.registerOutParameter(5, OracleTypes.VARCHAR);
      cs.execute();
      result = (String) cs.getObject(5);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db, "Error occured while trying to access 'p_set_user_company'", "user: " +personnelId, 86030, false);
    } finally {
      try {
        cs.close();
      } catch (Exception ee) {
        ee.printStackTrace();
      }
    }
    return result;
  } //end of method


  public String[] createNewUser(Hashtable inData) {
    String[] result = new String[2];
    ResultSet rs = null;
    Connection connect1 = null;
    CallableStatement cs = null;
    try {
      connect1 = db.getConnection();
      cs = connect1.prepareCall("{call P_PERSONNEL_CREATE(?,?,?,?,?,?,?)}");
      cs.setString(1, null);
      cs.setString(2, inData.get("LOGON_ID").toString());
      cs.setString(3, inData.get("EMAIL").toString());
      cs.setString(4, inData.get("PREF_FACILITY_ID").toString());
      cs.setString(5, "-1");
      cs.registerOutParameter(6, OracleTypes.NUMBER);
      cs.registerOutParameter(7, OracleTypes.VARCHAR);
      cs.execute();
      BigDecimal personnelId = (BigDecimal) cs.getObject(6);
      String message = (String) cs.getObject(7);
      if (personnelId != null && "OK".equalsIgnoreCase(message)) {
        inData.put("USER_ID", new Integer(personnelId.intValue()));
        String updateMessage = updateUserPersonInfo(inData,false);
        result[0] = personnelId.toString();
        result[1] = updateMessage;
        setCompanyApplicationLogonForSeagate(personnelId,inData.get("LOGON_ID").toString());
      } else {
        result[0] = "0";
        result[1] = message;
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db, "Error occured while trying to access 'p_personnel_create'", "data: " + inData, 86030, false);
    } finally {
      try {
        cs.close();
      } catch (Exception ee) {
        ee.printStackTrace();
      }
    }
    return result;
  } //end of method

  private void setCompanyApplicationLogonForSeagate(BigDecimal personnelId, String logonId) {
      try {
        if ("SEAGATE".equalsIgnoreCase(db.getClient())) {
            StringBuilder query = new StringBuilder("insert into company_application_logon (company_id,company_application_logon_id,application,personnel_id)");
            query.append(" values ('SEAGATE','").append(logonId).append("','iProcurement',").append(personnelId).append(")");
            db.doUpdate(query.toString());
        }
      }catch (Exception e) {
          e.printStackTrace();
          HelpObjs.sendMail(db,"Set company application logon from allthecode failed","personnel_id: "+personnelId+" - logon:"+logonId,86030,false);
      }
  }

  public String updateUserPersonInfo(Hashtable inData, boolean onlyUpdateColumnsWithData) {
    String result = "";
    ResultSet rs = null;
    Connection connect1 = null;
    CallableStatement cs = null;
    try {
      Integer personnelId = (Integer) inData.get("USER_ID");
      String loginId = null;
      String email = null;
      String password = null;
      String firstName = null;
      String lastName = null;
      String mid = null;
      String phone = null;
      String altPhone = null;
      String pager = null;
      String fax = null;
      String facilityId = null;
      String application = null;
      String status = null;
      String printerLocation = null;
      String tmp = "";

      //onlyUpdateColumnsWithData
      //i.e. if called from change password don't set empty columns to null since it is not being pass in.
      //otherwiser, if pass in data are empty then set to null
      if (onlyUpdateColumnsWithData) {
        tmp = (String) inData.get("LOGON_ID");
        if (!BothHelpObjs.isBlankString(tmp)) {
          loginId = HelpObjs.singleQuoteToDouble(tmp.trim());
        }
        tmp = (String) inData.get("EMAIL");
        if (!BothHelpObjs.isBlankString(tmp)) {
          email = HelpObjs.singleQuoteToDouble(tmp.trim());
        }
        tmp = (String) inData.get("PASSWORD");
        if (!BothHelpObjs.isBlankString(tmp)) {
          password = tmp;
        }
        tmp = (String) inData.get("FIRST_NAME");
        if (!BothHelpObjs.isBlankString(tmp)) {
          firstName = HelpObjs.singleQuoteToDouble(tmp.trim());
        }
        tmp = (String) inData.get("LAST_NAME");
        if (!BothHelpObjs.isBlankString(tmp)) {
          lastName = HelpObjs.singleQuoteToDouble(tmp.trim());
        }
        tmp = (String) inData.get("MI");
        if (!BothHelpObjs.isBlankString(tmp)) {
          mid = HelpObjs.singleQuoteToDouble(tmp.trim());
        }
        tmp = (String) inData.get("PHONE");
        if (!BothHelpObjs.isBlankString(tmp)) {
          phone = HelpObjs.singleQuoteToDouble(tmp.trim());
        }
        tmp = (String) inData.get("ALT_PHONE");
        if (!BothHelpObjs.isBlankString(tmp)) {
          altPhone = HelpObjs.singleQuoteToDouble(tmp.trim());
        }
        tmp = (String) inData.get("PAGER");
        if (!BothHelpObjs.isBlankString(tmp)) {
          pager = HelpObjs.singleQuoteToDouble(tmp.trim());
        }
        tmp = (String) inData.get("FAX");
        if (!BothHelpObjs.isBlankString(tmp)) {
          fax = HelpObjs.singleQuoteToDouble(tmp.trim());
        }
        tmp = (String) inData.get("PRINTER_LOCATION");
        if (!BothHelpObjs.isBlankString(tmp)) {
          printerLocation = tmp;
        }
        tmp = (String) inData.get("PREF_FACILITY_ID");
        if (!BothHelpObjs.isBlankString(tmp)) {
          facilityId = tmp;
        }
        tmp = (String) inData.get("PREF_APPLICATION");
        if (!BothHelpObjs.isBlankString(tmp)) {
          application = tmp;
        }
        tmp = (String) inData.get("STATUS");
        if (!BothHelpObjs.isBlankString(tmp)) {
          status = tmp;
        }else {
          status = "A";
        }
      }else {
        tmp = (String) inData.get("LOGON_ID");
        if (!BothHelpObjs.isBlankString(tmp)) {
          loginId = HelpObjs.singleQuoteToDouble(tmp.trim());
        }
        tmp = (String) inData.get("EMAIL");
        if (!BothHelpObjs.isBlankString(tmp)) {
          email = HelpObjs.singleQuoteToDouble(tmp.trim());
        }else {
          email = "***delete***";
        }
        tmp = (String) inData.get("PASSWORD");
        if (!BothHelpObjs.isBlankString(tmp)) {
          password = tmp;
        }
        tmp = (String) inData.get("FIRST_NAME");
        if (!BothHelpObjs.isBlankString(tmp)) {
          firstName = HelpObjs.singleQuoteToDouble(tmp.trim());
        }else {
          firstName = "***delete***";
        }
        tmp = (String) inData.get("LAST_NAME");
        if (!BothHelpObjs.isBlankString(tmp)) {
          lastName = HelpObjs.singleQuoteToDouble(tmp.trim());
        }else {
          lastName = "***delete***";
        }
        tmp = (String) inData.get("MI");
        if (!BothHelpObjs.isBlankString(tmp)) {
          mid = HelpObjs.singleQuoteToDouble(tmp.trim());
        }else {
          mid = "***delete***";
        }
        tmp = (String) inData.get("PHONE");
        if (!BothHelpObjs.isBlankString(tmp)) {
          phone = HelpObjs.singleQuoteToDouble(tmp.trim());
        }else {
          phone = "***delete***";
        }
        tmp = (String) inData.get("ALT_PHONE");
        if (!BothHelpObjs.isBlankString(tmp)) {
          altPhone = HelpObjs.singleQuoteToDouble(tmp.trim());
        }else {
          altPhone = "***delete***";
        }
        tmp = (String) inData.get("PAGER");
        if (!BothHelpObjs.isBlankString(tmp)) {
          pager = HelpObjs.singleQuoteToDouble(tmp.trim());
        }else {
          pager = "***delete***";
        }
        tmp = (String) inData.get("FAX");
        if (!BothHelpObjs.isBlankString(tmp)) {
          fax = HelpObjs.singleQuoteToDouble(tmp.trim());
        }else {
          fax = "***delete***";
        }
        tmp = (String) inData.get("PRINTER_LOCATION");
        if (!BothHelpObjs.isBlankString(tmp)) {
          printerLocation = tmp;
        }
        tmp = (String) inData.get("PREF_FACILITY_ID");
        if (!BothHelpObjs.isBlankString(tmp)) {
          facilityId = tmp;
        }
        tmp = (String) inData.get("PREF_APPLICATION");
        if (!BothHelpObjs.isBlankString(tmp)) {
          application = tmp;
        }
      }

      connect1 = db.getConnection();
      //cs = connect1.prepareCall("{call P_PERSONNEL_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
      cs = connect1.prepareCall("{call P_PERSONNEL_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
      cs.setInt(1, personnelId.intValue());
      cs.setString(2, loginId);
      cs.setString(3, email);
      cs.setString(4, password);
      cs.setString(5, firstName);
      cs.setString(6, lastName);
      cs.setString(7, mid);
      cs.setString(8, phone);
      cs.setString(9, altPhone);
      cs.setString(10, pager);
      cs.setString(11, fax);
      cs.setString(12, printerLocation);
      //cs.setString(13, facilityId);
      //cs.setString(14, application);
      //cs.setString(15, status);
      cs.registerOutParameter(13, OracleTypes.VARCHAR);
      cs.setString(14, null);
      cs.setString(15, null);
      cs.setString(16, "Medium");
      cs.setString(17, "Y");
      cs.execute();
      result = (String) cs.getObject(13);
      if ("OK".equals(result)) {
        updateDefaultFacility(personnelId,facilityId);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db, "Error occured while trying to access 'p_personnel_update'", "data: " + inData, 86030, false);
    } finally {
      try {
        cs.close();
      } catch (Exception ee) {
        ee.printStackTrace();
      }
    }
    return result;
  } //end of method

  public void updateDefaultFacility(Integer personnelId, String facilityId) {
    ResultSet rs = null;
    Connection connect1 = null;
    CallableStatement cs = null;
    try {
      connect1 = db.getConnection();
      cs = connect1.prepareCall("{call P_SET_USER_FACILITY(?,?,?,?,?,?,?,?)}");
      cs.setString(1, null);
      cs.setInt(2, personnelId.intValue());
      cs.setString(3, facilityId);
      cs.setString(4, "Y");
      cs.setString(5, "");
      cs.setString(6, "");
      cs.setString(7, "");
      cs.registerOutParameter(8, OracleTypes.VARCHAR);
      cs.execute();
      String result = (String) cs.getObject(8);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db, "Error occured while trying to access 'p_set_user_facility'", "data: " + personnelId.intValue()+" - "+facilityId, 86030, false);
    } finally {
      try {
        cs.close();
      } catch (Exception ee) {
        ee.printStackTrace();
      }
    }

  }

  public void setPersonnelId(int id) {
    this.personnel_id = new Integer(id);
  }

  public Integer getPersonnelId() {
    return personnel_id;
  }

  public void setGroups(Vector g) {
    groups = g;
  }

  public Vector getGroups() {
    return groups;
  }

  public void setReleasers(Vector g) {
    releasers = g;
  }

  public Vector getReleasers() {
    return releasers;
  }

  public void setApprovers(Hashtable g) {
    approvers = g;
  }

  public Hashtable getApprovers() {
    return approvers;
  }

  public void setMailLocation(String loc) {
    this.mail_location = loc;
  }

  public String getMailLocation() {
    return mail_location;
  }

  public void setFacilityId(String id) {
    this.facility_id = id;
  }

  public String getProxy() {
    return proxy;
  }

  public void setProxy(String id) {
    this.proxy = id;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String id) {
    this.port = id;
  }

  public String getFacilityId() {
    return facility_id;
  }

  public void setFirstName(String id) {
    this.first_name = id;
  }

  public String getFirstName() {
    return first_name;
  }

  public void setPassword(String id) {
    this.password = id;
  }

  public String getPassword() {
    return password;
  }

  public String getFullName() {
    String first = getFirstName();
    String middle = getMidInitial();
    String last = getLastName();
    if (middle == null) {
      middle = "";
    }
    if (first == null) {
      first = "";
    }
    if (last == null) {
      last = "";
    }
    if (BothHelpObjs.isBlankString(first + middle + last)) {
      return "";
    }
    String full = last + ", " + first + " " + middle;
    if (middle.length() > 0 && !full.endsWith(".")) {
      full = full + ".";
    }
    return full;
  }

  public void setLastName(String id) {
    this.last_name = id;
  }

  public String getLastName() {
    return last_name;
  }

  public void setMidInitial(String id) {
    this.mid_initial = id;
  }

  public String getMidInitial() {
    return mid_initial;
  }

  public void setPhone(String num) {
    this.phone = num;
  }

  public String getPhone() {
    return phone;
  }

  public void setAltPhone(String num) {
    this.alt_phone = num;
  }

  public String getAltPhone() {
    return alt_phone;
  }

  public void setPager(String id) {
    this.pager = id;
  }

  public String getPager() {
    return pager;
  }

  public void setEmail(String id) {
    this.email = id;
  }

  public String getEmail() {
    return email;
  }

  public String getUserLogon() {
    return userLogon;
  }

  public void setUserLogon(String logon) {
    this.userLogon = logon;
  }

  public void setShippingLocation(String loc) {
    this.shipping_location = loc;
  }

  public String getShippingLocation() {
    return shipping_location;
  }

  public void setFax(String num) {
    this.fax = num;
  }

  public String getFax() {
    return fax;
  }

  public void setPrefAccSysId(String s) {
    this.prefAccSysId = s;
  }

  public String getPrefAccSysId() {
    return prefAccSysId;
  }

  public String setShipArea() throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String retVal = new String("");
    String query = new String("select l.adress_line_1,l.address_line2,l.address_line3,l.city,l.state_abbrev,l.zip from location l,personnel p where l.location_id = p.shipping_location and p.personnel_id = " + personnel_id.toString());
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        retVal = new String(rs.getString(1) + "\n");
        if (rs.getString(2) != (String)null) {
          retVal = retVal + rs.getString(2) + "\n";
        }
        if (rs.getString(3) != (String)null) {
          retVal = retVal + rs.getString(3) + "\n";
        }
        retVal = retVal + rs.getString(4) + ", " + rs.getString(5) + " " + rs.getString(6);
      }

    } catch (Exception e) {
      throw e;
    } finally {
      dbrs.close();

    }
    return retVal;
  }

  public static String getUserName(TcmISDBModel db, String uID) throws Exception {
    String result = "";
    String query = "select last_name||', '||first_name full_name from personnel where personnel_id = " + uID;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        result = BothHelpObjs.makeBlankFromNull(rs.getString("full_name"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
    return result;

  }

  public void load() throws Exception {
    String query = "select * from personnel where personnel_id = " + personnel_id.toString();
    boolean found = false;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      if (rs.next()) {
        found = true;
        this.setPersonnelId( (int) rs.getInt(1));
        this.setMailLocation(rs.getString(2));
        this.setFacilityId(rs.getString(3));
        this.setFirstName(rs.getString(4));
        this.setLastName(rs.getString(5));
        this.setMidInitial(rs.getString(6));
        this.setPhone(rs.getString(7));
        this.setAltPhone(rs.getString(8));
        this.setPager(rs.getString(9));
        this.setEmail(rs.getString(10));
        this.setShippingLocation(rs.getString(11));
        this.setFax(rs.getString(12));
        this.setUserLogon(rs.getString(15));
        this.setPrefAccSysId(BothHelpObjs.makeBlankFromNull(rs.getString("preferred_account_sys_id")));
      }

      if (!found) {
        setPersonnelId(0);
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

//
  public void load(String LogonID) throws Exception {
    String query = "select * from personnel where logon_id  = '" + LogonID + "' ";
    boolean found = false;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      if (rs.next()) {

        found = true;
        this.setPersonnelId( (int) rs.getInt(1));
        this.setMailLocation(rs.getString(2));
        this.setFacilityId(rs.getString(3));
        this.setFirstName(rs.getString(4));
        this.setLastName(rs.getString(5));
        this.setMidInitial(rs.getString(6));
        this.setPhone(rs.getString(7));
        this.setAltPhone(rs.getString(8));
        this.setPager(rs.getString(9));
        this.setEmail(rs.getString(10));
        this.setShippingLocation(rs.getString(11));
        this.setFax(rs.getString(12));
        this.setUserLogon(rs.getString(15));
        this.setPrefAccSysId(BothHelpObjs.makeBlankFromNull(rs.getString("preferred_account_sys_id")));
      }

      if (!found) {
        setPersonnelId(0);
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

//

  public Vector retrieve(String where, String sortBy) throws Exception {
    String query = "select * from personnel ";
    if (where != (String)null) {
      query = query + " where " + where;
    }
    if (sortBy != (String)null) {
      query = query + " sort by " + sortBy;

    }
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector allP = new Vector();
    Personnel p;

    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        p = new Personnel();
        p.setPersonnelId( (int) rs.getInt(1));
        p.setMailLocation(rs.getString(2));
        p.setFacilityId(rs.getString(3));
        p.setFirstName(rs.getString(4));
        p.setLastName(rs.getString(5));
        p.setMidInitial(rs.getString(6));
        p.setPhone(rs.getString(7));
        p.setAltPhone(rs.getString(8));
        p.setPager(rs.getString(9));
        p.setEmail(rs.getString(10));
        p.setShippingLocation(rs.getString(11));
        p.setFax(rs.getString(12));
        p.setUserLogon(rs.getString("logon_id"));
        allP.addElement(p);
      }
      allP.trimToSize();

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return allP;
  }

  public Vector getNextName(String type, String fac, String lastNameIn) throws Exception {
    String lastName = HelpObjs.singleQuoteToDouble(lastNameIn);

    // passing last, first
    String last = null;
    String first = null;
    int id = 0;
    Vector result = new Vector();
    String query = null;
    int comma = lastName.indexOf(",");
    if (comma > 0) {
      last = lastName.substring(0, lastName.indexOf(",")).trim();
      //if (comma+2 >= lastName.length()){
      first = lastName.substring(comma + 1).trim();
      //}
    } else {
      last = lastName.trim();
    }

    if (type.equals("APPROVERS")) {
      query = "select p.last_name,p.first_name,p.personnel_id from finance_approver_list f,personnel p where f.facility_id = '" + fac + "' and f.personnel_id = p.personnel_id";
      if (first == null) {
        query = query + " and lower(p.last_name) like lower('" + last + "%')";
      } else {
        query = query + " and lower(p.last_name) = lower('" + last + "') and lower(p.first_name) like lower('" + first + "%')";
      }
    } else if (type.equals("RELEASERS")) {

      query = "select p.last_name,p.first_name,p.personnel_id from user_group_member r,personnel p where r.facility_id = '" + fac + "' and r.personnel_id = p.personnel_id and r.user_group_id = 'Releaser'";
      if (first == null) {
        query = query + " and lower(p.last_name) like lower('" + last + "%')";
      } else {
        query = query + " and lower(p.last_name) = lower('" + last + "') and lower(p.first_name) like lower('" + first + "%')";
      }
    } else if (type.equals("PERSONNEL")) {
      query = "select p.last_name,p.first_name,p.personnel_id from personnel p where ";
      if (fac.length() > 1) {
        query = query + " p.facility_id = '" + fac + "'";
      } else {
        query = query + " p.personnel_id > 0";
      }
      if (first == null) {
        query = query + " and lower(p.last_name) like lower('" + last + "%')";
      } else {
        query = query + " and lower(p.last_name) = lower('" + last + "') and lower(p.first_name) like lower('" + first + "%')";
      }
    } else if (type.equals("NEW_CHEM_APPROVERS")) {
      query = "select p.last_name,p.first_name,p.personnel_id from personnel p, chemical_approver u where ";
      query = query + " p.personnel_id > 0";

      if (first == null) {
        query = query + " and lower(p.last_name) like lower('" + last + "%')";
      } else {
        query = query + " and lower(p.last_name) = lower('" + last + "') and lower(p.first_name) like lower('" + first + "%')";
      }
      query = query + " and p.personnel_id = u.personnel_id";
    } else {
      return null;
    }
    query = query + " order by p.last_name";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        last = rs.getString(1);
        first = rs.getString(2);
        id = (int) rs.getInt(3);
        break;
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }

    if (last == null || last.length() == 0 || first == null || first.length() == 0 || id == 0) {

      return null;
    }
    result.addElement(last);
    result.addElement(first);
    result.addElement(new Integer(id));

    return result;
  }

  public Object[][] getAllNames(String type, String fac, String textIn, String searchBy) throws Exception {
    String text = HelpObjs.singleQuoteToDouble(textIn);

    Object[][] data = null;

    String last = null;
    String first = null;
    int id = 0;
    Vector result = new Vector();
    String query = null;

    if (type.equals("APPROVERS")) {
      query = "select p.last_name,p.first_name,p.personnel_id from finance_approver_list f,personnel p where f.facility_id = '" + fac + "' and f.personnel_id = p.personnel_id";
    } else if (type.equals("RELEASERS")) {
      query = "select p.last_name,p.first_name,p.personnel_id from user_group_member r,personnel p where r.facility_id = '" + fac + "' and r.personnel_id = p.personnel_id and r.user_group_id = 'Releaser'";
    } else if (type.equals("PERSONNEL")) {
      query = "select p.last_name,p.first_name,p.personnel_id from personnel p where p.personnel_id > 0 ";
    } else if (type.equals("NEW_CHEM_APPROVERS")) {
      query = "select distinct p.last_name,p.first_name,p.personnel_id from personnel p, chemical_approver u where p.personnel_id = u.personnel_id and p.personnel_id > 0";
    } else {
      return null;
    }
    if (searchBy.equals("LASTNAME")) {
      query = query + " and lower(p.last_name) like lower('" + text + "%')";
    } else if (searchBy.equals("FIRSTNAME")) {
      query = query + " and lower(p.first_name) like lower('" + text + "%')";
    } else if (searchBy.equals("ID")) {
      query = query + " and lower(p.personnel_id) like lower('" + text + "%')";
    }
    query = query + " order by last_name";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector tempV = new Vector();
    Object[] tempO;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        tempO = null;
        tempO = new Object[3];
        tempO[0] = new Integer( (int) rs.getInt(3));
        tempO[1] = rs.getString(2);
        tempO[2] = rs.getString(1);
        tempV.addElement(tempO);
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }

    data = new Object[tempV.size()][3];
    for (int i = 0; i < tempV.size(); i++) {
      tempO = null;
      tempO = new Object[3];
      tempO = (Object[]) tempV.elementAt(i);
      data[i][0] = tempO[0];
      data[i][1] = tempO[1];
      data[i][2] = tempO[2];
    }
    return data;
  }

  //11-07-01 create this new method coz I don't want to break anything else
  //this method will deal with logon_id instead of personnel_id -  search personnel dlg (userprofile - lookup action)
  public Object[][] getAllNamesNew(String type, String fac, String textIn, String searchBy) throws Exception {
    String text = HelpObjs.singleQuoteToDouble(textIn);

    Object[][] data = null;

    String last = null;
    String first = null;
    int id = 0;
    Vector result = new Vector();
    String query = null;

    if (type.equals("APPROVERS")) {
      query = "select p.logon_id,p.last_name,p.first_name,p.personnel_id,nvl(p.phone,' ') phone,nvl(p.email,' ') email from finance_approver_list f,personnel p where f.facility_id = '" + fac + "' and f.personnel_id = p.personnel_id";
    } else if (type.equals("RELEASERS")) {
      query = "select p.logon_id,p.last_name,p.first_name,p.personnel_id,nvl(p.phone,' ') phone,nvl(p.email,' ') email from user_group_member r,personnel p where r.facility_id = '" + fac + "' and r.personnel_id = p.personnel_id and r.user_group_id = 'Releaser'";
    } else if (type.equals("PERSONNEL")) {
      query = "select p.logon_id,p.last_name,p.first_name,p.personnel_id,nvl(p.phone,' ') phone,nvl(p.email,' ') email from personnel p where p.personnel_id > 0 ";
    } else if (type.equals("NEW_CHEM_APPROVERS")) {
      query = "select distinct p.logon_id,p.last_name,p.first_name,p.personnel_id,nvl(p.phone,' ') phone,nvl(p.email,' ') email from personnel p, chemical_approver u where p.personnel_id = u.personnel_id and p.personnel_id > 0";
    } else {
      return null;
    }
    if (searchBy.equals("LASTNAME")) {
      query = query + " and lower(p.last_name) like lower('" + text + "%')";
    } else if (searchBy.equals("FIRSTNAME")) {
      query = query + " and lower(p.first_name) like lower('" + text + "%')";
    } else if (searchBy.equals("ID")) {
      query = query + " and lower(p.logon_id) like lower('" + text + "%')";
    }
    query = query + " order by last_name";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector tempV = new Vector();
    Object[] tempO;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        tempO = null;
        tempO = new Object[6];
        tempO[0] = new Integer( (int) rs.getInt("personnel_id"));
        tempO[1] = rs.getString("logon_id");
        tempO[2] = rs.getString("first_name");
        tempO[3] = rs.getString("last_name");
        tempO[4] = rs.getString("phone");
        tempO[5] = rs.getString("email");
        tempV.addElement(tempO);
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }

    data = new Object[tempV.size()][6];
    for (int i = 0; i < tempV.size(); i++) {
      tempO = null;
      tempO = new Object[6];
      tempO = (Object[]) tempV.elementAt(i);
      data[i][0] = tempO[0];
      data[i][1] = tempO[1];
      data[i][2] = tempO[2];
      data[i][3] = tempO[3];
      data[i][4] = tempO[4];
      data[i][5] = tempO[5];
    }
    return data;
  }

  public Vector getNameSearch(Integer userID, String fac, String textIn, String searchBy) throws Exception {
    String text = HelpObjs.singleQuoteToDouble(textIn);
    Object[][] data = null;
    String last = null;
    String first = null;
    int id = 0;
    Vector result = new Vector();
    String query = "";
    if ("All".equalsIgnoreCase(fac)) {
      query = "select distinct p.logon_id,p.last_name,p.first_name,p.personnel_id,nvl(p.phone,' ') phone,nvl(p.email,' ') email from personnel p,fac_pref fp,fac_pref fp2" +
          " where fp.facility_id = fp2.facility_id and fp2.personnel_id = p.personnel_id and fp.personnel_id = " + userID;
    } else {
      query = "select distinct p.logon_id,p.last_name,p.first_name,p.personnel_id,nvl(p.phone,' ') phone,nvl(p.email,' ') email from personnel p,fac_pref fp" +
          " where p.personnel_id = fp.personnel_id and fp.facility_id = '" + fac + "'";
    }

    if (searchBy.equals("LASTNAME")) {
      query = query + " and lower(p.last_name) like lower('" + text + "%')";
    } else if (searchBy.equals("FIRSTNAME")) {
      query = query + " and lower(p.first_name) like lower('" + text + "%')";
    } else if (searchBy.equals("ID")) {
      query = query + " and lower(p.logon_id) like lower('" + text + "%')";
    }
    query = query + " order by last_name";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector tempV = new Vector();
    Object[] tempO;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        tempO = null;
        tempO = new Object[6];
        tempO[0] = new Integer( (int) rs.getInt("personnel_id"));
        tempO[1] = rs.getString("logon_id");
        tempO[2] = rs.getString("first_name");
        tempO[3] = rs.getString("last_name");
        tempO[4] = rs.getString("phone");
        tempO[5] = rs.getString("email");
        tempV.addElement(tempO);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }

    data = new Object[tempV.size()][6];
    for (int i = 0; i < tempV.size(); i++) {
      tempO = null;
      tempO = new Object[6];
      tempO = (Object[]) tempV.elementAt(i);
      data[i][0] = tempO[0];
      data[i][1] = tempO[1];
      data[i][2] = tempO[2];
      data[i][3] = tempO[3];
      data[i][4] = tempO[4];
      data[i][5] = tempO[5];
    }

    return (BothHelpObjs.getVectorFrom2DArray(data));
  }

  public Vector getUserGroups() throws Exception {
    /*String query = "select * from user_group_member";
           query = query + " where personnel_id = " + personnel_id.toString();
     */
    //trong 4/10
    String query = "select a.facility_id ,a.user_group_id, b.user_group_desc from ";
    query += "user_group_member a, user_group b where a.facility_id = b.facility_id ";
    query += "and a.user_group_id = b.user_group_id and a.personnel_id = " + personnel_id.toString();

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector allG = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        UserGroup ug = new UserGroup(db);
        ug.setGroupId(rs.getString("user_group_id"));
        ug.setGroupFacId(rs.getString("facility_id"));
        ug.setGroupDesc(BothHelpObjs.makeBlankFromNull(rs.getString("user_group_desc")));
        allG.addElement(ug);
      }
      allG.trimToSize();
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return allG;
  }

  public Vector getDefaultFac() throws Exception {
    String query = "select f.facility_id, f.facility_name from facility f, personnel p where p.personnel_id = " + personnel_id.toString() + " and f.facility_id = p.facility_id";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector res = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        res.addElement(rs.getString(1));
        res.addElement(rs.getString(2));
        break;
      }

      res.trimToSize();
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return res;
  }

  public Hashtable getAllApprovalGroupsHash(String textIn, String searchBy) throws Exception {
    String text = HelpObjs.singleQuoteToDouble(textIn);
    Hashtable data = new Hashtable();

    //making 'All' to be the first user_group_id
    String query = "select USER_GROUP_ID,USER_GROUP_DESC,FACILITY_ID";
    query += " from approval_user_group";
    String where = "";
    if (searchBy.equals("GROUP_ID")) {
      where += "lower(user_group_id) like lower('" + text + "%')";
    } else if (searchBy.equals("GROUP_DESC")) {
      where += "lower(user_group_desc) like lower('%" + text + "%')";
    }
    if (where.length() > 0) {
      query += " where "+where;
    }
    query += " order by facility_id,decode(user_group_id, 'All', '!', upper(user_group_id))";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      Vector result = new Vector();
      String lstFac = null;
      while (rs.next()) {
        String fac = rs.getString("FACILITY_ID");
        if (lstFac != null && !fac.equals(lstFac)) {
          data.put(lstFac, result);
          result = new Vector();
        }
        Hashtable h = new Hashtable();
        h.put("ID", rs.getString("user_group_id"));
        h.put("DESC", rs.getString("user_group_desc"));
        result.addElement(h);
        lstFac = fac;
      }
      data.put(lstFac, result); //last one

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }

    // Organize facs
    if (!data.containsKey("All")) {
      Hashtable h = new Hashtable();
      h.put("ID", "All");
      h.put("DESC", "Approved to all users");
      Vector v = new Vector();
      v.addElement(h);
      data.put("All", v);

    }

    return data;
  }

  public Object[][] getAllApprovalGroups(String searchBy, String text) throws Exception {
    Object[][] data = null;

    Vector result = new Vector();
    String query = "select * from user_group";
    query = query + " where USER_GROUP_TYPE = 'Approval' ";
    if (searchBy.equals("GROUP_ID")) {
      query = query + " and lower(user_group_id) like lower('" + text + "%')";
    } else if (searchBy.equals("GROUP_DESC")) {
      query = query + " and lower(user_group_desc) like lower('%" + text + "%')";
    }
    query = query + " order by USER_GROUP_ID";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector tempV = new Vector();
    Object[] tempO;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        tempO = null;
        tempO = new Object[3];
        tempO[0] = rs.getString("user_group_id");
        tempO[1] = rs.getString("user_group_desc");
        tempO[2] = rs.getString("facility_id");
        tempV.addElement(tempO);
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }

    data = new Object[tempV.size()][3];
    for (int i = 0; i < tempV.size(); i++) {
      tempO = null;
      tempO = new Object[3];
      tempO = (Object[]) tempV.elementAt(i);
      data[i][0] = tempO[0];
      data[i][1] = tempO[1];
      data[i][2] = tempO[2];
    }
    return data;
  }

  public Vector getAllGroupsVector(String textIn, String searchBy) throws Exception {
    String text = HelpObjs.singleQuoteToDouble(textIn);
    Vector data = null;

    Hashtable result = new Hashtable();
    String query = "select * from user_group";
    if (searchBy.equals("GROUP_ID")) {
      query = query + " where lower(user_group_id) like lower('%" + text + "%')";
    } else if (searchBy.equals("GROUP_DESC")) {
      query = query + " where lower(user_group_desc) like lower('%" + text + "%')";
    }
    query = query + " order by USER_GROUP_ID";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        result = new Hashtable();
        result.put("ID", rs.getString("user_group_id"));
        result.put("DESC", rs.getString("user_group_desc"));
        result.put("FAC_ID", rs.getString("facility_id"));
        data.addElement(result);
      }
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return data;
  }

  public Object[][] getAllGroups(String textIn, String searchBy) throws Exception {
    String text = HelpObjs.singleQuoteToDouble(textIn);
    Object[][] data = null;

    Vector result = new Vector();
    String query = "select * from user_group";
    if (searchBy.equals("GROUP_ID")) {
      query = query + " where lower(user_group_id) like lower('%" + text + "%')";
    } else if (searchBy.equals("GROUP_DESC")) {
      query = query + " where lower(user_group_desc) like lower('%" + text + "%')";
    }
    query = query + " order by USER_GROUP_ID";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector tempV = new Vector();
    Object[] tempO;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        tempO = null;
        tempO = new Object[3];
        tempO[0] = rs.getString("user_group_id");
        tempO[1] = rs.getString("user_group_desc");
        tempO[2] = rs.getString("facility_id");
        tempV.addElement(tempO);
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }

    data = new Object[tempV.size()][3];
    for (int i = 0; i < tempV.size(); i++) {
      tempO = null;
      tempO = new Object[2];
      tempO = (Object[]) tempV.elementAt(i);
      data[i][0] = tempO[0];
      data[i][1] = tempO[1];
      data[i][2] = tempO[2];
    }
    return data;
  }

  //trong 10-19-00 code for realm
  public Enumeration getAllGroupsEnum() throws Exception {
    String query = "select user_group_id from user_group";
    query = query + " order by user_group_id";
    Hashtable result = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      int i = 0;
      while (rs.next()) {
        String name = rs.getString("user_group_id");
        if (name == null || BothHelpObjs.isBlankString(name)) {
          continue;
        }
        result.put(new Integer(i++), name);
      }
      dbrs.close();
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      rs.close();
      throw e;
    }

    return result.elements();
  }

  public static boolean isLogonOnGroup(TcmISDBModel db, String logon, String grp) throws Exception {
    String query = "select count(*) from personnel p, user_group_member g ";
    query = query + " where g.user_group_id = '" + grp + "' and ";
    query = query + " g.personnel_id = p.personnel_id   and ";
    query = query + " p.logon_id ='" + logon + "' ";
    Hashtable result = new Hashtable();

    int i = 0;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        i++;
      }
      dbrs.close();
    } catch (Exception e) {
      e.printStackTrace();
      rs.close();
      throw e;
    }
    return i > 0;
  }

  public static boolean insertOnGroup(TcmISDBModel db, String grp, String logon) {
    String query = "insert into user_group_member ";
    query = query + " select '" + grp + "', personnel_id  from personnel where logon_id ='" + logon + "'";

    DBResultSet dbrs = null;
    try {
      dbrs = db.doQuery(query);
      dbrs = db.doQuery("commit");
      dbrs.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean removeFromGroup(TcmISDBModel db, String grp, String logon) {
    String query = "delete from user_group_member ";
    query = query + " where personnel_id in ";
    query = query + " (select a.personnel_id from user_group_member a, personnel b ";
    query = query + " where a.personnel_id = b.personnel_id and b.logon_id = '" + logon + "' ) ";
    query = query + " and   user_group_id ='" + grp + "'";

    DBResultSet dbrs = null;
    try {
      dbrs = db.doQuery(query);
      dbrs = db.doQuery("commit");
      dbrs.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static Enumeration getAllLogonForGroup(TcmISDBModel db, String grp) throws Exception {
    String query = "select logon_id from personnel p, user_group_member g ";
    query = query + " where g.user_group_id = '" + grp + "' and ";
    query = query + " g.personnel_id = p.personnel_id ";
    Hashtable result = new Hashtable();

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      int i = 0;
      while (rs.next()) {
        String name = rs.getString(1);
        if (name == null || BothHelpObjs.isBlankString(name)) {
          continue;
        }
        result.put(new Integer(i++), name);
      }
      dbrs.close();
    } catch (Exception e) {
      e.printStackTrace();
      rs.close();
      throw e;
    }
    return (result.isEmpty() ? null : result.elements());
  }

  public boolean isApprover() throws Exception {
    String query = "select count(*) from finance_approver_list";
    query = query + " where personnel_id = " + personnel_id.toString();
    query = query + " and facility_id = '" + facility_id + "'";

    int i = 0;
    try {
      i = DbHelpers.countQuery(db, query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return i != 0;
  }

  public Double getApproverAmt() throws Exception {
    String query = "select cost_limit from finance_approver_list";
    query = query + " where personnel_id = " + personnel_id.toString();
    query = query + " and facility_id = '" + facility_id + "'";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    double i = 0.0;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        i = (double) rs.getFloat(1);
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return new Double(i);
  }

  public boolean isReleaser() throws Exception {
    String query = "select count(*) from user_group_member";
    query = query + " where personnel_id = " + personnel_id.toString();
    query = query + " and facility_id = '" + facility_id + "' and user_group_id = 'Releaser'";

    int i = 0;
    try {
      i = DbHelpers.countQuery(db, query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return i != 0;
  }

  public boolean isAlternateRequestor(int prNumber) throws Exception {
    String query = "select count(*) from purchase_request_alt_requestor p, purchase_request pr"+
                   " where p.alternate_requestor = "+personnel_id.toString()+" and p.facility_id = 'All'"+
                   " and p.requestor = pr.requestor and pr.pr_number = "+prNumber;
    int i = 0;
    try {
      i = DbHelpers.countQuery(db, query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return i != 0;
  }


  public Vector getReleaserFacs() throws Exception {
    String query = "select facility_id from user_group_member";
    query = query + " where user_group_id = 'Releaser' and personnel_id = " + personnel_id.toString();

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector result = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        result.addElement(rs.getString(1));
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return result;
  }

  public Hashtable getApproverFacsAndAmt() throws Exception {
    String query = "select facility_id, cost_limit from finance_approver_list ";
    query = query + " where personnel_id = " + personnel_id.toString();

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        result.put(rs.getString(1), new Double(rs.getDouble(2)));
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return result;
  }

  public Hashtable getRelatedFacilities() throws Exception {

    //RELEASER
    String query = "select facility_id from user_group_member ";
    query = query + " where user_group_id = 'Releaser' and personnel_id = " + personnel_id.toString();

    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable facXref = new Hashtable();

    Vector relFac = new Vector();
    Vector appFac = new Vector();
    Vector prefFac = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        relFac.addElement(rs.getString(1));
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }

    //APPROVER
    query = new String();
    query = "select facility_id from finance_approver_list ";
    query = query + " where personnel_id = " + personnel_id.toString();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        appFac.addElement(rs.getString(1));
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }

    //PREFERRED
    query = new String();
    query = "select facility_id from fac_pref ";
    query = query + " where personnel_id = " + personnel_id.toString();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        prefFac.addElement(rs.getString(1));
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }

    // Add all eliminating dups
    Vector finalFac = new Vector();
    for (int i = 0; i < prefFac.size(); i++) {
      String testfac = (String) prefFac.elementAt(i);
      finalFac.addElement(testfac);
    }
    for (int i = 0; i < relFac.size(); i++) {
      String testfac = (String) relFac.elementAt(i);
      if (!finalFac.contains(testfac)) {
        finalFac.addElement(testfac);
      }
    }
    for (int i = 0; i < appFac.size(); i++) {
      String testfac = (String) appFac.elementAt(i);
      if (!finalFac.contains(testfac)) {
        finalFac.addElement(testfac);
      }
    }

    Facility fac = null;
    try {
      fac = new Facility(db);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }

    for (int i = 0; i < finalFac.size(); i++) {
      String testfac = new String( (String) finalFac.elementAt(i));
      fac.setFacilityId(testfac);
      fac.load();
      if (fac.getFacilityName() != (String)null && !testfac.equals("Salem")) {
        //facXref.put(fac.getFacilityName(),testfac);
        facXref.put(fac.getFacilityId(), testfac); //11-17-01
      }
    }

    if (this.getFacilityId() != null && !finalFac.contains(this.getFacilityId())) {
      String testfac = new String(this.getFacilityId());
      fac.setFacilityId(testfac);
      fac.load();
      if (fac.getFacilityName() != (String)null && !testfac.equals("Salem")) {
        //facXref.put(fac.getFacilityName(),testfac);
        facXref.put(fac.getFacilityId(), testfac); //11-17-01
      }
    }
    //System.out.println("\n\n---- my facXref: "+facXref);
    return facXref;
  }

  public boolean update() throws Exception {
    String query = null;
    if (personnel_id == null) {
      return false;
    }

    //bad code query = "update personnel set  personnel_id = " + personnel_id+",";
    query = "update personnel set";
    if (mail_location != null) {
      query = query + " mail_location = '" + mail_location + "',";
    }
    if (facility_id != null) {
      query = query + " facility_id = '" + facility_id + "',";
    }
    if (first_name != null) {
      query = query + " first_name = '" + HelpObjs.singleQuoteToDouble(first_name) + "',";
    }
    if (last_name != null) {
      query = query + " last_name = '" + HelpObjs.singleQuoteToDouble(last_name) + "',";
    }
    if (phone != null) {
      query = query + " phone = '" + HelpObjs.singleQuoteToDouble(phone) + "',";
    }
    if (alt_phone != null) {
      query = query + " alt_phone = '" + HelpObjs.singleQuoteToDouble(alt_phone) + "',";
    }
    if (mid_initial != null) {
      query = query + " mid_initial = '" + HelpObjs.singleQuoteToDouble(mid_initial) + "',";
    }
    if (pager != null) {
      query = query + " pager = '" + HelpObjs.singleQuoteToDouble(pager) + "',";
    }
    if (email != null) {
      query = query + " email = '" + HelpObjs.singleQuoteToDouble(email) + "',";
    }
    if (shipping_location != null) {
      query = query + " shipping_location = '" + shipping_location + "',";
    }
    if (fax != null) {
      query = query + " fax = '" + HelpObjs.singleQuoteToDouble(fax) + "',";
    }
    if (proxy != null) {
      query = query + " proxy_name = '" + HelpObjs.singleQuoteToDouble(proxy) + "',";
    }
    if (port != null) {
      query = query + " proxy_port = '" + HelpObjs.singleQuoteToDouble(port) + "',";
    }
    if (password != null) {
      query = query + " password = '" + HelpObjs.singleQuoteToDouble(password) + "',";
      //if (userLogon != null) query = query + " logon_id = lower('"+HelpObjs.singleQuoteToDouble(userLogon)+"',)";

      //removing the last commas
      //in case that logon_id is in query
      //System.out.println("\n\n--------- user profile update: "+query);
      /*
             if (query.indexOf(")") > 0 ) {
        query = query.substring(0,query.length()-2);
        query += ")";
             }else {
        query = query.substring(0,query.length()-1);
             } */
    }
    if (query.length() > 0) {
      query = query.substring(0, query.length() - 1);
    }

    query = query + " where personnel_id = " + personnel_id;

    //System.out.println("\n\n--------- user profile update: " + query);

    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
    return true;
  }

  public boolean insert() throws Exception {
    if (personnel_id == null) {
      return false;
    }

    String query = new String("insert into personnel (personnel_id,facility_id) values (" + personnel_id + ",'" + facility_id + "')");

    try {
      db.doUpdate(query);

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
    return true;
  }

  //5-15-01 since we are combing customer together, we can no longer create personnel record without logon_id
  public boolean insert2() throws Exception {
    if (personnel_id == null) {
      return false;
    }

    String query = new String("insert into personnel (personnel_id,facility_id,logon_id) values (" + personnel_id + ",'" + facility_id + "',lower('" + HelpObjs.singleQuoteToDouble(userLogon) + "'))");
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
    return true;
  }

  public boolean insert3() throws Exception {
    if (personnel_id == null) {
      return false;
    }
    String query = new String("insert into personnel (personnel_id, password_expire_date, facility_id,logon_id) values (" + personnel_id + ", SYSDATE, '" + facility_id + "',lower('" + HelpObjs.singleQuoteToDouble(userLogon) + "'))");
    //System.out.println("\n---- insert query : " + query);
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }
    return true;
  }

  public boolean updateGroups() throws Exception {
    String query = null;
    if (personnel_id == null) {
      return false;
    }
    query = new String("delete user_group_member where personnel_id =" + personnel_id);
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }

    if (groups.size() == 1 && ( (String) groups.elementAt(0)).equalsIgnoreCase("")) {
      return true;
    }

    for (int i = 0; i < groups.size(); i++) {
      UserGroup ug = (UserGroup) groups.elementAt(i);
      query = new String("insert into user_group_member  (personnel_id,user_group_id,facility_id) values ");
      query = query + " (" + personnel_id;
      query = query + " ,'" + ug.getGroupId() + "','" + ug.getGroupFacId() + "')";
      try {
        db.doUpdate(query);

      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
        throw e;
      }
    }
    return true;
  }

  public boolean updateReleaserFacs() throws Exception {
    String query = null;
    if (personnel_id == null) {
      return false;
    }

    query = new String("delete user_group_member where user_group_id = 'Releaser' and personnel_id =" + personnel_id);
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }

    for (int i = 0; i < releasers.size(); i++) {
      query = new String("insert into user_group_member (personnel_id,facility_id,user_group_id) values");
      query = query + " (" + personnel_id;
      query = query + " ,'" + (String) releasers.elementAt(i) + "','Releaser')";
      try {
        db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
        throw e;
      }
    }
    return true;
  }

  public boolean updateApproverList() throws Exception {
    // get facilities where is approver
    Hashtable appFacsOld = this.getApproverFacsAndAmt();

    String query = "";

    // delete those facs where no longer approver
    for (Enumeration E1 = appFacsOld.keys(); E1.hasMoreElements(); ) {
      String tmp = new String( (String) E1.nextElement());
      if (!approvers.containsKey(tmp)) {
        // delete approval for facid "tmp"
        query = new String("delete finance_approver_list where personnel_id =" + personnel_id + " and facility_id = '" + tmp + "'");
        try {
          db.doUpdate(query);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    // step through approvers
    // if already approver, update
    // else add
    for (Enumeration E = approvers.keys(); E.hasMoreElements(); ) {
      String tmp = new String( (String) E.nextElement());
      if (appFacsOld.containsKey(tmp)) {
        query = "update finance_approver_list set cost_limit = " + ( (Double) approvers.get(tmp)).doubleValue() + " where facility_id = '" + tmp + "' and personnel_id = " + personnel_id;
      } else {
        query = new String("insert into finance_approver_list (personnel_id,facility_id,cost_limit) values");
        query = query + " (" + personnel_id;
        query = query + " ,'" + tmp + "'";
        query = query + " ," + ( (Double) approvers.get(tmp)).doubleValue() + ")";
      }
      try {
        db.doUpdate(query);

      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
        throw e;
      }
    }

    /*String query = null;
           if (personnel_id == null) return false;
           DBResultSet dbrs = null;
           ResultSet rs = null;
           query = new String("delete finance_approver_list where personnel_id ="+personnel_id);
           try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
          } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
     } finally{
           dbrs.close();
          }
           for (Enumeration E = approvers.keys();E.hasMoreElements();){
      String tmp = new String((String) E.nextElement());
      query = new String("insert into finance_approver_list (personnel_id,facility_id,cost_limit) values");
      query = query + " ("+personnel_id;
      query = query + " ,'"+tmp+"'";
      query = query + " ,"+((Double) approvers.get(tmp)).doubleValue()+")";
      try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
     } finally{
           dbrs.close();
           }
           } */
    return true;
  }

  public boolean isAvailable() throws Exception {
    String query = new String("select count(*) from personnel where personnel_id = " + personnel_id);
    int count = 0;
    try {
      count = DbHelpers.countQuery(db, query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return count == 0;
  }

  public boolean isAvailable(String userLogon) throws Exception {
    String query = new String("select count(*) from personnel where logon_id = " + "'" + userLogon + "'");
    int count = 0;
    try {
      count = DbHelpers.countQuery(db, query);
      //System.out.println("Query count is " + count);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    //System.out.println("result of count query is " + (count == 0));
    return (count == 0);
  }

  public Integer getNewUid() throws Exception {
    int uid = 0;
    int count = 1;
    while (count != 0) {
      //String query = new String("select count(*) from personnel where personnel_id = " + new Integer(++uid));
      uid = DbHelpers.getNextVal(db, "personnel_seq");
      //System.out.println(" UID in personnel is " + uid);
      //String query = new String("select count(*) from personnel where personnel_id = "+uid);
      //12-20-01 rather than check the personnel table to see if the personnel ID exist look at global_personnel_id_view instead
      String query = new String("select count(*) from global_personnel_id_view where personnel_id = " + uid);
      try {
        count = DbHelpers.countQuery(db, query);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
    }
    return new Integer(uid);
  }

  //trong 8-7
  public boolean delete() throws Exception {
    if (personnel_id == null) {
      return false;
    }

    String query = null;
    //update company_personnel status = 'I'
    try {
      this.deleteUser(this.personnel_id);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }

    //if the person is an approver than set him/her to inactive
    query = "update chemical_approver set active = null where personnel_id = " + personnel_id;
    try {
      db.doUpdate(query);
    } catch (Exception ee) {
      ee.printStackTrace();
      throw ee;
    }

    return true;
  }

  public boolean updatePrefFacs() throws Exception {
    String query = null;
    if (personnel_id == null) {
      return false;
    }

    query = new String("delete fac_pref where personnel_id =" + personnel_id);
    try {
      db.doUpdate(query);
    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    }

    for (int i = 0; i < preffacs.size(); i++) {
      query = new String("insert into fac_pref (personnel_id,facility_id) values");
      query = query + " (" + personnel_id;
      query = query + " ,'" + preffacs.elementAt(i).toString() + "')";
      try {
        db.doUpdate(query); ;

      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
        throw e;
      }
    }
    return true;
  }

  public void setPrefFacs(Vector fac) {
    preffacs = fac;
  }

  public Enumeration getAllUserLogonEnum() throws Exception {
    String query = "select logon_id from personnel";
    query = query + " order by logon_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      int i = 0;
      while (rs.next()) {
        String name = rs.getString("logon_id");
        if (name == null || BothHelpObjs.isBlankString(name)) {
          continue;
        }
        result.put(new Integer(i++), name);
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return result.elements();
  }

  public boolean isSuperUser() throws Exception {
    String query = "select count(*) from user_group_member";
    query = query + " where personnel_id = " + personnel_id.toString();
    query = query + " and user_group_id = 'SuperUser'";
    int i = 0;
    try {
      i = DbHelpers.countQuery(db, query);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return i != 0;
  }

  public Facility[] getAdminFacilities() throws Exception {
    Vector v = new Vector();
    String query = "select facility_id from user_group_member";
    query = query + " where personnel_id = " + personnel_id.toString() + " and user_group_id = 'Administrator'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Facility f = new Facility(db);
        f.setFacilityId(rs.getString("facility_id"));
        v.addElement(f);
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();

    }
    Facility[] fa = new Facility[v.size()];
    for (int i = 0; i < v.size(); i++) {
      fa[i] = (Facility) v.elementAt(i);
    }
    return fa;
  }

  public Vector getNewChemApprovalFacilities() throws Exception {
    Vector v = new Vector();
    String query = "select distinct facility_id from chemical_approver";
    query = query + " where personnel_id = " + personnel_id.toString();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        v.addElement(rs.getString("facility_id"));
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();

    }
    return v;
  }

  public Vector getFacs() throws Exception {
    String query = "select facility_id from fac_pref ";
    query = query + " where personnel_id = " + personnel_id.toString() + " order by facility_id";
    Vector v = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        Facility f = new Facility(db);
        f.setFacilityId(rs.getString("facility_id"));
        v.addElement(f);
      }

    } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
      throw e;
    } finally {
      dbrs.close();
    }
    return v;
  }

  public void removeExtraReleaserAuths() throws Exception {
    Vector v = getFacs();
    Vector fi = new Vector();
    for (int i = 0; i < v.size(); i++) {
      Facility f = (Facility) v.elementAt(i);
      fi.addElement(f.getFacilityId());
    }
    Vector relFacs = this.getReleaserFacs();
    for (int i = 0; i < relFacs.size(); i++) {
      if (!fi.contains(relFacs.elementAt(i).toString())) {
        try {
          String query = "delete from user_group_member where user_group_id = 'Releaser' and facility_id = '" + relFacs.elementAt(i).toString() + "' and personnel_id = " + this.personnel_id.toString();
          db.doUpdate(query);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void removeExtraApproverAuths() throws Exception {
    Vector v = getFacs();
    Vector fi = new Vector();
    for (int i = 0; i < v.size(); i++) {
      Facility f = (Facility) v.elementAt(i);
      fi.addElement(f.getFacilityId());
    }
    Vector appFacs = this.getReleaserFacs();
    for (int i = 0; i < appFacs.size(); i++) {
      if (!fi.contains(appFacs.elementAt(i).toString())) {
        try {
          String query = "delete from finance_approver_list where facility_id = '" + appFacs.elementAt(i).toString() + "' and personnel_id = " + this.personnel_id.toString();
          db.doUpdate(query);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

}
