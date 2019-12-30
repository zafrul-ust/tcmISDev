package com.tcmis.client.fec.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ChemicalMovementBean <br>
 * @version: 1.0, Oct 3, 2006 <br>
 *****************************************************************************/

public class ChemicalMovementBean
    extends BaseDataBean {

  private String issueIdentifier;
  private String partNumber;
  private BigDecimal quantityRequested;
  private String quantityIssued;
  private String issueUnitOfMeasure;
  private String requestingEmployeeId;
  private String issuingFacility;
  private String receivingFacility;
  private String receivingWorkarea;
  private String receivingEmployeeId;
  private String issueDate;
  private String filename;
  private String facilityId;
  private String catPartNo;
  private BigDecimal itemId;
  private BigDecimal itemQuantity;
  private Date loadDate;
  private Date reportDate;

  //constructor
  public ChemicalMovementBean() {
  }

  //setters
  public void setIssueIdentifier(String issueIdentifier) {
    this.issueIdentifier = issueIdentifier;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public void setQuantityRequested(BigDecimal quantityRequested) {
    this.quantityRequested = quantityRequested;
  }

  public void setQuantityIssued(String quantityIssued) {
    this.quantityIssued = quantityIssued;
  }

  public void setIssueUnitOfMeasure(String issueUnitOfMeasure) {
    this.issueUnitOfMeasure = issueUnitOfMeasure;
  }

  public void setRequestingEmployeeId(String requestingEmployeeId) {
    this.requestingEmployeeId = requestingEmployeeId;
  }

  public void setIssuingFacility(String issuingFacility) {
    this.issuingFacility = issuingFacility;
  }

  public void setReceivingFacility(String receivingFacility) {
    this.receivingFacility = receivingFacility;
  }

  public void setReceivingWorkarea(String receivingWorkarea) {
    this.receivingWorkarea = receivingWorkarea;
  }

  public void setReceivingEmployeeId(String receivingEmployeeId) {
    this.receivingEmployeeId = receivingEmployeeId;
  }

  public void setIssueDate(String issueDate) {
    this.issueDate = issueDate;
  }

  public void setFilename(String filename) {
    this.filename = filename;
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

  public void setLoadDate(Date loadDate) {
    this.loadDate = loadDate;
  }

  public void setReportDate(Date reportDate) {
    this.reportDate = reportDate;
  }

  //getters
  public String getIssueIdentifier() {
    return issueIdentifier;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public BigDecimal getQuantityRequested() {
    return quantityRequested;
  }

  public String getQuantityIssued() {
    return quantityIssued;
  }

  public String getIssueUnitOfMeasure() {
    return issueUnitOfMeasure;
  }

  public String getRequestingEmployeeId() {
    return requestingEmployeeId;
  }

  public String getIssuingFacility() {
    return issuingFacility;
  }

  public String getReceivingFacility() {
    return receivingFacility;
  }

  public String getReceivingWorkarea() {
    return receivingWorkarea;
  }

  public String getReceivingEmployeeId() {
    return receivingEmployeeId;
  }

  public String getIssueDate() {
    return issueDate;
  }

  public String getFilename() {
    return filename;
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

  public Date getLoadDate() {
    return loadDate;
  }

  public Date getReportDate() {
    return reportDate;
  }
}