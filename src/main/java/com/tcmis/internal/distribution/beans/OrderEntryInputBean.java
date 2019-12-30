package com.tcmis.internal.distribution.beans;

import com.tcmis.common.framework.BaseDataBean;


/**
 * OrderEntryInputBean.
 * 
 * 
 */
public class OrderEntryInputBean extends BaseDataBean 
{

	private static final long serialVersionUID = -6048282702632099261L;


	private String searchArgument;
	private String action;	

	public OrderEntryInputBean()
	{
	}


	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}	
	
	public void setAction(String action) {
		this.action = action;
	}
	public String getAction() {
		return action;
	}



}