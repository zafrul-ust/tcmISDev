package com.tcmis.client.raytheon.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TransportDataBean <br>
 * @version: 1.0, Jun 28, 2005 <br>
 *****************************************************************************/

public class TransportDataBean
    extends BaseDataBean {

  private java.lang.String transportId;
  private java.lang.String shippingInstructions;

  //constructor
  public TransportDataBean() {
  }

  //setters
  public void setTransportId(java.lang.String transportId) {
    this.transportId = transportId;
  }

  public void setShippingInstructions(java.lang.String shippingInstructions) {
    this.shippingInstructions = shippingInstructions;
  }

  //getters
  public java.lang.String getTransportId() {
    return transportId;
  }

  public java.lang.String getShippingInstructions() {
    return shippingInstructions;
  }
}