package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: NewChemTrackingViewBean <br>
 * @version: 1.0, Jun 23, 2009 <br>
 *****************************************************************************/


public class NewChemTrackingViewBean extends BaseDataBean {

	private static final long serialVersionUID = -8558769533183073365L;
	
	private String companyId;
	private BigDecimal requestId;
	private BigDecimal requestor;
	private String name;
	private Date requestDate;
	private String requestStatusDesc;
	private BigDecimal requestStatus;
	private String facilityId;
	private String facilityName;
	private String catPartNo;
	private String materialDesc;
	private String materialType;
	private String manufacturer;
	private String packaging;
	private BigDecimal partId;
	private String mfgCatalogId;
	private String application;
	private String userGroupId;
	private String applicationDisplay;
	private BigDecimal customerRequestId;
	private BigDecimal itemId;
	private BigDecimal approvalGroup;
	private BigDecimal approvalGroupSeq;
	private BigDecimal startingView;
	private String catalogId;
	private String catalogCompanyId;
	private String requestorName;
	private String engineeringEvaluation;
	private BigDecimal lineItem;
    private String approvalList;
    private Date approvalGroupSeqStartTime;
    private BigDecimal agingTime;
    private String workAreaList;
    private String startingViewDesc;
    private Date submitDate;
    private Date lastUpdated;
    private String customerMsdsNumber;
    private String customerMixtureNumber;
    private String startingViewDescJsp;
    private String requestStatusDescJsp;
    private BigDecimal netwt;
    private String netwtUnit;
	private String		qualityIdLabel;
	private String		qualityId;
	private String		catPartAttributeHeader;
	private String		catPartAttribute;

	public String getQualityIdLabel() {
		return qualityIdLabel;
	}

	public void setQualityIdLabel(String qualityIdLabel) {
		this.qualityIdLabel = qualityIdLabel;
	}

	public String getQualityId() {
		return qualityId;
	}

	public void setQualityId(String qualityId) {
		this.qualityId = qualityId;
	}

	public String getCatPartAttributeHeader() {
		return catPartAttributeHeader;
	}

	public void setCatPartAttributeHeader(String catPartAttributeHeader) {
		this.catPartAttributeHeader = catPartAttributeHeader;
	}

	public String getCatPartAttribute() {
		return catPartAttribute;
	}

	public void setCatPartAttribute(String catPartAttribute) {
		this.catPartAttribute = catPartAttribute;
	}

    //constructor
	public NewChemTrackingViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public void setRequestStatusDesc(String requestStatusDesc) {
		this.requestStatusDesc = requestStatusDesc;
	}
	public void setRequestStatus(BigDecimal requestStatus) {
		this.requestStatus = requestStatus;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public void setMfgCatalogId(String mfgCatalogId) {
		this.mfgCatalogId = mfgCatalogId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public void setApplicationDisplay(String applicationDisplay) {
		this.applicationDisplay = applicationDisplay;
	}
	public void setCustomerRequestId(BigDecimal customerRequestId) {
		this.customerRequestId = customerRequestId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setApprovalGroup(BigDecimal approvalGroup) {
		this.approvalGroup = approvalGroup;
	}
	public void setApprovalGroupSeq(BigDecimal approvalGroupSeq) {
		this.approvalGroupSeq = approvalGroupSeq;
	}
	public void setStartingView(BigDecimal startingView) {
		this.startingView = startingView;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setEngineeringEvaluation(String engineeringEvaluation) {
		this.engineeringEvaluation = engineeringEvaluation;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public BigDecimal getRequestor() {
		return requestor;
	}
	public String getName() {
		return name;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public String getRequestStatusDesc() {
		return requestStatusDesc;
	}
	public BigDecimal getRequestStatus() {
		return requestStatus;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getMaterialType() {
		return materialType;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public String getPackaging() {
		return packaging;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public String getMfgCatalogId() {
		return mfgCatalogId;
	}
	public String getApplication() {
		return application;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public String getApplicationDisplay() {
		return applicationDisplay;
	}
	public BigDecimal getCustomerRequestId() {
		return customerRequestId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getApprovalGroup() {
		return approvalGroup;
	}
	public BigDecimal getApprovalGroupSeq() {
		return approvalGroupSeq;
	}
	public BigDecimal getStartingView() {
		return startingView;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	/**
	 * @param requestorName the requestorName to set
	 */
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	/**
	 * @return the requestorName
	 */
	public String getRequestorName() {
		return requestorName;
	}

	public String getEngineeringEvaluation() {
		return engineeringEvaluation;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

    public String getApprovalList() {
        return approvalList;
    }

    public void setApprovalList(String approvalList) {
        this.approvalList = approvalList;
    }

    public Date getApprovalGroupSeqStartTime() {
        return approvalGroupSeqStartTime;
    }

    public void setApprovalGroupSeqStartTime(Date approvalGroupSeqStartTime) {
        this.approvalGroupSeqStartTime = approvalGroupSeqStartTime;
    }

    public BigDecimal getAgingTime() {
        return agingTime;
    }

    public void setAgingTime(BigDecimal agingTime) {
        this.agingTime = agingTime;
    }

    public String getWorkAreaList() {
        return workAreaList;
    }

    public void setWorkAreaList(String workAreaList) {
        this.workAreaList = workAreaList;
    }

    public String getStartingViewDesc() {
        return startingViewDesc;
    }

    public void setStartingViewDesc(String startingViewDesc) {
        this.startingViewDesc = startingViewDesc;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCustomerMsdsNumber() {
        return customerMsdsNumber;
    }

    public void setCustomerMsdsNumber(String customerMsdsNumber) {
        this.customerMsdsNumber = customerMsdsNumber;
    }

    public String getCustomerMixtureNumber() {
        return customerMixtureNumber;
    }

    public void setCustomerMixtureNumber(String customerMixtureNumber) {
        this.customerMixtureNumber = customerMixtureNumber;
    }
    public String getStartingViewDescJsp() {
        return startingViewDescJsp;
    }

    public void setStartingViewDescJsp(String startingViewDescJsp) {
        this.startingViewDescJsp = startingViewDescJsp;
    }
    public String getRequestStatusDescJsp() {
        return requestStatusDescJsp;
    }

    public void setRequestStatusDescJsp(String requestStatusDescJsp) {
        this.requestStatusDescJsp = requestStatusDescJsp;
    }

    public BigDecimal getNetwt() {
        return netwt;
    }

    public void setNetwt(BigDecimal netwt) {
        this.netwt = netwt;
    }

    public String getNetwtUnit() {
        return netwtUnit;
    }

    public void setNetwtUnit(String netwtUnit) {
        this.netwtUnit = netwtUnit;
    }
}