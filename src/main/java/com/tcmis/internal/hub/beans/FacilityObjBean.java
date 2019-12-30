package com.tcmis.internal.hub.beans;

import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubIgDockOvBean <br>
 * @version: 1.0, Apr 19, 2005 <br>
 *****************************************************************************/

public class FacilityObjBean
    extends BaseDataBean
    implements SQLData {

  private String facilityId;
  private String facilityName;
  private String companyId;
  private String sqlType = "COMPANY_FACILITY_OBJ";

  //constructor
  public FacilityObjBean() {
  }

  //setters
  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setFacilityName(String s) {
    this.facilityName = s;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  //getters
  public String getFacilityId() {
    return this.facilityId;
  }

  public String getFacilityName() {
    return this.facilityName;
  }

  public String getCompanyId() {
    return this.companyId;
  }


  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setCompanyId(stream.readString());
      this.setFacilityId(stream.readString());
      this.setFacilityName(stream.readString());
    }
    catch (SQLException e) {
      throw (SQLException) e;
    }
    catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".readSQL method failed").
          initCause(e);
    }
  }

  public void writeSQL(SQLOutput stream) throws SQLException {
    try {
      stream.writeString(this.getCompanyId());
      stream.writeString(this.getFacilityId());
      stream.writeString(this.getFacilityName());
    }
    catch (SQLException e) {
      throw (SQLException) e;
    }
    catch (Exception e) {
      new IllegalStateException(getClass().getName() +
                                ".writeSQL method failed").
          initCause(e);
    }
  }
}