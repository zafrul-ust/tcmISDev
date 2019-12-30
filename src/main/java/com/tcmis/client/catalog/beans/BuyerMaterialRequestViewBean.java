package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: BuyerMaterialRequestViewBean <br>
 * @version: 1.0, Aug 27, 2007 <br>
 *****************************************************************************/


public class BuyerMaterialRequestViewBean extends BaseDataBean {

	private String branchPlant;
	private BigDecimal buyerId;
	private String fullName;
	private BigDecimal prNumber;
	private BigDecimal radianPo;
	private BigDecimal receiptId;
        private BigDecimal personnelId;
        private String email;


	//constructor
	public BuyerMaterialRequestViewBean() {
	}

	//setters
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
        public void setPersonnelId(BigDecimal personnelId) {
                this.personnelId = personnelId;
        }
        public void setEmail(String email) {
          this.email = email;
        }

	//getters
	public String getBranchPlant() {
		return branchPlant;
	}
	public BigDecimal getBuyerId() {
		return buyerId;
	}
	public String getFullName() {
		return fullName;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
        public BigDecimal getPersonnelId() {
           return personnelId;
        }
        public String getEmail() {
          return email;
        }
}