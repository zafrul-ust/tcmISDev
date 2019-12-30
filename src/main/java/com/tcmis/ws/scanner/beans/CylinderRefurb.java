package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class CylinderRefurb extends BaseDataBean {

	private String		id;
	private String		manufacturerIdNo;
	private String		refurbCategory;
	private String		refurbSubcategory;
	private String		serialNumber;
	private Date		dateServiced;
	private BigDecimal	personnelId;
	private String		supplier;

	// constructor
	public CylinderRefurb() {
	}

	public String getManufacturerIdNo() {
		return this.manufacturerIdNo;
	}

	public String getRefurbCategory() {
		return this.refurbCategory;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setManufacturerIdNo(String manufacturerIdNo) {
		this.manufacturerIdNo = manufacturerIdNo;
	}

	public void setRefurbCategory(String refurbCategory) {
		this.refurbCategory = refurbCategory;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public boolean isValid() {
		return StringUtils.isNotBlank(supplier) && StringUtils.isNotBlank(manufacturerIdNo) && StringUtils.isNotBlank(serialNumber) && StringUtils.isNotBlank(refurbCategory)
				&& StringUtils.isNotBlank(refurbSubcategory) && dateServiced != null && personnelId != null;
	}

	public String getInvalidMessage() {
		return "Cylinder Refurb requires: supplier, manufacturerIdNo, serialNumber, refurbCategory, refurbSubcategory, dateServiced and personnelId";
	}

	public String getId() {
		return this.id;
	}

	public String getRefurbSubcategory() {
		return this.refurbSubcategory;
	}

	public Date getDateServiced() {
		return this.dateServiced;
	}

	public BigDecimal getPersonnelId() {
		return this.personnelId;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRefurbSubcategory(String refurbSubcategory) {
		this.refurbSubcategory = refurbSubcategory;
	}

	public void setDateServiced(Date dateServiced) {
		this.dateServiced = dateServiced;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
}