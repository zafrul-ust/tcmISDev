package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;

public class VvUnidocsStorageContainerBean extends BaseDataBean {
    //constructor
	public VvUnidocsStorageContainerBean() {
	}
	
	private String unidocsStorageContainer;
	private String description;
	
	public void setUnidocsStorageContainer(String unidocsStorageContainer) {
		this.unidocsStorageContainer = unidocsStorageContainer;
	}
	
	public String getUnidocsStorageContainer() {
		return unidocsStorageContainer;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
