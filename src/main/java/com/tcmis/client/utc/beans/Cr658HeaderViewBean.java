package com.tcmis.client.utc.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: Cr658HeaderViewBean <br>
 * @version: 1.0, May 9, 2005 <br>
 *****************************************************************************/

public class Cr658HeaderViewBean
    extends BaseDataBean {

  private String recordId;
  private String bukrs;
  private String xblnr;
  private Date bldat;
  private String lifnr;
  private String bktxt;
  private String cblnr;
  private String waers;
  private BigDecimal wrbtr;
  private BigDecimal wmwst;
  private String facilityId;
  private BigDecimal invoice;
  private BigDecimal issueId;
  private BigDecimal issueCostRevision;
  private BigDecimal counter;
  private String clockid;

  private Collection cr658ItemViewBeanCollection = new Vector();

  //constructor
  public Cr658HeaderViewBean() {
  }

  //setters
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  public void setBukrs(String bukrs) {
    this.bukrs = bukrs;
  }

  public void setXblnr(String xblnr) {
    this.xblnr = xblnr;
  }

  public void setBldat(Date bldat) {
    this.bldat = bldat;
  }

  public void setLifnr(String lifnr) {
    this.lifnr = lifnr;
  }

  public void setBktxt(String bktxt) {
    this.bktxt = bktxt;
  }

  public void setCblnr(String cblnr) {
    this.cblnr = cblnr;
  }

  public void setWaers(String waers) {
    this.waers = waers;
  }

  public void setWrbtr(BigDecimal wrbtr) {
    this.wrbtr = wrbtr;
  }

  public void setWmwst(BigDecimal wmwst) {
    this.wmwst = wmwst;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setIssueId(BigDecimal issueId) {
    this.issueId = issueId;
  }

  public void setIssueCostRevision(BigDecimal issueCostRevision) {
    this.issueCostRevision = issueCostRevision;
  }

  public void setCounter(BigDecimal counter) {
    this.counter = counter;
  }

  public void setClockid(String clockid) {
    this.clockid = clockid;
  }

  public void setCr658ItemViewBeanCollection(Collection c) {
    this.cr658ItemViewBeanCollection = c;
  }

  public void addCr658ItemViewBean(Cr658ItemViewBean b) {
    this.cr658ItemViewBeanCollection.add(b);
  }

  //getters
  public String getRecordId() {
    return recordId;
  }

  public String getBukrs() {
    return bukrs;
  }

  public String getXblnr() {
    return xblnr;
  }

  public Date getBldat() {
    return bldat;
  }

  public String getLifnr() {
    return lifnr;
  }

  public String getBktxt() {
    return bktxt;
  }

  public String getCblnr() {
    return cblnr;
  }

  public String getWaers() {
    return waers;
  }

  public BigDecimal getWrbtr() {
    return wrbtr;
  }

  public BigDecimal getWmwst() {
    return wmwst;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getIssueId() {
    return issueId;
  }

  public BigDecimal getIssueCostRevision() {
    return issueCostRevision;
  }

  public BigDecimal getCounter() {
    return counter;
  }

  public String getClockid() {
    return clockid;
  }

  public Collection getCr658ItemViewBeanCollection() {
    return this.cr658ItemViewBeanCollection;
  }
}
