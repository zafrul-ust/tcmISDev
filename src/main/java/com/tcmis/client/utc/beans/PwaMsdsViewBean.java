package com.tcmis.client.utc.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PwaMsdsViewBean <br>
 * @version: 1.0, Jul 25, 2006 <br>
 *****************************************************************************/


public class PwaMsdsViewBean extends BaseDataBean {

	private String catalogItemId;
	private String catalogComponentId;
	private String mfgName;
	private String urlIn;
	private String outputFilename;
	private Date revisionDate;
	private Date pwaRevisionDate;
	private String catalogComponentItemId;
        private String differentRev;
        private String content;

	//constructor
	public PwaMsdsViewBean() {
	}

	//setters
	public void setCatalogItemId(String catalogItemId) {
		this.catalogItemId = catalogItemId;
	}
	public void setCatalogComponentId(String catalogComponentId) {
		this.catalogComponentId = catalogComponentId;
	}
	public void setMfgName(String mfgName) {
		this.mfgName = mfgName;
	}
	public void setUrlIn(String urlIn) {
		this.urlIn = urlIn;
	}
	public void setOutputFilename(String outputFilename) {
		this.outputFilename = outputFilename;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setPwaRevisionDate(Date pwaRevisionDate) {
		this.pwaRevisionDate = pwaRevisionDate;
	}
        public void setCatalogComponentItemId(String catalogComponentItemId) {
                this.catalogComponentItemId = catalogComponentItemId;
        }
        public void setDifferentRev(String differentRev) {
          this.differentRev = differentRev;
        }
        public void setContent(String content) {
          this.content = content;
        }

	//getters
	public String getCatalogItemId() {
		return catalogItemId;
	}
	public String getCatalogComponentId() {
		return catalogComponentId;
	}
	public String getMfgName() {
		return mfgName;
	}
	public String getUrlIn() {
		return urlIn;
	}
	public String getOutputFilename() {
		return outputFilename;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public Date getPwaRevisionDate() {
		return pwaRevisionDate;
	}
        public String getCatalogComponentItemId() {
                return catalogComponentItemId;
        }
        public String getDifferentRev() {
          return differentRev;
        }
        public String getContent() {
          return content;
        }
}