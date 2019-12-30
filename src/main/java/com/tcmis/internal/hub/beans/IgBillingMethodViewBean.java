package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: IgBillingMethodViewBean <br>
 * @version: 1.0, Nov 27, 2006 <br>
 *****************************************************************************/

public class IgBillingMethodViewBean
    extends BaseDataBean {

  private String inventoryGroup;
  private String billingMethod;
  private String hub;
  private String issueGeneration;
  private String homeCompanyId;
  private String homeCompanyShortName;
  private String billingMethodDesc;

  //constructor
  public IgBillingMethodViewBean() {
  }

  //setters
  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setBillingMethod(String billingMethod) {
    this.billingMethod = billingMethod;
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

  public void setBillingMethodDesc(String billingMethodDesc) {
    this.billingMethodDesc = billingMethodDesc;
  }

  //getters
  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getBillingMethod() {
    return billingMethod;
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

  public String getBillingMethodDesc() {
    return billingMethodDesc;
  }
}