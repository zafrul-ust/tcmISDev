package com.tcmis.internal.distribution.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvReturnReasonBean <br>
 * @version: 1.0, Jul 13, 2009 <br>
 *****************************************************************************/


public class VvReleaseStatusBean extends BaseDataBean {

	private String releaseStatus;
	private String releaseStatusLabel;
	private String releaseStatusDescription;


	//constructor
	public VvReleaseStatusBean() {
	}


	public String getReleaseStatus() {
		return releaseStatus;
	}


	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}


	public String getReleaseStatusDescription() {
		return releaseStatusDescription;
	}


	public void setReleaseStatusDescription(String releaseStatusDescription) {
		this.releaseStatusDescription = releaseStatusDescription;
	}


	public String getReleaseStatusLabel() {
		return releaseStatusLabel;
	}


	public void setReleaseStatusLabel(String releaseStatusLabel) {
		this.releaseStatusLabel = releaseStatusLabel;
	}


}