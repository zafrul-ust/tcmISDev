package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SpecsToReceiveViewBean <br>
 * @version: 1.0, Oct 12, 2009 <br>
 *****************************************************************************/


public class SpecsToReceiveViewBean extends BaseDataBean {

	
	private static final long serialVersionUID = 8358147107616937075L;
	private BigDecimal receiptId;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String specId;
	private String specLibrary;
	private String specLibraryDesc;
	private String detail;
	private String coc;
	private String coa;
	private String certReference;
	private String specName;
	private String specVersion;
	private String specAmendment;
	private String inReceiptSpec;
	private String action;	
	
	//constructor
	public SpecsToReceiveViewBean() {
	}

	//setters
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setSpecId(String specId) {
		this.specId = specId;
	}
	public void setSpecLibrary(String specLibrary) {
		this.specLibrary = specLibrary;
	}
	public void setSpecLibraryDesc(String specLibraryDesc) {
		this.specLibraryDesc = specLibraryDesc;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public void setCoc(String coc) {
		this.coc = coc;
	}
	public void setCoa(String coa) {
		this.coa = coa;
	}
	public void setCertReference(String certReference) {
		this.certReference = certReference;
	}
	public void setInReceiptSpec(String inReceiptSpec) {
		this.inReceiptSpec = inReceiptSpec;
	}


	//getters
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public String getSpecId() {
		return specId;
	}
	public String getSpecLibrary() {
		return specLibrary;
	}
	public String getSpecLibraryDesc() {
		return specLibraryDesc;
	}
	public String getDetail() {
		return detail;
	}
	public String getCoc() {
		return coc;
	}
	public String getCoa() {
		return coa;
	}
	public String getCertReference() {
		return certReference;
	}
	public String getInReceiptSpec() {
		return inReceiptSpec;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
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


}