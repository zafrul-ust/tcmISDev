package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class CatalogAddStorageBean extends BaseDataBean {

	private String companyId;	
	private BigDecimal requestId;
	private String application;
	private String applicationDesc;
	private BigDecimal maximumQuantityStored;
	private BigDecimal averageQuantityStored;
	private String newRow;
	private String updated;
	
	
	public String getCompanyId()
	{
		return companyId;
	}
	public BigDecimal getRequestId()
	{
		return requestId;
	}
	
	public String getApplication()
	{
		return application;
	}
	
	public String getApplicationDesc()
	{
		return applicationDesc;
	}
	
	public BigDecimal getMaximumQuantityStored()
	{
		return maximumQuantityStored;
	}
	
	public BigDecimal getAverageQuantityStored()
	{
		return averageQuantityStored;
	}
	
	public String getUpdated()
	{
		return updated;
	}
	
	public String getNewRow()
	{
		return newRow;
	}

	public void setUpdated(String updated)
	{
		this.updated = updated;
	}
	
	public void setNewRow(String newRow)
	{
		this.newRow = newRow;
	}

	public void setCompanyId(String companyId)
	{
		this.companyId = companyId;
	}
	public void setRequestId(BigDecimal requestId)
	{
		this.requestId = requestId;
	}
	
	public void setApplication(String application)
	{
		this.application = application;
	}
	
	public void setApplicationDesc(String applicationDesc)
	{
		this.applicationDesc = applicationDesc;
	}
	
	
	public void setMaximumQuantityStored(BigDecimal maximumQuantityStored)
	{
		this.maximumQuantityStored = maximumQuantityStored;
	}
	
	public void setAverageQuantityStored(BigDecimal averageQuantityStored)
	{
		this.averageQuantityStored = averageQuantityStored;
	}
}
