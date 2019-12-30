package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;
import java.math.BigDecimal;

/******************************************************************************
 * @version: 1.0, Feb 18, 2011 <br>
 *****************************************************************************/


public class TypeBean extends BaseDataBean {

	private BigDecimal typeId;
	private String typeName;
	private String originalTypeName;
    
	//constructor
	public TypeBean() {
	}

	public String getOriginalTypeName() {
		return originalTypeName;
	}

	public void setOriginalTypeName(String originalTypeName) {
		this.originalTypeName = originalTypeName;
	}

	public BigDecimal getTypeId() {
		return typeId;
	}

	public void setTypeId(BigDecimal typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
}