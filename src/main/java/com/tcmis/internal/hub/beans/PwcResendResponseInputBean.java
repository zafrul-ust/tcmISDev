package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;


/**
* Change History --------- 04/21/09 - Shahzad Butt - Added new input variables.
* 
* 
*/
public class PwcResendResponseInputBean extends BaseDataBean 
{
   	private static final long serialVersionUID = 4528995700358994019L;
   	
   	private String poNumberSearch;
   	private String lookupAction;
   	private String orderLookupStatus;
   	private String lookupBy;
   	private String orderNumberSearch;
	private String searchArgument;
    private String action;
    private String customerPo;
    private String customerPoLineNumber;
    private String prNumber;
    private String prLineNumber;
    
    
    public PwcResendResponseInputBean()
	{
	}



	public String getPoNumberSearch() {
		return poNumberSearch;
	}



	public void setPoNumberSearch(String poNumberSearch) {
		this.poNumberSearch = poNumberSearch;
	}



	public String getLookupAction() {
		return lookupAction;
	}



	public void setLookupAction(String lookupAction) {
		this.lookupAction = lookupAction;
	}



	public String getOrderLookupStatus() {
		return orderLookupStatus;
	}



	public void setOrderLookupStatus(String orderLookupStatus) {
		this.orderLookupStatus = orderLookupStatus;
	}



	public String getLookupBy() {
		return lookupBy;
	}



	public void setLookupBy(String lookupBy) {
		this.lookupBy = lookupBy;
	}



	public String getOrderNumberSearch() {
		return orderNumberSearch;
	}



	public void setOrderNumberSearch(String orderNumberSearch) {
		this.orderNumberSearch = orderNumberSearch;
	}



	public String getSearchArgument() {
        return searchArgument;
    }

    public void setSearchArgument(String searchArgument) {
        this.searchArgument = searchArgument;
    }



	public String getCustomerPo() {
		return customerPo;
	}



	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}



	public String getCustomerPoLineNumber() {
		return customerPoLineNumber;
	}



	public void setCustomerPoLineNumber(String customerPoLineNumber) {
		this.customerPoLineNumber = customerPoLineNumber;
	}



	public String getPrNumber() {
		return prNumber;
	}



	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}



	public String getPrLineNumber() {
		return prLineNumber;
	}



	public void setPrLineNumber(String prLineNumber) {
		this.prLineNumber = prLineNumber;
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
   



	
}