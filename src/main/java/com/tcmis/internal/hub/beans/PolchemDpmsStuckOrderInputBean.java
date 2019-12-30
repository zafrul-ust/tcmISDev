package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;

public class PolchemDpmsStuckOrderInputBean extends BaseDataBean 
{
   	private String searchArgument;
    private String action;
    
    
    public PolchemDpmsStuckOrderInputBean()
	{
	}

    public String getSearchArgument() {
        return searchArgument;
    }

    public void setSearchArgument(String searchArgument) {
        this.searchArgument = searchArgument;
    }

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
}