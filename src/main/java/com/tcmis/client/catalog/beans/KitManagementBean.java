package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.HubBaseInputBean;

	public class KitManagementBean extends HubBaseInputBean {

		private String materialDesc;
		private String vocDisplay;
		private String densityDisplay;
		private String specificGravityDisplay;
		private String vocLwesDisplay;
		private String vocDetect;
		private BigDecimal voc;
		private String vocUnit;
		private String vocLwesDetect;
		private BigDecimal vocLwes;
		private String vocLwesUnit;
		private String searchField;
		private String searchMode;
		private String mixtureDesc;
		private String customerMsdsNumber;
		private String customerMixtureNumber;
		private String searchArgument;
		private String okDoUpdate;
		private String facilityId;
		private String physicalState;
		private String physicalStateSource;
		private BigDecimal mixtureId;
		private BigDecimal materialId;
		private BigDecimal vocUpper;
		private BigDecimal vocLower;
		private String vocSource;
		private BigDecimal vocLwesUpper;
		private BigDecimal vocLwesLower;
		private String vocLwesSource;
		private BigDecimal amount;
		private String sizeUnit;
		private String status;
		private String showActive;
		private String mixtureMfg;
        private Date mixtureRevisionDate;
        private String mixtureRevisionDateDisplay;
        private String mixtureProductCode;
        private Date componentRevisionDate;
        private Date lastUpdated;
        private String customerMsdsDb;
        private String updatedByName;
        private String mfgShortName;
        private String revisionComments;
        private String dataSource;
        private String amountPermission;

		public KitManagementBean() {
			super();
		}
		public KitManagementBean(ActionForm inputForm) {
			super(inputForm); 
		}
		
		public KitManagementBean(ActionForm inputForm, Locale locale) {
			super(inputForm, locale);
		}
		
		public void setAmountPermission(String amountPermission) {
			this.amountPermission = amountPermission;
		}
		public String getAmountPermission() {
			return amountPermission;
		}	
		public void setDataSource(String dataSource) {
			this.dataSource = dataSource;
		}
		public String getDataSource() {
			return dataSource;
		}
		public void setVocDisplay(String vocDisplay) {
			this.vocDisplay = vocDisplay;
		}
		public String getVocDisplay() {
			return vocDisplay;
		}
		public void setDensityDisplay(String densityDisplay) {
			this.densityDisplay = densityDisplay;
		}
		public String getDensityDisplay() {
			return densityDisplay;
		}
		public void setSpecificGravityDisplay(String specificGravityDisplay) {
			this.specificGravityDisplay = specificGravityDisplay;
		}
		public String getSpecificGravityDisplay() {
			return specificGravityDisplay;
		}
		public void setVocLwesDisplay(String vocLwesDisplay) {
			this.vocLwesDisplay = vocLwesDisplay;
		}
		public String getVocLwesDisplay() {
			return vocLwesDisplay;
		}
		public void setRevisionComments(String revisionComments) {
			this.revisionComments = revisionComments;
		}
		public String getRevisionComments() {
			return revisionComments;
		}
				
		public void setMfgShortName(String mfgShortName) {
			this.mfgShortName = mfgShortName;
		}
		public String getMfgShortName() {
			return mfgShortName;
		}
		public void setUpdatedByName(String updatedByName) {
			this.updatedByName = updatedByName;
		}
		public String getUpdatedByName() {
			return updatedByName;
		}
		public void setLastUpdated(Date lastUpdated) {
			this.lastUpdated = lastUpdated;
		}
		public Date getLastUpdated() {
			return lastUpdated;
		}
		public void setCustomerMsdsDb(String customerMsdsDb) {
			this.customerMsdsDb = customerMsdsDb;
		}
		public String getCustomerMsdsDb() {
			return customerMsdsDb;
		}
		public void setComponentRevisionDate(Date componentRevisionDate) {
			this.componentRevisionDate = componentRevisionDate;
		}
		public Date getComponentRevisionDate() {
			return componentRevisionDate;
		}
		public void setMixtureProductCode(String mixtureProductCode) {
			this.mixtureProductCode = mixtureProductCode;
		}
		public String getMixtureProductCode() {
			return mixtureProductCode;
		}
		public void setMixtureRevisionDateDisplay(String mixtureRevisionDateDisplay) {
			this.mixtureRevisionDateDisplay = mixtureRevisionDateDisplay;
		}
		public String getMixtureRevisionDateDisplay() {
			return mixtureRevisionDateDisplay;
		}
		public void setMixtureRevisionDate(Date mixtureRevisionDate) {
			this.mixtureRevisionDate = mixtureRevisionDate;
		}
		public Date getMixtureRevisionDate() {
			return mixtureRevisionDate;
		}
		public void setMixtureMfg(String mixtureMfg) {
			this.mixtureMfg = mixtureMfg;
		}
		public String getMixtureMfg() {
			return mixtureMfg;
		}		
		public void setShowActive(String showActive) {
			this.showActive = showActive;
		}
		
		public String getShowActive() {
			return showActive;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
		public String getStatus() {
			return status;
		}

		public void setVocLower(BigDecimal vocLower) {
			this.vocLower = vocLower;
		}
		
		public BigDecimal getVocLower() {
			return vocLower;
		}
		public void setVocLwesLower(BigDecimal vocLwesLower) {
			this.vocLwesLower = vocLwesLower;
		}
		
		public BigDecimal getVocLwesLower() {
			return vocLwesLower;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		
		public BigDecimal getAmount() {
			return amount;
		}
		
		public void setSizeUnit(String sizeUnit) {
			this.sizeUnit = sizeUnit;
		}
		
		public String getSizeUnit() {
			return sizeUnit;
		}
		
		public void setVocUpper(BigDecimal vocUpper) {
			this.vocUpper = vocUpper;
		}
		
		public BigDecimal getVocUpper() {
			return vocUpper;
		}

		public void setVocSource(String vocSource) {
			this.vocSource = vocSource;
		}
		
		public String getVocSource() {
			return vocSource;
		}
		
		public void setVocLwesUpper(BigDecimal vocLwesUpper) {
			this.vocLwesUpper = vocLwesUpper;
		}
		
		public BigDecimal getVocLwesUpper() {
			return vocLwesUpper;
		}
		
		public void setVocLwesSource(String vocLwesSource) {
			this.vocLwesSource = vocLwesSource;
		}
		
		public String getVocLwesSource() {
			return vocLwesSource;
		}
		
		public void setMixtureId(BigDecimal mixtureId) {
			this.mixtureId = mixtureId;
		}
		
		public BigDecimal getMixtureId() {
			return mixtureId;
		}	
		public void setMaterialId(BigDecimal materialId) {
			this.materialId = materialId;
		}
		
		public BigDecimal getMaterialId() {
			return materialId;
		}
		public void setFacilityId(String facilityId) {
			this.facilityId = facilityId;
		}
		
		public String getFacilityId() {
			return facilityId;
		}
		public void setOkDoUpdate(String okDoUpdate) {
			this.okDoUpdate = okDoUpdate;
		}
		
		public boolean isOkDoUpdate () {
			return "true".equals(okDoUpdate);
		}

		public String getOkDoUpdate() {
			return okDoUpdate;
		}

		public String getMaterialDesc() {
			return materialDesc;
		}
		
		public BigDecimal getVoc() {
			return voc;
		}
		public String getVocDetect() {
			return vocDetect;
		}
		public String getVocUnit() {
			return vocUnit;
		}
		public BigDecimal getVocLwes() {
			return vocLwes;
		}
		public String getVocLwesDetect() {
			return vocLwesDetect;
		}
		public String getVocLwesUnit() {
			return vocLwesUnit;
		}
		public String getSearchField() {
			return searchField;
		}

		public String getSearchMode() {
			return searchMode;
		}

		public String getMixtureDesc() {
			return mixtureDesc;
		}
		
		public String getCustomerMsdsNumber() {
			return customerMsdsNumber;
		}
		
		public String getSearchArgument() {
			return searchArgument;
		}
		
		public String getPhysicalState() {
			return physicalState;
		}
		
		public String getPhysicalStateSource() {
			return physicalStateSource;
		}
		
		public String getCustomerMixtureNumber() {
			return customerMixtureNumber;
		}
		

		@Override
		public void setHiddenFormFields() {
			addHiddenFormField("searchArgument");
			addHiddenFormField("searchField");
			addHiddenFormField("searchMode");
		}

		public void setMaterialDesc(String materialDesc) {
			this.materialDesc = materialDesc;
		}
		
		public void setVoc(BigDecimal voc) {
			this.voc = voc;
		}
		public void setVocDetect(String vocDetect) {
			this.vocDetect = vocDetect;
		}
		public void setVocUnit(String vocUnit) {
			this.vocUnit = vocUnit;
		}
		
		public void setVocLwes(BigDecimal vocLwes) {
			this.vocLwes = vocLwes;
		}
		public void setVocLwesDetect(String vocLwesDetect) {
			this.vocLwesDetect = vocLwesDetect;
		}
		public void setVocLwesUnit(String vocLwesUnit) {
			this.vocLwesUnit = vocLwesUnit;
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

		public void setMixtureDesc(String mixtureDesc) {
			this.mixtureDesc = mixtureDesc;
		}
		
		public void setCustomerMsdsNumber(String customerMsdsNumber) {
			this.customerMsdsNumber = customerMsdsNumber;
		}
		
		public void setPhysicalState(String physicalState) {
			this.physicalState = physicalState;
		}
		
		public void setPhysicalStateSource(String physicalStateSource) {
			this.physicalStateSource = physicalStateSource;
		}

		public void setCustomerMixtureNumber(String customerMixtureNumber) {
			this.customerMixtureNumber = customerMixtureNumber;
		}
		

	}

