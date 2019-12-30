package com.tcmis.client.report.beans;

import java.util.Date;
import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PublishTemplateInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class PublishTemplateInputBean extends BaseDataBean {

	private String action;
	private String reportType;
	private BigDecimal templateId;
	private String templateName;
	private String calledFrom;
	private String successFlag = "Ok";
	private String myTemplateView;
	private String usersTemplateView;
	private String userGroupsTemplateView;
	private String companyTemplateView;
	private String userIdList;
	private String[] userIdArray;
	private String[] userGroupIdArray;
	private String companyId;
	private String userGroupFacilityIdList;

	//constructor
	public PublishTemplateInputBean() {
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public BigDecimal getTemplateId() {
		return templateId;
	}

	public void setTemplateId(BigDecimal templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getCalledFrom() {
		return calledFrom;
	}

	public void setCalledFrom(String calledFrom) {
		this.calledFrom = calledFrom;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public String getMyTemplateView() {
		return myTemplateView;
	}

	public void setMyTemplateView(String myTemplateView) {
		this.myTemplateView = myTemplateView;
	}

	public String getUsersTemplateView() {
		return usersTemplateView;
	}

	public void setUsersTemplateView(String usersTemplateView) {
		this.usersTemplateView = usersTemplateView;
	}

	public String getUserGroupsTemplateView() {
		return userGroupsTemplateView;
	}

	public void setUserGroupsTemplateView(String userGroupsTemplateView) {
		this.userGroupsTemplateView = userGroupsTemplateView;
	}

	public String getCompanyTemplateView() {
		return companyTemplateView;
	}

	public void setCompanyTemplateView(String companyTemplateView) {
		this.companyTemplateView = companyTemplateView;
	}

	public String getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}

	public String[] getUserIdArray() {
		return userIdArray;
	}

	public void setUserIdArray(String[] userIdArray) {
		this.userIdArray = userIdArray;
	}

	public String[] getUserGroupIdArray() {
		return userGroupIdArray;
	}

	public void setUserGroupIdArray(String[] userGroupIdArray) {
		this.userGroupIdArray = userGroupIdArray;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUserGroupFacilityIdList() {
		return userGroupFacilityIdList;
	}

	public void setUserGroupFacilityIdList(String userGroupFacilityIdList) {
		this.userGroupFacilityIdList = userGroupFacilityIdList;
	}
}