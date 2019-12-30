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

public class InventoryGroupFacilityObjBean
    extends BaseDataBean
    implements SQLData {

  private String inventoryGroup;
  private String collection;
  private String inventoryGroupName;
  private String issueGeneration;
  private String skipReceivingQc;
  private String hazardLabelGroup;
  private String orderQtyUpdateOnReceipt;
  private Collection facilities;
  private Array facilitiesArray;
  private String sqlType = "INV_GROUP_FAC_COLLECTION_OBJ";

  //constructor
  public InventoryGroupFacilityObjBean() {
  }

  //setters
  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setCollection(String s) {
    this.collection = s;
  }

  public void setInventoryGroupName(String inventoryGroupName) {
    this.inventoryGroupName = inventoryGroupName;
  }

  public void setIssueGeneration(String issueGeneration) {
    this.issueGeneration = issueGeneration;
  }

  public void setSkipReceivingQc(String skipReceivingQc) {
    this.skipReceivingQc = skipReceivingQc;
  }

  public void setHazardLabelGroup(String hazardLabelGroup) {
    this.hazardLabelGroup = hazardLabelGroup;
  }

  public void setOrderQtyUpdateOnReceipt(String orderQtyUpdateOnReceipt) {
    this.orderQtyUpdateOnReceipt = orderQtyUpdateOnReceipt;
  }

  public void setFacilities(Collection coll) {
    this.facilities = coll;
  }

  public void setFacilitiesArray(Array facilitiesArray) {
    //System.out.println("setting docks with:" + docksArray);
    List list = null;
    try {
      list = Arrays.asList( (Object[]) facilitiesArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setFacilities(list);
  }

  //getters
  public String getInventoryGroup() {
    return this.inventoryGroup;
  }

  public String getCollection() {
    return this.collection;
  }

  public String getInventoryGroupName() {
    return this.inventoryGroupName;
  }

  public String getIssueGeneration() {
    return this.issueGeneration;
  }

  public String getSkipReceivingQc() {
    return this.skipReceivingQc;
  }

  public String getHazardLabelGroup() {
    return this.hazardLabelGroup;
  }

  public String getOrderQtyUpdateOnReceipt() {
    return this.orderQtyUpdateOnReceipt;
  }

  public Collection getFacilities() {
    return this.facilities;
  }

  public Array getFacilitiesArray() {
    return this.facilitiesArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setInventoryGroup(stream.readString());
      this.setInventoryGroupName(stream.readString());
      this.setCollection(stream.readString());
      this.setIssueGeneration(stream.readString());
      this.setSkipReceivingQc(stream.readString());
      this.setHazardLabelGroup(stream.readString());
      this.setOrderQtyUpdateOnReceipt(stream.readString());
      this.setFacilitiesArray(stream.readArray());
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
      stream.writeString(this.getInventoryGroup());
      stream.writeString(this.getInventoryGroupName());
      stream.writeString(this.getCollection());
      stream.writeString(this.getIssueGeneration());
      stream.writeString(this.getSkipReceivingQc());
      stream.writeString(this.getHazardLabelGroup());
      stream.writeString(this.getOrderQtyUpdateOnReceipt());
      stream.writeArray(this.getFacilitiesArray());
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