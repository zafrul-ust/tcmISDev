package com.tcmis.internal.msds.beans;

import java.io.File;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.*;
import java.util.Collection;
import java.util.List;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: BldgFloorEditOvBean <br>
 * @version: 1.0, May 23, 2005 <br>
 *****************************************************************************/

public class MsdsInputBean extends BaseDataBean {

  private String msds;
  private String status;
  private String tradeName;
  private String manufacturerName;
  private Date revisionDate;
  private File theFile;
  private String fileName;
  private String onLine;
  private String facilityId;
  private String building;
  private String floor;
  private String department;
  private String facilityIdToDelete;
  private String buildingToDelete;
  private String floorToDelete;
  private String departmentToDelete;
  private String sortBy;
  private String hazardClassification;
  private String search;
  private String submit;
  private String submitNew;
  private String submitSearch;
  private String submitAdd;
  private String submitUpdate;

  public MsdsInputBean() {
    super();
  }

  public MsdsInputBean(boolean b) {
    super(b);
  }


  public String getMsds() {
      return this.msds;
  }

  public void setMsds(String msds) {
    if(msds != null && this.doTrim) {
      this.msds = msds.trim();
    }
    else {
      this.msds = msds;
    }
  }

  public String getStatus() {
      return this.status;
  }

  public void setStatus(String status) {
     if(status != null && this.doTrim) {
       this.status = status.trim();
     }
     else {
       this.status = status;
     }
  }

  public String getTradeName() {
      return this.tradeName;
  }

  public void setTradeName(String tradeName) {
    if(tradeName != null && this.doTrim) {
      this.tradeName = tradeName.trim();
    }
    else {
      this.tradeName = tradeName;
    }
  }

  public String getManufacturerName() {
      return this.manufacturerName;
  }

  public void setManufacturerName(String manufacturerName) {
    if(manufacturerName != null && this.doTrim) {
      this.manufacturerName = manufacturerName.trim();
    }
    else {
      this.manufacturerName = manufacturerName;
    }
  }

  public Date getRevisionDate() {
      return this.revisionDate;
  }

  public void setRevisionDate(Date revisionDate) {
      this.revisionDate = revisionDate;
  }

  public File getFile() {
      return this.theFile;
  }

  public void setTheFile(File file) {
      this.theFile = file;
  }

  public void setFileName(String fileName) {
    if(fileName != null && this.doTrim) {
      this.fileName = fileName.trim();
    }
    else {
      this.fileName = fileName;
    }
  }

  public String getFileName() {
      return this.fileName;
  }

  public void setOnLine(String onLine) {
    if(onLine != null && this.doTrim) {
      this.onLine = onLine.trim();
    }
    else {
      this.onLine = onLine;
    }
  }

  public String getOnLine() {
      return this.onLine;
  }

  public String getFacilityId() {
      return this.facilityId.trim();
  }

  public void setFacilityId(String facilityId) {
    if(facilityId != null && this.doTrim) {
      this.facilityId = facilityId.trim();
    }
    else {
      this.facilityId = facilityId;
    }
  }

  public String getBuilding() {
      return this.building;
  }

  public void setBuilding(String building) {
    if(building != null && this.doTrim) {
      this.building = building.trim();
    }
    else {
      this.building = building;
    }
  }

  public String getFloor() {
      return this.floor;
  }

  public void setFloor(String floor) {
    if(floor != null && this.doTrim) {
      this.floor = floor.trim();
    }
    else {
      this.floor = floor;
    }
  }

  public String getDepartment() {
      return this.department;
  }

  public void setDepartment(String department) {
    if(department != null && this.doTrim) {
      this.department = department.trim();
    }
    else {
      this.department = department;
    }
  }

  public String getFacilityIdToDelete() {
      return this.facilityIdToDelete;
  }

  public void setFacilityIdToDelete(String facilityIdToDelete) {
    if(facilityIdToDelete != null && this.doTrim) {
      this.facilityIdToDelete = facilityIdToDelete.trim();
    }
    else {
      this.facilityIdToDelete = facilityIdToDelete;
    }
  }

  public String getBuildingToDelete() {
      return this.buildingToDelete;
  }

  public void setBuildingToDelete(String buildingToDelete) {
    if(buildingToDelete != null && this.doTrim) {
      this.buildingToDelete = buildingToDelete.trim();
    }
    else {
      this.buildingToDelete = buildingToDelete;
    }
  }

  public String getFloorToDelete() {
      return this.floorToDelete;
  }

  public void setFloorToDelete(String floorToDelete) {
    if(floorToDelete != null && this.doTrim) {
      this.floorToDelete = floorToDelete.trim();
    }
    else {
      this.floorToDelete = floorToDelete;
    }
  }

  public String getDepartmentToDelete() {
      return this.departmentToDelete;
  }

  public void setDepartmentToDelete(String departmentToDelete) {
    if(departmentToDelete != null && this.doTrim) {
      this.departmentToDelete = departmentToDelete.trim();
    }
    else {
      this.departmentToDelete = departmentToDelete;
    }
  }

  public String getSubmit() {
      return this.submit;
  }

  public void setSubmit(String submit) {
      this.submit = submit;
  }

  public String getSubmitNew() {
      return this.submitNew;
  }

  public void setSubmitNew(String submitNew) {
      this.submitNew = submitNew;
  }

  public String getSubmitSearch() {
      return this.submitSearch;
  }

  public void setSubmitSearch(String submitSearch) {
      this.submitSearch = submitSearch;
  }

  public String getSubmitAdd() {
      return this.submitAdd;
  }

  public void setSubmitAdd(String submitAdd) {
      this.submitAdd = submitAdd;
  }

  public String getSubmitUpdate() {
      return this.submitUpdate;
  }

  public void setSubmitUpdate(String submitUpdate) {
      this.submitUpdate = submitUpdate;
  }

  public String getSortBy() {
      return this.sortBy;
  }

  public void setSortBy(String sortBy) {
    if(sortBy != null && this.doTrim) {
      this.sortBy = sortBy.trim();
    }
    else {
      this.sortBy = sortBy;
    }
  }

  public String getSearch() {
      return this.search;
  }

  public void setSearch(String search) {
    if(search != null && this.doTrim) {
      this.search = search.trim();
    }
    else {
      this.search = search;
    }
  }

  public String getHazardClassification() {
      return this.hazardClassification;
  }

  public void setHazardClassification(String s) {
    if(s != null && this.doTrim) {
      this.hazardClassification = s.trim();
    }
    else {
      this.hazardClassification = s;
    }
  }
}