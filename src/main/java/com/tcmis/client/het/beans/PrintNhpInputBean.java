package com.tcmis.client.het.beans;

import com.tcmis.common.framework.BaseInputBean;

public class PrintNhpInputBean extends BaseInputBean {

	private String containerId;
	//constructor
	public PrintNhpInputBean() {
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	public String getContainerId() {
		return containerId;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
}
