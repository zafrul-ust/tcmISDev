package com.tcmis.internal.msds.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: MillerMsdsSearchBean <br>
 * @version: 1.0, Jun 1, 2005 <br>
 *****************************************************************************/

public class MillerMsdsSearchBean
    extends BaseDataBean {

  private String customerMsdsNo;
  private String tradeName;
  private String catPartNoString;
  private String manufacturer;
  private String materialId;
  private String content;
  private String onLine;
  private String department;
  private String status;
  private String hazardClassification;

  //constructor
  public MillerMsdsSearchBean() {
  }

  //setters
  public void setCustomerMsdsNo(String customerMsdsNo) {
    this.customerMsdsNo = customerMsdsNo;
  }

  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }

  public void setCatPartNoString(String catPartNoString) {
    this.catPartNoString = catPartNoString;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public void setMaterialId(String materialId) {
    this.materialId = materialId;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setOnLine(String onLine) {
    this.onLine = onLine;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setHazardClassification(String s) {
    this.hazardClassification = s;
  }
  //getters
  public String getCustomerMsdsNo() {
    return customerMsdsNo;
  }

  public String getTradeName() {
    return tradeName;
  }

  public String getCatPartNoString() {
    return catPartNoString;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String getMaterialId() {
    return materialId;
  }

  public String getContent() {
    return content;
  }

  public String getOnLine() {
    return onLine;
  }

  public String getDepartment() {
    return department;
  }

  public String getStatus() {
    return status;
  }

  public String getHazardClassification() {
    return this.hazardClassification;
  }
}