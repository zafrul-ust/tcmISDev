package com.tcmis.client.waste.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TsdfReportViewBean <br>
 * @version: 1.0, Feb 23, 2007 <br>
 *****************************************************************************/


public class TsdfReportViewBean extends BaseDataBean {

	private String tsdfVendorId;
	private String tsdfVendorDesc;
	private String tsdfManifest;
	private Date tsdfShipDate;
	private String tsdfVendorProfileId;
	private String tsdfPackaging;
	private BigDecimal tsdfContainerId;
	private String genManifest;
	private Date genShipDate;
	private String genVendorProfileId;
	private String genPackaging;
	private String genCompanyId;
	private String genFacilityId;
	private String generationPoint;
	private String genWrLineItem;
	private String requestor;
	private BigDecimal genContainerId;
	private String tsdfCompanyId;
	private BigDecimal tsdfWasteRequestId;
	private BigDecimal tsdfLineItem;
	private String tsdfFacilityId;
	private String tsdfLocationId;
	private String genVendorId;
	private String containerAncestry;
        private String tagNumber;
        private String genProfileUrl;
        private String tsdfProfileUrl;
        private String locationGroup;

	//constructor
	public TsdfReportViewBean() {
	}

	//setters
	public void setTsdfVendorId(String tsdfVendorId) {
		this.tsdfVendorId = tsdfVendorId;
	}
	public void setTsdfVendorDesc(String tsdfVendorDesc) {
		this.tsdfVendorDesc = tsdfVendorDesc;
	}
	public void setTsdfManifest(String tsdfManifest) {
		this.tsdfManifest = tsdfManifest;
	}
	public void setTsdfShipDate(Date tsdfShipDate) {
		this.tsdfShipDate = tsdfShipDate;
	}
	public void setTsdfVendorProfileId(String tsdfVendorProfileId) {
		this.tsdfVendorProfileId = tsdfVendorProfileId;
	}
	public void setTsdfPackaging(String tsdfPackaging) {
		this.tsdfPackaging = tsdfPackaging;
	}
	public void setTsdfContainerId(BigDecimal tsdfContainerId) {
		this.tsdfContainerId = tsdfContainerId;
	}
	public void setGenManifest(String genManifest) {
		this.genManifest = genManifest;
	}
	public void setGenShipDate(Date genShipDate) {
		this.genShipDate = genShipDate;
	}
	public void setGenVendorProfileId(String genVendorProfileId) {
		this.genVendorProfileId = genVendorProfileId;
	}
	public void setGenPackaging(String genPackaging) {
		this.genPackaging = genPackaging;
	}
	public void setGenCompanyId(String genCompanyId) {
		this.genCompanyId = genCompanyId;
	}
	public void setGenFacilityId(String genFacilityId) {
		this.genFacilityId = genFacilityId;
	}
	public void setGenerationPoint(String generationPoint) {
		this.generationPoint = generationPoint;
	}
	public void setGenWrLineItem(String genWrLineItem) {
		this.genWrLineItem = genWrLineItem;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public void setGenContainerId(BigDecimal genContainerId) {
		this.genContainerId = genContainerId;
	}
	public void setTsdfCompanyId(String tsdfCompanyId) {
		this.tsdfCompanyId = tsdfCompanyId;
	}
	public void setTsdfWasteRequestId(BigDecimal tsdfWasteRequestId) {
		this.tsdfWasteRequestId = tsdfWasteRequestId;
	}
	public void setTsdfLineItem(BigDecimal tsdfLineItem) {
		this.tsdfLineItem = tsdfLineItem;
	}
	public void setTsdfFacilityId(String tsdfFacilityId) {
		this.tsdfFacilityId = tsdfFacilityId;
	}
	public void setTsdfLocationId(String tsdfLocationId) {
		this.tsdfLocationId = tsdfLocationId;
	}
	public void setGenVendorId(String genVendorId) {
		this.genVendorId = genVendorId;
	}
	public void setContainerAncestry(String containerAncestry) {
		this.containerAncestry = containerAncestry;
	}
        public void setTagNumber(String tagNumber) {
                this.tagNumber = tagNumber;
        }
        public void setGenProfileUrl(String genProfileUrl) {
                this.genProfileUrl = genProfileUrl;
        }
        public void setTsdfProfileUrl(String tsdfProfileUrl) {
                this.tsdfProfileUrl = tsdfProfileUrl;
        }
        public void setLocationGroup(String locationGroup) {
          this.locationGroup = locationGroup;
        }

	//getters
	public String getTsdfVendorId() {
		return tsdfVendorId;
	}
	public String getTsdfVendorDesc() {
		return tsdfVendorDesc;
	}
	public String getTsdfManifest() {
		return tsdfManifest;
	}
	public Date getTsdfShipDate() {
		return tsdfShipDate;
	}
	public String getTsdfVendorProfileId() {
		return tsdfVendorProfileId;
	}
	public String getTsdfPackaging() {
		return tsdfPackaging;
	}
	public BigDecimal getTsdfContainerId() {
		return tsdfContainerId;
	}
	public String getGenManifest() {
		return genManifest;
	}
	public Date getGenShipDate() {
		return genShipDate;
	}
	public String getGenVendorProfileId() {
		return genVendorProfileId;
	}
	public String getGenPackaging() {
		return genPackaging;
	}
	public String getGenCompanyId() {
		return genCompanyId;
	}
	public String getGenFacilityId() {
		return genFacilityId;
	}
	public String getGenerationPoint() {
		return generationPoint;
	}
	public String getGenWrLineItem() {
		return genWrLineItem;
	}
	public String getRequestor() {
		return requestor;
	}
	public BigDecimal getGenContainerId() {
		return genContainerId;
	}
	public String getTsdfCompanyId() {
		return tsdfCompanyId;
	}
	public BigDecimal getTsdfWasteRequestId() {
		return tsdfWasteRequestId;
	}
	public BigDecimal getTsdfLineItem() {
		return tsdfLineItem;
	}
	public String getTsdfFacilityId() {
		return tsdfFacilityId;
	}
	public String getTsdfLocationId() {
		return tsdfLocationId;
	}
	public String getGenVendorId() {
		return genVendorId;
	}
	public String getContainerAncestry() {
		return containerAncestry;
	}
        public String getTagNumber() {
                return tagNumber;
        }
        public String getGenProfileUrl() {
                return genProfileUrl;
        }
        public String getTsdfProfileUrl() {
                return tsdfProfileUrl;
        }
        public String getLocationGroup() {
          return locationGroup;
        }
}