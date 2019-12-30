package com.tcmis.client.report.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacAppDockDpViewBean <br>
 * @version: 1.0, Mar 1, 2006 <br>
 *****************************************************************************/

public class FacAppDockDpViewBean
    extends BaseDataBean {

  private BigDecimal personnelId;
  private String facilityId;
  private String dockLocationId;
  private String dockDesc;
  private String deliveryPoint;
  private String deliveryPointDesc;

  private Collection dockBeanCollection = new Vector();
  private Collection deliveryPointBeanCollection = new Vector();
  //constructor
  public FacAppDockDpViewBean() {
  }

  //setters
  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setDockLocationId(String dockLocationId) {
    this.dockLocationId = dockLocationId;
  }

  public void setDockDesc(String dockDesc) {
    this.dockDesc = dockDesc;
  }

  public void setDeliveryPoint(String deliveryPoint) {
    this.deliveryPoint = deliveryPoint;
  }

  public void setDeliveryPointDesc(String deliveryPointDesc) {
    this.deliveryPointDesc = deliveryPointDesc;
  }

  public void setDeliveryPointBeanCollection(Collection c) {
    this.deliveryPointBeanCollection = c;
  }

  public void setDockBeanCollection(Collection c) {
    this.dockBeanCollection = c;
  }

  public void addDockBean(FacAppDockDpViewBean bean) {
    this.dockBeanCollection.add(bean);
  }

  public void addDeliveryPointBean(FacAppDockDpViewBean bean) {
    this.deliveryPointBeanCollection.add(bean);
  }

  //getters
  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getDockLocationId() {
    return dockLocationId;
  }

  public String getDockDesc() {
    return dockDesc;
  }

  public String getDeliveryPoint() {
    return deliveryPoint;
  }

  public String getDeliveryPointDesc() {
    return deliveryPointDesc;
  }

  public Collection getDockBeanCollection() {
    return this.dockBeanCollection;
  }

  public Collection getDeliveryPointBeanCollection() {
    return this.deliveryPointBeanCollection;
  }
}