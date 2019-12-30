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

public class CompanyOvBean
    extends BaseDataBean {

  private Collection facilityIdCollection;
  private String companyId;

  //constructor
  public CompanyOvBean() {
  }

  //setters
  public void setFacilityIdCollection(Collection facilityIdCollection) {
    this.facilityIdCollection = facilityIdCollection;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  //getters

  public Collection getFacilityIdCollection() {
    return facilityIdCollection;
  }


  public String getCompanyId() {
    return companyId;
  }
}
