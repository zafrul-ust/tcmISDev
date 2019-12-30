package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ItemCatalogViewBean <br>
 * @version: 1.0, Apr 16, 2009 <br>
 *****************************************************************************/


public class ItemCatalogViewBean extends BaseDataBean {

	private BigDecimal itemId;
	private BigDecimal partId;
	private BigDecimal componentsPerItem;
	private String grade;
	private String sizeVaries;
	private String storageTemp;
	private BigDecimal minTemp;
	private BigDecimal maxTemp;
	private String tempUnits;
	private String shelfLife;
	private String materialDesc;
	private String mfgDesc;
	private BigDecimal materialId;
	private String packaging;
	private String mfgPartNo;
	private String msdsOnLine;
	private String sampleOnly;
	private String itemDesc;
	private BigDecimal categoryId;
	private String countryName;
	//this is for calculating how row to span
	private BigDecimal numberOfKitsPerItem;
	private String engEval;
    private BigDecimal globalItemCount;
    private String customerMixtureNumer;
    private String kitMsds;
    private String kitDesc;
    private String msdsNumber;
    private BigDecimal amount;
    private String sizeUnit;
    

	//constructor
	public ItemCatalogViewBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public void setComponentsPerItem(BigDecimal componentsPerItem) {
		this.componentsPerItem = componentsPerItem;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setSizeVaries(String sizeVaries) {
		this.sizeVaries = sizeVaries;
	}
	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}
	public void setMinTemp(BigDecimal minTemp) {
		this.minTemp = minTemp;
	}
	public void setMaxTemp(BigDecimal maxTemp) {
		this.maxTemp = maxTemp;
	}
	public void setTempUnits(String tempUnits) {
		this.tempUnits = tempUnits;
	}
	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}
	public void setMsdsOnLine(String msdsOnLine) {
		this.msdsOnLine = msdsOnLine;
	}
	public void setSampleOnly(String sampleOnly) {
		this.sampleOnly = sampleOnly;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public void setNumberOfKitsPerItem(BigDecimal numberOfKitsPerItem) {
		this.numberOfKitsPerItem = numberOfKitsPerItem;
	}
	public void setEngEval(String engEval) {
		this.engEval = engEval;
	}
    public void setGlobalItemCount(BigDecimal globalItemCount) {
        this.globalItemCount = globalItemCount;
    }
    public void setCustomerMixtureNumer(String customerMixtureNumer) {
        this.customerMixtureNumer = customerMixtureNumer;
    }  
    public void setKitMsds(String kitMsds) {
        this.kitMsds = kitMsds;
    }
    public void setKitDesc(String kitDesc) {
        this.kitDesc = kitDesc;
    }
    public void setMsdsNumber(String msdsNumber) {
        this.msdsNumber = msdsNumber;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }
    //getters
	public BigDecimal getAmount() {
		return amount;
	} 
	public String getSizeUnit() {
		return sizeUnit;
	} 
	public String getKitMsds() {
		return kitMsds;
	} 
	public String getKitDesc() {
		return kitDesc;
	} 
	public String getMsdsNumber() {
		return msdsNumber;
	} 
    
	public String getCustomerMixtureNumer() {
		return customerMixtureNumer;
	} 
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public BigDecimal getComponentsPerItem() {
		return componentsPerItem;
	}
	public String getGrade() {
		return grade;
	}
	public String getSizeVaries() {
		return sizeVaries;
	}
	public String getStorageTemp() {
		return storageTemp;
	}
	public BigDecimal getMinTemp() {
		return minTemp;
	}
	public BigDecimal getMaxTemp() {
		return maxTemp;
	}
	public String getTempUnits() {
		return tempUnits;
	}
	public String getShelfLife() {
		return shelfLife;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getMfgPartNo() {
		return mfgPartNo;
	}
	public String getMsdsOnLine() {
		return msdsOnLine;
	}
	public String getSampleOnly() {
		return sampleOnly;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getCategoryId() {
		return categoryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public BigDecimal getNumberOfKitsPerItem() {
		return numberOfKitsPerItem;
	}
	public String getEngEval() {
		return engEval;
	}
    public BigDecimal getGlobalItemCount() {
        return globalItemCount;
    }
}