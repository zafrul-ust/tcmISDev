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

public class FacilityOvBean
    extends BaseDataBean {

  private Collection dockCollection;
  private String facilityId;

  //constructor
  public FacilityOvBean() {
  }

  //setters
  public void setDockCollection(Collection dockCollection) {
    this.dockCollection = dockCollection;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  //getters

  public Collection getDockCollection() {
    return dockCollection;
  }


  public String getFacilityId() {
    return facilityId;
  }
}
