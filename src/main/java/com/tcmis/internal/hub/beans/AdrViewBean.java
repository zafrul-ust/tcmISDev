package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Cfr49HazardousMaterialViewBean <br>
 * @version: 1.0, Nov 5, 2007 <br>
 *****************************************************************************/


public class AdrViewBean extends BaseDataBean 
{

	private BigDecimal adrId;
	private String nameAndDescription;
	private String adrClass;
	private String unNo;
	private String packingGroup;
	private BigDecimal shippingNameCount;
	private String classificationCode;
	private String limitedQuantity;
	private String transportCategory;
	private String tunnelCode;
	private String properShippingName;
	private String technicalNameRequired;
	
	//constructor
	public AdrViewBean() {
	}

	//setters
	public void setProperShippingName(String properShippingName) {
		this.properShippingName = properShippingName;
	}
	public void setAdrId(BigDecimal adrId) {
		this.adrId = adrId;
	}
	public void setNameAndDescription(String nameAndDescription) {
		this.nameAndDescription = nameAndDescription;
	}
	public void setAdrClass(String adrClass) {
		this.adrClass = adrClass;
	}
	public void setUnNo(String unNo) {
		this.unNo = unNo;
	}
	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}
	public void setShippingNameCount(BigDecimal shippingNameCount) {
		this.shippingNameCount = shippingNameCount;
	}
	
	public void setClassificationCode (String classificationCode)
	{
		this.classificationCode = classificationCode;
	}
	public void setLimitedQuantity (String limitedQuantity)
	{
		this.limitedQuantity = limitedQuantity;
	}
	public void setTransportCategory (String transportCategory)
	{
		this.transportCategory = transportCategory;
	}
	public void setTunnelCode (String tunnelCode)
	{
		this.tunnelCode = tunnelCode;
	}


	//getters
	public String getProperShippingName() {
		return properShippingName;
	}
	public BigDecimal getAdrId() {
		return adrId;
	}
	public String getNameAndDescription() {
		return nameAndDescription;
	}
	public String getAdrClass() {
		return adrClass;
	}
	public String getUnNo() {
		return unNo;
	}
	public String getPackingGroup() {
		return packingGroup;
	}
	public BigDecimal getShippingNameCount() {
		return shippingNameCount;
	}
	
	public String getClassificationCode()
	{
		return classificationCode;
	}
	public String getLimitedQuantity()
	{
		return limitedQuantity;
	}
	public String getTransportCategory()
	{
		return transportCategory;
	}
	public String getTunnelCode()
	{
		return tunnelCode;
	}
	public String getTechnicalNameRequired() {
		return technicalNameRequired;
	}
	public void setTechnicalNameRequired(String technicalNameRequired) {
		this.technicalNameRequired = technicalNameRequired;
	}
	
}