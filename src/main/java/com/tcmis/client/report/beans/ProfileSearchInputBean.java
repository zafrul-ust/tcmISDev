package com.tcmis.client.report.beans;

import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: AdHocUsageInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/

public class ProfileSearchInputBean
    extends BaseDataBean {

  private String vendorId;
  private String searchText;
  private String searchButton;
  //constructor
  public ProfileSearchInputBean() {
  }

  //setters
  public void setVendorId(String s) {
    this.vendorId = s;
  }

  public void setSearchText(String s) {
    this.searchText = s;
  }

  public void setSearchButton(String s) {
    this.searchButton = s;
  }

  //getters
  public String getVendorId() {
    return this.vendorId;
  }

  public String getSearchText() {
    return this.searchText;
  }

  public String getSearchButton() {
    return this.searchButton;
  }

} //end of class