package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.DateHandler;

/******************************************************************************
 * CLASSNAME: HetOpsAirViewBean <br>
 * @version: 1.0, Oct 25, 2011 <br>
 *****************************************************************************/

public class HetOpsAirViewBean extends BaseDataBean {

	private BigDecimal amountRemaining;
	private String applicationDesc;
	private BigDecimal applicationId;
	private String applicationMethod;
	private String areaName;
	private String buildingName;
	private BigDecimal cartId;
	private String catPartNo;
	private String companyId;
	private String containerId;
	private String controlDevice;
	private String deletionCode;
	private String deptName;
	private String diluent;
	private String discarded;
	private String employee;
	private String exported;
	private Date exportedDate;
	private String facilityId;
	private Date insertDate;
	private BigDecimal itemId;
	private BigDecimal kitId;
	private String modified;
	private Date modifiedDate;
	private String msdsNo;
	private String painted;
	private BigDecimal partId;
	private String partType;
	private String permit;
	private BigDecimal quantity;
	private BigDecimal quantityUsedLb;
	private String remarks;
	private String reportingEntityId;
	private String solvent;
	private String substrate;
	private BigDecimal transactionId;
	private String unitOfMeasure;
	private Date usageDate;
	private BigDecimal usageId;
	private BigDecimal userId;
	private BigDecimal vocLbPerGal;
	private BigDecimal vocPercent;
	private BigDecimal wasteQuantityLb;

	//constructor
	public HetOpsAirViewBean() {
	}

	public BigDecimal getAmountRemaining() {
		return amountRemaining;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public String getApplicationMethod() {
		return applicationMethod;
	}

	public String getAreaName() {
		return areaName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public BigDecimal getCartId() {
		return cartId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getControlDevice() {
		return controlDevice;
	}

	public String getDeletionCode() {
		return deletionCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getDiluent() {
		return diluent;
	}

	public String getDiscarded() {
		return discarded;
	}

	public String getEmployee() {
		return employee;
	}

	public String getExported() {
		return exported;
	}

	public Date getExportedDate() {
		return exportedDate;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKitId() {
		return kitId;
	}

	public String getModified() {
		return modified;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getMsdsNo() {
		return msdsNo;
	}

	public String getPainted() {
		if ("F".equals(getPartType()) || "R".equals(getPartType()) ) {
			return "F";
		}
		return painted;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public String getPartType() {
		return partType;
	}

	public String getPermit() {
		return permit;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getQuantityUsedLb() {
		return quantityUsedLb;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public String getSolvent() {
		return solvent;
	}

	public String getSubstrate() {
		return substrate;
	}

	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public Date getUsageDate() {
		return usageDate;
	}

	public BigDecimal getUsageId() {
		return usageId;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public BigDecimal getVocLbPerGal() {
		return vocLbPerGal;
	}

	public BigDecimal getVocPercent() {
		return vocPercent;
	}

	public BigDecimal getWasteQuantityLb() {
		return wasteQuantityLb;
	}

	public boolean isModified() {
		return !"Y".equals(modified);
	}

	public void setAmountRemaining(BigDecimal amountRemaining) {
		this.amountRemaining = amountRemaining;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicationMethod(String applicationMethod) {
		this.applicationMethod = applicationMethod;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public void setCartId(BigDecimal cartId) {
		this.cartId = cartId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setControlDevice(String controlDevice) {
		this.controlDevice = controlDevice;
	}

	public void setDeletionCode(String deletionCode) {
		this.deletionCode = deletionCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setDiluent(String diluent) {
		this.diluent = diluent;
	}

	public void setDiscarded(String discarded) {
		this.discarded = discarded;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public void setExported(String exported) {
		this.exported = exported;
	}

	public void setExportedDate(Date exportedDate) {
		this.exportedDate = exportedDate;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKitId(BigDecimal kitId) {
		this.kitId = kitId;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setMsdsNo(String msdsNo) {
		this.msdsNo = msdsNo;
	}

	public void setPainted(String painted) {
		this.painted = painted;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public void setPermit(String permit) {
		this.permit = permit;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setQuantityUsedLb(BigDecimal quantityUsedLb) {
		this.quantityUsedLb = quantityUsedLb;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public void setSolvent(String solvent) {
		this.solvent = solvent;
	}

	public void setSubstrate(String substrate) {
		this.substrate = substrate;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public void setUsageDate(Date usageDate) {
		this.usageDate = usageDate;
	}

	public void setUsageId(BigDecimal usageId) {
		this.usageId = usageId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public void setVocLbPerGal(BigDecimal vocLbPerGal) {
		this.vocLbPerGal = vocLbPerGal;
	}

	public void setVocPercent(BigDecimal vocPercent) {
		this.vocPercent = vocPercent;
	}

	public void setWasteQuantityLb(BigDecimal wasteQuantityLb) {
		this.wasteQuantityLb = wasteQuantityLb;
	}

	@Override
	public String toString() {
		DecimalFormat lbsFormat = new DecimalFormat("0000000000000000.000000");
		StringBuilder line = new StringBuilder();
		line.append(getTransactionId()).append(","); // Record id
		line.append(DateHandler.formatDate(getInsertDate(), "MM/dd/yyyy")).append(","); // Entry Date
		line.append(","); // Crib, always blank
		line.append(getEmployee()).append(","); // employee from logon_id
		line.append(getDeptName()).append(","); // Dept_usage
		line.append(getBuildingName()).append(","); // BLDG_usage
		line.append(getAreaName()).append(","); // PLT_usage, using AREA
		line.append(getPermit()).append(","); // PERMITVENT,
		line.append(getPartType()).append(","); // FLYING (F,N or R)
		line.append(getPainted()).append(","); // PAINTED (F,N,Y) F if AIRCRAFT or AIRCRAFT_REWORK
		line.append(getSubstrate()).append(","); // Substrate
		line.append(getApplicationMethod()).append(","); // APPL_METHOD
		line.append(getMsdsNo()).append(","); // ENTRY_INDEX (Use MSDS_NO)
		line.append(getMsdsNo()).append(","); // MSDS_NO
		line.append(lbsFormat.format(getQuantityUsedLb())).append(","); // QTY_USED_LBS, zero filled 16 digits, decimal point, 6 digits
		line.append(lbsFormat.format(getWasteQuantityLb())).append(","); // WASTE_QTY
		line.append(getApplicationDesc()).append(","); // CRIB_LOC -> use application_desc
		line.append(DateHandler.formatDate(getUsageDate(), "MM/dd/yyyy")).append(","); // Transaction Date
		line.append("01,"); // DIV_CHAR, hard_coded, non-pertinent
		line.append(getContainerId()).append(","); // CONTAINER
		line.append(DateHandler.formatDate(getInsertDate(), "yyyyMMdd")).append(","); // OVR_TIME
		line.append(getInsertDate().getHours() * 3600 + getInsertDate().getMinutes() * 60 + getInsertDate().getSeconds()).append(","); // TRAN_TIME
		line.append("0").append(","); // SEQ_NO
		line.append(isModified() ? "U" : "W").append(","); // TRAN_TYP
		line.append(isModified() ? "C" : " ").append(","); // REASON
		line.append(","); // REF_NO
		//		line.append().append(","); //
		//		line.append().append(","); //
		//		line.append().append(","); //
		//		line.append().append(","); //
		//		line.append().append(","); //
		//		line.append().append(","); //
		//		line.append().append(","); //
		//		line.append().append(","); //
		//		line.append().append(","); //
		return line.toString();
	}
}