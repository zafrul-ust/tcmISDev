package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class NewRemitToInputBean extends BaseDataBean 
{
	private static final long serialVersionUID = 8452050714284495908L;
	
	private String 	action;
	private String 	searchArgument;	
	private BigDecimal rowIndex;

	public NewRemitToInputBean()
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


	public BigDecimal getRowIndex() {
		return rowIndex;
	}


	public void setRowIndex(BigDecimal rowIndex) {
		this.rowIndex = rowIndex;
	}

}
