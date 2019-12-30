package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.HubBaseInputBean;

/**
 * @author spiros.petratos
 * 
 *         Input bean used by search form
 */
@SuppressWarnings("serial")
public class AllocationsInputBean extends HubBaseInputBean
{
	private String	searchArgument;
	private String	searchField;
	private String	searchMode;

	/**
	 * Default Constructor
	 */
	public AllocationsInputBean()
	{
		super();
	}

	/**
	 * @return the searchArgument
	 */
	public String getSearchArgument()
	{
		return this.searchArgument;
	}

	/**
	 * @return the searchField
	 */
	public String getSearchField()
	{
		return this.searchField;
	}

	/**
	 * @return the searchMode
	 */
	public String getSearchMode()
	{
		return this.searchMode;
	}

	/**
	 * @param searchArgument
	 *            the searchArgument to set
	 */
	public void setSearchArgument(String searchArgument)
	{
		this.searchArgument = searchArgument;
	}

	/**
	 * @param searchField
	 *            the searchField to set
	 */
	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	/**
	 * @param searchMode
	 *            the searchMode to set
	 */
	public void setSearchMode(String searchMode)
	{
		this.searchMode = searchMode;
	}

	/* (non-Javadoc)
	 * @see com.tcmis.common.framework.IHiddenFormFields#setHiddenFormFields()
	 */
	@Override
	public void setHiddenFormFields()
	{
		super.addHiddenFormField("searchArgument");
		super.addHiddenFormField("searchField");
		super.addHiddenFormField("searchMode");
	}

}