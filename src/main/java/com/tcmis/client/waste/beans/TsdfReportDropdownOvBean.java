package com.tcmis.client.waste.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TsdfReportDropdownOvBean <br>
 * @version: 1.0, Mar 1, 2007 <br>
 *****************************************************************************/

public class TsdfReportDropdownOvBean extends BaseDataBean implements SQLData {

  private BigDecimal personnelId;
  private String companyId;
  private String tsdfFacilityName;
  private String tsdfFacilityId;
  private String tsdfVendorId;
  private String tsdfVendorName;
  private Collection toVendorList;
  private Array toVendorListArray;
  private Collection wasteGenerationCompanyList;
  private Array wasteGenerationCompanyListArray;
  private String sqlType = "TSDF_REPORT_DROPDOWN_OBJ";

  //constructor
  public TsdfReportDropdownOvBean() {
  }

  //setters
  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setTsdfFacilityName(String tsdfFacilityName) {
    this.tsdfFacilityName = tsdfFacilityName;
  }

  public void setTsdfFacilityId(String tsdfFacilityId) {
    this.tsdfFacilityId = tsdfFacilityId;
  }

  public void setTsdfVendorId(String tsdfVendorId) {
    this.tsdfVendorId = tsdfVendorId;
  }

  public void setTsdfVendorName(String tsdfVendorName) {
    this.tsdfVendorName = tsdfVendorName;
  }

  public void setToVendorList(Collection coll) {
    this.toVendorList = coll;
  }

  public void setToVendorListArray(Array toVendorListArray) {
    List list = null;
    try {
      list = Arrays.asList((Object[]) toVendorListArray.getArray());
    } catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setToVendorList(list);
  }

  public void setWasteGenerationCompanyList(Collection coll) {
    this.wasteGenerationCompanyList = coll;
  }

  public void setWasteGenerationCompanyListArray(Array wasteGenerationCompanyListArray) {
    List list = null;
    try {
      list = Arrays.asList((Object[]) wasteGenerationCompanyListArray.getArray());
    } catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setWasteGenerationCompanyList(list);
  }

  //getters
  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getTsdfFacilityName() {
    return tsdfFacilityName;
  }

  public String getTsdfFacilityId() {
    return tsdfFacilityId;
  }

  public String getTsdfVendorId() {
    return tsdfVendorId;
  }

  public String getTsdfVendorName() {
    return tsdfVendorName;
  }

  public Collection getToVendorList() {
    return this.toVendorList;
  }

  public Array getToVendorListArray() {
    return toVendorListArray;
  }

  public Collection getWasteGenerationCompanyList() {
    return this.wasteGenerationCompanyList;
  }

  public Array getWasteGenerationCompanyListArray() {
    return wasteGenerationCompanyListArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setPersonnelId(stream.readBigDecimal());
      this.setCompanyId(stream.readString());
      this.setTsdfFacilityName(stream.readString());
      this.setTsdfFacilityId(stream.readString());
      this.setTsdfVendorId(stream.readString());
      this.setTsdfVendorName(stream.readString());
      this.setToVendorListArray(stream.readArray());
      this.setWasteGenerationCompanyListArray(stream.readArray());
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
      stream.writeString(this.getTsdfFacilityName());
      stream.writeString(this.getTsdfFacilityId());
      stream.writeString(this.getTsdfVendorId());
      stream.writeString(this.getTsdfVendorName());
      stream.writeArray(this.getToVendorListArray());
      stream.writeArray(this.getWasteGenerationCompanyListArray());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".writeSQL method failed").initCause(e);
    }
  }
}