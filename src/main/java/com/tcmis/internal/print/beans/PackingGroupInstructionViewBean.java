package com.tcmis.internal.print.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PackingGroupInstructionViewBean <br>
 * @version: 1.0, Feb 20, 2008 <br>
 *****************************************************************************/


public class PackingGroupInstructionViewBean extends BaseDataBean {

	private BigDecimal packingGroupId;
	private String instructions;


	//constructor
	public PackingGroupInstructionViewBean() {
	}

	//setters
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}


	//getters
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}
	public String getInstructions() {
		return instructions;
	}
}