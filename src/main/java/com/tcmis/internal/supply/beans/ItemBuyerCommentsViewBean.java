package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ItemBuyerCommentsViewBean <br>
 * @version: 1.0, Oct 24, 2006 <br>
 *****************************************************************************/


public class ItemBuyerCommentsViewBean extends BaseDataBean {

	private BigDecimal buyerId;
	private BigDecimal itemId;
	private String comments;
	private Date transactionDate;
	private BigDecimal recordNo;
	private Date lastUpdated;
	private String enteredByName;
        
        // form input bean vars
        private String delete;
        private String changed;


	//constructor
	public ItemBuyerCommentsViewBean() {
	}

	//setters
	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setRecordNo(BigDecimal recordNo) {
		this.recordNo = recordNo;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}


	//getters
	public BigDecimal getBuyerId() {
		return buyerId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getComments() {
		return comments;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public BigDecimal getRecordNo() {
		return recordNo;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public String getEnteredByName() {
		return enteredByName;
	}
        
        /**
         * Getter for property delete.
         * @return Value of property delete.
         */
        public java.lang.String getDelete() {
           return delete;
        }
        
        /**
         * Setter for property delete.
         * @param delete New value of property delete.
         */
        public void setDelete(java.lang.String delete) {
           this.delete = delete;
        }
        
        /**
         * Getter for property changed.
         * @return Value of property changed.
         */
        public java.lang.String getChanged() {
           return changed;
        }
        
        /**
         * Setter for property changed.
         * @param changed New value of property changed.
         */
        public void setChanged(java.lang.String changed) {
           this.changed = changed;
        }
        
}