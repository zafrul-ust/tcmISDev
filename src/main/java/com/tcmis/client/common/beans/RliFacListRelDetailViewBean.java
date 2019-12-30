package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: RliFacListRelDetailViewBean <br>
 * @version: 1.0, Jun 4, 2009 <br>
 *****************************************************************************/


public class RliFacListRelDetailViewBean extends BaseDataBean {

	private static final long serialVersionUID = 3437260401769499574L;
	private String companyId;
	private BigDecimal prNumber;
	private String lineItem;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private BigDecimal quantity;
	private String listApprovalStatus;
	private String listApprover;
	private String listApprovalComment;
	private String listApprovalDate;
	private String overMrLimit;
	private String overMrLimitThreshold;
	private String overYtdLimit;
	private String overPeriodLimit;
	private String overPeriodLimitThreshold;
	private String listId;
	private String listName;
	private BigDecimal itemId;
	private BigDecimal mrLimit;
	private String mrLimitUnit;
	private BigDecimal mrValue;
	private String mrLimitThresholdCas;
	private BigDecimal mrLimitThreshold;
	private BigDecimal mrLimitThresholdValue;
	private String mrLimitThresholdUnit;
	private BigDecimal ytdLimitValue;
	private BigDecimal ytdLimit;
	private String ytdLimitUnit;
	private String ytdLimitThresholdCas;
	private BigDecimal ytdLimitThreshold;
	private String ytdLimitThresholdUnit;
	private BigDecimal ytdLimitThresholdValue;
	private BigDecimal periodLimitValue;
	private BigDecimal periodLimit;
	private String periodLimitUnit;
	private String periodLimitThresholdCas;
	private BigDecimal periodLimitThreshold;
	private BigDecimal periodLimitThresholdValue;
	private String periodLimitThresholdUnit;
	private BigDecimal periodDays;
	private Date listApprovalDateRaw;


