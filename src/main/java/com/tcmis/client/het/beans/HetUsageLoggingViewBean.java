package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HetUsageLoggingViewBean <br>
 * 
 * @version: 1.0, Jun 1, 2011 <br>
 *****************************************************************************/

public class HetUsageLoggingViewBean extends BaseDataBean {

	private boolean		allowSplitKits;
	private BigDecimal	amountRemaining;
	private BigDecimal	cartId;
	private String		cartName;
	private String		catPartNo;
	private Date		checkedOut;
	private String		companyId;
	private String		containerId;
	private String		containerType;
	private String		custMsdsNo;
	private String		employee;
	private String		facilityId;
	private boolean		hetMixture					= false;
	private boolean		hetMultipleBuildingUsage	= false;
	private boolean		inMixture					= false;
	private BigDecimal	itemId;
	private BigDecimal	kitId;
	private String		materialDesc;
	private BigDecimal	materialId;
	private String		mixtureId;
	private String		mixtureName;
	private String		msdsInMixture;
	private boolean		separable;
	private boolean		solvent						= false;
	private String		unitOfMeasure;

	// constructor
	public HetUsageLoggingViewBean() {
	}

	public BigDecimal getAmountRemaining() {
		return amountRemaining;
	}

	public BigDecimal getCartId() {
		return cartId;
	}

	public String getCartName() {
		return cartName;
	}

	// getters
	public String getCatPartNo() {
		return catPartNo;
	}

	public Date getCheckedOut() {
		return checkedOut;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getContainerType() {
		return containerType;
	}

	public String getCustMsdsNo() {
		return custMsdsNo;
	}

	public String getEmployee() {
		return employee;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKitId() {
		return kitId;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMixtureId() {
		return mixtureId;
	}

	public int getMixtureMsdsCount() {
		return StringHandler.isBlankString(msdsInMixture) ? 0 : msdsInMixture.split(",").length;
	}

	public String getMixtureName() {
		return mixtureName;
	}

	public String getMsdsInMixture() {
		return msdsInMixture;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public boolean isAllowSplitKit() {
		return allowSplitKits;
	}

	public boolean isAllowSplitKits() {
		return allowSplitKits;
	}

	public boolean isHetMixture() {
		return hetMixture;
	}

	public boolean isHetMultipleBuildingUsage() {
		return hetMultipleBuildingUsage;
	}

	public boolean isInMixture() {
		return inMixture;
	}

	public boolean isSeparable() {
		return separable;
	}

	public boolean isSolvent() {
		return solvent;
	}

	public void setAllowSplitKits(boolean allowSplitKits) {
		this.allowSplitKits = allowSplitKits;
	}

	public void setAmountRemaining(BigDecimal amountRemaining) {
		this.amountRemaining = amountRemaining;
	}

	// setters

	public void setCartId(BigDecimal recipeId) {
		this.cartId = recipeId;
	}

	public void setCartName(String recipeName) {
		this.cartName = recipeName;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCheckedOut(Date checkedOut) {
		this.checkedOut = checkedOut;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public void setCustMsdsNo(String custMsdsNo) {
		this.custMsdsNo = custMsdsNo;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setHetMixture(boolean hetMixture) {
		this.hetMixture = hetMixture;
	}

	public void setHetMultipleBuildingUsage(boolean hetMultipleBuildingUsage) {
		this.hetMultipleBuildingUsage = hetMultipleBuildingUsage;
	}

	public void setInMixture(boolean inMixture) {
		this.inMixture = inMixture;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKitId(BigDecimal partId) {
		this.kitId = partId;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMixtureId(String mixtureId) {
		this.mixtureId = mixtureId;
	}

	public void setMixtureName(String mixtureName) {
		this.mixtureName = mixtureName;
	}

	public void setMsdsInMixture(String msdsInMixture) {
		this.msdsInMixture = msdsInMixture;
	}

	public void setSeparable(boolean seperable) {
		this.separable = seperable;
	}

	public void setSolvent(boolean solvent) {
		this.solvent = solvent;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
}