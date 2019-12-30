package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DeliveryScheduleChangeBean <br>
 * @version: 1.0, Jun 21, 2007 <br>
 *****************************************************************************/


public class DeliveryScheduleChangeBean extends BaseDataBean {

	private String companyId;
	private BigDecimal prNumber;
	private String lineItem;
	private Date dateToDeliver;
	private BigDecimal originalQty;
	private BigDecimal revisedQty;
	private Date updatedDate;
	private BigDecimal updatedBy;
	private BigDecimal expediteApproval;
	private BigDecimal csrApproval;
    private Date expediteApprovalDate;
	private Date csrApprovalDate;


    //constructor
	public DeliveryScheduleChangeBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setDateToDeliver(Date dateToDeliver) {
		this.dateToDeliver = dateToDeliver;
	}
	public void setOriginalQty(BigDecimal originalQty) {
		this.originalQty = originalQty;
	}
	public void setRevisedQty(BigDecimal revisedQty) {
		this.revisedQty = revisedQty;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

    public void setExpediteApproval(BigDecimal expediteApproval) {
        this.expediteApproval = expediteApproval;
    }

    public void setCsrApproval(BigDecimal csrApproval) {
        this.csrApproval = csrApproval;
    }

    public void setExpediteApprovalDate(Date expediteApprovalDate) {
        this.expediteApprovalDate = expediteApprovalDate;
    }

    public void setCsrApprovalDate(Date csrApprovalDate) {
        this.csrApprovalDate = csrApprovalDate;
    }

    //getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public Date getDateToDeliver() {
		return dateToDeliver;
	}
	public BigDecimal getOriginalQty() {
		return originalQty;
	}
	public BigDecimal getRevisedQty() {
		return revisedQty;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

    public BigDecimal getExpediteApproval() {
        return expediteApproval;
    }

    public BigDecimal getCsrApproval() {
        return csrApproval;
    }

    public Date getExpediteApprovalDate() {
        return expediteApprovalDate;
    }

    public Date getCsrApprovalDate() {
        return csrApprovalDate;
    }
}