	//constructor
	public RliFacListRelDetailViewBean() {
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
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setListApprovalStatus(String listApprovalStatus) {
		this.listApprovalStatus = listApprovalStatus;
	}
	public void setListApprover(String listApprover) {
		this.listApprover = listApprover;
	}
	public void setListApprovalComment(String listApprovalComment) {
		this.listApprovalComment = listApprovalComment;
	}
	public void setListApprovalDate(String listApprovalDate) {
		this.listApprovalDate = listApprovalDate;
	}
	public void setOverMrLimit(String overMrLimit) {
		this.overMrLimit = overMrLimit;
	}
	public void setOverMrLimitThreshold(String overMrLimitThreshold) {
		this.overMrLimitThreshold = overMrLimitThreshold;
	}
	public void setOverYtdLimit(String overYtdLimit) {
		this.overYtdLimit = overYtdLimit;
	}
	public void setOverPeriodLimit(String overPeriodLimit) {
		this.overPeriodLimit = overPeriodLimit;
	}
	public void setOverPeriodLimitThreshold(String overPeriodLimitThreshold) {
		this.overPeriodLimitThreshold = overPeriodLimitThreshold;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setMrLimit(BigDecimal mrLimit) {
		this.mrLimit = mrLimit;
	}
	public void setMrLimitUnit(String mrLimitUnit) {
		this.mrLimitUnit = mrLimitUnit;
	}
	public void setMrValue(BigDecimal mrValue) {
		this.mrValue = mrValue;
	}
	public void setMrLimitThresholdCas(String mrLimitThresholdCas) {
		this.mrLimitThresholdCas = mrLimitThresholdCas;
	}
	public void setMrLimitThreshold(BigDecimal mrLimitThreshold) {
		this.mrLimitThreshold = mrLimitThreshold;
	}
	public void setMrLimitThresholdValue(BigDecimal mrLimitThresholdValue) {
		this.mrLimitThresholdValue = mrLimitThresholdValue;
	}
	public void setMrLimitThresholdUnit(String mrLimitThresholdUnit) {
		this.mrLimitThresholdUnit = mrLimitThresholdUnit;
	}
	public void setYtdLimitValue(BigDecimal ytdLimitValue) {
		this.ytdLimitValue = ytdLimitValue;
	}
	public void setYtdLimit(BigDecimal ytdLimit) {
		this.ytdLimit = ytdLimit;
	}
	public void setYtdLimitUnit(String ytdLimitUnit) {
		this.ytdLimitUnit = ytdLimitUnit;
	}
	public void setYtdLimitThresholdCas(String ytdLimitThresholdCas) {
		this.ytdLimitThresholdCas = ytdLimitThresholdCas;
	}
	public void setYtdLimitThreshold(BigDecimal ytdLimitThreshold) {
		this.ytdLimitThreshold = ytdLimitThreshold;
	}
	public void setYtdLimitThresholdUnit(String ytdLimitThresholdUnit) {
		this.ytdLimitThresholdUnit = ytdLimitThresholdUnit;
	}
	public void setYtdLimitThresholdValue(BigDecimal ytdLimitThresholdValue) {
		this.ytdLimitThresholdValue = ytdLimitThresholdValue;
	}
	public void setPeriodLimitValue(BigDecimal periodLimitValue) {
		this.periodLimitValue = periodLimitValue;
	}
	public void setPeriodLimit(BigDecimal periodLimit) {
		this.periodLimit = periodLimit;
	}
	public void setPeriodLimitUnit(String periodLimitUnit) {
		this.periodLimitUnit = periodLimitUnit;
	}
	public void setPeriodLimitThresholdCas(String periodLimitThresholdCas) {
		this.periodLimitThresholdCas = periodLimitThresholdCas;
	}
	public void setPeriodLimitThreshold(BigDecimal periodLimitThreshold) {
		this.periodLimitThreshold = periodLimitThreshold;
	}
	public void setPeriodLimitThresholdValue(BigDecimal periodLimitThresholdValue) {
		this.periodLimitThresholdValue = periodLimitThresholdValue;
	}
	public void setPeriodLimitThresholdUnit(String periodLimitThresholdUnit) {
		this.periodLimitThresholdUnit = periodLimitThresholdUnit;
	}
	public void setPeriodDays(BigDecimal periodDays) {
		this.periodDays = periodDays;
	}

	public void setListApprovalDateRaw(Date listApprovalDateRaw) {
		this.listApprovalDateRaw = listApprovalDateRaw;
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
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getListApprovalStatus() {
		return listApprovalStatus;
	}
	public String getListApprover() {
		return listApprover;
	}
	public String getListApprovalComment() {
		return listApprovalComment;
	}
	public String getListApprovalDate() {
		return listApprovalDate;
	}
	public String getOverMrLimit() {
		return overMrLimit;
	}
	public String getOverMrLimitThreshold() {
		return overMrLimitThreshold;
	}
	public String getOverYtdLimit() {
		return overYtdLimit;
	}
	public String getOverPeriodLimit() {
		return overPeriodLimit;
	}
	public String getOverPeriodLimitThreshold() {
		return overPeriodLimitThreshold;
	}
	public String getListId() {
		return listId;
	}
	public String getListName() {
		return listName;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getMrLimit() {
		return mrLimit;
	}
	public String getMrLimitUnit() {
		return mrLimitUnit;
	}
	public BigDecimal getMrValue() {
		return mrValue;
	}
	public String getMrLimitThresholdCas() {
		return mrLimitThresholdCas;
	}
	public BigDecimal getMrLimitThreshold() {
		return mrLimitThreshold;
	}
	public BigDecimal getMrLimitThresholdValue() {
		return mrLimitThresholdValue;
	}
	public String getMrLimitThresholdUnit() {
		return mrLimitThresholdUnit;
	}
	public BigDecimal getYtdLimitValue() {
		return ytdLimitValue;
	}
	public BigDecimal getYtdLimit() {
		return ytdLimit;
	}
	public String getYtdLimitUnit() {
		return ytdLimitUnit;
	}
	public String getYtdLimitThresholdCas() {
		return ytdLimitThresholdCas;
	}
	public BigDecimal getYtdLimitThreshold() {
		return ytdLimitThreshold;
	}
	public String getYtdLimitThresholdUnit() {
		return ytdLimitThresholdUnit;
	}
	public BigDecimal getYtdLimitThresholdValue() {
		return ytdLimitThresholdValue;
	}
	public BigDecimal getPeriodLimitValue() {
		return periodLimitValue;
	}
	public BigDecimal getPeriodLimit() {
		return periodLimit;
	}
	public String getPeriodLimitUnit() {
		return periodLimitUnit;
	}
	public String getPeriodLimitThresholdCas() {
		return periodLimitThresholdCas;
	}
	public BigDecimal getPeriodLimitThreshold() {
		return periodLimitThreshold;
	}
	public BigDecimal getPeriodLimitThresholdValue() {
		return periodLimitThresholdValue;
	}
	public String getPeriodLimitThresholdUnit() {
		return periodLimitThresholdUnit;
	}
	public BigDecimal getPeriodDays() {
		return periodDays;
	}

	public Date getListApprovalDateRaw() {
		return listApprovalDateRaw;
	}
}