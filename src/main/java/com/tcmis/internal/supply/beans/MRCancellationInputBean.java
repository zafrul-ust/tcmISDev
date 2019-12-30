package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.HubBaseInputBean;

/**
 * @author spiros.petratos
 * 
 *         Input bean used by MR Cancellation form
 */
/**
 * @author spiros.petratos
 *
 */
@SuppressWarnings("serial")
public class MRCancellationInputBean extends HubBaseInputBean
{
	private String 	checkBuyerId = "";
	private String 	currentUser = "";
	private String	searchArgument;
	private String	searchField;
	private String	searchMode;

	/**
	 * Default Constructor
	 */
	public MRCancellationInputBean()
	{
		super();
	}

	/**
	 * @return the checkBuyerId
	 */
	public String getCheckBuyerId()
	{
		return checkBuyerId;
	}

	/**
	 * @return the currentUser
	 */
	public String getCurrentUser()
	{
		return currentUser;
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
	 * @param checkBuyerId the checkBuyerId to set
	 */
	public void setCheckBuyerId(String checkBuyerId)
	{
		this.checkBuyerId = checkBuyerId;
	}

	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(String currentUser)
	{
		this.currentUser = currentUser;
	}

	/* (non-Javadoc)
	 * @see com.tcmis.common.framework.HubBaseInputBean#setHiddenFormFields()
	 */
	public void setHiddenFormFields()
	{
		// TODO Auto-generated method stub
		super.addHiddenFormField("searchArgument");
		super.addHiddenFormField("searchField");
		super.addHiddenFormField("searchMode");
		
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
}