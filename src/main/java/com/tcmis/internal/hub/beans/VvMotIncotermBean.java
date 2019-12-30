package com.tcmis.internal.hub.beans;

import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

public class VvMotIncotermBean extends BaseDataBean {
    
    private String modeOfTransport;
    private Collection<VvIncotermBean> vvIncotermList;
    
	public String getModeOfTransport() {
		return modeOfTransport;
	}
	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}
	public Collection<VvIncotermBean> getVvIncotermList() {
		return vvIncotermList;
	}
	public void setVvIncotermList(Collection<VvIncotermBean> vvIncotermList) {
		this.vvIncotermList = vvIncotermList;
	}
	
    
}
 