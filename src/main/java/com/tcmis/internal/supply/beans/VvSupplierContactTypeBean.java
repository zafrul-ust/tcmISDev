package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvSupplierContactTypeBean <br>
 * @version: 1.0, Nov 17, 2009 <br>
 *****************************************************************************/


public class VvSupplierContactTypeBean extends BaseDataBean {

	
	private static final long serialVersionUID = -3348403721324908730L;
	private String contactType;
	private String description;


	//constructor
	public VvSupplierContactTypeBean() {
	}

	//setters
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	//getters
	public String getContactType() {
		return contactType;
	}
	public String getDescription() {
		return description;
	}
}