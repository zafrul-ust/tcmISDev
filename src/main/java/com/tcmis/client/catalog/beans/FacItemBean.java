package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacItemBean <br>
 * @version: 1.0, Aug 17, 2005 <br>
 *****************************************************************************/

public class FacItemBean
 extends BaseDataBean {

 private String facilityId;
 private String specId;
 private String facPartNo;
 private String stocked;
 private String specLibrary;
 private BigDecimal unitPrice;
 private BigDecimal minimumBuy;
 private BigDecimal shelfLife;
 private String shelfLifeUnit;
 private String shelfLifeBasis;
 private String cOfCc;
 private String cOfAa;
 private BigDecimal annualUsage;
 private String storageTemp;
 private String shelfLifeRelaxed;
 private BigDecimal shipChrg;
 private BigDecimal testChrg;
 private BigDecimal minBuyAmt;
 private BigDecimal miscChrg;
 private BigDecimal cylRentChrg;
 private BigDecimal setupChrg;
 private BigDecimal certChrg;
 private BigDecimal expedChrg;
 private BigDecimal cutChrg;
 private BigDecimal dryIceChrg;
 private BigDecimal hazmatChrg;
 private BigDecimal packChrg;
 private BigDecimal enviroChrg;
 private BigDecimal regChrg;
 private BigDecimal orderPoint;
 private BigDecimal stockingLevel;
 private String establishedStock;
 private String unitOfSale;
 private String companyId;
 private String partDescription;
 private BigDecimal catalogPrice;
 private String billingCategory;
 private BigDecimal totalRecertsAllowed;
 private BigDecimal shelfLifeExtension;
 private String catalogNotes;
 private String search;
 private String consumable;
 private String category;
 private String labelSpec;
 private String alternateName;
 private BigDecimal unitOfSaleQuantityPerEach;
 private String partShortName;
 private String partHazardImage;
 private String incomingTesting;
 private String recertInstructions;
 private BigDecimal partGroupNo;
 private BigDecimal maxRecertNumber;
 private String timeTempSensitive;
 private String qualityId;
 private String approvalCode;
 private String hetUsageRecording;
 private String customerPartRevision;
 private String materialCategoryName; 
 private String materialSubcategoryName;
 private String productionMaterial;
 private String catPartAttribute;

 //constructor
 public FacItemBean() {
 }

 //setters
 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setSpecId(String specId) {
	this.specId = specId;
 }

 public void setFacPartNo(String facPartNo) {
	this.facPartNo = facPartNo;
 }

 public void setStocked(String stocked) {
	this.stocked = stocked;
 }

 public void setSpecLibrary(String specLibrary) {
	this.specLibrary = specLibrary;
 }

 public void setUnitPrice(BigDecimal unitPrice) {
	this.unitPrice = unitPrice;
 }

 public void setMinimumBuy(BigDecimal minimumBuy) {
	this.minimumBuy = minimumBuy;
 }

 public void setShelfLife(BigDecimal shelfLife) {
	this.shelfLife = shelfLife;
 }

 public void setShelfLifeUnit(String shelfLifeUnit) {
	this.shelfLifeUnit = shelfLifeUnit;
 }

 public void setShelfLifeBasis(String shelfLifeBasis) {
	this.shelfLifeBasis = shelfLifeBasis;
 }

 public void setCOfC(String cOfCc) {
	this.cOfCc = cOfCc;
 }

 public void setCOfA(String cOfAa) {
	this.cOfAa = cOfAa;
 }

 public void setAnnualUsage(BigDecimal annualUsage) {
	this.annualUsage = annualUsage;
 }

 public void setStorageTemp(String storageTemp) {
	this.storageTemp = storageTemp;
 }

 public void setShelfLifeRelaxed(String shelfLifeRelaxed) {
	this.shelfLifeRelaxed = shelfLifeRelaxed;
 }

 public void setShipChrg(BigDecimal shipChrg) {
	this.shipChrg = shipChrg;
 }

 public void setTestChrg(BigDecimal testChrg) {
	this.testChrg = testChrg;
 }

 public void setMinBuyAmt(BigDecimal minBuyAmt) {
	this.minBuyAmt = minBuyAmt;
 }

 public void setMiscChrg(BigDecimal miscChrg) {
	this.miscChrg = miscChrg;
 }

 public void setCylRentChrg(BigDecimal cylRentChrg) {
	this.cylRentChrg = cylRentChrg;
 }

 public void setSetupChrg(BigDecimal setupChrg) {
	this.setupChrg = setupChrg;
 }

 public void setCertChrg(BigDecimal certChrg) {
	this.certChrg = certChrg;
 }

 public void setExpedChrg(BigDecimal expedChrg) {
	this.expedChrg = expedChrg;
 }

 public void setCutChrg(BigDecimal cutChrg) {
	this.cutChrg = cutChrg;
 }

 public void setDryIceChrg(BigDecimal dryIceChrg) {
	this.dryIceChrg = dryIceChrg;
 }

 public void setHazmatChrg(BigDecimal hazmatChrg) {
	this.hazmatChrg = hazmatChrg;
 }

 public void setPackChrg(BigDecimal packChrg) {
	this.packChrg = packChrg;
 }

 public void setEnviroChrg(BigDecimal enviroChrg) {
	this.enviroChrg = enviroChrg;
 }

 public void setRegChrg(BigDecimal regChrg) {
	this.regChrg = regChrg;
 }

 public void setOrderPoint(BigDecimal orderPoint) {
	this.orderPoint = orderPoint;
 }

 public void setStockingLevel(BigDecimal stockingLevel) {
	this.stockingLevel = stockingLevel;
 }

 public void setEstablishedStock(String establishedStock) {
	this.establishedStock = establishedStock;
 }

 public void setUnitOfSale(String unitOfSale) {
	this.unitOfSale = unitOfSale;
 }

 public void setCompanyId(String companyId) {
	this.companyId = companyId;
 }

 public void setPartDescription(String partDescription) {
	this.partDescription = partDescription;
 }

 public void setCatalogPrice(BigDecimal catalogPrice) {
	this.catalogPrice = catalogPrice;
 }

 public void setBillingCategory(String billingCategory) {
	this.billingCategory = billingCategory;
 }

 public void setTotalRecertsAllowed(BigDecimal totalRecertsAllowed) {
	this.totalRecertsAllowed = totalRecertsAllowed;
 }

 public void setShelfLifeExtension(BigDecimal shelfLifeExtension) {
	this.shelfLifeExtension = shelfLifeExtension;
 }

 public void setCatalogNotes(String catalogNotes) {
	this.catalogNotes = catalogNotes;
 }

 public void setSearch(String search) {
	this.search = search;
 }

 public void setConsumable(String consumable) {
	this.consumable = consumable;
 }

 public void setCategory(String category) {
	this.category = category;
 }

 public void setLabelSpec(String labelSpec) {
	this.labelSpec = labelSpec;
 }

 public void setAlternateName(String alternateName) {
	this.alternateName = alternateName;
 }

 public void setUnitOfSaleQuantityPerEach(BigDecimal unitOfSaleQuantityPerEach) {
	this.unitOfSaleQuantityPerEach = unitOfSaleQuantityPerEach;
 }

 public void setPartShortName(String partShortName) {
	this.partShortName = partShortName;
 }

 public void setPartHazardImage(String partHazardImage) {
	this.partHazardImage = partHazardImage;
 }

public void setCOfCc(String cOfCc) {
    this.cOfCc = cOfCc;
}

public void setCOfAa(String cOfAa) {
    this.cOfAa = cOfAa;
}

public void setCustomerPartRevision(String customerPartRevision) {
    this.customerPartRevision = customerPartRevision;
}

    //getters
 public String getFacilityId() {
	return facilityId;
 }

 public String getSpecId() {
	return specId;
 }

 public String getFacPartNo() {
	return facPartNo;
 }

 public String getStocked() {
	return stocked;
 }

 public String getSpecLibrary() {
	return specLibrary;
 }

 public BigDecimal getUnitPrice() {
	return unitPrice;
 }

 public BigDecimal getMinimumBuy() {
	return minimumBuy;
 }

 public BigDecimal getShelfLife() {
	return shelfLife;
 }

 public String getShelfLifeUnit() {
	return shelfLifeUnit;
 }

 public String getShelfLifeBasis() {
	return shelfLifeBasis;
 }

 public String getCOfC() {
	return cOfCc;
 }

 public String getCOfA() {
	return cOfAa;
 }

 public BigDecimal getAnnualUsage() {
	return annualUsage;
 }

 public String getStorageTemp() {
	return storageTemp;
 }

 public String getShelfLifeRelaxed() {
	return shelfLifeRelaxed;
 }

 public BigDecimal getShipChrg() {
	return shipChrg;
 }

 public BigDecimal getTestChrg() {
	return testChrg;
 }

 public BigDecimal getMinBuyAmt() {
	return minBuyAmt;
 }

 public BigDecimal getMiscChrg() {
	return miscChrg;
 }

 public BigDecimal getCylRentChrg() {
	return cylRentChrg;
 }

 public BigDecimal getSetupChrg() {
	return setupChrg;
 }

 public BigDecimal getCertChrg() {
	return certChrg;
 }

 public BigDecimal getExpedChrg() {
	return expedChrg;
 }

 public BigDecimal getCutChrg() {
	return cutChrg;
 }

 public BigDecimal getDryIceChrg() {
	return dryIceChrg;
 }

 public BigDecimal getHazmatChrg() {
	return hazmatChrg;
 }

 public BigDecimal getPackChrg() {
	return packChrg;
 }

 public BigDecimal getEnviroChrg() {
	return enviroChrg;
 }

 public BigDecimal getRegChrg() {
	return regChrg;
 }

 public BigDecimal getOrderPoint() {
	return orderPoint;
 }

 public BigDecimal getStockingLevel() {
	return stockingLevel;
 }

 public String getEstablishedStock() {
	return establishedStock;
 }

 public String getUnitOfSale() {
	return unitOfSale;
 }

 public String getCompanyId() {
	return companyId;
 }

 public String getPartDescription() {
	return partDescription;
 }

 public BigDecimal getCatalogPrice() {
	return catalogPrice;
 }

 public String getBillingCategory() {
	return billingCategory;
 }

 public BigDecimal getTotalRecertsAllowed() {
	return totalRecertsAllowed;
 }

 public BigDecimal getShelfLifeExtension() {
	return shelfLifeExtension;
 }

 public String getCatalogNotes() {
	return catalogNotes;
 }

 public String getSearch() {
	return search;
 }

 public String getConsumable() {
	return consumable;
 }

 public String getCategory() {
	return category;
 }

 public String getLabelSpec() {
	return labelSpec;
 }

 public String getAlternateName() {
	return alternateName;
 }

 public BigDecimal getUnitOfSaleQuantityPerEach() {
	return unitOfSaleQuantityPerEach;
 }

 public String getPartShortName() {
	return partShortName;
 }
 
 

 public String getIncomingTesting() {
	return incomingTesting;
}

public void setIncomingTesting(String incomingTesting) {
	this.incomingTesting = incomingTesting;
}

public String getRecertInstructions() {
	return recertInstructions;
}

public void setRecertInstructions(String recertInstructions) {
	this.recertInstructions = recertInstructions;
}

public String getPartHazardImage() {
	return partHazardImage;
 }

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public BigDecimal getMaxRecertNumber() {
		return maxRecertNumber;
	}

	public void setMaxRecertNumber(BigDecimal maxRecertNumber) {
		this.maxRecertNumber = maxRecertNumber;
	}

	public String getTimeTempSensitive() {
		return timeTempSensitive;
	}

	public void setTimeTempSensitive(String timeTempSensitive) {
		this.timeTempSensitive = timeTempSensitive;
	}

	public String getQualityId() {
		return qualityId;
	}

	public void setQualityId(String qualityId) {
		this.qualityId = qualityId;
	}

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

	public String getHetUsageRecording() {
		return hetUsageRecording;
	}

	public void setHetUsageRecording(String hetUsageRecording) {
		this.hetUsageRecording = hetUsageRecording;
	}

    public String getCOfCc() {
        return cOfCc;
    }

    public String getCOfAa() {
        return cOfAa;
    }

    public String getCustomerPartRevision() {
        return customerPartRevision;
    }

	public String getMaterialCategoryName() {
		return materialCategoryName;
	}

	public void setMaterialCategoryName(String materialCategoryName) {
		this.materialCategoryName = materialCategoryName;
	}

	public String getMaterialSubcategoryName() {
		return materialSubcategoryName;
	}

	public void setMaterialSubcategoryName(String materialSubcategoryName) {
		this.materialSubcategoryName = materialSubcategoryName;
	}

    public String getProductionMaterial() {
        return productionMaterial;
    }

    public void setProductionMaterial(String productionMaterial) {
        this.productionMaterial = productionMaterial;
    }

    public String getCatPartAttribute() {
        return catPartAttribute;
    }

    public void setCatPartAttribute(String catPartAttribute) {
        this.catPartAttribute = catPartAttribute;
    }
}