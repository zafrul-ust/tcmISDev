package com.tcmis.client.cxml.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PunchoutSetupRequestBean <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class PunchOutSetupResponseBean
    extends BaseDataBean {

  private String payloadId;
  private String timestamp;
  private String statusCode;
  private String statusText;
  private String startPageUrl;

  //constructor
  public PunchOutSetupResponseBean() {
  }

  //setters
  public void setPayloadId(String payloadId) {
    this.payloadId = payloadId;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public void setStatusText(String statusText) {
    this.statusText = statusText;
  }

  public void setStartPageUrl(String startPageUrl) {
    this.startPageUrl = startPageUrl;
  }

  //getters
  public String getPayloadId() {
    return payloadId;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public String getStatusText() {
    return statusText;
  }

  public String getStartPageUrl() {
    return startPageUrl;
  }
}