package com.tcmis.client.catalog.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: NewChemUrlViewBean <br>
 * @version: 1.0, Feb 4, 2014 <br>
 *****************************************************************************/


public class NewChemUrlViewBean extends BaseDataBean {

	private String facilityId;
	private String tcmFacilityId;
	private String application;
	private String processDesc;
	private BigDecimal requestId;
	private String catPartNo;
	private String materialDesc;
	private String mfgTradeName;
	private String customerMsdsNumber;
	private String pkgStyle;
	private String sizeUnit;
	private String partSize;
	private String kit;
	private BigDecimal startingView;
	private String vvSizeUnit;
	private String vvPkgStyle;


	//constructor
	public NewChemUrlViewBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setTcmFacilityId(String tcmFacilityId) {
		this.tcmFacilityId = tcmFacilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setMfgTradeName(String mfgTradeName) {
		this.mfgTradeName = mfgTradeName;
	}
	public void setCustomerMsdsNumber(String customerMsdsNumber) {
		this.customerMsdsNumber = customerMsdsNumber;
	}
	public void setPkgStyle(String pkgStyle) {
		this.pkgStyle = pkgStyle;
	}
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	public void setPartSize(String partSize) {
		this.partSize = partSize;
	}
	public void setKit(String kit) {
		this.kit = kit;
	}
	public void setStartingView(BigDecimal startingView) {
		this.startingView = startingView;
	}
	public void setVvSizeUnit(String vvSizeUnit) {
		this.vvSizeUnit = vvSizeUnit;
	}
	public void setVvPkgStyle(String vvPkgStyle) {
		this.vvPkgStyle = vvPkgStyle;
	}


	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getTcmFacilityId() {
		return tcmFacilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getProcessDesc() {
		return processDesc;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getMfgTradeName() {
		return mfgTradeName;
	}
	public String getCustomerMsdsNumber() {
		return customerMsdsNumber;
	}
	public String getPkgStyle() {
		return pkgStyle;
	}
	public String getSizeUnit() {
		return sizeUnit;
	}
	public String getPartSize() {
		return partSize;
	}
	public String getKit() {
		return kit;
	}
	public BigDecimal getStartingView() {
		return startingView;
	}
	public String getVvSizeUnit() {
		return vvSizeUnit;
	}
	public String getVvPkgStyle() {
		return vvPkgStyle;
	}

}