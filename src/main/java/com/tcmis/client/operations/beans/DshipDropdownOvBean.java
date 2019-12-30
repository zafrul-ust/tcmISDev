package com.tcmis.client.operations.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: DshipDropdownOvBean <br>
 * @version: 1.0, Oct 2, 2007 <br>
 *****************************************************************************/

public class DshipDropdownOvBean extends BaseDataBean implements SQLData {

  private BigDecimal personnelId;
  private String companyId;
  private String companyName;
  private Collection facilityList;
  private Array facilityListArray;
  private String sqlType = "dship_dropdown_ov";

  //constructor
  public DshipDropdownOvBean() {
  }

  //setters
  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setFacilityList(Collection coll) {
    this.facilityList = coll;
  }

  public void setFacilityListArray(Array facilityListArray) {
    List list = null;
    try {
      list = Arrays.asList((Object[]) facilityListArray.getArray());
    } catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setFacilityList(list);
  }

  //getters
  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public Collection getFacilityList() {
    return this.facilityList;
  }

  public Array getFacilityListArray() {
    return facilityListArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setPersonnelId(stream.readBigDecimal());
      this.setCompanyId(stream.readString());
      this.setCompanyName(stream.readString());
      this.setFacilityListArray(stream.readArray());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".readSQL method failed").initCause(e);
    }
  }

  public void writeSQL(SQLOutput stream) throws SQLException {
    try {
      stream.writeBigDecimal(this.getPersonnelId());
      stream.writeString(this.getCompanyId());
      stream.writeString(this.getCompanyName());
      stream.writeArray(this.getFacilityListArray());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".writeSQL method failed").initCause(e);
    }
  }
}