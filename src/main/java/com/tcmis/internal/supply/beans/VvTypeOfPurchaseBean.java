package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;

public class VvTypeOfPurchaseBean extends BaseDataBean 
{
	private static final long serialVersionUID = 8452050714284495908L;
	
	private String 	typeOfPurchase;
	private String 	sapAccountGroup;	
	private String 	typeOfPurchaseId;

	public VvTypeOfPurchaseBean()
	{		  
	}

	public String getSapAccountGroup() {
		return sapAccountGroup;
	}

	public void setSapAccountGroup(String sapAccountGroup) {
		this.sapAccountGroup = sapAccountGroup;
	}

	public String getTypeOfPurchase() {
		return typeOfPurchase;
	}

	public void setTypeOfPurchase(String typeOfPurchase) {
		this.typeOfPurchase = typeOfPurchase;
	}

	public String getTypeOfPurchaseId() {
		return typeOfPurchaseId;
	}

	public void setTypeOfPurchaseId(String typeOfPurchaseId) {
		this.typeOfPurchaseId = typeOfPurchaseId;
	}
	

}
