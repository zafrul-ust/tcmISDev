package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class HubFacDockViewOvBean
    extends BaseDataBean {

  private BigDecimal personnelId;
  private Collection companyIdCollection;
  private String hub;
  private String hubName;

  //constructor
  public HubFacDockViewOvBean() {
  }

  //setters
  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setCompanyIdCollection(Collection companyIdCollection) {
    this.companyIdCollection = companyIdCollection;
  }

  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setHubName(String hubName) {
    this.hubName = hubName;
  }

  //getters
  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public Collection getCompanyIdCollection() {
    return companyIdCollection;
  }

  public String getHub() {
    return hub;
  }

  public String getHubName() {
    return hubName;
  }

}
