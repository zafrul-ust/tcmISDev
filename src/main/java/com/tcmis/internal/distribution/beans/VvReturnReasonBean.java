package com.tcmis.internal.distribution.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvReturnReasonBean <br>
 * @version: 1.0, Jul 13, 2009 <br>
 *****************************************************************************/


public class VvReturnReasonBean extends BaseDataBean {

	
	private static final long serialVersionUID = 818526188498368832L;
	private String returnReasonId;
	private String reasonDescription;
	private String reasonLabel;


	//constructor
	public VvReturnReasonBean() {
	}

	//setters
	public void setReturnReasonId(String returnReasonId) {
		this.returnReasonId = returnReasonId;
	}
	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}
	public void setReasonLabel(String reasonLabel) {
		this.reasonLabel = reasonLabel;
	}


	//getters
	public String getReturnReasonId() {
		return returnReasonId;
	}
	public String getReasonDescription() {
		return reasonDescription;
	}
	public String getReasonLabel() {
		return reasonLabel;
	}
}