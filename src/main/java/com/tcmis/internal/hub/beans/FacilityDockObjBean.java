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

public class FacilityDockObjBean
    extends BaseDataBean
    implements SQLData {

  private String facilityId;
  private String dockLocationId;
  private String dockDesc;
  private String companyId;
  private String status;
  private String sqlType = "CUSTOMER.FACILITY_DOCK_OBJ";

  //constructor
  public FacilityDockObjBean() {
  }

  //setters
  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setDockLocationId(String dockLocationId) {
    this.dockLocationId = dockLocationId;
  }

  public void setDockDesc(String dockDesc) {
    this.dockDesc = dockDesc;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  //getters
  public String getFacilityId() {
    return this.facilityId;
  }

  public String getDockLocationId() {
    return this.dockLocationId;
  }

  public String getDockDesc() {
    return this.dockDesc;
  }

  public String getCompanyId() {
    return this.companyId;
  }

  public String getStatus() {
    return this.status;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setFacilityId(stream.readString());
      this.setDockLocationId(stream.readString());
      this.setDockDesc(stream.readString());
      this.setCompanyId(stream.readString());
      this.setStatus(stream.readString());
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
      stream.writeString(this.getFacilityId());
      stream.writeString(this.getDockLocationId());
      stream.writeString(this.getDockDesc());
      stream.writeString(this.getCompanyId());
      stream.writeString(this.getStatus());
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

