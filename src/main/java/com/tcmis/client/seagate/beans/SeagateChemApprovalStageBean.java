package com.tcmis.client.seagate.beans;


import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SeagateChemApprovalStageBean <br>
 * @version: 1.0, Feb 4, 2014 <br>
 *****************************************************************************/


public class SeagateChemApprovalStageBean extends BaseDataBean {

	private BigDecimal requestorId;
	private BigDecimal requestId;
	private String facilityId;
	private String facilityDesc;
	private String buildingId;
	private String buildingDesc;
	private String processDesc;
	private BigDecimal requesterDeptId;
	private String catPartNo;
	private String chemicalName;
	private String seagateMsdsNumber;
	private String msdsTradeName;
	private String containerType;
	private String containerSize;
	private String unitOfMeasure;
	private String kitFlag;
	private BigDecimal radianRequestId;
	private BigDecimal seagateRequestId;
	private String locationId;
	private String locationDesc;
	private Date processedDate;
	private String processedStatus;
	private Date tcmLoadDate;
	private String seagateFileName;
	private String seagateZipfileName;
	private String craStatus;
	private BigDecimal requestStatus;
	private String catalogId;
	private String catalogCompanyId;


	//constructor
	public SeagateChemApprovalStageBean() {
	}

	//setters
	public void setRequestorId(BigDecimal requestorId) {
		this.requestorId = requestorId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityDesc(String facilityDesc) {
		this.facilityDesc = facilityDesc;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public void setBuildingDesc(String buildingDesc) {
		this.buildingDesc = buildingDesc;
	}
	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	public void setRequesterDeptId(BigDecimal requesterDeptId) {
		this.requesterDeptId = requesterDeptId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}
	public void setSeagateMsdsNumber(String seagateMsdsNumber) {
		this.seagateMsdsNumber = seagateMsdsNumber;
	}
	public void setMsdsTradeName(String msdsTradeName) {
		this.msdsTradeName = msdsTradeName;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public void setKitFlag(String kitFlag) {
		this.kitFlag = kitFlag;
	}
	public void setRadianRequestId(BigDecimal radianRequestId) {
		this.radianRequestId = radianRequestId;
	}
	public void setSeagateRequestId(BigDecimal seagateRequestId) {
		this.seagateRequestId = seagateRequestId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	public void setProcessedStatus(String processedStatus) {
		this.processedStatus = processedStatus;
	}
	public void setTcmLoadDate(Date tcmLoadDate) {
		this.tcmLoadDate = tcmLoadDate;
	}
	public void setSeagateFileName(String seagateFileName) {
		this.seagateFileName = seagateFileName;
	}
	public void setSeagateZipfileName(String seagateZipfileName) {
		this.seagateZipfileName = seagateZipfileName;
	}
	public void setCraStatus(String craStatus) {
		this.craStatus = craStatus;
	}
	public void setRequestStatus(BigDecimal requestStatus) {
		this.requestStatus = requestStatus;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public BigDecimal getRequestStatus() {
		return requestStatus;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public BigDecimal getRequestorId() {
		return requestorId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityDesc() {
		return facilityDesc;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public String getBuildingDesc() {
		return buildingDesc;
	}
	public String getProcessDesc() {
		return processDesc;
	}
	public BigDecimal getRequesterDeptId() {
		return requesterDeptId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getChemicalName() {
		return chemicalName;
	}
	public String getSeagateMsdsNumber() {
		return seagateMsdsNumber;
	}
	public String getMsdsTradeName() {
		return msdsTradeName;
	}
	public String getContainerType() {
		return containerType;
	}
	public String getContainerSize() {
		return containerSize;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public String getKitFlag() {
		return kitFlag;
	}
	public BigDecimal getRadianRequestId() {
		return radianRequestId;
	}
	public BigDecimal getSeagateRequestId() {
		return seagateRequestId;
	}
	public String getLocationId() {
		return locationId;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public Date getProcessedDate() {
		return processedDate;
	}
	public String getProcessedStatus() {
		return processedStatus;
	}
	public Date getTcmLoadDate() {
		return tcmLoadDate;
	}
	public String getSeagateFileName() {
		return seagateFileName;
	}
	public String getSeagateZipfileName() {
		return seagateZipfileName;
	}
	public String getCraStatus() {
		return craStatus;
	}

}