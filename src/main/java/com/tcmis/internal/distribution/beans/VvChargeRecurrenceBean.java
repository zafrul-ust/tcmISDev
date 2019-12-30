package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvChargeRecurrenceBean <br>
 * @version: 1.0, Aug 11, 2009 <br>
 *****************************************************************************/


public class VvChargeRecurrenceBean extends BaseDataBean {

	private String chargeRecurrence;
	private String description;
	private String headerApplicable;
	private String lineApplicable;
	private String jspLabel;


	//constructor
	public VvChargeRecurrenceBean() {
	}

	//setters
	public void setChargeRecurrence(String chargeRecurrence) {
		this.chargeRecurrence = chargeRecurrence;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setHeaderApplicable(String headerApplicable) {
		this.headerApplicable = headerApplicable;
	}
	public void setLineApplicable(String lineApplicable) {
		this.lineApplicable = lineApplicable;
	}
	public void setJspLabel(String jspLabel) {
		this.jspLabel = jspLabel;
	}


	//getters
	public String getChargeRecurrence() {
		return chargeRecurrence;
	}
	public String getDescription() {
		return description;
	}
	public String getHeaderApplicable() {
		return headerApplicable;
	}
	public String getLineApplicable() {
		return lineApplicable;
	}
	public String getJspLabel() {
		return jspLabel;
	}

}