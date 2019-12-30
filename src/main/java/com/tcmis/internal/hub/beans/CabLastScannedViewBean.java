package com.tcmis.internal.hub.beans;

import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: CabLastScannedViewBean <br>
 * @version: 1.0, Jul 29, 2004 <br>
 *****************************************************************************/

public class CabLastScannedViewBean
    extends BaseDataBean {

  private String companyId;
  private String application;
  private Date lastScanned;

  //constructor
  public CabLastScannedViewBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setLastScanned(Date lastScanned) {
    this.lastScanned = lastScanned;
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public String getApplication() {
    return application;
  }

  public Date getLastScanned() {
    return lastScanned;
  }
}