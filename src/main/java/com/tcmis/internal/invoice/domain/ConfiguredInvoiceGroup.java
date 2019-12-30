/**
 * 
 */
package com.tcmis.internal.invoice.domain;

import java.io.Serializable;

/**
 * @author spiros.petratos
 * 
 */
@SuppressWarnings("serial")
public class ConfiguredInvoiceGroup implements Serializable,
		IConfiguredInvoiceGroup
{
	private String invoiceConfigurationBeanName;
	private String invoiceGroupLabel;
	private String invoiceGroupValue;
	private boolean isGroup = false;

	/**
	 * 
	 */
	public ConfiguredInvoiceGroup()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroup#
	 * getInvoiceConfigurationBeanName()
	 */
	public String getInvoiceConfigurationBeanName()
	{
		return this.invoiceConfigurationBeanName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroup#getInvoiceGroupLabel
	 * ()
	 */
	public String getInvoiceGroupLabel()
	{
		return this.invoiceGroupLabel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroup#getInvoiceGroupValue
	 * ()
	 */
	public String getInvoiceGroupValue()
	{
		return this.invoiceGroupValue;
	}

	/* (non-Javadoc)
	 * @see com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroup#isGroup()
	 */
	public boolean isGroup()
	{
		return this.isGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroup#
	 * setInvoiceConfigurationBeanName(java.lang.String)
	 */
	public void setInvoiceConfigurationBeanName(String invConfigBeanName)
	{
		this.invoiceConfigurationBeanName = invConfigBeanName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroup#setInvoiceGroupLabel
	 * (java.lang.String)
	 */
	public void setInvoiceGroupLabel(String invGroupLabel)
	{
		this.invoiceGroupLabel = invGroupLabel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroup#setInvoiceGroupValue
	 * (java.lang.String)
	 */
	public void setInvoiceGroupValue(String invGroupValue)
	{
		this.invoiceGroupValue = invGroupValue;
	}

	public void setIsGroup(boolean isGrp)
	{
		this.isGroup = isGrp;
	}

}
