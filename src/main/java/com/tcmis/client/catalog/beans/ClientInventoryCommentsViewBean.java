package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ChemSynonymViewBean <br>
 * @version: 1.0, Jan 10, 2008 <br>
 *****************************************************************************/


public class ClientInventoryCommentsViewBean extends BaseDataBean {

	
	private static final long serialVersionUID = 8461206922911473943L;
	
	private String companyId;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private String facilityId;
	private String comments;
	private String catalogCompanyId;

	//constructor
	public ClientInventoryCommentsViewBean() {
	}


	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}


	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	/**
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return catalogId;
	}


	/**
	 * @param catalogId the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}


	/**
	 * @return the catPartNo
	 */
	public String getCatPartNo() {
		return catPartNo;
	}


	/**
	 * @param catPartNo the catPartNo to set
	 */
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}


	/**
	 * @return the partGroupNo
	 */
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}


	/**
	 * @param partGroupNo the partGroupNo to set
	 */
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}


	/**
	 * @return the facilityId
	 */
	public String getFacilityId() {
		return facilityId;
	}


	/**
	 * @param facilityId the facilityId to set
	 */
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}


	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}


	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}


	/**
	 * @return the catalogCompanyId
	 */
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}


	/**
	 * @param catalogCompanyId the catalogCompanyId to set
	 */
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	
}