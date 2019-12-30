/**
 * 
 */
package com.tcmis.internal.invoice.domain;

/**
 * @author spiros.petratos
 * 
 */
public interface IConfiguredInvoiceGroup
{
	/**
	 * Returns the invoiceConfigurationBeanName used in the reporting module to
	 * configure the specific invoice
	 * 
	 * @return the invoiceConfigurationBeanName
	 */
	public String getInvoiceConfigurationBeanName();

	/**
	 * Returns the String representing the label displayed for the invoice
	 * selection
	 * 
	 * @return the invoiceGroupLabel
	 */
	public String getInvoiceGroupLabel();

	/**
	 * Returns the String representing the value for the invoice selection
	 * 
	 * @return the invoiceGroupValue
	 */
	public String getInvoiceGroupValue();

	/**
	 * Returns the boolean indicating if batch report
	 * 
	 * @return the isGroup. True if batch report
	 */
	public boolean isGroup();
	
	/**
	 * Sets the invoiceConfigurationBeanName used in the reporting module to
	 * configure the specific invoice
	 * 
	 * @param invConfigBeanName
	 *            the name of the Spring bean used to configure invoice
	 */
	public void setInvoiceConfigurationBeanName(String invConfigBeanName);

	/**
	 * Sets the String representing the label displayed for the invoice
	 * selection
	 * 
	 * @param invGroupLabel
	 *            The label displayed for the invoice selection
	 */
	public void setInvoiceGroupLabel(String invGroupLabel);

	/**
	 * Sets  the boolean identifying a batch report
	 * 
	 * @param isGrp
	 *            boolean set to true is batch report
	 */
	public void setInvoiceGroupValue(String invGroupValue);
	
	public void setIsGroup(boolean isGrp);
	

}
