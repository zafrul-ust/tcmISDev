package com.tcmis.client.cal.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FmcExtractBean <br>
 * @version: 1.0, Mar 17, 2005 <br>
 *****************************************************************************/

public class FmcExtractBean
    extends BaseDataBean {

  public BigDecimal keyId;
  public String trlType;
  public Date trlDate;
  public String trlCostcode;
  public String trlEvent;
  public String trlPart;
  public BigDecimal trlQuantity;
  public BigDecimal trlPrice;
  public String parClass;
  public String workOrderDescription;
  public String costCodeDescription;
  public String partDescription;
  public String parUom;
  public String unitOfMeasureDescription;
  public BigDecimal itemId;
  public String fileName;
  public Date loadDate;
  public String catPartNo;
  public BigDecimal itemQuantityIssued;
  public String category;
  public String applicationMethod;
  public Date endUseDatetime;
  public String endUseComplete;
  public Date lastMod;
  public BigDecimal itemQtyIssued;
  public BigDecimal emissionFactor;
  public BigDecimal volumeGal;
  public String application;
  public String facilityId;
  public String traOrg;

  //constructor
  public FmcExtractBean() {
  }

  //setters
  public void setKeyId(BigDecimal keyId) {
    this.keyId = keyId;
  }

  public void setTrlType(String trlType) {
    this.trlType = trlType;
  }

  public void setTrlDate(Date trlDate) {
    this.trlDate = trlDate;
  }

  public void setTrlCostcode(String trlCostcode) {
    this.trlCostcode = trlCostcode;
  }

  public void setTrlEvent(String trlEvent) {
    this.trlEvent = trlEvent;
  }

  public void setTrlPart(String trlPart) {
    this.trlPart = trlPart;
  }

  public void setTrlQuantity(BigDecimal trlQuantity) {
    this.trlQuantity = trlQuantity;
  }

  public void setTrlPrice(BigDecimal trlPrice) {
    this.trlPrice = trlPrice;
  }

  public void setParClass(String parClass) {
    this.parClass = parClass;
  }

  public void setWorkOrderDescription(String workOrderDescription) {
    this.workOrderDescription = workOrderDescription;
  }

  public void setCostCodeDescription(String costCodeDescription) {
    this.costCodeDescription = costCodeDescription;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public void setParUom(String parUom) {
    this.parUom = parUom;
  }

  public void setUnitOfMeasureDescription(String unitOfMeasureDescription) {
    this.unitOfMeasureDescription = unitOfMeasureDescription;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setLoadDate(Date loadDate) {
    this.loadDate = loadDate;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setItemQuantityIssued(BigDecimal itemQuantityIssued) {
    this.itemQuantityIssued = itemQuantityIssued;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setApplicationMethod(String applicationMethod) {
    this.applicationMethod = applicationMethod;
  }

  public void setEndUseDatetime(Date endUseDatetime) {
    this.endUseDatetime = endUseDatetime;
  }

  public void setEndUseComplete(String endUseComplete) {
    this.endUseComplete = endUseComplete;
  }

  public void setLastMod(Date lastMod) {
    this.lastMod = lastMod;
  }

  public void setItemQtyIssued(BigDecimal itemQtyIssued) {
    this.itemQtyIssued = itemQtyIssued;
  }

  public void setEmissionFactor(BigDecimal emissionFactor) {
    this.emissionFactor = emissionFactor;
  }

  public void setVolumeGal(BigDecimal volumeGal) {
    this.volumeGal = volumeGal;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setTraOrg(String traOrg) {
    this.traOrg = traOrg;
  }

  //getters
  public BigDecimal getKeyId() {
    return keyId;
  }

  public String getTrlType() {
    return trlType;
  }

  public Date getTrlDate() {
    return trlDate;
  }

  public String getTrlCostcode() {
    return trlCostcode;
  }

  public String getTrlEvent() {
    return trlEvent;
  }

  public String getTrlPart() {
    return trlPart;
  }

  public BigDecimal getTrlQuantity() {
    return trlQuantity;
  }

  public BigDecimal getTrlPrice() {
    return trlPrice;
  }

  public String getParClass() {
    return parClass;
  }

  public String getWorkOrderDescription() {
    return workOrderDescription;
  }

  public String getCostCodeDescription() {
    return costCodeDescription;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public String getParUom() {
    return parUom;
  }

  public String getUnitOfMeasureDescription() {
    return unitOfMeasureDescription;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public String getFileName() {
    return fileName;
  }

  public Date getLoadDate() {
    return loadDate;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getItemQuantityIssued() {
    return itemQuantityIssued;
  }

  public String getCategory() {
    return category;
  }

  public String getApplicationMethod() {
    return applicationMethod;
  }

  public Date getEndUseDatetime() {
    return endUseDatetime;
  }

  public String getEndUseComplete() {
    return endUseComplete;
  }

  public Date getLastMod() {
    return lastMod;
  }

  public BigDecimal getItemQtyIssued() {
    return itemQtyIssued;
  }

  public BigDecimal getEmissionFactor() {
    return emissionFactor;
  }

  public BigDecimal getVolumeGal() {
    return volumeGal;
  }

  public String getApplication() {
    return application;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getTraOrg() {
    return traOrg;
  }
}