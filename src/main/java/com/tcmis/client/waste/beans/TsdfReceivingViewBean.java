package com.tcmis.client.waste.beans;

import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TsdfReceivingViewBean <br>
 * @version: 1.0, Dec 19, 2006 <br>
 *****************************************************************************/


public class TsdfReceivingViewBean extends BaseDataBean {

	private String generatorCompanyId;
	private BigDecimal shipmentId;
	private Date actualShipDate;
	private String generatorFacilityId;
	private BigDecimal orderNo;
	private BigDecimal containerId;
	private String tsdfCompanyId;
	private String tsdfFacilityId;
	private String tsdfFacilityName;
	private BigDecimal wasteItemId;
        private String vendorId;
	private String vendorProfileId;
	private String profileDescription;
	private String packaging;
	private String stateWasteCodes;
	private String epaWasteCodes;
        private String generatorWasteLocationId;
        private String tsdfFacilityIdForGenerator;
        private String manifestId;
        private String manifestState;
        private String manifestCountry;
        private Collection wasteItemConvertViewBean;
        private Collection wasteLocationBean;



	//constructor
	public TsdfReceivingViewBean() {
	}

	//setters
	public void setGeneratorCompanyId(String generatorCompanyId) {
		this.generatorCompanyId = generatorCompanyId;
	}
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setActualShipDate(Date actualShipDate) {
		this.actualShipDate = actualShipDate;
	}
	public void setGeneratorFacilityId(String generatorFacilityId) {
		this.generatorFacilityId = generatorFacilityId;
	}
	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}
	public void setContainerId(BigDecimal containerId) {
		this.containerId = containerId;
	}
	public void setTsdfCompanyId(String tsdfCompanyId) {
		this.tsdfCompanyId = tsdfCompanyId;
	}
	public void setTsdfFacilityId(String tsdfFacilityId) {
		this.tsdfFacilityId = tsdfFacilityId;
	}
	public void setTsdfFacilityName(String tsdfFacilityName) {
		this.tsdfFacilityName = tsdfFacilityName;
	}
	public void setWasteItemId(BigDecimal wasteItemId) {
		this.wasteItemId = wasteItemId;
	}
        public void setVendorId(String vendorId) {
                this.vendorId = vendorId;
        }
	public void setVendorProfileId(String vendorProfileId) {
		this.vendorProfileId = vendorProfileId;
	}
	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setStateWasteCodes(String stateWasteCodes) {
		this.stateWasteCodes = stateWasteCodes;
	}
	public void setEpaWasteCodes(String epaWasteCodes) {
		this.epaWasteCodes = epaWasteCodes;
	}
        public void setWasteItemConvertViewBean(Collection wasteItemConvertViewBean) {
          this.wasteItemConvertViewBean = wasteItemConvertViewBean;
        }
        public void setWasteLocationBean(Collection wasteLocationBean) {
          this.wasteLocationBean = wasteLocationBean;
        }
        public void setGeneratorWasteLocationId(String generatorWasteLocationId) {
          this.generatorWasteLocationId = generatorWasteLocationId;
        }
        public void setTsdfFacilityIdForGenerator(String tsdfFacilityForGenerator) {
          this.tsdfFacilityIdForGenerator = tsdfFacilityIdForGenerator;
        }
        public void setManifestId(String manifestId) {
          this.manifestId = manifestId;
        }
        public void setManifestState(String manifestState) {
          this.manifestState = manifestState;
        }
        public void setManifestCountry(String manifestCountry) {
          this.manifestCountry = manifestCountry;
        }

	//getters
	public String getGeneratorCompanyId() {
		return generatorCompanyId;
	}
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public Date getActualShipDate() {
		return actualShipDate;
	}
	public String getGeneratorFacilityId() {
		return generatorFacilityId;
	}
	public BigDecimal getOrderNo() {
		return orderNo;
	}
	public BigDecimal getContainerId() {
		return containerId;
	}
	public String getTsdfCompanyId() {
		return tsdfCompanyId;
	}
	public String getTsdfFacilityId() {
		return tsdfFacilityId;
	}
	public String getTsdfFacilityName() {
		return tsdfFacilityName;
	}
	public BigDecimal getWasteItemId() {
		return wasteItemId;
	}
        public String getVendorId() {
                return vendorId;
        }
	public String getVendorProfileId() {
		return vendorProfileId;
	}
	public String getProfileDescription() {
		return profileDescription;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getStateWasteCodes() {
		return stateWasteCodes;
	}
	public String getEpaWasteCodes() {
		return epaWasteCodes;
	}
        public Collection getWasteItemConvertViewBean() {
          return wasteItemConvertViewBean;
        }
        public Collection getWasteLocationBean() {
          return wasteLocationBean;
        }
        public String getGeneratorWasteLocationId() {
          return generatorWasteLocationId;
        }
        public String getTsdfFacilityIdForGenerator() {
          return tsdfFacilityIdForGenerator;
        }
        public String getManifestId() {
          return manifestId;
        }
        public String getManifestState() {
          return manifestState;
        }
        public String getManifestCountry() {
          return manifestCountry;
        }
}