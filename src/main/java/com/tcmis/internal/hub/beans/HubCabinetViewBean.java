package com.tcmis.internal.hub.beans;
import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class HubCabinetViewBean
    extends BaseDataBean {

  private String preferredWarehouse;
  private String companyId;
  private String facilityId;
  private String facilityName;
  private String application;
  private String applicationDesc;
  private BigDecimal cabinetId;
  private String cabinetName;
  private BigDecimal barcodeCabinetId;
  private String branchPlant;
  private String hubName;

  private Collection facilityBeanCollection = new Vector();
  private Collection applicationBeanCollection = new Vector();
  private Collection cabinetBeanCollection = new Vector();
//this is all cabinets for all work areas sorted by cabinet
//if you're wondering why this is needed ask Nawaz
  private Collection sortedCabinetBeanForAllWorkAreasCollection = new Vector();

  //constructor
  public HubCabinetViewBean() {
  }

  //setters
  public void setPreferredWarehouse(String preferredWarehouse) {
    this.preferredWarehouse = preferredWarehouse;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setApplicationDesc(String applicationDesc) {
    this.applicationDesc = applicationDesc;
  }

  public void setCabinetId(BigDecimal cabinetId) {
    this.cabinetId = cabinetId;
  }

  public void setCabinetName(String cabinetName) {
    this.cabinetName = cabinetName;
  }

  public void setBarcodeCabinetId(BigDecimal b) {
    this.barcodeCabinetId = b;
  }

  public void setFacilityBeanCollection(Collection c) {
    this.facilityBeanCollection = c;
  }

  public void setApplicationBeanCollection(Collection c) {
    this.applicationBeanCollection = c;
  }

  public void setCabinetBeanCollection(Collection c) {
    this.cabinetBeanCollection = c;
  }

  public void addFacilityBean(HubCabinetViewBean bean) {
    this.facilityBeanCollection.add(bean);
  }

  public void addApplicationBean(HubCabinetViewBean bean) {
    this.applicationBeanCollection.add(bean);
  }

  public void addCabinetBean(HubCabinetViewBean bean) {
    this.cabinetBeanCollection.add(bean);
  }

  public void setBranchPlant(String s) {
    this.branchPlant = s;
  }

  public void setHubName(String s) {
    this.hubName = s;
  }

  public void setSortedCabinetBeanForAllWorkAreasCollection(Collection c) {
    this.sortedCabinetBeanForAllWorkAreasCollection = c;
  }
  //getters
  public String getPreferredWarehouse() {
    return preferredWarehouse;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getFacilityName() {
    return facilityName;
  }

  public String getApplication() {
    return application;
  }

  public String getApplicationDesc() {
    return applicationDesc;
  }

  public BigDecimal getCabinetId() {
    return cabinetId;
  }

  public String getCabinetName() {
    return cabinetName;
  }

  public BigDecimal getBarcodeCabinetId() {
    return barcodeCabinetId;
  }

  public Collection getFacilityBeanCollection() {
    return this.facilityBeanCollection;
  }

  public Collection getApplicationBeanCollection() {
    return this.applicationBeanCollection;
  }

  public Collection getCabinetBeanCollection() {
    return this.cabinetBeanCollection;
  }

  public String getBranchPlant() {
    return this.branchPlant;
  }

  public String getHubName() {
    return this.hubName;
  }

  public Collection getFacilityCabinetCollection() {
    Collection c = new Vector();
    if(this.applicationBeanCollection != null) {
      Iterator iterator = this.applicationBeanCollection.iterator();
      while(iterator.hasNext()) {
        HubCabinetViewBean b = (HubCabinetViewBean)iterator.next();
        c.addAll(b.getCabinetBeanCollection());
      }
    }
    return c;
  }

  public Collection getSortedCabinetBeanForAllWorkAreasCollection() {
    return this.sortedCabinetBeanForAllWorkAreasCollection;
  }
}