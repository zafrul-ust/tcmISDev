package com.tcmis.client.peiprojects.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PeiProjectViewBean <br>
 * @version: 1.0, Dec 14, 2005 <br>
 *****************************************************************************/


public class PeiProjectViewBean extends BaseDataBean {

	private BigDecimal projectId;
	private String projectName;
	private String projectDesc;
	private String projectCategory;
	private BigDecimal projectManagerId;
	private String facilityId;
	private Date approvedDate;
	private Date startDate;
	private Date projectedFinistedDate;
	private BigDecimal pojectedCost;
	private BigDecimal actualCost;
	private String comments;
	private String bestPractice;
	private String projectStatus;
	private Date actualFinishDate;
	private String priority;
	private String projectManager;
	private String projectManagerEmail;
	private String projectManagerPhone;
	private BigDecimal customerContactId;
	private String customerContactManager;
	private String customerContactEmail;
	private String customerContactPhone;
	private String search;
	private BigDecimal totalProjectedPeriodSavings;
	private BigDecimal totalActualPeriodSavings;
	private Collection keywordsCollection;
	private Collection baseKeywordsCollection;
	private Collection documentsCollection;
	private Collection savingsCollection;
	private String[] keywordsCollectionSelect = {};
	private String insertOrUpdate;
	private String printPdf;
	private String companyId;
	private String gainShareDuration;
	private Date proposedDateToClient;

  //constructor
	public PeiProjectViewBean() {
	}

	//setters
	public void setProjectId(BigDecimal projectId) {
		this.projectId = projectId;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}
	public void setProjectManagerId(BigDecimal projectManagerId) {
		this.projectManagerId = projectManagerId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setProjectedFinistedDate(Date projectedFinistedDate) {
		this.projectedFinistedDate = projectedFinistedDate;
	}
	public void setPojectedCost(BigDecimal pojectedCost) {
		this.pojectedCost = pojectedCost;
	}
	public void setActualCost(BigDecimal actualCost) {
		this.actualCost = actualCost;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setBestPractice(String bestPractice) {
		this.bestPractice = bestPractice;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public void setActualFinishDate(Date actualFinishDate) {
		this.actualFinishDate = actualFinishDate;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public void setProjectManagerEmail(String projectManagerEmail) {
		this.projectManagerEmail = projectManagerEmail;
	}
	public void setProjectManagerPhone(String projectManagerPhone) {
		this.projectManagerPhone = projectManagerPhone;
	}
	public void setCustomerContactId(BigDecimal customerContactId) {
		this.customerContactId = customerContactId;
	}
	public void setCustomerContactManager(String customerContactManager) {
		this.customerContactManager = customerContactManager;
	}
	public void setCustomerContactEmail(String customerContactEmail) {
		this.customerContactEmail = customerContactEmail;
	}
	public void setCustomerContactPhone(String customerContactPhone) {
		this.customerContactPhone = customerContactPhone;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public void setTotalProjectedPeriodSavings(BigDecimal totalProjectedPeriodSavings) {
		this.totalProjectedPeriodSavings = totalProjectedPeriodSavings;
	}
	public void setTotalActualPeriodSavings(BigDecimal totalActualPeriodSavings) {
		this.totalActualPeriodSavings = totalActualPeriodSavings;
	}
  public void setKeywordsCollection(Collection keywordsCollection) {
	 this.keywordsCollection = keywordsCollection;
	}
  public void setDocumentsCollection(Collection documentsCollection) {
	 this.documentsCollection = documentsCollection;
	}
	public void setSavingsCollection(Collection savingsCollection) {
	this.savingsCollection = savingsCollection;
  }
	public void setKeywordsCollectionSelect(String keywordsCollectionSelect[]) {
	 this.keywordsCollectionSelect = keywordsCollectionSelect;
	}
	public void setBaseKeywordsCollection(Collection baseKeywordsCollection) {
	 this.baseKeywordsCollection = baseKeywordsCollection;
	}
	public void setInsertOrUpdate(String insertOrUpdate) {
	 this.insertOrUpdate = insertOrUpdate;
	}
	public void setPrintPdf(String printPdf) {
	 this.printPdf = printPdf;
	}
	public void setCompanyId(String companyId) {
	 this.companyId = companyId;
	}
	public void setGainShareDuration(String gainShareDuration) {
	 this.gainShareDuration = gainShareDuration;
	}
	public void setProposedDateToClient(Date proposedDateToClient) {
	 this.proposedDateToClient = proposedDateToClient;
	}

	//getters
	public BigDecimal getProjectId() {
		return projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public String getProjectDesc() {
		return projectDesc;
	}
	public String getProjectCategory() {
		return projectCategory;
	}
	public BigDecimal getProjectManagerId() {
		return projectManagerId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getProjectedFinistedDate() {
		return projectedFinistedDate;
	}
	public BigDecimal getPojectedCost() {
		return pojectedCost;
	}
	public BigDecimal getActualCost() {
		return actualCost;
	}
	public String getComments() {
		return comments;
	}
	public String getBestPractice() {
		return bestPractice;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public Date getActualFinishDate() {
		return actualFinishDate;
	}
	public String getPriority() {
		return priority;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public String getProjectManagerEmail() {
		return projectManagerEmail;
	}
	public String getProjectManagerPhone() {
		return projectManagerPhone;
	}
	public BigDecimal getCustomerContactId() {
		return customerContactId;
	}
	public String getCustomerContactManager() {
		return customerContactManager;
	}
	public String getCustomerContactEmail() {
		return customerContactEmail;
	}
	public String getCustomerContactPhone() {
		return customerContactPhone;
	}
	public String getSearch() {
		return search;
	}
	public BigDecimal getTotalProjectedPeriodSavings() {
		return totalProjectedPeriodSavings;
	}
	public BigDecimal getTotalActualPeriodSavings() {
		return totalActualPeriodSavings;
	}
	public Collection getKeywordsCollection() {
	 return keywordsCollection;
	}
	public Collection getDocumentsCollection() {
	 return documentsCollection;
	}
	public Collection getSavingsCollection() {
	 return savingsCollection;
	}
	public String[] getKeywordsCollectionSelect() {
	 return (this.keywordsCollectionSelect);
	}
	public Collection getBaseKeywordsCollection() {
	 return baseKeywordsCollection;
	}
	public String getInsertOrUpdate() {
	return insertOrUpdate;
 }
 public String getPrintPdf() {
	return printPdf;
 }
 public String getCompanyId() {
 return companyId;
 }
 public String getGainShareDuration() {
	return gainShareDuration;
 }
 public Date getProposedDateToClient() {
	return proposedDateToClient;
 }
}