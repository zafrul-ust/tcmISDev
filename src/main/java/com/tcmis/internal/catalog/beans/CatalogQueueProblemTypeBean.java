package com.tcmis.internal.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

public class CatalogQueueProblemTypeBean extends BaseDataBean {

	private String problemType;
	private String problemTypeDesc;
	public String getProblemType() {
		return problemType;
	}
	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}
	public String getProblemTypeDesc() {
		return problemTypeDesc;
	}
	public void setProblemTypeDesc(String problemTypeDesc) {
		this.problemTypeDesc = problemTypeDesc;
	}
}
