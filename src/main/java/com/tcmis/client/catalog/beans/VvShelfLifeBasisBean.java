package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: VvShelfLifeBasisBean <br>
 * @version: 1.0, Mar 8, 2011 <br>
 *****************************************************************************/

public class VvShelfLifeBasisBean extends BaseDataBean {

	private String shelfLifeBasis;
	private String shelfLifeBasisDesc;
	private BigDecimal displayOrder;
	private String jspLabel;

	 //constructor
 	public VvShelfLifeBasisBean() {
 	}

	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}

	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}

	public String getShelfLifeBasisDesc() {
		return shelfLifeBasisDesc;
	}

	public void setShelfLifeBasisDesc(String shelfLifeBasisDesc) {
		this.shelfLifeBasisDesc = shelfLifeBasisDesc;
	}

	public BigDecimal getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getJspLabel() {
		return jspLabel;
	}

	public void setJspLabel(String jspLabel) {
		this.jspLabel = jspLabel;
	}
}