package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
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

public class HubIgDockOvBean
    extends BaseDataBean
    implements SQLData {

  private String companyId;
  private BigDecimal personnelId;
  private String facilityId;
  private BigDecimal priority;
  private String homeCompanyId;
  private String homeCurrencyId;
  private String branchPlant;
  private String hubName;
  private Collection inventoryGroupDocks;
  private Array inventoryGroupDocksArray;
  private String sqlType = "HUB_IG_DOCK_OBJ";

  //constructor
  public HubIgDockOvBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setPriority(BigDecimal priority) {
    this.priority = priority;
  }

  public void setHomeCompanyId(String homeCompanyId) {
    this.homeCompanyId = homeCompanyId;
  }

  public void setHomeCurrencyId(String homeCurrencyId) {
    this.homeCurrencyId = homeCurrencyId;
  }

  public void setBranchPlant(String branchPlant) {
    this.branchPlant = branchPlant;
  }

  public void setHubName(String hubName) {
    this.hubName = hubName;
  }

  public void setInventoryGroupDocks(Collection coll) {
    this.inventoryGroupDocks = coll;
  }

  public void setInventoryGroupDocksArray(Array inventoryGroupDocksArray) {
    List list = null;
    try {
      list = Arrays.asList( (Object[]) inventoryGroupDocksArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setInventoryGroupDocks(list);
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public BigDecimal getPriority() {
    return priority;
  }

  public String getHomeCompanyId() {
    return homeCompanyId;
  }

  public String getHomeCurrencyId() {
    return homeCurrencyId;
  }

  public String getBranchPlant() {
    return branchPlant;
  }

  public String getHubName() {
    return hubName;
  }

  public Collection getInventoryGroupDocks() {
    return this.inventoryGroupDocks;
  }

  public Array getInventoryGroupDocksArray() {
    return inventoryGroupDocksArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setCompanyId(stream.readString());
      this.setPersonnelId(stream.readBigDecimal());
      this.setFacilityId(stream.readString());
      this.setPriority(stream.readBigDecimal());
      this.setHomeCompanyId(stream.readString());
      this.setHomeCurrencyId(stream.readString());
      this.setBranchPlant(stream.readString());
      this.setHubName(stream.readString());
      this.setInventoryGroupDocksArray(stream.readArray());
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
      stream.writeBigDecimal(this.getPersonnelId());
      stream.writeString(this.getFacilityId());
      stream.writeBigDecimal(this.getPriority());
      stream.writeString(this.getHomeCompanyId());
      stream.writeString(this.getHomeCurrencyId());
      stream.writeString(this.getBranchPlant());
      stream.writeString(this.getHubName());
      stream.writeArray(this.getInventoryGroupDocksArray());
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