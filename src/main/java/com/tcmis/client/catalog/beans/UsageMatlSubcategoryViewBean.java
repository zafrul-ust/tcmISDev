package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UsageMatlSubcategoryViewBean <br>
 * @version: 1.0, Jul 22, 2011 <br>
 *****************************************************************************/


public class UsageMatlSubcategoryViewBean extends BaseDataBean {

	private String companyId;
	private BigDecimal requestId;
	private String typeOfUse;
	private String usageSubcategoryName;
	private String production;
	private BigDecimal approvalCodeId;
	private String approvalExpires;
	private String includesSpecialCoatings;
	private String materialSubcategoryName;
	private String materialSubcategoryDesc;
	private String coatCategory;
	private String showForProd;
	private String showForNonProd;
	private String toVocet;
	private String specialtyCoating;
	private String hideQtyPerShift;
	private String qtyPerShiftOptional;
	private String hideAnnualUsage;
	private String annualUsageOptional;
	private String hideMultiComponent;
	private String hideProcessInfo;
	private String hideFtwSpecific;
    private String useName;
    private String flexPermitRequired;
    private BigDecimal maxQtyPerShift;
    private String maxQtyPerShiftUnit;


    //constructor
	public UsageMatlSubcategoryViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setTypeOfUse(String typeOfUse) {
		this.typeOfUse = typeOfUse;
	}
	public void setUsageSubcategoryName(String usageSubcategoryName) {
		this.usageSubcategoryName = usageSubcategoryName;
	}
	public void setProduction(String production) {
		this.production = production;
	}
	public void setApprovalCodeId(BigDecimal approvalCodeId) {
		this.approvalCodeId = approvalCodeId;
	}
	public void setApprovalExpires(String approvalExpires) {
		this.approvalExpires = approvalExpires;
	}
	public void setIncludesSpecialCoatings(String includesSpecialCoatings) {
		this.includesSpecialCoatings = includesSpecialCoatings;
	}
	public void setMaterialSubcategoryName(String materialSubcategoryName) {
		this.materialSubcategoryName = materialSubcategoryName;
	}
	public void setMaterialSubcategoryDesc(String materialSubcategoryDesc) {
		this.materialSubcategoryDesc = materialSubcategoryDesc;
	}
	public void setCoatCategory(String coatCategory) {
		this.coatCategory = coatCategory;
	}
	public void setShowForProd(String showForProd) {
		this.showForProd = showForProd;
	}
	public void setShowForNonProd(String showForNonProd) {
		this.showForNonProd = showForNonProd;
	}
	public void setToVocet(String toVocet) {
		this.toVocet = toVocet;
	}
	public void setSpecialtyCoating(String specialtyCoating) {
		this.specialtyCoating = specialtyCoating;
	}
	public void setHideQtyPerShift(String hideQtyPerShift) {
		this.hideQtyPerShift = hideQtyPerShift;
	}
	public void setQtyPerShiftOptional(String qtyPerShiftOptional) {
		this.qtyPerShiftOptional = qtyPerShiftOptional;
	}
	public void setHideAnnualUsage(String hideAnnualUsage) {
		this.hideAnnualUsage = hideAnnualUsage;
	}
	public void setAnnualUsageOptional(String annualUsageOptional) {
		this.annualUsageOptional = annualUsageOptional;
	}
	public void setHideMultiComponent(String hideMultiComponent) {
		this.hideMultiComponent = hideMultiComponent;
	}
	public void setHideProcessInfo(String hideProcessInfo) {
		this.hideProcessInfo = hideProcessInfo;
	}
	public void setHideFtwSpecific(String hideFtwSpecific) {
		this.hideFtwSpecific = hideFtwSpecific;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public String getTypeOfUse() {
		return typeOfUse;
	}
	public String getUsageSubcategoryName() {
		return usageSubcategoryName;
	}
	public String getProduction() {
		return production;
	}
	public BigDecimal getApprovalCodeId() {
		return approvalCodeId;
	}
	public String getApprovalExpires() {
		return approvalExpires;
	}
	public String getIncludesSpecialCoatings() {
		return includesSpecialCoatings;
	}
	public String getMaterialSubcategoryName() {
		return materialSubcategoryName;
	}
	public String getMaterialSubcategoryDesc() {
		return materialSubcategoryDesc;
	}
	public String getCoatCategory() {
		return coatCategory;
	}
	public String getShowForProd() {
		return showForProd;
	}
	public String getShowForNonProd() {
		return showForNonProd;
	}
	public String getToVocet() {
		return toVocet;
	}
	public String getSpecialtyCoating() {
		return specialtyCoating;
	}
	public String getHideQtyPerShift() {
		return hideQtyPerShift;
	}
	public String getQtyPerShiftOptional() {
		return qtyPerShiftOptional;
	}
	public String getHideAnnualUsage() {
		return hideAnnualUsage;
	}
	public String getAnnualUsageOptional() {
		return annualUsageOptional;
	}
	public String getHideMultiComponent() {
		return hideMultiComponent;
	}
	public String getHideProcessInfo() {
		return hideProcessInfo;
	}
	public String getHideFtwSpecific() {
		return hideFtwSpecific;
	}

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getFlexPermitRequired() {
        return flexPermitRequired;
    }

    public void setFlexPermitRequired(String flexPermitRequired) {
        this.flexPermitRequired = flexPermitRequired;
    }

    public BigDecimal getMaxQtyPerShift() {
        return maxQtyPerShift;
    }

    public void setMaxQtyPerShift(BigDecimal maxQtyPerShift) {
        this.maxQtyPerShift = maxQtyPerShift;
    }

    public String getMaxQtyPerShiftUnit() {
        return maxQtyPerShiftUnit;
    }

    public void setMaxQtyPerShiftUnit(String maxQtyPerShiftUnit) {
        this.maxQtyPerShiftUnit = maxQtyPerShiftUnit;
    }
}