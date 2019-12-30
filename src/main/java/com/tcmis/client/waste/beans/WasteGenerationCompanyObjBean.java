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

public class WasteGenerationCompanyObjBean extends BaseDataBean implements SQLData {

  private String companyId;
  private String companyName;
  private Collection genFacilityList;
  private Array genFacilityListArray;
  private String sqlType = "WASTE_GENERATION_COMPANY_OBJ";

  //constructor
  public WasteGenerationCompanyObjBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setGenFacilityList(Collection coll) {
    this.genFacilityList = coll;
  }

  public void setGenFacilityListArray(Array genFacilityListArray) {
    List list = null;
    try {
      list = Arrays.asList((Object[]) genFacilityListArray.getArray());
    } catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setGenFacilityList(list);
  }


  //getters
  public String getCompanyId() {
    return companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public Collection getGenFacilityList() {
    return this.genFacilityList;
  }

  public Array getGenFacilityListArray() {
    return genFacilityListArray;
  }


  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setCompanyId(stream.readString());
      this.setCompanyName(stream.readString());
      this.setGenFacilityListArray(stream.readArray());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".readSQL method failed").initCause(e);
    }
  }

  public void writeSQL(SQLOutput stream) throws SQLException {
    try {
      stream.writeString(this.getCompanyId());
      stream.writeString(this.getCompanyName());
      stream.writeArray(this.getGenFacilityListArray());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".writeSQL method failed").initCause(e);
    }
  }
}