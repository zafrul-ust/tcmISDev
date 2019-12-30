package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ItemBuyerCommentsViewBean <br>
 * @version: 1.0, Oct 24, 2006 <br>
 *****************************************************************************/


public class CatalogItemNotesViewBean extends BaseDataBean {

	private BigDecimal personnelId;
	private BigDecimal itemId;
	private String comments;
	private Date transactionDate;
	private BigDecimal recordNo;
	private Date lastUpdated;
	private String enteredByName;
        
        // form input bean vars
	private String originalComments;
    private String delete;


	//constructor
	public CatalogItemNotesViewBean() {
	}

	//setters
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
        
		public BigDecimal getPersonnelId() {
			return personnelId;
		}

		public void setPersonnelId(BigDecimal personnelId) {
			this.personnelId = personnelId;
		}

		public String getOriginalComments() {
			return originalComments;
		}

		public void setOriginalComments(String originalComments) {
			this.originalComments = originalComments;
		}
        
}