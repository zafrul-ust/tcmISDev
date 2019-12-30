package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

public class GHSFieldsInputBean extends BaseInputBean {

	private BigDecimal tabIndex;
	private String codeAbbrev;
	private String ghsStatementId;
	private BigDecimal msdsId;
	private BigDecimal isFromMsds;
	private String statementNotRequired;
	private BigDecimal statementNotRequiredId;
	private BigDecimal materialId;
	private Date revisionDate;
	
	public GHSFieldsInputBean(ActionForm form, Locale locale, String dateFormat) {
		super(form, locale, dateFormat);
	}
	
	public BigDecimal getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(BigDecimal tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getCodeAbbrev() {
		return codeAbbrev;
	}

	public void setCodeAbbrev(String codeAbbrev) {
		this.codeAbbrev = codeAbbrev;
	}

	public String getGhsStatementId() {
		return ghsStatementId;
	}

	public void setGhsStatementId(String ghsStatementId) {
		this.ghsStatementId = ghsStatementId;
	}

	public BigDecimal getMsdsId() {
		return msdsId;
	}

	public void setMsdsId(BigDecimal msdsId) {
		this.msdsId = msdsId;
	}

	public BigDecimal getIsFromMsds() {
		return isFromMsds;
	}

	public void setIsFromMsds(BigDecimal isFromMsds) {
		this.isFromMsds = isFromMsds;
	}

	public String getStatementNotRequired() {
		return statementNotRequired;
	}

	public void setStatementNotRequired(String statementNotRequired) {
		this.statementNotRequired = statementNotRequired;
	}

	public BigDecimal getStatementNotRequiredId() {
		return statementNotRequiredId;
	}

	public void setStatementNotRequiredId(BigDecimal statementNotRequiredId) {
		this.statementNotRequiredId = statementNotRequiredId;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	@Override
	public void setHiddenFormFields() {
		
	}

}
