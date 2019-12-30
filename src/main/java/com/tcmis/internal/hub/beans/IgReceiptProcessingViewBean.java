package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: IgReceiptProcessingViewBean <br>
 * @version: 1.0, Nov 6, 2006 <br>
 *****************************************************************************/

public class IgReceiptProcessingViewBean
    extends BaseDataBean {

  private String inventoryGroup;
  private String receiptProcessingMethod;
  private String hub;
  private String issueGeneration;
  private String homeCompanyId;
  private String homeCompanyShortName;
  private String receiptProcessingMethodDesc;

  //constructor
  public IgReceiptProcessingViewBean() {
  }

  //setters
  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setReceiptProcessingMethod(String receiptProcessingMethod) {
    this.receiptProcessingMethod = receiptProcessingMethod;
  }

  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setIssueGeneration(String issueGeneration) {
    this.issueGeneration = issueGeneration;
  }

  public void setHomeCompanyId(String homeCompanyId) {
    this.homeCompanyId = homeCompanyId;
  }

  public void setHomeCompanyShortName(String homeCompanyShortName) {
    this.homeCompanyShortName = homeCompanyShortName;
  }

  public void setReceiptProcessingMethodDesc(String receiptProcessingMethodDesc) {
    this.receiptProcessingMethodDesc = receiptProcessingMethodDesc;
  }

  //getters
  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getReceiptProcessingMethod() {
    return receiptProcessingMethod;
  }

  public String getHub() {
    return hub;
  }

  public String getIssueGeneration() {
    return issueGeneration;
  }

  public String getHomeCompanyId() {
    return homeCompanyId;
  }

  public String getHomeCompanyShortName() {
    return homeCompanyShortName;
  }

  public String getReceiptProcessingMethodDesc() {
    return receiptProcessingMethodDesc;
  }
}