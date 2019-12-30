package com.tcmis.client.waste.beans;

import java.math.BigDecimal;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TdsfWasteReceivingInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class TsdfWasteReceivingInputBean extends BaseDataBean {

        private String tsdf;
	private String generatorCompany;
        private String tsdfFacilityIdForGenerator;
        private String tsdfWasteItemId;
        private String tsdfFacilityId;
        private String tsdfLocation;
        private String containerId;
        private String dateOfReceipt;
        private String submitSearch;
        private String submitReceive;
        private String ok;

	//constructor
	public TsdfWasteReceivingInputBean() {
	}

	//setters
        public void setTsdf(String tsdf) {
          this.tsdf = tsdf;
        }
	public void setGeneratorCompany(String generatorCompany) {
		this.generatorCompany = generatorCompany;
	}
        public void setTsdfFacilityIdForGenerator(String tsdfFacilityIdForGenerator) {
                this.tsdfFacilityIdForGenerator = tsdfFacilityIdForGenerator;
        }
        public void setTsdfWasteItemId(String tsdfWasteItemId) {
          this.tsdfWasteItemId = tsdfWasteItemId;
        }
        public void setTsdfFacilityId(String tsdfFacilityId) {
          this.tsdfFacilityId = tsdfFacilityId;
        }
        public void setTsdfLocation(String tsdfLocation) {
          this.tsdfLocation = tsdfLocation;
        }
        public void setContainerId(String containerId) {
          this.containerId = containerId;
        }
        public void setDateOfReceipt(String dateOfReceipt) {
          this.dateOfReceipt = dateOfReceipt;
        }
        public void setSubmitSearch(String submitSearch) {
                this.submitSearch = submitSearch;
        }
        public void setSubmitReceive(String submitReceive) {
          this.submitReceive = submitReceive;
        }
        public void setOk(String ok) {
          this.ok = ok;
        }

	//getters
        public String getTsdf() {
          return tsdf;
        }
	public String getGeneratorCompany() {
		return generatorCompany;
	}
        public String getTsdfFacilityIdForGenerator() {
                return tsdfFacilityIdForGenerator;
        }
        public String getTsdfWasteItemId() {
          return tsdfWasteItemId;
        }
        public String getTsdfFacilityId() {
          return tsdfFacilityId;
        }
        public String getTsdfLocation() {
          return tsdfLocation;
        }
        public String getContainerId() {
          return containerId;
        }
        public String getDateOfReceipt() {
          return dateOfReceipt;
        }
        public String getSubmitSearch() {
                return submitSearch;
        }
        public String getSubmitReceive() {
                return submitReceive;
        }
        public String getOk() {
          return ok;
        }
}