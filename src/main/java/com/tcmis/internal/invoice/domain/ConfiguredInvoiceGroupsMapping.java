/**
 * 
 */
package com.tcmis.internal.invoice.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.struts.util.LabelValueBean;

/**
 * @author spiros.petratos
 * 
 */
@SuppressWarnings("serial")
public class ConfiguredInvoiceGroupsMapping implements Serializable,
		IConfiguredInvoiceGroupsMapping
{
	private Map<String, IConfiguredInvoiceGroup> configuredInvoiceGroupsMap = null;
	private Vector<LabelValueBean> invoiceGroupsSelection = null;

	public ConfiguredInvoiceGroupsMapping()
	{
		super();
		configuredInvoiceGroupsMap = new HashMap<String, IConfiguredInvoiceGroup>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroupsMapping#
	 * getConfiguredInvoiceGroupsMap()
	 */
	public Map<String, IConfiguredInvoiceGroup> getConfiguredInvoiceGroupsMap()
	{
		return this.configuredInvoiceGroupsMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroupsMapping#
	 * setConfiguredInvoiceGroupsMap(java.util.Map)
	 */
	public void setConfiguredInvoiceGroupsMap(
			Map<String, IConfiguredInvoiceGroup> configuredInvGrpMap)
	{
		this.configuredInvoiceGroupsMap = configuredInvGrpMap;
		if (this.configuredInvoiceGroupsMap != null
				&& !this.configuredInvoiceGroupsMap.isEmpty())
		{
			this.invoiceGroupsSelection = new Vector<LabelValueBean>();
			ArrayList<IConfiguredInvoiceGroup> invGrpList = new ArrayList<IConfiguredInvoiceGroup>();
			invGrpList.addAll(configuredInvoiceGroupsMap.values());
			Iterator<IConfiguredInvoiceGroup> invGrpListIterator = invGrpList
					.iterator();
			while (invGrpListIterator.hasNext())
			{
				IConfiguredInvoiceGroup configuredInvoiceGroup = invGrpListIterator
						.next();
				this.invoiceGroupsSelection.add(new LabelValueBean(
						configuredInvoiceGroup.getInvoiceGroupLabel(),
						configuredInvoiceGroup.getInvoiceGroupValue()));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroupsMapping#
	 * hasInvoiceGroup(java.lang.String)
	 */
	public boolean hasInvoiceGroup(String invGrp)
	{
		if (this.configuredInvoiceGroupsMap != null
				&& this.configuredInvoiceGroupsMap.containsKey(invGrp))
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroupsMapping#
	 * getInvoiceGroupsSelection()
	 */
	public Collection<LabelValueBean> getInvoiceGroupsSelection()
	{
		return this.invoiceGroupsSelection;
	}
}
