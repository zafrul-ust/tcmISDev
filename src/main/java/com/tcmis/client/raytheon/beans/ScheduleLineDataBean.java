package com.tcmis.client.raytheon.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ScheduleLineDataBean <br>
 * @version: 1.0, Jun 29, 2005 <br>
 *****************************************************************************/

public class ScheduleLineDataBean
    extends BaseDataBean {

  private java.lang.String scheduleLineId;
  private java.lang.String quantityValue;
  private java.lang.String uomCoded;
  private java.lang.String requestedDeliveryDate;

  //constructor
  public ScheduleLineDataBean() {
  }

  //setters
  public void setScheduleLineId(java.lang.String scheduleLineId) {
    this.scheduleLineId = scheduleLineId;
  }

  public void setQuantityValue(java.lang.String quantityValue) {
    this.quantityValue = quantityValue;
  }

  public void setUomCoded(java.lang.String uomCoded) {
    this.uomCoded = uomCoded;
  }

  public void setRequestedDeliveryDate(java.lang.String requestedDeliveryDate) {
    this.requestedDeliveryDate = requestedDeliveryDate;
  }

  //getters
  public java.lang.String getScheduleLineId() {
    return scheduleLineId;
  }

  public java.lang.String getQuantityValue() {
    return quantityValue;
  }

  public java.lang.String getUomCoded() {
    return uomCoded;
  }

  public java.lang.String getRequestedDeliveryDate() {
    return requestedDeliveryDate;
  }
}