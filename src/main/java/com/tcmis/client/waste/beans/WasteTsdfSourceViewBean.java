package com.tcmis.client.waste.beans;

import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: WasteTsdfSourceViewBean <br>
 * @version: 1.0, Jan 19, 2007 <br>
 *****************************************************************************/

public class WasteTsdfSourceViewBean
    extends BaseDataBean {

  private String tsdfCompanyId;
  private String tsdfEpaId;
  private String tsdfName;
  private String tsdfFacilityId;
  private String tsdfLocationDesc;
  private String tsdfLocationStatus;
  private String generatorCompanyId;
  private String generatorFacilityId;
  private String generatorWasteLocationId;
  private String tsdfFacilityIdForGenerator;
  private String generatorActive;
  private String generatorLocationDesc;
  private String generatorLocationStatus;
  private BigDecimal personnelId;
  private String userGroupId;
  private String admin;

  private Collection generatorCompanyIdBeanCollection = new Vector();
  private Collection tsdfFacilityIdForGeneratorBeanCollection = new Vector();
  private String generatorFacilityStorageLocation;
  private String generatorFacilityStorageLocationId;

  //constructor
  public WasteTsdfSourceViewBean() {
  }

  //combining the colum my self
  public void setGeneratorFacilityStorageLocation() {
    if (tsdfFacilityIdForGenerator != null && generatorWasteLocationId != null) {
      generatorFacilityStorageLocation = generatorFacilityId+" / "+generatorWasteLocationId;
      generatorFacilityStorageLocationId = generatorFacilityId+":::"+generatorWasteLocationId;
    }
  }
  public String getGeneratorFacilityStorageLocation() {
    return this.generatorFacilityStorageLocation;
  }
  public String getGeneratorFacilityStorageLocationId() {
    return this.generatorFacilityStorageLocationId;
  }

  //add
  public void addGeneratorCompanyIdBeanCollection(WasteTsdfSourceViewBean bean) {
    this.generatorCompanyIdBeanCollection.add(bean);
  }

  public void addTsdfFacilityIdForGeneratorBeanCollection(
      WasteTsdfSourceViewBean bean) {
    this.tsdfFacilityIdForGeneratorBeanCollection.add(bean);
  }

  //setters
  public void setTsdfCompanyId(String tsdfCompanyId) {
    this.tsdfCompanyId = tsdfCompanyId;
  }

  public void setTsdfEpaId(String tsdfEpaId) {
    this.tsdfEpaId = tsdfEpaId;
  }

  public void setTsdfName(String tsdfName) {
    this.tsdfName = tsdfName;
  }

  public void setTsdfFacilityId(String tsdfFacilityId) {
    this.tsdfFacilityId = tsdfFacilityId;
  }

  public void setTsdfLocationDesc(String tsdfLocationDesc) {
    this.tsdfLocationDesc = tsdfLocationDesc;
  }

  public void setTsdfLocationStatus(String tsdfLocationStatus) {
    this.tsdfLocationStatus = tsdfLocationStatus;
  }

  public void setGeneratorCompanyId(String generatorCompanyId) {
    this.generatorCompanyId = generatorCompanyId;
  }

  public void setGeneratorFacilityId(String generatorFacilityId) {
    this.generatorFacilityId = generatorFacilityId;
  }

  public void setGeneratorWasteLocationId(String generatorWasteLocationId) {
    this.generatorWasteLocationId = generatorWasteLocationId;
    setGeneratorFacilityStorageLocation();
  }

  public void setTsdfFacilityIdForGenerator(String tsdfFacilityIdForGenerator) {
    this.tsdfFacilityIdForGenerator = tsdfFacilityIdForGenerator;
    setGeneratorFacilityStorageLocation();
  }

  public void setGeneratorActive(String generatorActive) {
    this.generatorActive = generatorActive;
  }

  public void setGeneratorLocationDesc(String generatorLocationDesc) {
    this.generatorLocationDesc = generatorLocationDesc;
  }

  public void setGeneratorLocationStatus(String generatorLocationStatus) {
    this.generatorLocationStatus = generatorLocationStatus;
  }

  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setUserGroupId(String userGroupId) {
    this.userGroupId = userGroupId;
  }

  public void setAdmin(String admin) {
    this.admin = admin;
  }

  public void setGeneratorCompanyIdBeanCollection(Collection c) {
    this.generatorCompanyIdBeanCollection = c;
  }

  public void setTsdfFacilityIdForGeneratorBeanCollection(Collection c) {
    this.tsdfFacilityIdForGeneratorBeanCollection = c;
  }
  //getters
  public String getTsdfCompanyId() {
    return tsdfCompanyId;
  }

  public String getTsdfEpaId() {
    return tsdfEpaId;
  }

  public String getTsdfName() {
    return tsdfName;
  }

  public String getTsdfFacilityId() {
    return tsdfFacilityId;
  }

  public String getTsdfLocationDesc() {
    return tsdfLocationDesc;
  }

  public String getTsdfLocationStatus() {
    return tsdfLocationStatus;
  }

  public String getGeneratorCompanyId() {
    return generatorCompanyId;
  }

  public String getGeneratorFacilityId() {
    return generatorFacilityId;
  }

  public String getGeneratorWasteLocationId() {
    return generatorWasteLocationId;
  }

  public String getTsdfFacilityIdForGenerator() {
    return tsdfFacilityIdForGenerator;
  }

  public String getGeneratorActive() {
    return generatorActive;
  }

  public String getGeneratorLocationDesc() {
    return generatorLocationDesc;
  }

  public String getGeneratorLocationStatus() {
    return generatorLocationStatus;
  }

  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public String getUserGroupId() {
    return userGroupId;
  }

  public String getAdmin() {
    return admin;
  }

  public Collection getGeneratorCompanyIdBeanCollection() {
    return generatorCompanyIdBeanCollection;
  }

  public Collection getTsdfFacilityIdForGeneratorBeanCollection() {
    return tsdfFacilityIdForGeneratorBeanCollection;
  }
}