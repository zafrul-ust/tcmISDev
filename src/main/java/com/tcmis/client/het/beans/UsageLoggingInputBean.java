package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * 
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class UsageLoggingInputBean extends BaseInputBean {

	private Collection<VvHetApplicationMethodBean>	applicationMethods;
	private String									areaId;
	private String									buildingId;
	private String									cartId;
	private String									cartName;
	private String									companyId;
	private String									containerIds;
	private String									containerType;
	private String									facilityId;
	private String									hetMixture;
	private BigDecimal								materialId;
	private String									mixtureId;
	private String									msdsPreviouslyUsed;
	private Collection<HetPermitBean>				permits;
	private String									searchArgument;
	private String									searchField;
	private String									searchMode;
	private Collection<VvHetSubstrateBean>			substrates;
	private BigDecimal								workArea;
	private boolean									workAreaAllowedSplitKits;
	private String									workAreaGroup;

	// constructor
	public UsageLoggingInputBean() {
	}

	public UsageLoggingInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public Collection<VvHetApplicationMethodBean> getApplicationMethods() {
		return applicationMethods;
	}

	public String getAreaId() {
		return areaId;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public String getCartId() {
		return cartId;
	}

	public String getCartName() {
		return cartName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getContainerIds() {
		return containerIds;
	}

	public String getContainerType() {
		return containerType;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getHetMixture() {
		return hetMixture;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMixtureId() {
		return mixtureId;
	}

	public String getMsdsPreviouslyUsed() {
		return msdsPreviouslyUsed;
	}

	public Collection<HetPermitBean> getPermits() {
		return permits;
	}

	public String getSearchArgument() {
		// if (isSearchFieldContainer() && hasSearchArgument()) {
		// if (searchArgument.contains("-")) {
		// return searchArgument.substring(0, searchArgument.indexOf("-"));
		// }
		// }
		return searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public Collection<VvHetSubstrateBean> getSubstrates() {
		return substrates;
	}

	public BigDecimal getWorkArea() {
		return workArea;
	}

	public String getWorkAreaGroup() {
		return workAreaGroup;
	}

	public boolean hasCartName() {
		return !StringHandler.isBlankString(cartName);
	}

	public boolean hasFacility() {
		return !StringHandler.isBlankString(this.facilityId);
	}

	public boolean hasSearchArgument() {
		return !StringHandler.isBlankString(this.searchArgument);
	}

	public boolean hasWorkAreaGroup() {
		return this.workAreaGroup != null;
	}

	public boolean isCartSearch() {
		return hasSearchArgument() && isSearchFieldCartName();
	}

	public boolean isDoAddContainerMixtureSearch() {
		return "addContainerMixture".equalsIgnoreCase(getuAction());
	}

	public boolean isDoAdHocMixtureSearch() {
		return "adHocMixture".equalsIgnoreCase(getuAction());
	}

	public boolean isDoOtherContainerSearch() {
		return "othercontainer".equalsIgnoreCase(getuAction());
	}

	public boolean isDoSolventSearch() {
		return "solvent".equalsIgnoreCase(getuAction());
	}

	public boolean isSearchFieldCartName() {
		return "CART_NAME".equals(searchField);
	}

	public boolean isSearchFieldContainer() {
		return "CONTAINER_ID".equals(searchField);
	}

	public boolean isWorkAreaAllowedSplitKits() {
		return workAreaAllowedSplitKits;
	}

	public void setApplicationMethods(Collection<VvHetApplicationMethodBean> applicationMethods) {
		this.applicationMethods = applicationMethods;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public void setCartName(String cartName) {
		this.cartName = cartName;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerIds(String containerIds) {
		this.containerIds = containerIds;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setHetMixture(String hetMixture) {
		this.hetMixture = hetMixture;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("companyId");
		addHiddenFormField("workAreaGroup");
		addHiddenFormField("facilityId");
		addHiddenFormField("workArea");
		addHiddenFormField("searchArgument");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMixtureId(String mixtureId) {
		this.mixtureId = mixtureId;
	}

	public void setMsdsPreviouslyUsed(String msdsPreviouslyUsed) {
		this.msdsPreviouslyUsed = msdsPreviouslyUsed;
	}

	public void setPermits(Collection<HetPermitBean> permits) {
		this.permits = permits;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public void setSubstrates(Collection<VvHetSubstrateBean> substrates) {
		this.substrates = substrates;
	}

	public void setWorkArea(BigDecimal workArea) {
		this.workArea = workArea;
	}

	public void setWorkAreaAllowedSplitKits(boolean workAreaAllowedSplitKits) {
		this.workAreaAllowedSplitKits = workAreaAllowedSplitKits;
	}

	public void setWorkAreaGroup(String application) {
		this.workAreaGroup = application;
	}
}