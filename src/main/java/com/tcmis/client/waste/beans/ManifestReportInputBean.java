package com.tcmis.client.waste.beans;

import java.math.BigDecimal;
import java.util.Date;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TdsfWasteReceivingInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class ManifestReportInputBean extends BaseDataBean {

        private String tsdf;
        private String manifest;
        private String submitSearch;

	//constructor
	public ManifestReportInputBean() {
	}

	//setters
        public void setTsdf(String tsdf) {
          this.tsdf = tsdf;
        }
        public void setManifest(String manifest) {
          this.manifest = manifest;
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
        public String getSubmitSearch() {
                return submitSearch;
        }
}