package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class CatPartComponentBean extends BaseDataBean {
	
	  private String materialDesc;
	  private String packaging;
	  private BigDecimal mfgId;
	  private String mfgDesc;
	  private String mfgPartNo;
	  private String compMsds;
	  private BigDecimal shelfLifeDays;
	  private String shelfLifeBasis;
	  private String customerTemperatureId;
	  private BigDecimal maxRecertNumber;
	  private BigDecimal roomTempOutTime;
	  private String displayTemp;
	  
	  public CatPartComponentBean() {
		}


	


	public String getMaterialDesc() {
		return materialDesc;
	}





	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}





	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public BigDecimal getMfgId() {
		return mfgId;
	}

	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}

	public String getMfgDesc() {
		return mfgDesc;
	}

	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}

	public String getMfgPartNo() {
		return mfgPartNo;
	}


	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}


	public String getCompMsds() {
		return compMsds;
	}


	public void setCompMsds(String compMsds) {
		this.compMsds = compMsds;
	}


	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}


	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}


	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}


	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}


	public String getCustomerTemperatureId() {
		return customerTemperatureId;
	}


	public void setCustomerTemperatureId(String customerTemperatureId) {
		this.customerTemperatureId = customerTemperatureId;
	}


	public BigDecimal getMaxRecertNumber() {
		return maxRecertNumber;
	}


	public void setMaxRecertNumber(BigDecimal maxRecertNumber) {
		this.maxRecertNumber = maxRecertNumber;
	}


	public BigDecimal getRoomTempOutTime() {
		return roomTempOutTime;
	}


	public void setRoomTempOutTime(BigDecimal roomTempOutTime) {
		this.roomTempOutTime = roomTempOutTime;
	}

	public String getDisplayTemp() {
		return displayTemp;
	}


	public void setDisplayTemp(String displayTemp) {
		this.displayTemp = displayTemp;
	}
	
	  
	  

}
