package com.tcmis.client.waste.beans;

import java.math.BigDecimal;
import java.util.Date;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TdsfWasteReceivingInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class TsdfReportInputBean extends BaseDataBean {

        private String tsdf;
        private String tsdfFacilityId;
        private String tsdfVendor;
        private String tsdfManifest;
        private Date tsdfStartShipDate;
        private Date tsdfEndShipDate;
        private String tsdfProfile;
        private BigDecimal tsdfContainerId;
        private String genCompany;
        private String genFacilityId;
        private String generationPoint;
        private String genManifest;
        private Date genStartShipDate;
        private Date genEndShipDate;
        private String genProfile;
        private BigDecimal genContainerId;
        private String submitSearch;
        private String notShip;
        private String wasteTag;
        private String noManifest;

	//constructor
	public TsdfReportInputBean() {
	}

	//setters
        public void setTsdf(String tsdf) {
          this.tsdf = tsdf;
        }
        public void setTsdfFacilityId(String tsdfFacilityId) {
          this.tsdfFacilityId = tsdfFacilityId;
        }
        public void setTsdfVendor (String tsdfVendor) {
          this.tsdfVendor = tsdfVendor;
        }
        public void setTsdfManifest(String manifest) {
          this.tsdfManifest = manifest;
        }
        public void setTsdfStartShipDate(Date shipDate) {
          this.tsdfStartShipDate = shipDate;
        }
        public void setTsdfEndShipDate(Date shipDate) {
          this.tsdfEndShipDate = shipDate;
        }
        public void setTsdfProfile(String profile) {
          this.tsdfProfile = profile;
        }
        public void setTsdfContainerId(BigDecimal containerId) {
          this.tsdfContainerId = containerId;
        }
        public void setGenCompany(String genCompany) {
          this.genCompany = genCompany;
        }
        public void setGenFacilityId(String genFacilityId) {
          this.genFacilityId = genFacilityId;
        }
        public void setGenerationPoint(String generationPoint) {
          this.generationPoint = generationPoint;
        }
        public void setGenManifest(String manifest) {
          this.genManifest = manifest;
        }
        public void setGenStartShipDate(Date shipDate) {
          this.genStartShipDate = shipDate;
        }
        public void setGenEndShipDate(Date shipDate) {
          this.genEndShipDate = shipDate;
        }
        public void setGenProfile(String profile) {
          this.genProfile = profile;
        }
        public void setGenContainerId(BigDecimal containerId) {
          this.genContainerId = containerId;
        }
        public void setSubmitSearch(String submitSearch) {
                this.submitSearch = submitSearch;
        }
        public void setNotShip(String notShip) {
                this.notShip = notShip;
        }
        public void setWasteTag(String wasteTag) {
          this.wasteTag = wasteTag;
        }
        public void setNoManifest(String noManifest) {
          this.noManifest = noManifest;
        }

	//getters
        public String getTsdf() {
          return tsdf;
        }
        public String getTsdfFacilityId() {
          return tsdfFacilityId;
        }
        public String getTsdfVendor() {
          return tsdfVendor;
        }
        public String getTsdfManifest() {
          return tsdfManifest;
        }
        public Date getTsdfStartShipDate() {
          return tsdfStartShipDate;
        }
        public Date getTsdfEndShipDate() {
          return tsdfEndShipDate;
        }
        public String getTsdfProfile() {
          return tsdfProfile;
        }
        public BigDecimal getTsdfContainerId() {
          return tsdfContainerId;
        }
        public String getGenCompany() {
                return genCompany;
        }
        public String getGenFacilityId() {
          return genFacilityId;
        }
        public String getGenerationPoint() {
          return generationPoint;
        }
        public String getGenManifest() {
          return genManifest;
        }
        public Date getGenStartShipDate() {
          return genStartShipDate;
        }
        public Date getGenEndShipDate() {
          return genEndShipDate;
        }
        public String getGenProfile() {
          return genProfile;
        }
        public BigDecimal getGenContainerId() {
          return genContainerId;
        }
        public String getSubmitSearch() {
                return submitSearch;
        }
        public String getNotShip() {
                return notShip;
        }
        public String getWasteTag() {
          return wasteTag;
        }
        public String getNoManifest() {
          return noManifest;
        }
}