package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetPartItemBinBean <br>
 * 
 * @version: 1.0, Oct 10, 2006 <br>
 *****************************************************************************/

@SuppressWarnings("serial")
public class CabinetPartItemBinBean extends BaseDataBean
{

	private String		application;
	private BigDecimal	binId;
	private String		catalogId;
	private String		catPartNo;
	private String		companyId;
	private String		countType;
	private String		facilityId;
	private BigDecimal	itemId;
	private BigDecimal	partGroupNo;
	private String		status;
	private String 	catalogCompanyId;

	// constructor
	public CabinetPartItemBinBean()
	{
	}

	public String getApplication()
	{
		return application;
	}

	public BigDecimal getBinId()
	{
		return binId;
	}

	public String getCatalogId()
	{
		return catalogId;
	}

	public String getCatPartNo()
	{
		return catPartNo;
	}

	// getters
	public String getCompanyId()
	{
		return companyId;
	}

	/**
	 * Gets the countType
	 * 
	 * @return the countType
	 */
	public String getCountType()
	{
		return countType;
	}

	public String getFacilityId()
	{
		return facilityId;
	}

	public BigDecimal getItemId()
	{
		return itemId;
	}

	public BigDecimal getPartGroupNo()
	{
		return partGroupNo;
	}

	public String getStatus()
	{
		return status;
	}

	public void setApplication(String application)
	{
		this.application = application;
	}

	public void setBinId(BigDecimal binId)
	{
		this.binId = binId;
	}

	public void setCatalogId(String catalogId)
	{
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo)
	{
		this.catPartNo = catPartNo;
	}

	// setters
	public void setCompanyId(String companyId)
	{
		this.companyId = companyId;
	}

	/**
	 * Sets the countType
	 * 
	 * @param countType
	 *            the countType to set
	 */
	public void setCountType(String countType)
	{
		this.countType = countType;
	}

	public void setFacilityId(String facilityId)
	{
		this.facilityId = facilityId;
	}

	public void setItemId(BigDecimal itemId)
	{
		this.itemId = itemId;
	}

	public void setPartGroupNo(BigDecimal partGroupNo)
	{
		this.partGroupNo = partGroupNo;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
}