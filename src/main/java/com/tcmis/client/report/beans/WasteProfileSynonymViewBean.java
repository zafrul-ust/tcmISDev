package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: WasteProfileSynonymViewBean <br>
 * @version: 1.0, Mar 3, 2006 <br>
 *****************************************************************************/

public class WasteProfileSynonymViewBean
    extends BaseDataBean {

  private String vendorId;
  private String vendorProfileId;
  private String description;
  private String searchString;

  //constructor
  public WasteProfileSynonymViewBean() {
  }

  //setters
  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public void setVendorProfileId(String vendorProfileId) {
    this.vendorProfileId = vendorProfileId;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setSearchString(String searchString) {
    this.searchString = searchString;
  }

  //getters
  public String getVendorId() {
    return vendorId;
  }

  public String getVendorProfileId() {
    return vendorProfileId;
  }

  public String getDescription() {
    return description;
  }

  public String getSearchString() {
    return searchString;
  }
}