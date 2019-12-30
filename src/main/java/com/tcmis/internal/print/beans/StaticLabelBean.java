package com.tcmis.internal.print.beans;

import com.tcmis.common.framework.BaseDataBean;

public class StaticLabelBean extends BaseDataBean {
	private String labelDescription;
	private String labelFileName;
	private String labelName;

	public String getLabelDescription() {
		return labelDescription;
	}

	public String getLabelFileName() {
		return labelFileName;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelDescription(String labelDescription) {
		this.labelDescription = labelDescription;
	}

	public void setLabelFileName(String labelFileName) {
		this.labelFileName = labelFileName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
