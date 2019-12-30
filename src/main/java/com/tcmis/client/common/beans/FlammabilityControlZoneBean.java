package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: DeptBean <br>
 * @version: 1.0, Nov 29, 2011 <br>
 *****************************************************************************/

public class FlammabilityControlZoneBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private BigDecimal flammabilityControlZoneId;
	private String flammabilityControlZoneDesc;
	private String flashPointLimitOperator;
	private String flashPointLimit;
	private String flashPointLimitUnit;
	private String amountLimitOperator;
	private String amountLimit;
	private String amountLimitUnit;
	private String status;
	
	//constructor
	public FlammabilityControlZoneBean() {
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getFlammabilityControlZoneDesc() {
		return flammabilityControlZoneDesc;
	}
	public void setFlammabilityControlZoneDesc(String flammabilityControlZoneDesc) {
		this.flammabilityControlZoneDesc = flammabilityControlZoneDesc;
	}
	public String getFlashPointLimitOperator() {
		return flashPointLimitOperator;
	}
	public void setFlashPointLimitOperator(String flashPointLimitOperator) {
		this.flashPointLimitOperator = flashPointLimitOperator;
	}
	public String getFlashPointLimit() {
		return flashPointLimit;
	}
	public void setFlashPointLimit(String flashPointLimit) {
		this.flashPointLimit = flashPointLimit;
	}
	public String getFlashPointLimitUnit() {
		return flashPointLimitUnit;
	}
	public void setFlashPointLimitUnit(String flashPointLimitUnit) {
		this.flashPointLimitUnit = flashPointLimitUnit;
	}
	public String getAmountLimitOperator() {
		return amountLimitOperator;
	}
	public void setAmountLimitOperator(String amountLimitOperator) {
		this.amountLimitOperator = amountLimitOperator;
	}
	public String getAmountLimit() {
		return amountLimit;
	}
	public void setAmountLimit(String amountLimit) {
		this.amountLimit = amountLimit;
	}
	public String getAmountLimitUnit() {
		return amountLimitUnit;
	}
	public void setAmountLimitUnit(String amountLimitUnit) {
		this.amountLimitUnit = amountLimitUnit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getFlammabilityControlZoneId() {
		return flammabilityControlZoneId;
	}
	public void setFlammabilityControlZoneId(BigDecimal flammabilityControlZoneId) {
		this.flammabilityControlZoneId = flammabilityControlZoneId;
	}
	public boolean isNewFlammabilityControlZoneId(){
		return (this.flammabilityControlZoneId==null);
	}
	public boolean hasFlammabilityControlZoneDesc(){
		return (!StringHandler.isBlankString(this.flammabilityControlZoneDesc));
	}
}