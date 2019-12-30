package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: MaxInvoicePeriodBean <br>
 * @version: 1.0, Feb 8, 2005 <br>
 *****************************************************************************/

public class MaxInvoicePeriodBean
    extends BaseDataBean {

  private BigDecimal maxPeriod;

  //constructor
  public MaxInvoicePeriodBean() {
  }

  //setters
  public void setMaxPeriod(BigDecimal maxPeriod) {
    this.maxPeriod = maxPeriod;
  }

  //getters
  public BigDecimal getMaxPeriod() {
    return maxPeriod;
  }
}