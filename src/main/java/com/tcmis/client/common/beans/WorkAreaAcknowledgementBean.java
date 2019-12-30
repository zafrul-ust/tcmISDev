package com.tcmis.client.common.beans;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetBinItemViewBean <br>
 * 
 * @version: 1.0, Sep 6, 2007 <br>
 * 
 *           Note that the field "quantity" is not in the database view;
 * 
 *****************************************************************************/

public class WorkAreaAcknowledgementBean extends BaseDataBean {

	private String		application;
	private String		applicationDesc;
	private BigDecimal	binId;
	private String		binName;
	private BigDecimal	cabinetId;
	private String		cabinetName;
	private int			cabinetRowspan;
	private ArrayList	cabinetsList;
	private Date		cabinetStartDate;
	private String		catalogDesc;
	private String		catPartNo;
	private String		companyId;
	private String		countType;
	private String		cpigStatus;
	private String		description;
	private String		facilityId;
	private BigDecimal	itemId;
	private ArrayList	itemsList;
	private String		packaging;
	private Date		disbursedDate;
	private Date		acknowledgedDate;
	private Date		dateProcessed;
	private BigDecimal	countQuantity;
	private String		startupCountNeeded;
	private String		updatedStatus;
	private String		useApplication;
	private String disbursedBy;
	private String acknowledgedBy;
	private BigDecimal cabinetCountId;


	// constructor
	public WorkAreaAcknowledgementBean() {
	}

	public String getApplication() {
		return application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public BigDecimal getBinId() {
		return binId;
	}

	public String getBinName() {
		return binName;
	}

	public BigDecimal getCabinetId() {
		return cabinetId;
	}

	public String getCabinetName() {
		return cabinetName;
	}

	public int getCabinetRowspan() {
		return cabinetRowspan;
	}

	public ArrayList getCabinetsList() {
		return cabinetsList;
	}

	public Date getCabinetStartDate() {
		return cabinetStartDate;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	// getters
	public String getCompanyId() {
		return companyId;
	}

	public String getCountType() {
		return countType;
	}

	public String getCpigStatus() {
		return cpigStatus;
	}

	public String getDescription() {
		return description;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public ArrayList getItemsList() {
		return itemsList;
	}

	public String getPackaging() {
		return packaging;
	}

	public Date getDisbursedDate() {
		return disbursedDate;
	}
		
	public Date getDateProcessed() {
		return dateProcessed;
	}

	public BigDecimal getCountQuantity() {
		return countQuantity;
	}

	public String getStartupCountNeeded() {
		return startupCountNeeded;
	}

	public String getUpdatedStatus() {
		return updatedStatus;
	}

	public String getUseApplication() {
		return useApplication;
	}

	public String getDisbursedBy() {
		return disbursedBy;
	}
	
	public Date getAcknowledgedDate() {
		return acknowledgedDate;
	}
	
	public BigDecimal getCabinetCountId() {
		return cabinetCountId;
	}
	
	public void setCabinetCountId(BigDecimal cabinetCountId) {
		this.cabinetCountId = cabinetCountId;
	}

	public void setDisbursedBy(String disbursedBy) {
		this.disbursedBy = disbursedBy;
	}
	public String getAcknowledgedBy() {
		return acknowledgedBy;
	}
	public void setAcknowledgedBy(String acknowledgedBy) {
		this.acknowledgedBy = acknowledgedBy;
	}
	
	public boolean isAdvancedReceipt() {
		return "ADVRECEIPT".equals(countType);
	}

	public boolean isAutomaticRefill() {
		// return "AutomaticRefill".equals(countType);
		return !isAdvancedReceipt() && StringUtils.isNotBlank(countType) && countType.startsWith("A");
	}

	public boolean isCountByItemId() {
		// return "ITEM_ID".equals(countType);
		return StringUtils.isNotBlank(countType) && countType.startsWith("I");
	}

	public boolean isCountByLevel() {
		// return "PART".equals(countType);
		return StringUtils.isNotBlank(countType) && countType.startsWith("L");
	}

	public boolean isCountByPart() {
		// return "PART".equals(countType);
		return StringUtils.isNotBlank(countType) && countType.startsWith("P");
	}

	public boolean isCountByReceipt() {
		// return StringUtils.isNotBlank(countType) &&
		// countType.startsWith("RECEIPT");
		return StringUtils.isNotBlank(countType) && countType.startsWith("R");
	}

	public boolean isKanBanCount() {
		// return "KanBan".equals(countType);
		return StringUtils.isNotBlank(countType) && countType.startsWith("K");
	}
	
	
	public void setAcknowledgedDate(Date acknowledgedDate) {
		 this.acknowledgedDate = acknowledgedDate;
	}
	
	public void setDateProcessed(Date dateProcessed) {
		 this.dateProcessed = dateProcessed;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public void setCabinetId(BigDecimal cabinetId) {
		this.cabinetId = cabinetId;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	public void setCabinetRowspan(int cabinetRowspan) {
		this.cabinetRowspan = cabinetRowspan;
	}

	public void setCabinetsList(ArrayList cabinetsList) {
		this.cabinetsList = cabinetsList;
	}

	public void setCabinetStartDate(Date cabinetStartDate) {
		this.cabinetStartDate = cabinetStartDate;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	// setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setCpigStatus(String cpigStatus) {
		this.cpigStatus = cpigStatus;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemsList(ArrayList itemsList) {
		this.itemsList = itemsList;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setDisbursedDate(Date disbursedDate) {
		this.disbursedDate = disbursedDate;
	}

	public void setCountQuantity(BigDecimal countQuantity) {
		this.countQuantity = countQuantity;
	}

	public void setStartupCountNeeded(String startupCountNeeded) {
		this.startupCountNeeded = startupCountNeeded;
	}

	public void setUpdatedStatus(String updatedStatus) {
		this.updatedStatus = updatedStatus;
	}

	public void setUseApplication(String useApplication) {
		this.useApplication = useApplication;
	}
}