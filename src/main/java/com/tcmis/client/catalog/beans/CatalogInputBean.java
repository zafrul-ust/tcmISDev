package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CatalogInputBean extends BaseDataBean {

 private String searchText;
 private String facilityId;
 private String facilityName;
 private String applicationId;
 private String applicationDesc;
 private String catalog;
 private String searchTypeName;
 private String facilityOrAllCatalog;
 private String workAreaApprovedOnly;
 private String activeOnly;
 private String[] catPartNo;
 private String[] partDescription;
 private String[] shelfLife;
 private String[] printCheckBox;
 private String[] inventoryGroup;
 private boolean printScreen = false;
 private String accountSysId;
 private BigDecimal selectedItemId;
 private String catalogId;
 private String catalogCompanyId;
 private String catalogDesc;
 private String payloadId;
 private String previousEvalOrder;
 private String evalRequestor;
 private String evalWorkArea;
 private String companyId;
 private String companyName;
 private String branchPlant;
 private String inventoryGroupDefault;
 private BigDecimal itemId;
 private String specLibrary;
 private BigDecimal posRequestorId;
 private String posInventoryGroup;
 //the following fields are for catalog adds
 private BigDecimal requestId;
 private String catalogAddCatalogCompanyId;
 private String catalogAddCatalogId;
 private String catalogAddCatPartNo;
 private String catalogAddPartGroupNo;
 private String catalogAddInventoryGroup;
 private String catalogAddStocked;
 private String calledFrom;
 private String catalogAddPartDesc;
 private String chargeType;
 private String chargeId;
 private String chargeNumbers;
 private String userEnteredChargeNumber;
 private String distribution;
 private String poNumber;
 private String poLine;
 private String useApplication;
 private String uAction;
 private String workAreaApprovedOnlyPos;
 private String facilityOrFullMsdsDatabase;
 private String materialId;
 private String custMsdsNo;
 private BigDecimal catalogAddItemId;
 private String sourcePage;
 private boolean facilityOrAllShelflife = false;

//USE fac_loc_app.charge_type default first unless it's overrides by vv_account_sys.fac_item_charge_type_override
//here's the logic for overriding fac_loc_app.charge_type_default
//vv_account_sys.fac_item_charge_type_override == fac_item.part_charge_type OR vv_accoount_sys.fac_item_charge_type_override = a
//then USE fac_item.part_charge_type
//vv_account_sys.fac_item_charge_type_override:
// d - and fac_item.part_charge_type = d then USE fac_item.part_charge_type (in this case it's d)
// i - and fac_item.part_charge_type = i then USE fac_item.part_charge_type (in this case it's i)
// a - always USE fac_item.part_charge_type
// n - never override => USE fla.charge_type_default
 private String engEvalPartType;
 private String facLocAppChargeTypeDefault;
 private String facItemChargeTypeOverride;

 //the following fields are for setting data in customer.customer_msds_or_mixture_use
 private String customerMsdsDb;
 private String ignoreNullChargeNumber;
 private String updateInsertComment;
 private String description;
 private String includeObsoleteParts;
 private String showMaterialOnly;
 
 private String unitOfMeasure;
 private BigDecimal activityQuantity;

 private String poRequired;
 private String prAccountRequired;
 private String approvalPath;

//constructor
 public CatalogInputBean() {
 }

 //setters
 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setApplicationId(String applicationId) {
	this.applicationId = applicationId;
 }

 public void setSearchText(String searchText) {
	this.searchText = searchText;
 }

 public void setCatalog(String catalog) {
	this.catalog = catalog;
 }

 public void setFacilityOrAllCatalog(String facilityOrAllCatalog) {
	this.facilityOrAllCatalog = facilityOrAllCatalog;
 }

 public void setWorkAreaApprovedOnly(String workAreaApprovedOnly) {
	this.workAreaApprovedOnly = workAreaApprovedOnly;
 }

 public void setActiveOnly(String activeOnly) {
	this.activeOnly = activeOnly;
 }

 public void setCatPartNo(String[] catPartNo) {
	this.catPartNo = catPartNo;
 }

 public void setPartDescription(String[] partDescription) {
	this.partDescription = partDescription;
 }

 public void setShelfLife(String[] shelfLife) {
	this.shelfLife = shelfLife;
 }

 public void setPrintCheckBox(String[] printCheckBox) {
  this.printCheckBox = printCheckBox;
 }

 public void setInventoryGroup(String[] inventoryGroup) {
  this.inventoryGroup = inventoryGroup;
 }

 public void setPrintScreen(boolean printScreen) {
	this.printScreen = printScreen;
 }

	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}

	public void setSelectedItemId(BigDecimal selectedItemId) {
		this.selectedItemId = selectedItemId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setPayloadId(String payloadId) {
		this.payloadId = payloadId;
	}

	public void setPreviousEvalOrder(String previousEvalOrder) {
		this.previousEvalOrder = previousEvalOrder;
	}

	public void setEvalRequestor(String evalRequestor) {
		this.evalRequestor = evalRequestor;
	}

	public void setEvalWorkArea(String evalWorkArea) {
		this.evalWorkArea = evalWorkArea;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	public void setInventoryGroupDefault(String inventoryGroupDefault) {
		this.inventoryGroupDefault = inventoryGroupDefault;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setSpecLibrary(String specLibrary) {
		this.specLibrary = specLibrary;
	}

	public void setPosRequestorId(BigDecimal posRequestorId) {
		this.posRequestorId = posRequestorId;
	}

    public void setPosInventoryGroup(String posInventoryGroup) {
        this.posInventoryGroup = posInventoryGroup;
    }

    public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public void setCatalogAddCatalogCompanyId(String catalogAddCatalogCompanyId) {
		this.catalogAddCatalogCompanyId = catalogAddCatalogCompanyId;
	}

	public void setCatalogAddCatalogId(String catalogAddCatalogId) {
		this.catalogAddCatalogId = catalogAddCatalogId;
	}

	public void setCatalogAddCatPartNo(String catalogAddCatPartNo) {
		this.catalogAddCatPartNo = catalogAddCatPartNo;
	}

	public void setCatalogAddPartGroupNo(String catalogAddPartGroupNo) {
		this.catalogAddPartGroupNo = catalogAddPartGroupNo;
	}

	public void setCatalogAddInventoryGroup(String catalogAddInventoryGroup) {
		this.catalogAddInventoryGroup = catalogAddInventoryGroup;
	}

	public void setCatalogAddStocked(String catalogAddStocked) {
		this.catalogAddStocked = catalogAddStocked;
	}

	public void setCalledFrom(String calledFrom) {
		this.calledFrom = calledFrom;
	}

	public void setCatalogAddPartDesc(String catalogAddPartDesc) {
		this.catalogAddPartDesc = catalogAddPartDesc;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public void setChargeNumbers(String chargeNumbers) {
		this.chargeNumbers = chargeNumbers;
	}
	
    public void setUpdateInsertComment(String updateInsertComment) {
        this.updateInsertComment = updateInsertComment;
    }
    public void setIncludeObsoleteParts(String includeObsoleteParts) {
        this.includeObsoleteParts = includeObsoleteParts;
    }
    public void setShowMaterialOnly(String showMaterialOnly) {
        this.showMaterialOnly = showMaterialOnly;
    }

    public void setActivityQuantity(BigDecimal activityQuantity) {
     	this.activityQuantity = activityQuantity;
    }
    public void setUnitOfMeasure(String unitOfMeasure) {
     	this.unitOfMeasure = unitOfMeasure;
     }
    
	//getters
     
    public BigDecimal getActivityQuantity() {
    	return activityQuantity;
    }

    public String getUnitOfMeasure() {
    	return unitOfMeasure;
    }
    
    public String getShowMaterialOnly() {
        return showMaterialOnly;
    }

    public String getIncludeObsoleteParts() {
        return includeObsoleteParts;
    }
    
    public String getUpdateInsertComment() {
        return updateInsertComment;
    }
 public String getFacilityId() {
	return this.facilityId;
 }

 public String getApplicationId() {
	return this.applicationId;
 }

 public String getSearchText() {
	return this.searchText;
 }

 public String getCatalog() {
	return this.catalog;
 }

 public String getFacilityOrAllCatalog() {
	return this.facilityOrAllCatalog;
 }

 public String getWorkAreaApprovedOnly() {
	return this.workAreaApprovedOnly;
 }

 public String getActiveOnly() {
	return this.activeOnly;
 }

 public String[] getCatPartNo() {
	return catPartNo;
 }

 public String[] getPartDescription() {
	return partDescription;
 }

 public String[] getShelfLife() {
	return shelfLife;
 }

  public String[] getPrintCheckBox() {
    return printCheckBox;
  }

  public String[] getInventoryGroup() {
    return inventoryGroup;
  }

public String getApplicationDesc() {
	return applicationDesc;
}

public void setApplicationDesc(String applicationDesc) {
	this.applicationDesc = applicationDesc;
}

public String getSearchTypeName() {
	return searchTypeName;
}

public void setSearchTypeName(String searchTypeName) {
	this.searchTypeName = searchTypeName;
}

public String getFacilityName() {
	return facilityName;
}

public void setFacilityName(String facilityName) {
	this.facilityName = facilityName;
}

public boolean isPrintScreen() {
	return printScreen;
}

	public String getAccountSysId() {
		return accountSysId;
	}

	public BigDecimal getSelectedItemId() {
		return selectedItemId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getPayloadId() {
		return payloadId;
	}

	public String getPreviousEvalOrder() {
		return previousEvalOrder;
	}

	public String getEvalRequestor() {
		return evalRequestor;
	}

	public String getEvalWorkArea() {
		return evalWorkArea;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getBranchPlant() {
		return branchPlant;
	}

	public String getInventoryGroupDefault() {
		return inventoryGroupDefault;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getSpecLibrary() {
		return specLibrary;
	}

	public BigDecimal getPosRequestorId() {
		return posRequestorId;
	}

    public String getPosInventoryGroup() {
        return posInventoryGroup;
    }

    public BigDecimal getRequestId() {
		return requestId;
	}

	public String getCatalogAddCatalogCompanyId() {
		return catalogAddCatalogCompanyId;
	}

	public String getCatalogAddCatalogId() {
		return catalogAddCatalogId;
	}

	public String getCatalogAddCatPartNo() {
		return catalogAddCatPartNo;
	}

	public String getCatalogAddPartGroupNo() {
		return catalogAddPartGroupNo;
	}

	public String getCatalogAddInventoryGroup() {
		return catalogAddInventoryGroup;
	}

	public String getCatalogAddStocked() {
		return catalogAddStocked;
	}

	public String getCalledFrom() {
		return calledFrom;
	}

	public String getCatalogAddPartDesc() {
		return catalogAddPartDesc;
	}

	public String getChargeType() {
		return chargeType;
	}
	
	public String getChargeId() {
		return chargeId;
	}

	public String getChargeNumbers() {
		return chargeNumbers;
	}

	public String getUserEnteredChargeNumber() {
		return userEnteredChargeNumber;
	}

	public void setUserEnteredChargeNumber(String userEnteredChargeNumber) {
		this.userEnteredChargeNumber = userEnteredChargeNumber;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getPoLine() {
		return poLine;
	}

	public void setPoLine(String poLine) {
		this.poLine = poLine;
	}

	public String getUseApplication() {
		return useApplication;
	}

	public void setUseApplication(String useApplication) {
		this.useApplication = useApplication;
	}

	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public String getWorkAreaApprovedOnlyPos() {
		return workAreaApprovedOnlyPos;
	}

	public void setWorkAreaApprovedOnlyPos(String workAreaApprovedOnlyPos) {
		this.workAreaApprovedOnlyPos = workAreaApprovedOnlyPos;
	}

	public String getFacilityOrFullMsdsDatabase() {
		return facilityOrFullMsdsDatabase;
	}

	public void setFacilityOrFullMsdsDatabase(String facilityOrFullMsdsDatabase) {
		this.facilityOrFullMsdsDatabase = facilityOrFullMsdsDatabase;
	}

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getCustMsdsNo() {
        return custMsdsNo;
    }

    public void setCustMsdsNo(String custMsdsNo) {
        this.custMsdsNo = custMsdsNo;
    }

    public BigDecimal getCatalogAddItemId() {
        return catalogAddItemId;
    }

    public void setCatalogAddItemId(BigDecimal catalogAddItemId) {
        this.catalogAddItemId = catalogAddItemId;
    }

    public String getSourcePage() {
        return sourcePage;
    }

    public void setSourcePage(String sourcePage) {
        this.sourcePage = sourcePage;
    }

    public String getEngEvalPartType() {
        return engEvalPartType;
    }

    public void setEngEvalPartType(String engEvalPartType) {
        this.engEvalPartType = engEvalPartType;
    }

    public String getFacLocAppChargeTypeDefault() {
        return facLocAppChargeTypeDefault;
    }

    public void setFacLocAppChargeTypeDefault(String facLocAppChargeTypeDefault) {
        this.facLocAppChargeTypeDefault = facLocAppChargeTypeDefault;
    }

    public String getFacItemChargeTypeOverride() {
        return facItemChargeTypeOverride;
    }

    public void setFacItemChargeTypeOverride(String facItemChargeTypeOverride) {
        this.facItemChargeTypeOverride = facItemChargeTypeOverride;
    }

    public String getCustomerMsdsDb() {
        return customerMsdsDb;
    }

    public void setCustomerMsdsDb(String customerMsdsDb) {
        this.customerMsdsDb = customerMsdsDb;
    }

    public String getIgnoreNullChargeNumber() {
        return ignoreNullChargeNumber;
    }

    public void setIgnoreNullChargeNumber(String ignoreNullChargeNumber) {
        this.ignoreNullChargeNumber = ignoreNullChargeNumber;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getPoRequired() {
		return poRequired;
	}

	public void setPoRequired(String poRequired) {
		this.poRequired = poRequired;
	}

	public String getPrAccountRequired() {
		return prAccountRequired;
	}

	public void setPrAccountRequired(String prAccountRequired) {
		this.prAccountRequired = prAccountRequired;
	}

	public String getApprovalPath() {
		return approvalPath;
	}

	public void setApprovalPath(String approvalPath) {
		this.approvalPath = approvalPath;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public boolean isFacilityOrAllShelflife() {
		return facilityOrAllShelflife;
	}

	public void setFacilityOrAllShelflife(boolean facilityOrAllShelflife) {
		this.facilityOrAllShelflife = facilityOrAllShelflife;
	}
}  //end of class