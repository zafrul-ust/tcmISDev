/**
 * 
 */
package com.tcmis.internal.invoice.domain;

import java.util.Collection;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

/**
 * @author spiros.petratos
 * 
 *         Interface to access mapping of invoice groups that are configured
 *         through Spring. Mapping is used to dynamically generate the invoice
 *         group selections in the invoice.jsp
 * 
 */
public interface IConfiguredInvoiceGroupsMapping
{

	/**
	 * Returns a map containing the invoice groups configuration
	 * 
	 * @return Map of IConfiguredInvoiceGroup
	 */
	public Map<String, IConfiguredInvoiceGroup> getConfiguredInvoiceGroupsMap();

	/**
	 * Sets the map containing the invoice groups map
	 * 
	 * @param configuredInvGrpMap
	 */
	public void setConfiguredInvoiceGroupsMap(
			Map<String, IConfiguredInvoiceGroup> configuredInvGrpMap);

	/**
	 * Checks in a given invoice group has an entry in the map
	 * 
	 * @param invGrp
	 *            the invoice group used as a key
	 * @return true if the invGrp has an entry in the map
	 */
	public boolean hasInvoiceGroup(String invGrp);

	/**
	 * Returns Collection of LabelValueBean used for drop-down
	 * 
	 * @return Collection
	 */
	public Collection<LabelValueBean> getInvoiceGroupsSelection();
}
