package com.tcmis.internal.distribution.beans;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class AddSpecsBean extends BaseDataBean {
	private static final long serialVersionUID = 1L;
	
	private String searchMode;
	private String searchArgument;
	private String specId;
	private String detail;
	private String specDetailType;
	private String specDetailClass;
	private String specDetailGroup;
	private String specDetailForm;
	private String specDetailGrade;
	private String specDetailStyle;
	private String specDetailFinish;
	private String specDetailSize;
	private String specDetailColor;
	private String specDetailMethod;
	private String specDetailCondition;
	private String specDetailDash;
	private String specDetailNote;
	private String specDetailOther;
	private String specLibrary;
	private String specLibraryDesc;
	private BigDecimal itemId;
	private String specName;
	private String specVersion;
	private String specAmendment;
	private Date specDate;
	private String content;
	private String specTitle;	
	private String location;
	private File theFile;
	private String uAction;
	private String coc;
	private String coa;
	private String specOrg;
	
	/**
	 * @return the searchMode
	 */
	public String getSearchMode() {
		return searchMode;
	}
	/**
	 * @param searchMode the searchMode to set
	 */
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}
	/**
	 * @return the searchArgument
	 */
	public String getSearchArgument() {
		return searchArgument;
	}
	/**
	 * @param searchArgument the searchArgument to set
	 */
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	/**
	 * @return the specId
	 */
	public String getSpecId() {
		return specId;
	}
	/**
	 * @param specId the specId to set
	 */
	public void setSpecId(String specId) {
		this.specId = specId;
	}

	/**
	 * @return the specLibrary
	 */
	public String getSpecLibrary() {
		return specLibrary;
	}
	/**
	 * @param specLibrary the specLibrary to set
	 */
	public void setSpecLibrary(String specLibrary) {
		this.specLibrary = specLibrary;
	}
	/**
	 * @return the specLibraryDesc
	 */
	public String getSpecLibraryDesc() {
		return specLibraryDesc;
	}
	/**
	 * @param specLibraryDesc the specLibraryDesc to set
	 */
	public void setSpecLibraryDesc(String specLibraryDesc) {
		this.specLibraryDesc = specLibraryDesc;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	/**
	 * @return the specName
	 */
	public String getSpecName() {
		return specName;
	}
	/**
	 * @param specName the specName to set
	 */
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	/**
	 * @return the specVersion
	 */
	public String getSpecVersion() {
		return specVersion;
	}
	/**
	 * @param specVersion the specVersion to set
	 */
	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}
	/**
	 * @return the specAmendment
	 */
	public String getSpecAmendment() {
		return specAmendment;
	}
	/**
	 * @param specAmendment the specAmendment to set
	 */
	public void setSpecAmendment(String specAmendment) {
		this.specAmendment = specAmendment;
	}
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * @return the specDate
	 */
	public Date getSpecDate() {
		return specDate;
	}
	/**
	 * @param specDate the specDate to set
	 */
	public void setSpecDate(Date specDate) {
		this.specDate = specDate;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the specTitle
	 */
	public String getSpecTitle() {
		return specTitle;
	}
	/**
	 * @param specTitle the specTitle to set
	 */
	public void setSpecTitle(String specTitle) {
		this.specTitle = specTitle;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the file
	 */
	public File getTheFile() {
		return theFile;
	}
	/**
	 * @param file the file to set
	 */
	public void setTheFile(File theFile) {
		this.theFile = theFile;
	}
	public void setUAction(String uAction) {
		this.uAction = uAction;
	}
	public String getUAction() {
		return uAction;
	}
	/**
	 * @return the coc
	 */
	public String getCoc() {
		return coc;
	}
	/**
	 * @param coc the coc to set
	 */
	public void setCoc(String coc) {
		this.coc = coc;
	}
	/**
	 * @return the coa
	 */
	public String getCoa() {
		return coa;
	}
	/**
	 * @param coa the coa to set
	 */
	public void setCoa(String coa) {
		this.coa = coa;
	}
	public String getSpecOrg() {
		return specOrg;
	}
	public void setSpecOrg(String specOrg) {
		this.specOrg = specOrg;
	}
	public String getSpecDetailType() {
		return specDetailType;
	}
	public void setSpecDetailType(String specDetailType) {
		this.specDetailType = specDetailType;
	}
	public String getSpecDetailClass() {
		return specDetailClass;
	}
	public void setSpecDetailClass(String specDetailClass) {
		this.specDetailClass = specDetailClass;
	}
	public String getSpecDetailForm() {
		return specDetailForm;
	}
	public void setSpecDetailForm(String specDetailForm) {
		this.specDetailForm = specDetailForm;
	}
	
	public String getSpecDetailGroup() {
		return specDetailGroup;
	}
	public void setSpecDetailGroup(String specDetailGroup) {
		this.specDetailGroup = specDetailGroup;
	}
	public String getSpecDetailGrade() {
		return specDetailGrade;
	}
	public void setSpecDetailGrade(String specDetailGrade) {
		this.specDetailGrade = specDetailGrade;
	}
	public String getSpecDetailStyle() {
		return specDetailStyle;
	}
	public void setSpecDetailStyle(String specDetailStyle) {
		this.specDetailStyle = specDetailStyle;
	}
	public String getSpecDetailFinish() {
		return specDetailFinish;
	}
	public void setSpecDetailFinish(String specDetailFinish) {
		this.specDetailFinish = specDetailFinish;
	}
	public String getSpecDetailSize() {
		return specDetailSize;
	}
	public void setSpecDetailSize(String specDetailSize) {
		this.specDetailSize = specDetailSize;
	}
	public String getSpecDetailColor() {
		return specDetailColor;
	}
	public void setSpecDetailColor(String specDetailColor) {
		this.specDetailColor = specDetailColor;
	}
	public String getSpecDetailMethod() {
		return specDetailMethod;
	}
	public void setSpecDetailMethod(String specDetailMethod) {
		this.specDetailMethod = specDetailMethod;
	}
	public String getSpecDetailCondition() {
		return specDetailCondition;
	}
	public void setSpecDetailCondition(String specDetailCondition) {
		this.specDetailCondition = specDetailCondition;
	}
	public String getSpecDetailDash() {
		return specDetailDash;
	}
	public void setSpecDetailDash(String specDetailDash) {
		this.specDetailDash = specDetailDash;
	}
	public String getSpecDetailNote() {
		return specDetailNote;
	}
	public void setSpecDetailNote(String specDetailNote) {
		this.specDetailNote = specDetailNote;
	}
	public String getSpecDetailOther() {
		return specDetailOther;
	}
	public void setSpecDetailOther(String specDetailOther) {
		this.specDetailOther = specDetailOther;
	}
	

		
}
