package com.tcmis.client.waste.beans;

import java.math.BigDecimal;
import java.util.Date;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TdsfWasteReceivingInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class ManifestSearchInputBean extends BaseDataBean {

        private String tsdf;
        private String manifest;
        private String manifestSearchType;
        private Date shippedToStartDate;
        private Date shippedToEndDate;
        private String notShip;
        private String shippedToSearchType;
        private String shippedTo;
        private String submitSearch;

	//constructor
	public ManifestSearchInputBean() {
	}

	//setters
        public void setTsdf(String tsdf) {
          this.tsdf = tsdf;
        }
        public void setManifest(String manifest) {
          this.manifest = manifest;
        }
        public void setManifestSearchType(String manifestSearchType) {
          this.manifestSearchType = manifestSearchType;
        }
        public void setShippedToStartDate(Date shippedToStartDate) {
          this.shippedToStartDate = shippedToStartDate;
        }
        public void setShippedToEndDate(Date shippedToEndDate) {
          this.shippedToEndDate = shippedToEndDate;
        }
        public void setNotShip(String notShip) {
          this.notShip = notShip;
        }
        public void setShippedToSearchType(String shippedToSearchType) {
          this.shippedToSearchType = shippedToSearchType;
        }
        public void setShippedTo(String shippedTo) {
          this.shippedTo = shippedTo;
        }
        public void setSubmitSearch(String submitSearch) {
                this.submitSearch = submitSearch;
        }

	//getters
        public String getTsdf() {
          return tsdf;
        }
        public String getManifest() {
          return manifest;
        }
        public String getManifestSearchType() {
          return manifestSearchType;
        }
        public Date getShippedToStartDate() {
          return shippedToStartDate;
        }
        public Date getShippedToEndDate() {
          return shippedToEndDate;
        }
        public String getNotShip() {
          return notShip;
        }
        public String getShippedToSearchType() {
          return shippedToSearchType;
        }
        public String getShippedTo() {
          return shippedTo;
        }
        public String getSubmitSearch() {
                return submitSearch;
        }
}