package com.tcmis.client.report.beans;

import org.apache.struts.action.ActionForm;


import java.util.Collection;

import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: AdHocUsageInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/

public class AdHocWasteInputBean
    extends ActionForm  {

  private String id;
  private String companyId;
  private String personnelId;
  private String templateName;
  private String reportCriteria;
  private String profileId;
  private String profileDesc;
  private String vendor;
  private String accumulationPoint;
  private String method;
  private String beginDateJsp;
  private String endDateJsp;
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
  private String submitValue;
  private String profileFlag;

  private Collection reportFieldCollection = new Vector();

  private String[] foo;

	private String facilityName;
	private String applicationDesc;
	private String accumulationPointDesc;
	private String vendorDesc;

	private String templateId;
	private String allowEdit;
	private String publishTemplate = "N";
	private String unpublishTemplate = "N";
	private String owner;
	private String userGroupId;
	private String templateDescription;
	private String globalizationLabel;
	private String globalizationLabelLetter;

  //constructor
  public AdHocWasteInputBean() {
  }

  //setters
  public void setId(String id) {
    this.id = id;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setPersonnelId(String personnelId) {
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

  public void setBeginDateJsp(String beginDateJsp) {
    this.beginDateJsp = beginDateJsp;
  }

  public void setEndDateJsp(String endDateJsp) {
    this.endDateJsp = endDateJsp;
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

  public void setSubmitValue(String s) {
    this.submitValue = s;
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
  public String getId() {
    return id;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getPersonnelId() {
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

  public String getBeginDateJsp() {
    return beginDateJsp;
  }

  public String getEndDateJsp() {
    return endDateJsp;
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

  public String getSubmitValue() {
    return this.submitValue;
  }

  public String getProfileFlag() {
    return this.profileFlag;
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

	public String getAllowEdit() {
		return allowEdit;
	}

	public void setAllowEdit(String allowEdit) {
		this.allowEdit = allowEdit;
	}

	public String getPublishTemplate() {
		return publishTemplate;
	}

	public void setPublishTemplate(String publishTemplate) {
		this.publishTemplate = publishTemplate;
	}

	public String getUnpublishTemplate() {
		return unpublishTemplate;
	}

	public void setUnpublishTemplate(String unpublishTemplate) {
		this.unpublishTemplate = unpublishTemplate;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public String getGlobalizationLabel() {
		return globalizationLabel;
	}

	public void setGlobalizationLabel(String globalizationLabel) {
		this.globalizationLabel = globalizationLabel;
	}

	public String getGlobalizationLabelLetter() {
		return globalizationLabelLetter;
	}

	public void setGlobalizationLabelLetter(String globalizationLabelLetter) {
		this.globalizationLabelLetter = globalizationLabelLetter;
	}
}