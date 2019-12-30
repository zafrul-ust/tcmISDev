package com.tcmis.client.report.beans;
import java.util.Collection;
import java.util.Vector;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: WasteFacAppActVendorViewBean <br>
 * @version: 1.0, Mar 8, 2006 <br>
 *****************************************************************************/

public class WasteFacAppActVendorViewBean
    extends BaseDataBean {

  private String facilityId;
  private String wasteLocationId;
  private String application;
  private String applicationDesc;
  private String vendorId;
  private String companyName;
  private String facilityName;
  private String wasteLocationDesc;
  //these fields are for the normalized version
  private Collection applicationCollection = new Vector();
  private Collection wasteLocationIdCollection = new Vector();
  private Collection vendorIdCollection = new Vector();

  //constructor
  public WasteFacAppActVendorViewBean() {
  }

  //setters
  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setWasteLocationId(String wasteLocationId) {
    this.wasteLocationId = wasteLocationId;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setApplicationDesc(String applicationDesc) {
    this.applicationDesc = applicationDesc;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  public void setWasteLocationDesc(String wasteLocationDesc) {
    this.wasteLocationDesc = wasteLocationDesc;
  }

  public void setApplicationCollection(Collection c) {
    this.applicationCollection = c;
  }

  public void setWasteLocationIdCollection(Collection c) {
    this.wasteLocationIdCollection = c;
  }

  public void setVendorIdCollection(Collection c) {
    this.vendorIdCollection = c;
  }
  //getters
  public String getFacilityId() {
    return facilityId;
  }

  public String getWasteLocationId() {
    return wasteLocationId;
  }

  public String getApplication() {
    return application;
  }

  public String getApplicationDesc() {
    return applicationDesc;
  }

  public String getVendorId() {
    return vendorId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getFacilityName() {
    return facilityName;
  }

  public String getWasteLocationDesc() {
    return wasteLocationDesc;
  }

  public Collection getApplicationCollection() {
    return this.applicationCollection;
  }

  public Collection getWasteLocationIdCollection() {
    return this.wasteLocationIdCollection;
  }

  public Collection getVendorIdCollection() {
    return this.vendorIdCollection;
  }

  public void addApplicationBean(WasteFacAppActVendorViewBean bean) {
    this.applicationCollection.add(bean);
  }

  public void addWasteLocationBean(WasteFacAppActVendorViewBean bean) {
    this.wasteLocationIdCollection.add(bean);
  }

  public void addVendorBean(WasteFacAppActVendorViewBean bean) {
    this.vendorIdCollection.add(bean);
  }
}