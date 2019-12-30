package com.tcmis.client.report.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: GlobalAndCustomerListViewBean <br>
 * @version: 1.0, Mar 5, 2012 <br>
 *****************************************************************************/


public class GlobalAndCustomerListViewBean extends BaseDataBean {

	private String listId;
	private String companyId;
	private String owner;
	private String listName;
	private String active;
	private String reference;
	private String remark;
	private String url;
	private Date lastReviewDate;
	private BigDecimal lastReviewBy;
	private String lastReviewName;
	private Date insertDate;
	private BigDecimal insertBy;
	private String insertName;
	private String listDescription;
	private String supercedeGlobalListId;
	private String supercedeGlobalListName;
	private boolean updated;
	private boolean isAddLine;
	private String thresholdName;
	private String thresholdUnit;
	private String thresholdName2;
	private String thresholdUnit2;
	private String thresholdName3;
	private String thresholdUnit3;
	private String author;
	private String source;
	private String listThresholdName;
	private BigDecimal listThreshold;
	private String listThresholdUnit;
	
	//constructor
	public GlobalAndCustomerListViewBean() {
	}

	//setters
	public void setSource(String source) {
		this.source = source;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setLastReviewDate(Date lastReviewDate) {
		this.lastReviewDate = lastReviewDate;
	}
	public void setLastReviewBy(BigDecimal lastReviewBy) {
		this.lastReviewBy = lastReviewBy;
	}
	public void setLastReviewName(String lastReviewName) {
		this.lastReviewName = lastReviewName;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public void setInsertBy(BigDecimal insertBy) {
		this.insertBy = insertBy;
	}
	public void setInsertName(String insertName) {
		this.insertName = insertName;
	}
	public void setListDescription(String listDescription) {
		this.listDescription = listDescription;
	}
	public void setSupercedeGlobalListId(String supercedeGlobalListId) {
		this.supercedeGlobalListId = supercedeGlobalListId;
	}
	public void setSupercedeGlobalListName(String supercedeGlobalListName) {
		this.supercedeGlobalListName = supercedeGlobalListName;
	}
	public void setThresholdName(String thresholdName) {
		this.thresholdName = thresholdName;
	}
	public void setThresholdUnit(String thresholdUnit) {
		this.thresholdUnit = thresholdUnit;
	}
	public void setThresholdName2(String thresholdName2) {
		this.thresholdName2 = thresholdName2;
	}
	public void setThresholdUnit2(String thresholdUnit2) {
		this.thresholdUnit2 = thresholdUnit2;
	}
	public void setThresholdName3(String thresholdName3) {
		this.thresholdName3 = thresholdName3;
	}
	public void setThresholdUnit3(String thresholdUnit3) {
		this.thresholdUnit3 = thresholdUnit3;
	}	
	public boolean getIsAddLine() {
		return isAddLine;
	}
	public void setIsAddLine(boolean isAddLine) {
		this.isAddLine =  isAddLine;
	}
	public void setListThresholdName(String listThresholdName) {
		this.listThresholdName = listThresholdName;
	}	
	public void setListThreshold(BigDecimal listThreshold) {
		this.listThreshold = listThreshold;
	}
	public void setListThresholdUnit(String listThresholdUnit) {
		this.listThresholdUnit = listThresholdUnit;
	}	
	//getters
	public String getSource() {
		return source;
	}
	public boolean isUpdated() {
		return updated;
	}
	public String getAuthor() {
		return author;
	}
	public String getThresholdName3( ) {
		return thresholdName3;
	}
	public String getThresholdUnit3() {
		return thresholdUnit3;
	}
	public String getThresholdName2( ) {
		return thresholdName2;
	}
	public String getThresholdUnit2() {
		return thresholdUnit2;
	}
	public String getThresholdName() {
		return thresholdName;
	}
	public String getThresholdUnit() {
		return thresholdUnit;
	}
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	public String getListId() {
		return listId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getOwner() {
		return owner;
	}
	public String getListName() {
		return listName;
	}
	public String getActive() {
		return active;
	}
	public String getReference() {
		return reference;
	}
	public String getRemark() {
		return remark;
	}
	public String getUrl() {
		return url;
	}
	public Date getLastReviewDate() {
		return lastReviewDate;
	}
	public BigDecimal getLastReviewBy() {
		return lastReviewBy;
	}
	public String getLastReviewName() {
		return lastReviewName;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public BigDecimal getInsertBy() {
		return insertBy;
	}
	public String getInsertName() {
		return insertName;
	}
	public String getListDescription() {
		return listDescription;
	}
	public String getSupercedeGlobalListId() {
		return supercedeGlobalListId;
	}
	public String getSupercedeGlobalListName() {
		return supercedeGlobalListName;
	}
	public String getListThresholdName() {
		return listThresholdName;
	}	
	public BigDecimal getListThreshold() {
		return listThreshold;
	}
	public String getListThresholdUnit() {
		return listThresholdUnit;
	}	

}