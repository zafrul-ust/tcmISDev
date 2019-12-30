package com.tcmis.supplier.wbuy.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.util.Date;


/******************************************************************************
 * CLASSNAME: OrderListInputBean <br>
 * 
 *****************************************************************************/


public class OrderListInputBean extends BaseDataBean {
	
	private static final long serialVersionUID = 3694211665951357260L;
	
	private String[] supplierIdArray;
	private String[] poStatusChkbxArray;
	private String action;	
	private String showPOs;
	private String isNew;
	private String isAcknowledged;
	private String isResolved;
	private String isConfirmed;
	private String isRejected;
	private String isProblem;
	private String poNumber;
	private String radianPo;
	private String searchPo;
	private Date confirmedFromDate;
    private Date confirmedToDate;
    
	//constructor
	public OrderListInputBean() {
	}


	public String[] getSupplierIdArray() {
		return supplierIdArray;
	}


	public void setSupplierIdArray(String[] supplierIdArray) {
		this.supplierIdArray = supplierIdArray;
	}


	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}


	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}


	/**
	 * @return the showPOs
	 */
	public String getShowPOs() {
		return showPOs;
	}


	/**
	 * @param showPOs the showPOs to set
	 */
	public void setShowPOs(String showPOs) {
		this.showPOs = showPOs;
	}


	/**
	 * @return the isNew
	 */
	public String getIsNew() {
		return isNew;
	}


	/**
	 * @param isNew the isNew to set
	 */
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}


	/**
	 * @return the isAcknowledged
	 */
	public String getIsAcknowledged() {
		return isAcknowledged;
	}


	/**
	 * @param isAcknowledged the isAcknowledged to set
	 */
	public void setIsAcknowledged(String isAcknowledged) {
		this.isAcknowledged = isAcknowledged;
	}


	/**
	 * @return the isResolved
	 */
	public String getIsResolved() {
		return isResolved;
	}


	/**
	 * @param isResolved the isResolved to set
	 */
	public void setIsResolved(String isResolved) {
		this.isResolved = isResolved;
	}


	/**
	 * @return the isConfirmed
	 */
	public String getIsConfirmed() {
		return isConfirmed;
	}


	/**
	 * @param isConfirmed the isConfirmed to set
	 */
	public void setIsConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}


	/**
	 * @return the isRejected
	 */
	public String getIsRejected() {
		return isRejected;
	}


	/**
	 * @param isRejected the isRejected to set
	 */
	public void setIsRejected(String isRejected) {
		this.isRejected = isRejected;
	}


	/**
	 * @return the isProblem
	 */
	public String getIsProblem() {
		return isProblem;
	}


	/**
	 * @param isProblem the isProblem to set
	 */
	public void setIsProblem(String isProblem) {
		this.isProblem = isProblem;
	}


	/**
	 * @param poNumber the poNumber to set
	 */
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}


	/**
	 * @return the poNumber
	 */
	public String getPoNumber() {
		return poNumber;
	}


	/**
	 * @param poStatusChkbxArray the poStatusChkbxArray to set
	 */
	public void setPoStatusChkbxArray(String[] poStatusChkbxArray) {
		this.poStatusChkbxArray = poStatusChkbxArray;
	}


	/**
	 * @return the poStatusChkbxArray
	 */
	public String[] getPoStatusChkbxArray() {
		return poStatusChkbxArray;
	}


	/**
	 * @param radianPo the radianPo to set
	 */
	public void setRadianPo(String radianPo) {
		this.radianPo = radianPo;
	}


	/**
	 * @return the radianPo
	 */
	public String getRadianPo() {
		return radianPo;
	}


	/**
	 * @param searchPo the searchPo to set
	 */
	public void setSearchPo(String searchPo) {
		this.searchPo = searchPo;
	}


	/**
	 * @return the searchPo
	 */
	public String getSearchPo() {
		return searchPo;
	}


	/**
	 * @param confirmedFromDate the confirmedFromDate to set
	 */
	public void setConfirmedFromDate(Date confirmedFromDate) {
		this.confirmedFromDate = confirmedFromDate;
	}


	/**
	 * @return the confirmedFromDate
	 */
	public Date getConfirmedFromDate() {
		return confirmedFromDate;
	}


	/**
	 * @param confirmedToDate the confirmedToDate to set
	 */
	public void setConfirmedToDate(Date confirmedToDate) {
		this.confirmedToDate = confirmedToDate;
	}


	/**
	 * @return the confirmedToDate
	 */
	public Date getConfirmedToDate() {
		return confirmedToDate;
	}


	

	
}