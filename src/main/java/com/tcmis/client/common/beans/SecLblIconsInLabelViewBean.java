package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;
import java.math.BigDecimal;

/******************************************************************************
 * @version: 1.0, Feb 17, 2011 <br>
 *****************************************************************************/


public class SecLblIconsInLabelViewBean extends BaseDataBean {

	private String companyId;
	private String faciltiyId;
	private BigDecimal materialId;
	private BigDecimal iconId;
	private String iconName;
	private BigDecimal iconOrderNum;
	private String iconZpl;
	private String iconInLabelFlag;
	
	private String ok;
	private String iconDesc;
	
//	constructor
	public SecLblIconsInLabelViewBean() {
	}
    
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFaciltiyId() {
		return faciltiyId;
	}

	public void setFaciltiyId(String faciltiyId) {
		this.faciltiyId = faciltiyId;
	}

	public String getIconInLabelFlag() {
		return iconInLabelFlag;
	}

	public void setIconInLabelFlag(String iconInLabelFlag) {
		this.iconInLabelFlag = iconInLabelFlag;
	}

	public BigDecimal getIconId() {
		return iconId;
	}

	public void setIconId(BigDecimal iconId) {
		this.iconId = iconId;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public BigDecimal getIconOrderNum() {
		return iconOrderNum;
	}

	public void setIconOrderNum(BigDecimal iconOrderNum) {
		this.iconOrderNum = iconOrderNum;
	}

	public String getIconZpl() {
		return iconZpl;
	}

	public void setIconZpl(String iconZpl) {
		this.iconZpl = iconZpl;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getIconDesc() {
		return iconDesc;
	}

	public void setIconDesc(String iconDesc) {
		this.iconDesc = iconDesc;
	}

	
}