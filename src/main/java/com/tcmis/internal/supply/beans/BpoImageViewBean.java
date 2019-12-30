package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: BpoImageViewBean <br>
 * @version: 1.0, Sep 14, 2006 <br>
 *****************************************************************************/


public class BpoImageViewBean extends BaseDataBean {

	private BigDecimal imageId;
	private BigDecimal bpo;
        private BigDecimal radianPo;
	private Date datePrinted;
	private String fileUrl;
	private String supplier;
	private BigDecimal printedBy;
	private String printedByName;
	private String printType;
	private String supplierName;


	//constructor
	public BpoImageViewBean() {
	}

	//setters
	public void setImageId(BigDecimal imageId) {
		this.imageId = imageId;
	}
	public void setBpo(BigDecimal bpo) {
		this.bpo = bpo;
	}
        public void setRadianPo(BigDecimal radianPo) {
                this.radianPo = radianPo;
        }
	public void setDatePrinted(Date datePrinted) {
		this.datePrinted = datePrinted;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setPrintedBy(BigDecimal printedBy) {
		this.printedBy = printedBy;
	}
	public void setPrintedByName(String printedByName) {
		this.printedByName = printedByName;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	//getters
	public BigDecimal getImageId() {
		return imageId;
	}
	public BigDecimal getBpo() {
		return bpo;
	}
        public BigDecimal getRadianPo() {
                return radianPo;
        }
	public Date getDatePrinted() {
		return datePrinted;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public String getSupplier() {
		return supplier;
	}
	public BigDecimal getPrintedBy() {
		return printedBy;
	}
	public String getPrintedByName() {
		return printedByName;
	}
	public String getPrintType() {
		return printType;
	}
	public String getSupplierName() {
		return supplierName;
	}

}