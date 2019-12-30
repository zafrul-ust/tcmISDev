package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CarrierInfoBean <br>
 * @version: 1.0, Feb 23, 2007 <br>
 *****************************************************************************/

public class CarrierInfoBean
    extends BaseDataBean {

  private String carrierCode;
  private String companyId;
  private String shipToLocId;
  private String billToLocId;
  private String account;
  private String notes;
  private String carrierName;
  private String hub;
  private String haasVendor;
  private String carrierMethod;
  private String supplier;
  private String inventoryGroup;
  private String freightOnBoard;
  private String status;
  private BigDecimal minGrossShipmentWeight;
  private BigDecimal maxGrossShipmentWeight;
  private BigDecimal maxGrossIndividualWeight;
  private String modificationComments;

  //constructor
  public CarrierInfoBean() {
  }

  //setters
  public void setCarrierCode(String carrierCode) {
    this.carrierCode = carrierCode;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setShipToLocId(String shipToLocId) {
    this.shipToLocId = shipToLocId;
  }

  public void setBillToLocId(String billToLocId) {
    this.billToLocId = billToLocId;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setCarrierName(String carrierName) {
    this.carrierName = carrierName;
  }

  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setHaasVendor(String haasVendor) {
    this.haasVendor = haasVendor;
  }

  public void setCarrierMethod(String carrierMethod) {
    this.carrierMethod = carrierMethod;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setFreightOnBoard(String freightOnBoard) {
    this.freightOnBoard = freightOnBoard;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setMinGrossShipmentWeight(BigDecimal minGrossShipmentWeight) {
    this.minGrossShipmentWeight = minGrossShipmentWeight;
  }

  public void setMaxGrossShipmentWeight(BigDecimal maxGrossShipmentWeight) {
    this.maxGrossShipmentWeight = maxGrossShipmentWeight;
  }

  public void setMaxGrossIndividualWeight(BigDecimal maxGrossIndividualWeight) {
    this.maxGrossIndividualWeight = maxGrossIndividualWeight;
  }

  public void setModificationComments(String modificationComments) {
    this.modificationComments = modificationComments;
  }

  //getters
  public String getCarrierCode() {
    return carrierCode;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getShipToLocId() {
    return shipToLocId;
  }

  public String getBillToLocId() {
    return billToLocId;
  }

  public String getAccount() {
    return account;
  }

  public String getNotes() {
    return notes;
  }

  public String getCarrierName() {
    return carrierName;
  }

  public String getHub() {
    return hub;
  }

  public String getHaasVendor() {
    return haasVendor;
  }

  public String getCarrierMethod() {
    return carrierMethod;
  }

  public String getSupplier() {
    return supplier;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getFreightOnBoard() {
    return freightOnBoard;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getMinGrossShipmentWeight() {
    return minGrossShipmentWeight;
  }

  public BigDecimal getMaxGrossShipmentWeight() {
    return maxGrossShipmentWeight;
  }

  public BigDecimal getMaxGrossIndividualWeight() {
    return maxGrossIndividualWeight;
  }

  public String getModificationComments() {
    return modificationComments;
  }
}