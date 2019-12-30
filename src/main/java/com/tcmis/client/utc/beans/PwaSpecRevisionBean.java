package com.tcmis.client.utc.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PwaMaresBean <br>
 * @version: 1.0, May 9, 2005 <br>
 *****************************************************************************/


public class PwaSpecRevisionBean extends BaseDataBean {

	private String partNo;
        private String revision;
	private String revisionDate;
	private String fileName;
	private String comment;


	//constructor
	public PwaSpecRevisionBean() {
	}

	//setters
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
        public void setRevision(String revision) {
                this.revision = revision;
        }
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setCommnet(String comment) {
		this.comment = comment;
	}

	//getters
	public String getPartNo() {
		return partNo;
	}
        public String getRevision() {
                return revision;
        }
	public String getRevisionDate() {
		return revisionDate;
	}
	public String getFileName() {
		return fileName;
	}
	public String getComment() {
		return comment;
	}

}