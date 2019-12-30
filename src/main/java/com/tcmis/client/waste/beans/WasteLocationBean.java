package com.tcmis.client.waste.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: WasteLocationBean <br>
 * @version: 1.0, Jan 9, 2007 <br>
 *****************************************************************************/


public class WasteLocationBean extends BaseDataBean {

	private String facilityId;
	private String wasteLocationId;
	private String locationDesc;
	private String locationType;
	private String epaId;
	private String status;
	private String hubBased;
	private String transferToLocationDefault;
	private String autoTransferRequest;
        private String tsdf;
        private String sendEmail;
        private String locationGroup;
        private String tagNumberDisplay;

	//constructor
	public WasteLocationBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setWasteLocationId(String wasteLocationId) {
		this.wasteLocationId = wasteLocationId;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public void setEpaId(String epaId) {
		this.epaId = epaId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setHubBased(String hubBased) {
		this.hubBased = hubBased;
	}
	public void setTransferToLocationDefault(String transferToLocationDefault) {
		this.transferToLocationDefault = transferToLocationDefault;
	}
	public void setAutoTransferRequest(String autoTransferRequest) {
		this.autoTransferRequest = autoTransferRequest;
	}
        public void setTsdf(String stdf) {
          this.tsdf = tsdf;
        }
        public void setSendEmail(String sendEmail) {
          this.sendEmail = sendEmail;
        }
        public void setLocationGroup(String locationGroup) {
          this.locationGroup = locationGroup;
        }
        public void setTagNumberDisplay(String tagNumberDisplay){
          this.tagNumberDisplay = tagNumberDisplay;
        }

	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getWasteLocationId() {
		return wasteLocationId;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public String getLocationType() {
		return locationType;
	}
	public String getEpaId() {
		return epaId;
	}
	public String getStatus() {
		return status;
	}
	public String getHubBased() {
		return hubBased;
	}
	public String getTransferToLocationDefault() {
		return transferToLocationDefault;
	}
	public String getAutoTransferRequest() {
		return autoTransferRequest;
	}
        public String getTsdf() {
          return tsdf;
        }
        public String getSendEmail() {
          return sendEmail;
        }
        public String getLocationGroup() {
          return locationGroup;
        }
        public String getTagNumberDisplay() {
          return tagNumberDisplay;
        }
}