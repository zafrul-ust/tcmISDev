package com.tcmis.client.fec.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ChemicalOnHandBean <br>
 * @version: 1.0, Oct 3, 2006 <br>
 *****************************************************************************/

public class ChemicalOnHandBean
    extends BaseDataBean {

  private String onHandDate;
  private String partNumber;
  private String facility;
  private BigDecimal quantityOnhand;
  private String uom;
  private String filename;
  private Date loadDate;
  private String facilityId;
  private String catPartNo;
  private BigDecimal itemId;
  private BigDecimal itemQuantity;
  private Date reportDate;

  //constructor
  public ChemicalOnHandBean() {
  }

  //setters
  public void setOnHandDate(String onHandDate) {
    this.onHandDate = onHandDate;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public void setFacility(String facility) {
    this.facility = facility;
  }

  public void setQuantityOnhand(BigDecimal quantityOnhand) {
    this.quantityOnhand = quantityOnhand;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public void setLoadDate(Date loadDate) {
    this.loadDate = loadDate;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setItemQuantity(BigDecimal itemQuantity) {
    this.itemQuantity = itemQuantity;
  }

  public void setReportDate(Date reportDate) {
    this.reportDate = reportDate;
  }

  //getters
  public String getOnHandDate() {
    return onHandDate;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public String getFacility() {
    return facility;
  }

  public BigDecimal getQuantityOnhand() {
    return quantityOnhand;
  }

  public String getUom() {
    return uom;
  }

  public String getFilename() {
    return filename;
  }

  public Date getLoadDate() {
    return loadDate;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public BigDecimal getItemQuantity() {
    return itemQuantity;
  }

  public Date getReportDate() {
    return reportDate;
  }
}