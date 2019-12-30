package com.tcmis.client.report.beans;

import java.util.Date;
import java.util.Vector;
import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: WasteReportTemplateBean <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class WasteReportTemplateBean extends BaseDataBean {

	private BigDecimal reportTemplateId;
	private String companyId;
	private BigDecimal personnelId;
	private String templateName;
	private String reportCriteria;
	private String profileId;
	private String profileDesc;
    private String vendor;
	private String accumulationPoint;
	private String method;
	private Date beginDate;
	private Date endDate;
	private String application;
	private String facilityId;
	private String managementOption;
	private String managementOptionDesc;
	private String excludeWaste;
	private String reportGenerationType;
	private String reportName;

  //below variables are not from the db columns
  private String[] reportFieldList;
  private String submitReport;
  private String openTemplate;
  private String saveTemplate;
  private String profileFlag;
  private Collection reportFieldCollection = new Vector();

  private String[] foo;

	private String facilityName;
	private String applicationDesc;
	private String accumulationPointDesc;
	private String vendorDesc;
	private String templateId;
  private String userGroupId;
  private String userGroupFacilityId;
  private String owner;
  private String status;
  private String templateDescription;
  private String urlPageArg;

	 //constructor
	public WasteReportTemplateBean() {
	}

	//setters
	public void setReportTemplateId(BigDecimal reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public void setReportCriteria(String reportCriteria) {
		this.reportCriteria = reportCriteria;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public void setProfileDesc(String profileDesc) {
		this.profileDesc = profileDesc;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public void setAccumulationPoint(String accumulationPoint) {
		this.accumulationPoint = accumulationPoint;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setManagementOption(String managementOption) {
		this.managementOption = managementOption;
	}
	public void setManagementOptionDesc(String managementOptionDesc) {
		this.managementOptionDesc = managementOptionDesc;
	}
	public void setExcludeWaste(String excludeWaste) {
		this.excludeWaste = excludeWaste;
	}
	public void setReportGenerationType(String reportGenerationType) {
		this.reportGenerationType = reportGenerationType;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

  public void setReportFieldCollection(Collection c) {
    this.reportFieldCollection = c;
  }

  public void setReportFieldList(String[] reportFieldList) {
    this.reportFieldList = reportFieldList;
  }

  public void setSubmitReport(String s) {
    this.submitReport = s;
  }

  public void setSaveTemplate(String s) {
    this.saveTemplate = s;
  }

  public void setOpenTemplate(String s) {
    this.openTemplate = s;
  }

  public void setProfileFlag(String s) {
		this.profileFlag = s;
  }

  public void setFoo(String[] s) {
    this.foo = s;
  }

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setAccumulationPointDesc(String accumulationPointDesc) {
		this.accumulationPointDesc = accumulationPointDesc;
	}

	public void setVendorDesc(String vendorDesc) {
		this.vendorDesc = vendorDesc;
	}

	//getters
	public BigDecimal getReportTemplateId() {
		return reportTemplateId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public String getReportCriteria() {
		return reportCriteria;
	}
	public String getProfileId() {
		return profileId;
	}
	public String getProfileDesc() {
		return profileDesc;
	}
	public String getVendor() {
		return vendor;
	}
	public String getAccumulationPoint() {
		return accumulationPoint;
	}
	public String getMethod() {
		return method;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getApplication() {
		return application;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getManagementOption() {
		return managementOption;
	}
	public String getManagementOptionDesc() {
		return managementOptionDesc;
	}
	public String getExcludeWaste() {
		return excludeWaste;
	}
	public String getReportGenerationType() {
		return reportGenerationType;
	}
	public String getReportName() {
		return reportName;
	}
  public Collection getReportFieldCollection() {
    return this.reportFieldCollection;
  }

  public String[] getReportFieldList() {
    return reportFieldList;
  }

  public String getSubmitReport() {
    return this.submitReport;
  }

  public String getOpenTemplate() {
    return this.openTemplate;
  }

  public String getSaveTemplate() {
    return this.saveTemplate;
  }
    public String getProfileFlag() {
		return profileFlag;
	}
  public String[] getFoo() {
    return foo;
  }

	public String getFacilityName() {
		return facilityName;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getAccumulationPointDesc() {
		return accumulationPointDesc;
	}

	public String getVendorDesc() {
		return vendorDesc;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserGroupFacilityId() {
		return userGroupFacilityId;
	}

	public void setUserGroupFacilityId(String userGroupFacilityId) {
		this.userGroupFacilityId = userGroupFacilityId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public String getUrlPageArg() {
		return urlPageArg;
	}

	public void setUrlPageArg(String urlPageArg) {
		this.urlPageArg = urlPageArg;
	}
}