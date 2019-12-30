package com.tcmis.internal.distribution.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerContactSearchViewBean <br>
 * @version: 1.0, Mar 17, 2009 <br>
 *****************************************************************************/


public class ContactLookUpInputBean extends BaseDataBean {

	private String searchNameMode;
	private String searchNameArgument;
	private String searchPhoneMode;
	private String searchPhoneArgument;
	private String searchCustomerIdMode;
	private String searchCustomerIdArgument;
	private String searchBillToMode;
	private String searchBillToArgument;
	private String searchContactMode;
	private String searchContactArgument;
	private String searchEmailMode;
	private String searchEmailArgument;
	private String legacyCustomerId;
	private String searchSynonymMode;
	private String searchSynonymArgument;
	private String showinactive;
	
	private String uAction;


	//constructor
	public ContactLookUpInputBean() {
	}


	public String getSearchBillToArgument() {
		return searchBillToArgument;
	}


	public void setSearchBillToArgument(String searchBillToArgument) {
		this.searchBillToArgument = searchBillToArgument;
	}


	public String getSearchBillToMode() {
		return searchBillToMode;
	}


	public void setSearchBillToMode(String searchBillToMode) {
		this.searchBillToMode = searchBillToMode;
	}


	public String getSearchContactArgument() {
		return searchContactArgument;
	}


	public void setSearchContactArgument(String searchContactArgument) {
		this.searchContactArgument = searchContactArgument;
	}


	public String getSearchContactMode() {
		return searchContactMode;
	}


	public void setSearchContactMode(String searchContactMode) {
		this.searchContactMode = searchContactMode;
	}


	public String getSearchCustomerIdArgument() {
		return searchCustomerIdArgument;
	}


	public void setSearchCustomerIdArgument(String searchCustomerIdArgument) {
		this.searchCustomerIdArgument = searchCustomerIdArgument;
	}


	public String getSearchCustomerIdMode() {
		return searchCustomerIdMode;
	}


	public void setSearchCustomerIdMode(String searchCustomerIdMode) {
		this.searchCustomerIdMode = searchCustomerIdMode;
	}


	public String getSearchNameArgument() {
		return searchNameArgument;
	}


	public void setSearchNameArgument(String searchNameArgument) {
		this.searchNameArgument = searchNameArgument;
	}


	public String getSearchNameMode() {
		return searchNameMode;
	}


	public void setSearchNameMode(String searchNameMode) {
		this.searchNameMode = searchNameMode;
	}


	public String getSearchPhoneArgument() {
		return searchPhoneArgument;
	}


	public void setSearchPhoneArgument(String searchPhoneArgument) {
		this.searchPhoneArgument = searchPhoneArgument;
	}


	public String getSearchPhoneMode() {
		return searchPhoneMode;
	}


	public void setSearchPhoneMode(String searchPhoneMode) {
		this.searchPhoneMode = searchPhoneMode;
	}


	public String getUAction() {
		return uAction;
	}


	public void setUAction(String action) {
		uAction = action;
	}


	public String getSearchEmailArgument() {
		return searchEmailArgument;
	}


	public void setSearchEmailArgument(String searchEmailArgument) {
		this.searchEmailArgument = searchEmailArgument;
	}


	public String getSearchEmailMode() {
		return searchEmailMode;
	}


	public void setSearchEmailMode(String searchEmailMode) {
		this.searchEmailMode = searchEmailMode;
	}


	/**
	 * @return the legacyCustomerId
	 */
	public String getLegacyCustomerId() {
		return legacyCustomerId;
	}


	/**
	 * @param legacyCustomerId the legacyCustomerId to set
	 */
	public void setLegacyCustomerId(String legacyCustomerId) {
		this.legacyCustomerId = legacyCustomerId;
	}


	/**
	 * @return the searchSynonymMode
	 */
	public String getSearchSynonymMode() {
		return searchSynonymMode;
	}


	/**
	 * @param searchSynonymMode the searchSynonymMode to set
	 */
	public void setSearchSynonymMode(String searchSynonymMode) {
		this.searchSynonymMode = searchSynonymMode;
	}


	/**
	 * @return the searchSynonymArgument
	 */
	public String getSearchSynonymArgument() {
		return searchSynonymArgument;
	}


	/**
	 * @param searchSynonymArgument the searchSynonymArgument to set
	 */
	public void setSearchSynonymArgument(String searchSynonymArgument) {
		this.searchSynonymArgument = searchSynonymArgument;
	}


	public String getShowinactive() {
		return showinactive;
	}


	public void setShowinactive(String showinactive) {
		this.showinactive = showinactive;
	}



}