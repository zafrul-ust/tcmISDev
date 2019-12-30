package radian.tcmis.internal.admin.beans;

import java.util.Collection;
import radian.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacilityBean <br>
 * @version: 1.0, Mar 31, 2004 <br>
 *****************************************************************************/

public class FacilityBean
    extends BaseDataBean {

  private String facilityId;
  private String companyId;
  private String facilityType;
  private String mailLocation;
  private String facilityName;
  private String shippingLocation;
  private String branchPlant;
  private String preferredWarehouse;
  private int jdeId;
  private int jdeBillTo;
  private String active;
  private String epaId;
  private String stateGeneratorId;
  private String timeZone;
  private int wasteTransferSetPoint;
  private String ecommerce;
  private int programContact;
  private String eGlSegment2;
  private String inventoryGroupDefault;
  private String processDetailRequired;
  private int companyFacilityId;
  private String companyApproverId;
  private String priceGroupId;
  private String catAddApprovalDetailNeeded;
  private String salesTaxBasis;
  private String paySalesTax;
  private String useLimitsRestriction;
  private String plantId;
  private String bldgId;
  private String divisionId;
  private String sicCode;
  private String dunAndBradstreetNumber;
  private String physicalLocation;
  private String reportType;
  private Collection locationBeanCollection;

  //constructor
  public FacilityBean() {
  }

  //setters
  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setFacilityType(String facilityType) {
    this.facilityType = facilityType;
  }

  public void setMailLocation(String mailLocation) {
    this.mailLocation = mailLocation;
  }

  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  public void setShippingLocation(String shippingLocation) {
    this.shippingLocation = shippingLocation;
  }

  public void setBranchPlant(String branchPlant) {
    this.branchPlant = branchPlant;
  }

  public void setPreferredWarehouse(String preferredWarehouse) {
    this.preferredWarehouse = preferredWarehouse;
  }

  public void setJdeId(int jdeId) {
    this.jdeId = jdeId;
  }

  public void setJdeBillTo(int jdeBillTo) {
    this.jdeBillTo = jdeBillTo;
  }

  public void setActive(String active) {
    this.active = active;
  }

  public void setEpaId(String epaId) {
    this.epaId = epaId;
  }

  public void setStateGeneratorId(String stateGeneratorId) {
    this.stateGeneratorId = stateGeneratorId;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public void setWasteTransferSetPoint(int wasteTransferSetPoint) {
    this.wasteTransferSetPoint = wasteTransferSetPoint;
  }

  public void setEcommerce(String ecommerce) {
    this.ecommerce = ecommerce;
  }

  public void setProgramContact(int programContact) {
    this.programContact = programContact;
  }

  public void setEGlSegment2(String eGlSegment2) {
    this.eGlSegment2 = eGlSegment2;
  }

  public void setInventoryGroupDefault(String inventoryGroupDefault) {
    this.inventoryGroupDefault = inventoryGroupDefault;
  }

  public void setProcessDetailRequired(String processDetailRequired) {
    this.processDetailRequired = processDetailRequired;
  }

  public void setCompanyFacilityId(int companyFacilityId) {
    this.companyFacilityId = companyFacilityId;
  }

  public void setCompanyApproverId(String companyApproverId) {
    this.companyApproverId = companyApproverId;
  }

  public void setPriceGroupId(String priceGroupId) {
    this.priceGroupId = priceGroupId;
  }

  public void setCatAddApprovalDetailNeeded(String catAddApprovalDetailNeeded) {
    this.catAddApprovalDetailNeeded = catAddApprovalDetailNeeded;
  }

  public void setSalesTaxBasis(String salesTaxBasis) {
    this.salesTaxBasis = salesTaxBasis;
  }

  public void setPaySalesTax(String paySalesTax) {
    this.paySalesTax = paySalesTax;
  }

  public void setUseLimitsRestriction(String useLimitsRestriction) {
    this.useLimitsRestriction = useLimitsRestriction;
  }

  public void setPlantId(String plantId) {
    this.plantId = plantId;
  }

  public void setBldgId(String bldgId) {
    this.bldgId = bldgId;
  }

  public void setDivisionId(String divisionId) {
    this.divisionId = divisionId;
  }

  public void setSicCode(String sicCode) {
    this.sicCode = sicCode;
  }

  public void setDunAndBradstreetNumber(String dunAndBradstreetNumber) {
    this.dunAndBradstreetNumber = dunAndBradstreetNumber;
  }

  public void setPhysicalLocation(String physicalLocation) {
    this.physicalLocation = physicalLocation;
  }

  public void setReportType(String reportType) {
    this.reportType = reportType;
  }

  public void setLocationBeanCollection(Collection locationBeanCollection) {
    this.locationBeanCollection = locationBeanCollection;
  }

  //getters
  public String getFacilityId() {
    return facilityId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getFacilityType() {
    return facilityType;
  }

  public String getMailLocation() {
    return mailLocation;
  }

  public String getFacilityName() {
    return facilityName;
  }

  public String getShippingLocation() {
    return shippingLocation;
  }

  public String getBranchPlant() {
    return branchPlant;
  }

  public String getPreferredWarehouse() {
    return preferredWarehouse;
  }

  public int getJdeId() {
    return jdeId;
  }

  public int getJdeBillTo() {
    return jdeBillTo;
  }

  public String getActive() {
    return active;
  }

  public String getEpaId() {
    return epaId;
  }

  public String getStateGeneratorId() {
    return stateGeneratorId;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public int getWasteTransferSetPoint() {
    return wasteTransferSetPoint;
  }

  public String getEcommerce() {
    return ecommerce;
  }

  public int getProgramContact() {
    return programContact;
  }

  public String getEGlSegment2() {
    return eGlSegment2;
  }

  public String getInventoryGroupDefault() {
    return inventoryGroupDefault;
  }

  public String getProcessDetailRequired() {
    return processDetailRequired;
  }

  public int getCompanyFacilityId() {
    return companyFacilityId;
  }

  public String getCompanyApproverId() {
    return companyApproverId;
  }

  public String getPriceGroupId() {
    return priceGroupId;
  }

  public String getCatAddApprovalDetailNeeded() {
    return catAddApprovalDetailNeeded;
  }

  public String getSalesTaxBasis() {
    return salesTaxBasis;
  }

  public String getPaySalesTax() {
    return paySalesTax;
  }

  public String getUseLimitsRestriction() {
    return useLimitsRestriction;
  }

  public String getPlantId() {
    return plantId;
  }

  public String getBldgId() {
    return bldgId;
  }

  public String getDivisionId() {
    return divisionId;
  }

  public String getSicCode() {
    return sicCode;
  }

  public String getDunAndBradstreetNumber() {
    return dunAndBradstreetNumber;
  }

  public String getPhysicalLocation() {
    return physicalLocation;
  }

  public String getReportType() {
    return reportType;
  }

  public Collection getLocationBeanCollection() {
    return locationBeanCollection;
  }
}
