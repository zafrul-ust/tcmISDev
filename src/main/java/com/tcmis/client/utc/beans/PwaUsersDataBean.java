package com.tcmis.client.utc.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PwaUsersDataBean <br>
 * @version: 1.0, May 18, 2005 <br>
 *****************************************************************************/


public class PwaUsersDataBean extends BaseDataBean {

	private Date ftpLoadDate;
	private String personIdType;
	private String siteCode;
	private String personId;
	private String eln;
	private String efn;
	private String smtpAddress;
	private String pcAcronym;
	private String deptid;
	private String haasEnable;
	private String createMr;
	private String releaser;
	private String createNewChemical;
	private String administrator;
	private String createReports;
	private String approvedWorkAreas;
	private BigDecimal dollarLimit;
	private String approver;
	private String approverIdType;
	private String approverId;
	private String facilityId;
	private BigDecimal personnelIdId;
	private BigDecimal personnelId;
	private BigDecimal tcmApproverId;


	//constructor
	public PwaUsersDataBean() {
	}

	//setters
	public void setFtpLoadDate(Date ftpLoadDate) {
		this.ftpLoadDate = ftpLoadDate;
	}
	public void setPersonIdType(String personIdType) {
		this.personIdType = personIdType;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public void setEln(String eln) {
		this.eln = eln;
	}
	public void setEfn(String efn) {
		this.efn = efn;
	}
	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
	}
	public void setPcAcronym(String pcAcronym) {
		this.pcAcronym = pcAcronym;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public void setHaasEnable(String haasEnable) {
		this.haasEnable = haasEnable;
	}
	public void setCreateMr(String createMr) {
		this.createMr = createMr;
	}
	public void setReleaser(String releaser) {
		this.releaser = releaser;
	}
	public void setCreateNewChemical(String createNewChemical) {
		this.createNewChemical = createNewChemical;
	}
	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}
	public void setCreateReports(String createReports) {
		this.createReports = createReports;
	}
	public void setApprovedWorkAreas(String approvedWorkAreas) {
		this.approvedWorkAreas = approvedWorkAreas;
	}
	public void setDollarLimit(BigDecimal dollarLimit) {
		this.dollarLimit = dollarLimit;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public void setApproverIdType(String approverIdType) {
		this.approverIdType = approverIdType;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setPersonnelIdId(BigDecimal personnelIdId) {
		this.personnelIdId = personnelIdId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setTcmApproverId(BigDecimal tcmApproverId) {
		this.tcmApproverId = tcmApproverId;
	}


	//getters
	public Date getFtpLoadDate() {
		return ftpLoadDate;
	}
	public String getPersonIdType() {
		return personIdType;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public String getPersonId() {
		return personId;
	}
	public String getEln() {
		return eln;
	}
	public String getEfn() {
		return efn;
	}
	public String getSmtpAddress() {
		return smtpAddress;
	}
	public String getPcAcronym() {
		return pcAcronym;
	}
	public String getDeptid() {
		return deptid;
	}
	public String getHaasEnable() {
		return haasEnable;
	}
	public String getCreateMr() {
		return createMr;
	}
	public String getReleaser() {
		return releaser;
	}
	public String getCreateNewChemical() {
		return createNewChemical;
	}
	public String getAdministrator() {
		return administrator;
	}
	public String getCreateReports() {
		return createReports;
	}
	public String getApprovedWorkAreas() {
		return approvedWorkAreas;
	}
	public BigDecimal getDollarLimit() {
		return dollarLimit;
	}
	public String getApprover() {
		return approver;
	}
	public String getApproverIdType() {
		return approverIdType;
	}
	public String getApproverId() {
		return approverId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getPersonnelIdId() {
		return personnelIdId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public BigDecimal getTcmApproverId() {
		return tcmApproverId;
	}
}