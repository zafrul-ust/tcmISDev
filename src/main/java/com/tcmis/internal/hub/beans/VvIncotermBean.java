package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;

public class VvIncotermBean extends BaseDataBean {

    private String incoterm;
    private String waterOnly;
    private String incotermShortDesc;   
    
	public String getIncoterm() {
		return incoterm;
	}
	public void setIncoterm(String incoterm) {
		this.incoterm = incoterm;
	}
	public String getWaterOnly() {
		return waterOnly;
	}
	public void setWaterOnly(String waterOnly) {
		this.waterOnly = waterOnly;
	}
	public String getIncotermShortDesc() {
		return incotermShortDesc;
	}
	public void setIncotermShortDesc(String incotermShortDesc) {
		this.incotermShortDesc = incotermShortDesc;
	}
    
    
}
