package com.tcmis.client.common.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PurchaseRequestBean <br>
 * @version: 1.0, Jun 1, 2009 <br>
 *****************************************************************************/


public class PurchaseRequestBean extends BaseDataBean {

	private BigDecimal prNumber;
	private BigDecimal requestor;
	private String facilityId;
	private Date requestDate;
	private String prStatus;
	private String shipTo;
	private BigDecimal requestedFinanceApprover;
	private String rejectionReason;
	private BigDecimal requestedReleaser;
	private String poNumber;
	private String forwardTo;
	private String endUser;
	private String department;
	private String engineeringEvaluation;
	private BigDecimal requestId;
	private String accountSysId;
	private String chargeType;
	private String companyId;
	private String creditCardType;
	private BigDecimal creditCardNumber;
	private Date creditCardExpirationDate;
	private String creditCardName;
	private String contactInfo;
	private Date submittedDate;
	private Date lastUpdated;
	private BigDecimal lastUpdatedBy;
	private BigDecimal posUser;
	private Date mrReleasedDate;
	private Date financeApprovedDate;
    //the following is for approval details screen
    private String approverRequired;
    private String releaserRequired;
    private String listApproval;
    private String useApprovalLimitsOption;
    private String scrap;
	private String opsCompanyId;
	private String opsEntityId;
	private BigDecimal customerServiceRepId;
	private BigDecimal customerId;

    //constructor
	public PurchaseRequestBean() {
	}

	//setters
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public void setPrStatus(String prStatus) {
		this.prStatus = prStatus;
	}
	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}
	public void setRequestedFinanceApprover(BigDecimal requestedFinanceApprover) {
		this.requestedFinanceApprover = requestedFinanceApprover;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public void setRequestedReleaser(BigDecimal requestedReleaser) {
		this.requestedReleaser = requestedReleaser;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setForwardTo(String forwardTo) {
		this.forwardTo = forwardTo;
	}
	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setEngineeringEvaluation(String engineeringEvaluation) {
		this.engineeringEvaluation = engineeringEvaluation;
	}
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	public void setCreditCardNumber(BigDecimal creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
		this.creditCardExpirationDate = creditCardExpirationDate;
	}
	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public void setPosUser(BigDecimal posUser) {
		this.posUser = posUser;
	}
	public void setMrReleasedDate(Date mrReleasedDate) {
		this.mrReleasedDate = mrReleasedDate;
	}
	public void setFinanceApprovedDate(Date financeApprovedDate) {
		this.financeApprovedDate = financeApprovedDate;
	}


	//getters
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public BigDecimal getRequestor() {
		return requestor;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public String getPrStatus() {
		return prStatus;
	}
	public String getShipTo() {
		return shipTo;
	}
	public BigDecimal getRequestedFinanceApprover() {
		return requestedFinanceApprover;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public BigDecimal getRequestedReleaser() {
		return requestedReleaser;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getForwardTo() {
		return forwardTo;
	}
	public String getEndUser() {
		return endUser;
	}
	public String getDepartment() {
		return department;
	}
	public String getEngineeringEvaluation() {
		return engineeringEvaluation;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public String getAccountSysId() {
		return accountSysId;
	}
	public String getChargeType() {
		return chargeType;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCreditCardType() {
		return creditCardType;
	}
	public BigDecimal getCreditCardNumber() {
		return creditCardNumber;
	}
	public Date getCreditCardExpirationDate() {
		return creditCardExpirationDate;
	}
	public String getCreditCardName() {
		return creditCardName;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public BigDecimal getPosUser() {
		return posUser;
	}
	public Date getMrReleasedDate() {
		return mrReleasedDate;
	}
	public Date getFinanceApprovedDate() {
		return financeApprovedDate;
	}

    public String getApproverRequired() {
        return approverRequired;
    }

    public void setApproverRequired(String approverRequired) {
        this.approverRequired = approverRequired;
    }

    public String getReleaserRequired() {
        return releaserRequired;
    }

    public void setReleaserRequired(String releaserRequired) {
        this.releaserRequired = releaserRequired;
    }

    public String getListApproval() {
        return listApproval;
    }

    public void setListApproval(String listApproval) {
        this.listApproval = listApproval;
    }

    public String getUseApprovalLimitsOption() {
        return useApprovalLimitsOption;
    }

    public void setUseApprovalLimitsOption(String useApprovalLimitsOption) {
        this.useApprovalLimitsOption = useApprovalLimitsOption;
    }

    public String getScrap() {
        return scrap;
    }

    public void setScrap(String scrap) {
        this.scrap = scrap;
    }

	public String getOpsCompanyId() {
		return opsCompanyId;
	}

	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public BigDecimal getCustomerServiceRepId() {
		return customerServiceRepId;
	}

	public void setCustomerServiceRepId(BigDecimal customerServiceRepId) {
		this.customerServiceRepId = customerServiceRepId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
}